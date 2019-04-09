package org.xtuml.bp.welcome.cheatsheets.library;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.ui.GenericPackageAssignEventOnSM_TXNAction;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.welcome.cheatsheets.utilities.CheatSheetsUIUtilities;
import org.xtuml.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

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