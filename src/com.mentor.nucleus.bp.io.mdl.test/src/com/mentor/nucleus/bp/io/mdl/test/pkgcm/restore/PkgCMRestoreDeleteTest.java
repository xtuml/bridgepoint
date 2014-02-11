//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
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

package com.mentor.nucleus.bp.io.mdl.test.pkgcm.restore;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.EditorTestUtilities;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMDeleteTest;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class PkgCMRestoreDeleteTest extends PkgCMDeleteTest {

	private String editorTitles[];
	
public PkgCMRestoreDeleteTest(String name) {
	        
        super(projectName, getTestCaseName());
    }

    protected void setupProjectAndTestModel() throws CoreException {
        String path=getProperty("mdlClassPath");
        if(path!=null)
            mdlClassPath=new Path(path);
        reCopy = getProperty("reCopy",reCopy);
        if (!toRunTests()){
            if(reCopy){
                setupProject(projectName);
                CorePlugin.disableParseAllOnResourceChange();
                while(Display.getCurrent().readAndDispatch());
                ensureAvailableAndLoaded(domainName, false, true);
                reCopy=false;
            }
            else{
                setupProject(projectName);
            }
        }
    }
    
    protected void tearDown() throws Exception {
        if(mdlClassPath!=null)
            setProperty("mdlClassPath", mdlClassPath.toString());
        super.tearDown();             
        if (toRunTests() && project.exists()){
        	Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().
        	closeAllEditors(true);
        }
        BaseTest.waitForJobs();
    }

    protected void initTest() throws Exception {
        if(toSetupWorkspace()){
        super.initTest();
        saveTestVariables();
        } else {
        initTestVariables();
    }
        
    }
    protected void performDeleteChecks(Object[] openEditors,
            GraphicalEditor baseEditor, IFile oldFile,
            NonRootModelElement meBeingTested){    	
       if (baseEditor != null) {
            validateOrGenerateResults(baseEditor, generateResult);
            PlatformUI.getWorkbench().getActiveWorkbenchWindow()
            .getActivePage().closeEditor(baseEditor, true);
       }
       Display display = Display.getCurrent();
       while (display.readAndDispatch())
           ;
       String title = null;
       while(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
               .getActivePage().getActiveEditor() != null){
    	   title = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
           .getActivePage().getActiveEditor().getTitle();
    	   assertNotNull(title);
    	   PlatformUI.getWorkbench().getActiveWorkbenchWindow()
           .getActivePage().closeEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                   .getActivePage().getActiveEditor(), true);    
    	   while (display.readAndDispatch())
               ;
       }       
       checkFileDeletion(oldFile);
       checkTreeItemDeletion(meBeingTested);    	
    }
    private void saveTestVariables() {

        int edCount = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getEditorReferences().length;

        //number of opened editors
        setProperty("editorCount", new Integer(edCount).toString());
        setProperty("reCopy",reCopy);
        setProperty("mdlClassPath", oldFile.toString());
        setProperty("compType",compType);
        if(compName == null)
        	setProperty("compName","null");
        else
        	setProperty("compName",compName);
        for (int i = 0; i < openEditors.length; i++){        	
        	if (openEditors[i] instanceof IEditorPart) {
        		IEditorPart editor = (IEditorPart) openEditors[i];
        		setProperty("editroTitle"+String.valueOf(i),editor.getTitle());			
        	}
        }        
        setProperty("editorToTest", focusedEditor);
        
        if (focusedEditor == EditorTestUtilities.EDITOR_TYPE_NONE)
            removeProperty("focusEditorTitle");
        else
            setProperty("focusEditorTitle", PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage()
                    .getActiveEditor().getTitle());

        if (baseEditor != null)
            setProperty("baseEditorTitle", baseEditor.getTitle());
        else
            removeProperty("baseEditorTitle");
    }

    private void initTestVariables() throws Exception {
        
        compType = getProperty("compType");
        compName = getProperty("compName");
        if (compName.equals("null"))
        	compName = null;
        pmcBeingTested=getComponent(compType,compName );
        reCopy = Boolean.valueOf(getProperty("reCopy")).booleanValue();
        meBeingTested = pmcBeingTested.getRootModelElement();
        String title = getProperty("baseEditorTitle");
        focusedEditor=getProperty("editorToTest",-2);
        oldFile = project.getFile(pmcBeingTested.getFullPath().removeFirstSegments(1));
        if (title != null)
            baseEditor = (GraphicalEditor) EditorTestUtilities.findEditor(title, true);

        title = getProperty("focusEditorTitle");
        Integer editorCount = Integer.valueOf(getProperty("editorCount")); 
        editorTitles = new String[20];
        for(int i = 0; i < editorCount.intValue() ; i++){
        	if (getProperty("editroTitle"+String.valueOf(i)) == null){
        		break;
        	}
        	editorTitles[i] = new String(getProperty("editroTitle"+String.valueOf(i)));
        }        
        String actTitle=null;
        if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getActiveEditor()!=null)
            actTitle= PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getActiveEditor().getTitle();
        if(focusedEditor!=EditorTestUtilities.EDITOR_TYPE_NONE){
        assertEquals("Restore focused editor is not desired one.", title,
                actTitle);        
        }
    }
}