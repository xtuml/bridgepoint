//=====================================================================
//
//File:      $RCSfile: ModelRoot.java,v $
//Version:   $Revision: 1.46 $
//Modified:  $Date: 2013/05/10 13:26:31 $
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

package com.mentor.nucleus.bp.core.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.util.ListenerList;

import com.mentor.nucleus.bp.core.ArrayValue_c;
import com.mentor.nucleus.bp.core.ComponentReferenceValue_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InstanceReferenceValue_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.RuntimeValue_c;
import com.mentor.nucleus.bp.core.SimpleCoreValue_c;
import com.mentor.nucleus.bp.core.SimpleValue_c;
import com.mentor.nucleus.bp.core.StructuredValue_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.ValueInArray_c;
import com.mentor.nucleus.bp.core.ValueInStructure_c;

/**
 * The root element of the tree of elements that belongs to a domain.  
 */
public abstract class ModelRoot extends ModelElement implements IModelChangeProvider
{
    public static final String DEFAULT_WORKING_MODELSPACE = "__default_root";//$NON-NLS-1$
    
    public static final String COMPARE_MODEL_ROOT_NAME =
        "__compare_model_root_name"; //$NON-NLS-1$

	public static String leftCompareRootId = "LEFT_COMPARE_ROOT"; //$NON-NLS-1$
	public static String rightCompareRootId = "RIGHT_COMPARE_ROOT"; //$NON-NLS-1$;
	public static String ancestorCompareRootId = "ANCESTOR_COMPARE_ROOT"; //$NON-NLS-1$;
	
    public static final String CLIPBOARD_MODEL_ROOT_NAME =
        "__clipboard_model_root_name"; //$NON-NLS-1$
    
    public static boolean isFileBasedID(String modelRootID) {
        return modelRootID.toLowerCase().startsWith("/"); //$$NON-NLS-1$$
    }

    protected String rootId;
    
    protected static Map<String, ArrayList> modelRootMap = new HashMap<String, ArrayList>();
    
    /**
     * Stores deltas for which there is a high likelihood that there will 
     * be further deltas during the same transaction that describe a similar 
     * change to the same source, and about which we only care about the 
     * most recent value.  In such cases, only one delta with the latest 
     * value need be collected within the transaction.  Storing these
     * deltas in a hashmap allows for quick testing of whether there's a 
     * previous delta that may be considered redundant.
     */
    private HashMap redundantDeltasMap = new HashMap<String, ModelRoot>();
    
    /**
     * Constructor
     */
    protected ModelRoot(String aRootId) 
    {
        super();

        rootId = aRootId;
        
    }

    public String getId() 
    {
        return rootId;
    }
    
    /**
     * This method should be overridden by subtypes that
     * can have their persist behavior changed.
     * @return true if persist is enabled for this model root
     */
    public boolean persistEnabled() {
        return true;
    }

    public boolean isDefaultWorkModelRoot()
    {
        return DEFAULT_WORKING_MODELSPACE.equals(rootId);
    }    
    
    private boolean m_fullModelLoad = false;
    public void setFullModelIsLoaded() 
    {
        m_fullModelLoad = true;
    }

    public boolean isFullModelLoaded() 
    {
        return m_fullModelLoad;
    }
    
    protected Map<Class, InstanceList> instanceListMap = new Hashtable<Class, InstanceList>();
    
    // List of meta-model classes which particpate exclusively at
    // Verifier runtime. We use this list to allocate an instance
    // extent class with a backward search policy to optimize
    // Verifier runtime delete performance. See dts0101019516.
    private static final Class<?>[] runtimes = {RuntimeValue_c.class,
    	StructuredValue_c.class, SimpleValue_c.class, ArrayValue_c.class,
    	InstanceReferenceValue_c.class, SimpleCoreValue_c.class,
    	ComponentReferenceValue_c.class, ValueInStructure_c.class,
    	ValueInArray_c.class};
    // Traverse the list to see if the passed class is in the runtime set
    private static boolean isRuntime (Class<?> clazz) {
    	for (Class<?> runtime: runtimes) {
    		if (runtime.equals(clazz)) {
    			return true;
    		}
    	}
    	return false;
    }

