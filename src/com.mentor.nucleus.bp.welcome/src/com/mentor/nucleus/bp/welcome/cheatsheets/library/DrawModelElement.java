package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: DrawModelElement.java,v $
//Version: $Revision: 1.5 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUIUtilities;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class DrawModelElement extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String ToolSet = params[0];
		String modelElement = params[1];
		org.eclipse.swt.graphics.Point creationPoint = null ;
		boolean convertMouseCoor = true;
		try{
		}catch (Exception e) {
		}
		if ( params != null ){
			GraphicalEditor activeEditor = (GraphicalEditor) CheatSheetsUIUtilities.getActiveEditor();
			ZoomManager zoomManager = (ZoomManager) activeEditor.getAdapter(ZoomManager.class);
			zoomManager.setZoom(1);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			Model_c model = activeEditor.getModel();

			try{
				Shape_c[] shapes = Shape_c.getManyGD_SHPsOnR2(GraphicalElement_c.getManyGD_GEsOnR1(model));
				Shape_c[] sourceList = CheatSheetsUtilities.filterShape(shapes, 103);
				
				
				
				List children = activeEditor.getGraphicalViewer().getContents().getChildren();
				ShapeEditPart shape = (ShapeEditPart)children.get(children.size()-1);
				IFigure figure = shape.getFigure();
				Rectangle bounds = figure.getBounds();
				Point point = bounds.getCenter();
				creationPoint = new org.eclipse.swt.graphics.Point(point.x-500, point.y-75);
				Point p = new Point(creationPoint.x, creationPoint.y);
				if (!bounds.contains(p) && sourceList.length!=0){
					creationPoint = new org.eclipse.swt.graphics.Point(point.x, point.y);
				}
			}catch (Exception e) {
				creationPoint = new org.eclipse.swt.graphics.Point(400, 50);
				convertMouseCoor = false;
			}

			AbstractTool tool = CanvasUtilities.getTool(ToolSet, modelElement);
			CanvasUtilities.activateTool(tool);
			if (convertMouseCoor){
				creationPoint = CanvasUtilities.convertToMouseCoor(creationPoint, model);
			}
			CanvasUtilities.doMousePress(creationPoint.x, creationPoint.y);
			CanvasUtilities.doMouseRelease(creationPoint.x, creationPoint.y);
			CanvasUtilities.deactivateTool(tool);
			activeEditor.zoomAll();
		}
	}

}
