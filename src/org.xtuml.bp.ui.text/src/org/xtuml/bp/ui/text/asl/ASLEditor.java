package org.xtuml.bp.ui.text.asl;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.xtuml.bp.ui.preference.IPreferenceModel;
import org.xtuml.bp.ui.text.AbstractModelElementTextEditor;
import org.xtuml.bp.ui.text.OALEditorPlugin;
import org.xtuml.bp.ui.text.editor.ActionLanguageDocumentProvider;
import org.xtuml.bp.ui.text.editor.SyntaxHighlightingPreferences;

// TODO - refactor for OAL/ASL combination
public class ASLEditor extends AbstractModelElementTextEditor {

	private SyntaxHighlightingPreferences preferences;
	private IPreferenceModel.IChangeListener prefChangeListener;

	public ASLEditor() {
		// TODO - reuse OAL Syntax highlighting prefs??
		this(OALEditorPlugin.getDefaultOALPlugin().getSyntaxHighlightingPreferences()); 
	}

	public ASLEditor(SyntaxHighlightingPreferences aPreferences) {
		super();

		preferences = aPreferences;

		setSourceViewerConfiguration(new ASLEditorConfiguration(preferences, this));
		// TODO - switched to refactored version below setDocumentProvider(new ASLDocumentProvider());
		setDocumentProvider(new ActionLanguageDocumentProvider());
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
        // we don't currently want quick-diff info to be displayed in the line column bar
        return false;
    }
    
    @Override
    protected void createActions() {
        super.createActions();
    }
    
	@Override
	protected boolean isTabsToSpacesConversionEnabled()
	{ 
	    IPreferenceStore store = EditorsUI.getPreferenceStore(); 
	    boolean spacesForTabs = store.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS);
		return spacesForTabs;
	}
}