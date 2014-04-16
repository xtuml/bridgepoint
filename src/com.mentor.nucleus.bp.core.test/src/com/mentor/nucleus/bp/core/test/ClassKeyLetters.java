//========================================================================
//
// File: ClassKeyLetters.java
//
// Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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

package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class ClassKeyLetters extends BaseTest {

	public void testClassKeyLetterSynchronization() throws Exception {
		IProject project = TestingUtilities
				.createProject("testClassKeyLetterSynchronization");
		m_sys = getSystemModel(project.getName());
		TestUtil.executeInTransaction(m_sys, "Newpackage", new Object[0]);
		Package_c testPackage = Package_c.getOneEP_PKGOnR1401(m_sys);
		TestUtil.executeInTransaction(testPackage, "Newclass", new Object[0]);
		ModelClass_c clazz = ModelClass_c
				.getOneO_OBJOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(testPackage));
		assertTrue("", clazz.getKey_lett().equals("UnnamedClass"));
		TreeViewer meTreeViewer = getMETreeViewer();
		meTreeViewer.setSelection(new StructuredSelection(clazz));
		ExplorerUtil.renameItem("Test");
		assertTrue("Class name was not changed for test.", clazz.getName()
				.equals("Test"));
		assertTrue("Class key letters were not changed with a rename.", clazz
				.getKey_lett().equals("Test"));
		TestUtil.executeInTransaction(clazz, "setKey_lett",
				new Object[]{"KeyLett"});
		ExplorerUtil.renameItem("AnotherName");
		assertTrue("Class name was not changed for test.", clazz.getName()
				.equals("AnotherName"));
		assertTrue(
				"Class key letters were changed with a rename after modified by the user.",
				clazz.getKey_lett().equals("KeyLett"));
	}

}
