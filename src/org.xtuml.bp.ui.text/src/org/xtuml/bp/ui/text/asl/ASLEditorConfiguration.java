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

package org.xtuml.bp.ui.text.asl;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.xtuml.bp.ui.text.editor.SyntaxHighlightingPreferences;
import org.xtuml.bp.ui.text.editor.oal.OALPartitionScanner;
import org.xtuml.bp.ui.text.editor.oal.OALScanner;
import org.xtuml.bp.ui.text.editor.oal.OALTokenTypes;

public class ASLEditorConfiguration extends SourceViewerConfiguration {
	private ITokenScanner oalScanner;
	private ITokenScanner commentScanner;
	private ASLEditor editor;

	private SyntaxHighlightingPreferences tokenTypeManager;

	public ASLEditorConfiguration(SyntaxHighlightingPreferences tokenTypeManager, ASLEditor editor) {
		this.tokenTypeManager = tokenTypeManager;
		this.editor = editor;
	}

	public ASLEditorConfiguration(SyntaxHighlightingPreferences tokenTypeManager) {
	    this(tokenTypeManager, null);
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			// TODO - reuse OALPartitionScanner here????
			OALPartitionScanner.CONTENT_TYPE_multiline_comment };
	}

	protected ITokenScanner getActionLanguageScanner() {
		if (oalScanner == null) {
			//  TODO - reuse OALScanner here????
			oalScanner = new OALScanner(tokenTypeManager);
		}
		return oalScanner;
	}

	protected ITokenScanner getCommentScanner() {
		if (commentScanner == null) {
			RuleBasedScanner scanner = new RuleBasedScanner();
			// TODO - reuse OALTokenTypes here????
			scanner.setDefaultReturnToken(
				tokenTypeManager.getDefaultToken(OALTokenTypes.TOKEN_TYPE_multi_line_comment));

			commentScanner = scanner;
		}
		return commentScanner;
	}

	PresentationReconciler reconciler;
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		if (reconciler == null) {
			reconciler = new PresentationReconciler();

			DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getActionLanguageScanner());
			reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
			reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

			dr = new DefaultDamagerRepairer(getCommentScanner());
			// TODO - understand what the OALPartitionScanner is doing here and make an intelligent comment.
			//   Reuse that code???
			reconciler.setDamager(dr, OALPartitionScanner.CONTENT_TYPE_multiline_comment);
			reconciler.setRepairer(dr, OALPartitionScanner.CONTENT_TYPE_multiline_comment);

		}
		return reconciler;
	}
	
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sv) {
		// We don't yet support content assist in ASL editor.
		// When the time comes, refer to the way this is done in OALEditorConfiguration::getContentAssist()
	    return null;
	}

	@Override
	public int getTabWidth(ISourceViewer sourceViewer) 
	{
	    IPreferenceStore store = EditorsUI.getPreferenceStore();
	    int tabWidth = store.getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);
		return tabWidth;
	}
	
	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer,
            String contentType)
	{
	    IAutoEditStrategy strategy= (IDocument.DEFAULT_CONTENT_TYPE.equals(contentType)
	            ? new ASLAutoEditStrategy() : new DefaultIndentLineAutoEditStrategy());
	    return new IAutoEditStrategy[] { strategy };
	}
	
	@Override
	public String[] getIndentPrefixes(ISourceViewer sourceViewer,
            String contentType)
	{
		String[] prefixes = super.getIndentPrefixesForTab(getTabWidth(sourceViewer));
		return  prefixes;
	}

}