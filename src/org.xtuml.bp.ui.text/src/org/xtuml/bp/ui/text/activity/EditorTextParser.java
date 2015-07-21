package org.xtuml.bp.ui.text.activity;
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

import org.xtuml.bp.als.oal.OalParser;
import org.xtuml.bp.als.oal.Oal_validate;
import org.xtuml.bp.als.oal.TextParser;
import org.xtuml.bp.als.oal.pt_SemanticException;
import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.Oalconstants_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.util.ContainerUtil;
import org.xtuml.bp.ui.text.TextPlugin;
import org.xtuml.bp.ui.text.annotation.ActivityProblem;

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
