
//=====================================================================
//
//File:      IOMdlTestGenerics.java
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

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.GeneralPurposeLogger;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.io.mdl.ExportModel;
import org.xtuml.bp.io.mdl.ImportModel;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.utilities.ui.ProjectUtilities;

import junit.framework.TestCase;

@RunWith(OrderedRunner.class)
public class IOMdlTestGenerics extends TestCase {

	@Rule public TestName name = new TestName();

    GeneralPurposeLogger log1;  // log for core
    GeneralPurposeLogger log2;  // log for canvas

	Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();
	Ooaofgraphics graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());

	private static String m_system_name = "org.xtuml.bp.io.mdl.test";
	private String m_domain_name = "";
    private static String m_workspace_path = "";
    private static String m_logfile_path = "";
    static SystemModel_c m_system = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance() , new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((SystemModel_c)candidate).getName().equals(m_system_name);
			}
		});
		;

    /**
     * Whether this test run is meant to produce the expected
     * results files, rather than actually perform the tests.
     */
    private static boolean generateResults = false;

	public IOMdlTestGenerics() {
		super(null);
		
		// Change default for the parse on resource change preference to
		// "always"
		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
		store.setValue(BridgePointPreferencesStore.EXPORT_GRAPHICS, "always"); //$NON-NLS-1$
	}
	@Before
	public void setUp() throws Exception {
		String methodName = name.getMethodName();
		m_domain_name = methodName.substring(5, methodName.length());
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		super.setUp();
        IdAssigner.setSeedOfAllInstances(methodName.hashCode(), true);
        if (m_workspace_path == null || m_workspace_path.equals(""))
        {
            m_workspace_path = System.getProperty("WORKSPACE_PATH");
        }
        if (m_logfile_path == null || m_logfile_path.equals(""))
        {
            m_logfile_path = System.getProperty("LOGFILE_PATH"); //$NON-NLS-1$
        }
        assertNotNull( m_workspace_path );
        assertNotNull( m_logfile_path );
		if ( log1 == null )
			log1 = new GeneralPurposeLogger();
		else
			log1.clear();
		Ooaofooa.log = log1;

		if ( log2 == null )
			log2 = new GeneralPurposeLogger();
		else
			log2.clear();
		Ooaofgraphics.log = log2;		
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
        BaseTest.staticTearDown();
	}

	public void doTest() {
		importModel(TestingUtilities.getExpectedResultsPath() + Ooaofooa.MODELS_DIRNAME + "/");
		exportModel(TestingUtilities.getExpectedResultsPath() + Ooaofooa.MODELS_DIRNAME + "/");
	}
  
	
	@Test
	public void test_InteractionDiagramUpgradeTestsGenerics() {
		importModel(Ooaofooa.MODELS_DIRNAME + "/");
		exportModel(TestingUtilities.getExpectedResultsPath() + Ooaofooa.MODELS_DIRNAME + "/");
    }	
    @Test
    public void test_nested_testGenerics() {
		importModel(Ooaofooa.MODELS_DIRNAME + "/");
		exportModel(TestingUtilities.getExpectedResultsPath() + Ooaofooa.MODELS_DIRNAME + "/");
	}
	@Test
	public void test_odmsGenerics() {
		importModel(Ooaofooa.MODELS_DIRNAME + "/");
		exportModel(TestingUtilities.getExpectedResultsPath() + Ooaofooa.MODELS_DIRNAME + "/");
	}
	@Test
	public void test_ooaofooa() { doTest(); }
	@Test
	public void test_ooaofgraphics() { doTest(); }

	@Test
	public void test_asc() { doTest(); }
	@Test
	public void test_BP50_evt() { doTest(); }
	@Test
	public void test_BP50_evt2() { doTest(); }
	@Test
	public void test_br1() { doTest(); }
	@Test
	public void test_br2() { doTest(); }
	@Test
	public void test_br1f() { doTest(); }
	@Test
	public void test_br2f() { doTest(); }
	@Test
	public void test_bridges() { doTest(); }
	@Test
	public void test_cl() { doTest(); }
	@Test
	public void test_dogs() { doTest(); }
	@Test
	public void test_enum1() { doTest(); }
	@Test
	public void test_enum2() { doTest(); }
	@Test
	public void test_enum3() { doTest(); }
	@Test
	public void test_enum4() { doTest(); }
	@Test
	public void test_event() { doTest(); }
	@Test
	public void test_ex1() { doTest(); }
	@Test
	public void test_ex2() { doTest(); }
	@Test
	public void test_ex3() { doTest(); }

	@Test
	public void test_G_ALL_G_EVT_LE_precreated() { doTest(); }
	@Test
	public void test_G_ALL_R_BRG_tim() { doTest(); }
	@Test
	public void test_G_ALL_multiple_exit_return() { doTest(); }
	@Test
	public void test_G_ALL_nested_invoke() { doTest(); }
	@Test
	public void test_G_ALL_performance_test1() { doTest(); }
	@Test
	public void test_G_ALL_performance_test2() { doTest(); }
	@Test
	public void test_G_ALL_performance_test3() { doTest(); }
	@Test
	public void test_G_ALL_performance_test4() { doTest(); }
	@Test
	public void test_G_ALL_performance_test5() { doTest(); }
	@Test
	public void test_G_ALL_performance_test6() { doTest(); }
	@Test
	public void test_G_ALL_performance_test7() { doTest(); }
	@Test
	public void test_G_ALL_select_where_enum() { doTest(); }
	@Test
	public void test_G_BRG_G_ALL_interop() { doTest(); }
	@Test
	public void test_G_COP_R_ALL_interop() { doTest(); }
	@Test
	public void test_G_EVT_PE_G_EVT_NLE_nle_ignored() { doTest(); }
	@Test
	public void test_G_IOP_MDA_self_event() { doTest(); }
	@Test
	public void test_G_IOP_R_ALL_interop() { doTest(); }
	@Test
	public void test_G_MDA_R_ALL_interop() { doTest(); }
	@Test
	public void test_G_STE_G_COP_compare_date() { doTest(); }
	@Test
	public void test_G_STE_G_EVT_PE_to_creation() { doTest(); }
	@Test
	public void test_G_STE_G_STE_pe_le_same_state() { doTest(); }
	@Test
	public void test_G_STE_assoc_rel() { doTest(); }
	@Test
	public void test_G_STE_del_inst_mult() { doTest(); }

	@Test
	public void test_im1() { doTest(); }
	@Test
	public void test_im2() { doTest(); }
	@Test
	public void test_im3() { doTest(); }
	@Test
	public void test_im4() { doTest(); }
	@Test
	public void test_ims() { doTest(); }
	@Test
	public void test_ims2() { doTest(); }
	@Test
	public void test_imx() { doTest(); }
	@Test
	public void test_init1() { doTest(); }
	@Test
	public void test_init2() { doTest(); }
	@Test
	public void test_interop_otherdom() { doTest(); }
	@Test
	public void test_memleak() { doTest(); }
	@Test
	public void test_mt1() { doTest(); }
	@Test
	public void test_no_inst() { doTest(); }
	@Test
	public void test_poly() { doTest(); }
	@Test
	public void test_reflexive() { doTest(); }
	@Test
	public void test_select() { doTest(); }
	@Test
	public void test_self() { doTest(); }
	@Test
	public void test_sm() { doTest(); }
	@Test
	public void test_sync() { doTest(); }
	@Test
	public void test_syntax() { doTest(); }
	@Test
	public void test_trans() { doTest(); }
	@Test
	public void test_wim2() { doTest(); }
	@Test
	public void test_wim3() { doTest(); }
	@Test
	public void test_wims() { doTest(); }
	@Test
	public void test_wimx() { doTest(); }
	@Test
	public void testImportSyntaxError() throws FileNotFoundException
	{
		ImportModel impMod = new ImportModel(m_workspace_path + Ooaofooa.MODELS_DIRNAME + "/badGenerics." + Ooaofooa.MODELS_EXT, m_workspace_path + Ooaofooa.MODELS_DIRNAME + "/badGenerics." + Ooaofooa.MODELS_EXT, modelRoot, m_system, true, true, false); //$NON-NLS-1$
		impMod.run(new NullProgressMonitor());
		assertEquals( false, impMod.m_success );
		assertEquals( "line 7:1: expecting \"values\", found 'null'\n", //$NON-NLS-1$
		   impMod.m_errorMessage );
	}

    @Test
    public void testImportOdmsFNF() throws FileNotFoundException {
        String m_errorMessage = null;
        boolean importSuccess = true;
        try {
            String loc = m_workspace_path + "models/odms.xxx";//$NON-NLS-1$
            importSuccess = TestingUtilities
                    .importModelUsingWizard(m_system, loc,
                            false);
            ImportModel impMod = new ImportModel("odms.xxx", "odms.xxx", modelRoot, m_system, true, true, false);  //$NON-NLS-1$
            int i = impMod.countAndValidateInsertStatements();
            assertTrue ( i > 0 );
            impMod.run(new NullProgressMonitor());
        } catch (Exception e) {
            m_errorMessage = e.getMessage();
        }

        assertEquals(importSuccess, false);
        assertEquals("odms.xxx not found" ,  //$NON-NLS-1$
                m_errorMessage );
    }

    @Test
    public void testImportOdmsAccessError() {
        String m_errorMessage = null;
        try {
            ImportModel impMod = new ImportModel(
                    "mdl", "mdl", modelRoot, m_system, true, true, false); //$NON-NLS-1$
            int i = impMod.countAndValidateInsertStatements();
            assertTrue(i > 0);
            impMod.run(new NullProgressMonitor());
        } catch (FileNotFoundException e) {
            m_errorMessage = e.getMessage();
        }
        assertEquals("mdl not found", //$NON-NLS-1$
                m_errorMessage);
    }

	@Test
	public void testExportOdmsWithGraphicsFNF()
	{
		try {
			ExportModel expMod = new ExportModel(modelRoot, "bad_dir/odms.xxx", true); //$NON-NLS-1$
			expMod.run(new NullProgressMonitor());
		} catch (InvocationTargetException f) {
			fail( f.toString() );
		} catch (FileNotFoundException f) {
			String systemError;
			if (Platform.getOS().contains("win")) {
				systemError = "The system cannot find the path specified";
			} else {
				systemError = "No such file or directory";
			}
			assertEquals(
					"java.io.FileNotFoundException: bad_dir" //$NON-NLS-1$
							+ java.io.File.separator
							+ "odms.xxx (" + systemError + ")", f.toString()); //$NON-NLS-1$
		}
	}
	@Test
	public void testExportOdmsWithGraphicsAccessError()
	{
		boolean accessError = false;
		String directoryPath = m_workspace_path + "actual_results";  //$NON-NLS-1$
		BaseTest.ensureFolderExists(directoryPath);
		// trying to create a file over an existing folder will give an access error
		try {
			ExportModel expMod = new ExportModel(modelRoot, directoryPath, true);
			expMod.run(new NullProgressMonitor());
		} catch (InvocationTargetException f) {
			fail( f.toString() );
		} catch (FileNotFoundException f) {
			String systemError;
			if (Platform.getOS().contains("win")) {
				systemError = "Access is denied"; 
			}
			else {
				systemError = "Is a directory";
			}
			String osPath = new Path(directoryPath).toOSString();
			assertEquals( "java.io.FileNotFoundException: " + osPath + " (" + systemError + ")", f.toString() ); //$NON-NLS-1$//$NON-NLS-2$
			accessError = true;
		}
		assertTrue("access error did not occur", accessError);//$NON-NLS-1$
	}
	
    @Test
    public void testExportOdmsWithGraphics()
    {
        m_domain_name = "odmsGenerics";
        importModel( Ooaofooa.MODELS_DIRNAME + "/");
        // add the system and top-level packages to the selection
        m_system = SystemModel_c.SystemModelInstance(
                Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
                    public boolean evaluate(Object candidate) {
                        return ((SystemModel_c) candidate).getName().equals(
                                m_system_name);
                    }
                });
        Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_system);
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(m_system);
        Selection.getInstance().addToSelection(pkgs);

        String resultFile = m_workspace_path;
        if (generateResults) {
        	resultFile += TestingUtilities.getExpectedResultsPath();
        } else {
        	resultFile += "actual_results/";
        }
        resultFile += "odmsGenerics";
        if (BaseTest.testGlobals) {
            resultFile += "Globals." + Ooaofooa.MODELS_EXT;        	
        } else {
            resultFile += "." + Ooaofooa.MODELS_EXT;        	
        }
        TestingUtilities.exportModelUsingWizard(resultFile, true);
		    
		if (!generateResults) {
			if(BaseTest.testGlobals) {
			    TestingUtilities.fileContentsCompare(
				        m_workspace_path + TestingUtilities.getExpectedResultsPath() +"odmsGenericsGlobals." + Ooaofooa.MODELS_EXT,
				        m_workspace_path + "actual_results/odmsGenericsGlobals." + Ooaofooa.MODELS_EXT );				
			} else {
			    TestingUtilities.fileContentsCompare(
			        m_workspace_path + TestingUtilities.getExpectedResultsPath() +"odmsGenericsGlobals." + Ooaofooa.MODELS_EXT,
			        m_workspace_path + "actual_results/odmsGenerics." + Ooaofooa.MODELS_EXT );
			}
		 }
		
    }

   private void importModel(String path)
   {
       try {
           ProjectUtilities.deleteProject(m_system_name);
           ProjectUtilities.createProject(m_system_name);
       } catch (CoreException ce) {
           assertTrue(ce.getMessage(), false);
       }
       
       m_system = SystemModel_c.SystemModelInstance(
               Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
                   public boolean evaluate(Object candidate) {
                       return ((SystemModel_c) candidate).getName().equals(
                               m_system_name);
                   }
               });
           
       String loc = m_workspace_path + path+m_domain_name+"." + Ooaofooa.MODELS_EXT;//$NON-NLS-1$
       TestingUtilities.importModelUsingWizard(m_system,loc, false);
       modelRoot.setLoadPathForTests(loc);
       BaseTest.dispatchEvents(0);
   }

    private void exportModel(String expectedResultLocation) {
        BaseTest.ensureFolderExists(m_workspace_path + "actual_results/"
                + Ooaofooa.MODELS_DIRNAME + "/");
        String actualResultsCompletePath = m_workspace_path + "actual_results/"
                + Ooaofooa.MODELS_DIRNAME + "/" + m_domain_name + "."
                + Ooaofooa.MODELS_EXT;
        String expectedResultsCompletePath = m_workspace_path
                + expectedResultLocation + m_domain_name + "."
                + Ooaofooa.MODELS_EXT;
        if (BaseTest.testGlobals) {
            expectedResultsCompletePath = m_workspace_path
                    + expectedResultLocation + m_domain_name + "Globals" + "."
                    + Ooaofooa.MODELS_EXT;
        }

        // add the system and top-level packages to the selection
        m_system = SystemModel_c.SystemModelInstance(
                Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
                    public boolean evaluate(Object candidate) {
                        return ((SystemModel_c) candidate).getName().equals(
                                m_system_name);
                    }
                });
        Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_system);
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(m_system);
        Selection.getInstance().addToSelection(pkgs);

        TestingUtilities.exportModelUsingWizard(
                generateResults ? expectedResultsCompletePath
                        : actualResultsCompletePath, true);

        if (!generateResults) {
            String expected_path_start = m_workspace_path
                    + expectedResultLocation + m_domain_name;

            if (BaseTest.testGlobals) {
                expected_path_start = m_workspace_path + expectedResultLocation
                        + m_domain_name + "Globals";
            }
            String actual_path_start = m_workspace_path + "actual_results/" + Ooaofooa.MODELS_DIRNAME + "/" + m_domain_name; //$NON-NLS-1$//$NON-NLS-2$
            String path_end = "." + Ooaofooa.MODELS_EXT; //$NON-NLS-1$
            TestingUtilities.makeInsertsOneLine(actual_path_start + path_end, actual_path_start + "_a1" + path_end); //$NON-NLS-1$
            TestingUtilities.makeInsertsOneLine(expected_path_start + path_end, actual_path_start + "_e1" + path_end); //$NON-NLS-1$
            TestingUtilities.sortFile(actual_path_start + "_a1" + path_end, actual_path_start + "_a2" + path_end); //$NON-NLS-1$//$NON-NLS-2$
            TestingUtilities.sortFile(actual_path_start + "_e1" + path_end, actual_path_start + "_e2" + path_end); //$NON-NLS-1$//$NON-NLS-2$
            String supplement = "expected file: " + expected_path_start + path_end + ", actual file: " + actual_path_start + path_end;
            TestingUtilities.fileContentsCompare(actual_path_start + "_e2" + path_end, actual_path_start + "_a2" + path_end, supplement); //$NON-NLS-1$//$NON-NLS-2$
            // don't delete actual_path_start+path_end, as that is used later
            IPath del_path = new Path(actual_path_start + "_a1" + path_end);
            del_path.toFile().delete();
            del_path = new Path(actual_path_start + "_a2" + path_end);
            del_path.toFile().delete();
            del_path = new Path(actual_path_start + "_e1" + path_end);
            del_path.toFile().delete();
            del_path = new Path(actual_path_start + "_e2" + path_end);
            del_path.toFile().delete();
            // TestingUtilities.fileContentsCompare(
            //              workspace_path + expectedResultLocation + m_domain_name + "." + Ooaofooa.MODELS_EXT, //$NON-NLS-1$
            //              workspace_path + "actual_results/" + Ooaofooa.MODELS_DIRNAME + "/" + m_domain_name + "." + Ooaofooa.MODELS_EXT ); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        }
    }

   /**
    * See field.
    */
   public static void setGenerateResults(boolean generate)
   {
       generateResults = generate;
   }
}
