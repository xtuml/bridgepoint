package org.xtuml.bp.ui.properties.test;
//=====================================================================
//
//File:      $RCSfile: PropertiesRenameTests.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 05:34:58 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
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
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Enumerator_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.LiteralSymbolicConstant_c;
import org.xtuml.bp.core.MessageArgument_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StructureMember_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.cells.editors.ModelElementNameValidator;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.properties.ComponentNameValidator;
import org.xtuml.bp.ui.properties.InterfaceNameValidator;
import org.xtuml.bp.ui.properties.ModelClassNameValidator;
import org.xtuml.bp.ui.properties.PackageNameValidator;

@RunWith(OrderedRunner.class)
public class PropertiesRenameTests extends BaseTest {

	boolean firstRun = true;
	@Override
	@Before
	public void initialSetup() {
		if (!firstRun)
			return;
		firstRun = false;
		// load test model
		TestingUtilities
				.importTestingProjectIntoWorkspace("PropertiesRenameTests");
		BaseTest.dispatchEvents(0);
		m_sys = getSystemModel("PropertiesRenameTests");
		modelRoot = (Ooaofooa) Package_c.getOneEP_PKGOnR1401(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"testPackage");
					}
				}).getModelRoot();
	}

	@Test
	public void testRenameFunctionParameter() {
		FunctionParameter_c uut = FunctionParameter_c
				.FunctionParameterInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameOperationParameter() {
		OperationParameter_c uut = OperationParameter_c
				.OperationParameterInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameBridgeParameter() {
		BridgeParameter_c uut = BridgeParameter_c
				.BridgeParameterInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameFunction() {
		Function_c uut = Function_c.FunctionInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameOperation() {
		Operation_c uut = Operation_c.OperationInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameBridge() {
		Bridge_c uut = Bridge_c.BridgeInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameStateMachineEventDataItem() {
		StateMachineEventDataItem_c uut = StateMachineEventDataItem_c
				.StateMachineEventDataItemInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameEnumerator() {
		Enumerator_c uut = Enumerator_c.EnumeratorInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameLiteralSymbolicConstant() {
		LiteralSymbolicConstant_c uut = LiteralSymbolicConstant_c
				.LiteralSymbolicConstantInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameStructureMember() {
		StructureMember_c uut = StructureMember_c
				.StructureMemberInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameInterfaceOperation() {
		InterfaceOperation_c uut = InterfaceOperation_c
				.InterfaceOperationInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameInterfaceSignal() {
		InterfaceSignal_c uut = InterfaceSignal_c
				.InterfaceSignalInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenameMessageArgument() {
		MessageArgument_c uut = MessageArgument_c
				.MessageArgumentInstance(modelRoot);
		doRenameTestSpaces(uut, new ModelElementNameValidator(uut),
				CorePlugin.INVALID_NAME_SPACES);
	}

	@Test
	public void testRenamePackageInvalidOSChar() {
		Package_c uut = Package_c.PackageInstance(modelRoot);
		if (Platform.getOS().contains("win")) {
			doRenameTestInvalidChar(uut, new PackageNameValidator(), "* is an invalid character in resource name '*'.", "*");
		}
		else {
			doRenameTestInvalidChar(uut, new PackageNameValidator(), "/ is an invalid character in resource name '/'.", "/");
		}
	}

	@Test
	public void testRenameClassInvalidOSChar() {
		ModelClass_c uut = ModelClass_c.ModelClassInstance(modelRoot);
		if (Platform.getOS().contains("win")) {
			doRenameTestInvalidChar(uut, new ModelClassNameValidator(), "* is an invalid character in resource name '*'.", "*");
		}
		else {
			doRenameTestInvalidChar(uut, new ModelClassNameValidator(), "/ is an invalid character in resource name '/'.", "/");
		}
	}

	@Test
	public void testRenameComponentInvalidOSChar() {
		Component_c uut = Component_c.ComponentInstance(modelRoot);
		if (Platform.getOS().contains("win")) {
			doRenameTestInvalidChar(uut, new ComponentNameValidator(), "* is an invalid character in resource name '*'.", "*");
		}
		else {
			doRenameTestInvalidChar(uut, new ComponentNameValidator(), "/ is an invalid character in resource name '/'.", "/");
		}
	}

	@Test
	public void testRenameInterfaceInvalidOSChar() {
		Interface_c uut = Interface_c.InterfaceInstance(modelRoot);
		if (Platform.getOS().contains("win")) {
			doRenameTestInvalidChar(uut, new InterfaceNameValidator(), "* is an invalid character in resource name '*'.", "*");
		}
		else {
			doRenameTestInvalidChar(uut, new InterfaceNameValidator(), "/ is an invalid character in resource name '/'.", "/");
		}
	}

	private void doRenameTestInvalidChar(NonRootModelElement uut,
			ICellEditorValidator validator, String expected, String value) {
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(uut);
		assertEquals(expected, validator.isValid(value));
	}

	private void doRenameTestSpaces(NonRootModelElement uut,
			ICellEditorValidator validator, String expected) {
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(uut);
		assertEquals(expected, validator.isValid("name with spaces"));
	}
}
