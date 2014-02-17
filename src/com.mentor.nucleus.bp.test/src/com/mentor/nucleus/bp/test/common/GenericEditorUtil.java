//=====================================================================
//
//File:      $RCSfile: GenericEditorUtil.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:21:31 $
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

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.ExplorerUtil.GenericModelElementCriteria;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.graphics.actions.OpenGraphicsEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditorInput;
import com.mentor.nucleus.bp.ui.text.AbstractModelElementEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.AbstractModelElementTextEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.activity.ShowActivityAction;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInput;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.description.ShowDescriptionAction;

/**
 * @author araza
 * 
 */
public class GenericEditorUtil {
    public final static int EDITOR_TYPE_NONE=-1;
	public final static int EDITOR_TYPE_DESC = 0;

	public final static int EDITOR_TYPE_ACTIVITY = 1;

	public final static int EDITOR_TYPE_CANVAS = 2;
	
    public static final String YES_BUTTON="&Yes";
    public static final String NO_BUTTON="&No";
    public static final String CANCEL_BUTTON="&Cancel";
    public static final String OK_BUTTON="OK";
    
	public static Object[] openEditorsForRename(NonRootModelElement rootME) {

		Vector openedEditors=new Vector();
		
		Object[] clsz=RenameMElementDependency.getRenameDependentElements(rootME.getClass());
		IPersistenceHierarchyMetaData metaData = PersistenceManager
		.getHierarchyMetaData();
		
		for (int i = 0; i < clsz.length; i++) {
			Class cls=	(Class)clsz[i];
			NonRootModelElement me=null;
			if(i==0)
			 me=rootME;
			else
            {
			 List children=metaData.getChildrenComponentRootModelElements(rootME,cls );
             if (children.size()>0)
                 me=(NonRootModelElement)children.get(0);
            }
		TestCase.assertNotNull("Dependent ME '"+cls.getName()+"' of '"+rootME.getClass().getName()+"' not found", me);
		
		Vector e=new Vector();
        List suppEditors=getSupportedEditors(me);
        
        for (Iterator iter = suppEditors.iterator(); iter.hasNext();) {
            int editorType = ((Integer)iter.next()).intValue();
            IEditorPart ed=openRandomEditor(me, editorType);
            TestCase.assertNotNull("Required editor could not opened: "+me+editorType, ed);
            e.add(ed);
		}
        		
		openedEditors.addAll(e);
		}
		return openedEditors.toArray();
	}

	static public IEditorPart openRandomEditor(PersistableModelComponent component, int editorType) {
		return openRandomEditor(component, editorType, false);
	}

