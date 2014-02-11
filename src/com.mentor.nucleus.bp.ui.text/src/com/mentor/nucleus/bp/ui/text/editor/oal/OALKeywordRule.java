//========================================================================
//
//File:      $RCSfile: OALKeywordRule.java,v $
//Version:   $Revision: 1.10 $
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

package com.mentor.nucleus.bp.ui.text.editor.oal;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.jface.text.rules.IWordDetector;

import com.mentor.nucleus.bp.ui.text.editor.*;

/**
 * Evaluates whether a word is an OAL keyword.
 */
public class OALKeywordRule extends WordRule
{
    /**
     * Is reused (for speed) across calls to this.evaluate().
     * Holds the word being evaluated as it is provided (by a
     * scanner) one character at a time.  We'd reuse the 
     * similarly-named field of the parent class for this purpose, 
     * except that it was only given private access.
     */
    private StringBuffer word = new StringBuffer();

    /**
     * An abbreviation for the specified generated array.
     */
    private static final String[] keywords = OALKeywords.keywords;

    /**
     * Constructor.
     */
    private OALKeywordRule(IWordDetector detector, IToken defaultToken)
    {
        super(detector, defaultToken);
    }

    /**
     * Factory method. 
     *  
     * @param manager           Supplies the OAL token to associate with OAL keywords. 
     * @param defaultToken      See WordRule constructor.
     */
    public static OALKeywordRule createRule(SyntaxHighlightingPreferences manager, IToken defaultToken)
    {
        // create a new object of this class that sports our keyword-detector
        OALKeywordRule rule = new OALKeywordRule(new OALWordDetector(), defaultToken);

        // for each OAL keyword
        IToken keywordToken = manager.getDefaultToken(OALTokenTypes.TOKEN_TYPE_keyword);
        for (int i = 0; i < keywords.length; ++i) {
            // have the new rule associate this keyword with the keyword-token
            rule.addWord(keywords[i], keywordToken);
        }

        return rule;
    }

    /**
     * Answers whether given characters are some part of any OAL keyword. 
     */
    static class OALWordDetector implements IWordDetector
    {
    	// Since a word detector needs to detect all words including all keywords
    	// and non keywords, we use java identifier start and part rules, which
    	// apply to oal as well.
    	// The responsibility of determining if a given word is keyword or not
    	// does not fall under IWordDetector.
    	
		public boolean isWordStart(char c) {
			return Character.isJavaIdentifierStart(c);
		}

		public boolean isWordPart(char c) {
			return Character.isJavaIdentifierPart(c);
		}
    }

    /**
     * Evaluates whether the next word's-worth of characters from the given scanner
     * constitute an OAL keyword.
     * 
     * See parent method overridden.
     */
    public IToken evaluate(ICharacterScanner scanner)
    {
        // if the first character from the scanner is the start of any
        // OAL keyword, and either what column the character is at is unimportant, 
        // or matches the column constraint specified in the parent class
        char c = (char)scanner.read();
        if (fDetector.isWordStart(c) 
            && (fColumn == UNDEFINED || fColumn == scanner.getColumn() - 1)) {
            // until we hit a character that is no longer a part of any OAL keyword,
            // or the scanner has no more characters to supply to us      
            word.setLength(0);
            do {
                // add the current character to the word we are storing
                word.append(Character.toLowerCase(c));
                
                // get the next character from the scanner
                c = (char)scanner.read();
            } while (fDetector.isWordPart(c) && c != ICharacterScanner.EOF);
            
            // reposition the scanner back at the last character which caused
            // the above loop to terminate, for processing by other rules
            scanner.unread();

            // if we have a token associated with the word we collected above,
            // return it
            IToken token = (IToken) fWords.get(word.toString());
            if (token != null) return token;

            // reset the scanner to its initial position at the start of this method,
            // so other rules may evaluate the word (only if default token is not set).
            // NOTE: Setting the default token means that any words that are not 
            // recognized as keywords (i-e., not present in fWords) are recognized
            // as "other" words (highlighted in a color as specified in preferences
            // under "Others").
            
			if (fDefaultToken.isUndefined())
				unreadBuffer(scanner);
				
			return fDefaultToken;
		}

        // otherwise
        else {
            // reset the scanner to its initial position at the start of this method,
            // so other rules may evaluate the word
            scanner.unread();
        }

        // return a token that will signal the caller that the word was unrecognized
        // as an OAL keyword
        return Token.UNDEFINED;
    }
}