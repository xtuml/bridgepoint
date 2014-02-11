//=====================================================================
//
//File:      $RCSfile: OALKeywordRuleTest.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:46:48 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.ui.text.editor.oal.test;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;

import com.mentor.nucleus.bp.ui.text.OALEditorPlugin;
import com.mentor.nucleus.bp.ui.text.editor.SyntaxHighlightingPreferences;
import com.mentor.nucleus.bp.ui.text.editor.oal.OALKeywordRule;
import com.mentor.nucleus.bp.ui.text.editor.oal.OALTokenTypes;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;

/**
 * Unit tests of the like-named class.
 */
public class OALKeywordRuleTest extends UITextTest 
{
    public OALKeywordRuleTest(String projectName, String name) throws CoreException {
		super("test", name);	 //$NON-NLS-1$
	}
    public OALKeywordRuleTest( String name) throws CoreException {
		super("test", name);	 //$NON-NLS-1$
	}

	/**
     * Tests whether an oal-keyword-rule correctly recognizes
     * keywords of different capitalizations.
     */
    public void testEvaluate()
    {
        // create a test document
        Document document = new Document();
        
        // create our rule to be tested
        SyntaxHighlightingPreferences prefs = 
            OALEditorPlugin.getDefaultOALPlugin().getSyntaxHighlightingPreferences();        
        IToken otherToken = prefs.getDefaultToken(OALTokenTypes.TOKEN_TYPE_other);
        OALKeywordRule rule = OALKeywordRule.createRule(prefs, otherToken);
        
        // for each of a number of test words that are or are not
        // valid keywords 
        String[] words = {"select", "Select", "SELECT", "selec"};
        boolean[] isKeyword = {true, true, true, false};
        RuleBasedScanner scanner = new RuleBasedScanner();
        IToken keywordToken = prefs.getDefaultToken(
            OALTokenTypes.TOKEN_TYPE_keyword);
        for (int i = 0; i < words.length; i++) {
            // have our document be made up of just this word
            document.set(words[i]);
            
            // set our scanner's range in the document to be the whole word
            scanner.setRange(document, 0, words[i].length());
            
            // have the rule evalute the word
            IToken token = rule.evaluate(scanner);
            
            // if we know the word to be a valid keyword
            if (isKeyword[i]) {
                // check that the rule says the word is a keyword
                assertTrue("\"" + words[i] + "\" not accepted as keyword", 
                    token.equals(keywordToken));
            }
            
            // otherwise
            else {
                // check that the rule says the word is *not* a keyword
                assertTrue("\"" + words[i] + "\" accepted as keyword", 
                    !token.equals(keywordToken));
            } 
        }
    }
}

