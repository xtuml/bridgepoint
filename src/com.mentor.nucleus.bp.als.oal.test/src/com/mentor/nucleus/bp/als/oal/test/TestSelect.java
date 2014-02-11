//========================================================================
//
//File:      $RCSfile: TestSelect.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/10 23:00:36 $
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

import org.eclipse.jface.preference.IPreferenceStore;

import junit.framework.TestCase;
import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.AttributeValueReference_c;
import com.mentor.nucleus.bp.core.BinaryOperation_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.ChainLink_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InstanceReference_c;
import com.mentor.nucleus.bp.core.InstanceSetReference_c;
import com.mentor.nucleus.bp.core.InstanceSet_c;
import com.mentor.nucleus.bp.core.InstanceHandle_c;
import com.mentor.nucleus.bp.core.LiteralInteger_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.SelectFromInstancesWhere_c;
import com.mentor.nucleus.bp.core.SelectFromInstances_c;
import com.mentor.nucleus.bp.core.Select_c;
import com.mentor.nucleus.bp.core.SelectedReference_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;

public class TestSelect extends TestCase {
    public TestSelect() {
        super();
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_OPERATIONS_IN_WHERE,
                true);
    }

    static public void badSelectValidate(String[] vars, int numStmt,
            int numSel, int numVal) throws RecognitionException,
            TokenStreamException {
        OalParserTest.badRelationshipValidate(vars, numStmt, numSel, numVal);
        ChainLink_c[] cl = ChainLink_c
                .ChainLinkInstances(OalParserTest.modelRoot);
        assertEquals(0, cl.length);
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

public void goodSelectValidate(
        boolean implicit,
        String mult,
        boolean from_inst,
        boolean where,
        int numStmt,
        int numSel,
        String[] var_list,
        int numVal,
        String finalKL,
        int relNum,
        String phrase,
        int numLinks,
        boolean normalWhereTest)
        throws RecognitionException, TokenStreamException
{
    goodSelectValidate(
            implicit,
            mult,
            from_inst,
            where,
            numStmt,
            numSel,
            var_list,
            numVal,
            finalKL,
            relNum,
            phrase,
            numLinks,
            normalWhereTest,
            false, // Not testing any attribute value instances
            1,     // Number of selected instances expected (zero or 1)
            0 );   // Default index in the value instances where the "selected" instance is
}
public void goodSelectValidate(
	boolean implicit,
	String mult,
	boolean from_inst,
	boolean where,
	int numStmt,
	int numSel,
	String[] var_list,
	int numVal,
	String finalKL,
	int relNum,
	String phrase,
	int numLinks,
	boolean normalWhereTest,
    boolean testAttrValInst,
    int numSrs,
    int indexValInstSR )
	throws RecognitionException, TokenStreamException {
	Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
	assertEquals(1, blk.length);
	Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
	assertEquals(numStmt, st.length);
	for (int i = 0; i < numStmt; ++i)
		assertEquals(blk[0].getBlock_id(), st[i].getBlock_id());
	Variable_c[] var = Variable_c.VariableInstances(OalParserTest.modelRoot);
	assertEquals(var_list.length, var.length);
	for (int i = 0; i < var_list.length; ++i)
		assertEquals(var_list[i], var[i].getName());
	if (from_inst && where) {
		if (mult != "many") {//$NON-NLS-1$
			InstanceHandle_c[] inst = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
			assertEquals(var_list.length, inst.length);
			for (int i = 0; i < var_list.length; ++i)
				assertEquals(var[i].getVar_id(), inst[i].getVar_id());
		} else {
			InstanceSet_c[] irs = InstanceSet_c.InstanceSetInstances(OalParserTest.modelRoot);
			assertEquals(var_list.length, irs.length);
			for (int i = 0; i < var_list.length; ++i)
				assertEquals(var[i].getVar_id(), irs[i].getVar_id());
		}
		SelectFromInstancesWhere_c sel[] = SelectFromInstancesWhere_c.SelectFromInstancesWhereInstances(OalParserTest.modelRoot);
		assertEquals(numSel, sel.length);
		// only verify the last one
		assertEquals(st[numStmt - 1].getStatement_id(), sel[numSel - 1].getStatement_id());
		assertEquals(var[0].getVar_id(), sel[numSel - 1].getVar_id());
		assertEquals(implicit, sel[numSel - 1].getIs_implicit());
		assertEquals(mult, sel[numSel - 1].getCardinality());
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR676(sel[numSel - 1]);
		assertEquals(finalKL, obj.getKey_lett());
		Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
		assertEquals(numVal, val.length);
        int indexSR = 0, indexAV = 0, indexBO = 0, indexLI = 0;
        AttributeValueReference_c[] av = AttributeValueReference_c.AttributeValueReferenceInstances(OalParserTest.modelRoot);
        if ( testAttrValInst == true ) {
            indexSR = indexValInstSR; indexAV = indexSR+1; indexBO = indexAV+1; indexLI = indexBO+1;
            assertEquals(1, av.length);
            assertEquals(av[0].getValue_id(), val[indexAV].getValue_id());
        } else {
            indexSR = 0; indexAV = -1; indexBO = 1; indexLI = 2;
            indexSR = indexValInstSR; indexAV = -1; indexBO = indexSR+1; indexLI = indexBO+1;
        }
        SelectedReference_c[] sr = SelectedReference_c.SelectedReferenceInstances(OalParserTest.modelRoot);
		assertEquals(numSrs, sr.length);
		if (numSrs == 1) {
          assertEquals(val[indexSR].getValue_id(), sr[0].getValue_id());
          BinaryOperation_c[] bin = BinaryOperation_c.BinaryOperationInstances(OalParserTest.modelRoot);
          assertEquals(1, bin.length);
          assertEquals(val[indexBO].getValue_id(), bin[0].getValue_id());
  		  if (normalWhereTest) {
			LiteralInteger_c[] lin = LiteralInteger_c.LiteralIntegerInstances(OalParserTest.modelRoot);
			assertEquals(1, lin.length);
			assertEquals("1", lin[0].getValue());//$NON-NLS-1$
			assertEquals(lin[0].getValue_id(), val[indexLI].getValue_id());
            if ( testAttrValInst == true ) {
                OalParserTest.verifyBinaryOperation(bin[0], "==", av[0].getValue_id(), lin[0].getValue_id());//$NON-NLS-1$
            } else {
                OalParserTest.verifyBinaryOperation(bin[0], "==", sr[0].getValue_id(), lin[0].getValue_id());//$NON-NLS-1$
            }
		  } else {
			InstanceReference_c[] ir_val = InstanceReference_c.InstanceReferenceInstances(OalParserTest.modelRoot);
			assertEquals(1, ir_val.length);
			assertEquals(ir_val[0].getValue_id(), val[indexLI].getValue_id());
            if ( testAttrValInst == true ) {
                OalParserTest.verifyBinaryOperation(bin[0], "==", av[0].getValue_id(), ir_val[0].getValue_id());//$NON-NLS-1$
            } else {
                OalParserTest.verifyBinaryOperation(bin[0], "==", sr[0].getValue_id(), ir_val[0].getValue_id());//$NON-NLS-1$
            }
		  }
		}
	} else if (from_inst && !where) {
		if (mult != "many") {//$NON-NLS-1$
			InstanceHandle_c[] inst = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
			assertEquals(var_list.length, inst.length);
			for (int i = 0; i < var_list.length; ++i)
				assertEquals(var[i].getVar_id(), inst[i].getVar_id());
		} else {
			InstanceSet_c[] irs = InstanceSet_c.InstanceSetInstances(OalParserTest.modelRoot);
			assertEquals(var_list.length, irs.length);
			for (int i = 0; i < var_list.length; ++i)
				assertEquals(var[i].getVar_id(), irs[i].getVar_id());
		}
		SelectFromInstances_c sel[] = SelectFromInstances_c.SelectFromInstancesInstances(OalParserTest.modelRoot);
		assertEquals(numSel, sel.length);
		// only verify the last one
		assertEquals(st[numStmt - 1].getStatement_id(), sel[numSel - 1].getStatement_id());
		assertEquals(var[0].getVar_id(), sel[numSel - 1].getVar_id());
		assertEquals(implicit, sel[numSel - 1].getIs_implicit());
		assertEquals(mult, sel[numSel - 1].getCardinality());
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR677(sel[numSel - 1]);
		assertEquals(finalKL, obj.getKey_lett());
	} else if (!from_inst) {
		if (!where) {
			Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
			assertEquals(numSel, sel.length);
			// only verify the last statement
			assertEquals(st[numStmt - 1].getStatement_id(), sel[numSel - 1].getStatement_id());
			Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
			assertEquals(numVal, val.length);
			assertEquals(val[0].getValue_id(), sel[numSel - 1].getValue_id());
			InstanceReference_c irf = InstanceReference_c.getOneV_IRFOnR801(val[0]);
			if ( irf == null )
			{
				InstanceSetReference_c irs = InstanceSetReference_c.getOneV_ISROnR801(val[0]);
				assertNotNull(irs);
			}
			assertEquals(implicit, sel[numSel - 1].getIs_implicit());
			assertEquals(mult, sel[numSel - 1].getCardinality());
		} else // !from_inst && where
			{
			Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
            assertEquals(numVal, val.length);

            int indexSR = 0, indexAV = 0, indexBO = 0, indexLI = 0;
            AttributeValueReference_c[] av = AttributeValueReference_c.AttributeValueReferenceInstances(OalParserTest.modelRoot);
            if ( testAttrValInst == true ) {
                indexSR = indexValInstSR; indexAV = indexSR+1; indexBO = indexAV+1; indexLI = indexBO+1;
                assertEquals(1, av.length);
                assertEquals(av[0].getValue_id(), val[indexAV].getValue_id());
            } else {
                indexSR = indexValInstSR; indexAV = -1; indexBO = indexSR+1; indexLI = indexBO+1;
            }
            if (numSrs == 1) {
			  LiteralInteger_c[] lin = LiteralInteger_c.LiteralIntegerInstances(OalParserTest.modelRoot);
			  assertEquals(1, lin.length);
			  assertEquals("1", lin[0].getValue());//$NON-NLS-1$
			  assertEquals(lin[0].getValue_id(), val[indexLI].getValue_id());
			  SelectedReference_c[] sr = SelectedReference_c.SelectedReferenceInstances(OalParserTest.modelRoot);
			  assertEquals(1, sr.length);
			  assertEquals(val[indexSR].getValue_id(), sr[0].getValue_id());
			  BinaryOperation_c[] bin = BinaryOperation_c.BinaryOperationInstances(OalParserTest.modelRoot);
			  assertEquals(1, bin.length);
			  assertEquals(val[indexBO].getValue_id(), bin[0].getValue_id());
              if ( testAttrValInst == true ) {
	            OalParserTest.verifyBinaryOperation(bin[0], "==", av[0].getValue_id(), lin[0].getValue_id());//$NON-NLS-1$
	          } else {
	            OalParserTest.verifyBinaryOperation(bin[0], "==", sr[0].getValue_id(), lin[0].getValue_id());//$NON-NLS-1$
	          }
            }
		}
		Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
		assertEquals(numVal, val.length);
		Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
		if (mult != "many") {//$NON-NLS-1$
			InstanceHandle_c inst_var = InstanceHandle_c.getOneV_INTOnR814(Variable_c.getOneV_VAROnR638(sel[0]));
            ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(inst_var);
			assertEquals(finalKL, mc.getKey_lett());
		} else {
			InstanceSet_c inst_var = InstanceSet_c.getOneV_INSOnR814(Variable_c.getOneV_VAROnR638(sel[0]));
            ModelClass_c mc = ModelClass_c.getOneO_OBJOnR819(inst_var);
			assertEquals(finalKL, mc.getKey_lett());
		}
		if (numLinks == 1) {
			ChainLink_c cl = ChainLink_c.getOneACT_LNKOnR637(sel[0]);
			chainLinkValidate(cl, phrase, finalKL, relNum, IdAssigner.NULL_UUID);
		}
	}
}
public void testSelectAnyFromImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any x from instances of D_D;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	goodSelectValidate(true, "any", true, false, 1, 1, var_list, 1, "D_D", 4, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyFrom() throws RecognitionException, TokenStreamException {
	String act = "create object instance x of D_D; select any x from instances of D_D;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	goodSelectValidate(false, "any", true, false, 2, 1, var_list, 1, "D_D", 4, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}

public void testSelectAnyFromWhereImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any x from instances of D_D where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	goodSelectValidate(true, "any", true, true, 1, 1, var_list, 4, "D_D", 4, "", 1, true, true, 1, 0);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyFromWhere() throws RecognitionException, TokenStreamException {
	String act = "create object instance x of D_D; select any x from instances of D_D where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	goodSelectValidate(false, "any", true, true, 2, 1, var_list, 4, "D_D", 4, "", 1, true, true, 1, 0);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyFromWhereSelected() throws RecognitionException, TokenStreamException {
	String act = "create object instance x of D_D; select any x from instances of D_D where selected == x;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	goodSelectValidate(false, "any", true, true, 2, 1, var_list, 3, "D_D", 4, "", 1, false);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyFromWhereSelectedIOp() throws RecognitionException, TokenStreamException {
	String act = "create object instance x of D_D; select any x from instances of D_D where selected.testIBoolNoParm();"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
    // NOTE: This test is a bit different than others.  We simply make sure this
    //       one parses without error.  We can't call goodSelectValidate because
    //       the V_SLR class isn't setup to handle IB operation invocations
}
public void testSelectManyFromImplicit() throws RecognitionException, TokenStreamException {
	String act = "select many x from instances of D_D;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	goodSelectValidate(true, "many", true, false, 1, 1, var_list, 1, "D_D", 4, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyFrom() throws RecognitionException, TokenStreamException {
	String act = "select many x from instances of D_D;\nselect many x from instances of D_D;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	goodSelectValidate(false, "many", true, false, 2, 2, var_list, 1, "D_D", 4, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyFromWhereImplicit() throws RecognitionException, TokenStreamException {
	String act = "select many x from instances of D_D where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	goodSelectValidate(true, "many", true, true, 1, 1, var_list, 4, "D_D", 4, "", 1, true, true, 1, 0);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyFromWhere() throws RecognitionException, TokenStreamException {
	String act = "select many x from instances of D_D;\nselect many x from instances of D_D where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	goodSelectValidate(false, "many", true, true, 2, 1, var_list, 4, "D_D", 4, "", 1, true, true, 1, 0);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
private void chainLinkValidate(ChainLink_c cl, String phrase, String keyLett, int relNum, UUID nextLink) {
	assertNotNull(cl);
	assertEquals(phrase, cl.getRel_phrase());
	assertEquals(nextLink, cl.getNext_link_id());
	Association_c rel = Association_c.getOneR_RELOnR681(cl);
	assertNotNull(rel);
	assertEquals(relNum, rel.getNumb());
	ModelClass_c obj = ModelClass_c.getOneO_OBJOnR678(cl);
	assertNotNull(obj);
	assertEquals(keyLett, obj.getKey_lett());
}
public void testSelectOneRelatedByImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one h related by d->D_H[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "h" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_H", 4, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
    OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
}
public void testSelectOneRelatedByImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one h related by d->D_H[R4] where ( selected.Row_Number == 1);"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "h" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, true, 2, 1, var_list, 5, "D_H", 4, "", 1, true, true, 1, 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
}
public void testSelectOneRelatedByImplicitFromSelf() throws RecognitionException, TokenStreamException {
	String act = "select one d related by self->D_D[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_IB_OP, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "self" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 1, 1, var_list, 1, "D_D", 4, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_IB_OP, OalParserTest.TEST_VOID_NO_PARM);
}
public void testSelectOneRelatedByImplicitWhereFromSelf() throws RecognitionException, TokenStreamException {
	String act = "select one d related by self->D_D[R4] where ( selected.Disk_ID == 1);"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_IB_OP, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "self" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, true, 1, 1, var_list, 5, "D_D", 4, "", 1, true, true, 1, 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_IB_OP, OalParserTest.TEST_VOID_NO_PARM);
}

public void testSelectUnformalized() throws RecognitionException, TokenStreamException {
	String act = "select any tst from instances of D_TST; select one h related by tst->D_H[R16];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "tst", "h" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_H", 16, "", 1, false);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectUnformalizedWithPhrase() throws RecognitionException, TokenStreamException {
	String act = "select any tst from instances of D_TST; select one h related by tst->D_H[R16.'ends'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "tst", "h" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_H", 16, "'ends'", 1, false);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectUnformalizedOtherDirection() throws RecognitionException, TokenStreamException {
	String act = "select any h from instances of D_H; select one tst related by h->D_TST[R16];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "h", "tst" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_TST", 16, "", 1, false);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectUnformalizedOtherDirectionWithPhrase() throws RecognitionException, TokenStreamException {
	String act = "select any h from instances of D_H; select one tst related by h->D_TST[R16.'starts'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "h", "tst" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_TST", 16, "'starts'", 1, false);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectUnformalizedReflexiveWithPhrase() throws RecognitionException, TokenStreamException {
	String act = "select any tst from instances of D_TST; select one tst2 related by tst->D_TST[R17.'starts'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "tst", "tst2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_TST", 17, "'starts'", 1, false);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectUnformalizedReflexiveOtherDirectionWithPhrase() throws RecognitionException, TokenStreamException {
	String act = "select any tst from instances of D_TST; select one tst2 related by tst->D_TST[R17.'ends'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "tst", "tst2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_TST", 17, "'ends'", 1, false);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}


private void validateNLinkChain(Select_c sel, int num, String [] objs, int relNum, String[] phrases) {
	ChainLink_c cl = ChainLink_c.getOneACT_LNKOnR637(sel);
	class ChainLink_test1_c implements ClassQueryInterface_c {
		UUID m_id;
		ChainLink_test1_c(UUID p_id) {
			m_id = p_id;
		}
		public boolean evaluate(Object inst) {
			ChainLink_c selected = (ChainLink_c)inst;
			return selected.getLink_id().equals(m_id);
		}
	}
	for ( int i =0; i < num; ++i) {
		ChainLink_c[] cl2 = ChainLink_c.ChainLinkInstances(OalParserTest.modelRoot, new ChainLink_test1_c(cl.getNext_link_id()));
		if ( i == num - 1 ) {
			assertEquals(0, cl2.length);
			chainLinkValidate(cl, phrases[i], objs[i], relNum, IdAssigner.NULL_UUID);
		}
		else {
			assertEquals(1, cl2.length);
			chainLinkValidate(cl, phrases[i], objs[i], relNum, cl2[0].getLink_id());
			cl = cl2[0];
		}
	}
}
public void testSelectOneRelatedBy2Links() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one d related by d->D_H[R4]->D_D[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	goodSelectValidate(false, "one", false, false, 2, 1, var_list, 1, "D_D", 4, "", 2, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
	String[] phrases = { "", "" };
	String[] kls = { "D_H", "D_D" };
	validateNLinkChain(sel[0], 2, kls, 4, phrases);//$NON-NLS-1$//$NON-NLS-2$
}
public void testSelectOneRelatedBy2LinksWith1PhraseFirst() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one d related by d->D_H[R4.'is permanently assigned to']->D_D[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	goodSelectValidate(false, "one", false, false, 2, 1, var_list, 1, "D_D", 4, "'is permanently assigned to'", 2, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
	String[] phrases = { "'is permanently assigned to'", "" };
	String[] kls = { "D_H", "D_D" };
	validateNLinkChain(sel[0], 2, kls, 4, phrases);//$NON-NLS-1$//$NON-NLS-2$
}
public void testSelectOneRelatedBy2LinksWith1PhraseSecond() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one d related by d->D_H[R4]->D_D[R4.'is permanent home for'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	goodSelectValidate(false, "one", false, false, 2, 1, var_list, 1, "D_D", 4, "", 2, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
	String[] phrases = { "", "'is permanent home for'" };
	String[] kls = { "D_H", "D_D" };
	validateNLinkChain(sel[0], 2, kls, 4, phrases);//$NON-NLS-1$//$NON-NLS-2$
}
public void testSelectOneRelatedBy3LinksWith1PhraseMiddle() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one h related by d->D_H[R4]->D_D[R4.'is permanent home for']->D_H[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "h" };//$NON-NLS-1$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_H", 4, "", 2, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
	String[] phrases = { "", "'is permanent home for'", "" };
	String[] kls = { "D_H", "D_D", "D_H" };
	validateNLinkChain(sel[0], 3, kls, 4, phrases);//$NON-NLS-1$//$NON-NLS-2$
}
public void testSelectOneRelatedBy2LinksWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one d related by d->D_H[R4]->D_D[R4] where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	goodSelectValidate(false, "one", false, true, 2, 1, var_list, 5, "D_D", 4, "", 2, true, true, 1, 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
	InstanceHandle_c inst_var = InstanceHandle_c.getOneV_INTOnR814(Variable_c.getOneV_VAROnR638(sel[0]));
    ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(inst_var);
	assertEquals("D_D", mc.getKey_lett());//$NON-NLS-1$
	String[] phrases = { "", "" };
	String[] kls = { "D_H", "D_D" };
	validateNLinkChain(sel[0], 2, kls, 4, phrases);//$NON-NLS-1$//$NON-NLS-2$
}
public void testSelectAnyRelatedByManyImplicit() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D;\nselect any h related by d->D_H[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "h" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_H", 4, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	InstanceHandle_c[] inst = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
	assertEquals(1, inst.length);
	InstanceSet_c[] irs = InstanceSet_c.InstanceSetInstances(OalParserTest.modelRoot);
	assertEquals(1, irs.length);
}
public void testSelectAnyRelatedByManyImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D;\nselect any h related by d->D_H[R4] where ( selected.Row_Number == 1);"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "h" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, true, 2, 1, var_list, 5, "D_H", 4, "", 1, true, true, 1, 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	InstanceHandle_c[] inst = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
	assertEquals(1, inst.length);
	InstanceSet_c[] irs = InstanceSet_c.InstanceSetInstances(OalParserTest.modelRoot);
	assertEquals(1, irs.length);
}
public void testSelectManyRelatedByManyImplicit() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D;\nselect many h related by d->D_H[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "h" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "many", false, false, 2, 1, var_list, 1, "D_H", 4, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyRelatedByManyImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D;\nselect many h related by d->D_H[R4] where ( selected.Row_Number == 1);"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "h" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "many", false, true, 2, 1, var_list, 5, "D_H", 4, "", 1, true, true, 1, 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyRelatedByManyRelImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect any dq related by d->D_DQ[R1];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "dq" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_DQ", 1, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	InstanceHandle_c[] inst = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
	assertEquals(2, inst.length);
	InstanceSet_c[] irs = InstanceSet_c.InstanceSetInstances(OalParserTest.modelRoot);
	assertEquals(0, irs.length);
}
public void testSelectAnyRelatedByManyRelImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect any dq related by d->D_DQ[R1] where (selected.Qualified_Process_ID == 1);"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "dq" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, true, 2, 1, var_list, 5, "D_DQ", 1, "", 1, true, true, 1, 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	InstanceHandle_c[] inst = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
	assertEquals(2, inst.length);
	InstanceSet_c[] irs = InstanceSet_c.InstanceSetInstances(OalParserTest.modelRoot);
	assertEquals(0, irs.length);
}
public void testSelectManyRelatedByManyRelImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect many dq related by d->D_DQ[R1];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "dq" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "many", false, false, 2, 1, var_list, 1, "D_DQ", 1, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyRelatedByManyRelImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect many dq related by d->D_DQ[R1] where (selected.Qualified_Process_ID == 1);"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "dq" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "many", false, true, 2, 1, var_list, 5, "D_DQ", 1, "", 1, true, true, 1, 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectOneRelatedByAssocRelImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one qp related by d->D_QP[R2];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "qp" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_QP", 2, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectOneRelatedByAssocRelImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one qp related by d->D_QP[R2] where (selected.Qualified_Process_ID == 1);"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "qp" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, true, 2, 1, var_list, 5, "D_QP", 2, "", 1, true, true, 1, 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectOneRelatedByAssocRel2Implicit() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one qp related by d->D_DO[R2]->D_QP[R2];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "qp" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_QP", 2, "", 2, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
	assertEquals(1, val.length);
	Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
	InstanceHandle_c inst_var = InstanceHandle_c.getOneV_INTOnR814(Variable_c.getOneV_VAROnR638(sel[0]));
    ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(inst_var);
	assertEquals("D_QP", mc.getKey_lett());//$NON-NLS-1$
	String[] phrases = { "", "" };
	String[] kls = { "D_DO", "D_QP" };
	validateNLinkChain(sel[0], 2, kls, 2, phrases);//$NON-NLS-1$//$NON-NLS-2$
}
public void testSelectOneRelatedByAssocRel2ImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one qp related by d->D_DO[R2]->D_QP[R2] where selected.Qualified_Process_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "qp" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, true, 2, 1, var_list, 5, "D_QP", 2, "", 2, true, true, 1, 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
	InstanceHandle_c inst_var = InstanceHandle_c.getOneV_INTOnR814(Variable_c.getOneV_VAROnR638(sel[0]));
    ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(inst_var);
	assertEquals("D_QP", mc.getKey_lett());//$NON-NLS-1$
	String[] phrases = { "", "" };
	String[] kls = { "D_DO", "D_QP" };
	validateNLinkChain(sel[0], 2, kls, 2, phrases);//$NON-NLS-1$//$NON-NLS-2$
}
public void testSelectOneGoodReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D; select one d2 related by d->D_D[R12.'precedes'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "d2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_D", 12, "'precedes'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectOneGoodReflexive2() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D; select one d2 related by d->D_D[R12.'follows'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "d2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_D", 12, "'follows'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectOneGoodReflexive3() throws RecognitionException, TokenStreamException {
	String act = "select any t from instances of D_TST; select one t2 related by t->D_TST[R18.'has parent'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "t", "t2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_TST", 18, "'has parent'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyGoodReflexive() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D; select any d2 related by d->D_D[R12.'precedes'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "d2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_D", 12, "'precedes'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyGoodReflexive2() throws RecognitionException, TokenStreamException {
	String act = "select any t from instances of D_TST; select any t2 related by t->D_TST[R18.'is parent of'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "t", "t2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_TST", 18, "'is parent of'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyGoodReflexiveWhere() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D; select any d2 related by d->D_D[R12.'precedes'] where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "d2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, true, 2, 1, var_list, 5, "D_D", 12, "'precedes'", 1, true, true, 1, 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyGoodReflexive() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D; select many d2 related by d->D_D[R12.'follows'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "d2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "many", false, false, 2, 1, var_list, 1, "D_D", 12, "'follows'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyGoodReflexive2() throws RecognitionException, TokenStreamException {
	String act = "select any t from instances of D_TST; select many t2 related by t->D_TST[R18.'is parent of'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "t", "t2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "many", false, false, 2, 1, var_list, 1, "D_TST", 18, "'is parent of'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectOneAssocGoodReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any qpo from instances of D_QPO; select one qp related by qpo->D_QP[R13.'precedes'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "qpo", "qp" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_QP", 13, "'precedes'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectOneAssocGoodReflexive2() throws RecognitionException, TokenStreamException {
	String act = "select any qpo from instances of D_QPO; select one qp related by qpo->D_QP[R13.'follows'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "qpo", "qp" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_QP", 13, "'follows'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectOneAssocGoodReflexive3() throws RecognitionException, TokenStreamException {
	String act = "select any t from instances of D_TST; select one t2 related by t->D_TST[R19.'has parent'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "t", "t2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_TST", 19, "'has parent'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyAssocGoodReflexive() throws RecognitionException, TokenStreamException {
	String act = "select many qpo from instances of D_QPO; select any qp related by qpo->D_QP[R13.'precedes'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "qpo", "qp" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_QP", 13, "'precedes'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyAssocGoodReflexive2() throws RecognitionException, TokenStreamException {
	String act = "select any t from instances of D_TST; select any t2 related by t->D_TST[R19.'is parent of'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "t", "t2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_TST", 19, "'is parent of'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyAssocGoodReflexive() throws RecognitionException, TokenStreamException {
	String act = "select many qpo from instances of D_QPO; select many qp related by qpo->D_QP[R13.'follows'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "qpo", "qp" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "many", false, false, 2, 1, var_list, 1, "D_QP", 13, "'follows'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyAssocGoodReflexive2() throws RecognitionException, TokenStreamException {
   String act = "select any t from instances of D_TST; select many t2 related by t->D_TST[R19.'is parent of'];"; //$NON-NLS-1$
   String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
   assertEquals("", x); //$NON-NLS-1$
   String[] var_list = { "t", "t2" };//$NON-NLS-1$//$NON-NLS-2$
   goodSelectValidate(true, "many", false, false, 2, 1, var_list, 1, "D_TST", 19, "'is parent of'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectOneAssocRelGoodReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any t from instances of D_TST; select one t2 related by t->D_N[R19.'has parent'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "t", "t2" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_N", 19, "'has parent'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyAssocRelGoodReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any qp from instances of D_QP; select any qpo related by qp->D_QPO[R13.'precedes'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "qp", "qpo" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_QPO", 13, "'precedes'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyAssocRelGoodReflexive2() throws RecognitionException, TokenStreamException {
   String act = "select any qp from instances of D_QP; select any qp2 related by qp->D_QPO[R13.'precedes']->D_QP[R13.'precedes'];"; //$NON-NLS-1$
   String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
   assertEquals("", x); //$NON-NLS-1$
   String[] var_list = { "qp", "qp2" };//$NON-NLS-1$//$NON-NLS-2$
   goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_QP", 13, "'precedes'", 2, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
   Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
   InstanceHandle_c inst_var = InstanceHandle_c.getOneV_INTOnR814(Variable_c.getOneV_VAROnR638(sel[0]));
   ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(inst_var);
   assertEquals("D_QP", mc.getKey_lett());//$NON-NLS-1$
   String[] phrases = { "'precedes'", "'precedes'" };
   String[] kls = { "D_QPO", "D_QP" };
   validateNLinkChain(sel[0], 2, kls, 13, phrases);//$NON-NLS-1$//$NON-NLS-2$
}
public void testSelectAnyAssocRelGoodReflexive3() throws RecognitionException, TokenStreamException {
  String act = "select any qp from instances of D_QP; select any qp2 related by qp->D_QPO[R13.'precedes']->D_QP[R13.'follows'];"; //$NON-NLS-1$
  String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
  assertEquals("", x); //$NON-NLS-1$
  String[] var_list = { "qp", "qp2" };//$NON-NLS-1$//$NON-NLS-2$
  goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_QP", 13, "'precedes'", 2, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
  Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
  InstanceHandle_c inst_var = InstanceHandle_c.getOneV_INTOnR814(Variable_c.getOneV_VAROnR638(sel[0]));
  ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(inst_var);
  assertEquals("D_QP", mc.getKey_lett());//$NON-NLS-1$
  String[] phrases = { "'precedes'", "'follows'" };
  String[] kls = { "D_QPO", "D_QP" };
  validateNLinkChain(sel[0], 2, kls, 13, phrases);//$NON-NLS-1$//$NON-NLS-2$
}
public void testSelectAnyAssocRelGoodReflexive4() throws RecognitionException, TokenStreamException {
   String act = "select any t from instances of D_TST; select any t2 related by t->D_N[R19.'is parent of'];"; //$NON-NLS-1$
   String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
   assertEquals("", x); //$NON-NLS-1$
   String[] var_list = { "t", "t2" };//$NON-NLS-1$//$NON-NLS-2$
   goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_N", 19, "'is parent of'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyAssocRelGoodReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any qp from instances of D_QP; select many qpo related by qp->D_QPO[R13.'follows'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "qp", "qpo" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "many", false, false, 2, 1, var_list, 1, "D_QPO", 13, "'follows'", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
	assertEquals(1, val.length);
	Select_c sel[] = Select_c.SelectInstances(OalParserTest.modelRoot);
	InstanceSet_c inst_set_var = InstanceSet_c.getOneV_INSOnR814(Variable_c.getOneV_VAROnR638(sel[0]));
    ModelClass_c mc = ModelClass_c.getOneO_OBJOnR819(inst_set_var);
	assertEquals("D_QPO", mc.getKey_lett());//$NON-NLS-1$
	ChainLink_c cl = ChainLink_c.getOneACT_LNKOnR637(sel[0]);
	chainLinkValidate(cl, "'follows'", "D_QPO", 13, IdAssigner.NULL_UUID);//$NON-NLS-1$//$NON-NLS-2$
}
public void testSelectOneRelatedByFromSup() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one od related by d->D_OD[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "od" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_OD", 3, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyRelatedByFromManySup() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D;\nselect any od related by d->D_OD[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "od" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_OD", 3, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyRelatedByFromManySup() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D;\nselect many od related by d->D_OD[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "d", "od" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "many", false, false, 2, 1, var_list, 1, "D_OD", 3, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectOneRelatedByFromSub() throws RecognitionException, TokenStreamException {
	String act = "select any od from instances of D_OD;\nselect one d related by od->D_D[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "od", "d" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "one", false, false, 2, 1, var_list, 1, "D_D", 3, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyRelatedByFromManySub() throws RecognitionException, TokenStreamException {
	String act = "select many od from instances of D_OD;\nselect any d related by od->D_D[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "od", "d" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "any", false, false, 2, 1, var_list, 1, "D_D", 3, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectManyRelatedByFromManySub() throws RecognitionException, TokenStreamException {
	String act = "select many od from instances of D_OD;\nselect many d related by od->D_D[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	assertEquals("", x); //$NON-NLS-1$
	String[] var_list = { "od", "d" };//$NON-NLS-1$//$NON-NLS-2$
	goodSelectValidate(true, "many", false, false, 2, 1, var_list, 1, "D_D", 3, "", 1, true);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
public void testSelectAnyFromWhereImplicitNoSelected() throws RecognitionException, TokenStreamException {
	String act = "select any x from instances of D_D where true;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals("", lines[0]); //$NON-NLS-1$
	String[] var_list = {"x"};
	goodSelectValidate(true, "any", true, true, 1, 1, var_list, 1, "D_D", 1, "", 1, true, false, 0, 0);
}
public void testSelectOneRelatedByNoSelectedWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one h related by d->D_H[R4] where ( true );"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals("", lines[0]); //$NON-NLS-1$
	String[] var_list = { "d", "h" };//$NON-NLS-1$
	goodSelectValidate(true, "one", false, true, 2, 1, var_list, 2, "D_H", 4, "", 1, true, false, 0, 0);
}
public void testSelectOneFromImplicit() throws RecognitionException, TokenStreamException {
	String act = "select one x from instances of D_D;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":1:32-34: SELECT ONE cannot be used with FROM INSTANCES OF. Use SELECT ANY or SELECT MANY", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:36: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = {
	};
	OalParserTest.badRelationshipValidate(var_list, 0, 0, 0);
	Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
	assertEquals(0, val.length);
}
public void testSelectOneFrom() throws RecognitionException, TokenStreamException {
	String act = "create object instance x of D_D;\nselect one x from instances of D_D;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:32-34: SELECT ONE cannot be used with FROM INSTANCES OF. Use SELECT ANY or SELECT MANY", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:36: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 0, 0);
}
public void testSelectAnyRelatedByOneImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect any h related by d->D_H[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:34-34: No right class has multiplicity of many. With SELECT ANY, at least one class must have multiplicity of many", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:36: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAnyRelatedByOneImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect any h related by d->D_H[R4] where selected.Row_Number == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:65-65: No right class has multiplicity of many. With SELECT ANY, at least one class must have multiplicity of many", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:67: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectManyRelatedByOneImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect many h related by d->D_H[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:35-35: No right class has multiplicity of many. With SELECT MANY, at least one class must have multiplicity of many", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:37: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectManyRelatedByOneImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect many h related by d->D_H[R4] where selected.Row_Number == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:66-66: No right class has multiplicity of many. With SELECT MANY, at least one class must have multiplicity of many", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:68: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectOneRelatedByManyImplicit() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D;\nselect one h related by d->D_H[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:34-34: At least one association has a right class with multiplicity of many.  With SELECT ONE, all right classes must have multiplicity of one", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:36: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectOneRelatedByManyImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select many d from instances of D_D;\nselect one h related by d->D_H[R4] where selected.Row_Number == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:65-65: At least one association has a right class with multiplicity of many.  With SELECT ONE, all right classes must have multiplicity of one", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:67: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectOneRelatedByManyRelImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one dq related by d->D_DQ[R1];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:36-36: At least one association has a right class with multiplicity of many.  With SELECT ONE, all right classes must have multiplicity of one", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:38: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectOneRelatedByManyRelImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one dq related by d->D_DQ[R1] where selected.Qualified_Process_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:77-77: At least one association has a right class with multiplicity of many.  With SELECT ONE, all right classes must have multiplicity of one", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:79: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectOneRelatedByManyAssocRelImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one qp related by d->D_QP[R1];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:36-36: At least one association has a right class with multiplicity of many.  With SELECT ONE, all right classes must have multiplicity of one", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:38: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectOneRelatedByManyAssocRelImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one qp related by d->D_QP[R1] where selected.Qualified_Process_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:77-77: At least one association has a right class with multiplicity of many.  With SELECT ONE, all right classes must have multiplicity of one", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:79: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectOneRelatedByManyAssoc2RelImplicit() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one qp related by d->D_DQ[R1]->D_QP[R1];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:46-46: At least one association has a right class with multiplicity of many.  With SELECT ONE, all right classes must have multiplicity of one", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:48: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectOneRelatedByManyAssoc2RelImplicitWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one qp related by d->D_DQ[R1]->D_QP[R1] where selected.Qualified_Process_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:87-87: At least one association has a right class with multiplicity of many.  With SELECT ONE, all right classes must have multiplicity of one", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:89: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectOneRelatedBy2LinksBadWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one d related by d->D_H[R4]->D_D[R4] where selected.Disk_ID;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:60-66: Expression within Select Where clause does not yield boolean result", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:68: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectBinaryBadRelNum() throws RecognitionException, TokenStreamException {
	String act = "select any x from instances of D_D;\nselect one y related by x->D_D[R99];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals(":2:32-34: Cannot find specified association ->R99<-", lines[0]); //$NON-NLS-1$
	//assertEquals( "line 2:36: expecting Semicolon, found 'null'", lines[1] ); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectBinaryWrongRelNum() throws RecognitionException, TokenStreamException {
	String act = "select any x from instances of D_D;\nselect one y related by x->D_D[R4];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:34-34: The specified association ->R4<- does not exist between classes ->Disk<- and ->Disk<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:36: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:36: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectBinaryWrongPhraseNotReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any d1 from instances of D_D;\nselect one h related by d1->D_H[R4.'is permanent home for'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:59-59: Class ->Permanent Home in Library<- in association ->R4<- does not contain destination association phrase ->is permanent home for<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:61: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:61: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "d1" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectBinaryBadPhraseNotReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any d1 from instances of D_D;\nselect one h related by d1->D_H[R4.'bad phrase'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:48-48: Class ->Permanent Home in Library<- in association ->R4<- does not contain destination association phrase ->bad phrase<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:50: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:50: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "d1" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectBinaryBadPhraseReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any d1 from instances of D_D;\nselect one d2 related by d1->D_D[R12.'neither'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:47-47: Class ->Disk<- in reflexive association ->R12<- does not contain destination association phrase ->neither<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:49: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:49: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "d1" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectBinaryBadNoPhraseReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any d1 from instances of D_D;\nselect one d2 related by d1->D_D[R12];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:37-37: The destination association phrase must be specified for reflexive association ->R12<- between classes ->Disk<- and ->Disk<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:39: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:39: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "d1" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectUnformalizedReflexiveNoPhrase() throws RecognitionException, TokenStreamException {
	String act = "select any tst from instances of D_TST;\nselect one tst2 related by tst->D_TST[R17];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:42-42: The destination association phrase must be specified for reflexive association ->R17<- between classes ->Test<- and ->Test<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:44: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:44: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "tst" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectBinaryPhraseSubtype() throws RecognitionException, TokenStreamException {
	String act = "select any d1 from instances of D_D;\nselect one od related by d1->D_OD[R3.'is a'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:44-44: A destination association phrase is not allowed for a supertype/subtype association", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:46: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:46: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "d1" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectBinaryTwoSubtypes() throws RecognitionException, TokenStreamException {
	String act = "select any od1 from instances of D_OND;\nselect one od2 related by od1->D_OD[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:39-39: The classes ->Online Disk<- and ->Offline Disk<- are subtype classes in the association ->R3<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:41: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:41: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "od1" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAssocBadAone() throws RecognitionException, TokenStreamException {
	String act = "select any h from instances of D_H;\nselect any qp related by h->D_DQ[R1]->D_QP[R1];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals(":2:36-36: The specified association ->R1<- does not exist between classes ->Permanent Home in Library<- and ->Disk Request<-", lines[0]); //$NON-NLS-1$
	String[] var_list = { "h" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAssocBadAoneNoAssr() throws RecognitionException, TokenStreamException {
	String act = "select any h from instances of D_H;\nselect any qp related by h->D_QP[R1];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:36-36: The specified association ->R1<- does not exist between classes ->Permanent Home in Library<- and ->Qualified Process<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:38: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:38: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "h" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAssocBadAoth() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect any h related by d->D_DQ[R1]->D_H[R1];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:44-44: The specified association ->R1<- does not exist between classes ->Disk Request<- and ->Permanent Home in Library<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:46: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:46: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAssocBadAothNoAssr() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect any h related by d->D_H[R1];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:34-34: The specified association ->R1<- does not exist between classes ->Disk<- and ->Permanent Home in Library<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:36: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:36: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAssocBadAssr() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect any h related by d->D_H[R1]->D_QP[R1];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals(":2:34-34: The specified association ->R1<- does not exist between classes ->Disk<- and ->Permanent Home in Library<-", lines[0]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAssocBadReflexiveNoPhrase() throws RecognitionException, TokenStreamException {
	String act = "select any qp1 from instances of D_QP;\nselect any qp2 related by qp1->D_QPO[R13]->D_QP[R13];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals(":2:41-41: The destination association phrase must be specified for reflexive association ->R13<- between classes ->Qualified Process<- and ->Qualified Process Order<-", lines[0]); //$NON-NLS-1$
	String[] var_list = { "qp1" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAssocBadReflexiveNoPhraseNoAssr() throws RecognitionException, TokenStreamException {
	String act = "select any qp1 from instances of D_QP;\n select any qp2 related by qp1->D_QP[R13];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:41-41: The destination association phrase must be specified for reflexive association ->R13<- between classes ->Qualified Process<- and ->Qualified Process<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:43: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:43: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "qp1" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAssocBadReflexiveBadPhrase() throws RecognitionException, TokenStreamException {
	String act = "select any qp1 from instances of D_QP;\nselect any qp2 related by qp1->D_QPO[R13.'neither']->D_QP[R13];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals(":2:51-51: Class ->Qualified Process Order<- in reflexive association ->R13<- does not contain destination association phrase ->neither<-", lines[0]); //$NON-NLS-1$
	String[] var_list = { "qp1" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAssocBadReflexiveBadPhraseNoAssr() throws RecognitionException, TokenStreamException {
	String act = "select any qp1 from instances of D_QP;\nselect any qp2 related by qp1->D_QP[R13.'neither'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:50-50: Class ->Qualified Process<- in reflexive association ->R13<- does not contain destination association phrase ->neither<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:52: unexpected token: null", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:52: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "qp1" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectOneAssocBadReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any t from instances of D_TST; select one t2 related by t->D_TST[R19.'is parent of'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":1:91-91: At least one association has a right class with multiplicity of many.  With SELECT ONE, all right classes must have multiplicity of one", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:93: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "t" };//$NON-NLS-1$//$NON-NLS-2$
	OalParserTest.badRelationshipValidate(var_list, 1, 1, 0);
	Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
	assertEquals(0, val.length);
}
public void testSelectAnyAssocBadReflexive() throws RecognitionException, TokenStreamException {
	String act = "select any t from instances of D_TST; select any t2 related by t->D_TST[R19.'has parent'];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":1:89-89: No right class has multiplicity of many. With SELECT ANY, at least one class must have multiplicity of many", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:91: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "t" };//$NON-NLS-1$//$NON-NLS-2$
	OalParserTest.badRelationshipValidate(var_list, 1, 1, 0);
	Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
	assertEquals(0, val.length);
}
public void testSelectAnyFromBadVar() throws RecognitionException, TokenStreamException {
	String act = "x = 1;\nselect any x from instances of D_H;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:32-34: Variable ->x<- does not exist in scope as an object instance variable", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:36: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 0, 2);
}
public void testSelectAnyFromWrongVar() throws RecognitionException, TokenStreamException {
	String act = "create object instance x of D_D;\nselect any x from instances of D_H;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:32-34: Variable ->x<- already exists as a different type", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:36: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 0, 0);
}
public void testSelectManyFromBadVar() throws RecognitionException, TokenStreamException {
	String act = "x = 1;\nselect many x from instances of D_H;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:33-35: Variable ->x<- does not exist in scope as an object instance set variable", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:37: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 0, 2);
}
public void testSelectManyFromWrongVar() throws RecognitionException, TokenStreamException {
	String act = "select many x from instances of D_D;\nselect many x from instances of D_H;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:33-35: Variable ->x<- already exists as a different type", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:37: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectOneFromWhereImplicit() throws RecognitionException, TokenStreamException {
	String act = "select one x from instances of D_D where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":1:62-62: SELECT ONE cannot be used with FROM INSTANCES OF. Use SELECT ANY or SELECT MANY", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:64: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = {};
	badSelectValidate(var_list, 0, 0, 1);
}
public void testSelectOneFromWhere() throws RecognitionException, TokenStreamException {
	String act = "create object instance x of D_H;\nselect one x from instances of D_D where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:62-62: SELECT ONE cannot be used with FROM INSTANCES OF. Use SELECT ANY or SELECT MANY", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:64: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 0, 1);
}
public void testSelectAnyFromBadVarWhere() throws RecognitionException, TokenStreamException {
	String act = "x = 1;\nselect any x from instances of D_D where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:62-62: Variable ->x<- does not exist in scope as an object instance variable", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:64: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 0, 3);
}
public void testSelectAnyFromWrongVarWhere() throws RecognitionException, TokenStreamException {
	String act = "create object instance x of D_H;\nselect any x from instances of D_D where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:62-62: Variable ->x<- already exists as a different type", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:64: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 0, 1);
}
public void testSelectManyFromBadVarWhere() throws RecognitionException, TokenStreamException {
	String act = "x = 1;\nselect many x from instances of D_D where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:63-63: Variable ->x<- does not exist in scope as an object instance set variable", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:65: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 0, 3);
}
public void testSelectManyFromWrongVarWhere() throws RecognitionException, TokenStreamException {
	String act = "select many x from instances of D_H;\nselect many x from instances of D_D where selected.Disk_ID == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(":2:63-63: Variable ->x<- already exists as a different type", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:65: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectAnyFromWhereImplicitBadAttr() throws RecognitionException, TokenStreamException {
	String act = "select any x from instances of D_D where selected.Bad == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals(":1:51-53: ->Bad<- is not an attribute of class ->Disk<-.", lines[0]); //$NON-NLS-1$
	String[] var_list = {};
	badSelectValidate(var_list, 0, 0, 1);
}
public void testSelectManyFromWhereBadAttr() throws RecognitionException, TokenStreamException {
	String act = "select many x from instances of D_D;\nselect many x from instances of D_D where selected.Bad == 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals(":2:52-54: ->Bad<- is not an attribute of class ->Disk<-.", lines[0]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectAnyFromWhereImplicitJustSelected() throws RecognitionException, TokenStreamException {
	String act = "select any x from instances of D_D where selected;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":1:42-49: Expression within Select Where clause does not yield boolean result", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:51: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = {};
	badSelectValidate(var_list, 0, 0, 0);
}
public void testSelectOneRelatedByNotBooleanWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one h related by d->D_H[R4] where ( selected.Row_Number );"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":2:64-64: Expression within Select Where clause does not yield boolean result", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:66: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectOneRelatedByBadAttrWhere() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one h related by d->D_H[R4] where ( selected.xxx == 1 );"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":2:53-55: ->xxx<- is not an attribute of class ->Permanent Home in Library<-.", lines[0]); //$NON-NLS-1$
    assertEquals(":2:62-62: Expression within Select Where clause does not yield boolean result", lines[1]); //$NON-NLS-1$
    assertEquals("line 2:64: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectManyFromWhereNotBool() throws RecognitionException, TokenStreamException {
	String act = "select many x from instances of D_D;\nselect many x from instances of D_D where selected.Disk_ID;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":2:52-58: Expression within Select Where clause does not yield boolean result", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:60: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "x" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 1);
}
public void testSelectedVarOutsideWhere() throws RecognitionException, TokenStreamException {
	String act = "selected = 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(3, lines.length);
	assertEquals(":1:1-8: Keyword ->Selected<- cannot be used outside a where expression", lines[0]); //$NON-NLS-1$
    assertEquals("line 1:13: expecting TOK_EQUAL, found ';'", lines[1]); //$NON-NLS-1$
    assertEquals("line 1:14: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
	String[] var_list = {};
	badSelectValidate(var_list, 0, 0, 1);
}
public void testSelectedAttrOutsideWhere() throws RecognitionException, TokenStreamException {
	String act = "select any selected from instances of D_D;\nselected.Disk_ID = 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(4, lines.length);
	assertEquals(":1:12-19: Keyword ->Selected<- cannot be used outside a where expression", lines[0]); //$NON-NLS-1$
	assertEquals(":2:10-16: Keyword ->Selected<- cannot be used outside a where expression", lines[1]); //$NON-NLS-1$
	assertEquals("line 2:21: expecting TOK_EQUAL, found ';'", lines[2]); //$NON-NLS-1$
    assertEquals("line 2:22: expecting Semicolon, found 'null'", lines[3]); //$NON-NLS-1$
	String[] var_list = {};
	badSelectValidate(var_list, 0, 0, 3);
}
public void testSelectFromWhereSelectedMisspelled() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D where selectd.Disk_ID = 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals(":1:42-48: Variable ->selectd<- used in context where it must already exist.", lines[0]); //$NON-NLS-1$
	String[] var_list = {};
	badSelectValidate(var_list, 0, 0, 0);
}
public void testSelectRelatedByWhereSelectedMisspelled() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect one h related by d->D_H[R4] where slected.Disk_ID = 1;"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(1, lines.length);
	assertEquals(":2:42-48: Variable ->slected<- used in context where it must already exist.", lines[0]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAnyRelatedByFromSup() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect any od related by d->D_OD[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":2:36-36: No right class has multiplicity of many. With SELECT ANY, at least one class must have multiplicity of many", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:38: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectManyRelatedByFromSup() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;\nselect many od related by d->D_OD[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":2:37-37: No right class has multiplicity of many. With SELECT MANY, at least one class must have multiplicity of many", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:39: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "d" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectAnyRelatedByFromSub() throws RecognitionException, TokenStreamException {
	String act = "select any od from instances of D_OD;\nselect any d related by od->D_D[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":2:35-35: No right class has multiplicity of many. With SELECT ANY, at least one class must have multiplicity of many", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:37: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "od" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}
public void testSelectManyRelatedByFromSub() throws RecognitionException, TokenStreamException {
	String act = "select any od from instances of D_OD;\nselect many d related by od->D_D[R3];"; //$NON-NLS-1$
	String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":2:36-36: No right class has multiplicity of many. With SELECT MANY, at least one class must have multiplicity of many", lines[0]); //$NON-NLS-1$
	assertEquals("line 2:38: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
	String[] var_list = { "od" };//$NON-NLS-1$
	badSelectValidate(var_list, 1, 1, 0);
}

}
