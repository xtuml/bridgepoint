package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: AssignEvent.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.ui.GenericPackageAssignEventOnSM_TXNAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUIUtilities;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class AssignEvent extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String connectorName = params[0];
		GraphicalEditor activeEditor = (GraphicalEditor) CheatSheetsUIUtilities.getActiveEditor();
		
		Model_c model = activeEditor.getModel();
		Connector_c[] connectors = Connector_c.getManyGD_CONsOnR2(GraphicalElement_c.getManyGD_GEsOnR1(model));
		Connector_c requiredConnector = CheatSheetsUtilities.getDesiredConnector(connectors,connectorName);
		if ( requiredConnector==null){
			CheatSheetsUtilities.canNotFindElementReport(null, connectorName);
		}
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(requiredConnector);
		Object represents = ge.getRepresents();
		Action a = new Action() {
		};
		if ( represents instanceof Transition_c){
			GenericPackageAssignEventOnSM_TXNAction assignEvent = new GenericPackageAssignEventOnSM_TXNAction();
			assignEvent.setActivePart(a, PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActivePart());
			StructuredSelection sel = new StructuredSelection(represents);
			Selection.getInstance().setSelection(sel, true);
			assignEvent.run(a);
		}
	}

}
