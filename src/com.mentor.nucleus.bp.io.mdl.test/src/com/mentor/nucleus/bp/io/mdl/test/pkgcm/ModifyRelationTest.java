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
package com.mentor.nucleus.bp.io.mdl.test.pkgcm;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.test.TigerNatureTestGenerics;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

abstract class ModifyRelationTest extends PkgCMBaseTest {
    // array of all opened editors whose title can be effected by Modify
    // of component under test
    protected Object[] openEditors;

    protected IFile oldTestingFile;

    protected IFile oldDeletedFile;

    protected IFile oldRenamedFile;

    protected PersistableModelComponent pmcBeingRenamed;

    protected PersistableModelComponent pmcBeingDeleted;

    protected PersistableModelComponent pmcBeingCreated;

    protected PersistableModelComponent pmcBeingTested;

    protected Class meClassBeingDeleted;

    protected Class meClassBeingRenamed;

    protected GraphicalEditor baseEditor = null;

    protected String newName;

    protected String oldName;

    protected String shapeToCreate;

    protected String editorTitles[];

    protected int childrenCount;

    protected int oldProxyRefs;

    protected boolean testOnlyDelete = false;

    protected  boolean testCanvas;
    protected int expectedEditorCount;
    
    public ModifyRelationTest(String project, String name) {
        super(project, name);
    }

    protected void initTest(String compType,String compName, String[] testNames) throws Exception {

        pmcBeingTested = getComponent(compType, compName);
        oldTestingFile = pmcBeingTested.getFile();
        childrenCount = pmcBeingTested.getChildrenCount();
        
        Object[] children = null;
        if (testOnlyDelete) {
            children = pmcBeingTested.getChildren().toArray();
            assertTrue(
                    "Component should have atleast one editable child components",
                    children.length >= 1);
        } else {
            children = filterEditable(pmcBeingTested.getChildren().toArray());
            assertTrue(
                    "Component should have atleast two editable child components",
                    children.length >= 2);
        }

        pmcBeingDeleted = getPMCByElementName(children, testNames[0]);
        oldDeletedFile = pmcBeingDeleted.getFile();
        meClassBeingDeleted = pmcBeingDeleted.getRootModelElement().getClass();

        if (!testOnlyDelete) {
            pmcBeingRenamed = getPMCByElementName(children, testNames[1]);
            meClassBeingRenamed = pmcBeingRenamed.getRootModelElement()
                    .getClass();
            oldRenamedFile = pmcBeingRenamed.getFile();
            oldName = pmcBeingRenamed.getName();
            newName = oldName + "_n";
        }

        openEditors(); //open all editors
        baseEditor = (GraphicalEditor) EditorTestUtilities.openRandomEditor(
                pmcBeingTested,
                EditorTestUtilities.EDITOR_TYPE_CANVAS);
        if (baseEditor != null)
            m_wp.activate(baseEditor);

        editorTitles = new String[openEditors.length];
        for (int i = 0; i < openEditors.length; i++) {
            editorTitles[i] = ((IEditorPart) openEditors[i]).getTitle();

        }

        Display d = Display.getCurrent();
        while (d.readAndDispatch())
            ;
    }
    private PersistableModelComponent getPMCByElementName(Object[] children, String name) {
    	PersistableModelComponent result = null;
    	for (int i=0; i < children.length; i++) {
    		if (children[i] instanceof PersistableModelComponent) {
    		  if (((PersistableModelComponent)children[i]).getName().equals(name)) {
    			result = (PersistableModelComponent)children[i];
    			break;
    		  }
    		}
    	}
    	assertNotNull("PMC for test not found.", result);
    	return result;
    }

