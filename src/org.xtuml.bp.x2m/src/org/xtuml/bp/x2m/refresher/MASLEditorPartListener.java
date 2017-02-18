package org.xtuml.bp.x2m.refresher;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.Selection;

public class MASLEditorPartListener implements IPartListener2
{
    @Override
    public void partClosed(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partActivated(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partBroughtToTop(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partDeactivated(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partOpened(IWorkbenchPartReference partRef) {
    	if ( !partRef.getId().equals("org.xtuml.bp.xtext.masl.MASL") && !partRef.getId().equals("org.xtuml.bp.xtext.masl.MASLPartial")) { return; }
    		
    	IStructuredSelection sel = Selection.getInstance().getStructuredSelection();
		if (sel.isEmpty()) {
			return;
		}
		Object current = sel.iterator().next();
		Object dialectObj = current;
		if (dialectObj instanceof NonRootModelElement) {
			NonRootModelElement nrme = ((NonRootModelElement) dialectObj).getRoot();
			if (nrme instanceof SystemModel_c) {
				Refresher.exportSystem(((SystemModel_c)nrme));
			}
		}
    }

    @Override
    public void partHidden(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partVisible(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partInputChanged(IWorkbenchPartReference partRef) {
    }

}
