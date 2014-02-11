
package com.mentor.nucleus.bp.core.common;
//========================================================================
//
//File:      $RCSfile: InstanceList.java,v $
//Version:   $Revision: 1.34 $
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;

public abstract class InstanceList extends ArrayList<NonRootModelElement> {
	ModelRoot root;
	Class type;
    HashMap<BPElementID, NonRootModelElement> instanceMap = new HashMap<BPElementID, NonRootModelElement>();

	public InstanceList(ModelRoot aRoot, Class aType) {
		super(0);
		root = aRoot;
		type = aType;
	}

  protected InstanceList(ArrayList list, HashMap map, ModelRoot aRoot, Class aType) {
    super(list);
    instanceMap = map;
    root = aRoot;
    type = aType;
  }

	public ModelRoot getRoot() {
		return root;
	}

	public Class getType() {
		return type;
	}
    
    public synchronized Object get(Object key) {
      if (key instanceof String) {
    	  if(key.equals(IdAssigner.NULL_UUID.toString())) {
    		  return null;
    	  }
    	  return instanceMap.get(new BPElementID((String)key));
      }
      if (key instanceof Object[]) {
        return instanceMap.get(new BPElementID((Object[])key));
      }
      if (key instanceof UUID) {
        return instanceMap.get(new BPElementID(new Object[]{key}));
      }
      return null;
    }

    public HashMap<BPElementID, NonRootModelElement> getInstanceMap() {
    	return instanceMap;
    }
    
    /**

     * This function searches the current model root for the given key,
     * and if it is not found in the current model root, is searches all 
     * mode roots.
     * 
     * @param key The key we are searching for
     * @return
     */
    public Object getGlobal(Object key) {
    	return getGlobal(null, key);
    }
    
	public Object getGlobal(SystemModel_c src_system, Object key) {
		if (key instanceof String) {
			if (key.equals(IdAssigner.NULL_UUID.toString())) {
				return null;
			}
		}
		if (key instanceof UUID) {
			if (key.equals(IdAssigner.NULL_UUID)) {
				return null;
			}
		}
		Object result = get(key);
		if (result == null && !isLongBased(key)) {
			// If we didn't find it in this model root, then
			// search all model roots.
			Class<?> typeOfClass = this.getType();
			// search default model root first in case it is a global
			Ooaofooa def = Ooaofooa.getDefaultInstance();
			result = def.getInstanceList(typeOfClass).get(key);
			ModelRoot firstRootSearched = this.getRoot();
			SystemModel_c firstSystemSearched = null;
			if (src_system != null) {
				firstSystemSearched = src_system;
			} else {
				if (firstRootSearched instanceof Ooaofooa) {
					firstSystemSearched = ((Ooaofooa) firstRootSearched)
							.getRoot();
				}
			}
			if (result == null) {
				Ooaofooa[] roots = null;
				if (typeOfClass == SystemModel_c.class) {
					roots = new Ooaofooa[] { Ooaofooa
							.getInstance(ModelRoot.DEFAULT_WORKING_MODELSPACE) };
				} else {
					if (src_system != null) {
						roots = Ooaofooa.getInstancesUnderSystem(src_system.getName());
					} else {
						roots = Ooaofooa
								.getInstancesUnderSystem(firstRootSearched);
					}
				}
				for (int rootCount = 0; result == null
						&& rootCount < roots.length; rootCount++) {
					ModelRoot currentRoot = roots[rootCount];
					if(currentRoot.isCompareRoot()) {
						// do not search compare roots
						continue;
					}
					if (currentRoot != firstRootSearched) { // Already searched
						                                    // this root
						result = currentRoot.getInstanceList(typeOfClass).get(
								key);
						if (result != null) {
							break;
						}
					}
				}
			}
			// if the element is still not found, search in other projects for
			// it
			if (result == null) {
				// get all systems in the default model root
				SystemModel_c[] systems = SystemModel_c
						.SystemModelInstances(Ooaofooa
								.getInstance(ModelRoot.DEFAULT_WORKING_MODELSPACE));
				// for each system (except the system previously searched)
				for (int i = 0; i < systems.length; i++) {
					if (systems[i] != firstSystemSearched) {
						// get all roots under the system
						Ooaofooa[] roots = Ooaofooa
								.getInstancesUnderSystem(systems[i].getName());
						// for each root
						for (int rootCount = 0; result == null
								&& rootCount < roots.length; rootCount++) {
							if(roots[rootCount].isCompareRoot()) {
								// do not search compare roots
								continue;
							}
							// search for element under the root using get(key)
							result = roots[rootCount].getInstanceList(
									this.getType()).get(key);
							if (result != null) {
								break;
							}
						} // end for each root
						// if element is found, stop iterating
						if (result != null) {
							break;
						}
					}
				} // end for each system
			} // end if element still not found
		}
		return result;
	}
    
