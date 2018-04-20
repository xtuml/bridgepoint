package org.xtuml.bp.welcome.gettingstarted;

//====================================================================
//
//    File: $RCSfile: SampleProjectGettingStartedAction.java,v $
//    Version: $Revision: 1.9 $
//    Modified: $Date: 2013/06/12 13:09:36 $
//
//    (c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesModel;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectPreferences;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.core.util.WorkspaceUtil;
import org.xtuml.bp.utilities.ui.ProjectUtilities;
import org.xtuml.bp.welcome.WelcomePlugin;

public class SampleProjectGettingStartedAction implements IIntroAction {

    public void run(IIntroSite site, Properties params) {
        String modelName = params.getProperty("model", "load_error");
        String singleFile = params.getProperty("SingleFileModel", "false");
        String enableIPR = params.getProperty("enableIPR", "false");
        String importIntoWorkspace = params.getProperty("ImportIntoWorkspace", "true");
        String launchGettingStartedHelp = params.getProperty("LaunchGettingStartedHelp", "true");
        String readmePath = params.getProperty("readmePath", "");
        String exes = params.getProperty("exes", "");
        String dialect = params.getProperty("dialect", "OAL");
        if (modelName.equals("load_error")) {
               CorePlugin.logError(
                    "The SampleProjectGettingStartedAction could not determine the model requested.", null);
               return;
        } else {
            setup(modelName, singleFile, enableIPR, importIntoWorkspace, launchGettingStartedHelp, readmePath, exes, dialect);
        }
    }

    private void setup(String modelName, String singleFile, String enableIPR, String enableImportIntoWorkspace, String launchGettingStarted, String readmePath, String exes, String dialect) {
        // create project and all necessary parts
    	boolean setupSucceeded = false;
    	if ( modelName.contains(",") && singleFile.equals("zip") ) {
            setupSucceeded = setupProjects(modelName.split(",")[0], Arrays.copyOfRange(modelName.split(","), 1, modelName.split(",").length), singleFile, enableIPR, enableImportIntoWorkspace);
    	}
    	else {
            setupSucceeded = setupProject(modelName, singleFile, enableIPR, enableImportIntoWorkspace);
    	}
        if ( setupSucceeded ) {
            // show the xtUML Modeling perspective if not shown
            ProjectUtilities.openxtUMLPerspective();
            // close welcome page for the xtUML perspective

            closeWelcomePage();

            // if there are executables, assure their permissions are set properly
            setExePermissions( exes );

            // check for dialect specific workspace settings
            checkWorkspaceSettings( dialect );

            if (singleFile.compareToIgnoreCase("true") != 0)  {
                // See if there is a readme file and if so open it
                openReadme(modelName, readmePath);
            }

            if (launchGettingStarted.equalsIgnoreCase("true")) {
                // Display the getting started help
                PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(IGettingStartedConstants.gpsGettingStartedLink);
            }
        }
    }

    private boolean setupProject(String modelName, String singleFile, String enableIPR, String enableImportIntoWorkspace) {
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(modelName);
        boolean setupSucceeded = false;
        try {
            if (project.exists()) {
                boolean userOKsNotice = false;
                String msg = "Your workspace already contains a \"" + modelName
                        + "\" project.\n\nDo you wish to delete this project and create a new one";
                userOKsNotice = UIUtil.displayYesNoQuestion(msg);

                if (userOKsNotice) {
                    try {
                        ProjectUtilities.deleteProject(project);
                    } catch (CoreException e) {
                        CorePlugin.logError("Could not delete existing "
                                + modelName + " project", e);
                        return false;
                    }
                } else {
                    project.open(new NullProgressMonitor());
                    return false;
                }
            }

            try {
                WorkspaceUtil.setAutobuilding(false);
            } catch (CoreException e1) {
                // Log an error for this, but continue
                CorePlugin.logError(
                        "Could not disable Auto Building", e1);
            }

            String modelPath;
            if (singleFile.compareToIgnoreCase("true") == 0) {
                project = ProjectUtilities.createProject(modelName);
                SystemModel_c systemModel = ProjectUtilities.getSystemModel(project);
                project.open(new NullProgressMonitor());
                project.refreshLocal(IResource.DEPTH_INFINITE, null);

                if (enableIPR.compareToIgnoreCase("true") == 0) {
                    IScopeContext projectScope = new ProjectScope(project);
                    Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
                    projectNode.putBoolean("com.mentor.nucleus.bp.ui.project.references", true);
                }

                modelPath = resolvePath(IGettingStartedConstants.modelFolder
                        + "/" + modelName + "." + Ooaofooa.MODELS_EXT); //$NON-NLS-1$ //$NON-NLS-2$
                if (!modelPath.isEmpty()) {
                    setupSucceeded = ProjectUtilities.importModelUsingWizard(systemModel, modelPath, false);
                }
            } else {
                if (singleFile.compareToIgnoreCase("false") != 0 && enableImportIntoWorkspace.equalsIgnoreCase("true")) {
                    modelName += "." + singleFile;
                }
                if (enableImportIntoWorkspace.equalsIgnoreCase("true")) {
                    modelPath = resolvePath(IGettingStartedConstants.modelFolder
                        + "/" + modelName); //$NON-NLS-1$
                } else {
                    modelPath = singleFile;
                }

                if (!modelPath.isEmpty()) {
                    setupSucceeded = ProjectUtilities.importExistingProject(modelPath, enableImportIntoWorkspace.equalsIgnoreCase("true"));
                }
               }

        } catch (CoreException e) {
            setupSucceeded = false;
        } catch (OperationCanceledException oce) {
            // The user canceled the operation
            return false;
        }

        if (!setupSucceeded) {
            IStatus status = new Status(IStatus.ERROR, SampleProjectGettingStartedAction.class
                    .getPackage().getName(), IStatus.ERROR,
                    "Failed to create the sample project: " + modelName, null);
            CoreException ce = new CoreException(status);
            CorePlugin.logError(
                    "Could not create requested project " + modelName, ce);
        }

        return setupSucceeded;
    }

