//=====================================================================
//
//File:      $RCSfile: ModifyContentsTest.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/05/10 05:16:04 $
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

package com.mentor.nucleus.bp.io.mdl.test.pkgcm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasEditorUtils;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

abstract class ModifyContentsTest extends PkgCMBaseTest{
    //array of all opened editors whose title can be effected by  Modify
    //of component under test
    protected Object[] openEditors;
    protected String[] editorTitles;
    protected IFile oldFile;
    protected NonRootModelElement meBeingTested;
    protected PersistableModelComponent pmcBeingTested;
    protected String[] oldEditorContents;
    protected String componentName=null;
    protected String textToChange="// test text \n";
    protected int expectedEditorCount;
    protected int editorToTest;
        
    public ModifyContentsTest(String project, String name) {
        super(project, name);
    }

    protected void initTest(String compType,String compName, int focusedEditor,int expectedEditorCount) throws Exception{
        
        PersistableModelComponent component = getComponent(compType, compName);

        pmcBeingTested=component;
        oldFile=pmcBeingTested.getFile();
        this.expectedEditorCount=expectedEditorCount;
                
        if (focusedEditor==EditorTestUtilities.EDITOR_TYPE_NONE)
        {
            openEditors=new Object[0];
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
        }
        //baseEdior player no role in single file modify test
        else
        {
           openEditors=openEditors(component,expectedEditorCount);
        }
          
        oldEditorContents= new String[openEditors.length];
        editorTitles= new String[openEditors.length];
        
        for (int i = 0; i < openEditors.length; i++) {
            editorTitles[i]=((IEditorPart)openEditors[i]).getTitle();
            if(openEditors[i] instanceof ITextEditor){
                ITextEditor editor=(ITextEditor)openEditors[i];
                IDocument doc =
                    editor.getDocumentProvider().getDocument(editor.getEditorInput());
                   oldEditorContents[i]=doc.get();
            }
        }
        meBeingTested=pmcBeingTested.getRootModelElement();
        componentName=pmcBeingTested.getName();
        editorToTest=focusedEditor;
        Display d = Display.getCurrent();
        while ( d.readAndDispatch() ) ;    
    }
   
    protected void performModifyComponentContents(String compType,String compName, int focusedEditor,int expectedEditorCount) throws Exception {

        initTest(compType, compName, focusedEditor, expectedEditorCount);
        // this pass is for setup workspace next pass will execute actual test
        if (!toRunTests())
            return;
        // clear old messed up mhistory; required for restore tests
        oldFile.clearHistory(null);
        
        changeEditorContents(editorToTest);
        Display display = Display.getCurrent();
        while(display.readAndDispatch());

        // restore from local history
        try {
            IFileState[] history = oldFile.getHistory(null);
            oldFile.setContents(history[history.length-1],IFile.NONE,null);         
            
        } catch (CoreException e) {
              CorePlugin.logError(e.getMessage(), e);
        }
        while(display.readAndDispatch());
        waitForJobs();
        // the content replace will trigger another required upgrade
        // need to let it finish before proceeding
        BaseTest.dispatchEvents(0);
        //get reloaded model element
        pmcBeingTested=PersistenceManager.findComponent(oldFile.getFullPath());
        pmcBeingTested.load(new NullProgressMonitor());
        meBeingTested=pmcBeingTested.getRootModelElement();
        performModifyChecks();

        while(display.readAndDispatch());
    }
    protected void performModifyComponentContentsGenerics(String compType,String compName, int focusedEditor,int expectedEditorCount) throws Exception {

        initTest(compType, compName, focusedEditor, expectedEditorCount);
        // this pass is for setup workspace next pass will execute actual test
        if (!toRunTests())
            return;
        // clear old messed up mhistory; required for restore tests
        oldFile.clearHistory(null);
        
        changeEditorContents(editorToTest);
        Display display = Display.getCurrent();
        while(display.readAndDispatch());

        // restore from local history
        try {
            IFileState[] history = oldFile.getHistory(null);
            oldFile.setContents(history[history.length-1],IFile.NONE,null);         
            
        } catch (CoreException e) {
              CorePlugin.logError(e.getMessage(), e);
        }
        while(display.readAndDispatch());
        waitForJobs();
        //get reloaded model element
        pmcBeingTested=PersistenceManager.findComponent(oldFile.getFullPath());
        meBeingTested=pmcBeingTested.getRootModelElement();
        performModifyChecksGenerics();

        while(display.readAndDispatch());
    }
    //change contents of all editors opened for this test and
    //set focus to editorToTest
    private void changeEditorContents(int editorToTest) {
        for (int i = 0; i < openEditors.length; i++) {
            m_wp.activate((IEditorPart) openEditors[i]);
            if(openEditors[i] instanceof ITextEditor){
                ITextEditor editor=(ITextEditor)openEditors[i];
                IDocument doc =
                    editor.getDocumentProvider().getDocument(editor.getEditorInput());
                   oldEditorContents[i]=doc.get();
                   try {
                       doc.replace(0, 0, textToChange);
                       editor.doSave(null);
                       testEditorContents(editor, textToChange+oldEditorContents[i]);
                   } catch (BadLocationException e) {
                       e.printStackTrace();
                       fail(e.getMessage());
                   }
            }else if(openEditors[i] instanceof GraphicalEditor){
                CanvasEditorUtils.edit((GraphicalEditor)(openEditors[i]));    
            }
        }//end for
        setFocus(openEditors, editorToTest); 
    }

    protected void performModifyChecks() {
        while(Display.getCurrent().readAndDispatch());
        Display.getCurrent().update();
        while(Display.getCurrent().readAndDispatch());
       selectMEInModelExplorer(pmcBeingTested.getFullPath());
       checkTreeItemExistance(meBeingTested,componentName);
       
        for (int i = 0; i < openEditors.length; i++) {
            // editor titles should be same as before test
            assertEquals("Editor title does not match.", editorTitles[i], ((IEditorPart)openEditors[i]).getTitle());
            if(openEditors[i] instanceof ITextEditor){
                testEditorContents((ITextEditor)openEditors[i],oldEditorContents[i]);
            }else if(openEditors[i] instanceof GraphicalEditor){
                validateOrGenerateResults((GraphicalEditor)openEditors[i], generateResult,true);
        }
        } // end for   
        
    }
    protected void performModifyChecksGenerics() {
        while(Display.getCurrent().readAndDispatch());
        Display.getCurrent().update();
        while(Display.getCurrent().readAndDispatch());
       selectMEInModelExplorer(pmcBeingTested.getFullPath());
       checkTreeItemExistance(meBeingTested,componentName);
       
        for (int i = 0; i < openEditors.length; i++) {
            // editor titles should be same as before test
            assertEquals("Editor title does not match.", editorTitles[i], ((IEditorPart)openEditors[i]).getTitle());
            if(openEditors[i] instanceof ITextEditor){
                testEditorContents((ITextEditor)openEditors[i],oldEditorContents[i]);
            }else if(openEditors[i] instanceof GraphicalEditor){
                validateOrGenerateResultsGenerics((GraphicalEditor)openEditors[i], generateResult,true);
        }
        } // end for   
        
    }

    private void testEditorContents(ITextEditor editor,String contentsToCheck) {
        
        IDocument doc =
            editor.getDocumentProvider().getDocument(editor.getEditorInput());
        assertEquals("Contents does not match:",contentsToCheck,doc.get());
    }
}