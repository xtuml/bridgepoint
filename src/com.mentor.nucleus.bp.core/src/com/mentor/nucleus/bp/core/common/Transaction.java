//=====================================================================
//
//File:      $RCSfile: Transaction.java,v $
//Version:   $Revision: 1.25 $
//Modified:  $Date: 2013/01/17 03:39:27 $
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.EclipseOoaofooa;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;

public class Transaction {
	public static final String DEFAULT_TYPE = "__default";

	public static final String UNDO_TYPE = "__undo";

	public static final String REDO_TYPE = "__redo";

    public static final String DELETE_TRANS = "Delete";
    
    public static final String AUTORECONCILE_TYPE = "Auto-Reconcile";

	private boolean undoable = true;

	long id;

	String type;

	String displayName;

	Map<ModelRoot, Object> deltasMap = new HashMap<ModelRoot, Object>();
	
	TransactionManager manager = null;

	IDeltaCollector deltaCollector;

	public boolean memoryOnly = false;

	public boolean forceCompletion = false;

	protected Transaction(String aDisplayName, IDeltaCollector aDeltaCollector,
			String aType, boolean isUndoable, TransactionManager p_Manager){
		displayName = aDisplayName;
		id = System.currentTimeMillis();
		deltaCollector = aDeltaCollector;
		type = aType;
		undoable = isUndoable;
		manager = p_Manager;
	}

	public String getType() {
		return type;
	}

	public TransactionManager getTransactionManager() {
		return manager;
	}
	
	public long getID() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public ModelRoot getParticipatingModelRoot(Class<?> type) {
		for (Iterator<ModelRoot> iterator = deltasMap.keySet().iterator(); iterator
				.hasNext();) {
			Object modelRoot = iterator.next();
			if (type == modelRoot.getClass()) {
				return (ModelRoot) modelRoot;
			}
		}
		return null;
	}

	public ModelRoot[] getParticipatingModelRoots() {
		ModelRoot[] roots = (ModelRoot[]) deltasMap.keySet().toArray(new ModelRoot[0]);
		ArrayList<ModelRoot> list = new ArrayList<ModelRoot>();
		for(int i = 0; i < roots.length; i++) {
			if(roots[i] instanceof Ooaofooa) {
				list.add(roots[i]);
			}
		}
		for(int i = 0; i < roots.length; i++) {
			if(!(roots[i] instanceof Ooaofooa)) {
				list.add(roots[i]);
			}
		}
		return list.toArray(new ModelRoot[0]);
	}

	public ModelRoot[] getParticipatingModelRoots(Class<?> type) {
		List<ModelRoot> modelRoots = new ArrayList<ModelRoot>();
		for (Iterator<ModelRoot> iterator = deltasMap.keySet().iterator(); iterator
				.hasNext();) {
			ModelRoot modelRoot = iterator.next();
			if (type == modelRoot.getClass()) {
				modelRoots.add(modelRoot);
			}
		}

		if (modelRoots.size() > 0) {
			return (ModelRoot[]) modelRoots.toArray(new ModelRoot[modelRoots
					.size()]);
		} else {
			return new ModelRoot[0];
		}
	}

	public IModelDelta[] getDeltas(ModelRoot modelRoot) {
		return (IModelDelta[]) deltasMap.get(modelRoot);
	}

	protected void setDeltas(ModelRoot modelRoot, IModelDelta[] deltas) {
		deltasMap.put(modelRoot, deltas);
	}

	protected IDeltaCollector getDeltaCollector() {
		return deltaCollector;
	}

	public boolean isUndoable() {
		return undoable;
	}

	/**
	 * Get the number of deltas involved with the given transaction
	 */
	public int getDeltaCount() {
		ModelRoot[] modelRoots = getParticipatingModelRoots();
		int deltaCount = 0;
		// determine how many deltas are part of
		// the given transaction
		for (int i = 0; i < modelRoots.length; i++) {
			deltaCount = deltaCount + getDeltas(modelRoots[i]).length;
		}
		return deltaCount;
	}

