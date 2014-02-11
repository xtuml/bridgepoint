//========================================================================
//
//File:      $RCSfile: OALEditor.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:20:54 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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