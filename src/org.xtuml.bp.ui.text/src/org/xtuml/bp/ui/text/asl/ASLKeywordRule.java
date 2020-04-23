package org.xtuml.bp.ui.text.asl;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.jface.text.rules.IWordDetector;

import org.xtuml.bp.ui.text.editor.*;

/**
 * Evaluates whether a word is an ASL keyword.
 */
public class ASLKeywordRule extends WordRule
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
    private static final String[] keywords = ASLKeywords.keywords;

    /**
     * Constructor.
     */
    private ASLKeywordRule(IWordDetector detector, IToken defaultToken)
    {
        super(detector, defaultToken);
    }

    /**
     * Factory method. 
     *  
     * @param manager           Supplies the ASL token to associate with ASL keywords. 
     * @param defaultToken      See WordRule constructor.
     */
    public static ASLKeywordRule createRule(SyntaxHighlightingPreferences manager, IToken defaultToken)
    {
        // create a new object of this class that sports our keyword-detector
        ASLKeywordRule rule = new ASLKeywordRule(new ASLWordDetector(), defaultToken);

        // for each ASL keyword
        IToken keywordToken = manager.getDefaultToken(ASLTokenTypes.TOKEN_TYPE_keyword);
        for (int i = 0; i < keywords.length; ++i) {
            // have the new rule associate this keyword with the keyword-token
            rule.addWord(keywords[i], keywordToken);
        }

        return rule;
    }

    /**
     * Answers whether given characters are some part of any ASL keyword. 
     */
    // TODO SKB - I think this function and the next is where we need to do more
    // work for keywords with hyphen
    static class ASLWordDetector implements IWordDetector
    {
    	// Since a word detector needs to detect all words including all keywords
    	// and non keywords, we use java identifier start and part rules, which
    	// apply to ASL as well.
    	// The responsibility of determining if a given word is keyword or not
    	// does not fall under IWordDetector.
    	
		public boolean isWordStart(char c) {
			return Character.isJavaIdentifierStart(c);
		}

		public boolean isWordPart(char c) {
			// ASL allows hyphen in keywords as well
			return Character.isJavaIdentifierPart(c) || (c == '-');
		}
    }

    /**
     * Evaluates whether the next word's-worth of characters from the given scanner
     * constitute an ASL keyword.
     * 
     * See parent method overridden.
     */
    public IToken evaluate(ICharacterScanner scanner)
    {
        // if the first character from the scanner is the start of any
        // ASL keyword, and either what column the character is at is unimportant, 
        // or matches the column constraint specified in the parent class
        char c = (char)scanner.read();
        if (fDetector.isWordStart(c) 
            && (fColumn == UNDEFINED || fColumn == scanner.getColumn() - 1)) {
            // until we hit a character that is no longer a part of any ASL keyword,
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
        // as an ASL keyword
        return Token.UNDEFINED;
    }
}