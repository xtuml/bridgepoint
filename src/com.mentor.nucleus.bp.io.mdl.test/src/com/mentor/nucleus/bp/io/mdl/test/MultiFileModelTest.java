package com.mentor.nucleus.bp.io.mdl.test;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class MultiFileModelTest extends BaseTest {
    static PersistenceManager manager = null;

	public MultiFileModelTest(String name) {
		super("MultiFileModelTest", name);
	}

	static boolean setupDone = false;
	protected void setUp() throws Exception {
		if (!setupDone) {
			manager = PersistenceManager
				.getDefaultInstance();
			ensureAvailableAndLoaded("MultiLevelModel", false);
            setupDone = true;
		}
	}


    public void testReloadComponents() throws Exception {
        PersistableModelComponent rootComponent = PersistenceManager.getRootComponent(getProject());
        rootComponent.load(new NullProgressMonitor());

        Ooaofooa originalMR = Ooaofooa.getInstance("MultiLevelModel-original");
        // loading model using old import method.
        IFile oldModelFile = getProject().getFile(new Path(Ooaofooa.MODELS_DIRNAME + "/MultiLevelModel.xtuml"));
        importModel(oldModelFile, originalMR, false, false, true);
        Ooaofooa testMR = loadDomain("MultiLevelModel");
        assertEquals("See STD Output for details", true, TestingUtilities.compareModels(originalMR, testMR));
        
        reloadLeafComponent(originalMR, testMR, manager.getRootComponent(getProject()).getChild("MultiLevelModel"), new NullProgressMonitor());
    }

    private void reloadLeafComponent(Ooaofooa originalMR, Ooaofooa testMR, PersistableModelComponent component, IProgressMonitor monitor) throws Exception{
        if(component.getChildrenCount() > 0){
            for (Iterator iterator = component.getChildren().iterator(); iterator.hasNext();) {
                PersistableModelComponent child = (PersistableModelComponent) iterator.next();
                reloadLeafComponent(originalMR, testMR, child, monitor);
            }
        }
        
        component.load(monitor, true,true);
        assertEquals("See STD Output for details", true, TestingUtilities.compareModels(originalMR, testMR));
    }    
    
    private static final int RENAME_FILE = 0; 
    private static final int RENAME_DIRECTORY = 1; 
    private static final int RENAME_ME = 2; 

    public void testRenameComponentsFileWithoutLoading() throws Exception{
        PersistableModelComponent rootComponent = manager.getRootComponent(getProject());
        
        clearDatabaseOfComponent(rootComponent);
        
        renameComponentsResource(rootComponent, "" + RENAME_DIRECTORY, RENAME_DIRECTORY);
        renameComponentsResource(rootComponent, "" + RENAME_FILE , RENAME_FILE);
    }
    
    public void testRenameComponentsFileAfterLoading() throws Exception{
        PersistableModelComponent rootComponent = manager.getRootComponent(getProject());
        rootComponent.load(new NullProgressMonitor());
        loadDomain("MultiLevelModel10");
        
        renameComponentsResource(rootComponent, "" + RENAME_DIRECTORY, RENAME_DIRECTORY);
        renameComponentsResource(rootComponent, "" + RENAME_FILE , RENAME_FILE);
        renameComponentsResource(rootComponent, "" + RENAME_ME, RENAME_ME);
    }

    private static final String ISM = "InstanceStateMachine";
    private static final String CSM = "ClassStateMachine";
    private void renameComponentsResource(PersistableModelComponent component, String newName, int method) throws Exception{
        if(component.getChildrenCount() > 0){
            for (Iterator iterator = component.getChildren().iterator(); iterator.hasNext();) {
                PersistableModelComponent child = (PersistableModelComponent) iterator.next();
                renameComponentsResource(child, newName, method);
            }
        }
        
        IPersistenceHierarchyMetaData metaData = PersistenceManager.getHierarchyMetaData(); 

        IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot(); 
        
        IPath oldPath = null;
        String oldName = component.getName();
        newName = component.getName() + newName;

        IResource[] oldResourcePossibilities = new IResource[4];
        if(metaData.isRootElementRenamable(component)){
            oldResourcePossibilities[0] = wsRoot.getFolder(component.getContainingDirectoryPath()); 
            oldResourcePossibilities[1] = wsRoot.getFile(component.getFullPath()); 
        }else{
            oldResourcePossibilities[0] = wsRoot.getFolder(component.getParentDirectoryPath().append("/" + newName)); 
            oldResourcePossibilities[1] = wsRoot.getFile(component.getParentDirectoryPath().append("/" + newName + "/" + newName + "." + Ooaofooa.MODELS_EXT)); 
        }
        oldResourcePossibilities[2] = wsRoot.getFile(component.getParentDirectoryPath().append("/" + newName + "/" + oldName + "." + Ooaofooa.MODELS_EXT));
        oldResourcePossibilities[3] = wsRoot.getFile(component.getParentDirectoryPath().append("/" + oldName + "/" + newName + "." + Ooaofooa.MODELS_EXT));
        
        switch(method){
        case RENAME_FILE:
            oldPath = component.getFullPath();
            IPath newPath = oldPath.removeLastSegments(1).append(newName + "." + oldPath.getFileExtension());
                
            IFile file = wsRoot.getFile(oldPath);
            file.move(newPath, true, true, new NullProgressMonitor());
            break;
            
        case RENAME_DIRECTORY:
            oldPath = component.getContainingDirectoryPath();
            
            newPath = oldPath.removeLastSegments(1).append(newName);
                
            IFolder folder = wsRoot.getFolder(oldPath);
            folder.move(newPath, true, new NullProgressMonitor());
            break;
        case RENAME_ME:
            NonRootModelElement me = component.getRootModelElement();
            if(!metaData.isComponentRoot(me) || metaData.isRootElementRenamable(component)){
                TransactionManager manager = me.getTransactionManager();
                Transaction transaction = manager.startTransaction("Renaming ME to " + newName, Ooaofooa.getDefaultInstance());
                PersistenceManager.getHierarchyMetaData().setRootElementName(me, newName); 
                manager.endTransaction(transaction);
            }
            break;
        }
        
        if(ISM.equals(component.getComponentType())){
            assertEquals(ISM, component.getName());
            assertEquals(ISM, component.getFullPath().removeFileExtension().lastSegment());
            assertEquals(ISM, component.getContainingDirectoryPath().lastSegment());
        }else if(CSM.equals(component.getComponentType())){
            assertEquals(CSM, component.getName());
            assertEquals(CSM, component.getFullPath().removeFileExtension().lastSegment());
            assertEquals(CSM, component.getContainingDirectoryPath().lastSegment());
        }else{
            assertEquals(newName, component.getName());
            assertEquals(newName, component.getFullPath().removeFileExtension().lastSegment());
            assertEquals(newName, component.getContainingDirectoryPath().lastSegment());
        }

        assertEquals(true, wsRoot.getFolder(component.getContainingDirectoryPath()).exists());
        assertEquals(true, wsRoot.getFile(component.getFullPath()).exists());
        
        for (int i = 0; i < oldResourcePossibilities.length; i++) {
            assertEquals(false, oldResourcePossibilities[i].exists());
        }
    }
    
    private Ooaofooa loadDomain(String name) throws Exception{
        PersistableModelComponent component = manager.getRootComponent(getProject()).getChild(name);
        
        Ooaofooa modelRoot = Ooaofooa.getInstance(component.getUniqueID());
        IProgressMonitor monitor = new NullProgressMonitor();

        component.loadComponentAndChildren(modelRoot, monitor);
        
        return modelRoot;
    }
    
    private void clearDatabaseOfComponent(PersistableModelComponent component) throws Exception{
        Collection children = component.getChildren();
        for (Iterator iterator = children.iterator(); iterator.hasNext();) {
            PersistableModelComponent child = (PersistableModelComponent) iterator.next();
            clearDatabaseOfComponent(child);
        }        
        component.clearDatabase();
    }
    
}