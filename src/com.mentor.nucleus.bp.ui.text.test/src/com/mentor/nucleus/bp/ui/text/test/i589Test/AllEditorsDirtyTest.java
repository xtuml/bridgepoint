//=====================================================================
//
//File:      $RCSfile: AllEditorsDirtyTest.java,v $
//Version:   $Revision: 1.9 $
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;


public class AllEditorsDirtyTest extends UITextTest {
	public AllEditorsDirtyTest(String projectName, String name) throws CoreException {
		super(null, name);
	}

	public AllEditorsDirtyTest(String name) throws CoreException {
		super(null, name);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testMakeAllEditorsDirty()
	{
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
					if (editors[k].getPart(false) instanceof ActivityEditor 
							|| editors[k].getPart(false) instanceof DescriptionEditor ) 
					{
						TextEditor editor =(TextEditor)editors[k].getPart(false); 
						IDocument doc =editor.getDocumentProvider().getDocument(editor.getEditorInput());
						doc.set(editor.getTitle());
						assertTrue( editor.isSaveOnCloseNeeded() );
						assertTrue( editor.isDirty() );	
					}
				}
			}
		}
	}
}
