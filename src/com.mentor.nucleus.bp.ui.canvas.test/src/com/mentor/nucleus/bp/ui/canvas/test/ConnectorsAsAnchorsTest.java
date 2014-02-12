//========================================================================
//
//File:      $RCSfile: ConnectorsAsAnchorsTest.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2013/05/10 05:41:50 $
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

package com.mentor.nucleus.bp.ui.canvas.test;

import java.lang.reflect.Method;
import java.util.UUID;

import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.graphics.Point;

import com.mentor.nucleus.bp.core.ActorParticipant_c;
import com.mentor.nucleus.bp.core.ClassInstanceParticipant_c;
import com.mentor.nucleus.bp.core.InteractionParticipant_c;
import com.mentor.nucleus.bp.core.Lifespan_c;
import com.mentor.nucleus.bp.core.Message_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SynchronousMessage_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.LineSegment_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

/**
 * Contains tests that exercise the functionality for sequence support.
 */
public class ConnectorsAsAnchorsTest extends CanvasTest {
	private String test_id;

	private static boolean generateResults;

	public ConnectorsAsAnchorsTest(String arg0) {
		super(null, arg0);
	}

	/**
	 * Whether the first test of this class is the one that's currently being
	 * run.
	 */
	private static boolean firstTest = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		// if it's the first test of this class that's being setup
		if (firstTest) {
		    loadProject("TestConnectorsAsAnchors");
			firstTest = false;
		}
	}

	/**
	 * Tests that connectors connected to another remain connected after a
	 * waypoint is moved
	 */
	public void testBendingConnectorWithConnectorsStartingAt() {
		test_id = "1";
		String diagramName = "SQ A";
		Package_c sequence = getPackage(diagramName);
		CanvasTestUtils.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor(diagramName
				+ ": Package Diagram");

		ClassInstanceParticipant_c cip = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals("testInst");
							}

						});

		final Lifespan_c lifespan = Lifespan_c.getOneSQ_LSOnR940(InteractionParticipant_c.getOneSQ_POnR930(cip));

		assertNotNull(lifespan);

		GraphicalElement_c ge = GraphicalElement_c.GraphicalElementInstance(
				graphicsModelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						GraphicalElement_c selected = (GraphicalElement_c) candidate;
						return selected.getRepresents() == lifespan;
					}

				});

		assertNotNull(ge);

		Connector_c connector = Connector_c.getOneGD_CONOnR2(ge);

		assertNotNull(connector);

		UITestingUtilities.addElementToGraphicalSelection(ge.getRepresents());
		
		Point center = CanvasTestUtils.getConnectorCenter(connector);
		center = CanvasTestUtils.convertToMouseCoor(center, ce.getModel());

		// bend the test connector at the center
		CanvasTestUtils.doMouseMove(center.x, center.y);
		CanvasTestUtils.doMousePress(center.x, center.y);
		CanvasTestUtils.doMouseMove(center.x - 100, center.y + 100);
		CanvasTestUtils.doMouseRelease(center.x - 100, center.y + 100);

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests that only the first/last segment of connectors connected at another
	 * is moved when only one side of the anchor connector is selected
	 */
// TODO:    dts0100656082
/*	public void testMovingConnectorWithConnectorsStartingEndingAtOneSideSelected() {
		test_id = "2";
		String diagramName = "SQ D";
		Sequence_c sequence = getSequence(diagramName);
		CanvasTestUtils.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor(diagramName
				+ ": Package Diagram");

		final ClassInstanceParticipant_c cip = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals("testInstB");
							}

						});

		GraphicalElement_c ge = GraphicalElement_c.GraphicalElementInstance(
				graphicsModelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						GraphicalElement_c selected = (GraphicalElement_c) candidate;
						return selected.getRepresents() == cip;
					}

				});

		assertNotNull(ge);

		Shape_c shp = Shape_c.getOneGD_SHPOnR2(ge);

		assertNotNull(shp);

		Point center = CanvasTestUtils.getShapeCenter(shp);
		center = CanvasTestUtils.convertToMouseCoor(center, ce.getModel());

		// move the shape
		CanvasTestUtils.doMouseMove(center.x, center.y);
		CanvasTestUtils.doMousePress(center.x, center.y);
		CanvasTestUtils.doMouseMove(center.x - 100, center.y);
		CanvasTestUtils.doMouseRelease(center.x - 100, center.y);

		validateOrGenerateResults(ce, generateResults);

	}
*/
	/**
	 * Tests that all segments of connectors connected at another are moved when
	 * both sides of the anchor connector are selected
	 */
