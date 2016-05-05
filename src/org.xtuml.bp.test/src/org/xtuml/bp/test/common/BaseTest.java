//=====================================================================
//
//File:      $RCSfile: BaseTest.java,v $
//Version:   $Revision: 1.51 $
//Modified:  $Date: 2013/05/10 05:37:52 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

package org.xtuml.bp.test.common;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.team.internal.ccvs.ui.CVSLightweightDecorator;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.internal.decorators.DecoratorManager;
import org.eclipse.ui.internal.views.log.AbstractEntry;
import org.eclipse.ui.internal.views.log.LogEntry;
import org.eclipse.ui.internal.views.log.LogView;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.junit.After;
import org.junit.Before;
import org.osgi.framework.Constants;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Elementtypeconstants_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.GlobalElementInSystem_c;
import org.xtuml.bp.core.ImportedClass_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.LeafSymbolicConstant_c;
import org.xtuml.bp.core.LiteralSymbolicConstant_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.ReferentialAttribute_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StructureMember_c;
import org.xtuml.bp.core.SymbolicConstant_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.Visibility_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ComponentResourceListener;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.ui.perspective.BridgePointPerspective;
import org.xtuml.bp.io.mdl.ImportModel;
import org.xtuml.bp.test.GlobalsTestEnabler;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.explorer.ExplorerView;
import org.xtuml.bp.ui.explorer.decorators.SynchronizationDecorator;
import org.xtuml.bp.ui.text.placeholder.PlaceHolderManager;
import org.xtuml.bp.utilities.ui.ProjectUtilities;

import junit.framework.TestCase;


public class BaseTest extends TestCase {
	
	/**
	 *  stores the current model-root
	 *  for each extending test case
	 */
	protected static Ooaofooa modelRoot;
	
	/**
	 * store the current graphics
	 * model-root for each extending
	 * test case
	 */
	protected static Ooaofgraphics graphicsModelRoot;
	
	/**
	 * stores the workspace path
	 * for each extending test case
	 */
	protected static String m_workspace_path;
	
	/**
	 * stores the path to the
	 * current test's logfile
	 */
	protected static String m_logfile_path;
	
	/**
	 * stores the WorkbenchPage of
	 * the xtUML Modeling Perspective
	 */
	protected static IWorkbenchPage m_wp; 
	
	/**
	 * stores a handle to the
	 * Model Explorer View
	 */
	protected static ExplorerView m_bp_view;
	
	/**
	 * stores a handle to the
	 * Model Explorer Tree Viewer
	 */
	protected static TreeViewer m_bp_tree;
	
	/**
	 * stores the current SystemModel_c
	 * instance under test
	 */
	protected static SystemModel_c m_sys;
	
	private static Map sharedResults = new Hashtable(); 
	
	protected IProject project;
	
	IWorkbenchPage workbenchPage = null;

	private static List<Class<?>> inializedTestCases = new ArrayList<Class<?>>();
	
	protected static String defaultProjectName = "Default Project";
	
	private static String defaultDomainName = "Default Model";
	
	public static final String DEFAULT_TEST_MODELSPACE = "/" + defaultProjectName + "/" + Ooaofooa.MODELS_DIRNAME
	+ "/" + defaultProjectName + "/" + defaultDomainName  + "." + Ooaofooa.MODELS_EXT;
	
	public static boolean testGlobals = false;
	protected static boolean delayGlobalUpgrade = false;

	
	public BaseTest(){
		this(null, "");
	}
	public BaseTest(String projectName, String name) {
		//super(name);
		// disable synchronization decoration, costs too
		// much test time
		try {
			PlatformUI.getWorkbench().getDecoratorManager().setEnabled(SynchronizationDecorator.ID, false);
		} catch (CoreException e) {
			fail("Unable to disable synchronization decorator.");
		}
		setupProject(projectName);
	}
	
	/**
	 * Clients should override this method to
	 * perform a one time setup step, this will
	 * be called once for all tests under the test
	 * case.
	 * @throws Exception 
	 */
	protected void initialSetup() throws Exception {
		// let clients override
	}
	
	protected void setupProject(String projectName){
		// during project setup
		// disable the resource change
		// listener
		ComponentResourceListener.setIgnoreResourceChangesMarker(true);
		if(projectName == null) {
			projectName = defaultProjectName;
		}
		if (projectName != null) {
			if(!projectExists(projectName)) {
				IdAssigner.setSeedOfAllInstances(projectName.hashCode());
				try {
					project = TestingUtilities.createProject(projectName);
				} catch (CoreException e) {
					fail(e.getMessage());
				}
				// get the SystemModel_c instance related to the
				// newly created project
				m_sys = getSystemModel(projectName);
				IdAssigner.setSeedOfAllInstances(0);
			} else {
				project = getProjectHandle(projectName);
			}
		}
		ComponentResourceListener.setIgnoreResourceChangesMarker(false);
		
		// Change default for the parse on resource change prefernece to "never"
		// so that dialog will not appear during tests.
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.PARSE_ALL_ON_RESOURCE_CHANGE, "never"); //$NON-NLS-1$
        
        // Never show the  "editing derived input" confirmation dialog during unit tests.
        String lineNumbers = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_WARN_IF_INPUT_DERIVED;
        EditorsUI.getPreferenceStore().setValue(lineNumbers, false);
        
