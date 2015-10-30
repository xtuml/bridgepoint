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

package org.xtuml.bp.ui.canvas.test;

import org.eclipse.swt.graphics.Point;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

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
            "Dialing Monitor");
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

}