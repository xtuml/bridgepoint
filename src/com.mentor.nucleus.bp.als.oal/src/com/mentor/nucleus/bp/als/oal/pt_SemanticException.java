package com.mentor.nucleus.bp.als.oal;
//=====================================================================
//
//File:      $RCSfile: pt_SemanticException.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:43:47 $
//
//(c) Copyright 2003-2013 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Project Technology, Inc. and is not for external distribution.
//=====================================================================

import antlr.SemanticException;
import antlr.Token;

public class pt_SemanticException extends SemanticException {

	public Token token;

	public pt_SemanticException(
		String s,
		Token t)
	{
		super(s, "", t.getLine(), t.getColumn());  //$NON-NLS-1$
		token = t;
	}

  	public String toString() {
  		int endCol = column;
  		if ( token.getText() != null )
  		{
			endCol = column + token.getText().length() - 1;
  		}

		return fileName + ":" + String.valueOf(line) + 
                          ":" + String.valueOf(column) + "-" + String.valueOf(endCol) + 
                          ": " + getMessage();
	}

}
