//========================================================================
//
//File:      $RCSfile: TestExpr_Generics.java,v $
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
package org.xtuml.bp.als.oal.test;

import java.util.UUID;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.AttributeValueReference_c;
import org.xtuml.bp.core.BinaryOperation_c;
import org.xtuml.bp.core.Block_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.InstanceReference_c;
import org.xtuml.bp.core.InstanceSetReference_c;
import org.xtuml.bp.core.LiteralBoolean_c;
import org.xtuml.bp.core.LiteralEnumerator_c;
import org.xtuml.bp.core.LiteralInteger_c;
import org.xtuml.bp.core.LiteralReal_c;
import org.xtuml.bp.core.LiteralString_c;
import org.xtuml.bp.core.Statement_c;
import org.xtuml.bp.core.TransientValueReference_c;
import org.xtuml.bp.core.UnaryOperation_c;
import org.xtuml.bp.core.Value_c;
import org.xtuml.bp.core.Variable_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import junit.framework.TestCase;

@RunWith(OrderedRunner.class)
public class TestExpr_Generics extends TestCase {

    @Before
	public void setUp() throws Exception {
        super.setUp();

        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_INT_TO_REAL_PROMOTION,
                MessageDialogWithToggle.ALWAYS);
        store.setValue(BridgePointPreferencesStore.ALLOW_REAL_TO_INT_COERCION,
                MessageDialogWithToggle.ALWAYS);
    }

    @After
	public void tearDown() throws Exception {
        try {
            super.tearDown();
            OalParserTest_Generics.tearDownActionData();
        } catch (RecognitionException re) {
            // do nothing
        } catch (TokenStreamException te) {
            // do nothing
        }
    }

	public void binaryOpTest(String p_action, UUID op1_dt, UUID bin_dt,
			UUID op2_dt, UUID result_dt, int bin_index, int numStatements)
			throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction(p_action, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numStatements, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		int lastStmtIndex = numStatements - 1;
		assertEquals(numStatements, t.length);
		assertEquals("x", t[lastStmtIndex].getName()); //$NON-NLS-1$
		assertEquals(t[lastStmtIndex].getBlock_id(), blk[0].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, val.length);
        assertEquals(result_dt, val[0].getDt_id());
		assertEquals(op1_dt, val[1].getDt_id());
		assertEquals(bin_dt, val[2].getDt_id());
		assertEquals(op2_dt, val[3].getDt_id());
	}

	@Test
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

	@Test
	public void testAddExprI2PI2IP() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = 1 + (2 - 3);", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
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
		String x = OalParserTest_Generics.parseAction(p_action, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
		assertEquals("x", t[0].getName()); //$NON-NLS-1$
		assertEquals(t[0].getBlock_id(), blk[0].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(6, val.length);
        assertEquals(val[0].getDt_id(), result_dt);
        assertEquals(val[1].getDt_id(), op1_dt);
		assertEquals(val[2].getDt_id(), bin1_dt);
		assertEquals(val[3].getDt_id(), op2_dt);
		assertEquals(val[4].getDt_id(), bin2_dt);
		assertEquals(val[5].getDt_id(), op3_dt);
	}

	public void postExpr3op(UUID op1_val_id, UUID op2_val_id, UUID op3_val_id,
			String op1, String op2) throws RecognitionException,
			TokenStreamException {
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0], op1, op1_val_id, op2_val_id);
		OalParserTest_Generics.verifyBinaryOperation(bin[1], op2, bin[0].getValue_id(), op3_val_id);
	}

	@Test
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

	@Test
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
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lr.length);
		assertEquals("2.4", lr[0].getValue()); //$NON-NLS-1$
		postExpr3op(lin[0].getValue_id(), lin[1].getValue_id(), lr[0]
				.getValue_id(), "+", "-"); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
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
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lr.length);
		assertEquals("2.0", lr[0].getValue()); //$NON-NLS-1$
		postExpr3op(lin[0].getValue_id(), lr[0].getValue_id(), lin[1]
				.getValue_id(), "+", "-"); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
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
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lr.length);
		assertEquals(".7", lr[0].getValue());//$NON-NLS-1$
		postExpr3op(lr[0].getValue_id(), lin[0].getValue_id(), lin[1]
				.getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void testAddExprI2R2R() throws RecognitionException,
			TokenStreamException {
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		expr3op("x = 1 + 9.2 - 2.4;", intId, realId, realId, realId, realId, realId); //$NON-NLS-1$
		LiteralInteger_c[] lin = LiteralInteger_c
				.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lin.length);
		assertEquals("1", lin[0].getValue());//$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, lr.length);
		assertEquals("9.2", lr[0].getValue());//$NON-NLS-1$
		assertEquals("2.4", lr[1].getValue());//$NON-NLS-1$
		postExpr3op(lin[0].getValue_id(), lr[0].getValue_id(), lr[1]
				.getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void testAddExprR2I2R() throws RecognitionException,
			TokenStreamException {
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		expr3op("x = 1.11 + 2 - 3.14;", realId, realId, intId, realId, realId, realId); //$NON-NLS-1$
		LiteralInteger_c[] lin = LiteralInteger_c
				.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lin.length);
		assertEquals("2", lin[0].getValue());//$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, lr.length);
		assertEquals("1.11", lr[0].getValue());//$NON-NLS-1$
		assertEquals("3.14", lr[1].getValue());//$NON-NLS-1$
		postExpr3op(lr[0].getValue_id(), lin[0].getValue_id(), lr[1]
				.getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void testAddExprR2R2I() throws RecognitionException,
			TokenStreamException {
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		expr3op("x = .99 + 0.01 - 3;", realId, realId, realId, realId, intId, realId); //$NON-NLS-1$
		LiteralInteger_c[] lin = LiteralInteger_c
				.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lin.length);
		assertEquals("3", lin[0].getValue());//$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, lr.length);
		assertEquals(".99", lr[0].getValue());//$NON-NLS-1$
		assertEquals("0.01", lr[1].getValue());//$NON-NLS-1$
		postExpr3op(lr[0].getValue_id(), lr[1].getValue_id(), lin[0]
				.getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void testAddExprPI2IP2I() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = (1 + 2) - 3;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
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

	@Test
	public void testAddExprR2R() throws RecognitionException,
			TokenStreamException {
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		binaryOpTest("x = 1.1 - 2.9;", realId, realId, realId, realId, 1, 1); //$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, lr.length);
		assertEquals("1.1", lr[0].getValue());//$NON-NLS-1$
		assertEquals("2.9", lr[1].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"-", lr[0].getValue_id(), lr[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testAddExprR2R2R() throws RecognitionException,
			TokenStreamException {
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		expr3op("x = 0.1 + 0.2 - 0.3;", realId, realId, realId, realId, realId, realId); //$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, lr.length);
		assertEquals("0.1", lr[0].getValue());//$NON-NLS-1$
		assertEquals("0.2", lr[1].getValue());//$NON-NLS-1$
		assertEquals("0.3", lr[2].getValue());//$NON-NLS-1$
		postExpr3op(lr[0].getValue_id(), lr[1].getValue_id(), lr[2]
				.getValue_id(), "+", "-");//$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void testAddExprI2R() throws RecognitionException,
			TokenStreamException {
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		binaryOpTest("x = 1 - 2.2;", intId, realId, realId, realId, 1, 1); //$NON-NLS-1$
		LiteralInteger_c[] lin = LiteralInteger_c
				.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lin.length);
		assertEquals("1", lin[0].getValue());//$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lr.length);
		assertEquals("2.2", lr[0].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"-", lin[0].getValue_id(), lr[0].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testAddExprR2I() throws RecognitionException,
			TokenStreamException {
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		binaryOpTest("x = 2.3 + 1;", realId, realId, intId, realId, 1, 1); //$NON-NLS-1$
		LiteralInteger_c[] lin = LiteralInteger_c
				.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lin.length);
		assertEquals("1", lin[0].getValue());//$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lr.length);
		assertEquals("2.3", lr[0].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"+", lr[0].getValue_id(), lin[0].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testAddExprS2S() throws RecognitionException,
			TokenStreamException {
		UUID strId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "string");//$NON-NLS-1$
		binaryOpTest("x = \"top \" + \"job\";", strId, strId, strId, strId, 1, 1); //$NON-NLS-1$
		LiteralString_c[] ls = LiteralString_c
				.LiteralStringInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, ls.length);
		assertEquals("top ", ls[0].getValue());//$NON-NLS-1$
		assertEquals("job", ls[1].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"+", ls[0].getValue_id(), ls[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testAddExprS2S2S() throws RecognitionException,
			TokenStreamException {
		UUID strId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "string");//$NON-NLS-1$
		expr3op(
				"x = \"top \" + \"job\" + \" done\";", strId, strId, strId, strId, strId, strId); //$NON-NLS-1$
		LiteralString_c[] ls = LiteralString_c
				.LiteralStringInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, ls.length);
		assertEquals("top ", ls[0].getValue());//$NON-NLS-1$
		assertEquals("job", ls[1].getValue());//$NON-NLS-1$
		assertEquals(" done", ls[2].getValue());//$NON-NLS-1$
		postExpr3op(ls[0].getValue_id(), ls[1].getValue_id(), ls[2]
				.getValue_id(), "+", "+");//$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
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

	@Test
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

	@Test
	public void testMultExprR2R() throws RecognitionException,
			TokenStreamException {
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		binaryOpTest("x = 2.1 / 2.2;", realId, realId, realId, realId, 1, 1); //$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, lr.length);
		assertEquals("2.1", lr[0].getValue());//$NON-NLS-1$
		assertEquals("2.2", lr[1].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"/", lr[0].getValue_id(), lr[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testMultExprR2R2R() throws RecognitionException,
			TokenStreamException {
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		expr3op("x = 0.1 / 0.2 * 0.3;", realId, realId, realId, realId, realId, realId); //$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, lr.length);
		assertEquals("0.1", lr[0].getValue());//$NON-NLS-1$
		assertEquals("0.2", lr[1].getValue());//$NON-NLS-1$
		assertEquals("0.3", lr[2].getValue());//$NON-NLS-1$
		postExpr3op(lr[0].getValue_id(), lr[1].getValue_id(), lr[2]
				.getValue_id(), "/", "*");//$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	public void testMultExprNot() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = not true;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		LiteralBoolean_c[] lbool = LiteralBoolean_c
				.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lbool.length);
		assertEquals("TRUE", lbool[0].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, bin.length);
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, u.length);
		OalParserTest_Generics.verifyUnaryOperation(u[0], "not", lbool[0].getValue_id());//$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
		assertEquals("x", t[0].getName());//$NON-NLS-1$
	}

	@Test
	public void testMultExprNotUDTAttr() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"select any t from instances of D_TST; x = not t.u_bool;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		AttributeValueReference_c[] av = AttributeValueReference_c
				.AttributeValueReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, av.length);
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, bin.length);
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, u.length);
		OalParserTest_Generics.verifyUnaryOperation(u[0], "not", av[0].getValue_id());//$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, t.length);
		assertEquals("t", t[0].getName());//$NON-NLS-1$
		assertEquals("x", t[1].getName());//$NON-NLS-1$
	}

	@Test
	public void testMultExprNotI() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = NOT 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:9-9: Operand for boolean negation is not of type boolean", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:11: unexpected token: null", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testMultExprNotR() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = not 3.14;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:9-12: Operand for boolean negation is not of type boolean", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:14: unexpected token: null", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testMultExprNotS() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = not \"true\";", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:9-14: Operand for boolean negation is not of type boolean", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:16: unexpected token: null", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testConjunctionExprBaB() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		binaryOpTest("x = true and false;", boolId, boolId, boolId, boolId, 2, 1); //$NON-NLS-1$
		LiteralBoolean_c[] ls = LiteralBoolean_c
				.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, ls.length);
		assertEquals("TRUE", ls[0].getValue());//$NON-NLS-1$
		assertEquals("FALSE", ls[1].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"and", ls[0].getValue_id(), ls[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testConjunctionExprBaBaB() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		expr3op(
				"x = true and false and true;", boolId, boolId, boolId, boolId, boolId, boolId); //$NON-NLS-1$
		LiteralBoolean_c[] ls = LiteralBoolean_c
				.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, ls.length);
		assertEquals("TRUE", ls[0].getValue());//$NON-NLS-1$
		assertEquals("FALSE", ls[1].getValue());//$NON-NLS-1$
		assertEquals("TRUE", ls[2].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"and", ls[0].getValue_id(), ls[1].getValue_id());//$NON-NLS-1$
		OalParserTest_Generics.verifyBinaryOperation(bin[1],
				"and", bin[0].getValue_id(), ls[2].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testConjunctionExprBaBUDTAttr() throws RecognitionException,
			TokenStreamException {
		UUID udt_bool = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "udt_boolean");//$NON-NLS-1$
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$

        String x = OalParserTest_Generics.parseAction("select any t from instances of D_TST;\n x = t.u_bool AND t.u_bool;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        assertEquals("", x); //$NON-NLS-1$
        Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, blk.length);
        Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        int lastStmtIndex = 1;
        assertEquals(2, t.length);
        assertEquals("x", t[lastStmtIndex].getName()); //$NON-NLS-1$
        assertEquals(t[lastStmtIndex].getBlock_id(), blk[0].getBlock_id());
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(6, val.length);
        assertEquals(boolId, val[0].getDt_id());
        assertEquals(val[1].getDt_id(), val[3].getDt_id());
        assertEquals(udt_bool, val[2].getDt_id());
        assertEquals(udt_bool, val[4].getDt_id());
        assertEquals(boolId, val[5].getDt_id());
		AttributeValueReference_c[] av = AttributeValueReference_c
				.AttributeValueReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, av.length);
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"and", av[0].getValue_id(), av[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testConjunctionExprBaB2() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = 1 < 2 and 4 > 3;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
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

	@Test
	public void testSubExprBoB() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		binaryOpTest("x = true or false;", boolId, boolId, boolId, boolId, 2, 1); //$NON-NLS-1$
		LiteralBoolean_c[] ls = LiteralBoolean_c
				.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, ls.length);
		assertEquals("TRUE", ls[0].getValue());//$NON-NLS-1$
		assertEquals("FALSE", ls[1].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"or", ls[0].getValue_id(), ls[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testSubExprBoBoB() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		expr3op(
				"x = true or false or true;", boolId, boolId, boolId, boolId, boolId, boolId); //$NON-NLS-1$
		LiteralBoolean_c[] ls = LiteralBoolean_c
				.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, ls.length);
		assertEquals("TRUE", ls[0].getValue());//$NON-NLS-1$
		assertEquals("FALSE", ls[1].getValue());//$NON-NLS-1$
		assertEquals("TRUE", ls[2].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"or", ls[0].getValue_id(), ls[1].getValue_id());//$NON-NLS-1$
		OalParserTest_Generics.verifyBinaryOperation(bin[1],
				"or", bin[0].getValue_id(), ls[2].getValue_id());//$NON-NLS-1$
	}

	public void noValuesPresent(int numStatements) throws RecognitionException,
			TokenStreamException {
		LiteralBoolean_c[] lbool = LiteralBoolean_c
				.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, lbool.length);
		LiteralInteger_c[] lin = LiteralInteger_c
				.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, lin.length);
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, lr.length);
		LiteralString_c[] ls = LiteralString_c
				.LiteralStringInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, ls.length);
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, bin.length);
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, u.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numStatements, st.length);
	}

	@Test
	public void testConjunctionExprIaI() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = 1 and 2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:11-11: Incompatible operands for boolean and expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:13: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testConjunctionExprIaR() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = 1 and 2.2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:11-13: Incompatible operands for boolean and expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:15: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testConjunctionExprRoI() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = 1.1 or 2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:12-12: Incompatible operands for boolean or expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:14: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testConjunctionExprRaR() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = 1.1 and 2.1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:13-15: Incompatible operands for boolean and expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:17: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testConjunctionExprSaS() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = \"x\" and \"y\";", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:13-15: Incompatible operands for boolean and expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:17: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testConjunctionExprIROaIRO() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"create object instance d1 of D_D; create object instance d2 of D_D; x = d1 and d2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:80-81: Incompatible operands for boolean and expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:83: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		noValuesPresent(2);
	}

	@Test
	public void testSubExprIoI() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = 1 or 2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:10-10: Incompatible operands for boolean or expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:12: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testSubExprRoR() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = 1.1 or 2.1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:12-14: Incompatible operands for boolean or expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:16: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testSubExprSoS() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = \"x\" or \"y\";", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:12-14: Incompatible operands for boolean or expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:16: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testConjunctionExprIROoIRO() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"create object instance d1 of D_D; create object instance d2 of D_D; x = d1 or d2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:79-80: Incompatible operands for boolean or expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:82: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		noValuesPresent(2);
	}

	@Test
	public void testTermCardinality() throws RecognitionException,
			TokenStreamException {
		String act = "create object instance d1 of D_D; \nx = cardinality d1;";
		validateInstUnaryOp(act, "cardinality", "inst_ref<Object>", "integer");
	}

	@Test
	public void testTermEmpty() throws RecognitionException,
			TokenStreamException {
		String act = "create object instance d1 of D_D; \nx = empty d1;";
		validateInstUnaryOp(act, "empty", "inst_ref<Object>", "boolean");
	}

	@Test
	public void testTermNotEmpty() throws RecognitionException,
			TokenStreamException {
		String act = "create object instance d1 of D_D; \nx = not_empty d1;";
		validateInstUnaryOp(act, "not_empty", "inst_ref<Object>", "boolean");
	}

	@Test
	public void testTermCardinalitySet() throws RecognitionException,
			TokenStreamException {
		String act = "select many d1 from instances of D_D; \nx = cardinality d1;";
		validateInstUnaryOp(act, "cardinality", "inst_ref_set<Object>", "integer");
	}

	@Test
	public void testTermEmptySet() throws RecognitionException,
			TokenStreamException {
		String act = "select many d1 from instances of D_D; \nx = empty d1;";
		validateInstUnaryOp(act, "empty", "inst_ref_set<Object>", "boolean");
	}

	@Test
	public void testTermNotEmptySet() throws RecognitionException,
			TokenStreamException {
		String act = "select many d1 from instances of D_D; \nx = not_empty d1;";
		validateInstUnaryOp(act, "not_empty", "inst_ref_set<Object>", "boolean");
	}

	private void validateInstUnaryOp(String act, String op, String operandType, String resultType) {
		String x = OalParserTest_Generics.parseAction(
				act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, t.length);
		assertEquals("d1", t[0].getName());//$NON-NLS-1$
		assertEquals("x", t[1].getName());//$NON-NLS-1$
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, val.length);
		UUID id = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, operandType);//$NON-NLS-1$
		UUID resultId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, resultType);//$NON-NLS-1$
        assertEquals(val[0].getDt_id(), resultId);
		assertEquals(val[1].getDt_id(), id);
		assertEquals(val[2].getDt_id(), resultId);
		UUID valueId;
		if ( operandType.equals("inst_ref<Object>") ) {
			InstanceReference_c[] irv = InstanceReference_c
					.InstanceReferenceInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, irv.length);
			assertEquals(irv[0].getVar_id(), t[0].getVar_id());
			assertEquals(irv[0].getValue_id(), val[1].getValue_id());
			valueId = irv[0].getValue_id();
		}
		else {
			InstanceSetReference_c[] isr = InstanceSetReference_c
					.InstanceSetReferenceInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, isr.length);
			assertEquals(isr[0].getVar_id(), t[0].getVar_id());
			assertEquals(isr[0].getValue_id(), val[1].getValue_id());
			valueId = isr[0].getValue_id();
		}
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, u.length);
		OalParserTest_Generics.verifyUnaryOperation(u[0], op, valueId);//$NON-NLS-1$
		assertEquals(u[0].getValue_id(), val[2].getValue_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
	}

	@Test
	public void testTermCardinalityS() throws RecognitionException,
	TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"y = \"D_D\";\nx = cardinality y;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":2:17-17: Operand for cardinality operator is not of type inst_ref<Object>", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:19: unexpected token: null", lines[1]); //$NON-NLS-1$
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, u.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, t.length);
		assertEquals("y", t[0].getName());//$NON-NLS-1$
        assertEquals("x", t[1].getName());//$NON-NLS-1$
	}

	@Test
	public void testTermEmptyI() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"y = 1;\nx = empty y;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":2:11-11: Operand for empty operator is not of type inst_ref<Object>", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:13: unexpected token: null", lines[1]); //$NON-NLS-1$
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, u.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, t.length);
		assertEquals("y", t[0].getName());//$NON-NLS-1$
        assertEquals("x", t[1].getName());//$NON-NLS-1$
	}

	@Test
	public void testTermNotEmptyR() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"y = 2.2;\nx = not_empty y;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":2:15-15: Operand for not_empty operator is not of type inst_ref<Object>", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:17: unexpected token: null", lines[1]); //$NON-NLS-1$
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, u.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        assertEquals("y", t[0].getName());//$NON-NLS-1$
        assertEquals("x", t[1].getName());//$NON-NLS-1$
	}

	@Test
	public void testUnaryPlusI() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction("x = +2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
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
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
	}

	@Test
	public void testUnaryPlusIUDTAttr() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"select any t from instances of D_TST; x = +t.u_int;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, t.length);
		assertEquals("t", t[0].getName());//$NON-NLS-1$
		assertEquals("x", t[1].getName());//$NON-NLS-1$
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, val.length);
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		UUID udt_int = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "udt_integer");//$NON-NLS-1$
        UUID objId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
        assertEquals(val[0].getDt_id(), intId);
		assertEquals(val[1].getDt_id(), intId);
        assertEquals(val[2].getDt_id(), objId);
        assertEquals(val[3].getDt_id(), udt_int);
		AttributeValueReference_c[] av = AttributeValueReference_c
				.AttributeValueReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, av.length);
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, u.length);
		OalParserTest_Generics.verifyUnaryOperation(u[0], "+", av[0].getValue_id());//$NON-NLS-1$
		assertEquals(u[0].getValue_id(), val[1].getValue_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
	}

	@Test
	public void testUnaryMinusR() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = -2.2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
		assertEquals("x", t[0].getName());//$NON-NLS-1$
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, val.length);
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		assertEquals(val[0].getDt_id(), realId);
		assertEquals(val[1].getDt_id(), realId);
        assertEquals(val[2].getDt_id(), realId);
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, lr.length);
		assertEquals("2.2", lr[0].getValue());//$NON-NLS-1$
		assertEquals(lr[0].getValue_id(), val[2].getValue_id());
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, u.length);
		OalParserTest_Generics.verifyUnaryOperation(u[0], "-", lr[0].getValue_id());//$NON-NLS-1$
		assertEquals(u[0].getValue_id(), val[1].getValue_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
	}

	@Test
	public void testUnaryMinusRUDTAttr() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"select any t from instances of D_TST; x = -t.u_real;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, t.length);
		assertEquals("t", t[0].getName());//$NON-NLS-1$
		assertEquals("x", t[1].getName());//$NON-NLS-1$
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(4, val.length);
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		UUID udt_real = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "udt_real");//$NON-NLS-1$
        UUID objId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		assertEquals(val[0].getDt_id(), realId);
        assertEquals(val[1].getDt_id(), realId);
        assertEquals(val[2].getDt_id(), objId);
        assertEquals(val[3].getDt_id(), udt_real);
		AttributeValueReference_c[] av = AttributeValueReference_c
				.AttributeValueReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, av.length);
		assertEquals(av[0].getValue_id(), val[3].getValue_id());
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, u.length);
		OalParserTest_Generics.verifyUnaryOperation(u[0], "-", av[0].getValue_id());//$NON-NLS-1$
		assertEquals(u[0].getValue_id(), val[1].getValue_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
	}

	@Test
	public void testUnaryPlusS() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = + \"X\";", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":1:7-9: Incompatible operand for unary +", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:11: unexpected token: null", lines[1]); //$NON-NLS-1$
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, u.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
	}

	@Test
	public void testUnaryMinusB() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = -false;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":1:6-10: Incompatible operand for unary -", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:12: unexpected token: null", lines[1]); //$NON-NLS-1$
		UnaryOperation_c[] u = UnaryOperation_c
				.UnaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, u.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
	}

	@Test
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

	@Test
	public void testRelExprR2R() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		binaryOpTest("x = 1.1 > 2.0;", realId, boolId, realId, boolId, 1, 1); //$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, lr.length);
		assertEquals("1.1", lr[0].getValue());//$NON-NLS-1$
		assertEquals("2.0", lr[1].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				">", lr[0].getValue_id(), lr[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testRelExprS2S() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		UUID strId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "string");//$NON-NLS-1$
		binaryOpTest("x = \"up\" != \"down\";", strId, boolId, strId, boolId, 1, 1); //$NON-NLS-1$
		LiteralString_c[] ls = LiteralString_c
				.LiteralStringInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, ls.length);
		assertEquals("up", ls[0].getValue());//$NON-NLS-1$
		assertEquals("down", ls[1].getValue());//$NON-NLS-1$
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"!=", ls[0].getValue_id(), ls[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testRelExprEnumEQEnum() throws RecognitionException,
			TokenStreamException {
		UUID color = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "color");//$NON-NLS-1$
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		binaryOpTest(
				"x = color::red == color::orange;", color, boolId, color, boolId, 1, 1); //$NON-NLS-1$
		LiteralEnumerator_c[] len = LiteralEnumerator_c
				.LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, len.length);
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"==", len[0].getValue_id(), len[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testRelExprEnumNEEnum() throws RecognitionException,
			TokenStreamException {
		UUID fruit = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "fruit");//$NON-NLS-1$
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		binaryOpTest(
				"x = fruit::apple != fruit::orange;", fruit, boolId, fruit, boolId, 1, 1); //$NON-NLS-1$
		LiteralEnumerator_c[] len = LiteralEnumerator_c
				.LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, len.length);
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"!=", len[0].getValue_id(), len[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testRelExprEnumNeOtherEnum() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = fruit::orange != color::orange;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:29-34: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:36: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, val.length);
		LiteralEnumerator_c[] len = LiteralEnumerator_c
				.LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, len.length);
	}

	@Test
	public void testRelExprEnumEqOtherEnum() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = fruit::red == fruit::apple;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(4, lines.length);
		assertEquals(
				":1:12-14: Cannot find enumerator ->red<- for enumeration data type ->fruit<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:19: expecting Semicolon, found 'fruit'", lines[1]); //$NON-NLS-1$
		assertEquals(
				":1:26-30: Cannot find specified function ->apple<-", lines[2]); //$NON-NLS-1$
		assertEquals("line 1:32: expecting Semicolon, found 'null'", lines[3]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, val.length);
		LiteralEnumerator_c[] len = LiteralEnumerator_c
				.LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, len.length);
	}

	@Test
	public void testRelExprEnumEqOtherEnum2() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = fruit::apple == fruit::red;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:28-30: Cannot find enumerator ->red<- for enumeration data type ->fruit<-", lines[0]); //$NON-NLS-1$
		assertEquals(
				":1:31-31: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
		assertEquals("line 1:32: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, val.length);
		LiteralEnumerator_c[] len = LiteralEnumerator_c
				.LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, len.length);
	}

	@Test
	public void testRelExprIROeqIRO() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		binaryOpTest(
				"create object instance d1 of D_D; create object instance d2 of D_D; x = d1 == d2;", id, boolId, id, boolId, 1, 3); //$NON-NLS-1$
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, t.length);
		assertEquals("d1", t[0].getName());//$NON-NLS-1$
		assertEquals("d2", t[1].getName());//$NON-NLS-1$
		assertEquals("x", t[2].getName());//$NON-NLS-1$
		InstanceReference_c[] irv = InstanceReference_c
				.InstanceReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, irv.length);
		assertEquals(irv[0].getVar_id(), t[0].getVar_id());
		assertEquals(irv[1].getVar_id(), t[1].getVar_id());
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"==", irv[0].getValue_id(), irv[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testRelExprIROneIRO() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		binaryOpTest(
				"create object instance d1 of D_D; create object instance d2 of D_D; x = d1 != d2;", id, boolId, id, boolId, 1, 3); //$NON-NLS-1$
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, t.length);
		assertEquals("d1", t[0].getName());//$NON-NLS-1$
		assertEquals("d2", t[1].getName());//$NON-NLS-1$
		assertEquals("x", t[2].getName());//$NON-NLS-1$
		InstanceReference_c[] irv = InstanceReference_c
				.InstanceReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, irv.length);
		assertEquals(irv[0].getVar_id(), t[0].getVar_id());
		assertEquals(irv[1].getVar_id(), t[1].getVar_id());
		BinaryOperation_c[] bin = BinaryOperation_c
				.BinaryOperationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, bin.length);
		OalParserTest_Generics.verifyBinaryOperation(bin[0],
				"!=", irv[0].getValue_id(), irv[1].getValue_id());//$NON-NLS-1$
	}

	@Test
	public void testRelExprI2R() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = 1 < 2.2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        assertEquals("", x);//$NON-NLS-1$
        UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
		binaryOpTest("x = 1 < 2.2;", intId, boolId, realId, boolId, 1, 1); //$NON-NLS-1$
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
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

	@Test
	public void testRelExprI2B() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = 1 != true;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:10-13: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:15: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
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

	@Test
	public void testRelExprIROleIRO() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"create object instance d1 of D_D; create object instance d2 of D_D; x = d1 <= d2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:79-80: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:82: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, t.length);
        assertEquals("d1", t[0].getName());//$NON-NLS-1$
		assertEquals("d2", t[1].getName());//$NON-NLS-1$
        assertEquals("x", t[2].getName());//$NON-NLS-1$
	}

	@Test
	public void testRelExprIROltIRO() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"create object instance d1 of D_D; create object instance d2 of D_D; x = d1 < d2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:78-79: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:81: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, t.length);
		assertEquals("d1", t[0].getName());//$NON-NLS-1$
		assertEquals("d2", t[1].getName());//$NON-NLS-1$
        assertEquals("x", t[2].getName());//$NON-NLS-1$
	}

	@Test
	public void testRelExprIROgeIRO() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"create object instance d1 of D_D; create object instance d2 of D_D; x = d1 >= d2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:79-80: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:82: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, t.length);
		assertEquals("d1", t[0].getName());//$NON-NLS-1$
		assertEquals("d2", t[1].getName());//$NON-NLS-1$
        assertEquals("x", t[2].getName());//$NON-NLS-1$
	}

	@Test
	public void testRelExprIROgtIRO() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"create object instance d1 of D_D; create object instance d2 of D_D; x = d1 > d2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:78-79: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:81: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, t.length);
		assertEquals("d1", t[0].getName());//$NON-NLS-1$
		assertEquals("d2", t[1].getName());//$NON-NLS-1$
        assertEquals("x", t[2].getName());//$NON-NLS-1$
	}

	@Test
	public void testAddExprSFS() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = \"a\" - \"b\";", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:11-13: Incompatible operands for addition expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:15: unexpected token: null", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testAddExprIROFIRO() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"create object instance d1 of D_D; create object instance d2 of D_D; x = d1 + d2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:78-79: Incompatible operands for addition expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:81: unexpected token: null", lines[1]); //$NON-NLS-1$
		noValuesPresent(2);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, t.length);
		assertEquals("d1", t[0].getName());//$NON-NLS-1$
		assertEquals("d2", t[1].getName());//$NON-NLS-1$
        assertEquals("x", t[2].getName());//$NON-NLS-1$
	}

	@Test
	public void testAddExprB2B() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = false + true;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:13-16: Incompatible operands for addition expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:18: unexpected token: null", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testMultExprS2S() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = \"c\" * \"d\";", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:11-13: Incompatible operands for multiplication expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:15: unexpected token: null", lines[1]); //$NON-NLS-1$
		noValuesPresent(0);
	}

	@Test
	public void testMultExprIROFIRO() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"create object instance d1 of D_D; create object instance d2 of D_D; x = d1 % d2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:78-79: Incompatible operands for multiplication expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:81: unexpected token: null", lines[1]); //$NON-NLS-1$
		noValuesPresent(2);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, t.length);
		assertEquals("d1", t[0].getName());//$NON-NLS-1$
		assertEquals("d2", t[1].getName());//$NON-NLS-1$
        assertEquals("x", t[2].getName());//$NON-NLS-1$
	}

	@Test
	public void testNestedExpr() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = (1); x1 = -(2); x2 = (-3); y = 1 + (x * 2); z = ((x)+300)-(y+2);", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        assertEquals("", x);//$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
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

	@Test
	public void testMultExprB2B() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest_Generics.parseAction(
				"x = true / false;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(
				":1:12-16: Incompatible operands for multiplication expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:18: unexpected token: null", lines[1]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
		LiteralBoolean_c[] lbool = LiteralBoolean_c
				.LiteralBooleanInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, lbool.length);
	}

}
