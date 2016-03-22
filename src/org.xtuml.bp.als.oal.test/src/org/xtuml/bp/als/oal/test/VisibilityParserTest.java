//========================================================================
//
//File:      $RCSfile: VisibilityParserTest.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 04:52:48 $
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
package org.xtuml.bp.als.oal.test;

import java.io.StringReader;

import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.als.oal.OalLexer;
import org.xtuml.bp.als.oal.OalParser;
import org.xtuml.bp.als.oal.Oal_validate;
import org.xtuml.bp.core.Block_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Oalconstants_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

@RunWith(OrderedRunner.class)
public class VisibilityParserTest extends BaseTest {

	private static boolean initialized = false;
    public VisibilityParserTest() throws Exception {
    	super(null, null);
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if (!initialized){
			String modelName = "ParserVisibilityModel";
			TestingUtilities.importTestingProjectIntoWorkspace(modelName);
			project = ResourcesPlugin.getWorkspace().getRoot().getProject("ParserVisibilityModel");
			m_sys = getSystemModel(project.getName());

			String modelRootId = Ooaofooa.createModelRootId(project, modelName, true);
			modelRoot = Ooaofooa.getInstance(modelRootId, true);
		
			initialized  = true;
		}
	}
	
	@Test
	public void testInValidAttributeAccess(){
		String x = parseAction("create object instance obj of Classes;\nobj.attrA = 1;" , "CompB");
		String errMsg = ":2:5-9: ->attrA<- is not an attribute of class ->Classes<-.\nline 2:14: expecting TOK_EQUAL, found ';'\nline 2:15: expecting Semicolon, found 'null'\n";	
		assertEquals(errMsg, x);
		
	}
	@Test
	public void testValidAttributeAccess(){
		String x = parseAction("create object instance obj of Classes;\nobj.attrB = 1;" , "CompB");
		String errMsg = "";
		assertEquals(errMsg, x);
		
	}
	@Test
	public void testInValidInstanceOperationAccess(){
		String x = parseAction("create object instance obj of Classes;\nobj.instOpA();" , "CompB");
		String errMsg = ":2:5-11: Cannot find specified operation ->Classes::instOpA<-\nline 2:13: expecting Semicolon, found ')'\n";	
		assertEquals(errMsg, x);
		
	}
	@Test
	public void testValidInstanceOperationAccess(){
		String x = parseAction("create object instance obj of Classes;\nobj.instOpB();" , "CompB");
		String errMsg = "";
		assertEquals(errMsg, x);
		
	}
	@Test
	public void testInValidClassOperationAccess(){
		String x = parseAction("Classes::classOpA();" , "CompB");
		String errMsg = ":1:10-17: Cannot find bridge, operation or message ->Classes::classOpA<-\nline 1:19: expecting Semicolon, found ')'\n";
		assertEquals(errMsg, x);
		
	}
	@Test
	public void testValidClassOperationAccess(){
		String x = parseAction("Classes::classOpB();" , "CompB");
		String errMsg = "";
		assertEquals(errMsg, x);
		
	}
	@Test
	public void testInValidBridgeAccess(){
		String x = parseAction("External::bridgeA();" , "CompB");
		String errMsg = ":1:11-17: Cannot find bridge, operation or message ->External::bridgeA<-\nline 1:19: expecting Semicolon, found ')'\n";
		assertEquals(errMsg, x);
		
	}
	@Test
	public void testValidBridgeAccess(){
		String x = parseAction("External::bridgeB();" , "CompB");
		String errMsg = "";
		assertEquals(errMsg, x);
		
	}
	
	
	private String parseAction(String stmts, final String componentName) 
	{
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
    	Component_c[] comps = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkgs));
    	Component_c comp = null;
    	for (Component_c component : comps) {
			if (component.getName().equalsIgnoreCase("CompB"))
				comp = component;
		}
    	
		OalLexer lexer = new OalLexer(new StringReader(stmts));
		OalParser parser = new OalParser(modelRoot, lexer);
		assertTrue("Test component not found: " +componentName, comp != null);
		parser.m_oal_context = new Oal_validate(pkgs[0]);
		pkgs = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(comp));
		Package_c testPackage = null;
		for (Package_c pkg : pkgs) {
			if (pkg.getName().equalsIgnoreCase("Test"))
				testPackage = pkg;
		}
		Function_c testfunc = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPackage));
		assertTrue("Test function not found", testfunc != null);
		testfunc.setAction_semantics_internal(stmts);
		try {
			parser.action(testfunc.getSync_id(), Oalconstants_c.FUNCTION_TYPE);
		}catch (TokenStreamException e)
		{
			Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
			if ( e instanceof TokenStreamRecognitionException )
			{
				TokenStreamRecognitionException tsre = (TokenStreamRecognitionException)e;
				parser.reportError(tsre.recog);
			}
			else
			{
				fail("Token stream exception in parser");
			}
		}
		catch (RecognitionException e)
		{
			Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
			parser.reportError(e);
		}
		catch (InterruptedException ie){
		}
		return parser.m_output;
	}

}
