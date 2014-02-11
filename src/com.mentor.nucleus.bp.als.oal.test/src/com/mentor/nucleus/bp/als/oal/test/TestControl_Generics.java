//========================================================================
//
//File:      $RCSfile: TestControl_Generics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:00:32 $
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

import java.util.UUID;

import junit.framework.TestCase;
import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.mentor.nucleus.bp.core.BinaryOperation_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.Break_c;
import com.mentor.nucleus.bp.core.Continue_c;
import com.mentor.nucleus.bp.core.Control_c;
import com.mentor.nucleus.bp.core.ElseStmt_c;
import com.mentor.nucleus.bp.core.ElseifStmt_c;
import com.mentor.nucleus.bp.core.ForStmt_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.IfStmt_c;
import com.mentor.nucleus.bp.core.InstanceSet_c;
import com.mentor.nucleus.bp.core.InstanceHandle_c;
import com.mentor.nucleus.bp.core.LiteralBoolean_c;
import com.mentor.nucleus.bp.core.LiteralInteger_c;
import com.mentor.nucleus.bp.core.LiteralReal_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.ReturnStmt_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.TransientValueReference_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.core.WhileStmt_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;

public class TestControl_Generics extends TestCase {

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

	public void testEmptyWhile() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("while (false)\n end while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c act = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(act);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(act.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(act.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(act.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		WhileStmt_c[] whl = WhileStmt_c.WhileStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, whl.length);
		assertEquals(whl[0].getStatement_id(), st[0].getStatement_id());
		assertEquals(whl[0].getBlock_id(), blk[1].getBlock_id());
		LiteralBoolean_c[] lbool = LiteralBoolean_c.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lbool.length);
		assertEquals("FALSE", lbool[0].getValue());//$NON-NLS-1$
		assertEquals(whl[0].getValue_id(), lbool[0].getValue_id());
	}
	public void testWhileBreak() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("while ( true )\n a = 1;\n break;\nend while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c act = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(act);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(act.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(act.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(act.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		WhileStmt_c[] whl = WhileStmt_c.WhileStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, whl.length);
		assertEquals(whl[0].getStatement_id(), st[0].getStatement_id());
		assertEquals(whl[0].getBlock_id(), blk[1].getBlock_id());
		LiteralBoolean_c[] lbool = LiteralBoolean_c.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lbool.length);
		assertEquals("TRUE", lbool[0].getValue());//$NON-NLS-1$
		assertEquals(whl[0].getValue_id(), lbool[0].getValue_id());
		Break_c[] break_stmt = Break_c.BreakInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, break_stmt.length);
		assertEquals(st[2].getStatement_id(), break_stmt[0].getStatement_id());
	}
	public void testWhileContinue() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("while ( true )\n a = 1;\n continue;\nend while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c act = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(act);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(act.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(act.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(act.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		WhileStmt_c[] whl = WhileStmt_c.WhileStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, whl.length);
		assertEquals(whl[0].getStatement_id(), st[0].getStatement_id());
		assertEquals(whl[0].getBlock_id(), blk[1].getBlock_id());
		LiteralBoolean_c[] lbool = LiteralBoolean_c.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lbool.length);
		assertEquals("TRUE", lbool[0].getValue());//$NON-NLS-1$
		assertEquals(whl[0].getValue_id(), lbool[0].getValue_id());
		Continue_c[] continue_stmt = Continue_c.ContinueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, continue_stmt.length);
		assertEquals(st[2].getStatement_id(), continue_stmt[0].getStatement_id());
	}
	public void testSimpleWhile1() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("i = 2;\n while (i < 10)\n i = 13;\n end while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c act = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(act);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(act.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(act.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(act.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
		assertEquals("i", t[0].getName());//$NON-NLS-1$
		assertEquals(t[0].getBlock_id(), blk[0].getBlock_id());
		LiteralInteger_c[] lin = LiteralInteger_c.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, lin.length);
		assertEquals("2", lin[0].getValue());//$NON-NLS-1$
		assertEquals("10", lin[1].getValue());//$NON-NLS-1$
		assertEquals("13", lin[2].getValue());//$NON-NLS-1$
		TransientValueReference_c[] tv = TransientValueReference_c.TransientValueReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, tv.length);
		assertEquals(tv[0].getVar_id(), t[0].getVar_id());
        assertEquals(tv[1].getVar_id(), t[0].getVar_id());
        assertEquals(tv[2].getVar_id(), t[0].getVar_id());
		BinaryOperation_c[] bin = BinaryOperation_c.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0], "<", tv[1].getValue_id(), lin[1].getValue_id());//$NON-NLS-1$
		WhileStmt_c[] whl = WhileStmt_c.WhileStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, whl.length);
		assertEquals(whl[0].getStatement_id(), st[1].getStatement_id());
		assertEquals(whl[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(whl[0].getValue_id(), bin[0].getValue_id());
	}
	public void testSimpleWhile2() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("i = true;\n while (i)\n z = \"test\"; \n i = false;\n end while;z = 2.2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c act = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(act);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(act.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(act.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(act.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(5, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[3].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[4].getBlock_id(), blk[0].getBlock_id());
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, t.length);
		assertEquals("i", t[0].getName());//$NON-NLS-1$
		assertEquals("z", t[1].getName());//$NON-NLS-1$
		assertEquals("z", t[2].getName());//$NON-NLS-1$
		assertEquals(t[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(t[1].getBlock_id(), blk[1].getBlock_id());
		assertEquals(t[2].getBlock_id(), blk[0].getBlock_id());
		LiteralBoolean_c[] lbool = LiteralBoolean_c.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, lbool.length);
		assertEquals("TRUE", lbool[0].getValue());//$NON-NLS-1$
		assertEquals("FALSE", lbool[1].getValue());//$NON-NLS-1$
		TransientValueReference_c[] tv = TransientValueReference_c.TransientValueReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(5, tv.length);
		assertEquals(tv[0].getVar_id(), t[0].getVar_id());
        assertEquals(tv[1].getVar_id(), t[0].getVar_id());
        assertEquals(tv[2].getVar_id(), t[1].getVar_id());
        assertEquals(tv[3].getVar_id(), t[0].getVar_id());
        assertEquals(tv[4].getVar_id(), t[2].getVar_id());
		WhileStmt_c[] whl = WhileStmt_c.WhileStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, whl.length);
		assertEquals(whl[0].getStatement_id(), st[1].getStatement_id());
		assertEquals(whl[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(whl[0].getValue_id(), tv[1].getValue_id());
	}
	public void testSimpleWhile3() throws RecognitionException, TokenStreamException {
		class Object_test1_c implements ClassQueryInterface_c {
			public boolean evaluate(Object inst) {
				ModelClass_c selected = (ModelClass_c)inst;
				return selected.getKey_lett().equals("D_D");//$NON-NLS-1$
			}
		}
		ModelClass_c testObj = ModelClass_c.ModelClassInstance(OalParserTest_Generics.modelRoot, new Object_test1_c());
		if (testObj == null) {
			testObj = new ModelClass_c(OalParserTest_Generics.modelRoot);
			testObj.setKey_lett("D_D");//$NON-NLS-1$
		}
		String x = OalParserTest_Generics.parseAction("i = 4;\n while (i == 4)\n create object instance d1 of D_D; \n i = 11;\n end while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c act = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(act);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(act.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(act.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(act.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[3].getBlock_id(), blk[1].getBlock_id());
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, t.length);
		assertEquals("i", t[0].getName());//$NON-NLS-1$
		assertEquals("d1", t[1].getName());//$NON-NLS-1$
		assertEquals(t[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(t[1].getBlock_id(), blk[1].getBlock_id());
		InstanceHandle_c[] iv = InstanceHandle_c.InstanceHandleInstances(OalParserTest_Generics.modelRoot);
        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(iv[0]);
		assertEquals("D_D", mc.getKey_lett());//$NON-NLS-1$
		LiteralInteger_c[] lin = LiteralInteger_c.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, lin.length);
		assertEquals("4", lin[0].getValue());//$NON-NLS-1$
		assertEquals("4", lin[1].getValue());//$NON-NLS-1$
		assertEquals("11", lin[2].getValue());//$NON-NLS-1$
		TransientValueReference_c[] tv = TransientValueReference_c.TransientValueReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, tv.length);
		assertEquals(tv[0].getVar_id(), t[0].getVar_id());
        assertEquals(tv[1].getVar_id(), t[0].getVar_id());
        assertEquals(tv[2].getVar_id(), t[0].getVar_id());
		BinaryOperation_c[] bin = BinaryOperation_c.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0], "==", tv[1].getValue_id(), lin[1].getValue_id());//$NON-NLS-1$
		WhileStmt_c[] whl = WhileStmt_c.WhileStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, whl.length);
		assertEquals(whl[0].getStatement_id(), st[1].getStatement_id());
		assertEquals(whl[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(whl[0].getValue_id(), bin[0].getValue_id());
	}
	private void validateForStmt( ForStmt_c x, UUID stmt_id, boolean isImplicit, UUID block_id,
		String kl, UUID set_id, UUID iter_id )throws RecognitionException, TokenStreamException {
		assertEquals(stmt_id, x.getStatement_id());
		assertEquals(isImplicit, x.getIs_implicit());
		assertEquals(block_id, x.getBlock_id());
        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR670(x);
		assertEquals(kl, mc.getKey_lett());
		assertEquals(set_id, x.getSet_var_id());
		assertEquals(iter_id, x.getLoop_var_id());
		InstanceSet_c[] irs_val = InstanceSet_c.InstanceSetInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, irs_val.length);
		assertEquals(irs_val[0].getVar_id(), set_id);
	}

	public void testForSimpleImplicit() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;  for each d in d_set a = 1; end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, var.length);
		assertEquals("d_set", var[0].getName());//$NON-NLS-1$
		assertEquals("d", var[1].getName());//$NON-NLS-1$
		assertEquals("a", var[2].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[1].getBlock_id());
		ForStmt_c[] for_stmt = ForStmt_c.ForStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, for_stmt.length);
		validateForStmt( for_stmt[0],st[1].getStatement_id(), true, blk[1].getBlock_id(),
			"D_D",var[0].getVar_id(),var[1].getVar_id() );//$NON-NLS-1$
	}
	public void testForSimpleImplicitWithReference() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;  for each d in d_set x = empty d; end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, var.length);
		assertEquals("d_set", var[0].getName());//$NON-NLS-1$
		assertEquals("d", var[1].getName());//$NON-NLS-1$
		assertEquals("x", var[2].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[1].getBlock_id());
		ForStmt_c[] for_stmt = ForStmt_c.ForStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, for_stmt.length);
		validateForStmt( for_stmt[0],st[1].getStatement_id(), true, blk[1].getBlock_id(),
			"D_D",var[0].getVar_id(),var[1].getVar_id() );//$NON-NLS-1$
	}
	public void testForSimple() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select any d from instances of D_D; select many d_set from instances of D_D;  for each d in d_set a = 1; end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[3].getBlock_id(), blk[1].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, var.length);
		assertEquals("d", var[0].getName());//$NON-NLS-1$
		assertEquals("d_set", var[1].getName());//$NON-NLS-1$
		assertEquals("a", var[2].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[1].getBlock_id());
		ForStmt_c[] for_stmt = ForStmt_c.ForStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, for_stmt.length);
		validateForStmt( for_stmt[0],st[2].getStatement_id(), false, blk[1].getBlock_id(),
			"D_D",var[1].getVar_id(),var[0].getVar_id() );//$NON-NLS-1$
	}
	public void testForSimpleWithReference() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select any d from instances of D_D; select many d_set from instances of D_D;  for each d in d_set x = empty d; end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[3].getBlock_id(), blk[1].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, var.length);
		assertEquals("d", var[0].getName());//$NON-NLS-1$
		assertEquals("d_set", var[1].getName());//$NON-NLS-1$
		assertEquals("x", var[2].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[1].getBlock_id());
		ForStmt_c[] for_stmt = ForStmt_c.ForStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, for_stmt.length);
		validateForStmt( for_stmt[0],st[2].getStatement_id(), false, blk[1].getBlock_id(),
			"D_D",var[1].getVar_id(),var[0].getVar_id() );//$NON-NLS-1$
	}
	public void test2ForSimpleWithReference() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select any d from instances of D_D; select many d_set from instances of D_D;  for each d in d_set x = empty d; end for; for each d in d_set x = empty d; end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(6, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[3].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[4].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[5].getBlock_id(), blk[2].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, var.length);
		assertEquals("d", var[0].getName());//$NON-NLS-1$
		assertEquals("d_set", var[1].getName());//$NON-NLS-1$
		assertEquals("x", var[2].getName());//$NON-NLS-1$
		assertEquals("x", var[3].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(var[3].getBlock_id(), blk[2].getBlock_id());
		ForStmt_c[] for_stmt = ForStmt_c.ForStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, for_stmt.length);
		validateForStmt( for_stmt[0],st[2].getStatement_id(), false, blk[1].getBlock_id(),
			"D_D",var[1].getVar_id(),var[0].getVar_id() );//$NON-NLS-1$
		validateForStmt( for_stmt[1],st[4].getStatement_id(), false, blk[2].getBlock_id(),
			"D_D",var[1].getVar_id(),var[0].getVar_id() );//$NON-NLS-1$
	}
	public void test2ForSimpleImplicitWithReference() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;  for each d in d_set x = empty d; end for; for each d in d_set x = empty d; end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(5, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[3].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[4].getBlock_id(), blk[2].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, var.length);
		assertEquals("d_set", var[0].getName());//$NON-NLS-1$
		assertEquals("d", var[1].getName());//$NON-NLS-1$
		assertEquals("x", var[2].getName());//$NON-NLS-1$
		assertEquals("x", var[3].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(var[3].getBlock_id(), blk[2].getBlock_id());
		ForStmt_c[] for_stmt = ForStmt_c.ForStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, for_stmt.length);
		validateForStmt( for_stmt[0],st[1].getStatement_id(), true, blk[1].getBlock_id(),
			"D_D",var[0].getVar_id(),var[1].getVar_id() );//$NON-NLS-1$
		validateForStmt( for_stmt[1],st[3].getStatement_id(), false, blk[2].getBlock_id(),
			"D_D",var[0].getVar_id(),var[1].getVar_id() );//$NON-NLS-1$
	}
	public void testForBreak() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;  for each d in d_set a = 1; break; end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[3].getBlock_id(), blk[1].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, var.length);
		assertEquals("d_set", var[0].getName());//$NON-NLS-1$
		assertEquals("d", var[1].getName());//$NON-NLS-1$
		assertEquals("a", var[2].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[1].getBlock_id());
		ForStmt_c[] for_stmt = ForStmt_c.ForStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, for_stmt.length);
		validateForStmt( for_stmt[0],st[1].getStatement_id(), true, blk[1].getBlock_id(),
			"D_D",var[0].getVar_id(),var[1].getVar_id() );//$NON-NLS-1$
		Break_c[] break_stmt = Break_c.BreakInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, break_stmt.length);
		assertEquals(st[3].getStatement_id(), break_stmt[0].getStatement_id());
	}
	public void testForContinue() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;  for each d in d_set continue; a = 1; end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[3].getBlock_id(), blk[1].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, var.length);
		assertEquals("d_set", var[0].getName());//$NON-NLS-1$
		assertEquals("d", var[1].getName());//$NON-NLS-1$
		assertEquals("a", var[2].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[1].getBlock_id());
		ForStmt_c[] for_stmt = ForStmt_c.ForStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, for_stmt.length);
		validateForStmt( for_stmt[0],st[1].getStatement_id(), true, blk[1].getBlock_id(),
			"D_D",var[0].getVar_id(),var[1].getVar_id() );//$NON-NLS-1$
		Continue_c[] continue_stmt = Continue_c.ContinueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, continue_stmt.length);
		assertEquals(st[2].getStatement_id(), continue_stmt[0].getStatement_id());
	}
	public void validateNestedBlocks() throws RecognitionException, TokenStreamException {
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(7, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[3].getBlock_id(), blk[2].getBlock_id());
		assertEquals(st[4].getBlock_id(), blk[2].getBlock_id());
		assertEquals(st[5].getBlock_id(), blk[1].getBlock_id());
		assertEquals(st[6].getBlock_id(), blk[1].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, var.length);
		assertEquals("d_set", var[0].getName());//$NON-NLS-1$
		assertEquals("d", var[1].getName());//$NON-NLS-1$
		assertEquals("a", var[2].getName());//$NON-NLS-1$
		assertEquals("b", var[3].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[0].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[2].getBlock_id());
		assertEquals(var[3].getBlock_id(), blk[1].getBlock_id());
		ForStmt_c[] for_stmt = ForStmt_c.ForStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, for_stmt.length);
		validateForStmt( for_stmt[0],st[1].getStatement_id(), true, blk[1].getBlock_id(),
			"D_D",var[0].getVar_id(),var[1].getVar_id() );//$NON-NLS-1$
		WhileStmt_c[] whl = WhileStmt_c.WhileStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, whl.length);
		assertEquals(whl[0].getStatement_id(), st[2].getStatement_id());
		assertEquals(whl[0].getBlock_id(), blk[2].getBlock_id());
	}
	public void testNestedBreak() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;\nfor each d in d_set\n  while ( true )\n   a = 1;\n   break;\n  end while;\n  b = 2;\n  break;\n end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		validateNestedBlocks();
		Break_c[] break_stmt = Break_c.BreakInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, break_stmt.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(st[4].getStatement_id(), break_stmt[0].getStatement_id());
		assertEquals(st[6].getStatement_id(), break_stmt[1].getStatement_id());
	}
	public void testNestedContinue() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;\nfor each d in d_set\n  while ( true )\n   a = 1;\n   continue;\n  end while;\n  b = 2;\n  continue;\n end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		validateNestedBlocks();
		Continue_c[] continue_stmt = Continue_c.ContinueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, continue_stmt.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(st[4].getStatement_id(), continue_stmt[0].getStatement_id());
		assertEquals(st[6].getStatement_id(), continue_stmt[1].getStatement_id());
	}
	public void testIfOnly() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( false ) a = 1; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(st[1].getBlock_id(), blk[1].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, var.length);
		assertEquals("a", var[0].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[1].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, val.length);
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
	}
	public void testIfOnlyEmpty() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( false ) end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
		assertEquals(st[0].getBlock_id(), blk[0].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, var.length);
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, val.length);
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
	}
	public void testIfElse() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( false ) a = 1; else b = 2; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		assertEquals(blk[1].getBlock_id(), st[1].getBlock_id());
		assertEquals(blk[2].getBlock_id(), st[2].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[3].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, var.length);
		assertEquals("a", var[0].getName());//$NON-NLS-1$
		assertEquals("b", var[1].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[2].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(5, val.length);
		assertEquals(val[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(val[1].getBlock_id(), blk[1].getBlock_id());
        assertEquals(val[2].getBlock_id(), blk[1].getBlock_id());
        assertEquals(val[3].getBlock_id(), blk[2].getBlock_id());
        assertEquals(val[4].getBlock_id(), blk[2].getBlock_id());
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
		ElseStmt_c[] else_stmt = ElseStmt_c.ElseStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, else_stmt.length);
		assertEquals(st[3].getStatement_id(), else_stmt[0].getStatement_id());
		assertEquals(else_stmt[0].getBlock_id(), blk[2].getBlock_id());
		assertEquals(else_stmt[0].getIf_statement_id(), st[0].getStatement_id());
	}
	public void testIfEmptyElse() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( false ) else b = 2; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		assertEquals(blk[2].getBlock_id(), st[1].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[2].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, var.length);
		assertEquals("b", var[0].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[2].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, val.length);
		assertEquals(val[0].getBlock_id(), blk[0].getBlock_id());
        assertEquals(val[1].getBlock_id(), blk[2].getBlock_id());
		assertEquals(val[2].getBlock_id(), blk[2].getBlock_id());
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
		ElseStmt_c[] else_stmt = ElseStmt_c.ElseStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, else_stmt.length);
		assertEquals(st[2].getStatement_id(), else_stmt[0].getStatement_id());
		assertEquals(else_stmt[0].getBlock_id(), blk[2].getBlock_id());
		assertEquals(else_stmt[0].getIf_statement_id(), st[0].getStatement_id());
	}
	public void testIfElseEmpty() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( false ) a = 1; else end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		assertEquals(blk[1].getBlock_id(), st[1].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[2].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, var.length);
		assertEquals("a", var[0].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[1].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, val.length);
		assertEquals(val[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(val[1].getBlock_id(), blk[1].getBlock_id());
        assertEquals(val[2].getBlock_id(), blk[1].getBlock_id());
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
		ElseStmt_c[] else_stmt = ElseStmt_c.ElseStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, else_stmt.length);
		assertEquals(st[2].getStatement_id(), else_stmt[0].getStatement_id());
		assertEquals(else_stmt[0].getBlock_id(), blk[2].getBlock_id());
		assertEquals(else_stmt[0].getIf_statement_id(), st[0].getStatement_id());
	}
	public void testIfElif() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; elif ( false ) b = 2; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		assertEquals(blk[1].getBlock_id(), st[1].getBlock_id());
		assertEquals(blk[2].getBlock_id(), st[2].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[3].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, var.length);
		assertEquals("a", var[0].getName());//$NON-NLS-1$
		assertEquals("b", var[1].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[2].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(6, val.length);
		assertEquals(val[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(val[1].getBlock_id(), blk[1].getBlock_id());
		assertEquals(val[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(val[3].getBlock_id(), blk[0].getBlock_id());
        assertEquals(val[4].getBlock_id(), blk[2].getBlock_id());
        assertEquals(val[5].getBlock_id(), blk[2].getBlock_id());
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
		ElseifStmt_c[] elif_stmt = ElseifStmt_c.ElseifStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, elif_stmt.length);
		assertEquals(st[3].getStatement_id(), elif_stmt[0].getStatement_id());
		assertEquals(elif_stmt[0].getBlock_id(), blk[2].getBlock_id());
		assertEquals(st[0].getStatement_id(), elif_stmt[0].getIf_statement_id());
	}
	public void testIfEmptyElif() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) elif ( false ) b = 2; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		assertEquals(blk[2].getBlock_id(), st[1].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[2].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, var.length);
		assertEquals("b", var[0].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[2].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, val.length);
		assertEquals(val[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(val[1].getBlock_id(), blk[0].getBlock_id());
        assertEquals(val[2].getBlock_id(), blk[2].getBlock_id());
		assertEquals(val[3].getBlock_id(), blk[2].getBlock_id());
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
		ElseifStmt_c[] elif_stmt = ElseifStmt_c.ElseifStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, elif_stmt.length);
		assertEquals(st[2].getStatement_id(), elif_stmt[0].getStatement_id());
		assertEquals(elif_stmt[0].getBlock_id(), blk[2].getBlock_id());
		assertEquals(st[0].getStatement_id(), elif_stmt[0].getIf_statement_id());
	}
	public void testIfElifEmpty() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; elif ( false ) end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		assertEquals(blk[1].getBlock_id(), st[1].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[2].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, var.length);
		assertEquals("a", var[0].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[1].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, val.length);
		assertEquals(val[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(val[1].getBlock_id(), blk[1].getBlock_id());
        assertEquals(val[2].getBlock_id(), blk[1].getBlock_id());
        assertEquals(val[3].getBlock_id(), blk[0].getBlock_id());
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
		ElseifStmt_c[] elif_stmt = ElseifStmt_c.ElseifStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, elif_stmt.length);
		assertEquals(st[2].getStatement_id(), elif_stmt[0].getStatement_id());
		assertEquals(elif_stmt[0].getBlock_id(), blk[2].getBlock_id());
		assertEquals(st[0].getStatement_id(), elif_stmt[0].getIf_statement_id());
	}
	public void testIf2Elif() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; elif ( false ) b = 2; elif ( false ) c = 3; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[3].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(6, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		assertEquals(blk[1].getBlock_id(), st[1].getBlock_id());
		assertEquals(blk[2].getBlock_id(), st[2].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[3].getBlock_id());
		assertEquals(blk[3].getBlock_id(), st[4].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[5].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, var.length);
		assertEquals("a", var[0].getName());//$NON-NLS-1$
		assertEquals("b", var[1].getName());//$NON-NLS-1$
		assertEquals("c", var[2].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[2].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[3].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(9, val.length);
		assertEquals(val[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(val[1].getBlock_id(), blk[1].getBlock_id());
		assertEquals(val[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(val[3].getBlock_id(), blk[0].getBlock_id());
		assertEquals(val[4].getBlock_id(), blk[2].getBlock_id());
        assertEquals(val[5].getBlock_id(), blk[2].getBlock_id());
        assertEquals(val[6].getBlock_id(), blk[0].getBlock_id());
        assertEquals(val[7].getBlock_id(), blk[3].getBlock_id());
		assertEquals(val[8].getBlock_id(), blk[3].getBlock_id());
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
		ElseifStmt_c[] elif_stmt = ElseifStmt_c.ElseifStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, elif_stmt.length);
		assertEquals(st[3].getStatement_id(), elif_stmt[0].getStatement_id());
		assertEquals(blk[2].getBlock_id(), elif_stmt[0].getBlock_id());
		assertEquals(st[0].getStatement_id(), elif_stmt[0].getIf_statement_id());
		assertEquals(st[5].getStatement_id(), elif_stmt[1].getStatement_id());
		assertEquals(blk[3].getBlock_id(), elif_stmt[1].getBlock_id());
		assertEquals(st[0].getStatement_id(), elif_stmt[1].getIf_statement_id());
	}
	public void testIfElifElse() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; elif ( false ) b = 2; else c = 3; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[3].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(6, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		assertEquals(blk[1].getBlock_id(), st[1].getBlock_id());
		assertEquals(blk[2].getBlock_id(), st[2].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[3].getBlock_id());
		assertEquals(blk[3].getBlock_id(), st[4].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[5].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, var.length);
		assertEquals("a", var[0].getName());//$NON-NLS-1$
		assertEquals("b", var[1].getName());//$NON-NLS-1$
		assertEquals("c", var[2].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[2].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[3].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(8, val.length);
		assertEquals(val[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(val[1].getBlock_id(), blk[1].getBlock_id());
		assertEquals(val[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(val[3].getBlock_id(), blk[0].getBlock_id());
        assertEquals(val[4].getBlock_id(), blk[2].getBlock_id());
        assertEquals(val[5].getBlock_id(), blk[2].getBlock_id());
        assertEquals(val[6].getBlock_id(), blk[3].getBlock_id());
		assertEquals(val[7].getBlock_id(), blk[3].getBlock_id());
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
		ElseifStmt_c[] elif_stmt = ElseifStmt_c.ElseifStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, elif_stmt.length);
		assertEquals(st[3].getStatement_id(), elif_stmt[0].getStatement_id());
		assertEquals(blk[2].getBlock_id(), elif_stmt[0].getBlock_id());
		assertEquals(st[0].getStatement_id(), elif_stmt[0].getIf_statement_id());
		ElseStmt_c[] else_stmt = ElseStmt_c.ElseStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, else_stmt.length);
		assertEquals(st[5].getStatement_id(), else_stmt[0].getStatement_id());
		assertEquals(blk[3].getBlock_id(), else_stmt[0].getBlock_id());
		assertEquals(st[0].getStatement_id(), else_stmt[0].getIf_statement_id());
	}
	public void testIf2ElifElse() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; elif ( false ) b = 2; elif ( false ) c = 3; else  d = 4; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Body_c action = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(OalParserTest_Generics.m_testFunc[OalParserTest_Generics.TEST_VOID_NO_PARM]));
		assertNotNull(action);
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(5, blk.length);
		assertEquals(action.getAction_id(), blk[0].getParsed_action_id());
		assertEquals(action.getParsed_block_id(), blk[0].getBlock_id());
		assertEquals(action.getAction_id(), blk[1].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[2].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[3].getParsed_action_id());
		assertEquals(action.getAction_id(), blk[4].getParsed_action_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(8, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		assertEquals(blk[1].getBlock_id(), st[1].getBlock_id());
		assertEquals(blk[2].getBlock_id(), st[2].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[3].getBlock_id());
		assertEquals(blk[3].getBlock_id(), st[4].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[5].getBlock_id());
		assertEquals(blk[4].getBlock_id(), st[6].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[7].getBlock_id());
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, var.length);
		assertEquals("a", var[0].getName());//$NON-NLS-1$
		assertEquals("b", var[1].getName());//$NON-NLS-1$
		assertEquals("c", var[2].getName());//$NON-NLS-1$
		assertEquals("d", var[3].getName());//$NON-NLS-1$
		assertEquals(var[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(var[1].getBlock_id(), blk[2].getBlock_id());
		assertEquals(var[2].getBlock_id(), blk[3].getBlock_id());
		assertEquals(var[3].getBlock_id(), blk[4].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(11, val.length);
		assertEquals(val[0].getBlock_id(), blk[0].getBlock_id());
		assertEquals(val[1].getBlock_id(), blk[1].getBlock_id());
		assertEquals(val[2].getBlock_id(), blk[1].getBlock_id());
		assertEquals(val[3].getBlock_id(), blk[0].getBlock_id());
		assertEquals(val[4].getBlock_id(), blk[2].getBlock_id());
		assertEquals(val[5].getBlock_id(), blk[2].getBlock_id());
        assertEquals(val[6].getBlock_id(), blk[0].getBlock_id());
        assertEquals(val[7].getBlock_id(), blk[3].getBlock_id());
        assertEquals(val[8].getBlock_id(), blk[3].getBlock_id());
        assertEquals(val[9].getBlock_id(), blk[4].getBlock_id());
        assertEquals(val[10].getBlock_id(), blk[4].getBlock_id());
		IfStmt_c[] if_stmt = IfStmt_c.IfStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, if_stmt.length);
		assertEquals(st[0].getStatement_id(), if_stmt[0].getStatement_id());
		assertEquals(if_stmt[0].getBlock_id(), blk[1].getBlock_id());
		assertEquals(if_stmt[0].getValue_id(), val[0].getValue_id());
		ElseifStmt_c[] elif_stmt = ElseifStmt_c.ElseifStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, elif_stmt.length);
		assertEquals(st[3].getStatement_id(), elif_stmt[0].getStatement_id());
		assertEquals(blk[2].getBlock_id(), elif_stmt[0].getBlock_id());
		assertEquals(st[0].getStatement_id(), elif_stmt[0].getIf_statement_id());
		assertEquals(st[5].getStatement_id(), elif_stmt[1].getStatement_id());
		assertEquals(blk[3].getBlock_id(), elif_stmt[1].getBlock_id());
		assertEquals(st[0].getStatement_id(), elif_stmt[1].getIf_statement_id());
		ElseStmt_c[] else_stmt = ElseStmt_c.ElseStmtInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, else_stmt.length);
		assertEquals(st[7].getStatement_id(), else_stmt[0].getStatement_id());
		assertEquals(blk[4].getBlock_id(), else_stmt[0].getBlock_id());
		assertEquals(st[0].getStatement_id(), else_stmt[0].getIf_statement_id());
	}
	public void testForBadVarType() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;  x = 1; for each x in d_set end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":1:74-76: Variable ->x<- does not exist in scope as an object instance variable", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:78: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 2, 2);
	}
	public void testForWrongVarType() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select any h from instances of D_H; select many d_set from instances of D_D;  for each h in d_set end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":1:103-105: Variable ->h<- already exists as a different type", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:107: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 2, 0);
	}
	public void testForNotSetVar() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("x = 1; for each y in x end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:28-30: Variable ->x<- does not exist in scope as an object instance set variable", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:32: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 1, 2);
	}
	public void testForNoSetVar() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("for each y in x end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals(":1:15-15: Variable ->x<- used in context where it must already exist", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 1, 0);
	}
	public void testForWithEndWhile() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D; for each d in d_set end while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:66: expecting \"for\", found 'while'", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 2, 0);
	}
	public void testForWithEndIf() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D; for each d in d_set end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:66: expecting \"for\", found 'if'", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 2, 0);
	}
	public void testForNoEnd() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D; for each d in d_set x = 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals("line 1:68: expecting \"end\", found 'null'", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:68: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 3, 2);
	}
	public void testBreakBeforeWhileLoop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("break; while (true) end while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals(":1:1-5: Break statement can only be used in WHILE and FOR EACH block", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 0, 0);
	}
	public void testBreakAfterWhileLoop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("while (true) end while; break;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:25-29: Break statement can only be used in WHILE and FOR EACH block", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:31: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 1, 1);
	}
	public void testBreakBeforeForLoop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;  break; for each d in d_set end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals(":1:43-47: Break statement can only be used in WHILE and FOR EACH block", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 1, 0);
	}
	public void testBreakAfterForLoop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;  for each d in d_set end for; break;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:72-76: Break statement can only be used in WHILE and FOR EACH block", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:78: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 2, 0);
	}
	public void testBreakWithNoLoop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("break;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:1-5: Break statement can only be used in WHILE and FOR EACH block", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:7: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 0, 0);
	}
	public void testContinueBeforeWhileLoop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("continue; while (true) end while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals(":1:1-8: Continue statement can only be used in WHILE and FOR EACH block", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 0, 0);
	}
	public void testContinueAfterWhileLoop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("while (true) end while; continue;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:25-32: Continue statement can only be used in WHILE and FOR EACH block", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:34: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 1, 1);
	}
	public void testContinueBeforeForLoop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;  continue; for each d in d_set end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals(":1:43-50: Continue statement can only be used in WHILE and FOR EACH block", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 1, 0);
	}
	public void testContinueAfterForLoop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("select many d_set from instances of D_D;  for each d in d_set end for; continue;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:72-79: Continue statement can only be used in WHILE and FOR EACH block", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:81: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 2, 0);
	}
	public void testContinueWithNoLoop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("continue;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:1-8: Continue statement can only be used in WHILE and FOR EACH block", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:10: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 0, 0);
	}
	public void testIfNotBoolean() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( 1 ) a = 1; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:21-22: If expression data type is not boolean", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:24: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 0, 0);
	}
	public void testIfWithEndFor() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:24: expecting \"if\", found 'for'", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 2, 3);
	}
	public void testIfWithEndWhile() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; end while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:24: expecting \"if\", found 'while'", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 2, 3);
	}
	public void testIfWithNoEnd() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals("line 1:19: unexpected token: null", lines[0]);//$NON-NLS-1$
		assertEquals("line 1:19: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 2, 3);
	}
	public void testIfElseWithEndFor() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; else b = 2; end for;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:36: expecting \"if\", found 'for'", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(3, 3, 5);
	}
	public void testIfElseWithEndWhile() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; else b = 2; end while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:36: expecting \"if\", found 'while'", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(3, 3, 5);
	}
	public void testIfElseWithNoEnd() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; else b = 2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals("line 1:31: expecting \"end\", found 'null'", lines[0]);//$NON-NLS-1$
		assertEquals("line 1:31: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(3, 3, 5);
	}
	public void testElifNotBoolean() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; elif ( \"true\" ) b = 2; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals(":1:47-48: Elif expression data type is not boolean", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(3, 3, 5);
	}
	public void testElifOutsideIf() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; end if; elif ( true ) b = 2; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:28: expecting EOF, found 'elif'", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 2, 3);
	}
	public void testElseOutsideIf() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; end if; else b = 2; end if;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:28: expecting EOF, found 'else'", lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(2, 2, 3);
	}
	public void testIfElifWithNoEnd() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; elif (false) b = 2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals("line 1:39: unexpected token: null", lines[0]);//$NON-NLS-1$
		assertEquals("line 1:39: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(3, 4, 6);
	}
	public void testIfElifElseWithNoEnd() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; elif ( false ) b = 2; else c = 3;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals("line 1:53: expecting \"end\", found 'null'", lines[0]);//$NON-NLS-1$
		assertEquals("line 1:53: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(4, 5, 8);
	}
	public void testIf2ElifWithNoEnd() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; elif ( false ) b = 2; elif ( false ) c = 3;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals("line 1:63: unexpected token: null", lines[0]);//$NON-NLS-1$
		assertEquals("line 1:63: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(4, 6, 9);
	}
	public void testIf2ElifElseWithNoEnd() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("if ( true ) a = 1; elif ( false ) b = 2; elif ( false ) c = 3; else  d = 4;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals("line 1:76: expecting \"end\", found 'null'", lines[0]);//$NON-NLS-1$
		assertEquals("line 1:76: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(5, 7, 11);
	}
	public void testWhileNonBooleanVar() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("x = 1; while (x)\n end while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":2:6-10: While expression data type is not boolean", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:12: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 1, 2);
	}
	public void testWhileNonBooleanVar2() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("x = 1; while (x)\n x = 0; end while;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":2:13-17: While expression data type is not boolean", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:19: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, 1, 2);
	}
	public void testControlStop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("control stop;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		Control_c[] ctl = Control_c.ControlInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, ctl.length);
		assertEquals(st[0].getStatement_id(), ctl[0].getStatement_id());
	}
    public void testCleanupBeforeContinuing() throws RecognitionException,
            TokenStreamException {
        // This is not really a test. It is just a container for some cleanup
        // functions that need to run before continuing.
        for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
            OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
                    OalParserTest_Generics.TEST_VOID_NO_PARM);
            OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
                    OalParserTest_Generics.TEST_INT_NO_PARM);
            OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
                    OalParserTest_Generics.TEST_REAL_NO_PARM);
        }
    }
    public void testReturn() throws RecognitionException, TokenStreamException {
		for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
			String x = OalParserTest_Generics.parseAction("return;", i, OalParserTest_Generics.TEST_VOID_NO_PARM);//$NON-NLS-1$
			assertEquals("", x); //$NON-NLS-1$
			Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, blk.length);
			Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, st.length);
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, ret.length);
			assertEquals(st[0].getStatement_id(), ret[0].getStatement_id());
			OalParserTest_Generics.clearActionData(i, OalParserTest_Generics.TEST_VOID_NO_PARM);
		}
		for (int i = OalParserTest_Generics.STATE_ISM_NONE; i <= OalParserTest_Generics.STATE_ISM_POLYTWO; ++i) {
			String x = OalParserTest_Generics.parseAction("return;", OalParserTest_Generics.ACTIVITY_TYPE_STATE, i);//$NON-NLS-1$
			assertEquals("", x);
			Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, blk.length);
			Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, st.length);
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, ret.length);
			assertEquals(st[0].getStatement_id(), ret[0].getStatement_id());
			OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_STATE, i);
		}
		for (int i = OalParserTest_Generics.TRANS_ISM_NONE; i <= OalParserTest_Generics.TRANS_CSM_THREE; ++i) {
			String x = OalParserTest_Generics.parseAction("return;", OalParserTest_Generics.ACTIVITY_TYPE_TRANSITION, i);//$NON-NLS-1$
			assertEquals("", x);
			Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, blk.length);
			Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, st.length);
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, ret.length);
			assertEquals(st[0].getStatement_id(), ret[0].getStatement_id());
			OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_TRANSITION, i);
		}
	}
	public void testReturnSFromV() throws RecognitionException, TokenStreamException {
		String err_msgs[] =
			{
				":1:8-13: Return value not required by function",//$NON-NLS-1$
				":1:8-13: Return value not required by bridge",//$NON-NLS-1$
				":1:8-13: Return value not required by class operation",//$NON-NLS-1$
				":1:8-13: Return value not required by operation" };//$NON-NLS-1$
		for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
			String x = OalParserTest_Generics.parseAction("return \"test\";", i, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
			String lines[] = x.split("\n");//$NON-NLS-1$
			assertEquals(err_msgs[i], lines[0]); //$NON-NLS-1$
			assertEquals("line 1:15: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
			Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, blk.length);
			Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
			assertEquals(0, st.length);
			ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
			assertEquals(0, ret.length);
			Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
			assertEquals(0, val.length);
			OalParserTest_Generics.clearActionData(i, OalParserTest_Generics.TEST_VOID_NO_PARM);
		}
	}
	public void testReturnIFromS() throws RecognitionException, TokenStreamException {
		String err_msgs[] =
			{
				":1:8-8: Invalid data type returned for function",//$NON-NLS-1$
				":1:8-8: Invalid data type returned for bridge",//$NON-NLS-1$
				":1:8-8: Invalid data type returned for class operation",//$NON-NLS-1$
				":1:8-8: Invalid data type returned for operation" };//$NON-NLS-1$
		for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
			String x = OalParserTest_Generics.parseAction("return 7;", i, OalParserTest_Generics.TEST_STRING_NO_PARM); //$NON-NLS-1$
			String lines[] = x.split("\n");//$NON-NLS-1$
			assertEquals(err_msgs[i], lines[0]); //$NON-NLS-1$
			assertEquals("line 1:10: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
			Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, blk.length);
			Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
			assertEquals(0, st.length);
			ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
			assertEquals(0, ret.length);
			Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
			assertEquals(0, val.length);
			OalParserTest_Generics.clearActionData(i, OalParserTest_Generics.TEST_STRING_NO_PARM);
		}
	}
	public void testReturnVFromS() throws RecognitionException, TokenStreamException {
		String err_msgs[] =
			{
				":1:1-6: Return value required by function",//$NON-NLS-1$
				":1:1-6: Return value required by bridge",//$NON-NLS-1$
				":1:1-6: Return value required by class operation",//$NON-NLS-1$
				":1:1-6: Return value required by operation" };//$NON-NLS-1$
		for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
			String x = OalParserTest_Generics.parseAction("return ;", i, OalParserTest_Generics.TEST_STRING_NO_PARM); //$NON-NLS-1$
			String lines[] = x.split("\n");//$NON-NLS-1$
			assertEquals(err_msgs[i], lines[0]); //$NON-NLS-1$
			assertEquals("line 1:9: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
			Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, blk.length);
			Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
			assertEquals(0, st.length);
			ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
			assertEquals(0, ret.length);
			OalParserTest_Generics.clearActionData(i, OalParserTest_Generics.TEST_STRING_NO_PARM);
		}
	}
	public void testNoReturnFromS() throws RecognitionException, TokenStreamException {
		String err_msgs[] =
			{
				":1:1-1: Return value required by function",//$NON-NLS-1$
				"",//$NON-NLS-1$
				":1:1-1: Return value required by class operation",//$NON-NLS-1$
				":1:1-1: Return value required by operation" };//$NON-NLS-1$
		for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
			String x = OalParserTest_Generics.parseAction("", i, OalParserTest_Generics.TEST_STRING_NO_PARM); //$NON-NLS-1$
			String lines[] = x.split("\n");//$NON-NLS-1$
			assertEquals(1, lines.length);
			assertEquals(err_msgs[i], lines[0]);
			Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, blk.length);
			Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
			assertEquals(0, st.length);
			ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
			assertEquals(0, ret.length);
			OalParserTest_Generics.clearActionData(i, OalParserTest_Generics.TEST_STRING_NO_PARM);
		}
	}
	public void testReturnIFromI() throws RecognitionException, TokenStreamException {
		for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
			String x = OalParserTest_Generics.parseAction("return 1;", i, OalParserTest_Generics.TEST_INT_NO_PARM); //$NON-NLS-1$
			assertEquals("", x); //$NON-NLS-1$
			Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, blk.length);
			LiteralInteger_c[] lin = LiteralInteger_c.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, lin.length);
			assertEquals("1", lin[0].getValue());//$NON-NLS-1$
			Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, st.length);
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, ret.length);
			assertEquals(st[0].getStatement_id(), ret[0].getStatement_id());
			assertEquals(lin[0].getValue_id(), ret[0].getValue_id());
			OalParserTest_Generics.clearActionData(i, OalParserTest_Generics.TEST_INT_NO_PARM);
		}
	}
	public void testReturnRFromR() throws RecognitionException, TokenStreamException {
		for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
			String x = OalParserTest_Generics.parseAction("return 1.1;", i, OalParserTest_Generics.TEST_REAL_NO_PARM); //$NON-NLS-1$
			assertEquals("", x); //$NON-NLS-1$
			Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, blk.length);
			Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, val.length);
			LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, lr.length);
			assertEquals("1.1", lr[0].getValue());//$NON-NLS-1$
			Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, st.length);
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, ret.length);
			assertEquals(st[0].getStatement_id(), ret[0].getStatement_id());
			assertEquals(lr[0].getValue_id(), ret[0].getValue_id());
			OalParserTest_Generics.clearActionData(i, OalParserTest_Generics.TEST_REAL_NO_PARM);
		}
	}
	public void testReturnIFromR() throws RecognitionException, TokenStreamException {
		for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
			String x = OalParserTest_Generics.parseAction("return 1;", i, OalParserTest_Generics.TEST_REAL_NO_PARM); //$NON-NLS-1$
			assertEquals("", x); //$NON-NLS-1$
			Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, blk.length);
			LiteralInteger_c[] lin = LiteralInteger_c.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, lin.length);
			assertEquals("1", lin[0].getValue());//$NON-NLS-1$
			Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, st.length);
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, val.length);
			ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, ret.length);
			assertEquals(st[0].getStatement_id(), ret[0].getStatement_id());
			assertEquals(lin[0].getValue_id(), ret[0].getValue_id());
			OalParserTest_Generics.clearActionData(i, OalParserTest_Generics.TEST_REAL_NO_PARM);
		}
	}
    public void testReturnRFromI() throws RecognitionException, TokenStreamException {
        for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
            String x = OalParserTest_Generics.parseAction("return 1.1;", i, OalParserTest_Generics.TEST_INT_NO_PARM); //$NON-NLS-1$
            assertEquals("", x); //$NON-NLS-1$
            Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
            assertEquals(1, blk.length);
            LiteralReal_c[] lreal = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
            assertEquals(1, lreal.length);
            assertEquals("1.1", lreal[0].getValue());//$NON-NLS-1$
            Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
            assertEquals(1, st.length);
            assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
            Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
            assertEquals(1, val.length);
            ReturnStmt_c[] ret = ReturnStmt_c.ReturnStmtInstances(OalParserTest_Generics.modelRoot);
            assertEquals(1, ret.length);
            assertEquals(st[0].getStatement_id(), ret[0].getStatement_id());
            assertEquals(lreal[0].getValue_id(), ret[0].getValue_id());
            OalParserTest_Generics.clearActionData(i, OalParserTest_Generics.TEST_INT_NO_PARM);
        }
    }

}