	void revert() {
		revert(false);
	}
	/**
	 * Wrapper method to create a progress dialog if necessary, that is if the
	 * amount of deltas is over a certain size
	 *
	 * @param transactionType
	 */
	void revert(final boolean inMemoryOnly) {
		int deltaCount = getDeltaCount();
		if (deltaCount > 200) {
			ProgressMonitorDialog pmDialog = new ProgressMonitorDialog(
					PlatformUI.getWorkbench().getDisplay().getActiveShell());
			try {
				pmDialog.run(false, false, new IRunnableWithProgress() {

					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						revert(monitor, inMemoryOnly);
					}

				});
			} catch (InvocationTargetException e) {
				CorePlugin.logError(
						"Unable to invoke undo of the last transaction.", e);
			} catch (InterruptedException e) {
				CorePlugin.logError(
						"The undo of the last transaction was interrupted.", e);
			}
		} else {
			revert(new NullProgressMonitor(), inMemoryOnly);
		}
	}

	/**
	 * This method takes a transaction and reverses the changes that were made
	 * by it.
	 *
	 * @param pm
	 */

	void revert(IProgressMonitor pm, boolean inMemoryOnly) {

		ModelRoot[] modelRoots = getParticipatingModelRoots();

		if(inMemoryOnly) {
            ModelRoot.disableChangeNotification();
		}
        try {
		int deltaCount = getDeltaCount();

		pm.beginTask("Restoring model...", deltaCount);

		for (int i = 0; i < modelRoots.length; i++) {

			IModelDelta[] deltas = getDeltas(modelRoots[i]);
			// process each delta set
			for (int j = deltas.length - 1; j >= 0; j--) {
				ModelElement modelElement = deltas[j].getModelElement();
				NonRootModelElement nrme = (NonRootModelElement) modelElement;

				// restore any deletions contained in the
				// delta set
				if (deltas[j].getKind() == Modeleventnotification_c.DELTA_DELETE) {
					if(nrme.getModelRoot() instanceof Ooaofooa) {
						// if the element is a container to a model root, we must
						// add that model root and the graphical one back into the
						// model root map
						Ooaofooa foundInstance = EclipseOoaofooa
									.findInstance(nrme.getModelRoot().getId());
						if(foundInstance == null) {
							EclipseOoaofooa.addInstance(nrme.getModelRoot());
							Object graphicsRoot = locateGraphicsRoot(nrme);
							if(graphicsRoot != null)
								OoaofgraphicsUtil.addInstance(graphicsRoot);
						}
					} else {
						if (nrme.getModelRoot().getClass() == OoaofgraphicsUtil
								.getGraphicsClass()) {
							Object foundInstance = OoaofgraphicsUtil
								.findInstance(nrme.getModelRoot()
									.getId());
							if(foundInstance == null) {
								OoaofgraphicsUtil.addInstance(nrme.getModelRoot());
							}
						}
					}
					// if the element deleted was converted to a proxy
					if(nrme.isProxy()) {
						// convert it to a non proxy
						nrme.convertFromProxy();
					}
					InstanceList instanceList = nrme.getModelRoot().getInstanceList(nrme.getClass());
					synchronized (instanceList) {
						instanceList.add(nrme);
					}
                    nrme.addInstanceToMap(nrme.getInstanceKey());
					if (nrme instanceof ActiveObject_c)
						Activepoller_c.register((ActiveObject_c) nrme);
					modelRoots[i].fireModelElementCreated(new BaseModelDelta(
							Modeleventnotification_c.DELTA_NEW, nrme));
				}

				// restore all previous attribute values
				if (deltas[j] instanceof AttributeChangeModelDelta) {
					AttributeChangeModelDelta delta = (AttributeChangeModelDelta) deltas[j];
					String setMethod = "set" + delta.getAttributeName(); // $NON-NLS-1$
					Method method = null;
					try {
                        Object oldValue = delta.getOldValue();
                        Class<?> attributeClass = oldValue != null ?
                            getPrimitiveClass(delta.getOldValue().getClass())
                            : new Object().getClass();
                        method = nrme.getClass().getMethod(
                            setMethod, new Class[] {attributeClass});
						Object[] arg = {delta.getOldValue()};
						method.invoke(modelElement, arg);
					} catch (Exception e) {
						CorePlugin.logError(
								"Unable to find or invoke set method.", e);
					}
				}

				// restore all relationship changes that were made
				if (deltas[j] instanceof RelationshipChangeModelDelta) {
					RelationshipChangeModelDelta delta = (RelationshipChangeModelDelta) deltas[j];
					NonRootModelElement dest = (NonRootModelElement) delta
							.getDestinationModelElement();

					String direction = "To"; // $NON-NLS-1$
					String relationship = "relate"; // $NON-NLS-1$

					if (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_RELATED) {
						direction = "From"; // $NON-NLS-1$
						relationship = "unrelate"; // $NON-NLS-1$
					}

					// build the method name from the delta information
					String relateMethod = relationship + "AcrossR" // $NON-NLS-1$
							+ delta.getRelationName() + direction
							+ delta.getRelationDirectionPhrase();

                    // find the method of that name and invoke it
					try {
                        Method method = nrme.getClass().getMethod(relateMethod,
								new Class[] { dest.getClass() });
						Object[] arg = new Object[1];
						arg[0] = dest;
						method.invoke(nrme, arg);
					} catch (Exception e) {
						CorePlugin.logError(
								"Unable to find or invoke relate method.", e);
					}
				}

				// remove any elements that were created during the
				// transaction
				if (deltas[j].getKind() == Modeleventnotification_c.DELTA_NEW) {
					if (nrme.delete()) {
						// we do not need listeners enabled here, the original
						// transaction will contain all deltas to fully restore
						// the model (there is a specific case in merge that shows
						// a graphic being disposed when we do not want)  This
						// is shown when merging an unformalize and then undoing
						// and re-merging it
						try {
							ModelRoot.disableChangeNotification();
							modelRoots[i]
									.fireModelElementDeleted(new BaseModelDelta(
											Modeleventnotification_c.DELTA_DELETE, nrme));
						} finally {
							ModelRoot.enableChangeNotification();
						}
					}
					// remove the PMC if it exists and we are reverting
					// in memory, otherwise it will be removed by the
					// ComponentTransactionListener
					if(inMemoryOnly) {
						if(nrme.getPersistableComponent() != null) {
							if(nrme.getPersistableComponent().getRootModelElement() == nrme) {
								nrme.getPersistableComponent().deleteSelf();
							}
						}
					}

                    // remove the element from the current selection,
                    // as would be done if the deletion was done by a delete-action
                    Selection.getInstance().removeFromSelection(nrme);
				}

				pm.worked(1);
			}
		}
        }
        finally {
			if(inMemoryOnly) {
				ModelRoot.enableChangeNotification();
			}
        }
		pm.done();
	}

    private Object locateGraphicsRoot(NonRootModelElement nrme) {
		IModelDelta[] deltas = getDeltas((ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
				Ooaofooa.getDefaultInstance().getId(), OoaofgraphicsUtil
						.getGraphicsClass()));
		if(deltas == null) {
			return null;
		}
		for(int i = 0; i < deltas.length; i++) {
			// locate a matching GraphicalElement_c
			NonRootModelElement modelElement = (NonRootModelElement) deltas[i]
					.getModelElement();
			if (modelElement.getModelRoot().getId().equals(
					nrme.getModelRoot().getId())) {
				return modelElement.getModelRoot();
			}
		}
    	return null;
	}

	/**
     * Returns the class of the primitive type which corresponds to the
     * given primitive-wrapping type (e.g. Integer, Boolean, etc.).
     * For non-primitive-wrapping types, the String class is returned
     * when it is the one given, and the Object class is returned for all
     * others.
     */
	public static Class<?> getPrimitiveClass(Class<?> clazz)
    {
        if (clazz == Float.class) return Float.TYPE;
        if (clazz == Integer.class) return Integer.TYPE;
        if (clazz == Long.class) return Long.TYPE;
        if (clazz == Boolean.class) return Boolean.TYPE;
        if (clazz == String.class) return clazz;
        if (clazz == UUID.class) return clazz;

        return Object.class;
    }

	public void updateParticipatingModelRoot(ModelRoot newRoot, ModelRoot oldRoot) {
		Object object = deltasMap.get(oldRoot);
		deltasMap.put(newRoot, object);
		deltasMap.remove(oldRoot);
	}
	
	public void setModelDeltas(IModelDelta[] deltas, ModelRoot root) {
		deltasMap.clear();
		deltasMap.put(root, deltas);
	}
}
