//========================================================================
//
//File:      $RCSfile: TestCRUD.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/17 03:38:09 $
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

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.CreateNoVariable_c;
import com.mentor.nucleus.bp.core.Create_c;
import com.mentor.nucleus.bp.core.Delete_c;
import com.mentor.nucleus.bp.core.InstanceHandle_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.RelateUsing_c;
import com.mentor.nucleus.bp.core.Relate_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.UnrelateUsing_c;
import com.mentor.nucleus.bp.core.Unrelate_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;

public class TestCRUD extends TestCase {

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

	public void testObjectKeyletterFound() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("create object instance d1 of D_D; ", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(1, st.length);
		Create_c[] s = Create_c.CreateInstances(OalParserTest.modelRoot);
		assertEquals(1, s.length);
		assertTrue(s[0].getIs_implicit());
		ModelClass_c[] obj_set = ModelClass_c.ModelClassInstances(OalParserTest.modelRoot);
		assertEquals(st[0].getStatement_id(), s[0].getStatement_id());
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(1, t.length);
		assertEquals("d1", t[0].getName()); //$NON-NLS-1$
		InstanceHandle_c[] iv = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
        ModelClass_c mc = ModelClass_c.getOneO_OBJOnR818(iv[0]);
		assertEquals("D_D", mc.getKey_lett()); //$NON-NLS-1$
	}
	public void testObjectKeyletterFoundNoVar() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("create object instance of D_D; ", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(1, st.length);
		CreateNoVariable_c[] s = CreateNoVariable_c.CreateNoVariableInstances(OalParserTest.modelRoot);
		assertEquals(1, s.length);
		ModelClass_c[] obj_set = ModelClass_c.ModelClassInstances(OalParserTest.modelRoot);
		assertEquals(st[0].getStatement_id(), s[0].getStatement_id());
		InstanceHandle_c[] iv = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
		assertEquals(0, iv.length);
	}
	public void testObjectKeyletterNotFound() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("create object instance nsi of XX ; ", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n"); //$NON-NLS-1$
		assertEquals(":1:31-32: Cannot find specified class key letters ->XX<-.", lines[0]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(0, st.length);
		Create_c[] cs = Create_c.CreateInstances(OalParserTest.modelRoot);
		assertEquals(0, cs.length);
		CreateNoVariable_c[] s = CreateNoVariable_c.CreateNoVariableInstances(OalParserTest.modelRoot);
		assertEquals(0, s.length);
		InstanceHandle_c[] iv = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
		assertEquals(0, iv.length);
	}
	public void testObjectKeyletterNotFoundNoVar() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("create object instance of XX ; ", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n"); //$NON-NLS-1$
		assertEquals(":1:27-28: Cannot find specified class key letters ->XX<-.", lines[0]); //$NON-NLS-1$
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(0, st.length);
		Create_c[] cs = Create_c.CreateInstances(OalParserTest.modelRoot);
		assertEquals(0, cs.length);
		CreateNoVariable_c[] s = CreateNoVariable_c.CreateNoVariableInstances(OalParserTest.modelRoot);
		assertEquals(0, s.length);
		InstanceHandle_c[] iv = InstanceHandle_c.InstanceHandleInstances(OalParserTest.modelRoot);
		assertEquals(0, iv.length);
	}
	public void testCreateWrongVarType() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("x = 1; create object instance x of D_D;", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":1:36-38: Variable ->x<- does not exist in scope as an object instance variable", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:40: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(1, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(1, t.length);
		Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
		assertEquals(2, val.length); // the literal 1 and transient x
		Create_c[] cre = Create_c.CreateInstances(OalParserTest.modelRoot);
		assertEquals(0, cre.length);
	}
	public void testCreateWrongClassType() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("create object instance x of D_D;\ncreate object instance x of D_H;", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":2:29-31: Variable ->x<- already exists as a different type", lines[0]); //$NON-NLS-1$
		assertEquals("line 2:33: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(1, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(1, t.length);
		Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
		assertEquals(0, val.length);
		Create_c[] cre = Create_c.CreateInstances(OalParserTest.modelRoot);
		assertEquals(1, cre.length);
	}
	public void testDelete() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("create object instance d1 of D_D; delete object instance d1;", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		assertEquals("", x); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(2, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		assertEquals(blk[0].getBlock_id(), st[1].getBlock_id());
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(1, t.length);
		assertEquals("d1", t[0].getName());//$NON-NLS-1$
		Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
		assertEquals(0, val.length);
		Delete_c[] del = Delete_c.DeleteInstances(OalParserTest.modelRoot);
		assertEquals(1, del.length);
		assertEquals(st[1].getStatement_id(), del[0].getStatement_id());
		assertEquals(t[0].getVar_id(), del[0].getVar_id());
	}
	public void testDeleteI() throws RecognitionException, TokenStreamException {
		String x = OalParserTest.parseAction("x = 5; delete object instance x;", OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":1:31-31: Variable ->x<- does not exist in scope as an object instance variable", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:33: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(1, st.length);
		assertEquals(blk[0].getBlock_id(), st[0].getBlock_id());
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(1, t.length);
		assertEquals("x", t[0].getName());//$NON-NLS-1$
		Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
		assertEquals(2, val.length);
		Delete_c[] del = Delete_c.DeleteInstances(OalParserTest.modelRoot);
		assertEquals(0, del.length);
	}
	private void goodRelateValidate(String[] vars, int relNum, int numStmt, String phrase, boolean using)
		throws RecognitionException, TokenStreamException {
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(numStmt, st.length);
		for (int i = 0; i < numStmt; ++i)
			assertEquals(blk[0].getBlock_id(), st[i].getBlock_id());
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(vars.length, t.length);
		for (int i = 0; i < vars.length; ++i)
			assertEquals(vars[i], t[i].getName());
		if (using) {
			RelateUsing_c rel[] = RelateUsing_c.RelateUsingInstances(OalParserTest.modelRoot);
			assertEquals(1, rel.length);
			assertEquals(st[numStmt - 1].getStatement_id(), rel[0].getStatement_id());
			assertEquals(t[0].getVar_id(), rel[0].getOne_side_var_id());
			assertEquals(t[1].getVar_id(), rel[0].getOther_side_var_id());
			assertEquals(t[2].getVar_id(), rel[0].getAssociative_var_id());
			assertEquals(phrase, rel[0].getRelationship_phrase());
			Association_c r_rel = Association_c.getOneR_RELOnR654(rel[0]);
			assertEquals(relNum, r_rel.getNumb());
		} else {
			Relate_c rel[] = Relate_c.RelateInstances(OalParserTest.modelRoot);
			assertEquals(1, rel.length);
			assertEquals(st[2].getStatement_id(), rel[0].getStatement_id());
			assertEquals(t[0].getVar_id(), rel[0].getOne_side_var_id());
			assertEquals(t[1].getVar_id(), rel[0].getOther_side_var_id());
			assertEquals(phrase, rel[0].getRelationship_phrase());
			Association_c r_rel = Association_c.getOneR_RELOnR653(rel[0]);
			assertEquals(relNum, r_rel.getNumb());
		}
	}
	private void goodUnrelateValidate(String[] vars, int relNum, int numStmt, String phrase, boolean using)
		throws RecognitionException, TokenStreamException {
		Block_c[] blk = Block_c.BlockInstances(OalParserTest.modelRoot);
		assertEquals(1, blk.length);
		Statement_c[] st = Statement_c.StatementInstances(OalParserTest.modelRoot);
		assertEquals(numStmt, st.length);
		for (int i = 0; i < numStmt; ++i)
			assertEquals(blk[0].getBlock_id(), st[i].getBlock_id());
		Value_c[] val = Value_c.ValueInstances(OalParserTest.modelRoot);
		assertEquals(0, val.length);
		Variable_c[] t = Variable_c.VariableInstances(OalParserTest.modelRoot);
		assertEquals(vars.length, t.length);
		for (int i = 0; i < vars.length; ++i)
			assertEquals(vars[i], t[i].getName());
		if (using) {
			UnrelateUsing_c rel[] = UnrelateUsing_c.UnrelateUsingInstances(OalParserTest.modelRoot);
			assertEquals(1, rel.length);
			assertEquals(st[numStmt - 1].getStatement_id(), rel[0].getStatement_id());
			assertEquals(t[0].getVar_id(), rel[0].getOne_side_var_id());
			assertEquals(t[1].getVar_id(), rel[0].getOther_side_var_id());
			assertEquals(t[2].getVar_id(), rel[0].getAssociative_var_id());
			assertEquals(phrase, rel[0].getRelationship_phrase());
			Association_c r_rel = Association_c.getOneR_RELOnR656(rel[0]);
			assertEquals(relNum, r_rel.getNumb());
		} else {
			Unrelate_c rel[] = Unrelate_c.UnrelateInstances(OalParserTest.modelRoot);
			assertEquals(1, rel.length);
			assertEquals(st[2].getStatement_id(), rel[0].getStatement_id());
			assertEquals(t[0].getVar_id(), rel[0].getOne_side_var_id());
			assertEquals(t[1].getVar_id(), rel[0].getOther_side_var_id());
			assertEquals(phrase, rel[0].getRelationship_phrase());
			Association_c r_rel = Association_c.getOneR_RELOnR655(rel[0]);
			assertEquals(relNum, r_rel.getNumb());
		}
	}
	public void testRelateBinary() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance h of D_H;\nrelate d1 to h across R4;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		String[] var_list = { "d1", "h" };//$NON-NLS-1$//$NON-NLS-2$
		goodRelateValidate(var_list, 4, 3, "", false);//$NON-NLS-1$
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		assertEquals("", x); //$NON-NLS-1$
		goodUnrelateValidate(var_list, 4, 3, "", false);//$NON-NLS-1$
	}
	public void testRelateBinaryGoodPhraseNotReflexive() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance h of D_H;\nrelate d1 to h across R4.'is permanently assigned to';"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		String[] var_list = { "d1", "h" };//$NON-NLS-1$//$NON-NLS-2$
		goodRelateValidate(var_list, 4, 3, "'is permanently assigned to'", false);//$NON-NLS-1$
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		assertEquals("", x); //$NON-NLS-1$
		goodUnrelateValidate(var_list, 4, 3, "'is permanently assigned to'", false);//$NON-NLS-1$
	}
	public void testRelateBinaryGoodReflexive() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance d2 of D_D;\nrelate d1 to d2 across R12.'precedes';"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		String[] var_list = { "d1", "d2" };//$NON-NLS-1$//$NON-NLS-2$
		goodRelateValidate(var_list, 12, 3, "'precedes'", false);//$NON-NLS-1$
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		assertEquals("", x); //$NON-NLS-1$
		goodUnrelateValidate(var_list, 12, 3, "'precedes'", false);//$NON-NLS-1$
	}
	public void testRelateBinaryGoodReflexive2() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance d2 of D_D;\nrelate d1 to d2 across R12.'follows';"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		String[] var_list = { "d1", "d2" };//$NON-NLS-1$//$NON-NLS-2$
		goodRelateValidate(var_list, 12, 3, "'follows'", false);//$NON-NLS-1$
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		assertEquals("", x); //$NON-NLS-1$
		goodUnrelateValidate(var_list, 12, 3, "'follows'", false);//$NON-NLS-1$
	}
	public void testRelateSubtypeGood() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance od of D_OD;\nrelate d1 to od across R3;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		String[] var_list = { "d1", "od" };//$NON-NLS-1$//$NON-NLS-2$
		goodRelateValidate(var_list, 3, 3, "", false);//$NON-NLS-1$
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		assertEquals("", x); //$NON-NLS-1$
		goodUnrelateValidate(var_list, 3, 3, "", false);//$NON-NLS-1$
	}
	public void testRelateAssocGood() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance q of D_QP;\ncreate object instance dq of D_DQ;\nrelate d1 to q across R1 using dq;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		String[] var_list = { "d1", "q", "dq" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		goodRelateValidate(var_list, 1, 4, "", true);//$NON-NLS-1$
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		assertEquals("", x); //$NON-NLS-1$
		goodUnrelateValidate(var_list, 1, 4, "", true);//$NON-NLS-1$
	}
	public void testRelateAssocGood2() throws RecognitionException, TokenStreamException {
		String act = "create object instance q of D_QP;\ncreate object instance d1 of D_D;\ncreate object instance dq of D_DQ;\nrelate q to d1 across R1 using dq;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		String[] var_list = { "q", "d1", "dq" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		goodRelateValidate(var_list, 1, 4, "", true);//$NON-NLS-1$
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		assertEquals("", x); //$NON-NLS-1$
		goodUnrelateValidate(var_list, 1, 4, "", true);//$NON-NLS-1$
	}
	public void testRelateAssocGoodWithPhrase() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance q of D_QP;\ncreate object instance dq of D_DQ;\nrelate d1 to q across R1.'needed by' using dq;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		String[] var_list = { "d1", "q", "dq" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		goodRelateValidate(var_list, 1, 4, "'needed by'", true);//$NON-NLS-1$
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		assertEquals("", x); //$NON-NLS-1$
		goodUnrelateValidate(var_list, 1, 4, "'needed by'", true);//$NON-NLS-1$
	}
	public void testRelateAssocGoodReflexive() throws RecognitionException, TokenStreamException {
		String act = "create object instance qp1 of D_QP;\ncreate object instance qp2 of D_QP;\ncreate object instance qpo of D_QPO;\nrelate qp1 to qp2 across R13.'precedes' using qpo;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		String[] var_list = { "qp1", "qp2", "qpo" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		goodRelateValidate(var_list, 13, 4, "'precedes'", true);//$NON-NLS-1$
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		assertEquals("", x); //$NON-NLS-1$
		goodUnrelateValidate(var_list, 13, 4, "'precedes'", true);//$NON-NLS-1$
	}
	public void testRelateAssocGoodReflexive2() throws RecognitionException, TokenStreamException {
		String act = "create object instance qp1 of D_QP;\ncreate object instance qp2 of D_QP;\ncreate object instance qpo of D_QPO;\nrelate qp1 to qp2 across R13.'follows' using qpo;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		assertEquals("", x); //$NON-NLS-1$
		String[] var_list = { "qp1", "qp2", "qpo" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		goodRelateValidate(var_list, 13, 4, "'follows'", true);//$NON-NLS-1$
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		assertEquals("", x); //$NON-NLS-1$
		goodUnrelateValidate(var_list, 13, 4, "'follows'", true);//$NON-NLS-1$
	}
	public void testRelateBinaryWrongPhraseNotReflexive() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance h of D_H;\nrelate d1 to h across R4.'is permanent home for';"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:26-48: Class ->Permanent Home in Library<- in association ->R4<- does not contain destination association phrase ->is permanent home for<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:50: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "d1", "h" };//$NON-NLS-1$//$NON-NLS-2$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:30-52: Class ->Permanent Home in Library<- in association ->R4<- does not contain destination association phrase ->is permanent home for<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:54: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
	}
	public void testRelateBinaryBadPhraseNotReflexive() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance h of D_H;\nrelate d1 to h across R4.'bad phrase';"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:26-37: Class ->Permanent Home in Library<- in association ->R4<- does not contain destination association phrase ->bad phrase<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:39: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "d1", "h" };//$NON-NLS-1$//$NON-NLS-2$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:30-41: Class ->Permanent Home in Library<- in association ->R4<- does not contain destination association phrase ->bad phrase<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:43: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
	}
	public void testRelateBinaryBadPhraseReflexive() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance d2 of D_D;\nrelate d1 to d2 across R12.'neither';"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:28-36: Class ->Disk<- in reflexive association ->R12<- does not contain destination association phrase ->neither<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:38: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "d1", "d2" };//$NON-NLS-1$//$NON-NLS-2$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:32-40: Class ->Disk<- in reflexive association ->R12<- does not contain destination association phrase ->neither<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:42: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
	}
	public void testRelateBinaryBadNoPhraseReflexive() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance d2 of D_D;\nrelate d1 to d2 across R12;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:24-26: The destination association phrase must be specified for reflexive association ->R12<- between classes ->Disk<- and ->Disk<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:28: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "d1", "d2" };//$NON-NLS-1$//$NON-NLS-2$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:28-30: The destination association phrase must be specified for reflexive association ->R12<- between classes ->Disk<- and ->Disk<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:32: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
	}
	public void testRelateBinaryPhraseSubtype() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance od of D_OD;\nrelate d1 to od across R3.'is a';"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:27-32: A destination association phrase is not allowed for a supertype/subtype association", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:34: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "d1", "od" };//$NON-NLS-1$//$NON-NLS-2$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:31-36: A destination association phrase is not allowed for a supertype/subtype association", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:38: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
	}
	public void testRelateBinaryTwoSubtypes() throws RecognitionException, TokenStreamException {
		String act = "create object instance od1 of D_OND;\ncreate object instance od2 of D_OD;\nrelate od1 to od2 across R3;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:26-27: The classes ->Online Disk<- and ->Offline Disk<- are subtype classes in the association ->R3<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:29: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "od1", "od2" };//$NON-NLS-1$//$NON-NLS-2$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:30-31: The classes ->Online Disk<- and ->Offline Disk<- are subtype classes in the association ->R3<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:33: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
	}
	public void testRelateBinaryBadRelNum() throws RecognitionException, TokenStreamException {
		String act = "create object instance x of D_D;\ncreate object instance y of D_D;\nrelate x to y across R99;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:22-24: Cannot find specified association ->R99<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:26: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "x", "y" };//$NON-NLS-1$//$NON-NLS-2$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:26-28: Cannot find specified association ->R99<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:30: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
	}
	public void testRelateBinaryWrongRelNum() throws RecognitionException, TokenStreamException {
		String act = "create object instance x of D_D;\ncreate object instance y of D_OD;\nrelate x to y across R4;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:22-23: The specified association ->R4<- does not exist between classes ->Disk<- and ->Offline Disk<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:25: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "x", "y" };//$NON-NLS-1$//$NON-NLS-2$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:26-27: The specified association ->R4<- does not exist between classes ->Disk<- and ->Offline Disk<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:29: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
	}
	public void testRelateBinaryBadWithUsing() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance h of D_H;\ncreate object instance dq of D_DQ;\nrelate d1 to h across R4 using dq;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:32-33: Association ->R4<- is not linked", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:35: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "d1", "h", "dq" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:36-37: Association ->R4<- is not linked", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:39: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
	}
	public void testRelateAssocBadNoUsing() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance q of D_QP;\nrelate d1 to q across R1;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:23-24: Need USING clause for link classes in association ->R1<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:26: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "d1", "q" };//$NON-NLS-1$//$NON-NLS-2$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":3:27-28: Need USING clause for link classes in association ->R1<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 3:30: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 2, 0, 0);
	}
	public void testRelateAssocBadAone() throws RecognitionException, TokenStreamException {
		String act = "create object instance h of D_H;\ncreate object instance qp of D_QP;\ncreate object instance dq of D_DQ;\nrelate h to qp across R1 using dq;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:32-33: Class ->Permanent Home in Library<- does not participate in the link association ->R1<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:35: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "h", "qp", "dq" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:36-37: Class ->Permanent Home in Library<- does not participate in the link association ->R1<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:39: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
	}
	public void testRelateAssocBadAoth() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance h of D_H;\ncreate object instance dq of D_DQ;\nrelate d1 to h across R1 using dq;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:32-33: Class ->Permanent Home in Library<- does not participate in the link association ->R1<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:35: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "d1", "h", "dq" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:36-37: Class ->Permanent Home in Library<- does not participate in the link association ->R1<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:39: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
	}
	public void testRelateAssocBadAssr() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance qp of D_QP;\ncreate object instance h of D_H;\nrelate d1 to qp across R1 using h;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:33-33: Class ->Permanent Home in Library<- is not the link class between classes ->Disk<- and ->Qualified Process<- in link association ->R1<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:35: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "d1", "qp", "h" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:37-37: Class ->Permanent Home in Library<- is not the link class between classes ->Disk<- and ->Qualified Process<- in link association ->R1<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:39: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
	}
	public void testRelateAssocWrongOrderAssr() throws RecognitionException, TokenStreamException {
		String act = "create object instance d1 of D_D;\ncreate object instance qp of D_QP;\ncreate object instance dq of D_DQ;\nrelate d1 to dq across R1 using qp;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:33-34: Class ->Qualified Process<- is incorrectly specified in link association ->R1<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:36: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "d1", "qp", "dq" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:37-38: Class ->Qualified Process<- is incorrectly specified in link association ->R1<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:40: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
	}
	public void testRelateAssocBadReflexiveNoPhrase() throws RecognitionException, TokenStreamException {
		String act = "create object instance qp1 of D_QP;\ncreate object instance qp2 of D_QP;\ncreate object instance qpo of D_QPO;\nrelate qp1 to qp2 across R13 using qpo;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:36-38: The destination association phrase must be specified for reflexive association ->R13<- between classes ->Qualified Process<- and ->Qualified Process<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:40: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "qp1", "qp2", "qpo" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:40-42: The destination association phrase must be specified for reflexive association ->R13<- between classes ->Qualified Process<- and ->Qualified Process<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:44: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
	}
	public void testRelateAssocBadReflexiveBadPhrase() throws RecognitionException, TokenStreamException {
		String act = "create object instance qp1 of D_QP;\ncreate object instance qp2 of D_QP;\ncreate object instance qpo of D_QPO;\nrelate qp1 to qp2 across R13.'neither' using qpo;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:46-48: Class ->Qualified Process<- in reflexive association ->R13<- does not contain destination association phrase ->neither<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:50: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		String[] var_list = { "qp1", "qp2", "qpo" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(":4:50-52: Class ->Qualified Process<- in reflexive association ->R13<- does not contain destination association phrase ->neither<-", lines[0]); //$NON-NLS-1$
		assertEquals("line 4:54: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 3, 0, 0);
	}
	public void testInvalidRelSpecInChain() throws RecognitionException, TokenStreamException {
		String act = "select any h from instances of D_H; select one d related by h->D_D[R];"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(4, lines.length);
		assertEquals("line 1:68: expecting TOK_RELID, found 'R'", lines[0]);
		assertEquals(":1:69-69: Invalid association specification", lines[1]);
		assertEquals("line 1:71: unexpected token: null", lines[2]);
		assertEquals("line 1:71: expecting Semicolon, found 'null'", lines[3]);
		String[] var_list = { "h" };//$NON-NLS-1$
		OalParserTest.badRelationshipValidate(var_list, 1, 1, 0);
	}
	public void testInvalidRelSpecInRelate() throws RecognitionException, TokenStreamException {
		String act = "select any h from instances of D_H; select any d from instances of D_D; relate d to h across R;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(3, lines.length);
		assertEquals("line 1:94: expecting TOK_RELID, found 'R'", lines[0]);
		assertEquals(":1:94-94: Invalid association specification", lines[1]);
		assertEquals("line 1:96: expecting Semicolon, found 'null'", lines[2]);
		String[] var_list = { "h", "d" };//$NON-NLS-1$//$NON-NLS-2$
		OalParserTest.badRelationshipValidate(var_list, 2, 2, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(3, lines.length);
		assertEquals("line 1:98: expecting TOK_RELID, found 'R'", lines[0]);
		assertEquals(":1:98-98: Invalid association specification", lines[1]);
		assertEquals("line 1:100: expecting Semicolon, found 'null'", lines[2]);
		OalParserTest.badRelationshipValidate(var_list, 2, 2, 0);
	}
	public void testInvalidRelSpecInRelateUsing() throws RecognitionException, TokenStreamException {
		String act = "select any h from instances of D_H; select any d from instances of D_D; create object instance dq of D_DQ;\nrelate d to h across R using dq;"; //$NON-NLS-1$
		String x = OalParserTest.parseAction(act, OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(3, lines.length);
		assertEquals("line 2:22: expecting TOK_RELID, found 'R'", lines[0]);
		assertEquals(":2:30-31: Invalid association specification", lines[1]);
		assertEquals("line 2:33: expecting Semicolon, found 'null'", lines[2]);
		String[] var_list = { "h", "d", "dq" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		OalParserTest.badRelationshipValidate(var_list, 3, 2, 0);
		x = OalParserTest.parseAction(act.replaceFirst("relate", "unrelate").replaceFirst(" to ", " from "), OalParserTest.ACTIVITY_TYPE_FUNC, OalParserTest.TEST_VOID_NO_PARM); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		lines = x.split("\n");//$NON-NLS-1$
		assertEquals(3, lines.length);
		assertEquals("line 2:26: expecting TOK_RELID, found 'R'", lines[0]);
		assertEquals(":2:34-35: Invalid association specification", lines[1]);
		assertEquals("line 2:37: expecting Semicolon, found 'null'", lines[2]);
		OalParserTest.badRelationshipValidate(var_list, 3, 2, 0);
	}

}
