//=====================================================================
//
//File:      $RCSfile: ClassToStateDiagramNavigationTest.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.WorkbenchPart;

import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.NewInstanceStateMachineOnO_OBJAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.EditorUtil;
import com.mentor.nucleus.bp.core.util.OoaofooaUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Diagram_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.util.GraphNodeUtil;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

/**
 * Contains tests related to the ability to navigate from a class shape 
 * on a class diagram to the class's associated instance and class state 
 * diagrams, as well to the functionality of the marker-icons displayed
 * within a class shape to represent the associated state machines.
 */
public class ClassToStateDiagramNavigationTest extends BaseTest
{
    /**
     * That on which these tests operate.
     */
    private static Selection selection = Selection.getInstance();
    
    /**
     * That on which these tests operate.
     */
    private static GraphicalEditor editor;
    
    /**
     * That on which these tests operate.
     */
    private static Package_c subsystem;
    
    /**
     * Whether the first test of this class is the one that's currently being
     * run.
     */
    private static boolean firstTime = true;

    
    public ClassToStateDiagramNavigationTest(String arg0){
        super("Navigation", arg0);
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp()throws Exception
    {
        super.setUp();
        // if we haven't yet imported the model used for these tests
        if (firstTime)  {
            loadProject("odms");
            subsystem = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
				
				@Override
				public boolean evaluate(Object candidate) {
					return ((Package_c) candidate).getName().equals("Odms");
				}
			});
            
            // open a class diagram editor on the odms subsystem
            editor = CanvasTestUtils.openPackageCanvasEditor(subsystem);
            
            // if the diagram's zoom ratio is 1:1
            Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(editor.getModel());
            if (diagram.getZoom() == 1.0) {
                // make it something other than 1:1, to test if the scaling 
                // of the marker-icons for the state machines is working
                // properly
                editor.zoomOut();
            }
            firstTime = false;
        }
    }
    
    /**
     * Tests that performing an open-action on a model-class opens the 
     * correct state machine diagram, which depends upon whether the class 
     * has an associated instance and/or class state machine (or neither).
     */
    public void testOpenActionOnClassOpensStateDiagram()
    {
        // select a class which we know should only be displaying an 
        // instance state machine marker-icon
        String className = "Disk";
        ModelClass_c clazz = OoaofooaUtil.getClass(subsystem, className);
        UITestingUtilities.clearGraphicalSelection();
        UITestingUtilities.addElementToGraphicalSelection(clazz);
        
        // do an open-action on the class
        editor.getOpenAction().run();
        
        // check that the new current editor is for the correct 
        // instance state machine
        WorkbenchPart editorOpened = (WorkbenchPart) EditorUtil.getCurrentEditor();
        assertTrue("Instance state machine diagram wasn't opened", 
            editorOpened.getPartName().startsWith(
                className + ": Instance State Machine"));
        
        // close the editor just opened
        EditorUtil.closeEditor((IEditorPart) editorOpened);

        // select a class which we know should only be displaying an 
        // class state machine marker-icon
        className = "Disk Ownership";
        clazz = OoaofooaUtil.getClass(subsystem, className);
        UITestingUtilities.clearGraphicalSelection();
        UITestingUtilities.addElementToGraphicalSelection(clazz);
        
        // do an open-action on the class
        editor.getOpenAction().run();
        
        // check that the new current editor is for the correct 
        // class state machine
        editorOpened = (WorkbenchPart) EditorUtil.getCurrentEditor();
        assertTrue("Class state machine diagram wasn't opened", 
            editorOpened.getPartName().startsWith(
                className + ": Class State Machine"));
        
        // close the editor just opened
        EditorUtil.closeEditor((IEditorPart) editorOpened);
        
        // select a class which we know should be displaying no 
        // state machine marker-icons
        className = "Online Disk";
        clazz = OoaofooaUtil.getClass(subsystem, className);
        UITestingUtilities.clearGraphicalSelection();
        UITestingUtilities.addElementToGraphicalSelection(clazz);
        
        // do an open-action on the class
        editor.getOpenAction().run();
        
        // check that the new current editor is the correct 
        // description editor
        editorOpened = (WorkbenchPart) EditorUtil.getCurrentEditor();
        assertTrue("Class description editor was not opened.", 
            editorOpened.getPartName().startsWith(
                className + ": Model Class Description"));
        
        // close the editor just opened
        EditorUtil.closeEditor((IEditorPart) editorOpened);
        
        // create a state machine
        selection.clear(); selection.addToSelection(clazz);
        NewInstanceStateMachineOnO_OBJAction action = new NewInstanceStateMachineOnO_OBJAction();
        action.run(null);
        
        // undo the creation of the state machine above
        // and verify that the state machine is removed
        clazz.getTransactionManager().getUndoAction().run();
        
        InstanceStateMachine_c machine = InstanceStateMachine_c.getOneSM_ISMOnR518(clazz);
        
        // verify that no instance state machine exists for the class
        assertTrue("The creation of a state machine was not undone.", machine == null);
    }
    
    /**
     * Tests that double-clicking on a marker-icon representing a class's
     * associated instance or state machine opens the correct state diagram.
     * Also tests whether the tooltip text and context menu shown 
     * for the marker-icons are correct. 
     */
    public void testDoubleClickOnMarkerIconOpensStateDiagram()
    {
        // find a class which we know is displaying an 
        // instance state machine marker-icon
        String className = "Disk";
        ModelClass_c clazz = OoaofooaUtil.getClass(subsystem, className);
        
        // double-click the marker-icon
        Graphnode_c node = GraphNodeUtil.getNode(graphicsModelRoot, clazz);
        AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) UITestingUtilities.getEditorPartFor(Shape_c.getOneGD_SHPOnR19(node));
        int iconSize = Ooaofgraphics.Geticonsize(graphicsModelRoot);
        editor.zoomAll();
        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        Point point = ((IFigure) part.getFigure().getChildren().get(0)).getBounds().getLocation().getSWTPoint();
        point.x = point.x + (iconSize / 2);
        point.y = point.y + (iconSize / 2);
        point = CanvasTestUtils.convertToMouseCoor(point, editor.getModel());
        UITestingUtilities.doMouseDoubleClick(point.x, point.y);
        
        // check that the editor that now has focus is for the correct 
        // instance state machine
        WorkbenchPart editorOpened = (WorkbenchPart) EditorUtil.getCurrentEditor();
        assertTrue("Instance state machine diagram wasn't opened", 
            editorOpened.getPartName().startsWith(
                className + ": Instance State Machine"));
        
        // close the editor just opened
        EditorUtil.closeEditor((IEditorPart) editorOpened);
        
        // check that there is tooltip text for the marker-icon
        Label label = (Label) ((IFigure) part.getFigure().getChildren().get(0)).getToolTip();
        assertTrue("Tooltip text is absent or incorrect", 
            label.getText().indexOf("Instance State Machine Diagram") >= 0);
        
        // find a class which we know is displaying a class 
        // state machine marker-icon
        className = "Disk Ownership";
        clazz = OoaofooaUtil.getClass(subsystem, className);
        
        // double-click the marker-icon
        node = GraphNodeUtil.getNode(graphicsModelRoot, clazz);
        part = (AbstractGraphicalEditPart) UITestingUtilities.getEditorPartFor(Shape_c.getOneGD_SHPOnR19(node));

        UITestingUtilities.clearGraphicalSelection();
        editor.zoomAll();
        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        
        point = ((IFigure) part.getFigure().getChildren().get(0)).getBounds().getLocation().getSWTPoint();
        point.x = point.x + (iconSize / 2);
        point.y = point.y + (iconSize / 2);
        point = CanvasTestUtils.convertToMouseCoor(point, editor.getModel());
        UITestingUtilities.doMouseDoubleClick(point.x, point.y);
        
        // check that the editor that now has focus is for the correct 
        // instance state machine
        editorOpened = (WorkbenchPart) EditorUtil.getCurrentEditor();
        assertTrue("Class state machine diagram wasn't opened", 
            editorOpened.getPartName().startsWith(
                className + ": Class State Machine"));
        
        // close the editor just opened
        EditorUtil.closeEditor((IEditorPart) editorOpened);

        // check that there is tooltip text for the marker-icon
        label = (Label) ((IFigure) part.getFigure().getChildren().get(0)).getToolTip();
        assertTrue("Tooltip text is absent or incorrect", 
            label.getText().indexOf("Class State Machine Diagram") >= 0);
    }

}
