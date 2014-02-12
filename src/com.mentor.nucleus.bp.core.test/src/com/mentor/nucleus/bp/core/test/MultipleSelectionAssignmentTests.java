//========================================================================
//
//File:      $RCSfile: MultipleSelectionAssignmentTests.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/05/10 04:30:24 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package com.mentor.nucleus.bp.core.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ConstantSpecification_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.LeafSymbolicConstant_c;
import com.mentor.nucleus.bp.core.LiteralSymbolicConstant_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.PropertyParameter_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.StructureMember_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.SymbolicConstant_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.actions.GenericPackageAssignComponentOnCL_ICAction;
import com.mentor.nucleus.bp.core.ui.actions.SetReturnTypeOnC_IOAction;
import com.mentor.nucleus.bp.core.ui.actions.SetReturnTypeOnO_TFRAction;
import com.mentor.nucleus.bp.core.ui.actions.SetReturnTypeOnS_BRGAction;
import com.mentor.nucleus.bp.core.ui.actions.SetReturnTypeOnS_SYNCAction;
import com.mentor.nucleus.bp.core.ui.actions.SetTypeOnCNST_LSCAction;
import com.mentor.nucleus.bp.core.ui.actions.SetTypeOnC_PPAction;
import com.mentor.nucleus.bp.core.ui.actions.SetTypeOnO_ATTRAction;
import com.mentor.nucleus.bp.core.ui.actions.SetTypeOnO_TPARMAction;
import com.mentor.nucleus.bp.core.ui.actions.SetTypeOnSM_EVTDIAction;
import com.mentor.nucleus.bp.core.ui.actions.SetTypeOnS_BPARMAction;
import com.mentor.nucleus.bp.core.ui.actions.SetTypeOnS_MBRAction;
import com.mentor.nucleus.bp.core.ui.actions.SetTypeOnS_SPARMAction;
import com.mentor.nucleus.bp.core.ui.actions.SetTypeOnS_UDTAction;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.FailableRunnable;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;

public class MultipleSelectionAssignmentTests extends BaseTest {

	private static final String TEST_PROJECT_NAME = "MultipleSelectionAssignmentTests";

