package org.xtuml.bp.ui.text.asl;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.xtuml.bp.core.CorePlugin;

// TODO - This is pretty similar to OALAutoEditStrategy.  See if it can be refactored.
public class ASLAutoEditStrategy implements IAutoEditStrategy {

    public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
        try {
            if (command.text.equals(System.lineSeparator())) {
                int line = document.getLineOfOffset(command.offset);
                String curIndent = getIndentOfLine(document, line);
                String nextIndent = getAdditionalIndent(document, line);
                command.text = System.lineSeparator() + curIndent + nextIndent;
            }
        } catch (BadLocationException ble) {
            CorePlugin.logError("Invalid edit location exception caught.", ble);
        }
    }

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

    public static String getAdditionalIndent(IDocument document, int line) throws BadLocationException {
        if (line > -1) {
            int start = document.getLineOffset(line);
            int end = start + document.getLineLength(line);
            String text = document.get(start,end-start);
            // The following regex implements our pattern matching where we look for the required keywords
            // to auto-indent after.  We look for lines beginning with "if", "else", "for", "loop", "switch", 
            // "case", and "default"
            text = text.trim();
            if ( text.matches("^\\s*(?i)(if|else|for|loop|switch|case|default)(.*)") ) {
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