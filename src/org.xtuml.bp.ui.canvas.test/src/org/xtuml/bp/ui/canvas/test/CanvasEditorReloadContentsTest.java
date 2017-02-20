//=====================================================================
//
//File:      $RCSfile: CanvasEditorReloadContentsTest.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/05/10 05:41:50 $
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

package org.xtuml.bp.ui.canvas.test;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

import junit.framework.TestCase;

/**
 * Performs tests related to the updating of an editor's 
 * contents when a model is reloaded from disk.
 */
@RunWith(OrderedRunner.class)
public class CanvasEditorReloadContentsTest extends CanvasTest
{
    /**
     * The name of the test domain used during these tests.
     */
    private static final String testModelName = "odms";

    /**
     * Constructor.
     */
    public CanvasEditorReloadContentsTest()
    {
        super(null, null);
    }
    
    /* (non-Javadoc)
     * @see org.xtuml.bp.ui.canvas.test.CanvasTest#getResultName()
     */
    protected String getResultName()
    {
        return null;
    }
    
    @Override
    @Before
	public void setUp() throws Exception {
    	super.setUp();
    	Ooaofooa.setPersistEnabled(true);
    }

    @After
	public void tearDown() throws Exception {
    	super.tearDown();
    	Ooaofooa.setPersistEnabled(false);
    }
    
    /**
     * For issue 780.  Drags a shape on a domain diagram, then copies
     * over the domain's changed file with the original version,
     * checking to see that the shape reverts to its original position
     * due to the domain being reloaded.
     * @throws IOException 
     * @throws CoreException 
     */
    @Test
	public void testEditorUpdatesOnReload() throws CoreException, IOException
    {
        // make sure the user isn't prompted to do a parse all
        CorePlugin.enableParseAllOnResourceChange();

        // setup the test project and model
        loadProject(testModelName);
        
        BaseTest.dispatchEvents(0);

        // find a subsystem's shape that is in the canvas
        Package_c subsystem = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Odms");
			}
		});
        // open an editor on the test domain
        GraphicalEditor editor = UITestingUtilities.getGraphicalEditorFor(subsystem, false);
        Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(modelRoot.getId());
        GraphicalElement_c element = 
        	CanvasTestUtils.getGraphicalElement(graphicsRoot, subsystem);
        Shape_c shape = Shape_c.getOneGD_SHPOnR2(element);
        UITestingUtilities.addElementToGraphicalSelection(subsystem);
        // drag the subsystem's shape a bit, then let go to get the 
        // change persisted
        Model_c canvas = editor.getModel();
        Point oldCenter = CanvasTestUtils.getShapeCenter(shape);
        Point mouse = CanvasTestUtils.convertToMouseCoor(oldCenter, canvas);
        CanvasTestUtils.createMouseEvent(
            mouse.x, mouse.y, "MouseDown");
        int dx = 30, dy = 30;
        CanvasTestUtils.createMouseEvent(
            mouse.x + dx, mouse.y + dy, "MouseMove");
        CanvasTestUtils.createMouseEvent(
            mouse.x + dx, mouse.y + dy, "MouseUp");
        Point draggedCenter = CanvasTestUtils.getShapeCenter(shape);
        draggedCenter = CanvasTestUtils.convertToMouseCoor(draggedCenter, canvas);
        assertTrue("Drag had no effect", !draggedCenter.equals(oldCenter));
        BaseTest.dispatchEvents(0);
        // restore from local history
        IFile file = ((NonRootModelElement) Model_c.getOneGD_MDOnR1(element).getRepresents()).getFile();
        IFileState[] history = file.getHistory(new NullProgressMonitor());
        file.setContents(history[0], IFile.FORCE, new NullProgressMonitor());
        
        // find the same subsystem's shape (once again) that
        // is presumably in the editor's new canvas (as the shape
        // would not be created if there were not also a new canvas
        // created)
        subsystem = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Odms");
			}
		});
        element = CanvasTestUtils.getGraphicalElement(graphicsRoot, subsystem);
        shape = Shape_c.getOneGD_SHPOnR2(element);
        assertNotNull("No new shape (and therefore, canvas) after reload", shape);
        
        // check that the shape reverted to its original position 
        // due to the reload
        Point finalCenter = CanvasTestUtils.getShapeCenter(shape);
        assertTrue("Shape position did not revert after reload",
            finalCenter.equals(oldCenter));
    }

    /**
     * For issue MFP issue 54.  If the data for a state machine changes,
     * make sure the canvas editor doesn't close.
     * @throws IOException 
     * @throws CoreException 
     */
    @Test
	public void testISMEditorStaysOpenOnReload() throws CoreException, IOException
    {
		// setup the test project and model
		loadProject(testModelName);

		// open an editor on the test domain
		ModelClass_c mc = ModelClass_c.ModelClassInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						if (((ModelClass_c) candidate).getName().equals("Disk"))
							return true;
						else
							return false;
					}
        } );
    	InstanceStateMachine_c uut = InstanceStateMachine_c.getOneSM_ISMOnR518(mc);
    	TestCase.assertNotNull(uut);
    	CanvasTestUtils.openCanvasEditor(uut);
    	GraphicalEditor editor = CanvasTestUtils.getCanvasEditor("Disk");

    	BaseTest.dispatchEvents(0);
        // find a state's shape that is in the canvas
        StateMachineState_c state = StateMachineState_c.getOneSM_STATEOnR501(
        		StateMachine_c.getOneSM_SMOnR517(uut));
        moveShapeInEditor(editor, state);
        
        // replace the ISM file with previous from local history
        IFileState[] fh = state.getFile().getHistory(null);
        state.getFile().setContents(fh[0], true, true, null);

        Display d = Display.getDefault();
        while (d.readAndDispatch()) ;
        
        // verify that the canvas editor is still open
    	GraphicalEditor afterEditor = CanvasTestUtils.getCanvasEditor("Disk");
        assertNotNull("Editor closed by file reload", afterEditor);  
    }

    /**
     * Move the shape that represents me in the editor
     * 
     * @param editor
     * @param me
     * @return the center of the shape before the move
     */
	private Point moveShapeInEditor(GraphicalEditor editor, NonRootModelElement me) {
		editor.zoomAll();
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(modelRoot.getId());
        GraphicalElement_c element = 
        	CanvasTestUtils.getGraphicalElement(graphicsRoot, me);
        Shape_c shape = Shape_c.getOneGD_SHPOnR2(element);
        
        // drag the state's shape a bit, then let go to get the 
        // change persisted
        Model_c canvas = editor.getModel();
        Point oldCenter = CanvasTestUtils.getShapeCenter(shape);
        Point mouse = CanvasTestUtils.convertToMouseCoor(oldCenter, canvas);
        CanvasTestUtils.createMouseEvent(
            mouse.x, mouse.y, "MouseDown");
        int dx = 20, dy = 20;
        CanvasTestUtils.createMouseEvent(
            mouse.x + dx, mouse.y + dy, "MouseMove");
        CanvasTestUtils.createMouseEvent(
            mouse.x + dx, mouse.y + dy, "MouseUp");
        Point draggedCenter = CanvasTestUtils.getShapeCenter(shape);
        assertTrue("Drag had no effect", !draggedCenter.equals(oldCenter));
        return oldCenter;
	}
 
}
