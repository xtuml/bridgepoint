//=====================================================================
//
//File:      $RCSfile: PkgCMModifyRelationTestGenerics.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/05/10 17:34:01 $
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

package com.mentor.nucleus.bp.io.mdl.test.pkgcm;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class PkgCMModifyRelationTestGenerics extends ModifyRelationTest {

    protected static String projectName = "MultiLevelModelSystem";

    protected static String mdlClassUnderTest = "X";

    protected static String dtpUnderTest = "SubDataTypes";

    protected static boolean reCopy_modRel = true;
    protected static boolean firstTime = true;
    
    public PkgCMModifyRelationTestGenerics(String name) {
        super(projectName,name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setupProjectAndTestModel();
    }
    protected void setupProjectAndTestModel() throws CoreException {
    	setupProject(projectName);
    	m_sys= getSystemModel(projectName);
        if(firstTime){
        //    ensureAvailableAndLoaded(domainName, false,false);  
        	loadProject(projectName);
            	
        	   firstTime=false;
            }
         	project = getProjectHandle(projectName);
            modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(getProjectHandle(projectName), "MultiLevelModel", true), true);
       	    graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
        	Ooaofooa.setPersistEnabled(true);
            //projectName should be set in test which cause it to change
           
                // IO_MDL tests set this false we need it true
    	   CorePlugin.disableParseAllOnResourceChange();

    
    }
    /* Modify Tests through Canvas Editor : start */

//    public void testModifyPackageWithChildren() throws Exception {
//        performModifyComponentGenerics("Package", "SS1", "Class",false,true, 4, new String[] {"SSInSS1", "SSInSS12"});
//    }
    
    public void testModifyDomainPackageWithChildren() throws Exception {    	
        performModifyComponentGenerics("Package", "MultiLevelModel", "Package", false, true, 5, new String[] {"External Entities", "SS1"});
    }

    public void testModifyDatatypePackageWithChildren() throws Exception {
        performModifyComponentGenerics("Package", "Datatypes", "Package",false,true, 4, new String[] {"SubDataTypes", "SubDataTypes2"});
    }
    
}
