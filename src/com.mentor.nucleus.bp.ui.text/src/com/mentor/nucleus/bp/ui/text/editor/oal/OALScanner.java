//========================================================================
//
// File:      $RCSfile: OALScanner.java,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:20:54 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
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
