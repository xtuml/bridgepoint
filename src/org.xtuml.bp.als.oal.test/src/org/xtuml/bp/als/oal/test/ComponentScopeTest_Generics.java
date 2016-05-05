package org.xtuml.bp.als.oal.test;

import java.io.StringReader;
import java.util.UUID;

import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.als.oal.OalLexer;
import org.xtuml.bp.als.oal.OalParser;
import org.xtuml.bp.als.oal.Oal_validate;
import org.xtuml.bp.core.Block_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Oalconstants_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

@RunWith(OrderedRunner.class)
public class ComponentScopeTest_Generics extends BaseTest {

	public static boolean configured = false;
    private static String m_workspace_path = ""; //$NON-NLS-1$
    private static String m_comp_pkg_name = "ComponentSyntaxTest"; //$NON-NLS-1$
    public ComponentScopeTest_Generics() {
    	super("org.xtuml.bp.als.oal.test", null);
    }
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		if (configured) {
			return;
		}
		configured = true;
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

		TestingUtilities.importTestingProjectIntoWorkspace(m_comp_pkg_name);
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				getProjectHandle(m_comp_pkg_name), m_comp_pkg_name, true));
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(
          BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING, true);
	}

	@Test
	public void testInScopeSingletonClassReference() {
		String x = parseAction("create object instance gsc of GSC;",
                                         "Good Syntax", "Good Singleton Class");
		assertEquals("Unexpected error:", "", x);
	}
	@Test
	public void testInScopeDuplicateClassReference() {
		String x = parseAction("create object instance gsc of GDC;",
                                         "Good Syntax", "Good Singleton Class");
		assertEquals("Unexpected error:", "", x);
	}
	@Test
	public void testInScopeSingletonAssociationReference() {
		String x = parseAction("select one gdc related by self->GDC[R1];",
                                         "Good Syntax", "Good Singleton Class");
		assertEquals("Unexpected error:", "", x);
	}
	@Test
	public void testInScopeGoodDuplicateAssociationReference() {
		String x = parseAction("select one gdc related by self->GDC[R2];",
                                         "Good Syntax", "Good Singleton Class");
		assertEquals("Unexpected error:", "", x);
	}
	@Test
	public void testOutOfScopeSingletonClassReference() {
		String x = parseAction("create object instance gsc of GSC;",
                                         "Component Scoping Test", "Bad Duplicate Class I");
		assertEquals("Unexpected error:", ":1:31-33: Cannot find specified class key letters ->GSC<-.\nline 1:35: expecting Semicolon, found 'null'\n", x);
	}
	@Test
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

		parser.m_oal_context = new Oal_validate(comp);
		class ModelClassNameTest implements ClassQueryInterface_c {
			public boolean evaluate(Object candidate) {
				ModelClass_c selected = (ModelClass_c) candidate;
				return selected.getName().equals(clazz);
			}
		}
		ModelClass_c modelClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR1405(m_sys)),
				new ModelClassNameTest());
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
