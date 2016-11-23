//========================================================================
//
//File:      $RCSfile: CanvasPasteAction.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/10 05:37:56 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package org.xtuml.bp.ui.graphics.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.InstanceList;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.ModelStreamProcessor;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.ui.PasteAction;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.ContainingShape_c;
import org.xtuml.bp.ui.canvas.Diagram_c;
import org.xtuml.bp.ui.canvas.Diagramelement_c;
import org.xtuml.bp.ui.canvas.Graphconnector_c;
import org.xtuml.bp.ui.canvas.Graphedge_c;
import org.xtuml.bp.ui.canvas.Graphelement_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Graphnode_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.tools.GraphicalPanningSelectionTool;
import org.xtuml.bp.ui.graphics.utilities.GraphicsUtil;

public class CanvasPasteAction extends PasteAction {

	private GraphicalEditor m_editor;
	private int offsetX;
	private int offsetY;
	private GraphicalElement_c[] graphicElements;
	private ChangeBoundsRequest fRequest;
	
	public CanvasPasteAction(GraphicalEditor editor) {
		super();
		m_editor = editor;
	}

	@Override
	public void runWithEvent(Event event) {
		// if the paste was initiated via
		// the keyboard, then clear the last
		// context press and use 0,0 for the
		// location (until a better layout is
		// provided)
		if(event.keyCode != 0) {
			GraphicalViewer viewer = (GraphicalViewer) m_editor
					.getAdapter(GraphicalViewer.class);
			GraphicalPanningSelectionTool tool = (GraphicalPanningSelectionTool) viewer
					.getEditDomain().getActiveTool();
			tool.clearLastLeftClickLocation();
		}
		super.runWithEvent(event);
	}

	@Override 
	public void processGraphics(NonRootModelElement destination) throws Exception {
		// we only want to do graphical processing
		// if the destination is the diagram
		if (getDestinations().size() == 1
				&& getDestinations().get(0) == m_editor.getModel()
						.getRepresents() && !moveIsInProgress()) {
			NonRootModelElement[] elements = getLoadedGraphicalInstances((NonRootModelElement) m_editor
					.getModel().getRepresents());
			graphicElements = getPastedGraphicalElements((NonRootModelElement) m_editor
					.getModel().getRepresents(), processorMap);
			if(graphicElements.length == 0) {
				// this is likely a copy from ME to diagram where
				// no graphics exist, one case and maybe the only
				// is copy a domain and paste to SYS diagram
				return;
			}
			boolean newParent = areGraphicalElementsExternal();
			updateGraphicalElementRoots(elements, m_editor.getModel().getModelRoot());
			for (int i = 0; i < graphicElements.length; i++) {
				graphicElements[i].unrelateAcrossR1From(Model_c.getOneGD_MDOnR1(graphicElements[i]));
				graphicElements[i].relateAcrossR1To(m_editor.getModel());
				updateContainement(m_editor.getModel(), graphicElements[i]);
			}
			updateLocation(graphicElements, newParent);
			if(m_editor.getModel().Hascontainersymbol()) {
				Shape_c container = Shape_c.getOneGD_SHPOnR2(GraphicalElement_c
						.getOneGD_GEOnR1(m_editor.getModel()));
				ContainingShape_c cs = ContainingShape_c.getOneGD_CTROnR28(container);
				if(cs != null) {
					cs.Autoresize();
				}
			}
			offsetX = 0; offsetY = 0;
		} else {
			handleNonDiagramElementAsDestination(destination, processorMap);
		}
	}

	public static void handleNonDiagramElementAsDestination(
			NonRootModelElement destination,
			HashMap<NonRootModelElement, ModelStreamProcessor> processorMap) throws Exception {
		// we are pasting into a shape, we need to move the
		// graphical elements to the new Model_c instance
		Model_c destGD_MD = getModel(destination);
		// skip if no diagram
		if (destGD_MD == null) {
			return;
		}
		if (moveIsInProgress()) {
			ArrayList<GraphicalElement_c> graphicalElementsToMove = new ArrayList<GraphicalElement_c>();
			for (NonRootModelElement ooaElementMoved : ELEMENT_MOVE_SOURCE_SELECTION) {	
				moveGraphicalElement(ooaElementMoved, destGD_MD);
			}
		} else {
	 		GraphicalElement_c[] pastedGraphicalElements = getPastedGraphicalElements(
					destination, processorMap);
			NonRootModelElement[] loadedGraphicalElements = processorMap.get(
					destination).getImporter().getLoadedGraphicalInstances();
			updateGraphicalElementRoots(loadedGraphicalElements, destGD_MD.getModelRoot());
			for (GraphicalElement_c element : pastedGraphicalElements) {
				element.relateAcrossR1To(destGD_MD);
				updateContainement(destGD_MD, element);
			}
			// move the elements so they do not overlap any existing elements
			moveGraphicalElementsToPreventOverlapping(pastedGraphicalElements, destGD_MD);
		}
		if (destGD_MD.Hascontainersymbol()) {
			Shape_c container = Shape_c.getOneGD_SHPOnR2(GraphicalElement_c
					.getOneGD_GEOnR1(destGD_MD));
			ContainingShape_c cs = ContainingShape_c
					.getOneGD_CTROnR28(container);
			if (cs != null) {
				cs.Autoresize();
			}
		}
	}

