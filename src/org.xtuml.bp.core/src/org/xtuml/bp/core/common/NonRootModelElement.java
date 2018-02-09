//========================================================================
//
//File:      $RCSfile: NonRootModelElement.java,v $
//Version:   $Revision: 1.42 $
//Modified:  $Date: 2013/05/10 13:26:32 $
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

package org.xtuml.bp.core.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.ComponentResultSet_c;
import org.xtuml.bp.core.ComponentVisibility_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CoreDataType_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ElementVisibility_c;
import org.xtuml.bp.core.Elementtypeconstants_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.InstanceReferenceDataType_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.IntegrityManager_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.SearchResultSet_c;
import org.xtuml.bp.core.Severity_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.inspector.IModelClassInspector;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.ui.marker.UmlProblem;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import org.xtuml.bp.core.util.OoaofgraphicsUtil;
import org.xtuml.bp.core.util.PersistenceUtil;
import org.xtuml.bp.core.util.RTOUtil;
import org.xtuml.bp.core.util.SupertypeSubtypeUtil;
import org.xtuml.bp.core.util.UIUtil;


public abstract class NonRootModelElement extends ModelElement implements IAdaptable {
	private ModelRoot m_parent_root;
	private ModelRoot m_this_root;
	private PersistableModelComponent component;
    protected String m_contentPath;
    protected IFile file;
    private UUID m_id = Gd_c.Null_unique_id();

	protected NonRootModelElement(ModelRoot root) {
		m_parent_root = root;
		InstanceList instanceList = m_parent_root.getInstanceList(getClass());
        synchronized (instanceList) {
            instanceList.add(this);
		}
	}

	protected InstanceList getInstanceList() {
		return getModelRoot().getInstanceList(getClass());
	}

	public ModelRoot getParentRoot() {
		return m_parent_root;
	}

	/**
	 * Get the transaction manager of
	 * this elements root parent
	 */
	public TransactionManager getTransactionManager() {
		return TransactionManager.getSingleton();
	}

	private boolean isRoot() {
		if(this instanceof SystemModel_c) {
			return true;
		} else {
			return false;
		}
	}

	public NonRootModelElement getRoot() {
		NonRootModelElement root = null;
		if(getModelRoot() instanceof Ooaofooa) {
			root = ((Ooaofooa) getModelRoot()).getRoot();
		}
		if(root == null) {
			if(!getModelRoot().getId().equals(Ooaofooa.DEFAULT_WORKING_MODELSPACE) &&
					!getModelRoot().getId().equals(Ooaofooa.COMPARE_MODEL_ROOT_NAME)) {
				String name = Ooaofooa.getProjectNameFromModelRootId(getModelRoot().getId());
				root = getSystemModelFromName(name);
			}
		}
		return root;
	}

