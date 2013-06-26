//=====================================================================
//
//	File:      $RCSfile: AbstractModelElementTextEditor.java,v $
//	Version:   $Revision: 1.13 $
//	Modified:  $Date: 2013/01/10 23:20:56 $
//
//	(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//	=====================================================================
//	This document contains information proprietary and confidential to
//	Mentor Graphics Corp. and is not for external distribution.
//	=====================================================================
package com.mentor.nucleus.bp.ui.text;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.EditorUtil;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInputFactory;

public class AbstractModelElementTextEditor extends TextEditor {
	
	public AbstractModelElementTextEditor() {
		super();		
	}
	
	class ElementRenameListener implements IElementRenameListener{

		/* (non-Javadoc)
		 * @see com.mentor.nucleus.bp.ui.text.IElementRenameListener#elementRenamed(java.lang.Object, com.mentor.nucleus.bp.core.NonRootModelElement)
		 */
		public void elementRenamed(Object editorInput) {
			String newPartName = ((IEditorInput)editorInput).getName(); 
			if(!getPartName().equals(newPartName)){
				setPartName(newPartName);		
			}
		}		
	}

	protected ElementRenameListener fElementRenameListener = new ElementRenameListener();
		
	protected void setDocumentProvider(IEditorInput input) {
		DocumentProvider dp = (DocumentProvider)getDocumentProvider();
		if (dp != null){
			dp.removeElementRenameListener(fElementRenameListener);
		}		
		super.setDocumentProvider(input);
		
		dp = (DocumentProvider)getDocumentProvider();
		
		if (dp!= null){
			dp.addElementRenameListener(fElementRenameListener);
		}
	}
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.texteditor.AbstractTextEditor#handleElementContentReplaced()
     * 
     * This will be called when this editor's contents are reloaded from disk.
     */
    protected void handleElementContentReplaced()
    {
        super.handleElementContentReplaced();
        
        // try to locate the reloaded content of this editor 
        // and point this editor to it
        final AbstractModelElementPropertyEditorInput oldInput = 
            (AbstractModelElementPropertyEditorInput)getEditorInput();
        EditorUtil.pointEditorToReloadedContent(this, oldInput.getModelElement(), null,
            oldInput.getModelRoot(), 
            new EditorUtil.CallerToPointEditorToReloadedContent() {
                public IEditorInput createInput(
                    // create either an activity editor input, or a description
                    // editor input, based on the type of the old input
                    IEditorPart editor, NonRootModelElement element) throws Exception {
                    return (oldInput instanceof ActivityEditorInput) ?
                        ActivityEditorInputFactory.getDefaultInstance().
                            createInstance(element)
                        : DescriptionEditorInputFactory.getDefaultInstance().
                            createInstance(element); 
                }
                public void setInput(IEditorPart editor, IEditorInput input) {
                    ((TextEditor)editor).setInput(input);
                }
            });
    }
}
