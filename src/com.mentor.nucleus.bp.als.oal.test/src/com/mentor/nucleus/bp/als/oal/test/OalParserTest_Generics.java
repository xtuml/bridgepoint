package com.mentor.nucleus.bp.als.oal.test;
//=====================================================================
//
//File:      $RCSfile: OalParserTest_Generics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:52:48 $
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

import org.eclipse.core.resources.ResourcesPlugin;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

import com.mentor.nucleus.bp.als.oal.OalLexer;
import com.mentor.nucleus.bp.als.oal.OalParser;
import com.mentor.nucleus.bp.als.oal.Oal_validate;
import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.BinaryOperation_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.BridgeBody_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.DerivedAttributeBody_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.Oalconstants_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationBody_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.RelateUsing_c;
import com.mentor.nucleus.bp.core.Relate_c;
import com.mentor.nucleus.bp.core.SelectFromInstances_c;
import com.mentor.nucleus.bp.core.StateActionBody_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.TransitionActionBody_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.UnaryOperation_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.util.ContainerUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class OalParserTest_Generics extends BaseTest {

	private static boolean m_requiresClear = false;
	private static int m_funcType = 0;
	private static int m_funcNum = 0;
	private static boolean firstSetup = false;
	static public Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();
	public OalParserTest_Generics(String arg0) {
		super(null, arg0);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		if (!firstSetup)
		{
			
			
		String modelName = "testOAL1";
		
		TestingUtilities.importTestingProjectIntoWorkspace("testOAL1");
		
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("testOAL1");		
		m_sys = getSystemModel(project.getName());
		
		String modelRootId = Ooaofooa.createModelRootId(project, modelName, true);
		modelRoot = Ooaofooa.getInstance(modelRootId, true);

		
		}
		
		firstSetup = true;
		
		populateFunctionInstances();
		populateBridgeInstances();
		class Object_test1_c implements ClassQueryInterface_c {
			Object_test1_c(String id) {
				m_id = id;
			}
			private String m_id;
			public boolean evaluate(Object inst) {
				ModelClass_c selected = (ModelClass_c)inst;
				return selected.getKey_lett().equals(m_id);
			}
		}
		ModelClass_c diskObj = ModelClass_c.ModelClassInstance(modelRoot, new Object_test1_c("D_D"));//$NON-NLS-1$
		ModelClass_c libObj = ModelClass_c.ModelClassInstance(modelRoot, new Object_test1_c("D_H"));//$NON-NLS-1$
		populateTransformerInstances(diskObj, false);
		populateTransformerInstances(libObj, true);
		populateStateActionInstances();
		populateMDAInstances();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
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
	
	public void testAttributeWriteIssue176_3() throws RecognitionException, TokenStreamException {
		// this test must be first, so that the id assigner values are correct
		String x = parseAction("create object instance d1 of D_D; \nx = 5 + 1; d1.Disk_ID = 2;", ACTIVITY_TYPE_FUNC, TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
	}

	public void testEmptyAction() throws RecognitionException, TokenStreamException {
		String x = parseAction("", ACTIVITY_TYPE_FUNC, TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		validateBlkStmtVal( 1, 0, 0 );
		clearActionData(ACTIVITY_TYPE_FUNC, TEST_VOID_NO_PARM);
	}

	public void testParseMDA() throws RecognitionException, TokenStreamException {
		String act = "self.test_mda = 1;"; //$NON-NLS-1$
		String x = parseAction(act, ACTIVITY_TYPE_MDA, TEST_MDA);
		assertEquals("", x);
		validateBlkStmtVal( 1, 1, 3 );
		Variable_c [] vars = Variable_c.VariableInstances(modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testParseMDAReturn() throws RecognitionException, TokenStreamException {
		String act = "self.test_mda = 1; return;"; //$NON-NLS-1$
		String x = parseAction(act, ACTIVITY_TYPE_MDA, TEST_MDA);
		assertEquals("", x);
		validateBlkStmtVal( 1, 2, 3 );
		Variable_c [] vars = Variable_c.VariableInstances(modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testParseMDAWrongAttr() throws RecognitionException, TokenStreamException {
		String act = "self.test_mda = 1;  self.test_mda2 = 2;"; //$NON-NLS-1$
		String x = parseAction(act, ACTIVITY_TYPE_MDA, TEST_MDA);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:38-38: Cannot assign to a derived attribute ->test_mda2<-", lines[0]);//$NON-NLS-1$
		assertEquals("line 1:40: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		validateBlkStmtVal( 1, 1, 5 );
		Variable_c [] vars = Variable_c.VariableInstances(modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testMDANotAssigned() throws RecognitionException, TokenStreamException {
		String act = "x = 1;"; //$NON-NLS-1$
		String x = parseAction(act, ACTIVITY_TYPE_MDA, TEST_MDA);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(1, lines.length);
		assertEquals(":1:7-7: You must assign a value to the derived attribute self.test_mda", lines[0]);//$NON-NLS-1$
		validateBlkStmtVal( 1, 1, 2 );
		Variable_c [] vars = Variable_c.VariableInstances(modelRoot);
		assertEquals( 1, vars.length );
	}
	public void testParseMDAReturnValue() throws RecognitionException, TokenStreamException {
		String act = "self.test_mda = 1; return 5;"; //$NON-NLS-1$
		String x = parseAction(act, ACTIVITY_TYPE_MDA, TEST_MDA);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":1:27-27: Return value not required by derived attribute", lines[0]);//$NON-NLS-1$
		assertEquals("line 1:29: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		validateBlkStmtVal( 1, 1, 3 );
		Variable_c [] vars = Variable_c.VariableInstances(modelRoot);
		assertEquals( 1, vars.length );
		clearActionData(ACTIVITY_TYPE_MDA, TEST_MDA);
	}
	public void testAssignUnparsedMDA() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST; t.unparsed_mda = 3;"; //$NON-NLS-1$
		String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_VOID_NO_PARM);
		assertEquals("", x);//$NON-NLS-1$
		validateBlkStmtVal( 1, 2, 3 );
		Variable_c [] vars = Variable_c.VariableInstances(modelRoot);
		assertEquals( 1, vars.length );
		clearActionData(ACTIVITY_TYPE_FUNC, TEST_VOID_NO_PARM);
	}
	public void testDebugStatements() throws RecognitionException, TokenStreamException {
		String act = "_debug _on; _debug _off; _debug _stat; _debug _dump _off; _debug _dump _on; _debug _trace _off; _debug _trace _on; _debug _sor _off; _debug _sor _on;"; //$NON-NLS-1$
		String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_VOID_NO_PARM);
		assertEquals("", x);//$NON-NLS-1$
		validateBlkStmtVal( 1, 0, 0 );
		Variable_c [] vars = Variable_c.VariableInstances(modelRoot);
		assertEquals( 0, vars.length );
	}




	static final int ACTIVITY_TYPE_FUNC = 0;
	static final int ACTIVITY_TYPE_BRG = 1;
	static final int ACTIVITY_TYPE_CB_OP = 2;
	static final int ACTIVITY_TYPE_IB_OP = 3;
	static final int ACTIVITY_TYPE_MDA = 4;
	static final int ACTIVITY_TYPE_STATE = 5;
	static final int ACTIVITY_TYPE_TRANSITION = 6;
	static public String parseAction(String stmts, int funcType, int funcNum)
	{
		m_requiresClear = true;
		m_funcType = funcType;
		m_funcNum = funcNum;
		OalLexer lexer = new OalLexer(new StringReader(stmts));
		OalParser parser = new OalParser(modelRoot, lexer);
		try
		{
		switch (funcType) {
			case ACTIVITY_TYPE_FUNC :
				parser.m_oal_context = new Oal_validate(ContainerUtil.getContainer(m_testFunc[funcNum]));
				m_testFunc[funcNum].setAction_semantics_internal(stmts);
				parser.action(m_testFunc[funcNum].getSync_id(), Oalconstants_c.FUNCTION_TYPE);
				break;
			case ACTIVITY_TYPE_BRG :
				parser.m_oal_context = new Oal_validate(ContainerUtil.getContainer(m_testBrg[funcNum]));
				m_testBrg[funcNum].setAction_semantics_internal(stmts);
				parser.action(m_testBrg[funcNum].getBrg_id(), Oalconstants_c.BRIDGE_TYPE);
				break;
			case ACTIVITY_TYPE_CB_OP :
				parser.m_oal_context = new Oal_validate(ContainerUtil.getContainer(m_testCBTfr[funcNum]));
				m_testCBTfr[funcNum].setAction_semantics_internal(stmts);
				parser.action(m_testCBTfr[funcNum].getTfr_id(), Oalconstants_c.OPERATION_TYPE);
				break;
			case ACTIVITY_TYPE_IB_OP :
				parser.m_oal_context = new Oal_validate(ContainerUtil.getContainer(m_testIBTfr[funcNum]));
				m_testIBTfr[funcNum].setAction_semantics_internal(stmts);
				parser.action(m_testIBTfr[funcNum].getTfr_id(), Oalconstants_c.OPERATION_TYPE);
				break;
			case ACTIVITY_TYPE_MDA :
				parser.m_oal_context = new Oal_validate(ContainerUtil.getContainer(m_testMDA[funcNum]));
				m_testMDA[funcNum].setAction_semantics_internal(stmts);
				parser.action(m_testMDA[funcNum].getAttr_id(), Oalconstants_c.MDA_TYPE);
				break;
			case ACTIVITY_TYPE_STATE :
				parser.m_oal_context = new Oal_validate(ContainerUtil.getContainer(m_testAction[funcNum]));
				TransitionActionBody_c tab = TransitionActionBody_c.getOneACT_TABOnR688(m_testAction[funcNum]);
				if (tab != null) {
					Body_c body = Body_c.getOneACT_ACTOnR698(tab);
					body.Dispose();
				}
				m_testAction[funcNum].setAction_semantics_internal(stmts);
				parser.action(m_testAction[funcNum].getAct_id(), Oalconstants_c.STATE_TYPE);
				break;
			case ACTIVITY_TYPE_TRANSITION :
				parser.m_oal_context = new Oal_validate(ContainerUtil.getContainer(m_testAction[funcNum]));
				// This test action can have both forms of Action body associated
				StateActionBody_c sab = StateActionBody_c.getOneACT_SABOnR691(m_testAction[funcNum]);
				if (sab != null) {
					Body_c body = Body_c.getOneACT_ACTOnR698(sab);
					body.Dispose();
				}
				m_testAction[funcNum].setAction_semantics_internal(stmts);
				parser.action(m_testAction[funcNum].getAct_id(), Oalconstants_c.TRANSITION_TYPE);
				break;
		    default:
		    	fail("parseAction: Unknown Activity type constant");
		}
		}
		catch (TokenStreamException e)
		{
		  Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
		  if ( e instanceof TokenStreamRecognitionException )
		  {
			  TokenStreamRecognitionException tsre = (TokenStreamRecognitionException)e;
			  parser.reportError(tsre.recog);
		  }
		  else
		  {
			  fail("Token stream exception in parser");
		  }
		}
		catch (RecognitionException e)
		{
			Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
			parser.reportError(e);
		}
		catch (InterruptedException ie){
		}
		return parser.m_output;
	}
	static public void clearActionData(int funcType, int funcNum) throws RecognitionException, TokenStreamException {
		switch (funcType) {
			case ACTIVITY_TYPE_FUNC :
				Body_c actact = Body_c.getOneACT_ACTOnR698(FunctionBody_c.getOneACT_FNBOnR695(m_testFunc[funcNum]));
				if (actact != null) {
					actact.Clear_blocks();
				}
				break;
			case ACTIVITY_TYPE_BRG :
				actact = Body_c.getOneACT_ACTOnR698(BridgeBody_c.getOneACT_BRBOnR697(m_testBrg[funcNum]));
				if (actact != null) {
					actact.Clear_blocks();
				}
				break;
			case ACTIVITY_TYPE_CB_OP :
				actact = Body_c.getOneACT_ACTOnR698(OperationBody_c.getOneACT_OPBOnR696(m_testCBTfr[funcNum]));
				if (actact != null) {
					actact.Clear_blocks();
				}
				break;
			case ACTIVITY_TYPE_IB_OP :
				actact = Body_c.getOneACT_ACTOnR698(OperationBody_c.getOneACT_OPBOnR696(m_testIBTfr[funcNum]));
				if (actact != null) {
					actact.Clear_blocks();
				}
				break;
			case ACTIVITY_TYPE_MDA :
				actact = Body_c.getOneACT_ACTOnR698(DerivedAttributeBody_c.getOneACT_DABOnR693(m_testMDA[funcNum]));
				if (actact != null) {
					actact.Clear_blocks();
				}
				break;
			case ACTIVITY_TYPE_STATE :
				actact = Body_c.getOneACT_ACTOnR698(StateActionBody_c.getOneACT_SABOnR691(m_testAction[funcNum]));
				if (actact != null) {
					actact.Clear_blocks();
				}
				break;
			case ACTIVITY_TYPE_TRANSITION :
				actact = Body_c.getOneACT_ACTOnR698(TransitionActionBody_c.getOneACT_TABOnR688(m_testAction[funcNum]));
				if (actact != null) {
					actact.Clear_blocks();
				}
				break;
		    default:
		    	fail("clearAction: Unknown Activity type constant");
		}
		m_requiresClear = false;
	}
	static public void tearDownActionData() throws RecognitionException, TokenStreamException {
		if (m_requiresClear) {
			clearActionData(m_funcType, m_funcNum);
		}
	}
	public static  void verifyBinaryOperation(BinaryOperation_c bin, String op, UUID left, UUID right) {
		assertEquals(bin.getOperator(), op);
		assertEquals(bin.getLeft_value_id(), left);
		assertEquals(bin.getRight_value_id(), right);
	}
	public static void verifyUnaryOperation(UnaryOperation_c u, String op, UUID operand) {
		assertEquals(op, u.getOperator());
		assertEquals(operand, u.getOperand_value_id());
	}
	static public void validateBlkStmtVal(int numBlock, int numStmt, int numVal) {
		FunctionBody_c[] fb = FunctionBody_c.FunctionBodyInstances(modelRoot);
		BridgeBody_c[] bb = BridgeBody_c.BridgeBodyInstances(modelRoot);
		OperationBody_c[] ob = OperationBody_c.OperationBodyInstances(modelRoot);
		StateActionBody_c[] sab = StateActionBody_c.StateActionBodyInstances(modelRoot);
		DerivedAttributeBody_c[] dab = DerivedAttributeBody_c.DerivedAttributeBodyInstances(modelRoot);
		TransitionActionBody_c[] tab = TransitionActionBody_c.TransitionActionBodyInstances(modelRoot);
		Body_c b[] = Body_c.BodyInstances(modelRoot);
		assertEquals( b.length, fb.length + bb.length + ob.length + sab.length + dab.length + tab.length);
		Block_c[] blk = Block_c.BlockInstances(modelRoot);
		assertEquals(numBlock, blk.length);
		Body_c[] bods = Body_c.BodyInstances(modelRoot);
		for ( int i = 0; i < bods.length; ++i)
		   assertEquals( IdAssigner.NULL_UUID, bods[i].getCurrentscope_id());
		Statement_c[] st = Statement_c.StatementInstances(modelRoot);
		assertEquals(numStmt, st.length);
		Value_c[] val = Value_c.ValueInstances(modelRoot);
		assertEquals(numVal, val.length);
	}
	static public void badRelationshipValidate(String[] vars, int numStmt,
			int numSel, int numVal) throws RecognitionException,
			TokenStreamException {
		Block_c[] blk = Block_c.BlockInstances(modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(modelRoot);
		assertEquals(numStmt, st.length);
		for (int i = 0; i < numStmt; ++i)
			assertEquals(blk[0].getBlock_id(), st[i].getBlock_id());
		Variable_c[] t = Variable_c.VariableInstances(modelRoot);
		assertEquals(vars.length, t.length);
		for (int i = 0; i < vars.length; ++i)
			assertEquals(vars[i], t[i].getName());
		Relate_c rel[] = Relate_c.RelateInstances(modelRoot);
		assertEquals(0, rel.length);
		RelateUsing_c ru[] = RelateUsing_c.RelateUsingInstances(modelRoot);
		assertEquals(0, ru.length);
		SelectFromInstances_c sel[] = SelectFromInstances_c
				.SelectFromInstancesInstances(modelRoot);
		assertEquals(numSel, sel.length);
		Value_c[] val = Value_c.ValueInstances(modelRoot);
		assertEquals(numVal, val.length);
	}


	static public final int TEST_VOID_NO_PARM = 0;
	static public final int TEST_INT_NO_PARM = 1;
	static public final int TEST_REAL_NO_PARM = 2;
	static public final int TEST_STRING_NO_PARM = 3;
	static public final int TEST1 = 4;
	static public final int TEST2 = 5;
	static public final int TEST3 = 6;
	static public final int TEST4 = 7;
	static public final int TEST5 = 8;
	static public final int TEST6 = 9;
	static public final int TEST7 = 10;
	static public final int MULT_FUNC1 = 11;
	static public final int MULT_FUNC2 = 12;
	static public final int MULT_PARMS = 13;
	static public final int PARAM_VAL = 14;
	static public final int PARAM_REF = 15;
	static public final int PARAM_VAL_VAL = 16;
	static public final int PARAM_VAL_REF = 17;
	static public final int PARAM_REF_REF = 18;
	static public final int _TEST = 19;
	static public final int _TEST_REF = 20;
	static private String funcs[] =
		{
			"testVoidNoParm", //$NON-NLS-1$
			"testIntNoParm",//$NON-NLS-1$
			"testRealNoParm",//$NON-NLS-1$
			"testStringNoParm",//$NON-NLS-1$
			"test1",//$NON-NLS-1$
			"test2",//$NON-NLS-1$
			"test3",//$NON-NLS-1$
			"test4",//$NON-NLS-1$
			"test5",//$NON-NLS-1$
			"test6",//$NON-NLS-1$
			"test7",//$NON-NLS-1$
			"mult_func1",//$NON-NLS-1$
			"mult_func2",//$NON-NLS-1$
			"mult_parms",//$NON-NLS-1$
			"paramVal",//$NON-NLS-1$
			"paramRef",//$NON-NLS-1$
			"paramValVal",//$NON-NLS-1$
			"paramValRef",//$NON-NLS-1$
			"paramRefRef",//$NON-NLS-1$
			"_test",//$NON-NLS-1$
			"_testRef" };//$NON-NLS-1$
	public static Function_c[] m_testFunc = new Function_c[funcs.length];
	public static Bridge_c[] m_testBrg = new Bridge_c[funcs.length];
	public static Operation_c[] m_testCBTfr = new Operation_c[funcs.length];
	public static Operation_c[] m_testIBTfr = new Operation_c[funcs.length];
	private void populateFunctionInstances() {
		class Function_test1_c implements ClassQueryInterface_c {
			Function_test1_c(String p) {
				m_p = p;
			}
			private String m_p;
			public boolean evaluate(Object inst) {
				Function_c selected = (Function_c)inst;
				return selected.getName().equals(m_p);
			}
		}
		for (int i = 0; i < funcs.length; ++i) {
			Function_c testFunc =
				Function_c.FunctionInstance(modelRoot, new Function_test1_c(funcs[i]));
			if (testFunc != null) {
				if (m_testFunc[i] == null)
					m_testFunc[i] = testFunc;
			} else
				fail("Missing function " + funcs[i]);//$NON-NLS-1$
		}
	}
	private void populateBridgeInstances() {
		class EE_test1_c implements ClassQueryInterface_c {
			EE_test1_c(String p) {
				m_p = p;
			}
			private String m_p;
			public boolean evaluate(Object inst) {
				ExternalEntity_c selected = (ExternalEntity_c)inst;
				return selected.getKey_lett().equals(m_p);
			}
		}
		ExternalEntity_c testEE = ExternalEntity_c.ExternalEntityInstance(modelRoot, new EE_test1_c("T"));//$NON-NLS-1$
		if (testEE == null)
			fail("Unknown external entity T");//$NON-NLS-1$
		class Bridge_test1_c implements ClassQueryInterface_c {
			Bridge_test1_c(String p) {
				m_p = p;
			}
			private String m_p;
			public boolean evaluate(Object inst) {
				Bridge_c selected = (Bridge_c)inst;
				return selected.getName().equals(m_p);
			}
		}
		for (int i = 0; i < funcs.length; ++i) {
			Bridge_c testFunc = Bridge_c.BridgeInstance(modelRoot, new Bridge_test1_c(funcs[i]));
			if (testFunc != null) {
				if (m_testBrg[i] == null)
					m_testBrg[i] = testFunc;
			} else
				fail("Missing bridge " + funcs[i]);//$NON-NLS-1$
		}
	}
	private void populateTransformerInstances(ModelClass_c testObj, boolean instanceBased) {
		for (int i = 0; i < funcs.length; ++i) {
			Operation_c[] testObjTrans = Operation_c.getManyO_TFRsOnR115(testObj);
			Operation_c testTrans = null;
			for (int tfrInstCount = 0; tfrInstCount < testObjTrans.length; tfrInstCount++) {
				Operation_c selected = testObjTrans[tfrInstCount];
				if (selected.getName().equals(funcs[i])) {
					testTrans = testObjTrans[tfrInstCount];
					break;
				}
			}
			if (testTrans != null) {
				if (instanceBased) {
					if (m_testIBTfr[i] == null)
						m_testIBTfr[i] = testTrans;
				} else {
					if (m_testCBTfr[i] == null)
						m_testCBTfr[i] = testTrans;
				}
			} else
				fail("Missing operation " + testObj.getKey_lett() + "::" + funcs[i]);//$NON-NLS-1$//$NON-NLS-2$
		}
	}
	static public final int TEST_MDA = 0;
	static public final int TEST_MDA2 = 1;
	static public final int UNPARSED_MDA = 2;
	static private String mda_names[] =
		{
			"test_mda", //$NON-NLS-1$
			"test_mda2",//$NON-NLS-1$
			"unparsed_mda",//$NON-NLS-1$
		};
	public static DerivedBaseAttribute_c[] m_testMDA = new DerivedBaseAttribute_c[mda_names.length];
	private void populateMDAInstances() {
		class MDA_test1_c implements ClassQueryInterface_c {
			MDA_test1_c(String p) {
				m_p = p;
			}
			private String m_p;
			public boolean evaluate(Object inst) {
				DerivedBaseAttribute_c selected = (DerivedBaseAttribute_c)inst;
				Attribute_c mdattr =
					Attribute_c.getOneO_ATTROnR106(
						BaseAttribute_c.getOneO_BATTROnR107(selected));
				return mdattr.getName().equals(m_p);
			}
		}
		for (int i = 0; i < mda_names.length; ++i) {
			DerivedBaseAttribute_c testMDA =
			DerivedBaseAttribute_c.DerivedBaseAttributeInstance(modelRoot, new MDA_test1_c(mda_names[i]));
			if (testMDA != null) {
				if (m_testMDA[i] == null)
					m_testMDA[i] = testMDA;
			} else
				fail("Missing MDA " + mda_names[i]);//$NON-NLS-1$
		}
	}
	static public final int STATE_ISM_NONE = 0;
	static public final int STATE_ISM_ONE = 1;
	static public final int STATE_ISM_TWO = 2;
	static public final int STATE_ISM_THREE = 3;
	static public final int STATE_ASM_ONE = 4;
	static public final int STATE_ASM_TWO = 5;
	static public final int STATE_ASM_THREE = 6;
	static public final int STATE_ISM_POLYTWO = 7;
	static public final int STATE_ISM_POLYORPHANED = 8;
	static public final int TRANS_ISM_NONE = 9;
	static public final int TRANS_ISM_ONE = 10;
	static public final int TRANS_ISM_TWO = 11;
	static public final int TRANS_ISM_THREE = 12;
	static public final int TRANS_CREATION_ISM_NONE = 13;
	static public final int TRANS_CREATION_ISM_ONE = 14;
	static public final int TRANS_CREATION_ISM_TWO = 15;
	static public final int TRANS_CREATION_ISM_THREE = 16;
	static public final int TRANS_CSM_NONE = 17;
	static public final int TRANS_CSM_ONE = 18;
	static public final int TRANS_CSM_TWO = 19;
	static public final int TRANS_CSM_THREE = 20;
	static public final int TRANS_ISM_POLYTWO = 21;
	static public final int TRANS_ISM_POLYORPHANED = 22;
	static public final int STATE_ISM_SDTTest = 23;
	static public final int TRANS_CREATION_ISM_SDTTest = 24;
	static public final int TRANS_ISM_SDTTest = 25;
	static public final int STATE_ASM_SDTTest = 26;
	static public final int TRANS_CSM_SDTTest = 27;
	static public final int STATE_ISM_SDTMultLvlTest = 28;
	static public final int TRANS_CREATION_ISM_SDTMultLvlTest = 29;
	static public final int TRANS_ISM_SDTMultLvlTest = 30;
	static public final int STATE_ASM_SDTMultLvlTest = 31;
	static public final int TRANS_CSM_SDTMultLvlTest = 32;

	
	public static Action_c[] m_testAction = new Action_c[33];

	private void populateStateActionInstances() {
		class Object_test1_c implements ClassQueryInterface_c {
			Object_test1_c(String p) {
				m_p = p;
			}
			private String m_p;
			public boolean evaluate(Object inst) {
				ModelClass_c selected = (ModelClass_c)inst;
				return selected.getName().equals(m_p);
			}
		}
		ModelClass_c obj = ModelClass_c.ModelClassInstance(modelRoot, new Object_test1_c("Test"));//$NON-NLS-1$
		InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR518(obj);
		StateMachine_c sm_i = StateMachine_c.getOneSM_SMOnR517(ism);
        StateMachineState_c [] states = StateMachineState_c.getManySM_STATEsOnR501(sm_i);
		Action_c i_acts[] = Action_c.getManySM_ACTsOnR514(
				                ActionHome_c.getManySM_AHsOnR513(
						      MooreActionHome_c.getManySM_MOAHsOnR511(states)));
		for ( int i = 0; i < i_acts.length; ++i )
		{
			ActionHome_c ah = ActionHome_c.getOneSM_AHOnR514(i_acts[i]);
			MooreActionHome_c moah = MooreActionHome_c.getOneSM_MOAHOnR513(ah);
 		    StateMachineState_c st = StateMachineState_c.getOneSM_STATEOnR511(moah);
			if ( st.getName().equals("none") )//$NON-NLS-1$
			  m_testAction[STATE_ISM_NONE] = i_acts[i];
			else if ( st.getName().equals("one") )//$NON-NLS-1$
			  m_testAction[STATE_ISM_ONE] = i_acts[i];
			else if ( st.getName().equals("two") )//$NON-NLS-1$
			  m_testAction[STATE_ISM_TWO] = i_acts[i];
			else if ( st.getName().equals("three") )//$NON-NLS-1$
			  m_testAction[STATE_ISM_THREE] = i_acts[i];
			else if ( st.getName().equals("SDTTest") )//$NON-NLS-1$
			  m_testAction[STATE_ISM_SDTTest] = i_acts[i];
			else if ( st.getName().equals("SDTMultLvlTest") )//$NON-NLS-1$
			  m_testAction[STATE_ISM_SDTMultLvlTest] = i_acts[i];
		}

		NewStateTransition_c [] trans = NewStateTransition_c.
		        getManySM_NSTXNsOnR507(Transition_c.getManySM_TXNsOnR505(sm_i));
		Action_c [] t_acts = Action_c.getManySM_ACTsOnR514(
				                ActionHome_c.getManySM_AHsOnR513(
						   TransitionActionHome_c.getManySM_TAHsOnR530(
								    Transition_c.getManySM_TXNsOnR507(trans))));
		for ( int i = 0; i < t_acts.length; ++i )
		{
			ActionHome_c ah = ActionHome_c.getOneSM_AHOnR514(t_acts[i]);
			TransitionActionHome_c tah = TransitionActionHome_c.getOneSM_TAHOnR513(ah);
			Transition_c tran = Transition_c.getOneSM_TXNOnR530(tah);
			if (tran.Get_name().contains("none"))//$NON-NLS-1$
			  m_testAction[TRANS_ISM_NONE] = t_acts[i];
			else if ( tran.getName().contains("one") )//$NON-NLS-1$
			  m_testAction[TRANS_ISM_ONE] = t_acts[i];
			else if ( tran.getName().contains("two") )//$NON-NLS-1$
			  m_testAction[TRANS_ISM_TWO] = t_acts[i];
			else if ( tran.getName().contains("three") )//$NON-NLS-1$
			  m_testAction[TRANS_ISM_THREE] = t_acts[i];
			else if ( tran.getName().contains("SDTTest") )//$NON-NLS-1$
			  m_testAction[TRANS_ISM_SDTTest] = t_acts[i];
			else if ( tran.getName().contains("SDTMultLvlTest") )//$NON-NLS-1$
			  m_testAction[TRANS_ISM_SDTMultLvlTest] = t_acts[i];
		}

		CreationTransition_c [] crtrs = CreationTransition_c.
                getManySM_CRTXNsOnR507(Transition_c.getManySM_TXNsOnR505(sm_i));
        Action_c [] crt_acts = Action_c.getManySM_ACTsOnR514(
		                      ActionHome_c.getManySM_AHsOnR513(
				     TransitionActionHome_c.getManySM_TAHsOnR530(
					                Transition_c.getManySM_TXNsOnR507(crtrs))));
        for ( int i = 0; i < crt_acts.length; ++i )
        {
	      ActionHome_c ah = ActionHome_c.getOneSM_AHOnR514(crt_acts[i]);
	      TransitionActionHome_c tah = TransitionActionHome_c.getOneSM_TAHOnR513(ah);
	      Transition_c tran = Transition_c.getOneSM_TXNOnR530(tah);
	      if (tran.Get_name().contains("none"))//$NON-NLS-1$
	        m_testAction[TRANS_CREATION_ISM_NONE] = crt_acts[i];
	      else if ( tran.getName().contains("one") )//$NON-NLS-1$
	        m_testAction[TRANS_CREATION_ISM_ONE] = crt_acts[i];
	      else if ( tran.getName().contains("two") )//$NON-NLS-1$
	        m_testAction[TRANS_CREATION_ISM_TWO] = crt_acts[i];
	      else if ( tran.getName().contains("three") )//$NON-NLS-1$
	        m_testAction[TRANS_CREATION_ISM_THREE] = crt_acts[i];
	      else if ( tran.getName().contains("SDTTest") )//$NON-NLS-1$
		    m_testAction[TRANS_CREATION_ISM_SDTTest] = crt_acts[i];
	      else if ( tran.getName().contains("SDTMultLvlTest") )//$NON-NLS-1$
		    m_testAction[TRANS_CREATION_ISM_SDTMultLvlTest] = crt_acts[i];
        }

		ClassStateMachine_c asm = ClassStateMachine_c.getOneSM_ASMOnR519(obj);
		StateMachine_c sm_a = StateMachine_c.getOneSM_SMOnR517(asm);
        states = StateMachineState_c.getManySM_STATEsOnR501(sm_a);
		Action_c a_acts[] = Action_c.getManySM_ACTsOnR514(
				                ActionHome_c.getManySM_AHsOnR513(
						      MooreActionHome_c.getManySM_MOAHsOnR511(states)));
		for ( int i = 0; i < a_acts.length; ++i )
		{
			ActionHome_c ah = ActionHome_c.getOneSM_AHOnR514(a_acts[i]);
			MooreActionHome_c moah = MooreActionHome_c.getOneSM_MOAHOnR513(ah);
			if (moah != null) {
			  StateMachineState_c st = StateMachineState_c.getOneSM_STATEOnR511(moah);
			  if ( st.getName().equals("one") )//$NON-NLS-1$
			    m_testAction[STATE_ASM_ONE] = a_acts[i];
			  else if ( st.getName().equals("two") )//$NON-NLS-1$
			    m_testAction[STATE_ASM_TWO] = a_acts[i];
			  else if ( st.getName().equals("three") )//$NON-NLS-1$
			    m_testAction[STATE_ASM_THREE] = a_acts[i];
			  else if ( st.getName().equals("SDTTest") )//$NON-NLS-1$
				m_testAction[STATE_ASM_SDTTest] = a_acts[i];
			  else if ( st.getName().equals("SDTMultLvlTest") )//$NON-NLS-1$
			    m_testAction[STATE_ASM_SDTMultLvlTest] = a_acts[i];
			}
		}
		trans = NewStateTransition_c.
        getManySM_NSTXNsOnR507(Transition_c.getManySM_TXNsOnR505(sm_a));
        t_acts = Action_c.getManySM_ACTsOnR514(
		                ActionHome_c.getManySM_AHsOnR513(
				      TransitionActionHome_c.getManySM_TAHsOnR530(
						            Transition_c.getManySM_TXNsOnR507(trans))));
        for ( int i = 0; i < t_acts.length; ++i )
        {
	      ActionHome_c ah = ActionHome_c.getOneSM_AHOnR514(t_acts[i]);
	      TransitionActionHome_c tah = TransitionActionHome_c.getOneSM_TAHOnR513(ah);
	      Transition_c tran = Transition_c.getOneSM_TXNOnR530(tah);
	      if (tran.Get_name().contains("none"))//$NON-NLS-1$
	        m_testAction[TRANS_CSM_NONE] = t_acts[i];
	      else if ( tran.getName().contains("one") )//$NON-NLS-1$
	        m_testAction[TRANS_CSM_ONE] = t_acts[i];
	      else if ( tran.getName().contains("two") )//$NON-NLS-1$
	        m_testAction[TRANS_CSM_TWO] = t_acts[i];
	      else if ( tran.getName().contains("three") )//$NON-NLS-1$
	        m_testAction[TRANS_CSM_THREE] = t_acts[i];
	      else if ( tran.getName().contains("SDTTest") )//$NON-NLS-1$
		    m_testAction[TRANS_CSM_SDTTest] = t_acts[i];
	      else if ( tran.getName().contains("SDTMultLvlTest") )//$NON-NLS-1$
			m_testAction[TRANS_CSM_SDTMultLvlTest] = t_acts[i];
        }

		obj = ModelClass_c.ModelClassInstance(modelRoot, new Object_test1_c("Test Poly"));//$NON-NLS-1$
		ism = InstanceStateMachine_c.getOneSM_ISMOnR518(obj);
		sm_i = StateMachine_c.getOneSM_SMOnR517(ism);

		i_acts = Action_c.getManySM_ACTsOnR515(sm_i);
		for ( int i = 0; i < i_acts.length; ++i )
		{
			ActionHome_c ah = ActionHome_c.getOneSM_AHOnR514(i_acts[i]);
			MooreActionHome_c moah = MooreActionHome_c.getOneSM_MOAHOnR513(ah);
			if (moah != null) {
			  StateMachineState_c st = StateMachineState_c.getOneSM_STATEOnR511(moah);
			  if ( st.getName().equals("PolyTwo") )//$NON-NLS-1$
			    m_testAction[STATE_ISM_POLYTWO] = i_acts[i];
			  else if ( st.getName().equals("PolyOrphaned") )//$NON-NLS-1$
			    m_testAction[STATE_ISM_POLYORPHANED] = i_acts[i];
			}
		}

		trans = NewStateTransition_c.
        getManySM_NSTXNsOnR507(Transition_c.getManySM_TXNsOnR505(sm_i));
        t_acts = Action_c.getManySM_ACTsOnR514(
		                ActionHome_c.getManySM_AHsOnR513(
				      TransitionActionHome_c.getManySM_TAHsOnR530(
						            Transition_c.getManySM_TXNsOnR507(trans))));
        for ( int i = 0; i < t_acts.length; ++i )
        {
	      ActionHome_c ah = ActionHome_c.getOneSM_AHOnR514(t_acts[i]);
	      TransitionActionHome_c tah = TransitionActionHome_c.getOneSM_TAHOnR513(ah);
	      Transition_c tran = Transition_c.getOneSM_TXNOnR530(tah);
			StateMachineState_c toState = StateMachineState_c
					.getOneSM_STATEOnR503(StateEventMatrixEntry_c
							.getManySM_SEMEsOnR504(NewStateTransition_c
									.getManySM_NSTXNsOnR507(tran)));
	      if (toState.getName().equals("PolyTwo"))//$NON-NLS-1$
	        m_testAction[TRANS_ISM_POLYTWO] = t_acts[i];
	      else if ( toState.getName().equals("PolyOrphaned") )//$NON-NLS-1$
	        m_testAction[TRANS_ISM_POLYORPHANED] = t_acts[i];
        }
		
	}
}
