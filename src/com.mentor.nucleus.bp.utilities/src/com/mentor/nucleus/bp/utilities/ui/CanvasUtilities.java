package com.mentor.nucleus.bp.utilities.ui;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.explorer.ExplorerPlugin;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.ui.graphics.actions.OpenGraphicsEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.ui.text.activity.ShowActivityAction;

public class CanvasUtilities {
	private static Point fDownLocation;
	private static ExplorerView explorer;

	public static Point getCenterPoint(Graphelement_c element, Graphnode_c node) {
		return new Point(
			(int)(element.getPositionx() + node.getWidth() / 2),
			(int)(element.getPositiony() + node.getHeight() / 2));
	}

	public static Point getShapeCenter(Shape_c shp) {
	    Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(shp);
	    Graphelement_c element = Graphelement_c.getOneDIM_GEOnR301(node);
		return getCenterPoint(element, node);
	}
	
	private static String removeAccelerator(String withAccelerator){
		return withAccelerator.replaceFirst("&", "");
	}

	public static MenuItem[] getMenuItems(final Menu menu, String childMenu) {

		Event event = new Event();
		event.x = 5;
		event.y = 5;
		menu.notifyListeners(SWT.Show, event);

		MenuItem[] items;

		// get child items if item in question
		// is contained in a child menu
		if (!childMenu.equals("")) {
			items = getChildMenuItems(menu, childMenu);
		} else {
			items = menu.getItems();
		}

		event.doit = true;
		menu.notifyListeners(SWT.Hide, event);

		return items;

	}
	public static void activateMenuItem(MenuItem item) {
		item.notifyListeners(SWT.Selection, null);
	}
	public static MenuItem getMenuItem(Menu menu, String name) {
		MenuItem[] items = getMenuItems(menu, "");
		for(int i = 0; items != null && i < items.length; i++) {
			if(items[i].getText().equals(name)) {
				return items[i];
			}
		}
		return null;
	}
	
	public static void activateMenuItem(Menu menu, String name) {
		MenuItem item = getMenuItem(menu, name);
		activateMenuItem(item);
	}
    public static void sleep(long millis)
    {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
    }
	
	public static void debugToDialog(long inHowManyMillis) {
		dismissDialog(inHowManyMillis, 0, false, "&Debug");
	}
    private static void cancelDialog(Dialog dialog) {
        Control bb = dialog.buttonBar;
        Button cb = findButton(bb.getParent(), "Cancel");
        cb.notifyListeners(SWT.Selection, null);
    }

