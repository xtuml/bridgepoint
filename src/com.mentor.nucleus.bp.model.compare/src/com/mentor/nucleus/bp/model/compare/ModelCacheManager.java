//=====================================================================
//
//File:      $RCSfile: ModelCacheManager.java,v $
//Version:   $Revision: 1.4.26.3 $
//Modified:  $Date: 2013/07/29 21:05:52 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.model.compare;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.CantHappen_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.EventIgnored_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.LocalEvent_c;
import com.mentor.nucleus.bp.core.MealyActionHome_c;
import com.mentor.nucleus.bp.core.MealyStateMachine_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.MooreStateMachine_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.NoEventTransition_c;
import com.mentor.nucleus.bp.core.NonLocalEvent_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.PolymorphicEvent_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.SignalEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.Util_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.ModelStreamProcessor;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.inspector.IModelInspectorRegistry;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.sorter.MetadataSortingManager;
import com.mentor.nucleus.bp.core.ui.IModelImport;
import com.mentor.nucleus.bp.io.core.CoreImport;
import com.mentor.nucleus.bp.io.core.ImportHelper;
import com.mentor.nucleus.bp.io.mdl.ImportModelComponent;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.inspector.GraphicalModelInspector;

public class ModelCacheManager {

	public static final String BODY_ID = "BodyForInternalUse????"; //$NON-NLS-1$

	private static final int LEFT = 0;

	private static final int ANCESTOR = 2;

	private static final int RIGHT = 1;

	public static boolean disableProgressMonitor = false;

	Hashtable<Object, Object> models = new Hashtable<Object, Object>();

	ModelInspector modelInspector;
	GraphicalModelInspector graphicalModelInspector;

	private HashMap<Object, NonRootModelElement[]> rootElementMap = new HashMap<Object, NonRootModelElement[]>();
	private List<Object> readonlyInputs = new ArrayList<Object>();

	public ModelCacheManager() {
		MetadataSortingManager sortingManager = MetadataSortingManager
				.createDefault();
		modelInspector = new ModelInspector(sortingManager);
		graphicalModelInspector = new GraphicalModelInspector(sortingManager);
	}

	public IStreamContentAccessor getModel(Object inputObject,
			Object requester, Ooaofooa compareRoot, Object key)
			throws ModelLoadException {

		// if input is null, could be an ancestor that is not available
		if (inputObject == null) {
			return null;
		}

		ModelMapEntry entry = (ModelMapEntry) models.get(key);
		if (entry == null) {
			entry = createModelMapEntry(inputObject, compareRoot, key);
			if (entry != null) {
				models.put(key, entry);
				if (!entry.isValid()) {
					throw new ModelLoadException(entry.errorMessage,
							entry.throwable);
				}
				if(requester != null) {
					entry.references.add(requester);
				}

				return entry.rootNode;
			}

		} else {
			return entry.rootNode;
		}
		return null;
	}

	public Model_c getLoadedGraphicalModelsForElements(Object element) {
		for (Object key : models.keySet()) {
			ModelMapEntry entry = (ModelMapEntry) models.get(key);
			Model_c[] loadedModels = entry.loadedModels;
			if (loadedModels == null) {
				continue;
			}
			for (Model_c model : loadedModels) {
				if (model.getRepresents() == element) {
					return model;
				}
			}
		}
		return null;
	}

	public UUID getOriginalSMIdFromEntry(Object key) {
		ModelMapEntry entry = (ModelMapEntry) models.get(key);
		return entry.originalSMId;
	}
	
