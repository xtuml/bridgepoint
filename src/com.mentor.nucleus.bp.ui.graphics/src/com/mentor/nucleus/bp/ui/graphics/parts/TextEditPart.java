//========================================================================
//
//File:      $RCSfile: TextEditPart.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:05:55 $
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
//
package com.mentor.nucleus.bp.ui.graphics.parts;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Color;

import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.Activator;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.layout.FloatingTextLocator;
import com.mentor.nucleus.bp.ui.graphics.trackers.TextDragEditPartsTracker;
import com.mentor.nucleus.bp.ui.graphics.utilities.TextUtilities;

public class TextEditPart extends AbstractGraphicalEditPart {
	
	@Override
	protected IFigure createFigure() {
		FlowPage figure = new FlowPage();
		figure.setBorder(new MarginBorder(5, 5, 5, 5));
	    TextFlow tf = new TextFlow() {

			@Override
			protected void paintFigure(Graphics g) {
				// configure color based off of style
				Object parentModel = ((EditPart) TextEditPart.this.getParent()).getModel();
				GraphicalElement_c graphicalElement = null;
				if(parentModel instanceof Connector_c) {
					graphicalElement = GraphicalElement_c.getOneGD_GEOnR2((Connector_c) parentModel); 
				}
				if(parentModel instanceof Shape_c) {
					graphicalElement = GraphicalElement_c.getOneGD_GEOnR2((Shape_c) parentModel);
				}
				Fillcolorstyle_c fcs = null;
				Linecolorstyle_c lcs = Linecolorstyle_c
						.getOneSTY_LCSOnR400(Elementstyle_c
								.getManySTY_SsOnR401(graphicalElement));
				if(lcs == null) {
					// see if there is an element to inherit from
					lcs = getInheritedLineColorStyle();
					if(lcs == null) {
						fcs = getInheritedFillColorStyleFromShapeOrConnector();
					}
				}
				if(lcs != null || fcs != null) {
					if(fcs != null) {
						Color lineColor = Activator.getDefault().getColor(
								fcs.getRed(), fcs.getGreen(), fcs.getBlue());
						g.setForegroundColor(lineColor);
					} else {
						Color lineColor = Activator.getDefault().getColor(
								lcs.getRed(), lcs.getGreen(), lcs.getBlue());
						g.setForegroundColor(lineColor);
					}
				}
				super.paintFigure(g);
			}
	    	
		};
	    figure.setHorizontalAligment(PositionConstants.ALWAYS_LEFT);
	    figure.add(tf);
		figure.setOpaque(false);
		if (getModelBounds().isEmpty()) {
			// initialize a default position
			Dimension stringExtents = FigureUtilities.getTextExtents(getText(),
					((GraphicalEditPart) getParent()).getFigure().getFont());
			Rectangle modelBounds = getModelBounds();
			if ((modelBounds.x == 0 && modelBounds.y == 0)
					|| (modelBounds.x == -4000 && modelBounds.y == -3000))
				((FloatingText_c) getModel()).Setdefaultlocation(
						stringExtents.height, stringExtents.width);
			modelBounds.width = -1;
			modelBounds.height = -1;
			figure.setBounds(modelBounds);
			tf.setText(getText());
		} else {
			figure.setBounds(getModelBounds());
			tf.setText(getText());
		}
		return figure;
	}

	protected Fillcolorstyle_c getInheritedFillColorStyleFromShapeOrConnector() {
		if(getParent() instanceof ShapeEditPart) {
			return ((ShapeEditPart) getParent()).getInheritedFillColorStyleFromConnector();
		} else {
			return ((ConnectorEditPart) getParent()).getInheritedFillColorStyleFromShape();
		}
	}

	protected Linecolorstyle_c getInheritedLineColorStyle() {
		if(getParent() instanceof ShapeEditPart) {
			return ((ShapeEditPart) getParent()).getInheritedLineColorStyle();
		} else {
			return ((ConnectorEditPart) getParent()).getInheritedLineColorStyle();
		}
	}

