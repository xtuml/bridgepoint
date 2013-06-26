//========================================================================
//
//File:      $RCSfile: OALEditor.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:20:54 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

package com.mentor.nucleus.bp.ui.text.editor.oal;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;
import com.mentor.nucleus.bp.ui.text.AbstractModelElementTextEditor;
import com.mentor.nucleus.bp.ui.text.OALEditorPlugin;
import com.mentor.nucleus.bp.ui.text.editor.SyntaxHighlightingPreferences;

public class OALEditor extends AbstractModelElementTextEditor {

	private SyntaxHighlightingPreferences preferences;
	private IPreferenceModel.IChangeListener prefChangeListener;

	public OALEditor() {
		this(OALEditorPlugin.getDefaultOALPlugin().getSyntaxHighlightingPreferences());
	}

	public OALEditor(SyntaxHighlightingPreferences aPreferences) {
		super();

		preferences = aPreferences;

		setSourceViewerConfiguration(new OALEditorConfiguration(preferences));
		setDocumentProvider(new OALDocumentProvider());
		setEventListeners();
	}
	
	public void createPartControl(Composite parent){
		super.createPartControl(parent);
		getTextWidget().setBackground(preferences.getBackgroundColor());
	}

	public void dispose() {
		preferences.removeModelChangeListener(prefChangeListener);
		super.dispose();
	}

	protected void setEventListeners() {
		prefChangeListener = new IPreferenceModel.IChangeListener() {
			public void modelChanged(IPreferenceModel model, int changeHints) {
				switch (changeHints) {
					case SyntaxHighlightingPreferences.MODEL_BACKGROUND_CHANGED :
						getTextWidget().setBackground(preferences.getBackgroundColor());
						break;
					case SyntaxHighlightingPreferences.MODEL_TOKEN_ATTRIBUTE_CHANGED :
						getSourceViewer().invalidateTextPresentation();
						//getTextWidget().redrawRange(0, doc.getLength(), true);
						break;
				}
			}
		};

		preferences.addModelChangeListener(prefChangeListener);
	}

	public StyledText getTextWidget() {
		return getSourceViewer().getTextWidget();
	}
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#isPrefQuickDiffAlwaysOn()
     */
    protected boolean isPrefQuickDiffAlwaysOn()
    {
        // we don't currently want quick-diff info to be displayed in the
        // line column bar
        return false;
    }
}