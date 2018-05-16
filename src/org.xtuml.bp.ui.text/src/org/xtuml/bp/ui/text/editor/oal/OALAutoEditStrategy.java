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

package org.xtuml.bp.ui.text.editor.oal;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

public class OALAutoEditStrategy implements IAutoEditStrategy {

    public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
        try {
            /* TODO - old example
            if (command.text.equals("{")) {
                int line = document.getLineOfOffset(command.offset);
                String indent = getIndentOfLine(document, line);
                String prevIndent = getIndentOfLine(document, line-1);
                // TODO - maybe when we see a newline is when we should look at 
                // the current line and see if it is a block construct (if/for/etc)
                // and if it is then do the auto-indent... and perhaps add the closing "end xxx"
                command.text = "{" + "\r\n" + prevIndent + indent + "}";
                configureCommand(command);
            } */
            if (command.text.equals(System.lineSeparator())) {
                int line = document.getLineOfOffset(command.offset);
                String curIndent = getIndentOfLine(document, line);
                String nextIndent = getAdditionalIndent(document, line);
                command.text = System.lineSeparator() + curIndent + nextIndent;
            }
        } catch (BadLocationException ble) {
            // TODO - what to do? Log, fail, nothing?
        }
    }

    private void configureCommand(DocumentCommand command) {
        // puts the caret between both the quotes
        command.caretOffset = command.offset + 1;
        command.shiftsCaret = false;
    }

    public static int findEndOfWhiteSpace(IDocument document, int offset, int end) throws BadLocationException {
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
            int end = start + document.getLineLength(line) - 1;
            int whiteend = findEndOfWhiteSpace(document, start, end);
            return document.get(start, whiteend - start);
        } else {
            return "";
        }
    }

    public static String getAdditionalIndent(IDocument document, int line) throws BadLocationException {
        if (line > -1) {
            int start = document.getLineOffset(line);
            int end = start + document.getLineLength(line) - 1;
            String text = document.get(start,end-start);
            text = text.replaceAll("\\s", "");
            if ( text.matches("^(?i)(if|elif|while)\\(.*") || text.matches("^(?i)(else|foreach).*") ) {
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