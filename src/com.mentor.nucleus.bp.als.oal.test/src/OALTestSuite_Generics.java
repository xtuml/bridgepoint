//=====================================================================
//
//File:      $RCSfile: OALTestSuite_Generics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/03/13 22:07:35 $
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

import com.mentor.nucleus.bp.als.oal.test.ComponentParamTest_Generics;
import com.mentor.nucleus.bp.als.oal.test.ComponentScopeTest_Generics;
import com.mentor.nucleus.bp.als.oal.test.ComponentSyntaxTest_Generics;
import com.mentor.nucleus.bp.als.oal.test.ImplicitComponentAddressTest_Generics;
import com.mentor.nucleus.bp.als.oal.test.OalParserTest_Generics;
import com.mentor.nucleus.bp.als.oal.test.ParseAllInDomain_Generics;
import com.mentor.nucleus.bp.als.oal.test.PolyEventTest;
import com.mentor.nucleus.bp.als.oal.test.RelaxedSameDataTest_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_0_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_1_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_19_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_2_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_3_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_4_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_5_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_6_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_7_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_8_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_9_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_10_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_11_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_12_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_13_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_14_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_15_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_16_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_17_Generics;
import com.mentor.nucleus.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_18_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestArray_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestAssign_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestAttribute_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestBPPrefAllowPromotion_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestBPPrefStrictTyping_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestCRUD_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestConstant_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestControl_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestEvent_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestExpr_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestInvocation_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestLineNumbers_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestSelectWhere_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestSelect_Generics;
import com.mentor.nucleus.bp.als.oal.test.TestStructuredDataType_Generics;
import com.mentor.nucleus.bp.als.oal.test.VisibilityParserTest;

public class OALTestSuite_Generics extends TestSuite {

	public static Test suite() {
		return new OALTestSuite_Generics();
	}
	public OALTestSuite_Generics() {
		addTest(new TestSuite(OalParserTest_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_0_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_1_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_2_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_3_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_4_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_5_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_6_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_7_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_8_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_9_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_10_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_11_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_12_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_13_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_14_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_15_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_16_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_17_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_18_Generics.class));
		addTest(new TestSuite(SingleDimensionFixedArrayAssigmentTest_19_Generics.class));
		addTest(new TestSuite(TestArray_Generics.class));
		addTest(new TestSuite(TestInvocation_Generics.class));
		addTest(new TestSuite(TestBPPrefAllowPromotion_Generics.class));
		addTest(new TestSuite(TestBPPrefStrictTyping_Generics.class));
		addTest(new TestSuite(TestAssign_Generics.class));
		addTest(new TestSuite(TestExpr_Generics.class));
		addTest(new TestSuite(TestAttribute_Generics.class));
		addTest(new TestSuite(TestControl_Generics.class));
		addTest(new TestSuite(TestCRUD_Generics.class));
		addTest(new TestSuite(TestSelect_Generics.class));
		addTest(new TestSuite(TestEvent_Generics.class));
		addTest(new TestSuite(TestLineNumbers_Generics.class));
		addTest(new TestSuite(TestStructuredDataType_Generics.class));
		addTest(new TestSuite(ParseAllInDomain_Generics.class));
		addTest(new TestSuite(VisibilityParserTest.class));
		addTest(new TestSuite(ComponentSyntaxTest_Generics.class));
		addTest(new TestSuite(ImplicitComponentAddressTest_Generics.class));
		addTest(new TestSuite(RelaxedSameDataTest_Generics.class));
		addTest(new TestSuite(ComponentParamTest_Generics.class));
		addTest(new TestSuite(ComponentScopeTest_Generics.class));
		addTest(new TestSuite(TestConstant_Generics.class));
	    addTest(new TestSuite(TestSelectWhere_Generics.class));
	    addTest(new TestSuite(PolyEventTest.class));
	}
}
