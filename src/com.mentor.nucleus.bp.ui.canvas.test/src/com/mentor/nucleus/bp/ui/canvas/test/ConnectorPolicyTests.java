package com.mentor.nucleus.bp.ui.canvas.test;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Message_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SynchronousMessage_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class ConnectorPolicyTests extends BaseTest {

	private static final String TestContainerName = "ConnectorPolicyTestModel";
	private static boolean initialized;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (!initialized) {
			// load the test model
			loadProject(TestContainerName);
			modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
					TestContainerName, "Root Package", true));
			initialized = true;
		}
	}

	public void testOverlappingTargetsForCreateRequestsStart() {
		Package_c testPackage = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Package One");
					}
				});

		UITestingUtilities.getGraphicalEditorFor(testPackage, true, true);

		// get the message named (Message One)
		final SynchronousMessage_c message = SynchronousMessage_c
				.getOneMSG_SMOnR1018(Message_c
						.getManyMSG_MsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(testPackage)),
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((SynchronousMessage_c) candidate)
										.getLabel().equals("Message One");
							}
						});
		// get the start of the message location
		Model_c model = ((GraphicalEditor) UITestingUtilities.getActiveEditor())
				.getModel();
		Connector_c connector = Connector_c.getOneGD_CONOnR2(GraphicalElement_c
				.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((GraphicalElement_c) candidate).getRepresents() == message;
					}
				}));
		GraphicalEditPart editPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(connector);
		  editPart.getFigure().getParent().revalidate();
		  editPart.getFigure().revalidate();
		  ((Connection) editPart.getFigure()).getConnectionRouter()
		    .route((Connection) editPart.getFigure());
		  while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		  Point firstPoint = ((Connection) editPart.getFigure()).getPoints()
				.getFirstPoint();
		// result should be allowed
		// get connector count before creation
		int connectorCount = Connector_c.getManyGD_CONsOnR2(GraphicalElement_c
				.getManyGD_GEsOnR1(model)).length;
		createConnectorInDiagram((GraphicalEditor) UITestingUtilities
				.getActiveEditor(), firstPoint, "Timing Mark", new Dimension(
				-50, 0));
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch()) ;
		TestingUtilities.allowJobCompletion();
		
		int connectorCountAfter = Connector_c
					.getManyGD_CONsOnR2(GraphicalElement_c.getManyGD_GEsOnR1(model)).length;
		assertTrue(
				"Timing mark was not created when overlapping a non-valid target at start.",
				connectorCountAfter - connectorCount == 1);
	}

	public void testOverlappingTargetsForCreateRequestsEnd() {
		Package_c testPackage = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Package One");
					}
				});

		UITestingUtilities.getGraphicalEditorFor(testPackage, true, true);

		// get the message named (Message Two)
		final SynchronousMessage_c message = SynchronousMessage_c
				.getOneMSG_SMOnR1018(Message_c
						.getManyMSG_MsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(testPackage)),
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((SynchronousMessage_c) candidate)
										.getLabel().equals("Message Two");
							}
						});
		// get the end of the message location
		Model_c model = ((GraphicalEditor) UITestingUtilities.getActiveEditor())
				.getModel();
		Connector_c connector = Connector_c.getOneGD_CONOnR2(GraphicalElement_c
				.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((GraphicalElement_c) candidate).getRepresents() == message;
					}
				}));
		GraphicalEditPart editPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(connector);
		Point lastPoint = ((Connection) editPart.getFigure()).getPoints()
				.getLastPoint();
		// result should be allowed
		// get connector count before creation
		int connectorCount = Connector_c.getManyGD_CONsOnR2(GraphicalElement_c
				.getManyGD_GEsOnR1(model)).length;
		createConnectorInDiagram((GraphicalEditor) UITestingUtilities
				.getActiveEditor(), lastPoint, "Timing Mark", new Dimension(
				-50, 0));
		int connectorCountAfter = Connector_c
				.getManyGD_CONsOnR2(GraphicalElement_c.getManyGD_GEsOnR1(model)).length;
		assertTrue(
				"Timing mark was not created when overlapping a non-valid target at end.",
				connectorCountAfter - connectorCount == 1);
	}

	public void testOverlappingTargetsForUpdateEndRequestsStart() {
		Package_c testPackage = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Package One");
					}
				});

		UITestingUtilities.getGraphicalEditorFor(testPackage, true, true);

		// get the message named (Message One)
		final SynchronousMessage_c message = SynchronousMessage_c
				.getOneMSG_SMOnR1018(Message_c
						.getManyMSG_MsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(testPackage)),
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((SynchronousMessage_c) candidate)
										.getLabel().equals("Message One");
							}
						});
		// get the start of the message location
		Model_c model = ((GraphicalEditor) UITestingUtilities.getActiveEditor())
				.getModel();
		Connector_c connector = Connector_c.getOneGD_CONOnR2(GraphicalElement_c
				.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((GraphicalElement_c) candidate).getRepresents() == message;
					}
				}));
		GraphicalEditPart editPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(connector);
		Point firstPoint = ((Connection) editPart.getFigure()).getPoints()
				.getFirstPoint();
		// select the message for testing
		editPart.getViewer().deselectAll();
		editPart.getViewer().appendSelection(editPart);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		// move the start end down to the same location as
		// the connector (Message Two) end point
		// get the message named (Message Two)
		final SynchronousMessage_c otherMessage = SynchronousMessage_c
				.getOneMSG_SMOnR1018(Message_c
						.getManyMSG_MsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(testPackage)),
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((SynchronousMessage_c) candidate)
										.getLabel().equals("Message Two");
							}
						});
		// get the end of the message location
		Connector_c otherConnector = Connector_c.getOneGD_CONOnR2(GraphicalElement_c
				.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((GraphicalElement_c) candidate).getRepresents() == otherMessage;
					}
				}));
		GraphicalEditPart otherPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(otherConnector);
		Point lastPoint = ((Connection) otherPart.getFigure()).getPoints()
				.getLastPoint();
		
		dragMouseTo(firstPoint, lastPoint, (GraphicalEditor) UITestingUtilities.getActiveEditor());
		// verify that the location of the first message matches that of the
		// location of the second message
		firstPoint = ((Connection) editPart.getFigure()).getPoints()
			.getFirstPoint();
		// we must account for the line cropping, so we compare the
		// y value differences
		assertTrue(
				"Unable to update connector start location to an overlapping position with a non-valid end start term.",
				firstPoint.x == lastPoint.x && lastPoint.y - firstPoint.y < 5);
	}

	public void testOverlappingTargetsForUpdateEndRequestsEnd() {
		Package_c testPackage = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Package One");
					}
				});

		UITestingUtilities.getGraphicalEditorFor(testPackage, true, true);

		// get the message named (Message One)
		final SynchronousMessage_c message = SynchronousMessage_c
				.getOneMSG_SMOnR1018(Message_c
						.getManyMSG_MsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(testPackage)),
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((SynchronousMessage_c) candidate)
										.getLabel().equals("Message One");
							}
						});
		// get the end of the message location
		Model_c model = ((GraphicalEditor) UITestingUtilities.getActiveEditor())
				.getModel();
		Connector_c connector = Connector_c.getOneGD_CONOnR2(GraphicalElement_c
				.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((GraphicalElement_c) candidate).getRepresents() == message;
					}
				}));
		GraphicalEditPart editPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(connector);
		Point lastPoint = ((Connection) editPart.getFigure()).getPoints()
				.getLastPoint();
		// select the message for testing
		editPart.getViewer().deselectAll();
		editPart.getViewer().appendSelection(editPart);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		// move the end end down to the same location as
		// the connector (Message Two) start point
		// get the message named (Message Two)
		final SynchronousMessage_c otherMessage = SynchronousMessage_c
				.getOneMSG_SMOnR1018(Message_c
						.getManyMSG_MsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(testPackage)),
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((SynchronousMessage_c) candidate)
										.getLabel().equals("Message Two");
							}
						});
		// get the end of the message location
		Connector_c otherConnector = Connector_c.getOneGD_CONOnR2(GraphicalElement_c
				.getOneGD_GEOnR1(model, new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((GraphicalElement_c) candidate).getRepresents() == otherMessage;
					}
				}));
		GraphicalEditPart otherPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(otherConnector);
		Point firstPoint = ((Connection) otherPart.getFigure()).getPoints()
				.getFirstPoint();
		dragMouseTo(lastPoint, firstPoint, (GraphicalEditor) UITestingUtilities.getActiveEditor());
		// verify that the location of the first message matches that of the
		// location of the second message
		lastPoint = ((Connection) editPart.getFigure()).getPoints()
			.getLastPoint();
		assertTrue(
				"Unable to update connector start location to an overlapping position with a non-valid end start term.",
				firstPoint.x == lastPoint.x && lastPoint.y - firstPoint.y < 5);
	}

	private void dragMouseTo(Point firstPoint, Point lastPoint, GraphicalEditor activeEditor) {
		Point start = firstPoint.getCopy();
		Point end = lastPoint.getCopy();
		CanvasTestUtils.translate(start, activeEditor.getModel());
		CanvasTestUtils.translate(end, activeEditor.getModel());
		CanvasTestUtilities.doMouseMove(start.x, start.y);
		CanvasTestUtilities.doMousePress(start.x, start.y);
		CanvasTestUtilities.doMouseMove(end.x, end.y);
		CanvasTestUtilities.doMouseRelease(end.x, end.y);
	}

	private void createConnectorInDiagram(GraphicalEditor activeEditor,
			Point location, String toolName, Dimension size) {
		AbstractTool tool = UITestingUtilities.getTool(toolName);
		UITestingUtilities.activateTool(tool);
		CanvasTestUtils.translateDimension(size, activeEditor.getModel());
		Point locationCopy = location.getCopy();
		CanvasTestUtils.translate(locationCopy, activeEditor.getModel());
		CanvasTestUtilities.doMouseMove(locationCopy.x, locationCopy.y);
		CanvasTestUtilities.doMousePress(locationCopy.x, locationCopy.y);
		CanvasTestUtilities.doMouseMove(locationCopy.x + size.width, locationCopy.y
				+ size.height);
		CanvasTestUtilities.doMouseRelease(locationCopy.x + size.width, locationCopy.y
				+ size.height);
		UITestingUtilities.deactivateTool(tool);
	}

}
