//========================================================================
//
//File:      $RCSfile: TestEvent.java,v $
//Version:   $Revision: 1.20 $
//Modified:  $Date: 2013/01/10 23:00:33 $
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

import junit.framework.TestCase;
import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.mentor.nucleus.bp.core.ActualParameter_c;
import com.mentor.nucleus.bp.core.AttributeValueReference_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.CreateEventStatement_c;
import com.mentor.nucleus.bp.core.CreateEventToClass_c;
import com.mentor.nucleus.bp.core.CreateEventToCreator_c;
import com.mentor.nucleus.bp.core.CreateEventToExternalEntity_c;
import com.mentor.nucleus.bp.core.CreateEventToInstance_c;
import com.mentor.nucleus.bp.core.CreateSmEventStatement_c;
import com.mentor.nucleus.bp.core.EventDatumValue_c;
import com.mentor.nucleus.bp.core.EventParameterReference_c;
import com.mentor.nucleus.bp.core.EventSpecificationStatement_c;
import com.mentor.nucleus.bp.core.ExternalEntityEvent_c;
import com.mentor.nucleus.bp.core.GenerateEventStatement_c;
import com.mentor.nucleus.bp.core.GeneratePreexistingEvent_c;
import com.mentor.nucleus.bp.core.GenerateSmEventStatement_c;
import com.mentor.nucleus.bp.core.GenerateToClass_c;
import com.mentor.nucleus.bp.core.GenerateToCreator_c;
import com.mentor.nucleus.bp.core.GenerateToExternalEntity_c;
import com.mentor.nucleus.bp.core.Generate_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.TransientValueReference_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;

public class TestEvent extends TestCase {
	public void validateEvent(String act, int genType, int numStmts,
			int numVal, String label, String[] parmList)
			throws RecognitionException, TokenStreamException {
			String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
			assertEquals("", x); //$NON-NLS-1$
			OalParserTest.validateBlkStmtVal(1, numStmts, numVal);
			Variable_c var[] = Variable_c.VariableInstances(OalParserTest.modelRoot);
			Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
			ActualParameter_c parms[];
			GenerateSmEventStatement_c gsme = null;
			if (genType == 0) {
				Generate_c gen[] = Generate_c.GenerateInstances(OalParserTest.modelRoot);
				assertEquals(1, gen.length);
				assertEquals(1, var.length);
				assertEquals(var[0].getVar_id(), gen[0].getVar_id());
				gsme = GenerateSmEventStatement_c.getOneE_GSMEOnR705(gen[0]);
			} else if (genType == 1) {
				GenerateToClass_c gen_a[] = GenerateToClass_c.GenerateToClassInstances(OalParserTest.modelRoot);
				assertEquals(1, gen_a.length);
				assertEquals(0, var.length);
				gsme = GenerateSmEventStatement_c.getOneE_GSMEOnR705(gen_a[0]);
			} else if (genType == 2) {
				GenerateToCreator_c gen_c[] = GenerateToCreator_c.GenerateToCreatorInstances(OalParserTest.modelRoot);
				assertEquals(1, gen_c.length);
				assertEquals(0, var.length);
				gsme = GenerateSmEventStatement_c.getOneE_GSMEOnR705(gen_c[0]);
			}
			switch (genType) {
				case 0 :
				case 1 :
				case 2 :{
					assertNotNull(gsme);
					StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR707(gsme);
					assertEquals(label, evt.getDrv_lbl());
					GenerateEventStatement_c ges = GenerateEventStatement_c.getOneE_GESOnR703(gsme);
					assertNotNull(ges);
					EventSpecificationStatement_c ess = EventSpecificationStatement_c.getOneE_ESSOnR701(ges);
					assertNotNull(ess);
					parms = ActualParameter_c.getManyV_PARsOnR700(ess);
					validateEventParms(parmList, val, parms, 0);
					break;
				}
				case 3 :{
					GenerateToExternalEntity_c gen_e[] = GenerateToExternalEntity_c.GenerateToExternalEntityInstances(OalParserTest.modelRoot);
					assertEquals(1, gen_e.length);
					assertEquals(0, var.length);
					ExternalEntityEvent_c eeevt = ExternalEntityEvent_c.getOneS_EEEVTOnR709(gen_e[0]);
					assertEquals(label, eeevt.getDrv_lbl());
					GenerateEventStatement_c ges = GenerateEventStatement_c.getOneE_GESOnR703(gen_e[0]);
					assertNotNull(ges);
					EventSpecificationStatement_c ess = EventSpecificationStatement_c.getOneE_ESSOnR701(ges);
					assertNotNull(ess);
					parms = ActualParameter_c.getManyV_PARsOnR700(ess);
					validateEventParms(parmList, val, parms, 0);
					break;
				}
				case 4 : {
					Generate_c gen[] = Generate_c.GenerateInstances(OalParserTest.modelRoot);
					assertEquals(0, gen.length);
					assertEquals(2, var.length);
					CreateEventToInstance_c cei[] = CreateEventToInstance_c.CreateEventToInstanceInstances(OalParserTest.modelRoot);
					assertEquals(numStmts - 1, cei.length);
					for (int i = 0; i < numStmts - 1; ++i) {
						assertEquals(var[0].getVar_id(), cei[i].getVar_id());
						CreateSmEventStatement_c csme = CreateSmEventStatement_c.getOneE_CSMEOnR704(cei[i]);
						assertNotNull(csme);
						StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR706(csme);
						assertEquals(label, evt.getDrv_lbl());
						CreateEventStatement_c ces = CreateEventStatement_c.getOneE_CESOnR702(csme);
						assertNotNull(ces);
						assertEquals(var[1].getVar_id(), ces.getVar_id());
						if (i == 0)
							assertTrue(ces.getIs_implicit());
						if (i == 1)
							assertFalse(ces.getIs_implicit());
						EventSpecificationStatement_c ess = EventSpecificationStatement_c.getOneE_ESSOnR701(ces);
						assertNotNull(ess);
						parms = ActualParameter_c.getManyV_PARsOnR700(ess);
						validateEventParms(parmList, val, parms, i);
					}
				}
					break;
				case 5 :{
					GenerateToClass_c gen_a[] = GenerateToClass_c.GenerateToClassInstances(OalParserTest.modelRoot);
					assertEquals(0, gen_a.length);
					assertEquals(1, var.length);
					CreateEventToClass_c cea[] = CreateEventToClass_c.CreateEventToClassInstances(OalParserTest.modelRoot);
					assertEquals(numStmts, cea.length);
					for (int i = 0; i < numStmts; ++i) {
						CreateSmEventStatement_c csme = CreateSmEventStatement_c.getOneE_CSMEOnR704(cea[i]);
						assertNotNull(csme);
						StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR706(csme);
						assertEquals(label, evt.getDrv_lbl());
						CreateEventStatement_c ces = CreateEventStatement_c.getOneE_CESOnR702(csme);
						assertNotNull(ces);
						assertEquals(var[0].getVar_id(), ces.getVar_id());
						if (i == 0)
							assertTrue(ces.getIs_implicit());
						if (i == 1)
							assertFalse(ces.getIs_implicit());
						EventSpecificationStatement_c ess = EventSpecificationStatement_c.getOneE_ESSOnR701(ces);
						assertNotNull(ess);
						parms = ActualParameter_c.getManyV_PARsOnR700(ess);
						validateEventParms(parmList, val, parms, i);
					}
				}
					break;
				case 6 :{
					GenerateToCreator_c gen_c[] = GenerateToCreator_c.GenerateToCreatorInstances(OalParserTest.modelRoot);
					assertEquals(0, gen_c.length);
					assertEquals(1, var.length);
					CreateEventToCreator_c cec[] = CreateEventToCreator_c.CreateEventToCreatorInstances(OalParserTest.modelRoot);
					assertEquals(numStmts, cec.length);
					for (int i = 0; i < numStmts; ++i) {
						CreateSmEventStatement_c csme = CreateSmEventStatement_c.getOneE_CSMEOnR704(cec[i]);
						assertNotNull(csme);
						StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR706(csme);
						assertEquals(label, evt.getDrv_lbl());
						CreateEventStatement_c ces = CreateEventStatement_c.getOneE_CESOnR702(csme);
						assertNotNull(ces);
						assertEquals(var[0].getVar_id(), ces.getVar_id());
						if (i == 0)
							assertTrue(ces.getIs_implicit());
						if (i == 1)
							assertFalse(ces.getIs_implicit());
						EventSpecificationStatement_c ess = EventSpecificationStatement_c.getOneE_ESSOnR701(ces);
						assertNotNull(ess);
						parms = ActualParameter_c.getManyV_PARsOnR700(ess);
						validateEventParms(parmList, val, parms, i);
					}
				}
					break;
				case 7 :{
					GenerateToExternalEntity_c gen_e[] = GenerateToExternalEntity_c.GenerateToExternalEntityInstances(OalParserTest.modelRoot);
					assertEquals(0, gen_e.length);
					assertEquals(1, var.length);
					CreateEventToExternalEntity_c cee[] =
						CreateEventToExternalEntity_c.CreateEventToExternalEntityInstances(OalParserTest.modelRoot);
					assertEquals(numStmts, cee.length);
					for (int i = 0; i < numStmts; ++i) {
						ExternalEntityEvent_c eeevt = ExternalEntityEvent_c.getOneS_EEEVTOnR708(cee[i]);
						assertEquals(label, eeevt.getDrv_lbl());
						CreateEventStatement_c ces = CreateEventStatement_c.getOneE_CESOnR702(cee[i]);
						assertNotNull(ces);
						assertEquals(var[0].getVar_id(), ces.getVar_id());
						if (i == 0)
							assertTrue(ces.getIs_implicit());
						if (i == 1)
							assertFalse(ces.getIs_implicit());
						EventSpecificationStatement_c ess = EventSpecificationStatement_c.getOneE_ESSOnR701(ces);
						assertNotNull(ess);
						parms = ActualParameter_c.getManyV_PARsOnR700(ess);
						validateEventParms(parmList, val, parms, i);
					}
					break;
				}
			}
		}
		private void validateEventParms(String[] parmList, Value_c[] val, ActualParameter_c[] parms, int stmtNum) {
			assertEquals(parmList.length, parms.length);
			int parmOrder1[] = { 0 };
			int parmOrder2[] = { 1, 0 };
			int parmOrder3[] = { 2, 0, 1 };
			int parmOrders[][] = { null, parmOrder1, parmOrder2, parmOrder3 };

			int parmOrder[] = parmOrders[parmList.length];
			for ( int j = 0; j < parmList.length; ++j)
			{
				if ( j < parmList.length-1 )
				{
					assertEquals( parms[parmOrder[j]].getNext_value_id(), parms[parmOrder[j+1]].getValue_id());
				}
				assertEquals( parmList[j], parms[parmOrder[j]].getName() );
				assertEquals( val[stmtNum*parmList.length+j].getValue_id(), parms[parmOrder[j]].getValue_id());
			}
		}

