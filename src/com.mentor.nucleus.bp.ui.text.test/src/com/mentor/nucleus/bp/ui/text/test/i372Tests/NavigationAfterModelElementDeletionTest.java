//=====================================================================
//
//File:      $RCSfile: NavigationAfterModelElementDeletionTest.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/05/10 06:06:15 $
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

package com.mentor.nucleus.bp.ui.text.test.i372Tests;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.test.activity.ActivityEditorInteraction;
import com.mentor.nucleus.bp.ui.text.test.description.DescriptionEditorInteraction;


public class NavigationAfterModelElementDeletionTest extends BaseTest {
	static Ooaofooa modelRoot = null;
	static Operation_c uut;
	static final Display display = Display.getCurrent();
	protected IEditorInput dEditorInput= null;
	protected IEditorInput aEditorInput= null;
	
	public void setUp(){
		modelRoot = BaseTest.getModelRootOfTestDomain();
		uut =  Operation_c.OperationInstance(modelRoot);
		assertNotNull(uut);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
		
	}

    protected void tearDown() throws Exception {
        super.tearDown();        
    }

	public void testNavigationLocationRemovalOnModelElementDeletion(){
		while ( display.readAndDispatch() ) ;
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		DescriptionEditorInteraction.openDescriptionEditor(uut);
		while ( display.readAndDispatch() ) ;
		IEditorPart dEditorPart = page.getActiveEditor();
		if (dEditorPart==null || !(dEditorPart instanceof DescriptionEditor))
		{
			fail("unable to open editor"); //$NON-NLS-1$
		}
		
		
		ActivityEditorInteraction.openActivityEditor(uut);
		while ( display.readAndDispatch() ) ;
		IEditorPart aEditorPart = page.getActiveEditor();
		if (aEditorPart ==null || !(aEditorPart instanceof ActivityEditor))
		{
			fail("unable to open Editor"); //$NON-NLS-1$
		}
		
		INavigationLocation activityLocation = null;
		INavigationLocation descriptionLocation = null;
		
		INavigationLocation locations[]=  page.getNavigationHistory().getLocations();
		for(int i=0; i<locations.length; i++){
			if(locations[i].getInput() == aEditorPart.getEditorInput()){
				activityLocation = locations[i];
			}else if(locations[i].getInput() == dEditorPart.getEditorInput()){
				descriptionLocation = locations[i];
			}
		}
		
		assertNotNull(activityLocation);
		assertNotNull(descriptionLocation);

        Selection.getInstance().clear();
		uut.Dispose();
		while ( display.readAndDispatch() ) ;
		if(modelElementExists(modelRoot,uut))
		{
			fail("model element was not deleted."); //$NON-NLS-1$
		}
		
		locations =  page.getNavigationHistory().getLocations();
		for(int i=0; i<locations.length; i++){
			if(locations[i] == activityLocation){
				fail("navigation location for activity editor not removed"); //$NON-NLS-1$
			}else if(locations[i] == descriptionLocation){
				fail("navigation location for description editor not removed"); //$NON-NLS-1$
			}
		}
	}
	
	 
	public boolean modelElementExists(Ooaofooa modelRoot, Object element){
		InstanceList v =modelRoot.getInstanceList(element.getClass());
		return v.contains(element);
	}
}