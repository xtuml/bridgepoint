package org.xtuml.bp.mc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xtuml.bp.utilities.build.BuilderManagement;

/**
 * implementation of a nature that customizes a project by adding the nature to
 * the project description.
 */
public abstract class AbstractNature implements IProjectNature {
    /** ID of BridgePoint Model Compilers **/
    public static final String C_SOURCE_MC_ID = "org.xtuml.bp.mc.c.source";
    public static final String C_BINARY_MC_ID = "org.xtuml.bp.mc.c.binary";
    public static final String SYSTEMC_SOURCE_MC_ID = "org.xtuml.bp.mc.systemc.source";
    public static final String CPP_SOURCE_MC_ID = "org.xtuml.bp.mc.cpp.source";

    public static final String C_SOURCE_MC_ID_OLD = "com.mentor.nucleus.bp.mc.c.source";
    public static final String C_BINARY_MC_ID_OLD = "com.mentor.nucleus.bp.mc.c.binary";
    public static final String SYSTEMC_SOURCE_MC_ID_OLD = "com.mentor.nucleus.bp.mc.systemc.source";
    public static final String CPP_SOURCE_MC_ID_OLD = "com.mentor.nucleus.bp.mc.cpp.source";

    public static final String MC_LAUNCH_ID = "Model Compiler.launch"; //$NON-NLS-1$

    public static final String EXTERNALTOOLBUILDER_FOLDER = ".externalToolBuilders"; //$NON-NLS-1$

    public static final String MC_ROOT_DIR = "tools/mc";

    public static final String MANIFEST_FILE_NAME = "default-manifest.xml"; //$NON-NLS-1$
    public static final String MANIFEST_FILE_DIR = MC_ROOT_DIR + File.separator + "schema"; //$NON-NLS-1$

    public static final String ARCHETYPE_FOLDER_NAME = "arc"; //$NON-NLS-1$
    public static final String SPECIALIZED_ARCHETYPE_FOLDER_NAME = "specialized"; //$NON-NLS-1$

    // This file is used to identify the fact that the SystemC model compiler is
    // present
    public static final String SystemC_Archetype = "t.sysc_main.c"; //$NON-NLS-1$

    public static String LAUNCH_ATTR_TOOL_LOCATION = "org.eclipse.ui.externaltools.ATTR_LOCATION"; //$NON-NLS-1$
    public static String LAUNCH_ATTR_TOOL_ARGS = "org.eclipse.ui.externaltools.ATTR_TOOL_ARGUMENTS"; //$NON-NLS-1$

    public static String BUILD_SETTINGS_FILE = "build_settings/build_setting.properties";

    /** To hold associated project reference */
    private IProject project;

    protected AbstractActivator abstractActivator;
    private String builderID = null;

    public AbstractNature(AbstractActivator pAbstractActivator, String pBuilderID) {
        super();
        abstractActivator = pAbstractActivator;
        builderID = pBuilderID;
    }

    public boolean hasNature(IProject project, final String natureId) {
        boolean ret_val = false;
        try {
            ret_val = project.isOpen() && project.hasNature(natureId);
        } catch (Exception e) {
            abstractActivator
                    .logError("Error checking for nature \"" + natureId + "\" for project " + project.getName(), e);
        }
        return ret_val;
    }

    public boolean addNature(IProject project, final String natureId) {
        boolean hasNature = hasNature(project, natureId);
        if (!hasNature) {
            try {
                IProjectDescription description = project.getDescription();
                String[] natures = description.getNatureIds();
                String[] newNatures = new String[natures.length + 1];
                System.arraycopy(natures, 0, newNatures, 0, natures.length);
                newNatures[natures.length] = natureId;
                description.setNatureIds(newNatures);
                project.setDescription(description, null);

                hasNature = true;

            } catch (CoreException e) {
                abstractActivator.logError(
                        "Error adding the nature \"" + natureId + "\" to the " + project.getName() + "project.", e);
            }
        }
        return hasNature;
    }

