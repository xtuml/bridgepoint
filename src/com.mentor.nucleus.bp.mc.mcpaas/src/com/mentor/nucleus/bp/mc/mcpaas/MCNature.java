package com.mentor.nucleus.bp.mc.mcpaas;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.VariablesPlugin;

import com.mentor.nucleus.bp.mc.AbstractActivator;
import com.mentor.nucleus.bp.mc.AbstractNature;
import com.mentor.nucleus.bp.mc.MCBuilderArgumentHandler;

public class MCNature extends AbstractNature {
	// The shared instance
	private static MCNature singleton;

	/**
	 * identifier of this nature in plugin.xml - (concatenate pluginid.natureid)
	 */
	public static final String MC_NATURE_ID = "com.mentor.nucleus.bp.mc.mcpaas.MCNature"; //NON-NLS-1

	/**
	 * identifier of this nature in plugin.xml - (concatenate
	 * pluginid.exportbuilderid)
	 */
	public static final String EXPORT_BUILDER_ID = "com.mentor.nucleus.bp.mc.mcpaas.export_builder"; //NON-NLS-1

	public MCNature() {
		super(Activator.getDefault(), EXPORT_BUILDER_ID);
		singleton = this;
	}

	/**
	 * Returns the shared instance. Creates if it has not yet been created
	 * 
	 * @return the shared instance
	 */
	public static MCNature getDefault() {
		if (MCNature.singleton == null) {
			MCNature.singleton = new MCNature();
		}
		return MCNature.singleton;
	}

	@Override
	public boolean hasNature(IProject project) {
		return hasNature(project, MC_NATURE_ID);
	}

	@Override
	public boolean addNature(IProject project) {
		return addNature(project, MC_NATURE_ID);
	}

	@Override
    public void configure() throws CoreException {
	    // TODO - This could all be refactored in AbstractNature to configure_common()
	    //   Then the AbstractNature::configure() would call configure_common() and the MCArgumentHandler bits
	    //   where as this would call configure_common() and not do the argument handler stuff.
        String src = VariablesPlugin.getDefault().getStringVariableManager()
                .performStringSubstitution(MC_ROOT_DIR_ENV_VAR_REF);
        String dest = getProject().getLocation().toOSString();

        // Add required folders
        IFolder srcFolder = getProject().getFolder(AbstractActivator.SRC_FOLDER_NAME);
        IFolder genFolder = getProject().getFolder(AbstractActivator.GEN_FOLDER_NAME);
        IFolder builderFolder = getProject().getFolder(EXTERNALTOOLBUILDER_FOLDER);
        createFolderIfNonexistent(srcFolder);
        createFolderIfNonexistent(genFolder);
        createFolderIfNonexistent(builderFolder);

        String[] srcFileDef = getFiles("system", "src", ""); //$NON-NLS-1$ //$NON-NLS-2$  //$NON-NLS-3$
        String[] destFileDef = getFiles("system", "dest", ""); //$NON-NLS-1$ //$NON-NLS-2$  //$NON-NLS-3$
        for (int i = 0; i < srcFileDef.length; i++) {
            IPath srcFilePath = new Path(src + File.separator + srcFileDef[i]);
            if (srcFilePath.toFile().exists()) {
                IPath dstPath = new Path(destFileDef[i]);
                // ensure the full path is created as required
                String[] segs = dstPath.segments();
                String curPathName = "";
                for (int j = 0; j < segs.length - 1; j++) {
                    curPathName = curPathName + File.separator + segs[j];
                    IFolder fldr = getProject().getFolder(curPathName);
                    if (!fldr.exists()) {
                        createFolderIfNonexistent(fldr);
                    }
                }
                IPath destPath = new Path(destFileDef[i]);
                IFolder destFolder = getProject().getFolder(destPath
                        .uptoSegment(destPath.segmentCount() - 1));
                File srcFile = srcFilePath.toFile();
                IFile destFile = destFolder.getFile(destPath.lastSegment());
                if (!destFile.exists()) {
                    try {
                        abstractActivator.copyFile(srcFile.getAbsolutePath(),
                                destFile.getLocation().toOSString());
                    } catch (IOException e) {
                        String err_msg = "Error copying file " + srcFileDef[i]
                                + " in the " + getProject().getName() + " project ";
                        abstractActivator.logError(err_msg, e);
                    }
                }
            }
        }
        IPath srcLaunchFolder = getLauchSpecFolder();
        // add builder specification file to builder folder
        IPath srcLaunchFile = new Path(srcLaunchFolder + MC_LAUNCH_ID);

        String tgtFilePath = dest.toString() + File.separator
                + EXTERNALTOOLBUILDER_FOLDER + File.separator + MC_LAUNCH_ID;
        IFolder destLaunchFolder = getProject()
                .getFolder(EXTERNALTOOLBUILDER_FOLDER);
        createFolderIfNonexistent(destLaunchFolder);
        IFile destLaunchFile = destLaunchFolder.getFile(MC_LAUNCH_ID);

        if (srcLaunchFile.toFile().exists()) {
            if (!destLaunchFile.exists()) {
                try {
                    abstractActivator.copyFile(srcLaunchFile.toString(),
                            tgtFilePath);
                } catch (IOException e) {
                    String err_msg = "Error copying file " + MC_LAUNCH_ID
                            + " in the " + getProject().getName() + " project ";
                    abstractActivator.logError(err_msg, e);
                }
            }
        }

        // Add the Builder to the project
        addBuilderToBuildSpec(getProject(), getBuilderID());

        // refresh directory to pick up new files
        try {
            getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            abstractActivator.logError("During refresh while adding nature", e);
        }
    }

}
