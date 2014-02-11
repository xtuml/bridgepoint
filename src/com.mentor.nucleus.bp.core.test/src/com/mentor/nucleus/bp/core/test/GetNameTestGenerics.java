//========================================================================
//
//File:      $RCSfile: GetNameTestGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:27 $
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

import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOneSide_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOtherSide_c;
import com.mentor.nucleus.bp.core.ClassAsDerivedOneSide_c;
import com.mentor.nucleus.bp.core.ClassAsDerivedOtherSide_c;
import com.mentor.nucleus.bp.core.ClassAsLink_c;
import com.mentor.nucleus.bp.core.ClassAsSimpleFormalizer_c;
import com.mentor.nucleus.bp.core.ClassAsSimpleParticipant_c;
import com.mentor.nucleus.bp.core.ClassAsSubtype_c;
import com.mentor.nucleus.bp.core.ClassAsSupertype_c;
import com.mentor.nucleus.bp.core.ClassIdentifier_c;
import com.mentor.nucleus.bp.core.ClassInAssociation_c;
import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DerivedAssociation_c;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.NoEventTransition_c;
import com.mentor.nucleus.bp.core.NonLocalEvent_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.PolymorphicEvent_c;
import com.mentor.nucleus.bp.core.ReferentialAttribute_c;
import com.mentor.nucleus.bp.core.ReferredToClassInAssoc_c;
import com.mentor.nucleus.bp.core.ReferringClassInAssoc_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.test.TestUtil;

public class GetNameTestGenerics extends CoreTest {

	static boolean initialized = false;

	public GetNameTestGenerics(String name) {
		super("Default Project", name);

	}

	protected void setUp() throws Exception {
		super.setUp();
		if (!initialized) {

			loadProject("testRename1");
			initialized = true;
			Display d = Display.getCurrent();
			while (d.readAndDispatch());

		}
	}

