//=====================================================================
//
//File:      $RCSfile: PkgCMBaseTest.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2013/01/10 23:12:56 $
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasEditorUtils;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.test.common.ExplorerUtil.ISelectionCriteria;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.explorer.test.ExplorerTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.tools.GraphicsCreationTool;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;

public class PkgCMBaseTest extends CanvasTest {

    protected static final int TEST_ONLY = 1;

    protected static final int SETUP_ONLY = 2;

    protected static final int SETUP_AND_TEST = 3;
    
    protected String resultPostfix = null;

    protected boolean throughRN;

    /**
     * ExplorerTest being used here, sets Ooaofooa.setPersistEnabled(false) we
     * need it true, that is why this static block is here
     */
    public static boolean generateResult = false;

    private Properties properties;

    // protected static boolean isSetupWorkspace = true;

    private static int mode = TEST_ONLY;

    static {
        try {
            Class
                    .forName("com.mentor.nucleus.bp.ui.explorer.test.ExplorerTest");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Ooaofooa.setPersistEnabled(true);
    }

    /**
     * @param projectName
     * @param name
     */
    public PkgCMBaseTest(String projectName, String name) {
        super(projectName, name);
    }
    protected void setupProject(String projectName){
        if (projectName != null) {
            if(!projectExists(projectName)) {
                try {
                    project = TestingUtilities.createProject(projectName);
                } catch (CoreException e) {
                    fail(e.getMessage());
                }
                // get the SystemModel_c instance related to the
                // newly created project
                m_sys = getSystemModel(projectName);
            } else {
                project = getProjectHandle(projectName);
            }
            Collection doms = getSystemModel(projectName).getPersistableComponent().getChildren();
            Domain_c dom = null;
            for (Iterator iter = doms.iterator(); iter.hasNext()&& dom == null; ) {
            	PersistableModelComponent pmc = (PersistableModelComponent)iter.next();
            	String type = "";
            	if (pmc != null) {
            		type = pmc.getComponentType();
            	}
            	if ( type.equalsIgnoreCase("Domain")  ) {
	                dom=(Domain_c) pmc.getRootModelElement();
	                if(dom!=null){
	                    modelRoot = (Ooaofooa)dom.getModelRoot();
	                    graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
	                }
            	}
            }
        }
    }
    protected void setUp() throws Exception {
        properties = new Properties();
        try {
            String sPath = System.getProperty("Property_File");
            if (sPath != null) {
                IPath path = new Path(sPath);
                File file = path.toFile();
                FileInputStream inStream = new FileInputStream(file);
                properties.load(inStream);
                inStream.close();
            }
        } catch (FileNotFoundException ef) {
            // ef.printStackTrace();

        } catch (IOException e1) {
            e1.printStackTrace();
            fail("Can't load properties file.");
        }
        determineMode();
        super.setUp();
        //rename .log file created by last test
        IPath in_path = new Path(m_logfile_path);
        File in_fh = in_path.toFile();
        if ( in_fh.exists() )
        {
            File newFile = in_path.removeLastSegments(1).append(getName()+"_in_setup.log").toFile();
            in_fh.renameTo(newFile);
        }
    }

    protected void tearDown() throws Exception {
        String sPath = System.getProperty("Property_File");
        if (sPath != null) {
            IPath path = new Path(sPath);
            File file = path.toFile();
            if (file.exists())
                file.delete();

            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                properties.store(fos, "Restore test states");
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                fail("Can't Save properties file.");
            }
        }
        //restore tests take too much time, .log file causes unnecessary
        //failure of test, so rename .log file to save time
        IPath in_path = new Path(m_logfile_path);
        File in_fh = in_path.toFile();
        if ( in_fh.exists() )
        { 
        	String failure = super.getLogViewResult(getName());
            File newFile = in_path.removeLastSegments(1).append(getName()+".log").toFile();
            in_fh.renameTo(newFile);
            if(!failure.equals("")) {
            	// found a real failure
            	fail(failure);
            }
        }
        super.tearDown();
    }

