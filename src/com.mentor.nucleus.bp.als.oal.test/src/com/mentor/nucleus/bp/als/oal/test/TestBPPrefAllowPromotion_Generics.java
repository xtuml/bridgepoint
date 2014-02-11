//========================================================================
//
//File:      $RCSfile: TestBPPrefAllowPromotion_Generics.java,v $
//Version:   $Revision: 1.4 $
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

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BinaryOperation_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InstanceSet_c;
import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.LiteralBoolean_c;
import com.mentor.nucleus.bp.core.LiteralInteger_c;
import com.mentor.nucleus.bp.core.LiteralReal_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.ReturnStmt_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.TransientValueReference_c;
import com.mentor.nucleus.bp.core.TransientVar_c;
import com.mentor.nucleus.bp.core.UnaryOperation_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class TestBPPrefAllowPromotion_Generics extends TestCase {

    private TestAssign_Generics assignTest = new TestAssign_Generics();
    private TestExpr_Generics exprTest = new TestExpr_Generics();
    private TestAttribute_Generics attrTest = new TestAttribute_Generics();

    protected void setUp() throws Exception {
        super.setUp();

        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_INT_TO_REAL_PROMOTION,
                MessageDialogWithToggle.ALWAYS);
        store.setValue(BridgePointPreferencesStore.ALLOW_REAL_TO_INT_COERCION,
                MessageDialogWithToggle.NEVER);
    }

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

	public void AssignTypeV2Vtest(String stmts, UUID dt1_id, UUID dt2_id,
			int numTV, int numIRV, int numIRS) throws RecognitionException,
			TokenStreamException {
        assignTest.AssignTypeV2Vtest(stmts, dt1_id, dt2_id, numTV, numIRV, numIRS);
	}

    public void binaryOpTest(String p_action, UUID op1_dt, UUID bin_dt,
            UUID op2_dt, UUID result_dt, int bin_index, int numStatements)
            throws RecognitionException, TokenStreamException {
        exprTest.binaryOpTest(p_action, op1_dt, bin_dt, op2_dt, result_dt, bin_index, numStatements);
    }

	public void testAssignTypeBooleanV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"assign x = true; assign y = false; assign x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeIntV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"assign x = 1; assign y = 27; assign x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeRealV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"assign x = 1.3; assign y = 2.7; assign x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeBooleanV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"x = true; y = false; x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeIntV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		AssignTypeV2Vtest("x = 1; y = 27; x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeRealV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		AssignTypeV2Vtest("x = 1.3; y = 2.7; x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeIV2RV() throws RecognitionException,
			TokenStreamException {
		UUID id1 = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		UUID id2 = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		AssignTypeV2Vtest("x = 5.13; y = 99; x = y; ", id1, id2, 2, 0, 0); //$NON-NLS-1$
	}

	public void testTransValPop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("x = 1; y = x; z = y; z = x;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, t.length);
		assertEquals("x", t[0].getName()); //$NON-NLS-1$
		assertEquals("y", t[1].getName()); //$NON-NLS-1$
		assertEquals("z", t[2].getName()); //$NON-NLS-1$
		TransientVar_c[] tv = TransientVar_c.TransientVarInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, tv.length);
		UUID id = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		assertEquals(id, t[0].getDt_id());
		assertEquals(id, t[1].getDt_id());
		assertEquals(id, t[2].getDt_id());
		TransientValueReference_c[] val = TransientValueReference_c.TransientValueReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(7, val.length);
        assertEquals(val[0].getVar_id(), t[0].getVar_id());
        assertEquals(val[1].getVar_id(), t[1].getVar_id());
        assertEquals(val[2].getVar_id(), t[0].getVar_id());
        assertEquals(val[3].getVar_id(), t[2].getVar_id());
        assertEquals(val[4].getVar_id(), t[1].getVar_id());
        assertEquals(val[5].getVar_id(), t[2].getVar_id());
        assertEquals(val[6].getVar_id(), t[0].getVar_id());
		LiteralInteger_c[] lin = LiteralInteger_c.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lin.length);
		assertEquals("1", lin[0].getValue()); //$NON-NLS-1$
	}

	public void assignTypeMismatchTest(String stmts, UUID dt1, UUID dt2,
			int numTV, int numIRV, int numIRS) throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(stmts,
				OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
				OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n"); //$NON-NLS-1$
		assertEquals(
				":3:5-5: Variable ->x<- already exists as a different type", lines[0]); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c
				.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, t.length);
		assertEquals("x", t[0].getName()); //$NON-NLS-1$
		assertEquals("y", t[1].getName()); //$NON-NLS-1$
		TransientVar_c[] tv = TransientVar_c
				.TransientVarInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numTV, tv.length);
		if (numTV == 2) {
			assertEquals(dt1, t[0].getDt_id());
			assertEquals(dt2, t[1].getDt_id());
		}
		Instance_c[] iv = Instance_c.InstanceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numIRV, iv.length);
		InstanceSet_c[] isv = InstanceSet_c
				.InstanceSetInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numIRS, isv.length);
	}

    public void testAssignTypeMismatchRV2IV() throws RecognitionException,
            TokenStreamException {
        UUID id1 = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        UUID id2 = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        assignTypeMismatchTest("x = 1;\ny = 3.14;\nx = y;\n ", id2, id1, 2, 0, 0); //$NON-NLS-1$
    }

	public void testAssignTypeMismatchB2I() throws RecognitionException,
			TokenStreamException {
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$

		assignTypeMismatchTest(
				"x = 1;\ny = true;\nx = y;\n ", intId, boolId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchI2S() throws RecognitionException,
			TokenStreamException {
		UUID strId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "string");//93//$NON-NLS-1$
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//91		//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = \"test\";\ny = 2;\nx = y;\n ", strId, intId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchB2R() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//90//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//92	//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = 5.13;\ny = false;\nx = y;\n ", realId, boolId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchI2B() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//90//$NON-NLS-1$
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//91//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = false;\ny = 1;\nx = y;\n ", boolId, intId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchR2B() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//90//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//92//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = false;\ny = 1.1111;\nx = y;\n ", boolId, realId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignInt2UDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_int = 13;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal( 1, 2, 3 );
	}
	public void testAssignUDT2Int() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; x = 12; x = t.u_int;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal( 1, 3, 5 );
	}
	public void testAssignReal2UDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_real = 1.3;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal( 1, 2, 3 );
	}
	public void testAssignUDT2Real() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; x = 1.2; x = t.u_real;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal( 1, 3, 5 );
	}
 	public void testAssignInt2RealUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_real = 13;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal( 1, 2, 3 );
	}
    public void testAssignRealUDT2Int() throws RecognitionException, TokenStreamException {
        String act = "select any t from instances of D_TST; x = 12; x = t.u_real;"; //$NON-NLS-1$
        String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(2, lines.length);
        assertEquals(":1:53-58: Variable ->x<- already exists as a different type", lines[0]);
        assertEquals("line 1:60: expecting Semicolon, found 'null'", lines[1]);
        OalParserTest_Generics.validateBlkStmtVal( 1, 2, 4 );
    }
    public void testAssignReal2IntUDT() throws RecognitionException, TokenStreamException {
        String act = "select any t from instances of D_TST; t.u_int = 1.3;"; //$NON-NLS-1$
        String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(2, lines.length);
        assertEquals(":1:49-51: Attribute ->u_int<- is a different type", lines[0]);
        assertEquals("line 1:53: expecting Semicolon, found 'null'", lines[1]);
        OalParserTest_Generics.validateBlkStmtVal( 1, 1, 2 );
    }
	public void testAssignIntUDT2Real() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; x = 1.2; x = t.u_int;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal( 1, 3, 5 );
	}
	public void testAssignInt2StringUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_str = 23;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:49-50: Attribute ->u_str<- is a different type", lines[0]);
		assertEquals("line 1:52: expecting Semicolon, found 'null'", lines[1]);
		OalParserTest_Generics.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testAssignBool2IntUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_int = true;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:49-52: Attribute ->u_int<- is a different type", lines[0]);
		assertEquals("line 1:54: expecting Semicolon, found 'null'", lines[1]);
		OalParserTest_Generics.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testAssignBool2RealUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_real = true;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:50-53: Attribute ->u_real<- is a different type", lines[0]);
		assertEquals("line 1:55: expecting Semicolon, found 'null'", lines[1]);
		OalParserTest_Generics.validateBlkStmtVal( 1, 1, 2 );
	}


    // Expression tests
    public void testAddExprI2I() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        binaryOpTest("x = 1 + 2;", intId, intId, intId, intId, 1, 1); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lin.length);
        assertEquals("1", lin[0].getValue()); //$NON-NLS-1$
        assertEquals("2", lin[1].getValue()); //$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "+", lin[0].getValue_id(), lin[1].getValue_id()); //$NON-NLS-1$
    }

    public void testAddExprI2PI2IP() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = 1 + (2 - 3);", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        assertEquals("", x); //$NON-NLS-1$
        Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, blk.length);
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, t.length);
        assertEquals("x", t[0].getName()); //$NON-NLS-1$
        assertEquals(t[0].getBlock_id(), blk[0].getBlock_id());
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(6, val.length);
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        assertEquals(val[0].getDt_id(), intId);
        assertEquals(val[1].getDt_id(), intId);
        assertEquals(val[2].getDt_id(), intId);
        assertEquals(val[3].getDt_id(), intId);
        assertEquals(val[4].getDt_id(), intId);
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, lin.length);
        assertEquals("1", lin[0].getValue()); //$NON-NLS-1$
        assertEquals("2", lin[1].getValue()); //$NON-NLS-1$
        assertEquals("3", lin[2].getValue()); //$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "+", lin[0].getValue_id(), bin[1].getValue_id()); //$NON-NLS-1$
        OalParserTest_Generics.verifyBinaryOperation(bin[1],
                "-", lin[1].getValue_id(), lin[2].getValue_id()); //$NON-NLS-1$
    }

    public void expr3op(String p_action, UUID op1_dt, UUID bin1_dt,
            UUID op2_dt, UUID bin2_dt, UUID op3_dt, UUID result_dt)
            throws RecognitionException, TokenStreamException {
        exprTest.expr3op(p_action, op1_dt, bin1_dt, op2_dt, bin2_dt, op3_dt, result_dt);
    }

    public void postExpr3op(UUID op1_val_id, UUID op2_val_id, UUID op3_val_id,
            String op1, String op2) throws RecognitionException,
            TokenStreamException {
        exprTest.postExpr3op(op1_val_id, op2_val_id, op3_val_id, op1, op2);
    }

    public void testAddExprI2I2I() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        expr3op("x = 1 + 2 - 3;", intId, intId, intId, intId, intId, intId); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, lin.length);
        assertEquals("1", lin[0].getValue()); //$NON-NLS-1$
        assertEquals("2", lin[1].getValue()); //$NON-NLS-1$
        assertEquals("3", lin[2].getValue()); //$NON-NLS-1$
        postExpr3op(lin[0].getValue_id(), lin[1].getValue_id(), lin[2]
                .getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
    }

    public void testAddExprI2I2R() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        expr3op("x = 1 + 2 - 2.4;", intId, intId, intId, realId, realId, realId); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lin.length);
        assertEquals("1", lin[0].getValue()); //$NON-NLS-1$
        assertEquals("2", lin[1].getValue()); //$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lr.length);
        assertEquals("2.4", lr[0].getValue()); //$NON-NLS-1$
        postExpr3op(lin[0].getValue_id(), lin[1].getValue_id(), lr[0]
                .getValue_id(), "+", "-"); //$NON-NLS-1$//$NON-NLS-2$
    }

    public void testAddExprI2R2I() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        expr3op("x = 1 + 2.0 - 3;", intId, realId, realId, realId, intId, realId); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lin.length);
        assertEquals("1", lin[0].getValue()); //$NON-NLS-1$
        assertEquals("3", lin[1].getValue()); //$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lr.length);
        assertEquals("2.0", lr[0].getValue()); //$NON-NLS-1$
        postExpr3op(lin[0].getValue_id(), lr[0].getValue_id(), lin[1]
                .getValue_id(), "+", "-"); //$NON-NLS-1$//$NON-NLS-2$
    }

    public void testAddExprR2I2I() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        expr3op("x = .7 + 2 - 3;", realId, realId, intId, realId, intId, realId); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lin.length);
        assertEquals("2", lin[0].getValue()); //$NON-NLS-1$
        assertEquals("3", lin[1].getValue()); //$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lr.length);
        assertEquals(".7", lr[0].getValue());//$NON-NLS-1$
        postExpr3op(lr[0].getValue_id(), lin[0].getValue_id(), lin[1]
                .getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
    }

    public void testAddExprI2R2R() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        expr3op("x = 1 + 9.2 - 2.4;", intId, realId, realId, realId, realId, realId); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lin.length);
        assertEquals("1", lin[0].getValue());//$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lr.length);
        assertEquals("9.2", lr[0].getValue());//$NON-NLS-1$
        assertEquals("2.4", lr[1].getValue());//$NON-NLS-1$
        postExpr3op(lin[0].getValue_id(), lr[0].getValue_id(), lr[1]
                .getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
    }

    public void testAddExprR2I2R() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        expr3op("x = 1.11 + 2 - 3.14;", realId, realId, intId, realId, realId, realId); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lin.length);
        assertEquals("2", lin[0].getValue());//$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lr.length);
        assertEquals("1.11", lr[0].getValue());//$NON-NLS-1$
        assertEquals("3.14", lr[1].getValue());//$NON-NLS-1$
        postExpr3op(lr[0].getValue_id(), lin[0].getValue_id(), lr[1]
                .getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
    }

    public void testAddExprR2R2I() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        expr3op("x = .99 + 0.01 - 3;", realId, realId, realId, realId, intId, realId); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lin.length);
        assertEquals("3", lin[0].getValue());//$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lr.length);
        assertEquals(".99", lr[0].getValue());//$NON-NLS-1$
        assertEquals("0.01", lr[1].getValue());//$NON-NLS-1$
        postExpr3op(lr[0].getValue_id(), lr[1].getValue_id(), lin[0]
                .getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
    }

    public void testAddExprPI2IP2I() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = (1 + 2) - 3;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        assertEquals("", x); //$NON-NLS-1$
        Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, blk.length);
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, t.length);
        assertEquals("x", t[0].getName()); //$NON-NLS-1$
        assertEquals(t[0].getBlock_id(), blk[0].getBlock_id());
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(6, val.length);
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        assertEquals(val[0].getDt_id(), intId);
        assertEquals(val[1].getDt_id(), intId);
        assertEquals(val[2].getDt_id(), intId);
        assertEquals(val[3].getDt_id(), intId);
        assertEquals(val[4].getDt_id(), intId);
        assertEquals(val[5].getDt_id(), intId);
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, lin.length);
        assertEquals("1", lin[0].getValue());//$NON-NLS-1$
        assertEquals("2", lin[1].getValue());//$NON-NLS-1$
        assertEquals("3", lin[2].getValue());//$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "+", lin[0].getValue_id(), lin[1].getValue_id());//$NON-NLS-1$
        OalParserTest_Generics.verifyBinaryOperation(bin[1],
                "-", bin[0].getValue_id(), lin[2].getValue_id());//$NON-NLS-1$
    }

    public void testAddExprR2R() throws RecognitionException,
            TokenStreamException {
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        binaryOpTest("x = 1.1 - 2.9;", realId, realId, realId, realId, 1, 1); //$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lr.length);
        assertEquals("1.1", lr[0].getValue());//$NON-NLS-1$
        assertEquals("2.9", lr[1].getValue());//$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "-", lr[0].getValue_id(), lr[1].getValue_id());//$NON-NLS-1$
    }

    public void testAddExprR2R2R() throws RecognitionException,
            TokenStreamException {
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        expr3op("x = 0.1 + 0.2 - 0.3;", realId, realId, realId, realId, realId, realId); //$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, lr.length);
        assertEquals("0.1", lr[0].getValue());//$NON-NLS-1$
        assertEquals("0.2", lr[1].getValue());//$NON-NLS-1$
        assertEquals("0.3", lr[2].getValue());//$NON-NLS-1$
        postExpr3op(lr[0].getValue_id(), lr[1].getValue_id(), lr[2]
                .getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
    }

    public void testAddExprI2R() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        binaryOpTest("x = 1 - 2.2;", intId, realId, realId, realId, 1, 1); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lin.length);
        assertEquals("1", lin[0].getValue());//$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lr.length);
        assertEquals("2.2", lr[0].getValue());//$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "-", lin[0].getValue_id(), lr[0].getValue_id());//$NON-NLS-1$
    }

    public void testAddExprR2I() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        binaryOpTest("x = 2.3 + 1;", realId, realId, intId, realId, 1, 1); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lin.length);
        assertEquals("1", lin[0].getValue());//$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lr.length);
        assertEquals("2.3", lr[0].getValue());//$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "+", lr[0].getValue_id(), lin[0].getValue_id());//$NON-NLS-1$
    }

    public void testMultExprI2I() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        binaryOpTest("x = 1 * 2;", intId, intId, intId, intId, 1, 1); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lin.length);
        assertEquals("1", lin[0].getValue());//$NON-NLS-1$
        assertEquals("2", lin[1].getValue());//$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "*", lin[0].getValue_id(), lin[1].getValue_id());//$NON-NLS-1$
    }

    public void testMultExprI2I2I() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        expr3op("x = 1 * 2 / 3;", intId, intId, intId, intId, intId, intId); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, lin.length);
        assertEquals("1", lin[0].getValue());//$NON-NLS-1$
        assertEquals("2", lin[1].getValue());//$NON-NLS-1$
        assertEquals("3", lin[2].getValue());//$NON-NLS-1$
        postExpr3op(lin[0].getValue_id(), lin[1].getValue_id(), lin[2]
                .getValue_id(), "*", "/");//$NON-NLS-1$//$NON-NLS-2$
    }

    public void testMultExprR2R() throws RecognitionException,
            TokenStreamException {
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        binaryOpTest("x = 2.1 / 2.2;", realId, realId, realId, realId, 1, 1); //$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lr.length);
        assertEquals("2.1", lr[0].getValue());//$NON-NLS-1$
        assertEquals("2.2", lr[1].getValue());//$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "/", lr[0].getValue_id(), lr[1].getValue_id());//$NON-NLS-1$
    }

    public void testMultExprR2R2R() throws RecognitionException,
            TokenStreamException {
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        expr3op("x = 0.1 / 0.2 * 0.3;", realId, realId, realId, realId, realId, realId); //$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, lr.length);
        assertEquals("0.1", lr[0].getValue());//$NON-NLS-1$
        assertEquals("0.2", lr[1].getValue());//$NON-NLS-1$
        assertEquals("0.3", lr[2].getValue());//$NON-NLS-1$
        postExpr3op(lr[0].getValue_id(), lr[1].getValue_id(), lr[2]
                .getValue_id(), "/", "*");//$NON-NLS-1$//$NON-NLS-2$
    }

    public void testMultExprNotI() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = NOT 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":1:9-9: Operand for boolean negation is not of type boolean", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:11: unexpected token: null", lines[1]); //$NON-NLS-1$
        noValuesPresent(0);
    }

    public void testMultExprNotR() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = not 3.14;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":1:9-12: Operand for boolean negation is not of type boolean", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:14: unexpected token: null", lines[1]); //$NON-NLS-1$
        noValuesPresent(0);
    }

    public void testConjunctionExprBaB2() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = 1 < 2 and 4 > 3;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        assertEquals("", x); //$NON-NLS-1$
        Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, blk.length);
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, t.length);
        assertEquals("x", t[0].getName());//$NON-NLS-1$
        assertEquals(t[0].getBlock_id(), blk[0].getBlock_id());
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(8, val.length);
        UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        assertEquals(val[0].getDt_id(), boolId);
        assertEquals(val[1].getDt_id(), intId);
        assertEquals(val[2].getDt_id(), boolId);
        assertEquals(val[3].getDt_id(), intId);
        assertEquals(val[4].getDt_id(), intId);
        assertEquals(val[5].getDt_id(), boolId);
        assertEquals(val[6].getDt_id(), intId);
        assertEquals(val[7].getDt_id(), boolId);
        LiteralBoolean_c[] lbool = LiteralBoolean_c
                .LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, lbool.length);
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(4, lin.length);
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "<", lin[0].getValue_id(), lin[1].getValue_id());//$NON-NLS-1$
        OalParserTest_Generics.verifyBinaryOperation(bin[1],
                ">", lin[2].getValue_id(), lin[3].getValue_id());//$NON-NLS-1$
        OalParserTest_Generics.verifyBinaryOperation(bin[2],
                "and", bin[0].getValue_id(), bin[1].getValue_id());//$NON-NLS-1$
    }

    public void noValuesPresent(int numStatements) throws RecognitionException,
            TokenStreamException {
        exprTest.noValuesPresent(numStatements);
    }

    public void testConjunctionExprIaI() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = 1 and 2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":1:11-11: Incompatible operands for boolean and expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:13: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        noValuesPresent(0);
    }

    public void testConjunctionExprIaR() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = 1 and 2.2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":1:11-13: Incompatible operands for boolean and expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:15: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        noValuesPresent(0);
    }

    public void testConjunctionExprRoI() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = 1.1 or 2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":1:12-12: Incompatible operands for boolean or expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:14: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        noValuesPresent(0);
    }

    public void testConjunctionExprRaR() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = 1.1 and 2.1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":1:13-15: Incompatible operands for boolean and expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:17: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        noValuesPresent(0);
    }

    public void testSubExprIoI() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = 1 or 2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":1:10-10: Incompatible operands for boolean or expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:12: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        noValuesPresent(0);
    }

    public void testSubExprRoR() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = 1.1 or 2.1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":1:12-14: Incompatible operands for boolean or expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:16: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        noValuesPresent(0);
    }

    public void testTermEmptyI() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "y = 1;\nx = empty y;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:11-11: Operand for empty operator is not of type inst_ref<Object>", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:13: unexpected token: null", lines[1]); //$NON-NLS-1$
        UnaryOperation_c[] u = UnaryOperation_c
                .UnaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, u.length);
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        assertEquals("y", t[0].getName());//$NON-NLS-1$
        assertEquals("x", t[1].getName());//$NON-NLS-1$
    }

    public void testTermNotEmptyR() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "y = 2.2;\nx = not_empty y;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:15-15: Operand for not_empty operator is not of type inst_ref<Object>", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:17: unexpected token: null", lines[1]); //$NON-NLS-1$
        UnaryOperation_c[] u = UnaryOperation_c
                .UnaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, u.length);
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        assertEquals("y", t[0].getName());//$NON-NLS-1$
        assertEquals("x", t[1].getName());//$NON-NLS-1$
    }

    public void testUnaryPlusI() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = +2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        assertEquals("", x);//$NON-NLS-1$
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, t.length);
        assertEquals("x", t[0].getName());//$NON-NLS-1$
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, val.length);
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        assertEquals(val[0].getDt_id(), intId);
        assertEquals(val[1].getDt_id(), intId);
        assertEquals(val[2].getDt_id(), intId);
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lin.length);
        assertEquals("2", lin[0].getValue());//$NON-NLS-1$
        assertEquals(lin[0].getValue_id(), val[2].getValue_id());
        UnaryOperation_c[] u = UnaryOperation_c
                .UnaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, u.length);
        OalParserTest_Generics.verifyUnaryOperation(u[0], "+", lin[0].getValue_id());//$NON-NLS-1$
        assertEquals(u[0].getValue_id(), val[1].getValue_id());
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
    }

    public void testRelExprI2I() throws RecognitionException,
            TokenStreamException {
        UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        binaryOpTest("x = 1 < 2;", intId, boolId, intId, boolId, 1, 1); //$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lin.length);
        assertEquals("1", lin[0].getValue());//$NON-NLS-1$
        assertEquals("2", lin[1].getValue());//$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "<", lin[0].getValue_id(), lin[1].getValue_id());//$NON-NLS-1$
    }

    public void testRelExprR2R() throws RecognitionException,
            TokenStreamException {
        UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        binaryOpTest("x = 1.1 > 2.0;", realId, boolId, realId, boolId, 1, 1); //$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, lr.length);
        assertEquals("1.1", lr[0].getValue());//$NON-NLS-1$
        assertEquals("2.0", lr[1].getValue());//$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                ">", lr[0].getValue_id(), lr[1].getValue_id());//$NON-NLS-1$
    }

    public void testRelExprI2R() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = 1 < 2.2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        assertEquals("", x);//$NON-NLS-1$
        UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        binaryOpTest("x = 1 < 2.2;", intId, boolId, realId, boolId, 1, 1); //$NON-NLS-1$
        LiteralReal_c[] lr = LiteralReal_c
                .LiteralRealInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lr.length);
        assertEquals("2.2", lr[0].getValue());//$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, lin.length);
        assertEquals("1", lin[0].getValue());//$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "<", lin[0].getValue_id(), lr[0].getValue_id());//$NON-NLS-1$
    }

    public void testRelExprI2B() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = 1 != true;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":1:10-13: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:15: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, t.length);
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, lin.length);
        LiteralBoolean_c[] lbool = LiteralBoolean_c
                .LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, lbool.length);
    }

    public void testNestedExpr() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "x = (1); x1 = -(2); x2 = (-3); y = 1 + (x * 2); z = ((x)+300)-(y+2);", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        assertEquals("", x);//$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(5, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(5, t.length);
        assertEquals("x", t[0].getName());//$NON-NLS-1$
        assertEquals("x1", t[1].getName());//$NON-NLS-1$
        assertEquals("x2", t[2].getName());//$NON-NLS-1$
        assertEquals("y", t[3].getName());//$NON-NLS-1$
        assertEquals("z", t[4].getName());//$NON-NLS-1$
        LiteralInteger_c[] lin = LiteralInteger_c
                .LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
        assertEquals(7, lin.length);
        assertEquals("1", lin[0].getValue());//$NON-NLS-1$
        assertEquals("2", lin[1].getValue());//$NON-NLS-1$
        assertEquals("3", lin[2].getValue());//$NON-NLS-1$
        assertEquals("1", lin[3].getValue());//$NON-NLS-1$
        assertEquals("2", lin[4].getValue());//$NON-NLS-1$
        assertEquals("300", lin[5].getValue());//$NON-NLS-1$
        assertEquals("2", lin[6].getValue());//$NON-NLS-1$
        TransientValueReference_c[] tv = TransientValueReference_c
                .TransientValueReferenceInstances(OalParserTest_Generics.modelRoot);
        assertEquals(8, tv.length);
        assertEquals(tv[0].getVar_id(), t[0].getVar_id());
        assertEquals(tv[1].getVar_id(), t[1].getVar_id());
        assertEquals(tv[2].getVar_id(), t[2].getVar_id());
        assertEquals(tv[3].getVar_id(), t[3].getVar_id());
        assertEquals(tv[4].getVar_id(), t[0].getVar_id());
        assertEquals(tv[5].getVar_id(), t[4].getVar_id());
        assertEquals(tv[6].getVar_id(), t[0].getVar_id());
        assertEquals(tv[7].getVar_id(), t[3].getVar_id());
        UnaryOperation_c[] u = UnaryOperation_c
                .UnaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, u.length);
        OalParserTest_Generics.verifyUnaryOperation(u[0], "-", lin[1].getValue_id());//$NON-NLS-1$
        OalParserTest_Generics.verifyUnaryOperation(u[1], "-", lin[2].getValue_id());//$NON-NLS-1$
        BinaryOperation_c[] bin = BinaryOperation_c
                .BinaryOperationInstances(OalParserTest_Generics.modelRoot);
        assertEquals(5, bin.length);
        OalParserTest_Generics.verifyBinaryOperation(bin[0],
                "+", lin[3].getValue_id(), bin[1].getValue_id());//$NON-NLS-1$
        OalParserTest_Generics.verifyBinaryOperation(bin[1],
                "*", tv[4].getValue_id(), lin[4].getValue_id());//$NON-NLS-1$
        OalParserTest_Generics.verifyBinaryOperation(bin[2],
                "+", tv[6].getValue_id(), lin[5].getValue_id());//$NON-NLS-1$
        OalParserTest_Generics.verifyBinaryOperation(bin[3],
                "-", bin[2].getValue_id(), bin[4].getValue_id());//$NON-NLS-1$
        OalParserTest_Generics.verifyBinaryOperation(bin[4],
                "+", tv[7].getValue_id(), lin[6].getValue_id());//$NON-NLS-1$
    }



    // Attribute tests
    public void goodAttrWriteCheck(String attr_name, UUID dt_id, String var_name,
            String kl, int numStmt) {
        attrTest.goodAttrWriteCheck(attr_name, dt_id, var_name, kl, numStmt);
    }

    public void testAttributeWriteI2R() throws RecognitionException, TokenStreamException {
        String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nd1.real_attr = 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        assertEquals("", x);//$NON-NLS-1$
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//91//$NON-NLS-1$
        goodAttrWriteCheck("real_attr", intId, "d1", "D_D", 2);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
    }

    public void badAttrWriteCheck(int numStmts, int numVars, int numVals)
        throws RecognitionException, TokenStreamException {
        attrTest.badAttrWriteCheck(numStmts, numVars, numVals);
    }

    public void testBadAttributeWriteR2I() throws RecognitionException, TokenStreamException {
        String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nd1.Disk_ID = 1.1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":2:14-16: Attribute ->Disk_ID<- is a different type", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:18: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        badAttrWriteCheck(1, 1, 2);
    }



    // Control tests
    public void testCleanupBeforeContinuing() throws RecognitionException, TokenStreamException {
        // This is not really a test.  It is just a container for some cleanup
        // functions that need to run before continuing.
        OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
        OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_INT_NO_PARM);
        OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_REAL_NO_PARM);

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
            String lines[] = x.split("\n");//$NON-NLS-1$
            String errmsg[] = lines[0].split(" for ");//$NON-NLS-1$
            assertEquals(":1:8-10: Invalid data type returned", errmsg[0]); //$NON-NLS-1$
            assertEquals("line 1:12: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
            Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
            assertEquals(1, blk.length);
            OalParserTest_Generics.clearActionData(i, OalParserTest_Generics.TEST_INT_NO_PARM);
        }
    }

}
