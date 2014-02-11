//========================================================================
//
//File:      $RCSfile: DerivedAttributeTestGenerics.java,v $
//Version:   $Revision: 1.5 $
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

import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.AttributeReferenceInClass_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.ClassIdentifierAttribute_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.ExternalEntityEvent_c;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.ReferentialAttribute_c;
import com.mentor.nucleus.bp.core.ReferredToIdentifierAttribute_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.test.TestUtil;

public class DerivedAttributeTestGenerics extends CoreTest {

	static boolean firstTime = true;

	public DerivedAttributeTestGenerics(String name) {
		super("Default Project", name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (firstTime) {
			Display d = Display.getCurrent();
			while (d.readAndDispatch());
			firstTime = false;
		}
	}

	public void testImportedObjectObj_Name() throws Exception {
		ImportedClass_c[] ic = ImportedClass_c
				.ImportedClassInstances(modelRoot);
		String result = ic[0].getObj_name();
		// changed by rename test
		assertEquals("Test Class", result);
	}
	public void testImportedObjectObj_KL() throws Exception {
		ImportedClass_c ic = ImportedClass_c.ImportedClassInstance(modelRoot);
		String result = ic.getObj_kl();
		assertEquals("T", result);
	}
	private class findUnassignedImportedClass implements ClassQueryInterface_c {
		public boolean evaluate(Object inst) {
			ImportedClass_c selected = (ImportedClass_c) inst;
			return selected.getObj_id().equals(IdAssigner.NULL_UUID);
		}
	}
	public void testUnassignedImportedObjectObj_Name() throws Exception {

		ImportedClass_c ic = ImportedClass_c.ImportedClassInstance(modelRoot,
				new findUnassignedImportedClass());
		String result = ic.getObj_name();
		assertEquals("Unassigned Imported Class", result);
	}
	public void testUnassignedImportedObjectObj_KL() throws Exception {
		ImportedClass_c ic = ImportedClass_c.ImportedClassInstance(modelRoot,
				new findUnassignedImportedClass());
		String result = ic.getObj_kl();
		assertEquals("ORPH", result);
	}
	public void testBaseAttributeName() throws Exception {
		// Pfx_Mode = "No Prefix", "Prefix", "Referred To Prefix" 

		BaseAttribute_c battr = BaseAttribute_c
				.BaseAttributeInstance(modelRoot);
		Attribute_c attr = Attribute_c.getOneO_ATTROnR106(battr);
		attr.setPfx_mode(0);
		String result = attr.getName();
		assertEquals(attr.getRoot_nam(), result);
		attr.setPfx_mode(1);
		attr.setPrefix("pre_");
		result = attr.getName();
		assertEquals("pre_" + attr.getRoot_nam(), result);
		attr.setPfx_mode(2);
		result = attr.getName();
		assertEquals("Orphaned", result);
	}
	public void testReferentialAttributeName() throws Exception {
		// Ref_mode = "Local Attribute", "Referred To Attribute"

		ReferentialAttribute_c rattr = ReferentialAttribute_c
				.ReferentialAttributeInstance(modelRoot);
		rattr.setRef_mode(0);
		Attribute_c attr = Attribute_c.getOneO_ATTROnR106(rattr);
		attr.setPrefix("local_");
		Attribute_c rattr_root = Attribute_c
				.getOneO_ATTROnR105(ClassIdentifierAttribute_c
						.getOneO_OIDAOnR110(ReferredToIdentifierAttribute_c
								.getOneO_RTIDAOnR111(AttributeReferenceInClass_c
										.getOneO_REFOnR108(rattr))));
		rattr_root.setPrefix("ref_");
		rattr_root.setRoot_nam("test");
		rattr_root.setPfx_mode(1);

		attr.setPfx_mode(0);
		String result = attr.getName();
		assertEquals("id", result);
		attr.setPfx_mode(1);
		result = attr.getName();
		assertEquals("local_id", result);
		attr.setPfx_mode(2);
		result = attr.getName();
		assertEquals("ref_id", result);

		rattr.setRef_mode(1);
		attr.setPfx_mode(0);
		result = attr.getName();
		assertEquals("test", result);
		attr.setPfx_mode(1);
		result = attr.getName();
		assertEquals("local_test", result);
		attr.setPfx_mode(2);
		result = attr.getName();
		assertEquals("ref_test", result);
	}
	public void testStateMachineEventDrv_LblInstance() throws Exception {
		// Drv_lbl = "Class Keyletters", "Custom Keyletters"
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.InstanceStateMachineInstance(modelRoot, new ISM_by_name_c(
						"Test Class"));
		StateMachineEvent_c evt = StateMachineEvent_c
				.getOneSM_EVTOnR502(StateMachine_c.getOneSM_SMOnR517(ism));
		evt.setIs_lbl_u(0);
		String result = evt.getDrv_lbl();
		assertEquals("T1", result);
		evt.setIs_lbl_u(1);
		evt.setUnq_lbl("X");
		result = evt.getDrv_lbl();
		assertEquals("X1", result);
	}
	public void testStateMachineEventDrv_LblClass() throws Exception {
		// Drv_lbl = "Class Keyletters", "Custom Keyletters"
		ClassStateMachine_c csm = ClassStateMachine_c
				.ClassStateMachineInstance(modelRoot, new CSM_by_name_c(
						"Test Class"));
		StateMachineEvent_c evt = StateMachineEvent_c
				.getOneSM_EVTOnR502(StateMachine_c.getOneSM_SMOnR517(csm));
		evt.setIs_lbl_u(0);
		String result = evt.getDrv_lbl();
		assertEquals("T_A1", result);
		evt.setIs_lbl_u(1);
		evt.setUnq_lbl("X");
		result = evt.getDrv_lbl();
		assertEquals("X1", result);
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
}