    /*
     * protected void setupRestoredProject(String projectName,
     * NonRootModelElement me) { assertNotNull("Project name should not be
     * null.", projectName); assertTrue("Restored project does not exist. " +
     * projectName, projectExists(projectName)); // set the model-roots
     * interesting to the current test setupProject(projectName); modelRoot =
     * (Ooaofooa) me.getModelRoot(); graphicsModelRoot =
     * Ooaofgraphics.getInstance(modelRoot.getId()); }
     */

    protected String getResultName() {
        if(resultPostfix == null)
        	return getName();
        else
        	{
        	String pfix=resultPostfix;
        	return getName()+pfix;
        	}
    }

    protected String getProperty(String key) {
        return properties.getProperty(key);
    }

    protected int getProperty(String key, int defaultValue) {
        return Integer.parseInt(properties.getProperty(key, new Integer(
                defaultValue).toString()));
    }

    protected boolean getProperty(String key, boolean defaultValue) {
        return Boolean.valueOf(
                properties.getProperty(key, Boolean.toString(defaultValue)
                        .toString())).booleanValue();
    }

    protected void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    protected void setProperty(String key, int value) {
        properties.setProperty(key, new Integer(value).toString());
    }

    protected void setProperty(String key, boolean value) {
        properties.setProperty(key, Boolean.toString(value));
    }

    protected Object removeProperty(String key) {
        return properties.remove(key);
    }


    /**
     * Shows the BridgePoint perspective, on which it creates a model explorer
     * view.
     */
    public void showModelExplorer() {
    	ExplorerUtil.showModelExplorer();
    }

    /**
     * Selects (what should be) the only system item present in the model
     * explorer tree.
     */
    /**
     * Selects the given item in the model explorer tree.
     */
    public void selectItemInModelExplorer(TreeItem item) {
    	ExplorerUtil.selectItem(item);
    }
    public void selectMEInModelExplorer(NonRootModelElement me){
    	ExplorerUtil.selectItem(me);
    }

    public TreeItem selectMEInModelExplorer(IPath itemPath) {
    	waitForDecorator();
    	dispatchEvents(0);
    	return ExplorerUtil.selectMEInModelExplorer(itemPath);
    }

    public void doDeleteThruMExplorer() {
    	ExplorerUtil.deleteItem();
}
    public void doDeleteThruCanvasEditor(GraphicalEditor editor, Shape_c shape) {
        CanvasEditorUtils.delete(editor, shape);        
    }

    public void doDeleteThruResNav(PersistableModelComponent pmc) {
        try {
            pmc.getFile().delete(false, false, new NullProgressMonitor());
        } catch (CoreException e) {
            fail("Could not perform delete file operation");
        }
    }

    public void doRenameThruMExplorer(String newName) {
    	ExplorerUtil.renameItem(newName);
    }

    public void doRenameThruCanvasEditor(String newName) {

        Object selection = Selection.getInstance().getStructuredSelection()
                .getFirstElement();
        String oldName = Cl_c.Getname(selection);

        Runnable query = RenameAction.getRenameQuery(selection, newName,
                oldName, false);
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
                .getDisplay().asyncExec(query);
    }

    public void doRenameThruResNav(PersistableModelComponent pmc, String newName) {
        IFile file = pmc.getFile();
        while (Display.getCurrent().readAndDispatch())
            ;
        try {
            if(pmc.isRootComponent())
            {
                pmc.getFile().getProject().move(new Path("/"+newName), false, null);
            }else{
                IPath newPath = file.getFullPath().removeLastSegments(1).append(
                        newName + "." + Ooaofooa.MODELS_EXT);
            file.move(newPath, false, new NullProgressMonitor());
            }
        } catch (CoreException e) {
            fail("Could not rename file");
        }
    }