		public void validateEventGenerateAndCreate(String act, String label, String[] parms,
			String secondCreate, int evtType, int numStmts, int numVal )
			throws RecognitionException, TokenStreamException {
			validateEvent(act, evtType, numStmts, numVal, label, parms);
			String create_act = act.replaceFirst("generate ", "create event instance e1 of ");//$NON-NLS-1$//$NON-NLS-2$
			validateEvent(create_act, evtType+4, numStmts, numVal, label, parms);
			create_act = create_act.replaceFirst("create ", secondCreate);//$NON-NLS-1$
			validateEvent(create_act, evtType+4, numStmts+1, numVal+parms.length, label, parms);
		}

		public void testEventNoParmsToInstance() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST1 to t;"; //$NON-NLS-1$
			String parms[] = {};
			validateEventGenerateAndCreate(act, "D_TST1", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST1 to t; create ", 0, 2, 0);//$NON-NLS-1$
		}
		public void testPolyEventNoParmsToSuper() throws RecognitionException, TokenStreamException {
			String act = "select any tst from instances of D_TST;  generate D_TST7* to tst;"; //$NON-NLS-1$
			String parms[] = {};
			validateEventGenerateAndCreate(act, "D_TST7", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST7* to tst; create ", 0, 2, 0);//$NON-NLS-1$
		}
		public void testPolyEventNoParmsToSub() throws RecognitionException, TokenStreamException {
			String act = "select any tp from instances of D_TP;  generate D_TST7* to tp;"; //$NON-NLS-1$
			String parms[] = {};
			validateEventGenerateAndCreate(act, "D_TST7", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST7* to tp; create ", 0, 2, 0);//$NON-NLS-1$
		}
		public void testEventNoParmsToAssigner() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A1 to D_TST assigner;"; //$NON-NLS-1$
			String parms[] = {};
			validateEventGenerateAndCreate(act, "D_TST_A1", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST_A1 to D_TST class; create ", 1, 1, 0);//$NON-NLS-1$
		}
		public void testEventNoParmsToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST1 to D_TST creator;"; //$NON-NLS-1$
			String parms[] = {};
			validateEventGenerateAndCreate(act, "D_TST1", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST1 to D_TST creator; create ", 2, 1, 0);//$NON-NLS-1$
		}
		public void testInstEventNoParmsToInstanceWithMeaning() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST1:'none' to t;"; //$NON-NLS-1$
			String parms[] = {};
			validateEventGenerateAndCreate(act, "D_TST1", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST1:'none' to t; create ", 0, 2, 0);//$NON-NLS-1$
		}
		public void testEventNoParmsToAssignerWithMeaning() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A1:'none' to D_TST assigner;"; //$NON-NLS-1$
			String parms[] = {};
			validateEventGenerateAndCreate(act, "D_TST_A1", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST_A1:'none' to D_TST class; create ", 1, 1, 0);//$NON-NLS-1$
		}
		public void testEventNoParmsToCreatorWithMeaning() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST1:'none' to D_TST creator;"; //$NON-NLS-1$
			String parms[] = {};
			validateEventGenerateAndCreate(act, "D_TST1", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST1:'none' to D_TST creator; create ", 2, 1, 0);//$NON-NLS-1$
		}
		public void testEventOneParmToInstance() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST2(i: 7) to t;"; //$NON-NLS-1$
			String parms[] = {"i"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "D_TST2", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST2(i:7) to t; create ", 0, 2, 1);//$NON-NLS-1$
		}
		public void testEventOneParmToAssigner() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A2(i:8) to D_TST assigner;"; //$NON-NLS-1$
			String parms[] = {"i"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "D_TST_A2", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST_A2(i:8) to D_TST class; create ", 1, 1, 1);//$NON-NLS-1$
		}
		public void testEventOneParmToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST2(i:8) to D_TST creator;"; //$NON-NLS-1$
			String parms[] = {"i"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "D_TST2", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST2(i:8) to D_TST creator; create ", 2, 1, 1);//$NON-NLS-1$
		}
		public void testEventOneParmToInstanceWithUnderscore() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of _T;  generate _T1(_edi: 7) to t;"; //$NON-NLS-1$
			String parms[] = {"_edi"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "_T1", parms, //$NON-NLS-1$
				"create event instance e1 of _T1(_edi:7) to t; create ", 0, 2, 1);//$NON-NLS-1$
		}
		public void testEventOneParmToAssignerWithUnderscore() throws RecognitionException, TokenStreamException {
			String act = "generate _T_A1(_edi:8) to _T assigner;"; //$NON-NLS-1$
			String parms[] = {"_edi"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "_T_A1", parms, //$NON-NLS-1$
				"create event instance e1 of _T_A1(_edi:8) to _T class; create ", 1, 1, 1);//$NON-NLS-1$
		}
		public void testEventOneParmToCreatorWithUnderscore() throws RecognitionException, TokenStreamException {
			String act = "generate _T1(_edi:8) to _T creator;"; //$NON-NLS-1$
			String parms[] = {"_edi"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "_T1", parms, //$NON-NLS-1$
				"create event instance e1 of _T1(_edi:8) to _T creator; create ", 2, 1, 1);//$NON-NLS-1$
		}
		public void testInstEventOneParmToInstanceWithMeaning() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST2:'one'(i:8) to t;"; //$NON-NLS-1$
			String parms[] = {"i"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "D_TST2", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST2:'one'(i:8) to t; create ", 0, 2, 1);//$NON-NLS-1$
		}
		public void testEventOneParmToAssignerWithMeaning() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A2:'one'(i:8) to D_TST assigner;"; //$NON-NLS-1$
			String parms[] = {"i"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "D_TST_A2", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST_A2:'one'(i:8) to D_TST class; create ", 1, 1, 1);//$NON-NLS-1$
		}
		public void testEventOneParmToCreatorWithMeaning() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST2:'one'(i:8) to D_TST creator;"; //$NON-NLS-1$
			String parms[] = {"i"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "D_TST2", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST2:'one'(i:8) to D_TST creator; create ", 2, 1, 1);//$NON-NLS-1$
		}
		public void testEventOneParmToInstanceWithMeaningWithUnderscore() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of _T;  generate _T1:'_test'(_edi: 7) to t;"; //$NON-NLS-1$
			String parms[] = {"_edi"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "_T1", parms, //$NON-NLS-1$
				"create event instance e1 of _T1:'_test'(_edi:7) to t; create ", 0, 2, 1);//$NON-NLS-1$
		}
		public void testEventOneParmToAssignerWithMeaningWithUnderscore() throws RecognitionException, TokenStreamException {
			String act = "generate _T_A1:'_test'(_edi:8) to _T assigner;"; //$NON-NLS-1$
			String parms[] = {"_edi"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "_T_A1", parms, //$NON-NLS-1$
				"create event instance e1 of _T_A1:'_test'(_edi:8) to _T class; create ", 1, 1, 1);//$NON-NLS-1$
		}
		public void testEventOneParmToCreatorWithMeaningWithUnderscore() throws RecognitionException, TokenStreamException {
			String act = "generate _T1:'_test'(_edi:8) to _T creator;"; //$NON-NLS-1$
			String parms[] = {"_edi"};//$NON-NLS-1$
			validateEventGenerateAndCreate(act, "_T1", parms, //$NON-NLS-1$
				"create event instance e1 of _T1:'_test'(_edi:8) to _T creator; create ", 2, 1, 1);//$NON-NLS-1$
		}
		public void testEventTwoParmsToInstance() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST3(i: 7, s:\"ok\") to t;"; //$NON-NLS-1$
			String parms[] = {"i", "s"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST3(i:7, s:\"ok\") to t; create ", 0, 2, 2);//$NON-NLS-1$
		}
		public void testEventTwoParmsToInstanceWithBridgeParms() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST3(i: T::testIntNoParm(), s:T::testStringNoParm()) to t;"; //$NON-NLS-1$
			String parms[] = {"i", "s"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST3(i:T::testIntNoParm(), s:T::testStringNoParm()) to t; create ", 0, 2, 2);//$NON-NLS-1$
		}
		public void testEventTwoParmsToAssigner() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A3(i:8, s:\"ok\") to D_TST assigner;"; //$NON-NLS-1$
			String parms[] = {"i", "s"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST_A3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST_A3(i:8, s:\"ok\") to D_TST class; create ", 1, 1, 2);//$NON-NLS-1$
		}
		public void testEventTwoParmsToAssignerWithBridgeParms() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A3(i:T::testIntNoParm(), s:T::testStringNoParm()) to D_TST assigner;"; //$NON-NLS-1$
			String parms[] = {"i", "s"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST_A3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST_A3(i:T::testIntNoParm(), s:T::testStringNoParm()) to D_TST class; create ", 1, 1, 2);//$NON-NLS-1$
		}
		public void testEventTwoParmsToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST3(i:8, s:\"ok\") to D_TST creator;"; //$NON-NLS-1$
			String parms[] = {"i", "s"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST3(i:8, s:\"ok\") to D_TST creator; create ", 2, 1, 2);//$NON-NLS-1$
		}
		public void testEventTwoParmsToCreatorWithBridgeParms() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST3(i:T::testIntNoParm(), s:T::testStringNoParm()) to D_TST creator;"; //$NON-NLS-1$
			String parms[] = {"i", "s"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST3(i:T::testIntNoParm(), s:T::testStringNoParm()) to D_TST creator; create ", 2, 1, 2);//$NON-NLS-1$
		}
		public void testInstEventTwoParmsToInstanceWithMeaning() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST3:'two'(i:8, s:\"ok\") to t;"; //$NON-NLS-1$
			String parms[] = {"i", "s"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST3:'two'(i:8, s:\"ok\") to t; create ", 0, 2, 2);//$NON-NLS-1$
		}
		public void testEventTwoParmsToAssignerWithMeaning() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A3:'two'(i:8, s:\"ok\") to D_TST assigner;"; //$NON-NLS-1$
			String parms[] = {"i", "s"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST_A3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST_A3:'two'(i:8, s:\"ok\") to D_TST class; create ", 1, 1, 2);//$NON-NLS-1$
		}
		public void testEventTwoParmsToCreatorWithMeaning() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST3:'two'(i:8, s:\"ok\") to D_TST creator;"; //$NON-NLS-1$
			String parms[] = {"i", "s"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST3:'two'(i:8, s:\"ok\") to D_TST creator; create ", 2, 1, 2);//$NON-NLS-1$
		}
		public void testEventTwoParmsToInstanceOtherOrder() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST3(s:\"ok\", i:6) to t;"; //$NON-NLS-1$
			String parms[] = {"s", "i"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST3(s:\"ok2\", i:6) to t; create ", 0, 2, 2);//$NON-NLS-1$
		}
		public void testEventTwoParmsToAssignerOtherOrder() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A3(s:\"ok2\", i:6) to D_TST assigner;"; //$NON-NLS-1$
			String parms[] = {"s", "i"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST_A3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST_A3(s:\"ok2\", i:6) to D_TST class; create ", 1, 1, 2);//$NON-NLS-1$
		}
		public void testEventTwoParmsToCreatorOtherOrder() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST3(s:\"ok2\", i:6) to D_TST creator;"; //$NON-NLS-1$
			String parms[] = {"s", "i"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST3", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST3(s:\"ok2\", i:6) to D_TST creator; create ", 2, 1, 2);//$NON-NLS-1$
		}
		public void testEventThreeParmsToInstance() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST4(i: 7, s:\"ok\", r:2.1) to t;"; //$NON-NLS-1$
			String parms[] = {"i", "s", "r"};//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			validateEventGenerateAndCreate(act, "D_TST4", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST4(i:7, s:\"ok\", r:2.1) to t; create ", 0, 2, 3);//$NON-NLS-1$
		}
		public void testEventThreeParmsToAssigner() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A4(i:8, s:\"ok\", r:2.1) to D_TST assigner;"; //$NON-NLS-1$
			String parms[] = {"i", "s", "r"};//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			validateEventGenerateAndCreate(act, "D_TST_A4", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST_A4(i:8, s:\"ok\", r:2.1) to D_TST class; create ", 1, 1, 3);//$NON-NLS-1$
		}
		public void testEventThreeParmsToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST4(i:8, s:\"ok\", r:2.1) to D_TST creator;"; //$NON-NLS-1$
			String parms[] = {"i", "s", "r"};//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			validateEventGenerateAndCreate(act, "D_TST4", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST4(i:8, s:\"ok\", r:2.1) to D_TST creator; create ", 2, 1, 3);//$NON-NLS-1$
		}
		public void testEventThreeParmsToInstanceOtherOrder() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST4(s:\"ok\", r:2.1, i:8) to t;"; //$NON-NLS-1$
			String parms[] = {"s", "r", "i"};//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			validateEventGenerateAndCreate(act, "D_TST4", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST4(s:\"ok\", r:2.1, i:8) to t; create ", 0, 2, 3);//$NON-NLS-1$
		}
		public void testEventThreeParmsToAssignerOtherOrder() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A4(s:\"ok\", r:2.1, i:8) to D_TST assigner;"; //$NON-NLS-1$
			String parms[] = {"s", "r", "i"};//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			validateEventGenerateAndCreate(act, "D_TST_A4", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST_A4(s:\"ok\", r:2.1, i:8) to D_TST class; create ", 1, 1, 3);//$NON-NLS-1$
		}
		public void testEventThreeParmsToCreatorOtherOrder() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST4(s:\"ok\", r:2.1, i:8) to D_TST creator;"; //$NON-NLS-1$
			String parms[] = {"s", "r", "i"};//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			validateEventGenerateAndCreate(act, "D_TST4", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST4(s:\"ok\", r:2.1, i:8) to D_TST creator; create ", 2, 1, 3);//$NON-NLS-1$
		}
		public void testPolyEventTwoParmsToSuper() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST8*(i:2, r: 1.1) to t;"; //$NON-NLS-1$
			String parms[] = {"i", "r"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST8", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST8*(i:2, r: 1.1) to t; create ", 0, 2, 2);//$NON-NLS-1$
		}
		public void testPolyEventTwoParmsToSub() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TP;  generate D_TST8*(i:2, r: 1.1) to t;"; //$NON-NLS-1$
			String parms[] = {"i", "r"};//$NON-NLS-1$//$NON-NLS-2$
			validateEventGenerateAndCreate(act, "D_TST8", parms, //$NON-NLS-1$
				"create event instance e1 of D_TST8*(i:2, r: 1.1) to t; create ", 0, 2, 2);//$NON-NLS-1$
		}

		public void testGeneratePreExistingFromVar() throws RecognitionException, TokenStreamException {
			String act = "create event instance x of D_TST1 to D_TST creator; generate x;"; //$NON-NLS-1$
			String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
			assertEquals("", x); //$NON-NLS-1$
			OalParserTest.validateBlkStmtVal(1, 2, 1);
			Variable_c var[] = Variable_c.VariableInstances(OalParserTest.modelRoot);
			assertEquals(1, var.length);
			Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
			ActualParameter_c parms[];
			GeneratePreexistingEvent_c gpe[] = GeneratePreexistingEvent_c.GeneratePreexistingEventInstances(OalParserTest.modelRoot);
			assertEquals(1, gpe.length);
			TransientValueReference_c tvl = TransientValueReference_c.getOneV_TVLOnR801(Value_c.getOneV_VALOnR714(gpe[0]));
			assertEquals(var[0].getVar_id(), tvl.getVar_id());
			AttributeValueReference_c attrVal = AttributeValueReference_c.getOneV_AVLOnR801(Value_c.getOneV_VALOnR714(gpe[0]));
			assertNull(attrVal);
		}

		public void testGeneratePreExistingFromAttr() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate t.evt_inst;"; //$NON-NLS-1$
			String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
			assertEquals("", x); //$NON-NLS-1$
			OalParserTest.validateBlkStmtVal(1, 2, 2);
			Variable_c var[] = Variable_c.VariableInstances(OalParserTest.modelRoot);
			assertEquals(1, var.length);
			GeneratePreexistingEvent_c gpe[] = GeneratePreexistingEvent_c.GeneratePreexistingEventInstances(OalParserTest.modelRoot);
			assertEquals(1, gpe.length);
			TransientValueReference_c tvl = TransientValueReference_c.getOneV_TVLOnR801(Value_c.getOneV_VALOnR714(gpe[0]));
			assertNull(tvl);
			AttributeValueReference_c attrVal = AttributeValueReference_c.getOneV_AVLOnR801(Value_c.getOneV_VALOnR714(gpe[0]));
			assertNotNull(attrVal);
			Attribute_c attr = Attribute_c.getOneO_ATTROnR806(attrVal);
			assertEquals("evt_inst", attr.getName());//$NON-NLS-1$
			ModelClass_c obj = ModelClass_c.getOneO_OBJOnR102(attr);
			assertEquals("D_TST", obj.getKey_lett());//$NON-NLS-1$
			OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		}

		public void testGenerateEventToSelf() throws RecognitionException, TokenStreamException {
			generateEventToSelf(OalParserTest.ACTIVITY_TYPE_STATE);
		}

		public void testGenerateEventToSelfFromTransition() throws RecognitionException, TokenStreamException {
			generateEventToSelf(OalParserTest.ACTIVITY_TYPE_TRANSITION);
		}

		private void generateEventToSelf(int activityType) throws RecognitionException, TokenStreamException {
			String act = "generate D_TST1 to self;"; //$NON-NLS-1$
			String x = OalParserTest.parseAction(act, activityType, OalParserTest.STATE_ISM_NONE); //$NON-NLS-1$
			assertEquals("", x); //$NON-NLS-1$
			OalParserTest.validateBlkStmtVal(1, 1, 0);
			Variable_c var[] = Variable_c.VariableInstances(OalParserTest.modelRoot);
			assertEquals(1, var.length);
			Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
			ActualParameter_c parms[];
			Generate_c gen[] = Generate_c.GenerateInstances(OalParserTest.modelRoot);
			assertEquals(1, gen.length);
			assertEquals(1, var.length);
			assertEquals(var[0].getVar_id(), gen[0].getVar_id());
			GenerateSmEventStatement_c gsme = GenerateSmEventStatement_c.getOneE_GSMEOnR705(gen[0]);
		}

		public void testCreateEventToSelf() throws RecognitionException, TokenStreamException {
			createEventToSelf(OalParserTest.ACTIVITY_TYPE_STATE);
		}

		public void testCreateEventToSelfInTransition() throws RecognitionException, TokenStreamException {
			createEventToSelf(OalParserTest.ACTIVITY_TYPE_TRANSITION);
		}

		private void createEventToSelf(int activityType) throws RecognitionException, TokenStreamException {
			String act = "create event instance e1 of D_TST1 to self;"; //$NON-NLS-1$
			String x = OalParserTest.parseAction(act, activityType, OalParserTest.STATE_ISM_NONE); //$NON-NLS-1$
			assertEquals("", x); //$NON-NLS-1$
			OalParserTest.validateBlkStmtVal(1, 1, 0);
			Variable_c var[] = Variable_c.VariableInstances(OalParserTest.modelRoot);
			assertEquals(2, var.length);
			Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
			ActualParameter_c parms[];
			Generate_c gen[] = Generate_c.GenerateInstances(OalParserTest.modelRoot);
			assertEquals(0, gen.length);
			CreateEventToInstance_c cei[] = CreateEventToInstance_c.CreateEventToInstanceInstances(OalParserTest.modelRoot);
			assertEquals(1, cei.length);
			assertEquals(var[1].getVar_id(), cei[0].getVar_id());
			CreateSmEventStatement_c csme = CreateSmEventStatement_c.getOneE_CSMEOnR704(cei[0]);
			assertNotNull(csme);
			StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR706(csme);
			assertEquals("D_TST1", evt.getDrv_lbl());
			CreateEventStatement_c ces = CreateEventStatement_c.getOneE_CESOnR702(csme);
			assertNotNull(ces);
			assertEquals(var[0].getVar_id(), ces.getVar_id());
			assertTrue(ces.getIs_implicit());
			EventSpecificationStatement_c ess = EventSpecificationStatement_c.getOneE_ESSOnR701(ces);
			assertNotNull(ess);
			parms = ActualParameter_c.getManyV_PARsOnR700(ess);
			assertEquals( 0, parms.length );
			OalParserTest.clearActionData(activityType, OalParserTest.STATE_ISM_NONE);
		}

		private void validateBadGenEvent(String act, String[] err_msg, int numStmts, int numVal)
			throws RecognitionException, TokenStreamException {
			String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
			String lines[] = x.split("\n");//$NON-NLS-1$
			assertEquals(err_msg.length, lines.length);
			for (int i = 0; i < err_msg.length; ++i) {
				assertEquals(err_msg[i], lines[i]); //$NON-NLS-1$
			}
			OalParserTest.validateBlkStmtVal(1, numStmts, numVal);
			EventSpecificationStatement_c ess[] = EventSpecificationStatement_c.EventSpecificationStatementInstances(OalParserTest.modelRoot);
			assertEquals(0, ess.length);
			GenerateEventStatement_c ges[] = GenerateEventStatement_c.GenerateEventStatementInstances(OalParserTest.modelRoot);
			assertEquals(0, ges.length);
			GenerateSmEventStatement_c gsme[] = GenerateSmEventStatement_c.GenerateSmEventStatementInstances(OalParserTest.modelRoot);
			assertEquals(0, gsme.length);
			Generate_c gen[] = Generate_c.GenerateInstances(OalParserTest.modelRoot);
			assertEquals(0, gen.length);
			GenerateToClass_c gen_a[] = GenerateToClass_c.GenerateToClassInstances(OalParserTest.modelRoot);
			assertEquals(0, gen_a.length);
			GenerateToCreator_c gen_c[] = GenerateToCreator_c.GenerateToCreatorInstances(OalParserTest.modelRoot);
			assertEquals(0, gen_c.length);
			GenerateToExternalEntity_c gen_e[] = GenerateToExternalEntity_c.GenerateToExternalEntityInstances(OalParserTest.modelRoot);
			assertEquals(0, gen_e.length);
			Variable_c var[] = Variable_c.VariableInstances(OalParserTest.modelRoot);
			assertEquals(numStmts, var.length);
			ActualParameter_c parms[] = ActualParameter_c.ActualParameterInstances(OalParserTest.modelRoot);
			assertEquals(0, parms.length);
		}
		public void testGenerateEventNoParmsToSelfFromFunction() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST1 to self;"; //$NON-NLS-1$
			String[] err = { ":1:20-23: Keyword ->self<- cannot be used in function AL specifications.",//$NON-NLS-1$
			   "line 1:25: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testCreateEventNoParmsToSelfFromFunction() throws RecognitionException, TokenStreamException {
			String act = "x=1; create event instance e1 of D_TST1 to self;"; //$NON-NLS-1$
			String[] err = { ":1:44-47: Keyword ->self<- cannot be used in function AL specifications.",//$NON-NLS-1$
			   "line 1:49: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 2);
		}

		public void testGenerateNoSuchEvent() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST12 to t;"; //$NON-NLS-1$
			String[] err = { ":1:49-55: Cannot find event for event label ->D_TST12<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testGenerateNoSuchEventToAssigner() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A10 to D_TST assigner;"; //$NON-NLS-1$
			String[] err = { ":1:10-18: Cannot find event for event label ->D_TST_A10<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateNoSuchEventToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST12 to D_TST creator;"; //$NON-NLS-1$
			String[] err = { ":1:10-16: Cannot find event for event label ->D_TST12<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateNoSuchEventToEE() throws RecognitionException, TokenStreamException {
			String act = "generate T9 to T;"; //$NON-NLS-1$
			String[] err = { ":1:10-11: Cannot find event for event label ->T9<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateNoSuchEventAndMeaning() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST12:'none' to t;"; //$NON-NLS-1$
			String[] err = { ":1:49-55: Cannot find event for event label ->D_TST12<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testGenerateNoSuchEventAndMeaningToAssigner() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A10:'none' to D_TST assigner;"; //$NON-NLS-1$
			String[] err = { ":1:10-18: Cannot find event for event label ->D_TST_A10<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateNoSuchEventAndMeaningToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST12:'none' to D_TST creator;"; //$NON-NLS-1$
			String[] err = { ":1:10-16: Cannot find event for event label ->D_TST12<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateNoSuchEventAndMeaningToEE() throws RecognitionException, TokenStreamException {
			String act = "generate T9:'none' to T;"; //$NON-NLS-1$
			String[] err = { ":1:10-11: Cannot find event for event label ->T9<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateWrongMeaning() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST1:'one' to t;"; //$NON-NLS-1$
			String[] err = { ":1:56-60: Event meaning ->'one'<- is not associated with event label ->D_TST1<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testGenerateWrongMeaningToAssigner() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A1:'one' to D_TST class;"; //$NON-NLS-1$
			String[] err = { ":1:19-23: Event meaning ->'one'<- is not associated with event label ->D_TST_A1<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateWrongMeaningToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST1:'one' to D_TST creator;"; //$NON-NLS-1$
			String[] err = { ":1:17-21: Event meaning ->'one'<- is not associated with event label ->D_TST1<-" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateWrongSMEventToInstance() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TP1 to t;"; //$NON-NLS-1$
			String[] err =
				{
					":1:58-58: Cannot find event ->D_TP1<- for class ->Test<-",//$NON-NLS-1$
					"line 1:60: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testGenerateWrongSMEventToAssigner() throws RecognitionException, TokenStreamException {
			String act = "generate D_TP1 to D_TST class;"; //$NON-NLS-1$
			String[] err =
				{
					":1:25-29: Event ->D_TP1<- does not exist for class state machine of class ->Test<-",//$NON-NLS-1$
					"line 1:31: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateWrongSMEventToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TP1 to D_TST creator;"; //$NON-NLS-1$
			String[] err =
				{
					":1:25-31: Event ->D_TP1<- is not a creation transition for class ->Test<-",//$NON-NLS-1$
					"line 1:33: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateWrongSMEventToEE() throws RecognitionException, TokenStreamException {
			String act = "generate D_TP1 to T;"; //$NON-NLS-1$
			String[] err =
				{
					":1:19-19: Cannot find event for event label ->D_TP1<- for external entity ->Test<-",//$NON-NLS-1$
					"line 1:21: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateMultDefinedEvent() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST5:'mult' to t;"; //$NON-NLS-1$
			String[] err =
				{ ":1:49-54: More than one event with label ->D_TST5<- You will need to eventually run the audit and/or clear this up" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testGenerateMultDefinedEventToAssigner() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A5:'mult' to D_TST class;"; //$NON-NLS-1$
			String[] err =
				{ ":1:10-17: More than one event with label ->D_TST_A5<- You will need to eventually run the audit and/or clear this up" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateMultDefinedEventToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST5:'mult' to D_TST creator;"; //$NON-NLS-1$
			String[] err =
				{ ":1:10-15: More than one event with label ->D_TST5<- You will need to eventually run the audit and/or clear this up" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testCreateEventToInstanceWithBadType() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; x = 1; create event instance x of D_TST1 to t;"; //$NON-NLS-1$
			String[] err =
				{
					":1:83-83: Variable ->x<- already exists as a different type",//$NON-NLS-1$
					"line 1:85: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 2, 2);
		}
		public void testCreateEventToAssignerWithBadType() throws RecognitionException, TokenStreamException {
			String act = "x = 1; create event instance x of D_TST_A1 to D_TST class;"; //$NON-NLS-1$
			String[] err =
				{
					":1:53-57: Variable ->x<- already exists as a different type",//$NON-NLS-1$
					"line 1:59: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 2);
		}
		public void testCreateEventToCreatorWithBadType() throws RecognitionException, TokenStreamException {
			String act = "x = 1; create event instance x of D_TST1 to D_TST creator;"; //$NON-NLS-1$
			String[] err =
				{
					":1:51-57: Variable ->x<- already exists as a different type",//$NON-NLS-1$
					"line 1:59: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 2);
		}
		public void testGenerateToInstanceWithBadVar() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST1 to x;"; //$NON-NLS-1$
			String[] err =
				{
					":1:20-20: Token ->x<- must be a variable in current scope and per instance, or be external entity keyletters",//$NON-NLS-1$
					"line 1:22: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}

	    public void testGenerateToInstanceWithWrongVar() throws RecognitionException, TokenStreamException {
	        String act = "zz = 1; generate D_TST1 to zz;"; //$NON-NLS-1$
	        String[] err =
	            {
	                ":1:28-29: Token ->zz<- must be a variable in current scope and per instance, or be external entity keyletters",//$NON-NLS-1$
	                "line 1:31: expecting Semicolon, found 'null'" };//$NON-NLS-1$
	        validateBadGenEvent(act, err, 1, 2);
	    }
		public void testGeneratePolyEventToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST7 to D_TST creator;"; //$NON-NLS-1$
			String[] err =
				{
					":1:26-32: Event ->D_TST7<- is not a creation transition for class ->Test<-",//$NON-NLS-1$
					"line 1:34: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGenerateNLEventToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST7 to D_TP creator;"; //$NON-NLS-1$
			String[] err =
				{
					":1:25-31: Event ->D_TST7<- is not a creation transition for class ->Test Poly<-",//$NON-NLS-1$
					"line 1:33: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}

		public void testGenerateNonCreationEventToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_D1 to D_D creator;"; //$NON-NLS-1$
			String[] err =
				{
					":1:22-28: Event ->D_D1<- is not a creation transition for class ->Disk<-",//$NON-NLS-1$
					"line 1:30: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}

		public void testGeneratePolyMarkedNonPolyEventToInstance() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST1* to t;"; //$NON-NLS-1$
			String[] err =
				{
					":1:59-59: Event is not a polymorphic event. Event label must not contain '*'.",//$NON-NLS-1$
					"line 1:61: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}

		public void testGeneratePolyMarkedNonPolyEventToCreator() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST1* to D_TST creator;"; //$NON-NLS-1$
			String[] err =
				{
					":1:27-33: Event is not a polymorphic event. Event label must not contain '*'.",//$NON-NLS-1$
					"line 1:35: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}

		public void testGeneratePolyMarkedNonPolyEventToClass() throws RecognitionException, TokenStreamException {
			String act = "generate D_TST_A1* to D_TST class;"; //$NON-NLS-1$
			String[] err =
				{
					":1:29-33: Event is not a polymorphic event. Event label must not contain '*'.",//$NON-NLS-1$
					"line 1:35: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testGeneratePreExistingFromUnknownVar() throws RecognitionException, TokenStreamException {
			String act = "generate x;"; //$NON-NLS-1$
			String[] err =
				{
					":1:10-10: Variable ->x<- used in context where it must already exist.",//$NON-NLS-1$
					"line 1:12: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}

		public void testGeneratePreExistingFromWrongVar() throws RecognitionException, TokenStreamException {
			String act = "x = 1; generate x;"; //$NON-NLS-1$
			String[] err = { ":1:17-17: Variable ->x<- must be of type inst<Event>",//$NON-NLS-1$
				"line 1:19: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 2);
		}

		public void testGeneratePreExistingFromUnknownAttr() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate t.bad;"; //$NON-NLS-1$
			String[] err =
				{
					":1:50-52: ->bad<- is not an attribute of class ->Test<-.",//$NON-NLS-1$
					"line 1:54: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testGeneratePreExistingFromWrongAttr() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate t.id;"; //$NON-NLS-1$
			String[] err = { ":1:50-51: Attribute ->id<- must be of type inst<Event>",//$NON-NLS-1$
				"line 1:53: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 1);
		}

		public void testSMEventNoParmsWithParm() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST1( x: true ) to t;"; //$NON-NLS-1$
			String[] err = { ":1:69-69: Parameter ->x<- is not associated with state machine event with label ->D_TST1<-",//$NON-NLS-1$
				"line 1:71: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventOneParmWithoutParm() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST2() to t;"; //$NON-NLS-1$
			String[] err = { ":1:60-60: State machine event with label ->D_TST2<- is missing corresponding parameter ->i<-",//$NON-NLS-1$
				"line 1:62: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventOneParmUnknownParm() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST2(i: x) to t;"; //$NON-NLS-1$
			String[] err = { ":1:58-58: Variable ->x<- used in context where it must already exist.",//$NON-NLS-1$
				"line 1:61: expecting TOK_RPAREN, found 'to'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventTwoParmsSecondUnknownParm() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST3( i: 7, s: z ) to t;"; //$NON-NLS-1$
			String[] err = { ":1:65-65: Variable ->z<- used in context where it must already exist.",//$NON-NLS-1$
				"line 1:69: expecting TOK_RPAREN, found 'to'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventOneParmExtraParm() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST2( i: 2, r: 3.14 ) to t;"; //$NON-NLS-1$
			String[] err = { ":1:75-75: Parameter ->r<- is not associated with state machine event with label ->D_TST2<-",//$NON-NLS-1$
				"line 1:77: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventOneParmDupParm() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST2( i: 2, i: 1 ) to t;"; //$NON-NLS-1$
			String[] err = { ":1:72-72: State machine event with label ->D_TST2<- has redundant parameters",//$NON-NLS-1$
				"line 1:74: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventOneParmExtraParmDiffOrder() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST2( r: 3.14, i:2) to t;"; //$NON-NLS-1$
			String[] err = { ":1:73-73: Parameter ->r<- is not associated with state machine event with label ->D_TST2<-",//$NON-NLS-1$
				"line 1:75: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventOneParmWrongType() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST2( i:true ) to t;"; //$NON-NLS-1$
			String[] err = { ":1:68-68: Parameter ->i<- has been assigned value of different type",//$NON-NLS-1$
				"line 1:70: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventTwoParmsWrongTypeFirst() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST3( i: false, s:\"good\" ) to t;"; //$NON-NLS-1$
			String[] err = { ":1:80-80: Parameter ->i<- has been assigned value of different type",//$NON-NLS-1$
				"line 1:82: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventTwoParmsWrongTypeSecond() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST3( i: 7, s:true ) to t;"; //$NON-NLS-1$
			String[] err = { ":1:74-74: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
				"line 1:76: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventTwoParmsWrongTypeFirstDiffOrder() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST3( s:true, i:7 ) to t;"; //$NON-NLS-1$
			String[] err = { ":1:73-73: Parameter ->s<- has been assigned value of different type",//$NON-NLS-1$
				"line 1:75: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventTwoParmsWrongTypeSecondDiffOrder() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST3( s:\"good\", i:false ) to t;"; //$NON-NLS-1$
			String[] err = { ":1:79-79: Parameter ->i<- has been assigned value of different type",//$NON-NLS-1$
				"line 1:81: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventDupParms() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST; generate D_TST6( a: 7 ) to t;"; //$NON-NLS-1$
			String[] err = { ":1:66-66: More than one parameter with name ->a<-",//$NON-NLS-1$
				"line 1:68: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}
		public void testSMEventThreeParmsToInstanceBadSecond() throws RecognitionException, TokenStreamException {
			String act = "select any t from instances of D_TST;  generate D_TST4(i: 7, s:t.s, r:2.1) to t;"; //$NON-NLS-1$
			String[] err = { ":1:66-66: ->s<- is not an attribute of class ->Test<-.",//$NON-NLS-1$
			"line 1:69: expecting TOK_RPAREN, found 'r'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}

		public void testISMEventToClassWithoutCSM() throws RecognitionException, TokenStreamException {
			String act = "generate D_S1 to D_S class;"; //$NON-NLS-1$
			String[] err = { ":1:22-26: Cannot find Class State Machine for class ->Slot<-",//$NON-NLS-1$
				"line 1:28: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 0, 0);
		}
		public void testCSMEventToInstanceWithoutISM() throws RecognitionException, TokenStreamException {
			String act = "select any sda from instances of D_SDA; generate D_SDA_A1 to sda;"; //$NON-NLS-1$
			String[] err = { ":1:62-64: Cannot find Instance State Machine for class ->Slot_Disk Assignment<-", //$NON-NLS-1$
				"line 1:66: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateBadGenEvent(act, err, 1, 0);
		}

		public void validateEvtParam(int activityType, String act, int actNum, int numStmts, int numVal, int numVar, int numEDV, String[] names)
			throws RecognitionException, TokenStreamException {
			String x = OalParserTest.parseAction(act, activityType, actNum); //$NON-NLS-1$
			assertEquals("", x); //$NON-NLS-1$
			OalParserTest.validateBlkStmtVal(1, numStmts, numVal);
			Variable_c var[] = Variable_c.VariableInstances(OalParserTest.modelRoot);
			assertEquals(numVar, var.length);
			Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
            assertEquals(numVal, val.length);
			EventDatumValue_c[] edv = EventDatumValue_c.EventDatumValueInstances(OalParserTest.modelRoot);
			assertEquals(numEDV, edv.length);
			for ( int i = 0; i < numEDV; ++i )
			{
				StateMachineEventDataItem_c edi = StateMachineEventDataItem_c.getOneSM_EVTDIOnR846(EventParameterReference_c.getOneV_EPROnR834(edv[i]));
				assertNotNull(edi);
				assertEquals(edi.getName(), names[i]);
			}
			OalParserTest.clearActionData(activityType, actNum);
		}

		public void testRead1EvtData() throws RecognitionException, TokenStreamException {
			OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
			String act = "x = rcvd_evt.i;"; //$NON-NLS-1$
			String names[] = { "i" };//$NON-NLS-1$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_ONE, 1, 2, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_ONE, 1, 2, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_ONE, 1, 2, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_ONE, 1, 2, 1, 1, names);
		}
		public void testRead1EvtParms() throws RecognitionException, TokenStreamException { 
			OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
			String act = "x = param.i;"; //$NON-NLS-1$
			String names[] = { "i" };//$NON-NLS-1$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_ONE, 1, 2, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_ONE, 1, 2, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_ONE, 1, 2, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_ONE, 1, 2, 1, 1, names);
		}
		
		public void testRead1SDTEvtData() throws RecognitionException, TokenStreamException {
			OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
			String act = "x = rcvd_evt.SDT_e.age;"; //$NON-NLS-1$
			String names[] = { "SDT_e" };//$NON-NLS-1$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_SDTTest, 1, 3, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_SDTTest, 1, 3, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_SDTTest, 1, 3, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_SDTTest, 1, 3, 1, 1, names);
		}
		public void testRead1SDTEvtParms() throws RecognitionException, TokenStreamException {
			OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
			String act = "x = param.SDT_e.age;"; //$NON-NLS-1$
			String names[] = { "SDT_e" };//$NON-NLS-1$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_SDTTest, 1, 3, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_SDTTest, 1, 3, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_SDTTest, 1, 3, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_SDTTest, 1, 3, 1, 1, names);
		}
		public void testRead1SDTMultLvlEvtData() throws RecognitionException, TokenStreamException {
			OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
			String act = "x = rcvd_evt.SDT_e.person.age;"; //$NON-NLS-1$
			String names[] = { "SDT_e" };//$NON-NLS-1$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_SDTMultLvlTest, 1, 4, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_SDTMultLvlTest, 1, 4, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_SDTMultLvlTest, 1, 4, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_SDTMultLvlTest, 1, 4, 1, 1, names);
		}
		public void testRead1SDTMultLvlEvtParms() throws RecognitionException, TokenStreamException {
			OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
			String act = "x = param.SDT_e.person.age;"; //$NON-NLS-1$
			String names[] = { "SDT_e" };//$NON-NLS-1$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_SDTMultLvlTest, 1, 4, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_SDTMultLvlTest, 1, 4, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_SDTMultLvlTest, 1, 4, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_SDTMultLvlTest, 1, 4, 1, 1, names);
		}
		
		public void testRead2EvtData() throws RecognitionException, TokenStreamException {
			String act = "x = rcvd_evt.s; y = rcvd_evt.i;"; //$NON-NLS-1$
			String names[] = { "s", "i" };//$NON-NLS-1$//$NON-NLS-2$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_TWO, 2, 4, 2, 2, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_TWO, 2, 4, 2, 2, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_TWO, 2, 4, 2, 2, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_TWO, 2, 4, 2, 2, names);
		}
		public void testRead2EvtParms() throws RecognitionException, TokenStreamException {
			String act = "x = param.s; y = param.i;"; //$NON-NLS-1$
			String names[] = { "s", "i" };//$NON-NLS-1$//$NON-NLS-2$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_TWO, 2, 4, 2, 2, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_TWO, 2, 4, 2, 2, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_TWO, 2, 4, 2, 2, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_TWO, 2, 4, 2, 2, names);
		}
		public void testRead3EvtData() throws RecognitionException, TokenStreamException {
			String act = "v = rcvd_evt.r; x = rcvd_evt.s; y = rcvd_evt.i;"; //$NON-NLS-1$
			String names[] = { "r", "s", "i" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_THREE, 3, 6, 3, 3, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_THREE, 3, 6, 3, 3, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_THREE, 3, 6, 3, 3, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_THREE, 3, 6, 3, 3, names);
		}
		public void testRead3EvtParms() throws RecognitionException, TokenStreamException {
			String act = "v = param.r; x = param.s; y = param.i;"; //$NON-NLS-1$
			String names[] = { "r", "s", "i" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_THREE, 3, 6, 3, 3, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_THREE, 3, 6, 3, 3, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_THREE, 3, 6, 3, 3, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_THREE, 3, 6, 3, 3, names);
		}
		public void testRead3EvtDataParmsMix() throws RecognitionException, TokenStreamException {
			String act = "v = rcvd_evt.r; x = param.s; y = rcvd_evt.i;"; //$NON-NLS-1$
			String names[] = { "r", "s", "i" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_THREE, 3, 6, 3, 3, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ASM_THREE, 3, 6, 3, 3, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_THREE, 3, 6, 3, 3, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_CSM_THREE, 3, 6, 3, 3, names);
		}
		public void testReadPolyEvtData() throws RecognitionException, TokenStreamException {
			OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
			String act = "x = rcvd_evt.r;"; //$NON-NLS-1$
			String names[] = { "r" };//$NON-NLS-1$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_POLYTWO, 1, 2, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_POLYTWO, 1, 2, 1, 1, names);
		}
		public void testReadPolyEvtParms() throws RecognitionException, TokenStreamException {
			OalParserTest.clearActionData(OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
			String act = "x = param.r;"; //$NON-NLS-1$
			String names[] = { "r" };//$NON-NLS-1$
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_STATE, act, OalParserTest.STATE_ISM_POLYTWO, 1, 2, 1, 1, names);
			validateEvtParam( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, OalParserTest.TRANS_ISM_POLYTWO, 1, 2, 1, 1, names);
		}

		public void validateEvtParamError(int activityType, String act, String[] err_msg, int actNum, int numStmts, int numVal, int numVars, int numEDV)
			throws RecognitionException, TokenStreamException {
			String x = OalParserTest.parseAction(act, activityType, actNum); //$NON-NLS-1$
			String lines[] = x.split("\n");//$NON-NLS-1$
			assertEquals(err_msg.length, lines.length);
			for (int i = 0; i < err_msg.length; ++i) {
				assertEquals(err_msg[i], lines[i]); //$NON-NLS-1$
			}
			OalParserTest.validateBlkStmtVal(1, numStmts, numVal);
			Variable_c var[] = Variable_c.VariableInstances(OalParserTest.modelRoot);
			assertEquals(numVars, var.length);
			EventDatumValue_c[] edv = EventDatumValue_c.EventDatumValueInstances(OalParserTest.modelRoot);
			assertEquals(numEDV, edv.length);
			OalParserTest.clearActionData(activityType, actNum);
		}

		public void testReadNoSuchEvtData() throws RecognitionException, TokenStreamException {
			String act = "x = rcvd_evt.x;"; //$NON-NLS-1$
			String[] err_msg = { ":1:14-14: The following incoming messages do not carry required parameter ->x<- one",//$NON-NLS-1$
				"line 1:16: unexpected token: null",//$NON-NLS-1$
				"line 1:16: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_ONE, 0, 2, 1, 1 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_ONE, 0, 2, 1, 1 );
			err_msg[0] = ":1:14-14: Parameter ->x<- is not carried by event one"; //$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_ONE, 0, 2, 1, 1 );// 
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_ONE, 0, 2, 1, 1 );
		}
		public void testReadNoSuchEvtParms() throws RecognitionException, TokenStreamException {
			String act = "x = param.x;"; //$NON-NLS-1$
			String[] err_msg = { ":1:11-11: The following incoming messages do not carry required parameter ->x<- one",//$NON-NLS-1$
				"line 1:13: unexpected token: null",//$NON-NLS-1$
				"line 1:13: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_ONE, 0, 2, 1, 1 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_ONE, 0, 2, 1, 1 );
			err_msg[0] = ":1:11-11: Parameter ->x<- is not carried by event one"; //$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_ONE, 0, 2, 1, 1 );// 
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_ONE, 0, 2, 1, 1 );
		}
		public void testWriteEvtData() throws RecognitionException, TokenStreamException {
			String act = "rcvd_evt.i = 1;"; //$NON-NLS-1$
			String[] err_msg = { "line 1:1: unexpected token: rcvd_evt" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_ONE, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_ONE, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_ONE, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_ONE, 1, 2, 1, 0 );
		}
		public void testWriteEvtParms() throws RecognitionException, TokenStreamException {
			String act = "param.i = 1;"; //$NON-NLS-1$
			String[] err_msg = { ":1:7-7: Event parameters are not assignable",
					"line 1:12: expecting TOK_EQUAL, found ';'", //$NON-NLS-1$
					"line 1:13: expecting Semicolon, found 'null'"};//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_ONE, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_ONE, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_ONE, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_ONE, 0, 0, 0, 0 );
		}
		public void testWriteEvtDataMultData() throws RecognitionException, TokenStreamException {
			String act = "rcvd_evt.s = \"bad\";"; //$NON-NLS-1$
			String[] err_msg = { "line 1:1: unexpected token: rcvd_evt" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_TWO, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_TWO, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_TWO, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_TWO, 1, 2, 1, 0 );
		}
		public void testWriteEvtParmsMultParms() throws RecognitionException, TokenStreamException {
			String act = "param.s = \"bad\";"; //$NON-NLS-1$
			String[] err_msg = { ":1:7-7: Event parameters are not assignable",
					"line 1:16: expecting TOK_EQUAL, found ';'", //$NON-NLS-1$
					"line 1:17: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_TWO, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_TWO, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_TWO, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_TWO, 0, 0, 0, 0 );
		}
		public void testWriteEvtDataWrongType() throws RecognitionException, TokenStreamException {
			String act = "rcvd_evt.i = true;"; //$NON-NLS-1$
			String[] err_msg = { "line 1:1: unexpected token: rcvd_evt" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_ONE, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_ONE, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_ONE, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_ONE, 1, 2, 1, 0 );
		}
		public void testWriteEvtParmWrongType() throws RecognitionException, TokenStreamException {
			String act = "param.i = true;"; //$NON-NLS-1$
			String[] err_msg = { ":1:7-7: Event parameters are not assignable",   //$NON-NLS-1$
					             "line 1:11: expecting TOK_EQUAL, found 'true'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_ONE, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_ONE, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_ONE, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_ONE, 0, 0, 0, 0 );
		}
		public void testWriteEvtDataWrongTypeMultData() throws RecognitionException, TokenStreamException {
			String act = "rcvd_evt.s = 2.1;"; //$NON-NLS-1$
			String[] err_msg = { "line 1:1: unexpected token: rcvd_evt" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_THREE, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_THREE, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_THREE, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_THREE, 1, 2, 1, 0 );
		}
		public void testWriteEvtParmWrongTypeMultParm() throws RecognitionException, TokenStreamException {
			String act = "param.s = 2.1;"; //$NON-NLS-1$
			String[] err_msg = { ":1:7-7: Event parameters are not assignable", //$NON-NLS-1$
					             "line 1:14: expecting TOK_EQUAL, found ';'", //$NON-NLS-1$
					             "line 1:15: expecting Semicolon, found 'null'"};//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_THREE, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_THREE, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_THREE, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_THREE, 0, 0, 0, 0 );
		}
		public void testReadEvtDataWrongType() throws RecognitionException, TokenStreamException {
			String act = "x = true; x = rcvd_evt.i;"; //$NON-NLS-1$
			String[] err_msg = { ":1:24-24: Variable ->x<- already exists as a different type",//$NON-NLS-1$
				"line 1:26: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_ONE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_ONE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_ONE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_ONE, 1, 3, 1, 0 );
		 }
		public void testReadEvtParmWrongType() throws RecognitionException, TokenStreamException {
			String act = "x = true; x = param.i;"; //$NON-NLS-1$
			String[] err_msg = { ":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
				"line 1:23: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_ONE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_ONE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_ONE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_ONE, 1, 3, 1, 0 );
		 }
		public void testReadEvtDataWrongTypeMultParms() throws RecognitionException, TokenStreamException {
			String act = "x = true; x = rcvd_evt.r;"; //$NON-NLS-1$
			String[] err_msg = { ":1:24-24: Variable ->x<- already exists as a different type",//$NON-NLS-1$
				"line 1:26: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_THREE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_THREE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_THREE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_THREE, 1, 3, 1, 0 );
		}
		public void testReadEvtParmWrongTypeMultParms() throws RecognitionException, TokenStreamException {
			String act = "x = true; x = param.r;"; //$NON-NLS-1$
			String[] err_msg = { ":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
				"line 1:23: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_THREE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ASM_THREE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_THREE, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_CSM_THREE, 1, 3, 1, 0 );
		}
		public void testReadNoSuchPolyEvtData() throws RecognitionException, TokenStreamException {
			String act = "x = rcvd_evt.x;"; //$NON-NLS-1$
			String[] err_msg = { ":1:14-14: The following incoming messages do not carry required parameter ->x<- poly_data",//$NON-NLS-1$
				"line 1:16: unexpected token: null",//$NON-NLS-1$
				"line 1:16: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_POLYTWO, 0, 2, 1, 1 );
			err_msg[0] = ":1:14-14: Parameter ->x<- is not carried by event poly_data";
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_POLYTWO, 0, 2, 1, 1 );
		}
		public void testReadNoSuchPolyEvtParm() throws RecognitionException, TokenStreamException {
			String act = "x = param.x;"; //$NON-NLS-1$
			String[] err_msg = { ":1:11-11: The following incoming messages do not carry required parameter ->x<- poly_data",//$NON-NLS-1$
				"line 1:13: unexpected token: null",//$NON-NLS-1$
				"line 1:13: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_POLYTWO, 0, 2, 1, 1 );
			err_msg[0] = ":1:11-11: Parameter ->x<- is not carried by event poly_data";
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_POLYTWO, 0, 2, 1, 1 );
		}
		public void testReadOrphanedPolyEvtData() throws RecognitionException, TokenStreamException {
			String act = "x = rcvd_evt.r;"; //$NON-NLS-1$
			String[] err_msg = { ":1:14-14: Parameter ->r<- belongs to a Polymorphic Event that is no longer defined in supertype",//$NON-NLS-1$
				"line 1:16: unexpected token: null",//$NON-NLS-1$
				"line 1:16: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_POLYORPHANED, 0, 2, 1, 1 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_POLYORPHANED, 0, 2, 1, 1 );
		}
		public void testReadOrphanedPolyEvtParm() throws RecognitionException, TokenStreamException {
			String act = "x = param.r;"; //$NON-NLS-1$
			String[] err_msg = { ":1:11-11: Parameter ->r<- belongs to a Polymorphic Event that is no longer defined in supertype",//$NON-NLS-1$
				"line 1:13: unexpected token: null",//$NON-NLS-1$
				"line 1:13: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_POLYORPHANED, 0, 2, 1, 1 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_POLYORPHANED, 0, 2, 1, 1 );
		}
		public void testReadPolyEvtDataWrongType() throws RecognitionException, TokenStreamException {
			String act = "x = true; x = rcvd_evt.r;"; //$NON-NLS-1$
			String[] err_msg = { ":1:24-24: Variable ->x<- already exists as a different type",//$NON-NLS-1$
				"line 1:26: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_POLYTWO, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_POLYTWO, 1, 3, 1, 0 );
		 }
		public void testReadPolyEvtParmWrongType() throws RecognitionException, TokenStreamException {
			String act = "x = true; x = param.r;"; //$NON-NLS-1$
			String[] err_msg = { ":1:21-21: Variable ->x<- already exists as a different type",//$NON-NLS-1$
				"line 1:23: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_POLYTWO, 1, 3, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_POLYTWO, 1, 3, 1, 0 );
		 }
		public void testWritePolyEvtData() throws RecognitionException, TokenStreamException {
			String act = "rcvd_evt.r = 1.1;"; //$NON-NLS-1$
			String[] err_msg = { "line 1:1: unexpected token: rcvd_evt" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_POLYTWO, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_POLYTWO, 1, 2, 1, 0 );
		}
		public void testWritePolyEvtParm() throws RecognitionException, TokenStreamException {
			String act = "param.r = 1.1;"; //$NON-NLS-1$
			String[] err_msg = { ":1:7-7: Event parameters are not assignable", //$NON-NLS-1$
		             "line 1:14: expecting TOK_EQUAL, found ';'", //$NON-NLS-1$
		             "line 1:15: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_POLYTWO, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_POLYTWO, 0, 0, 0, 0 );
		}
		public void testWritePolyEvtDataWrongType() throws RecognitionException, TokenStreamException {
			String act = "rcvd_evt.r = \"bad\";"; //$NON-NLS-1$
			String[] err_msg = { "line 1:1: unexpected token: rcvd_evt" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_POLYTWO, 1, 2, 1, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_POLYTWO, 1, 2, 1, 0 );
		}
		public void testWritePolyEvtParmWrongType() throws RecognitionException, TokenStreamException {
			String act = "param.r = \"bad\";"; //$NON-NLS-1$
			String[] err_msg = { ":1:7-7: Event parameters are not assignable", //$NON-NLS-1$
		             "line 1:16: expecting TOK_EQUAL, found ';'",     //$NON-NLS-1$
		             "line 1:17: expecting Semicolon, found 'null'" };//$NON-NLS-1$
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_STATE, act, err_msg, OalParserTest.STATE_ISM_POLYTWO, 0, 0, 0, 0 );
			validateEvtParamError( OalParserTest.ACTIVITY_TYPE_TRANSITION, act, err_msg, OalParserTest.TRANS_ISM_POLYTWO, 0, 0, 0, 0 );
		}
		public void tearDown() {
			try {
			  OalParserTest.tearDownActionData();
			}
			catch (RecognitionException re) {
				// do nothing
			}
			catch (TokenStreamException te) {
				// do nothing
			}
		}
}
