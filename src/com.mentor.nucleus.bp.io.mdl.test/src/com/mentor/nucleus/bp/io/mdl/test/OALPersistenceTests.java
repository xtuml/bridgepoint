package com.mentor.nucleus.bp.io.mdl.test;

import java.io.File;
import java.io.FileNotFoundException;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.GeneralPurposeLogger;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class OALPersistenceTests extends TestCase {
	
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
        IdAssigner.setSeedOfAllInstances(getName().hashCode());
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
			String domainName = "testOAL1";
			final IProject project = TestingUtilities.createProject("OAL Test Project");			
			
			SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c () {
				public boolean evaluate(Object candidate) {
					return ((SystemModel_c)candidate).getName().equals(project.getName());
				}
			});
			
			Path mdlPath = new Path(m_workspace_path + Ooaofooa.MODELS_DIRNAME + 
					"/");
			
	        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
	        store.setValue(BridgePointPreferencesStore.PARSE_ALL_ON_RESOURCE_CHANGE, "never"); //$NON-NLS-1$
			TestingUtilities.importModelUsingWizard(system,
					mdlPath,
					domainName + "." + Ooaofooa.MODELS_EXT,
					false);
			
	        Domain_c dom = Domain_c.getOneS_DOMOnR28(system);
	        
	        // create all necessary component files
	        dom.getPersistableComponent().refresh();
	        dom.getPersistableComponent().persistSelfAndChildren();
	        
	        // add the domain to the selection
	        Selection.getInstance().clear(); 
	        Selection.getInstance().addToSelection(dom);
	        
			// Change default for the parse on resource change preference to "always"
	        store.setValue(BridgePointPreferencesStore.EXPORT_OAL, "always"); //$NON-NLS-1$
			// export with persisted OAL	        
	        TestingUtilities.exportModelUsingWizard(m_workspace_path + (generateResults ? 
	              "expected_results" : "actual_results") + 
	              "/" + domainName + "." +  Ooaofooa.MODELS_EXT, true);
	        
	        if (!generateResults) {  // make sure the exported model has OAL
	            TestingUtilities.fileContentsCompare( 
	                m_workspace_path + "expected_results/" + domainName + "." +  Ooaofooa.MODELS_EXT, 
	                m_workspace_path + "actual_results/" + domainName + "." +  Ooaofooa.MODELS_EXT );
	        }
        } catch (CoreException f) {
            fail( f.toString() );
        }
    }

	
}