    // used for multiple project models in zip archives
    private boolean setupProjects(String archiveName, String[] modelNames, String singleFile, String enableIPR, String enableImportIntoWorkspace) {
        boolean setupSucceeded = false;
        try {
            List<IProject> existingProjects = new ArrayList<>();
    	    for ( String modelName : modelNames ) {
                IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(modelName);
                if (project.exists()) {
                	existingProjects.add( project );
                }
    	    }
    	    if ( existingProjects.size() > 0 ) {
                boolean userOKsNotice = false;
                String formattedProjects = "";
                String sep = "";
                for ( IProject project : existingProjects ) { formattedProjects += sep + project.getName(); sep = ", "; }
                String msg = "Your workspace already contains the following projects: " + formattedProjects
                     + ".\n\nDo you wish to delete these projects and create new ones?";
                userOKsNotice = UIUtil.displayYesNoQuestion(msg);

                if (userOKsNotice) {
                    for ( IProject project : existingProjects ) {
                        try {
                            ProjectUtilities.deleteProject(project);
                        } catch (CoreException e) {
                            CorePlugin.logError("Could not delete existing "
                                + project.getName() + " project", e);
                            return false;
                        }
                    }
                } else {
                    return false;
                }
    	    }

            try {
                WorkspaceUtil.setAutobuilding(false);
            } catch (CoreException e1) {
                // Log an error for this, but continue
                CorePlugin.logError(
                        "Could not disable Auto Building", e1);
            }

            String modelPath = "";
            if (enableImportIntoWorkspace.equalsIgnoreCase("true")) {
                modelPath = resolvePath(IGettingStartedConstants.modelFolder + "/" + archiveName + "." + singleFile);
            }

            if (!modelPath.isEmpty()) {
                setupSucceeded = ProjectUtilities.importExistingProject(modelPath, enableImportIntoWorkspace.equalsIgnoreCase("true"));
            }

        } catch (OperationCanceledException oce) {
            // The user canceled the operation
            return false;
        }

        if (!setupSucceeded) {
            IStatus status = new Status(IStatus.ERROR, SampleProjectGettingStartedAction.class
                    .getPackage().getName(), IStatus.ERROR,
                    "Failed to create the sample projects: " + Arrays.toString(modelNames), null);
            CoreException ce = new CoreException(status);
            CorePlugin.logError(
                    "Could not create requested projects " + Arrays.toString(modelNames), ce);
        }

        return setupSucceeded;
    }

    private String resolvePath(String path) {
        String pathString = WelcomePlugin.getPluginPathAbsolute() + path;
        File myFile = new File(pathString);
        if (!myFile.exists()) {
            CorePlugin.logError("Could not locate \"" + path + "\" in " + WelcomePlugin.getDefault().getClass().getName(), null);
            return "";
        }
        return pathString;
    }

    private void closeWelcomePage() {
        // close welcome page
        // Check to see if the xtUML perspective contains an intro part
        // This prevents a NPE in the case where no welcome page exists
        boolean foundIntroPart = false;
        IViewReference views[] = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
        for(int i = 0; i < views.length; i ++) {
            if(views[i].getPartName().equals("Welcome")) { //$NON-NLS-1$
                foundIntroPart = true;
            }
        }
        if(foundIntroPart == true) {
            IIntroPart introPart = PlatformUI.getWorkbench().getIntroManager().getIntro();
            if(introPart != null) {
                PlatformUI.getWorkbench().getIntroManager().closeIntro(introPart);
            }
            while(Display.getCurrent().readAndDispatch());
        }
    }

