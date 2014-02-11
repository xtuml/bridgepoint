package com.mentor.nucleus.bp.debug.engine;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.debug.test.VerifierTest;
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

public class ParameterPassingTest extends VerifierTest {

	public ParameterPassingTest() {
		super(new String[]{"ParameterPassing","BT"});
	}
	
	public void testParameterPassing()throws InterruptedException {
		IdAssigner.setSeedOfAllInstances(1);
		IFolder binFolder = project.getFolder("bin");
		IFolder libFolder = null;
		if (!binFolder.exists()) {
			try {
			  binFolder.create(true, true, null);
			}
			catch(CoreException e) {
				fail("Could not create user's bin folder. Reason: " + e);
			}
		}
		if (binFolder.exists()) {
			libFolder = binFolder.getFolder("lib");
			if (!libFolder.exists()) {
				try {
					  libFolder.create(true, true, null);
				}
				catch(CoreException e) {
					fail("Could not create user's lib folder. Reason: " + e);
				}
			}
		}
        // get Eclipse to notice the changed file (and wait
        // until the resulting model-events have been dispatched, 
        // before proceeding)
        try {
            TestUtil.DispatchOnDoneProgressMonitor monitor = 
                new TestUtil.DispatchOnDoneProgressMonitor();
            libFolder.refreshLocal(0, monitor);
            while (!monitor.done) TestUtil.sleep(10);
        } catch (CoreException e) {
            fail("Could not get Eclipse to recognize new lib folder. Reason: " + e);
        }
		if (libFolder.exists()) {
			// copy the test class file to the users lib folder
			TestUtil.copyClassFileIntoProject("BT", "com.mentor.nucleus.bp.core.test", project);
		    // perform the model test.
			redirectOutput("testParameterPassing");
		    setIsVerifyingAttribute(true);
		    setupWirings();
		    executeModel();
			endRedirection();			
		}
		else {
			fail("bin/lib folder not found.");
		}
	}
	
	public void testOutputParameterPassing()throws InterruptedException {
		compareOutput("testParameterPassing");	
		TestUtil.deleteProject(project);
	}
	
	public void tearDown() throws InterruptedException {
		super.tearDown();
	}
	
	private void setIsVerifyingAttribute(boolean verifying)
	{
		SystemModel_c[] sysmodels = SystemModel_c
		.SystemModelInstances(Ooaofooa.getDefaultInstance());

		Domain_c[] domains = Domain_c.getManyS_DOMsOnR28(sysmodels);
		for (int i = 0; i < domains.length; i++)
		{
			ComponentInstance_c exEng = new ComponentInstance_c(domains[i].getModelRoot());
			exEng.Initializearchitecture();
		}
	}
	
	private void setupWirings()
	{
		
		SystemModel_c[] sysmodels = SystemModel_c
		.SystemModelInstances(Ooaofooa.getDefaultInstance());

		Domain_c[] domains = Domain_c.getManyS_DOMsOnR28(sysmodels);		
		for (int i = 0; i < domains.length; i++)
		{
			domains[i].Removewirings();
			domains[i].Setupwirings();
		}	
	}
	
}