	/**
	 * If there is a graphical element we need to update its
	 * containment by un-associating it from it's source canvas
	 * and associating it with the destination
	 * 
	 * @param ooaElementMoved
	 * @param destGD_MD
	 */
	private static void moveGraphicalElement(final NonRootModelElement ooaElementMoved, Model_c destGD_MD) {
		Model_c srcModel = null;
		Model_c[] models = Model_c.ModelInstances(Ooaofgraphics.getInstance(ooaElementMoved.getModelRoot().getId()));
		Model_c[] systemModels = Model_c.ModelInstances(Ooaofgraphics.getDefaultInstance());
		Model_c[] allModels = new Model_c[models.length + systemModels.length];
		System.arraycopy(models, 0, allModels, 0, models.length);
		System.arraycopy(systemModels, 0, allModels, models.length, systemModels.length);
		GraphicalElement_c graphicalElementMoved = null;
		for(int i = 0; i < allModels.length; i++) {
			graphicalElementMoved = GraphicalElement_c.getOneGD_GEOnR1(allModels[i], new ClassQueryInterface_c() {
				
				@Override
				public boolean evaluate(Object candidate) {
					GraphicalElement_c element = (GraphicalElement_c) candidate;
					if(element.getRepresents() != null) {
						return element.getRepresents().equals(ooaElementMoved);
					} else {
						return element.getOoa_id().equals(ooaElementMoved.Get_ooa_id());
					}
				}
			});
			CanvasPlugin.setGraphicalRepresents(allModels[i]);
			if(graphicalElementMoved != null) {
				break;
			}
		}

		if (graphicalElementMoved != null) {

			// Every GraphicalElement is part of a diagram, so this will never be null.
			Model_c gd_mdThisElementIsPartOf = Model_c.getOneGD_MDOnR1(graphicalElementMoved);			
			
			// This finds the GD_MD instance that has the same "represents" as the given
			// GD_GE. This is null unless the ooaElementMoved represents a container.
			Model_c gd_mdOftheElementMoved = getModel(ooaElementMoved);
	
			// If we are moving to a different graphical model root then we need to actually switch the root. 
			// In this case we update self and children because the root moves for all of them
			if (gd_mdOftheElementMoved!= null && gd_mdOftheElementMoved != destGD_MD) {
				Diagram_c diag = Diagram_c.getOneDIM_DIAOnR18(gd_mdOftheElementMoved);
				diag.setComponent(destGD_MD.getPersistableComponent());
				gd_mdOftheElementMoved.setComponent(destGD_MD.getPersistableComponent());
				diag.updateRootForSelfAndChildren(gd_mdOftheElementMoved.getModelRoot(), destGD_MD.getModelRoot());
			}
			
			graphicalElementMoved.setComponent(destGD_MD.getPersistableComponent());
			
			graphicalElementMoved.unrelateAcrossR1From(gd_mdThisElementIsPartOf);
			graphicalElementMoved.relateAcrossR1To(destGD_MD);
			
			updateContainement(destGD_MD, graphicalElementMoved);

			GraphicalElement_c[] graphicalElementList = {graphicalElementMoved};
			
			moveGraphicalElementsToPreventOverlapping(graphicalElementList, destGD_MD);			
		}
	}
	