    public static Button findButton(Composite parent, String buttonName) {
        Control [] child_set = parent.getChildren();
        for ( int i = 0; i < child_set.length; ++i )
        {
        	if ( child_set[i] instanceof Button )
            {
                Button cc = (Button) child_set[i];
                String l = cc.getText();
                if ( l.equals(buttonName))
                {
                 return cc;   
                }
            }
            else if ( child_set[i] instanceof Composite )
            {
                Button result = findButton((Composite)child_set[i], buttonName);
                if ( result != null )
                {
                   return result;   
                }
            }
        }
        return null;
	}    
    
    
	/**
	 * This is used to store the text of a dialog just before it is dismissed.
	 * It allows us to validate that the text that was in the dialog is what we expected.
	 */
	public static String dialogText;
    private static void dismissDialog(final long inHowManyMillis, 
            final int currentRecursionDepth, final boolean shouldDismiss, final String button)
    {
        // run this on a separate thread, so that the dialog invocation to be performed
        // by the caller may occur
        final int maxRecursionDepth = 10;
        Thread dismissThread = new Thread(new Runnable() {
		
			@Override
			public void run() {

                // wait to give the expected modal dialog time to get displayed 
                sleep(inHowManyMillis);
                dialogText = "";
                
                // if the currently active shell is a dialog 
                PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				
					@Override
					public void run() {
		                Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		                if (shell != null && shell.getData() instanceof Dialog) {
		                    // close the dialog
		                	if(!(shell.getData() instanceof ProgressMonitorDialog)) {
		                    	Control[] ctrls = ((Dialog)shell.getData()).getShell().getChildren();
		                    	for (int i = 0; i< ctrls.length; i++) {
		                    		Control ctrl = ctrls[i];
		                    		
		                    		if (ctrl instanceof Label) {
		                    			dialogText = dialogText + ((Label)ctrl).getText();
		                    		}
		                    	}
		                        if ( shouldDismiss )
		                        {
		                		    ((Dialog)shell.getData()).close();
		                        }
		                        else if ( button != null )
		                        {
		                        	Button foundButton = findButton(shell, button);
		                        	foundButton.notifyListeners(SWT.Selection, null);
		                        } else {
		                           cancelDialog( (Dialog)shell.getData() );   
		                        }
		                	} else {
		                		dismissDialog(inHowManyMillis, currentRecursionDepth + 1, shouldDismiss, button);
		                	}
		                	
		                }
		                // otherwise, call this recursively, so that further attempts 
		                // may be made, with each one allowing the UI-thread to take
		                // back control for awhile to get the dialog shown
		                else if (currentRecursionDepth <= maxRecursionDepth) {
		                	Shell[] shells = PlatformUI.getWorkbench().getDisplay().getShells();
		                	for (int i = 0; i < shells.length; i++) {
		                		if (shells[i].getData() instanceof LaunchConfigurationsDialog) {
		                			shells[i].forceActive();
		                		}
		                		else if (shells[i].getData() instanceof MessageDialog) {
		                			if (((MessageDialog)shells[i].getData()).getShell().getText().equals("Upgrade model to global data types")) {
		                				shells[i].forceActive();
		                			}
		                		}
		                	}
		                    dismissDialog(inHowManyMillis, currentRecursionDepth + 1, shouldDismiss, button);
		                }
		                else {
		                	Throwable t = new Throwable();
		                	t.setStackTrace(Thread.currentThread().getStackTrace());
		                	CorePlugin.logError("Failed to dismiss dialog", t);
		                }
					}
				});
			}
		});
        dismissThread.start();
    }
    
	
	
	
	
	private static MenuItem[] getChildMenuItems(Menu menu, String childMenu) {
		MenuItem[] parentItems = menu.getItems();
		for (int i = 0; i < parentItems.length; i++) {
			if (removeAccelerator(parentItems[i].getText()).equals(childMenu)) {
				MenuManager manager = (MenuManager) parentItems[i].getData();
				Menu child = manager.getMenu();
				Event event = new Event();
				event.x = 5;
				event.y = 5;
				child.notifyListeners(SWT.Show, event);
				return child.getItems();
			}
		}
		return null;
	}
	
	
	
	public static boolean checkItemStatusInContextMenu(Menu menu, String text, String childMenu, boolean readonly) {
    	
    	MenuItem[] items = getMenuItems(menu, childMenu);
    	
    	// find the item in question
    	for(int i = 0; items != null && i < items.length; i++) {
    		if(removeAccelerator(items[i].getText()).indexOf(text) != -1) {
    			// verify the item
    			// if item is enabled
    			if(items[i].isEnabled()) {
    				// and is read only
    				if(readonly) {
    					// item should be disabled
    					return false;
    				} else {
    					// item is fine
    					return true;
    				}
    			} else {
    				// if item is disabled
    				// switch the returns
    				if(readonly) {
    					return true;
    				} else {
    					return false;
    				}
    			}
    		}
    	}
    	// action was not found
    	return false;
    }
	
	static public void openCanvasEditor(final Object uut) {
		try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor)
					throws CoreException {
					IStructuredSelection ss = new StructuredSelection(uut);
	                Selection selection = Selection.getInstance();
	                selection.addToSelection(ss);
					OpenGraphicsEditor sca = new OpenGraphicsEditor();
					selection.setSelection(ss);
					Action a = new Action() {
					};
					sca.run(a);
				}
			};
			getWorkspace().run(r, null);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().update();
		} catch (CoreException x) {
			CorePlugin.logError("Unable to open canvas editor.", x);
		}
	}

	/**
	 * Returns the workspace instance.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}
	
	static public void openActivityEditor(final Object uut) {

		try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor)
				throws CoreException {
					IStructuredSelection ss = new StructuredSelection(uut);
					ShowActivityAction sda = new ShowActivityAction();
					Action a = new Action() {
					};
					sda.selectionChanged(a, ss);
					sda.run(a);
				}
			};
			CanvasUtilities.getWorkspace().run(r, null);
		} catch (CoreException x) {
			CorePlugin.logError("Unable to open activity editor.", x);
		}

	}
	public static void doMousePress(int x, int y)
    {
		createMouseEvent(x, y, "MouseDown");
    }
	
	public static void doMouseMove(int x, int y)
    {
		createMouseEvent(x, y, "MouseMove");
    }
	public static void doMouseRelease(int x, int y)
    {
		createMouseEvent(x, y, "MouseUp");
    }
	public static void createMouseEvent(int x, int y, String eventType) {
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Event me = new Event();

		if (eventType.equals("MouseMove")) {
			me.x = x;
			me.y = y;
			if(fDownLocation != null) {
				me.stateMask |= SWT.BUTTON1;
				// GEF has a drag event, which we need
				// to imitate.
				// we will move the mouse 10 times, to reach
				// the final destination
				int xDownLocation = fDownLocation.x;
				int yDownLocation = fDownLocation.y;
				int xDelta = x - xDownLocation;
				int yDelta = y - yDownLocation;
				int xIncrement = xDelta / 10;
				int yIncrement = yDelta / 10;
				boolean crossedThreshold = false;
				int xIncrementRealValue = xIncrement;
				if(xIncrementRealValue < 0)
					xIncrementRealValue = xIncrementRealValue * -1;
				int yIncrementRealValue = yIncrement;
				if(yIncrementRealValue < 0)
					yIncrementRealValue = yIncrementRealValue * -1;
				boolean reverse = x < xDownLocation;
				if(yIncrementRealValue >= xIncrementRealValue)
					reverse = y < yDownLocation;
				while(!crossedThreshold) {
					xDownLocation = xDownLocation + xIncrement;
					yDownLocation = yDownLocation + yIncrement;
					me.x = xDownLocation;
					me.y = yDownLocation;
					if(xIncrementRealValue > yIncrementRealValue) {
						if(reverse) {
							// check that the current x < expected x
							if(xDownLocation < x)
								crossedThreshold = true;
						} else {
							if(xDownLocation > x)
								crossedThreshold = true;
						}
					} else {
						if(reverse) {
							// check that the current x < expected x
							if(yDownLocation < y)
								crossedThreshold = true;
						} else {
							if(yDownLocation > y)
								crossedThreshold = true;
						}
					}
					if(crossedThreshold) {
						// If we crossed the expected location, do not
						// send this location. Instead send the expected
						// location
						me.x = x;
						me.y = y;
					}
					ce.getCanvas().notifyListeners(SWT.MouseMove, me);
				}
			} else
				ce.getCanvas().notifyListeners(SWT.MouseMove, me);
		} else if (eventType.equals("MouseDown")) {
			// with GEF we must create a mouse move first,
			// this will set the proper source edit part
			// for any creation request
			createMouseEvent(x, y, "MouseMove"); // $NON-NLS-1$
			me.x = x;
			me.y = y;
			me.button = 1;
			fDownLocation = new Point(x, y);
			ce.getCanvas().notifyListeners(SWT.MouseDown, me);
		} else if (eventType.equals("MouseDownRetainSelection")) {
			me.x = x;
			me.y = y;
			me.button = 1;
			me.stateMask |= SWT.CTRL;
			ce.getCanvas().notifyListeners(SWT.MouseDown, me);
		} else if (eventType.equals("MouseContextDown")) {
			me.x = x;
			me.y = y;
			me.button = 3;
			ce.getCanvas().notifyListeners(SWT.MouseDown, me);
		} else if (eventType.equals("MouseUp")) {
			me.x = x;
			me.y = y;
			me.button = 1;
			fDownLocation = null;
			ce.getCanvas().notifyListeners(SWT.MouseUp, me);
			// allow any transaction to complete if started
			// this will allow graphical symbols to be fully
			// configured before a test proceeds
			waitForTransaction();
		} else if (eventType.equals("MouseDoubleClick")) {
			// with GEF we must create a mouse move first,
			// this will set the proper source edit part
			// for any creation request
			createMouseEvent(x, y, "MouseMove"); // $NON-NLS-1$
			createMouseEvent(x, y, "MouseDown"); // $NON-NLS-1$
			fDownLocation = null;
			me.x = x;
			me.y = y;
			me.button = 1;
			ce.getCanvas().notifyListeners(SWT.MouseDoubleClick, me);
		}
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;
	}
	
	public static void waitForTransaction() {
		try {
			Platform.getJobManager().join(TransactionManager.FAMILY_TRANSACTION, null);
		} catch (OperationCanceledException e) {
			// fail(e.getMessage());
		} catch (InterruptedException e) {
			// ignore
		}
	}
	
	public static Point convertToMouseCoor(Point center, Model_c mdl) {
	    GraphicalEditor editor = GraphicalEditor.getEditor(mdl);
	    double zoom = editor.getZoom();
	    org.eclipse.draw2d.geometry.Point viewportLocation = editor.getPersistedViewportLocation();
	    center.x = (int) (center.x * zoom);
	    center.y = (int) (center.y * zoom);
		return new Point(
			Math.round((center.x - viewportLocation.x)),
			Math.round((center.y - viewportLocation.y)));
	}
	
	public static IEditorPart getActiveEditor() {
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(activeEditor instanceof ModelEditor) {
			return ((ModelEditor) activeEditor).getActivePart();
		} else
			return activeEditor;
	}
	public static AbstractTool getTool(String toolName) {
		return ((GraphicalEditor) getActiveEditor()).getTool(toolName);
	}
	public static AbstractTool getTool(String toolSet, String toolName) {
		return ((GraphicalEditor) getActiveEditor()).getTool(toolSet, toolName);
		
	}
	public static void activateTool(AbstractTool tool) {
		((GraphicalEditor) getActiveEditor()).getDomain().setActiveTool(tool);
	}
	public static void deactivateTool(AbstractTool tool) {
		tool.deactivate();
	}
	
	
	// Model Explorer testing methods :
	public static void setView(ExplorerView ev)
    {
        explorer=ev;
    }


	public static void showModelExplorer()
    {
    	if(explorer == null)
    	{
    		IWorkbenchPage page =  showBridgePointPerspective();

    		// create the model explorer view 
    		try {
    			explorer = (ExplorerView)page.showView(
    					"com.mentor.nucleus.bp.ui.explorer.ExplorerView", null, 
    					IWorkbenchPage.VIEW_CREATE);
    			page.activate(explorer);
    		} catch (PartInitException e) {
    			ExplorerPlugin.logError("Could not show Model Explorer view", e);
    		}
    	}
    }
	public static IWorkbenchPage showBridgePointPerspective()
    {
        // show the BridgePoint perspective
        IWorkbench workbench = CorePlugin.getDefault().getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        try {
            return workbench.showPerspective(
                "com.mentor.nucleus.bp.core.perspective", window);
        } catch (WorkbenchException e) {
            CorePlugin.logError("Could not show BridgePoint perspective", e);
            return null;
        }
    }
	
	public static ExplorerView getView()
    {
    	return explorer;
    }

}
