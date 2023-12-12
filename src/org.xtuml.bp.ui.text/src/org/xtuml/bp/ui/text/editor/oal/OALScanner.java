
package org.xtuml.bp.ui.text.editor.oal;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;

import org.xtuml.bp.ui.text.editor.*;

public class OALScanner extends RuleBasedScanner {

	public OALScanner(SyntaxHighlightingPreferences manager) {

		IToken otherToken = manager.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_other);
		
		setDefaultReturnToken(otherToken);

		IRule[] rules = new IRule[6];

		//@note Defining rule for white space.
		rules[0] = new WhitespaceRule(new WhitespaceDetector());

		//@note Defining rule for single line comment.
		rules[1] = new EndOfLineRule("//", manager.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_single_line_comment));

		//@note Defining rule for single line strings.
		rules[2] = new SingleLineRule("'", "'", manager.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_string));

        //@note Defining rule for single line strings.
        rules[3] = new SingleLineRule("\"", "\"", manager.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_string));

        //@note Defining rule for marks.
        rules[4] = new SingleLineRule("@", ";", manager.getDefaultToken(ActionLanguageTokenTypes.TOKEN_TYPE_mark));
		
		//@note Defining rule for string literals, Archetype will generate code in createRule method.
		rules[5] = OALKeywordRule.createRule(manager, otherToken);

		setRules(rules);
	}
	
}
