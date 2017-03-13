//=====================================================================
//
//	File:      $RCSfile: PlaceholderLifecycleForProjectTest.java,v $
//	Version:   $Revision: 1.12 $
//	Modified:  $Date: 2013/05/10 06:03:50 $
//
//	(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//	=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//	=====================================================================
package org.xtuml.bp.ui.text.test.i673Tests.placeholder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.text.activity.ActivityEditor;
import org.xtuml.bp.ui.text.activity.ActivityEditorInputFactory;
import org.xtuml.bp.ui.text.description.DescriptionEditor;
import org.xtuml.bp.ui.text.description.DescriptionEditorInputFactory;
import org.xtuml.bp.ui.text.test.UITextTest;
import org.xtuml.bp.ui.text.test.activity.ActivityEditorInteraction;
import org.xtuml.bp.ui.text.test.description.DescriptionEditorInteraction;

@RunWith(OrderedRunner.class)
public class PlaceholderLifecycleForProjectTest extends UITextTest {

//	public PlaceholderLifecycleForProjectTest(String projectName, String name) throws CoreException {
//		super("testOpen", name); //$NON-NLS-1$
//	}
//	
//	public PlaceholderLifecycleForProjectTest(String name) throws CoreException {
//		super("testOpen", name); //$NON-NLS-1$
//	}
	
	public PlaceholderLifecycleForProjectTest() throws CoreException {
		super();
//		super("testOpen", null); //$NON-NLS-1$
		// TODO Auto-generated constructor stub
	}

	@Test
	public void testOpenProjectWithFiles() throws Exception{
		IProject currentProject = getProject();
        Ooaofooa.setInUnitTest(true);
		
		//Open description editor for package
		Package_c pkg = Package_c.PackageInstance(modelRoot);
		assertNotNull(pkg);
		DescriptionEditorInteraction.openDescriptionEditor(pkg);
		DescriptionEditor domainEd = (DescriptionEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		assertNotNull(domainEd);			
		
		//Open description editor for Model Class
		ModelClass_c obj = ModelClass_c.ModelClassInstance(modelRoot);
		assertNotNull(obj);
		DescriptionEditorInteraction.openDescriptionEditor(obj);
		DescriptionEditor classEd = (DescriptionEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		assertNotNull(classEd);
		
		//Open activity editor for an Operation
		Operation_c op = Operation_c.OperationInstance(modelRoot);
		assertNotNull(op);
		ActivityEditorInteraction.openActivityEditor(op);
		ActivityEditor operationEd = (ActivityEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		assertNotNull(operationEd);
		
		//Get File instance of each of the above editors
		IFile pkgFile = UITextTest.getExistingPlaceHolderFromManager(pkg, DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION, this);
		assertNotNull(pkgFile);
		IFile classFile = UITextTest.getExistingPlaceHolderFromManager(obj, DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION, this);
		assertNotNull(classFile);
		IFile opFile = UITextTest.getExistingPlaceHolderFromManager(op, ActivityEditorInputFactory.PLACEHOLDER_EXTENSION, this);
		assertNotNull(opFile);
		
		//Add Bookmarks
		IMarker domMarker = createNewMarker(pkgFile, 1, "Marker in domain", IMarker.BOOKMARK);
		assertNotNull(domMarker);
		IMarker classMarker = createNewMarker(classFile, 1, "Marker in model class", IMarker.BOOKMARK);
		assertNotNull(classMarker);
		IMarker opMarker = createNewMarker(opFile, 1, "Marker in operation", IMarker.BOOKMARK);
		assertNotNull(opMarker);
		
		Display d = Display.getCurrent();
		while(d.readAndDispatch()){}
		
		//Confirm that the underlying files now exist
		assertTrue(pkgFile.exists());
		assertTrue(classFile.exists());
		assertTrue(opFile.exists());		
		
		//Closing the project
		try {
			currentProject.close(new NullProgressMonitor());
		} catch (CoreException e) {
			fail("Can not close the project :: " + e.toString()); //$NON-NLS-1$
		}
		d = Display.getCurrent();
		while(d.readAndDispatch()){}
		
		//Confirm that the underlying files exist
		assertTrue(pkgFile.getLocation().toFile().exists());
		assertTrue(classFile.getLocation().toFile().exists());
		assertTrue(opFile.getLocation().toFile().exists());		
		
		try {
			//Open the project again. And see if the placeholder map has the files
			currentProject.open(new NullProgressMonitor());
		} catch (CoreException e1) {
			fail("Can not open the project ::" + e1.toString()); //$NON-NLS-1$
		}
		
		//we need to load rest of components in the start so that Instance* methods
		//work correctly.
		PersistenceManager.getDefaultInstance().
		getRootComponent(currentProject).loadComponentAndChildren(new NullProgressMonitor());
		
		//We need to reload the model
		String modelRootId = modelRoot.getId();
		modelRoot = Ooaofooa.getInstance(modelRootId, true);
		
		//Getting the instances again
		pkg = Package_c.PackageInstance(modelRoot);
		assertNotNull(pkg);
		
		obj = ModelClass_c.ModelClassInstance(modelRoot);
		assertNotNull(obj);
		
		op = Operation_c.OperationInstance(modelRoot);
		assertNotNull(op);
		
		d = Display.getCurrent();
		while(d.readAndDispatch()){}
		
		//Get File instance of each of the above editors
		pkgFile = UITextTest.getExistingPlaceHolderFromManager(pkg, DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION, this);
		assertNotNull(pkgFile);
		classFile = UITextTest.getExistingPlaceHolderFromManager(obj, DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION, this);
		assertNotNull(classFile);
		opFile = UITextTest.getExistingPlaceHolderFromManager(op, ActivityEditorInputFactory.PLACEHOLDER_EXTENSION, this);
		assertNotNull(opFile);
		
		d = Display.getCurrent();
		while(d.readAndDispatch()){}
		//Confirm that the underlying files exist
		assertTrue(pkgFile.exists());
		assertTrue(classFile.exists());
		assertTrue(opFile.exists());			
	}	
}
