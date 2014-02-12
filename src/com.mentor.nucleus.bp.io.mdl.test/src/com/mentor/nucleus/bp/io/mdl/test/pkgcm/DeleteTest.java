package com.mentor.nucleus.bp.io.mdl.test.pkgcm;

//=====================================================================
//
//File:      $RCSfile: DeleteTest.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/01/17 03:32:36 $
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

import java.util.ConcurrentModificationException;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasEditorUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public abstract class DeleteTest extends PkgCMBaseTest {

    protected PersistableModelComponent pmcBeingTested;

    protected NonRootModelElement meBeingTested;

    protected IFile oldFile;

    // array of all opened editors that should be closed due to delete operation
    // of component under test
    protected Object[] openEditors;
    
    protected GraphicalEditor baseEditor = null;

    protected String compType;

    protected String compName;

    protected int focusedEditor;

    protected int expectedEditorCount;

    public DeleteTest(String project, String name) {
        super(project, name);
    }
    protected void initTest() throws Exception {

        pmcBeingTested = getComponent(compType, compName);
        
        assertNotNull("Could not find component; " + compType + ":" + compName,
                pmcBeingTested);
        Display d = Display.getCurrent();
        
        oldFile = pmcBeingTested.getFile();
                
        if (focusedEditor != EditorTestUtilities.EDITOR_TYPE_NONE) {            
            while (d.readAndDispatch());
            BaseTest.waitForJobs();
            openEditors = openEditors(pmcBeingTested);
            meBeingTested = pmcBeingTested.getRootModelElement();
            assertEquals("Not all required editors opened",expectedEditorCount,openEditors.length);
            // open diagram editor that contains shape of meBeingTested.
           
            baseEditor = CanvasEditorUtils
                    .openEditorWithShapeOf(meBeingTested);

            if (focusedEditor != EditorTestUtilities.EDITOR_TYPE_CANVAS ||baseEditor==null)
                setFocus(openEditors, focusedEditor);
            
            while (d.readAndDispatch());
            assertEquals("Desired editor not focused", focusedEditor,
                    EditorTestUtilities.getEditorType(UITestingUtilities.getActiveEditor()));
        }else{ //focusedEditor==None
            openEditors=new Object[0];
        }
    }

    private Object[] openEditors(PersistableModelComponent component) {
        Vector v = new Vector();
        IEditorPart editor;

        // Opening editors of MEs within component
        editor = EditorTestUtilities.openRandomEditor(component,
                EditorTestUtilities.EDITOR_TYPE_CANVAS, false);
        if (editor != null) {
            v.add(editor);
        }

        editor = EditorTestUtilities.openRandomEditor(component,
                EditorTestUtilities.EDITOR_TYPE_ACTIVITY, false);
        if (editor != null) {
            v.add(editor);
        }

        editor = EditorTestUtilities.openRandomEditor(component,
                EditorTestUtilities.EDITOR_TYPE_DESC, false);
        if (editor != null) {
            v.add(editor);
        }
        // Child components will not be deleted when parent's component file is 
        // deleted throgh resource navigator, so, there is no need to check the
        // child component's editors.
        if (component.getChildren().size() > 0 && ! throughRN) {
            // Opening editors of MEs within components that are children of
            // this component.
            PersistableModelComponent pmc = (PersistableModelComponent) component
                    .getChildren().iterator().next();
            editor = EditorTestUtilities.openRandomEditor(pmc,
                    EditorTestUtilities.EDITOR_TYPE_CANVAS);
            if (editor != null) {
                v.add(editor);
            }

            editor = EditorTestUtilities.openRandomEditor(pmc,
                    EditorTestUtilities.EDITOR_TYPE_ACTIVITY);
            if (editor != null) {
                v.add(editor);
            }

            editor = EditorTestUtilities.openRandomEditor(pmc,
                    EditorTestUtilities.EDITOR_TYPE_DESC);
            if (editor != null) {
                v.add(editor);
            }
        }
        while (Display.getCurrent().readAndDispatch())
            ;
        return v.toArray();
    }

    protected void performDeleteComponentThruME(String compType,
            String compName, int focusedEditor, int expectedEditors)
            throws Exception {
        this.compType = compType;
        this.compName = compName;
        this.focusedEditor = focusedEditor;
        this.expectedEditorCount = expectedEditors;        
        this.throughRN = false;
        initTest();

        if (!toRunTests())
            return;

        selectMEInModelExplorer(oldFile.getFullPath());        
        meBeingTested = pmcBeingTested.getRootModelElement();
        dispatchEvents(0);
        if (compType.equals("SystemModel")){        	
            TestUtil.okToDialog(2000);
        }
        doDeleteThruMExplorer();        
        try{
            while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        }catch(ConcurrentModificationException e){}
    	performDeleteChecks(openEditors, baseEditor, oldFile, meBeingTested);
    }
    protected void performDeleteComponentThruMEGenerics(String compType,
            String compName, int focusedEditor, int expectedEditors)
            throws Exception {
        this.compType = compType;
        this.compName = compName;
        this.focusedEditor = focusedEditor;
        this.expectedEditorCount = expectedEditors;        
        this.throughRN = false;
        initTest();

        if (!toRunTests())
            return;

        selectMEInModelExplorer(oldFile.getFullPath());        
        meBeingTested = pmcBeingTested.getRootModelElement();
        dispatchEvents(0);
        if (compType.equals("SystemModel")){        	
            TestUtil.okToDialog(2000);
        }
        doDeleteThruMExplorer();        
        try{
            while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        }catch(ConcurrentModificationException e){}
    	performDeleteChecksGenerics(openEditors, baseEditor, oldFile, meBeingTested);
    }
    protected void performDeleteComponentThruCE(String compType,
            String compName, int focusedEditor, int expectedEditors)
            throws Exception {
        this.compType = compType;
        this.compName = compName;
        this.focusedEditor = focusedEditor;
        this.expectedEditorCount = expectedEditors;
        this.throughRN = false;

        initTest();
        if (!toRunTests())
            return;

        assertNotNull(baseEditor);

        CanvasEditorUtils.delete(baseEditor, meBeingTested);
        Display display = Display.getCurrent();
        while (display.readAndDispatch())
            ;
        performDeleteChecks(openEditors, baseEditor, oldFile, meBeingTested);
    }
    protected void performDeleteComponentThruCEGenerics(String compType,
            String compName, int focusedEditor, int expectedEditors)
            throws Exception {
        this.compType = compType;
        this.compName = compName;
        this.focusedEditor = focusedEditor;
        this.expectedEditorCount = expectedEditors;
        this.throughRN = false;

        initTest();
        if (!toRunTests())
            return;

        assertNotNull(baseEditor);

        CanvasEditorUtils.delete(baseEditor, meBeingTested);
        Display display = Display.getCurrent();
        while (display.readAndDispatch())
            ;
        performDeleteChecksGenerics(openEditors, baseEditor, oldFile, meBeingTested);
    }
    protected void performDeleteComponentThruRN(String compType,
            String compName, int focusedEditor, int expectedEditors) throws Exception {
    	performDeleteComponentThruRN(compType, compName, focusedEditor,
				expectedEditors, true);
    }
    protected void performDeleteComponentThruRN(String compType,
            String compName, int focusedEditor, int expectedEditors, boolean removed)
            throws Exception {
        this.compType = compType;
        this.compName = compName;
        this.focusedEditor = focusedEditor;
        this.expectedEditorCount = expectedEditors;
        this.throughRN = true;

        initTest();
        if (!toRunTests())
            return;
        dispatchEvents(0);
        doDeleteThruResNav(pmcBeingTested);
        dispatchEvents(0);
        if(!removed)
        	assertTrue("System model file was not restored after deletion in the resource navigator.", pmcBeingTested.getFile().exists());
        else 
	        performDeleteChecks(openEditors, baseEditor, oldFile, meBeingTested);
        while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
    }
}