    /**
     * Open the file named README if it exists for the given project name.
     * If 'readmePath' contains a value, attempt to open the file that it points
     * to.
     *
     * @param modelFolderName
     * @param readmePath
     */
    private void openReadme(String modelFolderName, String readmePath) {
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
                modelFolderName);
        IFile fileToBeOpened;
        if ( null != readmePath && !"".equals(readmePath) ) {
            fileToBeOpened = ResourcesPlugin.getWorkspace().getRoot().getFile( new Path( readmePath ) );
        }
        else {
            fileToBeOpened = project.getFile("README");
        }
        if (!fileToBeOpened.exists()) {
            // A readme does not have to exist.  We just display it if it does.
            return;
        }
        IEditorInput editorInput = new FileEditorInput(fileToBeOpened);
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        try {
            page.openEditor(editorInput, org.eclipse.ui.editors.text.EditorsUI.DEFAULT_TEXT_EDITOR_ID); //$NON-NLS-1$
        } catch (PartInitException pie) {
            CorePlugin.logError("Failed to open " + modelFolderName + " README.", pie);
        }
    }

    /**
     * Mark each file in exeString as executable. exeString is a comma
     * separated list of files. If exeString is null or empty, or the file does
     * not exist in the workspace, silently do nothing.
     *
     * @param exeString
     */
    private void setExePermissions( String exeString ) {
        if ( null != exeString ) {
            String[] exes = exeString.split( "," );
            for ( String exe : exes ) {
                IFile exeFile = ResourcesPlugin.getWorkspace().getRoot().getFile( new Path( exe ) );
                if ( exeFile.exists() ) {
                    ResourceAttributes attributes = exeFile.getResourceAttributes();
                    attributes.setExecutable( true );
                    try {
                        exeFile.setResourceAttributes( attributes );
                    } catch (CoreException e) {
                        CorePlugin.logError( "Error setting file executable.", e);
                    }
                }
            }
        }
    }

    /**
     * Check for workspace specific settings. Open a dialog asking the user if he would
     * like these settings to be set automatically. Also warn that mixing dialects is not
     * recommended.
     *
     * @param dialect
     */
    private void checkWorkspaceSettings( String dialect ) {
        if ( null != dialect ) {
            BridgePointPreferencesModel model = new BridgePointPreferencesModel();
            model.getStore().loadModel( CorePlugin.getDefault().getPreferenceStore(), null, model );
            final String title = "Set default " + dialect + " preferences";
            final String message = "Users working with " + dialect + " dialect models are required to set the following workspace preferences. " +
                                   "Select OK to set these preferences now.\n\n" +
                                   "Note: it is not recommended to edit models with different action language dialects in the same workspace.";
            if ( "OAL".equals( dialect ) ) {
                // Check:
                // - default dialect == OAL
                // - concrete polys == disabled
                if ( model.defaultActionLanguageDialect != Actiondialect_c.oal ||
                     model.allowConcretePolys == true ) {
                    boolean setPreferences = UIUtil.openScrollableTextDialog( PlatformUI.getWorkbench().getDisplay().getActiveShell(), true,
                        title,
                        "Default action language dialect: OAL\n" +
                        "Allow concrete polymorphic events: false",
                        message,
                        null, null, true, false);
                    if ( setPreferences ) {
                        model.defaultActionLanguageDialect = Actiondialect_c.oal;
                        model.allowConcretePolys = false;
                    }
                }
            }
            else if ( "MASL".equals( dialect ) ) {
                // Check:
                // - default dialect == MASL
                // - concrete polys == enabled
                // - restricted identifiers == enabled
                if ( model.defaultActionLanguageDialect != Actiondialect_c.masl ||
                     model.allowConcretePolys == false ||
                     model.requireMaslStyleIdentifiers == false ) {
                    boolean setPreferences = UIUtil.openScrollableTextDialog( PlatformUI.getWorkbench().getDisplay().getActiveShell(), true,
                        title,
                        "Default action language dialect: MASL\n" +
                        "Enable restricted identifier naming for model elements: true\n" +
                        "Allow concrete polymorphic events: true",
                        message,
                        null, null, true, false);
                    if ( setPreferences ) {
                        model.defaultActionLanguageDialect = Actiondialect_c.masl;
                        model.requireMaslStyleIdentifiers = true;
                        model.allowConcretePolys = true;
                    }
                }
            }
            else { /* do nothing */ }
            model.getStore().saveModel( CorePlugin.getDefault().getPreferenceStore(), model );
        }
    }

}
