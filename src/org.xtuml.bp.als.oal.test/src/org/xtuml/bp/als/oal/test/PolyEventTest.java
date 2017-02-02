//========================================================================
//
//File:      $RCSfile: PolyEventTest.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 04:52:47 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
import java.util.UUID;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.als.oal.OalLexer;
import org.xtuml.bp.als.oal.OalParser;
import org.xtuml.bp.als.oal.Oal_validate;
import org.xtuml.bp.core.Block_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.Oalconstants_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.util.ContainerUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

@RunWith(OrderedRunner.class)
public class PolyEventTest extends BaseTest {
	
	private static String projectName = "PolyEventTest";

	static private boolean initialized = false;
	
	public PolyEventTest() throws Exception {
		super(null,  null);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		if (!initialized){
			TestingUtilities .importTestingProjectIntoWorkspace("PolyEventTest");
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
			.getProject(projectName);
			

			project.getName();
			m_sys = getSystemModel(project.getName());

			m_sys = SystemModel_c.SystemModelInstance(Ooaofooa
					.getDefaultInstance(), new ClassQueryInterface_c() {

				public boolean evaluate(Object candidate) {
					return ((SystemModel_c) candidate).getName().equals(
							project.getName());
				}

			});

			CorePlugin.enableParseAllOnResourceChange();

			TestingUtilities.allowJobCompletion();
			
			String modelRootId = Ooaofooa.createModelRootId(project, "PolyEventTest", true);
			modelRoot = Ooaofooa.getInstance(modelRootId, true);
			
			initialized = true;
		}
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testSendNonPolyEventToSubType(){
		String x = parseAction("create object instance super of SuperType;" +
				"\ncreate object instance sub of SubType1;" +
				"\ngenerate SuperType1:SuperEvent to sub;");
		
		String errMsg = ":3:35-37: Supertype event ->SuperType1<- is not polymorphic\n" +
				"line 3:39: expecting Semicolon, found 'null'\n";
		assertEquals(errMsg, x);

	}
	@Test
	public void testValidPolyEventGeneration(){
		String x = parseAction("create object instance super of SuperType;" +
				"\ncreate object instance sub of SubType1; " + 
				"\ngenerate SuperType2:PolyEvent to sub;");
		String errMsg = "";
		assertEquals(errMsg, x);
		
	}
	@Test
	public void testSendPolyEventToSuperType(){
		String x = parseAction("create object instance super of SuperType;" +
				"\ncreate object instance sub of SubType1; " + 
				"\ngenerate SuperType2:PolyEvent to super;");
		String errMsg = "";
		assertEquals(errMsg, x);
		
	}

	private String parseAction(String stmts) { 
		{
			OalLexer lexer = new OalLexer(new StringReader(stmts));
			OalParser parser = new OalParser(modelRoot, lexer);
			UUID actID = Gd_c.Null_unique_id();


			Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1405(m_sys);
			assertNotNull(pkgs);
			Package_c modelPkg = null;
			for (Package_c pkg : pkgs) {
				if(pkg.getName().equalsIgnoreCase("Model")){
					modelPkg = pkg;
					break;
				}
			}
			Function_c func = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(modelPkg));

			parser.m_oal_context = new Oal_validate(ContainerUtil.getContainer(func));
			
			actID = func.getSync_id();
			func.setAction_semantics_internal(stmts);
			
			try {
				parser.action(actID, Oalconstants_c.FUNCTION_TYPE);

			}
			catch (TokenStreamException e)
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

}
