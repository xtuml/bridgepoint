package com.mentor.nucleus.bp.core.test;

import com.mentor.nucleus.bp.core.AcceptEventAction_c;
import com.mentor.nucleus.bp.core.AcceptEvent_c;
import com.mentor.nucleus.bp.core.AcceptTimeEventAction_c;
import com.mentor.nucleus.bp.core.ActionNode_c;
import com.mentor.nucleus.bp.core.ActivityDiagramAction_c;
import com.mentor.nucleus.bp.core.ActivityFinalNode_c;
import com.mentor.nucleus.bp.core.ActivityNode_c;
import com.mentor.nucleus.bp.core.ActorParticipant_c;
import com.mentor.nucleus.bp.core.ClassInstanceParticipant_c;
import com.mentor.nucleus.bp.core.ClassParticipant_c;
import com.mentor.nucleus.bp.core.ComponentParticipant_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ConstantSpecification_c;
import com.mentor.nucleus.bp.core.ControlNode_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DecisionMergeNode_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.ExternalEntityParticipant_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FlowFinalNode_c;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.InitialNode_c;
import com.mentor.nucleus.bp.core.InteractionParticipant_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.ObjectNode_c;
import com.mentor.nucleus.bp.core.PackageParticipant_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SendSignal_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.UseCaseParticipant_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.ElementInModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class PackageReconciliationTests extends BaseTest {

	public void testReconciliationUnderPackage() {
		// create the test package
		m_sys.Newpackage();
		Package_c[] packages = Package_c.getManyEP_PKGsOnR1405(m_sys);
		Package_c testPackage = packages[packages.length - 1];
		// create one of every element allowed
		testPackage.Newaccepteventaction();
		testPackage.Newaccepttimeeventaction();
		testPackage.Newactivityfinalnode();
		testPackage.Newactor();
		testPackage.Newclass();
		testPackage.Newclassinstance();
		testPackage.Newclassparticipant();
		testPackage.Newcomponent();
		testPackage.Newcomponentparticipant();
		testPackage.Newconstantspecification();
		testPackage.Newdatatype();
		testPackage.Newdecisionmergenode();
		testPackage.Newenumeration();
		testPackage.Newexternalentity();
		testPackage.Newexternalentityparticipant();
		testPackage.Newflowfinalnode();
		testPackage.Newgenericaction();
		testPackage.Newiclass();
		testPackage.Newimportedcomponent();
		testPackage.Newinitialnode();
		testPackage.Newinterface();
		testPackage.Newobjectnode();
		testPackage.Newpackage();
		testPackage.Newpackageparticipant();
		testPackage.Newsendsignalaction();
		testPackage.Newstructureddatatype();
		testPackage.Newusecase();
		// create a destination package
		m_sys.Newpackage();
		packages = Package_c.getManyEP_PKGsOnR1405(m_sys);
		final Package_c destinationPackage = packages[packages.length - 1];
		// copy all elements from the test package
		Selection.getInstance().clear();
		addElementsToSelection(testPackage);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		// now paste the copied data into the destination
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(destinationPackage);
		UITestingUtilities.pasteClipboardContentsInExplorer();
		// now make sure there is one GD_GE for every item
		// copied
		ElementSpecification_c[] specs = getElementSpecifications();
		for(int i = 0; i < specs.length; i++) {
			GraphicalElement_c element = GraphicalElement_c.getOneGD_GEOnR10(specs[i], new ClassQueryInterface_c() {
				
				@Override
				public boolean evaluate(Object candidate) {
					GraphicalElement_c test = (GraphicalElement_c) candidate;
					Model_c model = Model_c.getOneGD_MDOnR1(test);
					return model.getRepresents() == destinationPackage;
				}
			});
			assertNotNull(
					"A graphical element was not created for element type: "
							+ specs[i].getRepresents().getSimpleName(), element);
		}
	}
	private ElementSpecification_c[] getElementSpecifications() {
		final ModelSpecification_c packageSpec = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((ModelSpecification_c) candidate)
										.getRepresents() == Package_c.class;
							}
						});
		ElementSpecification_c[] specs = ElementSpecification_c
				.ElementSpecificationInstances(Ooaofgraphics
						.getDefaultInstance(), new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						ElementSpecification_c spec = (ElementSpecification_c) candidate;
						ModelSpecification_c[] modelSpecs = ModelSpecification_c
								.getManyGD_MSsOnR11(ElementInModelSpecification_c
										.getManyGD_EMSsOnR11(spec));
						return modelSpecificationsContains(modelSpecs)
								&& ((ElementSpecification_c) candidate)
										.getSymboltype().equals("shape");
					}

					private boolean modelSpecificationsContains(
							ModelSpecification_c[] specs) {
						for(int i = 0; i < specs.length; i++) {
							if(specs[i] == packageSpec) {
								return true;
							}
						}
						return false;
					}
				});
		return specs;
	}
	private void addElementsToSelection(Package_c testPackage) {
		PackageableElement_c[] elements = PackageableElement_c
				.getManyPE_PEsOnR8000(testPackage);
		for (int i = 0; i < elements.length; i++) {
			AcceptEventAction_c aea = AcceptEventAction_c
					.getOneA_AEAOnR1112(AcceptEvent_c
							.getOneA_AEOnR1107(ActionNode_c
									.getOneA_ACTOnR1105(ActivityNode_c
											.getOneA_NOnR8001(elements[i]))));
			if (aea != null) {
				Selection.getInstance().addToSelection(aea);
			}
			AcceptTimeEventAction_c atea = AcceptTimeEventAction_c
					.getOneA_ATEOnR1112(AcceptEvent_c
							.getOneA_AEOnR1107(ActionNode_c
									.getOneA_ACTOnR1105(ActivityNode_c
											.getOneA_NOnR8001(elements[i]))));
			if (atea != null) {
				Selection.getInstance().addToSelection(atea);
			}
			ActivityFinalNode_c afn = ActivityFinalNode_c
					.getOneA_AFOnR1106(ControlNode_c
							.getOneA_CTLOnR1105(ActivityNode_c
									.getOneA_NOnR8001(elements[i])));
			if (afn != null) {
				Selection.getInstance().addToSelection(afn);
			}
			ActorParticipant_c ap = ActorParticipant_c
					.getOneSQ_APOnR930(InteractionParticipant_c
							.getOneSQ_POnR8001(elements[i]));
			if (ap != null) {
				Selection.getInstance().addToSelection(ap);
			}
			ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR8001(elements[i]);
			if (clazz != null) {
				Selection.getInstance().addToSelection(clazz);
			}
			ClassInstanceParticipant_c cip = ClassInstanceParticipant_c
					.getOneSQ_CIPOnR930(InteractionParticipant_c
							.getOneSQ_POnR8001(elements[i]));
			if (cip != null) {
				Selection.getInstance().addToSelection(cip);
			}
			ClassParticipant_c cp = ClassParticipant_c
					.getOneSQ_CPOnR930(InteractionParticipant_c
							.getOneSQ_POnR8001(elements[i]));
			if (cp != null) {
				Selection.getInstance().addToSelection(cp);
			}
			Component_c comp = Component_c.getOneC_COnR8001(elements[i]);
			if (comp != null) {
				Selection.getInstance().addToSelection(comp);
			}
			ComponentParticipant_c cop = ComponentParticipant_c
					.getOneSQ_COPOnR930(InteractionParticipant_c
							.getOneSQ_POnR8001(elements[i]));
			if (cop != null) {
				Selection.getInstance().addToSelection(cop);
			}
			ConstantSpecification_c con = ConstantSpecification_c
					.getOneCNST_CSPOnR8001(elements[i]);
			if (con != null) {
				Selection.getInstance().addToSelection(con);
			}
			UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(DataType_c
					.getOneS_DTOnR8001(elements[i]));
			if (udt != null) {
				Selection.getInstance().addToSelection(udt);
			}
			DecisionMergeNode_c dmn = DecisionMergeNode_c
					.getOneA_DMOnR1106(ControlNode_c
							.getOneA_CTLOnR1105(ActivityNode_c
									.getOneA_NOnR8001(elements[i])));
			if (dmn != null) {
				Selection.getInstance().addToSelection(dmn);
			}
			EnumerationDataType_c edt = EnumerationDataType_c
					.getOneS_EDTOnR17(DataType_c.getOneS_DTOnR8001(elements[i]));
			if (edt != null) {
				Selection.getInstance().addToSelection(edt);
			}
			ExternalEntity_c ee = ExternalEntity_c
					.getOneS_EEOnR8001(elements[i]);
			if (ee != null) {
				Selection.getInstance().addToSelection(ee);
			}
			ExternalEntityParticipant_c eep = ExternalEntityParticipant_c
					.getOneSQ_EEPOnR930(InteractionParticipant_c
							.getOneSQ_POnR8001(elements[i]));
			if (eep != null) {
				Selection.getInstance().addToSelection(eep);
			}
			FlowFinalNode_c ffn = FlowFinalNode_c
					.getOneA_FFOnR1106(ControlNode_c
							.getOneA_CTLOnR1105(ActivityNode_c
									.getOneA_NOnR8001(elements[i])));
			if (ffn != null) {
				Selection.getInstance().addToSelection(ffn);
			}
			ActivityDiagramAction_c ada = ActivityDiagramAction_c
					.getOneA_GAOnR1107(ActionNode_c
							.getOneA_ACTOnR1105(ActivityNode_c
									.getOneA_NOnR8001(elements[i])));
			if (ada != null) {
				Selection.getInstance().addToSelection(ada);
			}
			ImportedClass_c ic = ImportedClass_c
					.getOneO_IOBJOnR8001(elements[i]);
			if (ic != null) {
				Selection.getInstance().addToSelection(ic);
			}
			ComponentReference_c cr = ComponentReference_c
					.getOneCL_ICOnR8001(elements[i]);
			if (cr != null) {
				Selection.getInstance().addToSelection(cr);
			}
			InitialNode_c in = InitialNode_c.getOneA_INIOnR1106(ControlNode_c
					.getOneA_CTLOnR1105(ActivityNode_c
							.getOneA_NOnR8001(elements[i])));
			if (in != null) {
				Selection.getInstance().addToSelection(in);
			}
			Interface_c iface = Interface_c.getOneC_IOnR8001(elements[i]);
			if (iface != null) {
				Selection.getInstance().addToSelection(iface);
			}
			ObjectNode_c on = ObjectNode_c.getOneA_OBJOnR1105(ActivityNode_c
					.getOneA_NOnR8001(elements[i]));
			if (on != null) {
				Selection.getInstance().addToSelection(on);
			}
			Package_c pkg = Package_c.getOneEP_PKGOnR8001(elements[i]);
			if (pkg != null) {
				Selection.getInstance().addToSelection(pkg);
			}
			PackageParticipant_c pp = PackageParticipant_c
					.getOneSQ_PPOnR930(InteractionParticipant_c
							.getOneSQ_POnR8001(elements[i]));
			if (pp != null) {
				Selection.getInstance().addToSelection(pp);
			}
			SendSignal_c ss = SendSignal_c.getOneA_SSOnR1107(ActionNode_c
					.getOneA_ACTOnR1105(ActivityNode_c
							.getOneA_NOnR8001(elements[i])));
			if (ss != null) {
				Selection.getInstance().addToSelection(ss);
			}
			StructuredDataType_c sdt = StructuredDataType_c
					.getOneS_SDTOnR17(DataType_c.getOneS_DTOnR8001(elements[i]));
			if (sdt != null) {
				Selection.getInstance().addToSelection(sdt);
			}
			UseCaseParticipant_c ucp = UseCaseParticipant_c
					.getOneIA_UCPOnR930(InteractionParticipant_c
							.getOneSQ_POnR8001(elements[i]));
			if (ucp != null) {
				Selection.getInstance().addToSelection(ucp);
			}
		}
	}

}
