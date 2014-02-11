package com.mentor.nucleus.bp.ui.canvas;
//=====================================================================
//
//File:      $RCSfile: Gr_c.java,v $
//Version:   $Revision: 1.39 $
//Modified:  $Date: 2013/01/10 23:19:00 $
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
// This class implements the realized code for the 'Graphics (GR)'
// External Entity.
//
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ScrollBar;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Justification_c;
import com.mentor.nucleus.bp.core.Style_c;
// Graphics Platform
public class Gr_c {
  public static Canvas cur_canvas = null;
  public static Model_c cur_model = null;

  private static int[] triangle = null;
  private static int[] openArrow = null;
  private static int[] cross;
  private static int lastX = 0;
  private static int lastY = 0;
  public static void Setlastx(int value) {
    Gr_c.lastX = value;
  }
  public static void Setlasty(int value) {
    Gr_c.lastY = value;
  }
  public static void Drawtext(final GCDelegate Context, final int Justified_to, final String Text, final int Text_style, final int X, final int Y) {
    String[] toPrint = Text.split("\n");  //$NON-NLS-1$
    int yOffset = 0;
    if (Justified_to == Justification_c.Center_in_X) {
      for (int i = 0; i < toPrint.length; i++) {
        int width = Scale(Context.textExtent(toPrint[i]).x);
        Context.drawText(toPrint[i], (int) ((X - (width / 2)) * m_ZoomFactor), (int) ((Y + yOffset) * m_ZoomFactor), true);
        yOffset = yOffset + Scale(Context.textExtent(toPrint[i]).y);
      }
    } else if (Justified_to == Justification_c.Center) {
      // Start by Centering the text block in the y axis
      yOffset = -Scale(Context.textExtent(Text).y) / 2;
      for (int i = 0; i < toPrint.length; i++) {
        int width = Scale(Context.textExtent(toPrint[i]).x);
        Context.drawText(toPrint[i], (int) ((X - (width / 2)) * m_ZoomFactor), (int) ((Y + yOffset) * m_ZoomFactor), true);
        yOffset = yOffset + Scale(Context.textExtent(toPrint[i]).y);
      }
    } else if (Justified_to == Justification_c.Right) {
      for (int i = 0; i < toPrint.length; i++) {
        int width = Scale(Context.textExtent(toPrint[i]).x);
        Context.drawText(toPrint[i], (int) ((X - width) * m_ZoomFactor), (int) ((Y + yOffset) * m_ZoomFactor), true);
        yOffset = yOffset + Scale(Context.textExtent(toPrint[i]).y);
      }
    } else {
      Context.drawText(Text, (int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), true);
    }
  } // End drawText
  public static void Drawrect(final GCDelegate Context, boolean filled, final int H, final int W, final int X, final int Y) {
    if (filled) {
    	Context.fillRectangle((int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), (int) (W * m_ZoomFactor), (int) (H * m_ZoomFactor));
    }
    Context.drawRectangle((int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), (int) (W * m_ZoomFactor), (int) (H * m_ZoomFactor));
  } // End drawRect
  public static void Clipto(final GCDelegate Context, final int H, final int W, final int X, final int Y) {
    Context.setClipping((int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), (int) (W * m_ZoomFactor), (int) (H * m_ZoomFactor));
  } // End clipTo
  public static int Gettextextent(final int Axis, final GCDelegate Context, final String Text) {
  	if ( Context == null )
  	{   
		return 0;
  	} 
    Point extent = Context.textExtent(Text);
    if (Axis == Axis_c.X)
      return Scale(extent.x);
    else
      return Scale(extent.y);
  } // End getTextExtent
  public static int Getgraphicspacing() {
    int graphic_spacing = 4;
    return Gr_c.Unscale(graphic_spacing);
  } // End getGraphicSpacing
  public static void Drawline(final GCDelegate Context, final int X, final int X2, final int Y, final int Y2) {
    Context.drawLine((int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), (int) (X2 * m_ZoomFactor), (int) (Y2 * m_ZoomFactor));
  } // End drawLine
  public static void Unclip(final GCDelegate Context) {
    Context.setClipping((Rectangle) null);
  } // End unClip
  public static void Drawroundrect(final GCDelegate Context, final int H, final int W, final int X, final int Y) {
    int curveRadius = 25;
    Context.fillRoundRectangle(
      (int) (X * m_ZoomFactor),
      (int) (Y * m_ZoomFactor),
      (int) (W * m_ZoomFactor),
      (int) (H * m_ZoomFactor),
      (int) (curveRadius * m_ZoomFactor),
      (int) (curveRadius * m_ZoomFactor));
    Context.drawRoundRectangle(
      (int) (X * m_ZoomFactor),
      (int) (Y * m_ZoomFactor),
      (int) (W * m_ZoomFactor),
      (int) (H * m_ZoomFactor),
      (int) (curveRadius * m_ZoomFactor),
      (int) (curveRadius * m_ZoomFactor));
  } // End drawRoundRect
  public static void Drawfolder(final GCDelegate Context, final int H, final int T, final int W, final int X, final int Y) {
    // Tab
    Context.fillRectangle((int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), (int) ((3 * T) * m_ZoomFactor), (int) ((T + 2) * m_ZoomFactor));
    Context.drawRectangle((int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), (int) ((3 * T) * m_ZoomFactor), (int) ((T + 2) * m_ZoomFactor));
    // Main Area
    Context.fillRectangle((int) (X * m_ZoomFactor), (int) ((T + Y) * m_ZoomFactor), (int) (W * m_ZoomFactor), (int) ((H - T) * m_ZoomFactor));
    Context.drawRectangle((int) (X * m_ZoomFactor), (int) ((T + Y) * m_ZoomFactor), (int) (W * m_ZoomFactor), (int) ((H - T) * m_ZoomFactor));
  } // End drawFolder
  public static void Setlinewidth(final GCDelegate Context, final int W) {
    if (m_ZoomFactor > 1.0f)
      Context.setLineWidth((int) (W * m_ZoomFactor));
    else
      Context.setLineWidth(W);
  } // End setLineWidth
  public static void Setlinestyle(final GCDelegate Context, final int New_style) {
    if (New_style == Style_c.Solid) {
      Context.setLineStyle(SWT.LINE_SOLID);
    } else if (New_style == Style_c.Broken) {
      Context.setLineStyle(SWT.LINE_DOT);
    } else if (New_style == Style_c.DashDouble) {
      Context.setLineStyle(SWT.LINE_DASH);
      Context.setLineWidth(Context.getLineWidth() * 2);
    } else if (New_style == Style_c.Dash) {
      Context.setLineStyle(SWT.LINE_DASH);
    }
  } // End setLineStyle
  public static int getSWTLineStyle(int bpStyle) {
	  if (bpStyle == Style_c.Solid) {
			return SWT.LINE_SOLID;
		} else if (bpStyle == Style_c.Broken) {
			return SWT.LINE_DOT;
		} else if (bpStyle == Style_c.DashDouble) {
			return SWT.LINE_DASH;
		} else if (bpStyle == Style_c.Dash) {
			return SWT.LINE_DASH;
		}  
	  return SWT.LINE_SOLID;
  }
  public static int Scale(final int Value) {
	  return Value;
  } // End scale
  public static int Unscale(final int Value) {
	  return Value;
  } // End unScale

