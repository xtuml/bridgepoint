package org.xtuml.bp.ui.text.asl;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.ui.text.editor.ActionLanguageAutoEditStrategy;

public class ASLAutoEditStrategy extends ActionLanguageAutoEditStrategy implements IAutoEditStrategy {

    public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
        try {
            if (command.text.equals(System.lineSeparator())) {
            	// The following regex implements our pattern matching where we look for the required keywords
                // to auto-indent after.  For ASL we look for lines beginning with "if", "else", "for", "loop", 
                // "switch", "case", and "default"
                String regex = "^\\s*(?i)(if|else|for|loop|switch|case|default)(.*)";

                int line = document.getLineOfOffset(command.offset);
                String curIndent = getIndentOfLine(document, line);
                String nextIndent = getAdditionalIndent(document, line, regex, "");
                command.text = System.lineSeparator() + curIndent + nextIndent;
            }
        } catch (BadLocationException ble) {
            CorePlugin.logError("Invalid edit location exception caught.", ble);
        }
    }

}