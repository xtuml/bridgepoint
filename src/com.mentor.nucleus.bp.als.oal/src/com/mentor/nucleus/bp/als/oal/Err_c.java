package com.mentor.nucleus.bp.als.oal;
//=====================================================================
//
//File:      $RCSfile: Err_c.java,v $
//Version:   $Revision: 1.10 $
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

