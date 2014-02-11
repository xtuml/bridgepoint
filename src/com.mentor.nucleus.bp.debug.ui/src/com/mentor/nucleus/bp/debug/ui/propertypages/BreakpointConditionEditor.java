//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 
//
package com.mentor.nucleus.bp.debug.ui.propertypages;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.DefaultUndoManager;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.model.IBPBreakpoint;

public class BreakpointConditionEditor {
	
	private TextViewer fViewer;

	private boolean fIsValid;
		
	private String fOldValue;
	private String fErrorMessage;
	
	private BPBreakpointPage fPage;
	private IBPBreakpoint fBreakpoint;
	
    private IDocumentListener fDocumentListener;
		
	public BreakpointConditionEditor(Group parent, BPBreakpointPage page) {
		fPage= page;
		fBreakpoint= (IBPBreakpoint) fPage.getBreakpoint();
		String condition;
		try {
			condition= fBreakpoint.getCondition();
		} catch (CoreException e) {
			BPDebugUIPlugin.logError("Unable to get breakpoint condition", e); 
			return;
		}
		fErrorMessage= "Enter a condition";
		fOldValue= ""; //$NON-NLS-1$
			
		// the source viewer
		fViewer= new TextViewer(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		fViewer.setInput(parent);
		
		IDocument document= new Document();
		fViewer.setEditable(true);
		fViewer.setDocument(document);
		final IUndoManager undoManager= new DefaultUndoManager(10);
		fViewer.setUndoManager(undoManager);
		undoManager.connect(fViewer);
		
		fViewer.getTextWidget().setFont(JFaceResources.getTextFont());
			
		Control control= fViewer.getControl();
		GridData gd = new GridData(GridData.FILL_BOTH);
		control.setLayoutData(gd);
		
		// listener for check the value
		fDocumentListener= new IDocumentListener() {
            public void documentAboutToBeChanged(DocumentEvent event) {
            }
            public void documentChanged(DocumentEvent event) {
                valueChanged();
            }
        };
		fViewer.getDocument().addDocumentListener(fDocumentListener);
		
		gd= (GridData)fViewer.getControl().getLayoutData();
		gd.heightHint= fPage.convertHeightInCharsToPixels(10);
		gd.widthHint= fPage.convertWidthInCharsToPixels(40);	
		document.set(condition);
		valueChanged();
		
	}

	/**
	 * Returns the condition defined in the source viewer.
	 * @return the contents of this condition editor
	 */
	public String getCondition() {
		return fViewer.getDocument().get();
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditor#refreshValidState()
	 */
	protected void refreshValidState() {
		// the value is valid if the field is not editable, or if the value is not empty
		if (!fViewer.isEditable()) {
			fPage.removeErrorMessage(fErrorMessage);
			fIsValid= true;
		} else {
			String text= fViewer.getDocument().get();
			fIsValid= text != null && text.trim().length() > 0;
			if (!fIsValid) {
				fPage.addErrorMessage(fErrorMessage);
			} else {
				fPage.removeErrorMessage(fErrorMessage);
			}
		}
	}
		
	/**
	 * @see org.eclipse.jface.preference.FieldEditor#setEnabled(boolean, org.eclipse.swt.widgets.Composite)
	 */
	public void setEnabled(boolean enabled) {
	    fViewer.setEditable(enabled);
	    fViewer.getTextWidget().setEnabled(enabled);
		if (enabled) {
			Color color= fViewer.getControl().getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
			fViewer.getTextWidget().setBackground(color);
			fViewer.getTextWidget().setFocus();
		} else {
			Color color= fViewer.getControl().getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
			fViewer.getTextWidget().setBackground(color);
		}
		valueChanged();
	}
	
	protected void valueChanged() {
		refreshValidState();
				
		String newValue = fViewer.getDocument().get();
		if (!newValue.equals(fOldValue)) {
			fOldValue = newValue;
		}
	}
	
	public void dispose() {
	    fViewer.getDocument().removeDocumentListener(fDocumentListener);
	}
}
