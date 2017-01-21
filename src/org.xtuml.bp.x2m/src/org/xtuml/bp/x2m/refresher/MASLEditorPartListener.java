package org.xtuml.bp.x2m.refresher;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.Selection;

public class MASLEditorPartListener implements IPartListener2
{
	// TODO - should this be a singleton???
	
    @Override
    public void partClosed(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partActivated(IWorkbenchPartReference partRef) {
            // Auto-generated method stub
            
    }

    @Override
    public void partBroughtToTop(IWorkbenchPartReference partRef) {
            // Auto-generated method stub
            
    }

    @Override
    public void partDeactivated(IWorkbenchPartReference partRef) {
            // Auto-generated method stub
            
    }

    @Override
    public void partOpened(IWorkbenchPartReference partRef) {
    	//inputClass = bundle.loadClass("org.xtuml.bp.ui.text.masl.MASLEditorInput"); //$NON-NLS-1$
			//editorId = (String) inputClass.getField("EDITOR_ID").get(null); //$NON-NLS-1$
    	if ( !partRef.getId().equals("org.xtuml.bp.xtext.masl.MASL")) { return; }
    		
    	IStructuredSelection sel = Selection.getInstance().getStructuredSelection();
		if (sel.isEmpty()) {
			return;
		}
		Object current = sel.iterator().next();
		Object dialectObj = current;
		if (dialectObj instanceof NonRootModelElement) {
			NonRootModelElement nrme = ((NonRootModelElement) dialectObj).getRoot();
			if (nrme instanceof SystemModel_c) {
				long startTime = System.nanoTime(); // TODO - remove timing
				Refresher.exportSystem(((SystemModel_c)nrme));
				long endTime = System.nanoTime();
				long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
				System.out.println("Refresh took " + duration + " milliseconds.");
			}
		}
    }

    @Override
    public void partHidden(IWorkbenchPartReference partRef) {
            // Auto-generated method stub
            
    }

    @Override
    public void partVisible(IWorkbenchPartReference partRef) {
            // Auto-generated method stub
            
    }

    @Override
    public void partInputChanged(IWorkbenchPartReference partRef) {
            // Auto-generated method stub
            
    }

}
