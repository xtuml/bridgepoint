package org.xtuml.bp.core.test;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.ClassInstanceParticipant_c;
import org.xtuml.bp.core.ClassParticipant_c;
import org.xtuml.bp.core.ComponentParticipant_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ExternalEntityParticipant_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.InteractionParticipant_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.PackageParticipant_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectPreferences;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import org.xtuml.bp.core.util.WorkspaceUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;

@RunWith(OrderedRunner.class)
public class InteractionResolutionTests extends BaseTest {

	private static SystemModel_c inscopeOtherProject;

	@Override
	protected void initialSetup() throws Exception {
		// disable auto build
		WorkspaceUtil.setAutobuilding(false);

		IProject testProject = TestingUtilities.createProject("InteractionResolutionTests");

		File sourceProject = new File(m_workspace_path + "../InteractionResolutionTests");

		CorePlugin.disableParseAllOnResourceChange();

		TestingUtilities.copyProjectContents(sourceProject, testProject);

		TestingUtilities.allowJobCompletion();

		m_sys = getSystemModel(testProject.getName());
		m_sys.setUseglobals(true);
		m_sys.getPersistableComponent().loadComponentAndChildren(new NullProgressMonitor());

		IScopeContext projectScope = new ProjectScope(testProject);
		Preferences projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		
		IProject inscope = TestingUtilities.createProject("InScope Other");
		inscopeOtherProject = getSystemModel(inscope.getName());
		inscopeOtherProject.setUseglobals(true);
		projectScope = new ProjectScope(inscope);
		projectNode = projectScope
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.putBoolean(
				BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, true);
		inscopeOtherProject.Newpackage();
	}
	
	@Test
	public void testComponentParticipantResolution() {
		Package_c pkgInOtherSys = Package_c.getOneEP_PKGOnR1401(inscopeOtherProject);
		pkgInOtherSys.Newcomponent();
		Component_c elementInOtherProject = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(pkgInOtherSys));
		ComponentParticipant_c part = ComponentParticipant_c
				.getOneSQ_COPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1401(m_sys))));
		part.Formalize(elementInOtherProject.getId());
		Package_c partPackage = Package_c
				.getOneEP_PKGOnR8000(PackageableElement_c
						.getManyPE_PEsOnR8001(InteractionParticipant_c
								.getManySQ_PsOnR930(part)));
		UITestingUtilities.cutElementInExplorer(part, getExplorerView());
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(partPackage);
		UITestingUtilities.pasteClipboardContentsInExplorer();
		// get the new part
		part = ComponentParticipant_c
				.getOneSQ_COPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1401(m_sys))));
		assertTrue("On paste the referred to element was not reassociated.",
				Component_c.getOneC_COnR955(part) == elementInOtherProject);
	}
	@Test
	public void testPackageParticipantResolution() {
		Package_c pkgInOtherSys = Package_c.getOneEP_PKGOnR1401(inscopeOtherProject);
		pkgInOtherSys.Newpackage();
		Package_c elementInOtherProject = Package_c
				.getOneEP_PKGOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(pkgInOtherSys));
		PackageParticipant_c part = PackageParticipant_c
				.getOneSQ_PPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1401(m_sys))));
		part.Formalize(elementInOtherProject.getPackage_id());
		Package_c partPackage = Package_c
				.getOneEP_PKGOnR8000(PackageableElement_c
						.getManyPE_PEsOnR8001(InteractionParticipant_c
								.getManySQ_PsOnR930(part)));
		UITestingUtilities.cutElementInExplorer(part, getExplorerView());
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(partPackage);
		UITestingUtilities.pasteClipboardContentsInExplorer();
		// get the new part
		part = PackageParticipant_c
				.getOneSQ_PPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1401(m_sys))));
		assertTrue("On paste the referred to element was not reassociated.",
				Package_c.getOneEP_PKGOnR956(part) == elementInOtherProject);
	}
	@Test
	public void testClassParticipantResolution() {
		Package_c pkgInOtherSys = Package_c.getOneEP_PKGOnR1401(inscopeOtherProject);
		pkgInOtherSys.Newclass();
		ModelClass_c elementInOtherProject = ModelClass_c
				.getOneO_OBJOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(pkgInOtherSys));
		elementInOtherProject.setKey_lett("EXP");
		ClassParticipant_c part = ClassParticipant_c
				.getOneSQ_CPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1401(m_sys))));
		part.Formalize(elementInOtherProject.getObj_id());
		Package_c partPackage = Package_c
				.getOneEP_PKGOnR8000(PackageableElement_c
						.getManyPE_PEsOnR8001(InteractionParticipant_c
								.getManySQ_PsOnR930(part)));
		UITestingUtilities.cutElementInExplorer(part, getExplorerView());
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(partPackage);
		UITestingUtilities.pasteClipboardContentsInExplorer();
		// get the new part
		part = ClassParticipant_c
				.getOneSQ_CPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1401(m_sys))));
		assertTrue("On paste the referred to element was not reassociated.",
				ModelClass_c.getOneO_OBJOnR939(part) == elementInOtherProject);
	}
	@Test
	public void testClassInstanceParticipantResolution() {
		Package_c pkgInOtherSys = Package_c.getOneEP_PKGOnR1401(inscopeOtherProject);
		ModelClass_c elementInOtherProject = ModelClass_c
				.getOneO_OBJOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(pkgInOtherSys));
		ClassInstanceParticipant_c part = ClassInstanceParticipant_c
				.getOneSQ_CIPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1401(m_sys))));
		part.Formalize(elementInOtherProject.getObj_id());
		Package_c partPackage = Package_c
				.getOneEP_PKGOnR8000(PackageableElement_c
						.getManyPE_PEsOnR8001(InteractionParticipant_c
								.getManySQ_PsOnR930(part)));
		UITestingUtilities.cutElementInExplorer(part, getExplorerView());
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(partPackage);
		UITestingUtilities.pasteClipboardContentsInExplorer();
		// get the new part
		part = ClassInstanceParticipant_c
				.getOneSQ_CIPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1401(m_sys))));
		assertTrue("On paste the referred to element was not reassociated.",
				ModelClass_c.getOneO_OBJOnR934(part) == elementInOtherProject);
	}
	@Test
	public void testExternalEntityParticipantResolution() {
		Package_c pkgInOtherSys = Package_c.getOneEP_PKGOnR1401(inscopeOtherProject);
		pkgInOtherSys.Newexternalentity();
		ExternalEntity_c elementInOtherProject = ExternalEntity_c
				.getOneS_EEOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(pkgInOtherSys));
		ExternalEntityParticipant_c part = ExternalEntityParticipant_c
				.getOneSQ_EEPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1401(m_sys))));
		part.Formalize(elementInOtherProject.getEe_id());
		Package_c partPackage = Package_c
				.getOneEP_PKGOnR8000(PackageableElement_c
						.getManyPE_PEsOnR8001(InteractionParticipant_c
								.getManySQ_PsOnR930(part)));
		UITestingUtilities.cutElementInExplorer(part, getExplorerView());
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(partPackage);
		UITestingUtilities.pasteClipboardContentsInExplorer();
		// get the new part
		part = ExternalEntityParticipant_c
				.getOneSQ_EEPOnR930(InteractionParticipant_c
						.getManySQ_PsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(Package_c
										.getManyEP_PKGsOnR1401(m_sys))));
		assertTrue("On paste the referred to element was not reassociated.",
				ExternalEntity_c.getOneS_EEOnR933(part) == elementInOtherProject);
	}

}