	static private void openCanvasEditor(final Object uut) {
		try {
			IStructuredSelection ss = new StructuredSelection(uut);
			Selection selection = Selection.getInstance();
			selection.addToSelection(ss);
			OpenGraphicsEditor sca = new OpenGraphicsEditor();
			selection.setSelection(ss);
			Action a = new Action() {
			};
			sca.run(a);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch())
				;
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().update();
		} catch (Exception x) {
			CorePlugin.logError("Unable to open canvas editor.", x);
		}
	}
	
	private static void moveGraphicalElementsToPreventOverlapping(
			GraphicalElement_c[] elements, Model_c model) {
		// store current selection
		ISelection selection = Selection.getInstance().getSelection();
		// clear the current selection and add all elements
		// to it, this allows connectors to move properly
		Selection.getInstance().clear();
		Point northWestSelectionPoint = getNorthWestSelectionPoint(elements);
		Point nextAvailableEastLocation = getNextAvailableEastLocation(model);
		for(int i = 0; i < elements.length; i++) {
			Cl_c.Addtoselection(elements[i]);
		}
		for(int i = 0; i < elements.length; i++) {
			Connector_c connector = Connector_c.getOneGD_CONOnR2(elements[i]);
			if(connector == null) {
				elements[i]
						.Move(nextAvailableEastLocation.x
								- northWestSelectionPoint.x,
								nextAvailableEastLocation.y
										- northWestSelectionPoint.y);
			}
		}
		// restore the selection
		Selection.getInstance().setSelection(selection);
	}

	private static Point getNextAvailableEastLocation(Model_c model) {
		GraphicalElement_c[] existing = GraphicalElement_c.getManyGD_GEsOnR1(model);
		Point east = new Point(-1, Integer.MAX_VALUE);
		for(int i = 0; i < existing.length; i++) {
			Graphelement_c graphEle = Graphelement_c.getOneDIM_GEOnR23(existing[i]);
			Graphnode_c node = Graphnode_c.getOneDIM_NDOnR301(graphEle);
			Connector_c connector = Connector_c
					.getOneGD_CONOnR2(GraphicalElement_c
							.getOneGD_GEOnR23(graphEle));
			if(connector == null) {
				if(east.x < graphEle.getPositionx() + node.getWidth()) {
					east.x = (int) (graphEle.getPositionx() + node.getWidth() + 50);
				}
				if(east.y > graphEle.getPositiony()) {
					east.y = (int) graphEle.getPositiony();
				}
			}
		}
		return east;
	}

	private static Model_c getModel(NonRootModelElement destination) {
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(destination.getModelRoot().getId());
		Model_c[] models = Model_c.ModelInstances(graphicsRoot);
		for(Model_c model : models) {
			if(model.getRepresents() == destination) {
				return model;
			}
		}
		return null;
	}

	private boolean areGraphicalElementsExternal() {
		// right now all graphical elements come from
		// the same location, so just check the first
		// in the list
		UUID copiedFromDiagramId = graphicElements[0].getDiagramidCachedValue();
		Diagram_c destinationDiagram = Diagram_c.getOneDIM_DIAOnR18(m_editor
				.getModel());
		if (!copiedFromDiagramId.equals(destinationDiagram.getDiagramid())) {
			return true;
		}
		return false;
	}

	private static void updateContainement(Model_c ptCanvas, GraphicalElement_c element_c) {
		Diagramelement_c diaElem = Diagramelement_c
				.getOneDIM_ELEOnR302(Graphelement_c
						.getOneDIM_GEOnR23(element_c));
		if(ptCanvas.Hascontainersymbol()) {
			// if this shape is pasted into
			// a shape, make sure the necessary
			// containment associations are setup
			Graphelement_c container = Graphelement_c.getOneDIM_GEOnR307(diaElem);
			if(container == null) {
				container = Graphelement_c
						.getOneDIM_GEOnR23(GraphicalElement_c
								.getOneGD_GEOnR2(Shape_c
										.getOneGD_SHPOnR28(ContainingShape_c
												.getOneGD_CTROnR28(Shape_c
														.getOneGD_SHPOnR2(GraphicalElement_c
																.getOneGD_GEOnR1(ptCanvas))))));
				diaElem.relateAcrossR307To(container);
			}
		} else {
			// if not pasted into a shape
			// make sure no containment
			// association is setup
			Graphelement_c container = Graphelement_c.getOneDIM_GEOnR307(diaElem);
			if(container != null) {
				diaElem.unrelateAcrossR307From(container);
			}
		}
	}

	private static void updateGraphicalElementRoots(NonRootModelElement[] elements, ModelRoot modelRoot) {
		for(int i = 0; i < elements.length; i++) {
			InstanceList list = elements[i].getModelRoot().getInstanceList(
					elements[i].getClass());
			synchronized (list) {
				list.remove(elements[i]);
			}
			InstanceList parentList = modelRoot
					.getInstanceList(elements[i].getClass());
			synchronized (parentList) {
				parentList.add(elements[i]);
				parentList.put(elements[i].getInstanceKey(), elements[i]);
			}
			elements[i].setModelRoot(modelRoot);
		}
	}

	private static GraphicalElement_c[] getPastedGraphicalElements(
			NonRootModelElement destination, HashMap<NonRootModelElement, ModelStreamProcessor> processorMap) {
		// do not process unless the destination is the diagram
		ArrayList<GraphicalElement_c> list = new ArrayList<GraphicalElement_c>();
		GraphicalElement_c[] pastedElems = GraphicalElement_c
				.GraphicalElementInstances(Ooaofgraphics
						.getInstance(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME));
		for (int i = 0; i < pastedElems.length; i++) {
			// skip containing shapes
			ContainingShape_c cs = ContainingShape_c.getOneGD_CTROnR28(Shape_c
					.getOneGD_SHPOnR2(pastedElems[i]));
			if (cs != null) {
				continue;
			}
			// skip connectors that start on a container shape
			// as they are never part of the copy, if we later
			// enable copy of a container then we will likely
			// need to remove these checks
			cs = ContainingShape_c
					.getOneGD_CTROnR28(Shape_c
							.getOneGD_SHPOnR2(GraphicalElement_c
									.getOneGD_GEOnR23(Graphelement_c
											.getOneDIM_GEOnR311(Graphconnector_c
													.getOneDIM_CONOnR320(Graphedge_c
															.getOneDIM_EDOnR20(Connector_c
																	.getOneGD_CONOnR2(pastedElems[i])))))));
			if (cs != null) {
				continue;
			}
			cs = ContainingShape_c
					.getOneGD_CTROnR28(Shape_c
							.getOneGD_SHPOnR2(GraphicalElement_c
									.getOneGD_GEOnR23(Graphelement_c
											.getOneDIM_GEOnR311(Graphconnector_c
													.getOneDIM_CONOnR321(Graphedge_c
															.getOneDIM_EDOnR20(Connector_c
																	.getOneGD_CONOnR2(pastedElems[i])))))));
			if (cs != null) {
				continue;
			}
			if (pastedElems[i].getRepresents() != null) {
				if(processorMap.get(destination).isTypePartOfExport((NonRootModelElement) pastedElems[i]
				                                                              						.getRepresents())) {
					list.add(pastedElems[i]);
				}
			}
		}
		return list.toArray(new GraphicalElement_c[list.size()]);
	}

	/**
	 * Determines the position for the pasted elements, then makes a call
	 * to update their positional values.
	 * 
	 * @param elements
	 */
	private void updateLocation(GraphicalElement_c[] elements, boolean newParent) {
		if(offsetX == 0 && offsetY == 0) {
			if(fRequest != null) {
				offsetX = fRequest.getMoveDelta().x;
				offsetY = fRequest.getMoveDelta().y;
				// clear the request cache
				fRequest = null;
			} else {
				// determine the offset based on the northwest corner
				// point of the selection and the mouse point where
				// the user decided to paste the selection
				Point nw = getNorthWestSelectionPoint(elements);
				Point location = getMouseLocation();
				offsetX = (int) (location.x - nw.x);
				offsetY = (int) (location.y - nw.y);
				GraphicalViewer viewer = (GraphicalViewer) m_editor
						.getAdapter(GraphicalViewer.class);
				GraphicalPanningSelectionTool tool = (GraphicalPanningSelectionTool) viewer
						.getEditDomain().getActiveTool();
				if (tool.getLastLeftClickLocation().x == 0
						&& tool.getLastLeftClickLocation().y == 0) {
					// there is no location hint, so we
					// just give a slight offset to the right
					// and down a bit, unless the elements are
					// being pasted from a different diagram
					if(newParent) {
						// then we simply use the top left corner
						Point topLeft = new Point(15, 15);
						((AbstractGraphicalEditPart) viewer.getContents())
								.getFigure().translateToRelative(topLeft);
						offsetX = topLeft.x - nw.x;
						offsetY = topLeft.y - nw.y;
					} else {
						offsetX = 15;
						offsetY = 15;
					}
				}

			}
		}
		updateLocations(offsetX, offsetY, elements);
	}

	private Point getMouseLocation() {
		GraphicalViewer viewer = (GraphicalViewer) m_editor
				.getAdapter(GraphicalViewer.class);
		GraphicalPanningSelectionTool tool = (GraphicalPanningSelectionTool) viewer
				.getEditDomain().getActiveTool();
		Point lastLeftClickLocation = tool.getLastLeftClickLocation().getCopy();
		((AbstractGraphicalEditPart) viewer.getContents()).getFigure()
				.translateToRelative(lastLeftClickLocation);
		return lastLeftClickLocation;
	}

	/**
	 * Updates the positional values of the elements on the clipboard, using
	 * the given offset.  Note the elements are added to the selection just in
	 * case the selection was cleared before a paste, this allows for connectors
	 * to move appropriately.
	 * 
	 * @param offsetX2
	 * @param offsetY2
	 * @param elements
	 */
	private void updateLocations(int offsetX2, int offsetY2, GraphicalElement_c[] elements) {
		// set the selection before moving and after refreshing
		// the diagram contents (the refresh will create
		// the new edit parts)
		m_editor.refresh();
		GraphicalViewer viewer = (GraphicalViewer) m_editor
				.getAdapter(GraphicalViewer.class);
		viewer.deselectAll();
		for (int i = 0; i < elements.length; i++) {
			Object newModel = null;
			Shape_c shape = Shape_c.getOneGD_SHPOnR2(elements[i]);
			if(shape != null) {
				newModel = shape;
			}
			Connector_c connector = Connector_c.getOneGD_CONOnR2(elements[i]);
			if(connector != null) {
				newModel = connector;
			}
			List<?> children = viewer.getContents().getChildren();
			for(Object child: children) {
				EditPart editPart = (EditPart) child;
				if(editPart.getModel() == newModel) {
					viewer.appendSelection(editPart);
				}
			}
		}
		for(int i = 0; i < elements.length; i++) {
			Connector_c connector = Connector_c.getOneGD_CONOnR2(elements[i]);
			if(connector == null) {
				elements[i].Move(offsetX2, offsetY2);
			}
		}
	}

	/**
	 * Looks through the elements on the clipboard to determine
	 * a reference point for location placement
	 * 
	 * @param elements
	 * @return
	 */
	private static Point getNorthWestSelectionPoint(GraphicalElement_c[] elements) {
		Point nw = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		for(int i = 0; i < elements.length; i++) {
			Graphelement_c graphEle = Graphelement_c.getOneDIM_GEOnR23(elements[i]);
			Connector_c connector = Connector_c
					.getOneGD_CONOnR2(GraphicalElement_c
							.getOneGD_GEOnR23(graphEle));
			if(connector == null) {
				if(nw.x > graphEle.getPositionx()) {
					nw.x = (int) graphEle.getPositionx();
				}
				if(nw.y > graphEle.getPositiony()) {
					nw.y = (int) graphEle.getPositiony();
				}
			}
		}
		return nw;
	}
	
	@Override
	protected boolean supportsPaste(Object target, String child) {
		return Cl_c.supportsPaste(target, child);
	}
	
	public TransactionManager getTransactionManager() {
		return ((NonRootModelElement)m_editor.getModel().getRepresents()).getTransactionManager();
	}
	
	@Override
	public List<NonRootModelElement> getDestinations() {
		if(fRequest != null) {
			// this is for the clone command, which always needs
			// the diagram host as the destination
			List<NonRootModelElement> destinations = new ArrayList<NonRootModelElement>();
			destinations.add((NonRootModelElement) m_editor.getModel().getRepresents());
			return destinations;
		}
		IStructuredSelection selection = (IStructuredSelection) m_editor
				.getSite().getSelectionProvider().getSelection();
		return getDestinationsFromSelection(selection);
	}

	private List<NonRootModelElement> getDestinationsFromSelection(IStructuredSelection selection) {
		List<NonRootModelElement> destinations = new ArrayList<NonRootModelElement>();
		for(Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
			Object selected = iterator.next();
			if(selected instanceof EditPart) {
				EditPart part = (EditPart) selected;
				NonRootModelElement element = (NonRootModelElement) GraphicsUtil
					.getRepresentsFromEditPart(part);
				destinations.add(element);
			}
		}
		return destinations;
	}

	public void setRequest(ChangeBoundsRequest request) {
		fRequest = request;
	}

	@Override
	public boolean isEnabled() {
		if(!super.isEnabled() || (m_editor == null) ) {
			return false;
		}
		return true;
	}
}
