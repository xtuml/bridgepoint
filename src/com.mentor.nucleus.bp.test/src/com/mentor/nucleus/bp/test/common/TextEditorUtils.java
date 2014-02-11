//=====================================================================
//
//File:      $RCSfile: TextEditorUtils.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/05/10 05:37:52 $
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

package com.mentor.nucleus.bp.test.common;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.ui.text.TextPlugin;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.ShowActivityAction;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.description.ShowDescriptionAction;

/**
 * Holds utility methods used by the text tests. 
 */
public class TextEditorUtils
{
    /**
     * Opens (and returns) an activity editor on the state of the 
     * given name found under the given model-root.
     */
    public static ActivityEditor openActivityEditor(
        Ooaofooa modelRoot, final String stateName, final String stateEditorTitle)
    {
        ClassQueryInterface_c test = new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                StateMachineState_c selected = (StateMachineState_c)candidate;
                return selected.getName().equals(stateName);
            }
        };
        final StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(
            modelRoot, test);
        try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor)
					throws CoreException {
					IStructuredSelection ss = new StructuredSelection(state);
					ShowActivityAction sda = new ShowActivityAction();
					Action a = new Action() {
					};
					sda.selectionChanged(a, ss);
					sda.run(a);
				}
			};
			ResourcesPlugin.getWorkspace().run(r, null);
		} catch (CoreException x) {
			TestCase.fail("open editor problem");
		}
//        if ( state != null )
//        {
//            ActivityEditorInteraction.openActivityEditor(state);
//        }
        return getActivityEditor(stateEditorTitle);
    }
    static public ActivityEditor getActivityEditor(String title) {

		IWorkbenchWindow[] windows =
			PlatformUI.getWorkbench().getWorkbenchWindows();
		for (int i = 0; i < windows.length; ++i) {
			IWorkbenchPage[] pages = windows[i].getPages();
			for (int j = 0; j < pages.length; ++j) {
				IEditorReference[] editors = pages[j].getEditorReferences();
				for (int k = 0; k < editors.length; ++k) {
					if (editors[k].getTitle().equals(title)) {
						return (ActivityEditor) editors[k].getEditor(false);
					}
				}
			}
		}
		return null;
	}
    
    /**
     * Opens (and returns) a description editor on the domain 
     * associated with the given model-root.
     */
    public static DescriptionEditor openDomainDescriptionEditor(
        Ooaofooa modelRoot)
    {
        final Domain_c domain = Domain_c.DomainInstance(modelRoot);
        
        try
		{
		  IWorkspaceRunnable r = new IWorkspaceRunnable()
		  {
			public void run(IProgressMonitor monitor) throws CoreException
			{
				IStructuredSelection ss = new StructuredSelection(domain);
				ShowDescriptionAction sda = new ShowDescriptionAction();
				Action a = new Action(){};
				sda.selectionChanged(a, ss);
				sda.run( a );
			}
		  };
		  ResourcesPlugin.getWorkspace().run(r, null);
		}
		catch (CoreException x)
		{
		  TestCase.fail("open editor problem");
		}
        //DescriptionEditorInteraction.openDescriptionEditor(domain);
        return getDomainDescriptionEditor(domain);
    }
    
    static public DescriptionEditor getDescriptionEditor( String title )
	{
	
		IWorkbenchWindow [] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for ( int i = 0; i < windows.length; ++i)
		{
			IWorkbenchPage [] pages = windows[i].getPages();
			for ( int j = 0; j < pages.length; ++j )
			{
				IEditorReference [] editors = pages[j].getEditorReferences();
				for ( int k = 0; k < editors.length; ++k )
				{
					if ( editors[k].getTitle().equals(title) )
					{
						return (DescriptionEditor)editors[k].getEditor(false);
					}
				}
			}
		}
		return null;
	}
    
    /**
     * Returns the (presumed) already-open description editor on the 
     * given domain. 
     */
    public static DescriptionEditor getDomainDescriptionEditor(
        Domain_c domain)
    {
        String title = domain.getName() + ": Domain Description";
        return getDescriptionEditor(title);
    }

    /**
     * Opens (and returns) a description editor on the package 
     * associated with the given model-root.
     */
    public static DescriptionEditor openPackageDescriptionEditor(
        Ooaofooa modelRoot, final String expectedName)
    {
        final Package_c pkg = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals(expectedName);
			}
		});
        
        try
		{
		  IWorkspaceRunnable r = new IWorkspaceRunnable()
		  {
			public void run(IProgressMonitor monitor) throws CoreException
			{
				IStructuredSelection ss = new StructuredSelection(pkg);
				ShowDescriptionAction sda = new ShowDescriptionAction();
				Action a = new Action(){};
				sda.selectionChanged(a, ss);
				sda.run( a );
			}
		  };
		  ResourcesPlugin.getWorkspace().run(r, null);
		}
		catch (CoreException x)
		{
		  TestCase.fail("open editor problem");
		}
        return getPackageDescriptionEditor(pkg);
    }
    
    /**
     * Returns the (presumed) already-open description editor on the 
     * given package. 
     */
    public static DescriptionEditor getPackageDescriptionEditor(
        Package_c pkg)
    {
        String title = pkg.getName() + ": Package Description";
        return getDescriptionEditor(title);
    }
    
    /**
     * Adds text to the given editor that will cause a syntax error,
     * then saves the parsed file to generate a problem marker for that error.
     */
    public static void createProblemMarker(ActivityEditor editor)
    {
        // enter some text into the editor that we know will cause
        // a syntax error 
        TextEditorUtils.addTextToEditor(editor, "bad;\n");
        
        // wait for the error to get parsed
		editor.waitForParseThread();
        
        // get the editor's contents saved so that the syntax error
        // produces a marker
        editor.doSave(new NullProgressMonitor());
    }
    
    /**
     * Returns the entire textual contents of the given editor.
     */
    public static String getEditorContents(TextEditor editor)
    {
        IEditorInput input = editor.getEditorInput();
        IDocument document = editor.getDocumentProvider().getDocument(input);
        return document.get();
    }
    
    /**
     * Returns the markers that are associated with the file of the 
     * given editor.
     */
    public static IMarker[] getMarkers(TextEditor editor)
    {
        // find the markers of the editor's associated file
        IFileEditorInput input = (IFileEditorInput)editor.getEditorInput();
        return getMarkers(input.getFile());
    }
    
    /**
     * Returns the markers that are associated with the given file,
     * which does not have to exist.
     */
    public static IMarker[] getMarkers(IFile file)
    {
        // if the given file doesn't exist, there are no markers to return
        if (!file.exists()) return new IMarker[0];
        
        // find the markers associated with the file
        IMarker[] markers = null;
        try {
            markers = file.findMarkers(null, false, 1);
        } catch (CoreException e) {
            TextPlugin.logError("Could not retrieve markers associated with file", e);
            return new IMarker[0];
        }
        
        return markers;
    }
    
    /**
     * A convenience method for closing the given editor.
     */
    public static void closeEditor(IEditorPart editor)
    {
        editor.getSite().getPage().closeEditor(editor, false);
    }
    
    /**
     * Adds the given text to the first line of the given editor.
     */
    public static void addTextToEditor(TextEditor editor, String text)
    {
        IDocument document = editor.getDocumentProvider().getDocument(
            editor.getEditorInput());
        try {
            document.replace(0, 0, text);
        } catch (BadLocationException e) {
            TextPlugin.logError("Could not add text to editor", e);
        }
    }
}
