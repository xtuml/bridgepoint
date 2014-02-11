//=====================================================================
//
//File:      $RCSfile: PropertiesViewTest2.java,v $
//Version:   $Revision: 1.23 $
//Modified:  $Date: 2013/05/10 05:34:58 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
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

package com.mentor.nucleus.bp.ui.properties.test;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassInstanceParticipant_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FormalAttributeValue_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InformalAttributeValue_c;
import com.mentor.nucleus.bp.core.InstanceAttributeValue_c;
import com.mentor.nucleus.bp.core.Message_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SynchronousMessage_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.OoaofooaUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.FailableRunnable;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.ui.properties.AttributeO_ATTRPropertySource;
import com.mentor.nucleus.bp.ui.properties.BridgeParameterS_BPARMPropertySource;
import com.mentor.nucleus.bp.ui.properties.BridgeS_BRGPropertySource;
import com.mentor.nucleus.bp.ui.properties.ChooserPropertyDescriptor;
import com.mentor.nucleus.bp.ui.properties.ClassO_OBJPropertySource;
import com.mentor.nucleus.bp.ui.properties.FormalInstanceAttributeValuesSQ_AVPropertySource;
import com.mentor.nucleus.bp.ui.properties.FunctionS_SYNCPropertySource;
import com.mentor.nucleus.bp.ui.properties.InformalInstanceAttributeValuesSQ_AVPropertySource;
import com.mentor.nucleus.bp.ui.properties.PackagesEP_PKGPropertySource;

/**
 * Contains tests that exercise various aspects of the properties view.
 */
public class PropertiesViewTest2 extends BaseTest 
{
    /**
     * Whether the first test of this class is the one that's currently 
     * being run.
     */
    private static boolean firstTest = true;

    /**
     * That used throughout these tests.
     */
    private static Selection selection = Selection.getInstance();

    public PropertiesViewTest2(String name) {
        super(null, name);
    }    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
	protected void setUp() throws Exception {
        super.setUp();

        // if it's the first test of this class that's being setup
        if (firstTest) {
            ensureAvailableAndLoaded("com.mentor.nucleus.bp.ui.properties.test", "odms1", false, false, "Package");

            // open the odms subsystem diagram, as its getting focus will 
            // cause one of our custom properties sheets to be created and 
            // set into the properties view
            CanvasTestUtilities.openClassDiagramEditor(modelRoot);
            while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
            IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			ExplorerView view = (ExplorerView) page.showView(
					"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null,
					IWorkbenchPage.VIEW_CREATE);
			page.activate(view);

            firstTest = false;
        }	
        // reset perspective
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.resetPerspective();
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
    }

    /**
     * Tests that a property which, prior to issue 1861, would only be displayed
     * if the "Show Advanced Properties" button was depressed, is now shown 
     * regardless of the button's state.
     */
	public void testNoPropertiesAreAdvancedAndBasicCategoryFirst() {
        // select the R9 association
        Association_c r9 = OoaofooaUtil.getAssociation(modelRoot, 9);
        selection.clear();
        selection.addToSelection(r9);
        // check that the AssociationFormalizerEnds category is shown in the
        // properties view, as this was previously only shown if the 
        // "Show Advanced Properties" button of the properties view was 
        // depressed
        Tree tree = UIUtil.getPropertyTree();
		assertTrue("Previously advanced property still not shown", tree
				.getItems()[1].getText().equals("Association Formalizer End"));
    }

    /**
     * Tests that the "Basic" property category is the first one listed for 
     * a model element in the properties view.
     */
	public void testBasicCategoryListedFirst() {
		// select the R9 association
        Association_c r9 = OoaofooaUtil.getAssociation(modelRoot, 9);
		selection.clear();
		selection.addToSelection(r9);

        // check that the "Basic" properties category is the first one shown
        // in the properties view, even though there are other categories for 
        // the selected relationship that would precede it, alphabetically
        Tree tree = UIUtil.getPropertyTree();
		assertTrue("Basic property category is not the first one shown", tree
				.getItems()[0].getText().equals("Basic"));
    }
    