    public NonRootModelElement getSystemModelFromName(final String name) {
    	return SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((SystemModel_c) candidate).getName().equals(
								name);
					}
				});
	}

    public void Checkintegrity() {
    	// do nothing let subtypes override
    }
    
	public void checkReferentialIntegrity() {
		// gather all rtos and for any that are not
		// resolvable create an integrity issue
		List<NonRootModelElement> rtos = RTOUtil.getRTOs(this);
		for (NonRootModelElement rto : rtos) {
			if (rto.isProxy()) {
				// before going further try to load the proxy
				rto.loadProxy();
				if (rto.isProxy()) {
					// should have been loaded, we have a dangling
					// reference
					// first load the entire workspace and then search
					// globally for the element, if found batchRelate
					// the rto
					synchronized (getPersistableComponent()) {
						final NonRootModelElement element = (NonRootModelElement) Ooaofooa
								.getDefaultInstance()
								.getInstanceList(rto.getClass())
								.getGlobal(rto.getInstanceKey(), false);
						if (element != null && !element.isProxy()) {
							// honor IPR settings if this element is not in
							// the same system
							if (!getRoot().equals(element.getRoot())
									&& getRoot() instanceof SystemModel_c) {
								if (!Pref_c
										.Getsystemboolean(
												BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID,
												getRoot().getName())) {
									continue;
								}
							}
							NonRootModelElement[] results = null;
							// now we must honor visibility settings
							Package_c parentPackage = getParentPackage();
							if (parentPackage != null) {
								parentPackage.Clearscope();
								parentPackage.Collectvisibleelementsforname(
										((SystemModel_c) getRoot())
												.getUseglobals(), Gd_c
												.Null_unique_id(), false, element.getName(),
										parentPackage.getPackage_id(),
										element.getElementType());
								class SearchResultSet_test455_c implements
										ClassQueryInterface_c {
									public boolean evaluate(Object candidate) {
										SearchResultSet_c selected = (SearchResultSet_c) candidate;
										return ((selected.getName().equals(element.getName())) && (selected
												.getType() == element.getElementType()));
									}
								}
								SearchResultSet_c srs = SearchResultSet_c
										.getOnePE_SRSOnR8005(parentPackage,
												new SearchResultSet_test455_c());

								results = PackageableElement_c
										.getManyPE_PEsOnR8002(ElementVisibility_c
												.getManyPE_VISsOnR8006(srs));
							}
							Component_c parentComponent = getFirstParentComponent();
							if (parentComponent != null) {
								parentComponent.Clearscope();
								parentComponent.Collectvisibleelementsforname(
										((SystemModel_c) getRoot())
												.getUseglobals(), Gd_c
												.Null_unique_id(), element.getName(),
										parentComponent.getId(),
										element.getElementType());
								class ComponentResultSet_test456_c implements
										ClassQueryInterface_c {
									public boolean evaluate(Object candidate) {
										ComponentResultSet_c selected = (ComponentResultSet_c) candidate;
										return ((selected.getName().equals(element.getName())) && (selected
												.getType() == element.getElementType()));
									}
								}
								ComponentResultSet_c compResultSet = ComponentResultSet_c
										.getOnePE_CRSOnR8007(
												parentComponent,
												new ComponentResultSet_test456_c());

								results = PackageableElement_c
										.getManyPE_PEsOnR8004(ComponentVisibility_c
												.getManyPE_CVSsOnR8008(compResultSet));

							}
							// if the element does not exist in the result
							// set, then it is not visible
							boolean foundValidElement = false;
							for (NonRootModelElement result : results) {
								List<NonRootModelElement> subtypes = SupertypeSubtypeUtil.getSubtypes(result);
								// should only be one just check the first element
								if (element.equals(subtypes.get(0))) {
									foundValidElement = true;
									break;
								}
							}
							if (!foundValidElement) {
								continue;
							}
							// first unrelate, here we do not know the
							// association so we unrelate all
							batchUnrelate();
							// delete the proxy that was created
							rto.delete_unchecked();
							// now re-associate all elements
							batchRelate(getModelRoot(), false, true, true);
							// need to clear any synchronization flags
							UIUtil.refresh(null);
						} else {
							// still could not find the element, log the
							// integrity issue
							IntegrityManager_c
									.Createissue(
											getModelRoot(),
											"Found a dangling reference.  An element with the following attributes could not be found:\n\n"
													+ "Referenced Element ID: "
													+ rto.Get_ooa_id()
													+ "\n"
													+ "Referenced Element file: "
													+ rto.getContent(), this,
											Get_ooa_id(), getName(), getPath(),
											Severity_c.Error,
											((SystemModel_c) getRoot())
													.getSys_id());
						}
					}
				}
			}
		}
	}
    
	/**
	 * Return this element's associated PE_PE or null if this is not
	 * a PE_PE. This takes advantage of the instance list to avoid
	 * the need to know the specific type of element this is as would be required
	 * to get this information via PE_PE.getOnePE_PEOnR8001(<specific type>)
	 */
	public PackageableElement_c getPE() {
		InstanceList instances = getModelRoot().getInstanceList(PackageableElement_c.class);
		PackageableElement_c result = (PackageableElement_c)instances.get(this.Get_ooa_id());
		if ( result == null ) {
			instances = getParentRoot().getInstanceList(PackageableElement_c.class);
		    result = (PackageableElement_c)instances.get(this.Get_ooa_id());
		}
		return result;
	}
	
	private int getElementType() {
		PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8000(getFirstParentPackage());
		if(pe == null) {
			pe = PackageableElement_c.getOnePE_PEOnR8003(getFirstParentComponent());
		}
		if(pe != null) {
			return pe.getType();
		}
		return Elementtypeconstants_c.OOA_UNINITIALIZED_ENUM;
	}

	public String getPath() {
		ModelInspector inspector = new ModelInspector();
		String path = "";
		if ( !(this instanceof DataType_c) ) {
			// If this is a datatype, the name will be handled by the subtype
			// via the "parent" handling below.
			path = getName();
		}
		if (this instanceof ClassStateMachine_c) {
			path = "Class State Machine";
		} else if (this instanceof InstanceStateMachine_c) {
			path = "Instance State Machine";
		}
		IModelClassInspector elementInspector = inspector
				.getInspector(getClass());
		if (elementInspector != null) {
			NonRootModelElement parent = (NonRootModelElement) elementInspector
					.getParent(this);
			while (parent != null) {
				if (parent instanceof ClassStateMachine_c) {
					path = "Class State Machine" + "::" + path;
				} else if (parent instanceof InstanceStateMachine_c) {
					path = "Instance State Machine" + "::" + path;
				} else {
					if ( path.isEmpty() ) {
						path = parent.getName();	
					} else {
						path = parent.getName() + "::" + path;
					}
				}
				parent = (NonRootModelElement) inspector.getParent(parent);
			}
		}
		if (getModelRoot().isCompareRoot()) {
			return "";
		}
		return path;
	}
    
    /**
     * Set union for model elements
     */
    public static <T extends NonRootModelElement> T[] setUnion( T a, T b, T[] emptyArray ) {
        return setUnion( Collections.singleton( a ), Collections.singleton( b ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setUnion( T a, T[] b, T[] emptyArray ) {
        return setUnion( Collections.singleton(a), new HashSet<>( Arrays.asList( b ) ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setUnion( T[] a, T b, T[] emptyArray ) {
        return setUnion( new HashSet<>( Arrays.asList( a ) ), Collections.singleton( b ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setUnion( T[] a, T[] b, T[] emptyArray ) {
        return setUnion( new HashSet<>( Arrays.asList( a ) ), new HashSet<>( Arrays.asList( b ) ) ).toArray( emptyArray );
    }

    private static <T extends NonRootModelElement> Set<T> setUnion( Set<T> a, Set<T> b ) {
        Set<T> returnSet = new HashSet<>( a );
        returnSet.addAll( b );
        return returnSet;
    }

    /**
     * Set intersection for model elements
     */
    public static <T extends NonRootModelElement> T[] setIntersection( T a, T b, T[] emptyArray ) {
        return setIntersection( Collections.singleton( a ), Collections.singleton( b ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setIntersection( T a, T[] b, T[] emptyArray ) {
        return setIntersection( Collections.singleton(a), new HashSet<>( Arrays.asList( b ) ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setIntersection( T[] a, T b, T[] emptyArray ) {
        return setIntersection( new HashSet<>( Arrays.asList( a ) ), Collections.singleton( b ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setIntersection( T[] a, T[] b, T[] emptyArray ) {
        return setIntersection( new HashSet<>( Arrays.asList( a ) ), new HashSet<>( Arrays.asList( b ) ) ).toArray( emptyArray );
    }

    private static <T extends NonRootModelElement> Set<T> setIntersection( Set<T> a, Set<T> b ) {
        Set<T> returnSet = new HashSet<>( a );
        returnSet.retainAll( b );
        return returnSet;
    }

    /**
     * Set difference for model elements
     */
    public static <T extends NonRootModelElement> T[] setDifference( T a, T b, T[] emptyArray ) {
        return setDifference( Collections.singleton( a ), Collections.singleton( b ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setDifference( T a, T[] b, T[] emptyArray ) {
        return setDifference( Collections.singleton(a), new HashSet<>( Arrays.asList( b ) ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setDifference( T[] a, T b, T[] emptyArray ) {
        return setDifference( new HashSet<>( Arrays.asList( a ) ), Collections.singleton( b ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setDifference( T[] a, T[] b, T[] emptyArray ) {
        return setDifference( new HashSet<>( Arrays.asList( a ) ), new HashSet<>( Arrays.asList( b ) ) ).toArray( emptyArray );
    }

    private static <T extends NonRootModelElement> Set<T> setDifference( Set<T> a, Set<T> b ) {
        Set<T> returnSet = new HashSet<>( a );
        returnSet.removeAll( b );
        return returnSet;
    }

    /**
     * Set symmetric difference for model elements
     */
    public static <T extends NonRootModelElement> T[] setSymmetricDifference( T a, T b, T[] emptyArray ) {
        return setSymmetricDifference( Collections.singleton( a ), Collections.singleton( b ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setSymmetricDifference( T a, T[] b, T[] emptyArray ) {
        return setSymmetricDifference( Collections.singleton(a), new HashSet<>( Arrays.asList( b ) ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setSymmetricDifference( T[] a, T b, T[] emptyArray ) {
        return setSymmetricDifference( new HashSet<>( Arrays.asList( a ) ), Collections.singleton( b ) ).toArray( emptyArray );
    }

    public static <T extends NonRootModelElement> T[] setSymmetricDifference( T[] a, T[] b, T[] emptyArray ) {
        return setSymmetricDifference( new HashSet<>( Arrays.asList( a ) ), new HashSet<>( Arrays.asList( b ) ) ).toArray( emptyArray );
    }

    private static <T extends NonRootModelElement> Set<T> setSymmetricDifference( Set<T> a, Set<T> b ) {
        Set<T> returnSet = setUnion( a, b );
        returnSet.removeAll( setIntersection( a, b ) );
        return returnSet;
    }

    /**
     * Set compare equal for model elements
     */
    public static <T extends NonRootModelElement> boolean setEquals( T[] a, T[] b ) {
    	if ( ( null == a || null == b ) || a.length != b.length ) return false;
    	Set<T> setA = new HashSet<>( Arrays.asList( a ) );
    	Set<T> setB = new HashSet<>( Arrays.asList( b ) );
    	return ( setA.containsAll( setB ) && setB.containsAll( setA ) );
    }
    
	/**
     * Set the unique id for this instance.
     */
	public void setUniqueId(UUID id) {
	    m_id = id;
    }

    /**
     * Add a map entry for this class
     */
    public void addInstanceToMap(Object key) {
        getInstanceList().put(key, this);
    }

    /**
     * This method is overridden by subtype classes
     * which provide their unique instance key
     */
    public Object getInstanceKey() {
        return null;
    }
    public boolean setInstanceKey(UUID newKey) {
		return false;
    }

    /**
     * This method is used to update an objects
     * key within the instance map held by the
     * <code>InstanceList</code> class
     */
    public void updateInstanceKey(Object oldKey, Object newKey) {
    	if (oldKey != null
    			&& !new BPElementID((Object[]) oldKey).equals(new BPElementID(
    					(Object[]) newKey))) {
    		
    		getInstanceList().updateKey(oldKey, newKey, this);
    		if(getModelRoot() instanceof Ooaofooa) {
    			updateSubtypeKeys(this, oldKey, newKey);
    		}
    	}
    }
    public boolean hasSuperType(){
    	return false;
    }
    

    private static void updateSubtypeKeys(NonRootModelElement supertype, Object oldSupKey, Object newKey ) {
    	BPElementID oldSupertypeKey = new BPElementID((Object[]) oldSupKey);
		List<NonRootModelElement> subtypes = SupertypeSubtypeUtil
				.getSubtypes(supertype);
		for (NonRootModelElement subtype : subtypes) {
			// update the key for this subtype
			Object oldSubtypeKey = subtype.getInstanceKey();
			BPElementID oldKey = new BPElementID((Object[]) oldSubtypeKey);
			if(oldKey != null) {
				if ( ( oldKey.size() == oldSupertypeKey.size() ) ) {
						subtype.updateInstanceKey(oldSubtypeKey, newKey);
					if (oldKey.size() == 1 && oldSupertypeKey.size() == 1) {
						if (newKey instanceof Object[]) {
							Object[] p_newKey = (Object[]) newKey;
							if (p_newKey[0] instanceof UUID) {
								subtype.setInstanceKey((UUID)p_newKey[0] );
								
							}
						}
					}
				}
			}
		}
	}
	
	public boolean delete() {
        if (!isOrphaned()) {
            // if there are associated RGO instances
            // convert to a proxy instead
			IPersistenceHierarchyMetaData metaData = PersistenceManager
					.getHierarchyMetaData();
			boolean hasExternalRefs = metaData.hasExternalRGO(this);
			// During merge we do not convert elements, they are moved
			// as is from one side to the other.  During a merge undo we
			// can hit a case where RTOs are removed before the RGO, causing
			// the check for external references to be true.
			if(hasExternalRefs && !getModelRoot().isCompareRoot()) {
				convertToProxy();
			} else {
				delete_unchecked();
			}
            return true;
		}
        return false;
	}

	public void delete_unchecked() {
		InstanceList instanceList = getInstanceList();
        synchronized (instanceList) {
            instanceList.remove(this);
        }
	}

	/**
	 * Move this element from its current InstanceList to the 
	 * list in the provided ModelRoot
	 * 
	 * @param destination
	 */
	public void move_unchecked(ModelRoot destination) {
		InstanceList sourceInstanceList = getInstanceList();
        synchronized (sourceInstanceList) {
        	sourceInstanceList.remove(this);
        }		
        InstanceList destinationInstanceList = destination.getInstanceList(getClass());
        synchronized (destinationInstanceList) {
        	destinationInstanceList.add(this);
        }		
	}
	
	public ModelRoot getModelRoot() {
		if(m_this_root == null)
			return m_parent_root;
		return m_this_root;
	}

	public ModelRoot getThisRoot() {
		return m_this_root;
	}

	public void setModelRoot(ModelRoot root) {
		m_this_root = root;
	}

	public void setParentModelRoot(ModelRoot root) {
		m_parent_root = root;
	}

	public boolean isOrphaned(){
		// during compare do not consider an element orphaned
		if(getModelRoot().getId().equals(ModelRoot.COMPARE_MODEL_ROOT_NAME)) {
			return false;
		}
		InstanceList instanceList = getInstanceList();
		return !instanceList.contains(this);
	}

	/**
	 * 
	 * @param msg A string to append to the info displayed
	 */
	public void printInstanceListInfo(String msg){
		InstanceList instanceList = getInstanceList();
		instanceList.report(msg);
	}

    public IFile getFile() {
    	if(component!=null){
         return component.getFile();
        }
    	if(file==null){
    	    if(m_contentPath!=null)
    	    {
    	        return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(m_contentPath));
       		}
    	}
    	return file;

    }
	abstract public boolean isUUID(String attributeName);

	public UUID Get_ooa_id() {
		return m_id;
	}

    abstract public boolean equals (Object elem);

    /**
     * Returns the id-assigner used for elements of this model-element's type.
     * This should be overridden by any model-element type which employs
     * an id-assigner.
     */
    public IdAssigner getIdAssigner() {return null;}
    abstract public boolean identityEquals(Object elem);
    abstract public boolean cachedIdentityEquals(Object elem);
    abstract public void batchRelate(ModelRoot modelRoot, boolean notifyChanges, boolean searchAllRoots);
    abstract public void batchRelate(ModelRoot modelRoot, boolean relateProxies, boolean notifyChanges, boolean searchAllRoots);
    abstract public void batchUnrelate(boolean notifyChanges);
    
    public void batchUnrelate() {
    	batchUnrelate(false);
    }

    public void setComponent(PersistableModelComponent aComponent){
    	setComponent(aComponent, true);
    }
    
    public void setComponent(PersistableModelComponent aComponent, boolean resetRoot){
    	// do not set the component for clipboard root elements
    	if(getModelRoot().getId().equals(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME)) return;

        // only set the component once
        // if the child is loaded before the parent, then the
        // component has already been set
        if (aComponent == null) {
        	this.component = null;
            getPersistableComponent();
        }else{
                component = aComponent;
                if (component != null)
                    file = component.getFile();
        }
        if(component != null) {
        	if(getModelRoot().getId().equals(Ooaofooa.COMPARE_MODEL_ROOT_NAME) || getModelRoot().isCompareRoot())
        		return;

        	// get the id of the model root from the component unique id
	        String rootId = component.getUniqueID();
	        // if the model root of this element has already been set correctly
	        // ignore
	        if (m_this_root != null && rootId.equals(m_this_root.getId())) return;
	        
			if (resetRoot) {
				ModelRoot newRoot = null;
				if (getModelRoot() instanceof Ooaofooa) {
					newRoot = Ooaofooa.getInstance(rootId);
				} else {
					newRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
							rootId, OoaofgraphicsUtil.getGraphicsClass());
				}

				// if the old root does not equal the model root
				// created from the unique component id update the
				// root of this element
				ModelRoot currentRoot = m_this_root;
				if(currentRoot == null) {
					currentRoot = m_parent_root;
				}
				if (newRoot != null && !currentRoot.getId().equals(newRoot.getId())) {
					updateModelRoot(newRoot);
				}
	        }
	        // if this element is the component's root element
	        // store this root in the map using the component type
	        // as the key
	        if(component.getRootModelElement() == this) {

	            String key = component.getComponentType();

	            ArrayList<ModelRoot> list = null;
	            Map<String, ArrayList> modelRootMap = ModelRoot.getModelRootMap();
	            if(!modelRootMap.containsKey(key)) {
	            	list = new ArrayList<ModelRoot>();
	            	list.add(getModelRoot());
	            	modelRootMap.put(key, list);
	            } else {
	                ArrayList rootList = modelRootMap.get(key);
		            if(!rootList.contains(getModelRoot())) {
			            if(modelRootMap.containsKey(key)) {
			            	list = modelRootMap.get(key);
			            	list.add(getModelRoot());
			            	modelRootMap.put(key, list);

			            }
		            }
	            }
	        }
        }
   }

    public PersistableModelComponent getPersistableComponent(){
    	return getPersistableComponent(true);
    }

    public PersistableModelComponent getPersistableComponent(boolean findCommponent){
        if(findCommponent) {
	    	if(component == null || component.isOrphaned())
	        {
	            component=null; //reset orphaned component
	            NonRootModelElement root =this;
	            if (isProxy() && !getModelRoot().isCompareRoot()) {
	                component = PersistenceManager.findOrCreateComponent(new Path(m_contentPath));
	                }
	            else {
	                IPersistenceHierarchyMetaData hmd = PersistenceManager.getHierarchyMetaData();
	                if ( !hmd.isComponentRoot(this) )
	                {
	                    root = hmd.getComponentRootModelElement(this);
	                }
	                if (root != null && root.component == null) {
	                    NonRootModelElement parent = hmd
	                            .getParentComponentRootModelElement(root, false);
	                    if(parent!=null){
	                        PersistableModelComponent parentPMC = parent
	                                .getPersistableComponent();
	                        String name=hmd.getRootElementName(root);
	                        if(parentPMC!=null && name !=null && !name.equals("")){
	                            IPath path = PersistableModelComponent.getChildPath(parentPMC.getFullPath(), name);
	                            component=PersistenceManager.findComponent(path);
	                            if(component==null){
		                            try {
	    	                          component = new PersistableModelComponent(root,parentPMC.getFile());
	    	                          } catch (Exception e) {
	                                	component=null;
	                           		 }
	                            }
	                        }
	                    }
	                } else if (root != null) {
	                    setComponent(root.component);
	                }
	            }
	        }
	        if (component != null)
	            file = component.getFile();
        }
    	return component;
    }

    /**
     * Determine if a proxy needs to be written during export when traversing
     * between the given elements.  If they are not in the same component, and
     * they are not null, then a proxy is needed, otherwise a proxy is not
     * needed.
     *
     * @param i1
     * @param i2
     * @return True is a proxy should be written
     */
    public static boolean shouldWriteProxy(NonRootModelElement i1,
            NonRootModelElement i2) {
    	boolean shouldWriteProxy = false;
		if (i1 != null && i2 != null) {
	        PersistableModelComponent pmc1 = i1.getPersistableComponent();
	        PersistableModelComponent pmc2 = i2.getPersistableComponent();
	        // Only if neither pmc is null and the componets are different should
	        // we write the proxy
			if (pmc1 != null && pmc2 != null && pmc1 != pmc2) {
    			shouldWriteProxy = true;
			}
			// if one of the element's pmc is null but is a proxy allow writing the
			// proxy
			if(pmc1 == null && i1.isProxy() && pmc2 != null) {
				shouldWriteProxy = true;
			}
			if(pmc1 != null && pmc2 == null && i2.isProxy()) {
				shouldWriteProxy = true;
			}
			// if dealing with a compare root, write as long as i1 is a proxy
			if(i1.getModelRoot().isCompareRoot() && i1.isProxy()) {
				shouldWriteProxy = true;
			}
    	}
    	return shouldWriteProxy;
    }

    public boolean inSameComponent(NonRootModelElement i1,
            NonRootModelElement i2) {
        PersistableModelComponent pmc1 = i1.getPersistableComponent();
        PersistableModelComponent pmc2 = i2.getPersistableComponent();
        return pmc1 == pmc2;
    }

    public String getContent() {
        if (isProxy()) {
            return m_contentPath;
        } else {
            PersistableModelComponent pmc = getPersistableComponent();
            if (pmc != null) {
                // may be null during creation
                return pmc.getFullPath().toString();
            }
        }
        return null;
    }

    public String getContent(IPath p_localPath) {

        IPath path=null;

        if ( getPersistableComponent()!=null ) {
            path=getPersistableComponent().getFullPath();
        }else if(getContent()!=null){
            path=new Path(getContent());
        } else {
            Throwable e = new Throwable(
                    "Component and contentPath both are null.");
            e.fillInStackTrace();
            CorePlugin.logError("Component and contentPath both are null.", e);
            path=new Path(IPath.SEPARATOR+"");
        }

        return PersistenceUtil.createRelativePath(p_localPath, path);

    }

    public void updateContentPath(IPath fullPath) {
        if (isProxy()) {
            IPath oldPath = new Path(m_contentPath);
            IPath newPath = new Path("/");
            for (int i = 0; i < oldPath.segmentCount(); ++i) {
                // the fullpath of the model element being updated
                // may be shorter than this model element's path
                // don't copy the actual file name
                if (i < fullPath.segmentCount() - 1) {
                    newPath = newPath.append(fullPath.segment(i));
                } else {
                    newPath = newPath.append(oldPath.segment(i));
                }
            }
            m_contentPath = newPath.toString();
        }
    }

    public void setContentPath(String path) {
    	m_contentPath = path;
    }

    public void convertToProxy() {
        if (!isProxy()) {
            PersistableModelComponent pmc = getPersistableComponent();
            if (pmc != null) {
                m_contentPath = pmc.getFullPath().toString();
                UmlProblem.convertedToProxy(this);
            } else {
            	// do not throw exception if under testing
            	// some test elements are not configured fully
            	if(!Ooaofooa.inUnitTest()) {
	                Exception e = new IllegalStateException();
	                e.fillInStackTrace();
	                CorePlugin.logError("Conversion to proxy failed", e);
            	}
            }
            component = null;
        }
    }

    public boolean loadProxy() {
        boolean result = true;
		if (isProxy()
				&& (!getModelRoot().getId().equals(
						Ooaofooa.COMPARE_MODEL_ROOT_NAME) && !getModelRoot().isCompareRoot())) {
			result = PersistenceManager.loadAndFinishComponent(m_contentPath);
		}
		return result;
    }
    
    public void convertFromProxy() {
        if (isProxy()) {
            m_contentPath = null;
            UmlProblem.proxyResolved(this);
        }
    }

    public boolean isProxy() {
        return m_contentPath != null;
    }

    public void removeRef() {
        if ( isProxy() && !isReferenced() ) {
            getModelRoot().addProxy(this);
        }
    }

    public void addRef() {
        getModelRoot().removeProxy(this);
    }

    public boolean isReferenced() {
        return PersistenceManager.getHierarchyMetaData().hasExternalRGO(this, false);
    }

    public boolean ensureLoaded(boolean load) {
        if (load) {
            return loadProxy();
        } else {
            return !isProxy();
}
    }
    /*
     * get Compound Unique ID This method will be implemented in all persistable
     * classes and will be used in Markers to identify its marker
     */
    public String getCompUniqueID() {
        return null;
    }

	public void updateModelRoot(ModelRoot modelRoot) {
		if(m_this_root != null) {
			m_parent_root = m_this_root;
			m_this_root = null;
		}
		switchRoot(m_parent_root, modelRoot);
	}

	/**
	 * This method will be implemented by the subtype.  If not it will call
	 * the Get_name method that will also be implemented by the subtype
	 * returning an empty string if neither method was implemented.
	 */
	public String getName() {
		return Get_name();
	}
	public String Get_name() {
		return "";
	}
	/**
	 * This method will be implemented by the subtype.  If not it will do
	 * nothing.
	 */
	public void setName(String newValue) {};

	public void updateRootForSelfAndChildren(ModelRoot oldRoot,
			                                                ModelRoot newRoot) {
      IPersistenceHierarchyMetaData phm =
                                      PersistenceManager.getHierarchyMetaData();
      List children = phm.getChildren(this, false);
      for (Iterator i=children.iterator(); i.hasNext();) {
        NonRootModelElement child = (NonRootModelElement)i.next();
        child.updateRootForSelfAndChildren(oldRoot, newRoot);
      }
      if (!(getModelRoot() instanceof Ooaofooa)) {
    	oldRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(oldRoot.getId(), OoaofgraphicsUtil.getGraphicsClass());
       	newRoot = (ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(newRoot.getId(), OoaofgraphicsUtil.getGraphicsClass());
      }
      switchRoot(oldRoot, newRoot);
    }
	
	private void switchRoot(ModelRoot oldRoot, ModelRoot newRoot) {
		setModelRoot(newRoot);
		if(oldRoot == newRoot || oldRoot.isCompareRoot()) {
			// no need to update
			return;
		}
		InstanceList instanceList = oldRoot.getInstanceList(getClass());
		BPElementID key = null;
		for (Entry<BPElementID, NonRootModelElement> entry : instanceList.instanceMap
				.entrySet()) {
			if (entry.getValue().equals(this)) {
				key = entry.getKey();
				break;
			}
		}
		synchronized (instanceList) {
			instanceList.remove(this);
		}
		instanceList = newRoot.getInstanceList(getClass());
		synchronized (instanceList) {
			instanceList.add(this);
		}
		if (key != null) {
			instanceList.put(key, this);
		} else {
			instanceList.put(getInstanceKey(), this);
		}

		Ooaofooa.getDefaultInstance().fireModelRootChanged(this,
				oldRoot.getId());
	}

    //
    // This function is used to update the PMC for an element and it's children.
    // It is used recursively to traverse down and reset the component if the
    // element has the originalRootME as the PMC's root model element.
    public void updateComponentForSelfAndChildren(
            NonRootModelElement originalRootME, PersistableModelComponent newComp,
            boolean resetRoot) {
        IPersistenceHierarchyMetaData phm = PersistenceManager
                .getHierarchyMetaData();
        List children = phm.getChildren(this, false);
        for (Iterator i = children.iterator(); i.hasNext();) {
            NonRootModelElement child = (NonRootModelElement) i.next();
            child.updateComponentForSelfAndChildren(originalRootME, newComp, resetRoot);
        }
        PersistableModelComponent pmc = getPersistableComponent();
        if (originalRootME == pmc.getRootModelElement()) {
            setComponent(newComp);
        }
    }
    
	public boolean selfIsCoreDataType() {
		NonRootModelElement test = this;
		if(this instanceof DataType_c) {
			test = CoreDataType_c.getOneS_CDTOnR17((DataType_c) this);
			if(test == null) {
				test = UserDataType_c.getOneS_UDTOnR17((DataType_c) this);
			}
			if(test == null) {
				return false;
			}
		}
		if(test instanceof CoreDataType_c) {
			return true;
		}
		if(test instanceof UserDataType_c) {
			UserDataType_c udt = (UserDataType_c) test;
			if(udt.getGen_type() != 0) {
				return true;
			}
		}
		return false;
	}

	public Package_c getParentPackage() {
		PersistableModelComponent parent = getPersistableComponent();
		if (parent != null) {
			parent = parent.getParent();
		}
		while (parent != null) {
			if (parent.getRootModelElement() instanceof Package_c) {
				return (Package_c) parent.getRootModelElement();
			}			
			parent = parent.getParent();
		}
		return null;		
	}
	
	/** 
	 * This method is looking for the first parent of the element. It can be  
	 * used when the visible element collection infrastructure is required and
	 * the correct starting place is required.
	 * 
	 */
	public Package_c getFirstParentPackage() {
		PersistableModelComponent parent = getPersistableComponent();
		// the parent can be null in certain cases due
		// to a different thread unloading the element
		if(parent == null) {
			return null;
		}
		if(this instanceof Component_c) {
			// we are looking for the parent of the component
			parent = parent.getParent();
		}
		while (parent != null) {
			if (parent.getRootModelElement() instanceof Package_c) {
				return (Package_c) parent.getRootModelElement();
			}
			
			// Any caller of this method needs to handle the null case.
			// This will occur when the element is contained under a 
			// component if findFirst is true.
			if (parent.getRootModelElement() instanceof Component_c) {
				return null;
			}

			parent = parent.getParent();
		}
		return null;		
	}
	
	/** 
	 * See the description for {@code NonRootModelElement.getFirstParentPackage()}
	 * 
	 */
	public Component_c getFirstParentComponent() {
		PersistableModelComponent parent = getPersistableComponent();
		if(this instanceof Package_c) {
			// we are looking for the parent of the package
			parent = parent.getParent();
		}
		while (parent != null) {
			// Any caller of this method needs to handle the null case.
			// This will occur when the element is contained under a 
			// component if findFirst is true.
			if (parent.getRootModelElement() instanceof Package_c) {
				return null;
			}
			if (parent.getRootModelElement() instanceof Component_c) {
				return (Component_c) parent.getRootModelElement();
			}
			parent = parent.getParent();
		}
		return null;
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		// system model handled differently
		if(this instanceof SystemModel_c) {
			return null;
		}
		if (adapter == org.eclipse.core.resources.IFolder.class) {
			PersistableModelComponent comp = getPersistableComponent(false);
			if(comp == null || comp.getRootModelElement() != this) {
				return null;
			}
			if (comp != null) {
				return comp.getFile().getParent();
			}
		} else if (adapter == org.eclipse.core.resources.IFile.class) {
			PersistableModelComponent comp = getPersistableComponent(false);
			if(comp == null) {
				return null;
			}
				return comp.getFile();
			}
		return null;
	}

	public void Collectchanges(Object list) {
		// do nothing, let subtypes override if necessary
	}

	public void Synchronize() {
		// do nothing, let subtypes override if necessary
	}

	public boolean Issynchronized() {
		// return true, subtypes will override if necessary
		return true;
	}

	public void Collectreferencesforsynchronization(Object referenceList, int syncType) {
		// do nothing, subtypes will override if necessary
	}
	
	/**
	 * Helper function to get the elements RTO. This is only implemented for datatypes right now.
	 * 
	 * @return RTO if there is one, or self if not
	 */
	public NonRootModelElement getRTOElementForResolution() {
		if (this instanceof UserDataType_c) {
			DataType_c dt = DataType_c
					.getOneS_DTOnR17((UserDataType_c) this);
			return dt;
		} else if (this instanceof StructuredDataType_c) {
			DataType_c dt = DataType_c
					.getOneS_DTOnR17((StructuredDataType_c) this);
			return dt;
		} else if (this instanceof EnumerationDataType_c) {
			DataType_c dt = DataType_c
					.getOneS_DTOnR17((EnumerationDataType_c) this);
			return dt;
		} else if (this instanceof InstanceReferenceDataType_c) {
			DataType_c dt = DataType_c
					.getOneS_DTOnR17((InstanceReferenceDataType_c) this);
			return dt;
		} else {
			return this;
		}
	}

}
