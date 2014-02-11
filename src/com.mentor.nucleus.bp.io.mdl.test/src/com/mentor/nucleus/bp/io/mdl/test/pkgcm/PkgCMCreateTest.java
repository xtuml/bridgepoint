//=====================================================================
//
//File:      $RCSfile: PkgCMCreateTest.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2013/01/10 23:12:57 $
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
import org.eclipse.core.runtime.IPath;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class PkgCMCreateTest extends CreateTest {

    protected static final String domainName = "MultiLevelModel";

    protected static String projectName = "com.mentor.nucleus.bp.io.mdl.createTest";

    protected static PersistableModelComponent sysPMC = null;

    protected static IPath mdlClassPath = null;

    public PkgCMCreateTest(String projName, String name) {
        super(projName, name);
        showModelExplorer();
    }

    public PkgCMCreateTest(String name) {
        this(projectName, name);
    }

    protected static boolean firstTime_create = true;

    protected void setUp() throws Exception {
        super.setUp();
        setupProjectAndTestModel();
    }

    protected void setupProjectAndTestModel() throws CoreException {
        setupProject(projectName);
        if (firstTime_create) {
            ensureAvailableAndLoaded(domainName, false);
            project= getProjectHandle(projectName);
            UIUtil.refresh(project);
            // IO_MDL tests set this false we need it true
            Ooaofooa.setPersistEnabled(true);
            project= getProjectHandle(projectName);
            UIUtil.refresh(project);

        }
        firstTime_create = false;
        // projectName should be set in test which cause it to change
        
    }

    // ModelClass
    public void testCreateModelClass_ThruCE() throws Exception {
        // save pmc for next ism test
        PersistableModelComponent pmc=performCreateComponentThruCE("Subsystem", "SS1", "Class");
        //it could be null during setup
        if(pmc!=null)
            mdlClassPath = pmc.getFullPath();
    }

    public void testCreateISM_ThruME() throws Exception {
        performCreateComponentThruME("ModelClass",mdlClassPath.toString(), EditorTestUtilities.EDITOR_TYPE_CANVAS,"ISM","");
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
    public void testCreateSystem_ThruME_NoEditor() throws Exception {
        performCreateComponentThruME("SystemModel",null,
                EditorTestUtilities.EDITOR_TYPE_NONE, "SystemModel" ,"TestSysThruME");
    }

    public void testCreateDomain_ThruME_NoEditor() throws Exception {
        performCreateComponentThruME("SystemModel","TestSysThruME",
                EditorTestUtilities.EDITOR_TYPE_NONE ,"Domain","TestDomainThuME");
    }

}
