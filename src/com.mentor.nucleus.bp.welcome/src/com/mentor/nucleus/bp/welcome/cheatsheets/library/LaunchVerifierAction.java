/**
 * 
 */
package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: LaunchVerifierAction.java,v $
//Version: $Revision: 1.5 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.Thread.State;

import javax.swing.JPanel;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.IBPDebugUIPluginConstants;
import com.mentor.nucleus.bp.debug.ui.actions.ExecuteAction;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.launch.VerifierLaunchConfiguration;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

/**
 * @author hkhaled
 * 
 */
public class LaunchVerifierAction extends Action implements ICheatSheetAction {
	IWorkbenchPage m_wp;
	ExplorerView m_bp_view;
	TreeViewer m_bp_tree;

	@Override
	public void run(String[] params, ICheatSheetManager manager) {

		String projectName = params[0];
		final String componentName = params[1];
		String leaveWizard = params[2];

		SystemModel_c systemModel = ProjectUtilities.getSystemModel(projectName);

		openPerspectiveAndView("com.mentor.nucleus.bp.core.perspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);
		processDebugEvents();
		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR1405(systemModel)),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						Component_c selected = (Component_c) candidate;
						return selected.getName().equals(componentName);
					}
				});

		openPerspectiveAndView(
				"com.mentor.nucleus.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		Selection.getInstance().clear();
		Selection.getInstance().setSelection(new StructuredSelection(comp));
		Menu menu = m_bp_tree.getControl().getMenu();
		CanvasUtilities.checkItemStatusInContextMenu(menu,
				"Launch Verifier", "", false);
		MenuItem launchVerifierItem = getLaunchVerifierItem(menu);
		
		if (leaveWizard == null){
			CanvasUtilities.debugToDialog(200);
		}
		launchVerifierItem.notifyListeners(SWT.Selection, null);
		processDisplayEvents();

		// wait for the engines to start
		joinLaunchJob();

	}

	public static void processDisplayEvents() {
		try {
			while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
				;
			processPlatformJobs();
		} catch (SWTException e) {
			// ignore and continue processing, the test
			// code completes before allowing a progress
			// dialog from properly closing
			processDisplayEvents();
		}
	}

	public static void processPlatformJobs() {
		Job[] jobs = Job.getJobManager().find(null);
		for (int i = 0; i < jobs.length; i++) {
			boolean wait = jobs[i].getState() == Job.RUNNING;
			Thread thread = jobs[i].getThread();
			if (wait) {
				if (thread != null)
					wait = thread.getState() != State.WAITING;
			}
			while (wait) {
				PlatformUI.getWorkbench().getDisplay().readAndDispatch();
				wait = jobs[i].getState() == Job.RUNNING;
				if (wait) {
					thread = jobs[i].getThread();
					if (thread != null)
						wait = thread.getState() != State.WAITING;
				}
			}
		}
	}

	public static void waitForExecution() {
		processPlatformJobs();
		// join the execute thread
		Thread thread = ExecuteAction.getRunner();
		if (thread != null) {
			while (thread.isAlive()) {
				Display.getCurrent().readAndDispatch();
			}
		}
		processDebugEvents();
	}

	public static Menu getMenuInSETree(Object treeElement) {
		SessionExplorerView sev = BPDebugUtils.openSessionExplorerView(true);
		UIUtil.dispatchAll();

		String text = "";
		if (treeElement instanceof NonRootModelElement) {
			text = ((NonRootModelElement) treeElement).getName();
		}
		TreeViewer viewer = sev.getTreeViewer();
		viewer.expandAll();
		UIUtil.refreshViewer(viewer);
		Selection.getInstance().setSelection(
				new StructuredSelection(treeElement));
		UIUtil.dispatchAll();
		Tree sevTree = viewer.getTree();
		sevTree.selectAll();
		UIUtil.dispatchAll();

		TreeItem x[] = sevTree.getSelection();

		for (int i = 0; i < x.length; ++i) {

			String item = x[i].getText();

			if (item.equals(text)) {
				TreeItem[] sel = new TreeItem[1];
				sel[0] = x[i];
				sevTree.setSelection(sel);
				UIUtil.dispatchAll();
				break;
			}
		}

		Menu menu = viewer.getTree().getMenu();
		return menu;
	}

	public static void setSelectionInSETree(IStructuredSelection sel) {
		SessionExplorerView sev = BPDebugUtils.openSessionExplorerView(true);
		sev.getTreeViewer().expandAll();
		sev.getTreeViewer().setSelection(sel);
		Selection.getInstance().setSelection(sel);
		UIUtil.dispatchAll();
	}

	public static SessionExplorerView openSessionExplorerView(
			final boolean showView) {

		IViewPart tempView = null;
		try {
			IWorkbenchPage dbgPage = PlatformUI.getWorkbench().showPerspective(
					IBPDebugUIPluginConstants.BPPERSPECTIVE_ID,
					PlatformUI.getWorkbench().getActiveWorkbenchWindow());

			if (dbgPage != null) {
				if (showView) {
					tempView = dbgPage
							.showView(IBPDebugUIPluginConstants.ID_VIEW_INSTANCE);
				} else {
					tempView = dbgPage
							.findView(IBPDebugUIPluginConstants.ID_VIEW_INSTANCE);
				}
			}

		} catch (WorkbenchException e) {
		}

		if (tempView instanceof SessionExplorerView) {
			return (SessionExplorerView) tempView;
		}

		return null;
	}

	public static MenuItem getExecuteItem(Menu menu) {
		MenuItem[] items = menu.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getText().equals("Execute")) {
				return items[i];
			}
		}
		return null;
	}

	static void joinLaunchJob() {
		Job[] jobs = Job.getJobManager().find(null);
		for (int i = 0; i < jobs.length; i++) {
			if (jobs[i].getName().equals("Launching")) {
				try {
					jobs[i].join();
					processDisplayEvents();
				} catch (InterruptedException e) {
				}
			}
		}
	}

	static void processDebugEvents() {
		DebugPlugin.getDefault().asyncExec(new Runnable() {
			public void run() {
			}
		});
		Job job = getJob("Debug Event Dispatch");
		if (job != null && job.getState() == Job.RUNNING) {
			try {
				job.join();
			} catch (InterruptedException e) {
			}
		}
	}

	public static Job getJob(String name) {
		Job[] jobs = Platform.getJobManager().find(null);
		for (int i = 0; i < jobs.length; i++) {
			if (jobs[i].getName().equals(name)) {
				return jobs[i];
			}
		}
		return null;
	}

	public static MenuItem getLaunchVerifierItem(Menu menu) {
		MenuItem[] items = menu.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getText().equals("Launch Verifier")) {
				return items[i];
			}
		}
		return null;
	}

	public boolean openPerspectiveAndView(final String perspective,
			final String view) {
		boolean wasSuccessful = true;

		// Wait for the xtUMl Debug perspective to open
		try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					m_wp = PlatformUI.getWorkbench().showPerspective(
							perspective,
							PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow());
					m_bp_view = (ExplorerView) m_wp.findView(view);
					m_bp_tree = m_bp_view.getTreeViewer();
					m_wp.activate(m_bp_view);
				}
			};
			ResourcesPlugin.getWorkspace().run(r, null);
			processDisplayEvents();
		} catch (CoreException x) {

		}
		return wasSuccessful;
	}
}
