//========================================================================
//
// File:      $RCSfile: OALScanner.java,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:20:54 $
//
// (c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// This document contains information proprietary and confidential to
// Mentor Graphics Corp., and is not for external distribution.
//========================================================================

package com.mentor.nucleus.bp.ui.text.editor.oal;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;

import com.mentor.nucleus.bp.ui.text.editor.*;

/**
 * @author babar_ali
 */
public class OALScanner extends RuleBasedScanner {

	public OALScanner(SyntaxHighlightingPreferences manager) {

		IToken otherToken = manager.getDefaultToken(OALTokenTypes.TOKEN_TYPE_other);
		
		setDefaultReturnToken(otherToken);

		IRule[] rules = new IRule[5];

		//@note Defining rule for white space.
		rules[0] = new WhitespaceRule(new WhitespaceDetector());

		//@note Defining rule for single line comment.
		rules[1] = new EndOfLineRule("//", manager.getDefaultToken(OALTokenTypes.TOKEN_TYPE_single_line_comment));

		//@note Defining rule for single line strings.
		rules[2] = new SingleLineRule("'", "'", manager.getDefaultToken(OALTokenTypes.TOKEN_TYPE_string));

        //@note Defining rule for single line strings.
        rules[3] = new SingleLineRule("\"", "\"", manager.getDefaultToken(OALTokenTypes.TOKEN_TYPE_string));
		
		//@note Defining rule for string literals, Archetype will generate code in createRule method.
		rules[4] = OALKeywordRule.createRule(manager, otherToken);

		setRules(rules);
	}
	
}