    public boolean removeNature(IProject project, final String natureId) {
        boolean hasNature = hasNature(project, natureId);
        if (hasNature) {
            try {
                IProjectDescription description = project.getDescription();
                String[] natures = description.getNatureIds();
                String[] newNatures = new String[natures.length - 1];
                int curIndex = 0;
                int newIndex = 0;
                for (; curIndex < natures.length; ++curIndex) {
                    if (!natures[curIndex].equals(natureId)) {
                        newNatures[newIndex] = natures[curIndex];
                        newIndex++;
                    }
                }
                description.setNatureIds(newNatures);
                project.setDescription(description, null);

                hasNature = true;

            } catch (CoreException e) {
                abstractActivator.logError(
                        "Error removing the nature \"" + natureId + "\" from the " + project.getName() + "project.", e);
            }
        }
        return hasNature;
    }

    public void removeAllMCNatures(IProject project) {
        try {
            // First remove the old MC nature. We also do some housekeeping here
            // to remove the old (deprecated) XMI Nature if it still exists on the project.
            IProjectDescription description = project.getDescription();
            String[] natures = description.getNatureIds();
            int curIndex = 0;
            for (; curIndex < natures.length; ++curIndex) {
                if (natures[curIndex].matches(".*bp.+mc.*MC.*Nature")) {
                    removeNature(project, natures[curIndex]);
                }
            }

            // Next remove the prior builders for pre-builder and the MC itself
            BuilderManagement.findAndRemoveBuilder(project, ".*bp.+mc.*export_builder.*");
            BuilderManagement.findAndRemoveBuilder(project, ".*externalToolBuilders.*Model Compiler.+launch.*");
        } catch (CoreException ce) {
            abstractActivator.logError("Could not read project description data for  " + project.getName() + "project.",
                    ce);
        }
        return;
    }

