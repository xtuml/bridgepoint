//=====================================================================
//
//File:      $RCSfile: AllEditorsDirtyTest.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/05/10 06:01:48 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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
