package com.mentor.nucleus.bp.als.oal;
//=====================================================================
//
//File:      $RCSfile: pt_SemanticException.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:43:47 $
//
//(c) Copyright 2003-2014 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
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
