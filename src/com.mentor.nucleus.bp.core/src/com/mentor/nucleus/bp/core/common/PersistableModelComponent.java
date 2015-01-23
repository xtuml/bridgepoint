// ========================================================================
//
//File: $RCSfile: PersistableModelComponent.java,v $
//Version: $Revision: 1.31.16.1 $
//Modified: $Date: 2013/07/24 19:20:20 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//========================================================================
//
package com.mentor.nucleus.bp.core.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.WorkbenchException;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.BridgeBody_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DerivedAttributeBody_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.GlobalElementInSystem_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationBody_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.StateActionBody_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.SystemDatatypeInPackage_c;
import com.mentor.nucleus.bp.core.SystemDatatypePackage_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.core.ui.AbstractModelExportFactory;
import com.mentor.nucleus.bp.core.ui.AbstractModelImportFactory;
import com.mentor.nucleus.bp.core.ui.IModelImport;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;

/*
 * A component is referred as directory and a file within that directory having
 * the same name with a only different that file has extension and directory
 * does not have one. The file represent a model component, whose type can be
 * determined by reading the header of the file.
 */

public class PersistableModelComponent implements Comparable {

    private static final String SYSTEM_MODEL_NAME = "SystemModel";
    
    private int status = STATUS_NOTLOADED;
    
    public static final int STATUS_NOTLOADED = 0;

    public static final int STATUS_LOADING = 1;

    public static final int STATUS_LOADED = 2;

    public static final int STATUS_UNLOADING = 3;

    public static final int STATUS_PERSISTING = 4;
    
    private final String dummyCompareName = "__dummyCompareProj/__dummyFile.xtuml";
    
    // Although type can be determined at runtime, this variable is used "only"
    // when component is not loaded
    // and component type is determined by reading the header of the file.
    private String componentType;

    protected IFile underlyingResource;

    // instance of ME when component is loaded otherwise it will be null;
    private NonRootModelElement componentRootME;
    