	protected Fillcolorstyle_c getInheritedFillColorStyle() {
		if(getParent() instanceof ShapeEditPart) {
			return ((ShapeEditPart) getParent()).getInheritedFillColorStyle();
		} else {
			return ((ConnectorEditPart) getParent()).getInheritedFillColorStyle();
		}
	}
	
	public String getText() {
		FloatingText_c text = (FloatingText_c) getModel();
		return TextUtilities.getTextValueFor(text,
				(AbstractGraphicalEditPart) getParent());
	}

	public void refreshVisuals() {
		if (getParent() == null)
			return;
	    
		Rectangle modelBounds = getModelBounds();
		if(!modelBounds.equals(getFigure().getBounds())) {
			// update the bounds if necessary, we need
			// this to account for undo/redo bounds
			// changes
			getFigure().setBounds(modelBounds);
		}
		
		TextFlow tf = ((TextFlow) getFigure().getChildren().get(0));
		if(!tf.getText().equals(getText())) {
			// update the text
			tf.setText(getText());
		}
		
		// validate here, so that the minimum width
		// calculate below is always correct
		getFigure().validate();
		
		// if the user has never set a width, do not perform
		// the following
		if(modelBounds.width != 0) {
			int minimumWidth = FloatingTextLocator.getMinimumWidth(getFigure());
			if(minimumWidth != modelBounds.width) {
				// if the current width does not equal the
				// minimum, then the text has been shortened
				// we need to crop the width 
				modelBounds.width = minimumWidth;
			}
		}
		
		if(!modelBounds.equals(getFigure().getBounds())) {
			// update the bounds if necessary, we need
			// this to account for bounds changes caused
			// by text changes
			getFigure().setBounds(modelBounds);
		}
		
		// invalidate the figure, to assure correct
		// placement when a shape owns the text
		getFigure().revalidate();
		// repaint the figure
		getFigure().repaint();
	}

	public Rectangle getModelBounds() {
		FloatingText_c text = (FloatingText_c) getModel();
		Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(text);
		if (node == null)
			return new Rectangle();
		Graphelement_c elem = Graphelement_c.getOneDIM_GEOnR301(node);
		return new Rectangle((int) elem.getPositionx(), (int) elem
				.getPositiony(), (int) node.getWidth(), (int) node.getHeight());
	}

	@Override
	public void performRequest(Request req) {
		if (req.getType().equals(RequestConstants.REQ_OPEN)) {
			GraphicalEditor.handleOpen(((SelectionRequest) req).getLocation(),
					(Model_c) getRoot().getContents().getModel(),
					(IStructuredSelection) getViewer().getSelectionManager()
							.getSelection());
		}
	}

	@Override
	protected void createEditPolicies() {
		// no edit policies needed
	}

	@Override
	public DragTracker getDragTracker(Request request) {
		return new TextDragEditPartsTracker(this);
	}

	public Object getModelElement() {
		FloatingText_c text = (FloatingText_c) getModel();
		Connector_c con = Connector_c.getOneGD_CONOnR8(text);
		if (con != null) {
			GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(con);
			if (ge == null)
				return null;
			if (ge.getRepresents() == null)
				return ge;
			return ge.getRepresents();
		}
		Shape_c shp = Shape_c.getOneGD_SHPOnR27(text);
		if (shp != null) {
			GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
			if (ge == null)
				return null;
			if (ge.getRepresents() == null)
				return ge;
			return ge.getRepresents();
		}
		return null;
	}

	@Override
	public void setSelected(int value) {
		if (value == SELECTED_NONE) {
			super.setSelected(value);
			return;
		}
		getViewer().deselect(this);
		getViewer().appendSelection(getParent());
	}

	/**
	 * Set's the selected value for this element, skipping the overridden method
	 * 
	 * @param value
	 */
	public void setInternalSelected(int value) {
		super.setSelected(value);
	}

}
