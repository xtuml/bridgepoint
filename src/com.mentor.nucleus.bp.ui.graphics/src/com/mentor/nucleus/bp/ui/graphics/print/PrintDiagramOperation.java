//========================================================================
//
//File:      $RCSfile: PrintDiagramOperation.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:06:27 $
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
package com.mentor.nucleus.bp.ui.graphics.print;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.print.PrintGraphicalViewerOperation;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicalZoomManager;

public class PrintDiagramOperation extends PrintGraphicalViewerOperation {

	private GraphicalEditor fEditor;
	public static final int NO_FIT = 5;

	public PrintDiagramOperation(Printer p, GraphicalViewer g,
			GraphicalEditor editor) {
		super(p, g);
		fEditor = editor;
	}

	@Override
	protected void setupPrinterGraphicsFor(Graphics graphics, IFigure figure) {
		Rectangle symbolExtent = GraphicalZoomManager
				.getExtentRectangle(GraphicalEditor.getAllSymbols(getViewer(),
						fEditor.getModel().Hascontainersymbol()));
		configureGraphics(graphics, figure, getPrinter(), getPrintRegion(),
				symbolExtent, getPrintMode());
	}

	@Override
	protected void printPages() {
		Graphics graphics = getFreshPrinterGraphics();
		IFigure figure = getPrintSource();
		setupPrinterGraphicsFor(graphics, figure);
		Rectangle bounds = figure.getBounds();
		int x = bounds.x, y = bounds.y;
		Rectangle clipRect = new Rectangle();
		while (y < bounds.y + bounds.height) {
			while (x < bounds.x + bounds.width) {
				graphics.pushState();
				getPrinter().startPage();
				graphics.translate(-x, -y);
				graphics.getClip(clipRect);
				clipRect.setLocation(x, y);
				graphics.clipRect(clipRect);
				figure.paint(graphics);
				getPrinter().endPage();
				graphics.popState();
				x += clipRect.width;
			}
			x = bounds.x;
			y += clipRect.height;
		}
	}

	public static void configureGraphics(Graphics graphics, IFigure figure,
			Printer printer, Rectangle printRegion, Rectangle symbolExtent,
			int printMode) {
		double dpiScale = 1;
		if (printer != null)
			dpiScale = (double) printer.getDPI().x
					/ Display.getCurrent().getDPI().x;

		// put the print region in display coordinates
		printRegion.width /= dpiScale;
		printRegion.height /= dpiScale;

		figure.setBounds(symbolExtent);

		double xScale = (double) printRegion.width / symbolExtent.width;
		double yScale = (double) printRegion.height / symbolExtent.height;
		switch (printMode) {
		case FIT_PAGE:
			graphics.scale(Math.min(xScale, yScale) * dpiScale);
			break;
		case FIT_WIDTH:
			graphics.scale(xScale * dpiScale);
			break;
		case FIT_HEIGHT:
			graphics.scale(yScale * dpiScale);
			break;
		case NO_FIT:
			break;
		default:
			graphics.scale(dpiScale);
		}
		graphics.setForegroundColor(figure.getForegroundColor());
		graphics.setBackgroundColor(figure.getBackgroundColor());
		graphics.setFont(figure.getFont());
	}

	public static void printImage(Image image, GraphicalViewer viewer,
			Rectangle printRegion, boolean modelHasContainer, int fitStyle) {
		GC gc = new GC(image);
		SWTGraphics graphics = new SWTGraphics(gc);
		graphics.setFont(viewer.getControl().getFont());
		List<?> selected = viewer.getSelectedEditParts();
		List<Object> maintainedSelection = new ArrayList<Object>(selected);
		viewer.deselectAll();
		LayerManager lm = (LayerManager) viewer.getEditPartRegistry().get(
				LayerManager.ID);
		IFigure figure = lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
		Rectangle symbolExtent = GraphicalZoomManager
				.getExtentRectangle(GraphicalEditor.getAllSymbols(viewer,
						modelHasContainer));
		if(fitStyle == NO_FIT){
			symbolExtent = printRegion;
		}
		configureGraphics(graphics, figure, null, printRegion, symbolExtent,
				fitStyle);
		Rectangle bounds = figure.getBounds();
		int x = bounds.x, y = bounds.y;
		Rectangle clipRect = new Rectangle();
		graphics.pushState();
		graphics.translate(-x, -y);
		graphics.getClip(clipRect);
		clipRect.setLocation(x, y);
		graphics.clipRect(clipRect);
		figure.paint(graphics);
		graphics.popState();
		viewer.setSelection(new StructuredSelection(maintainedSelection));
		graphics.dispose();
		if ( (gc != null) && !gc.isDisposed()) {
		    gc.dispose();
		}
	}

}