// TODO:    dts0100656082
	/*public void testMovingConnectorWithConnectorsStartingEndingAtBothSidesSelected() {
		test_id = "3";
		String diagramName = "SQ D";
		Sequence_c sequence = getSequence(diagramName);
		CanvasTestUtils.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor(diagramName
				+ ": Package Diagram");

		final ClassInstanceParticipant_c cip = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals("testInstB");
							}

						});

		ExternalEntityParticipant_c eep = ExternalEntityParticipant_c
				.ExternalEntityParticipantInstance(modelRoot);

		GraphicalElement_c ge = GraphicalElement_c.GraphicalElementInstance(
				graphicsModelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						GraphicalElement_c selected = (GraphicalElement_c) candidate;
						return selected.getRepresents() == cip;
					}

				});

		assertNotNull(ge);

		Shape_c shp = Shape_c.getOneGD_SHPOnR2(ge);

		assertNotNull(shp);

		Point center = CanvasTestUtils.getShapeCenter(shp);
		center = CanvasTestUtils.convertToMouseCoor(center, ce.getModel());

		// move both sides of a connector
		CanvasTestUtils.doMouseMove(center.x, center.y);
		CanvasTestUtils.doMousePress(center.x, center.y);
		// add the other side to the selection
		Selection.getInstance().addToSelection(eep);
		CanvasTestUtils.doMouseMove(center.x + 200, center.y + 200);
		CanvasTestUtils.doMouseRelease(center.x + 200, center.y + 200);

		validateOrGenerateResults(ce, generateResults);

	}*/

	/**
	 * Tests that last connector intersected is what the connector is finalized
	 * on.
	 */
	public void testNewConnectorIntersectingMultipleConnectors() {
		test_id = "4";
		String diagramName = "SQ B";
		Package_c sequence = getPackage(diagramName);
		CanvasTestUtils.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// get the class instance which has the lifespan needed
		// for testing
		final ClassInstanceParticipant_c cip = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals(
										"Unnamed Instance A");
							}

						});

		final Lifespan_c lifespan = Lifespan_c.getOneSQ_LSOnR940(InteractionParticipant_c.getOneSQ_POnR930(cip));

		assertNotNull(lifespan);

		GraphicalElement_c ge = GraphicalElement_c.GraphicalElementInstance(
				graphicsModelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						GraphicalElement_c selected = (GraphicalElement_c) candidate;
						return selected.getRepresents() == lifespan;
					}

				});

		Connector_c connector = Connector_c.getOneGD_CONOnR2(ge);

		assertNotNull(connector);

		Point center = CanvasTestUtils.getConnectorCenter(connector);
		center = CanvasTestUtils.convertToMouseCoor(center, ce.getModel());

		// get the second class instance participant which has the second
		// lifespan needed for testing associated with it
		final ClassInstanceParticipant_c cip2 = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals(
										"Unnamed Instance B");
							}

						});

		final Lifespan_c lifespan2 = Lifespan_c.getOneSQ_LSOnR940(InteractionParticipant_c.getOneSQ_POnR930(cip2));

		assertNotNull(lifespan);

		GraphicalElement_c ge2 = GraphicalElement_c.GraphicalElementInstance(
				graphicsModelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						GraphicalElement_c selected = (GraphicalElement_c) candidate;
						return selected.getRepresents() == lifespan2;
					}

				});

		Connector_c connector2 = Connector_c.getOneGD_CONOnR2(ge2);

		assertNotNull(connector2);

		Point center2 = CanvasTestUtils.getConnectorCenter(connector2);
		center2 = CanvasTestUtils.convertToMouseCoor(center2, ce.getModel());

		// initialize the synchronous message tool
		AbstractTool tool = UITestingUtilities.getTool("Synchronous Message");
		UITestingUtilities.activateTool(tool);

		// draw the connector starting before the first lifespan
		// continuing through the second
		CanvasTestUtils.doMouseMove(center.x - 20, center.y);
		CanvasTestUtils.doMousePress(center.x - 20, center.y);
		CanvasTestUtils.doMouseMove(center2.x + 20, center.y);
		CanvasTestUtils.doMouseRelease(center2.x + 20, center.y);

		validateOrGenerateResults(ce, generateResults);

		UITestingUtilities.deactivateTool(tool);
	}

	/**
	 * Tests that a segments end may be moved to another segment
	 */
	public void testUpdatingEndOfSegmentWithNewSegment() {
		test_id = "5";
		String diagramName = "SQ B";
		Package_c sequence = getPackage(diagramName);
		CanvasTestUtils.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor(diagramName
				+ ": Package Diagram");

		// get the class instance which has the lifespan needed
		// for testing
		final ClassInstanceParticipant_c cip = ClassInstanceParticipant_c
				.ClassInstanceParticipantInstance(modelRoot,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								ClassInstanceParticipant_c selected = (ClassInstanceParticipant_c) candidate;
								return selected.getName().equals(
										"Unnamed Instance C");
							}

						});

		final Lifespan_c lifespan = Lifespan_c.getOneSQ_LSOnR940(InteractionParticipant_c.getOneSQ_POnR930(cip));

		assertNotNull(lifespan);

		GraphicalElement_c ge = GraphicalElement_c.GraphicalElementInstance(
				graphicsModelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						GraphicalElement_c selected = (GraphicalElement_c) candidate;
						return selected.getRepresents() == lifespan;
					}

				});

		Connector_c connector = Connector_c.getOneGD_CONOnR2(ge);

		assertNotNull(connector);

		final UUID firstSegId = connector.Getstartingsegmentid();

		LineSegment_c firstSeg = LineSegment_c.getOneGD_LSOnR6(connector,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						LineSegment_c selected = (LineSegment_c) candidate;
						return selected.getElementid().equals(firstSegId);
					}

				});

		final UUID lastSegId = connector.Getendingsegmentid();

		LineSegment_c lastSeg = LineSegment_c.getOneGD_LSOnR6(connector,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						LineSegment_c selected = (LineSegment_c) candidate;
						return selected.getElementid().equals(lastSegId);
					}

				});

		Point center = CanvasTestUtils.getSegmentCenter(firstSeg);

		center = CanvasTestUtils.convertToMouseCoor(center, ce.getModel());

		Point lastSegCenter = CanvasTestUtils.getSegmentCenter(lastSeg);

		lastSegCenter = CanvasTestUtils.convertToMouseCoor(lastSegCenter,
				ce.getModel());

		// initialize the synchronous message tool
		AbstractTool tool = UITestingUtilities.getTool("Synchronous Message");
		UITestingUtilities.activateTool(tool);

		// draw a connector so that its end is on one segment
		CanvasTestUtils.doMouseMove(center.x - 100, center.y);
		CanvasTestUtils.doMousePress(center.x - 100, center.y);
		CanvasTestUtils.doMouseMove(center.x, center.y);
		CanvasTestUtils.doMouseRelease(center.x, center.y);

		UITestingUtilities.deactivateTool(tool);

		// select the creation synchronous message
		SynchronousMessage_c message = SynchronousMessage_c
				.getOneMSG_SMOnR1018(Message_c
						.getOneMSG_MOnR1007(InteractionParticipant_c
								.getOneSQ_POnR930(lifespan)));
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(message);

		// now move the created connector so that
		// the line intersects a different segment
		CanvasTestUtils.doMouseMove(center.x + 100, center.y + 100);
		CanvasTestUtils.doMouseMove(center.x, center.y);
		CanvasTestUtils.doMousePress(center.x, center.y);
		CanvasTestUtils.doMouseMove(lastSegCenter.x + 20, center.y);
		CanvasTestUtils.doMouseRelease(lastSegCenter.x + 20, center.y);

		validateOrGenerateResults(ce, generateResults);

	}

	/**
	 * Tests that fixed aspect shapes are drawn with a fixed aspect ratio
	 */
	public void testFixedAspectDrawing() {
		// This test is removed as the fixed aspect support
		// has changed
	}

	/**
	 * Tests resizing a fixed aspect shape leaves the shape with the
	 * same aspect
	 */
	public void testFixedAspectResizing() {
		// This test is removed as the fixed aspect support
		// has changed
	}

	/**
	 * Checks to see if the aspect ratio for a fixed aspect shape is correct,
	 * based on the defined values in the corresponding element specification.
	 * 
	 * @param ge
	 */
	private boolean isAspectRatioCorrect(GraphicalElement_c ge) {
		ElementSpecification_c spec = ElementSpecification_c
				.getOneGD_ESOnR10(ge);
		int w = spec.getDefaultwidth();
		int h = spec.getDefaultheight();

		float expectedRatio = w / h;

		Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(Shape_c
				.getOneGD_SHPOnR2(ge));

		assertNotNull(node);

		float actualRatio = node.getWidth() / node.getHeight();

		return expectedRatio == actualRatio;

	}

	/**
	 * Tests that an actor is drawn to fill the shape compartment
	 * when no name is given
	 */
	public void testActorDrawnToFullShapeSizeWhenNotNamed() {
		test_id = "9";
		String diagramName = "SQ C";
		Package_c sequence = getPackage(diagramName);
		CanvasUtilities.openCanvasEditor(sequence);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");

		ActorParticipant_c actor = ActorParticipant_c
				.ActorParticipantInstance(modelRoot);
		if(actor != null)
			actor.Dispose();

		AbstractTool tool = UITestingUtilities.getTool("Actor");
		assertNotNull(tool);

		UITestingUtilities.activateTool(tool);
		
		CanvasTestUtilities.doMouseMove(800, 100);
		CanvasTestUtilities.doMousePress(800, 100);
		CanvasTestUtilities.doMouseMove(1000, 300);
		CanvasTestUtilities.doMouseRelease(1000, 300);

		actor = ActorParticipant_c.ActorParticipantInstance(modelRoot);
		actor.setName("");
		
		validateOrGenerateResults(ce, generateResults);

		UITestingUtilities.deactivateTool(tool);

	}
	
	/**
	 * Returns a sequence with the given name
	 */
	private Package_c getPackage(final String name) {
		Package_c sequence = Package_c.PackageInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(name);
					}

				});
		return sequence;
	}

	protected String getResultName() {
		return "ConnectorsAsAnchors" + "_" + test_id;
	}

	public void setGenerateResults() {
		try {
			generateResults = true;
			setUp();
			Method methods[] = this.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().startsWith("test")) {
					methods[i].invoke(this, new Object[] {});
				}
			}
		} catch (Exception e) {
			System.out.println("Exception encountered by test result creator: "
					+ e);
		}
	}
}