	public NonRootModelElement[] getRootElements(Object inputObject,
			Object requester, boolean reload, Ooaofooa compareRoot, Object key)
			throws ModelLoadException {
		NonRootModelElement[] rootElements = rootElementMap.get(key);
		if(rootElements != null && reload) {
			ModelMapEntry entry = (ModelMapEntry) models.get(key);
			clearEntry(entry, key);
		}
		rootElements = rootElementMap.get(key);
		if (rootElements == null && requester != null) {
			getModel(inputObject, requester, compareRoot, key);
			rootElements = rootElementMap.get(key);
			ModelMapEntry entry = (ModelMapEntry) models.get(key);
			updateSMId(rootElements, entry);
		} else {
			// we need to re-update the SM_IDs as they
			// may have been set back on save
			ModelMapEntry entry = (ModelMapEntry) models.get(key);
			if(entry != null) {
				// update sm ids for the case where
				// we have updated them on a save
				updateSMId(rootElements, entry);
			}
			if(requester != null) {
				if(entry != null && !entry.references.contains(requester)) {
					entry.references.add(requester);
				}
			}
		}
		return rootElements;
	}

	private void updateSMId(NonRootModelElement[] rootElements,
			ModelMapEntry entry) {
		for(NonRootModelElement rootElement : rootElements) {
			StateMachine_c sm = null;
			UUID objId = null;
			String type = "";
			if(rootElement instanceof InstanceStateMachine_c) {
				InstanceStateMachine_c ism = (InstanceStateMachine_c) rootElement;
				sm = StateMachine_c.getOneSM_SMOnR517(ism);
				objId = ism.getObj_idCachedValue();
				type = "i";
			}
			if(rootElement instanceof ClassStateMachine_c) {
				ClassStateMachine_c csm = (ClassStateMachine_c) rootElement;
				sm = StateMachine_c.getOneSM_SMOnR517(csm);
				objId = csm.getObj_idCachedValue();
				type = "c";
			}
			UUID originalId = Gd_c.Null_unique_id();
			if(entry != null) {
				originalId = entry.getOriginalSMId();
			}
			if(sm != null) {
				UUID newId = Util_c.Getuniquestatemachineid(objId, type);
				if(!newId.equals(originalId) && entry != null) {
					updateIdForStateMachine(newId, sm);
				}
			}
		}
	}

