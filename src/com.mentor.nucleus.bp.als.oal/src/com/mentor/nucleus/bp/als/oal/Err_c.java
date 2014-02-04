package com.mentor.nucleus.bp.als.oal;
//=====================================================================
//
//File:      $RCSfile: Err_c.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:43:47 $
//
//(c) Copyright 2003-2013 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Project Technology, Inc. and is not for external distribution.
//=====================================================================

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mentor.nucleus.bp.core.CorePlugin;

import antlr.Token;

     // Error Reporting
  public class Err_c {
    public static void Reportparseerror(final String Msg,
      final Token token)
    throws pt_SemanticException
    {
    	String output = Msg;
    	String tokenText = token.getText();
    	if (tokenText != null) {
    		output = Msg.replaceFirst("%s", tokenText);    //$NON-NLS-1$
    	}
    	throw new pt_SemanticException( output, token );  //$NON-NLS-1$
    } // End reportParseError
       
    
	public static boolean Tokenhasvalues(final Token token) {
		return token.getColumn() > 1;
	} // End tokenHasValues

  } // End ERR_c

