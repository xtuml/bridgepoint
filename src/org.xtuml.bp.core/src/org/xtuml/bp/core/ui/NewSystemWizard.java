package org.xtuml.bp.core.ui;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.common.BaseModelDelta;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ComponentResourceListener;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;

public class NewSystemWizard extends DelegatingWizard implements INewWizard {

    private static final String MC_EXTENSION_POINT_NAME = "org.xtuml.bp.core.model-compilers";

    // Reference to the pages provided by this wizard
    private WizardNewSystemCreationPage m_creationPage;

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        WizardNewSystemCreationPage creationPage = new WizardNewSystemCreationPage("newxtUMLSystemCreationPage");//$NON-NLS-1$
        creationPage.setTitle("New xtUML Project");
        creationPage.setDescription("Create a new xtUML project");
        setWindowTitle("New xtUML Project");
        this.addPage(creationPage);
        setNeedsProgressMonitor(true);
        m_creationPage = creationPage;
        String[] mcis = getChoices();
        if (mcis.length > 0) {
            addPage(new ModelCompilerChooserPage("newxtUMLModelCompilerChooser", "xtUML Model Compilers",
                    "Select model compilers to use with this xtUML project", "Available xtUML model compilers:"));
        }
    }

    @Override
    public String getExtensionPoint() {
        return MC_EXTENSION_POINT_NAME;
    }

    @Override
    public boolean performFinish() {
        IProject newProject = createProject();
        if (newProject != null) {
            XtUMLNature.addNature(newProject);
            final String name = m_creationPage.getProjectName();
            NewSystemWizard.createSystemModel(newProject, name);
            for (IDelegateWizard delegate : getDelegateWizards()) {
                delegate.setExtraData(newProject);
            }
            return super.performFinish();
        } else {
            return false;
        }
    }

    private static void createSystemModel(IProject newProject, final String name) {
        ClassQueryInterface_c query = new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((SystemModel_c) candidate).getName().equals(name);
            }
        };
        final Ooaofooa modelRoot = Ooaofooa.getDefaultInstance();
        SystemModel_c newModel = SystemModel_c.SystemModelInstance(modelRoot, query);
        if (newModel == null) {
            newModel = new SystemModel_c(modelRoot);
            newModel.setUseglobals(true);
            // need to fire a created event so that
            // the diagram elements are created
            Ooaofooa.getDefaultInstance()
                    .fireModelElementCreated(new BaseModelDelta(Modeleventnotification_c.DELTA_NEW, newModel));
        }
        ModelRoot.disableChangeNotification();
        try {
            newModel.setName(name);
        } finally {
            ModelRoot.enableChangeNotification();
        }
        final PersistableModelComponent newComp = PersistenceManager.createRootComponent(newProject, newModel);
        IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                ComponentResourceListener.setIgnoreResourceChanges(true);
                newComp.persist();
            }
        };
        try {
            ResourcesPlugin.getWorkspace().run(runnable, new NullProgressMonitor());
            PersistenceManager.getDefaultInstance().loadProjects(List.of(newProject), new NullProgressMonitor());
        } catch (CoreException e) {
            CorePlugin.logError("Failed to create System Model data file", e);
        }

    }

    private IProject createProject() {
        final IProject newProjectHandle = m_creationPage.getProjectHandle();
        String name = m_creationPage.getProjectName();

        // UI ensures project doesn't exist, so create a new project
        try {
            IPath defaultPath = Platform.getLocation();
            IPath newPath = m_creationPage.getLocationPath();
            if (defaultPath.equals(newPath))
                newPath = null;
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            final IProjectDescription description = workspace.newProjectDescription(newProjectHandle.getName());
            description.setLocation(newPath);

            newProjectHandle.create(description, new NullProgressMonitor());
            newProjectHandle.open(new NullProgressMonitor());
            return newProjectHandle;
        } catch (CoreException e) {
            CorePlugin.logError("create project '" + name + "' error", e);
        }
        return null;
    }

    private static class WizardNewSystemCreationPage extends WizardNewProjectCreationPage {

        /**
         * Creates a new project creation wizard page.
         *
         * @param pageName
         *            the name of this page
         */
        public WizardNewSystemCreationPage(String pageName) {
            super(pageName);
        }

        /**
         * Returns whether the chosen system name is valid (specifically, it checks that
         * the name is unique for OS's that cannot distinguish files with the same name
         * in a different case).
         *
         * @return <code>true</code> if all controls are valid, and <code>false</code>
         *         if at least one is invalid
         */
        public boolean validateSystemName(final String projectNameContents) {
            if (CorePlugin.osIsCaseInsensitive()) {
                // can't be the same as an existing project
                IProject[] sys_set = CorePlugin.getWorkspace().getRoot().getProjects();
                for (int i = 0; i < sys_set.length; ++i) {
                    if (projectNameContents.toLowerCase().equals((sys_set[i]).getName().toLowerCase())) {
                        return false;
                    }
                }
            }
            return true;
        }

        /**
         * Returns whether this page's controls currently all contain valid values.
         *
         * @return <code>true</code> if all controls are valid, and <code>false</code>
         *         if at least one is invalid
         */
        protected boolean validatePage() {
            if (super.validatePage()) {
                // super.validatePage() has checked for an exact name match
                if (!validateSystemName(super.getProjectName())) {
                    setErrorMessage(
                            "The underlying operating system is case insensitive\nThe project name must be unique in this workspace");
                    setMessage(null);
                    return false;
                }
                setErrorMessage(null);
                setMessage(null);
                return true;
            }
            return false;
        }

        public void createControl(Composite parent) {
            // call the supertype createControl to setup control
            super.createControl(parent);
            // add the F1 context support to created control
            PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), "org.xtuml.bp.core.newProjectDialogId");
        }
    }
}
