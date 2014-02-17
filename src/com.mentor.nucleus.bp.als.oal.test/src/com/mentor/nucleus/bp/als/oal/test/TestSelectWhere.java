package com.mentor.nucleus.bp.als.oal.test;

//=====================================================================
//
//File:      $RCSfile: TestSelectWhere.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:00:33 $
//
//(c) Copyright 2012-2014 Mentor Graphics Corporation All rights reserved.
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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.IPreferenceStore;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

import com.mentor.nucleus.bp.als.oal.OalLexer;
import com.mentor.nucleus.bp.als.oal.OalParser;
import com.mentor.nucleus.bp.als.oal.Oal_validate;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.BridgeBody_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DerivedAttributeBody_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Oalconstants_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationBody_c;
import com.mentor.nucleus.bp.core.StateActionBody_c;
import com.mentor.nucleus.bp.core.Statement_c;
import com.mentor.nucleus.bp.core.TransitionActionBody_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.util.ContainerUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class TestSelectWhere extends BaseTest {

    private static boolean m_requiresClear = false;
    private static String m_projectName = "select_where_test";
    private static int m_funcType = 0;
    private static int m_funcNum = 0;
    private static boolean firstSetup = false;
    static public Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();

    public TestSelectWhere(String arg0) {
        super(m_projectName, arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        if (!firstSetup) {
            String modelPath = m_workspace_path + "models/select_where_test.xtuml";
            String pkgName = "CP";
            project = ResourcesPlugin.getWorkspace().getRoot().getProject(m_projectName);

            m_sys = getSystemModel(project.getName());

            TestingUtilities.importModelUsingWizardConvertToGenerics(m_sys,
                    modelPath, false);

            String modelRootId = Ooaofooa.createModelRootId(project, pkgName, true);
            modelRoot = Ooaofooa.getInstance(modelRootId, true);

            populateFunctionInstances();
        }

        firstSetup = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        try {
            super.tearDown();
            TestSelectWhere.tearDownActionData();
        } catch (RecognitionException re) {
            // do nothing
        } catch (TokenStreamException te) {
            // do nothing
        }
    }

    public void testSelectAnyFromWhereSelectedIOp1() throws RecognitionException, TokenStreamException {
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_OPERATIONS_IN_WHERE, false);

        String act = "select any My_Class from instances of The_Class where (selected.get_A() == 0);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:71-71: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:77-77: Expression within Select Where clause does not yield boolean result", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:79: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 0);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);        
    }

    public void testSelectAnyFromWhereSelectedIOp2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (0 == selected.get_A());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:76-76: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:77-77: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:79: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedIOp3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (selected.getTrue());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:73-73: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:75: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:76: unexpected token: null", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedCOp1() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (The_Class::class_based_op() == true);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:82-82: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:87: expecting TOK_RPAREN, found 'true'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:91: expecting Semicolon, found ')'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedCOp2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == The_Class::class_based_op());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:90-90: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:91-91: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:93: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedCOp3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (The_Class::class_based_op());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:82-82: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:84: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:85: unexpected token: null", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedMDA1() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (selected.Attr_B == 10);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:65-70: Accessing a derived base attribute value is not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:77-77: Expression within Select Where clause does not yield boolean result", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:79: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedMDA2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (10 == selected.Attr_B);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:71-76: Accessing a derived base attribute value is not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:77-77: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:79: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 2);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedMDA3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (selected.Attr_true);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:65-73: Accessing a derived base attribute value is not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:75: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:76: unexpected token: null", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 2);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedDomFunc1() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (::gfunc() == true);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:64-64: Function calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:69: expecting TOK_RPAREN, found 'true'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:73: expecting Semicolon, found ')'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedDomFunc2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == ::gfunc());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:72-72: Function calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:73-73: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:75: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedDomFunc3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (::gfunc());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:64-64: Function calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:66: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:67: unexpected token: null", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedBrg1() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (TEST::getTrue() == true);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:70-70: Bridge calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:75: expecting TOK_RPAREN, found 'true'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:79: expecting Semicolon, found ')'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedBrg2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == TEST::getTrue());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:78-78: Bridge calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:79-79: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:81: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }
    
    public void testSelectAnyFromWhereSelectedBrg3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (TEST::getTrue());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:70-70: Bridge calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:72: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:73: unexpected token: null", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedMsgOp1() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (my_if::if_op() == true);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:69-69: Message calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:74: expecting TOK_RPAREN, found 'true'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:78: expecting Semicolon, found ')'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);        
    }
    
    public void testSelectAnyFromWhereSelectedMsgOp2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == my_if::if_op());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:77-77: Message calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:78-78: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:80: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);                
    }

    public void testSelectAnyFromWhereSelectedMsgOp3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (my_if::if_op());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:69-69: Message calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:71: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:72: unexpected token: null", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);                
    }

    public void testSelectAnyFromWhereSelectedMsgSig() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (my_if::if_sig());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:63-68: Signal ->if_sig<- cannot be used in an expression", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:72: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:73: unexpected token: null", lines[2]); //$NON-NLS-1$
        validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);        
    }

    // Now we test the above selects with the preference set to allow the behavior
    // and expect to not get syntax errors reported
    public void testSelectAnyFromWhereSelectedIOp1Allowed() throws RecognitionException, TokenStreamException {
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_OPERATIONS_IN_WHERE, true);

        /* NOTE: See this file's history for why some tests are omitted.*/
    }

    public void testSelectAnyFromWhereSelectedIOp3Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (selected.getTrue());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSelectAnyFromWhereSelectedCOp1Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (The_Class::class_based_op() == true);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSelectAnyFromWhereSelectedCOp2Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == The_Class::class_based_op());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSelectAnyFromWhereSelectedCOp3Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (The_Class::class_based_op());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSelectAnyFromWhereSelectedMDA3Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (selected.Attr_true);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSelectAnyFromWhereSelectedDomFunc3Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (::gfunc());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSelectAnyFromWhereSelectedBrg1Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (TEST::getTrue() == true);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSelectAnyFromWhereSelectedBrg2Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == TEST::getTrue());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }
    
    public void testSelectAnyFromWhereSelectedBrg3Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (TEST::getTrue());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSelectAnyFromWhereSelectedMsgOp1Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (my_if::if_op() == true);"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSelectAnyFromWhereSelectedMsgOp2Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == my_if::if_op());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }

    public void testSelectAnyFromWhereSelectedMsgOp3Allowed() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (my_if::if_op());"; //$NON-NLS-1$
        String x = parseAction(act, ACTIVITY_TYPE_FUNC, TEST_OAL);
        assertEquals("", x); //$NON-NLS-1$
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    //
    //  Support data and functions for the tests follow
    //
    ////////////////////////////////////////////////////////////////////////////
    static final int ACTIVITY_TYPE_FUNC = 0;

    static public String parseAction(String stmts, int funcType, int funcNum) {
        m_requiresClear = true;
        m_funcType = funcType;
        m_funcNum = funcNum;
        OalLexer lexer = new OalLexer(new StringReader(stmts));
        OalParser parser = new OalParser(modelRoot, lexer);
        try {
            switch (funcType) {
            case ACTIVITY_TYPE_FUNC:
                parser.m_oal_context = new Oal_validate(ContainerUtil
                        .getContainer(m_testFunc[funcNum]));
                m_testFunc[funcNum].setAction_semantics_internal(stmts);
                parser.action(m_testFunc[funcNum].getSync_id(),
                        Oalconstants_c.FUNCTION_TYPE);
                break;
            default:
                fail("parseAction: Unknown Activity type constant");
            }
        } catch (TokenStreamException e) {
            Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
            if (e instanceof TokenStreamRecognitionException) {
                TokenStreamRecognitionException tsre = (TokenStreamRecognitionException) e;
                parser.reportError(tsre.recog);
            } else {
                fail("Token stream exception in parser");
            }
        } catch (RecognitionException e) {
            Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
            parser.reportError(e);
        } catch (InterruptedException ie) {
        }
        return parser.m_output;
    }

    static public void clearActionData(int funcType, int funcNum)
            throws RecognitionException, TokenStreamException {
        switch (funcType) {
        case ACTIVITY_TYPE_FUNC:
            Body_c actact = Body_c.getOneACT_ACTOnR698(FunctionBody_c
                    .getOneACT_FNBOnR695(m_testFunc[funcNum]));
            if (actact != null) {
                actact.Clear_blocks();
            }
            break;
        default:
            fail("clearAction: Unknown Activity type constant");
        }
        m_requiresClear = false;
    }

    static public void tearDownActionData() throws RecognitionException,
            TokenStreamException {
        if (m_requiresClear) {
            clearActionData(m_funcType, m_funcNum);
        }
    }

    public void validateBlkStmtVal(int numBlock, int numStmt, int numVal) {
        FunctionBody_c[] fb = FunctionBody_c.FunctionBodyInstances(modelRoot);
        BridgeBody_c[] bb = BridgeBody_c.BridgeBodyInstances(modelRoot);
        OperationBody_c[] ob = OperationBody_c
                .OperationBodyInstances(modelRoot);
        StateActionBody_c[] sab = StateActionBody_c
                .StateActionBodyInstances(modelRoot);
        DerivedAttributeBody_c[] dab = DerivedAttributeBody_c
                .DerivedAttributeBodyInstances(modelRoot);
        TransitionActionBody_c[] tab = TransitionActionBody_c
                .TransitionActionBodyInstances(modelRoot);
        Body_c b[] = Body_c.BodyInstances(modelRoot);
        assertEquals(b.length, fb.length + bb.length + ob.length + sab.length
                + dab.length + tab.length);
        Block_c[] blk = Block_c.BlockInstances(modelRoot);
        assertEquals(numBlock, blk.length);
        Body_c[] bods = Body_c.BodyInstances(modelRoot);
        for (int i = 0; i < bods.length; ++i)
            assertEquals(IdAssigner.NULL_UUID, bods[i].getCurrentscope_id());
        Statement_c[] st = Statement_c.StatementInstances(modelRoot);
        assertEquals(numStmt, st.length);
        Value_c[] val = Value_c.ValueInstances(modelRoot);
        assertEquals(numVal, val.length);
    }

    // Accessor IDs for domain functions in the model
    static public final int DBA_PARAM_FUNC = 0;
    static public final int GFUNC = 1;
    static public final int TEST_OAL = 2;
    static private String funcs[] = { "dba_param_func", //$NON-NLS-1$
            "gfunc", //$NON-NLS-1$
            "test_oal", //$NON-NLS-1$
            };
    public static Function_c[] m_testFunc = new Function_c[funcs.length];

    private void populateFunctionInstances() {
        class Function_test1_c implements ClassQueryInterface_c {
            Function_test1_c(String p) {
                m_p = p;
            }

            private String m_p;

            public boolean evaluate(Object inst) {
                Function_c selected = (Function_c) inst;
                return selected.getName().equals(m_p);
            }
        }
        for (int i = 0; i < funcs.length; ++i) {
            Function_c testFunc = Function_c.FunctionInstance(modelRoot,
                    new Function_test1_c(funcs[i]));
            if (testFunc != null) {
                if (m_testFunc[i] == null)
                    m_testFunc[i] = testFunc;
            } else
                fail("Missing function " + funcs[i]);//$NON-NLS-1$
        }
    }

}