	public void testActionGetName() throws Exception {
		//      - return ->SM_AH[R514]->SM_MOAH[R513]->SM_STATE[R511].Name

		Action_c act = Action_c.ActionInstance(modelRoot);
		String result = act.Get_name();
		assertEquals("State Action", result);
	}
	public void testClassAsAssociatedOneSideGetName() throws Exception {
		//      - return ->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201].Name
		ClassAsAssociatedOneSide_c aone = ClassAsAssociatedOneSide_c
				.ClassAsAssociatedOneSideInstance(modelRoot);
		String result = aone.Get_name();
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c
				.getOneR_OIROnR203(ReferredToClassInAssoc_c
						.getOneR_RTOOnR204(aone)));
		assertEquals(obj.getName(), result);
	}
	public void testClassAsAssociatedOtherSideGetName() throws Exception {
		//      - return ->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201].Name
		ClassAsAssociatedOtherSide_c aoth = ClassAsAssociatedOtherSide_c
				.ClassAsAssociatedOtherSideInstance(modelRoot);
		String result = aoth.Get_name();
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c
				.getOneR_OIROnR203(ReferredToClassInAssoc_c
						.getOneR_RTOOnR204(aoth)));
		assertEquals(obj.getName(), result);
	}
	public void testClassAsSimpleParticipantGetName() throws Exception {
		//      - return ->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201].Name
		ClassAsSimpleParticipant_c part = ClassAsSimpleParticipant_c
				.ClassAsSimpleParticipantInstance(modelRoot);
		String result = part.Get_name();
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c
				.getOneR_OIROnR203(ReferredToClassInAssoc_c
						.getOneR_RTOOnR204(part)));
		assertEquals(obj.getName(), result);
	}
	public void testClassAsSupertypeGetName() throws Exception {
		//      - return ->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201].Name
		ClassAsSupertype_c sup = ClassAsSupertype_c
				.ClassAsSupertypeInstance(modelRoot);
		String result = sup.Get_name();
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c
				.getOneR_OIROnR203(ReferredToClassInAssoc_c
						.getOneR_RTOOnR204(sup)));
		assertEquals(obj.getName(), result);
	}

	public void testClassAsDerivedOneSideGetName() throws Exception {
		//      - return ->R_OIR[R203]->O_OBJ[R201].Name
		ClassAsDerivedOneSide_c cone = ClassAsDerivedOneSide_c
				.ClassAsDerivedOneSideInstance(modelRoot);
		String result = cone.Get_name();
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c
				.getOneR_OIROnR203(cone));
		assertEquals(obj.getName(), result);
	}

	public void testClassAsDerivedOtherSideGetName() throws Exception {
		//      - return ->R_OIR[R203]->O_OBJ[R201].Name
		ClassAsDerivedOtherSide_c coth = ClassAsDerivedOtherSide_c
				.ClassAsDerivedOtherSideInstance(modelRoot);
		String result = coth.Get_name();
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c
				.getOneR_OIROnR203(coth));
		assertEquals(obj.getName(), result);
	}

	public void testClassAsLinkGetName() throws Exception {
		//      - return ->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201].Name
		ClassAsLink_c link = ClassAsLink_c.ClassAsLinkInstance(modelRoot);
		String result = link.Get_name();
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c
				.getOneR_OIROnR203(ReferringClassInAssoc_c
						.getOneR_RGOOnR205(link)));
		assertEquals(obj.getName(), result);
	}

	public void testClassAsSimpleFormalizerGetName() throws Exception {
		//      - return ->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201].Name
		ClassAsSimpleFormalizer_c form = ClassAsSimpleFormalizer_c
				.ClassAsSimpleFormalizerInstance(modelRoot);
		String result = form.Get_name();
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c
				.getOneR_OIROnR203(ReferringClassInAssoc_c
						.getOneR_RGOOnR205(form)));
		assertEquals(obj.getName(), result);
	}

	public void testClassAsSubtypeGetName() throws Exception {
		//      - return ->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201].Name
		ClassAsSubtype_c sub = ClassAsSubtype_c
				.ClassAsSubtypeInstance(modelRoot);
		String result = sub.Get_name();
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c
				.getOneR_OIROnR203(ReferringClassInAssoc_c
						.getOneR_RGOOnR205(sub)));
		assertEquals(obj.getName(), result);
	}

	public void testClassIdentifierGetName() throws Exception {
		//  - return "*" + INTEGER_TO_STRING(Oid_ID+1)
		ClassIdentifier_c oid = ClassIdentifier_c
				.ClassIdentifierInstance(modelRoot);
		String result = oid.Get_name();
		assertEquals("*" + String.valueOf(oid.getOid_id() + 1), result);
	}

	public void testCoreDataTypeGetName() throws Exception {
		//        - return ->S_DT[R17].Name
		CoreDataType_c cdt = CoreDataType_c.CoreDataTypeInstance(Ooaofooa.getDefaultInstance());
		String result = cdt.Get_name();
		DataType_c dt = DataType_c.getOneS_DTOnR17(cdt);
		assertEquals(dt.getName(), result);
	}

	public void testDerivedAssociationGetName() throws Exception {
		//      - return ->R_REL[R206].get_name()
		DerivedAssociation_c da = DerivedAssociation_c
				.DerivedAssociationInstance(modelRoot);
		String result = da.Get_name();
		Association_c rel = Association_c.getOneR_RELOnR206(da);
		assertEquals(rel.Get_name(), result);
	}

	private class findModelClass implements ClassQueryInterface_c {
		findModelClass(String p_kl) {
			m_kl = p_kl;
		}
		private String m_kl;
		public boolean evaluate(Object inst) {
			ModelClass_c selected = (ModelClass_c) inst;
			return selected.getKey_lett().equals(m_kl);//$NON-NLS-1$
		}
	}
	private class findEvent implements ClassQueryInterface_c {
		findEvent(String p_meaning) {
			m_meaning = p_meaning;
		}
		private String m_meaning;
		public boolean evaluate(Object inst) {
			StateMachineEvent_c selected = (StateMachineEvent_c) inst;
			return selected.getMning().equals(m_meaning);//$NON-NLS-1$
		}
	}
	private String validateSuppData(String kl, String meaning) {
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new findModelClass(kl));
		StateMachineEvent_c[] evt_set = StateMachineEvent_c
				.StateMachineEventInstances(modelRoot, new findEvent(meaning));
		StateMachineEvent_c evt = null;
		for (int i = 0; i < evt_set.length; ++i) {
			ModelClass_c temp = ModelClass_c
					.getOneO_OBJOnR518(InstanceStateMachine_c
							.getOneSM_ISMOnR517(StateMachine_c
									.getOneSM_SMOnR502(evt_set[i])));
			if (temp == mc) {
				evt = evt_set[i];
				break;
			}
		}
		NonLocalEvent_c nlEv = NonLocalEvent_c.getOneSM_NLEVTOnR526(SemEvent_c
				.getOneSM_SEVTOnR525(evt));
		if (nlEv != null) {
			evt = StateMachineEvent_c.getOneSM_EVTOnR525(PolymorphicEvent_c
					.getOneSM_PEVTOnR527(nlEv));
		}
		StateMachineEventDataItem_c[] evdis = StateMachineEventDataItem_c
				.getManySM_EVTDIsOnR532(evt);
		String result = "(";
		String sep = "";
		for (int i = 0; i < evdis.length; i++) {
			result = result + sep + evdis[i].getName();
			sep = ",";
		}
		result = result + ")";
		return result;
	}
	public void testEventSupplementalDataGetNameLocalEmpty() throws Exception {
		//      - return ->SM_EVTDI[R532] list separated by commas
		String result = validateSuppData("B", "local");
		assertEquals("()", result);
	}

	public void testEventSupplementalDataGetNameLocalOne() throws Exception {
		//      - return ->SM_EVTDI[R532] list separated by commas
		String result = validateSuppData("B", "local_one");
		assertEquals("(test)", result);
	}
	public void testEventSupplementalDataGetNameLocalTwo() throws Exception {
		//      - return ->SM_EVTDI[R532] list separated by commas
		String result = validateSuppData("B", "local_two");
		assertEquals("(test,test2)", result);
	}
	public void testEventSupplementalDataGetNameNonLocalEmpty()
			throws Exception {
		//      - return ->SM_EVTDI[R532] list separated by commas
		String result = validateSuppData("B", "test");
		assertEquals("()", result);
	}

	public void testEventSupplementalDataGetNameNonLocalOne() throws Exception {
		//      - return ->SM_EVTDI[R532] list separated by commas
		String result = validateSuppData("B", "test_one");
		assertEquals("(test1)", result);
	}
	public void testEventSupplementalDataGetNameNonLocalTwo() throws Exception {
		//      - return ->SM_EVTDI[R532] list separated by commas
		String result = validateSuppData("B", "test_two");
		assertEquals("(test1,test2)", result);
	}
	public void testEventSupplementalDataGetNamePolyEmpty() throws Exception {
		//      - return ->SM_EVTDI[R532] list separated by commas
		String result = validateSuppData("C", "test");
		assertEquals("()", result);
	}

	public void testEventSupplementalDataGetNamePolyOne() throws Exception {
		//      - return ->SM_EVTDI[R532] list separated by commas
		String result = validateSuppData("C", "test_one");
		assertEquals("(test1)", result);
	}
	public void testEventSupplementalDataGetNamePolyTwo() throws Exception {
		//      - return ->SM_EVTDI[R522] list separated by commas
		String result = validateSuppData("C", "test_two");
		assertEquals("(test1,test2)", result);
	}

	public void testImportedClassGetName() throws Exception {
		//      - return ->O_OBJ[R101].Name
		ImportedClass_c ic = ImportedClass_c.ImportedClassInstance(modelRoot);
		String result = ic.Get_name();
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR101(ic);
		assertEquals(obj.getName(), result);
	}

	public void testNoEventTransitionGetName() throws Exception {
		//      - return ->SM_STATE[R508].Name + "/" + ->SM_TXN[R507]->SM_STATE[R506].Name
		NoEventTransition_c net = NoEventTransition_c
				.NoEventTransitionInstance(modelRoot);
		String result = net.Get_name();
		StateMachineState_c initial_state = StateMachineState_c
				.getOneSM_STATEOnR508(net);

		StateMachineState_c final_state = StateMachineState_c
				.getOneSM_STATEOnR506(Transition_c.getOneSM_TXNOnR507(net));

		assertEquals(initial_state.getName() + "/" + final_state.getName(),
				result);
	}

	public void testNonLocalEventGetName() throws Exception {
		//      - return ->SM_SEVT[R526]->SM_EVT[R525].get_name()
		NonLocalEvent_c nle = NonLocalEvent_c.NonLocalEventInstance(modelRoot);
		String result = nle.Get_name();
		StateMachineEvent_c evt = StateMachineEvent_c
				.getOneSM_EVTOnR525(SemEvent_c.getOneSM_SEVTOnR526(nle));
		assertEquals(evt.Get_name(), result);
	}

	public void testReferentialAttributeGetName() throws Exception {
		ReferentialAttribute_c ra = ReferentialAttribute_c
				.ReferentialAttributeInstance(modelRoot);
		String result = ra.Get_name();
		String expected = "sup_id (R103)";
		assertEquals(expected, result);
	}

	public void testReferentialAttributeReferringToRefAttrGetName()
			throws Exception {
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new findModelClass("D"));
		Attribute_c[] attr_set = Attribute_c.getManyO_ATTRsOnR102(mc);
		assertEquals("sup_id", attr_set[0].getName());
		assertEquals("id", attr_set[1].getName());
		assertEquals("testid", attr_set[2].getName());
		ModelClass_c base_mc = ModelClass_c.ModelClassInstance(modelRoot,
				new findModelClass("C"));
		Attribute_c base_attr = Attribute_c.getOneO_ATTROnR102(base_mc);
		assertEquals("id", base_attr.getName());
		base_attr.setRoot_nam("idx");
		assertEquals("sup_idx", attr_set[0].getName());
		assertEquals("idx", attr_set[1].getName());
		assertEquals("testidx", attr_set[2].getName());
		ModelClass_c e_mc = ModelClass_c.ModelClassInstance(modelRoot,
				new findModelClass("E"));
		Attribute_c[] e_attr_set = Attribute_c.getManyO_ATTRsOnR102(e_mc);
		assertEquals("sup_idx", e_attr_set[0].getName());
		assertEquals("testidx", e_attr_set[1].getName());
		ModelClass_c b_mc = ModelClass_c.ModelClassInstance(modelRoot,
				new findModelClass("B"));
		Attribute_c[] b_attr_set = Attribute_c.getManyO_ATTRsOnR102(b_mc);
		assertEquals("sup_idx", b_attr_set[1].getName());
		b_attr_set[1].setPrefix("supx_");
		assertEquals("supx_idx", attr_set[0].getName());
		assertEquals("idx", attr_set[1].getName());
		assertEquals("testidx", attr_set[2].getName());
		assertEquals("supx_idx", e_attr_set[0].getName());
		assertEquals("testidx", e_attr_set[1].getName());
	}

}