    public synchronized InstanceList getInstanceList(Class type)
    {
    	InstanceList list = instanceListMap.get(type);
        if(list == null){
        	if (isRuntime(type)) {
        	  // Allocate a Verifier runtime optimized instance extent
              list = new RuntimeInstanceList(this, type);
        	}
        	else {
        	  // Allocate a standard instance extent
              list = new StaticInstanceList(this, type);
        	}
            instanceListMap.put(list.getType(), list);
        }
        return list;
    }
    
    protected static int enabledEventsMask = Modeleventnotification_c.MASK_ALL_EVENTS;
    protected static int enabledDeltaMask = Modeleventnotification_c.MASK_ALL_DELTAS;
    
    private ListenerList modelChangedListeners = new ListenerList(3);
    private int instantListenerCount = 0;
    private int batchListenerCount= 0;

	private static HashMap<Thread, Integer> threadsDisablingNotification = new HashMap<Thread, Integer>(); 
    
    /**
     * This method will add a count to the number of threads which
     * are currently disabling the change notification.
     * 
     */
    public static void disableChangeNotification() {
		synchronized (threadsDisablingNotification) {
			Thread current = Thread.currentThread();
			if (threadsDisablingNotification.containsKey(current)) {
				int count = threadsDisablingNotification.get(current);
				threadsDisablingNotification.put(current, ++count);
			}
			else {
				threadsDisablingNotification.put(current, 1);
			}
        }
    }
    
    public static void enableChangeNotification() {
		synchronized (threadsDisablingNotification) {
			Thread current = Thread.currentThread();
			if (threadsDisablingNotification.containsKey(current)) {
				int count = threadsDisablingNotification.get(current);
				if (--count > 0) {
					threadsDisablingNotification.put(current, count);
				}
				else {
					threadsDisablingNotification.remove(current);
				}
			}
			else {
			  Throwable err = new Throwable();
			  err.fillInStackTrace();
			  CorePlugin.logError("Thread attempted to enable change notification without prior disable", err);	
			}
        }
    }
    