    /**
     * Tests that attributes marked as User_Visible = false
     * are not shown in the properties view
     */
	public void testPropertiesMarkedAsNotShownAreNotShown() {
    	// create and select a synchronous message
    	Message_c parent = new Message_c(modelRoot);
    	SynchronousMessage_c message = new SynchronousMessage_c(modelRoot);
    	parent.relateAcrossR1018To(message);
    	
    	selection.clear();
    	selection.addToSelection(message);
    	
    	// verify that there are no blank
    	// entries, which would be the
    	// label attribute of the message
        Tree tree = UIUtil.getPropertyTree();
        TreeItem[] items = tree.getItems();
        for(int i = 0; i < items.length; i++) {
			assertTrue(
					"Found item without a name which represents a property that should not be shown.",
					!items[i].getText().equals(""));
        }
        parent.Dispose();
    }
    
    /**
     * Tests that putting a space into the value of a name property
     * does not succeed for model-element types which don't allow spaces in
     * their names.
     */
    public void testSpaceNotAllowedInModelElementsNameProperty()
    {
        // test that we can't add a space to an attribute name
        String name = "Serial_Number";
        Package_c subsystem = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Odms");
			}
		});
        ModelClass_c clazz = OoaofooaUtil.getClass(subsystem, "Disk");
        Attribute_c attribute = OoaofooaUtil.getAttribute(clazz, name);

        // must use a different element's name
        name = "Row_Number";
        checkSpaceInModelElementNameProperty(
            new AttributeO_ATTRPropertySource(attribute), "Root_Nam", name, false, true, false);

        // find a function to whose name we'll try to add a space
        name = "test_robot";
        Function_c function = OoaofooaUtil.getFunction(modelRoot, name);
        name = "start";
        checkSpaceInModelElementNameProperty(
            new FunctionS_SYNCPropertySource(function), "Name", name, false, false, false);

        // test that we can still add a space to a model class name
        ExplorerUtil.selectItem(clazz);
        checkSpaceInModelElementNameProperty(
            new ClassO_OBJPropertySource(clazz), "Name", "Disk", false, false, true);

        // test that we can still add a space to a subsystem name
        ExplorerUtil.selectItem(subsystem);
        checkSpaceInModelElementNameProperty(
            new PackagesEP_PKGPropertySource(subsystem), "Name", "Odms", false, false, true);
    }
        
    /**
     * Checks that after adding a space to the given model-element name,
     * the modified name is considered invalid/valid (according to the 
     * spaceAllowed parameter) by the cell editor for the model-element's 
     * name property.  The editor is created by the property-descriptor 
     * returned by the given property-source for the given property-id.
     */
    private void checkSpaceInModelElementNameProperty(
            IPropertySource propertySource, String propertyId, String name,
            boolean spaceAllowed, boolean duplicateName, boolean component)
        {
            // create a cell-editor for the name property's value
            IPropertyDescriptor descriptor =
                PropertiesUtil.getDescriptor(propertySource, propertyId);
            CellEditor editor = descriptor.createPropertyEditor(new Shell());

            // validate this test by checking that the editor says that the
            // given name is invalid since it is a duplicate
            ICellEditorValidator validator = editor.getValidator();
            String result = validator.isValid(name);
            // must use different message when working against a PMC root
            String message = CorePlugin.DUPLICATE_NAME_ERROR;
            if(component) {
            	message = "A model element with the same name already exists.\n\n"
					+ "This is most likely due to uncommitted renames or deletions."
					+ "  If this is the case, please commit any outstanding changes before proceeding.\n\n"
					+ "If the project is not connected to a configuration management system"
					+ ", you will need to remove the target manually.";
            }
            assertTrue(
                "Property cell editor says original name is invalid",
                 duplicateName ? result.equals(message) : result == null );

            // check that the editor says that a name containing a space is
            // invalid/valid
            result = validator.isValid(name + " ");
            assertTrue("Property cell editor says name with spaces is valid",
                spaceAllowed ? result == null : result != null);
        }
    
    /**
     * Tests 'IsFormal' while the Instance Attribute Value is Formal
     */
    public void testIsFormalForFormalInstanceAttributeValue() 
    {
    	ClassInstanceParticipant_c parent = new ClassInstanceParticipant_c(modelRoot);
    	InstanceAttributeValue_c AtrValue = new InstanceAttributeValue_c(modelRoot);
    	FormalAttributeValue_c FmAtr = new FormalAttributeValue_c(modelRoot);
    	AtrValue.relateAcrossR937To(parent);
		FmAtr.relateAcrossR948To(AtrValue);
		FormalInstanceAttributeValuesSQ_AVPropertySource ps = new FormalInstanceAttributeValuesSQ_AVPropertySource(AtrValue);

   		checkIsFormalPropertyForAttributeValue(ps, "true");
   		parent.Dispose();
    }
    /**
     * Tests 'IsFormal' while the Instance Attribute Value is Informal
     */
    public void testIsFormalForInformalInstanceAttributeValue() 
    {
    	ClassInstanceParticipant_c parent = new ClassInstanceParticipant_c(modelRoot);
    	InstanceAttributeValue_c AtrValue = new InstanceAttributeValue_c(modelRoot);
    	InformalAttributeValue_c InfAtr = new InformalAttributeValue_c(modelRoot);
    	AtrValue.relateAcrossR936To(parent);
		InfAtr.relateAcrossR948To(AtrValue);
		InformalInstanceAttributeValuesSQ_AVPropertySource ps = new InformalInstanceAttributeValuesSQ_AVPropertySource(AtrValue);
		
   		checkIsFormalPropertyForAttributeValue(ps, "false");
    	parent.Dispose();
    }

    public void testChooserDialogForChildProperty()
    {
    	// test that initiating the chooser dialog from
    	// a property child works
        Package_c subsystem = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Odms");
			}
		});
        ModelClass_c clazz = OoaofooaUtil.getClass(subsystem, "Disk");
        Attribute_c attr = Attribute_c.getOneO_ATTROnR102(clazz);
        Selection.getInstance().setSelection(new StructuredSelection(clazz));
		ChooserPropertyDescriptor descr = new ChooserPropertyDescriptor(
				"DataType", "Type", attr,
				"com.mentor.nucleus.bp.core.ui.actions.SetTypeOnO_ATTRAction",
				false);
		CellEditor editor = descr.createPropertyEditor(new Shell());
		Control[] children = ((Composite) editor.getControl()).getChildren();
		Label label = null;
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Label) {
				label = (Label) children[i];
				break;
			}
		}
		assertNotNull("Unable to locate label for chooser dialog descriptor", label);
		FailableRunnable chooseItemInDialog = TestUtil.chooseItemInDialog(500, "boolean");
		label.notifyListeners(SWT.MouseDoubleClick, new Event());
		assertTrue(chooseItemInDialog.getFailure(), chooseItemInDialog.getFailure().equals(""));
		TestUtil.okToDialog(1000);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		DataType_c dt = DataType_c.getOneS_DTOnR114(attr);
		assertTrue("boolean data type was not set as type for attribute.", dt.getName().equals("boolean"));
    }
    
    public void testElementOrderingInView() throws CoreException {
    	loadProject("BridgeParameterOrderingTest");
    	Package_c eePkg = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("External Entities");
			}
		});
    	assertNotNull(eePkg);
    	Bridge_c bridge = Bridge_c.getOneS_BRGOnR19(ExternalEntity_c.getManyS_EEsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(eePkg)), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Bridge_c) candidate).getName().equals("concat_string");
			}
		});
    	BridgeS_BRGPropertySource source = new BridgeS_BRGPropertySource(bridge);
    	IPropertyDescriptor[] propertyDescriptors = source.getPropertyDescriptors();
    	boolean foundCorrectOrder = false;
    	boolean foundOne = false;
    	for(IPropertyDescriptor descriptor : propertyDescriptors) {
    		Object propertyValue = source.getPropertyValue(descriptor.getId());
    		if(propertyValue instanceof BridgeParameterS_BPARMPropertySource) {
    			BridgeParameterS_BPARMPropertySource paramSource = (BridgeParameterS_BPARMPropertySource) propertyValue;
    			if(paramSource.toString().equals("s1")) {
    				foundOne = true;
    			}
    			if(paramSource.toString().equals("s2")) {
    				if(foundOne) {
    					foundCorrectOrder = true;
    				}
    				break;
    			}
    		}
    	}
    	assertTrue("User-defined ordering is not honored in the properties view.", foundCorrectOrder);
    }
    
	private void checkIsFormalPropertyForAttributeValue(IPropertySource ps, String value) {

 		Object pv = ps.getPropertyValue("isFormal");
    	assertNotNull("isFormal property not present", pv);
    	assertEquals("isFormal property has the wrong value", Boolean.valueOf(value), pv);
	}
}
