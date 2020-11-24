//
// This class factors out common behavior for automatic indentation handling between OAL
// and ASL activity editors.
//
package org.xtuml.bp.ui.text.editor;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

public class ActionLanguageAutoEditStrategy {

    public static int findEndOfIndent(IDocument document, int offset, int end) throws BadLocationException {
        while (offset < end) {
            char c = document.getChar(offset);
            if (c != ' ' & c != '\t') {
                return offset;
            }
            offset++;
        }
        return end;
    }

    public static String getIndentOfLine(IDocument document, int line) throws BadLocationException {
        if (line > -1) {
            int start = document.getLineOffset(line);
            int end = start + document.getLineLength(line);
            int whiteend = findEndOfIndent(document, start, end);
            return document.get(start, whiteend - start);
        } else {
            return "";
        }
    }

    public static String getAdditionalIndent(IDocument document, int line, String regex, String regex2) throws BadLocationException {
        if (line > -1) {
            int start = document.getLineOffset(line);
            int end = start + document.getLineLength(line);
            String text = document.get(start,end-start);
            text = text.trim();
            
            // The passed in regex(es) implements the desired pattern matching where we look for the required keywords
            // to auto-indent after.  
            boolean isAMatch = false;
            if (!regex2.isEmpty()) {
            	isAMatch = text.matches(regex) || text.matches(regex2);
            } else {
            	isAMatch = text.matches(regex);
            }

            if ( isAMatch ) {
                IPreferenceStore store = EditorsUI.getPreferenceStore();
                boolean spacesForTabs = store.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS);
                int tabWidth = store.getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);

                if ( spacesForTabs ) {
                   String indent = "";
                   for (int i=0; i<tabWidth; ++i) {
                       indent = indent.concat(" ");
                   }
                   return indent;
                } else {
                    return "\t"; 
                }
            } 
            return "";
        } else {
            return "";
        }
    }
}