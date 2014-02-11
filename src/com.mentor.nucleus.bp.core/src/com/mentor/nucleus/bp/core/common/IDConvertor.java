//=====================================================================
//
//File:      $RCSfile: IDConvertor.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2013/01/10 22:54:09 $
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

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;

public abstract class IDConvertor {

	protected List<NonRootModelElement> mesWithRelocatables = new Vector<NonRootModelElement>();
	
	public UUIDMap convertToUUID(Domain_c domain) {
		return convertToUUID(domain, new UUIDMap(null));
	}

	/*
	 * If inputMap == null then just create the ids even if it is dummy id.
	 */
	public UUIDMap convertToUUID(Domain_c domain, UUIDMap inputMap){
		UUIDMap newMap = null;
		if (inputMap != null) {
			newMap = new UUIDMap(domain);
		}
		
		ModelRoot.disableChangeNotification();
		try {
		  removeAndCacheRelocatables(domain);
		  newMap = _convertToUUID(domain, inputMap, newMap);
		  updateOoa_ids(domain);
		  updateRelocatables(domain);
		}
		finally {
		  ModelRoot.enableChangeNotification();
		}

		return newMap;
		
	}
	
	protected abstract UUIDMap _convertToUUID(Domain_c domain, UUIDMap inputMap, UUIDMap newMap);
	protected abstract void removeAndCacheRelocatables(Domain_c domain);
	protected abstract void updateRelocatables(Domain_c domain);
	
	protected void convertToUUID(InstanceList instanceList, UUIDMap newMap,
			UUIDMap inputMap) {
		for (Iterator i = instanceList.iterator(); i.hasNext();) {
			NonRootModelElement me = (NonRootModelElement) i.next();
			Object oldKey = me.getInstanceKey();
			UUID id = getId(me);
			if(id == null){
				//if id == null then it means that given model element does not 
				//have unique id
				break;
			}
			// if existing id is a dummy UUID or we just need to re-create.
			if (inputMap == null || IdAssigner.isUUIDDummy(id)) {
				// see if input map has an entry for given model element
				UUID knownId = null;
				if(inputMap != null){
					knownId = inputMap.getUUID(me);
				}
				
				if (knownId == null) {
					// input map does not contain the value so create new
					IdAssigner idAssigner = me.getIdAssigner();
					if (idAssigner != null) {
						knownId = idAssigner.createUUID();
						setId(me, knownId);
						me.updateInstanceKey(oldKey, me.getInstanceKey());
						if(newMap != null){
							createUUIDEntry(newMap, createKey(me), knownId);
						}
					}
				} else {
					setId(me, knownId);
                    me.updateInstanceKey(oldKey, me.getInstanceKey());
				}
			}
		}
	}

	public void recreateUUID(Domain_c domain){
		convertToUUID(domain, null);
	}

	protected abstract String createKey(NonRootModelElement modelElement);
	
	public abstract UUID getId(NonRootModelElement modelElement);
	public abstract void setId(NonRootModelElement modelElement, UUID id);

	protected abstract void updateOoa_ids(Domain_c domain);

	/*
	 * Following method is just to expose UUIDMap.createUUIDEntry method to 
	 * derived classes.
	 */
	protected static void createUUIDEntry(UUIDMap map, String key, UUID id){
		map.createUUIDEntry(key, id);
	}
	
	private static IDConvertor instance = null;
	public static IDConvertor getInstance(){
        if (instance == null) {
            try {
                Bundle io_mdl = Platform
                        .getBundle("com.mentor.nucleus.bp.io.mdl");//$NON-NLS-1$
                Class factoryClass = io_mdl
                        .loadClass("com.mentor.nucleus.bp.io.mdl.IDConversionUtil"); //$NON-NLS-1$
                instance = (IDConvertor) factoryClass
                        .newInstance();
            } catch (ClassNotFoundException cnf) {
                CorePlugin.logError(
                        "Problem loading persistence information ", cnf); //$NON-NLS-1$
            } catch (IllegalAccessException i) {
                CorePlugin.logError(
                        "Problem loading persistence information ", i); //$NON-NLS-1$
            } catch (InstantiationException i) {
                CorePlugin.logError(
                        "Problem loading persistence information ", i); //$NON-NLS-1$
            }
        }
        return instance;
    }
}
