package com.mentor.nucleus.bp.ui.text;
//====================================================================
//
// File:      $RCSfile: ModelElementID.java,v $
// Version:   $Revision: 1.17 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.ui.text.ModelAdapter.IModelElementAdapter;

/**
 * This class represents a collection of data that collectively identifies a
 * model element.
 */
public class ModelElementID implements Serializable, Comparable<ModelElementID>
{
  private static final String MODEL_ROOT_ID = "ModelRootID"; //$NON-NLS-1$
  private static final String MODEL_ELEMENT_TYPE = "ModelElementType"; //$NON-NLS-1$
  private static final String MODEL_ELEMENT_ID_COUNT = "ModelElementIDCount"; //$NON-NLS-1$
  private static final String MODEL_ELEMENT_ID = "ModelElementID"; //$NON-NLS-1$
  private static final String MODEL_ELEMENT_LAST_NAME = "ModelElementLastName"; //$NON-NLS-1$
  private static final String MODEL_COMPONENT_PATH = "ModelComponentPath"; //$NON-NLS-1$
  
  private String modelRootID;
  // We persist last Name so that we can obtain place holder file even if model is not loaded.
  private String lastName;
  private String componentPath;
  
  private static final long serialVersionUID = 200503311547L;

  private String type;
  
  private ArrayList<String> idList = new ArrayList<String>();
  
  /*
   * reference to resolved model element. This is not last good known model element
   * and may be null. 
   */
  transient NonRootModelElement modelElement;
  transient Ooaofooa modelRoot;
  
  protected ModelElementID(String aModelRootID, String aType, String aPath)
  {
  	modelRoot = Ooaofooa.getInstance(aModelRootID, false);
  	if(modelRoot == null){
  		throw new IllegalArgumentException("Could not create/load instance of model root from given model root id");
  	}
  	
  	modelRootID = modelRoot.getId();
  	type = aType;
    componentPath = aPath;
  }

  protected ModelElementID(IMemento memento) throws CoreException
  {
  	readFrom(memento);
  }
  
  public String getModelRootID(){
	modelRootID = modelRoot.getId();
  	return modelRootID;
  }
  
  public Ooaofooa getModelRoot(){
  	return modelRoot;
  }
  
  public String getType()
  {
	return type;
  }
  
  public String getLastValidName(){
  	if(resolve() != null){
  		IModelElementAdapter modelElementAdaptor = ModelAdapter.getModelElementAdapter(getType());
  		lastName = modelElementAdaptor.getName(modelElement);
  	}
  	
  	return lastName;
  }
  
  public String getComponentPath()
  {
      // check if the component path has changed
      // since when we were created
      if ( modelElement != null )
      {
          IFile f = modelElement.getFile();
          if ( f != null )
          {
              String currentPath = f.getFullPath().toString();
              if ( !currentPath.equals(componentPath) )
              {
                  componentPath = currentPath;
              }
          }
      }
      return componentPath;
  }
  public int getIdCount(){
  	return idList.size();
  }
  
  public String getId(int i)
  {
	return (String)idList.get(i);
  }

  protected void add(String id){
  	idList.add(id);
  }
  
  public boolean equals(Object object){
  	if(object instanceof ModelElementID){
  		return equals((ModelElementID)object);
  	}
  	return false;
  }
  
  public boolean equals(ModelElementID other)
  {
    boolean equals = getModelRootID().equals(other.getModelRootID());
    if(equals){
    	equals = type.equals(other.type);
    	if(equals){
    		equals = idList.equals(other.idList);
    	}
    }
  	
    return equals;
  }
  
  /**
   * @return true if this object represents the given model element
   */
  public boolean equals(NonRootModelElement modelElement){
  	if(type.equals(modelElement.getClass().getName())){
  		IModelElementAdapter adapter = ModelAdapter.getModelElementAdapter(modelElement);
  		if(adapter != null){
  			return equals(adapter.createModelElementID(modelElement));
  		}
  	}
  	return false;
  }
  
