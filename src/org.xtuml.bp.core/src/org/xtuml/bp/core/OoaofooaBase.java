//=====================================================================
//
//File:      $RCSfile: OoaofooaBase.java,v $
//Version:   $Revision: 1.39 $
//Modified:  $Date: 2013/05/10 04:55:54 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

package org.xtuml.bp.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ILogger;
import org.xtuml.bp.core.common.IModelChangeListener;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.InstanceList;
import org.xtuml.bp.core.common.ModelChangeAdapter;
import org.xtuml.bp.core.common.ModelChangedEvent;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.TraceLogger;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.util.OoaofgraphicsUtil;

/**
 * Holds all the non-platform-specific, Java-only methods of Ooaofooa.  
 * All instances of this class must also be instances of Ooaofooa.  
 * Externally, other classes should only know about Ooaofooa, and not 
 * this class.  Call this class's thisAsOoaofooa() method as a 
 * convenient way to cast an instance of this class to an Ooaofooa.   
 */
abstract class OoaofooaBase extends ModelRoot 
{
    public static final String MODELS_DIRNAME = "models";

    public static final String MODELS_EXT = "xtuml";
    
    public static ILogger log = new TraceLogger("org.xtuml.bp.core/debug");
    private SystemModel_c m_root = null;

    private ModelChangeListener modelChangeListener;
    
    private ITransactionListener transactionListener;
    
    protected static Map<String, OoaofooaBase> rootInstanceMap = new Hashtable<String, OoaofooaBase>();

    /**
     * Constructor 
     */
    protected OoaofooaBase(String aRootId)
    {
        super(aRootId);

        rootInstanceMap.put(aRootId, this);
        
        if(isDefaultWorkModelRoot()) {
            modelChangeListener = new ModelChangeListener();
            transactionListener = new ModelRootTransactionListener();
            Ooaofooa.getDefaultInstance().addModelChangeListener(modelChangeListener);
            TransactionManager.getSingleton().addTransactionListener(
                    transactionListener, true);
        }
  
        // if this model-root is not one of the those to which
        // this class should not add global model-change listeners
        if (!idsNotToAddListenersTo.contains(rootId)) {
            // for each stateless model-change listener this class has been told to 
            // add to each of its instances
            for (int i = 0; i < modelChangeListenersToAdd.size(); i++) {
                // add this listener to this model-root
                IModelChangeListener listener = (IModelChangeListener)
                    modelChangeListenersToAdd.get(i);
                addModelChangeListener(listener);
            }
        }
    }
    
    /**
     * Returns an array of all the currently existing instances of this class.
     */
    public static Ooaofooa[] getInstances()
    {
        return (Ooaofooa[])rootInstanceMap.values().toArray(
                new Ooaofooa[rootInstanceMap.size()]);
    }

    public static Ooaofooa[] getInstancesUnderSystem(ModelRoot modelRoot) {
        String systemName = getProjectNameFromModelRootId(modelRoot.getId());
        return getInstancesUnderSystem(systemName);
    }
    
    public static Ooaofooa[] getInstancesUnderSystem(String systemName) {
        List<Ooaofooa> list = new ArrayList<Ooaofooa>();
        Ooaofooa[] roots = getInstances();
        for(int i = 0; i < roots.length; i++) {
            if(!roots[i].getId().equals("") && getProjectNameFromModelRootId(roots[i].getId()).equals(systemName))
                list.add(roots[i]);
        }
        return list.toArray(new Ooaofooa[list.size()]);
    }
    
    /**
     * Encapsulates the policy for forming a model-root ID from its constituent parts. Returns the model-root formed by
     * the combination of the given system- and domain-names, adding the standard model-file extension only if specified
     * (as, in some situations, the given domain name will already contain the extension).
     */
    public static String createModelRootId(String systemName, 
        String domainName, boolean addExtension) 
    {
        if(!addExtension) {
            if(domainName.indexOf("." + MODELS_EXT) != -1) {
                domainName = domainName.replaceAll("." + MODELS_EXT, "");
                addExtension = true;
            }
        }
        
        String result = "/" + systemName + "/" + MODELS_DIRNAME + "/"
                + systemName + "/" + domainName + "/" + domainName;
        if (addExtension) {
            result = result + "." + MODELS_EXT; //$$NON-NLS-1$$
        }
        return result;
    }
    
    private static HashMap<String, String> rootIdToSystemName = new HashMap<String, String>();
    public static String getProjectNameFromModelRootId(String id)
    {
        String projectName = rootIdToSystemName.get(id);
        if(projectName != null) {
            return projectName;
        }
        if(id.equals(CLIPBOARD_MODEL_ROOT_NAME) || id.equals(DEFAULT_WORKING_MODELSPACE) || Ooaofooa.getInstance(id).isCompareRoot() || id.equals(COMPARE_MODEL_ROOT_NAME)) { 
            return "";
        }
        String [] parts = id.split("/");
        if (parts.length < 2) {
          throw new IllegalArgumentException("Illegal root Id format: " + id);
        }
        rootIdToSystemName.put(id, parts[1]);
        return parts[1];
    }
    
