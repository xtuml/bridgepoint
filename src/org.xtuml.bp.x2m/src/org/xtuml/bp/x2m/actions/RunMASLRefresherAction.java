package org.xtuml.bp.x2m.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.x2m.generator.Generator;
import org.xtuml.bp.x2m.refresher.Refresher;

/**
 * Run the MASL Refresher
 */
public class RunMASLRefresherAction implements IObjectActionDelegate {
	private Object curSel = null;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
	    if (curSel instanceof SystemModel_c) {
			if (!Refresher.exportHasRun()) {
				// Run it synchronously. We use this CME to run this action
				// through reflection in some places (in bp.core). It is
				// important in those places that the action complete before 
				// returning
				Refresher refresh = new Refresher(false);
				refresh.exportSystem((SystemModel_c)curSel);
			}
	    }	    
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSel = (IStructuredSelection)selection;
			Object selected = sSel.getFirstElement();
			curSel = selected;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub

	}

	public boolean Actionfilter(final String p_Name, final String p_Value) {
		boolean shouldDisplay = false;
		if  (p_Name.equals("can") && p_Value.equals("run masl refresher")) {
			// Is this masl mode?
			int dialect = Pref_c.Getactiondialect("bridgepoint_prefs_default_action_language_dialect");
			if ((dialect == Actiondialect_c.masl)) {
				shouldDisplay = true;
			}
		}
		return shouldDisplay;
	}
}