	static public IEditorPart openRandomEditor(PersistableModelComponent component, int editorType, boolean fromChildComponents) {
		GenericModelElementCriteria criteria = new GenericModelElementCriteria();
		criteria.init(editorType);
		
		TreeItem treeItem = null; 
		
		if(fromChildComponents){
            //TODO this has some problem, hangs in infinite loop
			treeItem = ExplorerUtil.findChildComponentTreeItemInMEFor(component, criteria);
		}else{
			treeItem = ExplorerUtil.findTreeItemInMEFor(component, criteria);
		}
		
		if(treeItem==null)
		return null;
        TestCase.assertFalse("Model Elemenet '"+TestingUtilities.getName((NonRootModelElement)treeItem.getData())+"' is orphaned",((NonRootModelElement)treeItem.getData()).isOrphaned());
		IActionDelegate action = null;

		switch (editorType) {
		case EDITOR_TYPE_DESC:
			action = new ShowDescriptionAction();
			break;
		case EDITOR_TYPE_ACTIVITY:
			action = new ShowActivityAction();
			break;
		case EDITOR_TYPE_CANVAS:
			action = new OpenGraphicsEditor();
			break;
		}
		
		if(action != null){
			IStructuredSelection selection = new StructuredSelection(treeItem.getData());
			final IActionDelegate act = action;
			final Action a = new Action() {};
			Selection.getInstance().setSelection(selection);
			act.selectionChanged(a, selection);
			
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					act.run(a);
				}
			};
			try {
				CorePlugin.getWorkspace().run(r, null);
                while(Display.getCurrent().readAndDispatch());
                IEditorPart editor= PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getActiveEditor();
                
                Assert.assertEquals("Required editor not opened",editorType,getEditorType(editor));
                return editor;
			} catch (CoreException e) {
				CorePlugin.logError("open editor problem", e);
			}
		}

		return null;
	}
	
	
	/**
	 * opens given type of editor for given ME, activates and returns existing
	 * editor if one already exists for same ME
	 * 
	 * @param uut,
	 *            NonRootModelElement for which editor need to be opened
	 * @param editorType,
	 *            Type of editor to to be opened
	 */
	static public IEditorPart openRandomEditor(NonRootModelElement rootME, final int editorType) {
		TestCase.assertNotNull(rootME);
		
		IActionDelegate action = null;
		IStructuredSelection selection = null;
		
		switch (editorType) {
		case EDITOR_TYPE_DESC:
			List mes = findMEsWithText(rootME, DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION);
			if(mes.size() > 0){
				selection = new StructuredSelection(mes.get(0));
				action = new ShowDescriptionAction();
			}
			break;
		case EDITOR_TYPE_ACTIVITY:
			mes = findMEsWithText(rootME, ActivityEditorInputFactory.PLACEHOLDER_EXTENSION);
			if(mes.size() > 0){
				selection = new StructuredSelection(mes.get(0));
				action = new ShowActivityAction();
			}
			break;
		case EDITOR_TYPE_CANVAS:
			NonRootModelElement me = rootME;//findMEWithModelInstance(rootME);
			if(me != null){
				selection = new StructuredSelection(me);
				action = new OpenGraphicsEditor();
			}
			break;
		}
		
		if(action != null){
			final IActionDelegate act = action;
			final Action a = new Action() {};
			Selection.getInstance().setSelection(selection);
			act.selectionChanged(a, selection);
			
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					act.run(a);
				}
			};
			try {
				CorePlugin.getWorkspace().run(r, null);
                while(Display.getCurrent().readAndDispatch());
				IEditorPart editor= PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
                
                Assert.assertEquals("Required editor not opened",editorType,getEditorType(editor));
                return editor;
			} catch (CoreException e) {
				CorePlugin.logError("open editor problem", e);
			}
		}

		return null;
	}	

	static public IEditorPart findEditor(String title, boolean restoreEditor) {

		IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		for (int i = 0; i < windows.length; ++i) {
			IWorkbenchPage[] pages = windows[i].getPages();
			for (int j = 0; j < pages.length; ++j) {
				IEditorReference[] editors = pages[j].getEditorReferences();
				for (int k = 0; k < editors.length; ++k) {
					if (editors[k].getTitle().equals(title)) {
						return editors[k].getEditor(restoreEditor);
					}
				}
			}
		}
		return null;
	}
	
	static public boolean supportsEditor(NonRootModelElement element, int editorType){
		switch(editorType){
		case EDITOR_TYPE_CANVAS:
		    Ooaofgraphics modelRoot = Ooaofgraphics.getInstance(element.getModelRoot().getId()); 
            Model_c[] diagrams = Model_c.ModelInstances(modelRoot);
            for (int i = 0; i < diagrams.length; i++) 
                if (diagrams[i].inSameComponent(element, diagrams[i])) 
                    return true;
			break;
		case EDITOR_TYPE_DESC:
			AbstractModelElementEditorInputFactory factory = DescriptionEditorInputFactory.getDefaultInstance();
			return (factory.isSupported(element) && factory.getRequiredModelElement(element) != null);
		case EDITOR_TYPE_ACTIVITY:
			factory = ActivityEditorInputFactory.getDefaultInstance();
			return (factory.isSupported(element) && factory.getRequiredModelElement(element) != null);
		}
		return false;
	}
	
	
	static protected boolean isOpen(IEditorPart part){
		IEditorReference[] editorRefs =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
        for (int i = 0; i < editorRefs.length; i++) {
			if(editorRefs[i].getEditor(false) == part){
				return true;
			}
		}
        return false;
	}
	
    static public boolean isEditorSupported(NonRootModelElement me, int editorTypeToTest)
    {
        List l=getSupportedEditors(me);
        if (l.contains(new Integer(editorTypeToTest)))
            return true;
        else
            return false;
    }
    static public List getSupportedEditors(NonRootModelElement me)
    {
        TestCase.assertNotNull(me);
        
        Vector editors= new Vector();
        
        
        AbstractModelElementEditorInputFactory factory = null;
        
            factory = ActivityEditorInputFactory.getDefaultInstance();
            if (factory.isSupported(me))
                editors.add( new Integer(EDITOR_TYPE_ACTIVITY));
            
            factory = DescriptionEditorInputFactory.getDefaultInstance();
            if (factory.isSupported(me))
                editors.add( new Integer(EDITOR_TYPE_DESC));
            
            if(isCanvasEditorSupported(me))
            editors.add( new Integer(EDITOR_TYPE_CANVAS));
        return editors;
    }
	
	protected static boolean isCanvasEditorSupported(NonRootModelElement me) {
        ModelSpecification_c[] modelSpecs = ModelSpecification_c.ModelSpecificationInstances(Ooaofgraphics.getDefaultInstance());
        for (int i = 0; i < modelSpecs.length; i++) {
          if (modelSpecs[i].getRepresents() == me.getClass())
              return true;
        }
        return false;
    }

    /**
     * This funcation take desired parameters and returns expected title
     * @param componentRoot ME for which to generate Title
     * @param newName new name of ME, when componentRoot is ISM or CSM,
     *  newName will be name of its class  
     * @param editorType of editor for which to generate Title
	 * @return editor title string for given editor type and ME; returns null if
	 *         given editor type is not supported for given ME, so this method
	 *         can be to check wether given editor type is supported by given ME
	 */
	static public String getComponentEditorTitleString(
			NonRootModelElement componentRoot,String newName, int editorType) {
		String title = newName;
		
		if (componentRoot instanceof Domain_c) {
			if (editorType == EDITOR_TYPE_DESC)
				title += ": Domain Description"; //$NON-NLS-1$
			else if (editorType == EDITOR_TYPE_CANVAS)
				title += ": Domain Package Diagram"; //$NON-NLS-1$
		}
		if (componentRoot instanceof DataTypePackage_c) {
			if (editorType == EDITOR_TYPE_CANVAS)
				title += ": Data Type Package Diagram"; //$NON-NLS-1$
		}
		if (componentRoot instanceof ExternalEntityPackage_c) {
			if (editorType == EDITOR_TYPE_CANVAS)
				title += ": External Entity Package Diagram"; //$NON-NLS-1$
		}
		if (componentRoot instanceof FunctionPackage_c) {
			if (editorType == EDITOR_TYPE_CANVAS)
				title += ": Function Package Diagram"; //$NON-NLS-1$
		}
		if (componentRoot instanceof Subsystem_c) {
			if (editorType == EDITOR_TYPE_CANVAS)
				title += ": Class Diagram"; //$NON-NLS-1$
			else if (editorType == EDITOR_TYPE_DESC)
				title += ": Subsystem Description"; //$NON-NLS-1$
		}
		if (componentRoot instanceof ModelClass_c) {
		    if (editorType == EDITOR_TYPE_CANVAS)
                title += ": Class Diagram"; //$NON-NLS-1$
		    else if (editorType == EDITOR_TYPE_DESC)
				title += ": Model Class Description"; //$NON-NLS-1$
		}
        // when componentRoot is ISM or CSM, newName will be name of its class
		if (componentRoot instanceof InstanceStateMachine_c) {
			if (editorType == EDITOR_TYPE_CANVAS)
				title += ": Instance State Machine"; //$NON-NLS-1$
			else if (editorType == EDITOR_TYPE_DESC)
				title += ": Instance State Machine Description"; //$NON-NLS-1$
		}
		if (componentRoot instanceof ClassStateMachine_c) {
			if (editorType == EDITOR_TYPE_CANVAS)
				title += ": Class State Machine"; //$NON-NLS-1$
			else if (editorType == EDITOR_TYPE_DESC)
				title += ": Class State Machine Description"; //$NON-NLS-1$
			
		}
		return title;
	}
	
	static List findMEsWithText(NonRootModelElement element, String type) {
		return findMEsWithText(element, null, 2, type);
	}

	static List findMEsWithText(NonRootModelElement element, List mes,
			int count, String type) {
		if (mes == null) {
			mes = new Vector();
		}

		AbstractModelElementEditorInputFactory factory = null;

		if (type.equals(ActivityEditorInputFactory.PLACEHOLDER_EXTENSION)) {
			factory = ActivityEditorInputFactory.getDefaultInstance();
		} else if (type
				.equals(DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION)) {
			factory = DescriptionEditorInputFactory.getDefaultInstance();
		} else {
			throw new IllegalArgumentException("illegal type:" + type);
		}

		IPersistenceHierarchyMetaData metaData = PersistenceManager
				.getHierarchyMetaData();

		if (factory.isSupported(element)) {
			NonRootModelElement requiredME = factory
					.getRequiredModelElement(element);
			if (requiredME != null && !mes.contains(requiredME)) {
				mes.add(requiredME);
				if (count > 0 && (mes.size() >= count)) {
					return mes;
				}
			}
		}

		List children = metaData.getChildren(element, true);
		for (int i = 0; i < children.size(); i++) {
			findMEsWithText((NonRootModelElement) children.get(i), mes, count,
					type);
		}

		return mes;
	}

	static NonRootModelElement findMEWithModelInstance(
			NonRootModelElement element) {
		Ooaofgraphics modelRoot = Ooaofgraphics.getInstance(element.getModelRoot().getId()); 
		//TODO Does not work in restore case
		Model_c[] diagrams = Model_c.ModelInstances(modelRoot);
		for (int i = 0; i < diagrams.length; i++) {
			if (diagrams[i].inSameComponent(element, diagrams[i])) {
				return (NonRootModelElement) diagrams[i].getRepresents();
			}
		}
		return null;
	}
	
	static public void validateTextEditorContents(String title, String contents){
		IEditorPart editor = findEditor(title, false);
		TestCase.assertNotNull("Could not find editor with title:" + title, editor);
		TestCase.assertTrue("Editor not a text(activity/description) editor", (editor instanceof AbstractModelElementTextEditor));
		
		AbstractModelElementTextEditor textEditor = (AbstractModelElementTextEditor) editor;
		IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
		
		TestCase.assertEquals("contents of editor didn't match", contents, document.get());
	}

	static public void validateActiveTextEditorContents(String title, String contents) {
		IEditorPart editor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		TestCase.assertNotNull(editor);
		TestCase.assertEquals(title, editor.getTitle());
		if (editor instanceof AbstractModelElementTextEditor) {
			AbstractModelElementTextEditor textEditor = (AbstractModelElementTextEditor) editor;
			IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
			
			TestCase.assertEquals("contents of editor didn't match", contents, document.get());
		}

	}
	
	public static void checkAllEditorClosed(Object[] openEditors) {
		
        for (int i = 0; i < openEditors.length; i++) {
			checkEditorClosed((IEditorPart)openEditors[i]);	
		}
	
	}

	public static void checkEditorClosed(IEditorPart editor) {
		if(editor.getEditorInput()==null)
            return; // if it is closed it will be null
	    IEditorPart ed= PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findEditor(editor.getEditorInput());
		if(ed!=null)
        TestCase.fail("editor not closed : " + ed.getTitle());
		
	}

	public static void checkAllEditorTitles(Object[] openEditors, String newName) {
		for (int i = 0; i < openEditors.length; i++) {
			checkEditorTitle((IEditorPart)openEditors[i],newName);	
		}
	}
	
    public static void checkEditorTitle(IEditorPart editor,String newName) {
        NonRootModelElement me=null;
        if(editor instanceof DescriptionEditor)
        {
            DescriptionEditorInput dei=(DescriptionEditorInput)editor.getEditorInput();
            me=dei.getModelElement();
        }else if (editor instanceof ActivityEditor) {
            ActivityEditorInput aei = (ActivityEditorInput) editor.getEditorInput();
            me=aei.getModelElement();
        }else if (editor instanceof GraphicalEditor) {
            GraphicalEditorInput cei = (GraphicalEditorInput) editor.getEditorInput();
            me=(NonRootModelElement)cei.getInput().getRepresents();
            
        }
        String curTitle=editor.getTitle();
	    String expTitle=getComponentEditorTitleString(me, newName, getEditorType(editor));
    	TestCase.assertEquals("editor title not updated ",expTitle,curTitle);
		
	}
    public static int getEditorType(IEditorPart editor)
    {
        if (editor instanceof GraphicalEditor){
            
            // TODO how to get ME represented by given editor
            return EDITOR_TYPE_CANVAS;
        }
            else if (editor instanceof DescriptionEditor)
                return EDITOR_TYPE_DESC;
                else if (editor instanceof ActivityEditor)
                    return EDITOR_TYPE_ACTIVITY;    
                else
                    return EDITOR_TYPE_NONE;
            
    }
    /**
     * This method emulate button press behavior on active model dialog, this 
     * method must be called before model dialog is displayed.
     * @param buttonToPress Text of button, use provided static constants
     * @param title title of the dialog, only to be more specific
     * @param afterHowMuchTime after how much time to invoke the operation, as
     * this method is called before invocation of dialog, this method will wait for
     * dialog display for <b>afterHowMuchTime</b> mili secs between successive 
     * tries.
     */
    public static void DoDialog(final String buttonToPress,final String title, final int afterHowMuchTime) {
        
        final int maxRecursionDepth = 10;
        Display.getCurrent().asyncExec(
            new Runnable() {
                public void run() {
                    // wait to give the expected modal dialog time to get displayed 
                    TestUtil.sleep(afterHowMuchTime);
                    for(int callNumber=0;callNumber<maxRecursionDepth;callNumber++)
                    {
                    // if the currently active shell is a dialog 
                    Shell shell = getShell(title);
                    if (shell != null && shell.getData() instanceof Dialog) {
                            Dialog dialog=(Dialog)shell.getData();
                              if(doDialog( dialog,buttonToPress ))
                                  return;//button done successfully
                              else
                                  TestUtil.sleep(afterHowMuchTime); //wait for dialog to dispaly
                            }
                } //end for
            }//end run
            });
        
        
    }
    private static Shell getShell(String title){
        Shell[] shell = Display.getCurrent().getShells();
        for (int i = 0; i < shell.length; i++) {
            if(shell[i].getText().startsWith(title))
                return shell[i];
        }
        return null;
    }
    private static boolean doDialog(Dialog dialog,String buttonToPress)
    {   
        Control bb = dialog.buttonBar;
        Button cb = findButton(bb.getParent(),buttonToPress);
        if(cb!=null){
        cb.notifyListeners(SWT.Selection, null);
        return true;
        }

        return false;      
    }
    private static Button findButton(Composite parent,String buttonText) {
        Control [] child_set = parent.getChildren();
        for ( int i = 0; i < child_set.length; ++i )
        {
            if ( child_set[i] instanceof Button )
            {
                Button cc = (Button) child_set[i];
                String l = cc.getText();
                if ( l.equals(buttonText))
                {
                 return cc;   
                }
            }
            else if ( child_set[i] instanceof Composite )
            {
                Button result = findButton((Composite)child_set[i],buttonText);
                if ( result != null )
                {
                   return result;   
                }
            }
        }
        return null;
    }

    public static List getActivatedEditors(){
        List editorList = new Vector();
        
        IEditorReference[] editorRefs = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
        for (int i = 0; i < editorRefs.length; i++) {
            IEditorPart editor = editorRefs[i].getEditor(false);
            if(editor != null){
                editorList.add(editor);
            }
        }
        
        return editorList;
    }

}
