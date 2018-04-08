package org.xtuml.bp.ui.text.activity;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.xtuml.bp.als.oal.ParseRunnable;
import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.VariableLocation_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.OALPersistenceUtil;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.ui.text.TextPlugin;

/**
 * This action opens the declaration of model element references
 *
 */
public class OpenDeclarationAction implements IEditorActionDelegate {
	private IEditorPart targetEditor;

	@Override
	public void run(IAction arg0) {
		ActivityEditor editor;
		if (targetEditor instanceof ActivityEditor) {
			editor = (ActivityEditor) targetEditor;
		} else {
			throw new RuntimeException("Expecting Activity editor. Found: " + targetEditor.getClass().getName());
		}

		ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());

		try {
			int startLine = selection.getStartLine() + 1;
			int startOffset = selection.getOffset();
			IRegion findWord = editor.findWord(doc, startOffset);
			startOffset = findWord.getOffset();
			int lineOffset = doc.getLineOffset(startLine - 1);
			int offsetWithinLine = startOffset - lineOffset + 1;
			ActivityEditorInput input = (ActivityEditorInput) editor.getEditorInput();
			NonRootModelElement modelElement = input.getModelElement();
			Body_c bdy = OALPersistenceUtil.getOALModelElement(modelElement);
		    boolean parsed = false;
		    
			if (bdy == null) {
				parseBody(modelElement, offsetWithinLine, doc);
				bdy = OALPersistenceUtil.getOALModelElement(modelElement);
				if (bdy == null) {
					// element not found, just return
					return;
				}
				parsed = true;
			}
			NonRootModelElement declarationElement = (NonRootModelElement) bdy.Finddeclaration(offsetWithinLine,
					startLine);
			if (declarationElement == null && !parsed) {
				// If the user edited the OAL, it may need to be parsed  
				parseBody(modelElement, offsetWithinLine, doc);
				declarationElement = (NonRootModelElement) bdy.Finddeclaration(offsetWithinLine,
						startLine);
			}
			if (declarationElement != null) {
				if (declarationElement instanceof VariableLocation_c) {
					VariableLocation_c location = (VariableLocation_c) declarationElement;
				    lineOffset = doc.getLineOffset(location.getLinenumber() - 1);
					editor.selectAndReveal(lineOffset + location.getStartposition() - 1,
							location.getEndposition() + 1 - location.getStartposition());
				} else {
					UIUtil.openDeclaration(declarationElement);
				}
			}
		} catch (BadLocationException | PartInitException e) {
			TextPlugin.logError("Unable to locate line information for the text editor selection.", e);
		}

	}
	
	private void parseBody(NonRootModelElement modelElement, int offsetWithinLine, IDocument doc) {
		// run a parse of this body
		ParseRunnable parseRunner = new ParseRunnable(modelElement, doc.get());
		parseRunner.run();						
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		if (targetEditor instanceof ActivityEditor) {
			arg0.setEnabled(true);
		}
	}

	@Override
	public void setActiveEditor(IAction arg0, IEditorPart arg1) {
		if (arg1 == null && arg0 != null) {
			arg0.setEnabled(false);
		}
		if (arg1 instanceof ActivityEditor) {
			this.targetEditor = arg1;
			if (arg0 != null) {
				arg0.setEnabled(true);
			}
		}
	}

}
