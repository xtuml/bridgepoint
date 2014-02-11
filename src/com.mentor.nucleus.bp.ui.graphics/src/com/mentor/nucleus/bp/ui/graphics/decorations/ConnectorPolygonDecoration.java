//========================================================================
//
//File:      $RCSfile: ConnectorPolygonDecoration.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:05:56 $
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
package com.mentor.nucleus.bp.ui.graphics.decorations;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Transform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.core.Style_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.graphics.Activator;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;

public class ConnectorPolygonDecoration extends PolygonDecoration {

	private int fStyle;
	private ConnectorEditPart fPart;
	private Transform fCustomTransform = new Transform();
	private PointList secondaryDecoration = null;

	public ConnectorPolygonDecoration(int style, String tooltip, ConnectorEditPart fPart) {
		super();
		fStyle = style;
		this.fPart = fPart;
		if(!tooltip.equals("")) {
			Label label = new Label(tooltip);
			label.setBorder(new MarginBorder(3, 3, 3, 3));
			setToolTip(label);
		}
		if(style == Style_c.Triangle) {
			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(0,0);
			decorationPointList.addPoint(-3,3);
			decorationPointList.addPoint(-3,-3);
			decorationPointList.addPoint(0,0);
			setTemplate(decorationPointList);
			setBackgroundColor(ColorConstants.white);
		} else if(style == Style_c.FilledArrow) {
			setScale(1, 1);
			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(-12, -6);
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-12, 6);
			setTemplate(decorationPointList);			
		} else if(style == Style_c.FilledSquare) {
			setScale(1, 1);
			setBackgroundColor(ColorConstants.white);
			PointList decorationPointList = new PointList();
			createFilledSquare(decorationPointList);
			setTemplate(decorationPointList);
		} else if(style == Style_c.BoxArrowIn) {
			// create arrow in
			setScale(1, 1);
			setBackgroundColor(ColorConstants.white);
			PointList decorationPointList = new PointList();
			createFilledSquare(decorationPointList);
			setTemplate(decorationPointList);
			decorationPointList = new PointList();
			createBoxArrowIn(decorationPointList);
			secondaryDecoration = decorationPointList;
		} else if(style == Style_c.BoxArrowOut) {
			setScale(1, 1);
			setBackgroundColor(ColorConstants.white);
			PointList decorationPointList = new PointList();
			createFilledSquare(decorationPointList);
			setTemplate(decorationPointList);
			decorationPointList = new PointList();
			createBoxArrowOut(decorationPointList);
			secondaryDecoration = decorationPointList;
		} else if(style == Style_c.BoxArrowInOut) {
			setScale(1, 1);
			setBackgroundColor(ColorConstants.white);
			PointList decorationPointList = new PointList();
			createFilledSquare(decorationPointList);
			setTemplate(decorationPointList);
			decorationPointList = new PointList();
			createBoxArrowIn(decorationPointList);
			createBoxArrowOut(decorationPointList);
			secondaryDecoration = decorationPointList;
		}
	}

	static void createFilledSquare(PointList decorationPointList) {
		decorationPointList.addPoint(-8, 8);
		decorationPointList.addPoint(-8, -8);
		decorationPointList.addPoint(8, -8);
		decorationPointList.addPoint(8, 8);
		decorationPointList.addPoint(-8, 8);
	}

	private void createBoxArrowOut(PointList decorationPointList) {		
		decorationPointList.addPoint(-6, 0);
		decorationPointList.addPoint(6, 0);
		decorationPointList.addPoint(2, -3);
		decorationPointList.addPoint(6, 0);
		decorationPointList.addPoint(2, 3);
		decorationPointList.addPoint(6, 0);	
	}

	public void createBoxArrowIn(PointList decorationPointList) {
		decorationPointList.addPoint(6, 0);
		decorationPointList.addPoint(-6, 0);
		decorationPointList.addPoint(-2, -3);
		decorationPointList.addPoint(-6, 0);
		decorationPointList.addPoint(-2, 3);						
		decorationPointList.addPoint(-6, 0);
	}
	
	@Override
	public void setRotation(double angle) {
		if (fStyle == Style_c.FilledSquare || fStyle == Style_c.BoxArrowIn
				|| fStyle == Style_c.BoxArrowInOut
				|| fStyle == Style_c.BoxArrowOut) {
			super.setRotation(0.0);
			return;
		}
		super.setRotation(angle);
	}

	@Override
	public int getLineWidth() {
		if(fStyle == Style_c.FilledSquare)
			return 1;
		return ((PolylineConnection) getParent()).getLineWidth();
	}

	@Override
	protected void fillShape(Graphics g) {
		Linecolorstyle_c lcs = null;
		Fillcolorstyle_c fcs = Fillcolorstyle_c
				.getOneSTY_FCSOnR400(Elementstyle_c
						.getManySTY_SsOnR401(GraphicalElement_c
								.getOneGD_GEOnR2((Connector_c) fPart.getModel())));
		if (fcs == null) {
			// see if there is an element to inherit from
			fcs = fPart.getInheritedFillColorStyle();
			if(fcs == null) {
				// if no fill color inheritance, check
				// for line color inheritance from a shape
				lcs = fPart.getInheritedLineColorStyleFromShape();
			}
		}
		if (fcs != null || lcs != null) {
			if (lcs != null) {
				Color fillColor = Activator.getDefault().getColor(
						lcs.getRed(), lcs.getGreen(), lcs.getBlue());
				g.setBackgroundColor(fillColor);
			} else {
				Color fillColor = Activator.getDefault().getColor(fcs.getRed(),
						fcs.getGreen(), fcs.getBlue());
				g.setBackgroundColor(fillColor);
			}
		}
		super.fillShape(g);
	}

	@Override
	public void setLocation(Point p) {
		super.setLocation(p);
		if(fCustomTransform == null)
			fCustomTransform = new Transform();
		fCustomTransform.setTranslation(p.x, p.y);
	}

	@Override
	public void setScale(double x, double y) {
		super.setScale(x, y);
		if(fCustomTransform == null)
			fCustomTransform = new Transform();
		fCustomTransform.setScale(x, y);
	}

	@Override
	protected void outlineShape(Graphics g) {
		Object parentModel = fPart.getModel();
		GraphicalElement_c graphicalElement = GraphicalElement_c.getOneGD_GEOnR2((Connector_c) parentModel);
		Fillcolorstyle_c fcs = null;
		Linecolorstyle_c lcs = Linecolorstyle_c
				.getOneSTY_LCSOnR400(Elementstyle_c
						.getManySTY_SsOnR401(graphicalElement));
		if(lcs == null) {
			// see if there is an element to inherit from
			lcs = fPart.getInheritedLineColorStyle();
			if(lcs == null) {
				fcs = fPart.getInheritedFillColorStyleFromShape();
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
		g.setAntialias(SWT.ON);
		g.setLineWidth(getLineWidth());
		super.outlineShape(g);
		// if a secondary point list exists
		if(secondaryDecoration != null) {
			if(fPart != null) {
				double rotation = fPart.getCustomDecorationRotation(End_c.Start);
				fCustomTransform.setRotation(rotation);
				g.drawPolygon(getCustomPointsTransformed(fCustomTransform, secondaryDecoration));
			}
			super.outlineShape(g);
		}
	}

	private PointList getCustomPointsTransformed(Transform customTransform,
			PointList customDecoration) {
		PointList list = new PointList();
		for (int i = 0; i < customDecoration.size(); i++)
			list.addPoint(customTransform.getTransformed(customDecoration.getPoint(i)));
		return list;
	}

	public void setEditPart(ConnectorEditPart part) {
		fPart = part;
	}
}
