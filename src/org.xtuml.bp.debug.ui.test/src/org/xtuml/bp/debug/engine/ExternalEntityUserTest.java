package org.xtuml.bp.debug.engine;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.debug.test.VerifierTest;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.OrderedRunner;

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

@RunWith(OrderedRunner.class)
public class ExternalEntityUserTest extends VerifierTest {

	public ExternalEntityUserTest() {
		super("EE_Test");
	}

	@Test
	public void testUserEE() throws InterruptedException {
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
			TestUtil.copyClassFileIntoProject("LOG", "org.xtuml.bp.core.test", project);
		    // perform the model test.
		    redirectOutput("User_EE_Test");
		    executeModel(); // throws InterruptedException
			endRedirection();
		}
		else {
			fail("bin/lib folder not found.");
		}
	}
	
	@Test
	public void testOutput(){
		compareOutput("User_EE_test");
	}
}
