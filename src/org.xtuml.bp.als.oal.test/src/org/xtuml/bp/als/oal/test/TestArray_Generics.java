//========================================================================
//
//File:      $RCSfile: TestArray_Generics.java,v $
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
package org.xtuml.bp.als.oal.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.test.common.OrderedRunner;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import junit.framework.TestCase;

/**
 * 
 * This test class just tests the *negative* cases where we expect to find
 * syntax errors.  The positive cases where no errors should be found it
 * covered by the "array_test" model.  The array_test exercises all valid
 * uses of Arrays in BP.  It is ran as part of the "Parse All" unit tests
 * defined in ParseAllInDomain.java.
 *
 */
@RunWith(OrderedRunner.class)
public class TestArray_Generics extends TestCase {

    @Before
	public void setUp() throws Exception {
        super.setUp();
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

	@Test
	public void testBadArrayDepth_BridgeParam() throws RecognitionException, TokenStreamException {
		String act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  T::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		String[] err = { ":1:179-179: Parameter ->str_Dim2<- has incompatible array depth.","line 1:181: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1][1] = 1; t_int_Dim2[1][1] = 1;  T::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		err[0] = ":1:185-185: Parameter ->int_Dim1<- has incompatible array depth."; //$NON-NLS-1$
		err[1] = "line 1:187: expecting Semicolon, found 'null'"; //$NON-NLS-1$
		validateBadStatement(act, err);
	}

	@Test
	public void testBadArrayDepth_FunctionParam() throws RecognitionException, TokenStreamException {
		String act = "t_str_Dim1 = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;  ::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		String[] err = { ":1:178-178: Parameter ->str_Dim1<- has incompatible array depth.","line 1:180: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\";  t_int_Dim1[1][1] = 1; t_int_Dim2[1][1] = 1;  ::arrayTest(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		err[0] = ":1:184-184: Parameter ->int_Dim1<- has incompatible array depth."; //$NON-NLS-1$
		err[1] = "line 1:186: expecting Semicolon, found 'null'"; //$NON-NLS-1$
		validateBadStatement(act, err);
	}

	@Test
	public void testBadArrayDepth_MessageParam() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST;  t_str_Dim1 = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;   generate D_TST9(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2) to t;"; //$NON-NLS-1$
		String[] err = { ":1:226-226: Parameter ->str_Dim1<- has incompatible array depth.","line 1:228: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "select any t from instances of D_TST;  t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2[1][1][1] = 1;   generate D_TST9(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2) to t;"; //$NON-NLS-1$
		err[0] = ":1:232-232: Parameter ->int_Dim2<- has incompatible array depth."; //$NON-NLS-1$ 
		err[1] = "line 1:234: expecting Semicolon, found 'null'"; //$NON-NLS-1$
		validateBadStatement(act, err);
	}

	@Test
	public void testBadArrayDepth_OperationParam() throws RecognitionException, TokenStreamException {
		String act = "select any t from instances of D_TST;  t_str_Dim1[1][1] = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2[1][1] = 1;    t.testArray(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		String[] err = { ":1:224-224: Parameter ->str_Dim1<- has incompatible array depth.","line 1:226: expecting Semicolon, found 'null'" };//$NON-NLS-1$
		validateBadStatement(act, err);
		
		act = "select any t from instances of D_TST;  t_str_Dim1[1] = \"\"; t_str_Dim2[1][1] = \"\"; t_int_Dim1[1] = 1; t_int_Dim2 = 1;    t.testArray(str_Dim1:t_str_Dim1, str_Dim2:t_str_Dim2, int_Dim1:t_int_Dim1, int_Dim2:t_int_Dim2);"; //$NON-NLS-1$
		err[0] = ":1:215-215: Parameter ->int_Dim2<- has incompatible array depth."; //$NON-NLS-1$
		err[1] = "line 1:217: expecting Semicolon, found 'null'"; //$NON-NLS-1$
		validateBadStatement(act, err);
	}

	@Test
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
	
	@Test
	public void testAOOB_Assignment() throws RecognitionException, TokenStreamException {
		String act = "t_str_Dim0[1][1] = \"\"; t_str_Dim5[5][5] = \"\";  t_str_Dim0[0] = t_str_Dim5[0];"; //$NON-NLS-1$
		String[] err = { ":1:76-76: Variable ->t_str_Dim0<- has incompatible dimension size with the rvalue.", "line 1:78: expecting Semicolon, found 'null'" };//$NON-NLS-1$
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
		String x = OalParserTest_Generics.parseAction(act,
				OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
				OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(err_msg.length, lines.length);
		for (int i = 0; i < err_msg.length; ++i) {
			assertEquals(err_msg[i], lines[i]); //$NON-NLS-1$
		}
	}
}
