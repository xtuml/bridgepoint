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

package org.xtuml.bp.io.mdl.test.pkgcm;

import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;

@RunWith(OrderedRunner.class)
public class PkgCMModifyRelationTestGenerics extends ModifyRelationTest {

	@Rule public TestName name = new TestName();
	
	public String getName(){
		return name.getMethodName();
	}

    protected static String projectName = "MultiLevelModelSystem";

    protected static String mdlClassUnderTest = "X";

    protected static String dtpUnderTest = "SubDataTypes";

    protected static boolean reCopy_modRel = true;
    protected static boolean firstTime = true;
    
    public PkgCMModifyRelationTestGenerics() {
        super(projectName,null);
    }

    @Before
	public void setUp() throws Exception {
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
    
    @Test
	public void testModifyDomainPackageWithChildren() throws Exception {    	
        performModifyComponentGenerics("Package", "MultiLevelModel", "Package", false, true, 5, new String[] {"External Entities", "SS1"});
    }

    @Test
	public void testModifyDatatypePackageWithChildren() throws Exception {
        performModifyComponentGenerics("Package", "Datatypes", "Package",false,true, 4, new String[] {"SubDataTypes", "SubDataTypes2"});
    }
    
}
