package com.mentor.nucleus.bp.debug.ui.actions;

//======================================================================
//
// File: com/mentor/nucleus/bp/core/ui/MonitorAction.java
//
// (c) Copyright 2006-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//
//Version:      $Revision: 1.15 $
//
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ClassInEngine_c;
import com.mentor.nucleus.bp.core.ClassMonitor_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Monitor_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.debug.ui.model.BPThread;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;

public class MonitorAction implements IObjectActionDelegate {

	private ComponentInstance_c engine = null;
	/**
	 * Constructor for MonitorAction.
	 */
	public MonitorAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// The part is mainly needed to locate the selection provider, and
		// we cache our selection in core so no action is needed here.
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		IRunnableWithProgress r = new IRunnableWithProgress() {
			public void run(IProgressMonitor pm) {
				IStructuredSelection selection = (IStructuredSelection) Selection
						.getInstance().getSelection();
				Iterator it = selection.iterator();
				ModelRoot modelRoot = null;
				while (it.hasNext()) {
					Object obj = it.next();
					if (obj instanceof Instance_c) {
						Instance_c inst = (Instance_c) obj;
						modelRoot = inst.getModelRoot();
						Monitor_c mon = new Monitor_c(modelRoot);
						ComponentInstance_c exe = ComponentInstance_c
								.getOneI_EXEOnR2957(inst);
						mon.relateAcrossR2949To(exe);
						mon.relateAcrossR2949To(inst);
					}
					if(obj instanceof ClassInEngine_c) {
						obj = ModelClass_c.getOneO_OBJOnR2961((ClassInEngine_c)obj);
					}
					if (obj instanceof ModelClass_c) {
						ModelClass_c clazz = (ModelClass_c) obj;
						modelRoot = clazz.getModelRoot();
						ClassMonitor_c mon = new ClassMonitor_c(modelRoot);
						Domain_c domain = Domain_c.getOneS_DOMOnR1(Subsystem_c.getOneS_SSOnR2(clazz));
						if (engine != null) {
						  final ComponentInstance_c exe = engine;
						  ClassInEngine_c cie = ClassInEngine_c.getOneCSME_CIEOnR2961(clazz, new ClassQueryInterface_c() {
						
							public boolean evaluate(Object candidate) {
								return ((ClassInEngine_c)candidate).getExecution_engine_id().equals(exe.getExecution_engine_id());
							}
						
						  });
						  mon.relateAcrossR2950To(exe);
						  mon.relateAcrossR2950To(cie);
						}
					}
				}
				Ooaofooa.beginSaveOperation();
		        ModelChangedEvent mce = new ModelChangedEvent(modelRoot, Modeleventnotification_c.MODEL_EXECUTION_STOPPED, null);
		        Ooaofooa.getDefaultInstance().fireModelEvent(mce);
				Ooaofooa.endSaveOperation();
			}
		};
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(true,
					false, r);
		} catch (InterruptedException ie) {
			CorePlugin.logError("ExecuteAction", ie);
		} catch (InvocationTargetException ite) {
			CorePlugin.logError("ExecuteAction", ite);
		}
		BPThread.refreshCanvases();
		BPThread.refreshVerifierViews();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		Iterator it = ((IStructuredSelection) selection).iterator();
		boolean enabled = true;
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof Instance_c) {
				Instance_c inst = (Instance_c) obj;
				Monitor_c mon = Monitor_c.getOneI_MONOnR2949(inst);
				if (mon != null) {
					enabled = false;
				}
			}
			if(obj instanceof ClassInEngine_c) {
				obj = ModelClass_c.getOneO_OBJOnR2961((ClassInEngine_c)obj);
			}
			if (obj instanceof ModelClass_c) {
				ModelClass_c clazz = (ModelClass_c) obj;
				ComponentInstance_c exe = ComponentInstance_c
						.getOneI_EXEOnR2948(Domain_c
								.getOneS_DOMOnR1(Subsystem_c
										.getOneS_SSOnR2(clazz)));
				if (exe == null) {
					// see if this class is under a component
					// that is being verified
					exe = getExecutionEngine();
				}
				enabled = false;
				if (exe != null) {
				  final ComponentInstance_c finalEng = exe;
				  ClassMonitor_c mon = ClassMonitor_c.getOneCSME_CLMOnR2950(
						                ClassInEngine_c.getOneCSME_CIEOnR2961(
								            clazz, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((ClassInEngine_c)candidate).getExecution_engine_id().equals(finalEng.getExecution_engine_id());
					}
				
				  }));
				  ClassStateMachine_c clsm = ClassStateMachine_c
						.getOneSM_ASMOnR519(clazz);
				  if (mon == null && clsm != null) {
					enabled = true;
					engine = exe;
				  }
				}
			}
		}
		action.setEnabled(enabled);
	}

	private ComponentInstance_c getExecutionEngine() {
	      IWorkbenchPart activePart = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().getActivePart();
	      if(activePart instanceof SessionExplorerView) {
			SessionExplorerView sessionExplorer = (SessionExplorerView) activePart;
			ComponentInstance_c engine = sessionExplorer.getTreeViewer()
								             .getExecutionEngineOfSelectedElement();
			if(engine != null) {
				return engine;
			}
		  }
		  return null;
		}
}
