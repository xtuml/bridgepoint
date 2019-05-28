package org.xtuml.bp.ui.text.activity;
import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.xtuml.bp.als.oal.TextParser;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.ui.text.TextPlugin;
import org.xtuml.bp.ui.text.annotation.ActivityProblem;

import antlr.RecognitionException;
import antlr.TokenStream;

public class EditorTextParser extends TextParser
{
  ActivityEditorInput activityEditorInput = null;
  IAnnotationModel model = null;
  IDocument document = null;

  public EditorTextParser(
    Ooaofooa modelRoot,
    TokenStream lexer,
    IAnnotationModel model,
    ActivityEditorInput ae_input,
    IDocument document)
  {
    super(modelRoot, lexer);
    this.model = model;
    this.activityEditorInput = ae_input;
    this.document = document;
  }

  
public void reportError(RecognitionException ex) 
{ 
	super.reportError(ex, false);
	int offset = 0;
	int lineLength = 0;
	try {
		IRegion info = document.getLineInformation(parserLineNumber - 1);
		offset = info.getOffset();
		lineLength = info.getLength();
	} catch (BadLocationException x) {
		TextPlugin.logError("Bad line number: " + (parserLineNumber - 1), x);
	}
	int lastChar = document.getLength() - 1;
	int	col = java.lang.Math.min( ex.getColumn(), lineLength );
	int charStart = java.lang.Math.min( offset + col - 1, lastChar );
	int charEnd = charStart + parserTokenText.length() - 1;

	if (charStart==-1 && charEnd==-1 && parserLineNumber==1) {
		  charStart = 1;
		  charEnd = 1;
	}
	
	ActivityProblem ap =
		new ActivityProblem(
				parserMessage,
			IMarker.SEVERITY_ERROR,
			parserLineNumber,
			charStart,
			charEnd,
			activityEditorInput);

	((ActivityAnnotationModel) model).acceptProblem(ap);
}

}