  private static int[] topLeft = null;
  private static int[] topRight = null;
  private static int[] bottomLeft = null;
  private static int[] bottomRight = null;

  public static void Drawresizehandles(final GCDelegate Context, final int H, final int W, final int X, final int Y) {
    // Create the corner graphics once the first time they are required
    if (topLeft == null) {
      // Initialize the arrays
      topLeft = new int[12];
      topRight = new int[12];
      bottomLeft = new int[12];
      bottomRight = new int[12];
      int handleSize = Gethotspotsize() / 2;
      // Initialize the corner graphics
      topLeft[0] = 0;
      topLeft[1] = 0;
      topLeft[2] = handleSize * 3;
      topLeft[3] = 0;
      topLeft[4] = handleSize * 3;
      topLeft[5] = handleSize;
      topLeft[6] = handleSize;
      topLeft[7] = handleSize;
      topLeft[8] = handleSize;
      topLeft[9] = handleSize * 3;
      topLeft[10] = 0;
      topLeft[11] = handleSize * 3;
      // Create the other corners
      for (int i = 0; i < topLeft.length / 2; i++) {
        Point toRotate = new Point(topLeft[i * 2], topLeft[(i * 2) + 1]);
        toRotate = rotateAboutOrigin(toRotate, -Math.PI / 2.0);
        topRight[i * 2] = toRotate.x;
        topRight[(i * 2) + 1] = toRotate.y;
        toRotate.x = topLeft[i * 2];
        toRotate.y = topLeft[(i * 2) + 1];
        toRotate = rotateAboutOrigin(toRotate, -Math.PI);
        bottomRight[i * 2] = toRotate.x;
        bottomRight[(i * 2) + 1] = toRotate.y;
        toRotate.x = topLeft[i * 2];
        toRotate.y = topLeft[(i * 2) + 1];
        toRotate = rotateAboutOrigin(toRotate, -Math.PI * 1.5);
        bottomLeft[i * 2] = toRotate.x;
        bottomLeft[(i * 2) + 1] = toRotate.y;
      }
    }
    // Override line width for resize handles
    int lineWidth = Context.getLineWidth();
    Setlinewidth(Context, 1);
    drawHandle(Context, topLeft, X, Y);
    drawHandle(Context, topRight, X + W, Y);
    drawHandle(Context, bottomRight, X + W, Y + H);
    drawHandle(Context, bottomLeft, X, Y + H);
    // Restore line width
    Context.setLineWidth(lineWidth);
  }
  private static void drawHandle(GCDelegate Context, int[] handle, int X, int Y) {
    int[] handlePoints = new int[handle.length];
    for (int i = 0; i < handle.length / 2; i++) {
      handlePoints[i * 2] = Unscale(handle[i * 2] + X);
      handlePoints[(i * 2) + 1] = Unscale(handle[(i * 2) + 1] + Y);
    }
    Context.fillPolygon(handlePoints);
    Context.drawPolygon(handlePoints);
  }
  private static Point rotateAboutOrigin(Point start, double theta) {
    // To rotate by theta radians:
    // x' = x cos(theta) + y sin(theta) y' = -x sin(theta) + y cos(theta)
    // Principles of Interactive Computer Graphics, p54, Newman and Sproull ISBN 0-07-066455-2 7th Printing (1982). 
    return new Point(
      (int) Math.round((double) start.x * Math.cos(theta) + (double) start.y * Math.sin(theta)),
      (int) Math.round((double) - start.x * Math.sin(theta) + (double) start.y * Math.cos(theta)));
  } // End drawResizeHandles
  public static int Gethotspotsize() {
    return 8;
  } // End getHotspotSize
  public static int Getminshapesize() {
    return Gethotspotsize() * 3;
  } // End getMinShapeSize
  public static float Getangle(final int X1, final int X2, final int Y1, final int Y2) {
    // Returns the angle in radians whose tangent is 'y/x'
    // anomalies such as 'x=0' are checked for
    double x = X2 - X1;
    double y = Y2 - Y1;
    double result = 0.0d;
    if (Math.abs(x) < 0.0000001d) {
      if (Math.abs(y) > 0.0000001d) {
        if (y <= 0d) {
          result = Math.PI;
        }
      }
    } else {
      result = (Math.PI * 0.5) - Math.atan(y / x);
      if (x < 0d)
        result = result + Math.PI;
    }
    return (float) result;
  } // End getAngle
  public static int Getinterpolatedx(int Dx, int Dy, int X1, int X2, int X3, int Y1, int Y2, int Y3) {
    Point start = new Point(X1, Y1);
    Point end = new Point(X2, Y2);
    Point join = new Point(X3, Y3);
    double ratio = Gr_c.getDistanceRatio(end, join, start);
    int new_x = X2 + Dx;
    return (int) Math.round((double) (new_x - X1) * ratio) + X1;
  }
  public static int Getinterpolatedy(int Dx, int Dy, int X1, int X2, int X3, int Y1, int Y2, int Y3) {
    Point start = new Point(X1, Y1);
    Point end = new Point(X2, Y2);
    Point join = new Point(X3, Y3);
    double ratio = Gr_c.getDistanceRatio(end, join, start);
    int new_y = Y2 + Dy;
    return (int) Math.round((double) (new_y - Y1) * ratio) + Y1;
  }
  private static double getDistance(Point origin, Point vector) {
    Point dv = getDirectionVector(origin, vector);
    return Math.sqrt(Math.pow(dv.x, 2) + Math.pow(dv.y, 2));
  }
  private static Point getDirectionVector(Point base, Point end) {
    Point result = new Point(end.x - base.x, end.y - base.y);
    return result;
  }
  public static double getDistanceRatio(Point end, Point join, Point start) {
    double seglen = getDistance(start, end);
    double distToJoin = getDistance(start, join);
    return distToJoin / seglen;
  }
  public static int Getxintersect(final int X1, final int X2, final int X3, final int X4, final int Y1, final int Y2, final int Y3, final int Y4) {
    Point result = intersect(new Point(X1, Y1), new Point(X2 - X1, Y2 - Y1), new Point(X3, Y3), new Point(X4 - X3, Y4 - Y3));
    if (result != null) {
      return result.x;
    } else {
      return 0;
    }
  }
  public static int Getyintersect(final int X1, final int X2, final int X3, final int X4, final int Y1, final int Y2, final int Y3, final int Y4) {
    Point result = intersect(new Point(X1, Y1), new Point(X2 - X1, Y2 - Y1), new Point(X3, Y3), new Point(X4 - X3, Y4 - Y3));
    if (result != null) {
      return result.y;
    } else {
      return 0;
    }
  }
  private static Point intersect(Point v1, Point v2, Point v3, Point v4) {
    // Algorithm from Advanced Graphics on VGA and XGA cards using Borland C++,
    // p76, Angell and Tsobelis ISBN 0-333-56765-X
    Point result = null;
    float delta = (float) v2.x * (float) v4.y - (float) v2.y * (float) v4.x;
    if (Math.abs(delta) > 0.0000001d) {
      float mu = (((float) v3.x - (float) v1.x) * (float) v4.y - ((float) v3.y - (float) v1.y) * (float) v4.x) / delta;
      result = new Point((int) ((float) v1.x + mu * (float) v2.x), (int) ((float) v1.y + mu * (float) v2.y));
    }
    return result;
  }
  public static void Drawtriangle(final GCDelegate Context, final float Rotation, final int X, final int Y) {
    if (triangle == null) {
      triangle = new int[6];
      triangle[0] = 0;
      triangle[1] = 0;
      triangle[2] = 12;
      triangle[3] = 24;
      triangle[4] = -12;
      triangle[5] = 24;
    }
    int[] tri = new int[triangle.length];
    Point toRotate = new Point(0, 0);
    for (int i = 0; i < tri.length / 2; i++) {
      toRotate.x = triangle[i * 2];
      toRotate.y = triangle[(i * 2) + 1];
      toRotate = rotateAboutOrigin(toRotate, Rotation);
      tri[i * 2] = Unscale(toRotate.x + X);
      tri[(i * 2) + 1] = Unscale(toRotate.y + Y);
    }
    Context.fillPolygon(tri);
    Context.drawPolygon(tri);
  } // End drawTriangle
  public static String Wraptextto(final GCDelegate Context, final String Input, final int Width) {
  	if ( Context == null ) return Input;
  	
    int x = Gr_c.Gettextextent(Axis_c.X, Context, Input);
    if (x <= Width || Width == 0) {
      return Input;
    }
    String result = "";//$NON-NLS-1$
    String[] lines = Input.split("\n");//$NON-NLS-1$
    for (int i = 0; i < lines.length; i++) {
      String[] words = lines[i].split(" ");//$NON-NLS-1$
      String intermediateResult = "";//$NON-NLS-1$
      int wordCount = words.length;
      int currentWord = 0;
      while (currentWord < wordCount) {
        String line = "";//$NON-NLS-1$
        int wordWidth = Gr_c.Gettextextent(Axis_c.X, Context,
                                                            words[currentWord]);
        if (wordWidth > Width) {
          // there is only room for one word on this line . . . 
          line = words[currentWord];
          currentWord++;
        }
        else {
          // at least one word will fit on this line, but how many?
          boolean atLineStart = true;
          int lineWidth = wordWidth;
          while (lineWidth <= Width && currentWord < wordCount) {
            // total line width <= wrap width and there are more words ...
            if (!atLineStart) {
              // add the space back that was removed by the word split above
              line = line + " ";//$NON-NLS-1$
            }
            if (lineWidth <= Width) {
              // this word will fit, add it to the line ...
              line = line + words[currentWord];
              currentWord++; // ... move the cursor to the next word ...
              if (currentWord < wordCount) {
            	  // ... and recompute the line width with the addition 
            	  lineWidth = Gr_c.Gettextextent(Axis_c.X, Context, line +
            			                 " " + words[currentWord]);//$NON-NLS-1$
              }
            }
            atLineStart = false;
          }
        }
        intermediateResult = intermediateResult + line;
        if (currentWord < wordCount) {
          intermediateResult = intermediateResult + "\n";//$NON-NLS-1$
        }
      } // while len < count
      if (i < lines.length -1) {
    	  intermediateResult = intermediateResult + "\n";//$NON-NLS-1$
      }
      result = result + intermediateResult;
    } // for each line
    return result;
  } // End wrapTextTo
  public static int Getdirection(final float Angle) {
    double PIo4 = java.lang.Math.PI / 4;
    if (Angle >= PIo4 && Angle < 3 * PIo4) {
      return Direction_c.East;
    } else if (Angle >= 3 * PIo4 && Angle < 5 * PIo4) {
      return Direction_c.North;
    } else if (Angle >= 5 * PIo4 && Angle < 7 * PIo4) {
      return Direction_c.West;
    } else {
      return Direction_c.South;
    }
  } // End getDirection
  public static int Getmargin() {
    return 50;
  } // End getMargin

