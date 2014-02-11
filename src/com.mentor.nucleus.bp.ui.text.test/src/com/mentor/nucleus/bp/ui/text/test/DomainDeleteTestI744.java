//=====================================================================
//
//File:      $RCSfile: DomainDeleteTestI744.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/05/10 06:02:35 $
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
package com.mentor.nucleus.bp.ui.text.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.text.placeholder.PlaceHolderEntry;
import com.mentor.nucleus.bp.ui.text.test.activity.ActivityEditorInteraction;
import com.mentor.nucleus.bp.ui.text.test.description.DescriptionEditorInteraction;

public class DomainDeleteTestI744 extends UITextTest{
    
    /**
     * The name of the test domain used during these tests.
     */
    private static final String testModelName = "testDescrip1";

	public DomainDeleteTestI744(String projectName, String name) throws CoreException {
		super(null, name);	 //$NON-NLS-1$
	}

    public DomainDeleteTestI744(String name) throws CoreException {
		super(null, name);	 //$NON-NLS-1$
	}
    
    protected void setUp() throws Exception {
    	super.setUp();
    	
    	// close all existing editors.
    	PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);

    	loadProject(testModelName);
    }
    
    public void testPackageDelete() throws Exception {
        Ooaofooa.setInUnitTest(true);
		Operation_c op = Operation_c.OperationInstance(modelRoot);
		assertNotNull(op);
		
		DescriptionEditorInteraction.openDescriptionEditor(op);

		IEditorPart ed = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		assertNotNull(ed);

		//Checking the instance exists in the map
		IFile dscFile = UITextTest.getExistingPlaceHolderFromManager(op, "dsc", this); //$NON-NLS-1$
		assertNotNull(dscFile);
		
		//The underlying file must not exist
		assertFalse(dscFile.exists()); 
		
		//adding a new marker
		IMarker marker = createNewMarker(dscFile, 1, "This is a test bookmark", IMarker.BOOKMARK); //$NON-NLS-1$
		assertNotNull(marker);
		
		Display d = Display.getCurrent();
		while(d.readAndDispatch()){}
		//The underlying should exist
		assertTrue(dscFile.exists());		

		ActivityEditorInteraction.openActivityEditor(op);
		
		ed = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		assertNotNull(ed);
		
		//Checking the instance exists in the map
		IFile oalFile = UITextTest.getExistingPlaceHolderFromManager(op, "oal", this); //$NON-NLS-1$
		assertNotNull(oalFile);
		
		//The underlying file must not exist
		assertFalse(oalFile.exists()); 
		
		//adding a new marker
		marker = createNewMarker(oalFile, 1, "This is error", IMarker.PROBLEM); //$NON-NLS-1$
		assertNotNull(marker);
		
		while(d.readAndDispatch()){}
		//The underlying should exist
		assertTrue(oalFile.exists());		
		
		Package_c[] pkgs = Package_c.PackageInstances(modelRoot);

		StructuredSelection sel = new StructuredSelection(pkgs);
        Selection.getInstance().setSelection(sel);
        DeleteAction t2 = new DeleteAction(null);
        TransactionManager.getSingleton().disableDialog = true;
        t2.run();
        TransactionManager.getSingleton().disableDialog = false;

        Thread.sleep(1000);  // let PlaceHolder delete thread run
        
        while ( d.readAndDispatch() )  ;    	

		waitForThread(PlaceHolderEntry.DELETE_THREAD_NAME);    	
    
		//The underlying file must not exist
		assertFalse(dscFile.exists()); 
		//The underlying file must not exist
		assertFalse(oalFile.exists()); 
		
		IEditorReference[] editorRefs =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		// No editors should be open
		assertTrue(editorRefs.length == 0);
		
		// empty log is checked by teardown of super test class. 
        Ooaofooa.setInUnitTest(false);
    }
    
    public static void waitForThread(String threadName)
	{
		int num_threads = Thread.activeCount();
		Thread [] t_set = new Thread[num_threads];
		Thread.enumerate( t_set );
		for ( int i = 0; i < num_threads; ++i)
		{
			if ( t_set[i] != null && t_set[i].getName() != null && t_set[i].getName().equals(threadName))
			{
                Display d = Display.getCurrent();
                while(t_set[i].isAlive()){
                      while(d.readAndDispatch()){}
                }                                               
                return;
			}
		}
	}     
}
