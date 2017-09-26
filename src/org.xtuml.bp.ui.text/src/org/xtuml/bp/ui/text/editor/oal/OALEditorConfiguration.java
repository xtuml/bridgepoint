//========================================================================
//
//File:      $RCSfile: OALEditorConfiguration.java,v $
//Version:   $Revision: 1.8 $
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

package org.xtuml.bp.ui.text.editor.oal;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;
import org.xtuml.bp.ui.text.OALEditorPlugin;
import org.xtuml.bp.ui.text.editor.SyntaxHighlightingPreferences;

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

	public class OALContentAssistProcessor extends TemplateCompletionProcessor {

		@Override
		protected Template[] getTemplates(String contextTypeId) {
			if(contextTypeId.equals("__keyword")) {
				return new Template[] { new Template("Test", "Test", "__keyword", "true", true)};
			}
			return new Template[0];
		}

		@Override
		protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {
			getActionLanguageScanner().setRange(viewer.getDocument(), region.getOffset(), region.getLength());
	        SyntaxHighlightingPreferences prefs = 
	                OALEditorPlugin.getDefaultOALPlugin().getSyntaxHighlightingPreferences();        
	        IToken keyWord = prefs.getDefaultToken(OALTokenTypes.TOKEN_TYPE_keyword);
	        OALKeywordRule rule = OALKeywordRule.createRule(prefs, keyWord);
	        IToken evaluate = rule.evaluate((ICharacterScanner) getActionLanguageScanner());
	        if(!evaluate.isEOF() && !evaluate.isOther() && !evaluate.isUndefined() && !evaluate.isWhitespace()) {
	        	// found what we are looking for
	        	System.out.println();
	        }
			return new TemplateContextType("__keyword");
		}

		@Override
		protected Image getImage(Template template) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
	    ContentAssistant ca = new ContentAssistant();
	    IContentAssistProcessor pr = new OALContentAssistProcessor();
	    ca.setContentAssistProcessor(pr, "__keyword");
	    ca.setContentAssistProcessor(pr, IDocument.DEFAULT_CONTENT_TYPE);
	    ca.setInformationControlCreator(getInformationControlCreator(sourceViewer));
	    return ca;
	}

}