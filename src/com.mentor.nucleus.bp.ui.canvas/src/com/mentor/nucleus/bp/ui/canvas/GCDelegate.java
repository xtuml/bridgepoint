package com.mentor.nucleus.bp.ui.canvas;

//=====================================================================
//
//File:      $RCSfile: GCDelegate.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/01/10 23:19:01 $
//
//(c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================
//
//This class provides a wrapper around the SWT GC class. It is necessary
//because the SWT GC class is declared 'final', which means that it is
//not possible to extend it. Extension is desirable because we wish to
//instrument the GC class to verify that canvasses are being drawn
//correctly. For example, in com.mentor.nucleus.ui.canvas.test, a subtype of
//this class writes an entry to a testlog for each call made through the
//GCDelegate interface. This testlog is then compared with a specimin to
//determine that the correct output was produced.
//
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesModel;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;

public class GCDelegate {
    GC context = null;

    protected IPreferenceModel prefsModel;

    protected BridgePointPreferencesModel bpPrefs;

    public GCDelegate(Drawable dr) {
        context = new GC(dr);

        prefsModel = new BridgePointPreferencesModel();
        prefsModel.getStore().loadModel(
                CorePlugin.getDefault().getPreferenceStore(), null, prefsModel);
        bpPrefs = (BridgePointPreferencesModel) prefsModel;
    }

    public GCDelegate() {
        prefsModel = new BridgePointPreferencesModel();
        prefsModel.getStore().loadModel(
                CorePlugin.getDefault().getPreferenceStore(), null, prefsModel);
        bpPrefs = (BridgePointPreferencesModel) prefsModel;
    }

    public Point textExtent(String string) {
        return context.textExtent(string);
    }

    public void drawText(String string, int i, int j, boolean b) {
        context.drawText(string, i, j, b);
    }

    public void fillRectangle(int i, int j, int k, int l) {
        if (bpPrefs.disableGradients == true) {
            context.fillRectangle(i, j, k, l);
        } else {
            Color current = getForeground();
            setForeground(new Color(Display.getDefault(),
                    convertToRGB(bpPrefs.gradientBaseColor)));
            if (bpPrefs.invertGradients == true) {
                context.fillGradientRectangle(i, j + l, k, l * -1, true);
            } else {
                context.fillGradientRectangle(i, j, k, l, true);
            }
            setForeground(current);
        }
    }

    public void drawRectangle(int i, int j, int k, int l) {
        context.drawRectangle(i, j, k, l);
    }

    public void setClipping(int i, int j, int k, int l) {
        context.setClipping(i, j, k, l);
    }

    public void setClipping(Rectangle rectangle) {
        context.setClipping(rectangle);
    }

    public void setClipping(Region r) {
        context.setClipping(r);
    }

    public void setClipping(Path path) {
        context.setClipping(path);
    }

    public void fillRoundRectangle(int i, int j, int k, int l, int m, int n) {
        context.fillRoundRectangle(i, j, k, l, m, n);
    }

    public void drawLine(int i, int j, int k, int l) {
        context.drawLine(i, j, k, l);
    }

    public void drawRoundRectangle(int i, int j, int k, int l, int m, int n) {
        context.drawRoundRectangle(i, j, k, l, m, n);
    }

    public void fillOval(int i, int j, int k, int l) {
        context.fillOval(i, j, k, l);
    }

    public void drawOval(int i, int j, int k, int l) {
        context.drawOval(i, j, k, l);
    }

    public void drawArc(int i, int j, int k, int l, int startAngle, int arcAngle) {
        context.drawArc(i, j, k, l, startAngle, arcAngle);
    }

    public void setLineWidth(int i) {
        context.setLineWidth(i);
    }

    public void setLineStyle(int i) {
        context.setLineStyle(i);
    }

    public int getLineWidth() {
        return context.getLineWidth();
    }

    public Color getBackGround() {
        return context.getBackground();
    }

    public Color getForeground() {
        return context.getForeground();
    }

    public void fillPolygon(int[] tri) {
        if (bpPrefs.disableGradients == true) {
            context.fillPolygon(tri);
        } else {
            // Remember the old clipping
            Rectangle oldClipping = getClipping();

            Region r = new Region();
            r.add(tri);

            setClipping(r);

            Rectangle rect = r.getBounds();
            fillRectangle(rect);

            // Clean up
            setClipping(oldClipping);
            r.dispose();
        }
    }

    public void drawPolygon(int[] tri) {
        context.drawPolygon(tri);
    }

    public void drawPolyline(int[] arrow) {
        context.drawPolyline(arrow);
    }

    public void setBackground(Color New) {
        context.setBackground(New);
    }

    public void setForeground(Color New) {
        context.setForeground(New);
    }

    public Rectangle getClipping() {
        return context.getClipping();
    }

    public void fillRectangle(Rectangle rect) {
        if (bpPrefs.disableGradients == true) {
            context.fillRectangle(rect);
        } else {
            Color current = getForeground();
            setForeground(new Color(Display.getDefault(),
                    convertToRGB(bpPrefs.gradientBaseColor)));
            if (bpPrefs.invertGradients == true) {
                context.fillGradientRectangle(rect.x, rect.y + rect.height,
                        rect.width, rect.height * -1, true);
            } else {
                context.fillGradientRectangle(rect.x, rect.y, rect.width,
                        rect.height, true);
            }
            setForeground(current);
        }
    }

    public void setFont(Font displayFont) {
        context.setFont(displayFont);
    }

    public Font getFont() {
        return context.getFont();
    }

    public void dispose() {
        context.dispose();
    }

    public void drawImage(Image image, int sourceX, int sourceY,
            int sourceWidth, int sourceHeight, int destX, int destY,
            int destWidth, int destHeight) {
        context.drawImage(image, sourceX, sourceY, sourceWidth, sourceHeight,
                destX, destY, destWidth, destHeight);
    }

    public void setAntialias(boolean value) {
        if (value)
            context.setAntialias(SWT.ON);
        else
            context.setAntialias(SWT.OFF);
    }

    public void setAdvanced(boolean value) {
        context.setAdvanced(value);
    }

    protected RGB convertToRGB(long val) {
        int red;
        int green;
        int blue;

        blue = (int) (val & 0xFF);
        green = (int) ((val >> 8) & 0xFF);
        red = (int) ((val >> 16) & 0xFF);

        RGB rVal = new RGB(red, green, blue);
        return rVal;
    }
}
