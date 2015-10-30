//=====================================================================
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
//=====================================================================

package org.xtuml.bp.ui.explorer.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectPreferences;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.ExplorerUtil;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.LineSegment_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.canvas.test.CanvasTestUtilities;
import org.xtuml.bp.ui.explorer.MultipleOccurrenceElement;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

/**
 * Performs tests of model-explorer functionality.
 */
public class ExplorerTest extends BaseTest
{
    public ExplorerTest(String name) {
        super(packageName, name);
    }
    
    /**
     * The name of the test domain used during most of these tests.
     */
    private static String testModelName = "odms1";

    /**
     * The name of the test workspace project in which these 
     * tests operate.
     */
    private static final String packageName = "org.xtuml.bp.ui.explorer.test";
    
    static {
        ExplorerUtil.showModelExplorer();
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
						true);
        
        // make sure the user isn't prompted to do a parse all
        // disable parsing as resource locked exceptions occur
        CorePlugin.disableParseAllOnResourceChange();

        ExplorerUtil.showModelExplorer();
    }
    
    
    /**
     * Tests to see if a connector in a canvas can be selected
     * while the "Link with Editor" model-explorer button is depressed.
     * Issue 453 fixed a bug where such selections would fail.   
     */
    public void testConnectorSelectionInLinkedMode() throws CoreException,IOException
    {
        // setup the test project and model
    	PersistableModelComponent domainComponent = ensureAvailableAndLoaded(packageName, testModelName, false, false, "Package");
        modelRoot = (Ooaofooa)domainComponent.getRootModelElement().getModelRoot();

        // make the link-with-editor button (on the explorer view) depressed
        ExplorerUtil.setLinkWithEditor(true);
        
        // open the package that contains the class diagram
        Package_c odmspkg = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
            
            public boolean evaluate(Object candidate) {
                if(((Package_c)candidate).getName().equals("Odms"))
                    return true;
                else
                    return false;
            }
        } );
        GraphicalEditor editor = CanvasTestUtils.openPackageCanvasEditor(odmspkg);
        
        // zoom all contents
        editor.zoomAll();
        BaseTest.dispatchEvents(0);
        
        // get a handle to the R4 relationship
        Ooaofgraphics graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
        CanvasTestUtils util = new CanvasTestUtils();
        Association_c r4 = Association_c.AssociationInstance(
            modelRoot, util.new Association_by_numb_c(4));
            
        // detm the mouse coordinates of an (arbitrary) point on R4's connector    
        Connector_c connector = Connector_c.ConnectorInstance(
            graphicsModelRoot,
            util.new Connector_by_ooaid_c(r4.getRel_id(), 24));
        LineSegment_c segment = LineSegment_c.getOneGD_LSOnR6(connector);
        Point segmentCenter = CanvasTestUtils.getSegmentCenter(segment);
        Point mouseCoords = CanvasTestUtils.convertToMouseCoor(
            segmentCenter, editor.getModel());

        // do a mouse click on the point on R4 detm'd above 
        CanvasTestUtils.createMouseEvent(
            mouseCoords.x, mouseCoords.y, "MouseDown");
        CanvasTestUtils.createMouseEvent(
                mouseCoords.x, mouseCoords.y, "MouseUp");
        
        // check that R4 is the currently selected model element, 
        // even though the model explorer's selection is linked to
        // the canvas editor 
        IStructuredSelection selection = Selection.getInstance().getStructuredSelection();
        assertTrue("Selection is empty", !selection.isEmpty());
        Object selected = selection.getFirstElement();
        assertTrue("Selection is not R4", selected == r4);
    }
           
    static public void restoreProject() throws CoreException {
        // The files are still on the file system, so we just need to 
        // recreate the project resource
        IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot().getProject(packageName);

        if (projectHandle.exists()) {
            // try to open a currently existing project
            projectHandle.open(new NullProgressMonitor());
        }
        else
        {
            // project doesn't exist, create a new project
            final IProjectDescription myTestProject = ResourcesPlugin.getWorkspace().newProjectDescription(packageName);
            myTestProject.setLocation(null); // default location
            projectHandle.create(myTestProject, new NullProgressMonitor());
            projectHandle.open(new NullProgressMonitor());
            XtUMLNature.addNature(projectHandle);
        }
    }   
    
    /**
     * Selects (what should be) the only system item present
     * in the model explorer tree.
     */
    private void selectProjectItemInModelExplorer()
    {
        // select the only system node that should be present
        TreeItem systemItem = ExplorerUtil.findItem(getProject().getName());
        assertTrue("System node could not be found", systemItem != null);
        ExplorerUtil.selectItem(systemItem);
    }
    
    static public void checkTreeItemExistance(NonRootModelElement modelElement,String name)
    {
    	// the tree must be focused for the selection to be
        // be reported to the model explorer 
        TreeViewer viewer = ExplorerUtil.getTreeViewer();
        final Tree tree = viewer.getTree();
        boolean focused = tree.setFocus();
        assertTrue("Could not focus model explorer tree", focused);
        
        // select the node in the tree; note that we must specify
        // the model element, not its tree-item 
        viewer.setSelection(new StructuredSelection(
            new Object[] {modelElement}), false);
        
        // since this test is running on the event-dispatch thread, we
        // have to fire the event pump manually to get the 
        // selection event reported, before proceeding
        BaseTest.dispatchEvents(0);
        waitForDecorator();
        assertTrue("Tree Item with text '"+name+"' dz not exist", 
        			tree.getSelection()[0].getText().startsWith(name));
    }

   static public void checkTreeItemDeletion(NonRootModelElement modelElement)
    {
	   //  the tree must be focused for the selection to be
        // be reported to the model explorer 
        TreeViewer viewer = ExplorerUtil.getTreeViewer();
        final Tree tree = viewer.getTree();
        boolean focused = tree.setFocus();
        assertTrue("Could not focus model explorer tree", focused);
        
        // select the node in the tree; note that we must specify
        // the model element, not its tree-item 
        viewer.setSelection(new StructuredSelection(
            new Object[] {modelElement}), false);
        
        // since this test is running on the event-dispatch thread, we
        // have to fire the event pump manually to get the 
        // selection event reported, before proceeding
        BaseTest.dispatchEvents(0);
        assertTrue("Tree Item still exist after deletion ", tree.getSelectionCount()==0);
    }
    
    public void testSelectionChangeAfterCloseDoesntCauseError() throws Exception{
        // close the explorer
        ExplorerUtil.getView().getSite().getPage().closeAllPerspectives(false, false);
        
        // null out the explorer variable so that
        // the perspective will be opened properly
        ExplorerUtil.setView(null);
        
        // re-open the explorer and BP perspective
        ExplorerUtil.showModelExplorer();
        
        m_bp_view = ExplorerUtil.getView();
        m_bp_tree = m_bp_view.getTreeViewer();
        
        // select the project in the model explorer
        // this will cause an entry in the log file
        // if the listener was not unregistered
        selectProjectItemInModelExplorer();
    }

    private ModelClass_c getModelClassByName(Ooaofooa modelRoot, final String name) {
    	ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((ModelClass_c) candidate).getName().equals(name);
			}
		});
    	return clazz;
    }
    
    public void testSystemSelectionUpdatedWhenExplorerViewActivated() throws CoreException{
        m_bp_view.setLinkWithEditor(true);
    	
        ensureAvailableAndLoaded(packageName, testModelName, false, false, "Package");
    	
    	ModelClass_c disk = getModelClassByName(modelRoot, "Disk");
    	ModelClass_c drive = getModelClassByName(modelRoot, "Drive");
    	ModelClass_c slot = getModelClassByName(modelRoot, "Slot");
    	
    	// set the active part to something other than explorer
		IViewReference[] viewReferences = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for(int i = 0; i < viewReferences.length; i++) {
			if(viewReferences[i].getPart(false) != m_bp_view) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().activate(
								viewReferences[i].getPart(false));
				break;
			}
		}
    	// get the structured selection
    	IStructuredSelection selection = new StructuredSelection(new Object[] {disk, drive, slot});
    	Selection.getInstance().setSelection(selection);
    	
    	m_bp_view.setLinkWithEditor(false);
    	
    	// open the Odms package
    	Package_c odmspkg = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
            
            public boolean evaluate(Object candidate) {
                if(((Package_c)candidate).getName().equals("Odms"))
                    return true;
                else
                    return false;
            }
        } );
        CanvasTestUtils.openPackageCanvasEditor(odmspkg);
        
    	GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
    	
    	// get the center point of the robot class
    	Shape_c shape = CanvasTestUtilities.getModelClassShape(modelRoot, "Robot");
    	Point center = CanvasUtilities.getShapeCenter(shape);
    	center = CanvasTestUtilities.convertToMouseCoor(center, ce.getModel());
    	
    	// select the shape found above
    	CanvasTestUtilities.doMouseMove(center.x, center.y);
    	CanvasTestUtilities.doMousePress(center.x, center.y);
    	CanvasTestUtilities.doMouseRelease(center.x, center.y);

    	// now activate the model explorer view
    	PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(m_bp_view);
        
    	// verify that the current system selection
    	// equals that of what was selected above before
    	// the canvas selection was made
    	BaseTest.dispatchEvents(0);
    	
    	Iterator<?> iterator = selection.iterator();
    	while(iterator.hasNext()) {
    		ModelClass_c clazz = (ModelClass_c) iterator.next();
    		if(!Selection.getInstance().contains(clazz))
    			fail("System selection was not updated when model explorer was activated.");
    	}
   
    }
    
    public void testSystemSelectionNotUpdatedWhenExplorerViewActivated() {
    	// turn on link with editor
    	m_bp_view.setLinkWithEditor(true);
    	
    	ModelClass_c disk = getModelClassByName(modelRoot, "Disk");
    	ModelClass_c drive = getModelClassByName(modelRoot, "Drive");
    	ModelClass_c slot = getModelClassByName(modelRoot, "Slot");
    	
    	// get the structured selection
    	IStructuredSelection selection = new StructuredSelection(new Object[] {disk, drive, slot});
    	Selection.getInstance().setSelection(selection);
    	
    	// open the Odms package
    	Package_c odmspkg = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
            
            public boolean evaluate(Object candidate) {
                if(((Package_c)candidate).getName().equals("Odms"))
                    return true;
                else
                    return false;
            }
        } );
        CanvasTestUtils.openPackageCanvasEditor(odmspkg);
    	
    	GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
    	
    	// get the center point of the robot class
    	Shape_c shape = CanvasTestUtilities.getModelClassShape(modelRoot, "Robot");
    	Point center = CanvasUtilities.getShapeCenter(shape);
    	center = CanvasTestUtilities.convertToMouseCoor(center, ce.getModel());
    	
    	// select the shape found above
    	CanvasTestUtilities.doMouseMove(center.x, center.y);
    	CanvasTestUtilities.doMousePress(center.x, center.y);
    	CanvasTestUtilities.doMouseRelease(center.x, center.y);
    	
    	// now activate the model explorer view
    	PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(m_bp_view);
    	
    	// verify that the current system selection
    	// equals that of what was selected above before
    	// the canvas selection was made
    	BaseTest.dispatchEvents(0);
    	
    	Iterator<?> iterator = selection.iterator();
    	while(iterator.hasNext()) {
    		ModelClass_c clazz = (ModelClass_c) iterator.next();
    		if(Selection.getInstance().contains(clazz))
    			fail("System selection was updated when model explorer was activated.");
    	}
    }
    
	public void testMultipleOccurrenceInTreeInterfaceOperationParameter() {
		// create the interface project
		IProject referredTo = null;
		IProject referring = null;
		try {
			referredTo = TestingUtilities.createProject("interface_operation_referenced_project");
			referring = TestingUtilities.createProject("interface_operation_referring_project");
		} catch (CoreException e) {
				fail(e.getMessage());
		}
		IScopeContext projectScope = new ProjectScope(referring);
		IEclipsePreferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		// create interface with operation and signal each with a parameter
		SystemModel_c referredToSys = getSystemModel(referredTo.getName());
		SystemModel_c referringSys = getSystemModel(referring.getName());
		Menu menu = getExplorerView().getTreeViewer().getTree().getMenu();
		TestUtil.executeInTransaction(referredToSys, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referredToSys);
		Package_c container = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(container, "Newinterface", new Object[0]);
		Interface_c[] ifaces = Interface_c
				.getManyC_IsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(container));
		Interface_c iface = ifaces[ifaces.length - 1];
		TestUtil.executeInTransaction(iface, "Newexecutableproperty", new Object[] {false});
		InterfaceOperation_c iop = InterfaceOperation_c
				.getOneC_IOOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		TestUtil.executeInTransaction(iop, "Newparameter", new Object[0]);
		// create the referring elements
		TestUtil.executeInTransaction(referringSys, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referringSys);
		Package_c referringContainer = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referringContainer, "Newcomponent", new Object[0]);
		Component_c[] components = Component_c
				.getManyC_CsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(referringContainer));
		Component_c component = components[components.length - 1];
		// create a provided interface
		CanvasTestUtilities.openDiagramEditor(referringContainer);
		BaseTest.dispatchEvents(0);
		AbstractTool tool = UITestingUtilities.getTool("Components",
				"Provided Interface");
		UITestingUtilities.activateTool(tool);
		UITestingUtilities.doMouseMove(50, 50);
		UITestingUtilities.doMousePress(50, 50);
		UITestingUtilities.doMouseMove(300, 50);
		UITestingUtilities.doMouseRelease(300, 50);
		UITestingUtilities.deactivateTool(tool);
		// create a required interface
		tool = UITestingUtilities.getTool("Components", "Required Interface");
		UITestingUtilities.activateTool(tool);
		UITestingUtilities.doMouseMove(50, 50);
		UITestingUtilities.doMousePress(50, 50);
		UITestingUtilities.doMouseMove(50, 350);
		UITestingUtilities.doMouseRelease(50, 350);
		UITestingUtilities.deactivateTool(tool);
		// formalize each
		Provision_c pro = Provision_c.getOneC_POnR4009(InterfaceReference_c
				.getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(component)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(pro);
		TestUtil.chooseItemInDialog(500, "Unnamed Interface");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(menu, "Formalize...");
		Requirement_c req = Requirement_c.getOneC_ROnR4009(InterfaceReference_c
				.getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(component)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(req);
		TestUtil.chooseItemInDialog(500, "Unnamed Interface");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(menu, "Formalize...");
		DecoratingLabelProvider labelProvider = (DecoratingLabelProvider) getExplorerView()
				.getTreeViewer().getLabelProvider();
		ExplorerUtil.expandAll();
		BaseTest.dispatchEvents(0);
		TreeItem findItem = ExplorerUtil.findItem(null, "Unnamed Parameter");
		assertTrue("Expected to find a wrapper class.",
				findItem.getData() instanceof MultipleOccurrenceElement);
		MultipleOccurrenceElement ele = (MultipleOccurrenceElement) findItem
				.getData();
		assertTrue(
				"Label provider did not return same icon for the MultipleOccurrenceElement as it did for the real element.",
				labelProvider.getImage(ele) == labelProvider.getImage(ele
						.getElement()));
		assertTrue(
				"Label provider did not return same text for the MultipleOccurrenceElement as it did for the real element.",
				labelProvider.getText(ele) == labelProvider.getText(ele
						.getElement()));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(ele);
		assertTrue(
				"Unable to find Cut menu item for MultipleOccurrenceElement.",
				UITestingUtilities.checkItemStatusInContextMenu(menu, "Cut",
						"", false));
		assertTrue(
				"Unable to find Copy menu item for MultipleOccurrenceElement.",
				UITestingUtilities.checkItemStatusInContextMenu(menu, "Copy",
						"", false));
		assertTrue(
				"Unable to find Delete menu item for MultipleOccurrenceElement.",
				UITestingUtilities.checkItemStatusInContextMenu(menu, "Delete",
						"", false));
		assertTrue(
				"Unable to find Rename menu item for MultipleOccurrenceElement.",
				UITestingUtilities.checkItemStatusInContextMenu(menu, "Rename",
						"", false));
		// rename the occurrence
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(ele);
		ExplorerUtil.selectItem(findItem);
		ExplorerUtil.renameItem("new_name");
		List<MultipleOccurrenceElement> moes = getAllMultipleOccurrenceElements(ExplorerUtil
				.findItem(container));
		List<MultipleOccurrenceElement> otherMoes = getAllMultipleOccurrenceElements(ExplorerUtil
				.findItem(referringContainer));
		for (MultipleOccurrenceElement element : moes) {
			assertTrue(
					"MutlipleOccurrenceElement did not have its name properly updated.",
					labelProvider.getText(element).equals("new_name"));
		}
		for (MultipleOccurrenceElement element : otherMoes) {
			assertTrue(
					"MutlipleOccurrenceElement did not have its name properly updated.",
					labelProvider.getText(element).equals("new_name"));
		}
		ExplorerUtil.selectItem(findItem);
		ExplorerUtil.deleteItem();
		BaseTest.dispatchEvents(0);
		moes = getAllMultipleOccurrenceElements(ExplorerUtil
				.findItem(container));
		otherMoes = getAllMultipleOccurrenceElements(ExplorerUtil
				.findItem(referringContainer));
		assertTrue("MultipleOccurrenceElements were not removed on delete.",
				moes.size() == 0 && otherMoes.size() == 0);
	}

	public void testMultipleOccurrenceInTreeInterfaceSignalParameter() {
		// create the interface project
		IProject referredTo = null;
		IProject referring = null;
		try {
			referredTo = TestingUtilities.createProject("interface_signal_referenced_project");
			referring = TestingUtilities.createProject("interface_signal_referring_project");
		} catch (CoreException e) {
				fail(e.getMessage());
		}
		IScopeContext projectScope = new ProjectScope(referring);
		IEclipsePreferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		// create interface with operation and signal each with a parameter
		SystemModel_c referredToSys = getSystemModel(referredTo.getName());
		SystemModel_c referringSys = getSystemModel(referring.getName());
		Menu menu = getExplorerView().getTreeViewer().getTree().getMenu();
		TestUtil.executeInTransaction(referredToSys, "Newpackage", new Object[0]);
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(referredToSys);
		Package_c container = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(container, "Newinterface", new Object[0]);
		Interface_c[] ifaces = Interface_c
				.getManyC_IsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(container));
		Interface_c iface = ifaces[ifaces.length - 1];
		TestUtil.executeInTransaction(iface, "Newexecutableproperty", new Object[] {true});
		InterfaceSignal_c signal = InterfaceSignal_c
				.getOneC_ASOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		TestUtil.executeInTransaction(signal, "Newparameter", new Object[0]);
		// create the referring elements
		TestUtil.executeInTransaction(referringSys, "Newpackage", new Object[0]);
		pkgs = Package_c.getManyEP_PKGsOnR1401(referringSys);
		Package_c referringContainer = pkgs[pkgs.length - 1];
		TestUtil.executeInTransaction(referringContainer, "Newcomponent", new Object[0]);
		Component_c[] components = Component_c
				.getManyC_CsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(referringContainer));
		Component_c component = components[components.length - 1];
		// create a provided interface
		CanvasTestUtilities.openDiagramEditor(referringContainer);
		BaseTest.dispatchEvents(0);
		AbstractTool tool = UITestingUtilities.getTool("Components",
				"Provided Interface");
		UITestingUtilities.activateTool(tool);
		UITestingUtilities.doMouseMove(50, 50);
		UITestingUtilities.doMousePress(50, 50);
		UITestingUtilities.doMouseMove(300, 50);
		UITestingUtilities.doMouseRelease(300, 50);
		UITestingUtilities.deactivateTool(tool);
		// create a required interface
		tool = UITestingUtilities.getTool("Components", "Required Interface");
		UITestingUtilities.activateTool(tool);
		UITestingUtilities.doMouseMove(50, 50);
		UITestingUtilities.doMousePress(50, 50);
		UITestingUtilities.doMouseMove(50, 350);
		UITestingUtilities.doMouseRelease(50, 350);
		UITestingUtilities.deactivateTool(tool);
		// formalize each
		Provision_c pro = Provision_c.getOneC_POnR4009(InterfaceReference_c
				.getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(component)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(pro);
		TestUtil.chooseItemInDialog(500, "Unnamed Interface");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(menu, "Formalize...");
		Requirement_c req = Requirement_c.getOneC_ROnR4009(InterfaceReference_c
				.getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(component)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(req);
		TestUtil.chooseItemInDialog(500, "Unnamed Interface");
		TestUtil.okToDialog(1000);
		UITestingUtilities.activateMenuItem(menu, "Formalize...");
		DecoratingLabelProvider labelProvider = (DecoratingLabelProvider) getExplorerView()
				.getTreeViewer().getLabelProvider();
		ExplorerUtil.expandAll();
		BaseTest.dispatchEvents(0);
		TreeItem findItem = ExplorerUtil.findItem(null, "Unnamed Parameter");
		assertTrue("Expected to find a wrapper class.",
				findItem.getData() instanceof MultipleOccurrenceElement);
		MultipleOccurrenceElement ele = (MultipleOccurrenceElement) findItem
				.getData();
		assertTrue(
				"Label provider did not return same icon for the MultipleOccurrenceElement as it did for the real element.",
				labelProvider.getImage(ele) == labelProvider.getImage(ele
						.getElement()));
		assertTrue(
				"Label provider did not return same text for the MultipleOccurrenceElement as it did for the real element.",
				labelProvider.getText(ele) == labelProvider.getText(ele
						.getElement()));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(ele);
		assertTrue(
				"Unable to find Cut menu item for MultipleOccurrenceElement.",
				UITestingUtilities.checkItemStatusInContextMenu(menu, "Cut",
						"", false));
		assertTrue(
				"Unable to find Copy menu item for MultipleOccurrenceElement.",
				UITestingUtilities.checkItemStatusInContextMenu(menu, "Copy",
						"", false));
		assertTrue(
				"Unable to find Delete menu item for MultipleOccurrenceElement.",
				UITestingUtilities.checkItemStatusInContextMenu(menu, "Delete",
						"", false));
		assertTrue(
				"Unable to find Rename menu item for MultipleOccurrenceElement.",
				UITestingUtilities.checkItemStatusInContextMenu(menu, "Rename",
						"", false));
		// rename the occurrence
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(ele);
		ExplorerUtil.selectItem(findItem);
		ExplorerUtil.renameItem("new_name");
		List<MultipleOccurrenceElement> moes = getAllMultipleOccurrenceElements(ExplorerUtil
				.findItem(container));
		List<MultipleOccurrenceElement> otherMoes = getAllMultipleOccurrenceElements(ExplorerUtil
				.findItem(referringContainer));
		for (MultipleOccurrenceElement element : moes) {
			assertTrue(
					"MutlipleOccurrenceElement did not have its name properly updated.",
					labelProvider.getText(element).equals("new_name"));
		}
		for (MultipleOccurrenceElement element : otherMoes) {
			assertTrue(
					"MutlipleOccurrenceElement did not have its name properly updated.",
					labelProvider.getText(element).equals("new_name"));
		}
		ExplorerUtil.selectItem(findItem);
		ExplorerUtil.deleteItem();
		BaseTest.dispatchEvents(0);
		moes = getAllMultipleOccurrenceElements(ExplorerUtil
				.findItem(container));
		otherMoes = getAllMultipleOccurrenceElements(ExplorerUtil
				.findItem(referringContainer));
		assertTrue("MultipleOccurrenceElements were not removed on delete.",
				moes.size() == 0 && otherMoes.size() == 0);
	}

	private List<MultipleOccurrenceElement> getAllMultipleOccurrenceElements(TreeItem root) {
		List<MultipleOccurrenceElement> moes = new ArrayList<MultipleOccurrenceElement>();
		if(root.getData() instanceof MultipleOccurrenceElement) {
			moes.add((MultipleOccurrenceElement) root.getData());
		}
		for(TreeItem item : root.getItems()) {
			moes.addAll(getAllMultipleOccurrenceElements(item));
		}
		return moes;
	}

}