    public static String getDomainFileNameFromModelRootId(String id)
    {
        String [] parts = id.split("/");
        if (parts.length < 6) {
          throw new IllegalArgumentException("Illegal root Id format: " + id);
        }
        return parts[5];
    }
    
    protected static ILogger getLog() {return log;} 
    
    public void delete() {
    	delete(false);
    }
    
    public void delete(boolean force) 
    {
    	// do not delete if we are not empty, this occurs
    	// when our owning element has been removed but an
    	// external source references an element from us through
    	// a proxy
    	if(isEmpty() || force) {
	        OoaofgraphicsUtil.deleteGraphicsRoot(getId());
	        rootInstanceMap.remove(rootId);
	        rootIdToSystemName.remove(rootId);
	        instanceListMap.clear();
	        if(isDefaultWorkModelRoot()) {
	            Ooaofooa.getDefaultInstance().removeModelChangeListener(modelChangeListener);
	            TransactionManager.getSingleton().removeTransactionListener(transactionListener);
	        }
    	}
    }
    
    public void batchUnrelateAll() {
    	for(Object key : instanceListMap.keySet()) {
    		InstanceList instanceList = instanceListMap.get(key);
    		for(NonRootModelElement object : instanceList) {
    			object.batchUnrelate();
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
    private boolean isEmpty() {
    	Set<Class> keySet = instanceListMap.keySet();
    	for(Class key : keySet) {
    		InstanceList list = instanceListMap.get(key);
    		if(list.size() > 0) {
    			return false;
    		}
    	}
    	return true;
	}

	public SystemModel_c getRoot()
    { 
		if(getId().equals(CLIPBOARD_MODEL_ROOT_NAME)) {
			return null;
		}
        if(m_root == null) {
            final String projectName = getProjectNameFromModelRootId(getId());
            m_root = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
                
                @Override
                public boolean evaluate(Object candidate) {
                    return ((SystemModel_c) candidate).getName().equals(projectName);
                }
            });
        }
        return m_root;
    }

    public void setRoot(SystemModel_c p_root)
    { 
        // the default root cannot have a root
        if ( !isDefaultWorkModelRoot() ) {
            m_root = p_root;
        }
    }

    /**
     * Stateless instances of ModelChangeListener that other plug-in's have requested be added to each instance of this
     * class.
     */ 
    private static List<IModelChangeListener> modelChangeListenersToAdd = new ArrayList<IModelChangeListener>();
    
    /**
     * Has this class add the given listener to all existing instances 
     * of this class.  Also, has this class hold on to the given listener 
     * to add it to each instance of this class created henceforth.
     */ 
    public static void addModelChangeListenerToAll(IModelChangeListener listener)
    {
        // only the default root has change listeners now
        Ooaofooa.getDefaultInstance().addModelChangeListener(listener);
    }

    /**
     * Removes the given listener from those this class is to add to 
     * each instance of this class created henceforth.
     */
    public static void removeModelChangeListenerFromAll(IModelChangeListener listener) 
    {
        // only the default root has change listeners now
        Ooaofooa.getDefaultInstance().removeModelChangeListener(listener);
    }
    
    /**
     * A list of ID's (of instances of this class, which should *not* 
     * already exist) to which this class should not add any global 
     * model-change listeners.
     */
    private static List<String> idsNotToAddListenersTo = new ArrayList<String>();

    /**
     * Adds the given ID (of an instance of this class, which should *not* 
     * already exist) to this class's list of those to which global 
     * model-change listeners should not be added. 
     */
    public static void doNotAddGlobalModelChangeListenersTo(String id)
    {
        idsNotToAddListenersTo.add(id);
    }


    /** 
     * Has this model-root re-determine its ID from its constituent
     * system-name and domain-name parts. Is meant to be called 
     * only on model-root instances that have previously had their ID's 
     * fields set directly.  
     */
    public void updateId() 
    {
        // if this model-root is the default root
        // do not update it's id.  This occurs
        // during paste operations when elements
        // are pasted to a system model.
        if(getId().equals(DEFAULT_WORKING_MODELSPACE)) return;
        
        // determine what the new ID should be
        Package_c pkg = Package_c.PackageInstance(thisAsOoaofooa());

        String newId = "";
        if (pkg != null) {
            SystemModel_c system = SystemModel_c.getOneS_SYSOnR1401(pkg);
            if(system != null) {
                newId = createModelRootId(system.getName(), pkg.getName(), true);
            }
        }

        if(!newId.equals("") && rootId != DEFAULT_WORKING_MODELSPACE) {
          if (!newId.equals(rootId)) {
            // assign the new ID
            String oldId = rootId;
            rootInstanceMap.remove(rootId);
            rootId = newId;
            rootInstanceMap.put(rootId, this);

            // set the ID of the corresponding graphics-root 
            OoaofgraphicsUtil.setId(oldId, rootId);
          }
        }
    }

    
    public void updateId(final String newName) 
    {
        // if this model-root is the default root
        // do not update it's id.  This occurs
        // during paste operations when elements
        // are pasted to a system model.
        if(getId().equals(DEFAULT_WORKING_MODELSPACE)) return;
        
        // determine what the new ID should be
        Package_c pkg = Package_c.PackageInstance(thisAsOoaofooa(), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals(newName);
			}
		});
        String newId = "";
        if (pkg != null) {
            SystemModel_c system = SystemModel_c.getOneS_SYSOnR1401(pkg);
            if(system != null) {
                newId = createModelRootId(system.getName(), pkg.getName(), true);
            }
        }