    public static String Fittextto(final GCDelegate Context,
        boolean elideAtEnd, final String Input, final int Width)
    {
    String[] toFit = Input.split("\n");//$NON-NLS-1$
    String[] result = new String[toFit.length];
    String ellipsis = "...";//$NON-NLS-1$
    int ellipWidth = Gr_c.Gettextextent(Axis_c.X, Context, ellipsis);
    for (int i = 0; i < toFit.length; i++) {
      result[i] = toFit[i];
      int x = Gr_c.Gettextextent(Axis_c.X, Context, toFit[i]);
      if (x > Width) {
        int fitLen = Width - ellipWidth;
        int len = toFit[i].length();
                while (Gr_c.Gettextextent(Axis_c.X, Context, result[i]) > fitLen
                    && len > 1) {
                    if (elideAtEnd) result[i] = result[i].substring(0, --len);
                    else result[i] = result[i].substring(1, len--);
        }
                if (elideAtEnd) result[i] += ellipsis;
                else result[i] = ellipsis + result[i];
      }
    }
    if (result.length == 1) {
      return result[0];
    } else {
      String concatResult = "";//$NON-NLS-1$
      for (int i = 0; i < result.length; i++) {
        concatResult = concatResult + result[i];
        if (i != result.length - 1) {
          concatResult = concatResult + "\n";//$NON-NLS-1$
        }
      }
      return concatResult;
    }
    } 

