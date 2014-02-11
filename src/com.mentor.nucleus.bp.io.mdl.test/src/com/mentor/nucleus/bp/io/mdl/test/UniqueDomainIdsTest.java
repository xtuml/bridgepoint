package com.mentor.nucleus.bp.io.mdl.test;
//=====================================================================
//
//File:      $RCSfile: UniqueDomainIdsTest.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:12:54 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EclipseOoaofooa;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.ui.NewDomainWizard;
import com.mentor.nucleus.bp.core.ui.WizardNewDomainCreationPage;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;

public class UniqueDomainIdsTest extends BaseTest {
    
	public UniqueDomainIdsTest(String arg0) throws CoreException {
		super("UniqueDomainIdsTest", arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testUniqueDomainIds() {
		// create two domains and verify
		// that the ids of each are unique
		createNewDomainUsingWizard("testDom1");
		String id = Ooaofooa.createModelRootId(
				getProject(), "testDom1", true);
		Ooaofooa modelRoot = Ooaofooa.getInstance(id);
		Domain_c dom1 = Domain_c.DomainInstance(modelRoot);
		createNewDomainUsingWizard("testDom2");
		id = Ooaofooa.createModelRootId(
				getProject(), "testDom2", true);
		Ooaofooa modelRoot2 = Ooaofooa.getInstance(id);
		Domain_c dom2 = Domain_c.DomainInstance(modelRoot2);
		assertTrue("Two domains were created with the same domain id.", dom1.getDom_id() != dom2.getDom_id());
		// now verify that the GD_MD instance associated with
		// one of the domains has the new domain id set for
		// its OOA_ID attribute
		CanvasTestUtils.openCanvasEditor(dom2);
		ModelEditor mEditor = (ModelEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		GraphicalEditor ce = mEditor.getGraphicalEditor();
		Model_c model = ce.getModel();
		GraphicalElement_c[] elems = GraphicalElement_c.getManyGD_GEsOnR1(model);
		assertTrue("Could not find any elements for the associated GD_MD.", elems.length > 0);
	}

	public void testUniqueIdsOnNonNewlyCreatedDomains() {
		// copy the two files with the same dom id
		IFile modelFile = TestUtil.copyTestDomainIntoProject("odms-sameid", "Models", getProject());
		IFile modelFile2 = TestUtil.copyTestDomainIntoProject("odms", "Models", getProject());
		// get the model-root then the domain
		Ooaofooa firstModelRoot = EclipseOoaofooa.getInstance(modelFile, true);
		Ooaofooa secondModelRoot = EclipseOoaofooa.getInstance(modelFile2, true);
		Domain_c dom1 = Domain_c.DomainInstance(firstModelRoot);
		Domain_c dom2 = Domain_c.DomainInstance(secondModelRoot);
		// verify that the ids were changed
		assertTrue("The domain ids for two models were not changed to be unique.", dom1.getDom_id() != dom2.getDom_id());
	}
	
	public void createNewDomainUsingWizard(String name) {
		NewDomainWizard wizard = new NewDomainWizard();
		SystemModel_c sys = TestingUtilities.getSystemModel(getProject().getName());
		wizard.init(PlatformUI.getWorkbench(), new StructuredSelection(sys));
		WizardNewDomainCreationPage startingPage = (WizardNewDomainCreationPage) wizard.getStartingPage();
		startingPage.createControl(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		startingPage.setDomainNameFieldValue(name);
		startingPage.setPageComplete(true);
		if(wizard.canFinish()) {
			assertTrue(wizard.performFinish());
		}		
	}
}