	public static void updateIdForStateMachine(UUID id, final StateMachine_c sm) {
		// update the graphical ooa id
		Model_c gm = Model_c.ModelInstance(Ooaofgraphics
				.getInstance(sm.getModelRoot().getId()),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Model_c) candidate)
								.getOoa_id().equals(sm.getSm_id());
					}
				});
		if(gm != null) {
			gm.setOoa_id(id);
		}
		Object oldKey = sm.getInstanceKey();
		sm.setSm_id(id);
		updateSubtypes(sm);
		sm.updateInstanceKey(oldKey, sm.getInstanceKey());
	}

	private static void updateSubtypes(StateMachine_c sm) {
		InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR517(sm);
		if(ism != null) {
			Object oldKey = ism.getInstanceKey();
			ism.unrelateAcrossR517From(sm);
			ism.relateAcrossR517To(sm);
			ism.updateInstanceKey(oldKey, ism.getInstanceKey());
		}
		ClassStateMachine_c csm = ClassStateMachine_c.getOneSM_ASMOnR517(sm);
		if(csm != null) {
			Object oldKey = csm.getInstanceKey();
			csm.unrelateAcrossR517From(sm);
			csm.relateAcrossR517To(sm);
			csm.updateInstanceKey(oldKey, csm.getInstanceKey());
		}
		StateMachineEvent_c[] events = StateMachineEvent_c.getManySM_EVTsOnR502(sm);
		for(StateMachineEvent_c event : events) {
			Object oldEKey = event.getInstanceKey();
			event.unrelateAcrossR502From(sm);
			event.relateAcrossR502To(sm);
			event.updateInstanceKey(oldEKey, event.getInstanceKey());
			SemEvent_c sem = SemEvent_c.getOneSM_SEVTOnR525(event);
			if(sem != null) {
				Object oldSEMKey = sem.getInstanceKey();
				sem.unrelateAcrossR525From(event);
				sem.relateAcrossR525To(event);
				sem.updateInstanceKey(oldSEMKey, sem.getInstanceKey());
				LocalEvent_c local = LocalEvent_c.getOneSM_LEVTOnR526(sem);
				if(local != null) {
					Object oldLocKey = local.getInstanceKey();
					local.unrelateAcrossR526From(sem);
					local.relateAcrossR526To(sem);
					local.updateInstanceKey(oldLocKey, local.getInstanceKey());
				}
				SignalEvent_c sigEvt = SignalEvent_c.getOneSM_SGEVTOnR526(sem);
				if(sigEvt != null) {
					Object oldSigKey = sigEvt.getInstanceKey();
					sigEvt.unrelateAcrossR526From(sem);
					sigEvt.relateAcrossR526To(sem);
					sigEvt.updateInstanceKey(oldSigKey, sigEvt.getInstanceKey());
				}
				NonLocalEvent_c nle = NonLocalEvent_c.getOneSM_NLEVTOnR526(sem);
				if(nle != null) {
					Object oldNlKey = nle.getInstanceKey();
					nle.unrelateAcrossR526From(sem);
					nle.relateAcrossR526To(sem);
					PolymorphicEvent_c poly = PolymorphicEvent_c.getOneSM_PEVTOnR527(nle);
					if(poly != null) {
						poly.unrelateAcrossR527From(nle);
						poly.relateAcrossR527To(nle);
					}
					nle.updateInstanceKey(oldNlKey, nle.getInstanceKey());
				}
			}
			PolymorphicEvent_c poly = PolymorphicEvent_c.getOneSM_PEVTOnR525(event);
			if(poly != null) {
				NonLocalEvent_c[] nlevts = NonLocalEvent_c.getManySM_NLEVTsOnR527(poly);
				for(NonLocalEvent_c nlevt : nlevts) {
					// no instance key update required
					// just unrelate and relate to update
					// the cached value
					nlevt.unrelateAcrossR527From(poly);
					nlevt.relateAcrossR527To(poly);
				}
				Object oldPKey = poly.getInstanceKey();
				poly.unrelateAcrossR525From(event);
				poly.relateAcrossR525To(event);
				poly.updateInstanceKey(oldPKey, poly.getInstanceKey());
			}
		}
		StateMachineState_c[] states = StateMachineState_c.getManySM_STATEsOnR501(sm);
		for(StateMachineState_c state : states) {
			Object oldSKey = state.getInstanceKey();
			state.unrelateAcrossR501From(sm);
			state.relateAcrossR501To(sm);
			state.updateInstanceKey(oldSKey, state.getInstanceKey());
			StateEventMatrixEntry_c[] semes = StateEventMatrixEntry_c.getManySM_SEMEsOnR503(state);
			for(StateEventMatrixEntry_c seme : semes) {
				Object oldSEMEKey = seme.getInstanceKey();
				seme.unrelateAcrossR503From(state);
				seme.relateAcrossR503To(state);
				seme.updateInstanceKey(oldSEMEKey, seme.getInstanceKey());
				CantHappen_c ch = CantHappen_c.getOneSM_CHOnR504(seme);
				if(ch != null) {
					Object oldChKey = ch.getInstanceKey();
					ch.unrelateAcrossR504From(seme);
					ch.relateAcrossR504To(seme);
					ch.updateInstanceKey(oldChKey, ch.getInstanceKey());
				}
				EventIgnored_c ei = EventIgnored_c.getOneSM_EIGNOnR504(seme);
				if(ei != null) {
					Object oldEiKey = ei.getInstanceKey();
					ei.unrelateAcrossR504From(seme);
					ei.relateAcrossR504To(seme);
					ei.updateInstanceKey(oldEiKey, ei.getInstanceKey());
				}
			}
		}
		Transition_c[] transitions = Transition_c.getManySM_TXNsOnR505(sm);
		for(Transition_c transition : transitions) {
			Object oldTKey = transition.getInstanceKey();
			transition.unrelateAcrossR505From(sm);
			transition.relateAcrossR505To(sm);
			transition.updateInstanceKey(oldTKey, transition.getInstanceKey());
			NoEventTransition_c net = NoEventTransition_c.getOneSM_NETXNOnR507(transition);
			if(net != null) {
				Object oldNetKey = net.getInstanceKey();
				net.unrelateAcrossR507From(transition);
				net.relateAcrossR507To(transition);
				net.updateInstanceKey(oldNetKey, net.getInstanceKey());
			}
			NewStateTransition_c nst = NewStateTransition_c.getOneSM_NSTXNOnR507(transition);
			if(nst != null) {
				Object oldNstKey = nst.getInstanceKey();
				nst.unrelateAcrossR507From(transition);
				nst.relateAcrossR507To(transition);
				nst.updateInstanceKey(oldNstKey, nst.getInstanceKey());
			}
			CreationTransition_c ct = CreationTransition_c.getOneSM_CRTXNOnR507(transition);
			if(ct != null) {
				Object oldCtKey = ct.getInstanceKey();
				ct.unrelateAcrossR507From(transition);
				ct.relateAcrossR507To(transition);
				ct.updateInstanceKey(oldCtKey, ct.getInstanceKey());
			}
		}
		MealyStateMachine_c msm = MealyStateMachine_c.getOneSM_MEALYOnR510(sm);
		if(msm != null) {
			Object oldMsmKey = msm.getInstanceKey();
			msm.unrelateAcrossR510From(sm);
			msm.relateAcrossR510To(sm);
			msm.updateInstanceKey(oldMsmKey, msm.getInstanceKey());
		}
		MooreStateMachine_c mosm = MooreStateMachine_c.getOneSM_MOOREOnR510(sm);
		if(mosm != null) {
			Object oldMosmKey = mosm.getInstanceKey();
			mosm.unrelateAcrossR510From(sm);
			mosm.relateAcrossR510To(sm);
			mosm.updateInstanceKey(oldMosmKey, mosm.getInstanceKey());
		}
		Action_c[] actions = Action_c.getManySM_ACTsOnR515(sm);
		for(Action_c action : actions) {
			Object oldActKey = action.getInstanceKey();
			action.unrelateAcrossR515From(sm);
			action.relateAcrossR515To(sm);
			action.updateInstanceKey(oldActKey, action.getInstanceKey());
			ActionHome_c actionHome = ActionHome_c.getOneSM_AHOnR514(action);
			if(actionHome != null) {
				Object oldAHKey = actionHome.getInstanceKey();
				actionHome.unrelateAcrossR514From(action);
				actionHome.relateAcrossR514To(action);
				actionHome.updateInstanceKey(oldAHKey, actionHome.getInstanceKey());
				MealyActionHome_c mah = MealyActionHome_c.getOneSM_MEAHOnR513(actionHome);
				if(mah != null) {
					Object oldMahKey = mah.getInstanceKey();
					mah.unrelateAcrossR513From(actionHome);
					mah.relateAcrossR513To(actionHome);
					mah.updateInstanceKey(oldMahKey, mah.getInstanceKey());
				}
				MooreActionHome_c moah = MooreActionHome_c.getOneSM_MOAHOnR513(actionHome);
				if(moah != null) {
					Object oldMoahKey = moah.getInstanceKey();
					moah.unrelateAcrossR513From(actionHome);
					moah.relateAcrossR513To(actionHome);
					moah.updateInstanceKey(oldMoahKey, moah.getInstanceKey());
				}
				TransitionActionHome_c tah = TransitionActionHome_c.getOneSM_TAHOnR513(actionHome);
				if(tah != null) {
					Object oldTahKey = tah.getInstanceKey();
					tah.unrelateAcrossR513From(actionHome);
					tah.relateAcrossR513To(actionHome);
					tah.updateInstanceKey(oldTahKey, tah.getInstanceKey());
				}
			}
		}
		StateMachineEventDataItem_c[] edis = StateMachineEventDataItem_c.getManySM_EVTDIsOnR516(sm);
		for(StateMachineEventDataItem_c di : edis) {
			Object oldDiKey = di.getInstanceKey();
			di.unrelateAcrossR516From(sm);
			di.relateAcrossR516To(sm);
			di.updateInstanceKey(oldDiKey, di.getInstanceKey());
		}
	}

	public IModelInspectorRegistry getModelInspectorRegistry() {
		return modelInspector;
	}

	public void releaseModel(Object inputObject, Object requester, Object key) {
		if (inputObject == null) {
			return;
		}
		ModelMapEntry entry = (ModelMapEntry) models.get(key);
		if (entry != null) {
			entry.references.remove(requester);
			if (entry.references.isEmpty()) {
				clearEntry(entry, key);
			}
		}
	}

	private void clearEntry(ModelMapEntry entry, Object key) {
		releaseModelEntry(entry);
		models.remove(key);
		entry.originalSMId = null;
		NonRootModelElement[] rootElements = rootElementMap.get(key);
		if (rootElements != null) {
			for (NonRootModelElement rootElement : rootElements) {
				rootElement.batchUnrelate();
			}
			rootElementMap.remove(key);
		}
		if (entry != null) {
			if (entry.loadedModels != null) {
				for (Model_c model : entry.loadedModels) {
					model.batchUnrelate();
				}
			}
		}
		readonlyInputs.remove(key);
		deleteModelRoot(entry.compareRoot);
	}

	private void deleteModelRoot(Ooaofooa root) {
		root.batchUnrelateAll();
		// must batchUnrelate the graphics elements
		// before calling clearDatabase as that method
		// clears the graphics root as well
		Ooaofgraphics graphicsRoot = Ooaofgraphics.getInstance(root.getId());
		graphicsRoot.batchUnrelateAll();
		root.clearDatabase(new NullProgressMonitor());
		root.delete(true);
	}

	protected ModelMapEntry createModelMapEntry(Object input,
			Ooaofooa compareRoot, Object key) {
		ModelMapEntry modelMapEntry = new ModelMapEntry(input, compareRoot);
		if (!modelMapEntry.loadModel(compareRoot, key)) {
			return null;
		}
		return modelMapEntry;
	}

	public boolean isInputReadonly(Object key) {
		return readonlyInputs.contains(key);
	}

	public int getCachedModelCount() {
		return models.size();
	}

	protected void releaseModelEntry(ModelMapEntry entry) {
		entry.references.clear();
	}

	class ModelMapEntry {
		IStreamContentAccessor rootNode;
		String errorMessage;
		Throwable throwable;
		List<Object> references = new Vector<Object>();
		private Object input;
		private Model_c[] loadedModels = null;
		private Ooaofooa compareRoot;
		private UUID originalSMId;

		ModelMapEntry(Object input, Ooaofooa compareRoot) {
			this.input = input;
			this.compareRoot = compareRoot;
		}

		public Model_c[] getLoadedGraphicalModels() {
			return loadedModels;
		}

		public UUID getOriginalSMId() {
			return originalSMId;
		}
		
		public boolean loadModel(Ooaofooa compareRoot, Object key) {
			if (!(input instanceof IStreamContentAccessor)) {
				errorMessage = "Not a valid input";
			} else {
				InputStream sca = null;
				try {
					sca = ((IStreamContentAccessor) input).getContents();
					rootNode = ((IStreamContentAccessor) input);

					if (sca == null) {
						return false;
					}

					if(sca.available() == 0) {
						List<Integer> bytes = new ArrayList<Integer>();
						int read = sca.read();
						if(read == -1) {
							return false;
						} else {
							bytes.add(read);
							while(read != -1) {
						         read = sca.read();
						         if(read != -1) {
						        	 bytes.add(read);
						         }
						     }
							sca.close();
							byte[] b = new byte[bytes.size()];
							for(int i = 0; i < bytes.size(); i++) {
								b[i] = bytes.get(i).byteValue();
							}
							sca = new ByteArrayInputStream(b);
						}
					}
					// configure IdAssigners to have upgrade elements
					// created with the same id
					IdAssigner.setSeedOfAllInstances(1, false);

					SystemModel_c sys = new SystemModel_c(compareRoot);
					sys.setParentModelRoot(Ooaofooa.getDefaultInstance());
					sys.setModelRoot(Ooaofooa.getDefaultInstance());
					ImportHelper helper = new ImportHelper(null);
					helper.setUpGlobals(sys);
					sys.setParentModelRoot(compareRoot);
					sys.setModelRoot(compareRoot);
					
					ModelStreamProcessor streamProcessor = null;
					PersistableModelComponent com = new PersistableModelComponent(
							sys);
					IModelImport modelInputLoader = new ImportModelComponent(
							sca, compareRoot, com, false, false, true, false);
					boolean isSingleFile = isSingleFile((CoreImport) modelInputLoader);
					boolean isSingleFileOld = isSingleFileOld((CoreImport) modelInputLoader);
					if (isSingleFile) {
						readonlyInputs.add(key);
						sca.close();
						sca = ((IStreamContentAccessor) input).getContents();
						Scanner scanner = new java.util.Scanner(sca)
								.useDelimiter("\\A");
						String contents = "";
						if (scanner.hasNext()) {
							contents = scanner.next();
						}
						sca.close();
						sca = ((IStreamContentAccessor) input).getContents();
						streamProcessor = new ModelStreamProcessor();
						modelInputLoader = CorePlugin.getStreamImportFactory()
								.create(sca, compareRoot, true, new Path(""));
						CoreImport.createUniqueIds = false;
						streamProcessor.setContents(contents);
						streamProcessor.setImporter(modelInputLoader);
					}
					if (isSingleFileOld) {
						readonlyInputs.add(key);
						sca.close();
						sca = ((IStreamContentAccessor) input).getContents();
						// dealing with old single file models
						modelInputLoader = CorePlugin.getModelImportFactory()
								.create(sca, compareRoot, sys, false, false,
										true, false);
					}
					int ret_val = 0;
					if (!isSingleFile) {
						ret_val = modelInputLoader
								.countAndValidateInsertStatements();
					}
					boolean successful = true;
					if (ret_val > 0 || isSingleFile) {
						modelInputLoader.run(new NullProgressMonitor());
					} else {
						successful = false;
					}
					if (isSingleFile || isSingleFileOld) {
						modelInputLoader.finishLoad(new NullProgressMonitor());
					} else {
						modelInputLoader.finishComponentLoad(
								new NullProgressMonitor(), false);
					}
					if ((successful && modelInputLoader.getSuccessful())
							|| (successful && isSingleFile)) {
						NonRootModelElement ele = modelInputLoader
								.getRootModelElement();
						NonRootModelElement[] rootElements = null;
						if (isSingleFile) {
							rootElements = streamProcessor
									.getExportedElements();
						} else {
							rootElements = new NonRootModelElement[] { ele };
						}
						// special case to store original sm id
						// so that we can compare two sms with
						// different ids under the same class
						for(NonRootModelElement rootElement : rootElements) {
							if(rootElement instanceof InstanceStateMachine_c) {
								originalSMId = ((InstanceStateMachine_c) rootElement).getSm_id();
							}
							if(rootElement instanceof ClassStateMachine_c) {
								originalSMId = ((ClassStateMachine_c) rootElement).getSm_id();
							}
						}
						rootElementMap.put(key, rootElements);
						// store loaded model before clearing the
						// database as they have no association
						// connection to the real elements
						loadedModels = Model_c.ModelInstances(Ooaofgraphics
								.getInstance(compareRoot.getId()));
						for (Model_c model : loadedModels) {
							model.setRepresents(Cl_c.Getinstancefromooa_id(
									compareRoot, model.getOoa_id(),
									model.getOoa_typeCachedValue()));
						}
						// we need to associate graphical elements and the model
						// with their specification classes otherwise
						// persistence will
						// not work properly as ooa_types will not be
						// determinable
						associateGraphicalSpecifications(loadedModels);
					} else {
						errorMessage = modelInputLoader.getErrorMessage();
					}
				} catch (InvocationTargetException e) {
					throwable = e;
					errorMessage = "Internal Error";
					ComparePlugin.writeToLog(errorMessage, e, this);
				} catch (InterruptedException e) {
					throwable = e;
					errorMessage = "Process Interrupted";
					ComparePlugin.writeToLog(errorMessage, e, this);
				} catch (IOException e) {
					throwable = e;
					errorMessage = "Error while reading input resource";
					ComparePlugin.writeToLog(errorMessage, e, this);
				} catch (CoreException e) {
					throwable = e;
					errorMessage = e.getMessage();
					ComparePlugin.writeToLog(errorMessage, e, this);
				} finally {
					CoreImport.createUniqueIds = true;
					IdAssigner.setSeedOfAllInstances(0, false);
					if (sca != null) {
						try {
							sca.close();
						} catch (IOException e1) {
							throwable = e1;
							errorMessage = "Error while closing input stream";
							ComparePlugin.writeToLog(errorMessage, e1, this);
						}
					}
				}
			}
			return true;
		}

		public boolean isValid() {
			return (errorMessage == null);
		}
	}

	public static class ModelLoadException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ModelLoadException(String message, Throwable e) {
			super(message, e);
		}
	}

	public boolean isSingleFile(CoreImport importer) {
		if (importer.getHeader().getModelComponentType().equals("StreamData")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	public void associateGraphicalSpecifications(Model_c[] loadedModels) {
		for (final Model_c model : loadedModels) {
			ModelSpecification_c ms = ModelSpecification_c
					.ModelSpecificationInstance(
							Ooaofgraphics.getDefaultInstance(),
							new ClassQueryInterface_c() {

								@Override
								public boolean evaluate(Object candidate) {
									return ((ModelSpecification_c) candidate)
											.getModel_type() == model
											.getModel_typeCachedValue()
											&& ((ModelSpecification_c) candidate)
													.getOoa_type() == model
													.getOoa_typeCachedValue();
								}
							});
			if (ms != null) {
				ms.relateAcrossR9To(model);
			}
			GraphicalElement_c[] elements = GraphicalElement_c
					.getManyGD_GEsOnR1(model);
			for (final GraphicalElement_c element : elements) {
				ElementSpecification_c spec = ElementSpecification_c
						.ElementSpecificationInstance(
								Ooaofgraphics.getDefaultInstance(),
								new ClassQueryInterface_c() {

									@Override
									public boolean evaluate(Object candidate) {
										return ((ElementSpecification_c) candidate)
												.getOoa_type() == element
												.getOoa_typeCachedValue();
									}
								});
				if (spec != null) {
					spec.relateAcrossR10To(element);
				}
				element.setRepresents(Cl_c.Getinstancefromooa_id(
						Ooaofooa.getInstance(element.getModelRoot().getId()),
						element.getOoa_id(), element.getOoa_type()));
			}
		}
	}

	public boolean isSingleFileOld(CoreImport importer) {
		if (importer.getHeader().getModelComponentType().equals("domain")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	public static Object getAncestorKey(Object inputElement) {
		return ANCESTOR + inputElement.hashCode();
	}

	public static Object getLeftKey(Object inputElement) {
		return LEFT + inputElement.hashCode();
	}

	public static Object getRightKey(Object inputElement) {
		return RIGHT + inputElement.hashCode();
	}

	public void releaseAllFor(Object requester) {
		HashMap<Object, List<ModelMapEntry>> entriesToRemove = new HashMap<Object, List<ModelMapEntry>>();
		Set<Object> keySet = models.keySet();
		for(Object key : keySet) {
			ModelMapEntry object = (ModelMapEntry) models.get(key);
			if(object != null) {
				List<ModelMapEntry> list = entriesToRemove.get(key);
				if(list == null) {
					list = new ArrayList<ModelCacheManager.ModelMapEntry>();
				}
				list.add(object);
				entriesToRemove.put(key, list);
			}
		}
		Set<Object> toRemoveKeySet = entriesToRemove.keySet();
		for(Object key : toRemoveKeySet) {
			List<ModelMapEntry> list = entriesToRemove.get(key);
			for(ModelMapEntry entry : list) {
				releaseModel(entry.input, requester, key);
			}
		}
	}
}