  public static void Drawarrow(final GCDelegate Context, boolean filled, final float Rotation, final int X, final int Y) {
    if (openArrow == null) {
      openArrow = new int[6];
      openArrow[0] = -6;
      openArrow[1] = 12;
      openArrow[2] = 0;
      openArrow[3] = 0;
      openArrow[4] = 6;
      openArrow[5] = 12;
    }
    int[] arrow = new int[openArrow.length];
    Point toRotate = new Point(0, 0);
    for (int i = 0; i < arrow.length / 2; i++) {
      toRotate.x = openArrow[i * 2];
      toRotate.y = openArrow[(i * 2) + 1];
      toRotate = rotateAboutOrigin(toRotate, Rotation);
      arrow[i * 2] = Unscale(toRotate.x + X);
      arrow[(i * 2) + 1] = Unscale(toRotate.y + Y);
    }
    if (filled) {
      Color bg = Context.getBackGround();
      // Arrowheads are always black
      Context.setBackground(new Color(Display.getDefault(), new RGB(0, 0, 0)));
      Context.fillPolygon(arrow);
      Context.setBackground(bg);
    }
    Context.drawPolyline(arrow);
  } // End drawOpenArrow
  public static void Drawcross(final GCDelegate Context, final float Rotation, final int X, final int Y) {
	  if(cross == null) {
		  int extent = 24;
		  cross = new int[10];
		  cross[0] = extent * -1;
		  cross[1] = extent;
		  cross[2] = extent;
		  cross[3] = extent * -1;
		  cross[4] = 0;
		  cross[5] = 0;
		  cross[6] = extent * -1;
		  cross[7] = extent * -1;
		  cross[8] = extent;
		  cross[9] = extent;		  
	  }
	    int[] curCross = new int[cross.length];
	    Point toRotate = new Point(0, 0);
	    for (int i = 0; i < curCross.length / 2; i++) {
	      toRotate.x = Unscale(cross[i * 2]);
	      toRotate.y = Unscale(cross[(i * 2) + 1]);
	      toRotate = rotateAboutOrigin(toRotate, Rotation);
	      curCross[i * 2] = toRotate.x + Unscale(X);
	      curCross[(i * 2) + 1] = toRotate.y + Unscale(Y);
	    }
	  Context.drawPolyline(curCross);
  }
  public static void Drawactor(final GCDelegate Context, final int height, final int width, final int X, final int Y) {
	  // draw the head
	  int radius = width / 8;
	  // get the x coordinate which is the given x value
	  // plus half the given width
	  int locX = X + (width / 2);
	  // get the y coordinate which is the given y value
	  // plus the radius
	  int locY = Y + radius;

	  // set the line width directly
	  // to avoid increasing the width
	  // due to scaling
	  Context.setLineWidth(2);

	  // draw the head at the determined location
	  Drawcircle(Context,  false, radius, locX, locY);
	  
	  // adjust values for scaling
	  locX = Unscale(locX);
	  locY = Unscale(locY);
	  int scaledHeight = Unscale(height);
	  int scaledWidth = Unscale(width);
	  int scaledRadius = Unscale(radius);
	  
	  // define body points
	  int bodyLength = scaledHeight - (scaledRadius * 2);
	  int bodyWidth = scaledWidth * 2/3;
	  
	  Point start = new Point(locX, locY + scaledRadius);
	  Point center = new Point(locX, start.y + scaledRadius);
	  // remove neck length from bodyLength
	  bodyLength = bodyLength - scaledRadius;
	  Point end = new Point(locX, center.y + (bodyLength / 2));
	  Point larm = new Point(center.x - (bodyWidth / 2), center.y);
	  Point rarm = new Point(center.x + (bodyWidth / 2), center.y);
	  Point lleg = new Point(center.x - ((Unscale(Y) + scaledHeight) - end.y), Unscale(Y) + scaledHeight);
	  Point rleg = new Point(center.x + ((Unscale(Y) + scaledHeight) - end.y), Unscale(Y) + scaledHeight);

	  // create the body
	  int[] actorBody = new int[18];
	  actorBody[0] = start.x;
	  actorBody[1] = start.y;
	  actorBody[2] = center.x;
	  actorBody[3] = center.y;
	  actorBody[4] = larm.x;
	  actorBody[5] = larm.y;
	  actorBody[6] = rarm.x;
	  actorBody[7] = rarm.y;
	  actorBody[8] = center.x;
	  actorBody[9] = center.y;
	  actorBody[10] = end.x;
	  actorBody[11] = end.y;
	  actorBody[12] = lleg.x;
	  actorBody[13] = lleg.y;
	  actorBody[14] = end.x;
	  actorBody[15] = end.y;
	  actorBody[16] = rleg.x;
	  actorBody[17] = rleg.y;	  
	  
	  Context.drawPolyline(actorBody);
  }  
  public static void Drawcircle(final GCDelegate Context, boolean filled, final int Radius, final int X, final int Y) {
	    int w = Radius * 2;
	    int h = Radius * 2;
	    int x = X - Radius;
	    int y = Y - Radius;
	    if(filled) {
	    	Color current = Context.getBackGround();
	    	Context.setBackground(new Color(Display.getDefault(), 0, 0, 0));
	    	Context.fillOval((int) (x * m_ZoomFactor), (int) (y * m_ZoomFactor), (int) (w * m_ZoomFactor), (int) (h * m_ZoomFactor));
	    	Context.setBackground(current);
	    } else {
	    	Context.drawOval((int) (x * m_ZoomFactor), (int) (y * m_ZoomFactor), (int) (w * m_ZoomFactor), (int) (h * m_ZoomFactor));
	    }
  } // End drawFilledCircle
  public static void Drawsemicircle(final GCDelegate Context, boolean filled, final int Radius, float Rotation, final int X, final int Y) {
	    int w = Radius * 2;
	    int h = Radius * 2;
	    int x = X - Radius;
	    int y = Y - Radius;
	    double startAngle = (Rotation * (180 / Math.PI)) - 180;
	    Context.drawArc((int)(x * m_ZoomFactor), (int) (y * m_ZoomFactor), (int) (w * m_ZoomFactor), (int) (h * m_ZoomFactor), (int) startAngle, 180);
  } // End drawFilledCircle