    public void addModelChangeListener(IModelChangeListener newListener) {
        if(!newListener.isBatchedNotificationEnabled()){
            instantListenerCount++;
        }else{
            batchListenerCount++;
        }    
        
        getModelChangedListeners().add(newListener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ${package.name}.IModelEntityChangeProvider#RemoveModelEntityChangeListener(${package.name}.IModelEntityChangeListener)
     */
    public void removeModelChangeListener(IModelChangeListener toRemove) {
        if(!toRemove.isBatchedNotificationEnabled()){
            instantListenerCount--;
        }else{
            batchListenerCount--;
        }

        getModelChangedListeners().remove(toRemove);
    }

    
    protected void callFireMethod(Runnable runnable){
        // always notify synchronous listeners
        Object[] listeners = getModelChangedListeners().getListeners();      
        for (int i = 0; i < listeners.length; ++i) {
          try {
            IModelChangeListener listener = (IModelChangeListener)listeners[i];
            if ((isChangeNotificationEnabled() || !listener.isMaskable()) &&
                                                       listener.isSynchronous()) {
              callFireMethodSynchronously(runnable, listener);
            }
          } catch (Throwable e) {
            logError("error while dispatching model change event", e);
          }
        }
    	if(isChangeNotificationEnabled()) {
    		if (runnable instanceof ListenerMethodInvoker) {
    			((ModelChangedEvent)((ListenerMethodInvoker)runnable).getEvent()).setStackTrace();
    		}
            runnable.run();
    }
    }
    
    protected void callFireMethodSynchronously(Runnable runnable,
                                                 IModelChangeListener listener){
      if (runnable instanceof ListenerMethodInvoker) {
        ((ListenerMethodInvoker)runnable).invoke(listener);
      }
    }

    /**
     * This class places the common code in the fire methods in one place. All fire methods shall implement 
     * the abstract method 'invoke' to call the specific model change event handler method. 
     */
    public abstract class ListenerMethodInvoker implements Runnable{
        private ModelChangedEvent event;
        
        public ListenerMethodInvoker(ModelChangedEvent aEvent) {
            event = aEvent;
            
            //Indicating that a fire*() method has been fired but not yet been run().
            pendingEventNotifierCount++;
        }        
        public abstract void invoke(IModelChangeListener listener);
        
        public void run(){
            Object[] listeners = getModelChangedListeners().getListeners();          
            try {
                for (int i = 0; i < listeners.length; ++i) {
                    try {
                        IModelChangeListener listener = (IModelChangeListener)listeners[i];
            // if change notification enabled or the listener isn't maskable
            // Synchronous listener would have already been invoked
                        if ((isChangeNotificationEnabled() && listener.isMaskable()) &&
                                                    !listener.isSynchronous()) {
						if (getEvent().getModelDeltas() != null){
                            if(activeTransaction == null || !listener.isBatchedNotificationEnabled()){
                               invoke(listener);
                            }
                        }else{
                            invoke(listener);
                        }
                    }
                    } catch (Throwable e) {
                        logError("error while dispatching model change event", e);
                    }                   
                }
            } finally {
                //Indicating that the run() method has completed.
                pendingEventNotifierCount--;
            }
        }
		public ModelChangedEvent getEvent() {
			return event;
		}
    }
    
    
    /**
     * Used for theUNKNOWN model change event used to refresh views.
     * @param event
     */
    public void fireModelEvent(ModelChangedEvent event){
        if ((enabledEventsMask & event.getType()) != 0 || event.getType() == Modeleventnotification_c.MODEL_EXECUTION_STOPPED){
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(event){
                public void invoke(IModelChangeListener listener) {
                    listener.modelEventReceived(getEvent());
                }               
            };          
            callFireMethod(listenerMethod);
        }
    }
    
    public void fireModelElementCreated(IModelDelta modelDelta){
        getDeltaCollector().waitIfLocked();
        if(doFirePrework(modelDelta)){                              
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, modelDelta)){
                public void invoke(IModelChangeListener listener) {
                    listener.modelElementCreated(getEvent(), getEvent().getModelDelta());
                }                   
            };
            callFireMethod(listenerMethod);
        }
    }
    
