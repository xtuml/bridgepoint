package org.xtuml.bp.io.mdl.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

@RunWith(OrderedRunner.class)
public class ProxyTestsGenerics extends BaseTest {

    private static String projectName = "ProxyTests";

    public ProxyTestsGenerics() {
        super(projectName, null);
    }

    @Before
	public void setUp() throws Exception {
        super.setUp();
        //ensureAvailableAndLoaded("Proxy", false);
        String loc = m_workspace_path +Ooaofooa.MODELS_DIRNAME+"/ProxyGenerics"+"." + Ooaofooa.MODELS_EXT;//$NON-NLS-1$
        TestingUtilities.importModelUsingWizard(getSystemModel(projectName),loc, false);
        modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(getProjectHandle(projectName), "Proxy", true), true);
    }

//
// This test has been commented-out and an issue has been raised to resolve a
// problem that will allow this test to be enable in the future.  The issue
// raised is dts0100770280 - Fix error due to attempt to persist non persistent Packageable Element
//	
    @Test
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