    protected void setFocus(Object[] openEditors, int focusedEditor) {
        int i = 0;
        IEditorPart editor = null;
        switch (focusedEditor) {
        case EditorTestUtilities.EDITOR_TYPE_CANVAS:
            // find 1st canvas editor and set focus
            for (i = 0; i < openEditors.length; i++) {
                if (openEditors[i] instanceof GraphicalEditor) {
                    editor = (IEditorPart) openEditors[i];
                    break;
                }
            }
            break;
        case EditorTestUtilities.EDITOR_TYPE_DESC:
            // find 1st desc editor and set focus
            for (i = 0; i < openEditors.length; i++)
                if (openEditors[i] instanceof DescriptionEditor) {
                    editor = (IEditorPart) openEditors[i];
                    break;
                }
            break;
        case EditorTestUtilities.EDITOR_TYPE_ACTIVITY:
            // find 1st activity editor and set focus
            for (i = 0; i < openEditors.length; i++)
                if (openEditors[i] instanceof ActivityEditor) {
                    editor = (IEditorPart) openEditors[i];
                    break;
                }
        } // end switch
        assertNotNull("Editor of given type not found", editor);

        if(editor instanceof GraphicalEditor)
        	m_wp.activate(((GraphicalEditor) editor).getParentEditor());
        else
        	m_wp.activate(editor);

        assertTrue("Focus Not set on desired editor",
                UITestingUtilities.getActiveEditor() == editor);
    }

    /**
     * Returns the item amongst those given which has the given text as its
     * text.
     */
    public TreeItem findItem(String text, TreeItem[] items) {
        return ExplorerUtil.findItem(text, items);
    }

    protected static PersistableModelComponent getRandomComponent(
            IProject project, String type) throws Exception {
        List components = findComponents(project, type);
        assertTrue("Could not find component of type " + type, (components
                .size() > 0));
        return (PersistableModelComponent) components.get(0);
    }

    protected static PersistableModelComponent getRandomComponent(
            PersistableModelComponent parent, String type) throws Exception {
        List components = new Vector();
        findComponents(parent, type, components);

        assertTrue("Could not find component of type "
                + type.getClass().getName(), (components.size() > 0));
        return (PersistableModelComponent) components.get(0);
    }

    protected static List getSiblings(PersistableModelComponent sibling) {
        PersistableModelComponent parent = sibling.getParent();
        List components = new Vector();

        Collection children = parent.getChildren();
        if (children.size() > 0) {
            for (Iterator iterator = children.iterator(); iterator.hasNext();) {
                PersistableModelComponent child = (PersistableModelComponent) iterator
                        .next();
                if (sibling.getComponentType().equals(child.getComponentType())
                        && sibling != child) {
                    components.add(child);
                }
            }
        }

        return components;
    }

    protected static PersistableModelComponent getComponent(IProject project,
            String type, String name) throws Exception {
        PersistableModelComponent component = null;
        List components = findComponents(project, type);
        for (int i = 0; i < components.size(); i++) {
            component = (PersistableModelComponent) components.get(i);
            if (component.getName().equals(name)) {
                return component;
            }
        }
        return null;
    }

    protected static List findComponents(IProject project, String type)
            throws Exception {
        PersistableModelComponent rootComponent = PersistenceManager.getRootComponent(project);
        initializeComponents(project);

        List matches = new Vector();
        findComponents(rootComponent, type, matches);

        return matches;
    }

    private static void findComponents(PersistableModelComponent parent,
            String type, List matches) throws Exception {
        if (parent.getComponentType().equals(type)) {
            matches.add(parent);
        }

        Collection children = parent.getChildren();
        if (children.size() > 0) {
            for (Iterator iterator = children.iterator(); iterator.hasNext();) {
                PersistableModelComponent child = (PersistableModelComponent) iterator
                        .next();
                findComponents(child, type, matches);
            }
        }
    }

    private static void initializeComponents(IProject project) throws Exception {
        IFolder modelsFolder = project.getFolder(Ooaofooa.MODELS_DIRNAME);
        assertTrue("Does not contain models directory", modelsFolder.exists());
        initializeComponents(modelsFolder);
    }

    private static void initializeComponents(IFolder container)
            throws Exception {
        IResource[] children = container.members();
        for (int i = 0; i < children.length; i++) {
            if (children[i] instanceof IFile) {
                IFile file = (IFile) children[i];
                if (file.getFileExtension() != null
                        && file.getFileExtension().equals(Ooaofooa.MODELS_EXT)) {
                    PersistableModelComponent.findOrCreateInstance(file
                            .getFullPath());
                }
            }
        }

        for (int i = 0; i < children.length; i++) {
            if (children[i] instanceof IFolder) {
                initializeComponents((IFolder) children[i]);
            }
        }
    }

