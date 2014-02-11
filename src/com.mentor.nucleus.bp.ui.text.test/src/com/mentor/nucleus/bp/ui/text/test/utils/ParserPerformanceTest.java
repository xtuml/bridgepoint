//=====================================================================
//
//File:      $RCSfile: ParserPerformanceTest.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:47:06 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.ui.text.test.utils;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;

public class ParserPerformanceTest extends BaseTest {

	private static Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();
	
	static IFile testModel = null;

	public ParserPerformanceTest(String name) throws Exception {
		super("test", name); //$NON-NLS-1$
	}

	public ParserPerformanceTest(String projectName, String name)
			throws CoreException {
		super("test", name); //$NON-NLS-1$
	}

	protected void setUp() throws Exception {
		if (modelRoot != null && Domain_c.DomainInstance(modelRoot) != null) {
			modelRoot.clearDatabase(new NullProgressMonitor());
		}

		if (testModel == null) {
			TestUtil.copyTestDomainIntoProject("ooaofooa","com.mentor.nucleus.bp.core",getProject());  //$NON-NLS-1$//$NON-NLS-2$
			testModel = getProject().getFile(Ooaofooa.MODELS_DIRNAME + "/ooaofooa." + Ooaofooa.MODELS_EXT); //$NON-NLS-1$
		}

		try {
			modelRoot.setLoadPathForTests(testModel.getLocation().toString());
			importModel(testModel, modelRoot, false, false, false);			
		} catch (IOException e) {
			fail(e.toString());
		}
		super.setUp();
	}

	public void testOalModelParsing(){
		AllActivityModifier aam = new AllActivityModifier(Domain_c.DomainInstance(modelRoot),new NullProgressMonitor());
		long startTime = System.currentTimeMillis();
		aam.processAllActivities(AllActivityModifier.PARSE);
		
		System.out.println("Time taken by the model to parse: " + ((System.currentTimeMillis() - startTime)/1000) + " sec");
	}
}