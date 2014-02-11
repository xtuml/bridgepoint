//========================================================================
//
//File:      $RCSfile: TestAttribute_Generics.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:00:37 $
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

import com.mentor.nucleus.bp.core.AssignToMember_c;
import com.mentor.nucleus.bp.core.AttributeValueReference_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.InstanceHandle_c;
import com.mentor.nucleus.bp.core.InstanceReference_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.core.common.IdAssigner;

public class TestAttribute_Generics extends TestCase {

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

	public void validateAttributeRead(String act, String kl, String typeName, String [] vars) throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, t.length);
		assertEquals(vars[0], t[0].getName());//$NON-NLS-1$
		assertEquals(vars[1], t[1].getName());//$NON-NLS-1$
		InstanceHandle_c[] inst = InstanceHandle_c.InstanceHandleInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, inst.length);
		assertEquals(inst[0].getVar_id(), t[0].getVar_id());
        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(inst[0]);
		assertEquals(kl, mc.getKey_lett());//$NON-NLS-1$
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, val.length);
		UUID typeId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, typeName);
        UUID objId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "inst_ref<Object>");
		assertEquals(typeId, val[0].getDt_id());
        assertEquals(objId, val[1].getDt_id());
        assertEquals(typeId, val[2].getDt_id());
		AttributeValueReference_c[] av = AttributeValueReference_c.AttributeValueReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, av.length);
		InstanceReference_c[] ir = InstanceReference_c.InstanceReferenceInstances(OalParserTest_Generics.modelRoot);
        assertEquals(ir[0].getVar_id(), t[0].getVar_id());
		assertEquals(av[0].getValue_id(), val[2].getValue_id());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, st.length);
	}
	public void testAttributeRead() throws RecognitionException, TokenStreamException {
		String [] vars = { "d1", "x" };
		validateAttributeRead("create object instance d1 of D_D; \nx = d1.Disk_ID;", "D_D", "integer", vars);
	}
	public void testAttributeReadWithUnderscores() throws RecognitionException, TokenStreamException {
		String [] vars = { "d1", "x" };
		validateAttributeRead("create object instance d1 of _T; \nx = d1._attr;", "_T", "_testUdt", vars);
	}
	public void testBadAttributeRead() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nx = d1.Bad_Attribute;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":2:8-20: ->Bad_Attribute<- is not an attribute of class ->Disk<-.", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:22: unexpected token: null", lines[1]); //$NON-NLS-1$
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(2, t.length);
		assertEquals("d1", t[0].getName());//$NON-NLS-1$
        assertEquals("x", t[1].getName());//$NON-NLS-1$
		InstanceHandle_c[] inst = InstanceHandle_c.InstanceHandleInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, inst.length);
		assertEquals(inst[0].getVar_id(), t[0].getVar_id());
        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(inst[0]);
		assertEquals("D_D", mc.getKey_lett());//$NON-NLS-1$
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, val.length);
		AttributeValueReference_c[] av = AttributeValueReference_c.AttributeValueReferenceInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, av.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, st.length);
	}
	 public void testAttributeWrite() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nd1.Disk_ID = 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//91//$NON-NLS-1$
		goodAttrWriteCheck("Disk_ID", intId, "d1", "D_D", 2);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	}
	public void testAttributeWriteWithUnderscores() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of _T; \nd1._attr = 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//91//$NON-NLS-1$
		goodAttrWriteCheck("_attr", intId, "d1", "_T", 2);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	}
	public void testAttributeWriteSelf() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("self.Row_Number = 1;", OalParserTest_Generics.ACTIVITY_TYPE_IB_OP, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//91//$NON-NLS-1$
		goodAttrWriteCheck("Row_Number", intId, "self", "D_H", 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_IB_OP, OalParserTest_Generics.TEST_VOID_NO_PARM);
	}
	public void testAttributeWriteSelfFromTransition() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("self.u_int = 1;", OalParserTest_Generics.ACTIVITY_TYPE_TRANSITION, OalParserTest_Generics.TRANS_ISM_NONE); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
		goodAttrWriteCheck("u_int", intId, "self", "D_TST", 1);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_TRANSITION, OalParserTest_Generics.TRANS_ISM_NONE);
	}
	public void goodAttrWriteCheck(String attr_name, UUID dt_id, String var_name, String kl, int numStmt) {
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, t.length);
		assertEquals(var_name, t[0].getName());
		InstanceHandle_c[] inst = InstanceHandle_c.InstanceHandleInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, inst.length);
		assertEquals(inst[0].getVar_id(), t[0].getVar_id());
        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(inst[0]);
		assertEquals(kl, mc.getKey_lett());
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(3, val.length);
		assertEquals(dt_id, val[2].getDt_id());
		AssignToMember_c[] ata = AssignToMember_c.AssignToMemberInstances(OalParserTest_Generics.modelRoot);
		assertEquals(1, ata.length);
		AttributeValueReference_c avl = AttributeValueReference_c.getOneV_AVLOnR801(Value_c.getOneV_VALOnR689(ata[0]));
		InstanceReference_c irf = InstanceReference_c.getOneV_IRFOnR801(Value_c.getOneV_VALOnR807(avl));
		Variable_c v_var = Variable_c.getOneV_VAROnR808(irf);
		assertEquals(v_var.getVar_id(), t[0].getVar_id());
		assertEquals(ata[0].getR_value_id(), val[2].getValue_id());
		// validate Obj_ID and Attr_ID (R630)
		Attribute_c attr = Attribute_c.getOneO_ATTROnR806(AttributeValueReference_c.getOneV_AVLOnR801(Value_c.getOneV_VALOnR689(ata[0])));
		assertEquals(attr_name, attr.getName());
		ModelClass_c obj = ModelClass_c.getOneO_OBJOnR102(attr);
		assertEquals(kl, obj.getKey_lett());
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numStmt, st.length);
		assertEquals(st[numStmt-1].getStatement_id(), ata[0].getStatement_id());
	}
	public void testAttributeWriteI2R() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nd1.real_attr = 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//91//$NON-NLS-1$
		goodAttrWriteCheck("real_attr", intId, "d1", "D_D", 2);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}
	public void testAttributeWriteR2I() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nd1.Disk_ID = 1.1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x);//$NON-NLS-1$
		UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//92//$NON-NLS-1$
		goodAttrWriteCheck("Disk_ID", realId, "d1", "D_D", 2);//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}
	public void badAttrWriteCheck(int numStmts, int numVars, int numVals) throws RecognitionException, TokenStreamException {
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numVars, t.length);
		if (numVars >= 1)
			assertEquals("d1", t[0].getName());//$NON-NLS-1$
		if (numVars >= 2)
			assertEquals("d2", t[1].getName());//$NON-NLS-1$
		InstanceHandle_c[] inst = InstanceHandle_c.InstanceHandleInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numVars, inst.length);
		for (int i = 0; i < numVars; ++i) {
			assertEquals(inst[i].getVar_id(), t[i].getVar_id());
            ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(inst[i]);
			assertEquals("D_D", mc.getKey_lett());//$NON-NLS-1$
		}
		Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numVals, val.length);
		AssignToMember_c[] ata = AssignToMember_c.AssignToMemberInstances(OalParserTest_Generics.modelRoot);
		assertEquals(0, ata.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest_Generics.modelRoot);
		assertEquals(numStmts, st.length);
	}
	public void testAttributeWriteBadName() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nd1.Bad_Attribute = 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":2:4-16: ->Bad_Attribute<- is not an attribute of class ->Disk<-.", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:21: expecting TOK_EQUAL, found ';'", lines[1]); //$NON-NLS-1$
		assertEquals("line 2:22: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
		badAttrWriteCheck(1, 1, 0);
	}
	public void testAttributeWriteBad2CurrentState() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nd1.current_state = 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":2:20-20: Cannot assign to the current_state attribute", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:22: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		badAttrWriteCheck(1, 1, 2);
	}
	public void testAttributeWriteBad2RefAttr() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nd1.Row_Number = 2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":2:17-17: Attribute ->Row_Number<- is a different type", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:19: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		badAttrWriteCheck(1, 1, 2);
	}
	public void testAttributeWriteBad2MDA() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nd1.mda = \"bad\";", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":2:10-14: Cannot assign to a derived attribute ->mda<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:16: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		badAttrWriteCheck(1, 1, 2);
	}
	public void testAttributeWriteBad2ID() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \ncreate object instance d2 of D_D;\nd1.id2 = d2.id2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:13-15: Cannot assign to an attribute ->id2<- which is of type unique_id and also an identifier", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:17: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		badAttrWriteCheck(2, 2, 3);
	}
	public void testAttributeWriteS2I() throws RecognitionException, TokenStreamException {
		String x = OalParserTest_Generics.parseAction("create object instance d1 of D_D; \nd1.Disk_ID = \"x\";", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":2:14-16: Attribute ->Disk_ID<- is a different type", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:18: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		badAttrWriteCheck(1, 1, 2);
	}

}
