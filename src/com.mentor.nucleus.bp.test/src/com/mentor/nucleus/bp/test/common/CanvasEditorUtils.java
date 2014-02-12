package com.mentor.nucleus.bp.test.common;
//=====================================================================
//
//File:      $RCSfile: CanvasEditorUtils.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:21:31 $
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

import junit.framework.TestCase;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditorInput;



public class CanvasEditorUtils {

	static public void delete(GraphicalEditor editor, NonRootModelElement me) {
		getShape(me, true);

		editor.doDelete();
	}

	static public void rename(String newName)
	{
		Object selection = Selection.getInstance().getStructuredSelection()
        	.getFirstElement();
		String oldName = Cl_c.Getname(selection);

		Runnable query = RenameAction.getRenameQuery(selection, newName,
				oldName, false);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
        	.getDisplay().asyncExec(query);
	}
	static public void edit(GraphicalEditor editor, NonRootModelElement me) {
		Shape_c shape = getShape(me, false);
	   edit(editor, shape);
	}
    static public void edit(GraphicalEditor editor) {
        edit(editor,getRandomShape(editor));
    }
static public void edit(GraphicalEditor editor,Shape_c shape) {
	PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.activate(editor.getParentEditor());
    TestCase.assertNotNull(shape);
	
    editor.zoomAll();
    while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
    
	Point center = CanvasTestUtils.getShapeCenter(shape);
    center = CanvasTestUtils.convertToMouseCoor(center, editor.getModel());

		CanvasTestUtils.createMouseEvent(center.x, center.y, "MouseDown");
    CanvasTestUtils.createMouseEvent(center.x + 20, center.y + 20,
				"MouseMove");
    CanvasTestUtils.createMouseEvent(center.x + 20, center.y + 20,
				"MouseUp");
    Point center2 = CanvasTestUtils.getShapeCenter(shape);
    center2 = CanvasTestUtils.convertToMouseCoor(center2, editor.getModel());
    while(Display.getCurrent().readAndDispatch());
    TestCase.assertFalse("Shape not moved", center.equals(center2));
     
	}

	static public Shape_c getRandomShape(GraphicalEditor editor) {
		GraphicalEditorInput input = (GraphicalEditorInput) editor.getEditorInput();
		Model_c diagram = input.getInput();
		// pickup a shape
		return Shape_c.getOneGD_SHPOnR2(GraphicalElement_c
				.getManyGD_GEsOnR1(diagram));
	}

	static public Shape_c getShape(NonRootModelElement me, boolean select) {
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(me
				.getModelRoot().getId());
		GraphicalElement_c ge = CanvasTestUtils.getGraphicalElement(
				graphicsRoot, me);
		
		Shape_c shape = Shape_c.getOneGD_SHPOnR2(ge);
        TestCase.assertNotNull("Shape not found", shape);
        
		if(shape != null && select){
			Model_c diagram = Model_c.getOneGD_MDOnR1(ge, false);

			((GraphicalEditor) UITestingUtilities.getActiveEditor()).zoomAll();
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			
			Point center = CanvasTestUtils.getShapeCenter(shape);
            center = CanvasTestUtils.convertToMouseCoor(center, diagram);
			
			CanvasTestUtils.createMouseEvent(center.x, center.y, "MouseDown");
			CanvasTestUtils.createMouseEvent(center.x, center.y, "MouseUp");
			
			IStructuredSelection selection = (IStructuredSelection)Selection.getInstance().getSelection();
			TestCase.assertTrue("Model Element not selected", selection.getFirstElement() == me);
		}

		return Shape_c.getOneGD_SHPOnR2(ge);
	}

	static public GraphicalEditor openEditorWithShapeOf(NonRootModelElement me) {
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(me
				.getModelRoot().getId());
		GraphicalElement_c ge = CanvasTestUtils.getGraphicalElement(
				graphicsRoot, me);
		if (ge != null) {
			Model_c diagram = Model_c.getOneGD_MDOnR1(ge, false);
			Object containingME = Cl_c.Getinstancefromooa_id((Ooaofooa) me.getModelRoot(),
                    diagram.getOoa_id(),diagram.getOoa_type());
			CanvasTestUtils.openCanvasEditor(containingME);

			IEditorPart editor = UITestingUtilities.getActiveEditor();
			TestCase.assertTrue("Could not open canvas editor",
					(editor instanceof GraphicalEditor));
			TestCase.assertTrue("Could not open canvas editor",
					((GraphicalEditorInput) ((GraphicalEditor) editor)
							.getEditorInput()).getInput() == diagram);

			return (GraphicalEditor)editor;
		}

		return null;
	}
    
    static public boolean containsShape(IEditorPart editor, NonRootModelElement me){
        if(!(editor instanceof GraphicalEditor)){
            return false;
        }
        
        Model_c diagram = ((GraphicalEditorInput)  (editor).getEditorInput()).getInput();
        GraphicalElement_c[] ges = GraphicalElement_c.getManyGD_GEsOnR1(diagram);
        for (int i = 0; i < ges.length; i++) {
            if(ges[i].getRepresents() == me){
                return true;
            }
        }
        return false;
    }

    public static GraphicalEditor openEditorWithShapeOf(PersistableModelComponent pmc) {
        TreeItem item = ExplorerUtil.findRootTreeItem(pmc.getFullPath());
        return openEditorWithShapeOf((NonRootModelElement) item.getData());
    }
}
