//========================================================================
//
//File:      $RCSfile: TestInvocation_Generics.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/05/10 04:52:49 $
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

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;

import junit.framework.TestCase;
import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.mentor.nucleus.bp.core.ActualParameter_c;
import com.mentor.nucleus.bp.core.AssignToMember_c;
import com.mentor.nucleus.bp.core.AttributeValueReference_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.BridgeInvocation_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.BridgeValue_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.FunctionInvocation_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.FunctionValue_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.LiteralInteger_c;
import com.mentor.nucleus.bp.core.LiteralReal_c;
import com.mentor.nucleus.bp.core.LiteralString_c;
import com.mentor.nucleus.bp.core.OperationInvocation_c;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.OperationValue_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.ParameterValue_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class TestInvocation_Generics extends TestCase {
	private String[] m_invocation_prefix =
	{ "::", "bridge T::", "bridge S::",//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-1
	"T::", "S::", "D_D::", "create object instance h of D_H; h." };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-1
public void invocationTest(
	String act,
	String testName,
	String[] parms,
	String[] parmVals,
	int numVals,
	int intParmIndex,
	int strParmIndex)
	throws RecognitionException, TokenStreamException {
	for (int i = 0; i < m_invocation_prefix.length; ++i) {
		String x = OalParserTest_Generics.parseAction(m_invocation_prefix[i] + act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		assertEquals("", x);//$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		ActualParameter_c[] parm_list = ActualParameter_c.ActualParameterInstances(OalParserTest_Generics.modelRoot);
		assertEquals(parms.length, parm_list.length);
		LiteralInteger_c[] li = LiteralInteger_c.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		if (parms.length >= 1) {
			assertEquals(val[intParmIndex].getValue_id(), li[0].getValue_id());
			assertEquals(parmVals[intParmIndex], li[0].getValue());
		}
		LiteralString_c[] ls = LiteralString_c.LiteralStringInstances(OalParserTest_Generics.modelRoot);
		if (parms.length >= 2) {
			assertEquals(val[strParmIndex].getValue_id(), ls[0].getValue_id());
			assertEquals(parmVals[strParmIndex], ls[0].getValue());
		}
		if (i == 0) {
			OalParserTest_Generics.validateBlkStmtVal(1, 1, numVals);
			FunctionInvocation_c[] func = FunctionInvocation_c.FunctionInvocationInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, func.length);
			assertEquals(st[0].getStatement_id(), func[0].getStatement_id());
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			parm_list = ActualParameter_c.getManyV_PARsOnR669(func[0]);
			if (parms.length >= 1) {
				assertEquals(func[0].getStatement_id(), parm_list[0].getStatement_id());
			}
			if (parms.length >= 2) {
				assertEquals(func[0].getStatement_id(), parm_list[1].getStatement_id());
			}
			Function_c testFunc = Function_c.getOneS_SYNCOnR675(func[0]);
			assertEquals(testName, testFunc.getName());
		} else if (i >= 1 && i <= 4) {
			OalParserTest_Generics.validateBlkStmtVal(1, 1, numVals);
			BridgeInvocation_c[] brg = BridgeInvocation_c.BridgeInvocationInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, brg.length);
			assertEquals(st[0].getStatement_id(), brg[0].getStatement_id());
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			parm_list = ActualParameter_c.getManyV_PARsOnR628(brg[0]);
			if (parms.length >= 1) {
				assertEquals(brg[0].getStatement_id(), parm_list[0].getStatement_id());
			}
			if (parms.length >= 2) {
				assertEquals(brg[0].getStatement_id(), parm_list[1].getStatement_id());
			}
			Bridge_c testBrg = Bridge_c.getOneS_BRGOnR674(brg[0]);
			assertEquals(testName, testBrg.getName());
		} else if (i == 5 || i == 6) {
			OperationInvocation_c[] brg = OperationInvocation_c.OperationInvocationInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, brg.length);
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			parm_list = ActualParameter_c.getManyV_PARsOnR627(brg[0]);
			if (parms.length >= 1) {
				assertEquals(brg[0].getStatement_id(), parm_list[0].getStatement_id());
			}
			if (parms.length >= 2) {
				assertEquals(brg[0].getStatement_id(), parm_list[1].getStatement_id());
			}
			Operation_c testBrg = Operation_c.getOneO_TFROnR673(brg[0]);
			assertEquals(testName, testBrg.getName());
			if (i == 5) {
				OalParserTest_Generics.validateBlkStmtVal(1, 1, numVals);
				Variable_c t_var = Variable_c.getOneV_VAROnR667(brg[0]);
				assertEquals(null, t_var);
				assertEquals(st[0].getStatement_id(), brg[0].getStatement_id());
			} else {
				OalParserTest_Generics.validateBlkStmtVal(1, 2, numVals);
				assertEquals(st[1].getStatement_id(), brg[0].getStatement_id());
				Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
				assertEquals(1, var.length);
				assertEquals("h", var[0].getName());//$NON-NLS-1$
				assertEquals(var[0].getVar_id(), brg[0].getVar_id());
				assertEquals(st[1].getStatement_id(), brg[0].getStatement_id());
			}
		}
		if (parms.length == 0) {
			assertEquals(0, parm_list.length);
		} else if (parms.length == 1) {
			assertEquals(1, parm_list.length);
			assertEquals(li[intParmIndex].getValue_id(), parm_list[0].getValue_id());
			assertEquals(parms[intParmIndex], parm_list[0].getName());
			assertEquals(IdAssigner.NULL_UUID, parm_list[0].getNext_value_id());
		} else if (parms.length == 2) {
			assertEquals(2, parm_list.length);
			assertEquals(li[0].getValue_id(), parm_list[1 - intParmIndex].getValue_id());
			assertEquals(parms[intParmIndex], parm_list[1 - intParmIndex].getName());
			assertEquals(ls[0].getValue_id(), parm_list[1 - strParmIndex].getValue_id());
			assertEquals(parms[strParmIndex], parm_list[1 - strParmIndex].getName());
			assertEquals(parm_list[0].getValue_id(), parm_list[1].getNext_value_id());
			assertEquals(IdAssigner.NULL_UUID, parm_list[0].getNext_value_id());
		}
	}
}
private String[][] m_rval_prefix = { { "", " = ::" }, {//$NON-NLS-1$//$NON-NLS-2$
		"bridge ", " = T::" }, {//$NON-NLS-1$//$NON-NLS-2$
		"bridge ", " = S::" }, {//$NON-NLS-1$//$NON-NLS-2$
		"", " = bridge T::" }, {//$NON-NLS-1$//$NON-NLS-2$
		"", " = bridge S::" }, {//$NON-NLS-1$//$NON-NLS-2$
		"", " = T::" }, {//$NON-NLS-1$//$NON-NLS-2$
		"", " = S::" }, {//$NON-NLS-1$//$NON-NLS-2$
		"", " = D_D::" }, {//$NON-NLS-1$//$NON-NLS-2$
		"create object instance h of D_H; ", " = h." }//$NON-NLS-1$//$NON-NLS-2$
};
public void invocationRvalTest(
        String prefix,
        String varName,
        String act,
        String testName,
        UUID ret_dt,
        String[] parms,
        String[] parmVals,
        int numStmts,
        int numVals,
        int intParmIndex,
        int strParmIndex,
        int realParmIndex) throws RecognitionException, TokenStreamException
{
    invocationRvalTest( prefix, varName, act, testName, ret_dt, parms,
            parmVals, numStmts, numVals, intParmIndex, strParmIndex,
            realParmIndex, 0, 1 );
}
public void invocationRvalTest(
	String prefix,
	String varName,
	String act,
	String testName,
	UUID ret_dt,
	String[] parms,
	String[] parmVals,
	int numStmts,
	int numVals,
	int intParmIndex,
	int strParmIndex,
	int realParmIndex,
    int keyValInstIndex,
    int funcValInstIndex )
	throws RecognitionException, TokenStreamException {
	for (int i = 0; i < m_rval_prefix.length; ++i) {
        String action = prefix + m_rval_prefix[i][0] + varName + m_rval_prefix[i][1] + act;
		String x =
			OalParserTest_Generics.parseAction(
				action,
				OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
				OalParserTest_Generics.TEST_VOID_NO_PARM);
		assertEquals("", x);//$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		Variable_c[] var = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		ActualParameter_c[] parm_list = ActualParameter_c.ActualParameterInstances(OalParserTest_Generics.modelRoot);
		assertEquals(parms.length, parm_list.length);
		LiteralInteger_c[] li = LiteralInteger_c.LiteralIntegerInstances(OalParserTest_Generics.modelRoot);
		if (parms.length >= 1) {
            assertEquals(val[intParmIndex + 2].getValue_id(), li[0].getValue_id());
			assertEquals(parmVals[intParmIndex], li[0].getValue());
		}
		LiteralString_c[] ls = LiteralString_c.LiteralStringInstances(OalParserTest_Generics.modelRoot);
		if (parms.length >= 2) {
            assertEquals(val[strParmIndex + 2].getValue_id(), ls[0].getValue_id());
			assertEquals(parmVals[strParmIndex], ls[0].getValue());
		}
		LiteralReal_c[] lr = LiteralReal_c.LiteralRealInstances(OalParserTest_Generics.modelRoot);
		if (parms.length >= 3) {
            assertEquals(val[realParmIndex + 2].getValue_id(), lr[0].getValue_id());
			assertEquals(parmVals[realParmIndex], lr[0].getValue());
		}
		UUID rval_id = IdAssigner.NULL_UUID;
		if (i == 0) {
			OalParserTest_Generics.validateBlkStmtVal(1, numStmts, numVals);
			assertEquals(ret_dt, val[keyValInstIndex].getDt_id());
			FunctionValue_c[] fval = FunctionValue_c.FunctionValueInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, fval.length);
			assertEquals(val[funcValInstIndex].getValue_id(), fval[0].getValue_id());
			parm_list = ActualParameter_c.getManyV_PARsOnR817(fval[0]);
			if (parms.length >= 1) {
				assertEquals(fval[0].getValue_id(), parm_list[0].getInvocation_value_id());
			}
			if (parms.length >= 2) {
				assertEquals(fval[0].getValue_id(), parm_list[1].getInvocation_value_id());
			}
			Function_c testFunc = Function_c.getOneS_SYNCOnR827(fval[0]);
			assertEquals(testName, testFunc.getName());
			assertEquals(testFunc.getDt_id(), val[keyValInstIndex].getDt_id());
			rval_id = fval[0].getValue_id();
		} else if (i >= 1 && i <= 6) {
			OalParserTest_Generics.validateBlkStmtVal(1, numStmts, numVals);
			BridgeValue_c[] brg = BridgeValue_c.BridgeValueInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, brg.length);
			assertEquals(val[funcValInstIndex].getValue_id(), brg[0].getValue_id());
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			parm_list = ActualParameter_c.getManyV_PARsOnR810(brg[0]);
			if (parms.length >= 1) {
				assertEquals(brg[0].getValue_id(), parm_list[0].getInvocation_value_id());
			}
			if (parms.length >= 2) {
				assertEquals(brg[0].getValue_id(), parm_list[1].getInvocation_value_id());
			}
			Bridge_c testBrg = Bridge_c.getOneS_BRGOnR828(brg[0]);
			assertEquals(testName, testBrg.getName());
			rval_id = brg[0].getValue_id();
		} else if (i == 7 || i == 8) {
			OperationValue_c[] brg = OperationValue_c.OperationValueInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, brg.length);
			assertEquals(val[funcValInstIndex].getValue_id(), brg[0].getValue_id());
			assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
			parm_list = ActualParameter_c.getManyV_PARsOnR811(brg[0]);
			if (parms.length >= 1) {
				assertEquals(brg[0].getValue_id(), parm_list[0].getInvocation_value_id());
			}
			if (parms.length >= 2) {
				assertEquals(brg[0].getValue_id(), parm_list[1].getInvocation_value_id());
			}
			Operation_c testBrg = Operation_c.getOneO_TFROnR829(brg[0]);
			assertEquals(testName, testBrg.getName());
			if (i == 7) {
				OalParserTest_Generics.validateBlkStmtVal(1, numStmts, numVals);
				Variable_c t_var = Variable_c.getOneV_VAROnR830(brg[0]);
				assertEquals(null, t_var);
			} else {
				OalParserTest_Generics.validateBlkStmtVal(1, numStmts + 1, numVals);
				assertEquals(2, var.length);
				if (varName.length() == 1) {
					assertEquals(2, var.length);
					assertEquals("h", var[0].getName());//$NON-NLS-1$
					assertEquals(var[0].getVar_id(), brg[0].getVar_id());
					assertEquals("x", var[1].getName());//$NON-NLS-1$
				} else {
					assertEquals("x", var[0].getName());//$NON-NLS-1$
					assertEquals("h", var[1].getName());//$NON-NLS-1$
					assertEquals(var[1].getVar_id(), brg[0].getVar_id());
				}
			}
			rval_id = brg[0].getValue_id();
		}
		if (varName.length() != 1) {
			AssignToMember_c[] ata = AssignToMember_c.AssignToMemberInstances(OalParserTest_Generics.modelRoot);
			assertEquals(1, ata.length);
			if (i == 8)
				assertEquals(ata[0].getStatement_id(), st[numStmts].getStatement_id());
			else
				assertEquals(ata[0].getStatement_id(), st[numStmts - 1].getStatement_id());
			assertEquals(ata[0].getR_value_id(), rval_id);
			Attribute_c attr = Attribute_c.getOneO_ATTROnR806(AttributeValueReference_c.getOneV_AVLOnR801(Value_c.getOneV_VALOnR689(ata[0])));
			assertEquals("Row_Number", attr.getName());//$NON-NLS-1$
		}
		if (parms.length == 0) {
			assertEquals(0, parm_list.length);
		} else if (parms.length == 1) {
			assertEquals(1, parm_list.length);
			assertEquals(li[intParmIndex].getValue_id(), parm_list[0].getValue_id());
			assertEquals(parms[intParmIndex], parm_list[0].getName());
			assertEquals(IdAssigner.NULL_UUID, parm_list[0].getNext_value_id());
		} else if (parms.length == 2) {
			assertEquals(2, parm_list.length);
			assertEquals(li[0].getValue_id(), parm_list[1 - intParmIndex].getValue_id());
			assertEquals(parms[intParmIndex], parm_list[1 - intParmIndex].getName());
			assertEquals(ls[0].getValue_id(), parm_list[1 - strParmIndex].getValue_id());
			assertEquals(parms[strParmIndex], parm_list[1 - strParmIndex].getName());
			assertEquals(parm_list[0].getValue_id(), parm_list[1].getNext_value_id());
			assertEquals(IdAssigner.NULL_UUID, parm_list[0].getNext_value_id());
		} else if (parms.length == 3) {
			assertEquals(3, parm_list.length);
			int iplIndex = 1;
			if (intParmIndex == 0)
				iplIndex = 2;
			assertEquals(li[0].getValue_id(), parm_list[iplIndex].getValue_id());
			assertEquals(parms[intParmIndex], parm_list[iplIndex].getName());
			assertEquals(ls[0].getValue_id(), parm_list[strParmIndex - 1].getValue_id());
			assertEquals(parms[strParmIndex], parm_list[strParmIndex - 1].getName());
			int rplIndex = 2;
			if (realParmIndex == 2)
				rplIndex = 1;
			assertEquals(lr[0].getValue_id(), parm_list[rplIndex].getValue_id());
			assertEquals(parms[realParmIndex], parm_list[rplIndex].getName());
			assertEquals(parm_list[0].getValue_id(), parm_list[2].getNext_value_id());
			assertEquals(parm_list[1].getValue_id(), parm_list[0].getNext_value_id());
			assertEquals(IdAssigner.NULL_UUID, parm_list[1].getNext_value_id());
		}
	}
}
public void testInvocationRetVoidParmNone() throws RecognitionException, TokenStreamException {
	String parms[] = {
	};
	String parmVals[] = {
	};
	invocationTest("test1();", "test1", parms, parmVals, 0, 0, 0);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmInt() throws RecognitionException, TokenStreamException {
	String parms[] = { "i" };//$NON-NLS-1$
	String parmVals[] = { "2" };//$NON-NLS-1$
	invocationTest("test2( i: 2 );", "test2", parms, parmVals, 1, 0, 0);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmIntWithUnderscores() throws RecognitionException, TokenStreamException {
	String parms[] = { "_parm" };//$NON-NLS-1$
	String parmVals[] = { "2" };//$NON-NLS-1$
	invocationTest("_test( _parm: 2 );", "_test", parms, parmVals, 1, 0, 0);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmStringInt() throws RecognitionException, TokenStreamException {
	String parms[] = { "i", "s" };//$NON-NLS-1$//$NON-NLS-2$
	String parmVals[] = { "2", "t" };//$NON-NLS-1$//$NON-NLS-2$
	invocationTest("test3( i: 2, s: \"t\" );", "test3", parms, parmVals, 2, 0, 1); //$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmStringIntOtherOrder() throws RecognitionException, TokenStreamException {
	String parms[] = { "s", "i" };//$NON-NLS-1$//$NON-NLS-2$
	String parmVals[] = { "u", "3" };//$NON-NLS-1$//$NON-NLS-2$
	invocationTest("test3( s: \"u\", i: 3 );", "test3", parms, parmVals, 2, 1, 0); //$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetIntParmNone() throws RecognitionException, TokenStreamException {
	String parms[] = {};
	String parmVals[] = {};
//	UUID intId = BaseTest.getTypeID(OalParserTest_Generics.modelRoot, "integer");//91//$NON-NLS-1$
	UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//91//$NON-NLS-1$
	invocationRvalTest("", "x", "test4();", "test4", intId, parms, parmVals, 1, 2, 0, 0, 0);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
}
public void testInvocationRetIntParmInt() throws RecognitionException, TokenStreamException {
	String parms[] = { "i" };//$NON-NLS-1$
	String parmVals[] = { "2" };//$NON-NLS-1$
	UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//91//$NON-NLS-1$
	invocationRvalTest("", "x", "test5( i: 2 );", "test5", intId, parms, parmVals, 1, 3, 0, 0, 0);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
}
public void testInvocationRetStringParmStringInt() throws RecognitionException, TokenStreamException {
	String parms[] = { "i", "s" };//$NON-NLS-1$//$NON-NLS-2$
	String parmVals[] = { "6", "t2" };//$NON-NLS-1$//$NON-NLS-2$
	UUID strId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "string");//93//$NON-NLS-1$
	invocationRvalTest("", "x", "test6( i: 6, s: \"t2\" );", "test6", strId, parms, parmVals, 1, 4, 0, 1, 0);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
}
public void testInvocationRetStringParmStringIntOtherOrder() throws RecognitionException, TokenStreamException {
	String parms[] = { "s", "i" };//$NON-NLS-1$//$NON-NLS-2$
	String parmVals[] = { "t3", "7" };//$NON-NLS-1$//$NON-NLS-2$
	UUID strId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "string");//93//$NON-NLS-1$
	invocationRvalTest("", "x", "test6( s: \"t3\", i: 7  );", "test6", strId, parms, parmVals, 1, 4, 1, 0, 0);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
}
public void testInvocationRetBooleanParmRealStringInt() throws RecognitionException, TokenStreamException {
	String parms[] = { "r", "s", "i" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	String parmVals[] = { "2.1718", "t3", "7" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//90//$NON-NLS-1$
	invocationRvalTest(
		"",//$NON-NLS-1$
		"x",//$NON-NLS-1$
		"test7( r: 2.1718, s: \"t3\", i: 7 );",//$NON-NLS-1$
		"test7",//$NON-NLS-1$
		boolId,
		parms,
		parmVals,
		1,
		5,
		2,
		1,
		0);
}
public void testInvocationRetBooleanParmRealStringIntReverseOrder()
	throws RecognitionException, TokenStreamException {
	String parms[] = { "i", "s", "r" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	String parmVals[] = { "7", "t3", "2.1718" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//90//$NON-NLS-1$
	invocationRvalTest(
		"",//$NON-NLS-1$
		"x",//$NON-NLS-1$
		"test7( i: 7, s: \"t3\", r: 2.1718 );",//$NON-NLS-1$
		"test7",//$NON-NLS-1$
		boolId,
		parms,
		parmVals,
		1,
		5,
		0,
		1,
		2);
}
public void testInvocationRetBooleanParmRealStringIntBadSecond()
throws RecognitionException, TokenStreamException {
	String act = "test7( i: 7, s: x.s, r: 2.1718 );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":2:25-25: ->s<- is not an attribute of class ->Test<-.",//$NON-NLS-1$
			":2:33-33: ->s<- is not an attribute of class ->Test<-.",//$NON-NLS-1$
			":2:33-33: ->s<- is not an attribute of class ->Test<-.",//$NON-NLS-1$
			":2:33-33: ->s<- is not an attribute of class ->Test<-.",//$NON-NLS-1$
			":2:33-33: ->s<- is not an attribute of class ->Test<-.",//$NON-NLS-1$
			":2:26-26: ->s<- is not an attribute of class ->Test<-.",//$NON-NLS-1$
			":2:26-26: ->s<- is not an attribute of class ->Test<-.",//$NON-NLS-1$
			":2:28-28: ->s<- is not an attribute of class ->Test<-.",//$NON-NLS-1$
			":2:58-58: ->s<- is not an attribute of class ->Test<-." };//$NON-NLS-1$
	int num_stmts[] = { 1, 1, 1, 1, 1, 1, 1, 1, 2 };
	invocationRvalErrorTest("select any x from instances of D_TST;\n", "t", act, err_msgs, num_stmts, 2);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationAttrWriteInt() throws RecognitionException, TokenStreamException {
	String parms[] = {
	};
	String parmVals[] = {
	};
	UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//91//$NON-NLS-1$
	invocationRvalTest(
		"select any x from instances of D_H; ",//$NON-NLS-1$
		"x.Row_Number",//$NON-NLS-1$
		"test4();",//$NON-NLS-1$
		"test4",//$NON-NLS-1$
		intId,
		parms,
		parmVals,
		2,
		3,
		0,
		0,
		0,
        1,
        2);
}
private void invocationErrorTest(
	String act,
	String[] err_msgs,
	int numVals,
	int numLines,
	String line2,
	String line3,
	int[] numStmts)
	throws RecognitionException, TokenStreamException {
	for (int i = 0; i < m_invocation_prefix.length; ++i) {
		String x = OalParserTest_Generics.parseAction(m_invocation_prefix[i] + act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(numLines, lines.length);
		assertEquals(err_msgs[i], lines[0]); //$NON-NLS-1$
		if (numLines >= 2)
			assertEquals(line2, lines[1].replaceFirst("line \\d*:\\d*: ", ""));//$NON-NLS-1$//$NON-NLS-2$
		if (numLines >= 3)
			assertEquals(line3, lines[2].substring(11));
		OalParserTest_Generics.validateBlkStmtVal(1, numStmts[i], numVals);
		FunctionInvocation_c[] func = FunctionInvocation_c.FunctionInvocationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, func.length);
		BridgeInvocation_c[] brg = BridgeInvocation_c.BridgeInvocationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, brg.length);
		OperationInvocation_c[] tfr = OperationInvocation_c.OperationInvocationInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, tfr.length);
	}
	OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
}
private void invocationRvalErrorTest(
	String prefix,
	String varName,
	String act,
	String[] err_msgs,
	int[] numStmts,
	int numVals)
	throws RecognitionException, TokenStreamException {
	for (int i = 0; i < m_rval_prefix.length; ++i) {
		String input = prefix + m_rval_prefix[i][0] + varName +
		                                              m_rval_prefix[i][1] + act; 
		String x =
			OalParserTest_Generics.parseAction(
				input,
				OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
				OalParserTest_Generics.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(err_msgs[i], lines[0]); //$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal(1, numStmts[i], numVals);
		FunctionValue_c[] func = FunctionValue_c.FunctionValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, func.length);
		BridgeValue_c[] brg = BridgeValue_c.BridgeValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, brg.length);
		OperationValue_c[] tfr = OperationValue_c.OperationValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, tfr.length);
	}
}
public void testInvocationRetVoidNoParmsWithParm() throws RecognitionException, TokenStreamException {
	String act = "test1( x: true );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:10-10: Parameter ->x<- is not associated with function ->test1<-",//$NON-NLS-1$
			":1:18-18: Parameter ->x<- is not associated with bridge ->test1<-",//$NON-NLS-1$
			":1:18-18: Parameter ->x<- is not associated with bridge ->test1<-",//$NON-NLS-1$
			":1:11-11: Parameter ->x<- is not associated with bridge ->test1<-",//$NON-NLS-1$
			":1:11-11: Parameter ->x<- is not associated with bridge ->test1<-",//$NON-NLS-1$
			":1:13-13: Parameter ->x<- is not associated with operation ->test1<-",//$NON-NLS-1$
			":1:43-43: Parameter ->x<- is not associated with operation ->test1<-" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 1, "", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmIntWithoutParm() throws RecognitionException, TokenStreamException {
	String act = "test2();"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:9-9: Function ->test2<- is missing corresponding parameter(s)",//$NON-NLS-1$
			":1:17-17: Bridge ->test2<- is missing corresponding parameter(s)",//$NON-NLS-1$
			":1:17-17: Bridge ->test2<- is missing corresponding parameter(s)",//$NON-NLS-1$
			":1:10-10: Bridge ->test2<- is missing corresponding parameter(s)",//$NON-NLS-1$
			":1:10-10: Bridge ->test2<- is missing corresponding parameter(s)",//$NON-NLS-1$
			":1:12-12: Operation ->test2<- is missing corresponding parameter(s)",//$NON-NLS-1$
			":1:42-42: Operation ->test2<- is missing corresponding parameter(s)" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmIntUnknownParm() throws RecognitionException, TokenStreamException {
	String act = "test2( i: x );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:13-13: Variable ->x<- used in context where it must already exist.",//$NON-NLS-1$
			":1:21-21: Variable ->x<- used in context where it must already exist.",//$NON-NLS-1$
			":1:21-21: Variable ->x<- used in context where it must already exist.",//$NON-NLS-1$
			":1:14-14: Variable ->x<- used in context where it must already exist.",//$NON-NLS-1$
			":1:14-14: Variable ->x<- used in context where it must already exist.",//$NON-NLS-1$
			":1:16-16: Variable ->x<- used in context where it must already exist.",//$NON-NLS-1$
			":1:46-46: Variable ->x<- used in context where it must already exist." };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(
		act,
		err_msgs,
		1,
		3,
		"expecting TOK_RPAREN, found ';'",//$NON-NLS-1$
		"expecting Semicolon, found 'null'",//$NON-NLS-1$
		numStmts);
}
public void testInvocationRetVoidParmIntStringSecondUnknownParm()
	throws RecognitionException, TokenStreamException {
	String act = "test3( i: 7, s: z );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:19-19: Variable ->z<- used in context where it must already exist.",//$NON-NLS-1$
			":1:27-27: Variable ->z<- used in context where it must already exist.",//$NON-NLS-1$
			":1:27-27: Variable ->z<- used in context where it must already exist.",//$NON-NLS-1$
			":1:20-20: Variable ->z<- used in context where it must already exist.",//$NON-NLS-1$
			":1:20-20: Variable ->z<- used in context where it must already exist.",//$NON-NLS-1$
			":1:22-22: Variable ->z<- used in context where it must already exist.",//$NON-NLS-1$
			":1:52-52: Variable ->z<- used in context where it must already exist." };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(
		act,
		err_msgs,
		1,
		3,
		"expecting TOK_RPAREN, found ';'",//$NON-NLS-1$
		"expecting Semicolon, found 'null'",//$NON-NLS-1$
		numStmts);
}
public void testInvocationRetVoidParmIntExtraParm() throws RecognitionException, TokenStreamException {
	String act = "test2( i: 2, r: 3.14  );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:16-16: Parameter ->r<- is not associated with function ->test2<-",//$NON-NLS-1$
			":1:24-24: Parameter ->r<- is not associated with bridge ->test2<-",//$NON-NLS-1$
			":1:24-24: Parameter ->r<- is not associated with bridge ->test2<-",//$NON-NLS-1$
			":1:17-17: Parameter ->r<- is not associated with bridge ->test2<-",//$NON-NLS-1$
			":1:17-17: Parameter ->r<- is not associated with bridge ->test2<-",//$NON-NLS-1$
			":1:19-19: Parameter ->r<- is not associated with operation ->test2<-",//$NON-NLS-1$
			":1:49-49: Parameter ->r<- is not associated with operation ->test2<-" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 1, 1, "", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmIntDupParm() throws RecognitionException, TokenStreamException {
	String act = "test2( i: 2, i: 1 );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:21-21: Function ->test2<- has redundant parameters",//$NON-NLS-1$
			":1:29-29: Bridge ->test2<- has redundant parameters",//$NON-NLS-1$
			":1:29-29: Bridge ->test2<- has redundant parameters",//$NON-NLS-1$
			":1:22-22: Bridge ->test2<- has redundant parameters",//$NON-NLS-1$
			":1:22-22: Bridge ->test2<- has redundant parameters",//$NON-NLS-1$
			":1:24-24: Operation ->test2<- has redundant parameters",//$NON-NLS-1$
			":1:54-54: Operation ->test2<- has redundant parameters" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmIntExtraParmDiffOrder() throws RecognitionException, TokenStreamException {
	String act = "test2( r: 3.14, i:2   );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:10-10: Parameter ->r<- is not associated with function ->test2<-",//$NON-NLS-1$
			":1:18-18: Parameter ->r<- is not associated with bridge ->test2<-",//$NON-NLS-1$
			":1:18-18: Parameter ->r<- is not associated with bridge ->test2<-",//$NON-NLS-1$
			":1:11-11: Parameter ->r<- is not associated with bridge ->test2<-",//$NON-NLS-1$
			":1:11-11: Parameter ->r<- is not associated with bridge ->test2<-",//$NON-NLS-1$
			":1:13-13: Parameter ->r<- is not associated with operation ->test2<-",//$NON-NLS-1$
			":1:43-43: Parameter ->r<- is not associated with operation ->test2<-" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 1, "", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmIntWrongType() throws RecognitionException, TokenStreamException {
	String act = "test2( i:true );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:17-17: Parameter ->i<- has been assigned value of different type",//$NON-NLS-1$
			":1:25-25: Parameter ->i<- has been assigned value of different type",//$NON-NLS-1$
			":1:25-25: Parameter ->i<- has been assigned value of different type",//$NON-NLS-1$
			":1:18-18: Parameter ->i<- has been assigned value of different type",//$NON-NLS-1$
			":1:18-18: Parameter ->i<- has been assigned value of different type",//$NON-NLS-1$
			":1:20-20: Parameter ->i<- has been assigned value of different type",//$NON-NLS-1$
			":1:50-50: Parameter ->i<- has been assigned value of different type" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmIntStringWrongType() throws RecognitionException, TokenStreamException {
	String act = "test3( i: 7, s:true );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:23-23: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:31-31: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:31-31: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:24-24: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:24-24: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:26-26: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:56-56: Parameter ->s<- has been assigned value of different type" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmIntStringWrongTypeDiffOrder()
	throws RecognitionException, TokenStreamException {
	String act = "test3( s:true, i:7 );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:22-22: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:30-30: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:30-30: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:23-23: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:23-23: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:25-25: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
			":1:55-55: Parameter ->s<- has been assigned value of different type" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidNoParmUnknown() throws RecognitionException, TokenStreamException {
	String act = "bad1();"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:3-6: Cannot find specified function ->bad1<-",//$NON-NLS-1$
			":1:11-14: Cannot find specified bridge ->T::bad1<-",//$NON-NLS-1$
			":1:11-14: Cannot find specified bridge ->S::bad1<-",//$NON-NLS-1$
			":1:4-7: Cannot find bridge, operation or message ->T::bad1<-",//$NON-NLS-1$
			":1:4-7: Cannot find bridge, operation or message ->S::bad1<-",//$NON-NLS-1$
			":1:6-9: Cannot find bridge, operation or message ->D_D::bad1<-",//$NON-NLS-1$
			":1:36-39: Cannot find specified operation ->D_H::bad1<-" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found ')'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidNoParmMult() throws RecognitionException, TokenStreamException {
	String act = "mult_func1();"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:3-12: Multiple functions found for ->mult_func1<-: testOAL1::testOAL1::Functions::mult_func1 ,testOAL1::testOAL1::Functions::mult_func1",//$NON-NLS-1$
			":1:11-20: More than one bridge with name ->mult_func1<- You will need to eventually clear this up",//$NON-NLS-1$
			":1:11-20: More than one bridge with name ->mult_func1<- You will need to eventually clear this up",//$NON-NLS-1$
			":1:4-13: More than one bridge with name ->mult_func1<- You will need to eventually clear this up",//$NON-NLS-1$
			":1:4-13: More than one bridge with name ->mult_func1<- You will need to eventually clear this up",//$NON-NLS-1$
			":1:6-15: More than one operation with name ->mult_func1<- You will need to eventually run the audit and/or clear this up",//$NON-NLS-1$
			":1:36-45: More than one operation with name ->mult_func1<- You will need to eventually run the audit and/or clear this up" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found ')'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetRealNoParmUnknown() throws RecognitionException, TokenStreamException {
	String act = "bad2();"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:7-10: Cannot find specified function ->bad2<-",//$NON-NLS-1$
			":1:15-18: Cannot find specified bridge ->T::bad2<-",//$NON-NLS-1$
			":1:15-18: Cannot find specified bridge ->S::bad2<-",//$NON-NLS-1$
			":1:15-18: Cannot find specified bridge ->T::bad2<-",//$NON-NLS-1$
			":1:15-18: Cannot find specified bridge ->S::bad2<-",//$NON-NLS-1$
			":1:8-11: Cannot find bridge, operation or message ->T::bad2<-",//$NON-NLS-1$
			":1:8-11: Cannot find bridge, operation or message ->S::bad2<-",//$NON-NLS-1$
			":1:10-13: Cannot find bridge, operation or message ->D_D::bad2<-",//$NON-NLS-1$
			":1:40-43: Cannot find specified operation ->D_H::bad2<-" };//$NON-NLS-1$
	int num_stmts[] = { 1, 0, 0, 0, 0, 0, 0, 0, 1 };
	invocationRvalErrorTest("", "x", act, err_msgs, num_stmts, 1);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetRealNoParmMult() throws RecognitionException, TokenStreamException {
	String act = "mult_func2();"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:7-16: Multiple functions found for ->mult_func2<-: testOAL1::testOAL1::Functions::mult_func2 ,testOAL1::testOAL1::Functions::mult_func2",//$NON-NLS-1$
			":1:15-24: More than one bridge with name ->mult_func2<- You will need to eventually clear this up",//$NON-NLS-1$
			":1:15-24: More than one bridge with name ->mult_func2<- You will need to eventually clear this up",//$NON-NLS-1$
			":1:15-24: More than one bridge with name ->mult_func2<- You will need to eventually clear this up",//$NON-NLS-1$
			":1:15-24: More than one bridge with name ->mult_func2<- You will need to eventually clear this up",//$NON-NLS-1$
			":1:8-17: More than one bridge with name ->mult_func2<- You will need to eventually clear this up",//$NON-NLS-1$
			":1:8-17: More than one bridge with name ->mult_func2<- You will need to eventually clear this up",//$NON-NLS-1$
			":1:10-19: More than one operation with name ->mult_func2<- You will need to eventually run the audit and/or clear this up",//$NON-NLS-1$
			":1:40-49: More than one operation with name ->mult_func2<- You will need to eventually run the audit and/or clear this up" };//$NON-NLS-1$
	int num_stmts[] = { 1, 0, 0, 0, 0, 0, 0, 0, 1 };
	invocationRvalErrorTest("", "x", act, err_msgs, num_stmts, 1);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidDupParms() throws RecognitionException, TokenStreamException {
	String act = "mult_parms( a: 7 );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:19-19: More than one parameter with name ->a<- for function ->mult_parms<-",//$NON-NLS-1$
			":1:27-27: More than one parameter with name ->a<- for bridge ->mult_parms<-",//$NON-NLS-1$
			":1:27-27: More than one parameter with name ->a<- for bridge ->mult_parms<-",//$NON-NLS-1$
			":1:27-27: More than one parameter with name ->a<- for bridge ->mult_parms<-",//$NON-NLS-1$
			":1:27-27: More than one parameter with name ->a<- for bridge ->mult_parms<-",//$NON-NLS-1$
			":1:20-20: More than one parameter with name ->a<- for bridge ->mult_parms<-",//$NON-NLS-1$
			":1:20-20: More than one parameter with name ->a<- for bridge ->mult_parms<-",//$NON-NLS-1$
			":1:22-22: More than one parameter with name ->a<- for operation ->mult_parms<-",//$NON-NLS-1$
			":1:52-52: More than one parameter with name ->a<- for operation ->mult_parms<-" };//$NON-NLS-1$
	int num_stmts[] = { 0, 0, 0, 0, 0, 0, 0, 0, 1 };
	invocationRvalErrorTest("", "x", act, err_msgs, num_stmts, 1);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidNoParmsAsRval() throws RecognitionException, TokenStreamException {
	String act = "test1();"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:13-13: Function ->test1<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:21-21: Bridge ->test1<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:21-21: Bridge ->test1<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:21-21: Bridge ->test1<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:21-21: Bridge ->test1<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:14-14: Bridge ->test1<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:14-14: Bridge ->test1<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:16-16: Operation ->test1<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:46-46: Operation ->test1<- has no return type (return type is void) and thus cannot be used in an expression" };//$NON-NLS-1$
	int num_stmts[] = { 0, 1, 1, 0, 0, 0, 0, 0, 1 };
	invocationRvalErrorTest("", "x", act, err_msgs, num_stmts, 1);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmIntAsRval() throws RecognitionException, TokenStreamException {
	String act = "test2( i: 2 );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:19-19: Function ->test2<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:27-27: Bridge ->test2<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:27-27: Bridge ->test2<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:27-27: Bridge ->test2<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:27-27: Bridge ->test2<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:20-20: Bridge ->test2<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:20-20: Bridge ->test2<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:22-22: Operation ->test2<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:52-52: Operation ->test2<- has no return type (return type is void) and thus cannot be used in an expression" };//$NON-NLS-1$
	int num_stmts[] = { 0, 1, 1, 0, 0, 0, 0, 0, 1 };
	invocationRvalErrorTest("", "x", act, err_msgs, num_stmts, 1);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmStringIntAsRval() throws RecognitionException, TokenStreamException {
	String act = "test3( i: 2, s: \"t\" );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:27-27: Function ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:35-35: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:35-35: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:35-35: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:35-35: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:28-28: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:28-28: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:30-30: Operation ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:60-60: Operation ->test3<- has no return type (return type is void) and thus cannot be used in an expression" };//$NON-NLS-1$
	int num_stmts[] = { 0, 1, 1, 0, 0, 0, 0, 0, 1 };
	invocationRvalErrorTest("", "x", act, err_msgs, num_stmts, 1);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetVoidParmStringIntOtherOrderAsRval()
	throws RecognitionException, TokenStreamException {
	String act = "test3( s: \"u\", i: 3 );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:27-27: Function ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:35-35: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:35-35: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:35-35: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:35-35: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:28-28: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:28-28: Bridge ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:30-30: Operation ->test3<- has no return type (return type is void) and thus cannot be used in an expression",//$NON-NLS-1$
			":1:60-60: Operation ->test3<- has no return type (return type is void) and thus cannot be used in an expression" };//$NON-NLS-1$
	int num_stmts[] = { 0, 1, 1, 0, 0, 0, 0, 0, 1 };
	invocationRvalErrorTest("", "x", act, err_msgs, num_stmts, 1);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetIntNoParmsAsVoid() throws RecognitionException, TokenStreamException {
	String act = "test4();"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:9-9: Function ->test4<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:17-17: Bridge ->test4<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:17-17: Bridge ->test4<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:10-10: Bridge ->test4<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:10-10: Bridge ->test4<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:12-12: Operation ->test4<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:42-42: Operation ->test4<- has a return value which is not being assigned to a variable" };//$NON-NLS-1$
	int[] numStmts = { 0, 1, 1, 1, 1, 1, 2 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetIntParmIntAsVoid() throws RecognitionException, TokenStreamException {
	String act = "test5( i: 2 );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:15-15: Function ->test5<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:23-23: Bridge ->test5<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:23-23: Bridge ->test5<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:16-16: Bridge ->test5<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:16-16: Bridge ->test5<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:18-18: Operation ->test5<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:48-48: Operation ->test5<- has a return value which is not being assigned to a variable" };//$NON-NLS-1$
	int[] numStmts = { 0, 1, 1, 1, 1, 1, 2 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetStringParmStringIntAsVoid() throws RecognitionException, TokenStreamException {
	String act = "test6( i: 6, s: \"t2\" );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:24-24: Function ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:32-32: Bridge ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:32-32: Bridge ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:25-25: Bridge ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:25-25: Bridge ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:27-27: Operation ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:57-57: Operation ->test6<- has a return value which is not being assigned to a variable" };//$NON-NLS-1$
	int[] numStmts = { 0, 1, 1, 1, 1, 1, 2 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetStringParmStringIntOtherOrderAsVoid()
	throws RecognitionException, TokenStreamException {
	String act = "test6( s: \"t3\", i: 7 );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:24-24: Function ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:32-32: Bridge ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:32-32: Bridge ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:25-25: Bridge ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:25-25: Bridge ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:27-27: Operation ->test6<- has a return value which is not being assigned to a variable",//$NON-NLS-1$
			":1:57-57: Operation ->test6<- has a return value which is not being assigned to a variable" };//$NON-NLS-1$
	int[] numStmts = { 0, 1, 1, 1, 1, 1, 2 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetIntNoParmTypeMismatch() throws RecognitionException, TokenStreamException {
	String act = "test4();"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:22-22: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:30-30: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:30-30: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:30-30: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:30-30: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:23-23: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:23-23: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:25-25: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:55-55: Variable ->x<- already exists as a different type" };//$NON-NLS-1$
	int num_stmts[] = { 1, 1, 1, 1, 1, 1, 1, 1, 2 };
	invocationRvalErrorTest("x = \"x\"; ", "x", act, err_msgs, num_stmts, 3);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetIntParmIntTypeMismatch() throws RecognitionException, TokenStreamException {
	String act = "test5( i: 2 );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:28-28: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:36-36: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:36-36: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:36-36: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:36-36: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:29-29: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:29-29: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:31-31: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:61-61: Variable ->x<- already exists as a different type" };//$NON-NLS-1$
	int num_stmts[] = { 1, 1, 1, 1, 1, 1, 1, 1, 2 };
	invocationRvalErrorTest("x = \"x\"; ", "x", act, err_msgs, num_stmts, 3);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetStringParmStringIntTypeMismatch() throws RecognitionException, TokenStreamException {
	String act = "test6( i: 6, s: \"t2\" );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:36-36: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:44-44: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:44-44: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:44-44: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:44-44: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:37-37: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:37-37: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:39-39: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:69-69: Variable ->x<- already exists as a different type" };//$NON-NLS-1$
	int num_stmts[] = { 1, 1, 1, 1, 1, 1, 1, 1, 2 };
	invocationRvalErrorTest("x = 22; ", "x", act, err_msgs, num_stmts, 3);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationRetStringParmStringIntOtherOrderTypeMismatch()
	throws RecognitionException, TokenStreamException {
	String act = "test6( s: \"t3\", i: 7 );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:38-38: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:46-46: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:46-46: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:46-46: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:46-46: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:39-39: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:39-39: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:41-41: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:71-71: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			};
	int num_stmts[] = { 1, 1, 1, 1, 1, 1, 1, 1, 2 };
	invocationRvalErrorTest("x = true; ", "x", act, err_msgs, num_stmts, 3);//$NON-NLS-1$//$NON-NLS-2$
}
public void testMissingBridgeWithExistingOperation() throws RecognitionException, TokenStreamException {
	String act = "T::noSuchBrg();"; //$NON-NLS-1$
	String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":1:4-12: Cannot find bridge, operation or message ->T::noSuchBrg<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:14: expecting Semicolon, found ')'", lines[1]); //$NON-NLS-1$
	OalParserTest_Generics.validateBlkStmtVal(1, 1, 0);
	BridgeInvocation_c[] brg = BridgeInvocation_c.BridgeInvocationInstances(OalParserTest_Generics.modelRoot);
	assertEquals(0, brg.length);
}
public void testMissingOperationWithExistingBridge() throws RecognitionException, TokenStreamException {
	String act = "D_D::noSuchOp();"; //$NON-NLS-1$
	String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":1:6-13: Cannot find bridge, operation or message ->D_D::noSuchOp<-", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:15: expecting Semicolon, found ')'", lines[1]); //$NON-NLS-1$
	OalParserTest_Generics.validateBlkStmtVal(1, 1, 0);
	OperationInvocation_c[] tfr = OperationInvocation_c.OperationInvocationInstances(OalParserTest_Generics.modelRoot);
	assertEquals(0, tfr.length);
}
public void testIBOperationWithCBSyntax() throws RecognitionException, TokenStreamException {
	String act = "D_H::test1();"; //$NON-NLS-1$
	String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":1:6-10: Operation ->test1<- is instance-based and must be invoked as <var>.test1", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:12: expecting Semicolon, found ')'", lines[1]); //$NON-NLS-1$
	OalParserTest_Generics.validateBlkStmtVal(1, 1, 0);
	OperationInvocation_c[] tfr = OperationInvocation_c.OperationInvocationInstances(OalParserTest_Generics.modelRoot);
	assertEquals(0, tfr.length);
}
public void testRvalIBOperationWithCBSyntax() throws RecognitionException, TokenStreamException {
	String act = "x = D_H::test4();"; //$NON-NLS-1$
	String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":1:10-14: Operation ->test4<- is instance-based and must be invoked as <var>.test4", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:16: expecting Semicolon, found ')'", lines[1]); //$NON-NLS-1$
	OalParserTest_Generics.validateBlkStmtVal(1, 0, 1);
	OperationInvocation_c[] tfr = OperationInvocation_c.OperationInvocationInstances(OalParserTest_Generics.modelRoot);
	assertEquals(0, tfr.length);
}
public void testCBOperationWithIBSyntax() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;  d.test1();"; //$NON-NLS-1$
	String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":1:40-44: Operation ->test1<- is class-based and must be invoked as <kl>::test1", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:46: expecting Semicolon, found ')'", lines[1]); //$NON-NLS-1$
	OalParserTest_Generics.validateBlkStmtVal(1, 1, 0);
	OperationInvocation_c[] tfr = OperationInvocation_c.OperationInvocationInstances(OalParserTest_Generics.modelRoot);
	assertEquals(0, tfr.length);
}
public void testRvalCBOperationWithIBSyntax() throws RecognitionException, TokenStreamException {
	String act = "select any d from instances of D_D;  x = d.test4();"; //$NON-NLS-1$
	String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	String lines[] = x.split("\n");//$NON-NLS-1$
	assertEquals(2, lines.length);
	assertEquals(":1:44-48: Operation ->test4<- is class-based and must be invoked as <kl>::test4", lines[0]); //$NON-NLS-1$
	assertEquals("line 1:50: expecting Semicolon, found ')'", lines[1]); //$NON-NLS-1$
	OalParserTest_Generics.validateBlkStmtVal(1, 1, 1);
	OperationInvocation_c[] tfr = OperationInvocation_c.OperationInvocationInstances(OalParserTest_Generics.modelRoot);
	assertEquals(0, tfr.length);
}
public void paramRvalTest(String act, int funcNum, String[] paramList)
	throws RecognitionException, TokenStreamException {
	String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, funcNum);
	assertEquals("", x);//$NON-NLS-1$
	ParameterValue_c parms[] = ParameterValue_c.ParameterValueInstances(OalParserTest_Generics.modelRoot);
	assertEquals(paramList.length, parms.length);
	for (int i = 0; i < paramList.length; ++i) {
		BridgeParameter_c bparm = BridgeParameter_c.getOneS_BPARMOnR831(parms[i]);
		assertEquals(null, bparm);
		OperationParameter_c tparm = OperationParameter_c.getOneO_TPARMOnR833(parms[i]);
		assertEquals(null, tparm);
		FunctionParameter_c sparm = FunctionParameter_c.getOneS_SPARMOnR832(parms[i]);
		assertEquals(paramList[i], sparm.getName());
	}
	OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, funcNum);

	x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_BRG, funcNum);
	assertEquals("", x);//$NON-NLS-1$
	parms = ParameterValue_c.ParameterValueInstances(OalParserTest_Generics.modelRoot);
	assertEquals(paramList.length, parms.length);
	for (int i = 0; i < paramList.length; ++i) {
		OperationParameter_c tparm = OperationParameter_c.getOneO_TPARMOnR833(parms[i]);
		assertEquals(null, tparm);
		FunctionParameter_c sparm = FunctionParameter_c.getOneS_SPARMOnR832(parms[i]);
		assertEquals(null, sparm);
		BridgeParameter_c bparm = BridgeParameter_c.getOneS_BPARMOnR831(parms[i]);
		assertEquals(paramList[i], bparm.getName());
	}
	OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_BRG, funcNum);

	for (int tfrTest = OalParserTest_Generics.ACTIVITY_TYPE_CB_OP; tfrTest <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++tfrTest) {
		x = OalParserTest_Generics.parseAction(act, tfrTest, funcNum);
		assertEquals("", x);//$NON-NLS-1$
		parms = ParameterValue_c.ParameterValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(paramList.length, parms.length);
		for (int i = 0; i < paramList.length; ++i) {
			BridgeParameter_c bparm = BridgeParameter_c.getOneS_BPARMOnR831(parms[i]);
			assertEquals(null, bparm);
			FunctionParameter_c sparm = FunctionParameter_c.getOneS_SPARMOnR832(parms[i]);
			assertEquals(null, sparm);
			OperationParameter_c tparm = OperationParameter_c.getOneO_TPARMOnR833(parms[i]);
			assertEquals(paramList[i], tparm.getName());
		}
		OalParserTest_Generics.clearActionData(tfrTest, funcNum);
	}
}
public void paramWriteTest(String act, int funcNum, String[] paramList)
	throws RecognitionException, TokenStreamException {
	String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, funcNum);
	assertEquals("", x);//$NON-NLS-1$
	AssignToMember_c parms[] = AssignToMember_c.AssignToMemberInstances(OalParserTest_Generics.modelRoot);
	assertEquals(paramList.length, parms.length);
	for (int i = 0; i < paramList.length; ++i) {
		BridgeParameter_c bparm = BridgeParameter_c.getOneS_BPARMOnR831(ParameterValue_c.getManyV_PVLsOnR801(Value_c.getManyV_VALsOnR689(parms[i])));
		assertEquals(null, bparm);
		OperationParameter_c tparm = OperationParameter_c.getOneO_TPARMOnR833(ParameterValue_c.getManyV_PVLsOnR801(Value_c.getManyV_VALsOnR689(parms[i])));
		assertEquals(null, tparm);
		FunctionParameter_c sparm = FunctionParameter_c.getOneS_SPARMOnR832(ParameterValue_c.getManyV_PVLsOnR801(Value_c.getManyV_VALsOnR689(parms[i])));
		assertEquals(paramList[i], sparm.getName());
	}
	OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, funcNum);

	x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_BRG, funcNum);
	assertEquals("", x);//$NON-NLS-1$
	parms = AssignToMember_c.AssignToMemberInstances(OalParserTest_Generics.modelRoot);
	assertEquals(paramList.length, parms.length);
	for (int i = 0; i < paramList.length; ++i) {
		OperationParameter_c tparm = OperationParameter_c.getOneO_TPARMOnR833(ParameterValue_c.getManyV_PVLsOnR801(Value_c.getManyV_VALsOnR689(parms[i])));
		assertEquals(null, tparm);
		FunctionParameter_c sparm = FunctionParameter_c.getOneS_SPARMOnR832(ParameterValue_c.getManyV_PVLsOnR801(Value_c.getManyV_VALsOnR689(parms[i])));
		assertEquals(null, sparm);
		BridgeParameter_c bparm = BridgeParameter_c.getOneS_BPARMOnR831(ParameterValue_c.getManyV_PVLsOnR801(Value_c.getManyV_VALsOnR689(parms[i])));
		assertEquals(paramList[i], bparm.getName());
	}
	OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_BRG, funcNum);

	for (int tfrTest = OalParserTest_Generics.ACTIVITY_TYPE_CB_OP; tfrTest <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++tfrTest) {
		x = OalParserTest_Generics.parseAction(act, tfrTest, funcNum);
		assertEquals("", x);//$NON-NLS-1$
		parms = AssignToMember_c.AssignToMemberInstances(OalParserTest_Generics.modelRoot);
		assertEquals(paramList.length, parms.length);
		for (int i = 0; i < paramList.length; ++i) {
			BridgeParameter_c bparm = BridgeParameter_c.getOneS_BPARMOnR831(ParameterValue_c.getManyV_PVLsOnR801(Value_c.getManyV_VALsOnR689(parms[i])));
			assertEquals(null, bparm);
			FunctionParameter_c sparm = FunctionParameter_c.getOneS_SPARMOnR832(ParameterValue_c.getManyV_PVLsOnR801(Value_c.getManyV_VALsOnR689(parms[i])));
			assertEquals(null, sparm);
			OperationParameter_c tparm = OperationParameter_c.getOneO_TPARMOnR833(ParameterValue_c.getManyV_PVLsOnR801(Value_c.getManyV_VALsOnR689(parms[i])));
			assertEquals(paramList[i], tparm.getName());
		}
		OalParserTest_Generics.clearActionData(tfrTest, funcNum);
	}
}
private void paramErrorTest(
		String act,
		int funcNum,
		String[] err_msgs,
		int numVals,
		int numLines,
	    int numExpectedParams,
		String line2,
		String line3,
		int[] numStmts)
		throws RecognitionException, TokenStreamException {
	paramErrorTestwithNonStdAssignmentPattern(act,
			funcNum,
			err_msgs,
			numVals,
			numLines,
		    numExpectedParams,
		    0,
			line2,
			line3,
			numStmts);
}
private void paramErrorTestwithNonStdAssignmentPattern(
	String act,
	int funcNum,
	String[] err_msgs,
	int numVals,
	int numLines,
    int numExpectedParams,
    int numExpectedAssignments,
	String line2,
	String line3,
	int[] numStmts)
	throws RecognitionException, TokenStreamException {
	for (int i = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; i <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++i) {
		AssignToMember_c atps[] = AssignToMember_c.AssignToMemberInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, atps.length);
		String x = OalParserTest_Generics.parseAction(act, i, funcNum);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(numLines, lines.length);
		assertEquals(err_msgs[i], lines[0]); //$NON-NLS-1$
		if (numLines >= 2)
			assertEquals(line2, lines[1].replaceFirst("line \\d*:\\d*: ", ""));//$NON-NLS-1$//$NON-NLS-2$
		if (numLines >= 3)
			assertEquals(line3, lines[2].substring(11));
		OalParserTest_Generics.validateBlkStmtVal(1, numStmts[i], numVals);
		atps = AssignToMember_c.AssignToMemberInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numExpectedAssignments, atps.length);
		ParameterValue_c parmVal[] = ParameterValue_c.ParameterValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numExpectedParams, parmVal.length);
		OalParserTest_Generics.clearActionData(i, funcNum);
	}
}
public void testReadByValParam() throws RecognitionException, TokenStreamException {
	String parmList[] = { "i" };//$NON-NLS-1$
	paramRvalTest("if ( param.i == 1) end if;", OalParserTest_Generics.PARAM_VAL, parmList); //$NON-NLS-1$
}
public void testReadByRefParam() throws RecognitionException, TokenStreamException {
	String parmList[] = { "s" };//$NON-NLS-1$
	paramRvalTest("x = param.s;", OalParserTest_Generics.PARAM_REF, parmList); //$NON-NLS-1$
}
public void testRead2ByValParam() throws RecognitionException, TokenStreamException {
	String parmList[] = { "i", "b" };//$NON-NLS-1$//$NON-NLS-2$
	paramRvalTest("x = param.i; y = param.b;", OalParserTest_Generics.PARAM_VAL_VAL, parmList); //$NON-NLS-1$
}
public void testReadByRefByValParam() throws RecognitionException, TokenStreamException {
	String parmList[] = { "r", "i" };//$NON-NLS-1$//$NON-NLS-2$
	paramRvalTest("x = param.r; y = param.i;", OalParserTest_Generics.PARAM_VAL_REF, parmList); //$NON-NLS-1$
}
public void testRead2ByRefParam() throws RecognitionException, TokenStreamException {
	String parmList[] = { "s", "r" };//$NON-NLS-1$//$NON-NLS-2$
	paramRvalTest("x = param.s; y = param.r;", OalParserTest_Generics.PARAM_REF_REF, parmList); //$NON-NLS-1$
}
public void testWriteByRefParam() throws RecognitionException, TokenStreamException {
	String parmList[] = { "s" };//$NON-NLS-1$
	paramWriteTest("param.s = \"test\";", OalParserTest_Generics.PARAM_REF, parmList); //$NON-NLS-1$
}
public void testWriteByRefParamWithUnderscores() throws RecognitionException, TokenStreamException {
	String parmList[] = { "_ref" };//$NON-NLS-1$
	paramWriteTest("param._ref = 1;", OalParserTest_Generics._TEST_REF, parmList); //$NON-NLS-1$
}
public void testWrite2ByRefParam() throws RecognitionException, TokenStreamException {
	String parmList[] = { "s", "r" };//$NON-NLS-1$//$NON-NLS-2$
	paramWriteTest("param.s = \"test\"; param.r = 2.2;", OalParserTest_Generics.PARAM_REF_REF, parmList); //$NON-NLS-1$
}
public void testInvocationPassVarToByRef() throws RecognitionException, TokenStreamException {
	String act = "paramRef(s: x);"; //$NON-NLS-1$
	for (int i = 0; i < m_invocation_prefix.length; ++i) {
		String x =
			OalParserTest_Generics.parseAction("x = \"good\"; " + m_invocation_prefix[i] + act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);//$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
	}
}
public void testInvocationPassVarToByRefWithUnderscores() throws RecognitionException, TokenStreamException {
	String act = "_testRef(_ref: x);"; //$NON-NLS-1$
	for (int i = 0; i < m_invocation_prefix.length; ++i) {
		String x =
			OalParserTest_Generics.parseAction("x = 3; " + m_invocation_prefix[i] + act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);//$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
	}
}
public void testInvocationPassVarToByRefWithUnderscoresInParm() throws RecognitionException, TokenStreamException {
	String act = "_testRef(_ref: _x);"; //$NON-NLS-1$
	for (int i = 0; i < m_invocation_prefix.length; ++i) {
		String x =
			OalParserTest_Generics.parseAction("_x = 3; " + m_invocation_prefix[i] + act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);//$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
	}
}
public void testInvocationPassAttributeToByRef() throws RecognitionException, TokenStreamException {
	String act = "paramRef(s: d.string_attr);"; //$NON-NLS-1$
	for (int i = 0; i < m_invocation_prefix.length; ++i) {
		String x =
			OalParserTest_Generics.parseAction(
				"create object instance d of D_D; " + m_invocation_prefix[i] + act,//$NON-NLS-1$
				OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
				OalParserTest_Generics.TEST_VOID_NO_PARM);
		assertEquals("", x);//$NON-NLS-1$
	}
}
public void testInvocationPassByRefToByRef() throws RecognitionException, TokenStreamException {
	String act = "paramRef(s: param.s);"; //$NON-NLS-1$
	for (int op_type = OalParserTest_Generics.ACTIVITY_TYPE_FUNC; op_type <= OalParserTest_Generics.ACTIVITY_TYPE_IB_OP; ++op_type) {
		for (int i = 0; i < m_invocation_prefix.length; ++i) {
			String x =
				OalParserTest_Generics.parseAction(
					"create object instance d of D_D; " + m_invocation_prefix[i] + act,//$NON-NLS-1$
					op_type,
					OalParserTest_Generics.PARAM_REF);
			assertEquals("", x);//$NON-NLS-1$
			OalParserTest_Generics.clearActionData(op_type, OalParserTest_Generics.PARAM_REF);
		}
	}
}
public void testInvocationPassR2I() throws RecognitionException, TokenStreamException {
    // Make sure the prefs are set to catch this error
    IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
    String i2rp = store.getString(BridgePointPreferencesStore.ALLOW_INT_TO_REAL_PROMOTION);
    String r2ic = store.getString(BridgePointPreferencesStore.ALLOW_REAL_TO_INT_COERCION);
    store.setValue(BridgePointPreferencesStore.ALLOW_INT_TO_REAL_PROMOTION, MessageDialogWithToggle.NEVER);
    store.setValue(BridgePointPreferencesStore.ALLOW_REAL_TO_INT_COERCION, MessageDialogWithToggle.NEVER);
    
	String act = "paramVal(i: 3.14);"; //$NON-NLS-1$
	for (int i = 0; i < m_invocation_prefix.length; ++i) {
		String x = OalParserTest_Generics.parseAction(m_invocation_prefix[i] + act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		assertTrue(x.contains("Parameter ->i<- has been assigned value of different type"));//$NON-NLS-1$
	}
	
    // Set the prefs back to original state
    store.setValue(BridgePointPreferencesStore.ALLOW_INT_TO_REAL_PROMOTION, i2rp);
    store.setValue(BridgePointPreferencesStore.ALLOW_REAL_TO_INT_COERCION, r2ic);
}
public void testInvocationPassI2R() throws RecognitionException, TokenStreamException {
    // Make sure the prefs are set to catch this error
    IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
    String i2rp = store.getString(BridgePointPreferencesStore.ALLOW_INT_TO_REAL_PROMOTION);
    String r2ic = store.getString(BridgePointPreferencesStore.ALLOW_REAL_TO_INT_COERCION);
    store.setValue(BridgePointPreferencesStore.ALLOW_INT_TO_REAL_PROMOTION, MessageDialogWithToggle.NEVER);
    store.setValue(BridgePointPreferencesStore.ALLOW_REAL_TO_INT_COERCION, MessageDialogWithToggle.NEVER);

    String act = "paramValRef( i: 3, r: j );"; //$NON-NLS-1$
	for (int i = 0; i < m_invocation_prefix.length; ++i) {
		String x = OalParserTest_Generics.parseAction("j = 2; " + m_invocation_prefix[i] + act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);//$NON-NLS-1$
		assertTrue(x.contains("Parameter ->r<- has been assigned value of different type"));//$NON-NLS-1$
	}
	OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	
    // Set the prefs back to original state
    store.setValue(BridgePointPreferencesStore.ALLOW_INT_TO_REAL_PROMOTION, i2rp);
    store.setValue(BridgePointPreferencesStore.ALLOW_REAL_TO_INT_COERCION, r2ic);
}
public void testInvocationBridgeAsParm() throws RecognitionException, TokenStreamException {
	String act = "paramValVal( i: T::test4(), b: T::test7(i:1, s:\"\", r:0.0) );"; //$NON-NLS-1$
	for (int i = 0; i < m_invocation_prefix.length; ++i) {
		String x = OalParserTest_Generics.parseAction(m_invocation_prefix[i] + act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);//$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
	}
	OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
}
public void testInvocationBridgeWithUnderscores() throws RecognitionException, TokenStreamException {
	String act = "_T::_test(_parm: 1);"; //$NON-NLS-1$
    String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	assertEquals("", x);//$NON-NLS-1$
	OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
}
public void testReadNoSuchParam() throws RecognitionException, TokenStreamException {
	String act = "x = param.s;"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:11-11: Parameter ->s<- is not associated with function ->paramVal<-",//$NON-NLS-1$
			":1:11-11: Parameter ->s<- is not associated with bridge ->paramVal<-",//$NON-NLS-1$
			":1:11-11: Parameter ->s<- is not associated with operation ->paramVal<-",//$NON-NLS-1$
			":1:11-11: Parameter ->s<- is not associated with operation ->paramVal<-",//$NON-NLS-1$
			};
	int[] numStmts = { 0, 0, 0, 0, 0 };
	paramErrorTest(
		act,
		OalParserTest_Generics.PARAM_VAL,
		err_msgs,
		1,
		3,
        0,
		"unexpected token: null",//$NON-NLS-1$
		"expecting Semicolon, found 'null'",//$NON-NLS-1$
		numStmts);
}
public void testWriteNoSuchParam() throws RecognitionException, TokenStreamException {
	String act = "param.s = \"bad\";"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:7-7: Parameter ->s<- is not associated with function ->paramVal<-",//$NON-NLS-1$
			":1:7-7: Parameter ->s<- is not associated with bridge ->paramVal<-",//$NON-NLS-1$
			":1:7-7: Parameter ->s<- is not associated with operation ->paramVal<-",//$NON-NLS-1$
			":1:7-7: Parameter ->s<- is not associated with operation ->paramVal<-",//$NON-NLS-1$
			};
	int[] numStmts = { 0, 0, 0, 0, 0 };
	paramErrorTest(
		act,
		OalParserTest_Generics.PARAM_VAL,
		err_msgs,
		0,
		3,
        0,
		"expecting TOK_EQUAL, found ';'",//$NON-NLS-1$
		"expecting Semicolon, found 'null'",//$NON-NLS-1$
		numStmts);
}
public void testWriteByValParam() throws RecognitionException, TokenStreamException {
	String act = "param.i = 1;"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			};
	int[] numStmts = { 0, 0, 0, 0, 0 };
	paramErrorTest(
		act,
		OalParserTest_Generics.PARAM_VAL,
		err_msgs,
		0,
		3,
        0,
		"expecting TOK_EQUAL, found ';'",//$NON-NLS-1$
		"expecting Semicolon, found 'null'",//$NON-NLS-1$
		numStmts);
}
public void testWriteByValParamMultParms() throws RecognitionException, TokenStreamException {
	String act = "param.i = 1;"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			};
	int[] numStmts = { 0, 0, 0, 0, 0 };
	paramErrorTest(
		act,
		OalParserTest_Generics.PARAM_VAL_VAL,
		err_msgs,
		0,
		3,
        0,
		"expecting TOK_EQUAL, found ';'",//$NON-NLS-1$
		"expecting Semicolon, found 'null'",//$NON-NLS-1$
		numStmts);
}
public void testWriteByValParamWrongType() throws RecognitionException, TokenStreamException {
	String act = "param.i = 2.1;"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			};
	int[] numStmts = { 0, 0, 0, 0, 0 };
	paramErrorTest(
		act,
		OalParserTest_Generics.PARAM_VAL,
		err_msgs,
		0,
		3,
        0,
		"expecting TOK_EQUAL, found ';'",//$NON-NLS-1$
		"expecting Semicolon, found 'null'",//$NON-NLS-1$
		numStmts);
}
public void testWriteByValParamWrongTypeMultParms() throws RecognitionException, TokenStreamException {
	String act = "param.i = 2.1;"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			":1:7-7: Parameter ->i<- is not passed by reference and is not assignable",//$NON-NLS-1$
			};
	int[] numStmts = { 0, 0, 0, 0, 0 };
	paramErrorTest(
		act,
		OalParserTest_Generics.PARAM_VAL_VAL,
		err_msgs,
		0,
		3,
        0,
		"expecting TOK_EQUAL, found ';'",//$NON-NLS-1$
		"expecting Semicolon, found 'null'",//$NON-NLS-1$
		numStmts);
}
public void testReadByValParamWrongType() throws RecognitionException, TokenStreamException {
	String act = "x = true; x = param.i;"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			};
	int[] numStmts = { 1, 1, 1, 1, 1 };
	paramErrorTestwithNonStdAssignmentPattern(act, OalParserTest_Generics.PARAM_VAL, err_msgs, 3, 2, 0, 1, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testReadByValParamWrongTypeMultParms() throws RecognitionException, TokenStreamException {
	String act = "x = true; x = param.i;"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			};
	int[] numStmts = { 1, 1, 1, 1, 1 };
	paramErrorTestwithNonStdAssignmentPattern(act, OalParserTest_Generics.PARAM_VAL_VAL, err_msgs, 3, 2, 0, 1, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testReadByRefParamWrongType() throws RecognitionException, TokenStreamException {
	String act = "x = 2.1; x = param.s;"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:20-20: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:20-20: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:20-20: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:20-20: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			};
	int[] numStmts = { 1, 1, 1, 1, 1 };
	paramErrorTestwithNonStdAssignmentPattern(act, OalParserTest_Generics.PARAM_REF, err_msgs, 3, 2, 0, 1, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testReadByRefParamWrongTypeMultParms() throws RecognitionException, TokenStreamException {
	String act = "x = 2.1; x = param.s;"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:20-20: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:20-20: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:20-20: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			":1:20-20: Variable ->x<- already exists as a different type",//$NON-NLS-1$
			};
	int[] numStmts = { 1, 1, 1, 1, 1 };
	paramErrorTestwithNonStdAssignmentPattern(act, OalParserTest_Generics.PARAM_REF_REF, err_msgs, 3, 2, 0, 1, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testWriteByRefParamWrongType() throws RecognitionException, TokenStreamException {
	String act = "param.s = 1.1;"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:11-13: Data types are not assignable or compatible across assignment statement",//$NON-NLS-1$
			":1:11-13: Data types are not assignable or compatible across assignment statement",//$NON-NLS-1$
			":1:11-13: Data types are not assignable or compatible across assignment statement",//$NON-NLS-1$
			":1:11-13: Data types are not assignable or compatible across assignment statement",//$NON-NLS-1$
			};
	int[] numStmts = { 0, 0, 0, 0, 0 };
	paramErrorTest(act, OalParserTest_Generics.PARAM_REF, err_msgs, 0, 2, 0, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationPassLiteralToByRef() throws RecognitionException, TokenStreamException {
	String act = "paramRef(s: \"bad\");"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:20-20: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:28-28: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:28-28: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:21-21: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:21-21: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:23-23: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:53-53: Cannot pass read-only value to pass-by-reference parameter ->s<-" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}
public void testInvocationPassExpressionToByRef() throws RecognitionException, TokenStreamException {
	String act = "paramRef(s: \"really \" + \"bad\" );"; //$NON-NLS-1$
	String err_msgs[] =
		{
			":1:33-33: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:41-41: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:41-41: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:34-34: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:34-34: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:36-36: Cannot pass read-only value to pass-by-reference parameter ->s<-",//$NON-NLS-1$
			":1:66-66: Cannot pass read-only value to pass-by-reference parameter ->s<-" };//$NON-NLS-1$
	int[] numStmts = { 0, 0, 0, 1, 1, 1, 1 };
	invocationErrorTest(act, err_msgs, 0, 2, "expecting Semicolon, found 'null'", "", numStmts);//$NON-NLS-1$//$NON-NLS-2$
}

public void tearDown() {
	try {
	  OalParserTest_Generics.tearDownActionData();
	}
	catch (RecognitionException re) {
		// do nothing
	}
	catch (TokenStreamException te) {
		// do nothing
	}
}

}
