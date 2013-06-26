package com.mentor.nucleus.bp.als.oal.test;

import java.io.StringReader;
import java.util.UUID;

import org.eclipse.jface.preference.IPreferenceStore;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

import com.mentor.nucleus.bp.als.oal.OalLexer;
import com.mentor.nucleus.bp.als.oal.OalParser;
import com.mentor.nucleus.bp.als.oal.Oal_validate;
import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Oalconstants_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class ComponentScopeTest extends BaseTest {

    private static String m_workspace_path = ""; //$NON-NLS-1$
    private static String m_comp_pkg_name = "ComponentSyntaxTest"; //$NON-NLS-1$
    public ComponentScopeTest() {
    	super("Models", null);
    }
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

        if (m_workspace_path.equals(""))//$NON-NLS-1$
        {
          m_workspace_path = System.getProperty("WORKSPACE_PATH");//$NON-NLS-1$
        }
        assertNotNull( m_workspace_path );
        if (m_logfile_path == null || m_logfile_path.equals(""))
        {
          m_logfile_path = System.getProperty("LOGFILE_PATH");
        }
        assertNotNull( m_logfile_path );

        ensureAvailableAndLoaded("Models", m_comp_pkg_name, false, false, "Component Package");
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(
          BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING, true);
	}

	public void testInScopeSingletonClassReference() {
		String x = parseAction("create object instance gsc of GSC;",
                                         "Good Syntax", "Good Singleton Class");
		assertEquals("Unexpected error:", "", x);
	}
	public void testInScopeDuplicateClassReference() {
		String x = parseAction("create object instance gsc of GDC;",
                                         "Good Syntax", "Good Singleton Class");
		assertEquals("Unexpected error:", "", x);
	}
	public void testInScopeSingletonAssociationReference() {
		String x = parseAction("select one gdc related by self->GDC[R1];",
                                         "Good Syntax", "Good Singleton Class");
		assertEquals("Unexpected error:", "", x);
	}
	public void testInScopeGoodDuplicateAssociationReference() {
		String x = parseAction("select one gdc related by self->GDC[R2];",
                                         "Good Syntax", "Good Singleton Class");
		assertEquals("Unexpected error:", "", x);
	}
	public void testOutOfScopeSingletonClassReference() {
		String x = parseAction("create object instance gsc of GSC;",
                                         "Component Scoping Test", "Bad Duplicate Class I");
		assertEquals("Unexpected error:", ":1:31-33: Cannot find specified class key letters ->GSC<-.\nline 1:35: expecting Semicolon, found 'null'\n", x);
	}
	public void testInScopeBadDuplicateAssociationReference() {
		String x = parseAction("select one gdc related by self->GDC[R3];",
                                         "Component Scoping Test", "Bad Duplicate Class I");
		assertEquals("Unexpected error:", ":1:37-38: More than one association with number ->R3<- You will need to eventually clear this up\n", x);
	}

	private String parseAction(String stmts, final String compName,
                                                           final String clazz) {
		OalLexer lexer = new OalLexer(new StringReader(stmts));
		OalParser parser = new OalParser(modelRoot, lexer);
		UUID actID = Gd_c.Null_unique_id();
		class ComponentNameTest implements ClassQueryInterface_c {
			public boolean evaluate(Object candidate) {
				Component_c selected = (Component_c) candidate;
				return selected.getName().equals(compName);
			}
		}
		Component_c comp = Component_c.ComponentInstance(modelRoot,
                                                       new ComponentNameTest());
		assertTrue("Test component not found: " + compName, comp != null);
		Domain_c dom = Domain_c.getOneS_DOMOnR4204(
				                DomainAsComponent_c.getManyCN_DCsOnR4204(comp));
		parser.m_oal_context = new Oal_validate(dom);
		class ModelClassNameTest implements ClassQueryInterface_c {
			public boolean evaluate(Object candidate) {
				ModelClass_c selected = (ModelClass_c) candidate;
				return selected.getName().equals(clazz);
			}
		}
		ModelClass_c modelClass = ModelClass_c.getOneO_OBJOnR2(
				    Subsystem_c.getOneS_SSOnR1(dom), new ModelClassNameTest());
		assertTrue("Test class not found: " + clazz, modelClass != null);
	    Operation_c op = Operation_c.getOneO_TFROnR115(modelClass);
		assertTrue("Test operation not found", op != null);
	    op.setAction_semantics_internal(stmts);
		actID = op.getTfr_id();
		try {
			parser.action(actID, Oalconstants_c.OPERATION_TYPE);

		}
		catch (TokenStreamException e)
		{
		  Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
		  if ( e instanceof TokenStreamRecognitionException )
		  {
			  TokenStreamRecognitionException tsre = (TokenStreamRecognitionException)e;
			  parser.reportError(tsre.recog);
		  }
		  else
		  {
			  fail("Token stream exception in parser");
		  }
		}
		catch (RecognitionException e)
		{
			Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
			parser.reportError(e);
		}
		catch (InterruptedException ie){
		}
		return parser.m_output;
	}

}
