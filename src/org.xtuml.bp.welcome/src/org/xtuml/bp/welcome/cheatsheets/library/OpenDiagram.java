package org.xtuml.bp.welcome.cheatsheets.library;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.utilities.ui.CanvasUtilities;
import org.xtuml.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class OpenDiagram extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String projectName = params[0];
		String elementOoaType = params[1];
		String elementName = params[2];
		String containerPkg = params[3];
		NonRootModelElement element = CheatSheetsUtilities.findElement(projectName,containerPkg,  elementOoaType, elementName, null, null);
		if ( element != null ){
			CanvasUtilities.openCanvasEditor(element);
		}else {
			CheatSheetsUtilities.canNotFindElementReport(elementOoaType, elementName);
		}
		

	}

}
