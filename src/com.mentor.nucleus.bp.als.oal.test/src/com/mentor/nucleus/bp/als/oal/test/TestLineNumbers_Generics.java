//========================================================================
//
//File:      $RCSfile: TestLineNumbers_Generics.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:00:34 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================
//
package com.mentor.nucleus.bp.als.oal.test;

import junit.framework.TestCase;
import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.mentor.nucleus.bp.core.AssignToMember_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.BridgeBody_c;
import com.mentor.nucleus.bp.core.DerivedAttributeBody_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.OperationBody_c;
import com.mentor.nucleus.bp.core.StateActionBody_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.VariableLocation_c;
import com.mentor.nucleus.bp.core.Variable_c;

public class TestLineNumbers_Generics extends TestCase {

    protected void tearDown() throws Exception {
        try {
            super.tearDown();
            OalParserTest_Generics.tearDownActionData();
        } catch (RecognitionException re) {
            // do nothing
        } catch (TokenStreamException te) {
            // do nothing
        }
    }

	/**
	 * @param act the activity to parse
	 * @param type the type of class that contins the activity
	 * @param activity the name of the activity
	 * @param numBlock the number of Block_c instances created by the parse
	 * @param numStmt the number of Statement_c instances created by the parse
	 * @param numVar the number of Variable_c instances created by the parse
	 * @param numVal the number of Value_c instances created by the parse
	 * @param stmtLine an array of tuples { <code>&lt;line number&gt;, &lt;starting col&gt;</code> }, one for each statement
	 * @param varLineCol an array of tuples { <code>&lt;line number&gt;, &lt;starting col&gt;, &lt;ending col&gt;</code> },
	 *    one for each instance of VariableLocation_c
	 * @param varIdValue an array of the indexes to the corresponding variable
	 *    for each VariableLocation_c.  If the VariableLocation_c is related
	 *    to an Assignment To Parameter instance, then the value is the negative
	 *    of the index of the statement.
	 * @param valLineCol an array of tuples { <code>&lt;line number&gt;, &lt;starting col&gt;, &lt;ending col&gt;</code> },
	 *    one for each instance of Value_c
	 * @throws RecognitionException
	 * @throws TokenStreamException
	 */
	private void validateStatementLineNumbers(
			String act, int type, int activity,
			int numBlock, int numStmt, int numVar, int numVal,
			int[][] stmtLine, int[][] varLineCol, int[] varIdValue, int[][] valLineCol)
	  throws RecognitionException, TokenStreamException
	{
		Statement_c.clearInstances(OalParserTest_Generics.modelRoot);
		Block_c.clearInstances(OalParserTest_Generics.modelRoot);
		Variable_c.clearInstances(OalParserTest_Generics.modelRoot);
		VariableLocation_c.clearInstances(OalParserTest_Generics.modelRoot);
		Value_c.clearInstances(OalParserTest_Generics.modelRoot);
        AssignToMember_c.clearInstances(OalParserTest_Generics.modelRoot);
		String x = OalParserTest_Generics.parseAction(act, type, activity);
		assertEquals("", x);//$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal( numBlock, numStmt, numVal );
		validateLineColInfo(numStmt, numVar, numVal, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	private void validateLineColInfo(int numStmt, int numVar, int numVal, int[][] stmtLine, int[][] varLineCol, int[] varIdValue, int[][] valLineCol) {
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numStmt, st.length);
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals( numVar, vars.length );
		Value_c [] vals = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals( numVal, vals.length );
		for ( int i = 0; i < st.length; ++i) {
 		  assertEquals("Statement " + i + " has wrong linenumber", stmtLine[i][0], st[i].getLinenumber());
		  assertEquals("Statement " + i + " has wrong startposition", stmtLine[i][1], st[i].getStartposition());
		}

		VariableLocation_c [] alllocs = VariableLocation_c.VariableLocationInstances(OalParserTest_Generics.modelRoot);
		if ( varLineCol == null ) {
			assertEquals(0, alllocs.length);
		}
		else {
			assertEquals(varLineCol.length, alllocs.length);
			for ( int i = 0; i < alllocs.length; ++i) {
	 		  assertEquals("VariableLocation [" + i + "] has wrong linenumber", varLineCol[i][0], alllocs[i].getLinenumber());
			  assertEquals("VariableLocation [" + i + "] has wrong startposition", varLineCol[i][1], alllocs[i].getStartposition());
			  assertEquals("VariableLocation [" + i + "] has wrong endposition", varLineCol[i][2], alllocs[i].getEndposition());

  		     // check that VariableLocation is related to the correct instance
			  Variable_c v = Variable_c.getOneV_VAROnR835(alllocs[i]);
			  assertNotNull( "VariableLocation [" + i + "] is not related to a Variable instance", v );
			  assertEquals( vars[varIdValue[i]].getVar_id(), v.getVar_id());
			}
		}

		for ( int i = 0; i < vals.length; ++i) {
		  assertEquals("Value " + i + " has wrong linenumber", valLineCol[i][0], vals[i].getLinenumber());
		  assertEquals("Value " + i + " has wrong startposition", valLineCol[i][1], vals[i].getStartposition());
		  assertEquals("Value " + i + " has wrong endposition", valLineCol[i][2], vals[i].getEndposition());
		}
	}
	private void validateAssignToMember(int l, int c1, int c2) {
		AssignToMember_c [] atp_set =  AssignToMember_c.AssignToMemberInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, atp_set.length);
	}