    public synchronized void put(Object key, Object object) {
      if (!isNull(key)) {
        instanceMap.put(convertToUUID(key), (NonRootModelElement)object);
      }
    }
    
    public synchronized void updateKey(Object oldKey, Object newKey,
                                         Object object, boolean checkExisting) {
    	if(newKey == null) return;
    	boolean updateKey = true;
    	if(checkExisting && !isNull(oldKey)) {
    	  Object element = get(oldKey);
    		if(element == null) {
    			updateKey = false;
    		}
    	}
    	if(updateKey) {
        removeOldKey(oldKey, object);
    		put(newKey, object);
    	}    	
    }
    
    public void updateKey(Object oldKey, Object newKey, Object object) {
    	updateKey(oldKey, newKey, object, true);
    }
    
    protected synchronized void removeOldKey(Object key, Object object) {
      BPElementID uKey = new BPElementID((Object [])key);
      if (instanceMap.get(uKey) == object) {
        // It can happen that the wrong object is found, when
        // a model containing old long based ids is used, and the
        // containing instance that guaranteed uniqueness is already
        // destroyed. This can happen when a Persistable Model Component
        // is clearing its database. In this case, it is safe to ignore
        // the remove key, since the passed instance could not now be
        // located using the instance map. The passed instance itself
        // has already been removed from the instance extent anyway.
        Object removed = instanceMap.remove(uKey);
        /*  Painful performance hit for containsValue. Consider doing this when
         *  debug flag is set.
        if (instanceMap.containsValue(object)) {
          Throwable t = new Throwable();
          t.fillInStackTrace();
          CorePlugin.logError("InstanceList integrity error:" +
            "object still present in instance map after removal" + object, t);
        }*/
        if (removed == null ) {
          /* This is also painful
          System.out.println("No object removed for key: " + key);
          if (instanceMap.containsValue(object)) {
            Set<BPUUID> keySet = instanceMap.keySet();
            for (BPUUID uKey : keySet) {
              if (instanceMap.get(uKey) == object) {
                System.out.println("Actual key: " + uKey);
                break;
              }
            }
          }
          */
        }
      }
	}

	public synchronized boolean add(NonRootModelElement object) {
		if (!type.isAssignableFrom(object.getClass()))
			throw new IllegalArgumentException("Can only maintain list of type " + type.getName()); //$NON-NLS-1$
		return super.add(object);
	}

	/**
	 * A debug routine that dumps some info about the list.
	 */
	public synchronized void report(String msgToAdd) {
		int nameIndex = type.getName().lastIndexOf(".") > 0 ? type.getName().lastIndexOf(".") : 0;
		System.out.println(msgToAdd + "\ttype: " + type.getName().substring(nameIndex) +   "\tcount: " + String.valueOf(size())  + "\troot: " + root.rootId);  
	}
	
	public synchronized void addElement(NonRootModelElement object) {
		add(object);
	}

	public synchronized boolean remove(Object object) {
	  if (object instanceof NonRootModelElement) {
	    Object key = ((NonRootModelElement)object).getInstanceKey();
	    if (key != null) {
	      return remove(key, object);
	    }
	  }
	  return super.remove(object);
	}
	
	public abstract boolean remove(Object key, Object object);

	public synchronized void removeAllElements() {
		super.clear();
		instanceMap.clear();
	}
	
