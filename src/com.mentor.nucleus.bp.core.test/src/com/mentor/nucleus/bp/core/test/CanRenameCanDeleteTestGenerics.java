//=====================================================================
//
//File:      $RCSfile: CanRenameCanDeleteTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:24 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.runtime.NullProgressMonitor;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;

public class CanRenameCanDeleteTestGenerics extends CoreTest {

	static boolean isDone = false;

	static ModelClass_c testClass = null; //Class having test Attributes

	private static boolean initialized = false;
	protected void setUp() throws Exception {
		super.setUp();
		if (!initialized) {

			loadProject("testCanRenameDelete");
			initialized = true;
		}
	}
	protected void tearDown() throws Exception {
		if (isDone) {
			if (modelRoot != null) {
				modelRoot.clearDatabase(new NullProgressMonitor());
				//initialized = false;
			}

		}
		super.tearDown();
	}

	public CanRenameCanDeleteTestGenerics() {
		super();
	}

	public void testCanRenameAndCanDeleteUserDatatype() {

		class findUserDatatype implements ClassQueryInterface_c {
			findUserDatatype(String aname) {
				name = aname;
			}

			private String name;

			public boolean evaluate(Object candidate) {
				UserDataType_c selected = (UserDataType_c) candidate;
				return selected.Get_name().equals(name);
			}
		}

		UserDataType_c udt1 = UserDataType_c.getOneS_UDTOnR17(DataType_c
				.getManyS_DTsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getManyEP_PKGsOnR1405(m_sys))),
				new findUserDatatype("test")); //$NON-NLS-1$

		assertTrue(udt1.Canrename());
		assertTrue(udt1.Candelete());

	}

	private class findAttributeUsingName implements ClassQueryInterface_c {

		findAttributeUsingName(String aname) {
			name = aname;
		}

		private String name;

		public boolean evaluate(Object candidate) {
			Attribute_c selected = (Attribute_c) candidate;
			return selected.getName().equals(name);
		}
	}

	public void testCanRenameAndCanDeleteReferentialAttribute() {
		class findModelClassUsingName implements ClassQueryInterface_c {

			findModelClassUsingName(String aname) {
				name = aname;
			}

			private String name;

			public boolean evaluate(Object candidate) {
				ModelClass_c selected = (ModelClass_c) candidate;
				return selected.getName().equals(name);
			}
		}

		class findPackageUsingName implements ClassQueryInterface_c {
			findPackageUsingName(String aname) {
				name = aname;
			}

			private String name;

			public boolean evaluate(Object candidate) {
				Package_c selected = (Package_c) candidate;
				return selected.getName().equals(name);
			}
		}

		testClass = ModelClass_c
				.getOneO_OBJOnR8001(
						PackageableElement_c.getManyPE_PEsOnR8000(Package_c
								.PackageInstance(modelRoot,
										new findPackageUsingName(
												"CanRenameCanDelete Data"))), new findModelClassUsingName("Class D"));//$NON-NLS-1$ //$NON-NLS-2$
		Attribute_c attr = Attribute_c.getOneO_ATTROnR102(testClass,
				new findAttributeUsingName("id")); //$NON-NLS-1$ 

		assertFalse(attr.Canrename());
		assertFalse(attr.Candelete());

		Attribute_c attr_ren = Attribute_c.getOneO_ATTROnR102(testClass,
				new findAttributeUsingName("renamedRefId")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		assertTrue(attr_ren.Canrename());
		assertFalse(attr_ren.Candelete());
	}

	public void testCanRenameAndCanDeleteCurrentStateAttribute() {
		Attribute_c attr[] = Attribute_c.getManyO_ATTRsOnR102(testClass,
				new findAttributeUsingName("current_state")); //$NON-NLS-1$

		Attribute_c deleteable = null;
		assertNotNull(attr);
		for (int i = 0; i < attr.length; i++) {
			DataType_c dt = DataType_c.getOneS_DTOnR114(attr[i]);
			if (dt.getName().equals("state<State_Model>")) { //$NON-NLS-1$
				assertFalse(attr[i].Candelete());
				assertFalse(attr[i].Canrename());
			} else {
				deleteable = attr[i];
				assertTrue(attr[i].Candelete());
				assertTrue(attr[i].Canrename());
			}
		}

		//Getting datatype of deleteable current_state
		DataType_c adt = DataType_c.getOneS_DTOnR114(deleteable);
		adt.setName("state<State_Model>"); //$NON-NLS-1$

		//Now all attributes with current_state are deleteable.
		for (int i = 0; i < attr.length; i++) {
			assertTrue(attr[i].Candelete());
			assertTrue(attr[i].Canrename());
		}

		isDone = true;
	}
}