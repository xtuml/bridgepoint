package com.mentor.nucleus.bp.ui.text.activity;
//====================================================================
//
// File:      $RCSfile: TextParser.java,v $
// Version:   $Revision: 1.25 $
// Modified:  $Date: 2013/01/10 23:20:48 $
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

public class TextParser extends OalParser
{
  ActivityEditorInput activityEditorInput = null;
  IAnnotationModel model = null;
  IDocument document = null;
  long m_act_id = 0;

  public TextParser(
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

  public final UUID  action(  Object model, boolean clearContext ) 
  	throws RecognitionException, TokenStreamException, InterruptedException 
  {
      // The parser validation code assumes that all data types
      // are loaded.
    PersistableModelComponent.ensureDataTypesAvailable(getModelRoot());
    int type = Oalconstants_c.OOA_UNINITIALIZED_ENUM;
    UUID act_id = null;
    NonRootModelElement container = null;
    
 	if ( model instanceof StateMachineState_c ) {
 	    Action_c act = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
 		          MooreActionHome_c.getOneSM_MOAHOnR511((StateMachineState_c)model)));
		if ( act != null ) {
		    act_id = act.getAct_id();
		    container = ContainerUtil.getContainer(act);
		    type = Oalconstants_c.STATE_TYPE;
		} else {
		    // if we can't find the Action just give up
			return IdAssigner.NULL_UUID;
		}
 	}
 	else if ( model instanceof Transition_c ) {
 	    Action_c act = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
 		          TransitionActionHome_c.getOneSM_TAHOnR530((Transition_c)model)));
		if ( act != null ) {
		    act_id = act.getAct_id();
		    container = ContainerUtil.getContainer(act);
		    type = Oalconstants_c.TRANSITION_TYPE;
		} else {
		    // if we can't find the Action just give up
			return IdAssigner.NULL_UUID;
		}
 	}
 	else if ( model instanceof CreationTransition_c ) {
 	    Action_c act = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
 		          TransitionActionHome_c.getOneSM_TAHOnR530(Transition_c.getOneSM_TXNOnR507((CreationTransition_c)model))));
		if ( act != null ) {
		    act_id = act.getAct_id();
		    container = ContainerUtil.getContainer(act);
		    type = Oalconstants_c.TRANSITION_TYPE;
		} else {
		    // if we can't find the Action just give up
			return IdAssigner.NULL_UUID;
		}
 	}
	else if ( model instanceof Bridge_c ) {
		act_id = ((Bridge_c)model).getBrg_id();
	    container = ContainerUtil.getContainer((Bridge_c)model);
	    type = Oalconstants_c.BRIDGE_TYPE;
	}
	else if ( model instanceof Function_c ) {
		act_id = ((Function_c)model).getSync_id();
		container = ContainerUtil.getContainer((Function_c)model);
		type = Oalconstants_c.FUNCTION_TYPE;
	}
	else if ( model instanceof Operation_c ) {
		act_id = ((Operation_c)model).getTfr_id();
		container = ContainerUtil.getContainer((Operation_c)model);
		type = Oalconstants_c.OPERATION_TYPE;
	}
	else if ( model instanceof Attribute_c ) {
		act_id = ((Attribute_c)model).getAttr_id();
		container = ContainerUtil.getContainer((Attribute_c)model);
		type = Oalconstants_c.MDA_TYPE;
	}
	else if ( model instanceof RequiredOperation_c ) {
		act_id = ((RequiredOperation_c)model).getId();
		container = ContainerUtil.getContainer((RequiredOperation_c)model);
		type = Oalconstants_c.REQ_OPERATION_TYPE;
	}
	else if ( model instanceof RequiredSignal_c ) {
		act_id = ((RequiredSignal_c)model).getId();
		container = ContainerUtil.getContainer((RequiredSignal_c)model);
		type = Oalconstants_c.REQ_SIGNAL_TYPE;
	}
	else if ( model instanceof ProvidedOperation_c ) {
		act_id = ((ProvidedOperation_c)model).getId();
		container = ContainerUtil.getContainer((ProvidedOperation_c)model);
		type = Oalconstants_c.PROV_OPERATION_TYPE;
	}
	else if ( model instanceof ProvidedSignal_c ) {
		act_id = ((ProvidedSignal_c)model).getId();
		container = ContainerUtil.getContainer((ProvidedSignal_c)model);
		type = Oalconstants_c.PROV_SIGNAL_TYPE;
	}
	else
	{
		TextPlugin.logError("Unknown action type", null);
		return IdAssigner.NULL_UUID;
	}
 	// If we get here, the action type was recognized and everything is set up
 	if (clearContext) {
 	 	if (container instanceof Package_c) {
 	 		((Package_c)container).Clearscope();
 	 	}
 	 	else if (container instanceof Component_c) {
 	 		((Component_c)container).Clearscope();
 	 	}
 	}
    m_oal_context = new Oal_validate( container );
 	return super.action(act_id, type);
  }
  
public void reportError(RecognitionException ex) 
{
	String tokenText = " ";
	final int lineNumber = ex.getLine();
	String message = ex.getMessage();
	if (ex instanceof MismatchedTokenException) {
		MismatchedTokenException mmex = (MismatchedTokenException) ex;
		Token token = mmex.token;
		if (token != null && token.getText() != null) {
			tokenText = token.getText();
		}
	} else if (ex instanceof NoViableAltException) {
		NoViableAltException nvex = (NoViableAltException) ex;
		Token token = nvex.token;
		AST ast = nvex.node;
		if (token != null) {
			tokenText = token.getText();
		} else if (ast != null) {
			tokenText = token.getText();
		}
		if (tokenText == null) {
			tokenText = " ";
		}
	} else if (ex instanceof pt_SemanticException) {
		tokenText = ((pt_SemanticException) ex).token.getText();
		if (tokenText == null) {
			tokenText = " ";
		}
	}
	int offset = 0;
	int lineLength = 0;
	try {
		IRegion info = document.getLineInformation(lineNumber - 1);
		offset = info.getOffset();
		lineLength = info.getLength();
	} catch (BadLocationException x) {
		TextPlugin.logError("Bad line number: " + (lineNumber - 1), x);
	}
	int lastChar = document.getLength() - 1;
	int	col = java.lang.Math.min( ex.getColumn(), lineLength );
	int charStart = java.lang.Math.min( offset + col - 1, lastChar );
	int charEnd = charStart + tokenText.length() - 1;

	if (charStart==-1 && charEnd==-1 && lineNumber==1) {
		  charStart = 1;
		  charEnd = 1;
	}
	
	ActivityProblem ap =
		new ActivityProblem(
			message,
			IMarker.SEVERITY_ERROR,
			lineNumber,
			charStart,
			charEnd,
			activityEditorInput);

	((ActivityAnnotationModel) model).acceptProblem(ap);
}

}