	@Override
	protected void initialSetup() throws Exception {
		// disable auto build
		WorkspaceUtil.setAutobuilding(false);
		// load the test model
		loadProject(TEST_PROJECT_NAME);
		// make sure persistence is enabled
		Ooaofooa.setPersistEnabled(true);
		// need to persist to add new proxies to the file
		// before comparison
		Package_c testPackage = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Test Package");
					}
				});
		testPackage.getPersistableComponent().loadComponentAndChildren(new NullProgressMonitor());
		testPackage.getPersistableComponent().persistSelfAndChildren();
		// process any model change events that may have been
		// created during the above, there are model reload events
		// that can cause the selection to get cleared
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		
		BaseTest.dispatchEvents(0);
	}

	Class<?>[] testClasses = { Bridge_c.class, BridgeParameter_c.class,
			InterfaceOperation_c.class, PropertyParameter_c.class,
			Operation_c.class, OperationParameter_c.class, Function_c.class,
			FunctionParameter_c.class, StructureMember_c.class,
			StateMachineEventDataItem_c.class, Attribute_c.class,
			UserDataType_c.class, LiteralSymbolicConstant_c.class,
			ComponentReference_c.class };

	/**
	 * This test will test each type of element that can use multi-selection
	 * type setting.
	 * 
	 * @throws CoreException
	 * @throws IOException
	 */
	public void testMultipleSelectionAssignment() throws CoreException,
			IOException {
		for (int i = 0; i < testClasses.length; i++) {
			NonRootModelElement[] testElements = getTestElementsForType(testClasses[i]);
			assertTrue("Unable to locate test elements for class type: "
					+ testClasses[i].getSimpleName(), testElements.length > 0);
			// check existence of menu item
			String menuItem = getMenuItemForType(testClasses[i]);
			// add the test elements to the selection
			Selection.getInstance().clear();
			for (int j = 0; j < testElements.length; j++) {
				Selection.getInstance().addToSelection(testElements[j]);
			}
			assertTrue("Unable to locate " + menuItem + " for class type: "
					+ testClasses[i].getSimpleName(), UITestingUtilities
					.checkItemStatusInContextMenu(getExplorerView()
							.getTreeViewer().getTree().getMenu(), menuItem, "",
							false));
			if (testClasses[i] != ComponentReference_c.class) {
				// now execute, selecting string as the type
				FailableRunnable failable = TestUtil.chooseItemInDialog(200,
						"string");
				TestUtil.okElementSelectionDialog(failable);
				// open the dialog
				IObjectActionDelegate action = getActionForType(testClasses[i]);
				action.run(null);
				assertTrue("Error during element choosing.", failable
						.getFailure().equals(""));
				checkResultForType(testClasses[i], testElements);
			} else {
				// for a component reference we use a different action and
				// select a component
				FailableRunnable failable = TestUtil.chooseItemInDialog(200,
						"Component");
				TestUtil.okElementSelectionDialog(failable);
				GenericPackageAssignComponentOnCL_ICAction action = new GenericPackageAssignComponentOnCL_ICAction();
				action.run(null);
				assertTrue("Error during element choosing.", failable
						.getFailure().equals(""));
				// now assert that each test element is assigned to the chosen
				// component
				for (int k = 0; k < testElements.length; k++) {
					ComponentReference_c ref = (ComponentReference_c) testElements[k];
					Component_c comp = Component_c.getOneC_COnR4201(ref);
					assertTrue(
							"Component reference was not set to the proper type.",
							comp.getName().equals("Component"));
				}
			}
			// undo the change and compare the file version with its
			// original state
			m_sys.getTransactionManager().getUndoAction().run();
			compareFileHistoryForTestElements(testElements, testClasses[i]);
		}
	}

	private void compareFileHistoryForTestElements(
			NonRootModelElement[] testElements, Class<?> type)
			throws CoreException, IOException {
		List<IFile> compared = new ArrayList<IFile>();
		for (int i = 0; i < testElements.length; i++) {
			IFile file = testElements[i].getPersistableComponent().getFile();
			if (!compared.contains(file)) {
				IFileState[] history = file
						.getHistory(new NullProgressMonitor());
				// should be 2 history entries
				// we want to compare 1 with the current file
				IFileState state = history[1];
				compareStates(state, file, type);
				compared.add(file);
			}
		}
	}

	private void compareStates(IFileState state, IFile file,
			Class<?> type) throws CoreException, IOException {
		InputStream state1Stream = state.getContents();
		InputStream fileStream = file.getContents();
		byte[] state1bytes = new byte[state1Stream.available()];
		byte[] fileBytes = new byte[fileStream.available()];
		state1Stream.read(state1bytes);
		fileStream.read(fileBytes);
		String state1Contents = new String(state1bytes);
		String fileContents = new String(fileBytes);
		assertEquals("Undo left the following changed for type: ."
				+ type.getSimpleName(), fileContents, state1Contents);
	}

	private void checkResultForType(Class<?> type,
			NonRootModelElement[] testElements) {
		if (type == Bridge_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				Bridge_c element = (Bridge_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR20(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == BridgeParameter_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				BridgeParameter_c element = (BridgeParameter_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR22(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == InterfaceOperation_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				InterfaceOperation_c element = (InterfaceOperation_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR4008(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == PropertyParameter_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				PropertyParameter_c element = (PropertyParameter_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR4007(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == Operation_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				Operation_c element = (Operation_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR116(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == OperationParameter_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				OperationParameter_c element = (OperationParameter_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR118(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == Function_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				Function_c element = (Function_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR25(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == FunctionParameter_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				FunctionParameter_c element = (FunctionParameter_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR26(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == StructureMember_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				StructureMember_c element = (StructureMember_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR45(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == StateMachineEventDataItem_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				StateMachineEventDataItem_c element = (StateMachineEventDataItem_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR524(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == Attribute_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				Attribute_c element = (Attribute_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR114(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == UserDataType_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				UserDataType_c element = (UserDataType_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR18(element);
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
		if (type == LiteralSymbolicConstant_c.class) {
			for (int i = 0; i < testElements.length; i++) {
				LiteralSymbolicConstant_c element = (LiteralSymbolicConstant_c) testElements[i];
				DataType_c dt = DataType_c.getOneS_DTOnR1500(SymbolicConstant_c
						.getOneCNST_SYCOnR1502(LeafSymbolicConstant_c
								.getOneCNST_LFSCOnR1503(element)));
				assertTrue("Data type was not properly set for type "
						+ type.getSimpleName(), dt.getName().equals("string"));
			}
		}
	}

	private IObjectActionDelegate getActionForType(Class<?> type) {
		if (type == Bridge_c.class) {
			return new SetReturnTypeOnS_BRGAction();
		}
		if (type == BridgeParameter_c.class) {
			return new SetTypeOnS_BPARMAction();
		}
		if (type == InterfaceOperation_c.class) {
			return new SetReturnTypeOnC_IOAction();
		}
		if (type == PropertyParameter_c.class) {
			return new SetTypeOnC_PPAction();
		}
		if (type == Operation_c.class) {
			return new SetReturnTypeOnO_TFRAction();
		}
		if (type == OperationParameter_c.class) {
			return new SetTypeOnO_TPARMAction();
		}
		if (type == Function_c.class) {
			return new SetReturnTypeOnS_SYNCAction();
		}
		if (type == FunctionParameter_c.class) {
			return new SetTypeOnS_SPARMAction();
		}
		if (type == StructureMember_c.class) {
			return new SetTypeOnS_MBRAction();
		}
		if (type == StateMachineEventDataItem_c.class) {
			return new SetTypeOnSM_EVTDIAction();
		}
		if (type == Attribute_c.class) {
			return new SetTypeOnO_ATTRAction();
		}
		if (type == UserDataType_c.class) {
			return new SetTypeOnS_UDTAction();
		}
		if (type == LiteralSymbolicConstant_c.class) {
			return new SetTypeOnCNST_LSCAction();
		}
		return null;
	}

	private String getMenuItemForType(Class<?> type) {
		if (type == Bridge_c.class || type == InterfaceOperation_c.class
				|| type == Operation_c.class || type == Function_c.class) {
			return "Set Return Type...";
		}
		if (type == ComponentReference_c.class) {
			return "Assign Component...";
		}
		return "Set Type...";
	}

	private NonRootModelElement[] getTestElementsForType(Class<?> type) {
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		Package_c testPackage = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Test Package");
					}
				});
		assertNotNull(testPackage);
		addTestElementsFromPackage(type, elements, testPackage);
		return elements.toArray(new NonRootModelElement[elements.size()]);
	}

	private void addTestElementsFromPackage(Class<?> type,
			List<NonRootModelElement> elements, Package_c testPackage) {
		PackageableElement_c[] pes = PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage);
		if (type == Bridge_c.class) {
			Bridge_c[] bridges = Bridge_c.getManyS_BRGsOnR19(ExternalEntity_c
					.getManyS_EEsOnR8001(pes));
			for (int i = 0; i < bridges.length; i++) {
				elements.add(bridges[i]);
			}
			return;
		}
		if (type == BridgeParameter_c.class) {
			BridgeParameter_c[] items = BridgeParameter_c
					.getManyS_BPARMsOnR21(Bridge_c
							.getManyS_BRGsOnR19(ExternalEntity_c
									.getManyS_EEsOnR8001(pes)));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == InterfaceOperation_c.class) {
			InterfaceOperation_c[] items = InterfaceOperation_c
					.getManyC_IOsOnR4004(ExecutableProperty_c
							.getManyC_EPsOnR4003(Interface_c
									.getManyC_IsOnR8001(pes)));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == PropertyParameter_c.class) {
			PropertyParameter_c[] items = PropertyParameter_c
					.getManyC_PPsOnR4006(ExecutableProperty_c
							.getManyC_EPsOnR4003(Interface_c
									.getManyC_IsOnR8001(pes)));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == Operation_c.class) {
			Operation_c[] items = Operation_c.getManyO_TFRsOnR115(ModelClass_c
					.getManyO_OBJsOnR8001(pes));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == OperationParameter_c.class) {
			OperationParameter_c[] items = OperationParameter_c
					.getManyO_TPARMsOnR117(Operation_c
							.getManyO_TFRsOnR115(ModelClass_c
									.getManyO_OBJsOnR8001(pes)));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == Function_c.class) {
			Function_c[] items = Function_c.getManyS_SYNCsOnR8001(pes);
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == FunctionParameter_c.class) {
			FunctionParameter_c[] items = FunctionParameter_c
					.getManyS_SPARMsOnR24(Function_c.getManyS_SYNCsOnR8001(pes));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == StructureMember_c.class) {
			StructureMember_c[] items = StructureMember_c
					.getManyS_MBRsOnR44(StructuredDataType_c
							.getManyS_SDTsOnR17(DataType_c
									.getManyS_DTsOnR8001(pes)));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == StateMachineEventDataItem_c.class) {
			StateMachineEventDataItem_c[] items = StateMachineEventDataItem_c
					.getManySM_EVTDIsOnR516(StateMachine_c
							.getManySM_SMsOnR517(InstanceStateMachine_c
									.getManySM_ISMsOnR518(ModelClass_c
											.getManyO_OBJsOnR8001(pes))));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == Attribute_c.class) {
			Attribute_c[] items = Attribute_c.getManyO_ATTRsOnR102(ModelClass_c
					.getManyO_OBJsOnR8001(pes));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == UserDataType_c.class) {
			UserDataType_c[] items = UserDataType_c
					.getManyS_UDTsOnR17(DataType_c.getManyS_DTsOnR8001(pes));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == LiteralSymbolicConstant_c.class) {
			LiteralSymbolicConstant_c[] items = LiteralSymbolicConstant_c
					.getManyCNST_LSCsOnR1503(LeafSymbolicConstant_c
							.getManyCNST_LFSCsOnR1502(SymbolicConstant_c
									.getManyCNST_SYCsOnR1504(ConstantSpecification_c
											.getManyCNST_CSPsOnR8001(pes))));
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
		if (type == ComponentReference_c.class) {
			ComponentReference_c[] items = ComponentReference_c
					.getManyCL_ICsOnR8001(pes);
			for (int i = 0; i < items.length; i++) {
				elements.add(items[i]);
			}
			return;
		}
	}
}
