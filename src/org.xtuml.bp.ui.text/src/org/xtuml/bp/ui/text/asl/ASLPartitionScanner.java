package org.xtuml.bp.ui.text.asl;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

public class ASLPartitionScanner extends RuleBasedPartitionScanner {
	public final static String CONTENT_TYPE_multiline_comment = "__asl_multiline_comment";

	public ASLPartitionScanner() {
		super();

		IToken commentToken = new Token(CONTENT_TYPE_multiline_comment);

		IPredicateRule[] rules = new IPredicateRule[1];

		// TODO SKB - This is currently not working for multi-line comments.  I think 
		// the issue is that the single line comment uses the single token "#" (defined
		// in ASLScanner) and that is getting processed before this multiline rule
		rules[0] = new MultiLineRule("#{", "}#", commentToken, (char) 0, true); //$NON-NLS-1$

		setPredicateRules(rules);
	}

	static class WordPredicateRule extends WordRule implements IPredicateRule {

		private IToken fSuccessToken;

		public WordPredicateRule(IToken successToken) {
			super(new EmptyCommentDetector());
			fSuccessToken = successToken;
			addWord("#{}#", fSuccessToken); //$NON-NLS-1$
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
			return (c == '#');
		}

		/* (non-Javadoc)
		* Method declared on IWordDetector
		*/
		public boolean isWordPart(char c) {
			return (c == '{' || c == '}' || c == '#');
		}
	};

}
