package org.xtuml.bp.ui.text.asl;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.xtuml.bp.core.CorePlugin;

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
            // The following regex implements our pattern matching where we look for the required
            // keywords.  We look for if, elif, or while followed by an optionally parenthesized 
            // condition and we also look for else and for each while handling the user adding some
            // extra spaces in the keyword.
            text = text.trim();
            // TODO - This is where we need to do some pattern matching for ASL blocks!!!
            if ( text.matches("^\\s*(?i)(if|elif|while)\\s*\\(?.*") || text.matches("^\\s*(?i)(else|for\\s+each\\s+)(.*)") ) {
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