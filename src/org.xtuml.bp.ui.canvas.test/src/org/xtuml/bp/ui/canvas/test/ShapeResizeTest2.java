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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.ui.canvas.Graphnode_c;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

/**
 * Contains various tests involving the resizing of shapes on diagrams.
 */
public class ShapeResizeTest2 extends BaseTest
{
    /**
     * Constructor.
     */
    public ShapeResizeTest2(String name)
    {
        super(null, name);
    }

    public void setUp() throws Exception {
        super.setUp();
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