package com.mentor.nucleus.bp.io.mdl.test.pkgcm;
//=====================================================================
//
//File:      $RCSfile: FileVsFolder_i1608Test.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/10 23:12:56 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.TreeItem;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ModelElementFileFacade;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CVSUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;

public class FileVsFolder_i1608Test extends BaseTest {

	private PersistableModelComponent sysComponent;

	private static String projectName = null;

	private PersistableModelComponent domainComponent;;

	public FileVsFolder_i1608Test(String name) {
		super(null, name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		// make sure the user isn't prompted to do a parse all
		CorePlugin.disableParseAllOnResourceChange();
		Ooaofooa.setPersistEnabled(true);
		
		boolean checkoutProject = false;
		if(project.getName().equals(BaseTest.defaultProjectName)) {
			checkoutProject = true;
		}
		
		if (project == null || checkoutProject) { // only checkout once
			projectName = "CVS Test Project";
			CVSUtils.checkoutProject(projectName);
		}

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);

		PersistenceManager manager = PersistenceManager.getDefaultInstance();

		sysComponent = manager.getRootComponent(project);
		IPath path = sysComponent.getContainingDirectoryPath().append(
				"testDomain" + "/" + "testDomain" + "." + Ooaofooa.MODELS_EXT);

		domainComponent = PersistableModelComponent.findOrCreateInstance(path);
		domainComponent.loadComponentAndChildren(new NullProgressMonitor());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFileVsFolderCVSOperations() throws Exception {

		Domain_c dom = (Domain_c) domainComponent.getRootModelElement();

		m_bp_tree.expandToLevel(2); // expand upto domain
		
		TreeItem treeItem = ExplorerUtil.findTreeItem(sysComponent
				.getRootModelElement());
		// check for First child; it should be tree item representing ME as file
		TreeItem meFileItem = treeItem.getItems()[0];
		assertTrue(meFileItem.getItemCount() == 0);
		String elementName = meFileItem.getText();
		assertTrue("Unexpected system model text.", elementName.indexOf("CVS Test Project: System Model Package Diagram") != -1);

		treeItem = ExplorerUtil.findTreeItem(dom);
		meFileItem = treeItem.getItems()[0];

		assertTrue(meFileItem.getItemCount() == 0);
        elementName = meFileItem.getText(); 
		assertEquals("testDomain: Domain Package Diagram",
                elementName.substring(0, elementName.indexOf("Diagram")+7));

		ModelElementFileFacade fileFacade = (ModelElementFileFacade) dom
				.getAdapter(ModelElementFileFacade.class);
		// Component root elements should adapt to a ModelElementFileFacade
		assertNotNull(fileFacade);

		// perform rename on Model Element through file facade tree item
		ExplorerUtil.doRenameThruMExplorer(fileFacade, "testDomain_");
		
		dispatchEvents(0);
		
		treeItem = ExplorerUtil.findTreeItem(fileFacade);
        elementName = treeItem.getText();        
		assertEquals(">testDomain_: Domain Package Diagram",
				elementName.substring(0, elementName.indexOf("Diagram")+7));

		treeItem = ExplorerUtil.findTreeItem(dom);
		assertTrue("Expected domain CVS dirty decoration is incorrect.",
		        treeItem.getText().startsWith(">testDomain_"));

		// revert rename
		ExplorerUtil.doRenameThruMExplorer(fileFacade, "testDomain");
		
		dispatchEvents(0);
		
		CVSUtils.checkCVSDirtyMark(fileFacade, true);
		treeItem = ExplorerUtil.findTreeItem(fileFacade);
        elementName = treeItem.getText();
		assertEquals(">testDomain: Domain Package Diagram",
                elementName.substring(0, elementName.indexOf("Diagram")+7));

		treeItem = ExplorerUtil.findTreeItem(dom);
        assertTrue("Expected domain CVS dirty decoration is incorrect.",
                treeItem.getText().startsWith(">testDomain"));

		IPath domPath = dom.getFile().getFullPath();

		CVSUtils.replaceWithLatest(domPath, ResourcesPlugin.getWorkspace().getRoot().getProject(
                projectName));
		
		dispatchEvents(0);
		
		domainComponent = PersistableModelComponent.findOrCreateInstance(domPath);
		dom = (Domain_c) domainComponent.getRootModelElement();
		fileFacade = (ModelElementFileFacade) dom
				.getAdapter(ModelElementFileFacade.class);

		treeItem = ExplorerUtil.findTreeItem(fileFacade);
        elementName = treeItem.getText();
		assertEquals("testDomain: Domain Package Diagram",
                elementName.substring(0, elementName.indexOf("Diagram")+7));

		treeItem = ExplorerUtil.findTreeItem(dom);
        assertTrue("Expected domain CVS dirty decoration is incorrect.",
                treeItem.getText().startsWith(">testDomain"));

	}

}
