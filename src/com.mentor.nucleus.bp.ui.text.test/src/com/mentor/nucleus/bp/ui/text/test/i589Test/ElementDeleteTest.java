//=====================================================================
//
//File:      $RCSfile: ElementDeleteTest.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/05/10 06:01:48 $
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

package com.mentor.nucleus.bp.ui.text.test.i589Test;


import java.io.File;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.text.AbstractModelElementEditorInput;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;

public class ElementDeleteTest extends UITextTest {
	
	private static boolean firstSetup = true;
	private static String testModelName = "testDescrip1";
	
	public ElementDeleteTest(String projectName, String name) throws CoreException {
		super(null, name);
	}

	public ElementDeleteTest(String name) throws CoreException {
		super(null, name);
	}

    protected void setUp() throws Exception {
        super.setUp();
        if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

	public void testDeleteElement() throws Exception
		{
        Ooaofooa.setInUnitTest(true);
        while(Display.getCurrent().readAndDispatch());
		Package_c[] pkgs = Package_c.PackageInstances(BaseTest.getModelRootOfTestDomain());
		for(int i=0; i<pkgs.length; i++){
            TransactionManager manager = pkgs[i].getTransactionManager();
            manager.disableDialog = true;
            Transaction t = manager.startTransaction("deleting domain thru tests", Ooaofooa.getDefaultInstance());
			pkgs[i].Dispose();
            manager.endTransaction(t);
            manager.disableDialog = false;
		}

		BaseTest.dispatchEvents(100);
		
		List openEditorInputs = new Vector();
		
		IWorkbenchWindow[] windows =
			PlatformUI.getWorkbench().getWorkbenchWindows();
		for (int i = 0; i < windows.length; ++i) 
		{
			IWorkbenchPage[] pages = windows[i].getPages();
			for (int j = 0; j < pages.length; ++j) 
			{
				IEditorReference[] editors = pages[j].getEditorReferences();
				for (int k = 0; k < editors.length; ++k) 
				{
					IEditorPart editor = null;
					
					if (editors[k].getPart(false)instanceof ActivityEditor)
					{	
						editor = (IEditorPart)editors[k].getPart(false);
						openEditorInputs.add(editor.getEditorInput());
					}
					else if (editors[k].getPart(false)instanceof DescriptionEditor) 
					{
						editor = (IEditorPart)editors[k].getPart(false);
						openEditorInputs.add(editor.getEditorInput());
					}
					else
					{
						continue;
					}
				}
			}
		}
		
		if(openEditorInputs.size() > 0){
			StringBuffer s = new StringBuffer();
			for(int i=0; i<openEditorInputs.size(); i++){
				AbstractModelElementEditorInput input = (AbstractModelElementEditorInput)openEditorInputs.get(i);
				s.append("File name:" + input.getFile() + " Input:" + input.getModelElementID().getType() + "\r\n");  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			}
			
			IPath path = BaseTest.getModelRootOfTestDomain().getResourcePath();	
			IResource members[] = null;
		    IWorkspace myWorkspace = ResourcesPlugin.getWorkspace();
		    IWorkspaceRoot myWorkspaceRoot = myWorkspace.getRoot();
		    IContainer cons[] = myWorkspaceRoot.findContainersForLocation(path);
		    if (cons.length == 1)
		    {
				  try
				  {
					members = cons[0].members();
				  }
				  catch (CoreException e)
				  {
				  	fail(e.getMessage());
				  }
		    }
		    boolean found = false;
		    for (int index =0;index<members.length;index++)
		    {
		    	String fileExtension = (members[index] instanceof IFile)?members[index].getFileExtension():null;
		    	if(fileExtension != null && (fileExtension.equals("dsc") || fileExtension.equals("oal"))) //$NON-NLS-1$ //$NON-NLS-2$
		    	{
		    		if(!found)
		    		{
		    			found = true;
		    		}
		    		System.out.println(members[index].getName());
		    	}
		    }
			if(found)
			{
				fail(s.toString());
			}
			fail( openEditorInputs.size() + "  Editors open.");
		}
        Ooaofooa.setInUnitTest(false);		
	}
}

