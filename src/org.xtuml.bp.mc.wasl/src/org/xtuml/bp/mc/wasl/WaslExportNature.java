package org.xtuml.bp.mc.wasl;

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

public class WaslExportNature extends AbstractNature {
    // The shared instance
    private static WaslExportNature singleton;

    /**
     * identifier of this nature in plugin.xml - (concatenate pluginid.natureid)
     */
    public static final String MC_NATURE_ID = "org.xtuml.bp.mc.wasl.MCNature"; //NON-NLS-1
    public static final String BUILDER_ID = "org.xtuml.bp.mc.wasl.wasl_builder";

    public WaslExportNature() {
        super(Activator.getDefault(), BUILDER_ID);
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
        IProjectDescription desc = getProject().getDescription();
        List<ICommand> commands = new ArrayList<>(Arrays.asList(desc.getBuildSpec()));
        ICommand command = desc.newCommand();
        command.setBuilderName(BUILDER_ID);
        commands.add(0, command);
        desc.setBuildSpec(commands.toArray(new ICommand[0]));
        getProject().setDescription(desc, null);
    }

    /**
     * Returns the shared instance. Creates if it has not yet been created
     * 
     * @return the shared instance
     */
    public static WaslExportNature getDefault() {
        if (WaslExportNature.singleton == null) {
            WaslExportNature.singleton = new WaslExportNature();
        }
        return WaslExportNature.singleton;
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
    public boolean removeNature(IProject project) {
        return removeNature(project, MC_NATURE_ID);
    }

}
