//=====================================================================
//
//File:      $RCSfile: RenameInvolvingResourceTestGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:26 $
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

package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.CorePlugin; //import com.mentor.nucleus.bp.core.Domain_c;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

/**
 * Tests involving the renaming of either a model element
 * (such as a domain or system) or its associated resource
 * (such as a model-file or project), checking to see whether
 * the associated resource or model-element is renamed to match. 
 */
public class RenameInvolvingResourceTestGenerics extends CoreTest {

	/**
	 * The name of the test domain used during most of these tests.
	 * This isn't final because it needs to change temporarily 
	 * during the domain-rename tests. 
	 */
	private static String testModelName = "odms";

	/**
	 * For issue 742.  Renames a domain and checks to see 
	 * that the corresponding model-file's name has been changed to match.
	 */

	public RenameInvolvingResourceTestGenerics(String name) {
		super(null, name);
	}
	static boolean initialized = false;

	protected void setUp() throws Exception {
		super.setUp();

		if (!initialized) {
			loadProject("odms");
			initialized = true;
		}

	}

	public void testDomainRenameCausesModelFileNameChange() throws Exception {
		// setup the test project and model
		// Ooaofooa.setPersistEnabled(true);
		//	Ooaofooa.setConsistencyEnabled(true);
		// open a Package editor on the test Package
		Ooaofooa.setPersistEnabled(true);
		Package_c domain = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("odms"));
		GraphicalEditor editor = CanvasTestUtils
				.openPackageCanvasEditor(domain);

		// rename the Package 

		testModelName += "y";

		TransactionManager manager = domain.getTransactionManager();
		Transaction t = manager.startTransaction("Renaming Package", Ooaofooa
				.getDefaultInstance());
		domain.setName(testModelName);
		manager.endTransaction(t);
		// wait for the changes to finish
		Display d = Display.getCurrent();
		while (d.readAndDispatch());

		// wait for the changes to finish
		d = Display.getCurrent();
		while (d.readAndDispatch());

		// check that the filename of the domain's model-file has been changed
		// to match the domain's new name

		IFile file = domain.getFile();
		String filename = file.getFullPath().removeFileExtension()
				.lastSegment();
		assertTrue("Filename not renamed", file.exists());
		assertEquals("Filename does not equal changed domain name", filename,
				domain.getName());

		checkForNoDuplicatePackageEditorAfterRename(editor);

		// the next test will change the name back
		Ooaofooa.setPersistEnabled(false);
	}

	/**
	 * Checks that re-opening an editor on the domain already being
	 * edited by the given editor does not cause a second, duplicate
	 * editor to be opened.  One way such a condition can occur is 
	 * when the graphics-root corresponding to the domain's model-root 
	 * has not had its ID updated as a result of the domain's renaming.
	 */
	private void checkForNoDuplicatePackageEditorAfterRename(
			GraphicalEditor editor) {
		// re-open the domain editor; note that we can't use the result
		// of openDomainCanvasEditor (and therefore must call getCanvasEditor
		// afterwards) because it's dependent on a hardcoded domain name 
		CanvasTestUtils.openPackageCanvasEditor(Package_c.PackageInstance(
				modelRoot, new Package_by_name_c("odmsy"))

		);
		GraphicalEditor reOpenedEditor = CanvasTestUtils
				.getCanvasEditor(testModelName + ": Package Diagram");

		// check that the re-opening above simply activated the previously-opened 
		// domain editor
		assertEquals(
				"A new Package editor was opened after the Package's renaming",
				editor, reOpenedEditor);
	}

	/**
	 * For issue 742.  Renames a system and checks to see that 
	 * the corresponding project's name has been changed to match.
	 * 
	 * Relies on the final state of the previous test.
	 */
	public void testSystemRenameCausesProjectNameChange() throws Exception {
		// rename the system in the model explorer
		Package_c domain = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("odmsy"));
		SystemModel_c system = SystemModel_c.getOneS_SYSOnR1401(domain);
		String systemName = system.getName();
		// change from <name> to <name>z
		systemName += "z";

		Ooaofooa.setPersistEnabled(true);

		TransactionManager manager = system.getTransactionManager();
		Transaction t = manager.startTransaction("Renaming system model",
				Ooaofooa.getDefaultInstance());
		system.setName(systemName);
		manager.endTransaction(t);

		// wait for the changes to finish
		Display d = Display.getCurrent();
		while (d.readAndDispatch());

		verifyProjectFiles(systemName);

		// the next test will change the name back
		Ooaofooa.setPersistEnabled(false);
	}

	private void verifyProjectFiles(String systemName) {
		// check that the name of the system's project has been changed
		// to match the system's new name
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject renamedProject = wsRoot.getProject(systemName);
		assertTrue("Project not renamed to match new system name",
				renamedProject != null && renamedProject.exists());

		IFile newFile = wsRoot.getFile(PersistableModelComponent
				.getRootComponentPath(systemName));
		assertTrue("Project model file not renamed to match new system name",
				newFile != null && newFile.exists());

		IFolder newFolder = (IFolder) newFile.getParent();
		assertTrue(
				"Project model file folder not renamed to match new system name",
				newFolder != null && newFolder.exists()
						&& newFolder.getName().equals(systemName));
	}

	/**
	 * For issue 742.  Renames a project on disk
	 * and checks to see that the corresponding system's name
	 * has been changed to match.
	 * 
	 * Relies on the final state of the previous test.
	 */
	public void testProjectRenameCausesSystemNameChange() throws Exception {
		Ooaofooa.setPersistEnabled(true);

		// rename the system's project on disk
		Package_c domain = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("odmsy"));
		SystemModel_c system = SystemModel_c.getOneS_SYSOnR1401(domain);
		String systemName = system.getName();
		IProject oldProject = CorePlugin.getWorkspace().getRoot().getProject(
				systemName);
		assertNotNull(oldProject);
		// change from <name>z to <name>y
		systemName = systemName.substring(0, systemName.length() - 1) + "y";

		try {
			oldProject.move(new Path(systemName), true,
					new NullProgressMonitor());
		} catch (CoreException e) {
			CorePlugin.logError("Could not rename system's project", e);
		}

		// wait for the changes to finish
		Display d = Display.getCurrent();
		while (d.readAndDispatch());

		verifyProjectFiles(systemName);

		// check that the system's name has been changed to reflect
		// the changed project name
		assertEquals("System name not updated to match new project name",
				system.getName(), systemName);
		oldProject = getProjectHandle(systemName);
		Ooaofooa.setPersistEnabled(false);
		try {
			oldProject.move(new Path("odms"), true,
					new NullProgressMonitor());
		} catch (CoreException e) {
			CorePlugin.logError("Could not rename system's project", e);
		}
	}
}
