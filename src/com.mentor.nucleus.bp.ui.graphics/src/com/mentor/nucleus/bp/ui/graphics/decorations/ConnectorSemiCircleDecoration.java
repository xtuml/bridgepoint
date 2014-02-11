//========================================================================
//
//File:      $RCSfile: ConnectorSemiCircleDecoration.java,v $
//Version:   $Revision: 1.8 $
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

import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.graphics.Activator;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;

public class ConnectorSemiCircleDecoration extends PolylineDecoration {
	private static final Point TEMP_POINT = new Point(); 
	private static final Rectangle TEMP_RECTANGLE = new Rectangle();
	
	private final int GAP = 12;
	private int myRadius;
	private int myAngle;
	private ConnectorEditPart parent;

	public ConnectorSemiCircleDecoration(String tooltip, ConnectorEditPart parent) {
		setRadius(12);
		setScale(1, 1);
		if(!tooltip.equals("")) {
			Label label = new Label(tooltip);
			label.setBorder(new MarginBorder(3, 3, 3, 3));
			setToolTip(label);
		}
		this.parent = parent;
	}
	
	public void setRadius(int radius){
		myRadius = radius;
		setTemplate(new PointList(new int[] {radius - GAP, 0} ));
	}
	
	@Override
	public Rectangle getBounds() {
		if (bounds == null){
			//implicitly sets bounds
			super.getBounds();
			computeArcBounds(TEMP_RECTANGLE);
			TEMP_RECTANGLE.expand(1, 1);
			bounds.union(TEMP_RECTANGLE);
		}
		return bounds;
	}
	
	@Override
	public void setRotation(double angle) {
		super.setRotation(angle);
		myAngle = (int) (angle * 180 / Math.PI);
	}
	
	@Override
	protected void fillShape(Graphics g) {
		//do nothing
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
		computeArcBounds(TEMP_RECTANGLE);
		g.drawArc(TEMP_RECTANGLE, -myAngle + 80, 200);
	}
	
	private void computeArcBounds(Rectangle output){
		if (getPoints().size() == 0){
			output.setSize(0, 0);
			return;
		}
		getPoints().getPoint(TEMP_POINT, 0);
		output.setLocation(TEMP_POINT.x - myRadius, TEMP_POINT.y - myRadius);
		output.setSize(2 * myRadius, 2 * myRadius);
	}
	
	@Override
	public int getLineWidth() {
		return ((PolylineConnection) getParent()).getLineWidth();
	}
}
