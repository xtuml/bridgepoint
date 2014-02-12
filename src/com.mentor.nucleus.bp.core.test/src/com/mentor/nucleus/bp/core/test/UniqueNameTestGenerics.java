//=====================================================================
//
//File:      $RCSfile: UniqueNameTestGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:26 $
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
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class UniqueNameTestGenerics extends BaseTest {

	static DataTypePackage_c rootDpk = null;

	static boolean isDone = false;

	static ModelClass_c testClass = null; //Class having test Attributes

	private static boolean firstTime = true;
	private static boolean initialized = false;
	private static boolean oldValue;

	protected void setUp() throws Exception {
		super.setUp();
		if (firstTime) {
			setupProject("uniqueNameTest");
			if (!initialized) {
				loadProject("testTransaction");
				initialized = true;
                PersistableModelComponent pmc = m_sys.getPersistableComponent();
                pmc.loadComponentAndChildren(new NullProgressMonitor());
                
                Display d = Display.getDefault();
	            while (d.readAndDispatch());
			}
			firstTime = false;
		}
		// persistence needs to be enabled for
		// the tests contained in this file
		if (!firstTime) {
			oldValue = modelRoot.persistEnabled();
			Ooaofooa.setPersistEnabled(true);
		}
	}

	protected void tearDown() throws Exception {
		if (isDone) {
			if (modelRoot != null) {
				modelRoot.clearDatabase(new NullProgressMonitor());
			}

		}
		Ooaofooa.setPersistEnabled(oldValue);
		super.tearDown();
	}

	public UniqueNameTestGenerics() {
		super();
	}

	/**
	 * Issue 2437
	 * @throws Exception
	 */
	public void testUniqueSubsystemName() throws Exception {

		Package_c dom = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("testTransaction"));
		Package_c[] sss1 = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(dom));

		TransactionManager tm = dom.getTransactionManager();

		Transaction t = tm.startTransaction("Creating SS", Ooaofooa
				.getDefaultInstance());
		dom.Newpackage();
		tm.endTransaction(t);

		Package_c[] sss2 = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(dom));

		assertEquals(sss1.length + 1, sss2.length);
		assertEquals("Unnamed Package", sss2[sss2.length - 1].getName());

		t = tm.startTransaction("Creating SS", modelRoot);
		dom.Newpackage();
		tm.endTransaction(t);

		sss2 = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(dom));

		assertEquals(sss1.length + 2, sss2.length);
		assertEquals("Unnamed Package-1", sss2[sss2.length - 1].getName());
	}

	public void testUniqueModelClassName() throws Exception {
		//test unique model class name
		ModelClass_c[] mcs1 = ModelClass_c.ModelClassInstances(modelRoot);
		Package_c[] sss = Package_c.PackageInstances(modelRoot);
		Package_c ss = sss[sss.length - 1];

		TransactionManager tm = m_sys.getTransactionManager();
		Transaction t = tm.startTransaction("Creating ModelClass", Ooaofooa
				.getDefaultInstance());
		ss.Newclass();
		tm.endTransaction(t);

		ModelClass_c[] mcs2 = ModelClass_c.ModelClassInstances(modelRoot, null,
				false);

		assertEquals(mcs1.length + 1, mcs2.length);
		assertEquals("Unnamed Class", mcs2[mcs2.length - 1].getName());

		t = tm.startTransaction("Creating ModelClass", modelRoot);
		ss.Newclass();
		tm.endTransaction(t);

		mcs2 = ModelClass_c.ModelClassInstances(modelRoot, null, false);

		assertEquals(mcs1.length + 2, mcs2.length);
		assertEquals("Unnamed Class-1", mcs2[mcs2.length - 1].getName());

		isDone = true;
	}
}