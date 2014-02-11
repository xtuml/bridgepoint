//=====================================================================
//
//File:      $RCSfile: TestVerifierRunTimeErrorMsgs_0.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:28:42 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.debug.ui.test.runtimemsg;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class TestVerifierRunTimeErrorMsgs_0 extends TestVerifierRunTimeErrorMsgs {

	private static boolean initialized = false ;
	private String projectName = "TestVerifierRunTimeErrorMsgs";
    protected String getResultName() {
        return super.getResultName();
    }

    public TestVerifierRunTimeErrorMsgs_0(String arg0) {
        super(null, arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
		if (!initialized) {
		CorePlugin.disableParseAllOnResourceChange();
		TestVerifierRunTimeErrorMsgs.projectName = projectName;

		// set perspective switch dialog on launch
		DebugUIPlugin.getDefault().getPluginPreferences().setValue(
				IDebugUIConstants.PLUGIN_ID + ".switch_to_perspective",
				"always");

		CorePlugin
				.getDefault()
				.getPluginPreferences()
				.setDefault(
						BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING,
						true);

		// initialize test model
		final IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);

		loadProject(projectName);
		
		m_sys = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((SystemModel_c) candidate).getName().equals(
						project.getName());
			}

		});

		Ooaofooa.setPersistEnabled(true);
        delayGlobalUpgrade = false;
        modelRoot = (Ooaofooa) m_sys.getModelRoot();
		initialized = true;
	}
}

    protected void tearDown() throws Exception {
    	DebugUITestUtilities.stopSession(m_sys, projectName);
        
    }

 
    public void testA8_C1N1CSM() {
        test_id = getTestId("A8", "C1N1CSM", "1");
        launchComponentName ="WrapperComponent";
        List<NonRootModelElement> src =  selectA("A8");

        NonRootModelElement dest = selectCN("C1N1CSM","A8",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_Pkg_ModelClass_Class_StateMachine_State());
        
    }
    /**
     * Perform the test for the given matrix column (A1) and row (C3N3).
     * 
     */
    public void testA1_C3N3() {
        test_id = getTestId("A1", "C3N3", "9");
        launchComponentName ="Root Component";
        List<NonRootModelElement> src =  selectA("A1");

        NonRootModelElement dest = selectCN("C3N3","A1",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine_State());
        
    }

  

    public void testA2_C3N3() {
        test_id = getTestId("A2", "C3N3", "18");
        launchComponentName ="Root Component";
        List<NonRootModelElement> src =  selectA("A2");

        NonRootModelElement dest = selectCN("C3N3","A2",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine());
        
    }

    /**
     * Perform the test for the given matrix column (A3) and row (C1N1).
     * 
     */
    public void testA3_C1N1() {
        test_id = getTestId("A3", "C1N1", "19");
        launchComponentName ="WrapperComponent";
        List<NonRootModelElement> src =  selectA("A3");

        NonRootModelElement dest = selectCN("C1N1","A3",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_Pkg_Model());
        
    }

    /**
     * Perform the test for the given matrix column (A3) and row (C3N3).
     * 
     */
    public void testA3_C3N3() {
        test_id = getTestId("A3", "C3N3", "27");
        launchComponentName ="Root Component";
        List<NonRootModelElement> src =  selectA("A3");

        NonRootModelElement dest = selectCN("C3N3","A3",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_compPkg_Comp_Comp_subsys_Model());
        
    }



    /**
     * Perform the test for the given matrix column (A4) and row (C3N3).
     * 
     */
    public void testA4_C3N3() {
        test_id = getTestId("A4", "C3N3", "36");
        launchComponentName ="Root Component";
        List<NonRootModelElement> src =  selectA("A4");

        NonRootModelElement dest = selectCN("C3N3","A4",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_Pkg_Comp_Comp_subsys_ModelClass_Op());
        
    }

  


    /**
     * Perform the test for the given matrix column (A5) and row (C3N3).
     * 
     */
    public void testA5_C3N3() {
        test_id = getTestId("A5", "C3N3", "45");
        launchComponentName ="Root Component";
        List<NonRootModelElement> src =  selectA("A5");

        NonRootModelElement dest = selectCN("C3N3","A5",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_compPkg_Comp_Comp_FnPkg_Fn());
        
    }

   

    /**
     * Perform the test for the given matrix column (A6) and row (C3N3).
     * 
     */
    public void testA6_C3N3() {
        test_id = getTestId("A6", "C3N3", "54");
        launchComponentName ="Root Component";
        List<NonRootModelElement> src =  selectA("A6");

        NonRootModelElement dest = selectCN("C3N3","A6",src);

        A_CN_Action(dest);
        assertTrue("Sys_compPkg_Comp_Comp_EEPkg_EE_Brdg", checkResult_Sys_compPkg_Comp_Comp_EEPkg_EE_Brdg());
        
    }

  

    /**
     * Perform the test for the given matrix column (A7) and row (C3N3).
     * 
     */
    public void testA7_C3N3() {
        test_id = getTestId("A7", "C3N3", "63");
        launchComponentName ="Root Component";
        List<NonRootModelElement> src =  selectA("A7");

        NonRootModelElement dest = selectCN("C3N3","A7",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_compPkg_Comp_Comp_PrvOp());
        
    }



    /**
     * Perform the test for the given matrix column (A8) and row (C3N3).
     * 
     */
    public void testA8_C3N3() {
        test_id = getTestId("A8", "C3N3", "72");
        launchComponentName ="Root Component";
        List<NonRootModelElement> src =  selectA("A8");

        NonRootModelElement dest = selectCN("C3N3","A8",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_compPkg_Comp_Comp_PrvSig());
        
    }



    /**
     * Perform the test for the given matrix column (A9) and row (C3N3).
     * 
     */
    public void testA9_C3N3() {
        test_id = getTestId("A9", "C3N3", "81");
        launchComponentName ="Root Component";
        List<NonRootModelElement> src =  selectA("A9");

        NonRootModelElement dest = selectCN("C3N3","A9",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_compPkg_Comp_Comp_ReqOp());
        
    }



    /**
     * Perform the test for the given matrix column (A10) and row (C3N3).
     * 
     */
    public void testA10_C3N3() {
        test_id = getTestId("A10", "C3N3", "90");
        launchComponentName ="Root Component";
        List<NonRootModelElement> src =  selectA("A10");

        NonRootModelElement dest = selectCN("C3N3","A10",src);

        A_CN_Action(dest);
        assertTrue(checkResult_Sys_compPkg_Comp_Comp_ReqSig());
        
    }
    
    


}
