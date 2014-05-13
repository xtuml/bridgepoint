package com.mentor.nucleus.bp.internal.tools.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.CantHappen_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.EventIgnored_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.LocalEvent_c;
import com.mentor.nucleus.bp.core.MealyActionHome_c;
import com.mentor.nucleus.bp.core.MealyStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.MooreStateMachine_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.NoEventTransition_c;
import com.mentor.nucleus.bp.core.NonLocalEvent_c;
import com.mentor.nucleus.bp.core.PolymorphicEvent_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.SignalEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.Util_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.io.core.CorePlugin;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class UpdateSMIDs implements IActionDelegate {

	private ISelection selection;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		IStructuredSelection ss = (IStructuredSelection) selection;
		InstanceStateMachine_c ism = (InstanceStateMachine_c) ss.getFirstElement();
		List<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
		list.add(ism);
		upgradeModelClassesForSMIds(list);
	}

	/**
	 * Store any existing SM_ID in newly created id holders
	 */
	public void upgradeModelClassesForSMIds(List<NonRootModelElement> loadedElements) {
		for(NonRootModelElement element : loadedElements) {
			if(element instanceof InstanceStateMachine_c) {
				final InstanceStateMachine_c ism = (InstanceStateMachine_c) element;
				ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR518(ism);
				UUID id = Util_c
						.Getuniquestatemachineid(clazz.getObj_id(), "i");
				if (!id.equals(ism.getSm_id())) {
					// update the graphical ooa id
					Model_c gm = Model_c.ModelInstance(Ooaofgraphics
							.getInstance(ism.getModelRoot().getId()),
							new ClassQueryInterface_c() {

								@Override
								public boolean evaluate(Object candidate) {
									return ((Model_c) candidate)
											.getOoa_id().equals(ism.getSm_idCachedValue());
								}
							});
					if(gm != null) {
						gm.setOoa_id(id);
					}
					StateMachine_c sm = StateMachine_c.getOneSM_SMOnR517(ism);
					Object oldKey = sm.getInstanceKey();
					sm.setSm_id(id);
					updateSubtypes(sm);
					sm.updateInstanceKey(oldKey, sm.getInstanceKey());
					if (clazz.getIsm_id().equals(Gd_c.Null_unique_id())) {
						clazz.setIsm_id(ism.getSm_id());
						try {
							persistPersistableComponent(clazz.getPersistableComponent());
						} catch (CoreException e) {
							CorePlugin
									.logError(
											"Unable to persist updated state machine ids in the model class.",
											e);
						}
					}
					try {
						persistPersistableComponent(sm
								.getPersistableComponent());
					} catch (CoreException e) {
						CorePlugin.logError(
								"Unable to persist updated state machine ids.",
								e);
					}
				}
			}
			if(element instanceof ClassStateMachine_c) {
				final ClassStateMachine_c csm = (ClassStateMachine_c) element;
				ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR519(csm);
				UUID id = Util_c.Getuniquestatemachineid(clazz.getObj_id(), "c");
				if(!id.equals(csm.getSm_id())) {
					// update the graphical ooa id
					Model_c gm = Model_c.ModelInstance(Ooaofgraphics
							.getInstance(csm.getModelRoot().getId()),
							new ClassQueryInterface_c() {

								@Override
								public boolean evaluate(Object candidate) {
									return ((Model_c) candidate).getOoa_id()
											.equals(csm.getSm_idCachedValue());
								}
							});
					if(gm != null) {
						gm.setOoa_id(id);
					}
					StateMachine_c sm = StateMachine_c.getOneSM_SMOnR517(csm);
					Object oldKey = sm.getInstanceKey();
					sm.setSm_id(id);
					updateSubtypes(sm);
					sm.updateInstanceKey(oldKey, sm.getInstanceKey());
					if(clazz.getCsm_id().equals(Gd_c.Null_unique_id())) {
						clazz.setCsm_id(csm.getSm_id());
						try {
							persistPersistableComponent(clazz.getPersistableComponent());
						} catch (CoreException e) {
							CorePlugin
									.logError(
											"Unable to persist updated state machine ids in the model class.",
											e);
						}
					}
					try {
						persistPersistableComponent(sm
								.getPersistableComponent());
					} catch (CoreException e) {
						CorePlugin.logError(
								"Unable to persist updated state machine ids.",
								e);
					}
				}
			}
		}
	}

	private void updateSubtypes(StateMachine_c sm) {
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

	private void persistPersistableComponent(final PersistableModelComponent pmc) throws CoreException {
		WorkspaceJob job = new WorkspaceJob("Upgrade Persistence") {
			
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				pmc.persist();
				return Status.OK_STATUS;
			}
		};
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			CorePlugin.logError("Error joining the upgrade persistence job.", e);
		}
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
