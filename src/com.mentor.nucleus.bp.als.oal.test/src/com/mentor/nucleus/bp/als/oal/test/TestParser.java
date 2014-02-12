package com.mentor.nucleus.bp.als.oal.test;
//=====================================================================
//
//File:      $RCSfile: TestParser.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2013/01/10 23:00:38 $
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

import java.io.StringReader;
import java.util.UUID;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.mentor.nucleus.bp.als.oal.OalLexer;
import com.mentor.nucleus.bp.als.oal.OalParser;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Oalconstants_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.test.common.BaseTest;


/**
 * @author greg
 *
 * This class is useful for testing a single test,
 * before integrating it into the OalParserTest class.
 * 
 */
public class TestParser {

	public static void main(String[] args) {
		String testCase = "x = 1 + 2 - 3;"; //$NON-NLS-1$
		//String testCase = "x = 2; while (x < 10)\n end while;"; //$NON-NLS-1$
		//String testCase = "x = +1; y = 1 + x;";//$NON-NLS-1$
		//String testCase = "x = (1); " + //$NON-NLS-1$
		//                  "x1 = -(2); " + //$NON-NLS-1$
		//                  "x2 = (-3); " + //$NON-NLS-1$
		//                  "x = (1); " + //$NON-NLS-1$
		//                  "y = 1 + (x * 2); " + //$NON-NLS-1$
		//                  "z = ((x)+300)-(y+2);"; //$NON-NLS-1$

		Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();

		UUID boolId = BaseTest.getTypeID(modelRoot, "boolean");//$NON-NLS-1$
		if (boolId == null) {
			DataType_c boolDt = new DataType_c(modelRoot,
				UUID.randomUUID(), IdAssigner.NULL_UUID , "boolean", "", "" );
		}

		UUID intId = BaseTest.getTypeID(modelRoot, "integer");//$NON-NLS-1$
		if (intId == null) {
			DataType_c intDt = new DataType_c(modelRoot,
                UUID.randomUUID(), IdAssigner.NULL_UUID , "integer", "", "" );
		}

		UUID strId = BaseTest.getTypeID(modelRoot, "string");//$NON-NLS-1$
		if (strId == null) {
			DataType_c strDt = new DataType_c(modelRoot,
				UUID.randomUUID(), IdAssigner.NULL_UUID , "string", "", "" );
		}

		Function_c testFunc = new Function_c(modelRoot);
				
		OalLexer lexer = new OalLexer(new StringReader(testCase));
		OalParser parser = new OalParser(modelRoot, lexer);
		try {
		  // Parse the input expression
		  parser.action(testFunc.getSync_id(), Oalconstants_c.FUNCTION_TYPE);
		}
		catch(TokenStreamException e) {
			System.out.println( e.getMessage());
		}
		catch(RecognitionException e) {
			System.out.println( e.getMessage());
		}
		catch (InterruptedException ie){
		}

		Body_c act =
			Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(testFunc));

		if (act != null) {
			act.Clear_blocks();
		}
		testFunc.delete();
		
		Ooaofooa.shutdown();
		System.out.println( parser.m_output );
		System.exit(0);
	}
}
