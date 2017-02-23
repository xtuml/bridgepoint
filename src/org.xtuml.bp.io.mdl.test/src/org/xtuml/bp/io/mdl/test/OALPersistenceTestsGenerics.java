package org.xtuml.bp.io.mdl.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.GeneralPurposeLogger;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.OALPersistenceUtil;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.text.activity.ParseAllActivitiesAction;

@RunWith(OrderedRunner.class)
public class OALPersistenceTestsGenerics extends BaseTest {
	
	@Rule public TestName name = new TestName();
	
    GeneralPurposeLogger log1;  // log for core
    GeneralPurposeLogger log2;  // log for canvas
    
	Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();
	Ooaofgraphics graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());

    private static String m_workspace_path = "";
    private static String m_logfile_path = "";
    static SystemModel_c m_system = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance());
    
    /**
     * Whether this test run is meant to produce the expected
     * results files, rather than actually perform the tests. 
     */
    private static boolean generateResults = false;

	@Before
	public void setUp() throws Exception {
		super.setUp();
        IdAssigner.setSeedOfAllInstances(name.getMethodName().hashCode(), true);
        if (m_workspace_path == null || m_workspace_path.equals(""))
        {
            m_workspace_path = System.getProperty("WORKSPACE_PATH");
        }
        if (m_logfile_path == null || m_logfile_path.equals(""))
        {
            m_logfile_path = System.getProperty("LOGFILE_PATH"); //$NON-NLS-1$
        }
        assertNotNull( m_workspace_path );
        assertNotNull( m_logfile_path );
		if ( log1 == null ) 
			log1 = new GeneralPurposeLogger();
		else
			log1.clear();
		Ooaofooa.log = log1;
        
		if ( log2 == null ) 
			log2 = new GeneralPurposeLogger();
		else
			log2.clear();
		Ooaofgraphics.log = log2;
	}
    
	@After
	public void tearDown() throws Exception {
		BaseTest.staticTearDown();
		super.tearDown();
	}

//	// As of Java7 and JUnit 4, the ordering of test functions is not guaranteed to be the order
//	// in the file.  Thus, we add this public test to enforce ordering
//	@Test
//	public void testOALPersistence() throws FileNotFoundException, CoreException {
//		dotestPersistOAL();
//		dotestOALInstancesCreatedWhileBuild();
//		dotestOALInstancesExportedProperly();
//	}
	@Test
	public void testPersistOAL() throws FileNotFoundException, CoreException {
		try {
			IdAssigner.setSeedOfAllInstances(1, true);
			final String domainName = "testOAL1";

			loadProject(domainName);
			
			Package_c dom = Package_c.getOneEP_PKGOnR1401(m_sys,
					new ClassQueryInterface_c() {
						public boolean evaluate(Object candidate) {
							return ((Package_c) candidate).getName().equals(
									domainName);
						}
					});

			// create all necessary component files
			dom.getPersistableComponent().refresh();
			dom.getPersistableComponent().persistSelfAndChildren();

			// add the domain to the selection
			Selection.getInstance().clear();
			Selection.getInstance().addToSelection(dom);

			// Parse All
			ParseAllActivitiesAction paaa = new ParseAllActivitiesAction();
			paaa.run(null);

			OALPersistenceUtil.persistOAL((Ooaofooa) dom.getModelRoot());

			// Change default for the parse on resource change preference to
			// "always"
			IPreferenceStore store = CorePlugin.getDefault()
					.getPreferenceStore();
			store.setValue(BridgePointPreferencesStore.EXPORT_OAL, "always"); //$NON-NLS-1$
			store.setValue(BridgePointPreferencesStore.EXPORT_GRAPHICS, "always"); //$NON-NLS-1$
			store.setValue(BridgePointPreferencesStore.SHOW_EVENT_PARAMETERS, false); 
			
			String actualResultPath = m_workspace_path + "actual_results/";
			BaseTest.ensureFolderExists(actualResultPath);
			// export with persisted OAL
			TestingUtilities.exportModelUsingWizard(m_workspace_path
					+ (generateResults ? TestingUtilities.getExpectedResultsPath()
							: "actual_results/") + domainName
							+ "Generics" + "." + Ooaofooa.MODELS_EXT, true);

			if (!generateResults) { // make sure the exported model has OAL
				TestingUtilities.fileContentsCompare(m_workspace_path
						+ TestingUtilities.getExpectedResultsPath() + domainName + "Generics"
						+ "." + Ooaofooa.MODELS_EXT, m_workspace_path
						+ "actual_results/" + domainName + "Generics" + "."
						+ Ooaofooa.MODELS_EXT);
			}
		}
         catch (CoreException f) {
            fail( f.toString() );
        }
        }
   @Test
	public void testOALInstancesCreatedWhileBuild() throws FileNotFoundException, CoreException {
		try {
			IdAssigner.setSeedOfAllInstances(2, true);
			String domainName = "testOAL1";
			loadProject(domainName);
			project.build(IncrementalProjectBuilder.FULL_BUILD, null);
			TestingUtilities.allowJobCompletion();

			TestUtil.sleepWithDispatchOfEvents(100);
			TestingUtilities.exportModelUsingWizard(m_workspace_path
					+ (generateResults ? TestingUtilities.getExpectedResultsPath()
					: "actual_results/") + domainName + "."
					+ Ooaofooa.MODELS_EXT, true);

			if (!generateResults) { // make sure the exported model has OAL
				TestingUtilities.fileContentsCompare(m_workspace_path
						+ TestingUtilities.getExpectedResultsPath() + domainName + "."
						+ Ooaofooa.MODELS_EXT, m_workspace_path
						+ "actual_results/" + domainName + "."
						+ Ooaofooa.MODELS_EXT);
			}
		} catch (CoreException f) {
			fail(f.toString());
		}
    }
	
}
