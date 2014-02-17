package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: DrawConnector.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooatype_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.Waypoint_c;

import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUIUtilities;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class DrawConnector extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String paletteSection = params[0];
		String paletteObject = params[1];
		String sourceOoaType = params[2];
		String sourceName = params[3];
		String destinationOoaType = params[4];
		String destNameORLocationX = params[5];
		String locationY = params[6];
		                                   
		
		if (params != null ){
			GraphicalEditor activeEditor = (GraphicalEditor) CheatSheetsUIUtilities.getActiveEditor();
			ZoomManager zoomManager = (ZoomManager) activeEditor.getAdapter(ZoomManager.class);
			zoomManager.setZoom(1);
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());

			Model_c model = activeEditor.getModel();
			Shape_c[] shapes = Shape_c.getManyGD_SHPsOnR2(GraphicalElement_c.getManyGD_GEsOnR1(model));
			Connector_c[] connectors = Connector_c.getManyGD_CONsOnR2(GraphicalElement_c.getManyGD_GEsOnR1(model));

			Shape_c[] sourceList = CheatSheetsUtilities.filterShape(shapes, Integer.valueOf(sourceOoaType));
			Shape_c sourceShape = CheatSheetsUtilities.getDesiredShape(sourceList, sourceName);
			
			if ( sourceShape == null){
				CheatSheetsUtilities.canNotFindElementReport("", sourceName);
			}

			Shape_c destShape = null;
			Connector_c destConnector = null;
			int destOoaType = -1; 
			try {
				destOoaType = Integer.valueOf(destinationOoaType);
			}catch (Exception e ){
			}

			Point destPoint = null; 
			if ( sourceOoaType.equalsIgnoreCase(destinationOoaType)){
				destShape = CheatSheetsUtilities.getDesiredShape(sourceList, destNameORLocationX);
			}else if ( destOoaType == Ooatype_c.ProvidedInterface || destOoaType == Ooatype_c.RequiredInterface || destOoaType == Ooatype_c.Supertype ){
				Connector_c[] destList = CheatSheetsUtilities.filterConnector(connectors, Integer.valueOf(destinationOoaType));
				destConnector = CheatSheetsUtilities.getDesiredConnector(destList, destNameORLocationX);
				if ( destConnector == null){
					CheatSheetsUtilities.canNotFindElementReport("", destNameORLocationX);
				}

			}else if (!destinationOoaType.equals("Canvas") ){
				Shape_c[] destList = CheatSheetsUtilities.filterShape(shapes, Integer.valueOf(destinationOoaType));
				destShape = CheatSheetsUtilities.getDesiredShape(destList , destNameORLocationX);
				if (destShape == null){
					CheatSheetsUtilities.canNotFindElementReport("", destNameORLocationX);
				}
			}

			if (( destShape != null || destConnector != null || destinationOoaType.equals("Canvas") ) && sourceShape != null ) {
				AbstractTool tool = CanvasUtilities.getTool(paletteSection, paletteObject);
				CanvasUtilities.activateTool(tool);
				Point sourcePoint = CanvasUtilities.getShapeCenter(sourceShape);
				sourcePoint = CanvasUtilities.convertToMouseCoor(sourcePoint, model);
				if (destShape != null){
					destPoint = CanvasUtilities.getShapeCenter(destShape);
				}else if ( destConnector != null){
					destPoint = getConnectorEdge(destConnector);
				}else if ( destinationOoaType.equals("Canvas")){
					destPoint = new Point(sourcePoint.x + Integer.valueOf(destNameORLocationX), sourcePoint.y + Integer.valueOf( locationY));
				}

  
				CanvasUtilities.doMouseMove(sourcePoint.x, sourcePoint.y);
				CanvasUtilities.doMousePress(sourcePoint.x, sourcePoint.y);

				if (destShape != null || destConnector != null ){
					destPoint = CanvasUtilities.convertToMouseCoor(destPoint, model);
				}
				CanvasUtilities.doMouseMove(destPoint.x, destPoint.y);
				CanvasUtilities.doMouseRelease(destPoint.x, destPoint.y);
				CanvasUtilities.deactivateTool(tool);
			}else if(destShape == null || destConnector == null ){
				CheatSheetsUtilities.canNotFindElementReport("", destNameORLocationX);
			}
		}

	}
	public static Point getConnectorEdge(Connector_c connector) {
		Graphedge_c gEdge = Graphedge_c.getOneDIM_EDOnR20(connector);
		Waypoint_c[] points = Waypoint_c.getManyDIM_WAYsOnR319(gEdge);
		Waypoint_c connectPoint = points[points.length-1];
		return new Point(
				(int)(connectPoint.getPositionx()),
				(int)(connectPoint.getPositiony()));
	}


}