        // Unit tests expect parse errors to show during edit
		store.setValue(BridgePointPreferencesStore.ENABLE_PARSE_ON_ACTIVITY_EDITS, true);
	}
	public void setUp() throws Exception {
		super.setUp();
		
		Ooaofooa.setInUnitTest(true);
		/*
		 *	If the workspace path has not been
		 *  stored at this point, get and set
		 *  the value from the System Property 
		 */
		if (m_workspace_path == null || m_workspace_path.equals(""))
		{
			m_workspace_path = System.getProperty("WORKSPACE_PATH"); //$NON-NLS-1$
		}
		if (m_logfile_path == null || m_logfile_path.equals(""))
		{
			m_logfile_path = System.getProperty("LOGFILE_PATH"); //$NON-NLS-1$
		}
		assertNotNull( m_workspace_path );
		assertNotNull( m_logfile_path );
		/*
		 * open the xtUML Modeling perspective
		 * if it is not currently open. 
		 */
		if(m_wp == null)
		{
			/*
			 *  set the associated parts to
			 *  allow easy access for tests
			 */
			m_wp = TestUtil.showBridgePointPerspective();
			m_bp_view = (ExplorerView) m_wp.findView(BridgePointPerspective.ID_MGC_BP_EXPLORER);
			m_bp_tree = m_bp_view.getTreeViewer();
			//initliaze ExplorerUtil variables
			ExplorerUtil.setView(m_bp_view);
		}
		
		// call client initial setup, if this is the
		// first setUp call
		Class<?> clazz = getClass();
		if (getClass().getSuperclass() != BaseTest.class
				&& getClass().getSuperclass() != GlobalsTestEnabler.class
				&& !getClass().getSuperclass().getSimpleName()
						.equals("CanvasTest")) {
			clazz = getClass().getSuperclass();
		}
		if(!inializedTestCases.contains(clazz)) {
			initialSetup();
			inializedTestCases.add(clazz);
		}
		
		// If the log already exists then it was left over from a previous
		// test.  When this happens we don't want cascading errors so backup 
		// the old log to a unique name (.log-1, .log-2, etc...)
		File currentLog = Platform.getLogFileLocation().toFile();
		if ( currentLog.exists() ) {
			int logNum = 1;
			String newName = currentLog.getCanonicalPath() + "-" + Integer.toString(logNum);
			while ( currentLog.renameTo(new File(newName)) == false ) {
				logNum++;
				newName = currentLog.getCanonicalPath() + "-" + Integer.toString(logNum);
			}
		}
		assertTrue("Saving threads left hanging", Ooaofooa.threadsSaving < 1);
	}
	
	@After
	public void tearDown() throws Exception {
		BaseTest.staticTearDown();
	}
	

	public static void staticTearDown() throws Exception {
		// clear the UI events
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() {
				// nothing to do, just making sure the UI
				// is clear of events
			}
		});
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		
		String result = getLogViewResult("");
		if(!result.equals("")) {
			fail(result);
		}		
	} 
	
	public static String getLogViewResult(String prepend) {
		// verify that the log file is empty
		// if not fail the current test
		// in a few cases with eclipse 4.x we must
		// close the workbench, example being TigerNatureWorkspaceSetup
		// in that case the log view is not available so skip
		// checking for it if we get a null pointer due to
		// the workbench being closed
		LogView logView = null;
		try {
			logView = (LogView) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().findView(
						"org.eclipse.pde.runtime.LogView");
		if(logView == null) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.showView("org.eclipse.pde.runtime.LogView");
				logView = (LogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActivePart();
			} catch (PartInitException e) {
				return e.getMessage();
			}
		}
		} catch (NullPointerException npe) {
			// no need to log this or fail the test
		}
		String msg = "";
		File in_fh = Platform.getLogFileLocation().toFile();
		if(logView != null) {
			// if the log file does not exist, it was deleted by a test
			// we need to ignore here
			if(in_fh.exists()) {
				AbstractEntry[] elements = logView.getElements();
				for(int i = 0; (i < elements.length) && msg.isEmpty(); i++) {
					LogEntry entry = (LogEntry) elements[i];
					
					// Allow messages that are informational.  They are not errors
					if(entry.getSeverity() == IStatus.OK || entry.getSeverity() == IStatus.INFO) {
						continue;
					}
					
					// Allow errors that are caused by what appears to be an antlr decorator bug.
					// This decorator is being triggered as a result of a refresh, as it should, but
					// antlr should check to see if it really needs to access the file, it should NOT.  The
					// problem is that the file does not yet exist in some cases because the BP persistence has not
					// happened yet.  Another plugin doing this is org.eclipse.cdt.core.
					String stack = entry.getStack();
					String pluginID = entry.getPluginId();		
					if ((pluginID.equals("org.antlr.eclipse.ui") || pluginID
							.equals("org.eclipse.cdt.core"))
							&& stack.contains("ResourceException")
							&& stack.contains(".xtuml")) {
						continue;
					}
					
					// ignore all warnings, we only care about errors
					if (entry.getSeverity() == IStatus.WARNING) {
					    continue;
					}
					
					msg = prepend + ".log file is not empty";
				}
			}			
		} else {
			if ( in_fh.exists() )
			{
				msg = prepend + ".log file is not empty";
			}
		}
		// Since this function really looks at the log view and not just inspects the
		// current .log, here we clear the error log view so we don't pollute downstream 
		// tests with errors from earlier tests.
		clearErrorLogView();

		return msg;
	}

	public static void clearErrorLogView() {
		// in a few cases with eclipse 4.x we must
		// close the workbench, example being TigerNatureWorkspaceSetup
		// in that case the log view is not available so skip
		// checking for it if we get a null pointer due to
		// the workbench being closed
		LogView logView = null;
		try {
			logView = (LogView) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().findView("org.eclipse.pde.runtime.LogView");
		if(logView == null) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.showView("org.eclipse.pde.runtime.LogView");
				logView = (LogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActivePart();
			} catch (PartInitException e) {
				System.err.println(e.getMessage());
			}
		}
		} catch (NullPointerException npe) {
			// no need to log this or fail the test
		}
		if (logView != null) {
			IContributionItem[] items = logView.getViewSite().getActionBars().getToolBarManager().getItems();
			for(int i = 0; i < items.length; i++) {
			  if(items[i] instanceof ActionContributionItem) {
				  if ( ((ActionContributionItem) items[i]).getAction().getText().equals("C&lear Log Viewer")) {
					  ((ActionContributionItem) items[i]).getAction().run();
				  }
			  }
			}
		}		
	}
	
	public void deleteErrorLogAndLogViewEntries() {
        File in_fh = Platform.getLogFileLocation().toFile();
        assertTrue("Log file doesn't exist", in_fh.exists());
        in_fh.delete();
        CorePlugin cp = CorePlugin.getDefault();
        ILog log = cp.getLog();
        Status status =
            new Status(
                IStatus.INFO,
                (String)cp.getBundle().getHeaders().get(Constants.BUNDLE_NAME),
                IStatus.INFO,
                "Refreshing the log after a delete.",
                null);        
		log.log(status);	
		
		clearErrorLogView();
	}
	 
	public void loadProject(String projectName) throws CoreException {
    	TestUtil.showBridgePointPerspective();
        
    	ProjectUtilities.allowJobCompletion();
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		if (project.exists()) {
			TestingUtilities.deleteProject(projectName);
			dispatchEvents(0);
		}
		if (!project.exists()) {
			TestingUtilities.importTestingProjectIntoWorkspace(projectName);
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					projectName);
			TestingUtilities.allowJobCompletion();
			m_sys = getSystemModel(projectName);
		}
		String modelRootId = Ooaofooa.createModelRootId(project, projectName, true);
		modelRoot = Ooaofooa.getInstance(modelRootId, true);
		graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
	}
	
	/**
	 * Get's a project with the given name from the
	 * workspace root
	 * 
	 * @param projectName
	 * @return IProject
	 */
	protected IProject getProjectHandle(String projectName) {
		IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return newProject;
	}
	
	/**
	 * Check's if a project exists already
	 * 
	 * @param projectName
	 * @return boolean
	 */
	protected boolean projectExists(String projectName) {
		IProject newProject = getProjectHandle(projectName);
		if(newProject.exists()) {
			return true;
		}
		return false;
	}
	/**
	 * Return's SystemModel_c instance related to
	 * the given project
	 * 
	 * @param projectName
	 * @return SystemModel_c
	 */
	protected SystemModel_c getSystemModel(final String projectName) {
		SystemModel_c sys = getSystemModelInternal(projectName);
		// verify that we actually have a non-null instance
		assertNotNull(sys);
		return sys;
	}	
	/**
	 * Return's SystemModel_c instance related to
	 * the current project
	 * 
	 * @return SystemModel_c
	 */
	protected SystemModel_c getSystemModel() {
		// Query used to find the SystemModel associated with the
		// given project name
		assertNotNull(project);
		SystemModel_c sys = getSystemModelInternal(project.getName());
		if (sys == null) {
			// Fall back to default test project
			sys = getSystemModelInternal(defaultProjectName);
			}
		assertNotNull(sys);
		return sys;
	}	
	/**
	 * Return's SystemModel_c instance related to
	 * the given project
	 * 
	 * @param projectName
	 * @return SystemModel_c
	 */
	private SystemModel_c getSystemModelInternal(final String projectName) {
		// Query used to find the SystemModel associated with the
		// given project name
		ClassQueryInterface_c query = new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((SystemModel_c)candidate).getName().equals(projectName);
			}
		};
		// get the associated instance
		return SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), query);
	}	
	protected void putSharedResult(String key, Object result){
		sharedResults.put(key, result);
	}
	
	protected Object getSharedResult(String key){
		return sharedResults.get(key);
	}
	
	protected IProject getProject() {
		return project;
	}
	
	protected IPath getSourceDirectory(){
		return TestingUtilities.getSourceDirectory();
	}
	
	protected File createFullPathForFile(String name) throws FileNotFoundException {
		File file = new File(TestingUtilities.getSourceDirectory().toString(), name);
		if (!file.exists() || !file.isFile()) {
			throw new FileNotFoundException(file.toString());
		}
		return file;
	}
	
	protected IFolder importFolder(File folder, String dest) throws CoreException, IOException {
		return importFolder(folder, dest, false);
	}
	
	protected IFolder importFolder(File folder, String dest, boolean removeFirst) throws CoreException, IOException {
		return TestingUtilities.importFolder(project, folder, dest, removeFirst);
	}
	
	protected IFile importFile(File file) throws CoreException, IOException {
		return TestingUtilities.importFile(project, file);
	}
	
	protected IFile importModelFile(File file) throws CoreException, IOException {
		return TestingUtilities.importFile(project.getFolder(Ooaofooa.MODELS_DIRNAME), file);
	}
	
	protected IFile importFile(String fileNamePath) throws CoreException, IOException {
		return TestingUtilities.importFile(project, createFullPathForFile(fileNamePath));
	}
	
	protected PersistableModelComponent ensureAvailableAndLoaded(String domainName, boolean loadDomainOnly) throws CoreException{
		return ensureAvailableAndLoaded(domainName, loadDomainOnly, false);
	}    
	
	protected PersistableModelComponent ensureAvailableAndLoaded(String domainName, boolean loadDomainOnly, boolean reCopy) throws CoreException{
		return ensureAvailableAndLoaded(getSourceDirectory().lastSegment(), domainName, loadDomainOnly, reCopy);
	}
	
	protected PersistableModelComponent ensureAvailableAndLoaded(String systemName, String domainName, boolean loadDomainOnly, boolean reCopy) throws CoreException{
		return ensureAvailableAndLoaded(systemName, domainName, getProject(), loadDomainOnly, reCopy, "Domain");
	}
	
	protected PersistableModelComponent ensureAvailableAndLoaded(String systemName, String componentName, boolean loadComponentOnly, boolean reCopy, String componentType) throws CoreException{
		return ensureAvailableAndLoaded(systemName, componentName, getProject(), loadComponentOnly, reCopy, componentType);
	}
	protected PersistableModelComponent ensureAvailableAndLoaded(String systemName, String componentName, IProject targetProject, boolean loadComponentOnly, boolean reCopy) throws CoreException{
		return ensureAvailableAndLoaded(systemName, componentName, targetProject, loadComponentOnly, reCopy, "Domain");
	}
	protected PersistableModelComponent ensureAvailableAndLoaded(String systemName, String componentName, IProject targetProject, boolean loadComponentOnly, boolean reCopy, String componentType) throws CoreException{
		PersistableModelComponent rootComponent = PersistenceManager.getRootComponent(targetProject);
		if(rootComponent == null){
			throw new IllegalStateException("Root component not present");
		}
		
		if(!rootComponent.isLoaded()){
			rootComponent.load(new NullProgressMonitor());
		}
		
		IPath domPath = rootComponent.getContainingDirectoryPath().append(componentName+"/"+componentName+"."+Ooaofooa.MODELS_EXT);
		PersistableModelComponent component=PersistenceManager.findOrCreateComponent(domPath);
		File componentFolder = null;
		if(reCopy || component == null){
			if ( component != null && component.getRootModelElement()!=null )
			{
				ModelRoot mr = component.getRootModelElement().getModelRoot();
				ModelRoot.disableChangeNotification();
				try {
					unloadComponentAndChildren(component);
					component.loadComponentAndChildren(new NullProgressMonitor());
					component.deleteSelfAndChildren();
				}
				finally {
					ModelRoot.enableChangeNotification();
				}
				
				// TODO: figure out why these are needed
				((Ooaofooa)mr).clearDatabase(new NullProgressMonitor());
				Ooaofgraphics.getInstance(mr.getId()).clearDatabase(new NullProgressMonitor());
			}
			IPath sourceProjectPath = getSourceDirectory().removeLastSegments(1);
			IPath projectPath = sourceProjectPath.append("/" + systemName);
			
			componentFolder = new File(sourceProjectPath.toFile(), systemName + "/" + Ooaofooa.MODELS_DIRNAME
					+ "/" + systemName + "/" + componentName);
			
			if(!componentFolder.exists()) {
				componentFolder = findComponentFolder(projectPath.toFile(), systemName, componentName);
				if(componentFolder == null) {
					fail("Unable to locate given model: " + componentName);
				}
			}
			
			IFolder targetFolder = ResourcesPlugin.getWorkspace().getRoot()
			.getFolder(rootComponent.getContainingDirectoryPath().append(componentName)); 
			
			// turn off the resource listener, because it gets notified
			// of resource changes as each file is loaded, which causes 
			// problems when there is a proxy refering to a file that hasn't
			// been copied yet
			ComponentResourceListener.setIgnoreResourceChangesMarker(true);
			TestingUtilities.copyFolder(componentFolder, targetFolder, reCopy);
			IPath componentPath = rootComponent.getContainingDirectoryPath().append(componentName + "/" + componentName + "." + Ooaofooa.MODELS_EXT);
			component =  PersistableModelComponent.findOrCreateInstance(componentPath);
			PersistenceManager.traverseResourceContainer((IFolder) component.getFile().getParent());
			ComponentResourceListener.setIgnoreResourceChangesMarker(false);
			
		}
		
		ComponentResourceListener.setIgnoreResourceChangesMarker(true);
		removeSystemProxyEntries(component);	
		ComponentResourceListener.setIgnoreResourceChangesMarker(false);

		if(!component.isLoaded() || component.isOrphaned()){
			if(loadComponentOnly){
				component.load(new NullProgressMonitor());
			}else{
				component.loadComponentAndChildren(new NullProgressMonitor());
			}
		}
		
		// set the model-roots interesting to the current test
		modelRoot = (Ooaofooa)component.getRootModelElement().getModelRoot();
		graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
		SystemModel_c m_parent=(SystemModel_c) rootComponent.getRootModelElement();
		NonRootModelElement rootElement = component.getRootModelElement();
		if(rootElement instanceof Package_c) {
			// note, currently only one level of package is supported
			// and none of the direct associations with systems are set up
			// like the above for component packages
			Package_c pack = (Package_c) component.getRootModelElement();
			PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(pack);
			if(pe == null) {
				pe = new PackageableElement_c(pack.getModelRoot(), pack.getPackage_id(),
						Visibility_c.Public, Gd_c.Null_unique_id(),
						Gd_c.Null_unique_id(),
						Elementtypeconstants_c.PACKAGE);
				pe.relateAcrossR8001To(pack);
			}
			SystemModel_c oldSys = SystemModel_c.getOneS_SYSOnR1401(pack);
			pack.unrelateAcrossR1401From(oldSys);
			pack.relateAcrossR1401To((SystemModel_c) rootComponent.getRootModelElement());
			pack.relateAcrossR1405To((SystemModel_c) rootComponent.getRootModelElement());
			Ooaofooa root = (Ooaofooa)pack.getModelRoot();
			if (root.getRoot() != (SystemModel_c) rootComponent.getRootModelElement()) {
				root.setRoot((SystemModel_c) rootComponent.getRootModelElement());
			}
			associatePackagesWithSystem(pack, (SystemModel_c) rootComponent.getRootModelElement());
		}
		
		// persist the above parent changes, otherwise a reload
		// can lose required information (reverting back to the
		// copied from parent)
		component.persist();
		// clear this history from the file
		component.getFile().clearHistory(null);
		
		if (testGlobals && !delayGlobalUpgrade) {
		  // upgrade the loaded component
		  resolveGlobalDTsFor(component, loadComponentOnly);
		}
		
		return component;
	}
	
	private void associatePackagesWithSystem(NonRootModelElement element,
			SystemModel_c sys) {
		if (element instanceof Package_c) {
			Package_c pack = (Package_c) element;
			Package_c[] pkgs = Package_c
					.getManyEP_PKGsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(pack));
			for (Package_c pkg : pkgs) {
				pkg.relateAcrossR1405To(sys);
				associatePackagesWithSystem(pkg, sys);
			}
			Component_c[] comps = Component_c
					.getManyC_CsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(pack));
			for (Component_c comp : comps) {
				associatePackagesWithSystem(comp, sys);
			}
		} else if (element instanceof Component_c) {
			Package_c[] pkgs = Package_c
					.getManyEP_PKGsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8003((Component_c) element));
			for (Package_c pkg : pkgs) {
				pkg.relateAcrossR1405To(sys);
				associatePackagesWithSystem(pkg, sys);
			}
		}
	}
	
	private String locateElementNameById(UUID dtId, File componentFolder) {
		String fileContents = "";
		File rootFolder = new File(componentFolder.getAbsolutePath() + "/../");
		File[] files = rootFolder.listFiles();
		for(int i = 0; i < files.length; i++) {
			if(files[i].getName().equals("Datatypes")) {
				File[] dtPkgFiles = files[i].listFiles();
				for(int j = 0; j < dtPkgFiles.length; j++) {
					if(dtPkgFiles[j].isFile()) {
						byte[] bytes = new byte[(int) dtPkgFiles[j].length()];
						try {
							FileInputStream fis = new FileInputStream(dtPkgFiles[j]);
							fis.read(bytes);
							fileContents = new String(bytes);
						} catch (FileNotFoundException e) {
							fail("Unable to read CDT file.");
						} catch (IOException e) {
							fail("Unable to read CDT file.");
						}
					}
				}
			}
		}
		if(fileContents.equals("")) {
			return fileContents;
		} else {
			String result = "";
			String[] split = fileContents.split("\n");
			for(int i = 0; i < split.length; i++) {
				if(split[i].trim().startsWith("VALUES (\"" + dtId.toString())) {
					result = split[i + 2].split("'")[1];
					break;
				}
			}
			return result;
		}
	}
	
	private void relateDTToElement(NonRootModelElement element, DataType_c dt) {
		if(element instanceof Attribute_c) {
			((Attribute_c) element).relateAcrossR114To(dt);
		}
		if(element instanceof Bridge_c) {
			((Bridge_c) element).relateAcrossR20To(dt);
		}
		if(element instanceof BridgeParameter_c) {
			((BridgeParameter_c) element).relateAcrossR22To(dt);
		}
		if(element instanceof Operation_c) {
			((Operation_c) element).relateAcrossR116To(dt);
		}
		if(element instanceof OperationParameter_c) {
			((OperationParameter_c) element).relateAcrossR118To(dt);
		}
		if(element instanceof Function_c) {
			((Function_c) element).relateAcrossR25To(dt);
		}
		if(element instanceof FunctionParameter_c) {
			((FunctionParameter_c) element).relateAcrossR26To(dt);
		}
		if(element instanceof InterfaceOperation_c) {
			((InterfaceOperation_c) element).relateAcrossR4008To(dt);
		}
		if(element instanceof PropertyParameter_c) {
			((PropertyParameter_c) element).relateAcrossR4007To(dt);
		}
		if(element instanceof StateMachineEventDataItem_c) {
			((StateMachineEventDataItem_c) element).relateAcrossR524To(dt);
		}
		if(element instanceof LiteralSymbolicConstant_c) {
			SymbolicConstant_c sc = SymbolicConstant_c
					.getOneCNST_SYCOnR1502(LeafSymbolicConstant_c
							.getOneCNST_LFSCOnR1503((LiteralSymbolicConstant_c) element));
			sc.relateAcrossR1500To(dt);
		}
		if(element instanceof StructureMember_c) {
			((StructureMember_c) element).relateAcrossR45To(dt);
		}
	}
	
	private void removeSystemProxyEntries(PersistableModelComponent component) {
		if(component.getFile().exists()) {
			try {
				// some of our test models are in \r\n newline format while
				// others are simply \n, try both
				final String NEWLINE1 = "\n\t.*";
				final String NEWLINE2 = "\r\n\t.*";
				
				byte[] bytes = new byte[(int) component.getFile().getLocation()
						.toFile().length()];
				InputStream fileStream = component.getFile().getContents();
				fileStream.read(bytes);
				String fileString = new String(bytes);
				// S_SYS_PROXY with 4 lines
				fileString = fileString.replaceAll("INSERT INTO S_SYS_PROXY"
						+ NEWLINE1 + NEWLINE1 + NEWLINE1 + NEWLINE1 + "\\);", "");
				fileString = fileString.replaceAll("INSERT INTO S_SYS_PROXY"
						+ NEWLINE2 + NEWLINE2 + NEWLINE2 + NEWLINE2 + "\\);", "");

				// S_SYS_PROXY with 3 lines
				fileString = fileString.replaceAll("INSERT INTO S_SYS_PROXY"
						+ NEWLINE1 + NEWLINE1 + NEWLINE1 + "\\);", "");
				fileString = fileString.replaceAll("INSERT INTO S_SYS_PROXY"
						+ NEWLINE2 + NEWLINE2 + NEWLINE2 + "\\);", "");
				
				// S_SYS_PROXY with 7 lines
				fileString = fileString.replaceAll("INSERT INTO S_SYS_PROXY"
						+ NEWLINE1 + NEWLINE1 + NEWLINE1 + NEWLINE1 + NEWLINE1 + NEWLINE1 + NEWLINE1 + "\\);", "");
				fileString = fileString.replaceAll("INSERT INTO S_SYS_PROXY"
						+ NEWLINE2 + NEWLINE2 + NEWLINE2 + NEWLINE2 + NEWLINE2 + NEWLINE2 + NEWLINE2 + "\\);", "");

				fileStream.close();
				component.getFile().setContents(
						new ByteArrayInputStream(fileString.getBytes()),
						IFile.KEEP_HISTORY, new NullProgressMonitor());
			} catch (CoreException e) {
				fail("Unable to get file contents.");
			} catch (IOException e) {
				fail("Unable to get file contents.");
			}
		}
		Collection<?> children = component.getChildren();
		for(Object child : children) {
			PersistableModelComponent childComp = (PersistableModelComponent) child;
			if (childComp.getComponentType().equals("ComponentPackage")
					|| childComp.getComponentType().equals("InterfacePackage")
					|| childComp.getComponentType().equals("Package")
					|| childComp.getComponentType().equals("DataTypePackage")
					|| childComp.getComponentType().equals("Domain")
					|| childComp.getComponentType().equals("Component")) {
				removeSystemProxyEntries(childComp);
			}
		}
	}
	private void resolveGlobalDTsFor(PersistableModelComponent pmc, boolean componentOnlyLoaded) {
	  if (pmc.getRootModelElement() != null) {
		Ooaofooa modelRoot = (Ooaofooa)pmc.getRootModelElement().getModelRoot();
		DataType_c[] instances = DataType_c.DataTypeInstances(modelRoot);
		DataType_c[] globalDts = DataType_c.getManyS_DTsOnR8001(
				           PackageableElement_c.getManyPE_PEsOnR9100(
						  GlobalElementInSystem_c.getManyG_EISsOnR9100(getSystemModel())));

		if (!componentOnlyLoaded) {
		  Collection<?> children = pmc.getChildren();
		  Iterator<?> childIt = children.iterator();
		  while(childIt.hasNext()) {
			resolveGlobalDTsFor((PersistableModelComponent)childIt.next(), false);
		  }
		}
	  }
	}
	protected void unloadComponentAndChildren(PersistableModelComponent component) {
		component.setRootModelElement(null, false, false);
		Collection children = component.getChildren();
		Iterator iterator = children.iterator();
		while(iterator.hasNext()) {
			unloadComponentAndChildren((PersistableModelComponent)iterator.next());
		}
	}
	
	private File findComponentFolder(File parent, String systemName, String expectedFile) {
		File[] files = parent.listFiles();
		if(files == null) {
			return null;
		}
		for(int i = 0; i < files.length; i++) {
			if(files[i].getName().equals(expectedFile)) {
				return files[i];
			}
		}
		for(int i = 0; i < files.length; i++) {
			File result = findComponentFolder(files[i], systemName, expectedFile);
			if(result != null) {
				return result;
			}
		}    	
		return null;
	}
	protected IWorkbenchPage switchPerspective(String name) throws CoreException {
		workbenchPage =
			PlatformUI.getWorkbench().showPerspective(name, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		return workbenchPage;
	}
	
	protected IFile createFile(String fileNamePath, InputStream in) throws CoreException {
		Path filePath = new Path(fileNamePath);
		IFile file = project.getFile(filePath);
		file.create(in, true, new NullProgressMonitor());
		return file;
	}
	
	
	protected void importModel(IFile mdlFile, Ooaofooa modelRoot, boolean parseAllActivities, boolean clearDatabase, boolean parserGraphics) throws CoreException, IOException{
		importModel(new NullProgressMonitor(), mdlFile, modelRoot, parseAllActivities, clearDatabase, parserGraphics);
	}
	
	protected void importModel(IProgressMonitor progressMonitor, IFile mdlFile, Ooaofooa modelRoot, boolean parseAllActivities, boolean clearDatabase, boolean parserGraphics) throws CoreException, IOException{
		importModel(new NullProgressMonitor(), new SystemModel_c(modelRoot), mdlFile, modelRoot, parseAllActivities, clearDatabase, parserGraphics);
	}
	
	protected void importModel(IProgressMonitor progressMonitor, SystemModel_c sysModel, IFile mdlFile, Ooaofooa modelRoot, boolean parseAllActivities, boolean clearDatabase, boolean parserGraphics) throws CoreException, IOException{
		ImportModel impBP = new ImportModel(mdlFile.getContents(true), modelRoot, sysModel, parseAllActivities, clearDatabase, parserGraphics, false); //$NON-NLS-1$
		int i = impBP.countAndValidateInsertStatements();
		assertTrue(i > 0);
		impBP.run(progressMonitor);
		assertEquals("", impBP.m_errorMessage);   //$NON-NLS-1$
	}
	
	Throwable resException = null;
	class LogListener implements ILogListener{
		public void logging(IStatus status, String plugin) {
			resException = status.getException();
		}
	}
	protected void sendEventAndCheckLog(Control control, int eventType, Event event){
		resException = null;
		LogListener logListener = new LogListener();
		Platform.addLogListener(logListener);
		assertNotNull(control);
		control.notifyListeners(eventType, event);
		
		if (resException != null){
			fail(resException.toString());	    	
		}
		Platform.removeLogListener(logListener);
	}
	
	protected void setResourceToReadonly(PersistableModelComponent pmc) throws CoreException {
		IFile modelFile = pmc.getFile();
		TestUtil.changeFileReadonlyStatus(true, modelFile);
		
		Collection children = pmc.getChildren();
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			PersistableModelComponent child = (PersistableModelComponent) iterator.next();
			setResourceToReadonly(child);
		}
	}
	
	public static void waitForDecorator() {
		try {
			if(ExplorerUtil.getTreeViewer() != null) {
				ExplorerUtil.getTreeViewer().refresh();
			}
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			CVSLightweightDecorator.refresh();
			Job.getJobManager().join(DecoratorManager.FAMILY_DECORATE, null);
		} catch (OperationCanceledException e) {
			fail(e.getMessage());
		} catch (InterruptedException e) {
			// ignore
		}
	}
	public static void waitForTransaction() {
		try {
			Job.getJobManager().join(TransactionManager.FAMILY_TRANSACTION, null);
		} catch (OperationCanceledException e) {
			fail(e.getMessage());
		} catch (InterruptedException e) {
			// ignore
		}
	}
	public static void waitForPlaceHolderThread() {
		TestingUtilities.waitForThread(PlaceHolderManager.PLACEHOLDER_REWRITER_THREAD_NAME);
	}
	public static void dispatchEvents(long delay) {
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		
		WorkspaceJob job = new WorkspaceJob("test job") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.DECORATE);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
		}
		
		delay(delay);
		waitForTransaction();
		waitForPlaceHolderThread();
		//waitForJobs(); 		// TODO : some junit tests hang here specially in getConsoleText invocation. 
								// I could not figure out the unfinished job, and need to be investigated more.

		waitForDecorator();
		
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		
	}
	/**
	 * Creates the folder passed in if it doesn't exist.  This only creates 
	 * the folder at the last segment of the path.  If there is a file with 
	 * the same name, the file is deleted, and the folder created.
	 * 
	 * @param path the path of the folder that must exist
	 */
	public static void ensureFolderExists(String path) {
		
		File folder = new File(path);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		else if ( !folder.isDirectory() ) {
			folder.delete();
			folder.mkdir();
		}
	}
	public static Ooaofooa getModelRootOfTestDomain(){
		if(modelRoot == null){
			throw new IllegalStateException("Should be invoked after ensureAvailableAndLoaded is called in setup method");
		}
		return modelRoot;
	}
	
	
	/**
	 * Returns the model-root employed by legacy test cases which presume the existence 
	 * of just one domain within the modeling environment.  This model-root is 
	 * kept distinct from the main default root to ensure that the legacy test 
	 * cases aren't relying on instances present under that root.
	 */
	
	public static Ooaofooa getDefaultTestInstance() 
	{
		// ensure that the default model-root exists, as work performed in 
		// the test root will presume its presence
		Ooaofooa.getDefaultInstance();
		
		return Ooaofooa.getInstance(DEFAULT_TEST_MODELSPACE, false);
	}
	
	/**
	 * 
	 */
	public static ObjectLogger resultLogger = new ObjectLogger();
	
	public static boolean doCreateResults = false;

	public static String DEFAULT_XTUML_TEST_MODEL_REPOSITORY = "c:/repositories/git/xtuml/models/test";
	public static final String DEFAULT_PRIVATE_MODEL_REPOSITORY = "c:/repositories/git/xtuml/modelsmg/test";
	
	public static final String DEFAULT_XTUML_DEVELOPMENT_REPOSITORY = "c:/workspace";
	
	public static void compareAndOutputResults(String fileName) throws Exception{
		if (doCreateResults){
			writeResults(fileName);
			resultLogger.clearLog();
			return;
		}
		String[] log_output = resultLogger.getLogContents();
		resultLogger.clearLog();
		compareAndOutputResults(fileName, log_output);
	}
	
	public static void compareAndOutputResults(String fileName, String[] log_output) throws Exception{
		
		//Here get the contents from the file and compare with the contents of 
		//resultLogger.getLogContents()
		IPath expected_path = new Path(fileName);
		File expected_fh = expected_path.toFile();
		FileInputStream expected_fs = new FileInputStream(expected_fh);
		BufferedReader expected_br =
			new BufferedReader(new InputStreamReader(expected_fs));
		
		StringBuffer buffer = new StringBuffer(1000);
		
		String expectedResult = expected_br.readLine();
		
		while(expectedResult != null){
			buffer.append(expectedResult);
			buffer.append("\r\n");
			expectedResult = expected_br.readLine();
		}
		
		expectedResult = buffer.toString();
		buffer.setLength(0);
		
		for (int i = 0; i < log_output.length; ++i)
		{
			buffer.append(log_output[i]);
			buffer.append("\r\n");
		}
		
		String originalResult = buffer.toString();
		
		assertEquals(expectedResult, originalResult);
	}
	
	private static void writeResults(String fileName) throws Exception{
		IPath expected_path = new Path(fileName);
		File expected_fh = expected_path.toFile();
		FileOutputStream expected_fs = new FileOutputStream(expected_fh);
		BufferedWriter expected_wr =
			new BufferedWriter(new OutputStreamWriter(expected_fs));
		String[] log1_output = resultLogger.getLogContents();
		for (int i = 0; i < log1_output.length; ++i)
		{
			expected_wr.write(log1_output[i]);
			expected_wr.newLine();
		}
		expected_wr.close();
	}
	
	public static UUID getTypeID_Generic(ModelRoot modelRoot, final String typeName){		
		Package_c pkgs_lst[] = Package_c.getManyEP_PKGsOnR1405(m_sys);
		PackageableElement_c pkgElems_lst[] =PackageableElement_c.getManyPE_PEsOnR8000(pkgs_lst);
				
		DataType_c dataTypeList[] = DataType_c.getManyS_DTsOnR8001(pkgElems_lst);
		
		for  (int j=0; j< dataTypeList.length; j++)
		{
			if (dataTypeList[j].getName().equals(typeName))
				return dataTypeList[j].getDt_id();
		}
		
		dataTypeList = DataType_c.getManyS_DTsOnR8001(PackageableElement_c.
				                getManyPE_PEsOnR9100(GlobalElementInSystem_c.
						                          getManyG_EISsOnR9100(m_sys)));
		for  (int j=0; j< dataTypeList.length; j++)
		{
			if (dataTypeList[j].getName().equals(typeName))
				return dataTypeList[j].getDt_id();
		}
		
		return null;
	}


	
	public static void waitForJobs() {
		while (Job.getJobManager().currentJob() != null);
		TestingUtilities.allowJobCompletion();
	}
	public static void delay(long waitTimeMilli) {
		Display display = Display.getCurrent();
		display.timerExec((int) waitTimeMilli, new Runnable() {
			
			@Override
			public void run() {
				// do nothing, just here to wake the display
				// if no events hit
			}
		});
		if (display != null) {
			long endTime = System.currentTimeMillis() + waitTimeMilli;
			while (System.currentTimeMillis() < endTime) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			display.update();
		} else {
			try {
				Thread.sleep(waitTimeMilli);
			} catch (Exception e) {
				
			}
		}
	}
	public static ExplorerView getExplorerView() {
		return m_bp_view;
	}      
	public static TreeViewer getMETreeViewer() {
		return m_bp_tree;
	}
	public ModelClass_c getModelClass(final String name) {
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {
			public boolean evaluate(Object c) {
				ModelClass_c cls = (ModelClass_c) c;
				if (cls.getName().equals(name))
					return true;
				return false;
			}
			
		});
		return mc;
	}
	
	public boolean openPerspectiveAndView(final String perspective, final String view) {
		boolean wasSuccessful = true;
		
		// Wait for the xtUMl Debug perspective to open
		try
		{
		  IWorkspaceRunnable r = new IWorkspaceRunnable()
		  {
			public void run(IProgressMonitor monitor) throws CoreException
			{
				m_wp = PlatformUI.getWorkbench().showPerspective(perspective, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
				m_bp_view = (ExplorerView)m_wp.findView(view);
				m_bp_tree = m_bp_view.getTreeViewer();
				m_wp.activate(m_bp_view);
			}
		  };
		  ResourcesPlugin.getWorkspace().run(r, null);
		  TestingUtilities.processDisplayEvents();
		} 
		catch (CoreException x)
		{
			assertTrue("Unable to open the perspective: " + perspective + " view: " + view, false);
		}
		return wasSuccessful;
	}
	
	public class ModelClass_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			ModelClass_c selected = (ModelClass_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public ModelClass_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class Attribute_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Attribute_c selected = (Attribute_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Attribute_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class ISM_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			InstanceStateMachine_c selected =  (InstanceStateMachine_c) candidate;
			return (selected.Get_name().equals(m_name));
		}
		public ISM_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class CSM_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			ClassStateMachine_c selected =  (ClassStateMachine_c) candidate;
			return (selected.Get_name().equals(m_name));
		}
		public CSM_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}    
	public class State_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			StateMachineState_c selected =  (StateMachineState_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public State_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}    
	public class ReferentialAttribute_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			ReferentialAttribute_c selected = (ReferentialAttribute_c) candidate;
			return (selected.Get_root_name().equals(m_name));
		}
		public ReferentialAttribute_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class FunctionParam_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			FunctionParameter_c selected = (FunctionParameter_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public FunctionParam_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class Function_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Function_c selected = (Function_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Function_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class BridgeParam_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			BridgeParameter_c selected =  (BridgeParameter_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public BridgeParam_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class Bridge_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Bridge_c selected =  (Bridge_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Bridge_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class OperationParam_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			OperationParameter_c selected =  (OperationParameter_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public OperationParam_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class Operation_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Operation_c selected =  (Operation_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Operation_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	} 
	
	public class Package_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			Package_c selected =  (Package_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Package_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class StateMachineEventDataItem_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			StateMachineEventDataItem_c selected =  (StateMachineEventDataItem_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public StateMachineEventDataItem_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	} 
	public class ImportedClass_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			ImportedClass_c selected = (ImportedClass_c) candidate;
			return (selected.Get_name().equals(m_name));
		}
		public ImportedClass_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class Datatype_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			DataType_c selected = (DataType_c) candidate;
			return (selected.getName().equals(m_name));
		}
		public Datatype_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	public class UserDatatype_by_name_c implements ClassQueryInterface_c {
		public boolean evaluate(Object candidate) {
			UserDataType_c selected = (UserDataType_c) candidate;
			return (selected.Get_name().equals(m_name));
		}
		public UserDatatype_by_name_c(String name) {
			m_name = name;      
		}
		private String m_name;
	}
	
	public Transaction startTransaction() {
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Test Transaction",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			return transaction;
		} catch (Exception e) {
			if (transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(
						transaction, e);
			}
			CorePlugin.logError("Unable to start transaction.", e);
		}
		return null;
	}
	
	public void endTransaction(Transaction transaction) {
		TransactionManager.getSingleton().endTransaction(transaction);
	}
}