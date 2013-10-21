package com.mentor.nucleus.bp.io.mdl.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.OALPersistenceUtil;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.GeneralPurposeLogger;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.text.activity.ParseAllActivitiesAction;

public class OALPersistenceTestsGenerics extends BaseTest {
	
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

	protected void setUp() throws Exception {
		super.setUp();
        IdAssigner.setSeedOfAllInstances(getName().hashCode(), true);
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
    
	protected void tearDown() throws Exception {
		BaseTest.staticTearDown();
		super.tearDown();
	}

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
			store.setValue(BridgePointPreferencesStore.SHOW_EVENT_PARAMETERS, false); 
			// export with persisted OAL
			TestingUtilities.exportModelUsingWizard(m_workspace_path
					+ (generateResults ? "expected_results"
							: "actual_results") + "/" + domainName
							+ "Generics" + "." + Ooaofooa.MODELS_EXT, true);

			if (!generateResults) { // make sure the exported model has OAL
				TestingUtilities.fileContentsCompare(m_workspace_path
						+ "expected_results/" + domainName + "Generics"
						+ "." + Ooaofooa.MODELS_EXT, m_workspace_path
						+ "actual_results/" + domainName + "Generics" + "."
						+ Ooaofooa.MODELS_EXT);
			}
		}
         catch (CoreException f) {
            fail( f.toString() );
        }
        }
   
	public void testOALInstancesCreatedWhileBuild() throws FileNotFoundException, CoreException {
		try {
			IdAssigner.setSeedOfAllInstances(2, true);
			String domainName = "testOAL1";
			loadProject(domainName);
			project.build(IncrementalProjectBuilder.FULL_BUILD, null);
			TestingUtilities.allowJobCompletion();

			TestUtil.sleepWithDispatchOfEvents(100);
			TestingUtilities.exportModelUsingWizard(m_workspace_path
					+ (generateResults ? "expected_results" : "actual_results")
					+ "/" + domainName + "." + Ooaofooa.MODELS_EXT, true);

			if (!generateResults) { // make sure the exported model has OAL
				TestingUtilities.fileContentsCompare(m_workspace_path
						+ "expected_results/" + domainName + "."
						+ Ooaofooa.MODELS_EXT, m_workspace_path
						+ "actual_results/" + domainName + "."
						+ Ooaofooa.MODELS_EXT);
			}
		} catch (CoreException f) {
			fail(f.toString());
		}
    }

	public void testOALInstancesExportedProperly()
			throws FileNotFoundException, CoreException {
		try {
			String testProjectName = "Integration";
			loadProject(testProjectName);

			SystemModel_c systemModel = getSystemModel(project.getName());

			IPreferenceStore store = CorePlugin.getDefault()
					.getPreferenceStore();
			store.setValue(BridgePointPreferencesStore.EXPORT_GRAPHICS,
					MessageDialogWithToggle.NEVER); //$NON-NLS-1$
			store.setValue(BridgePointPreferencesStore.EXPORT_OAL,
					MessageDialogWithToggle.ALWAYS); //$NON-NLS-1$	     

			// add the systemModel to the selection
			Selection.getInstance().clear();
			Selection.getInstance().addToSelection(systemModel);

			String specializedModelPath = m_workspace_path + "actual_results"
					+ "/" + testProjectName + "Specialized" + "."
					+ Ooaofooa.MODELS_EXT;
			TestingUtilities.exportModelUsingWizard(specializedModelPath, true);
			BufferedReader bufferReader = new BufferedReader(new FileReader(
					specializedModelPath));
			String line = "";
			int Act_Count_inSpecialized = 0;
			while ((line = bufferReader.readLine()) != null) {
				if (line.contains("ACT_")) {
					Act_Count_inSpecialized++;
				}
			}
			bufferReader.close();
			
			assertEquals(8049, Act_Count_inSpecialized);

		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
}
