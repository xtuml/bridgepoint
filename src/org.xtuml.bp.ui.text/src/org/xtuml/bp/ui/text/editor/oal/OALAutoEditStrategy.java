package org.xtuml.bp.ui.text.editor.oal;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.ui.text.editor.ActionLanguageAutoEditStrategy;

public class OALAutoEditStrategy extends ActionLanguageAutoEditStrategy implements IAutoEditStrategy {

    public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
        try {
            if (command.text.equals(System.lineSeparator())) {
            	// The following regex implements our pattern matching where we look for the required
                // keywords.  For OAL look for if, elif, or while followed by an optionally parenthesized 
                // condition and we also look for else and for each while handling the user adding some
                // extra spaces in the keyword.
                String regex1 = "^\\s*(?i)(if|elif|while)\\s*\\(?.*";
                String regex2 = "^\\s*(?i)(else|for\\s+each\\s+)(.*)";
                
                int line = document.getLineOfOffset(command.offset);
                String curIndent = getIndentOfLine(document, line);
                String nextIndent = getAdditionalIndent(document, line, regex1, regex2);
                command.text = System.lineSeparator() + curIndent + nextIndent;
            }
        } catch (BadLocationException ble) {
            CorePlugin.logError("Invalid edit location exception caught.", ble);
        }
    }

}