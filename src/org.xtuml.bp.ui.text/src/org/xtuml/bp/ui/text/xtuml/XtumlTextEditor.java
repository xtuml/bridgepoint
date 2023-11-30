package org.xtuml.bp.ui.text.xtuml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.PatternRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.ui.text.OALEditorPlugin;
import org.xtuml.bp.ui.text.editor.ActionLanguageTokenTypes;
import org.xtuml.bp.ui.text.editor.SyntaxHighlightingPreferences;
import org.xtuml.bp.ui.text.editor.oal.OALKeywordRule;
import org.xtuml.bp.ui.text.editor.oal.OALScanner;
import org.xtuml.bp.ui.text.editor.oal.WhitespaceDetector;

public class XtumlTextEditor extends TextEditor {

	public XtumlTextEditor() {

		// create syntax highlighting scanner
		final ITokenScanner scanner = getSyntaxScanner();

		// set source configuration
		setSourceViewerConfiguration(new TextSourceViewerConfiguration() {

			public IPresentationReconciler getPresentationReconciler(ISourceViewer sv) {
				PresentationReconciler rec = new PresentationReconciler();
				DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
				rec.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
				rec.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
				return rec;
			}

		});

		// set document provider
		setDocumentProvider(new FileDocumentProvider());

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);

		// set the title
		if (input instanceof IFileEditorInput) {
			final PersistableModelComponent pmc = PersistenceManager.findComponent(((IFileEditorInput) input).getFile().getFullPath());
			if (pmc != null && pmc.getRootModelElement() != null) {
				setPartName(pmc.getRootModelElement().getName());
			}
		}
	}
	
	private ITokenScanner getSyntaxScanner() {
		final SyntaxHighlightingPreferences prefs = OALEditorPlugin.getDefaultOALPlugin()
				.getSyntaxHighlightingPreferences();
		final RuleBasedScanner scanner = new RuleBasedScanner();
		final IToken otherToken = prefs.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_other);
		
		scanner.setDefaultReturnToken(otherToken);
		
		List<IRule> rules = new ArrayList<>();

		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		rules.add(new EndOfLineRule("//", prefs.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_single_line_comment)));
        rules.add(new SingleLineRule("\"", "\"", prefs.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_string)));
        rules.add(new SingleLineRule("'", "'", prefs.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_other)));
        rules.add(new SingleLineRule("@", ";", prefs.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_mark)));
		rules.add(OALKeywordRule.createRule(prefs, prefs.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_other),
				XtumlKeywords.keywords));

		scanner.setRules(rules.toArray(new IRule[0]));

		return scanner;
	}
	

}
