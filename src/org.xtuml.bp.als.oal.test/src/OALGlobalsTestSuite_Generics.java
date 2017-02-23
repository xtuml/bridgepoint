//=====================================================================
//
//File:      $RCSfile: OALGlobalsTestSuite_Generics.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 04:53:02 $
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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xtuml.bp.als.oal.test.ComponentParamTest_Generics;
import org.xtuml.bp.als.oal.test.ComponentScopeTest_Generics;
import org.xtuml.bp.als.oal.test.ComponentSyntaxTest_Generics;
import org.xtuml.bp.als.oal.test.ImplicitComponentAddressTest_Generics;
import org.xtuml.bp.als.oal.test.OalParserGlobalsTest_Generics;
import org.xtuml.bp.als.oal.test.OalParserTest_Generics;
import org.xtuml.bp.als.oal.test.ParseAllInDomain_Generics;
import org.xtuml.bp.als.oal.test.ParseErrorForEmptySynchronousMessagesTests;
import org.xtuml.bp.als.oal.test.PolyEventTest;
import org.xtuml.bp.als.oal.test.RelaxedSameDataTest_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_0_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_10_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_11_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_12_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_13_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_14_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_15_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_16_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_17_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_18_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_19_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_1_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_2_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_3_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_4_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_5_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_6_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_7_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_8_Generics;
import org.xtuml.bp.als.oal.test.SingleDimensionFixedArrayAssigmentTest_9_Generics;
import org.xtuml.bp.als.oal.test.SupportConstantsViaIPRTests;
import org.xtuml.bp.als.oal.test.TestAllowInterfaceNameInICMsg_Generics;
import org.xtuml.bp.als.oal.test.TestArray_Generics;
import org.xtuml.bp.als.oal.test.TestAssign_Generics;
import org.xtuml.bp.als.oal.test.TestAttribute_Generics;
import org.xtuml.bp.als.oal.test.TestBPPrefAllowPromotion_Generics;
import org.xtuml.bp.als.oal.test.TestBPPrefStrictTyping_Generics;
import org.xtuml.bp.als.oal.test.TestCRUD_Generics;
import org.xtuml.bp.als.oal.test.TestConstant_Generics;
import org.xtuml.bp.als.oal.test.TestControl_Generics;
import org.xtuml.bp.als.oal.test.TestEvent_Generics;
import org.xtuml.bp.als.oal.test.TestExpr_Generics;
import org.xtuml.bp.als.oal.test.TestInvocation_Generics;
import org.xtuml.bp.als.oal.test.TestLineNumbers_Generics;
import org.xtuml.bp.als.oal.test.TestSelectWhere_Generics;
import org.xtuml.bp.als.oal.test.TestSelectWhere_OpNotAllowed;
import org.xtuml.bp.als.oal.test.TestSelect_Generics;
import org.xtuml.bp.als.oal.test.TestSelect_Generics_Ordered;
import org.xtuml.bp.als.oal.test.TestStructuredDataType_Generics;
import org.xtuml.bp.als.oal.test.VisibilityParserTest;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	
	OalParserGlobalsTest_Generics.class,
	OalParserTest_Generics.class,
	TestArray_Generics.class,
	TestInvocation_Generics.class,
	TestBPPrefAllowPromotion_Generics.class,
	TestBPPrefStrictTyping_Generics.class,
	TestAssign_Generics.class,
	TestExpr_Generics.class,
	TestAttribute_Generics.class,
	TestControl_Generics.class,
	TestCRUD_Generics.class,
	TestEvent_Generics.class,
	TestSelectWhere_Generics.class,
	TestSelectWhere_OpNotAllowed.class,
	TestSelect_Generics.class,
	TestSelect_Generics_Ordered.class,
	TestLineNumbers_Generics.class,
	TestStructuredDataType_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_0_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_1_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_2_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_3_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_4_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_5_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_6_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_7_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_8_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_9_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_10_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_11_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_12_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_13_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_14_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_15_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_16_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_17_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_18_Generics.class,
	SingleDimensionFixedArrayAssigmentTest_19_Generics.class,
	ParseAllInDomain_Generics.class,
	VisibilityParserTest.class,
	ComponentSyntaxTest_Generics.class,
	ImplicitComponentAddressTest_Generics.class,
	RelaxedSameDataTest_Generics.class,
	ComponentParamTest_Generics.class,
	ComponentScopeTest_Generics.class,
	TestConstant_Generics.class,
	TestAllowInterfaceNameInICMsg_Generics.class,
	PolyEventTest.class,
	ParseErrorForEmptySynchronousMessagesTests.class,
	SupportConstantsViaIPRTests.class,
})
public class OALGlobalsTestSuite_Generics extends TestSuite {
	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new OALGlobalsTestSuite_Generics();
	}
}
