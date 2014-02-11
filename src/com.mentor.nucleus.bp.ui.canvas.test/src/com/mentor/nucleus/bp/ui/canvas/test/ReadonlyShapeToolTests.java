//=====================================================================
//
//File:      $RCSfile: ReadonlyShapeToolTests.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:43:50 $
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

package com.mentor.nucleus.bp.ui.canvas.test;


import com.mentor.nucleus.bp.test.common.BaseTest;

public class ReadonlyShapeToolTests extends BaseTest {

//	public ReadonlyShapeToolTests(String arg0) {
//		super("com.mentor.nucleus.bp.canvas.ui.test", arg0);
//	}
//
//	public void setUp() throws Exception {
//        ensureAvailableAndLoaded("Models", "ContextMenuTests", false, false);
//		}
//	public void testPartiallyDrawnShapeCleared() {
//		// open the domain diagram
//		Domain_c dom = Domain_c.DomainInstance(modelRoot);
//        // set file to read-write
//        IFile modelFile = dom.getFile();
//        TestUtil.changeFileReadonlyStatus(false, modelFile);
//		CanvasTestUtils.openDiagramEditor(dom);
//		
//		// get the CanvasEditor instance
//		CanvasEditor ce = (CanvasEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		
//		ShapeTool_c shptool = CanvasTestUtils.getShapeTool(graphicsModelRoot, "Subsystem");
//		CanvasTestUtils.controlShapeToolState(graphicsModelRoot, shptool, "activate");
//		
//		// Begin shape creation
//		CanvasTestUtils.doMouseMove(2000, 2000);
//		CanvasTestUtils.doMousePress(2000, 2000);
//		CanvasTestUtils.doMouseMove(2010, 2010);
//		
//		assertTrue("Shape outline was never created.", ((ce.ptCanvas.getSelrecth() != 0) && (ce.ptCanvas.getSelrectw() != 0)));
//		
//		// set the model file to read only
//		TestUtil.changeFileReadonlyStatus(true, modelFile);
//		
//		CanvasTestUtils.doMouseMove(2010, 2020);
//		
//		assertTrue("Shape outline was not cleared.", ((ce.ptCanvas.getSelrecth() == 0) && (ce.ptCanvas.getSelrectw() == 0)));
//		
//		assertTrue("Expected state 1, got state " + shptool.get_current_state(), shptool.get_current_state() == 1 );
//	}
//	public void testDeactivateInStateInitializingState() {
//		// open the domain diagram
//		Domain_c dom = Domain_c.DomainInstance(modelRoot);
//        
//        // set file to read-write
//        IFile modelFile = dom.getFile();
//        TestUtil.changeFileReadonlyStatus(false, modelFile);
//		CanvasTestUtils.openDiagramEditor(dom);	
//					
//		ShapeTool_c shptool = CanvasTestUtils.getShapeTool(graphicsModelRoot, "Subsystem");
//		CanvasTestUtils.controlShapeToolState(graphicsModelRoot, shptool, "activate");
//		
//		// Get to Intialize Shape state
//		CanvasTestUtils.doMouseMove(2000, 2000);
//		CanvasTestUtils.doMousePress(2000, 2000);
//		
//		// set the model file to read only
//		TestUtil.changeFileReadonlyStatus(true, modelFile);
//		
//		CanvasTestUtils.doMouseMove(2010, 2010);
//		
//		assertTrue("Expected state 1, got state " + shptool.get_current_state(), shptool.get_current_state() == 1 );
//	}
}