  public int compareTo(ModelElementID other){
      int result = getModelRootID().compareTo(other.getModelRootID());
      if(result == 0){
          result = type.compareTo(other.type);
          if(result == 0){
              for (int i = 0; i < idList.size(); i++) {
                  result = idList.get(i).compareTo(other.idList.get(i));
                  if(result != 0){
                      break;
  }
              }
          }
      }
      return result;
  }
   
  public String getUniqueStringID(){
  	StringBuffer uniqueString = new StringBuffer();
  	for(int i=0; i<idList.size(); i++){
  		uniqueString.append(idList.get(i));
  	}
    uniqueString.append(type);
    uniqueString.append(System.identityHashCode(getModelRoot()));
  	return uniqueString.toString();
  	
  }
  
  /**
   * Resolves a model element
   * @return null if could not resolve to a model element
   */
  public NonRootModelElement resolve(){
  	// modelElement.isOrphaned() check handles a special case where a reload of 
  	// same model technically makes a model element orphaned but actually has a
  	// corresponding model element reloaded into memory and hence requires
  	// re-resolving.
  	if(modelElement == null || modelElement.isOrphaned()){
  		IModelElementAdapter modelElementAdaptor = ModelAdapter.getModelElementAdapter(getType());
  		
  		try {
			modelElement = modelElementAdaptor.resolveModelElement(this);
		} catch (IllegalArgumentException e) {
			modelElement = null;
		}
		
  		if(modelElement != null){
  			lastName = modelElementAdaptor.getName(modelElement);
  		}
  	}
  	return modelElement;
  }
  
    public InputStream createSerializedInputStream() throws IOException{
    	return new ByteArrayInputStream(getBytes());
    }
  
    private byte[] getBytes() throws IOException{
        // update lastName and modelRootID before writing
  	    getModelRootID();
  	    getLastValidName();    
        // if the given file already exists

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(buffer);
		out.writeObject(this);
		
		return buffer.toByteArray();
    }
  
    public void saveTo(IFile file) throws CoreException
    {
        try {
			file.create(createSerializedInputStream(), true, new NullProgressMonitor());
		} catch (IOException e) {
			throw new WorkbenchException("IOException while saving ModelElementID", e); //$NON-NLS-1$
		}
    }
    
    public boolean isRepresentedBy(File file){
    	if(!file.exists()){
    		return false;
    	}
    	
    	ModelElementID idOfFile = null;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			idOfFile = createInstance(in);
		} catch (FileNotFoundException e) {
		} catch (CoreException e) {
			TextPlugin.logError("Could not read ModelElementID from " + file, e); //$NON-NLS-1$
		}
		
