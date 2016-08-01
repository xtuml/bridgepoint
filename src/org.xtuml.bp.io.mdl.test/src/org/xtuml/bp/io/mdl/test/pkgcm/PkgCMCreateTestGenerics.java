//=====================================================================
//
//File:      $RCSfile: PkgCMCreateTestGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 17:34:00 $
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
import org.eclipse.core.runtime.IPath;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.test.common.OrderedRunner;

@RunWith(OrderedRunner.class)
public class PkgCMCreateTestGenerics extends CreateTest {

	@Rule public TestName name = new TestName();
	
	public String getName(){
		return name.getMethodName();
	}

    protected static String projectName = "MultiLevelModelSystem";

    protected static PersistableModelComponent sysPMC = null;

    protected static IPath mdlClassPath = null;

    public PkgCMCreateTestGenerics() {
        super(projectName, null);
        showModelExplorer();
    }

    protected static boolean firstTime_create = true;

    @Before
	public void setUp() throws Exception {
        super.setUp();
        setupProjectAndTestModel();
    }

    protected void setupProjectAndTestModel() throws CoreException {
        if (firstTime_create) {
        //    ensureAvailableAndLoaded(domainName, false);

        	loadProject(projectName);
        	 modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(getProjectHandle(projectName), "MultiLevelModel", true), true);
            // IO_MDL tests set this false we need it true
            Ooaofooa.setPersistEnabled(true);

        }
    	m_sys= getSystemModel(projectName);
        project = getProjectHandle(projectName);
        firstTime_create = false;
        // projectName should be set in test which cause it to change
        
    }

    @Test
	public void testCreatePackage_ThruCE() throws Exception {
    	performCreateComponentThruCEGenerics("Package","MultiLevelModel","Package");
    }

    @Test
	public void testCreatePackageInPackage_ThruCE() throws Exception {  
        performCreateComponentThruCEGenerics("Package", "SS1","Package");
    }

    @Test
	public void testCreateDatatypePackageInPkg_ThruCE() throws Exception {
        performCreateComponentThruCEGenerics("Package", "/MultiLevelModelSystem/models/MultiLevelModelSystem/MultiLevelModel/Datatypes/Datatypes.xtuml", "Package");
    }

    @Test
	public void testCreateEEPkgInPkg_ThruCE() throws Exception {
        performCreateComponentThruCEGenerics("Package", "External Entities", "Package");
    }

    // ModelClass
    @Test
	public void testCreateModelClass_ThruCE() throws Exception {
        // save pmc for next ism test
        PersistableModelComponent pmc=performCreateComponentThruCEGenerics("Package", "SS1", "Class");
        //it could be null during setup
        if(pmc!=null)
            mdlClassPath = pmc.getFullPath();
    }

    @Test
	public void testCreateISM_ThruME() throws Exception {
        performCreateComponentThruMEGenerics("ModelClass",mdlClassPath.toString(), EditorTestUtilities.EDITOR_TYPE_CANVAS,"ISM","");
        if(!toRunTests()){
            return ;
        }
        PersistableModelComponent mdlClassPMC=PersistenceManager.findComponent(mdlClassPath);
        ModelClass_c mdCls = (ModelClass_c) mdlClassPMC.getRootModelElement();
        Attribute_c current_state = Attribute_c.getOneO_ATTROnR102(mdCls
                .getModelRoot(), mdCls, new ClassQueryInterface_c() {

            public boolean evaluate(Object candidate) {
                Attribute_c attr = (Attribute_c) candidate;
                return attr.getName().equals("current_state");
            }
        });
        assertNotNull(
                "current_state attribute does not exist after ISM creation",
                current_state);
        checkTreeItemExistance(current_state, "current_state");
    }

    /* Create components through Model Explorer : start */
    @Test
	public void testCreateSystem_ThruME_NoEditor() throws Exception {
        performCreateComponentThruMEGenerics("SystemModel",null,
                EditorTestUtilities.EDITOR_TYPE_NONE, "SystemModel" ,"TestSysThruME");
    }

    @Test
	public void testCreateDomain_ThruME_NoEditor() throws Exception {
        performCreateComponentThruMEGenerics("SystemModel","TestSysThruME",
                EditorTestUtilities.EDITOR_TYPE_NONE ,"Package","TestDomainThuME");
    }

}