    protected TreeItem findTreeItem(NonRootModelElement modelElement) {
        m_bp_tree.expandToLevel(modelElement, TreeViewer.ALL_LEVELS);
        m_bp_tree.setSelection(new StructuredSelection(modelElement));

        TreeItem[] selectedItems = m_bp_tree.getTree().getSelection();

        return (selectedItems.length > 0) ? selectedItems[0] : null;
    }
    
    protected NonRootModelElement createShape(GraphicalEditor baseEditor, String componentType) {
        
        NonRootModelElement result=null;

        AbstractTool tool = UITestingUtilities.getTool(componentType);
        UITestingUtilities.activateTool(tool);
        
        //get number of existing instances
        ModelTool_c mdlTool = ((GraphicsCreationTool) tool).getTool(); 
        ElementSpecification_c es= ElementSpecification_c.getOneGD_ESOnR103(mdlTool);
        Class classOfME =es.getRepresents();
        int mes=modelRoot.getInstanceList(classOfME).size();
              
        
        CanvasTestUtils.createMouseEvent(1000, 1000, "MouseDown");
        CanvasTestUtils.createMouseEvent(1200, 1200, "MouseMove");
        CanvasTestUtils.createMouseEvent(1200, 1200, "MouseUp");
           
        //get number of instances after creation
        int mes_after = modelRoot.getInstanceList(classOfME).size();
        assertTrue("Number of "+componentType+" found: " + mes_after + "Expected: " + mes + 1, mes_after>mes);
        
        
        UITestingUtilities.deactivateTool(tool);
        CanvasTestUtils.createMouseEvent(1100, 1100, "MouseDown");
        CanvasTestUtils.createMouseEvent(1100, 1100, "MouseUp");
        
        return modelRoot.getInstanceList(classOfME).get(mes_after - 1);
    }
    
    protected NonRootModelElement createShape(GraphicalEditor baseEditor,String componentToolSet ,String componentType) {
        
        NonRootModelElement result=null;

        AbstractTool tool = UITestingUtilities.getTool(componentToolSet,componentType);
        UITestingUtilities.activateTool(tool);
        
        //get number of existing instances
        ModelTool_c mdlTool = ((GraphicsCreationTool) tool).getTool(); 
        ElementSpecification_c es= ElementSpecification_c.getOneGD_ESOnR103(mdlTool);
        Class classOfME =es.getRepresents();
        int mes=modelRoot.getInstanceList(classOfME).size();
              
        
        CanvasTestUtils.createMouseEvent(1000, 1000, "MouseDown");
        CanvasTestUtils.createMouseEvent(1200, 1200, "MouseMove");
        CanvasTestUtils.createMouseEvent(1200, 1200, "MouseUp");
           
        //get number of instances after creation
        int mes_after = modelRoot.getInstanceList(classOfME).size();
        assertTrue("Number of "+componentType+" found: " + mes_after + "Expected: " + mes + 1, mes_after>mes);
        
        
        UITestingUtilities.deactivateTool(tool);
        CanvasTestUtils.createMouseEvent(1100, 1100, "MouseDown");
        CanvasTestUtils.createMouseEvent(1100, 1100, "MouseUp");
        
        return modelRoot.getInstanceList(classOfME).get(mes_after - 1);
    }
    // ************** Delete checks
    protected void performDeleteChecks(Object[] openEditors,
            GraphicalEditor baseEditor, IFile oldFile,
            NonRootModelElement meBeingTested) {
        EditorTestUtilities.checkAllEditorClosed(openEditors);
        if (baseEditor != null) {
             validateOrGenerateResults(baseEditor, generateResult);
        }
        checkFileDeletion(oldFile);
        try{
        checkTreeItemDeletion(meBeingTested);
        }catch(ArrayIndexOutOfBoundsException e)
        {}

    }
    protected void performDeleteChecksGenerics(Object[] openEditors,
            GraphicalEditor baseEditor, IFile oldFile,
            NonRootModelElement meBeingTested) {
        EditorTestUtilities.checkAllEditorClosed(openEditors);
        if (baseEditor != null) {
             validateOrGenerateResultsGenerics(baseEditor, generateResult);
        }
        checkFileDeletion(oldFile);
        try{
        checkTreeItemDeletion(meBeingTested);
        }catch(ArrayIndexOutOfBoundsException e)
        {}

    }
    protected void checkFileDeletion(IFile oldFile) {
        BaseTest.waitForJobs();
        Display display = Display.getCurrent();
        while (display.readAndDispatch())
            ;
        assertFalse("File still exists after deletion: "
                + oldFile.getFullPath(), oldFile.exists());
        if(! throughRN){
        assertFalse("Component Folder still exists after file deletion: "
                + oldFile.getFullPath(), oldFile.getParent().exists());
    }
    }

