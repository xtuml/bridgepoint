package com.mentor.nucleus.bp.io.mdl.test;

//=====================================================================
//
//File:      $RCSfile: ProxyTestsGenerics.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:12:54 $
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
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class ProxyTestsGenerics extends BaseTest {

    private static String projectName = "ProxyTests";

    public ProxyTestsGenerics(String name) {
        super(projectName, name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        //ensureAvailableAndLoaded("Proxy", false);
        String loc = m_workspace_path +Ooaofooa.MODELS_DIRNAME+"/ProxyGenerics"+"." + Ooaofooa.MODELS_EXT;//$NON-NLS-1$
        TestingUtilities.importModelUsingWizardConvertToGenerics(getSystemModel(projectName),loc, false);
        modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(getProjectHandle(projectName), "Proxy", true), true);
    }

//
// This test has been commented-out and an issue has been raised to resolve a
// problem that will allow this test to be enable in the future.  The issue
// raised is dts0100770280 - Fix error due to attempt to persist non persistent Packageable Element
//	
    public void testConvertToProxyAfterComponentUnload() throws Exception {

//        ModelClass_c rtoClass = ModelClass_c.ModelClassInstance(modelRoot,
//                new ModelClass_by_name_c("RTOClass"));
//        ModelClass_c rgoClass = ModelClass_c.ModelClassInstance(modelRoot,
//                new ModelClass_by_name_c("RGOClass"));
//
//        ModelClass_c saClass = ModelClass_c.ModelClassInstance(modelRoot,
//                new ModelClass_by_name_c("StandaloneClass"));
//
//        Package_c subsystem = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(saClass));
//
//        BaseAttribute_c rtoIDBaseAttr = BaseAttribute_c
//                .getOneO_BATTROnR106(Attribute_c.getOneO_ATTROnR102(rtoClass,
//                        new Attribute_by_name_c("ID")));
//
//        Attribute_c nameAttr = Attribute_c.AttributeInstance(modelRoot,
//                new Attribute_by_name_c("Name"));
//
//        // Unload RTO component and check that its elements has been converted
//        // to the proxy
//        //Hint: To check why any element is not removed when expected, see
//        //its RGOs using PM.getHierMetaData().findExternalRGOs(element, false);
//        
//        rtoClass.getPersistableComponent().deleteSelf();
//        assertTrue("RTO Class does not converted to the proxy", rtoClass
//                .isProxy());
//        assertFalse("RTO Class prematurely removed", rtoClass.isOrphaned());
//
//        assertTrue("RTO BaseAttribute_c does not converted to the proxy",
//                rtoIDBaseAttr.isProxy());
//        assertFalse("RTO BaseAttribute_c prematurely removed", rtoIDBaseAttr
//                .isOrphaned());
//
//        assertTrue("Attribute of removed class does not removed", nameAttr
//                .isOrphaned());
//
//        // Unload RGO component and check that its proxies has been removed
//        rgoClass.getPersistableComponent().deleteSelf();
//        assertTrue("RGO Class does not converted to the proxy", rgoClass
//                .isProxy());
//        assertTrue("Proxy BaseAttribute does not removed after RGO unload",
//                rtoIDBaseAttr.isOrphaned());
//
//        assertTrue("Attribute of removed class does not removed", nameAttr
//                .isOrphaned());
//
//        PackageableElement_c peSA = PackageableElement_c.getOnePE_PEOnR8001(saClass);
//        assertFalse("Standalone Class Packageable Element prematurely converted to a proxy", peSA.isProxy());
//        
//        // Unload Parent and check it has been converted to the proxy
//        subsystem.getPersistableComponent().deleteSelf();
//        
//        assertTrue("Standalone Class Packageable Element is not converted to a proxy", peSA
//                .isProxy());
//        assertTrue("Proxy class does not removed", rtoClass.isOrphaned());
//
//        // unload child and check parent proxy is removed
//        ModelClass_c standaloneClass = ModelClass_c.ModelClassInstance(
//                modelRoot, new ModelClass_by_name_c("StandaloneClass"), false);
//        standaloneClass.getPersistableComponent().deleteSelf();
//
//        assertTrue("Package does not removed after child unload", subsystem
//                .isOrphaned());
//
    }

}
