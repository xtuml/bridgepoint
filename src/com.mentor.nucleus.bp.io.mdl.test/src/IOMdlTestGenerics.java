
//=====================================================================
//
//File:      $RCSfile: IOMdlTestGenerics.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/05/13 22:51:33 $
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

import junit.framework.TestCase;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.io.mdl.ExportModel;
import com.mentor.nucleus.bp.io.mdl.ImportModel;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.GeneralPurposeLogger;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;

public class IOMdlTestGenerics extends TestCase {

    GeneralPurposeLogger log1;  // log for core
    GeneralPurposeLogger log2;  // log for canvas

	Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();
	Ooaofgraphics graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());

	private static String m_system_name = "com.mentor.nucleus.bp.io.mdl.test";
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

	public IOMdlTestGenerics(String arg0) {
		super(arg0);
		// they all start with "test_"
		m_domain_name = arg0.substring(5, arg0.length());
	
	}
	protected void setUp() throws Exception {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		super.setUp();
        IdAssigner.setSeedOfAllInstances(getName().hashCode(), true);
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

	protected void tearDown() throws Exception {
		super.tearDown();
        BaseTest.staticTearDown();
	}

	public void doTest() {
		importModel("expected_results"  + "/" + Ooaofooa.MODELS_DIRNAME + "/");
		exportModel("expected_results/" + Ooaofooa.MODELS_DIRNAME + "/");
	}
  
	
	public void test_InteractionDiagramUpgradeTestsGenerics() {
		importModel(Ooaofooa.MODELS_DIRNAME + "/");
		exportModel("expected_results/"+Ooaofooa.MODELS_DIRNAME + "/");
    }	
    public void test_nested_testGenerics() {
		importModel(Ooaofooa.MODELS_DIRNAME + "/");
		exportModel("expected_results/" + Ooaofooa.MODELS_DIRNAME + "/");
	}
	public void test_odmsGenerics() {
		importModel(Ooaofooa.MODELS_DIRNAME + "/");
		exportModel("expected_results/" + Ooaofooa.MODELS_DIRNAME + "/");
	}
	public void test_ooaofooa() { doTest(); }
	public void test_ooaofgraphics() { doTest(); }

	public void test_asc() { doTest(); }
	public void test_BP50_evt() { doTest(); }
	public void test_BP50_evt2() { doTest(); }
	public void test_br1() { doTest(); }
	public void test_br2() { doTest(); }
	public void test_br1f() { doTest(); }
	public void test_br2f() { doTest(); }
	public void test_bridges() { doTest(); }
	public void test_cl() { doTest(); }
	public void test_dogs() { doTest(); }
	public void test_enum1() { doTest(); }
	public void test_enum2() { doTest(); }
	public void test_enum3() { doTest(); }
	public void test_enum4() { doTest(); }
	public void test_event() { doTest(); }
	public void test_ex1() { doTest(); }
	public void test_ex2() { doTest(); }
	public void test_ex3() { doTest(); }

	public void test_G_ALL_G_EVT_LE_precreated() { doTest(); }
	public void test_G_ALL_R_BRG_tim() { doTest(); }
	public void test_G_ALL_multiple_exit_return() { doTest(); }
	public void test_G_ALL_nested_invoke() { doTest(); }
	public void test_G_ALL_performance_test1() { doTest(); }
	public void test_G_ALL_performance_test2() { doTest(); }
	public void test_G_ALL_performance_test3() { doTest(); }
	public void test_G_ALL_performance_test4() { doTest(); }
	public void test_G_ALL_performance_test5() { doTest(); }
	public void test_G_ALL_performance_test6() { doTest(); }
	public void test_G_ALL_performance_test7() { doTest(); }
	public void test_G_ALL_select_where_enum() { doTest(); }
	public void test_G_BRG_G_ALL_interop() { doTest(); }
	public void test_G_COP_R_ALL_interop() { doTest(); }
	public void test_G_EVT_PE_G_EVT_NLE_nle_ignored() { doTest(); }
	public void test_G_IOP_MDA_self_event() { doTest(); }
	public void test_G_IOP_R_ALL_interop() { doTest(); }
	public void test_G_MDA_R_ALL_interop() { doTest(); }
	public void test_G_STE_G_COP_compare_date() { doTest(); }
	public void test_G_STE_G_EVT_PE_to_creation() { doTest(); }
	public void test_G_STE_G_STE_pe_le_same_state() { doTest(); }
	public void test_G_STE_assoc_rel() { doTest(); }
	public void test_G_STE_del_inst_mult() { doTest(); }

	public void test_im1() { doTest(); }
	public void test_im2() { doTest(); }
	public void test_im3() { doTest(); }
	public void test_im4() { doTest(); }
	public void test_ims() { doTest(); }
	public void test_ims2() { doTest(); }
	public void test_imx() { doTest(); }
	public void test_init1() { doTest(); }
	public void test_init2() { doTest(); }
	public void test_interop_otherdom() { doTest(); }
	public void test_memleak() { doTest(); }
	public void test_mt1() { doTest(); }
	public void test_no_inst() { doTest(); }
	public void test_poly() { doTest(); }
	public void test_reflexive() { doTest(); }
	public void test_select() { doTest(); }
	public void test_self() { doTest(); }
	public void test_sm() { doTest(); }
	public void test_sync() { doTest(); }
	public void test_syntax() { doTest(); }
	public void test_trans() { doTest(); }
	public void test_wim2() { doTest(); }
	public void test_wim3() { doTest(); }
	public void test_wims() { doTest(); }
	public void test_wimx() { doTest(); }
	public void testImportSyntaxError() throws FileNotFoundException
	{
		ImportModel impMod = new ImportModel(m_workspace_path + Ooaofooa.MODELS_DIRNAME + "/badGenerics." + Ooaofooa.MODELS_EXT, modelRoot, m_system, true, true, false); //$NON-NLS-1$
		impMod.run(new NullProgressMonitor());
		assertEquals( false, impMod.m_success );
		assertEquals( "line 7:1: expecting \"values\", found 'null'\n", //$NON-NLS-1$
		   impMod.m_errorMessage );
	}

    public void testImportOdmsFNF() throws FileNotFoundException {
        String m_errorMessage = null;
        boolean importSuccess = true;
        try {
            String loc = m_workspace_path + "models/odms.xxx";//$NON-NLS-1$
            importSuccess = TestingUtilities
                    .importModelUsingWizardConvertToGenerics(m_system, loc,
                            false);
            ImportModel impMod = new ImportModel("odms.xxx", modelRoot, m_system, true, true, false);  //$NON-NLS-1$
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

    public void testImportOdmsAccessError() {
        String m_errorMessage = null;
        try {
            ImportModel impMod = new ImportModel(
                    "mdl", modelRoot, m_system, true, true, false); //$NON-NLS-1$
            int i = impMod.countAndValidateInsertStatements();
            assertTrue(i > 0);
            impMod.run(new NullProgressMonitor());
        } catch (FileNotFoundException e) {
            m_errorMessage = e.getMessage();
        }
        assertEquals("mdl not found", //$NON-NLS-1$
                m_errorMessage);
    }

	public void testExportOdmsWithGraphicsFNF()
	{
		try {
			ExportModel expMod = new ExportModel(modelRoot, "bad_dir/odms.xxx", true); //$NON-NLS-1$
			expMod.run(new NullProgressMonitor());
		} catch (InvocationTargetException f) {
			fail( f.toString() );
		} catch (FileNotFoundException f) {
			assertEquals( f.toString(), "java.io.FileNotFoundException: bad_dir"  //$NON-NLS-1$
				+ java.io.File.separator
				+ "odms.xxx (The system cannot find the path specified)" ); //$NON-NLS-1$
		}
	}

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
			String osPath = new Path(directoryPath).toOSString();
			assertEquals( f.toString(), "java.io.FileNotFoundException: " + osPath + " (Access is denied)" ); //$NON-NLS-1$//$NON-NLS-2$
			accessError = true;
		}
		assertTrue("access error did not occur", accessError);//$NON-NLS-1$
	}
	
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
        	resultFile += "expected_results";
        } else {
        	resultFile += "actual_results";
        }
        resultFile += "/odmsGenerics";
        if (BaseTest.testGlobals) {
            resultFile += "Globals." + Ooaofooa.MODELS_EXT;        	
        } else {
            resultFile += "." + Ooaofooa.MODELS_EXT;        	
        }
        TestingUtilities.exportModelUsingWizard(resultFile, true);
		    
		if (!generateResults) {
			if(BaseTest.testGlobals) {
			    TestingUtilities.fileContentsCompare(
				        m_workspace_path + "expected_results/odmsGenericsGlobals." + Ooaofooa.MODELS_EXT,
				        m_workspace_path + "actual_results/odmsGenericsGlobals." + Ooaofooa.MODELS_EXT );				
			} else {
			    TestingUtilities.fileContentsCompare(
			        m_workspace_path + "expected_results/odmsGenerics." + Ooaofooa.MODELS_EXT,
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
       TestingUtilities.importModelUsingWizardConvertToGenerics(m_system,loc, false);
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
