/**
 * 
 */
package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: ExecuteElementAction.java,v $
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
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.IBPDebugUIPluginConstants;
import com.mentor.nucleus.bp.debug.ui.actions.ExecuteAction;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;


/**
 * @author hkhaled
 * 
 */
public class ExecuteElementAction extends Action implements ICheatSheetAction {
	IWorkbenchPage m_wp;
	ExplorerView m_bp_view;
	TreeViewer m_bp_tree;

	@Override
	public void run(String[] params, ICheatSheetManager manager) {

		String projectName = params[0];
		String elementType = params[1];

		SystemModel_c systemModel = ProjectUtilities.getSystemModel(projectName);
	     openPerspectiveAndView(
				"com.mentor.nucleus.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);		
		Menu menu = null;
		
		if (elementType.equalsIgnoreCase("function"))
		{
			final String pkgName = params[2];
		    final String fnName  =  params [3];
		    
		    
			Package_c pkg = Package_c.getOneEP_PKGOnR1405(systemModel,
					new ClassQueryInterface_c() {
						public boolean evaluate(Object candidate) {
							Package_c selected = (Package_c) candidate;
							return selected.getName().equals(pkgName);
						}
					});
			
		    Function_c function = Function_c.getOneS_SYNCOnR8001( PackageableElement_c.getManyPE_PEsOnR8000(pkg) , new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					Function_c selected = (Function_c) candidate;
					return selected.getName().equals(fnName);
				}
			});

			LaunchVerifierAction.setSelectionInSETree(new StructuredSelection(function));
		    menu = LaunchVerifierAction.getMenuInSETree(function);
			
		}
		else if (elementType.equalsIgnoreCase("interface Operation"))
		{
			final String compName = params [2];
			final String portName = params [3];
			final String ifaceOpName =  params [4];
			
			NonRootModelElement element = CheatSheetsUtilities.findElement(projectName,null, elementType, ifaceOpName, compName, portName);
			
			InterfaceOperation_c ifaceOp = (InterfaceOperation_c) element;
			LaunchVerifierAction.setSelectionInSETree(new StructuredSelection(ifaceOp));
		    menu = LaunchVerifierAction.getMenuInSETree(ifaceOp);
			
		}

		CanvasUtilities.checkItemStatusInContextMenu(menu,
				"Execute", "", false);
		
		MenuItem excuteMenuItem = LaunchVerifierAction.getExecuteItem(menu);
		excuteMenuItem.notifyListeners(SWT.Selection, null);

		LaunchVerifierAction.waitForExecution();
		LaunchVerifierAction.processDebugEvents();

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
			LaunchVerifierAction.processDisplayEvents();
		} catch (CoreException x) {

		}
		return wasSuccessful;
	}




}