	public void test_implicit_ib_transform_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = {
				{ 1, 1 },
				{ 2, 1 },
				{ 3, 1 },
				{ 4, 1 },
				{ 5, 1 },
			};
		final int [][] varLineCol = {
			{ 1, 24, 24 },   // h
			{ 2, 1, 1 },   // h ref
			{ 3, 1, 1 },   // x
			{ 3, 5, 5 },   // h ref
			{ 4, 1, 1 },   // h ref
			{ 4, 16, 16 },   // h ref
			{ 5, 11, 11 },   // h ref
		};
		final int [] varIdValue = { 0, 0, 1, 0, 0, 0, 0 };
		final int [][] valLineCol = {
	        { 3, 1, 1 },    // x
            { 3, 7, -1 },   // test4()
            { 4, 1, 1 },    // h ref
            { 4, 3, 12 },   // Row_Number
            { 4, 18, -1 },  // test4()
            { 5, 7, 7 },    // s
            { 5, 13, -1 },  // testStringNoParm()
		};
		String act = "create object instance h of D_H;\n" +
		  "h.test1();\n" +
		  "x = h.test4();\n" +
		  "h.Row_Number = h.test4();\n" +
		  "param.s = h.testStringNoParm();\n";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.PARAM_REF, 1, 5, 2, 7, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_function_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = null;
		String act = "::test1();";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 1, 0, 0, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_implicit_assignment_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = {
				{ 1, 1 },
				{ 2, 1 },
				{ 3, 1 },
				{ 4, 1 },
			};
		final int [][] varLineCol = {
			{ 1, 24, 24 },   // d
			{ 2, 1, 1 },   // x
			{ 3, 1, 1 },   // d ref
		};
		final int [] varIdValue = { 0, 1, 0 };
		final int [][] valLineCol = {
            { 2, 1, 1 },   // x
			{ 2, 5, 5 },   // ""
            { 3, 1, 1 },   // d
            { 3, 3, 13 },  // string_attr
			{ 3, 17, 17 }, // ""
			{ 4, 7, 7 },   // s
            { 4, 11, 11 }, // ""
		};
		String act = "create object instance d of D_D;\n" +
		  "x = \"\";\n" +
		  "d.string_attr = \"\";\n" +
		  "param.s = \"\";\n";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.PARAM_REF, 1, 4, 2, 7, stmtLine, varLineCol, varIdValue, valLineCol );
		validateAssignToMember(3, 1, 1);
	}
	public void test_implicit_bridge_or_transform_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 2 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 7, 1 }, { 8, 1 }, { 9, 1 } };
		final int [][] varLineCol = {
				{ 2, 1, 1 },   // x
				{ 3, 24, 24 },   // d
				{ 4, 1, 1 },   // d
				{ 7, 1, 1 },   // x
				{ 8, 1, 1 },   // d
		};
		final int [] varIdValue = { 0, 1, 1, 0, 1 };
		final int [][] valLineCol = {
                { 2, 1, 1 },    // x
				{ 2, 10, -1 },  // D_D::testStringNoParm()
                { 4, 1, 1 },    // d
                { 4, 3, 13 },   // string_attr
				{ 4, 22, -1 },  // D_D::testStringNoParm()
                { 5, 7, 7 },    // s
				{ 5, 16, -1 },  // D_D::testStringNoParm()
                { 7, 1, 1 },    // x
				{ 7, 8, -1 },   // T::testStringNoParm()
                { 8, 1, 1 },    // d
                { 8, 3, 13 },   // string_attr
				{ 8, 20, -1 },  // T::testStringNoParm()
                { 9, 7, 7 },    // s
				{ 9, 14, -1 },  // T::testStringNoParm()
			};
		String act = " D_D::test1();\n" +
		  "x = D_D::testStringNoParm();\n" +
          "create object instance d of D_D;\n" +
		  "d.string_attr = D_D::testStringNoParm();\n" +
		  "param.s = D_D::testStringNoParm();\n" +
          "T::test1();\n" +
		  "x = T::testStringNoParm();\n" +
		  "d.string_attr = T::testStringNoParm();\n" +
		  "param.s = T::testStringNoParm();\n";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.PARAM_REF, 1, 9, 2, 14, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_assignment_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = {
				{ 1, 1 },
				{ 2, 1 },
				{ 3, 1 },
				{ 4, 1 },
			};
		final int [][] varLineCol = {
			{ 1, 24, 24 },   // d
			{ 2, 8, 8 },   // x
			{ 3, 8, 8 },   // d ref
		};
		final int [] varIdValue = { 0, 1, 0 };
		final int [][] valLineCol = {
	        { 2, 8, 8 },  // x
			{ 2, 12, 12 },  // ""
            { 3, 8, 8 },  // d
            { 3, 10, 20 },  // string_attr
			{ 3, 24, 24 },  // ""
            { 4, 14, 14 },  // s
			{ 4, 18, 18 },  // ""
		};
		String act = "create object instance d of D_D;\n" +
		  "assign x = \"\";\n" +
		  "assign d.string_attr = \"\";\n" +
		  "assign param.s = \"\";\n";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.PARAM_REF, 1, 4, 2, 7, stmtLine, varLineCol, varIdValue, valLineCol );
		validateAssignToMember(4, 14, 14);
	}
	public void test_break_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 3 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = {
				{ -1, -1, -1 },  // false
		};
		String act = "while (false)\n" +
			"  break;\n" +
			"end while;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 2, 2, 0, 1, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_bridge_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 } };
		final int [][] varLineCol = {
				{ 2, 8, 8 },   // x
				{ 3, 24, 24 },   // d
				{ 4, 8, 8 },   // d
		};
		final int [] varIdValue = { 0, 1, 1 };
		final int [][] valLineCol = {
                { 2, 8, 8 },    // x
				{ 2, 15, -1 },  // T::testStringNoParm()
                { 4, 8, 8 },    // d
                { 4, 10, 20 },  // string_attr
				{ 4, 27, -1 },  // T::testStringNoParm()
                { 5, 14, 14 },  // s
				{ 5, 21, -1 },  // T::testStringNoParm()
			};
		String act = "bridge T::test1();\n" +
		  "bridge x = T::testStringNoParm();\n" +
	      "create object instance d of D_D;\n" +
		  "bridge d.string_attr = T::testStringNoParm();\n" +
		  "bridge param.s = T::testStringNoParm();\n";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.PARAM_REF, 1, 5, 2, 7, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_continue_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 3 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = {
				{ -1, -1, -1 },  // false
		};
		String act = "while (false)\n" +
			"  continue;\n" +
			"end while;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 2, 2, 0, 1, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_control_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = null;
		String act = "control stop;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 1, 0, 0, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_create_object_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 }, { 3, 1 } };
		final int [][] varLineCol = {
				{ 1, 24, 24 },   // d
				{ 2, 24, 24 },   // d ref
		};
		final int [] varIdValue = { 0, 0 };
		final int [][] valLineCol = null;
		String act = "create object instance d of D_D; // declare\n" +
			"create object instance d of D_D; // already declared\n" +
			"create object instance of D_D;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 3, 1, 0, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_create_event_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 } };
		final int [][] varLineCol = {
				{ 1, 24, 24 },   // t
				{ 2, 23, 23 },   // e
				{ 2, 38, 38 },   // t ref
				{ 3, 23, 23 },   // e ref
				{ 4, 23, 23 },   // e ref
		};
		final int [] varIdValue = { 0, 1, 0, 1, 1 };
		final int [][] valLineCol = null;
		String act = "create object instance t of D_TST;\n" +
			"create event instance e of D_TST1 to t;\n" +
			"create event instance e of D_TST_A1 to D_TST class;\n" +
			"create event instance e of D_TST1 to D_TST creator;\n";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 4, 2, 0, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_delete_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 } };
		final int [][] varLineCol = {
				{ 1, 24, 24 },   // d
				{ 2, 24, 24 },   // d ref
		};
		final int [] varIdValue = { 0, 0 };
		final int [][] valLineCol = null;
		String act = "create object instance d of D_D;\n" +
			"delete object instance d;\n";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 2, 1, 0, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_for_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 } };
		final int [][] varLineCol = {
				{ 1, 13, 14 },   // ds
				{ 2, 10, 10 },    // d
				{ 2, 15, 16 },   // ds ref
		};
		final int [] varIdValue = { 0, 1, 0 };
		final int [][] valLineCol = null;
		String act = "select many ds from instances of D_D;\n" +
			"for each d in ds\n" +
			"end for;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 2, 2, 2, 0, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_generate_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 7, 1 } };
		final int [][] varLineCol = {
				{ 1, 24, 24 },   // t
				{ 2, 20, 20 },   // t ref
				{ 5, 23, 23 },   // e
				{ 6, 10, 10 },   // e ref
				{ 7, 10, 10 },   // t ref
		};
		final int [] varIdValue = { 0, 0, 1, 1, 0 };
		final int [][] valLineCol = {
				{ 6, 10, 10 },  // e ref
				{ 7, 10, 10 },  // t ref
				{ 7, 12, 19 }   // evt_inst
		};
		String act = "create object instance t of D_TST;\n" +
			"generate D_TST1 to t;\n" +
			"generate D_TST_A1 to D_TST class;\n" +
			"generate D_TST1 to D_TST creator;\n" +
			"create event instance e of D_TST1 to D_TST creator;\n" +
			"generate e;\n" +
			"generate t.evt_inst;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 7, 2, 3, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_if_statement1_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = {
				{ -1, -1, -1 },  // true
		};
		String act = "if (true)\n" +
			"end if;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 2, 1, 0, 1, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_if_statement2_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = {
				{ -1, -1, -1 },  // true
				{ -1, -1, -1 },  // false
		};
		String act = "if (true)\n" +
			"elif ( false )\n" +
			"end if;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 3, 2, 0, 2, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_if_statement3_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 }, { 3, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = {
				{ -1, -1, -1 },  // true
				{ -1, -1, -1 },  // false
				{ -1, -1, -1 },  // false
		};
		String act = "if (true)\n" +
			"elif ( false )\n" +
			"elif ( false )\n" +
			"end if;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 4, 3, 0, 3, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_if_statement4_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = {
				{ -1, -1, -1 },  // true
		};
		String act = "if (true)\n" +
			"else\n" +
			"end if;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 3, 2, 0, 1, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_if_statement5_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 }, { 3, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = {
				{ -1, -1, -1 },  // true
				{ -1, -1, -1 },  // false
		};
		String act = "if (true)\n" +
			"elif ( false )\n" +
			"else\n" +
			"end if;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 4, 3, 0, 2, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_if_statement6_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = {
				{ -1, -1, -1 },  // true
				{ -1, -1, -1 },  // false
				{ -1, -1, -1 },  // false
		};
		String act = "if (true)\n" +
			"elif ( false )\n" +
			"elif ( false )\n" +
			"else\n" +
			"end if;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 5, 4, 0, 3, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_relate_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 } };
		final int [][] varLineCol = {
				{ 1, 12, 12 },   // d
				{ 2, 12, 12 },   // h
				{ 3, 8, 8 },     // d ref
				{ 3, 13, 13 },   // h ref
				{ 4, 24, 24 },   // q
				{ 5, 24, 25 },   // dq
				{ 6, 8, 8 },     // d ref
				{ 6, 13, 13 },   // q ref
				{ 6, 31, 32 },   // dq ref
		};
		final int [] varIdValue = { 0, 1, 0, 1, 2, 3, 0, 2, 3 };
		final int [][] valLineCol = null;
		String act =
		  "select any d from instances of D_D;\n" +
		  "select any h from instances of D_H;\n" +
		  "relate d to h across R4;\n" +
		  "create object instance q of D_QP;\n" +
		  "create object instance dq of D_DQ;\n" +
		  "relate d to q across R1 using dq;\n";

		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 6, 4, 0, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_returnvoid_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = null;
		String act = "return;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 1, 0, 0, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_returnint_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol =  { { 1, 8, 8 } };  // 1
		String act = "return 1;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST4, 1, 1, 0, 1, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_select_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 },
				{ 6, 1 }, { 7, 1 }, { 8, 1 }, { 9, 1 }, { 10, 1 } };
		final int [][] varLineCol = {
				{ 1, 12, 12 },   // d
				{ 2, 12, 12 },   // d ref
				{ 3, 13, 14 },   // ds
				{ 4, 13, 14 },   // ds ref
				{ 5, 12, 12 },   // h
				{ 6, 12, 12 },   // h ref
				{ 7, 12, 13 },   // dq
				{ 8, 12, 13 },   // dq ref
				{ 9, 13, 15 },   // dqs
				{ 10, 13, 15 },  // dqs ref
		};
		final int [] varIdValue = { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4 };
		final int [][] valLineCol = {
				{ 5, 25, 25 },   // d ref
				{ 6, 25, 25 },   // d ref
				{ 7, 26, 26 },   // d ref
				{ 8, 26, 26 },   // d ref
				{ 9, 28, 28 },   // d ref
				{ 10, 28, 28 },  // d ref
		};
		String act =
		  "select any d from instances of D_D;\n" +
		  "select any d from instances of D_D;\n" +
		  "select many ds from instances of D_D;\n" +
		  "select many ds from instances of D_D;\n" +
		  "select one h related by d->D_H[R4];\n" +
		  "select one h related by d->D_H[R4];\n" +
		  "select any dq related by d->D_DQ[R1];\n" +
		  "select any dq related by d->D_DQ[R1];\n" +
		  "select many dqs related by d->D_DQ[R1];\n" +
		  "select many dqs related by d->D_DQ[R1];\n";

		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 10, 5, 6, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_transform_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = {
				{ 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 },
			};
		final int [][] varLineCol = {
			{ 2, 11, 11 },   // x
			{ 3, 24, 24 },   // d
			{ 4, 11, 11 },   // d ref
		};
		final int [] varIdValue = { 0, 1, 1 };
		final int [][] valLineCol = {
	        { 2, 11, 11 },  // x
			{ 2, 20, -1 },  // testStringNoParm()
            { 4, 11, 11 },  // d
            { 4, 13, 23 },  // string_attr
			{ 4, 32, -1 },  // testStringNoParm()
            { 5, 17, 17 },  // s
			{ 5, 26, -1 },  // testStringNoParm()
		};
		String act = "transform D_D::testVoidNoParm();\n" +
		  "transform x = D_D::testStringNoParm();\n" +
		  "create object instance d of D_D;\n" +
		  "transform d.string_attr = D_D::testStringNoParm();\n" +
		  "transform param.s = D_D::testStringNoParm();";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.PARAM_REF, 1, 5, 2, 7, stmtLine, varLineCol, varIdValue, valLineCol );
		validateAssignToMember(4, 11, 11);
	}
	public void test_unrelate_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 } };
		final int [][] varLineCol = {
				{ 1, 12, 12 },   // d
				{ 2, 12, 12 },   // h
				{ 3, 10, 10 },   // d ref
				{ 3, 17, 17 },   // h ref
				{ 4, 12, 12 },   // q
				{ 5, 12, 13 },   // dq
				{ 6, 10, 10 },   // d ref
				{ 6, 17, 17 },   // q ref
				{ 6, 35, 36 },   // dq ref
		};
		final int [] varIdValue = { 0, 1, 0, 1, 2, 3, 0, 2, 3 };
		final int [][] valLineCol = null;
		String act =
		  "select any d from instances of D_D;\n" +
		  "select any h from instances of D_H;\n" +
		  "unrelate d from h across R4;\n" +
		  "select any q from instances of D_QP;\n" +
		  "select any dq from instances of D_DQ;\n" +
		  "unrelate d from q across R1 using dq;\n";

		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 6, 4, 0, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_while_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = { { 1, 1 } };
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = {
				{ -1, -1, -1 },  // false
		};
		String act = "while (false)\n" +
			"end while;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 2, 1, 0, 1, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void test_debug_statement_LineNumbers() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = null;
		final int [][] varLineCol = null;
		final int [] varIdValue = null;
		final int [][] valLineCol = null;
		String act = "_debug _on;";
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST1, 1, 0, 0, 0, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void testStatementLineNumbersInFunction() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = {
			{ 1, 1 },   // 0
			{ 2, 1 },   // 1
			{ 3, 1 },   // 2
			{ 5, 1 },   // 3
			{ 6, 1 },   // 4
			{ 7, 1 },   // 5
			{ 8, 1 },   // 6
			{ 9, 1 },   // 7
			{ 10, 1 },  // 8
			{ 10, 40 }   // 9
		};
		final int [][] varLineCol = {
			{ 1, 1, 1 },   // x
			{ 2, 1, 2 },   // yy
			{ 2, 6, 6 },   // x
			{ 3, 12, 12 }, // z
			{ 5, 1, 1 },   // q
			{ 6, 1, 1 },   // r
			{ 6, 5, 5 }, // z ref
			{ 7, 1, 1 },   // s
			{ 8, 13, 13 }, // x
			{ 9, 16, 17 }, // yy
			{10, 12, 12 }, // t
			{10, 59, 59},  // s
			{10, 65, 65 }  // t reference
		};
		final int [] varIdValue = { 0, 1, 0, 2, 3, 4, 2, 5, 0, 1, 6, 5, 6 };
		final int [][] valLineCol = {
	        { 1, 1, 1 },    // x
            { 1, 5, 5 },    // 1
            { 2, 1, 2 },    // yy
			{ 2, 6, 6 },    // x
			{ -1, -1, -1 }, // +
            { 2, 10, 10 },  // 1
            { 5, 1, 1 },    // q
			{ 5, 15, 15 },  // z
			{ -1, -1, -1 }, // not_empty
            { 6, 1, 1 },    // r
            { 6, 5, 5 },    // z
			{ 6, 7, 13 },   // Disk_ID
            { 7, 1, 1},     // s
			{ 7, 11, 11},   // i
			{ 8, 13, 13 },  // x
			{ 9, 16, 17 },  // yy
			{ 10, 59, 59 }  // s
		};
		String act = "x = 1;\n" + //$NON-NLS-1$
		    "yy = x + 1;\n" + //$NON-NLS-1$
		    "select any z \n" +//$NON-NLS-1$
		    "  from instances of D_D;\n" + //$NON-NLS-1$
		    "q = not_empty z;\n" + //$NON-NLS-1$
		    "r = z.Disk_ID;\n" + //$NON-NLS-1$
		    "s = param.i;\n" + //$NON-NLS-1$
		    "::test2( i: x );\n" + //$NON-NLS-1$
		    "D_D::test2( i: yy );\n" + //$NON-NLS-1$
			"select any t from instances of D_TST;  generate D_TST2(i: s) to t; "; //$NON-NLS-1$
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST2, 1, 10, 7, 17, stmtLine, varLineCol, varIdValue, valLineCol );
	}
	public void testStatementLineNumbersInState() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = {
			{ 1, 1 },
			{ 2, 1 }
		};
		final int [][] varLineCol = {
			{ 1, 1, 1 },   // s
			{ 2, 1, 1 },   // t
			{ 2, 5, 8 },    // self
		};
		final int [] varIdValue = { 0, 1, 2 };
		final int [][] valLineCol = {
	        { 1, 1, 1 },    // s
			{ 1, 14, 14 },  // i
            { 2, 1, 1 },    // t
            { 2, 5, 8 },    // self
			{ 2, 10, 14 },  // u_int
		};
		String act = "s = rcvd_evt.i;\nt = self.u_int;\n"; //$NON-NLS-1$
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_STATE, OalParserTest_Generics.STATE_ISM_ONE, 1, 2, 3, 5, stmtLine, varLineCol, varIdValue, valLineCol );
	}

	public void testStatementLineNumbersInOperation() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = {
				{ 1, 1 }
			};
		final int [][] varLineCol = {
			{ 1, 1, 1 },   // s
		};
		final int [] varIdValue = { 0 };
		final int [][] valLineCol = {
	        { 1, 1, 1 },    // s
			{ 1, 11, 11 },  // i
		};
		String act = "s = param.i;\n"; //$NON-NLS-1$
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_CB_OP, OalParserTest_Generics.TEST2, 1, 1, 1, 2, stmtLine, varLineCol, varIdValue, valLineCol );
	}

	public void testStatementLineNumbersInBridge() throws RecognitionException, TokenStreamException {
		final int [][] stmtLine = {
				{ 1, 1 }
			};
		final int [][] varLineCol = {
			{ 1, 1, 1 },   // s
		};
		final int [] varIdValue = { 0 };
		final int [][] valLineCol = {
	        { 1, 1, 1 },    // s
			{ 1, 11, 11 },  // i
		};
		String act = "s = param.i;\n"; //$NON-NLS-1$
		validateStatementLineNumbers(act, OalParserTest_Generics.ACTIVITY_TYPE_BRG, OalParserTest_Generics.TEST2, 1, 1, 1, 2, stmtLine, varLineCol, varIdValue, valLineCol );
	}
}