    static synchronized public PersistableModelComponent create(IPath modelFilePath) {
        PersistableModelComponent result = PersistenceManager.findComponent(modelFilePath);
        try {
            if(result==null){
            result = new PersistableModelComponent(modelFilePath);
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    private PersistableModelComponent(IPath modelFilePath) throws CoreException{
        if (modelFilePath.getFileExtension() == null
                || !modelFilePath.getFileExtension()
                        .equals(Ooaofooa.MODELS_EXT)) {
            throw new IllegalArgumentException(
                    "Given file is not a model component file");
        }

        underlyingResource = ResourcesPlugin.getWorkspace().getRoot().getFile(
                modelFilePath);
        if (PersistenceManager.findComponent(modelFilePath) != null)
            throw new WorkbenchException(
                    "Another component with same path already exists");
        
        // Following check throws IllegalArgumentException as we want the client
        // code to avoid
        // creating a component with file that does not exists.
        if (!underlyingResource.exists()) {
            throw new WorkbenchException("Given component file does not exist");
        }
        checkComponentConsistancy(null);

        // read the header from the path and set the component type.
        componentType = getComponentType(underlyingResource);

        PersistenceManager.addComponent(this);
        
		if (isRootComponent()
				&& !PersistenceManager.getDefaultInstance().isInitializing()
				&& PersistenceManager
						.getPersistenceVersionAsInt(PersistenceManager
								.getPersistenceVersion(this)) < PersistenceManager
						.getPersistenceVersionAsInt(PersistenceChangeTracker.version_716
								.getPersistenceVersion())) {
        	CorePlugin.addProjectForPEUpgrade(underlyingResource.getProject());
        }
    
    }

    public PersistableModelComponent(NonRootModelElement aComponentRootME,
            IFile parent) throws CoreException{
        if (aComponentRootME instanceof SystemModel_c) {
            throw new IllegalArgumentException(
                    "this constructor cannot be used for system model");
        }

        String name = PersistenceManager.getHierarchyMetaData()
                .getRootElementName(aComponentRootME);
        IPath modelFilePath = getChildPath(parent.getFullPath(), name);
        underlyingResource = ResourcesPlugin.getWorkspace().getRoot().getFile(
                modelFilePath);
        checkComponentConsistancy(null);
        
        // we don't check if underlying resource exists for the case when the ME
        // is
        // being created by the user
        
        componentRootME = aComponentRootME;
        componentType = PersistenceManager.getHierarchyMetaData()
                .getComponentType(aComponentRootME);

      setComponent(componentRootME);
        PersistenceManager.addComponent(this);
        }
    PersistableModelComponent(SystemModel_c systemModel, IProject project) throws CoreException{
        if (!XtUMLNature.hasNature(project)) {
            throw new IllegalArgumentException(
                    "Given project does not have xtUML nature");
        }
        
        if(systemModel == null){
            throw new IllegalArgumentException("System model cannot be null");
        }
        
        underlyingResource = ResourcesPlugin.getWorkspace().getRoot().getFile(
                getRootComponentPath(systemModel.getName()));
         checkComponentConsistancy(null);
        
        componentRootME = systemModel;
        componentType = PersistenceManager.getHierarchyMetaData()
                .getComponentType(systemModel);

      setComponent(systemModel);

        PersistenceManager.addComponent(this);
    }
    
    //special constructor for the compare only
    //this does not add component to the list and creates a dummy file
    public PersistableModelComponent(SystemModel_c systemModel) {
        if (systemModel == null) {
            throw new IllegalArgumentException("System model cannot be null");
        }

        componentRootME = systemModel;
        componentType = PersistenceManager.getHierarchyMetaData().getComponentType(systemModel);
      underlyingResource=ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(dummyCompareName));
        setComponent(systemModel);

    }

    public void loadComponentAndChildren(Ooaofooa modelRoot, IProgressMonitor monitor) throws Exception{
        load(modelRoot, monitor);
        Collection children = getChildren();
        for (Iterator iterator = children.iterator(); iterator.hasNext();) {
            PersistableModelComponent child = (PersistableModelComponent) iterator.next();
            child.loadComponentAndChildren(modelRoot, monitor);
        }
        
    }
    
  public void loadComponentAndChildren(IProgressMonitor monitor) {
    if (!isLoaded()) {
      try {
        load(monitor);
      } catch (CoreException e) {
        CorePlugin.logError("", e);
      }
    }
    // now get the children and load
    Collection children = getChildren();
    Iterator iterator = children.iterator();
    while (iterator.hasNext()) {
      PersistableModelComponent child = (PersistableModelComponent) iterator
          .next();
      child.loadComponentAndChildren(monitor);
    }
  }
  
    public static IPath getRootComponentPath(String name) {
        return new Path("/" + name + "/" + Ooaofooa.MODELS_DIRNAME + "/" + name
                + "/" + name + "." + Ooaofooa.MODELS_EXT);
    }
    
    public boolean isRootComponent(){
        return SYSTEM_MODEL_NAME.equals(getComponentType());
    }

    private void delete() {
        // Don't null out underlyingResource and componentRootME
        // these will be used to create mementos
        PersistenceManager.removeComponent(this);
        }

    static public PersistableModelComponent findOrCreateInstance(IPath path){
        PersistableModelComponent pmc = PersistenceManager.findComponent(path);
        if (pmc == null) {
            pmc = PersistableModelComponent.create(path);
        }
        return pmc;
    }

    public PersistableModelComponent getChild(String name) {
        return PersistenceManager.findComponent(getChildPath(getFullPath(),
                name));
    }

    static public IPath getChildPath(IPath parentPath, String name) {
        IPath parentDirPath = parentPath.removeLastSegments(1);
        
        return parentDirPath.append(name).append(
                name + "." + Ooaofooa.MODELS_EXT);
    }

    /**
     * @return Returns the PersistableModelComponent instances that are children
     *         of this instance that are in memory (not on disk).
     */
    public Collection getChildren() {
        return PersistenceManager.getChildrenOf(this);
    }

    /**
     * @return Returns the parent.
     */
    public PersistableModelComponent getParent() {
        if (isRootComponent()) {
            return null;
        } else {
            IPath parentPath = getParentDirectoryPath();
            IPath parentFilePath = parentPath.append(parentPath.lastSegment()
                    + "." + Ooaofooa.MODELS_EXT);
            return PersistenceManager.findComponent(parentFilePath);
        }
    }

    public String getComponentType() {
        return componentType;
    }

    /**
     * @return Returns the path of the directory containing the parent.
     */
    public IPath getParentDirectoryPath() {
      if (underlyingResource != null)
        return getFullPath().removeLastSegments(2);
      else
        return null;
    }

    public IPath getContainingDirectoryPath() {
        if (underlyingResource != null)
        return getFullPath().removeLastSegments(1);
        else
            return null;
    }

    public IPath getFullPath() {
      if (underlyingResource != null)
        return underlyingResource.getFullPath();
      else
        return null;
    }

    /**
     * @return Returns the rootElement.
     */
    public NonRootModelElement getRootModelElement() {
        return componentRootME;
    }
    

  public void setRootModelElement(NonRootModelElement modelElement, boolean reload, boolean validate) {
    // ignore the compare model root
    if(modelElement != null && modelElement.getModelRoot().getId().equals(Ooaofooa.COMPARE_MODEL_ROOT_NAME))
      return;
    IPersistenceHierarchyMetaData metadata = PersistenceManager
                                                    .getHierarchyMetaData();
    if (!validate) {
      componentRootME = modelElement;
      // Change the type of this PMC if necessary, but only
      // if the model element is non null, otherwise the type
      // cannot be determined and should be left as is.
      if (metadata != null && modelElement != null) {
        componentType = metadata.getComponentType(modelElement);
      }
      return;
    }
    
    if(!metadata.isComponentRoot(modelElement)){
      Throwable e = new Throwable();
      e.fillInStackTrace();
      CorePlugin.logError("Given model element is not a root element", e);
      return;
    }
    
    if(!metadata.getComponentType(modelElement).equals(getComponentType())){
      Throwable e = new Throwable();
      e.fillInStackTrace();
      CorePlugin.logError(
          "Cannot set model element as root with type other than "
          + getComponentType(), e);
      return;
    }
    
    componentRootME = modelElement;
    // make sure the PMC and the model element references are consistent
    if ( modelElement.getPersistableComponent() != this ) {
      // need to set it to null first so that it will be reassigned
      modelElement.setComponent(null);
      modelElement.setComponent(this);
      // since the model element's component reference pointed to a
      // different
      // component, that means we've done a load or reload of the
      // component, so notify listeners that the model element has been
      // loaded/reloaded
      if(reload)
        Ooaofooa.getDefaultInstance().fireModelElementReloaded(modelElement,
          null);
    }   
  }
    
    public void setRootModelElement(NonRootModelElement modelElement){
      setRootModelElement(modelElement, true, true);
    }

    public void setRootModelElement(NonRootModelElement modelElement, boolean reload){
      setRootModelElement(modelElement, reload, true);
    }

    public int getChildrenCount() {
        return getChildren().size();
    }
    
    public boolean isInChildHierarchy(PersistableModelComponent child){
        PersistableModelComponent parent = child.getParent();
        while(parent != null){
            if(parent == this){
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }
    
    public String getName() {
      if (underlyingResource != null)
        return underlyingResource.getFullPath().removeFileExtension().lastSegment();
      else
        return null;
    }
    
    /**
     * This method allows checks to occur on the component, the components
     * parent, or external references
     * 
     * @param externalRef
     * @return read-only status
     */
    public boolean isReadOnly(boolean checkParent, NonRootModelElement element) {
      // Check this component
      ResourceAttributes attrs = null;
      if (underlyingResource != null)
        attrs = underlyingResource.getResourceAttributes();
      if(attrs != null) {
        if(attrs.isReadOnly()) {
          return true;
        }
      }
      
      // Check the parent if specified
      if(checkParent) {
        PersistableModelComponent parent = this.getParent();
        if(parent != null) {
          if (parent.underlyingResource != null)
              attrs = parent.underlyingResource.getResourceAttributes();
          if(attrs != null) {
            if(attrs.isReadOnly()) {
              return true;
            }
          }
        }
      }
      
      // check external rgos if specified
      if(element != null) {
            IPersistenceHierarchyMetaData metaData = PersistenceManager
                    .getHierarchyMetaData();
        List externalRGOs = metaData.findExternalRGOs(element);
        excludeChildRootMEs(externalRGOs);
            for (Iterator iterator = externalRGOs.iterator(); iterator
                    .hasNext();) {
                PersistableModelComponent target = ((NonRootModelElement) iterator
                        .next()).getPersistableComponent();
                if ( target != null ) {
            return target.isReadOnly(false, null);
        }
        }
      }
      
      return false;
    }
    
    /**
     * This method allows checks to occur on the component and the components
     * parent if specified
     * 
     * @param checkParent
     * @return component read-only status
     */
    public boolean isReadOnly(boolean checkParent) {
      return isReadOnly(checkParent, null);
    }
    
    public boolean isLoaded() {
        return !(componentRootME == null || componentRootME.isProxy());
    }
    
    public boolean isOrphaned(){
        return PersistenceManager.isOrphaned(this);
        }

    public boolean isPersisted() {
        return getFile().exists();
    }

    public IFile getFile() {
      if (underlyingResource != null)
        return underlyingResource;
      else
        return null;
    }

    // we need to synchronized with loaded MEs
    public void refresh() throws CoreException {
        if (!isLoaded()) {
            // not loaded, ignore
            return;
        }

        IPersistenceHierarchyMetaData metadata = PersistenceManager
                .getHierarchyMetaData();
        List childrenMEs = metadata
                .getChildrenComponentRootModelElements(componentRootME);

        List addedChildren = new Vector();

        for (int i = 0; i < childrenMEs.size(); i++) {
            NonRootModelElement child = (NonRootModelElement) childrenMEs
                    .get(i);
            String name = metadata.getRootElementName(child);

            PersistableModelComponent childComponent = getChild(name);
            if (childComponent == null) {
                PersistableModelComponent com = new PersistableModelComponent(
                        child, componentRootME.getFile());
                addedChildren.add(com);
            } else if (childComponent.getRootModelElement() != child) {
                Throwable e = new Throwable();
                e.fillInStackTrace();
                CorePlugin.logError("Component name conflict:"
                        + childComponent.getFullPath(), e);
            }
        }

        if (addedChildren.size() > 0) {
            for (Iterator i = addedChildren.iterator(); i.hasNext();) {
                PersistableModelComponent childComponent = (PersistableModelComponent) i
                        .next();
                childComponent.refresh();
            }
        }
    }
    
    boolean persisting = false;

    public boolean isPersisting(){
      return persisting;
    }

    public void persist() throws CoreException {
      persist(new NullProgressMonitor());
    }

    public void persist(IProgressMonitor monitor) throws CoreException {
        if (!isLoaded()) {
            // not loaded, ignore
            return;
        }
        
        // if the persistence version will change then persist the root component
        PersistenceManager.updatePersistenceVersion(this);
        
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

        IPath componentParentDirectory = getContainingDirectoryPath();
        IFolder containingFolder = workspaceRoot
                .getFolder(componentParentDirectory);
        if (!containingFolder.exists()) {
            if (!containingFolder.getParent().exists()) {
                // the persist is part of a deletion, and the parent has already
                // been deleted
                return;
            }
            containingFolder.create(true, true, monitor);
        }

        IFile file = getFile();

        persisting = true;
        // create an export-factory and have it perform the persistence
        AbstractModelExportFactory factory = AbstractModelExportFactory
                .getInstance();
        try {
            IRunnableWithProgress runnable = factory.create(componentRootME,
                    file.getLocation().toString(), true);
            runnable.run(monitor);
        
            // get Eclipse to notice that the model's file has changed on disk
            containingFolder.refreshLocal(IFile.DEPTH_ONE,
                monitor);
        } catch (CoreException x) {
            CorePlugin.logError("Could not refresh persisted model file.", x);
        } catch (Exception e) {
            CorePlugin.logError("Could not persist model", e);
        } finally {
          persisting = false;
        }
    }

    public void persistSelfAndChildren() throws CoreException {
        this.persist();
        for (Iterator iterator = this.getChildren().iterator(); iterator
                .hasNext();) {
            PersistableModelComponent child = (PersistableModelComponent) iterator
                    .next();
            child.persistSelfAndChildren();
        }
    }
/**
 * This method loads component only if not already loaded. To reload use 
 * overloaded method.
 * @param monitor
 * @throws CoreException
 */
    public void load(IProgressMonitor monitor) throws CoreException {
        if (status == STATUS_LOADING) {
            // recursive call from some where
            // any code causing cotrol to reach here should be addressed
            // probably it requires disabled lazy loading
            return;
          }
        // use the model root defined by the componentRootMe, if it exists
        // this is important when we are in the middle of a resource rename
        // for example, when a project is renamed in the Resource Navigator,
        // cross component links must be updated in the existing model root
        // not in the newly named one (which is what would happen if
        // getUniqueID()
        // was called, because in that case the underlyingResource path has
        // already been updated, but the model root id hasn't yet)
        if ( componentRootME != null ) {
            load((Ooaofooa) componentRootME.getModelRoot(), monitor, false,false);
        } else {
            load(Ooaofooa.getInstance(getUniqueID()), monitor, false,false);
        }
    }

    public void load(IProgressMonitor monitor, boolean parseOal,boolean reload)
            throws CoreException {
        if (status == STATUS_LOADING) {
            // recursive call from some where
            // any code causing cotrol to reach here should be addressed
            // probably it requires disabled lazy loading
            return;
          }
        // see above
        if ( componentRootME != null ) {
            load((Ooaofooa) componentRootME.getModelRoot(), monitor, parseOal,reload);
        } else {
            load(Ooaofooa.getInstance(getUniqueID()), monitor, parseOal,reload);
        }
    }

    public void load(Ooaofooa modelRoot, IProgressMonitor monitor)
            throws CoreException {
        if (status == STATUS_LOADING) {
            // recursive call from some where
            // any code causing cotrol to reach here should be addressed
            // probably it requires disabled lazy loading
            return;
          }
        load(modelRoot, monitor, false,false);
    }
    
    public synchronized void load(Ooaofooa modelRoot, IProgressMonitor monitor,
            boolean parseOal,boolean reload) throws CoreException {
    	
        if(!reload && isLoaded())
        return;
    	
        // do not allow load if there is no xtUML nature
		if (!getFile().getProject().isAccessible()
				|| !getFile().getProject().hasNature(XtUMLNature.ID)
				|| PersistenceManager.getRootComponent(getFile().getProject()) == null) {
        	return;
        }
        
    	// do not load if the persistence version is not acceptable
    	if(!PersistenceManager.isPersistenceVersionAcceptable(this)) {
    		return;
    	}
    	
        int oldStatus = status;
      try {
            status = STATUS_LOADING;    
        
            IModelImport importer = createImporter(modelRoot, parseOal);
        if (importer == null) {
          // we're trying to load a file in a closed project
             status=oldStatus;
          return;
        }
        
        int validate_result = importer.countAndValidateInsertStatements();
        if (validate_result > 0) {
          importer.run(monitor);
          NonRootModelElement rootME = importer.getRootModelElement();
          
          if (rootME == null) {
            CorePlugin.logError("Error while loading model from "
                            + getFullPath() + " ERROR:"
                + importer.getErrorMessage(), new Throwable()
                .fillInStackTrace());
          }else{
            importer.finishComponentLoad(monitor, true);
                status = STATUS_LOADED;
            
                if( !underlyingResource.equals(dummyCompareName)){
              try{
                        checkComponentConsistancy(rootME);
              }catch (CoreException e) {
                        status = STATUS_NOTLOADED;
                        PersistenceManager.addInconsistentComponent(this);
                        deleteSelfAndChildren();
                        UIUtil.refresh(getRootModelElement());
                throw new WorkbenchException("Error while loading model from "
                                + getFullPath(), e);
              }
            }
            Ooaofooa.getDefaultInstance().fireModelElementLoaded(rootME);
          }
        }
      } catch (FileNotFoundException e) {
        throw new WorkbenchException("Error while loading model from "
                    + getFullPath(), e);
      } catch (IOException e) {
        throw new WorkbenchException("Error while loading model from "
                    + getFullPath(), e);
      } catch (InterruptedException e) {
        throw new WorkbenchException("Error while loading model from "
                    + getFullPath(), e);
      } catch (InvocationTargetException e) {
        throw new WorkbenchException("Error while loading model from "
                    + getFullPath(), e);
      } finally {
            if (status != STATUS_LOADED)
                status = oldStatus;
      }

    }
    private void checkComponentConsistancy( NonRootModelElement rootME) throws CoreException{
        String fileNameWithoutExtension = null;        
        if ( underlyingResource == null){
            throw new WorkbenchException(
                    "Component's file does not exist:");
        }else{ 
            fileNameWithoutExtension = underlyingResource.getFullPath().removeFileExtension().lastSegment();
            String folderName = underlyingResource.getFullPath().removeLastSegments(1).lastSegment();
            if(! fileNameWithoutExtension.equals(folderName)){
                throw new WorkbenchException(
                        "Component's Parent folder and file name does not same:"
                                + getFullPath());
            }
        }
        if(rootME != null){
            if (rootME == null) {
                throw new WorkbenchException(
                        "Component's Root Model Element does not exist:"
                        + getFullPath());
            }
            if( ! (rootME instanceof SystemModel_c || rootME instanceof Domain_c || rootME instanceof DataTypePackage_c)){
                NonRootModelElement myParentRootME = PersistenceManager.getHierarchyMetaData().getParentComponentRootModelElement(rootME,false);
                if (myParentRootME != null){// in case of component is being created and not yet relate with others.
                    PersistableModelComponent myParentPMC = myParentRootME.getPersistableComponent();
                    if (myParentPMC != getParent()){
                        throw new WorkbenchException(
                                "Component's Parent child ID Mismatch:" 
                                + getFullPath());
                    }
                }
            }
            if (rootME instanceof InstanceStateMachine_c){
                if( fileNameWithoutExtension != null && ! fileNameWithoutExtension.equals("InstanceStateMachine")){
                    throw new WorkbenchException(
                            "Instance State machine must have \"InstanceStateMachine.xtuml\" file name:"
                            + getFullPath());
                }
            }
            else if (rootME instanceof ClassStateMachine_c ){
                if( fileNameWithoutExtension != null && ! fileNameWithoutExtension.equals("ClassStateMachine")){
                    throw new WorkbenchException(
                            "Class State machine must have \"ClassStateMachine.xtuml\" file name:"
                            + getFullPath());
                }
            }
            else if(fileNameWithoutExtension != null && ! fileNameWithoutExtension.equals(CoreUtil.getName(rootME))){
                throw new WorkbenchException(
                        "Component's file name and root model element name does not match:"
                        + getFullPath());
            }
        }
    }

    private IModelImport createImporter(IFile file, Ooaofooa modelRoot,
            boolean parseOal) throws IOException {
        if (!file.exists() ) {
            // can't import file from a closed project or that doesn't exist
            return null;
        }

        AbstractModelImportFactory factory = CorePlugin.getModelImportFactory();

        if (modelRoot == null) {
            modelRoot = Ooaofooa.getInstance(getUniqueID());
        }
        
        return factory.create(file, modelRoot, this, parseOal, true, true,
                false);
    }
    
    private IModelImport createImporter(Ooaofooa modelRoot, boolean parseOal)
            throws IOException {

        return createImporter(getFile(), modelRoot, parseOal);
    }
    
    public PersistableModelComponent getDomainComponent() {
        PersistableModelComponent component = this;
        while (!component.getComponentType().equals("Domain")) {
            component = component.getParent();
            if (component == null) {
                return null;
            }
        }
        return component;
    }

    public String getUniqueID() {
        IPath path = getContainingDirectoryPath();
        int segmentCount = path.segmentCount();
        //projectname/models/system1/domain1
        if (segmentCount == 3) {
            // in case of system model
            return Ooaofooa.getDefaultInstance().getId();
        } else if (segmentCount < 3) {
          return path.toString();
        }
        
        //remove all trails except systemmodel and domain
        path = path.removeLastSegments(segmentCount - 4);

        String unique_id = path.toString() +
                    // add folder name to id
                    "/" + path.segment(path.segmentCount() - 1)
                    // add filename to id
                    + "." + Ooaofooa.MODELS_EXT;
        
        return unique_id; 
    }

    private String getComponentType(IFile componentFile) throws CoreException {
        try {
            IModelImport im = createImporter(componentFile, null, false);
            if (im == null) {
                // we're trying to load a file in a closed project
                return "";
            }
            return im.getHeader().getModelComponentType();
        } catch (FileNotFoundException e) {
            throw new WorkbenchException(
                    "Could not determine component type by reading header", e);
        } catch (IOException e) {
            throw new WorkbenchException(
                    "Could not determine component type by reading header", e);
        }
    }
    
    public  void deleteSelf() {
        NonRootModelElement saveRootME = componentRootME;
        clearDatabase();
        delete();
        if ( saveRootME != null ) {
            Ooaofooa.getDefaultInstance().fireModelElementUnloaded(saveRootME);
			Ooaofooa.getDefaultInstance().fireModelElementDeleted(
						new BaseModelDelta(
								Modeleventnotification_c.DELTA_DELETE,
								saveRootME));
        }
    }

    public void deleteSelfAndChildren(){
      if (underlyingResource != null){
        deleteChildren();
        deleteSelf();
      }
    }

    public void deleteChildren(){
        for (Iterator iterator = getChildren().iterator(); iterator.hasNext();) {
            PersistableModelComponent child = (PersistableModelComponent) iterator
                    .next();
            child.deleteSelfAndChildren();
        }
    }

    public synchronized void  clearDatabase() {
        boolean deletingProxy = false;
      if(!isLoaded()){
            // check if the proxy is loaded
            if (componentRootME != null && componentRootME.isProxy()) {
                deletingProxy = true;
            } else {
                return;
            }
      }
        status=STATUS_UNLOADING;
        
        ModelRoot modelRoot = componentRootME.getModelRoot();
        
        ModelRoot.disableChangeNotification();
        
        try {
        if (!deletingProxy) {
            Function_c[] functions = (Function_c[]) PersistenceManager
                    .getHierarchyMetaData().getActivityModelElements(
                            componentRootME, Function_c.class);
          if(functions != null){
            for (int i = 0; i < functions.length; i++) {
              Body_c body = Body_c.getOneACT_ACTOnR698(FunctionBody_c
                  .getOneACT_FNBOnR695(functions[i]));
                    if(body != null){
                        body.Dispose();
                    }
          }
          }
          
            Bridge_c[] bridges = (Bridge_c[]) PersistenceManager
                    .getHierarchyMetaData().getActivityModelElements(
                            componentRootME, Bridge_c.class);
          if(bridges != null){
            for (int i = 0; i < bridges.length; i++) {
              Body_c body = Body_c.getOneACT_ACTOnR698(BridgeBody_c
                  .getOneACT_BRBOnR697(bridges[i]));
                    if(body != null){
                        body.Dispose();
                    }
          }
          }
          
            Operation_c[] operations = (Operation_c[]) PersistenceManager
                    .getHierarchyMetaData().getActivityModelElements(
                            componentRootME, Operation_c.class);
          if(operations != null){
            for (int i = 0; i < operations.length; i++) {
              Body_c body = Body_c.getOneACT_ACTOnR698(OperationBody_c
                  .getOneACT_OPBOnR696(operations[i]));
                    if(body != null){
                        body.Dispose();
                    }
          }
          }
          
            DerivedBaseAttribute_c[] derivedAttributes = (DerivedBaseAttribute_c[]) PersistenceManager
                    .getHierarchyMetaData().getActivityModelElements(
                            componentRootME, DerivedBaseAttribute_c.class);
          if(derivedAttributes != null){
            for (int i = 0; i < derivedAttributes.length; i++) {
                    Body_c body = Body_c
                            .getOneACT_ACTOnR698(DerivedAttributeBody_c
                  .getOneACT_DABOnR693(derivedAttributes[i]));
                    if(body != null){
                        body.Dispose();
                    }
          }
          }
          
            StateMachineState_c[] states = (StateMachineState_c[]) PersistenceManager
                    .getHierarchyMetaData().getActivityModelElements(
                            componentRootME, StateMachineState_c.class);
            disposeStates(states);
        }
             
        // unrelate any global elements in system
        if(componentRootME instanceof SystemModel_c) {
				GlobalElementInSystem_c[] gis = GlobalElementInSystem_c
						.getManyG_EISsOnR9100((SystemModel_c) componentRootME);
				for(int i = 0; i < gis.length; i++) {
					PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR9100(gis[i]);
					gis[i].unrelateAcrossR9100From(pe);
					gis[i].unrelateAcrossR9100From((SystemModel_c) componentRootME);
					gis[i].delete();
				}
				SystemDatatypePackage_c[] pkgs = SystemDatatypePackage_c.getManySLD_SDPsOnR4400((SystemModel_c) componentRootME);
				for(int i = 0; i < pkgs.length; i++) {
					DataTypePackage_c pkg = DataTypePackage_c.getOneS_DPKOnR4400(pkgs[i]);
					pkg.unrelateAcrossR4400From(pkgs[i]);
					pkgs[i]
							.unrelateAcrossR4400From((SystemModel_c) componentRootME);
				}
        }
        deleteME(componentRootME);
        componentRootME = null;
        modelRoot.clearUnreferencedProxies();
        }
        finally {
            ModelRoot.enableChangeNotification();
        }
        status=STATUS_NOTLOADED;
    }
     
    private void disposeStates(StateMachineState_c[] states){
      if(states == null){
        return;
      }
      
    for (int i = 0; i < states.length; i++) {
            Body_c body = Body_c.getOneACT_ACTOnR698(StateActionBody_c
                    .getOneACT_SABOnR691(Action_c
          .getOneSM_ACTOnR514(ActionHome_c
                                    .getOneSM_AHOnR513(MooreActionHome_c
                                            .getOneSM_MOAHOnR511(states[i])))));
            if(body != null){
                body.Dispose();
            }
    }
    }

	private void deleteME(NonRootModelElement me) {
		if (me.getPersistableComponent() != this) {
			return;
		}

		IPersistenceHierarchyMetaData metaData = PersistenceManager
				.getHierarchyMetaData();

		List<?> findExternalRGOs = metaData.findExternalRGOs(me, false);
		boolean hasExternalRGO = !findExternalRGOs.isEmpty();
		List<?> children = metaData.getChildren(me, true);
		for (int i = 0; i < children.size(); i++) {
			NonRootModelElement child = (NonRootModelElement) children.get(i);
			deleteME(child);
		}
		if (hasExternalRGO) {
			me.convertToProxy();
		} else {
			me.delete_unchecked();
		}
		me.batchUnrelate();
	}
    
    public void excludeChildRootMEs(List externalRGOs) {
        Collection children = getChildren();
        for (Iterator iterator = children.iterator(); iterator.hasNext();) {
            PersistableModelComponent child = (PersistableModelComponent) iterator
                    .next();
            externalRGOs.remove(child.getRootModelElement());
        }
    }
  
    void setComponent(NonRootModelElement me){
      me.setComponent(this);
    }

    public void updateResource(IFile newFile) {
        updateResource(newFile, getChildren());
    }
    private void updateResource(IFile newFile, Collection children) {
        // remap component as key(path) has been changed
        NonRootModelElement thisMe = getRootModelElement();
        for (Iterator iterator = children.iterator(); iterator.hasNext();) {
            PersistableModelComponent child = (PersistableModelComponent) iterator
                    .next();
            String name = child.getName();
            IPath newChildPath = getChildPath(newFile.getFullPath(), name);
            IFile newChildFile = ResourcesPlugin.getWorkspace().getRoot()
                    .getFile(newChildPath);
            child.updateResource(newChildFile);
        }
        PersistenceManager.removeComponent(this);
        underlyingResource = newFile;
        PersistenceManager.addComponent(this);
        if (thisMe != null && thisMe.isProxy()) {
            thisMe.updateContentPath(underlyingResource.getFullPath());
        }
    }

    public static void ensureCoreDataTypesAvailable(ModelRoot modelRoot) {
        if (!modelRoot.isFullModelLoaded()) {
            // we'll go into an infinite loop in the isFullModelLoaded() case
            // during single file loads (conversion and import)
            Domain_c dom = Domain_c.DomainInstance(modelRoot);
            if (dom != null) {
              // if the domain is the formal content of
              // a component, ensure that the system
              // level core types are loaded
              Component_c formalComponent = Component_c
            .getOneC_COnR4204(DomainAsComponent_c
                .getOneCN_DCOnR4204(dom));
              if(formalComponent != null) {
                SystemModel_c system = SystemModel_c
              .getOneS_SYSOnR4606(ComponentPackage_c
                  .getOneCP_CPOnR4608(formalComponent));
                ensureSystemCoreDataTypesAvailable(system);
              } else {
                  PersistableModelComponent component = dom
                          .getPersistableComponent();
                  if (component != null) {
                      IPath newChildPath = getChildPath(component.getFullPath(), 
                              Ooaofooa.Getcoredatatypespackagename(modelRoot));
                      PersistableModelComponent dt_comp = findOrCreateInstance(newChildPath);
                      if (dt_comp != null && !dt_comp.isLoaded()) {
                          try {
                              IProgressMonitor monitor = new NullProgressMonitor();
                              dt_comp.load(monitor);
                          } catch (CoreException e) {
                              CorePlugin.logError(
                                      "Unable to load core data types", e);
                          }
                      }
                  }
              }
            }
        }
    }    

    public static void ensureSystemCoreDataTypesAvailable(final SystemModel_c system) {
      DataTypePackage_c dtPkg = DataTypePackage_c.getOneS_DPKOnR4401(SystemDatatypeInPackage_c.getManySLD_SDINPsOnR4402(system), new ClassQueryInterface_c() {
      public boolean evaluate(Object candidate) {
        return ((DataTypePackage_c)candidate).getName().equals(Ooaofooa.Getcoredatatypespackagename(system.getModelRoot()));
      }
    });
      if(dtPkg != null) {
        PersistableModelComponent component = dtPkg.getPersistableComponent();
        if(component != null && !component.isLoaded()) {
          try {
            IProgressMonitor monitor = new NullProgressMonitor();
            component.load(monitor);
          } catch (CoreException e) {
            CorePlugin.logError(
                "Unable to load core data types", e);
          }
        }
      }
    }
    
    public static void ensureComponentPackageCoreDataTypesAvailable(ComponentPackage_c compPackage) {
      SystemModel_c system = SystemModel_c.getOneS_SYSOnR4606(compPackage);
      ensureSystemCoreDataTypesAvailable(system);
    }
    
    public static void ensureComponentCoreDataTypesAvailable(Component_c component) {
      SystemModel_c system = SystemModel_c
        .getOneS_SYSOnR4606(ComponentPackage_c
            .getOneCP_CPOnR4608(component));
      ensureSystemCoreDataTypesAvailable(system);
    }
    
    public static void ensureDataTypesAvailable(ModelRoot modelRoot) {
        PersistenceManager.ensureAllInstancesLoaded(modelRoot, DataType_c.class);
    }
    public int getStatus(){
        return status;
    }
  public void resetComponentForChildren(IPath parentPath,
                                             String Name, Collection children) {
      parentPath = parentPath.append("\\" + Name);
      parentPath = parentPath.addFileExtension(Ooaofooa.MODELS_EXT);
      IFile newFile = ResourcesPlugin.getWorkspace().getRoot()
                                                           .getFile(parentPath);
      for (Iterator iterator = children.iterator(); iterator.hasNext();) {
        PersistableModelComponent child = (PersistableModelComponent) iterator
                                                                        .next();
        String name = child.getName();
        IPath newChildPath = getChildPath(newFile.getFullPath(), name);
        IFile newChildFile = ResourcesPlugin.getWorkspace().getRoot()
                                                         .getFile(newChildPath);
        child.updateResource(newChildFile);
      }
    }
    public int compareTo(Object o) {
      PersistableModelComponent pmc = (PersistableModelComponent)o;
      if (pmc.getFullPath().equals(getFullPath())) {
        return 0;
      }
      if(getFullPath().removeLastSegments(1).isPrefixOf(pmc.getFullPath())) {
        return 1;
      }
      return getFullPath().toString().compareTo(pmc.getFullPath().toString());
    }
}