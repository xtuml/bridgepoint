//=====================================================================
//
//File:      $RCSfile: StatechartTest.java,v $
//Version:   $Revision: 1.15 $
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
//
package com.mentor.nucleus.bp.ui.canvas.test;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Point;

import com.mentor.nucleus.bp.core.common.Activepoller_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Gr_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;

/**
 * Groups functionality common to all canvas-related statechart transition tests.
 * Tests derived from this class operate solely on the ODMS model's 
 * class diagram.  
 */
public abstract class StatechartTest extends CanvasTest
{
	/**
     * The ODMS class-diagram canvas on which this test is operating. 
	 */
    protected Model_c canvas;
    
    /**
	 * For any test derived from this class, whether it should record its 
     * results to disk as the new standard of passibility for later runs. 
	 */
	public static boolean recordResults = false;

	/**
	 * Stores an arbitrary entry for each test class derived from this one 
     * where the model instance to be used across its tests has already been 
     * loaded.  (For speed reasons, we don't want to have to reload the model 
     * for each test.)
	 */
	protected static Map modelLoadedByTestClass = new HashMap();

	/**
	 * A short name identifying this test when its results are reported. 
	 */
	protected String testID;

	/**
	 * The name of the model under test
	 */
	protected String modelName;
	/**
	 * Constructor
	 */
	public StatechartTest(String name)
	{
		super("com.mentor.nucleus.bp.ui.canvas.test", name);
	}

	/**
	 * See parent method overridden.
	 */
	public void setUp() throws Exception
	{
		setModelName("odms");
		
		super.setUp();
		
		// if no previously-run test from the currently executing derivation 
        // of this class has already loaded the model
        // this also has the effect of resetting the model to its initial state
		if (modelLoadedByTestClass.get(getClass()) == null) {
			// load the model
			ensureAvailableAndLoaded(getModelName(), false, true);
			modelLoadedByTestClass.put(getClass(), this);
	        
			// this keeps a leftover side effect of earlier tests from causing
			// exceptions - see issue 672
	        Gr_c.cur_canvas = null;

	        CanvasTestUtils.openClassDiagramEditor(modelRoot);
		}

        // locate the canvas on which this test will be performed
		canvas = getUUT(1048578, 5);
		CanvasTestUtils.matchCanvasSpaceToModelSpace(canvas);
	}
	/**
	 * See parent method overridden.
	 */
	protected String getResultName()
	{
		return getModelName() + testID;
	}

	/**
	 * See field.
	 */
	public static void setRecordResults(boolean newValue)
	{
		recordResults = newValue;
	}
	
	/**
	 * Simluates a mouse press on the "Disk Request" class.
	 * Returns the location on which the press was simulated.  
	 */
	private Point doMousePressOrReleaseOnDiskRequestClass(boolean press)
	{
	    Shape_c shape = CanvasTestUtils.getModelClassShape(
	        modelRoot, "Disk Request");
	    Point center = CanvasTestUtils.getShapeCenter(shape);
		CanvasTestUtils.createMouseEvent(center.x, center.y, 
		    press ? "MouseDown" : "MouseUp");
		return center;
	}

	/**
	 * Simluates a mouse press on the "Disk Request" class.  
	 */
	protected Point doMousePressOnDiskRequestClass()
	{
	    return doMousePressOrReleaseOnDiskRequestClass(true);
	}

	/**
	 * Simluates a mouse release on the "Disk Request" class.  
	 */
	protected Point doMouseReleaseOnDiskRequestClass()
	{
	    return doMousePressOrReleaseOnDiskRequestClass(false);
	}

	/**
	 * Simluates a mouse move over the northwest corner of the 
	 * "Disk Request" class.  Returns the location over which the move
	 * was simulated.  
	 */
	protected Point doMouseMoveOverNWCornerOfDiskRequestClass()
	{
	    Shape_c shape = CanvasTestUtils.getModelClassShape(
	        modelRoot, "Disk Request");
	    Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(shape);
	    Graphelement_c elem = Graphelement_c.getOneDIM_GEOnR301(node);
	    Point corner = new Point((int)elem.getPositionx(), (int)elem.getPositiony());
		CanvasTestUtils.doMouseMove(corner.x, corner.y);
		return corner;
	}

	/**
	 * A point on the canvas that we know isn't covered by a graphical element.
	 */
	private static final Point vacantAreaLocation = new Point(10, 10);

	/**
	 * Simluates a mouse press on a vacant area of the canvas.
	 * Returns the location on which the press was simulated.  
	 */
	protected Point doMousePressOnVacantArea()
	{
	    CanvasTestUtils.doMousePress(
	        vacantAreaLocation.x, vacantAreaLocation.y);
	    return vacantAreaLocation;
	}

	/**
	 * Simluates a mouse release on a vacant area of the canvas.  
	 */
	protected void doMouseReleaseOnVacantArea()
	{
		CanvasTestUtils.doMouseRelease(
		    vacantAreaLocation.x, vacantAreaLocation.y);
	}
	
	/**
	 * Simluates a mouse context-press on a vacant area of the canvas.
	 * Returns the location on which the press was simulated.  
	 */
	protected Point doMouseContextPressOnVacantArea()
	{
	    CanvasTestUtils.doMouseContextPress(
	        vacantAreaLocation.x, vacantAreaLocation.y);
	    return vacantAreaLocation;
	}

	/**
	 * Simluates a mouse press (with the current selection to be 
	 * retained) on a vacant area of the canvas.  
	 * Returns the location on which the press was simulated.  
	 */
	protected Point doMousePressRetainSelectionOnVacantArea()
	{
	    CanvasTestUtils.doMousePressRetainSelection(
	        vacantAreaLocation.x, vacantAreaLocation.y);
	    return vacantAreaLocation;
	}

	/**
	 * Specifies inputs for a test performed by performTest().
	 */
	protected interface Inputs
	{
		/**
		 * Performs whatever inputs are necessary for the current test, 
         * on the given canvas.
		 */
		void perform();
		
		/**
		 * Returns the current state of the tool being tested. 
		 */
		int getToolCurrentState();
		
		/**
		 * Performs whatever cleanup (i.e. post-test) inputs are necessary 
         * to leave the given canvas in a state that will allow the next 
         * test to function as expected.
		 */
		void cleanup();
	}
	
	/**
	 * Performs a test (of the given identifying name) on the canvas 
     * and an associated tool by injecting the given inputs, and 
     * comparing the final state of the associated tool with the 
     * expected one given.
	 */
	public void performTest(String testID, Inputs inputs, int expectedState) 
        throws Exception
	{
		this.testID = testID;
		
		try {
			// perform the inputs on the canvas
			inputs.perform();
			Activepoller_c.Oneshot();
			
			// test that the new current tool state is the given one 
            // that was expected
			int currentState = inputs.getToolCurrentState();
			assertTrue("Expected State: " + expectedState + " got " + currentState,
				currentState == expectedState);
			
            validateOrGenerateResults(UITestingUtilities.getGraphicalEditorFor(
					(NonRootModelElement) canvas.getRepresents(), true),
					recordResults, true);
		} 
		
		finally {
			// perform the given cleanup inputs on the canvas
			inputs.cleanup();
			Activepoller_c.Oneshot();
		}
	}
}