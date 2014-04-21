//========================================================================
//
// File: IntegrityIssueTests.java
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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class IntegrityIssueTests extends BaseTest {

	public void testProblemMarkers() throws Exception {
		loadProject("ModelIntegrityIssueTest");
		ModelClass_c[] classes = ModelClass_c
				.getManyO_OBJsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getOneEP_PKGOnR1401(m_sys)));
		for (ModelClass_c clazz : classes) {
			TestUtil.executeInTransaction(clazz, "setKey_lett",
					new Object[] { "KEY" });
		}
		// verify that a marker was created for each class
		for (ModelClass_c clazz : classes) {
			IMarker[] markers = clazz.getFile().findMarkers(IMarker.PROBLEM, true,
					IFile.DEPTH_INFINITE);
			assertTrue(
					"Did not find any problem markers for an integrity issue.",
					markers.length == 1);
		}
		Association_c[] assocs = Association_c
				.getManyR_RELsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getOneEP_PKGOnR1401(m_sys)));
		for (Association_c assoc : assocs) {
			TestUtil.executeInTransaction(assoc, "setNumb", new Object[] { 1 });
		}
		// verify that a marker was created for each association
		IMarker[] markers = assocs[0].getFile().findMarkers(IMarker.PROBLEM, true,
				IFile.DEPTH_INFINITE);
		assertTrue(
				"Did not find any problem markers for an integrity issue.",
				markers.length == 2);		
		IFileState[] history = assocs[0].getFile().getHistory(
				new NullProgressMonitor());
		assocs[0].getFile().setContents(history[0], IFile.FORCE,
				new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		// verify that the association markers have been removed
		markers = assocs[0].getFile().findMarkers(IMarker.PROBLEM, true,
				IFile.DEPTH_INFINITE);
		assertTrue(
				"Found remaining problem markers for an integrity issue.",
				markers.length == 0);		
	}
}
