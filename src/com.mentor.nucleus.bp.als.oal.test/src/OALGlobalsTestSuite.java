//=====================================================================
//
//File:      $RCSfile: OALGlobalsTestSuite.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:00:40 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.als.oal.test.ComponentParamTest;
import com.mentor.nucleus.bp.als.oal.test.ComponentScopeTest;
import com.mentor.nucleus.bp.als.oal.test.ComponentSyntaxTest;
import com.mentor.nucleus.bp.als.oal.test.ImplicitComponentAddressTest;
import com.mentor.nucleus.bp.als.oal.test.OalParserGlobalsTest;
import com.mentor.nucleus.bp.als.oal.test.OalParserTest;
import com.mentor.nucleus.bp.als.oal.test.ParseAllInDomain;
import com.mentor.nucleus.bp.als.oal.test.RelaxedSameDataTest;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_0;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_1;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_10;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_11;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_12;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_13;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_14;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_15;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_16;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_17;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_18;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_19;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_2;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_3;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_4;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_5;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_6;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_7;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_8;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_9;
import com.mentor.nucleus.bp.als.oal.test.TestArray;
import com.mentor.nucleus.bp.als.oal.test.TestAssign;
import com.mentor.nucleus.bp.als.oal.test.TestAttribute;
import com.mentor.nucleus.bp.als.oal.test.TestBPPrefAllowPromotion;
import com.mentor.nucleus.bp.als.oal.test.TestBPPrefStrictTyping;
import com.mentor.nucleus.bp.als.oal.test.TestCRUD;
import com.mentor.nucleus.bp.als.oal.test.TestControl;
import com.mentor.nucleus.bp.als.oal.test.TestEvent;
import com.mentor.nucleus.bp.als.oal.test.TestExpr;
import com.mentor.nucleus.bp.als.oal.test.TestInvocation;
import com.mentor.nucleus.bp.als.oal.test.TestLineNumbers;
import com.mentor.nucleus.bp.als.oal.test.TestSelect;
import com.mentor.nucleus.bp.als.oal.test.TestStructuredDataType;

public class OALGlobalsTestSuite extends TestSuite {

	public static Test suite() {
		return new OALGlobalsTestSuite();
	}
	public OALGlobalsTestSuite() {
		addTest(new TestSuite(OalParserGlobalsTest.class));
		addTest(new TestSuite(OalParserTest.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_0.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_1.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_2.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_3.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_4.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_5.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_6.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_7.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_8.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_9.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_10.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_11.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_12.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_13.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_14.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_15.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_16.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_17.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_18.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_19.class));
		addTest(new TestSuite(TestArray.class));
		addTest(new TestSuite(TestInvocation.class));
		addTest(new TestSuite(TestBPPrefAllowPromotion.class));
		addTest(new TestSuite(TestBPPrefStrictTyping.class));
		addTest(new TestSuite(TestAssign.class));
		addTest(new TestSuite(TestExpr.class));
		addTest(new TestSuite(TestAttribute.class));
		addTest(new TestSuite(TestControl.class));
		addTest(new TestSuite(TestCRUD.class));
		addTest(new TestSuite(TestSelect.class));
		addTest(new TestSuite(TestEvent.class));
		addTest(new TestSuite(TestLineNumbers.class));
		addTest(new TestSuite(TestStructuredDataType.class));
		addTest(new TestSuite(ParseAllInDomain.class));
		addTest(new TestSuite(ComponentSyntaxTest.class));
		addTest(new TestSuite(ImplicitComponentAddressTest.class));
		addTest(new TestSuite(RelaxedSameDataTest.class));
		addTest(new TestSuite(ComponentParamTest.class));
		addTest(new TestSuite(ComponentScopeTest.class));
	}
}
