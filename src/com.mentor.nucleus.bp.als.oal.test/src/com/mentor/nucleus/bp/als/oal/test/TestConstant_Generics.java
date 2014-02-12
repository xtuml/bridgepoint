//========================================================================
//
//File:      $RCSfile: TestConstant_Generics.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 04:52:49 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//========================================================================
//
package com.mentor.nucleus.bp.als.oal.test;

import java.io.StringReader;
import java.util.HashMap;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.StructuredSelection;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.mentor.nucleus.bp.als.oal.OalLexer;
import com.mentor.nucleus.bp.als.oal.OalParser;
import com.mentor.nucleus.bp.als.oal.Oal_validate;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ConstantSpecification_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Oalconstants_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SymbolicConstant_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.ContainerUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class TestConstant_Generics extends BaseTest {

    // This test class just tests the *negative* cases where we expect to find
    // syntax errors.  The positive cases where no errors should be found it
    // covered by the "sdt_test.xtuml" model.  It fully exercises all valid
    // uses of SDTs in BP.  It is ran as part of the "Parse All" unit tests
    // defined in ParseAllInDomain.java.
	protected static boolean initialized = false;
	 public static final String NO_ERR = "No Error";
	 public static final String MULTI_INST = "Multiple Instances";
	 //public static final String PVT_ERR ="Private Not Found";
	 //public static final String PRTCTD_ERR = "Protected Not Found";
		public static final String NOT_FOUND ="Variable Not Found";
		 HashMap<String, String> expectedResults = new HashMap<String, String>();
		 
    protected void setUp() throws Exception {
        super.setUp();
		if (!initialized)
		{
			
			
		String modelName = "testConst";
	
		Selection.getInstance().setSelection(
				new StructuredSelection(m_sys));
		
		TestingUtilities.importTestingProjectIntoWorkspace(modelName);
		modelRoot = Ooaofooa.getInstance(Ooaofooa
					.createModelRootId(getProjectHandle(modelName),
							modelName, true));
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("testConst");
		
		m_sys = getSystemModel(project.getName());

		initialized = true;
		}
	
		expectedResults.put("AboveTestConst_multiple",MULTI_INST);
		expectedResults.put("AboveTestConst_private",NOT_FOUND);
		expectedResults.put("AboveTestConst_protected",NO_ERR);
		expectedResults.put("AboveTestConst_public",NO_ERR);
		
		expectedResults.put("SiblingTestConst_multiple",MULTI_INST);
		expectedResults.put("SiblingTestConst_private",NO_ERR);
		expectedResults.put("SiblingTestConst_protected",NO_ERR);
		expectedResults.put("SiblingTestConst_public",NO_ERR);

		expectedResults.put("BelowPrivateTestConst_multiple",MULTI_INST);
		expectedResults.put("BelowPrivateTestConst_private",NOT_FOUND);
		expectedResults.put("BelowPrivateTestConst_protected",NOT_FOUND);
		expectedResults.put("BelowPrivateTestConst_public",NO_ERR);
		
		expectedResults.put("BelowProtectedTestConst_multiple",MULTI_INST);
		expectedResults.put("BelowProtectedTestConst_private",NOT_FOUND);
		expectedResults.put("BelowProtectedTestConst_protected",NOT_FOUND);
		expectedResults.put("BelowProtectedTestConst_public",NO_ERR);
		
	    expectedResults.put("BelowPublicTestConst_multiple",MULTI_INST);
		expectedResults.put("BelowPublicTestConst_private",NOT_FOUND);
		expectedResults.put("BelowPublicTestConst_protected",NOT_FOUND);
		expectedResults.put("BelowPublicTestConst_public",NO_ERR);
		
		expectedResults.put("AboveSiblingPublicTestConst_multiple",MULTI_INST);
		expectedResults.put("AboveSiblingPublicTestConst_private",NOT_FOUND);
		expectedResults.put("AboveSiblingPublicTestConst_protected",NOT_FOUND);
		expectedResults.put("AboveSiblingPublicTestConst_public",NO_ERR);
		
		expectedResults.put("AboveSiblingPrivateTestConst_multiple",NOT_FOUND);
		expectedResults.put("AboveSiblingPrivateTestConst_private",NOT_FOUND);
		expectedResults.put("AboveSiblingPrivateTestConst_protected",NOT_FOUND);
		expectedResults.put("AboveSiblingPrivateTestConst_public",NOT_FOUND);
		
		expectedResults.put("AboveSiblingProtectedTestConst_multiple",MULTI_INST);
		expectedResults.put("AboveSiblingProtectedTestConst_private",NOT_FOUND);
		expectedResults.put("AboveSiblingProtectedTestConst_protected",NOT_FOUND);
		expectedResults.put("AboveSiblingProtectedTestConst_public",NO_ERR);
		
		expectedResults.put("SystemLevelTestConst_multiple",MULTI_INST);
		expectedResults.put("SystemLevelTestConst_private",NOT_FOUND);
		expectedResults.put("SystemLevelTestConst_protected",NOT_FOUND); 
		expectedResults.put("SystemLevelTestConst_public",NO_ERR);
		expectedResults.put("SystemLevelBelow_protected_public",NOT_FOUND);
		expectedResults.put("SystemLevelBelow_public_public",NO_ERR);
		expectedResults.put("SystemLevelBelow_private_public",NOT_FOUND);
		
		expectedResults.put("AboveInTheSameComponentPackage_public_multiple",MULTI_INST);
		expectedResults.put("AboveInTheSameComponentPackage_public_private",NOT_FOUND);
		expectedResults.put("AboveInTheSameComponentPackage_public_protected",NOT_FOUND);
		expectedResults.put("AboveInTheSameComponentPackage_public_public",NO_ERR);
		
		expectedResults.put("AboveInTheSameComponentPackage_private_multiple",NOT_FOUND);
		expectedResults.put("AboveInTheSameComponentPackage_private_private",NOT_FOUND);
		expectedResults.put("AboveInTheSameComponentPackage_private_protected",NOT_FOUND);
		expectedResults.put("AboveInTheSameComponentPackage_private_public",NOT_FOUND);
		
		expectedResults.put("AboveInTheSameComponentPackage_protected_multiple",NOT_FOUND);
		expectedResults.put("AboveInTheSameComponentPackage_protected_private",NOT_FOUND);
		expectedResults.put("AboveInTheSameComponentPackage_protected_protected",NOT_FOUND);
		expectedResults.put("AboveInTheSameComponentPackage_protected_public",NOT_FOUND);
	
		
    }

    protected void tearDown() throws Exception {
        try {
            super.tearDown();
            OalParserTest_Generics.tearDownActionData();
        } catch (RecognitionException re) {
            // do nothing
        } catch (TokenStreamException te) {
            // do nothing
        }
    }

  
    public void testConstant() throws RecognitionException,
            TokenStreamException {
     	String stmts ="";   	
    	Function_c[] functions = Function_c.getManyS_SYNCsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)));
		Function_c fun =functions[0];
		
		Package_c [] packages =new Package_c[] { 
				Package_c.getOneEP_PKGOnR1405(m_sys,new Package_by_name_c("Above")),
				Package_c.getOneEP_PKGOnR1405(m_sys,new Package_by_name_c("System Level Test Package"))};
	
		
		Package_c [] pkgs = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(packages));
		ConstantSpecification_c   [] constspecs =
			ConstantSpecification_c.getManyCNST_CSPsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkgs));

          SymbolicConstant_c[] consts = SymbolicConstant_c.getManyCNST_SYCsOnR1504(constspecs);		
          
  		assertEquals(23,  consts.length);
  		String varName="";  
  		for (int i = 0; i < consts.length; i++) {
			varName= consts[i].getName();
			stmts = "a="+varName+";";
			String parserOutput = parserAction(stmts, fun);

			validateOutput(parserOutput,expectedResults.get(varName) ,varName);
					
		}

            
         
    }
   public void testConstInComponent() throws RecognitionException, TokenStreamException
   {      
	   String stmts ="";   	   
	   Function_c[] functions = Function_c.getManyS_SYNCsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)));
	   Function_c fun =functions[1]; 
		Package_c [] packages =new Package_c[] { 
				Package_c.getOneEP_PKGOnR1405(m_sys,new Package_by_name_c("AboveInTheSameComponent"))};	
		Package_c [] pkgs = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(packages))));
 		ConstantSpecification_c   [] constspecs =
		ConstantSpecification_c.getManyCNST_CSPsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkgs)); 
        SymbolicConstant_c[] consts = SymbolicConstant_c.getManyCNST_SYCsOnR1504(constspecs);		
       assertEquals(15, consts.length); 
        String varName="";
       
        for (int i = 0; i < consts.length; i++) {
			varName= consts[i].getName();
			stmts = "a="+varName+";";
			String parserOutput = parserAction(stmts, fun);

			validateOutput(parserOutput,expectedResults.get(varName) ,varName);
					
		}

   }

	/**
	 * @param actualResult
	 * 
	 * AboveTestConst_multiple
AboveTestConst_multiple
AboveTestConst_private
AboveTestConst_protected
AboveTestConst_public
SiblingTestConst_multiple
SiblingTestConst_multiple
SiblingTestConst_private
SiblingTestConst_protected
SiblingTestConst_public
BelowPrivateTestConst_multiple
BelowPrivateTestConst_multiple
BelowPrivateTestConst_private
BelowPrivateTestConst_protected
BelowPrivateTestConst_public
BelowProtectedTestConst_multiple
BelowProtectedTestConst_multiple
BelowProtectedTestConst_private
BelowProtectedTestConst_protected
BelowProtectedTestConst_public
BelowPublicTestConst_multiple
BelowPublicTestConst_multiple
BelowPublicTestConst_private
BelowPublicTestConst_protected
BelowPublicTestConst_public
AboveSiblingPublicTestConst_private
AboveSiblingPublicTestConst_multiple
AboveSiblingPublicTestConst_multiple
AboveSiblingPublicTestConst_protected
AboveSiblingPublicTestConst_public
AboveSiblingPrivateTestConst_multiple
AboveSiblingPrivateTestConst_multiple
AboveSiblingPrivateTestConst_private
AboveSiblingPrivateTestConst_protected
AboveSiblingPrivateTestConst_public
AboveSiblingProtectedTestConst_private
AboveSiblingProtectedTestConst_multiple
AboveSiblingProtectedTestConst_multiple
AboveSiblingProtectedTestConst_protected
AboveSiblingProtectedTestConst_public
	 */
	private void validateOutput(String actualResult, String expectedResult,
			String variableName) {
		if (expectedResult.equals(NO_ERR)) {
			assertEquals("", actualResult);/*,"Wrong Error message \"outPutResultIs \""
					+ actualResult + "\" expectedResultIs \""
					+ expectedResult + "\" variableName is \""
					+ variableName + "\""); //$NON-NLS-1$*/
		} else if (expectedResult.equals(MULTI_INST)
				|| expectedResult.equals(NOT_FOUND)) {
			String lines[] = actualResult.split("\n");//$NON-NLS-1$
			if ((expectedResult.equals(MULTI_INST))
					&& (lines[0].indexOf("There") != -1)) {
				assertEquals("There is more than one constant named ->"
						+ variableName + "<- in the system, cannot resolve.",
						lines[0].substring(lines[0].indexOf("There")));
			}
			else if ((expectedResult.equals(NOT_FOUND))
					&& (lines[0].indexOf("Variable") != -1)) {
				assertEquals(
						"Variable ->" + variableName + "<- used in context where it must already exist.", lines[0].substring(lines[0].indexOf("Variable"))); //$NON-NLS-1$
			} else {
				assertTrue("Wrong Error message \"outPutResultIs \""
						+ actualResult + "\" expectedResultIs \""
						+ expectedResult + "\" variableName is \""
						+ variableName + "\"", false);
			}
		}
	}	


	/**
	 * @param stmts
	 * @param function
	 * @return
	 * @throws RecognitionException
	 * @throws TokenStreamException
	 */
	private String parserAction(String stmts, Function_c function)
			throws RecognitionException, TokenStreamException {
		OalLexer lexer = new OalLexer(new StringReader(stmts));
		OalParser parser = new OalParser(modelRoot, lexer);
		parser.m_oal_context = new Oal_validate(ContainerUtil.getContainer(function));
		function.setAction_semantics_internal(stmts);
		try {
			parser.action(function.getSync_id(), Oalconstants_c.FUNCTION_TYPE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return parser.m_output;
	}



}
