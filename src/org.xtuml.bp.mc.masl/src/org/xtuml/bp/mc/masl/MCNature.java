package org.xtuml.bp.mc.masl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.xtuml.bp.mc.AbstractActivator;
import org.xtuml.bp.mc.AbstractNature;
import org.xtuml.bp.utilities.build.BuilderManagement;

public class MCNature extends AbstractNature {
    // The shared instance
    private static MCNature singleton;

    /**
     * identifier of this nature in plugin.xml - (concatenate pluginid.natureid)
     */
    public static final String MC_NATURE_ID = "org.xtuml.bp.mc.masl.MCNature"; //NON-NLS-1

    /**
     * identifier of this nature in plugin.xml - (concatenate
     * pluginid.exportbuilderid)
     */
    public static final String EXPORT_BUILDER_ID = "org.xtuml.bp.mc.masl.export_builder"; //NON-NLS-1

    public MCNature() {
        super(Activator.getDefault(), EXPORT_BUILDER_ID);
        singleton = this;
    }

    @Override
    public void configure() throws CoreException {
        // Add required folders
        IFolder genFolder = getProject().getFolder(AbstractActivator.GEN_FOLDER_NAME);
        createFolderIfNonexistent(genFolder);

        // Configure builders
        configureBuilders();

        // refresh directory to pick up new files
        try {
            getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            abstractActivator.logError("During refresh while adding nature", e);
        }
    }

    private void configureBuilders() throws CoreException {
        // Get project description and then the associated build commands
        IProjectDescription desc = getProject().getDescription();
        List<ICommand> commands = new ArrayList<>(Arrays.asList(desc.getBuildSpec()));
        
        // add builder to project
        ICommand command = desc.newCommand();
        command.setBuilderName(MaslExportBuilder.BUILDER_ID);
        commands.add(0, command);

        // Determine if pre-builder already associated
        if (BuilderManagement.hasBuilder(getProject(), EXPORT_BUILDER_ID) == -1) {
            command = desc.newCommand();
            command.setBuilderName(EXPORT_BUILDER_ID);
            commands.add(0, command);
        }
        desc.setBuildSpec(commands.toArray(new ICommand[0]));
        getProject().setDescription(desc, null);
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

}
