//========================================================================
//
//File:      $RCSfile: CanvasCopyAction.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:05:57 $
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
package com.mentor.nucleus.bp.ui.graphics.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.CopyAction;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ContainingShape_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicalZoomManager;
import com.mentor.nucleus.bp.ui.graphics.print.PrintDiagramOperation;

public class CanvasCopyAction extends CopyAction {

	private GraphicalEditor m_editor;

	public CanvasCopyAction(GraphicalEditor editor) {
		m_editor = editor;
	}

	public TransactionManager getTransactionManager() {
		return ((NonRootModelElement) m_editor.getModel().getRepresents())
				.getTransactionManager();
	}

	public NonRootModelElement[] getElementsToBeCopied(boolean includeGraphics) {
		return CanvasCopyAction.getCopiableElements(includeGraphics, m_editor
				.getModel());
	}

	public static NonRootModelElement[] getCopiableElements(
			boolean includeGraphics, Model_c model) {
		ArrayList<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
		GraphicalElement_c[] elements = GraphicalElement_c
				.getManyGD_GEsOnR1(model);
		for (int i = 0; i < elements.length; i++) {
			boolean supportsCopy = Cl_c.supportsCopy(model.getRepresents(),
					elements[i].getRepresents());
			if (elements[i].Isselected() && supportsCopy) {
				Connector_c connector = Connector_c
						.getOneGD_CONOnR2(elements[i]);
				if (connector != null) {
					if (!CanvasCopyAction.isConnectorCopiable(connector)) {
						continue;
					}
				}
				list.add((NonRootModelElement) elements[i].getRepresents());
				if (includeGraphics)
					list.add(elements[i]);
			}
		}
		return list.toArray(new NonRootModelElement[list.size()]);

	}


	@Override
	public boolean isEnabled() {
		// always allow for graphical copy
		return true;
	}

	/**
	 * Determines whether or not the selection contains elements which may be
	 * copied
	 */
	public boolean canSelectionBeCopied() {
		if (m_editor == null)
			return false;
		return CanvasCopyAction.isSelectionCopiable(m_editor.getModel());
	}

	public static boolean isSelectionCopiable(Model_c model) {
		boolean symbolEnablement = false;
		boolean connectorEnablement = true;
		GraphicalElement_c[] elements = GraphicalElement_c
				.getManyGD_GEsOnR1(model);
		for (int i = 0; i < elements.length; i++) {
			ContainingShape_c cs = ContainingShape_c.getOneGD_CTROnR28(Shape_c
					.getOneGD_SHPOnR2(elements[i]));
			if(cs != null) {
				// do not allow copy of a container shape yet
				// this will require some special conversion to
				// a non containing shape
				continue;
			}
			if (elements[i].Isselected()) {
				if (elements[i].getRepresents() == null) {
					continue;
				}
				Connector_c connector = Connector_c
						.getOneGD_CONOnR2(elements[i]);
				if (connector != null)
					connectorEnablement = CanvasCopyAction
							.isConnectorCopiable(connector);
				if (!connectorEnablement)
					return false;
				symbolEnablement = Cl_c.supportsCopy(model.getRepresents(),
						elements[i].getRepresents());
				if (!symbolEnablement)
					// if any selected element does not support copy
					// disable the action
					return false;
			}
		}
		if (symbolEnablement && connectorEnablement)
			return true;
		return false;
	}

	public static boolean isConnectorCopiable(Connector_c connector) {
		// do not allow free floating connectors to be copied
		// yet, there is more work to do to allow proper placement
		if(connector.Endsonws() && connector.Startsonws()) {
			return false;
		}
		if (!connector.Isendselected() || !connector.Isstartselected())
			return false;
		else {
			Connector_c endConnector = Connector_c
					.getOneGD_CONOnR2(GraphicalElement_c
							.getOneGD_GEOnR23(Graphelement_c
									.getOneDIM_GEOnR311(Graphconnector_c
											.getOneDIM_CONOnR320(Graphedge_c
													.getOneDIM_EDOnR20(connector)))));
			if (endConnector != null) {
				GraphicalElement_c conElem = GraphicalElement_c
						.getOneGD_GEOnR2(endConnector);
				if (!conElem.Isselected())
					return false;
			}
			Connector_c startConnector = Connector_c
					.getOneGD_CONOnR2(GraphicalElement_c
							.getOneGD_GEOnR23(Graphelement_c
									.getOneDIM_GEOnR311(Graphconnector_c
											.getOneDIM_CONOnR321(Graphedge_c
													.getOneDIM_EDOnR20(connector)))));
			if (startConnector != null) {
				GraphicalElement_c conElem = GraphicalElement_c
						.getOneGD_GEOnR2(startConnector);
				if (!conElem.Isselected())
					return false;
			}
		}
		return true;
	}

	public static Object getImageDataForSelection(GraphicalEditor editor) {
		//
		// Compute the required height and width to render
		boolean considerAll = false;
		IStructuredSelection selection = (IStructuredSelection) editor
				.getSite().getSelectionProvider().getSelection();
		if (!selection.isEmpty()) {
			if (selection.size() == 1
					&& selection.getFirstElement() instanceof DiagramEditPart) {
				// treat this as no selection
				considerAll = true;
			}
		}

		List<GraphicalEditPart> symbols = new ArrayList<GraphicalEditPart>();
		if (considerAll) {
			symbols = GraphicalEditor.getAllSymbols((GraphicalViewer) editor
					.getAdapter(GraphicalViewer.class), editor.getModel()
					.Hascontainersymbol());
		} else {
			for (Iterator<?> iterator = selection.iterator(); iterator
					.hasNext();) {
				Object next = iterator.next();
				if (next instanceof GraphicalEditPart) {
					if (next instanceof DiagramEditPart)
						continue;
					else
						symbols.add((GraphicalEditPart) next);
				}
			}
		}

		Rectangle extentRectangle = GraphicalZoomManager
				.getExtentRectangle(symbols);

		//
		// Set up the canvas for drawing.
		Rectangle client = new Rectangle(extentRectangle.x, extentRectangle.y,
				extentRectangle.width, extentRectangle.height);

		Image canvasImage = new Image(editor.getCanvas().getDisplay(),
				new org.eclipse.swt.graphics.Rectangle(client.x, client.y,
						client.width, client.height));
		GraphicalViewer viewer = (GraphicalViewer) editor
				.getAdapter(GraphicalViewer.class);
		PrintDiagramOperation.printImage(canvasImage, viewer, extentRectangle,
				editor.getModel().Hascontainersymbol(),
				PrintDiagramOperation.NO_FIT);

		return canvasImage.getImageData();
	}
	
	@Override
	protected Object getSecondaryClipboardData() {
		return getImageDataForSelection(m_editor);
	}

	@Override
	protected Transfer getSecondaryTransfer() {
		return ImageTransfer.getInstance();
	}

	@Override
	protected boolean onlyIncludeSecondaryData() {
		return !canSelectionBeCopied();
	}
}