package com.mentor.nucleus.bp.debug.ui.actions;

//======================================================================
//
// File: com/mentor/nucleus/bp/core/ui/MonitorAction.java
//
// (c) Copyright 2006-2014 by Mentor Graphics Corp.  All rights reserved.
//
//======================================================================
//
//Version:      $Revision: 1.14 $
//
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.*;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.debug.ui.model.BPThread;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;

public class MonitorOffAction implements IObjectActionDelegate {

	private ComponentInstance_c engine = null;
	/**
	 * Constructor for MonitorAction.
	 */
	public MonitorOffAction() {
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
						Monitor_c mon = Monitor_c.getOneI_MONOnR2949(inst);
						mon.Dispose();
					}
					if(obj instanceof ClassInEngine_c) {
						obj = ModelClass_c.getOneO_OBJOnR2961((ClassInEngine_c)obj);
					}
					if (obj instanceof ModelClass_c) {
						ModelClass_c clazz = (ModelClass_c) obj;
						modelRoot = clazz.getModelRoot();
						if (engine != null) {
						  final ComponentInstance_c finalEng = engine;
						  ClassMonitor_c mon = ClassMonitor_c.getOneCSME_CLMOnR2950(
					                ClassInEngine_c.getOneCSME_CIEOnR2961(
							            clazz, new ClassQueryInterface_c() {
				            public boolean evaluate(Object candidate) {
					          return ((ClassInEngine_c)candidate).getExecution_engine_id().equals(finalEng.getExecution_engine_id());
				            }
			              }));
						  if (mon != null) {
						    mon.Dispose();
						  }
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
		BPThread.refreshVerifierViews();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		ComponentInstance_c exe = null;
		Iterator it = ((IStructuredSelection) selection).iterator();
		boolean enabled = true;
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof Instance_c) {
				Instance_c inst = (Instance_c) obj;
				Monitor_c mon = Monitor_c.getOneI_MONOnR2949(inst);
				if (mon == null) {
					enabled = false;
				}
			}
			if (obj instanceof ClassInEngine_c) {
				exe = ComponentInstance_c.getOneI_EXEOnR2960((ClassInEngine_c)obj);
				obj = ModelClass_c.getOneO_OBJOnR2961((ClassInEngine_c)obj);
			}
			if (obj instanceof ModelClass_c) {
			  ModelClass_c clazz = (ModelClass_c) obj;
			  if (exe == null) {
				// If the selected tree element is a Class In Engine instance,'
				// we already have the correct Component Instance, else . . .
			    exe = ComponentInstance_c
				             .getOneI_EXEOnR2948(Domain_c
						              .getOneS_DOMOnR1(Subsystem_c
								                       .getOneS_SSOnR2(clazz)));
		        if (exe == null) {
			      // see if this class is under a component
			      // that is being verified
			      exe = getExecutionEngine();
		        }
		      }
		      if (exe != null) {
			    final ComponentInstance_c finalEng = exe;
			    ClassMonitor_c mon = ClassMonitor_c.getOneCSME_CLMOnR2950(
			                        ClassInEngine_c.getOneCSME_CIEOnR2961(
					                    clazz, new ClassQueryInterface_c() {
		          public boolean evaluate(Object candidate) {
			        return ((ClassInEngine_c)candidate).getExecution_engine_id().equals(finalEng.getExecution_engine_id());
		          }
	            }));
				if (mon == null || exe == null) {
					enabled = false;
				}
		      }
			  engine = exe;
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
