//=====================================================================
//
//File:      $RCSfile: ShapeResizeTest.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;

/**
 * Contains various tests involving the resizing of shapes on diagrams.
 */
public class ShapeResizeTest extends BaseTest
{
    private static boolean firstTest = true;
    /**
     * Constructor.
     */
    public ShapeResizeTest(String name)
    {
        super(null, name);
    }

    public void setUp() throws Exception {
        
        super.setUp();
        if(firstTest){
            // import the model used for this test
            loadProject("Loop Disconnect Telephone");
            firstTest = false;
        }
        
    }
    
    /**
     * Resizes a state on a statechart diagram using one of its
     * four corners, then checks all attached connectors to make sure
     * their ends have been adjusted to still lie on the state's boundary.
     */
    public void testConnectorsAttachedAfterShapeResize()
    {
        // open an existing state diagram
        CanvasTestUtils.openInstanceStateDiagram(modelRoot, "Dialing Monitor");
 
        // find the shape of the state that we're going to resize
        Shape_c shape = CanvasTestUtils.getModelStateShape(
            modelRoot, "Detecting Digit Line Low");
        final Point corner = CanvasTestUtils.getShapeSECorner(shape);
        
        // find the connector which starts at the south side of the state
        Connector_c southConn = Connector_c.ConnectorInstance(graphicsModelRoot, 
            new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((Connector_c)candidate).Getstarty() == corner.y;
            }
        });
        
        // find the connector which ends at the east side of the state
        Connector_c eastConn = Connector_c.ConnectorInstance(graphicsModelRoot, 
            new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((Connector_c)candidate).Getendx() == corner.x;
            }
        });
        
        // click within the state, to select it
        GraphicalEditor editor = CanvasTestUtils.getCanvasEditor(
            "Dialing Monitor: Instance State Machine");
        editor.zoomAll();
        Model_c canvas = editor.getModel();
        Point mouse = CanvasTestUtils.convertToMouseCoor(corner, canvas);
        final int offsetIntoShape = 20;
        CanvasTestUtils.doMousePress(
            mouse.x - offsetIntoShape, mouse.y - offsetIntoShape);
        CanvasTestUtils.doMouseRelease(
            mouse.x - offsetIntoShape, mouse.y - offsetIntoShape);

        // move the cursor over the state's SE corner, 
        // to bring up the resize cursor
        CanvasTestUtils.doMouseMove(mouse.x, mouse.y);

        // resize the state by dragging its SE corner in steps such that 
        // most of its height is removed
        CanvasTestUtils.doMousePress(mouse.x, mouse.y);
        
        final int stepSize = 10;
        int dx = -20, dy = -stepSize;
        final Point nwcorner = CanvasTestUtils.getShapeNWCorner(shape);
        final Point nwmouse = CanvasTestUtils.convertToMouseCoor(nwcorner, canvas);
        final int mouseHeight = mouse.y - nwmouse.y;
        int moves = 0;
        
        for (; dy > -mouseHeight; dy -= stepSize) {
            moves += 1;
            CanvasTestUtils.doMouseMove(mouse.x + dx, mouse.y + dy);
        }
  
        // now, drag the same corner such that some of the height is restored,
        // and then drag it again to remove the restored height; this 
        // bit will exercise the fix put into place for issue 1245
        for (int i = 0; i < moves; i++, dy += stepSize) {
            CanvasTestUtils.doMouseMove(mouse.x + dx, mouse.y + dy);
        }
        dy -= stepSize;
        CanvasTestUtils.doMouseMove(mouse.x + dx, mouse.y + dy);

        // make sure the total drag had some effect, to help validate this test
        CanvasTestUtils.doMouseRelease(mouse.x + dx, mouse.y + dy);
        Point draggedCorner = CanvasTestUtils.getShapeSECorner(shape);
        assertTrue("Drag had no effect", !draggedCorner.equals(corner));
        
        // check that the two connectors' ends are still on the boundary 
        // of the state
        assertTrue("Transition end was detached from state being resized.",
            southConn.Getstarty() == draggedCorner.y
            && eastConn.Getendx() == draggedCorner.x);
    }
    /**
     * Tests that undoing a shape resize does not cause following
     * resizes to operate incorrectly
     * @throws CoreException 
     */
    public void testResizeWorksAfterResizeUndo() throws CoreException
    {
        // import the model used for this test
    	loadProject("odms");
        
        Package_c ss = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((Package_c) candidate).getName().equals("Odms");
            }
        });
        assertNotNull(ss);
        CanvasTestUtils.openCanvasEditor(ss);

        GraphicalEditor editor = ((ModelEditor) PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage().getActiveEditor())
                .getGraphicalEditor();
        
        editor.zoomAll();
        
        Shape_c shape = CanvasTestUtils.getModelClassShape(modelRoot, "Disk");
        
        Point center = CanvasTestUtils.getShapeCenter(shape);
        Point mouse = CanvasTestUtils.convertToMouseCoor(center, editor.getModel());
        
        // select the symbol
        CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
        CanvasTestUtils.doMousePress(mouse.x, mouse.y);
        CanvasTestUtils.doMouseRelease(mouse.x, mouse.y);
        
        Point corner = CanvasTestUtils.getShapeSECorner(shape);
        
        mouse = CanvasTestUtils.convertToMouseCoor(corner, editor.getModel());

        // move the cursor over the shape's SE corner, 
        // to bring up the resize cursor
        CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
        
        // resize the shape
        CanvasTestUtils.doMousePress(mouse.x, mouse.y);
        CanvasTestUtils.doMouseMove(mouse.x + 20, mouse.y + 20);
        CanvasTestUtils.doMouseRelease(mouse.x + 20, mouse.y + 20);
    
        // undo the resize
        ss.getTransactionManager().getUndoAction().run();

        // rezoom all, as the undo will reset the scrolling
        editor.zoomAll();
        
        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        
        // try moving another shape
        shape = CanvasTestUtils.getModelClassShape(modelRoot, "Online Disk");
        float width = Graphnode_c.getOneDIM_NDOnR19(shape).getWidth();
        
        // select the shape
        center = CanvasTestUtils.getShapeCenter(shape);
        mouse = CanvasTestUtils.convertToMouseCoor(center, editor.getModel());
        
        CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
        CanvasTestUtils.doMousePress(mouse.x, mouse.y);
        CanvasTestUtils.doMouseRelease(mouse.x, mouse.y);
        
        // move the other shape
        corner = CanvasTestUtils.getShapeSECorner(shape);
        mouse = CanvasTestUtils.convertToMouseCoor(corner, editor.getModel());
        
        CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
        CanvasTestUtils.doMousePress(mouse.x, mouse.y);
        CanvasTestUtils.doMouseMove(mouse.x + 20, mouse.y);
        CanvasTestUtils.doMouseRelease(mouse.x + 20, mouse.y);
        
        float newWidth = Graphnode_c.getOneDIM_NDOnR19(shape).getWidth();
        
        assertTrue("Shape was unable to resize after undoing a resize.", newWidth != width );
    }
    /**
     * Tests that shape resizing still works when the user clicks over a
     * hotspot but produces no mouse movement
     */
    public void testResizeWorksAfterCancelledMove()
    {
    	Package_c ss = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((Package_c) candidate).getName().equals("Odms");
            }
        });
        assertNotNull(ss);
        CanvasTestUtils.openCanvasEditor(ss);

        GraphicalEditor editor = ((ModelEditor) PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage().getActiveEditor())
                .getGraphicalEditor();
        editor.zoomAll();
        
        Shape_c shape = CanvasTestUtils.getModelClassShape(modelRoot, "Disk");
        
        Point center = CanvasTestUtils.getShapeCenter(shape);
        Point mouse = CanvasTestUtils.convertToMouseCoor(center, editor.getModel());
        
        // select the symbol
        CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
        CanvasTestUtils.doMousePress(mouse.x, mouse.y);
        CanvasTestUtils.doMouseRelease(mouse.x, mouse.y);
        
        Point corner = CanvasTestUtils.getShapeSECorner(shape);
        
        mouse = CanvasTestUtils.convertToMouseCoor(corner, editor.getModel());

        // move the cursor over the shape's SE corner, 
        // to bring up the resize cursor
        CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
        
        // do a mouse press/release
        CanvasTestUtils.doMousePress(mouse.x, mouse.y);
        CanvasTestUtils.doMouseRelease(mouse.x, mouse.y);
        
        corner = CanvasTestUtils.getShapeNWCorner(shape);
        mouse = CanvasTestUtils.convertToMouseCoor(corner, editor.getModel());

        // move the cursor over the shape's NW corner, 
        // to bring up the resize cursor
        CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
        
        // do a mouse press/release
        CanvasTestUtils.doMousePress(mouse.x, mouse.y);
        CanvasTestUtils.doMouseRelease(mouse.x, mouse.y);

        // try to resize another shape
        shape = CanvasTestUtils.getModelClassShape(modelRoot, "Online Disk");
        float width = Graphnode_c.getOneDIM_NDOnR19(shape).getWidth();
        
        // select the shape
        center = CanvasTestUtils.getShapeCenter(shape);
        mouse = CanvasTestUtils.convertToMouseCoor(center, editor.getModel());
        
        CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
        CanvasTestUtils.doMousePress(mouse.x, mouse.y);
        CanvasTestUtils.doMouseRelease(mouse.x, mouse.y);
        
        // move the other shape
        corner = CanvasTestUtils.getShapeSECorner(shape);
        mouse = CanvasTestUtils.convertToMouseCoor(corner, editor.getModel());
        
        CanvasTestUtils.doMouseMove(mouse.x, mouse.y);
        CanvasTestUtils.doMousePress(mouse.x, mouse.y);
        CanvasTestUtils.doMouseMove(mouse.x + 20, mouse.y);
        CanvasTestUtils.doMouseRelease(mouse.x + 20, mouse.y);
        
        float newWidth = Graphnode_c.getOneDIM_NDOnR19(shape).getWidth();
        
        assertTrue("Shape was unable to resize after undoing a resize.", newWidth != width );
    }
}