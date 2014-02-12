//========================================================================
//
//File:      $RCSfile: GraphicsGCDelegate.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:06:29 $
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
package com.mentor.nucleus.bp.ui.graphics.draw;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.ScaledGraphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.ui.canvas.GCDelegate;
import com.mentor.nucleus.bp.ui.canvas.Gr_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class GraphicsGCDelegate extends GCDelegate {

	private Graphics fGraphics;

	public GraphicsGCDelegate(Graphics graphics, float zoom) {
		super();
		Gr_c.Setzoomfactor(zoom);
	    graphics.setFont(GraphicalEditor.getFont());
		fGraphics = graphics;
	}

	public GraphicsGCDelegate(Drawable dr) {
		fGraphics = new ScaledGraphics(new SWTGraphics(new GC(dr)));
	}

	@Override
	public void dispose() {	
	}
	
	@Override
	public void drawArc(int i, int j, int k, int l, int startAngle, int arcAngle) {
	    float lineInset = Math.max(1.0f, getLineWidth()) / 2.0f;
	    int inset1 = (int)Math.floor(lineInset);
	    int inset2 = (int)Math.ceil(lineInset);

	    i += inset1 ; 
	    j += inset1; 
	    k -= inset1 + inset2;
	    l -= inset1 + inset2;
	    
		fGraphics.drawArc(i, j, k, l, startAngle, arcAngle);
	}

	@Override
	public void drawImage(Image image, int sourceX, int sourceY,
			int sourceWidth, int sourceHeight, int destX, int destY,
			int destWidth, int destHeight) {
		fGraphics.drawImage(image, sourceX, sourceY, sourceWidth, sourceHeight,
				destX, destY, destWidth, destHeight);
	}

	@Override
	public void drawLine(int i, int j, int k, int l) {
	    float lineInset = Math.max(1.0f, getLineWidth()) / 2.0f;
	    int inset1 = (int)Math.floor(lineInset);
	    int inset2 = (int)Math.ceil(lineInset);

	    k -= inset1 + inset2;
	    
		fGraphics.drawLine(i, j, k, l);
	}

	@Override
	public void drawOval(int i, int j, int k, int l) {
		
	    float lineInset = Math.max(1.0f, getLineWidth()) / 2.0f;
	    int inset1 = (int)Math.floor(lineInset);
	    int inset2 = (int)Math.ceil(lineInset);

	    i += inset1 ; 
	    j += inset1; 
	    k -= inset1 + inset2;
	    l -= inset1 + inset2;
	    
		fGraphics.drawOval(i, j, k, l);
	}

	@Override
	public void drawPolygon(int[] tri) {
		fGraphics.drawPolygon(tri);
	}

	@Override
	public void drawPolyline(int[] arrow) {
		fGraphics.drawPolyline(arrow);
	}

	@Override
	public void drawRectangle(int i, int j, int k, int l) {
	    float lineInset = Math.max(1.0f, getLineWidth()) / 2.0f;
	    int inset1 = (int)Math.floor(lineInset);
	    int inset2 = (int)Math.ceil(lineInset);

	    i += inset1 ; 
	    j += inset1; 
	    k -= inset1 + inset2;
	    l -= inset1 + inset2;

		fGraphics.drawRectangle(i, j, k, l);
	}

	@Override
	public void drawRoundRectangle(int i, int j, int k, int l, int m, int n) {
	    float lineInset = Math.max(1.0f, getLineWidth()) / 2.0f;
	    int inset1 = (int)Math.floor(lineInset);
	    int inset2 = (int)Math.ceil(lineInset);

	    i += inset1 ; 
	    j += inset1; 
	    k -= inset1 + inset2;
	    l -= inset1 + inset2;
	    
		fGraphics.drawRoundRectangle(new org.eclipse.draw2d.geometry.Rectangle(i, j, k, l), m, n);
	}

	@Override
	public void drawText(String string, int i, int j, boolean b) {
		fGraphics.drawText(string, i, j);
	}

	@Override
	public void fillOval(int i, int j, int k, int l) {
		fGraphics.fillOval(i, j, k, l);
	}

	@Override
	public void fillPolygon(int[] tri) {
	    float lineInset = Math.max(1.0f, getLineWidth()) / 2.0f;
	    int inset2 = (int)Math.ceil(lineInset);

	    // collect the largest x and y values
	    int largestX = 0;
	    int largestY = 0;
	    for(int i = 0; i < tri.length; i++) {
	    	if(i % 2 == 0) {
	    		largestX = Math.max(largestX, tri[i]);
	    	} else {
	    		largestY = Math.max(largestY, tri[i]);
	    	}
	    }
	    // now adjust all values that are equal to the largest
	    for(int i = 0; i < tri.length; i++) {
	    	if(i % 2 == 0 && tri[i] == largestX) {
	    		tri[i] -= inset2;
	    	}
	    	if(i % 2 != 0 && tri[i] == largestY) {
	    		tri[i] -= inset2;
	    	}
	    }
	    
		fGraphics.fillPolygon(tri);
	}

	@Override
	public void fillRectangle(int i, int j, int k, int l) {
        if (bpPrefs.disableGradients == true) {
            fGraphics.fillRectangle(i, j, k, l);
        } else {
            Color current = getForeground();
            setForeground(new Color(Display.getDefault(),
                    convertToRGB(bpPrefs.gradientBaseColor)));
            if (bpPrefs.invertGradients == true) {
                fGraphics.fillGradient(i, j + l, k, l * -1, true);
            } else {
                fGraphics.fillGradient(i, j, k, l, true);
            }
            setForeground(current);
        }
	}

	@Override
	public void fillRectangle(Rectangle rect) {
        if (bpPrefs.disableGradients == true) {
            fGraphics.fillRectangle(new org.eclipse.draw2d.geometry.Rectangle(
					rect));
        } else {
            Color current = getForeground();
            setForeground(new Color(Display.getDefault(),
                    convertToRGB(bpPrefs.gradientBaseColor)));
            if (bpPrefs.invertGradients == true) {
            	fGraphics.fillGradient(rect.x, rect.y + rect.height,
                        rect.width, rect.height * -1, true);
            } else {
            	fGraphics.fillGradient(rect.x, rect.y, rect.width,
                        rect.height, true);
            }
            setForeground(current);
        }
	}

	@Override
	public void fillRoundRectangle(int i, int j, int k, int l, int m, int n) {
		fGraphics.fillRoundRectangle(new org.eclipse.draw2d.geometry.Rectangle(i, j, k, l), m, n);
	}

	@Override
	public Color getBackGround() {
		return fGraphics.getBackgroundColor();
	}

	@Override
	public Rectangle getClipping() {
		org.eclipse.draw2d.geometry.Rectangle rect = new org.eclipse.draw2d.geometry.Rectangle();
		org.eclipse.draw2d.geometry.Rectangle clip = fGraphics.getClip(rect);
		return new Rectangle(clip.x, clip.y, clip.width, clip.height);
	}

	@Override
	public Font getFont() {
		return fGraphics.getFont();
	}

	@Override
	public Color getForeground() {
		return fGraphics.getForegroundColor();
	}

	@Override
	public int getLineWidth() {
		return fGraphics.getLineWidth();
	}

	@Override
	public void setAdvanced(boolean value) {
		// not necessary
	}

	@Override
	public void setAntialias(boolean value) {
		if(value)
			fGraphics.setAntialias(SWT.ON);
		else
			fGraphics.setAntialias(SWT.OFF);
	}

	@Override
	public void setBackground(Color New) {
		fGraphics.setBackgroundColor(New);
	}

	@Override
	public void setClipping(int i, int j, int k, int l) {
		// no need to clip
	}

	@Override
	public void setClipping(Path path) {
		// no need to clip
	}

	@Override
	public void setClipping(Rectangle rectangle) {
		// no need to clip
	}

	@Override
	public void setClipping(Region r) {
		// no need to clip
	}

	@Override
	public void setFont(Font displayFont) {
		fGraphics.setFont(displayFont);
	}

	@Override
	public void setForeground(Color New) {
		fGraphics.setForegroundColor(New);
	}

	@Override
	public void setLineStyle(int i) {
		fGraphics.setLineStyle(i);
	}

	@Override
	public void setLineWidth(int i) {
		fGraphics.setLineWidth(i);
	}

	@Override
	public Point textExtent(String string) {
		Dimension extent = FigureUtilities.getTextExtents(string, getFont());
		return new Point(extent.width, extent.height);
	}

}
