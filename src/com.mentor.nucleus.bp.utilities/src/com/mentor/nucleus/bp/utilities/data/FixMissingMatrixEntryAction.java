//====================================================================
//
// File:      $RCSfile: FixMissingMatrixEntryAction.java,v $
// Version:   $Revision: 1.6 $
// Modified:  $Date: 2013/01/10 23:21:48 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.utilities.data;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.mentor.nucleus.bp.core.CantHappen_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.NonLocalEvent_c;
import com.mentor.nucleus.bp.core.PolymorphicEvent_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class FixMissingMatrixEntryAction implements IActionDelegate {

	private Object fElement;

	@Override
	public void run(IAction action) {
		ArrayList<StateMachine_c> machines = getMachinesFromSelection((NonRootModelElement) fElement);
		if (machines.size() == 0) {
			// report that no state machines exist
			UIUtil
					.openInformation(PlatformUI.getWorkbench().getDisplay()
							.getActiveShell(), "State Event Matrix Utility",
							"The selected element has no child state machine in it's hierarchy.");
			return;
		}
		// process each machine, looking for missing SM_SEME entries
		ArrayList<StateEventMatrixEntry_c> list = new ArrayList<StateEventMatrixEntry_c>();
		for (StateMachine_c machine : machines) {
			list.addAll(updateMachine(machine));
		}
		createAndDisplayReport(list);
		// persist the changes
		persistChanges(list);
	}

	private void persistChanges(ArrayList<StateEventMatrixEntry_c> list) {
		for(StateEventMatrixEntry_c entry : list) {
			try {
				entry.getPersistableComponent().persist();
			} catch (CoreException e) {
				CorePlugin.logError("Unable to persist changes.", e);
			}
		}
	}

	private void createAndDisplayReport(ArrayList<StateEventMatrixEntry_c> list) {
		// build a report to show the user what was missing and
		// created
		StateMachine_c current = null;
		String report = "";
		for (StateEventMatrixEntry_c entry : list) {
			StateMachineState_c state = StateMachineState_c
					.getOneSM_STATEOnR503(entry);
			StateMachine_c machine = StateMachine_c.getOneSM_SMOnR501(state);
			if (machine != current) {
				if (current != null) {
					report = report + "\n\n";
				}
				report = report + getPathForMachine(machine) + "\n";
				current = machine;
			}
			StateMachineEvent_c evt = StateMachineEvent_c
					.getOneSM_EVTOnR525(SemEvent_c.getOneSM_SEVTOnR503(entry));
			report = report + "\n\t";
			report = report + "State: " + state.getName() + "\n";
			report = report + "\tEvent: " + evt.Get_event_text() + "\n";
			report = report + "\tCreated: Cant Happen\n";
		}
		if (!report.equals("")) {
			// store the report then open it with the text
			// editor
			NonRootModelElement element = (NonRootModelElement) fElement;
			IFile file = element.getPersistableComponent().getFile();
			IProject project = file.getProject();
			IFile resultFile = project.getFile("StateEventMatrixUtilityReport.txt");
			if(!resultFile.exists()) {
				try {
					resultFile.create(new ByteArrayInputStream(report.getBytes()),
							true, new NullProgressMonitor());
				} catch (CoreException e) {
					CorePlugin.logError("Unable to store report.", e);
				}
			} else {
				try {
					resultFile.setContents(new ByteArrayInputStream(report
							.getBytes()), true, true, new NullProgressMonitor());
				} catch (CoreException e) {
					CorePlugin.logError("Unable to store report.", e);
				}
			}
			try {
				IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage(), resultFile);
			} catch (PartInitException e) {
				CorePlugin.logError("Unable to open text editor on result file.", e);
			}
		} else {
			UIUtil.openInformation(PlatformUI.getWorkbench()
					.getDisplay().getActiveShell(),
					"State Event Matrix Utility", "There were no missing entries.");			
		}
	}

	private String getPathForMachine(StateMachine_c machine) {
		String path = "";
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR517(machine);
		ModelClass_c clazz = null;
		if (ism != null) {
			clazz = ModelClass_c.getOneO_OBJOnR518(ism);
			path = path + "Instance State Machine";
		} else {
			path = path + "Class State Machine";
			ClassStateMachine_c csm = ClassStateMachine_c
					.getOneSM_ASMOnR517(machine);
			clazz = ModelClass_c.getOneO_OBJOnR519(csm);
		}
		path = clazz.Getpath(true, "") + "::" + path;
		return path;
	}

	private ArrayList<StateEventMatrixEntry_c> updateMachine(
			StateMachine_c machine) {
		ArrayList<StateEventMatrixEntry_c> list = new ArrayList<StateEventMatrixEntry_c>();
		StateMachineState_c[] states = StateMachineState_c
				.getManySM_STATEsOnR501(machine);
		for (int i = 0; i < states.length; i++) {
			StateMachineState_c state = states[i];
			StateEventMatrixEntry_c[] semes = StateEventMatrixEntry_c
					.getManySM_SEMEsOnR503(state);
			StateMachineEvent_c[] events = StateMachineEvent_c
					.getManySM_EVTsOnR502(machine);
			for (int k = 0; k < events.length; k++) {
				// ignore polymorphic events as they never have SM_SEME
				// data
				if(PolymorphicEvent_c.getOneSM_PEVTOnR525(events[k]) != null)
					continue;
				boolean found = false;
				UUID smevt_id = events[k].getSmevt_id();
				for (int l = 0; l < semes.length; l++) {
					if (semes[l].getSmevt_id().equals(smevt_id)) {
						found = true;
					}
				}
				// if not found, create here
				if (!found) {
					StateEventMatrixEntry_c entry = new StateEventMatrixEntry_c(
							machine.getModelRoot());
					entry.relateAcrossR503To(state);
					CantHappen_c ch = new CantHappen_c(machine.getModelRoot());
					entry.relateAcrossR504To(ch);
					SemEvent_c smevt = null;
					PolymorphicEvent_c poly = PolymorphicEvent_c.getOneSM_PEVTOnR525(events[k]);
					if(poly != null) {
						smevt = SemEvent_c.getOneSM_SEVTOnR526(NonLocalEvent_c.getOneSM_NLEVTOnR527(poly));
					} else {
						smevt = SemEvent_c.getOneSM_SEVTOnR525(events[k]);
					}
					entry.relateAcrossR503To(smevt);
					list.add(entry);
				}
			}
		}
		return list;
	}

	private ArrayList<StateMachine_c> getMachinesFromSelection(
			NonRootModelElement element) {
		ArrayList<StateMachine_c> list = new ArrayList<StateMachine_c>();
		IPersistenceHierarchyMetaData hierarchyMetaData = PersistenceManager
				.getHierarchyMetaData();
		List<?> children = hierarchyMetaData.getChildren(element, false);
		for (Object child : children) {
			if (child instanceof StateMachine_c) {
				list.add((StateMachine_c) child);
			} else {
				list
						.addAll(getMachinesFromSelection((NonRootModelElement) child));
			}
		}
		return list;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// extension point registration guarantees that the
		// selection will be as expected
		fElement = ((IStructuredSelection) selection).getFirstElement();
	}

}
