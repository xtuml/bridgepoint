//========================================================================
//
//File:      $RCSfile: OALEditorConfiguration.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:20:54 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

package com.mentor.nucleus.bp.ui.text.editor.oal;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import com.mentor.nucleus.bp.ui.text.editor.SyntaxHighlightingPreferences;

public class OALEditorConfiguration extends SourceViewerConfiguration {
	private ITokenScanner oalScanner;
	private ITokenScanner commentScanner;

	private SyntaxHighlightingPreferences tokenTypeManager;

	public OALEditorConfiguration(SyntaxHighlightingPreferences tokenTypeManager) {
		this.tokenTypeManager = tokenTypeManager;
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			OALPartitionScanner.CONTENT_TYPE_multiline_comment };
	}

	protected ITokenScanner getActionLanguageScanner() {
		if (oalScanner == null) {
			oalScanner = new OALScanner(tokenTypeManager);
		}
		return oalScanner;
	}

	protected ITokenScanner getCommentScanner() {
		if (commentScanner == null) {
			RuleBasedScanner scanner = new RuleBasedScanner();
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
			reconciler.setDamager(dr, OALPartitionScanner.CONTENT_TYPE_multiline_comment);
			reconciler.setRepairer(dr, OALPartitionScanner.CONTENT_TYPE_multiline_comment);

		}
		return reconciler;
	}

}