    	return (idOfFile == null)?false:equals(idOfFile);
    }
  
  public void saveTo(IMemento memento) {
  	memento.putString(MODEL_ROOT_ID, getModelRootID());
  	memento.putString(MODEL_ELEMENT_TYPE, getType());
  	memento.putString(MODEL_ELEMENT_LAST_NAME, getLastValidName());
    memento.putString(MODEL_COMPONENT_PATH, getComponentPath());
	memento.putInteger(MODEL_ELEMENT_ID_COUNT, getIdCount());
	for(int i=0; i<getIdCount(); i++){
		memento.putString(MODEL_ELEMENT_ID + i, getId(i)); 
	}
  }

  protected void readFrom(IMemento memento) throws CoreException{
  	String errorMessage = "Memento does not contain required information ";
  	modelRootID = memento.getString(MODEL_ROOT_ID);
  	// if the modelRootID is of the old format
  	String[] parts = modelRootID.split("/");
  	if(parts.length == 3) {
  		// get an id which would match the new format
  		String projectName = parts[1];
  		String domainName = parts[2];
  		modelRootID = Ooaofooa.createModelRootId(projectName, domainName, false);
  	}
  	if(modelRootID == null){
  		throw new WorkbenchException(errorMessage + MODEL_ROOT_ID);
  	}
  	
  	modelRoot = Ooaofooa.getInstance(modelRootID, false);
  	if(modelRoot == null){
  		throw new WorkbenchException("Could not create/load instance of model root from given model root id");
  	}
  	
  	type = memento.getString(MODEL_ELEMENT_TYPE);
  	if(type == null){
  		throw new WorkbenchException(errorMessage + MODEL_ELEMENT_TYPE);
  	}
  	
  	lastName = memento.getString(MODEL_ELEMENT_LAST_NAME);
  	if(lastName == null){
  		throw new WorkbenchException(errorMessage + MODEL_ELEMENT_LAST_NAME);
  	}

    componentPath = memento.getString(MODEL_COMPONENT_PATH);
    if(componentPath == null){
        throw new WorkbenchException(errorMessage + MODEL_COMPONENT_PATH);
    }

  	Integer idCountObj = memento.getInteger(MODEL_ELEMENT_ID_COUNT);
  	if(idCountObj == null){
  		throw new WorkbenchException(errorMessage + MODEL_ELEMENT_ID_COUNT);
  	}
  	
	int idCount = idCountObj.intValue();
	for(int i=0; i<idCount; i++){
		String id = memento.getString(MODEL_ELEMENT_ID + i);
		if(id == null){
	  		throw new WorkbenchException(errorMessage + MODEL_ELEMENT_ID + i);
		}
		idList.add(id);
	}
  }
  
  public static ModelElementID createInstance(IMemento memento) throws CoreException{
  	return new ModelElementID(memento);
  }
  
  public static ModelElementID createInstance(IFile file) throws CoreException{
  	if(!file.exists()){
  		throw new IllegalArgumentException("Given file does not exits:" + file.getLocation()); //$NON-NLS-1$
  	}
  	
  	ModelElementID meId = null;
  	InputStream in = null;
  	
  	try {
  		in = file.getContents();
  		meId = createInstance(in);

        /*
         * In order to avoid re-writing of place holder files when a model 
         * element is renamed, we attempt to determine following attributes from 
         * the path of place holder
         * - modelRootID
         * - lastName
         * - componentPath
         * 
         * However we still need to rename the place holder file.
         */
        
        //Remove name of place holder file.
        IPath componentPath = file.getFullPath().removeLastSegments(1);
        //Deduce name and path of the component from the path of place holder file
        String componentName = componentPath.lastSegment();
        componentPath = componentPath.append(componentName + "." + Ooaofooa.MODELS_EXT);
        
        String mrId = getModelRootID(componentPath);
        if(mrId != null){
            meId.modelRootID = mrId;
            meId.modelRoot = Ooaofooa.getInstance(mrId, false);
            meId.lastName = componentName;
            meId.componentPath = componentPath.toString(); 
        }
	} finally {
        if(in != null){
            try {
                in.close();
            } catch (IOException e1) {
            }    
        }
    }
  	return meId;
  }
  //TODO: this may be usable for other code, so should be moved to a common utility class
  // or to a class where there already exists similiar root id code such as Ooaofooa,
  // so that maintenance of this code is easy.
  private static String getModelRootID(IPath path){
      String rootID = null;
      int segmentCount = path.segmentCount();
      if(segmentCount == 3){
          //this is system component
          rootID = Ooaofooa.DEFAULT_WORKING_MODELSPACE;
      }else if(segmentCount > 3){
          //this is any valid component other then system's
          //Remove all path elements after first four elements, which would be system
          //name and domain name
          path = path.removeLastSegments(path.segmentCount()-4);
          path = path.append(path.lastSegment());
          rootID = path.toString() + "." + Ooaofooa.MODELS_EXT;
      }//else return null
      return rootID;
  }

  private static ModelElementID createInstance(InputStream inputStream) throws CoreException{
  	ModelElementID meId = null;
  	
    ObjectInputStream in = null;
  	try {
		in = new ObjectInputStream(inputStream);
		meId = (ModelElementID)in.readObject();
	} catch (IOException e) {
		throw new WorkbenchException("Error reading ModelElementPersistableID from file", e); //$NON-NLS-1$
	} catch (ClassNotFoundException e) {
		throw new WorkbenchException("Error reading ModelElementPersistableID from file", e); //$NON-NLS-1$
	} finally {
        if(in != null){
            try {
                in.close();
            } catch (IOException e1) {
            }    
        }
    }
  	
  	return meId;
  	
  }
}
