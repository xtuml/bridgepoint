//========================================================================
//
//File:      $RCSfile: TestArray.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:00:35 $
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

import com.mentor.nucleus.bp.core.ActualParameter_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.EventSpecificationStatement_c;
import com.mentor.nucleus.bp.core.GenerateEventStatement_c;
import com.mentor.nucleus.bp.core.GenerateSmEventStatement_c;
import com.mentor.nucleus.bp.core.GenerateToClass_c;
import com.mentor.nucleus.bp.core.GenerateToCreator_c;
import com.mentor.nucleus.bp.core.GenerateToExternalEntity_c;
import com.mentor.nucleus.bp.core.Generate_c;
import com.mentor.nucleus.bp.core.InstanceHandle_c;
import com.mentor.nucleus.bp.core.InstanceSet_c;
import com.mentor.nucleus.bp.core.LiteralEnumerator_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.TransientVar_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.test.common.BaseTest;

/**
 * 
 * This test class just tests the *negative* cases where we expect to find
 * syntax errors.  The positive cases where no errors should be found it
 * covered by the "array_test" model.  The array_test exercises all valid
 * uses of Arrays in BP.  It is ran as part of the "Parse All" unit tests
 * defined in ParseAllInDomain.java.
 *
 */
public class TestArray extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
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

	public void testBadArrayDepth_BridgeParam() throws RecognitionException, TokenStreamException {
		String act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  T::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		String[] err = { ":1:179-179: Parameter ->str_Dim2<- has incompatible array depth.","line 1:181: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1][1] = 1; t_int_Dim2[1][1] = 1;  T::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		err[0] = ":1:185-185: Parameter ->int_Dim1<- has incompatible array depth."; //$NON-NLS-1$
		err[1] = "line 1:187: expecting Semicolon, found 'null'"; //$NON-NLS-1$
		validateBadStatement(act, err);
	}

	public void testBadArrayDepth_FunctionParam() throws RecognitionException, TokenStreamException {
		String act = "t_str_Dim1 = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  ::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		String[] err = { ":1:178-178: Parameter ->str_Dim1<- has incompatible array depth.","line 1:180: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1][1] = 1; t_int_Dim2[1][1] = 1;  ::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		err[0] = ":1:184-184: Parameter ->int_Dim1<- has incompatible array depth."; //$NON-NLS-1$
		err[1] = "line 1:186: expecting Semicolon, found 'null'"; //$NON-NLS-1$
		validateBadStatement(act, err);
	}

	public void testBadArrayDepth_MessageParam() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST;  t_str_Dim1 = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;   generate D_TST9(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2) to t;"; //$NON-NLS-1$
		String[] err = { ":1:226-226: Parameter ->str_Dim1<- has incompatible array depth.","line 1:228: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "select any t from instances of D_TST;  t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2[1][1][1] = 1;   generate D_TST9(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2) to t;"; //$NON-NLS-1$
		err[0] = ":1:232-232: Parameter ->int_Dim2<- has incompatible array depth."; //$NON-NLS-1$ 
		err[1] = "line 1:234: expecting Semicolon, found 'null'"; //$NON-NLS-1$
		validateBadStatement(act, err);
	}

	public void testBadArrayDepth_OperationParam() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST;  t_str_Dim1[1][1] = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;    t.testArray(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		String[] err = { ":1:224-224: Parameter ->str_Dim1<- has incompatible array depth.","line 1:226: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "select any t from instances of D_TST;  t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2 = 1;    t.testArray(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		err[0] = ":1:215-215: Parameter ->int_Dim2<- has incompatible array depth."; //$NON-NLS-1$
		err[1] = "line 1:217: expecting Semicolon, found 'null'"; //$NON-NLS-1$
		validateBadStatement(act, err);
	}

	public void testBadArrayDepth_ReturnValue() throws RecognitionException, TokenStreamException {
		//arrayTest_return_int_Dim1
		String act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1][1] = 1;  foo = T::arrayTest_return_int_Dim1(); foo = t_int_Dim2;"; //$NON-NLS-1$
		String[] err = { ":1:134-143: Variable ->foo<- has incompatible array depth.","line 1:145: expecting Semicolon, found 'null'" };  //$NON-NLS-1$
		validateBadStatement(act, err);
		
		// arrayTest_return_int_Dim2
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  foo = T::arrayTest_return_int_Dim2();  foo = t_int_Dim1;"; //$NON-NLS-1$
		err[0] = ":1:132-141: Variable ->foo<- has incompatible array depth.";  //$NON-NLS-1$
		err[1] = "line 1:143: expecting Semicolon, found 'null'";  //$NON-NLS-1$
		validateBadStatement(act, err);
		
		// arrayTest_return_str_Dim1
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  foo = T::arrayTest_return_str_Dim1();  foo = t_str_Dim2;"; //$NON-NLS-1$
		err[0] = ":1:132-141: Variable ->foo<- has incompatible array depth.";  //$NON-NLS-1$
		validateBadStatement(act, err);
		
		// arrayTest_return_str_Dim2
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  foo = T::arrayTest_return_str_Dim2();  foo = t_str_Dim1;"; //$NON-NLS-1$
		err[0] = ":1:132-141: Variable ->foo<- has incompatible array depth.";  //$NON-NLS-1$
		validateBadStatement(act, err);
	}
	
	public void testAOOB_Assignment() throws RecognitionException, TokenStreamException {
		String act = "t_str_Dim0[1][1] = \"\"; t_str_Dim5[5][5] = \"\";  t_str_Dim0[0] = t_str_Dim5[0];"; //$NON-NLS-1$
		String[] err = { ":1:76-76: Variable ->t_str_Dim0<- has incompatible dimension size with the rvalue.", "line 1:78: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testOneMoreDimensionInTransientCreation() throws RecognitionException, TokenStreamException {
		String act = "arr[5] = 1;\ntemp2 = arr[2][2];"; //$NON-NLS-1$
		String[] err = { ":2:17-17: The rvalue has one more dimension than the definition","line 2:19: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testTwoMoreDimensionInTransientCreation() throws RecognitionException, TokenStreamException {
		String act = "arr[5] = 1;\ntemp2 = arr[2][2][2];"; //$NON-NLS-1$
		String[] err = { ":2:20-20: The rvalue has 2 more dimensions than the definition","line 2:22: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testIncompatibleDepthForOperands() throws RecognitionException, TokenStreamException {
		String act = "arr2[4][4] =1;\ntemp4 = arr2[2]+arr2[2][3];"; //$NON-NLS-1$
		String[] err = { ":2:26-26: Incompatible array depth for the oprands","line 2:28: unexpected token: null","line 2:28: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testRightValueIncompatibleDepth1() throws RecognitionException, TokenStreamException {
		String act = "arr[5] =1;\ntemp7 = arr[3][2] + arr[3];"; //$NON-NLS-1$
		String[] err = { ":2:26-26: The rvalue has incompatible array depth","line 2:28: unexpected token: null","line 2:28: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testRightValueIncompatibleDepth2() throws RecognitionException, TokenStreamException {
		String act = "arr[5]=1;\ntemp8 = arr[1][1] + arr[1][1];"; //$NON-NLS-1$
		String[] err = { ":2:29-29: The rvalue has incompatible array depth","line 2:31: unexpected token: null","line 2:31: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testRightValueIncompatibleDepth3() throws RecognitionException, TokenStreamException {
		String act = "arr[5] =1;\ntemp9 = arr[1][1] + arr;"; //$NON-NLS-1$
		String[] err = { ":2:21-23: The rvalue has incompatible array depth","line 2:25: unexpected token: null","line 2:25: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testRightValueIncompatibleDepth4() throws RecognitionException, TokenStreamException {
		String act = "arr[5]=1;\ntemp10 = arr[1][1] * arr[1][1];"; //$NON-NLS-1$
		String[] err = { ":2:30-30: The rvalue has incompatible array depth","line 2:32: unexpected token: null","line 2:32: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testRightValueIncompatibleDepth5() throws RecognitionException, TokenStreamException {
		String act = "arr[5]=1;\ntemp11 = arr[1][1] + 2;"; //$NON-NLS-1$
		String[] err = { ":2:22-22: The rvalue has incompatible array depth","line 2:24: unexpected token: null","line 2:24: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testValidExprUsingArrayElement() throws RecognitionException, TokenStreamException {
		String act = "arr[5]=1;\ntemp6 = arr[3] + arr[3];"; //$NON-NLS-1$
		String[] err = { "" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	
	public void testCreateTransientUsingArrayElement() throws RecognitionException, TokenStreamException {
		String act = "arr[5]=1;\ntemp = arr[3];"; //$NON-NLS-1$
		String[] err = { "" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testCreateArrayTransientUsingArray() throws RecognitionException, TokenStreamException {
		String act = "arr2[5][5]=1;\ntemp1 = arr2[3];"; //$NON-NLS-1$
		String[] err = { "" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testCreateMultiDimensionArrayTransientUsingArray() throws RecognitionException, TokenStreamException {
		String act = "arr2[5][5]=1;\ntemp1 = arr2;"; //$NON-NLS-1$
		String[] err = { "" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testValidIfStatementUsingArrayElement1() throws RecognitionException, TokenStreamException {
		String act = "arr[5]=1;\nif ( arr[2] == 5) end if;"; //$NON-NLS-1$
		String[] err = { "" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testValidIfStatementUsingArrayElement2() throws RecognitionException, TokenStreamException {
		String act = "arr[5]=1;\nif ( arr[2]+arr[1] == 5) end if;"; //$NON-NLS-1$
		String[] err = { "" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testIncompatibleDepthInsideIfCondition() throws RecognitionException, TokenStreamException {
		String act = "arr[5]=1;\nif ( arr[1][2] == 5) end if; "; //$NON-NLS-1$
		String[] err = { ":2:19-19: The rvalue has incompatible array depth","line 2:22: expecting TOK_RPAREN, found 'end'","line 2:28: unexpected token: ;","line 2:30: unexpected token: null","line 2:30: unexpected token: null","line 2:30: expecting Semicolon, found 'null'","line 2:30: unexpected token: null","line 2:30: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	public void testIncompatibleDepthForOperandsInsideIfCondition() throws RecognitionException, TokenStreamException {
		String act = "arr[5]=1;\n arr2[3][4]=1;\n if ( arr[1]+arr2[2] == 5) end if; "; //$NON-NLS-1$
		String[] err = { ":3:20-20: Incompatible array depth for the oprands",":3:32-33: If expression data type is not boolean","line 3:36: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);		
	}
	
	
	
	
	
	// TODO FIXME:  This is incomplete and shall be updated when CQ issue 
	// dts0100584445 is resolved.
/*	public void testAOOB_BridgeParam() throws RecognitionException, TokenStreamException {
		assertTrue("Known issue. See CQ DEI dts0100584445", false);
		String act = "t_str_Dim1[2] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  T::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		String[] err = { "TODO" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[2][1] = 1;  T::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		err[0] = "";//$NON-NLS-1$
		validateBadStatement(act, err);
		
	}
*/
	// TODO FIXME:  This is incomplete and shall be updated when CQ issue 
	// dts0100584445 is resolved.
/*	public void testAOOB_FunctionParam() throws RecognitionException, TokenStreamException {
		assertTrue("Known issue. See CQ DEI dts0100584445", false);
		String act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][2] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  ::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		String[] err = { "TODO" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[2][1] = 1;  ::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		err[0] = "TODO";//$NON-NLS-1$
		validateBadStatement(act, err);
	}
*/
	// TODO FIXME:  This is incomplete and shall be updated when CQ issue 
	// dts0100584445 is resolved.
/*	public void testAOOB_MessageParam() throws RecognitionException, TokenStreamException {
		assertTrue("Known issue. See CQ DEI dts0100584445", false);
		String act = "select any t from instances of D_TST;  t_str_Dim1[1] = \"\"; t_str_Dim2[1][2] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;   generate D_TST9(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2) to t;"; //$NON-NLS-1$
		String[] err = { "TODO" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "select any t from instances of D_TST;  t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[2] = 1; t_int_Dim2[1][1] = 1;   generate D_TST9(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2) to t;"; //$NON-NLS-1$
		err[0] = "TODO";//$NON-NLS-1$
		validateBadStatement(act, err);
	}
*/
	// TODO FIXME:  This is incomplete and shall be updated when CQ issue 
	// dts0100584445 is resolved.
/*	public void testAOOB_OperationParam() throws RecognitionException, TokenStreamException {
		assertTrue("Known issue. See CQ DEI dts0100584445", false);
		String act = "select any t from instances of D_TST;  t_str_Dim1[11] = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;    t.testArray(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		String[] err = { "TODO" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "select any t from instances of D_TST;  t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2[11][1] = 1;    t.testArray(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		err[0] = "TODO";//$NON-NLS-1$
		validateBadStatement(act, err);
	}
*/
	// TODO FIXME:  This is incomplete and shall be updated when CQ issue 
	// dts0100584445 is resolved.
/*	public void testAOOB_ReturnValue() throws RecognitionException, TokenStreamException {
		assertTrue("Known issue. See CQ DEI dts0100584445", false);
		//arrayTest_return_int_Dim1
		String act = "t_str_Dim1[1] = \"\"; t_str_Dim2[2][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  foo = T::arrayTest_return_int_Dim1();"; //$NON-NLS-1$
		String[] err = { "TODO" };  //$NON-NLS-1$
		validateBadStatement(act, err);
		
		// arrayTest_return_int_Dim2
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][2] = 1;  foo = T::arrayTest_return_int_Dim2();"; //$NON-NLS-1$
		err[0] = "TODO";  //$NON-NLS-1$
		validateBadStatement(act, err);
		
		// arrayTest_return_str_Dim1
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[2][1] = 1;  foo = T::arrayTest_return_str_Dim1();"; //$NON-NLS-1$
		err[0] = "TODO";  //$NON-NLS-1$
		validateBadStatement(act, err);
		
		// arrayTest_return_str_Dim2
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[2][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  foo = T::arrayTest_return_str_Dim2();"; //$NON-NLS-1$
		err[0] = "TODO";  //$NON-NLS-1$
		validateBadStatement(act, err);
	}
*/	
	private void validateBadStatement(String act, String[] err_msg) throws RecognitionException,
			TokenStreamException {
		String x = OalParserTest.parseAction(act,
				OalParserTest.ACTIVITY_TYPE_FUNC,
				OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(err_msg.length, lines.length);
		for (int i = 0; i < err_msg.length; ++i) {
			assertEquals(err_msg[i], lines[i]); //$NON-NLS-1$
		}
	}
	
}
