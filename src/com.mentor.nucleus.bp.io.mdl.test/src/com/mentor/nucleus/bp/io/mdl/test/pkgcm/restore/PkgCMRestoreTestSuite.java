package com.mentor.nucleus.bp.io.mdl.test.pkgcm.restore;
//=====================================================================
//
//File:      $RCSfile: PkgCMRestoreTestSuite.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:12:59 $
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
import java.lang.reflect.Method;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PkgCMRestoreTestSuite extends TestSuite {

    /**
     * Returns the suite.  This is required to
     * use the JUnit Launcher.
     */
    public static Test suite() {
        return new PkgCMRestoreTestSuite();
    }
    
    /**
     * Construct the test suite.
     */
    public PkgCMRestoreTestSuite()
    {
        String testCaseName=System.getProperty("TestCaseName");
                 
        Method m;
        //Note only one try block from all block will be successfull
        
        try {
            m = getTestMethod(PkgCMRestoreCreateTest.class,testCaseName);
            if(m!=null)// testcase exists
                addTest(new PkgCMRestoreCreateTest(testCaseName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            m = getTestMethod(PkgCMRestoreModifyContentsTest.class,testCaseName);
            if(m!=null)// testcase exists
                addTest(new PkgCMRestoreModifyContentsTest(testCaseName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            m = getTestMethod(PkgCMRestoreModifyRelationTest.class,testCaseName);
            if(m!=null)// testcase exists
                addTest(new PkgCMRestoreModifyRelationTest(testCaseName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            m = getTestMethod(PkgCMRestoreRenameTest.class,testCaseName);
            if(m!=null)// testcase exists
                addTest(new PkgCMRestoreRenameTest(testCaseName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            m = getTestMethod(PkgCMRestoreDeleteTest.class,testCaseName);
            if(m!=null)// testcase exists
                addTest(new PkgCMRestoreDeleteTest(testCaseName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
    }
    
    Method getTestMethod(Class theClass,String testCaseName) throws Exception{
        Class superClass= theClass;
        Method method=null;
        
        while (Test.class.isAssignableFrom(superClass)) {
            try{
            method= superClass.getDeclaredMethod(testCaseName, null);
            }catch(Exception e){
                
            }
            if(method!=null)
                break;
            
            superClass= superClass.getSuperclass();
        }
        return method;
    }
}