    protected void checkTreeItemDeletion(NonRootModelElement meBeingTested) {
    	ExplorerUtil.checkTreeItemDeletion(meBeingTested);
        
    }

    // ************** Rename checks
    protected void performRenameChecks(Object[] openEditors,
            GraphicalEditor baseEditor, PersistableModelComponent component,
            String newName) {        
        waitForJobs();
        if (!generateResult) {
          EditorTestUtilities.checkAllEditorTitles(openEditors, newName);
          checkFileRename(component, newName);

          checkTreeItemExistance(component.getRootModelElement(), newName);
        }
        if (baseEditor != null)
            validateOrGenerateResults(baseEditor, generateResult);
    }
    protected void performRenameChecksGenerics(Object[] openEditors,
            GraphicalEditor baseEditor, PersistableModelComponent component,
            String newName) {        
        waitForJobs();
        if (!generateResult) {
          EditorTestUtilities.checkAllEditorTitles(openEditors, newName);
          checkFileRename(component, newName);

          checkTreeItemExistance(component.getRootModelElement(), newName);
        }
        if (baseEditor != null)
            validateOrGenerateResultsGenerics(baseEditor, generateResult);
    }
    protected void checkFileRename(PersistableModelComponent pmcBeingTested,
            String newName) {

        assertTrue("File not renamed: " + pmcBeingTested.getFullPath(),
                pmcBeingTested.getFullPath().removeFileExtension()
                        .lastSegment().equals(newName));
        PkgCMRenameTest.assertTrue("Renamed file does not exist: "
                + pmcBeingTested.getFullPath(), pmcBeingTested.getFile()
                .exists());
        assertTrue("Parent Folder of File not renamed: "
                + pmcBeingTested.getFile().getParent().getName(),
                pmcBeingTested.getFile().getParent().getName().equals(newName));
        PkgCMRenameTest.assertTrue(
                "Parent Folder of Renamed file does not exist: "
                        + pmcBeingTested.getFullPath(), pmcBeingTested
                        .getFile().getParent().exists());
    }

    protected void checkTreeItemExistance(NonRootModelElement meBeingTested,
            String newName) {

        if (meBeingTested instanceof InstanceStateMachine_c)
            newName = "Instance State Machine";
        else if (meBeingTested instanceof ClassStateMachine_c)
            newName="Class State Machine";
        if(meBeingTested.getFile()!=null){
            TreeItem item = selectMEInModelExplorer(meBeingTested.getFile()
                    .getFullPath());
        }
        ExplorerTest.checkTreeItemExistance(meBeingTested, newName);
    }

    // ************** Create checks
    protected void performCreateChecks(GraphicalEditor baseEditor,
            PersistableModelComponent component) {
        checkFileExistance(component);
        checkTreeItemExistance(component.getRootModelElement(), component
                .getName());
        if (baseEditor != null)
            validateOrGenerateResults(baseEditor, generateResult, true);
    }
    protected void performCreateChecksGenerics(GraphicalEditor baseEditor,
            PersistableModelComponent component) {
        checkFileExistance(component);
        checkTreeItemExistance(component.getRootModelElement(), component
                .getName());
        if (baseEditor != null)
            validateOrGenerateResultsGenerics(baseEditor, generateResult, true);
    }
    protected void checkFileExistance(PersistableModelComponent pmcBeingTested) {

        assertTrue("New file does not exist: " + pmcBeingTested.getFullPath(),
                pmcBeingTested.getFile().exists());
        assertTrue("Parent Folder of new file does not exist: "
                + pmcBeingTested.getFullPath(), pmcBeingTested.getFile()
                .getParent().exists());
    }

