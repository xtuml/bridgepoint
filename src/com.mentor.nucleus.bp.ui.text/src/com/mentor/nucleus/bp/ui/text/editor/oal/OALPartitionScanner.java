//========================================================================
//
//File:      $RCSfile: OALPartitionScanner.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:20:54 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

package com.mentor.nucleus.bp.ui.text.editor.oal;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

public class OALPartitionScanner extends RuleBasedPartitionScanner {
	public final static String CONTENT_TYPE_multiline_comment = "__oal_multiline_comment";

	public OALPartitionScanner() {
		super();

		IToken commentToken = new Token(CONTENT_TYPE_multiline_comment);

		IPredicateRule[] rules = new IPredicateRule[1];

		rules[0] = new MultiLineRule("/*", "*/", commentToken, (char) 0, true); //$NON-NLS-1$

		setPredicateRules(rules);
	}

	static class WordPredicateRule extends WordRule implements IPredicateRule {

		private IToken fSuccessToken;

		public WordPredicateRule(IToken successToken) {
			super(new EmptyCommentDetector());
			fSuccessToken = successToken;
			addWord("/**/", fSuccessToken); //$NON-NLS-1$
		}

		/*
		 * @see org.eclipse.jface.text.rules.IPredicateRule#evaluate(ICharacterScanner, boolean)
		 */
		public IToken evaluate(ICharacterScanner scanner, boolean resume) {
			return super.evaluate(scanner);
		}

		/*
		 * @see org.eclipse.jface.text.rules.IPredicateRule#getSuccessToken()
		 */
		public IToken getSuccessToken() {
			return fSuccessToken;
		}
	};

	/**
	 * Detector for empty comments.
	 */
	static class EmptyCommentDetector implements IWordDetector {

		/* (non-Javadoc)
		* Method declared on IWordDetector
		*/
		public boolean isWordStart(char c) {
			return (c == '/');
		}

		/* (non-Javadoc)
		* Method declared on IWordDetector
		*/
		public boolean isWordPart(char c) {
			return (c == '*' || c == '/');
		}
	};

}