	public final synchronized int indexOf(Object elem, int index) {
		int result = super.subList(index, super.size()).indexOf(elem);
		return result != -1 ? result + index : -1;
	}

	public final synchronized int lastIndexOf(Object elem, int index) {
    int result = super.subList(0, index).lastIndexOf(elem);
    return result;
	}
	
  public BPElementID convertToUUID(Object object) {
    BPElementID uKey = null;
    if (object instanceof String) uKey = new BPElementID((String) object); 
    if (object instanceof Object[]) uKey = new BPElementID((Object[]) object); 
    if (object instanceof UUID) uKey = new BPElementID(new Object[] {object});
    if (object instanceof BPElementID) uKey = (BPElementID) object;
    return uKey;
  }
  
  private static boolean isNull(Object key) {
    if(key == null ||
      (key instanceof String && key.equals(IdAssigner.NULL_UUID.toString())) ||
      (key instanceof UUID && key.equals(IdAssigner.NULL_UUID))) {
      return true;
    }
    if (key instanceof Object[]) {
      Object[] temp = (Object[]) key;
      for (int i = 0; i < temp.length; i++) {
        if (!(temp[i] instanceof UUID)) {
          return false;
        }
        else {
          if (!((UUID)temp[i]).equals(IdAssigner.NULL_UUID)) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }

  private static boolean isLongBased(Object key) {
	    if (key instanceof String) {
          return IdAssigner.isUUIDDummy(UUID.fromString((String)key));
	    }
        else if (key instanceof UUID) {
          return IdAssigner.isUUIDDummy((UUID)key);
	    }
	    if (key instanceof Object[]) {
	      Object[] temp = (Object[]) key;
	      if (temp.length != 1) {
	    	return false;
	      }
	      return isLongBased(temp[0]);
	    }
	    return false;
	  }
	
	// The following methods are all overridden to
	// implement the synchronized semantic.
	
  public final synchronized int indexOf(Object elem) {
    return super.indexOf(elem);
  }

  public final synchronized int lastIndexOf(Object elem) {
    return super.lastIndexOf(elem);
  }
  
	public synchronized void trimToSize() {
	  super.trimToSize();
	}
	
	public synchronized void ensureCapacity(int newCapacity) {
	  super.ensureCapacity(newCapacity);
	}
	
	public synchronized int size() {
	  return super.size();
	}
	
	public synchronized boolean isEmpty() {
	  return super.isEmpty();
	}
	
	public abstract boolean contains(NonRootModelElement element);
	
	public synchronized int indexOf(NonRootModelElement element) {
	  return super.indexOf(element);
	}
	
	public synchronized int lastIndexOf(NonRootModelElement element) {
	  return super.lastIndexOf(element);
	}
	
	public synchronized Object clone() {
		  return clone((ArrayList)super.clone(), instanceMap, root, type );
	}
	// Allow specialized InstanceList to clone itself
    abstract Object clone(ArrayList clone,
			HashMap<BPElementID, NonRootModelElement> instanceMap,
			ModelRoot root, Class type);

  //public Object[] toArray() don't want this overridden
  //public Object[] toArray(NonRootModelElement []) don't want this overridden

	public synchronized NonRootModelElement get(int index) {
	  return super.get(index);
	}
	
	public synchronized NonRootModelElement set(int index, NonRootModelElement element) {
	  return super.set(index, element);
	}
	
	//public boolean add(NonRootModelElement element) already overridden
	
	public synchronized void add(int index, NonRootModelElement element) {
	  super.add(index, element);
	}
	
	public synchronized NonRootModelElement remove(int index) {
		if (index == -1) {
			return null;
		}
		else {
	      return super.remove(index);
		}
	}
	// public boolean remove (NonRootModelElement element) already overridden
	
	public synchronized void clear() {
	  super.clear();
	}
	
	public synchronized boolean addAll(java.util.Collection col) {
	  return super.addAll(col);
	}
	
	public synchronized boolean addAll(int index, java.util.Collection col) {
	  return super.addAll(index, col);
	}
	
	public synchronized void removeRange(int from, int to) {
	  super.removeRange(from, to);
	}
}

