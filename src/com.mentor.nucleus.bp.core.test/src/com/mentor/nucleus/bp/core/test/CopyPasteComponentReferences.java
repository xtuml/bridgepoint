package com.mentor.nucleus.bp.core.test;
//========================================================================
//
//File:      $RCSfile: CopyPasteComponentReferences.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:03 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.ImportedProvisionInSatisfaction_c;
import com.mentor.nucleus.bp.core.ImportedProvision_c;
import com.mentor.nucleus.bp.core.ImportedReference_c;
import com.mentor.nucleus.bp.core.ImportedRequirement_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.PortReference_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Satisfaction_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectPreferences;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;

public class CopyPasteComponentReferences extends BaseTest {

	private static IProject destinationProject;

	@Override
	protected void initialSetup() throws Exception {
		destinationProject = TestingUtilities.createProject("component_ref_destination");
		SystemModel_c destinationSystem = getSystemModel(destinationProject.getName());
		IScopeContext projectScope = new ProjectScope(destinationProject);
		Preferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Create test model",
					new ModelElement[] { Ooaofooa.getDefaultInstance() });
			destinationSystem.setUseglobals(true);
			m_sys.setUseglobals(true);
			// create test model
			m_sys.Newpackage();
			Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
			Package_c sourcePkg = pkgs[pkgs.length - 1];
			sourcePkg.setName("source");
			destinationSystem.Newpackage();
			pkgs = Package_c.getManyEP_PKGsOnR1401(destinationSystem);
			Package_c destinationPkg = pkgs[pkgs.length - 1];
			destinationPkg.setName("destination");
			sourcePkg.Newcomponent();
			Component_c[] components = Component_c
					.getManyC_CsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(sourcePkg));
			Component_c sourceComponentOne = components[components.length - 1];
			sourceComponentOne.setName("component_one");
			sourcePkg.Newcomponent();
			components = Component_c
					.getManyC_CsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(sourcePkg));
			Component_c sourceComponentTwo = components[components.length - 1];
			sourceComponentTwo.setName("component_two");
			sourcePkg.Newinterface();
			Interface_c[] interfaces = Interface_c
					.getManyC_IsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(sourcePkg));
			Interface_c iface = interfaces[interfaces.length - 1];
			iface.setName("interface");
			sourceComponentOne.Newprovision(sourceComponentOne.getId(), false, Gd_c.Null_unique_id(), false);
			Provision_c provision = Provision_c
					.getOneC_POnR4009(InterfaceReference_c
							.getManyC_IRsOnR4016(Port_c
									.getManyC_POsOnR4010(sourceComponentOne)));
			provision.Formalize(iface.getId(), false);
			sourceComponentTwo.Newrequirement(sourceComponentTwo.getId(),
					false, provision.getProvision_id(), false);
			destinationPkg.Newimportedcomponent();
			ComponentReference_c[] references = ComponentReference_c
					.getManyCL_ICsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(destinationPkg));
			ComponentReference_c sourceComponentReferenceOne = references[references.length - 1];
			sourceComponentReferenceOne.Assigntocomp(sourceComponentOne.getId());
			destinationPkg.Newimportedcomponent();
			references = ComponentReference_c
					.getManyCL_ICsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(destinationPkg));
			ComponentReference_c sourceComponentReferenceTwo = references[references.length - 1];
			sourceComponentReferenceTwo
					.Assigntocomp(sourceComponentTwo.getId());
			ImportedProvision_c importedPro = ImportedProvision_c
					.getOneCL_IPOnR4703(ImportedReference_c
							.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(sourceComponentReferenceOne)));
			ImportedRequirement_c importedRequirement = ImportedRequirement_c
					.getOneCL_IROnR4703(ImportedReference_c
							.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(sourceComponentReferenceTwo)));
			importedRequirement.Linkconnector(importedPro.getId());
			TransactionManager.getSingleton().endTransaction(transaction);
			BaseTest.dispatchEvents(0);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction);
			}
			fail(e.getMessage());
		}
	}

	public void testSatisfactionCreatedProperly() throws CoreException {
		// close the target project and reopen
		destinationProject.close(new NullProgressMonitor());
		destinationProject.open(new NullProgressMonitor());
		BaseTest.dispatchEvents(0);
		// now look for the satisfaction instance
		SystemModel_c destSystem = getSystemModel("component_ref_destination");
		Satisfaction_c satisfaction = Satisfaction_c
				.getOneC_SFOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR1401(
								destSystem, new ClassQueryInterface_c() {

									@Override
									public boolean evaluate(Object candidate) {
										return ((Package_c) candidate)
												.getName()
												.equals("destination");
									}
								})));
		assertNotNull(
				"Satisfaction was not properly created when connecting an imported required to an imported provision.",
				satisfaction);
	}

	public void testCopyPasteOfComponentReferences() {
		SystemModel_c destSystem = getSystemModel("component_ref_destination");
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(destSystem);
		Package_c sourcePkg = pkgs[pkgs.length - 1];
		Package_c destPkg = null;
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction("create destination", new ModelElement[] {Ooaofooa.getDefaultInstance()});
			destSystem.Newpackage();
			pkgs = Package_c.getManyEP_PKGsOnR1401(destSystem);
			destPkg = pkgs[pkgs.length - 1];
			destPkg.setName("comp_ref_dest");
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if(transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(transaction);
			}
			fail(e.getMessage());
		}
		assertNotNull(destPkg);
		ComponentReference_c[] references = ComponentReference_c
				.getManyCL_ICsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(sourcePkg));
		ImportedProvision_c importedPro = ImportedProvision_c
				.getOneCL_IPOnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(references)));
		ImportedRequirement_c importedReq = ImportedRequirement_c
				.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(references)));
		Selection.getInstance().clear();
		for(int i = 0; i < references.length; i++) {
			Selection.getInstance().addToSelection(references[i]);
		}
		Selection.getInstance().addToSelection(importedPro);
		Selection.getInstance().addToSelection(importedReq);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		UITestingUtilities.pasteClipboardContentsInExplorer(destPkg);
		// make sure that the imported pro and req are still satisfied
		references = ComponentReference_c
				.getManyCL_ICsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(destPkg));
		importedPro = ImportedProvision_c
				.getOneCL_IPOnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(references)));
		Satisfaction_c sat = Satisfaction_c
				.getOneC_SFOnR4705(ImportedProvisionInSatisfaction_c
						.getManyCL_IPINSsOnR4705(importedPro));
		assertNotNull("After paste the satisfactions were not kept.", sat);
	}
	
}