        if(!newId.equals("") && rootId != DEFAULT_WORKING_MODELSPACE) {
          if (!newId.equals(rootId)) {
            // assign the new ID
            String oldId = rootId;
            rootInstanceMap.remove(rootId);
            rootId = newId;
            rootInstanceMap.put(rootId, this);

            // set the ID of the corresponding graphics-root 
            OoaofgraphicsUtil.setId(oldId, rootId);
          }
        }
    }

    /** 
     * Listens for changes that occur to the model-elements 
     * (other than the system-model) associated with this model-root.
     */
    public class ModelChangeListener extends ModelChangeAdapter  
    {
    
        public void modelElementAttributeChanged(ModelChangedEvent event, IModelDelta delta) {
            AttributeChangeModelDelta attrDelta = (AttributeChangeModelDelta) delta; 
            
            if (!attrDelta.getAttributeName().equals("Name")) return ;
            
            NonRootModelElement modelElement = (NonRootModelElement) delta.getModelElement();
            Ooaofooa modelRoot = (Ooaofooa) modelElement.getModelRoot();
            String name = "";
            if(modelElement instanceof Package_c) {
                Package_c pkg = (Package_c) modelElement;
                name = pkg.getName();
                if(SystemModel_c.getOneS_SYSOnR1401(pkg) == null) {
                	return;
                }
                if(!attrDelta.getOldValue().equals("") && !attrDelta.getOldValue().equals(name)) {
                	modelRoot.updateId(name);
                }
            }
            if(!name.equals("")) {
                // if the change was actually different
                if (!attrDelta.getOldValue().equals("") && !attrDelta.getOldValue().equals(name)) {
                        // update this model-root's ID to 
                        // reflect the new name
                        modelRoot.updateId();
                }
            }
        }
        
        public boolean isBatchedNotificationEnabled() {
            return false;
        }        
        
        public void modelElementDeleted(ModelChangedEvent event,
                IModelDelta delta) {
            // nothing to handle here
        }
    }

    public class ModelRootTransactionListener implements ITransactionListener {

        @Override
        public void transactionCancelled(Transaction transaction) {
            // not concerned
        }

        @Override
        public void transactionEnded(Transaction transaction) {
            IModelDelta[] deltas = transaction.getDeltas(Ooaofooa.getDefaultInstance());
            if(deltas == null) return;
            for(int i = 0; i < deltas.length; i++) {
                IModelDelta delta = deltas[i];
                if(deltas[i].getKind() == Modeleventnotification_c.DELTA_DELETE) {
                    Object modelElement = delta.getModelElement();
                    // delete this model-root from further use;
                    // if we were to leave it around, its contents
                    // would not be reloaded from disk the next time
                    // it is retrieved from this class
                    if (modelElement instanceof Package_c) {
                        final NonRootModelElement element = (NonRootModelElement) modelElement;
                        // get the component passing false for locating the component
                        // otherwise the removal in progress will indicate there is
                        // no PMC
                        if (element.getFile() != null
                                && element.getFile().getFullPath().toString()
                                        .equals(element.getModelRoot().getId())) {
                        	// do this after all pending UI events to ensure
                        	// that any requiring the model root are processed
                        	// first
                        	PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
								
								@Override
								public void run() {
		                            ((Ooaofooa) element.getModelRoot()).delete();	
								}
							});
                        }
                    } else if (modelElement instanceof SystemModel_c) {
                        Ooaofooa[] roots = getInstancesUnderSystem(((SystemModel_c) modelElement)
                                .getName());
                        for (int j = 0; j < roots.length; j++) {
                            roots[j].delete();
                        }
                        delete();
                    }                               
                }
            }
        }

        @Override
        public void transactionStarted(Transaction transaction) {
            // not concerned
        }
        
    }
    
    /**
     * Returns this instance of this class as an instance of Ooaofooa,
     * which it is required to also be.
     */
    protected Ooaofooa thisAsOoaofooa() {return (Ooaofooa)this;}
}
