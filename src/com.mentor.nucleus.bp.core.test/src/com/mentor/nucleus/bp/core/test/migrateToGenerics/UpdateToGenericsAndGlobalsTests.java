package com.mentor.nucleus.bp.core.test.migrateToGenerics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.ActivityInActivity_c;
import com.mentor.nucleus.bp.core.Activity_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.CommunicationInCommunication_c;
import com.mentor.nucleus.bp.core.Communication_c;
import com.mentor.nucleus.bp.core.ComponentInComponent_c;
import com.mentor.nucleus.bp.core.ComponentPackageInPackage_c;
import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ConstantInPackage_c;
import com.mentor.nucleus.bp.core.DataTypeInPackage_c;
import com.mentor.nucleus.bp.core.DataTypePackageInPackage_c;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DelegationInComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EePackageInPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntityInModel_c;
import com.mentor.nucleus.bp.core.ExternalEntityInPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.FunctionInPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackageInDomain_c;
import com.mentor.nucleus.bp.core.FunctionPackageInPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.GlobalElementInSystem_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfacePackage_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.PackageInPackage_c;
import com.mentor.nucleus.bp.core.PackageParticipant_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.PropertyParameter_c;
import com.mentor.nucleus.bp.core.SatisfactionInComponentPackage_c;
import com.mentor.nucleus.bp.core.SatisfactionInComponent_c;
import com.mentor.nucleus.bp.core.SequenceInSequence_c;
import com.mentor.nucleus.bp.core.Sequence_c;
import com.mentor.nucleus.bp.core.SpecificationPackage_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StructureMember_c;
import com.mentor.nucleus.bp.core.SubsystemInDomain_c;
import com.mentor.nucleus.bp.core.SubsystemInSubsystem_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.SymbolicConstant_c;
import com.mentor.nucleus.bp.core.UseCaseDiagram_c;
import com.mentor.nucleus.bp.core.UseCaseInUseCase_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Ooatype_c;

public class UpdateToGenericsAndGlobalsTests extends BaseTest {

	public UpdateToGenericsAndGlobalsTests() {
		super("model_upgrade", null);
	}

	@Override
	public void initialSetup() throws CoreException {
		TestingUtilities.importModelUsingWizard(m_sys, m_workspace_path
				+ "/models/pre-716-test-models/pre-716-test-model.xtuml", false);
		BaseTest.dispatchEvents(0);
		m_sys = getSystemModel(getProject().getName());
	}

	public void testGlobalMigrationOperationParameterDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		OperationParameter_c param = OperationParameter_c.OperationParameterInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR118(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("OperationParameter was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with OperationParameter after global migration",
				dt.getName().equals("inst_ref<Timer>"));
	}

	public void testGlobalMigrationOperationDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		Operation_c param = Operation_c.OperationInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR116(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("Operation was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with Operation after global migration",
				dt.getName().equals("boolean"));
	}

	public void testGlobalMigrationAttributeDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		Attribute_c param = Attribute_c.AttributeInstance(testRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Attribute_c) candidate).getName().equals("attribute");
			}
		});
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR114(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("Attribute was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with Attribute after global migration",
				dt.getName().equals("boolean"));
	}

	public void testGlobalMigrationStateMachineEventDataItemDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		StateMachineEventDataItem_c param = StateMachineEventDataItem_c.StateMachineEventDataItemInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR524(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("StateMachineEventDataItem was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with StateMachineEventDataItem after global migration",
				dt.getName().equals("timestamp"));
	}

	public void testGlobalMigrationBridgeDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		Bridge_c param = Bridge_c.BridgeInstance(testRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Bridge_c) candidate).getName().equals("create_date");
			}
		});
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR20(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("Bridge was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with Bridge after global migration",
				dt.getName().equals("date"));
	}

	public void testGlobalMigrationBridgeParameterDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		BridgeParameter_c param = BridgeParameter_c.BridgeParameterInstance(testRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((BridgeParameter_c) candidate).getName().equals("message");
			}
		});
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR22(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("BridgeParameter was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with BridgeParameter after global migration",
				dt.getName().equals("string"));
	}

	public void testGlobalMigrationFunctionDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		Function_c param = Function_c.FunctionInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR25(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("Function was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with Function after global migration",
				dt.getName().equals("integer"));
	}

	public void testGlobalMigrationFunctionParameterDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		FunctionParameter_c param = FunctionParameter_c.FunctionParameterInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR26(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("FunctionParameter was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with FunctionParameter after global migration",
				dt.getName().equals("unique_id"));
	}

	public void testGlobalMigrationStructureMemberDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		StructureMember_c param = StructureMember_c.StructureMemberInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR45(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("StructureMember was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with StructureMember after global migration",
				dt.getName().equals("real"));
	}

	public void testGlobalMigrationUserDataTypeDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		UserDataType_c param = UserDataType_c.UserDataTypeInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR18(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("UserDataType was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with UserDataType after global migration",
				dt.getName().equals("boolean"));
	}

	public void testGlobalMigrationSymbolicConstantDomain() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/domain/domain.xtuml");
		SymbolicConstant_c param = SymbolicConstant_c.SymbolicConstantInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR1500(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("SymbolicConstant was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with SymbolicConstant after global migration",
				dt.getName().equals("string"));
	}

	public void testGlobalMigrationOperationParameterComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		OperationParameter_c param = OperationParameter_c.OperationParameterInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR118(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("OperationParameter was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with OperationParameter after global migration",
				dt.getName().equals("inst_ref<Timer>"));
	}

	public void testGlobalMigrationOperationComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		Operation_c param = Operation_c.OperationInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR116(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("Operation was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with Operation after global migration",
				dt.getName().equals("boolean"));
	}

	public void testGlobalMigrationAttributeComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		Attribute_c param = Attribute_c.AttributeInstance(testRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Attribute_c) candidate).getName().equals("attribute");
			}
		});
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR114(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("Attribute was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with Attribute after global migration",
				dt.getName().equals("boolean"));
	}

	public void testGlobalMigrationStateMachineEventDataItemComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		StateMachineEventDataItem_c param = StateMachineEventDataItem_c.StateMachineEventDataItemInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR524(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("StateMachineEventDataItem was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with StateMachineEventDataItem after global migration",
				dt.getName().equals("timestamp"));
	}

	public void testGlobalMigrationBridgeComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		Bridge_c param = Bridge_c.BridgeInstance(testRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Bridge_c) candidate).getName().equals("create_date");
			}
		});
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR20(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("Bridge was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with Bridge after global migration",
				dt.getName().equals("date"));
	}

	public void testGlobalMigrationBridgeParameterComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		BridgeParameter_c param = BridgeParameter_c.BridgeParameterInstance(testRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((BridgeParameter_c) candidate).getName().equals("message");
			}
		});
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR22(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("BridgeParameter was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with BridgeParameter after global migration",
				dt.getName().equals("string"));
	}

	public void testGlobalMigrationFunctionComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		Function_c param = Function_c.FunctionInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR25(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("Function was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with Function after global migration",
				dt.getName().equals("integer"));
	}

	public void testGlobalMigrationFunctionParameterComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		FunctionParameter_c param = FunctionParameter_c.FunctionParameterInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR26(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("FunctionParameter was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with FunctionParameter after global migration",
				dt.getName().equals("unique_id"));
	}

	public void testGlobalMigrationStructureMemberComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		StructureMember_c param = StructureMember_c.StructureMemberInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR45(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("StructureMember was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with StructureMember after global migration",
				dt.getName().equals("real"));
	}

	public void testGlobalMigrationUserDataTypeComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		UserDataType_c param = UserDataType_c.UserDataTypeInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR18(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("UserDataType was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with UserDataType after global migration",
				dt.getName().equals("boolean"));
	}

	public void testGlobalMigrationSymbolicConstantComponent() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/ComponentPackage/ComponentPackage.xtuml");
		SymbolicConstant_c param = SymbolicConstant_c.SymbolicConstantInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR1500(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("SymbolicConstant was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with SymbolicConstant after global migration",
				dt.getName().equals("string"));
	}

	public void testGlobalMigrationPropertyParameter() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/InterfacePackage/InterfacePackage.xtuml");
		PropertyParameter_c param = PropertyParameter_c.PropertyParameterInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR4007(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("PropertyParameter was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with PropertyParameter after global migration",
				dt.getName().equals("date"));
	}

	public void testGlobalMigrationInterfaceOperation() throws Exception {
		Ooaofooa testRoot = Ooaofooa
				.getInstance("/model_upgrade/models/model_upgrade/InterfacePackage/InterfacePackage.xtuml");
		InterfaceOperation_c param = InterfaceOperation_c.InterfaceOperationInstance(testRoot);
		assertNotNull("Unable to locate test element", param);
		DataType_c dt = DataType_c.getOneS_DTOnR4008(param);
		assertNotNull("Unable to locate test element", dt);
		GlobalElementInSystem_c gis = GlobalElementInSystem_c
				.getOneG_EISOnR9100(PackageableElement_c.getOnePE_PEOnR8001(dt));
		assertNotNull("InterfaceOperation was not migrated to use globals.", gis);
		assertTrue(
				"Incorrect data type associated with InterfaceOperation after global migration",
				dt.getName().equals("real"));		
	}
	
	public void testFunctionPackageParticipantUpgrade() {
		NonRootModelElement[] parts = collectAllElementsOfType(PackageParticipant_c.class);
		assertTrue("Function Package Participants were not converted to Package Participants", parts.length == 2);
		for(NonRootModelElement part : parts) {
			assertTrue("Function package participant was not left formalized",
					((PackageParticipant_c) part).getIsformal());
		}
	}
	
	public void testSystemModelConversion() {
		Package_c[] packages = Package_c.getManyEP_PKGsOnR1401(m_sys);
		assertEquals(
				"Unexpected package count under system model after upgrade.",
				packages.length, 8);
		performDiagramTest(m_sys, 8);
	}
	
	public void testDomainConversion() {
		Package_c domPkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		assertNotNull("Could not locate a converted domain", domPkg);
		performDiagramTest(domPkg, 9);
		performPETest(domPkg, 9);
	}
	
	private void performPETest(Package_c pkg, int expectedPECount) {
		PackageableElement_c[] pes = PackageableElement_c
				.getManyPE_PEsOnR8000(pkg);
		assertEquals("Did not find expected PE count.", expectedPECount,
				pes.length);
	}

	private void performPETest(Component_c comp, int expectedPECount) {
		PackageableElement_c[] pes = PackageableElement_c
				.getManyPE_PEsOnR8003(comp);
		assertEquals("Did not find expected PE count.", expectedPECount,
				pes.length);
	}
	
	public void testSequenceConversion() {
		Package_c domPkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		assertNotNull("Could not locate test package", domPkg);
		Package_c seqPackage = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(domPkg), new ClassQueryInterface_c() {
							
							@Override
							public boolean evaluate(Object candidate) {
								return ((Package_c) candidate).getName().equals("Sequence");
							}
						});
		assertNotNull("Could not locate converted sequence diagram.", seqPackage);
		performDiagramTest(seqPackage, 14);
		performPETest(seqPackage, 11);
	}
	
	public void testComponentPackageConversion() {
		Package_c cp = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("ComponentPackage");
			}
		});
		assertNotNull("Could not locate converted component package.", cp);
		performDiagramTest(cp, 12);
		performPETest(cp, 10);
	}
	
	public void testInterfacePackageConversion() {
		Package_c ip = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("InterfacePackage");
			}
		});
		assertNotNull("Could not locate converted interface package.", ip);
		performDiagramTest(ip, 2);
		performPETest(ip, 2);
	}
	
	public void testDomainAsComponentConversion() {
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("ComponentPackage");
			}
		});
		assertNotNull("Could not locate host package.", pkg);
		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(pkg), new ClassQueryInterface_c() {
					
					@Override
					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals("component");
					}
				});
		assertNotNull("Could not locate converted component.", comp);
		performDiagramTest(comp, 6);
		performPETest(comp, 4);
	}

	public void testDataTypePackageConversion() {
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Datatypes");
			}
		});
		assertNotNull("Could not locate converted data type package.", pkg);
		performDiagramTest(pkg, 5);
		performPETest(pkg, 5);
	}
	
	public void testSubsystemConversion() {
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		assertNotNull("Could not locate host package.", pkg);
		Package_c ss = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("subsystem");
			}
		});
		assertNotNull("Could not locate converted subsystem.", ss);
		performDiagramTest(ss, 9);
		performPETest(ss, 11);
	}	
	
	public void testActivityConversion() {
		Package_c domPkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		assertNotNull("Could not locate test package", domPkg);
		Package_c seqPackage = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(domPkg), new ClassQueryInterface_c() {
							
							@Override
							public boolean evaluate(Object candidate) {
								return ((Package_c) candidate).getName().equals("Activity");
							}
						});
		assertNotNull("Could not locate converted activity diagram.", seqPackage);
		performDiagramTest(seqPackage, 11);
		performPETest(seqPackage, 11);
	}
	
	public void testUseCaseConversion() {
		Package_c domPkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		assertNotNull("Could not locate test package", domPkg);
		Package_c seqPackage = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(domPkg), new ClassQueryInterface_c() {
							
							@Override
							public boolean evaluate(Object candidate) {
								return ((Package_c) candidate).getName().equals("Use Case Diagram");
							}
						});
		assertNotNull("Could not locate converted use case diagram.", seqPackage);
		performDiagramTest(seqPackage, 8);
		performPETest(seqPackage, 8);
	}
	
	public void testCommunicationConversion() {
		Package_c domPkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		assertNotNull("Could not locate test package", domPkg);
		Package_c seqPackage = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(domPkg), new ClassQueryInterface_c() {
							
							@Override
							public boolean evaluate(Object candidate) {
								return ((Package_c) candidate).getName().equals("Communication");
							}
						});
		assertNotNull("Could not locate converted communication diagram.", seqPackage);
		performDiagramTest(seqPackage, 11);
		performPETest(seqPackage, 10);	
	}
	
	public void testFunctionPackageConversion() {
		Package_c domPkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		assertNotNull("Could not locate test package", domPkg);
		Package_c seqPackage = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(domPkg), new ClassQueryInterface_c() {
							
							@Override
							public boolean evaluate(Object candidate) {
								return ((Package_c) candidate).getName().equals("functions");
							}
						});
		assertNotNull("Could not locate converted function package.", seqPackage);
		performDiagramTest(seqPackage, 1);
		performPETest(seqPackage, 2);	
	}
	
	public void testExternalEntityPackageConversion() {
		Package_c domPkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("domain");
			}
		});
		assertNotNull("Could not locate test package", domPkg);
		Package_c seqPackage = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(domPkg), new ClassQueryInterface_c() {
							
							@Override
							public boolean evaluate(Object candidate) {
								return ((Package_c) candidate).getName().equals("External Entities");
							}
						});
		assertNotNull("Could not locate converted external entity package.", seqPackage);
		performDiagramTest(seqPackage, 4);
		performPETest(seqPackage, 4);		
	}
	
	private void performDiagramTest(final NonRootModelElement element, int expectedGraphicalElementCount) {
		Model_c model = Model_c.ModelInstance(
				Ooaofgraphics.getInstance(element.getModelRoot().getId()),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Model_c) candidate).getOoa_id().equals(
								element.Get_ooa_id());
					}
				});
		assertNotNull("Could not locate diagram for element : "
				+ element.getClass().getSimpleName(), model);
		// if the element is a package assert the Ooa type for the model
		if(element instanceof Package_c) {
			assertTrue(
					"Found a diagram representing a package with an improper ooa type.",
					model.getOoa_type() == Ooatype_c.Package);
		}
		GraphicalElement_c[] elements = GraphicalElement_c.getManyGD_GEsOnR1(model);
		assertEquals("Did not find the expected graphical element count for : "
				+ element.getClass().getSimpleName(),
				expectedGraphicalElementCount, elements.length);
		for(GraphicalElement_c ge : elements) {
			// if the represents value is a package assert the proper ooa type
			if(ge.getRepresents() instanceof Package_c) {
				assertTrue(
						"Found a graphical element representing a package with an improper ooa type.",
						ge.getOoa_type() == Ooatype_c.Package);
			}
		}
	}

	public void testNoSpecilizedPackagesExist() {
		NonRootModelElement[] pkgs = collectAllElementsOfType(Sequence_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(Domain_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(DataTypePackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(Activity_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(UseCaseDiagram_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(Communication_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(ComponentPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(InterfacePackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(FunctionPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(Subsystem_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(ExternalEntityPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(SatisfactionInComponent_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(DelegationInComponent_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(DataTypeInPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(ConstantInPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(ComponentPackageInPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(UseCaseInUseCase_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(ActivityInActivity_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(SequenceInSequence_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(CommunicationInCommunication_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(ComponentInComponent_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(DataTypePackageInPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(FunctionPackageInDomain_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(FunctionPackageInPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(FunctionInPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(SubsystemInDomain_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(SubsystemInSubsystem_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(EePackageInPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(ExternalEntityInModel_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(ExternalEntityInPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(SpecificationPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(PackageInPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
		pkgs = collectAllElementsOfType(SatisfactionInComponentPackage_c.class);
		assertEquals("Found specialized packages after generic package upgrade",
				pkgs.length, 0);
	}
	
	private NonRootModelElement[] collectAllElementsOfType(Class<?> type) {
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		Ooaofooa[] roots = Ooaofooa.getInstancesUnderSystem(m_sys.getName());
		for(Ooaofooa root : roots) {
			InstanceList instances = root.getInstanceList(type);
			for(Object element : instances) {
				if(!((NonRootModelElement) element).isProxy()) {
					elements.add((NonRootModelElement) element);
				} else {
					if (((NonRootModelElement) element).isReferenced()) {
						elements.add((NonRootModelElement) element);
					}
					if (PersistenceManager.getHierarchyMetaData().getParent(
							(NonRootModelElement) element) != null) {
						elements.add((NonRootModelElement) element);
					}
				}
			}
		}
		return elements.toArray(new NonRootModelElement[elements.size()]);
	}

}
