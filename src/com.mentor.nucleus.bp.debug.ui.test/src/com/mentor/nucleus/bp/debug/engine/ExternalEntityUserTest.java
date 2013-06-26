package com.mentor.nucleus.bp.debug.engine;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.debug.test.VerifierTest;
import com.mentor.nucleus.bp.test.TestUtil;

//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

public class ExternalEntityUserTest extends VerifierTest {

	public ExternalEntityUserTest() {
		super("EE_Test");
	}

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
			TestUtil.copyClassFileIntoProject("LOG", "com.mentor.nucleus.bp.core.test", project);
		    // perform the model test.
		    redirectOutput("User_EE_Test");
		    executeModel(); // throws InterruptedException
			endRedirection();
		}
		else {
			fail("bin/lib folder not found.");
		}
	}
	
	public void testOutput(){
		compareOutput("User_EE_test");
	}
}
