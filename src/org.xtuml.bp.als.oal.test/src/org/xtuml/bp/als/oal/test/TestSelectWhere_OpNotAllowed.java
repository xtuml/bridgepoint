package org.xtuml.bp.als.oal.test;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Variable_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.TestingUtilities;

import antlr.RecognitionException;
import antlr.TokenStreamException;

public class TestSelectWhere_OpNotAllowed extends BaseTest {

    private static String m_projectName = "select_where_test";
    private static boolean firstSetup = false;
    static public Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();

    public TestSelectWhere_OpNotAllowed(String arg0) {
        super(null, arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        if (!firstSetup) {
            String pkgName = "CP";
            
            TestingUtilities.importTestingProjectIntoWorkspace(m_projectName);
            
            project = ResourcesPlugin.getWorkspace().getRoot().getProject(m_projectName);

            m_sys = getSystemModel(project.getName());

            String modelRootId = Ooaofooa.createModelRootId(project, pkgName, true);
            modelRoot = Ooaofooa.getInstance(modelRootId, true);
            
            TestSelectWhere_Generics.populateFunctionInstances();   
            
            IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
            store.setValue(BridgePointPreferencesStore.ALLOW_OPERATIONS_IN_WHERE, false);
            
        }
        
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_INTERFACE_NAME_IN_IC_MESSAGE, true);

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
            TestSelectWhere_Generics.tearDownActionData();
            IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
            store.setValue(BridgePointPreferencesStore.ALLOW_INTERFACE_NAME_IN_IC_MESSAGE, false);
        } catch (RecognitionException re) {
            // do nothing
        } catch (TokenStreamException te) {
            // do nothing
        }
    }

    public void testSelectAnyFromWhereSelectedCOp1() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (The_Class::class_based_op() == true);"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:82-82: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:87: expecting TOK_RPAREN, found 'true'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:91: expecting Semicolon, found ')'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedCOp2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == The_Class::class_based_op());"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:90-90: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:91-91: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:93: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedCOp3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (The_Class::class_based_op());"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:82-82: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:84: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:85: unexpected token: null", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedBrg1() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (TEST::getTrue() == true);"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:70-70: Bridge calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:75: expecting TOK_RPAREN, found 'true'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:79: expecting Semicolon, found ')'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedBrg2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == TEST::getTrue());"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:78-78: Bridge calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:79-79: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:81: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }
    
    public void testSelectAnyFromWhereSelectedBrg3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (TEST::getTrue());"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:70-70: Bridge calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:72: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:73: unexpected token: null", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedIOp1() throws RecognitionException, TokenStreamException {
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_OPERATIONS_IN_WHERE, false);

        String act = "select any My_Class from instances of The_Class where (selected.get_A() == 0);"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:71-71: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:77-77: Expression within Select Where clause does not yield boolean result", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:79: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 0);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedIOp2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (0 == selected.get_A());"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:76-76: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:77-77: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:79: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedIOp3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (selected.getTrue());"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:73-73: Operation calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:75: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:76: unexpected token: null", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedMDA1() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (selected.Attr_B == 10);"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:65-70: Accessing a derived base attribute value is not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:77-77: Expression within Select Where clause does not yield boolean result", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:79: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedMDA2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (10 == selected.Attr_B);"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:71-76: Accessing a derived base attribute value is not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:77-77: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:79: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 2);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedMDA3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (selected.Attr_true);"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:65-73: Accessing a derived base attribute value is not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:75: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:76: unexpected token: null", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 2);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedMsgOp1() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (my_if::if_op() == true);"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:69-69: Message calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:74: expecting TOK_RPAREN, found 'true'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:78: expecting Semicolon, found ')'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);        
    }

    public void testSelectAnyFromWhereSelectedMsgOp2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == my_if::if_op());"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:77-77: Message calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:78-78: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:80: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);                
    }

    public void testSelectAnyFromWhereSelectedMsgOp3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (my_if::if_op());"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:69-69: Message calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:71: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:72: unexpected token: null", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);                
    }

    public void testSelectAnyFromWhereSelectedDomFunc1() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (::gfunc() == true);"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:64-64: Function calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:69: expecting TOK_RPAREN, found 'true'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:73: expecting Semicolon, found ')'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedDomFunc2() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (true == ::gfunc());"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:72-72: Function calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals(":1:73-73: Right hand operand not found for relational expression", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:75: expecting TOK_RPAREN, found 'null'", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }

    public void testSelectAnyFromWhereSelectedDomFunc3() throws RecognitionException, TokenStreamException {
        String act = "select any My_Class from instances of The_Class where (::gfunc());"; //$NON-NLS-1$
        String x = TestSelectWhere_Generics.parseAction(act, TestSelectWhere_Generics.ACTIVITY_TYPE_FUNC, TestSelectWhere_Generics.TEST_OAL);
        String lines[] = x.split("\n");//$NON-NLS-1$
        assertEquals(":1:64-64: Function calls are not allowed inside a where condition.", lines[0]); //$NON-NLS-1$
        assertEquals("line 1:66: expecting TOK_RPAREN, found ';'", lines[1]); //$NON-NLS-1$
        assertEquals("line 1:67: unexpected token: null", lines[2]); //$NON-NLS-1$
        TestSelectWhere_Generics.validateBlkStmtVal(1, 0, 1);
        Variable_c[] vars = Variable_c.VariableInstances(modelRoot);
        assertEquals(0, vars.length);
    }
}
