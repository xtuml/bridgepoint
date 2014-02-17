//========================================================================
//
//File:      $RCSfile: TestStructuredDataType_Generics.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:00:38 $
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

import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.InstanceHandle_c;
import com.mentor.nucleus.bp.core.InstanceSet_c;
import com.mentor.nucleus.bp.core.LiteralEnumerator_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.TransientVar_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class TestStructuredDataType_Generics extends TestCase {

    // This test class just tests the *negative* cases where we expect to find
    // syntax errors.  The positive cases where no errors should be found it
    // covered by the "sdt_test.xtuml" model.  It fully exercises all valid
    // uses of SDTs in BP.  It is ran as part of the "Parse All" unit tests
    // defined in ParseAllInDomain.java.

    protected void setUp() throws Exception {
        super.setUp();
    }

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

    // Test some invalid assignments using SDTs
    public void testAssignTypeMismatchBool2SDT() throws RecognitionException,
            TokenStreamException {
        UUID personId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Person");//$NON-NLS-1$
        UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = t.sdt_identity;\ny = true;\nx = y;\n ",
                personId, boolId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchEnum2SDT() throws RecognitionException,
            TokenStreamException {
        UUID personId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Person");//$NON-NLS-1$
        UUID fruitId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "fruit");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = t.sdt_identity;\ny = fruit::apple;\nx = y;\n ",
                personId, fruitId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchInt2SDT() throws RecognitionException,
            TokenStreamException {
        UUID personId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Person");//$NON-NLS-1$
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = t.sdt_identity;\ny = 12;\nx = y;\n ",
                personId, intId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchReal2SDT() throws RecognitionException,
            TokenStreamException {
        UUID personId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Person");//$NON-NLS-1$
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = t.sdt_identity;\ny = 2.2;\nx = y;\n ",
                personId, realId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchString2SDT() throws RecognitionException,
            TokenStreamException {
        UUID personId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Person");//$NON-NLS-1$
        UUID stringId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "string");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = t.sdt_identity;\ny = \"test\";\nx = y;\n ",
                personId, stringId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchString2UDT() throws RecognitionException,
            TokenStreamException {
        UUID personId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Person");//$NON-NLS-1$
        UUID udt_booleanId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "udt_boolean");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = t.sdt_identity;\ny = t.u_bool;\nx = y;\n ",
                personId, udt_booleanId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchSDT2Bool() throws RecognitionException,
            TokenStreamException {
        UUID boolId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "boolean");//$NON-NLS-1$
        UUID animalId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Animal");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = false;\ny = t.sdt_pet1;\nx = y;\n ",
                boolId, animalId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchSDT2Enum() throws RecognitionException,
            TokenStreamException {
        UUID fruitId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "fruit");//$NON-NLS-1$
        UUID animalId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Animal");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = fruit::orange;\ny = t.sdt_pet1;\nx = y;\n ",
                fruitId, animalId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchSDT2Int() throws RecognitionException,
            TokenStreamException {
        UUID intId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "integer");//$NON-NLS-1$
        UUID animalId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Animal");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = 1;\ny = t.sdt_pet1;\nx = y;\n ",
                intId, animalId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchSDT2Real() throws RecognitionException,
            TokenStreamException {
        UUID realId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "real");//$NON-NLS-1$
        UUID animalId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Animal");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = 12.2;\ny = t.sdt_pet1;\nx = y;\n ",
                realId, animalId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchSDT2String() throws RecognitionException,
            TokenStreamException {
        UUID stringId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "string");//$NON-NLS-1$
        UUID animalId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Animal");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = \"foo\";\ny = t.sdt_pet1;\nx = y;\n ",
                stringId, animalId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchSDT2UDT() throws RecognitionException,
            TokenStreamException {
        UUID udt_booleanId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "udt_boolean");//$NON-NLS-1$
        UUID animalId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Animal");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = t.u_bool;\ny = t.sdt_pet1;\nx = y;\n ",
                udt_booleanId, animalId, 2, 1, 0); //$NON-NLS-1$
    }
    public void testAssignTypeMismatchSDT_A2SDT_B() throws RecognitionException,
            TokenStreamException {
        UUID personId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Person");//$NON-NLS-1$
        UUID animalId = BaseTest.getTypeID_Generic(OalParserTest_Generics.modelRoot, "Animal");//$NON-NLS-1$

        assignTypeMismatchTest(
                "select any t from instances of D_TST;\nx = t.sdt_identity;\n y = t.sdt_pet1;\nx = y;\n ",
                personId, animalId, 2, 1, 0); //$NON-NLS-1$
    }

    // Test invalid assignment of an SDT member
	public void testAssignBool2SDTstring() throws RecognitionException, TokenStreamException {
        String act = "select any t from instances of D_TST;\nt.sdt_identity.name = false;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(2, lines.length);
		assertEquals(":2:23-27: Variable ->name<- already exists as a different type", lines[0]);//$NON-NLS-1$
		assertEquals("line 2:29: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
		OalParserTest_Generics.validateBlkStmtVal( 1, 1, 3 );
		Variable_c [] vars = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
		assertEquals( 1, vars.length );
		OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
	}
    public void testAssignEnum2SDTstring() throws RecognitionException, TokenStreamException {
        String act = "select any t from instances of D_TST;\nt.sdt_identity.name = fruit::apple;"; //$NON-NLS-1$
        String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(2, lines.length);
        assertEquals(":2:30-34: Variable ->name<- already exists as a different type", lines[0]);//$NON-NLS-1$
        assertEquals("line 2:36: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
        OalParserTest_Generics.validateBlkStmtVal( 1, 1, 3 );
        Variable_c [] vars = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals( 1, vars.length );
        OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
    }
    public void testAssignInt2SDTstring() throws RecognitionException, TokenStreamException {
        String act = "select any t from instances of D_TST;\nt.sdt_identity.name = 1;"; //$NON-NLS-1$
        String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(2, lines.length);
        assertEquals(":2:23-23: Variable ->name<- already exists as a different type", lines[0]);//$NON-NLS-1$
        assertEquals("line 2:25: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
        OalParserTest_Generics.validateBlkStmtVal( 1, 1, 3 );
        Variable_c [] vars = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals( 1, vars.length );
        OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
    }
    public void testAssignReal2SDTstring() throws RecognitionException, TokenStreamException {
        String act = "select any t from instances of D_TST;\nt.sdt_identity.name = 2.0;"; //$NON-NLS-1$
        String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(2, lines.length);
        assertEquals(":2:23-25: Variable ->name<- already exists as a different type", lines[0]);//$NON-NLS-1$
        assertEquals("line 2:27: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
        OalParserTest_Generics.validateBlkStmtVal( 1, 1, 3 );
        Variable_c [] vars = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals( 1, vars.length );
        OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
    }
    public void testAssignUDT2SDTstring() throws RecognitionException, TokenStreamException {
        String act = "select any t from instances of D_TST;\nt.sdt_identity.name = t.u_bool;"; //$NON-NLS-1$
        String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(2, lines.length);
        assertEquals(":2:25-30: Variable ->name<- already exists as a different type", lines[0]);//$NON-NLS-1$
        assertEquals("line 2:32: expecting Semicolon, found 'null'", lines[1]);//$NON-NLS-1$
        OalParserTest_Generics.validateBlkStmtVal( 1, 1, 4 );
        Variable_c [] vars = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals( 1, vars.length );
        OalParserTest_Generics.clearActionData(OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM);
    }

    // Test comparison operations on SDTs
    public void testRelExprSDTNeOtherSDT() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics.parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity != t.sdt_pet1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:25-32: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:34: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTEqOtherSDT() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics.parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity == t.sdt_pet1;",
                        OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:25-32: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:34: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTNeBool() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics.parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity != false;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:23-27: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:29: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTNeEnum() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity != fruit::orange;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:30-35: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:37: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTNeInt() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity != 1;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:23-23: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:25: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTNeReal() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity != 42.2;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:23-26: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:28: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTNeString() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity != \"foo\";", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:23-27: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:29: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTNeUDT() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity != t.u_bool;", OalParserTest_Generics.ACTIVITY_TYPE_FUNC, OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:25-30: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:32: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTEqBool() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity == true;",
                        OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
                        OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:23-26: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:28: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTEqEnum() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity == fruit::apple;",
                        OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
                        OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:30-34: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:36: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTEqInt() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics.parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity == 42;",
                        OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
                        OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:23-24: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:26: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTEqReal() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity == 42.3;",
                        OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
                        OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:23-26: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:28: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTEqString() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity == \"bar\";",
                        OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
                        OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:23-27: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:29: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }
    public void testRelExprSDTEqUDT() throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics
                .parseAction(
                        "select any t from instances of D_TST;\nx = t.sdt_identity == t.u_bool;",
                        OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
                        OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(
                ":2:25-30: Incompatible operands for relational expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 2:32: expecting Semicolon, found 'null'", lines[1]); //$NON-NLS-1$
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(2, t.length);
        Value_c[] val = Value_c.ValueInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, val.length);
        LiteralEnumerator_c[] len = LiteralEnumerator_c
                .LiteralEnumeratorInstances(OalParserTest_Generics.modelRoot);
        assertEquals(0, len.length);
    }

    // Functions that support the tests above
    public void assignTypeMismatchTest(String stmts, UUID dt1, UUID dt2,
            int numTV, int numIRV, int numIRS) throws RecognitionException,
            TokenStreamException {
        String x = OalParserTest_Generics.parseAction(stmts,
                OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
                OalParserTest_Generics.TEST_VOID_NO_PARM); //$NON-NLS-1$
        String lines[] = x.split("\n"); //$NON-NLS-1$
        assertEquals(
                ":4:5-5: Variable ->x<- already exists as a different type", lines[0]); //$NON-NLS-1$
        Block_c[] blk = Block_c.BlockInstances(OalParserTest_Generics.modelRoot);
        assertEquals(1, blk.length);
        Statement_c[] st = Statement_c
                .StatementInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, st.length);
        Variable_c[] t = Variable_c.VariableInstances(OalParserTest_Generics.modelRoot);
        assertEquals(3, t.length);
        assertEquals("x", t[1].getName()); //$NON-NLS-1$
        assertEquals("y", t[2].getName()); //$NON-NLS-1$

        InstanceHandle_c[] iv = InstanceHandle_c.InstanceHandleInstances(OalParserTest_Generics.modelRoot);
        assertEquals(numIRV, iv.length);
        InstanceSet_c[] isv = InstanceSet_c
                .InstanceSetInstances(OalParserTest_Generics.modelRoot);
        assertEquals(numIRS, isv.length);
    }


}
