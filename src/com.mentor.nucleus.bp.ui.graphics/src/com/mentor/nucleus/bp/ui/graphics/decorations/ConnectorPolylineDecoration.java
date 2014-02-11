//========================================================================
//
//File:      $RCSfile: ConnectorPolylineDecoration.java,v $
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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.mentor.nucleus.bp.core.Style_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.graphics.Activator;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;

public class ConnectorPolylineDecoration extends PolylineDecoration {

	private int fStyle;
	private Point cachedLocation;
	private ConnectorEditPart parent;

	public ConnectorPolylineDecoration(int style, String tooltip, ConnectorEditPart parent) {
		super();
		setScale(1, 1);
		fStyle = style;
		if(!tooltip.equals("")) {
			Label label = new Label(tooltip);
			label.setBorder(new MarginBorder(3, 3, 3, 3));
			setToolTip(label);
		}
		if(style == Style_c.OpenArrow) {
			PointList decorationPointList = new PointList();
			decorationPointList.addPoint(-12, -6);
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(-12, 6);
			setTemplate(decorationPointList);			
		} else if(style == Style_c.Cross) {
			PointList decorationPointList = new PointList();
			int extent = 24;
			decorationPointList.addPoint(extent * -1, extent);
			decorationPointList.addPoint(extent, extent * -1);
			decorationPointList.addPoint(0, 0);
			decorationPointList.addPoint(extent * -1, extent * -1);
			decorationPointList.addPoint(extent, extent);
			setTemplate(decorationPointList);			
		}
		this.parent = parent;
	}

	@Override
	public int getLineWidth() {
		if(fStyle == Style_c.Cross) {
			return ((PolylineConnection) getParent()).getLineWidth() * 3;
		}
		return ((PolylineConnection) getParent()).getLineWidth();
	}

	@Override
	protected void outlineShape(Graphics g) {
		Object parentModel = parent.getModel();
		GraphicalElement_c graphicalElement = GraphicalElement_c.getOneGD_GEOnR2((Connector_c) parentModel);
		Fillcolorstyle_c fcs = null;
		Linecolorstyle_c lcs = Linecolorstyle_c
				.getOneSTY_LCSOnR400(Elementstyle_c
						.getManySTY_SsOnR401(graphicalElement));
		if(lcs == null) {
			// see if there is an element to inherit from
			lcs = parent.getInheritedLineColorStyle();
			if(lcs == null) {
				fcs = parent.getInheritedFillColorStyleFromShape();
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
	}

	public void setOrigin(Point newPoint) {
		setLocation(newPoint);
	}
	
	@Override
	public void setLocation(Point location) {
		cachedLocation = location.getCopy();
		super.setLocation(location);
	}
	@Override
	public Rectangle getBounds() {
		Rectangle adjustedBounds = super.getBounds().getCopy();
		if(cachedLocation == null)
			return adjustedBounds;
		adjustedBounds.union(getTargetArea());
		return adjustedBounds;
	}
	@Override
	public boolean containsPoint(int x, int y) {
		boolean result = super.containsPoint(x, y);
		if (!result && cachedLocation != null) {
			Rectangle targetArea = getTargetArea();
			if (targetArea.contains(x, y))
				result = true;
		}
		return result;
	}

	private Rectangle getTargetArea() {
		Rectangle targetArea = new Rectangle();
		targetArea.setSize(16, 16);
		targetArea.setLocation(cachedLocation.x - 8, cachedLocation.y - 8);
		return targetArea;
	}

}
