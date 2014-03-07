package com.mentor.nucleus.bp.io.mdl.test;

//=====================================================================
//
//File:      $RCSfile: ProxyTests.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/10 05:13:52 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectPreferences;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;

public class ProxyTests extends BaseTest {

    private static String projectName = "ProxyTests";

    public ProxyTests(String name) {
        super(projectName, name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Ooaofooa.setPersistEnabled(true);
        ensureAvailableAndLoaded("Proxy", false);
    }
    
    public void tearDown() throws Exception {
    	super.tearDown();
    	Ooaofooa.setPersistEnabled(false);
    }

    public void testConvertToProxyAfterComponentUnload() throws Exception {

        ModelClass_c rtoClass = ModelClass_c.ModelClassInstance(modelRoot,
                new ModelClass_by_name_c("RTOClass"));
        ModelClass_c rgoClass = ModelClass_c.ModelClassInstance(modelRoot,
                new ModelClass_by_name_c("RGOClass"));

        Subsystem_c subsystem = Subsystem_c.getOneS_SSOnR2(rgoClass);

        BaseAttribute_c rtoIDBaseAttr = BaseAttribute_c
                .getOneO_BATTROnR106(Attribute_c.getOneO_ATTROnR102(rtoClass,
                        new Attribute_by_name_c("ID")));

        Attribute_c nameAttr = Attribute_c.AttributeInstance(modelRoot,
                new Attribute_by_name_c("Name"));

        // Unload RTO component and check that its elements has been converted
        // to the proxy
        //Hint: To check why any element is not removed when expected, see
        //its RGOs using PM.getHierMetaData().findExternalRGOs(element, false);
        
        rtoClass.getPersistableComponent().deleteSelf();
        assertTrue("RTO Class does not converted to the proxy", rtoClass
                .isProxy());
        assertFalse("RTO Class prematurely removed", rtoClass.isOrphaned());

        assertTrue("RTO BaseAttribute_c does not converted to the proxy",
                rtoIDBaseAttr.isProxy());
        assertFalse("RTO BaseAttribute_c prematurely removed", rtoIDBaseAttr
                .isOrphaned());

        assertTrue("Attribute of removed class does not removed", nameAttr
                .isOrphaned());

        // Unload RGO component and check that its proxies has been removed
        rgoClass.getPersistableComponent().deleteSelf();
        assertTrue("RGO Class does not converted to the proxy", rgoClass
                .isProxy());
        assertTrue("Proxy BaseAttribute does not removed after RGO unload",
                rtoIDBaseAttr.isOrphaned());

        assertTrue("Attribute of removed class does not removed", nameAttr
                .isOrphaned());

        // Unload Parent and check it has been converted to the proxy
        subsystem.getPersistableComponent().deleteSelf();
        assertTrue("Subsystem does not converted to the proxy", subsystem
                .isProxy());
        assertTrue("Proxy class does not removed", rtoClass.isOrphaned());

        // unload child and check parent proxy is removed
        ModelClass_c standaloneClass = ModelClass_c.ModelClassInstance(
                modelRoot, new ModelClass_by_name_c("StandaloneClass"), false);
        // the deletion caused by this call will trigger a reload
        // and in this case we are not actually removing the file
        // this causes a listener in ui.text to reload the component
        Ooaofooa.disableChangeNotification();
        try {
        	standaloneClass.getPersistableComponent().deleteSelf();
        } finally {
        	Ooaofooa.enableChangeNotification();
        }

        assertTrue("Subsystem does not removed after child unload", subsystem
                .isOrphaned());

    }
    
    public void testProxyPathWhenReferencingNonLocalProject() {
    	// prevent re-use of ids
    	IdAssigner.setSeedOfAllInstances(345, true);
    	String externalWorkspaceName = "C:/" + UUID.randomUUID().toString();
    	File externalWorkspace = new File(externalWorkspaceName);
    	Transaction transaction = null;
    	try {
        	IProject external = TestingUtilities.createProject("external_project");
        	IProject local = TestingUtilities.createProject("local_project");
    		IScopeContext projectScope = new ProjectScope(local);
    		Preferences projectNode = projectScope
    				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
    		projectNode.putBoolean(
    				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);

        	SystemModel_c system = getSystemModel(external.getName());
        	SystemModel_c localSystem = getSystemModel(local.getName());
			transaction = TransactionManager.getSingleton().startTransaction(
					"Create test elements.",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
    		externalWorkspace.mkdir();
    		// now create the test project that will contain the external
    		// data
    		system.Newpackage();
    		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(system);
    		Package_c testPackage = pkgs[pkgs.length - 1];
    		testPackage.Newinterface();
			TransactionManager.getSingleton().endTransaction(transaction);
			// copy the project to the test workspace location
			File projectFile = system.getFile().getProject().getLocation().toFile();
			copyFolder(projectFile, new File(externalWorkspace.getAbsolutePath() + "/" + external.getName()));
			// delete the project
			external.delete(true, new NullProgressMonitor());
			BaseTest.dispatchEvents(0);
			// import the external project
			ProjectUtilities.importExistingProject(externalWorkspaceName, false);
			BaseTest.dispatchEvents(0);
			external = getProjectHandle(external.getName());
			external.close(new NullProgressMonitor());
			external.open(new NullProgressMonitor());
			BaseTest.dispatchEvents(0);
			system = getSystemModel(external.getName());
			transaction = TransactionManager.getSingleton().startTransaction(
					"Create test elements",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			// create the referring test elements
			localSystem.Newpackage();
    		pkgs = Package_c.getManyEP_PKGsOnR1401(localSystem);
    		testPackage = pkgs[pkgs.length - 1];
    		testPackage.Newcomponent();
			Component_c[] comps = Component_c
					.getManyC_CsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(testPackage));
			Component_c comp = comps[comps.length - 1];
			comp.Initializeprovision(true, Gd_c.Null_unique_id(), Gd_c.Null_unique_id());
			Provision_c provision = Provision_c
					.getOneC_POnR4009(InterfaceReference_c
							.getOneC_IROnR4016(Port_c.getOneC_POOnR4010(comp)));
			Interface_c iface = Interface_c
					.getOneC_IOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(Package_c
									.getManyEP_PKGsOnR1401(system)));
			provision.Formalize(iface.getId(), false);
			TransactionManager.getSingleton().endTransaction(transaction);
			BaseTest.dispatchEvents(0);
			// now search for the right number of ../s
			File compFile = comp.getPersistableComponent().getFile().getLocation().toFile();
			byte[] bytes = new byte[(int) compFile.length()];
			FileInputStream fis = new FileInputStream(compFile);
			fis.read(bytes);
			fis.close();
			String fileContents = new String(bytes);
			String interfaceProxyPath = "../../../../../"
				+ iface.getFile().getFullPath().toString().replaceAll("^/",
				"");
			external.delete(true, true, new NullProgressMonitor());
			BaseTest.dispatchEvents(0);
			assertTrue("", fileContents.contains(interfaceProxyPath));
    	} catch (IOException e) {
    		if(transaction != null && TransactionManager.getSingleton().getActiveTransaction() != null) {
    			TransactionManager.getSingleton().cancelTransaction(transaction);
    		}
    		fail(e.getMessage());
    	} catch (TransactionException e) {
    		if(transaction != null && TransactionManager.getSingleton().getActiveTransaction() != null) {
    			TransactionManager.getSingleton().cancelTransaction(transaction);
    		}
    		fail(e.getMessage());
    	} catch (CoreException e) {
    		if(transaction != null && TransactionManager.getSingleton().getActiveTransaction() != null) {
    			TransactionManager.getSingleton().cancelTransaction(transaction);
    		}
    		fail(e.getMessage());
		} finally {
    		if(externalWorkspace.exists()) {
    			deleteFolder(externalWorkspace);
    		}
    	}
    }

	private void deleteFolder(File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(File child : files) {
				deleteFolder(child);
			}
			boolean result = file.delete();
			if(!result) {
				System.err.println("Could not clean temporary project.");
			}
		} else {
			boolean result = file.delete();
			if(!result) {
				System.err.println("Could not clean temporary project.");
			}
		}
	}

	public static void copyFolder(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}
		} else {
			if (!dest.exists()) {
				dest.createNewFile();
			}
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}


}
