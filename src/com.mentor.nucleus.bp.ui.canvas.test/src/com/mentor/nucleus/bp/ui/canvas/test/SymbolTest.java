//=====================================================================
//
//File:      $RCSfile: SymbolTest.java,v $
//Version:   $Revision: 1.31 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================
//
//This class is responsible for performing the automatic test of the
//Graphics Domain by showing specially prepared canvases that show
//unusually extreme class, package and state shapes and comparing them
//with previously created test result data. Examples of extreme shapes
//very wide and thin or very tall and thin. This exercises the label
//placement and truncation algorithms. The test data is created by the
//CanvasTestResultCreator class defined elsewhere in this package. The
//test creator class also creates a JPEG for each canvas so that the
//sample results may be viewed by a human. 
//
package com.mentor.nucleus.bp.ui.canvas.test;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class SymbolTest extends CanvasTest {
	public SymbolTest(String arg0) {
		super(null, arg0);
	}

	public static boolean generateResults = false;
	private static String root = "canvastest";
	private static String test_id = "";

	public void setUp() throws Exception {
		setModelName("canvastest");
		super.setUp();

		loadProject("canvastest");
	}

	protected String getResultName() {
		return root + "_" + test_id;
	}

	public void testPackageDiagram() throws Exception {
		test_id = "1";
		// domain id's change each time someone creates a
		// workspace from a .bak file in BP 6.1
		Package_c d2 = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Class Diagram Test Subsystem");
			}
		});
		validateOrGenerateResultsGenerics(UITestingUtilities.getGraphicalEditorFor(d2, true), generateResults);
	}

	public void testComponentDiagram() throws Exception {
		test_id = "2";
		Component_c comp = Component_c.ComponentInstance(modelRoot);
		validateOrGenerateResultsGenerics(UITestingUtilities.getGraphicalEditorFor(comp, true), generateResults);
	}

	public void testInstanceStateMachineDiagram() throws Exception {
		test_id = "3";
		InstanceStateMachine_c ism = InstanceStateMachine_c.InstanceStateMachineInstance(modelRoot);
		validateOrGenerateResultsGenerics(UITestingUtilities.getGraphicalEditorFor(ism, true), generateResults);
	}
	
	public void testClassStateMachineDiagram() throws Exception {
		test_id = "4";
		ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		validateOrGenerateResultsGenerics(UITestingUtilities.getGraphicalEditorFor(csm, true), generateResults);
	}
	
	// Testing if Canvas Plugin loads the elemType values properly
	private class ElementSpecification_forName_c implements
			ClassQueryInterface_c {
		private String m_name;

		ElementSpecification_forName_c(String p_name) {
			m_name = p_name;
		}

		public boolean evaluate(Object candidate) {
			ElementSpecification_c selected = (ElementSpecification_c) candidate;
			return (selected.getName().equals(m_name));
		}
	}

	// Testing if Canvas Plugin loads the modelType values properly
	private class ModelSpecification_forName_c implements ClassQueryInterface_c {
		private String m_name;

		ModelSpecification_forName_c(String p_name) {
			m_name = p_name;
		}

		public boolean evaluate(Object candidate) {
			ModelSpecification_c selected = (ModelSpecification_c) candidate;
			return (selected.getName().equals(m_name));
		}
	}

	public void test_CanvasPlugin() {
		// *** symbol.elemType ***
		specifyElementType("Package", 108);
		specifyElementType("Component", 107);
		specifyModelType("Instance State Machine", 8);
		specifyModelType("Class State Machine", 10);
	}

	public void specifyElementType(String name, int value) {
		ElementSpecification_c es = ElementSpecification_c
				.ElementSpecificationInstance(
						Ooaofgraphics.getDefaultInstance(),
						new ElementSpecification_forName_c(name));
		assertNotNull(es);
		assertEquals(value, es.getOoa_type());
	}

	public void specifyModelType(String name, int value) {
		ModelSpecification_c ms = ModelSpecification_c
				.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ModelSpecification_forName_c(name));
		assertNotNull(ms);
		assertEquals(value, ms.getModel_type());
	}
}
