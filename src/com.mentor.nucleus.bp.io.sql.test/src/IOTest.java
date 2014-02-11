
//=====================================================================
//
//File:      $RCSfile: IOTest.java,v $
//Version:   $Revision: 1.36 $
//Modified:  $Date: 2013/01/10 23:07:37 $
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.ClassInAssociation_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.ReferredToClassInAssoc_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.io.core.CoreImport;
import com.mentor.nucleus.bp.io.sql.ImportBPSql;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.GeneralPurposeLogger;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class IOTest extends TestCase {

    GeneralPurposeLogger log1;
    GeneralPurposeLogger log2;
    
	Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();
	Ooaofgraphics graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
	
	private String m_domain_name = "";
    static String workspace_path = "";
    static SystemModel_c m_system = new SystemModel_c(Ooaofooa.getDefaultInstance());

    public IOTest(String arg0) {
		super(arg0);
		// they all start with "test_"
		m_domain_name = arg0.substring(5, arg0.length());
	}
    protected void setUp() throws Exception {
        super.setUp();
        IdAssigner.setSeedOfAllInstances(getName().hashCode());
        if (workspace_path == null || workspace_path.equals(""))
        {
            workspace_path = System.getProperty("WORKSPACE_PATH");
        }
        assertNotNull( workspace_path );
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
    }

    private void outputLogsCompare(String expected_file)
    {   
        String[] log1Entries = log1.getLogs(), log2Entries = log2.getLogs();
        String actualResults = TestUtil.join(log1Entries) 
            + (log2Entries.length > 0 ? "\r\n" + TestUtil.join(log2Entries) : "");
        String expectedResults = TestUtil.getTextFileContents(
            new Path(expected_file).toFile());
        assertEquals(expectedResults, actualResults);
    }

    public void testImportOdmsFNF() throws FileNotFoundException
    {   
		String m_errorMessage = null;
		try{
            ImportBPSql impBP = new ImportBPSql("odms.xxx", modelRoot, m_system, true, true, false);  //$NON-NLS-1$
			int i = impBP.countAndValidateInsertStatements();
			assertTrue ( i > 0 );
        	impBP.run(new NullProgressMonitor());
		}catch(FileNotFoundException e){
			m_errorMessage = e.getMessage();
    }
    
		assertEquals("odms.xxx not found" ,  //$NON-NLS-1$
		   m_errorMessage );
	}
	
    public void testImportOdmsAccessError()
    {   
		String m_errorMessage = null;
		try{
            ImportBPSql impBP = new ImportBPSql("mdl", modelRoot, m_system, true, true, false); //$NON-NLS-1$
			int i = impBP.countAndValidateInsertStatements();
			assertTrue ( i > 0 );
	        impBP.run(new NullProgressMonitor());
		}catch(FileNotFoundException e){
			m_errorMessage = e.getMessage();
    }
		assertEquals( "mdl not found",  //$NON-NLS-1$
		   m_errorMessage );
	}
    
    public void testImportSyntaxError() throws FileNotFoundException
    {   
        ImportBPSql impBP = new ImportBPSql(workspace_path+ Ooaofooa.MODELS_DIRNAME + "/bad.sql", modelRoot, m_system, true, true, false); //$NON-NLS-1$
		int i = impBP.countAndValidateInsertStatements();
		assertTrue ( i > 0 );
        impBP.run(new NullProgressMonitor());
        assertEquals( false, impBP.m_success );
        assertEquals( "line 7:1: expecting \"values\", found 'null'\n", //$NON-NLS-1$
           impBP.m_errorMessage );
    }
    
    public void testImportMultDomainError() throws FileNotFoundException
    {   
        ImportBPSql impBP = new ImportBPSql(workspace_path+ Ooaofooa.MODELS_DIRNAME + "/bad2.sql", modelRoot, m_system, true, true, false); //$NON-NLS-1$
		int i = impBP.countAndValidateInsertStatements();
		assertEquals ( CoreImport.PPS_ERROR, i );
        assertEquals( "The file "+workspace_path+ Ooaofooa.MODELS_DIRNAME + "/bad2.sql contains multiple domains.", //$NON-NLS-1$
           impBP.m_errorMessage );
    }
    
    public void testImportOdms() throws FileNotFoundException
    {   
        ImportBPSql impBP = new ImportBPSql(workspace_path+ Ooaofooa.MODELS_DIRNAME + "/odms.sql", modelRoot, m_system, true, true, false); //$NON-NLS-1$
		int i = impBP.countAndValidateInsertStatements();
		assertTrue ( i > 0 );
        impBP.run(new NullProgressMonitor());
        modelRoot.setLoadPathForTests(workspace_path + Ooaofooa.MODELS_DIRNAME + "/odms.sql"); //$NON-NLS-1$
        outputLogsCompare( workspace_path+"expected_results/odms.imp" ); //$NON-NLS-1$
        
        // Verify unformalized relationship (R12) imported with 
        // correct values for R_RTO.Oid_ID
        Association_c r12 = Association_c.AssociationInstance(modelRoot, new ClassQueryInterface_c(){

			public boolean evaluate(Object candidate) {
				Association_c selected = (Association_c)candidate;
				return selected.getNumb() == 12;
			}
        	
        });
        ClassInAssociation_c cia_set [] = ClassInAssociation_c.getManyR_OIRsOnR201(r12);
        ReferredToClassInAssoc_c rto [] = ReferredToClassInAssoc_c.getManyR_RTOsOnR203(cia_set);
        for ( int j = 0; j < rto.length; ++j )
        {
        	assertEquals ( -1, rto[j].getOid_id() );
        }
    }
    
    public void testImportUnicode() throws FileNotFoundException
    {   
        ImportBPSql impBP = new ImportBPSql(workspace_path+ Ooaofooa.MODELS_DIRNAME + "/unicode.sql", modelRoot, m_system, true, true, false); //$NON-NLS-1$
        int i = impBP.countAndValidateInsertStatements();
        assertTrue ( i > 0 );
        impBP.run(new NullProgressMonitor());
        modelRoot.setLoadPathForTests(workspace_path + Ooaofooa.MODELS_DIRNAME + "/unicode.sql"); //$NON-NLS-1$
        ModelClass_c uut = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						String actualName = ((ModelClass_c) candidate).getName();
						final String expectedResult = "�a�X�N";
						return (actualName.equals(expectedResult)); //$NON-NLS-1$ // This is a UNICODE test, we don't want this translated
					}
				});
        // If we found this class, then the test passed because the name would otherwise have been corrupted
        assertNotNull("Unicode file not loaded correctly", uut);
    }
    
    public void testPartiallyDerivedDomainImportExport() throws FileNotFoundException
    {
        ImportBPSql impBP = new ImportBPSql(workspace_path+ Ooaofooa.MODELS_DIRNAME + "/test_ca_smsmc2.sql", modelRoot, m_system, true, true, false);//$NON-NLS-1$
        int i = impBP.countAndValidateInsertStatements();
        assertTrue ( i > 0 );
        impBP.run(new NullProgressMonitor());
        assertEquals( "", impBP.m_errorMessage );
        modelRoot.setLoadPathForTests(workspace_path + Ooaofooa.MODELS_DIRNAME + "/test_ca_smsmc2.sql"); //$NON-NLS-1$
        outputLogsCompare( workspace_path+"expected_results/test_ca_smsmc2.imp" ); //$NON-NLS-1$
        }

        }
