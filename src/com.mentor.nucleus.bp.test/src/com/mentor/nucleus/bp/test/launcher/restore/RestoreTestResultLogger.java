//========================================================================
//
//File:      $RCSfile: RestoreTestResultLogger.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/03/13 22:18:37 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.test.launcher.restore;

import com.mentor.nucleus.bp.test.TestResultLogger;

public class RestoreTestResultLogger  {

    private static RestoreTestResultLogger defaultInst;

    public static RestoreTestResultLogger getDefault() {
        return defaultInst;
    }

	private long totalElapsedTime;

	private int testCount;

    /**
     * An object in which to accumulate info about a single test being run.
     */
    protected static class Result
    {
        /**
         * The name of the test-class method that performs the test. 
         */
        public String testName;
        
        /**
         * Whether the test completed successfully.
         */
        public boolean completed = true;
        
        /**
         * If the test failed to complete, the status code returned by the test harness.
         */
        public int status;
        
        /**
         * The stack-trace produced if the test failed or errored-out. 
         */
        public String trace;
        
        /**
         * When the test started (in ms).
         */
        public long startTime;
        
        /**
         * How long the test took to run (in ms).
         */
        public long elapsedTime;
        
        /**
         * For restore tests
         */
        public static final int MODE_NORMAL=0;
        public static final int MODE_RESTORE_SETUP=1;
        public static final int MODE_RESTORE_RUN=2;
        public int mode = MODE_NORMAL;
    }

	
	private Result currentResult;

    public RestoreTestResultLogger() {
        defaultInst = this;
    }

    public static boolean inSetupWorkspace = true;

    private static boolean isFirstTest;

    private static int restoreTestCount;

    public static boolean isInSetupWorkspace() {
        return inSetupWorkspace;
    }

    public static void setInSetupWorkspace(boolean inWorkspaceSetup) {
        RestoreTestResultLogger.inSetupWorkspace = inWorkspaceSetup;
    }

    // we are not sure that listner object have created when restore launch is
    // started
    // so we need to save this info and wait for listner creation
    public static void start(int testCount) {
        isFirstTest = true;
        restoreTestCount = testCount;
    }

    public void finished(long elapsedTime) {
        totalElapsedTime = elapsedTime;
        writeResults();
        isFirstTest = false;
    }

    private void writeResults() {
		// TODO Auto-generated method stub
		
	}

    public void testRunStarted(int testCount) {
        if (isFirstTest){
            initialize();
            this.testCount = restoreTestCount;
        }
        isFirstTest = false;
    }

    private void initialize() {
		// TODO Auto-generated method stub
		
	}

    public void testRunEnded(long elapsedTime) {
        // This will be called for each test case in restore case, so
        // we are not using it
        // instead finish() will be used that will be called from restoreLaunch
    }

    public void testRunStopped(long elapsedTime) {
        // testRunStopped in case of restore test is equal to end of one test
        testEnded(currentResult.testName, null);
    }

    public void testStarted(String testId, String testName) {
        // super is just fine
        testStarted(testId, testName);
        currentResult.mode = isInSetupWorkspace() ? Result.MODE_RESTORE_SETUP
                : Result.MODE_RESTORE_RUN;
    }

    public void testEnded(String testId, String testName) {
        testEnded(testId, testName);
    }

    public void testFailed(int status, String testId, String testName,
            String trace) {
        testFailed(status, testId, testName, trace);
    }

    public void testRunTerminated() {
        // we have not finished yet

    }

    public void testReran(String testId, String testClass, String testName,
            int status, String trace) {
        // does not matter

    }

}