    protected int getProxyRefrences(PersistableModelComponent component) {
        IProject project=component.getFile().getProject();
        IFolder proPath = project.getFolder(Ooaofooa.MODELS_DIRNAME + "/"
                + project.getFullPath());
        
        IFolder domPath = null;
       
        if (component.getComponentType().equals("SystemModel")){
            domPath=project.getFolder(Ooaofooa.MODELS_DIRNAME
                    + project.getFullPath());
        }
        else if (component.getComponentType().equals("Package")){
            domPath=project.getFolder(Ooaofooa.MODELS_DIRNAME
                    + project.getFullPath());
        }
 else {
			if (component.getDomainComponent() == null) {
				domPath = project.getFolder(Ooaofooa.MODELS_DIRNAME
						+ project.getFullPath() + "/"
						+ component.getName());
			}

			else {
				domPath = project.getFolder(Ooaofooa.MODELS_DIRNAME
						+ project.getFullPath() + "/"
						+ component.getDomainComponent().getName());
			}
		}
        return traverse4Reference(proPath, domPath, component.getFullPath());
    }

    /**
     * This funcation counts pathToFind(proxy) occurances in project and domain
     * Folder
     * 
     * @param projectFolder
     * @param domainFolder
     * @param pathToFind
     * @return number of occurances found
     */
    private int traverse4Reference(IFolder projectFolder, IFolder domainFolder,
            IPath pathToFind) {
        int refrences = 0;
        IResource[] members;
        try {
            members = projectFolder.members();

            for (int i = 0; i < members.length; i++) {
                if (members[i] instanceof IFile) {
                    IFile file = (IFile) members[i];
                    String extension = file.getFileExtension();

                    if ((extension != null)
                            && extension.equals(Ooaofooa.MODELS_EXT)) {
                        refrences += countInFile(file, pathToFind);
                    }
                } else if (members[i] instanceof IFolder
                        && members[i].getFullPath().toOSString().startsWith(
                                domainFolder.getFullPath().toOSString())) {
                    refrences += traverse4Reference((IFolder) members[i],
                            domainFolder, pathToFind);
                }
            }
        } catch (CoreException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        return refrences;
    }

    private int countInFile(IFile file, IPath pathToFind) {
        int count = 0;
        try {
            InputStreamReader in = new InputStreamReader(file.getContents());
            BufferedReader buffReader = new BufferedReader(in);

            StringBuffer sBuff = new StringBuffer(1024);
            String line = null;
            while ((line = buffReader.readLine()) != null)
                sBuff.append(line);

            int i = 0;

            while (true) {
                String str = "/" + pathToFind.lastSegment();
                i = sBuff.indexOf(str, i);
                if (i == -1)
                    break;
                count++;
                i += str.length();
            }
            in.close();
        } catch (Exception e) {
            CorePlugin.logError(e.getMessage(), e);
        }
        return count;
    }

    protected PersistableModelComponent getComponent(String compType,
            final String compName) throws Exception {
        PersistableModelComponent pmc = null;
        if (compType.equals("SystemModel") && compName != null) {
            pmc = getRandomComponent(getProjectHandle(compName), compType);
        } else if (compType.equals("InstanceStateMachine")) {// spacial case
            PersistableModelComponent clzPmc = getComponent(getProject(),
                    "ModelClass", compName);
            pmc = getRandomComponent(clzPmc, "InstanceStateMachine");
        } else if (compName != null && compName.startsWith("/")) {
            pmc = PersistenceManager.findComponent(new Path(compName));
        } else if (compName == null) {
            pmc = getRandomComponent(getProject(), compType);
        } else if (compType.equals("DataTypePackage") && (compName.equals("Datatypes"))) {
        	Domain_c domain = Domain_c.DomainInstance(modelRoot);
        	DataTypePackage_c dtPackage = DataTypePackage_c.getOneS_DPKOnR40(domain, new ClassQueryInterface_c() {
			
				public boolean evaluate(Object candidate) {
					return ((DataTypePackage_c)candidate).getName().equals(compName);
				}
			
			});
        	assertNotNull(dtPackage);
        	pmc = dtPackage.getPersistableComponent();
        }
        else
            pmc = getComponent(getProject(), compType, compName);

        return pmc;
    }

    protected Object[] openEditors(PersistableModelComponent component, int expectedEditors, boolean validate) {
        Vector v = new Vector();
        IEditorPart editor;
        
        if (m_bp_tree != null) {
        	m_bp_tree.refresh();
        }
               
        //Opening editors of MEs within component
        editor = EditorTestUtilities.openRandomEditor(component, EditorTestUtilities.EDITOR_TYPE_DESC);
        if(editor != null){v.add(editor);}
        
        editor = EditorTestUtilities.openRandomEditor(component, EditorTestUtilities.EDITOR_TYPE_CANVAS);
        if(editor != null){v.add(editor);}
    
        editor = EditorTestUtilities.openRandomEditor(component, EditorTestUtilities.EDITOR_TYPE_ACTIVITY);
        if(editor != null){v.add(editor);}

        if(validate)
        assertEquals("All editors did not open", expectedEditors,v.size());
        
        return v.toArray();
    }
    
    protected Object[] openEditors(PersistableModelComponent component, int expectedEditors) {
        return openEditors(component, expectedEditors,true);
    }

    protected static boolean getSetupWorkspaceProperty() {
        boolean result = false;
        String prop = System.getProperty("SETUP_WORKSPACE");
        if (prop.equalsIgnoreCase("True"))
            result = true;
        else if (prop.equalsIgnoreCase("False"))
            result = false;
        else
            fail("Value of SETUP_WORKSPACE argument can only be 'True' or 'False' :- "
                    + prop);
        return result;
    }

    protected static String getTestCaseName() {
        return System.getProperty("TestCaseName");

    }

    protected static void determineMode() {
        String prop = System.getProperty("SETUP_WORKSPACE");
        if (prop == null) {
            mode = TEST_ONLY;
        } else if (prop.equalsIgnoreCase("False")) {
            mode = TEST_ONLY;
        } else if (prop.equalsIgnoreCase("True")) {
            mode = SETUP_ONLY;
        } else {
            fail("Value of SETUP_WORKSPACE argument can only be 'True' or 'False' :- "
                    + prop);
        }
    }

    protected static boolean toSetupWorkspace() {
        return ((mode & SETUP_ONLY) > 0);
    }

    protected static boolean toRunTests() {
        return ((mode & TEST_ONLY) > 0);
    }

    protected static boolean isRestoreTest() {
        String prop = System.getProperty("SETUP_WORKSPACE");
        if (prop == null)
            return false;
        else
            return true;
    }


    static public class GenericModelElementCriteria implements
            ISelectionCriteria {
        public int editorType = -1;

        public String name = null;

        public void init() {
            editorType = -1;
            name = null;
        }

        public void init(String aName) {
            editorType = -1;
            name = aName;
        }

        public void init(int aEditorType) {
            editorType = aEditorType;
            name = null;
        }

        public boolean select(Object item) {
            if (item instanceof NonRootModelElement) {
                boolean toSelect = false;
                NonRootModelElement me = (NonRootModelElement) item;
                if (editorType != -1) {
                    toSelect = EditorTestUtilities.supportsEditor(me,
                            editorType);
                }

                if (!toSelect && name != null) {
                    IPersistenceHierarchyMetaData metaData = PersistenceManager
                            .getHierarchyMetaData();
                    toSelect = metaData.getRootElementName(me).equals(name);
                }

                return toSelect;
            }

            return false;
        }
    }
    protected void restoreFile(IFile oldFile) {
        Display d = Display.getCurrent();
        while (d.readAndDispatch())
            ;
        // restore from local history
        try {
            IFileState[] history = oldFile.getHistory(null);
            if (oldFile.exists()) {
                oldFile.setContents(history[history.length - 1], IFile.NONE,
                        null);
            } else {
                IFolder folder = oldFile.getParent().getParent().getFolder(
                        new Path(oldFile.getFullPath().removeFileExtension()
                                .lastSegment()));
                if (!folder.exists())
                    folder.create(true, false, null);
                if (!oldFile.exists())
                    oldFile.create(history[history.length - 1].getContents(), 
                            true, null);
            }

        } catch (CoreException e) {
            e.printStackTrace();
            fail(e.getMessage());
}
        while (d.readAndDispatch())
            ;
    }
}