    public void fireModelElementRecreated(IModelDelta modelDelta){
        getDeltaCollector().waitIfLocked();
        if(doFirePrework(modelDelta)){                              
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, modelDelta)){
                public void invoke(IModelChangeListener listener) {
                    listener.modelElementRecreated(getEvent(), getEvent().getModelDelta());
                }                   
            };
            callFireMethod(listenerMethod);
        }
    }
    
    public void fireModelElementDeleted(IModelDelta modelDelta){
        getDeltaCollector().waitIfLocked();     
        if(doFirePrework(modelDelta)){
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, modelDelta)){
                public void invoke(IModelChangeListener listener) {
                    listener.modelElementDeleted(getEvent(), getEvent().getModelDelta());
                }                   
            };
            callFireMethod(listenerMethod);             
        }
    }
    
    public void fireSystemAboutToBeDisabled(final SystemModel_c system) {
        if (!getModelChangedListeners().isEmpty()){
            ModelChangedEvent mce = new ModelChangedEvent(this, Modeleventnotification_c.UNKNOWN, system);
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(mce){
                public void invoke(IModelChangeListener listener) {
                    listener.systemAboutToBeDisabled(system);
                }                   
            };
            // invoke this directly, as it most be synchronous
            listenerMethod.run();
        }        
    }
    
    public void fireModelElementAttributeChanged(IModelDelta modelDelta){
        getDeltaCollector().waitIfLocked();
        if(doFirePrework(modelDelta)){
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, modelDelta)){
                public void invoke(IModelChangeListener listener) {
                    listener.modelElementAttributeChanged(getEvent(), getEvent().getModelDelta());
                }                   
            };
            callFireMethod(listenerMethod);
        }
    }
    
    public void fireModelElementRelationChanged(IModelDelta modelDelta){
        getDeltaCollector().waitIfLocked();
        if(doFirePrework(modelDelta)){
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, modelDelta)){
                public void invoke(IModelChangeListener listener) {
                    listener.modelElementRelationChanged(getEvent(), getEvent().getModelDelta());
                }                   
            };
            callFireMethod(listenerMethod);
        }
    }
    
    final public boolean doFirePrework(IModelDelta modelDelta){        
        boolean toFireEvent = ((enabledEventsMask & Modeleventnotification_c.MODEL_ELEMENT_CHANGED) != 0 && (enabledDeltaMask & modelDelta.getKind()) != 0);
        synchronized(collectedDeltas){
            // if activeTransaction == null: do not collect delta
            if(activeTransaction != null){
              // if the delta has already been recorded, don't add it again,
              // just update the new value instead
              if (modelDelta instanceof AttributeChangeModelDelta) {
                AttributeChangeModelDelta duplicate =
                    (AttributeChangeModelDelta)redundantDeltasMap.get(modelDelta);
                if (duplicate != null) {
                  // delta exists, just update the new value of the delta
                  duplicate.setNewValue(((AttributeChangeModelDelta)modelDelta).getNewValue());
                }
                else {
                  // delta does not exist, add it to the transaction
                  collectedDeltas.add(modelDelta);
                  redundantDeltasMap.put(modelDelta, modelDelta);
                  ((BaseModelDelta)modelDelta).isIgnored = !toFireEvent;
                }
              }
              else {
                // not an attribute change, add it to the transaction
                collectedDeltas.add(modelDelta);
                ((BaseModelDelta)modelDelta).isIgnored = !toFireEvent;
              }
            }
        }
        
        // transaction == null; batchListenerCount=0; instantListenerCount=0; toFireEvent = no  
        // transaction == null; batchListenerCount=0; instantListenerCount=1; toFireEvent = yes
        // transaction == null; batchListenerCount=1; instantListenerCount=0; toFireEvent = yes
        // transaction == null; batchListenerCount=1; instantListenerCount=1; toFireEvent = yes

        // transaction != null; batchListenerCount=0; instantListenerCount=0; toFireEvent = no 
        // transaction != null; batchListenerCount=0; instantListenerCount=1; toFireEvent = yes
        // transaction != null; batchListenerCount=1; instantListenerCount=0; toFireEvent = no
        // transaction != null; batchListenerCount=1; instantListenerCount=1; toFireEvent = yes
        
        if(toFireEvent){
            if(!getModelChangedListeners().isEmpty()){
                if(activeTransaction != null && batchListenerCount > 0 && instantListenerCount <=0){
                    toFireEvent = false;
                }
            }
        }
        
        return toFireEvent;
    }
    
    protected boolean isChangeNotificationEnabled() {
		return threadsDisablingNotification.isEmpty();
    }

    public void fireModelElementLoaded(ModelElement modelElement){
        if ((enabledEventsMask & Modeleventnotification_c.MODEL_ELEMENT_LOADED) != 0 && !getModelChangedListeners().isEmpty()){
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, Modeleventnotification_c.MODEL_ELEMENT_LOADED, modelElement)){
                public void invoke(IModelChangeListener listener) {
                    listener.modelElementLoaded(getEvent());
                }                   
            };
            callFireMethod(listenerMethod);         
        }
    }
    public void fireModelElementReloaded(ModelElement oldModelElement, ModelElement newModelElement){
        if ((enabledEventsMask & Modeleventnotification_c.MODEL_ELEMENT_RELOAD) != 0 && !getModelChangedListeners().isEmpty()){
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, Modeleventnotification_c.MODEL_ELEMENT_RELOAD, oldModelElement, newModelElement)){
                public void invoke(IModelChangeListener listener) {
                    listener.modelElementReloaded(getEvent());
                }                   
            };
            callFireMethod(listenerMethod);
        }
    }
    
    public void fireModelElementUnloaded(ModelElement modelElement){
        if ((enabledEventsMask & Modeleventnotification_c.MODEL_ELEMENT_UNLOADED) != 0 && !getModelChangedListeners().isEmpty()){
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, Modeleventnotification_c.MODEL_ELEMENT_UNLOADED, modelElement)){
                public void invoke(IModelChangeListener listener) {
                    listener.modelElementUnloaded(getEvent());
                }                   
            };
            callFireMethod(listenerMethod);
        }
    }
    
    public void fireModelElementAboutToBeDeleted(ModelElement modelElement){
        if ((enabledEventsMask & Modeleventnotification_c.MODEL_ELEMENT_PRE_DELETE) != 0 && !getModelChangedListeners().isEmpty()){
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, Modeleventnotification_c.MODEL_ELEMENT_PRE_DELETE, modelElement)){
                public void invoke(IModelChangeListener listener) {
                    listener.modelElementAboutToBeDeleted(getEvent());
                }                   
            };
            callFireMethod(listenerMethod);
        }
    }
    
    public void fireModelElementAboutToBeReloaded(ModelElement modelElement){
        if ((enabledEventsMask & Modeleventnotification_c.MODEL_ELEMENT_PRE_RELOAD) != 0 && !getModelChangedListeners().isEmpty()){
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, Modeleventnotification_c.MODEL_ELEMENT_PRE_RELOAD, modelElement)){
                public void invoke(IModelChangeListener listener) {
                    listener.modelElementAboutToBeReloaded(getEvent());
                }                   
            };
            callFireMethod(listenerMethod);
        }
    }

    public void fireModelRootChanged(ModelElement modelElement, String oldRootId) {
        if ((enabledEventsMask & Modeleventnotification_c.MODEL_ELEMENT_PRE_RELOAD) != 0 && !getModelChangedListeners().isEmpty()){
            ModelChangedEvent mce = new ModelChangedEvent(this, Modeleventnotification_c.MODEL_ELEMENT_PRE_RELOAD, modelElement);
            mce.oldModelRoot = oldRootId;
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(mce){
                public void invoke(IModelChangeListener listener) {
                    listener.modelRootChanged(getEvent());
                }                   
            };
            // invoke this directly, as it most be synchronous
            listenerMethod.run();
        }
    }
    
    protected void fireBatchedModelChanges(IModelDelta[] batchedDeltas){
        if ((enabledEventsMask & Modeleventnotification_c.MODEL_ELEMENT_CHANGED) != 0 && !getModelChangedListeners().isEmpty()){
            ListenerMethodInvoker listenerMethod = new ListenerMethodInvoker(new ModelChangedEvent(this, batchedDeltas)){
                public void invoke(IModelChangeListener listener) {
                    //This method must not be callled for the instant listeners, as this would send the events twice
                    if (listener.isBatchedNotificationEnabled()){
                        listener.modelChanged(this.getEvent());
                    }
                }                   
            };
            callFireMethod(listenerMethod);
        }
    }

    IDeltaCollector deltaCollector = new DeltaCollector();
    List collectedDeltas = new ArrayList(100);

    Transaction activeTransaction = null;
    Transaction disabledTransaction = null;

    /*
     * WARNING. Use disableDeltaCollection with extreme caution.
     * This mechanism is intended for use by the data upgrade
     * infrastructure, so that upgrades are not persisted. Any
     * other use should be thoroughly researched and tested.
     */
    public void disableDeltaCollection() {
        if (activeTransaction != null) {
          String errorMsg = "";
          if (disabledTransaction != null) {
            errorMsg = "Cached transaction is already set.";
          }
          if (errorMsg != "") {
            Throwable thr = new Throwable();
            thr.fillInStackTrace();
            throw new IllegalStateException(errorMsg, thr);
          }
          else {
            disabledTransaction = activeTransaction;
            activeTransaction = null;
          }
        }
    }
    
    /*
     * WARNING. Use enableDeltaCollection with extreme caution.
     * This mechanism is intended for use by the data upgrade
     * infrastructure, so that upgrades are not persisted. Any
     * other use should be thoroughly researched and tested.
     */
    public void enableDeltaCollection() {
        if (disabledTransaction != null) {
          String errorMsg = "";
          if (activeTransaction != null) {
            errorMsg = "Delta collection not disabled.";
          }
          if (errorMsg != "") {
            Throwable thr = new Throwable();
            thr.fillInStackTrace();
            throw new IllegalStateException(errorMsg, thr);
          }
          else {
            activeTransaction = disabledTransaction;
            disabledTransaction = null;
          }
        }
    }
    //All run methods increment this count before running, and decrment once the run() method is run.
    protected int pendingEventNotifierCount = 0;
    
    
    public IDeltaCollector getDeltaCollector(){
        return deltaCollector;
    }
    
    protected void logError(String msg, Throwable e){
        CorePlugin.logError(msg, e);
    }
    
    /**
     * The methods in this class are synchronized, so that the fire*() methods can
     * wait on the object of this class, if any of this method is running. 
     */
    class DeltaCollector implements IDeltaCollector{
        
        SynchronousLock lock = new SynchronousLock();
        
        public DeltaCollector(){
        }
                    
        public void startCollecting(Transaction transaction) throws TransactionException{
            try {
                lock.acquireLock();
                
            } catch (Exception e) {
                throw new TransactionException(e);
            }
            if(activeTransaction != null){
                throw new IllegalStateException("collector already engaged");
            }           
            activeTransaction = transaction;
            lock.releaseLock();
        }
        
        public void endCollecting(){
            try {
                lock.acquireLock();
            } catch (Exception e1) {                
                logError("Could not acquire lock for event firing", e1);  //$NON-NLS-1$             
            }
            IModelDelta[] deltas = null;
            synchronized(collectedDeltas){
                if (collectedDeltas.size() > 0){
                    deltas = (IModelDelta[]) collectedDeltas.toArray(new IModelDelta[0]);               
                    activeTransaction.setDeltas(ModelRoot.this, deltas);
                    collectedDeltas.clear();                    
                    redundantDeltasMap.clear();
                }
                activeTransaction = null;
                collectedDeltas.notifyAll();
            }
            lock.releaseLock();
        }

        public void waitIfLocked() {
            lock.waitOnLock();          
        }
        }

    static class SynchronousLock{
        private boolean locked = false;     
        
        synchronized public void acquireLock()throws Exception{
            if(locked) throw new Exception("Object already locked");
            locked = true;          
        }
        
        synchronized public void releaseLock(){
            if(!locked) throw new IllegalStateException("Object already un-locked");

            locked = false;
            this.notifyAll();
        }
        
        synchronized public void waitOnLock(){
            while(locked){
                try {
                    this.wait(1000);
                } catch (InterruptedException e) {                  
                }
            }
        }

    }

    /**
     * Returns the file (if any) in which the contents of this model-root 
     * are persisted, as an Object (in order to be platform-agnostic).
     */
    abstract public Object getPersistenceFile();
    
    /**
     * Returns whether the contents of this model-root are currently in 
     * the process of being persisted. 
     */
    abstract public boolean isPersisting();
    
    /**
     * Should be overridden to supply the platform-specific code
     * necessary to dispatch any events that are currently queued
     * up in the underlying platform. 
     */
    protected void dispatchPlatformEvents() {
        // Default is to do nothing
    }
    
    protected HashSet<NonRootModelElement> unreferencedProxies= new HashSet<NonRootModelElement>();
    
    public void addProxy(NonRootModelElement element)
    {
        synchronized (unreferencedProxies)
        {
            unreferencedProxies.add(element);
}
    }
    public void removeProxy(Object element)
    {
        synchronized (unreferencedProxies)
        {
            unreferencedProxies.remove(element);
        }
    } 
    public void clearUnreferencedProxies()
    {
        synchronized (unreferencedProxies)
        {
            Object[] proxies = unreferencedProxies.toArray();
            for (int i = 0; i < proxies.length; i++)
            {
                NonRootModelElement element = (NonRootModelElement) proxies[i];
                if(!element.isOrphaned())
                {
                    element.batchUnrelate();
                    element.delete_unchecked();
                }
            }
        unreferencedProxies.clear();
        }
        //this is special case, when domain is unloaded system_c proxy will be
        //in the default model root
        ModelRoot defaultRoot=Ooaofooa.getDefaultInstance();
        if(this!=defaultRoot){
            defaultRoot.clearUnreferencedProxies();
        }
    }

    public Object[] getModelRoots(String type, String excludedType) {
        if(getId().equals(DEFAULT_WORKING_MODELSPACE)) return new Object[] {};
        Ooaofooa thisAsOoaofooa = (Ooaofooa) this;
        // use the project name to limit the model-roots returned
        String projectName = Ooaofooa.getProjectNameFromModelRootId(thisAsOoaofooa.getId());
        ArrayList list = modelRootMap.get(type);
        ArrayList<ModelRoot> returnList = new ArrayList<ModelRoot>();   
        if (list!= null) {
            Object[] elements = list.toArray();
            for(int i = 0; i < elements.length; i++) {
            if(elements[i] instanceof Ooaofooa) {
                Ooaofooa modelRoot = (Ooaofooa) elements[i];
                if(projectName.equals(Ooaofooa.getProjectNameFromModelRootId(modelRoot.getId()))) {
                    PersistableModelComponent comp = PersistenceManager.findComponent(modelRoot.getId());
                    // the component may be null during testing, there are
                    // rename tests which trigger a call to this method
                    // during unloading of a component
                    if(comp != null) {
                        boolean excluded = false;
                        String[] excludedTypes = excludedType.split(",");
                        for(int j = 0; j < excludedTypes.length; j++) {
                            if(excludedTypes[j].equals(comp.getComponentType())) {
                                excluded = true;
                            }
                        }
                        if(!excluded)
                            returnList.add(modelRoot);
                    }
                }
            }
        }
      }
        return returnList.toArray();
    }
    
    public static Map<String, ArrayList> getModelRootMap() {
        return modelRootMap;
    }

    /**
     * This method creates the finds the model-root with
     * an id that is based on the combining the two parameters
     * 
     * 
     * @param contentPath - The path to the proxy element
     * @param elementLocalPath - The path of the element which references the proxy
     * @return ModelRoot
     */
    public static ModelRoot findModelRoot(ModelRoot original, String contentPath, IPath elementLocalPath) {
        // if the element local path is empty
        // this is the compare model-root
		if (elementLocalPath.isEmpty()
				|| original.getId().equals(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME)
				|| original.isCompareRoot()) {
            return original;
        }
        ModelRoot instance = null;
        String projectName = elementLocalPath.segment(0);
        IPath path = elementLocalPath.removeLastSegments(1).removeFirstSegments(1);
        String device = elementLocalPath.getDevice();
        String elementPath = path.addTrailingSeparator().toString();
        if (device != null) {
            elementPath = elementPath.replaceAll(device, "");
        } 
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        IResource resource = project.findMember(elementPath + contentPath);
        if(resource != null && resource.getFullPath().segmentCount() == 4)
            return Ooaofooa.getDefaultInstance();
        if(resource != null && resource.getType() == IResource.FILE && resource.exists()) {
            instance = Ooaofooa.getInstance((IFile)resource, false);
        }
        if(resource == null) {
        	resource = project.getFile(elementPath + contentPath);
        	instance = Ooaofooa.getInstance((IFile) resource, false);
        }
        return instance;
    }

    public Map<Class, InstanceList> getILMap() {
        return instanceListMap;
    }

  protected ListenerList getModelChangedListeners() {
    return modelChangedListeners;
  }

	public boolean isCompareRoot() {
		return getId().startsWith(getLeftCompareRootPrefix())
				|| getId().startsWith(getRightCompareRootPrefix())
				|| getId().startsWith(getAncestorCompareRootPrefix())
				|| getId().equals(COMPARE_MODEL_ROOT_NAME);
	}
	
	public boolean isNewCompareRoot() {
		return getId().startsWith(getLeftCompareRootPrefix())
				|| getId().startsWith(getRightCompareRootPrefix())
				|| getId().startsWith(getAncestorCompareRootPrefix());		
	}

	public static String getLeftCompareRootPrefix() {
		return leftCompareRootId;
	}

	public static String getRightCompareRootPrefix() {
		return rightCompareRootId;
	}
	
	public static String getAncestorCompareRootPrefix() {
		return ancestorCompareRootId;
	}
}