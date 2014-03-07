package com.mentor.nucleus.bp.debug.engine;

import java.io.File;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.debug.test.VerifierTest;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.TestUtil;


//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

public class ExternalEntityTest extends VerifierTest {

	public ExternalEntityTest() {
		super("EE_Test");
	}

	public void testBasicEE()throws InterruptedException {
	    redirectOutput("EE_Test");
		executeModel();
		endRedirection();
		compareOutput("EE_Test");
	}
	
	public void testIsRealizedFlag() throws CoreException {
		loadProject("ExternalEntityRealizedTests");
		File checkFile = new File(getProject().getLocation().toString()
				+ "/bin/lib/LOGR.class");
		assertTrue(
				"This test requires that you build ExternalEntityRealizedTests in your workspace before it will succeed.",
				checkFile.exists());
		Component_c testComponent = Component_c.getOneC_COnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR1401(m_sys)),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"Component");
					}
				});
		assertNotNull(testComponent);
		DebugUITestUtilities.setLogActivityAndLaunchForElement(testComponent,
				getExplorerView().getTreeViewer().getTree().getMenu(),
				m_sys.getName());
		// execute the runTest function
		Function_c testFunction = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(testComponent))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("runTest");
			}
		});
		assertNotNull(testFunction);
		BPDebugUtils.executeElement(testFunction);
		String actualResults = DebugUITestUtilities.getConsoleText();
		File expectedResultsFile = new File(
				m_workspace_path
						+ "expected_results/verifier/ee_is_realized.result");
		String expectedResults = TestUtil.getTextFileContents(expectedResultsFile);
		actualResults = actualResults.trim();
		expectedResults = expectedResults.trim();
		assertEquals("Unexpected verifier output for isRealized values on EE.", expectedResults, actualResults);
		ExternalEntity_c testEE = ExternalEntity_c.getOneS_EEOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(testComponent))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((ExternalEntity_c) candidate).getName().equals("Logging");
			}
		});
		testEE.setIsrealized(false);
		BPDebugUtils.executeElement(testFunction);
		actualResults = DebugUITestUtilities.getConsoleText();
		expectedResultsFile = new File(
				m_workspace_path
						+ "expected_results/verifier/ee_is_not_realized.result");
		expectedResults = TestUtil.getTextFileContents(expectedResultsFile);
		actualResults = actualResults.trim();
		expectedResults = expectedResults.trim();
		assertEquals("Unexpected verifier output for isRealized values on EE.", expectedResults, actualResults);		
	}
}