    protected void openEditors() {
        
        Object[] o1= openEditors(pmcBeingRenamed, 0, false);
        Object[] o2= openEditors(pmcBeingDeleted, 0, false);
        openEditors=new Object[o1.length+o2.length];
                
        System.arraycopy(o1, 0, openEditors,0,o1.length);
        System.arraycopy(o2, 0, openEditors,o1.length,o2.length);        
        
        assertEquals("All editors did not open", expectedEditorCount,openEditors.length );
        
    }
    
/**
 * Perform test on given type of component with given name
 * @param compType Type of the component
 * @param compName Optional, specify it if some specific component is being tested
 * @param shapeToCreate Type of new component that will be created.
 * @param testOnlyDelete If rename can't be performed on child like ISM then 
 * set this true. 
 * @param testCanvas if compType doesn't own canvas set this to false 
 * @throws Exception
 */
    protected void performModifyComponent(String compType,String compName,
            String shapeToCreate,boolean testOnlyDelete, boolean testCanvas, int expectedEditor, String[] testNames) throws Exception {
        this.shapeToCreate = shapeToCreate;
        this.testOnlyDelete = testOnlyDelete;
        this.testCanvas=testCanvas;
        this.expectedEditorCount = expectedEditor;
        initTest(compType,compName, testNames);
        
        if (!toRunTests())
            return;
        
        if(testCanvas)
            assertNotNull("Canvas editor with shape not opened; "
                    + compType + " : " + compName,baseEditor);
        // perform 3 changes
        // 1
        NonRootModelElement meBeingDeleted = pmcBeingDeleted.getRootModelElement(); 
        Selection.getInstance().setSelection(
                new StructuredSelection(meBeingDeleted));
        Action delete = CorePlugin.getDeleteAction();
        delete.run();
        while (Display.getCurrent().readAndDispatch())
            ;
        checkFileDeletion(pmcBeingDeleted.getFile());
        checkTreeItemDeletion(meBeingDeleted);

        // 2
        if (!testOnlyDelete) {
            Selection.getInstance().setSelection(
                    new StructuredSelection(pmcBeingRenamed
                            .getRootModelElement()));
            doRenameThruCanvasEditor(newName);
            while (Display.getCurrent().readAndDispatch())
                ;
            checkFileRename(pmcBeingRenamed, newName);
            checkTreeItemExistance(pmcBeingRenamed.getRootModelElement(),
                    newName);
        }
        // 3
        if (baseEditor != null) {
            pmcBeingCreated = PersistenceManager.getDefaultInstance()
                    .getComponent(createShape(baseEditor, shapeToCreate));
        } else if (shapeToCreate.equals("Domain")) {
            pmcBeingCreated = TigerNatureTestGenerics.createNewDomain("New_Domain",
                    (SystemModel_c) pmcBeingTested.getRootModelElement());
        }

        Display display = Display.getCurrent();
        while (display.readAndDispatch())
            ;

        if (pmcBeingCreated != null)
            performCreateChecks(null, pmcBeingCreated);

        restoreFile(oldTestingFile);
        while(Display.getCurrent().readAndDispatch());

        performModifyChecks();

    }
    protected void performModifyComponentGenerics(String compType,String compName,
            String shapeToCreate,boolean testOnlyDelete, boolean testCanvas, int expectedEditor, String [] testNames) throws Exception {
        this.shapeToCreate = shapeToCreate;
        this.testOnlyDelete = testOnlyDelete;
        this.testCanvas=testCanvas;
        this.expectedEditorCount = expectedEditor;
        initTest(compType,compName, testNames);
        
        if (!toRunTests())
            return;
        
        if(testCanvas)
            assertNotNull("Canvas editor with shape not opened; "
                    + compType + " : " + compName,baseEditor);
        // perform 3 changes
        // 1
        NonRootModelElement meBeingDeleted = pmcBeingDeleted.getRootModelElement(); 
        Selection.getInstance().setSelection(
                new StructuredSelection(meBeingDeleted));
        Action delete = CorePlugin.getDeleteAction();
        delete.run();
        while (Display.getCurrent().readAndDispatch())
            ;
        checkFileDeletion(pmcBeingDeleted.getFile());
        checkTreeItemDeletion(meBeingDeleted);

        // 2
        if (!testOnlyDelete) {
            Selection.getInstance().setSelection(
                    new StructuredSelection(pmcBeingRenamed
                            .getRootModelElement()));
            doRenameThruCanvasEditor(newName);
            while (Display.getCurrent().readAndDispatch())
                ;
            checkFileRename(pmcBeingRenamed, newName);
            checkTreeItemExistance(pmcBeingRenamed.getRootModelElement(),
                    newName);
        }
        // 3
        if (baseEditor != null) {
        	if (shapeToCreate.equals("Class"))
        	{ pmcBeingCreated = PersistenceManager.getDefaultInstance()
        	      .getComponent(createShape(baseEditor,"Classes",shapeToCreate));
        	}
        	else
        	{
        		pmcBeingCreated = PersistenceManager.getDefaultInstance()
      	      .getComponent(createShape(baseEditor,shapeToCreate));
        	}
        } else if (shapeToCreate.equals("Domain")) {
            pmcBeingCreated = TigerNatureTestGenerics.createNewDomain("New_Domain",
                    (SystemModel_c) pmcBeingTested.getRootModelElement());
        }

        Display display = Display.getCurrent();
        while (display.readAndDispatch())
            ;

        if (pmcBeingCreated != null)
            performCreateChecksGenerics(null, pmcBeingCreated);

        restoreFile(oldTestingFile);
        while(Display.getCurrent().readAndDispatch());

        performModifyChecksGenerics();

    }
    protected void performModifyChecks() {
        if (pmcBeingCreated != null) {
            // as discussed in issue 59
            checkTreeItemExistance(pmcBeingCreated.getRootModelElement(),
                    pmcBeingCreated.getName());
        }
        if (baseEditor != null)
            ;
        resultPostfix = "withDangling";
        validateOrGenerateResults(baseEditor, generateResult, false);

        // renamed component should not be effected
        if (!testOnlyDelete) {
            NonRootModelElement me = findProxy(meClassBeingRenamed,
                    pmcBeingRenamed.getFullPath());
            checkTreeItemExistance(me, newName);
            checkFileExistance(pmcBeingRenamed);
        }

        NonRootModelElement me = findProxy(meClassBeingDeleted, oldDeletedFile
                .getFullPath());
        assertNull(me);

        restoreFile(oldDeletedFile);
        pmcBeingDeleted = PersistenceManager.findComponent(oldDeletedFile
                .getFullPath());
        assertNotNull("Child component does not exist after restore",
                pmcBeingDeleted);
        m_bp_tree.refresh();
        selectMEInModelExplorer(pmcBeingDeleted.getRootModelElement());
        
        TreeItem item = m_bp_tree.getTree().getSelection()[0];
        assertTrue(
                "Dangling refrence does not got alived after restore, No + in ME",
                item.getItemCount() > 0);
        assertFalse(
                "Dangling refrence does not got alived after restore, isProxy=true",
                ((NonRootModelElement) item.getData()).isProxy());
        editorTitlesTest();
    }
    protected void performModifyChecksGenerics() {
        if (pmcBeingCreated != null) {
        	// restoring the original parent file will not cause
        	// the element to be removed if that element is a
        	// file root, only the graphics skip this test if true
        	if(pmcBeingCreated == pmcBeingTested) {
            checkTreeItemDeletion(pmcBeingCreated.getRootModelElement());
        }
        }
        if (baseEditor != null)
            ;
        resultPostfix = "withDangling";
        validateOrGenerateResultsGenerics(baseEditor, generateResult, false);

        // renamed component should not be effected
        if (!testOnlyDelete) {
            NonRootModelElement me = findProxy(meClassBeingRenamed,
                    pmcBeingRenamed.getFullPath());
            checkTreeItemExistance(me, newName);
            checkFileExistance(pmcBeingRenamed);
        }

        NonRootModelElement me = findProxy(meClassBeingDeleted, oldDeletedFile
                .getFullPath());
        assertNull(me);

        restoreFile(oldDeletedFile);
        pmcBeingDeleted = PersistenceManager.findComponent(oldDeletedFile
                .getFullPath());
        assertNotNull("Child component does not exist after restore",
                pmcBeingDeleted);
        m_bp_tree.refresh();
        selectMEInModelExplorer(pmcBeingDeleted.getRootModelElement());
        
        TreeItem item = m_bp_tree.getTree().getSelection()[0];
        assertTrue(
                "Dangling refrence does not got alived after restore, No + in ME",
                item.getItemCount() > 0);
        assertFalse(
                "Dangling refrence does not got alived after restore, isProxy=true",
                ((NonRootModelElement) item.getData()).isProxy());
        editorTitlesTestGenerics();
    }
    private void checkDangling(NonRootModelElement me) {
        assertTrue("Given ME is not a proxy.", me.isProxy());

    }
    private void editorTitlesTest(){
    	Display display = Display.getCurrent();
    	if (baseEditor != null) {
    		resultPostfix = "withoutDangling";
    		validateOrGenerateResults(baseEditor, generateResult, false);
            PlatformUI.getWorkbench().getActiveWorkbenchWindow()
            .getActivePage().closeEditor(baseEditor.getParentEditor(), true);
       }
       
       while (display.readAndDispatch())
           ;
       int i = 0;
       String title = null;
       while(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
               .getActivePage().getActiveEditor() != null){
    	   title = UITestingUtilities.getActiveEditor().getTitle();
    	   assertNotNull(title);
    	   if(title.indexOf(':') > -1)
    	   title = title.substring(title.indexOf(':') + 2, title.length());
    	   i = 0;
    	   while(!editorTitles[i].substring(editorTitles[i].indexOf(':') + 2, editorTitles[i].length()).
    			   equals(title)){
    		   if (i + 1 > editorTitles.length || editorTitles[i+1] == null)
    			   assertTrue(false);
    		   i++;    		   
    	   }
    	   PlatformUI.getWorkbench().getActiveWorkbenchWindow()
           .getActivePage().closeEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                   .getActivePage().getActiveEditor(), true);    
    	   while (display.readAndDispatch())
               ;
    	   i++;
       }       
    }

    private void editorTitlesTestGenerics(){
    	Display display = Display.getCurrent();
    	if (baseEditor != null) {
    		resultPostfix = "withoutDangling";
    		validateOrGenerateResultsGenerics(baseEditor, generateResult, false);
            PlatformUI.getWorkbench().getActiveWorkbenchWindow()
            .getActivePage().closeEditor(baseEditor.getParentEditor(), true);
       }
       
       while (display.readAndDispatch())
           ;
       int i = 0;
       String title = null;
       while(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
               .getActivePage().getActiveEditor() != null){
    	   title = UITestingUtilities.getActiveEditor().getTitle();
    	   assertNotNull(title);
    	   if(title.indexOf(':') > -1)
    	   title = title.substring(title.indexOf(':') + 2, title.length());
    	   i = 0;
    	   while(!editorTitles[i].substring(editorTitles[i].indexOf(':') + 2, editorTitles[i].length()).
    			   equals(title)){
    		   if (i + 1 > editorTitles.length || editorTitles[i+1] == null)
    			   assertTrue(false);
    		   i++;    		   
    	   }
    	   PlatformUI.getWorkbench().getActiveWorkbenchWindow()
           .getActivePage().closeEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                   .getActivePage().getActiveEditor(), true);    
    	   while (display.readAndDispatch())
               ;
    	   i++;
       }       
    }

    private Object[] filterEditable(Object[] components) {
        Vector editable = new Vector();
        for (int i = 0; i < components.length; i++) {
            Object me = ((PersistableModelComponent) components[i])
                    .getRootModelElement();
            StructuredSelection sel = new StructuredSelection(me);
            Selection.getInstance().setSelection(sel);
            if (RenameAction.canRenameAction())
                editable.add(components[i]);
        }
        return editable.toArray();
    }

    NonRootModelElement findProxy(Class type, IPath path) {
        List list = modelRoot.getInstanceList(type);
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            NonRootModelElement element = (NonRootModelElement) iterator.next();
            if (path.toString().equals(element.getContent()))
                return element;
        }

        return null;
    }
}