//========================================================================
//
//File:      $RCSfile: ActivityTestsGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:26 $
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

package com.mentor.nucleus.bp.core.test;

import java.lang.reflect.Method;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

/**
 * 
 * No Longer Needed
 * 
 * Contains tests that exercise the functionality of communication diagrams.
 */
public class ActivityTestsGenerics extends CanvasTest {
	private String test_id;

	private boolean generateResults = false;

	public ActivityTestsGenerics(String arg0) {
		super(null, arg0);
	}

	/**
	 * Whether the first test of this class is the one that's currently being
	 * run.
	 */
	private static boolean initialized = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		// if it's the first test of this class that's being setup
		if (!initialized) {
			loadProject("blankTest");
			initialized = true;
		}
	}

	/**
	 * Test creating activity under the system level
	 */
	public void testCreateInformalActivityUnderSystem() {
		test_id = "1";
		//		CanvasTestUtilities.openCanvasEditor(m_sys);
		//		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
		//				.getActiveEditor();
		//		assertNotNull(activeEditor);
		//		SequenceTests.createShapeInDiagram(activeEditor, new Point(300, 300),
		//				"Package");
		//		Package_c act = Package_c.getOneEP_PKGOnR1405(m_sys);
		//		assertNotNull("Activity could not be created under the system.", act);
		//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
		//				.getModelRoot();
		//		validateOrGenerateResults(activeEditor, generateResults);
	}

	/**
	 * Test creating activity under component package
	 */

	public void testCreateInformalActivityUnderComponentPackage() {
		//		test_id = "2";
		//		CanvasTestUtilities.openCanvasEditor(m_sys);
		//		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
		//				.getActiveEditor();
		//		assertNotNull(activeEditor);
		//		SequenceTests.createShapeInDiagram(activeEditor, new Point(600, 300),
		//				"Package");
		//		Package_c cp = Package_c.getOneCP_CPOnR4602(m_sys);
		//		assertNotNull("Unable to create test component package.", cp);
		//		CanvasTestUtilities.openCanvasEditor(cp);
		//		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		//		assertNotNull(activeEditor);
		//		SequenceTests.createShapeInDiagram(activeEditor, new Point(300, 300),
		//				"Activity Diagram");
		//		Package_c act = Package_c.getOneA_AOnR1114(cp);
		//		assertNotNull("Activity could not be created under component package.",
		//				act);
		//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
		//				.getModelRoot();
		//		validateOrGenerateResults(activeEditor, generateResults);
	}

	/**
	 * Test creating activity under component
	 */

	public void testCreateInformalActivityUnderComponent() {
		//		SequenceTests.resizeMainWindow();
		//		test_id = "3";
		//		Package_c cp = Package_c.getOneCP_CPOnR4602(m_sys);
		//		CanvasTestUtilities.openCanvasEditor(cp);
		//		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
		//				.getActiveEditor();
		//		assertNotNull(activeEditor);
		//		SequenceTests.createShapeInDiagram(activeEditor, new Point(300, 300),
		//				"Component");
		//		Component_c comp = Component_c.getOneC_COnR4604(cp);
		//		assertNotNull("Unable to create test component.", comp);
		//		CanvasTestUtilities.openCanvasEditor(comp);
		//		activeEditor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		//		((GraphicalEditPart) activeEditor.getGraphicalViewer().getContents())
		//				.getFigure().getUpdateManager().performUpdate();
		//		assertNotNull(activeEditor);
		//		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		//		SequenceTests.createShapeInDiagram(activeEditor, new Point(300, 300),
		//				"Activity Diagram");
		//		Package_c act = Package_c.getOneA_AOnR1115(comp);
		//		assertNotNull("Activity could not be created under component.", act);
		//		graphicsModelRoot = (Ooaofgraphics) activeEditor.getModel()
		//				.getModelRoot();
		//		validateOrGenerateResults(activeEditor, generateResults);
	}

	protected String getResultName() {
		return "Activity" + "_" + test_id;
	}

	public void setGenerateResults() {
		try {
			generateResults = true;
			setUp();
			Method methods[] = this.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().startsWith("test")) {
					methods[i].invoke(this, new Object[]{});
				}
			}
		} catch (Exception e) {
			System.out.println("Exception encountered by test result creator: "
					+ e);
		}
	}
}
