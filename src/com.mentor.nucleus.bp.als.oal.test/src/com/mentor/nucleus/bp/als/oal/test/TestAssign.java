//========================================================================
//
//File:      $RCSfile: TestAssign.java,v $
//Version:   $Revision: 1.18 $
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

import java.util.UUID;

import junit.framework.TestCase;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.mentor.nucleus.bp.core.AttributeValueReference_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.BridgeBody_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InstanceHandle_c;
import com.mentor.nucleus.bp.core.InstanceReference_c;
import com.mentor.nucleus.bp.core.InstanceSetReference_c;
import com.mentor.nucleus.bp.core.InstanceSet_c;
import com.mentor.nucleus.bp.core.LiteralInteger_c;
import com.mentor.nucleus.bp.core.OperationBody_c;
import com.mentor.nucleus.bp.core.StateActionBody_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.TransientValueReference_c;
import com.mentor.nucleus.bp.core.TransientVar_c;
import com.mentor.nucleus.bp.core.TransitionActionBody_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class TestAssign extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();

        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_INT_TO_REAL_PROMOTION,
                MessageDialogWithToggle.ALWAYS);
        store.setValue(BridgePointPreferencesStore.ALLOW_REAL_TO_INT_COERCION,
                MessageDialogWithToggle.ALWAYS);
    }

    protected void tearDown() throws Exception {
        try {
            super.tearDown();
            OalParserTest.tearDownActionData();
        } catch (RecognitionException re) {
            // do nothing
        } catch (TokenStreamException te) {
            // do nothing
        }
    }

	public void AssignTypeV2Vtest(String stmts, UUID dt1_id, UUID dt2_id,
			int numTV, int numIRV, int numIRS) throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest.parseAction(stmts,
				OalParserTest.ACTIVITY_TYPE_FUNC,
				OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c
				.StatementInstances(OalParserTest.modelRoot);
		assertEquals(3, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(2, t.length);
		assertEquals("x", t[0].getName()); //$NON-NLS-1$
		assertEquals("y", t[1].getName()); //$NON-NLS-1$
		if (numIRV == 2) {
			InstanceHandle_c[] ir = InstanceHandle_c
					.InstanceHandleInstances(OalParserTest.modelRoot);
			assertEquals(numIRV, ir.length);
			InstanceReference_c[] ir_val = InstanceReference_c
					.InstanceReferenceInstances(OalParserTest.modelRoot);
			assertEquals(numIRV, ir_val.length);
			TransientValueReference_c[] val = TransientValueReference_c
					.TransientValueReferenceInstances(OalParserTest.modelRoot);
			assertEquals(0, val.length);
			assertEquals(ir_val[0].getVar_id(), ir[0].getVar_id());
		} else if (numIRS == 2) {
			InstanceSet_c[] ir = InstanceSet_c
					.InstanceSetInstances(OalParserTest.modelRoot);
			assertEquals(numIRS, ir.length);
			InstanceSetReference_c[] ir_val = InstanceSetReference_c
					.InstanceSetReferenceInstances(OalParserTest.modelRoot);
			assertEquals(numIRS, ir_val.length);
			TransientValueReference_c[] val = TransientValueReference_c
					.TransientValueReferenceInstances(OalParserTest.modelRoot);
			assertEquals(0, val.length);
			assertEquals(ir_val[0].getVar_id(), ir[0].getVar_id());
		}
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	}

	public void testAssignTypeBooleanV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "boolean");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"assign x = true; assign y = false; assign x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeIntV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "integer");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"assign x = 1; assign y = 27; assign x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeStringV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "string");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"assign x = \"a\"; assign y = \"b\"; assign x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeRealV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "real");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"assign x = 1.3; assign y = 2.7; assign x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeIRO2IRO() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"select any x from instances of D_D; select any y from instances of D_D; assign x = y; ", id, id, 0, 2, 0); //$NON-NLS-1$
	}

	public void testAssignTypeIRS2IRS() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"select many x from instances of D_D; select many y from instances of D_D; assign x = y; ", id, id, 0, 0, 2); //$NON-NLS-1$
	}

	public void testAssignTypeIRO2IROImplicit() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest
				.parseAction(
						"select any x from instances of D_D; assign y = x; z = y.Disk_ID;", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		InstanceHandle_c[] ir = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
		assertEquals(2, ir.length);
		InstanceReference_c[] ir_val = InstanceReference_c
				.InstanceReferenceInstances(OalParserTest.modelRoot);
		assertEquals(3, ir_val.length);
		TransientValueReference_c[] val = TransientValueReference_c
				.TransientValueReferenceInstances(OalParserTest.modelRoot);
		assertEquals(1, val.length);
		assertEquals(ir_val[0].getVar_id(), ir[0].getVar_id());
		Statement_c[] st = Statement_c
				.StatementInstances(OalParserTest.modelRoot);
		assertEquals(3, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(3, t.length);
		AttributeValueReference_c[] attr_val = AttributeValueReference_c
				.AttributeValueReferenceInstances(OalParserTest.modelRoot);
		assertEquals(1, attr_val.length);
	}

	public void testAssignTypeIRS2IRSImplicit() throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest
				.parseAction(
						"select many x from instances of D_D; assign y = x; for each y1 in y end for;", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Variable_c[] var = Variable_c
				.VariableInstances(OalParserTest.modelRoot);
		assertEquals(3, var.length);
		assertEquals("x", var[0].getName());
		assertEquals("y", var[1].getName());
		assertEquals("y1", var[2].getName());

		InstanceSet_c[] is = InstanceSet_c
				.InstanceSetInstances(OalParserTest.modelRoot);
		assertEquals(2, is.length);
		assertEquals(var[0].getVar_id(), is[0].getVar_id());
		assertEquals(var[1].getVar_id(), is[1].getVar_id());
		InstanceSetReference_c[] isr_val = InstanceSetReference_c
				.InstanceSetReferenceInstances(OalParserTest.modelRoot);
		assertEquals(2, isr_val.length);
		assertEquals(isr_val[0].getVar_id(), is[0].getVar_id());
		Statement_c[] st = Statement_c
				.StatementInstances(OalParserTest.modelRoot);
		assertEquals(3, st.length);
		InstanceReference_c[] ir_val = InstanceReference_c
				.InstanceReferenceInstances(OalParserTest.modelRoot);
		assertEquals(0, ir_val.length);
		TransientValueReference_c[] val = TransientValueReference_c
				.TransientValueReferenceInstances(OalParserTest.modelRoot);
		assertEquals(0, val.length);
	}

	public void testIAssignTypeBooleanV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "boolean");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"x = true; y = false; x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeIntV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "integer");//$NON-NLS-1$
		AssignTypeV2Vtest("x = 1; y = 27; x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeStringV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "string");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"x = \"a\"; y = \"b\"; x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeRealV2V() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "real");//$NON-NLS-1$
		AssignTypeV2Vtest("x = 1.3; y = 2.7; x = y; ", id, id, 2, 0, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeIV2RV() throws RecognitionException,
			TokenStreamException {
		UUID id1 = BaseTest.getTypeID(OalParserTest.modelRoot, "real");//$NON-NLS-1$
		UUID id2 = BaseTest.getTypeID(OalParserTest.modelRoot, "integer");//$NON-NLS-1$
		AssignTypeV2Vtest("x = 5.13; y = 99; x = y; ", id1, id2, 2, 0, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeRV2IV() throws RecognitionException,
			TokenStreamException {
		UUID id1 = BaseTest.getTypeID(OalParserTest.modelRoot, "real");//$NON-NLS-1$
		UUID id2 = BaseTest.getTypeID(OalParserTest.modelRoot, "integer");//$NON-NLS-1$
		AssignTypeV2Vtest("x = 1; y = 3.14; x = y; ", id2, id1, 2, 0, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeIRO2IRO() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"select any x from instances of D_D; select any y from instances of D_D; x = y; ", id, id, 0, 2, 0); //$NON-NLS-1$
	}

	public void testIAssignTypeIRS2IRS() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		AssignTypeV2Vtest(
				"select many x from instances of D_D; select many y from instances of D_D; x = y; ", id, id, 0, 0, 2); //$NON-NLS-1$
	}
	public void testTransValPop() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("x = 1; y = x; z = y; z = x;", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(4, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(3, t.length);
		assertEquals("x", t[0].getName()); //$NON-NLS-1$
		assertEquals("y", t[1].getName()); //$NON-NLS-1$
		assertEquals("z", t[2].getName()); //$NON-NLS-1$
		TransientVar_c[] tv = TransientVar_c.TransientVarInstances(OalParserTest.modelRoot);
		assertEquals(3, tv.length);
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "integer");//$NON-NLS-1$
		assertEquals(id, t[0].getDt_id());
		assertEquals(id, t[1].getDt_id());
		assertEquals(id, t[2].getDt_id());
		TransientValueReference_c[] val = TransientValueReference_c.TransientValueReferenceInstances(OalParserTest.modelRoot);
		assertEquals(7, val.length);
		assertEquals(val[0].getVar_id(), t[0].getVar_id());
		assertEquals(val[1].getVar_id(), t[1].getVar_id());
        assertEquals(val[2].getVar_id(), t[0].getVar_id());
        assertEquals(val[3].getVar_id(), t[2].getVar_id());
        assertEquals(val[4].getVar_id(), t[1].getVar_id());
        assertEquals(val[5].getVar_id(), t[2].getVar_id());
		assertEquals(val[6].getVar_id(), t[0].getVar_id());
		LiteralInteger_c[] lin = LiteralInteger_c.LiteralIntegerInstances(OalParserTest.modelRoot);
		assertEquals(1, lin.length);
		assertEquals("1", lin[0].getValue()); //$NON-NLS-1$
	}

	public void assignTypeMismatchTest(String stmts, UUID dt1, UUID dt2,
			int numTV, int numIRV, int numIRS) throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest.parseAction(stmts,
				OalParserTest.ACTIVITY_TYPE_FUNC,
				OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n"); //$NON-NLS-1$
		assertEquals(
				":3:5-5: Variable ->x<- already exists as a different type", lines[0]); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c
				.StatementInstances(OalParserTest.modelRoot);
		assertEquals(2, st.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(2, t.length);
		assertEquals("x", t[0].getName()); //$NON-NLS-1$
		assertEquals("y", t[1].getName()); //$NON-NLS-1$
		TransientVar_c[] tv = TransientVar_c
				.TransientVarInstances(OalParserTest.modelRoot);
		assertEquals(numTV, tv.length);
		if (numTV == 2) {
			assertEquals(dt1, t[0].getDt_id());
			assertEquals(dt2, t[1].getDt_id());
		}
		InstanceHandle_c[] iv = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
		assertEquals(numIRV, iv.length);
		InstanceSet_c[] isv = InstanceSet_c
				.InstanceSetInstances(OalParserTest.modelRoot);
		assertEquals(numIRS, isv.length);
	}

	public void testAssignTypeMismatchB2I() throws RecognitionException,
			TokenStreamException {
		UUID intId = BaseTest.getTypeID(OalParserTest.modelRoot, "integer");//$NON-NLS-1$
		UUID boolId = BaseTest.getTypeID(OalParserTest.modelRoot, "boolean");//$NON-NLS-1$

		assignTypeMismatchTest(
				"x = 1;\ny = true;\nx = y;\n ", intId, boolId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchS2I() throws RecognitionException,
			TokenStreamException {
		UUID intId = BaseTest.getTypeID(OalParserTest.modelRoot, "integer");//91//$NON-NLS-1$
		UUID strId = BaseTest.getTypeID(OalParserTest.modelRoot, "string");		//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = 1;\ny = \"test\";\nx = y;\n ", intId, strId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchB2S() throws RecognitionException,
			TokenStreamException {
		UUID strId = BaseTest.getTypeID(OalParserTest.modelRoot, "string");//93//$NON-NLS-1$
		UUID boolId = BaseTest.getTypeID(OalParserTest.modelRoot, "boolean");//90//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = \"test\";\ny = false;\nx = y;\n ", strId, boolId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchI2S() throws RecognitionException,
			TokenStreamException {
		UUID strId = BaseTest.getTypeID(OalParserTest.modelRoot, "string");//93//$NON-NLS-1$
		UUID intId = BaseTest.getTypeID(OalParserTest.modelRoot, "integer");//91		//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = \"test\";\ny = 2;\nx = y;\n ", strId, intId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchR2S() throws RecognitionException,
			TokenStreamException {
			UUID strId = BaseTest.getTypeID(OalParserTest.modelRoot, "string");//93//$NON-NLS-1$
			UUID realId = BaseTest.getTypeID(OalParserTest.modelRoot, "real");//92//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = \"test\";\ny = 2.17;\nx = y;\n ", strId, realId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchB2R() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID(OalParserTest.modelRoot, "boolean");//90//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID(OalParserTest.modelRoot, "real");//92	//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = 5.13;\ny = false;\nx = y;\n ", realId, boolId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchS2R() throws RecognitionException,
			TokenStreamException {
		UUID realId = BaseTest.getTypeID(OalParserTest.modelRoot, "real");//92//$NON-NLS-1$
		UUID strId = BaseTest.getTypeID(OalParserTest.modelRoot, "string");//93	//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = 99.0001;\ny = \"test\";\nx = y;\n ", realId, strId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchI2B() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID(OalParserTest.modelRoot, "boolean");//90//$NON-NLS-1$
		UUID intId = BaseTest.getTypeID(OalParserTest.modelRoot, "integer");//91//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = false;\ny = 1;\nx = y;\n ", boolId, intId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchR2B() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID(OalParserTest.modelRoot, "boolean");//90//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID(OalParserTest.modelRoot, "real");//92//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = false;\ny = 1.1111;\nx = y;\n ", boolId, realId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchS2B() throws RecognitionException,
			TokenStreamException {
		UUID boolId = BaseTest.getTypeID(OalParserTest.modelRoot, "boolean");//90//$NON-NLS-1$
		UUID strId = BaseTest.getTypeID(OalParserTest.modelRoot, "string");//93//$NON-NLS-1$
		assignTypeMismatchTest(
				"x = true;\ny = \"test\";\nx = y;\n ", boolId, strId, 2, 0, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchIRO2IRO() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "inst_ref<Object>");//97//$NON-NLS-1$
		assignTypeMismatchTest(
				"create object instance x of D_D;\n create object instance y of D_H;\nx = y;\n ", id, id, 0, 2, 0); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchIRO2IRS() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		assignTypeMismatchTest(
				"select many x from instances of D_D;\n select any y from instances of D_D;\nx = y;\n ", id, id, 0, 1, 1); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchIRS2IRO() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		assignTypeMismatchTest(
				"select any x from instances of D_D;\n select many y from instances of D_D;\nx = y;\n ", id, id, 0, 1, 1); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchIRO2IRSClass()
			throws RecognitionException, TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		assignTypeMismatchTest(
				"select many x from instances of D_D;\n select any y from instances of D_H;\nx = y;\n ", id, id, 0, 1, 1); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchIRS2IROClass()
			throws RecognitionException, TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		assignTypeMismatchTest(
				"select any x from instances of D_D;\n select many y from instances of D_H;\nx = y;\n ", id, id, 0, 1, 1); //$NON-NLS-1$
	}

	public void testAssignTypeMismatchIRS2IRS() throws RecognitionException,
			TokenStreamException {
		UUID id = BaseTest.getTypeID(OalParserTest.modelRoot, "inst_ref<Object>");//$NON-NLS-1$
		assignTypeMismatchTest(
				"select many x from instances of D_D;\n select many y from instances of D_H;\nx = y;\n ", id, id, 0, 0, 2); //$NON-NLS-1$
	}
	public void testAccessSelfFromFunction() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("sr2 = Self;", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n"); //$NON-NLS-1$
		assertEquals(":1:7-10: Keyword ->Self<- cannot be used in function AL specifications.", lines[0]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(0, st.length);
	}
	public void testAssignToSelf() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("self = self;", OalParserTest.ACTIVITY_TYPE_IB_OP, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n"); //$NON-NLS-1$
		assertEquals(":1:1-4: Cannot assign a value to ->self<-.", lines[0]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(0, st.length);
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_IB_OP, OalParserTest.TEST_VOID_NO_PARM);
	}
	public void testVarNotKnown() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("x = y;", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n"); //$NON-NLS-1$
		assertEquals(":1:5-5: Variable ->y<- used in context where it must already exist.", lines[0]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(0, st.length);
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	}
	public void testAccessSelfFromBridge() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("sr2 = Self;", OalParserTest.ACTIVITY_TYPE_BRG, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":1:7-10: Keyword ->Self<- cannot be used in bridge AL specifications.", lines[0]); //$NON-NLS-1$
		Body_c act = Body_c.getOneACT_ACTOnR698(BridgeBody_c.getOneACT_BRBOnR697(OalParserTest.m_testBrg[OalParserTest.TEST_VOID_NO_PARM]));
		Block_c blk_set[] = Block_c.getManyACT_BLKsOnR612(act);
		assertEquals(1, blk_set.length);
		Statement_c[] st = Statement_c.getManyACT_SMTsOnR602(blk_set);
		assertEquals(0, st.length);
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_BRG, OalParserTest.TEST_VOID_NO_PARM);
	}
	public void testAccessSelfFromCBOp() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("sr2 = Self;", OalParserTest.ACTIVITY_TYPE_CB_OP, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":1:7-10: Keyword ->Self<- cannot be used in class operation AL specifications.", lines[0]); //$NON-NLS-1$
		Body_c act = Body_c.getOneACT_ACTOnR698(OperationBody_c.getOneACT_OPBOnR696(OalParserTest.m_testCBTfr[OalParserTest.TEST_VOID_NO_PARM]));
		Block_c blk_set[] = Block_c.getManyACT_BLKsOnR612(act);
		assertEquals(1, blk_set.length);
		Statement_c[] st = Statement_c.getManyACT_SMTsOnR602(blk_set);
		assertEquals(0, st.length);
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_CB_OP, OalParserTest.TEST_VOID_NO_PARM);
	}
	public void testAccessSelfFromClassAction() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("sr2 = Self;", OalParserTest.ACTIVITY_TYPE_STATE, OalParserTest.STATE_ASM_ONE); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":1:7-10: Keyword ->Self<- cannot be used in class state AL specifications.", lines[0]); //$NON-NLS-1$
		Body_c act = Body_c.getOneACT_ACTOnR698(StateActionBody_c.getOneACT_SABOnR691(OalParserTest.m_testAction[OalParserTest.STATE_ASM_ONE]));
		Block_c blk_set[] = Block_c.getManyACT_BLKsOnR612(act);
		assertEquals(1, blk_set.length);
		Statement_c[] st = Statement_c.getManyACT_SMTsOnR602(blk_set);
		assertEquals(0, st.length);
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_STATE, OalParserTest.STATE_ASM_ONE);
	}
	public void testAccessSelfFromClassTransitionAction() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("sr2 = Self;", OalParserTest.ACTIVITY_TYPE_TRANSITION, OalParserTest.STATE_ASM_ONE); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":1:7-10: Keyword ->Self<- cannot be used in class transition AL specifications.", lines[0]); //$NON-NLS-1$
		Body_c act = Body_c.getOneACT_ACTOnR698(TransitionActionBody_c.getOneACT_TABOnR688(OalParserTest.m_testAction[OalParserTest.STATE_ASM_ONE]));
		Block_c blk_set[] = Block_c.getManyACT_BLKsOnR612(act);
		assertEquals(1, blk_set.length);
		Statement_c[] st = Statement_c.getManyACT_SMTsOnR602(blk_set);
		assertEquals(0, st.length);
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_TRANSITION, OalParserTest.STATE_ASM_ONE);
	}
	public void testAccessSelfFromIBOp() throws RecognitionException, TokenStreamException {
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_IB_OP, OalParserTest.TEST_VOID_NO_PARM);
		String x = OalParserTest.parseAction("sr2 = Self;", OalParserTest.ACTIVITY_TYPE_IB_OP, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal(1, 1, 2);
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_IB_OP, OalParserTest.TEST_VOID_NO_PARM);
	}
	public void testAccessSelfFromInstanceAction() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("sr2 = Self;", OalParserTest.ACTIVITY_TYPE_STATE, OalParserTest.STATE_ISM_ONE); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal(1, 1, 2);
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_STATE, OalParserTest.STATE_ISM_ONE);
	}
	public void testAccessSelfFromTransitionAction() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("sr2 = Self;", OalParserTest.ACTIVITY_TYPE_TRANSITION, OalParserTest.STATE_ISM_ONE); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal(1, 1, 2);
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_TRANSITION, OalParserTest.STATE_ISM_ONE);
	}
	public void testAssignBool2UDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_bool = false;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 2, 3 );
	}
	public void testAssignUDT2Bool() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; x = true; x = t.u_bool;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 3, 5 );
	}
	public void testAssignInt2UDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_int = 13;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 2, 3 );
	}
	public void testAssignUDT2Int() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; x = 12; x = t.u_int;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 3, 5 );
	}
	public void testAssignReal2UDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_real = 1.3;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 2, 3 );
	}
	public void testAssignUDT2Real() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; x = 1.2; x = t.u_real;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 3, 5 );
	}
 	public void testAssignInt2RealUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_real = 13;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 2, 3 );
	}
	public void testAssignRealUDT2Int() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; x = 12; x = t.u_real;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 3, 5 );
	}
	public void testAssignReal2IntUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_int = 1.3;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 2, 3 );
	}
	public void testAssignIntUDT2Real() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; x = 1.2; x = t.u_int;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 3, 5 );
	}
	public void testAssignString2UDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_str = \"test\";"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 2, 3 );
	}
	public void testAssignUDT2String() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; x = \"\"; x = t.u_str;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 3, 5 );
	}
	public void testAssignInt2StringUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_str = 23;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:49-50: Attribute ->u_str<- is a different type", lines[0]);
		assertEquals("line 1:52: expecting Semicolon, found 'null'", lines[1]);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testAssignBool2StringUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_str = true;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:49-52: Attribute ->u_str<- is a different type", lines[0]);
		assertEquals("line 1:54: expecting Semicolon, found 'null'", lines[1]);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testAssignReal2StringUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_str = 2.3;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:49-51: Attribute ->u_str<- is a different type", lines[0]);
		assertEquals("line 1:53: expecting Semicolon, found 'null'", lines[1]);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testAssignString2IntUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_int = \"test\";"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:49-54: Attribute ->u_int<- is a different type", lines[0]);
		assertEquals("line 1:56: expecting Semicolon, found 'null'", lines[1]);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testAssignBool2IntUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_int = true;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:49-52: Attribute ->u_int<- is a different type", lines[0]);
		assertEquals("line 1:54: expecting Semicolon, found 'null'", lines[1]);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testAssignString2RealUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_real = \"test\";"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:50-55: Attribute ->u_real<- is a different type", lines[0]);
		assertEquals("line 1:57: expecting Semicolon, found 'null'", lines[1]);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testAssignBool2RealUDT() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_real = true;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:50-53: Attribute ->u_real<- is a different type", lines[0]);
		assertEquals("line 1:55: expecting Semicolon, found 'null'", lines[1]);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testAssignTime2Date() throws RecognitionException, TokenStreamException {
		String act = "d = TIM::current_date(); t = TIM::current_clock(); d = t;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 3, 6 );
	}
	public void testAssignDate2Time() throws RecognitionException, TokenStreamException {
		String act = "d = TIM::current_date(); t = TIM::current_clock(); t = d;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 3, 6 );
	}
	public void testAssignUDT2DiffUDTSameCore() throws RecognitionException, TokenStreamException {
		String act = "select any dt from instances of D_DT; select any t from instances of D_TST; t.u_str = dt.Status;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 3, 4 );
	}

	public void testIllegalStringLiteral() throws RecognitionException, TokenStreamException {
		String act = "assign x = \"test;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:18: expecting '\"', found '<EOF>'", lines[0]);
		OalParserTest.validateBlkStmtVal( 0, 0, 0 );
	}
	public void testIllegalStringLiteral2() throws RecognitionException, TokenStreamException {
		String act = "assign x = test\";"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:18: expecting '\"', found '<EOF>'", lines[0]);
		OalParserTest.validateBlkStmtVal( 0, 0, 0 );
	}

	public void testReservedWordAsEnumDTName() throws RecognitionException, TokenStreamException {
		String act = "x = End::Start;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testReservedWordAsEnumeratorName() throws RecognitionException, TokenStreamException {
		String act = "x = End::End;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testEnumeratorWithUnderscore() throws RecognitionException, TokenStreamException {
		String act = "x = _testEnum::_test;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
	}
	public void testVarCaseSensitivity() throws RecognitionException, TokenStreamException {
		String act = "thiS = 1; thIs = 1.1;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 2, 4 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 2, vars.length );
	}
	public void testVarStartingWithRelid() throws RecognitionException, TokenStreamException {
		String act = "r1rel = 1;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testVarStartingWithRelid2() throws RecognitionException, TokenStreamException {
		String act = "r12rel = 1;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testVarStartingWithKeyword1() throws RecognitionException, TokenStreamException {
		String act = "inbox = 1;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testVarStartingWithKeyword2() throws RecognitionException, TokenStreamException {
		String act = "notok = 1;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testVarStartingWithKeyword3() throws RecognitionException, TokenStreamException {
		String act = "selfish = 1;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testVarStartingWithKeyword4() throws RecognitionException, TokenStreamException {
		String act = "oracle = 1;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testVarStartingWithUnderscore() throws RecognitionException, TokenStreamException {
		String act = "_x = 1;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testDebugKeywordAsVar() throws RecognitionException, TokenStreamException {
		String act = "debug = 1; trace = 2; on = 3; off = 4; dump = 5; sor = 6;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x);
		OalParserTest.validateBlkStmtVal( 1, 6, 12 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 6, vars.length );
	}
	public void testInvalidVarName() throws RecognitionException, TokenStreamException {
		String act = "1bad = 23;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals("line 1:1: expecting EOF, found '1'", lines[0]);//$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 0, 0 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 0, vars.length );
	}
	public void testAssignUDTUniqueID() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.u_uid = 5;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:49-49: Attribute ->u_uid<- is a different type", lines[0]);//$NON-NLS-1$
		assertEquals("line 1:51: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 1, 2 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 1, vars.length );
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	}
	public void testParamCaseSensitivity() throws RecognitionException, TokenStreamException {
		String act = "x = param.I;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST2);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(3, lines.length);
		assertEquals(":1:11-11: Parameter ->I<- is not associated with function ->test2<-", lines[0]);//$NON-NLS-1$
		assertEquals("line 1:13: unexpected token: null",lines[1]);//$NON-NLS-1$
		assertEquals("line 1:13: expecting Semicolon, found 'null'", lines[2]);//$NON-NLS-1$
		OalParserTest.validateBlkStmtVal( 1, 0, 1 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals( 1, vars.length );
		OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST2);
	}

}
