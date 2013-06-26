//========================================================================
//
//File:      $RCSfile: TestResultLogger.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/03/13 22:18:53 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package com.mentor.nucleus.bp.test;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestRunSession;

import com.mentor.nucleus.bp.core.CorePlugin;

/**
 * Saves the test session into the standard XML format
 * 
 */
public class TestResultLogger extends TestRunListener {
    
	@Override
	public void sessionFinished(ITestRunSession session) {
		writeResults(session);
    }
    
    /**
     * Writes the session into an XML representation of the run.
     */
    protected void writeResults(ITestRunSession session)
    {
        // resolve the name of the first test performed during the run 
    	File folder = new File("test_results");
    	if(!folder.exists()) {
    		folder.mkdir();
        }
        String fileName = "test_results/" + session.getTestRunName() + ".xml";
        File file = new File(fileName);
        try {
			JUnitCore.exportTestRunSession(session, file);
		} catch (CoreException e) {
			CorePlugin.logError("Unable to save test run session to XML format.", e);
        }
    }
}
