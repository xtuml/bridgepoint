package org.xtuml.bp.ui.text.masl;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.ResourceUtil;

public class MASLPartListener implements IPartListener2
{

    @Override
    public void partClosed(IWorkbenchPartReference partRef) {
        IWorkbenchPart part = partRef.getPart(false);
        if ( part instanceof TextEditor ) {
            TextEditor editor = (TextEditor)part;
            IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
            // delete the file if empty
            if ( doc != null && doc.get().isEmpty() ) {
                IFile file = ResourceUtil.getFile( editor.getEditorInput() );
                if ( file != null && file.exists() ) {
                    try {
                        file.delete(true, false, null);
                    } catch ( CoreException ce ) {
                        System.out.println( ce );
                    }
                }
            }
        }
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
            // Auto-generated method stub
            
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
