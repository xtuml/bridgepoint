//=====================================================================
//
//File:      $RCSfile: ReadonlySelectionToolTests.java,v $
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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;

public class ReadonlySelectionToolTests extends BaseTest {

	public ReadonlySelectionToolTests(String arg0) {
        super("com.mentor.nucleus.bp.canvas.ui.test", arg0);
	}

    static private boolean firstTime = true;
	public void setUp() throws Exception {
        if (firstTime) {
            PersistableModelComponent domain_pmc = ensureAvailableAndLoaded("Models", "ContextMenuTests", false, false);
            setResourceToReadonly(domain_pmc);
            firstTime = false;
		}
	}
	public void testSelectionToolMoveNotAllowed() {
		// open the domain diagram
		Domain_c dom = Domain_c.DomainInstance(modelRoot);
		CanvasTestUtils.openDiagramEditor(dom);
		
		// get the CanvasEditor instance
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		
		// get the center of the Datatypes shape
		Shape_c shape = CanvasTestUtils.getModelDTPShape(modelRoot, "Datatypes");
		Point center = CanvasTestUtils.getShapeCenter(shape);
		Point mouse = CanvasTestUtils.convertToMouseCoor(center, ce.getModel());
		
		// Try to drag the shape right by 100 pixels
		CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
		CanvasTestUtils.doMousePress(mouse.x, mouse.y);
		CanvasTestUtils.doMouseMove(mouse.x + 100, mouse.y);
		CanvasTestUtils.doMouseRelease(mouse.x + 100, mouse.y);
		
		// verify that the center has not moved
		Point centerAfterMove = CanvasTestUtils.getShapeCenter(shape);
		assertTrue("The shape was moved while the model was readonly.", (center.x == centerAfterMove.x));
		assertTrue("The shape was moved while the model was readonly.", (center.y == centerAfterMove.y));
	}
	public void testSelectionToolSelectionAllowed() {
		// clear the current selection to prepare for test
		Selection.getInstance().clear();
		
		// open the domain diagram
		Domain_c dom = Domain_c.DomainInstance(modelRoot);
		CanvasTestUtils.openDiagramEditor(dom);
		
		// get the CanvasEditor instance
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		
		// get the center of the Datatypes shape
		Shape_c shape = CanvasTestUtils.getModelDTPShape(modelRoot, "Datatypes");
		Point center = CanvasTestUtils.getShapeCenter(shape);
		Point mouse = CanvasTestUtils.convertToMouseCoor(center, ce.getModel());
		
		// Select the shape
		CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
		CanvasTestUtils.doMousePress(mouse.x, mouse.y);
		CanvasTestUtils.doMouseRelease(mouse.x, mouse.y);
		
		// get the current selection
		IStructuredSelection structuredSelection = Selection.getInstance().getStructuredSelection();
		
		assertTrue("Selection was not allowed.", (structuredSelection.getFirstElement() instanceof DataTypePackage_c));
	}
	public void testShapeResizeIsNotAllowed() {
		// open the domain diagram
		Domain_c dom = Domain_c.DomainInstance(modelRoot);
		CanvasTestUtils.openDiagramEditor(dom);
		
		// get the CanvasEditor instance
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		
		// get the center of the Datatypes shape
		Shape_c shape = CanvasTestUtils.getModelDTPShape(modelRoot, "Datatypes");
		Point corner = CanvasTestUtils.getShapeSECorner(shape);
		Point mouse = CanvasTestUtils.convertToMouseCoor(corner, ce.getModel());
		
		// Try to resize the shape
		CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
		CanvasTestUtils.doMousePress(mouse.x, mouse.y);
		CanvasTestUtils.doMouseMove(mouse.x - 100, mouse.y);
		CanvasTestUtils.doMouseRelease(mouse.x - 100, mouse.y);
		
		// verify that the shape SE corner did not move
		Point cornerAfterMove = CanvasTestUtils.getShapeSECorner(shape);
		
		assertTrue("Resize was allowed on readonly model.", (corner.x == cornerAfterMove.x));
		assertTrue("Resize was allowed on readonly model.", (corner.y == cornerAfterMove.y));
	}
}
