package com.mentor.nucleus.bp.ui.text.activity;
//====================================================================
//
// File:      $RCSfile: EditorTextParser.java,v $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import java.util.UUID;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.IAnnotationModel;

import antlr.MismatchedTokenException;
import antlr.NoViableAltException;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.AST;

import com.mentor.nucleus.bp.als.oal.OalParser;
import com.mentor.nucleus.bp.als.oal.Oal_validate;
import com.mentor.nucleus.bp.als.oal.TextParser;
import com.mentor.nucleus.bp.als.oal.pt_SemanticException;
import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Oalconstants_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.util.ContainerUtil;
import com.mentor.nucleus.bp.ui.text.TextPlugin;
import com.mentor.nucleus.bp.ui.text.annotation.ActivityProblem;

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