  public static void Setantialias(final GCDelegate Context, boolean Value) {
	  Context.setAntialias(Value);
  }
  public static void Setadvancedgraphics(final GCDelegate Context, boolean Value) {
	  Context.setAdvanced(Value);
  }
  public static void Drawellipse(final GCDelegate Context, final int H, final int W, final int X, final int Y) {
	  Context.fillOval((int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), (int) (W * m_ZoomFactor), (int) (H * m_ZoomFactor));
	  Context.drawOval((int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), (int) (W * m_ZoomFactor), (int) (H * m_ZoomFactor));
  } // End drawFilledCircle 
  public static void Setfillto(final GCDelegate Context, final Color New) {
    Context.setBackground(New);
  } // End setFillTo
  public static void Setdefaultfill(final GCDelegate Context) {
    Context.setBackground(new Color(Display.getDefault(), 255, 255, 255));
  } // End setDefaultFill
  public static void Sethighlightfill(final GCDelegate Context, final Color Reference) {
    int r,g,b;
    if ((Reference.getRed() == Reference.getBlue() && Reference.getBlue() == Reference.getGreen()) &&
    		(Reference.getRed() == 0 || Reference.getRed() == 255)) {
      // background is black or white, use 50% grey
      r = 128;
      g = 128;
      b = 128;
    }
    else {
      // Note colors are deliberately rotated to provide contrast
      r = 255 - (255 - Reference.getBlue())* 2/5;
      g = 255 - (255 - Reference.getRed())* 2/5;
      b = 255 - (255 - Reference.getGreen())* 2/5;
    }
    Context.setBackground(new Color(Display.getDefault(), r, g, b));
  } // End Sethighlightfill
  public static void Clearbackgroundto(final GCDelegate Context, final Color New) {
    Rectangle rect = Context.getClipping();
    Context.setBackground(New);
    Context.fillRectangle(rect);
  } // End clearBackgroundTo
  public static void Setzoomfactor(final float Value) {
    m_ZoomFactor = Value;
  }
  private static float m_ZoomFactor = 0.0f;
  public static float getZoomFactor() {
    return m_ZoomFactor;
  } // End setZoomFactor
  public static int Getdefaulttextwidth() {
    return 100;
  } // End getDefaultTextWidth
  public static void Setmousecursor(final String Type) {
    if (cur_canvas != null && !cur_canvas.isDisposed()) {
      if (Type.equals("open hand")) {//$NON-NLS-1$
        cur_canvas.setCursor(getOpenHand());
      } else if (Type.equals("closed hand")) {//$NON-NLS-1$
        cur_canvas.setCursor(getClosedHand());
      } else if (Type.equals("pencil")) {//$NON-NLS-1$
        cur_canvas.setCursor(getPencil());
      } else if (Type.equals("nesw")) {//$NON-NLS-1$
        cur_canvas.setCursor(getNesw());
      } else if (Type.equals("nwse")) {//$NON-NLS-1$
        cur_canvas.setCursor(getNwse());
      } else if (Type.equals("waypoint tool")) {//$NON-NLS-1$
        cur_canvas.setCursor(getWaypoint());
      } else {
        cur_canvas.setCursor(null); //default
      }
    }
  } // End setMouseCursor
  private static Cursor openHand = null;
  public static Cursor getOpenHand() {
    if (openHand == null) {
      openHand =
        new Cursor(
          Display.getDefault(),
          CorePlugin.getImageDescriptor("hand.gif").getImageData(),//$NON-NLS-1$
          CorePlugin.getImageDescriptor("hand_mask.gif").getImageData(),//$NON-NLS-1$
          12,
          12);
    }
    return openHand;
  }
  private static Cursor closedHand = null;
  public static Cursor getClosedHand() {
    if (closedHand == null) {
      closedHand =
        new Cursor(
          Display.getDefault(),
          CorePlugin.getImageDescriptor("closed_hand.gif").getImageData(),//$NON-NLS-1$
          CorePlugin.getImageDescriptor("closed_hand_mask.gif").getImageData(),//$NON-NLS-1$
          12,
          12);
    }
    return closedHand;
  }
  private static Cursor pencil = null;
  private static Cursor getPencil() {
    if (pencil == null) {
      pencil =
        new Cursor(
          Display.getDefault(),
          CorePlugin.getImageDescriptor("pencil.gif").getImageData(),//$NON-NLS-1$
          CorePlugin.getImageDescriptor("pencil_mask.gif").getImageData(),//$NON-NLS-1$
          12,
          12);
    }
    return pencil;
  }
  private static Cursor nesw = null;
  private static Cursor getNesw() {
    if (nesw == null) {
      nesw =
        new Cursor(
          Display.getDefault(),
          CorePlugin.getImageDescriptor("nesw_arrows.gif").getImageData(),//$NON-NLS-1$
          CorePlugin.getImageDescriptor("nesw_arrows_mask.gif").getImageData(),//$NON-NLS-1$
          12,
          12);
    }
    return nesw;
  }
  private static Cursor nwse = null;
  private static Cursor getNwse() {
    if (nwse == null) {
      nwse =
        new Cursor(
          Display.getDefault(),
          CorePlugin.getImageDescriptor("nwse_arrows.gif").getImageData(),//$NON-NLS-1$
          CorePlugin.getImageDescriptor("nwse_arrows_mask.gif").getImageData(),//$NON-NLS-1$
          12,
          12);
    }
    return nwse;
  }
  private static Cursor waypoint = null;
  private static Cursor getWaypoint() {
    if (waypoint == null) {
      waypoint =
        new Cursor(
          Display.getDefault(),
          CorePlugin.getImageDescriptor("waypoint_tool.gif").getImageData(),//$NON-NLS-1$
          CorePlugin.getImageDescriptor("waypoint_tool_mask.gif").getImageData(),//$NON-NLS-1$
          12,
          12);
    }
    return waypoint;
  }
  public static void Redraw() {
    if (cur_canvas != null) {
      cur_canvas.update();
    }
  } // End reDraw
  public static int Getabsolutexposition() {
    return lastX;
  } // End getAbsoluteXPosition
  public static int Getabsoluteyposition() {
    return lastY;
  } // End getAbsoluteYPosition
  public static float Getgradient(final int X1, final int X2, final int Y1, final int Y2) {
    if (X1 == X2)
      return Float.MAX_VALUE;
    return ((float) Y1 - (float) Y2) / ((float) X1 - (float) X2);
  } // End getGradient
  public static void Drawflexhandles(final GCDelegate Context, final int Radius, final int X, final int Y) {
    // Override line width for flex handles
    int lineWidth = Context.getLineWidth();
    Setlinewidth(Context, 1);
    int offsetX = X - Radius;
    int offsetY = Y - Radius;
    Context.drawOval((int) (offsetX * m_ZoomFactor), (int) (offsetY * m_ZoomFactor), (int) (Radius * m_ZoomFactor) * 2, (int) (Radius * m_ZoomFactor) * 2);
    // Restore line width
    Context.setLineWidth(lineWidth);
  } // End drawFlexHandles
  public static void Scrollby(final int X, final int Y) {
    Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(cur_model);
    if (cur_canvas != null && diagram != null) {
      ScrollBar hScrollBar = cur_canvas.getHorizontalBar();
      ScrollBar vScrollBar = cur_canvas.getVerticalBar();
      int newValX = hScrollBar.getSelection() - (int) ((X) / m_ZoomFactor);
      hScrollBar.setSelection(newValX);
      int newValY = vScrollBar.getSelection() - (int) ((Y) / m_ZoomFactor);
      vScrollBar.setSelection(newValY);
      Rectangle visibleRect = cur_canvas.getClientArea();
      int imageWidth = 8000;
      int imageHeight = 6000;
      final float oldXOffset = diagram.getViewportx();
      final float newXOffset = Math.min(hScrollBar.getSelection(), imageWidth - visibleRect.width);
      final float oldYOffset = diagram.getViewporty();
      final float newYOffset = Math.min(vScrollBar.getSelection(), imageHeight - visibleRect.height);
      if (imageWidth > visibleRect.width) {
        if (oldXOffset != newXOffset) {
          diagram.setViewportx(newXOffset);
        }
      }
      if (imageHeight > visibleRect.height) {
        if (oldYOffset != newYOffset) {
          diagram.setViewporty(newYOffset);
        }
      }
    }
  } // End scrollBy
  public static int Getclientwidth() {
	  if(cur_canvas != null)
		  if(!cur_canvas.isDisposed()) {
			  return cur_canvas.getClientArea().width;
		  }
		  return 1000;
  }
  public static int Getclientheight() {
	  if(cur_canvas != null)
		  if(!cur_canvas.isDisposed()) {
			  return cur_canvas.getClientArea().height;
		  }
		  return 1000;
  }
  public static boolean Iswithinhotspot(final int X, final int X2, final int Y, final int Y2) {
    if ((X > X2 - Gr_c.Gethotspotsize() && X < X2 + Gr_c.Gethotspotsize())) {
      if ((Y > Y2 - Gr_c.Gethotspotsize() && Y < Y2 + Gr_c.Gethotspotsize())) {
        return true;
      }
    }
    return false;
  } // End isWithinHotspot
  public static GCDelegate Getcontext() {
    if ( cur_canvas == null )
    {
		return null;
    }
    Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(cur_model);
    if ( diagram == null ) {
        return null;
    }
    GCDelegate gc = new GCDelegate(Display.getDefault());
    String prefFont = JFacePreferences.getPreferenceStore().getString("com.mentor.nucleus.bp.canvas.font");//$NON-NLS-1$
    prefFont = prefFont.substring(0, prefFont.indexOf(';'));
    FontData prefFontData = new FontData(prefFont);
    int fontSize = (int) (prefFontData.getHeight()* diagram.getZoom());
    prefFontData.setHeight(fontSize);
    Font displayFont = new Font(cur_canvas.getDisplay(), prefFontData);
    gc.setFont(displayFont);
    return gc;
  }
  public static void Disposecontext(GCDelegate gc) {
  	if ( gc != null )
  	{
		gc.getFont().dispose();
		gc.dispose();
  	}
  }
  public static boolean Isover(final int Tolerance, final int X, final int X1, final int X2, final int Y, final int Y1, final int Y2) {
    boolean result = false;
    int max_x = X2;
    int min_x = X1;
    if ((X1 > X2)) {
      max_x = X1;
      min_x = X2;
    }
    int max_y = Y2;
    int min_y = Y1;
    if ((Y1 > Y2)) {
      max_y = Y1;
      min_y = Y2;
    }
    float m = Gr_c.Getgradient(X1, X2, Y1, Y2);
    float c = Y1 - (m * X1);
    if ((m < 10000.0f && m > -10000.0f)) {
      float min_projected_y = (m * (X - Tolerance)) + c;
      float max_projected_y = (m * (X + Tolerance)) + c;
      if ((m < 0.0f)) {
        float temp = min_projected_y;
        min_projected_y = max_projected_y;
        max_projected_y = temp;
      }
      if (((Y > min_projected_y - Tolerance) && (Y < max_projected_y + Tolerance))) {
        if (((X > (min_x - Tolerance) && (X < (max_x + Tolerance))) && (Y > (min_y - Tolerance) && (Y < (max_y + Tolerance))))) {
          result = true;
        }
      }
    } else {
      if (((X > (min_x - Tolerance) && (X < (max_x + Tolerance))) && (Y > (min_y - Tolerance) && (Y < (max_y + Tolerance))))) {
        result = true;
      }
    }
    return result;
  } // End isOver
  
    
    /**
     * Draws the image of the given name within the given graphics context
     * at the given diagram coordinates, which are converted to the canvas'
     * space.  The image is scaled according to the diagram's zoom factor.
     */  
    public static void Drawimage(GCDelegate Context, String name, int x, int y)
    {
        Image image = CorePlugin.getImageFor(name, false);
        Rectangle bounds = image.getBounds();
        Context.drawImage(image, 0, 0, bounds.width, bounds.height,
            (int)(x * m_ZoomFactor), (int)(y * m_ZoomFactor),
            (int)(bounds.width * m_ZoomFactor),
            (int)(bounds.height * m_ZoomFactor));
    }
    
    static private class PointSet {
    	public int numPoints;
    	public int xy[];
    	PointSet (int p_numPoints) {
    		numPoints = 0;
    		xy = new int[p_numPoints*2];
    	}
    }
    public static Object Startpoly(int numVert)
    {
    	return new Gr_c.PointSet(numVert);
    }
    public static void Polyaddpoint(Object poly, int x, int y)
    {
    	Gr_c.PointSet ps = (PointSet) poly;
    	ps.xy[ps.numPoints++] = Unscale(x);
    	ps.xy[ps.numPoints++] = Unscale(y);
    }
    public static void Polydraw(GCDelegate Context, Object poly)
    {
    	Gr_c.PointSet ps = (PointSet) poly;
        Context.fillPolygon(ps.xy);
        Context.drawPolygon(ps.xy);
    }
} // End Gr_c