    /**
     * Customizes the project by adding a nature and builder. Note that this gets
     * called by the Eclipse framework when a nature is added. In our case this
     * happens in addNature) when the call to IProjectDescription.setNatureIds() is
     * made.
     */
    public void configure() throws CoreException {
        String homedir = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
        homedir = homedir.replaceFirst("file:", "");
        String src = homedir + File.separator + MC_ROOT_DIR;

        // Add required folders
        IFolder srcFolder = project.getFolder(AbstractActivator.SRC_FOLDER_NAME);
        IFolder genFolder = project.getFolder(AbstractActivator.GEN_FOLDER_NAME);
        createFolderIfNonexistent(srcFolder);
        createFolderIfNonexistent(genFolder);

        String[] srcFileDef = getFiles("system", "src", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        String[] destFileDef = getFiles("system", "dest", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        for (int i = 0; i < srcFileDef.length; i++) {
            IPath srcFilePath = new Path(src + File.separator + srcFileDef[i]);
            if (srcFilePath.toFile().exists()) {
                IPath dstPath = new Path(destFileDef[i]);
                // ensure the full path is created as required
                String[] segs = dstPath.segments();
                String curPathName = "";
                for (int j = 0; j < segs.length - 1; j++) {
                    curPathName = curPathName + File.separator + segs[j];
                    IFolder fldr = project.getFolder(curPathName);
                    if (!fldr.exists()) {
                        createFolderIfNonexistent(fldr);
                    }
                }
                IPath destPath = new Path(destFileDef[i]);
                IFolder destFolder = project.getFolder(destPath.uptoSegment(destPath.segmentCount() - 1));
                File srcFile = srcFilePath.toFile();
                IFile destFile = destFolder.getFile(destPath.lastSegment());
                if (!destFile.exists()) {
                    try {
                        copyFile(srcFile.getAbsolutePath(), destFile.getLocation().toOSString());
                    } catch (IOException e) {
                        String err_msg = "Error copying file " + srcFileDef[i] + " in the " + project.getName()
                                + " project ";
                        abstractActivator.logError(err_msg, e);
                    }
                }
            }
        }

        // Add the Builder to the project
        addBuilderToBuildSpec(project, builderID);

        // refresh directory to pick up new files
        try {
            project.refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            abstractActivator.logError("During refresh while adding nature", e);
        }
    }

    protected void createFolderIfNonexistent(IFolder srcFolder) {
        if (!srcFolder.exists()) {
            try {
                srcFolder.create(false, true, null);
            } catch (CoreException e) {
                abstractActivator.logError("Error creating src folder in the " + project.getName(), e);
            }
        }
    }

    /**
     * Adds the builder to the project description for the selected project if it
     * does not already exist.
     */
    private void addBuilderToBuildSpec(IProject project, String builderID) throws CoreException {

        // Get project description and then the associated build commands
        IProjectDescription desc = project.getDescription();
        ICommand[] commands = desc.getBuildSpec();

        if (BuilderManagement.hasBuilder(project, builderID) == -1) {
            commands = desc.getBuildSpec();

            // add builder to project
            ICommand command = desc.newCommand();
            command.setBuilderName(builderID);
            ICommand[] newCommands = new ICommand[commands.length + 1];

            // Add it before other builders.
            System.arraycopy(commands, 0, newCommands, 1, commands.length);
            newCommands[0] = command;
            desc.setBuildSpec(newCommands);
            project.setDescription(desc, null);
        }
    }

    public void deconfigure() throws CoreException {
    }

    /**
     * Returns local reference to associated project
     */
    public IProject getProject() {
        return project;
    }

    /**
     * Saves local reference to associated project.
     */
    public void setProject(IProject value) {
        project = value;
    }

    /**
     * Gets the list of files either the source or the destination as required If a
     * model name is supplied, the name is prepended to the destination file name.
     * 
     * @param type
     * @param srcOrDest
     * @param modelName
     * @return
     */
    private String[] getFiles(String type, String srcOrDest, String modelName) {
        try {
            String homedir = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
            homedir = homedir.replaceFirst("file:", "");
            String mc_path = homedir + File.separator + MANIFEST_FILE_DIR;
            File manifest = new File(mc_path + File.separator + MANIFEST_FILE_NAME);
            if (manifest.exists()) {
                FileInputStream file = new FileInputStream(manifest);
                DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = parser.parse(file);
                file.close();
                document.getDocumentElement().normalize();
                NodeList outerNodes = document.getElementsByTagName(type);
                if (outerNodes != null) {
                    Node outerNode = outerNodes.item(0);
                    if (outerNode != null) {
                        NodeList nodes = outerNode.getChildNodes();
                        int resultCount = 0;
                        for (int s = 0; s < nodes.getLength(); s++) {
                            Node node = nodes.item(s);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                resultCount++;
                            }
                        }
                        String[] result = new String[resultCount];
                        resultCount = 0;
                        for (int s = 0; s < nodes.getLength(); s++) {
                            Node node = nodes.item(s);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element nodeElement = (Element) node;
                                String path = nodeElement.getAttribute(srcOrDest);
                                if (srcOrDest.equals("dest") && modelName.length() != 0) { //$NON-NLS-1$
                                    String prefix = nodeElement.getAttribute("prefix"); //$NON-NLS-1$
                                    if (prefix.equals("true")) { //$NON-NLS-1$
                                        Path p = new Path(path);
                                        String[] segs = p.segments();
                                        String filename = segs[p.segmentCount() - 1];
                                        segs[p.segmentCount() - 1] = modelName + filename; // $NON-NLS-1$
                                        path = "";
                                        for (int i = 0; i < segs.length; i++) {
                                            path = path + File.separator + segs[i];
                                        }
                                    }
                                }
                                if (path != null) {
                                    result[resultCount++] = path;
                                }
                            }
                        }
                        return result;
                    }
                }
            }
        } catch (Exception e) {
            abstractActivator.logError("Problem loading manifest file: ", e);
        }
        return new String[0];
    }

    private static void copyFile(String inputFile, String outputFile) throws IOException {
        FileInputStream srcStream = new FileInputStream(inputFile);
        FileChannel srcChannel = srcStream.getChannel();

        // Create channel on the destination
        FileOutputStream dstStream = new FileOutputStream(outputFile);
        FileChannel dstChannel = dstStream.getChannel();

        // Copy file contents from source to destination
        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

        // Close the channels
        srcStream.close();
        srcChannel.close();
        dstStream.close();
        dstChannel.close();
    }

    public String getBuilderID() {
        return builderID;
    }

    public abstract boolean hasNature(IProject project);

    /**
     * Add the nature to the project if it does not yet have the nature. The process
     * requires that you get the project description, get the current nature set,
     * and then add the new nature to the set.
     * 
     * This causes the Eclipse framework to call AbstractNature.configure() which
     * sets-up this new nature. This happens in when the call to
     * IProjectDescription.setNatureIds() is made in this function.
     */
    public abstract boolean addNature(IProject project);

}
