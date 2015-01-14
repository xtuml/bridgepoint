//=====================================================================
//
//File:      $RCSfile: UIUtil.java,v $
//Version:   $Revision: 1.29 $
//Modified:  $Date: 2013/06/12 13:08:18 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.properties.PropertySheet;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.core.ui.dialogs.ScrolledTextDialog;

/**
 * Utility methods related to this product's UI.
 */
public class UIUtil
{
	private static boolean displayYesNoQuestion_isYes = true;
    private static volatile boolean booleanDialogResult = false;
    
	
    /**
     * 
     * @param element null to refresh whole tree
     */
	public static void refresh(final Object element) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				StructuredViewer viewer = null;
				Class<?> explorerViewClass = null;
				Object elementToRefresh = element;

				try {
					Bundle ui_explorer = Platform
							.getBundle("com.mentor.nucleus.bp.ui.explorer");//$NON-NLS-1$
					explorerViewClass = ui_explorer
							.loadClass("com.mentor.nucleus.bp.ui.explorer.ExplorerView"); //$NON-NLS-1$
				} catch (Exception cnf) {
					CorePlugin.logError(
							"Problem accessing GraphicsUtil class ", cnf); //$NON-NLS-1$
					return;
				}

				Class<?>[] type;
				try {
					type = new Class<?>[] {};

					Method getExplorerTreeViewer = explorerViewClass.getMethod(
							"getExplorerTreeViewer", type); //$NON-NLS-1$
					Object[] args = new Object[] {};
					viewer = (StructuredViewer) getExplorerTreeViewer.invoke(
							explorerViewClass, args);
				} catch (Exception e) {
					CorePlugin
							.logError(
									"Error invoking getCanvasEditorTitle(NRME) in GraphicsUtil  ", e); //$NON-NLS-1$
				}
				if (element instanceof IProject) {
					elementToRefresh = SystemModel_c.SystemModelInstance(
							Ooaofooa.getDefaultInstance(),
							new ClassQueryInterface_c() {
								public boolean evaluate(Object c) {
									return ((SystemModel_c) c).getName()
											.equals(
													((IProject) element)
															.getName());
								}

							}, false);
				}
				refreshViewer(viewer, elementToRefresh);
				IEditorReference[] editorReferences = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getEditorReferences();
				for (IEditorReference reference : editorReferences) {
					IEditorPart editor = reference.getEditor(false);
					if (editor != null) {
						Method method = null;
						try {
							method = editor.getClass().getMethod(
									"refresh", new Class[0]); //$NON-NLS-1$
						} catch (SecurityException e) {
							CorePlugin.logError("Unable to locate method for refreshing graphical editors.", e);
						} catch (NoSuchMethodException e) {
							// most editors will not have this method, for those we do not
							// want to refresh anyway
						}
						if (method != null) {
							try {
								method.invoke(editor, new Object[0]);
							} catch (IllegalArgumentException e) {
								CorePlugin.logError("Unable to invoke refresh method for graphical editor.", e);
							} catch (IllegalAccessException e) {
								CorePlugin.logError("Unable to invoke refresh method for graphical editor.", e);
							} catch (InvocationTargetException e) {
								CorePlugin.logError("Unable to invoke refresh method for graphical editor.", e);
							}
						}
					}
				}
			}
		});
	}
    public static void refreshViewer(final StructuredViewer viewer) 
    {
        refreshViewer(viewer, null);
    }

    /**
     * Performs a refresh of the given viewer, on the UI thread,
     * starting with the given element.  If no element is given,
     * the refresh starts with the viewer's root element.
     */
    private static HashMap<StructuredViewer, ArrayList<Object>> updateList =
                             new HashMap<StructuredViewer, ArrayList<Object>>();
    
    public static void refreshViewer(final StructuredViewer viewer,
        final Object element) 
    {
		Object target = element;
		if (viewer == null)
			return;
		// if the given viewer hasn't been disposed
		Control control = viewer.getControl();
		if (!control.isDisposed()) {
			if (target instanceof NonRootModelElement) {
				IFile f = ((NonRootModelElement) target).getFile();
				if (f == null || !f.getProject().isOpen()) {
					target = null;
				}
			} else if (target instanceof Ooaofooa) {
				IFile f = ((Ooaofooa) target).getFile();
				if (f == null || !f.getProject().isOpen()) {
					refresh(viewer, null);
				}
			}
			boolean queueRefresh = true;
			if (updateList.containsKey(viewer)) {
			  // if the element is already scheduled for a refresh or if the whole
			  // tree is going to be refreshed, don't repeat the refresh
			  if(updateList.get(viewer).contains(target) || updateList.get(viewer).contains(null)) {
			    queueRefresh = false;
			  }
		  }
	    if (queueRefresh) {
	      synchronized(updateList) {
	            Display display = control.getDisplay();
	            // ask the viewer's UI thread to perform the refresh
	            final Object f_target = target;
	            display.asyncExec(
	               new Runnable() {
	                   public void run() {
                       updateList.get(viewer).remove(f_target);
	                     refresh(viewer, f_target);
	                   }
	            });
	            if (!updateList.containsKey(viewer)) {
	              updateList.put(viewer, new ArrayList<Object>());
	            }
	            updateList.get(viewer).add(f_target);
	      }
	    }
	            
	  }
    }
    
    /**
     * Makes the appropriate call to refresh() on the given viewer, depending on
     * whether the given element is non-null.
     */
    private static void refresh(final StructuredViewer viewer,
        final Object element)
    {
    	WorkspaceJob job = new WorkspaceJob("Refreshing views") {
			
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					
					@Override
					public void run() {
				        if(!viewer.getControl().isDisposed()){
				            if (element != null) viewer.refresh(element);
				            else viewer.refresh();
				        }	
					}
				});
		        return Status.OK_STATUS;
			}
		};
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
    }

    /**
     * Makes a call to the given control's redraw() method, ensuring
     * that the call occurs on the UI thread.  If the calling thread
     * is not the UI thread, the UI thread is asked to make the call. 
     */
    public static void redrawControl(final Control control) 
    {
        // if the given control has been disposed, there is nothing to do
        if (control.isDisposed()) return;
        
        // if we're running on the UI thread
        Display display = control.getDisplay();
        if (Display.getCurrent() == display) {
            // make the redraw call directly
            control.redraw();
        }

        // otherwise
        else {
            // ask the UI thread to make the redraw call
            display.asyncExec(
                new Runnable() {
                    public void run() {
                        control.redraw();
                    }
                });
        }
    }
    
    public static Menu getMenuForTreeItem(TreeViewer viewer, TreeItem item) {
        Tree sevTree = viewer.getTree();
        viewer.setSelection(new StructuredSelection(item.getData()));
        sevTree.setSelection(item);
        UIUtil.dispatchAll();

        Menu menu = viewer.getTree().getMenu();
        return menu;
    }
    
    /**
     * Causes the context menu for the given control to be populated,
     * by temporarily having it displayed.
     */
    public static void getContextMenuPopulated(Control control)
    {
        // have the menu closed a short while after it's displayed, below,
        // once it's very likely to have been fully populated
        final Menu menu = control.getMenu();
        Display display = control.getDisplay();
        display.timerExec(10, 
            new Runnable() {
                public void run() {
                    menu.setVisible(false);
				}
            });
            
        // display the context menu to get it populated 
        menu.setVisible(true);
        while (display.readAndDispatch());
    }
    
    /**
     * Is a shorthand method for the enclosed code.
     */
    public static void dispatchAll()
    {
    	if (PlatformUI.isWorkbenchRunning()) {   // If we are running in "truly headless" more there is no workbench 
	    	if (Display.getCurrent() != null) {
	          while (Display.getCurrent().readAndDispatch());
	    	}
    	}
    }
    
    
    /**
     * Returns the tree control displayed for the currently active properties
     * sheet page.  This method requires that the properties view is currently 
     * visible within the workbench. 
     */
    public static Tree getPropertyTree()
    {
        PropertySheet sheet = (PropertySheet)PlatformUI.getWorkbench().
            getActiveWorkbenchWindow().getActivePage().findView(
            IPageLayout.ID_PROP_SHEET);
        return (Tree)sheet.getCurrentPage().getControl();
    }    

    private static void outputTextForheadlessRun(BPMessageTypes type, String title, String text, String value) {    	
    	String outputString = type.getText() + ": " + title + ". " + text;
    	if (!value.isEmpty()) {
    		outputString += ".  Value:  " + value;
    	}
		CorePlugin.out.println(outputString);    	
    }
    
    /**
     * Opens a scrollable dialog with the given data
     */
	public static boolean openScrollableTextDialog(Shell parentShell,
			boolean allowCancel, String title, String textContents,
			String message, String optionalText, String preferenceKey,
			boolean defaultReturn) {
		if (CoreUtil.IsRunningHeadless) {
			outputTextForheadlessRun(BPMessageTypes.QUESTION, title, message, String.valueOf(defaultReturn));
		} else {
    		ScrolledTextDialog dialog = new ScrolledTextDialog(parentShell, allowCancel, title, textContents, message, optionalText, preferenceKey);
    		int result = dialog.open();
    		if(result == Window.OK) {
    			return true;
    		} else {
    			return false;
    		}
    	}
    	return defaultReturn;
    }
    
    /**
     * Shows an error dialog with the given title and message.
     */
    public static void showErrorDialog(String title, String message)
    {
    	IWorkbenchWindow activeWindow = null;
    	Shell parentShell = null;
    	if (!CoreUtil.IsRunningHeadless) {
    		activeWindow = CorePlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
    	}
    	if (activeWindow != null) {
    		parentShell = activeWindow.getShell();
    	}
		openError(parentShell, title, message);
    }
    
    public enum BPMessageTypes {
    	INFORMATION("Information"), WARNING("Warning"), ERROR("Error"), CONFIRM("Confirm"), QUESTION("Question"), INPUT("Input");
    	
    	private String description;
    	BPMessageTypes(String p_description) {
    		description = p_description;
    	}
    	public String getText() {
    		return description;
    	}
    };

    public static void openError(Shell parent, String title, final String message) {
    	openError(parent, title, message, false );
    }
    
    public static void openError(Shell parent, String title, final String message, boolean showTechSupportContactInfo) {
    	internalDialogHandler(parent, title, message, BPMessageTypes.ERROR, showTechSupportContactInfo );
    }

    public static void openErrorWithContactInfo(final String message) {
    	openError(null, null, message, true );
    }

    public static void openError(final String message) {
    	openError(null, null, message, false );
    }

    public static void displayError(final String message) {
    	openError(message);
    }
    public static void openWarning(Shell parent, String title, final String message) {
    	internalDialogHandler(parent, title, message, BPMessageTypes.WARNING, false);
    }
    
    public static void openWarning(final String message) {
    	openWarning(null, null, message);
    }
    
    public static void displayWarning(final String message) {
    	openWarning(message);
    }
    
    public static void openInformation(Shell parent, String title, final String message) {
    	internalDialogHandler(parent, title, message, BPMessageTypes.INFORMATION, false);
    }
    
    public static void openInformation(final String message) {
    	openInformation(null, null, message);
    }
    
    public static boolean  openConfirm(Shell parent, String title, final String message, boolean defaultValue) {
    	booleanDialogResult = defaultValue;
    	return internalDialogHandler(parent, title, message, BPMessageTypes.CONFIRM, false);
    }
    
    public static boolean openConfirm(final String message, boolean defaultValue) {
    	return openConfirm(null, null, message, defaultValue);
    }

    public static boolean  openQuestion(Shell parent, String title, final String message, boolean defaultValue) {
    	booleanDialogResult = defaultValue;
    	return internalDialogHandler(parent, title, message, BPMessageTypes.QUESTION, false);
    }
    
    public static boolean openQuestion(final String message, boolean defaultValue) {
    	return openQuestion(null, null, message, defaultValue);
    }
    
    public static void showMessageDialoginLaunch(Shell p_parent, String p_title,
			String p_msg, BPMessageTypes p_type) {
    	internalDialogHandler(p_parent, p_title, p_msg, p_type, false);
    }
    
    /**
     * Note this "MessageDialog" currently only supports an array of 2 dialog button
     * labels.   This is why the result is a boolean.  It simply behaves in
     * a similar manner as if a yes/no question had been asked (offset 0 is
     * yes and offset 1 is no).   If additional buttons are ever needed this
     * will need to be modified to handle it.
     */
	public static boolean openMessageDialog(Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, BPMessageTypes dialogType,
			String[] dialogButtonLabels, int defaultIndex) {
		int standardDialogType = MessageDialog.WARNING;
		if (dialogType == BPMessageTypes.ERROR) {
			standardDialogType = MessageDialog.ERROR;
		} else if ((dialogType == BPMessageTypes.INFORMATION)) {
			standardDialogType = MessageDialog.INFORMATION;
		}
		boolean  result = (defaultIndex == MessageDialog.OK);
		if (CoreUtil.IsRunningHeadless) {
			outputTextForheadlessRun(dialogType, dialogTitle, dialogMessage, "");
		} else {
			MessageDialog dialog = new MessageDialog(parentShell, dialogTitle,
					dialogTitleImage, dialogMessage, standardDialogType,
					dialogButtonLabels, defaultIndex);
			dialog.setBlockOnOpen(true);
			int actualResult = dialog.open();				
			result = MessageDialog.OK == actualResult;			
		} 
		return result;
    }
     
    private static class BPMessageDialog implements Runnable {
    	Shell m_parent;
    	final String m_title;
    	final String m_msg;
    	final BPMessageTypes m_type;
    	
    	BPMessageDialog(Shell p_parent, final String p_title,
    			final String p_msg, final BPMessageTypes p_type) {
    		m_parent = p_parent;
    		m_title = p_title;
			m_msg = p_msg;
			m_type = p_type;
    	}
    	
        public void run() {
        	boolean logTheMessage = false;
        	if (m_type == BPMessageTypes.ERROR) {
            	logTheMessage = true;        		
        	}
        	
        	if (m_parent == null && !CoreUtil.IsRunningHeadless) {
        		IWorkbenchWindow activeWBWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        		Shell parentShell = null;
        		if (activeWBWindow != null) {
        			m_parent = activeWBWindow.getShell();
        		}
        	}
        	
        	if (CoreUtil.IsRunningHeadless) {
        		// if headless there shouldn't be a parent shell, but this is just to be sure
        		m_parent = null;
        	}
        	
        	if (m_parent != null && !CoreUtil.IsRunningHeadless) {
	            if ( m_type == BPMessageTypes.INFORMATION ) {
	                org.eclipse.jface.dialogs.MessageDialog.openInformation(
	                	m_parent,
	                    m_title,
	                    m_msg
	                    );                    
	            } else if (m_type == BPMessageTypes.WARNING ) {
	                org.eclipse.jface.dialogs.MessageDialog
	                    .openWarning(
	                        m_parent,
	                        m_title,
	                        m_msg
	                        );                    
	            } else if ( m_type == BPMessageTypes.ERROR ) {
	                org.eclipse.jface.dialogs.MessageDialog
	                .openError(
	                        m_parent,
	                        m_title,
	                        m_msg
	                        );                	
	            } else if ( m_type == BPMessageTypes.CONFIRM ) {
	            	booleanDialogResult = org.eclipse.jface.dialogs.MessageDialog
	                .openConfirm(
	                        m_parent,
	                        m_title,
	                        m_msg
	                        );                	
	            } else if ( m_type == BPMessageTypes.QUESTION ) {
	            	booleanDialogResult = org.eclipse.jface.dialogs.MessageDialog
	                .openQuestion(
	                        m_parent,
	                        m_title,
	                        m_msg
	                        );                	
	            }
        	} else if (CoreUtil.IsRunningHeadless) {
    			outputTextForheadlessRun(m_type, m_title, m_msg, "");
        	} else{
        		// No Active shell is available for a UI dialog.
        		logTheMessage = true;
        	}
        	if (logTheMessage) {
        		CorePlugin.logError(m_title + "\n" + m_msg,
						null);
        	}
        }    	
    }
    
	private static boolean internalDialogHandler(Shell p_parent, String p_title,
			String p_msg, BPMessageTypes p_type,
			boolean p_showTechSupportContact) {
    	
    	booleanDialogResult = false;
    	p_title = (p_title == null) ? "BridgePoint UML Suite" : p_title;
        if (p_showTechSupportContact) {
        	p_msg = p_msg + "\n\n" + UIUtil.getTechSupportMessage();
        }
        
        if (CoreUtil.IsRunningHeadless) {
        	p_parent = null;
        }
        
    	Runnable dialog = new BPMessageDialog(p_parent, p_title, p_msg, p_type);
    	if (p_parent!= null || CoreUtil.IsRunningHeadless) {
    		dialog.run();
    	} else {
    		PlatformUI.getWorkbench().getDisplay().syncExec(dialog);
    	}
    	return booleanDialogResult;
    }
    
    public static String getTechSupportMessage() {
    	String msg =
          "Please visit the xtUML Forum for a searchable knowledgebase of technical issues,"
        + "the ability to open a service request online, and many other useful tools:\n\n"
        + "\thttps://www.xtuml.org/community/forum/xtuml-forum/\n";
    	return msg;
	}
    
    /**
     * 
     * @param msg
     * @return true if the user responds yes and false if the user responds no
     */
    public static boolean displayYesNoQuestion(final String msg) {
    	displayYesNoQuestion_isYes = true;
    
    	if (CoreUtil.IsRunningHeadless) {
    		displayYesNoQuestion_isYes = UIUtil.openMessageDialog(null,
				"BridgePoint UML Suite", null, msg,
				UIUtil.BPMessageTypes.QUESTION,
				new String[] { "Yes", "No" }, 0);  // yes is he default
    	} else {
	        PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
	
	            public void run() {
	                org.eclipse.swt.widgets.Shell sh = PlatformUI.getWorkbench()
	                        .getDisplay().getActiveShell();
	                MessageDialog dialog = new MessageDialog(
	                        sh, "BridgePoint UML Suite", null,
	                        msg,
	                        MessageDialog.QUESTION,
	                        new String[] {"Yes", "No"},
	                        0); // yes is the default
	                
	                displayYesNoQuestion_isYes = (dialog.open() == 0);
	            }
	        });
    	}
        return displayYesNoQuestion_isYes;
    }

    /**
     * Mirror the functionality of the jface InputDialog.  If the user cancels
     * then dialog then false is returned.  If the users select OK, then 
     * true is returned, and the text entered is found in 
     * UIUtil.inputDialogResult.
     */
    public static String inputDialogResult = "";
    public static boolean inputDialog(Shell parentShell, String dialogTitle,
            String dialogMessage, String initialValue, IInputValidator validator) {
    	inputDialogResult = "";
    	if(CoreUtil.IsRunningHeadless) {
    		outputTextForheadlessRun(BPMessageTypes.INPUT, dialogTitle, dialogMessage, initialValue);
    	} else {
	    	InputDialog id = new InputDialog(parentShell, dialogTitle,
	    			dialogMessage, initialValue, validator);
	  	    int result =  id.open();    	
	  	    id.getValue();
	  	    if (result == InputDialog.OK) {
	  	    	inputDialogResult = id.getValue();
	  	    	return true;
	  	    } else {
	  	    	return false;
	  	    }
    	}
    	return false;
    }
    
	/**
	 * A simple utility function that passes-along call to class RenameAction.
	 * 
	 * Returns null if the given name is valid for the given model element.
	 * Otherwise, returns a message stating why the name is invalid.
	 */
	public static String validateNameUsingRenameAction(String name, ModelElement element) {
		String result = RenameAction.isNameValid(name, element);
		if(result == null) {
	    	// if the element adapts to a file then we
	    	// must validate with the OS
	    	if(element instanceof NonRootModelElement) {
	    		NonRootModelElement nrme = (NonRootModelElement) element;
	    		if(PersistenceManager.getHierarchyMetaData().isComponentRoot(nrme)) {
                    IStatus validateName = RenameAction.validateName(nrme, name, IResource.FILE);
                    if(!validateName.isOK()) {
                        result = validateName.getMessage();
                    }
                }
	    	}
		}
		if(result != null && result.equals("")) {
			return null;
		}
		return result;
	}
	
	static class NameInputValidator implements IInputValidator {

		private ModelElement element;

		public NameInputValidator(ModelElement element) {
			this.element = element;
		}
		
		@Override
		public String isValid(String newText) {
			return validateNameUsingRenameAction(newText, element);
		}
		
	}
	
	public static IInputValidator newRenameValidator(ModelElement element) {
		return new NameInputValidator(element);
	}

	public static Object[] openSelectionDialog(Object[] objects, String message, String title) {
		if(CoreUtil.IsRunningHeadless) {
			return new Object[0];
		}
		UIUtil ui = new UIUtil();
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				new WorkbenchLabelProvider(), ui.new ProjectContentProvider(
						objects));
		dialog.setHelpAvailable(false);
		dialog.setInput(new Object());
		dialog.setMessage(message);
		dialog.setTitle(title);
		dialog.setAllowMultiple(true);
		dialog.setValidator(ui.new SelectionDialogValidator());
		
		int result = dialog.open();
		if (result == Dialog.OK) {
			return dialog.getResult();
		}
		return new Object[0];
	}

	class SelectionDialogValidator implements ISelectionStatusValidator {
		@Override
		public IStatus validate(Object[] selection) {
			if (selection.length == 0) {
				return new Status(IStatus.ERROR, CorePlugin.getName(), "");
			} else {
				return Status.CANCEL_STATUS;
			}
		}
	}
	
	class ProjectContentProvider implements ITreeContentProvider {
		Object[] projects;

		public ProjectContentProvider(Object[] projects) {
			this.projects = projects;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof IProject) {
				return new Object[0];
			} else {
				return projects;
			}
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof IProject) {
				return new Object[0];
			} else {
				return projects;
			}
		}

		@Override
		public void dispose() {
			// nothing to do
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// nothing to do
		}

	}
}
