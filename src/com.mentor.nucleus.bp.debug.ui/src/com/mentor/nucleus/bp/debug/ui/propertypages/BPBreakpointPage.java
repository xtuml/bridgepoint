//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package com.mentor.nucleus.bp.debug.ui.propertypages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.model.BPBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.IBPBreakpoint;

/**
 * Property page for configuring IJavaBreakpoints.
 */
public class BPBreakpointPage extends PropertyPage {
	
	protected Button fEnabledButton;
	protected Button fHitCountButton;
	protected Text fHitCountText;
	Button fEnableConditionButton;
	BreakpointConditionEditor fConditionEditor;
	Label fConditionLabel;
	
	protected List fErrorMessages= new ArrayList();
	
	/**
	 * Attribute used to indicate that a breakpoint should be deleted
	 * when cancel is pressed.
	 */
	public static final String ATTR_DELETE_ON_CANCEL = BPDebugUIPlugin.getUniqueIdentifier() + ".ATTR_DELETE_ON_CANCEL";  //$NON-NLS-1$
	
	private static final String fgHitCountErrorMessage= "Hit count must be a positive integer"; //$NON-NLS-1$
	
	/**
	 * Store the breakpoint properties.
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		IWorkspaceRunnable wr = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IBreakpoint breakpoint = (IBreakpoint) getBreakpoint();
				boolean delOnCancel = breakpoint.getMarker().getAttribute(ATTR_DELETE_ON_CANCEL) != null;
				if (delOnCancel) {
				    // if this breakpoint is being created, remove the "delete on cancel" attribute
				    // and register with the breakpoint manager
					breakpoint.getMarker().setAttribute(ATTR_DELETE_ON_CANCEL, (String)null);
					breakpoint.setRegistered(true);
				}
				doStore();
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(wr, null, 0, null);
		} catch (CoreException e) {
            UIUtil.openError(getShell(), "Error", "An exception occurred while saving breakpoint properties."); 
			BPDebugUIPlugin.logError("An exception occurred while saving breakpoint properties.", e);
		}
		return super.performOk();
	}
	
	/**
	 * Adds the given error message to the errors currently displayed on this page.
	 * The page displays the most recently added error message.
	 * Clients should retain messages that are passed into this method as the
	 * message should later be passed into removeErrorMessage(String) to clear the error.
	 * This method should be used instead of setErrorMessage(String).
	 * @param message the error message to display on this page.
	 */
	public void addErrorMessage(String message) {
		if (message == null) {
			return;
		}
		fErrorMessages.remove(message);
		fErrorMessages.add(message);
		setErrorMessage(message);
		setValid(false);
	}
	
	/**
	 * @deprecated Call addErrorMessage(String message) instead.
	 * @see org.eclipse.jface.dialogs.DialogPage#setErrorMessage(java.lang.String)
	 */
	public void setErrorMessage(String newMessage) {
		super.setErrorMessage(newMessage);
	}
	
	/**
	 * Removes the given error message from the errors currently displayed on this page.
	 * When an error message is removed, the page displays the error that was added
	 * before the given message. This is akin to popping the message from a stack.
	 * Clients should call this method instead of setErrorMessage(null).
	 * @param message the error message to clear
	 */
	public void removeErrorMessage(String message) {
		fErrorMessages.remove(message);
		if (fErrorMessages.isEmpty()) {
			setErrorMessage(null);
			setValid(true);
		} else {
			setErrorMessage((String) fErrorMessages.get(fErrorMessages.size() - 1));
		}
	}
	
	/**
	 * Stores the values configured in this page. This method
	 * should be called from within a workspace runnable to
	 * reduce the number of resource deltas.
	 */
	protected void doStore() throws CoreException {
		IBPBreakpoint breakpoint= getBreakpoint();
		storeHitCount(breakpoint);
		storeEnabled(breakpoint);
		storeCondition(breakpoint);
	}

	private void storeCondition(IBPBreakpoint breakpoint) throws CoreException {
		if (fConditionEditor != null) {
			boolean enableCondition= fEnableConditionButton.getSelection();
			String condition = fConditionEditor.getCondition();
			if (breakpoint.isConditionEnabled() != enableCondition) {
				breakpoint.setConditionEnabled(enableCondition);
			}
			if (!condition.equals(breakpoint.getCondition())) {
				breakpoint.setCondition(condition);
			}
		}
	}

	/**
	 * Stores the value of the enabled state in the breakpoint.
	 * @param breakpoint the breakpoint to update
	 * @throws CoreException if an exception occurs while setting
	 *  the enabled state
	 */
	private void storeEnabled(IBPBreakpoint breakpoint) throws CoreException {
		boolean enabled= fEnabledButton.getSelection();
		breakpoint.setEnabled(enabled);
	}

	/**
	 * Stores the value of the hit count in the breakpoint.
	 * @param breakpoint the breakpoint to update
	 * @throws CoreException if an exception occurs while setting
	 *  the hit count
	 */
	private void storeHitCount(IBPBreakpoint breakpoint) throws CoreException {
		if ( fHitCountButton != null ) {
			boolean hitCountEnabled= fHitCountButton.getSelection();
			int hitCount= -1;
			if (hitCountEnabled) {
				try {
					hitCount= Integer.parseInt(fHitCountText.getText());
				} catch (NumberFormatException e) {
					BPDebugUIPlugin.logError("BPBreakpointPage allowed input of invalid string for hit count value: " + fHitCountText.getText(), e); 
				}
			}
			breakpoint.setHitCount(hitCount);
		}
	}

	/**
	 * Creates the labels and editors displayed for the breakpoint.
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		noDefaultAndApplyButton();
		Composite mainComposite= createComposite(parent, 1);
		createLabels(mainComposite);
		try {
			createEnabledButton(mainComposite);
			createHitCountEditor(mainComposite);
			createTypeSpecificEditors(mainComposite);
		} catch (CoreException e) {
			BPDebugUIPlugin.logError("Error creating contents", e);
		}
		setValid(true);
		// if this breakpoint is being created, change the shell title to indicate 'creation'
		try {
            if (getBreakpoint().getMarker().getAttribute(ATTR_DELETE_ON_CANCEL) != null) {
            	getShell().addShellListener(new ShellListener() {
                    public void shellActivated(ShellEvent e) {
                        Shell shell = (Shell)e.getSource();
                        shell.setText("Create Breakpoint for " + getName((IBreakpoint)getBreakpoint())); //$NON-NLS-1$
                        shell.removeShellListener(this);
                    }
                    public void shellClosed(ShellEvent e) {
                    }
                    public void shellDeactivated(ShellEvent e) {
                    }
                    public void shellDeiconified(ShellEvent e) {
                    }
                    public void shellIconified(ShellEvent e) {
                    }
                });
            }
        } catch (CoreException e) {
        }
		return mainComposite;
	}
	
    /**
     * Returns the name of the given element.
     * 
     * @param element
     *            the element
     * @return the name of the element
     */
    private String getName(IAdaptable element) {
        IWorkbenchAdapter adapter = (IWorkbenchAdapter) element
                .getAdapter(IWorkbenchAdapter.class);
        if (adapter != null) {
            return adapter.getLabel(element);
        } 
        return "";//$NON-NLS-1$
    }	
	
	/**
	 * Creates the labels displayed for the breakpoint.
	 * @param parent
	 */
	protected void createLabels(Composite parent) {
		IBPBreakpoint breakpoint= (IBPBreakpoint) getElement();
		Composite labelComposite= createComposite(parent, 2);
		String typeName = null;
		String location = null;
		try {
			typeName = breakpoint.getTypeName();
			location = breakpoint.getLocation();
		} catch (CoreException e) {
			BPDebugUIPlugin.logError("Unable to get breakpoint type name or location", e); 
		}
		if (typeName != null && location != null) {
			createLabel(labelComposite, typeName); //$NON-NLS-1$
			createTypeSpecificLocation(labelComposite, location);
		}
		createTypeSpecificLabels(labelComposite);
	}

	protected void createTypeSpecificLocation(Composite parent, String location) {
		createLabel(parent, location);
	}

	/**
	 * @param parent the composite in which the hit count editor
	 * 		will be created
	 */
	private void createHitCountEditor(Composite parent) throws CoreException {
		IBPBreakpoint breakpoint= getBreakpoint();
		if ( breakpoint.supportsHitCount() ) {
			Composite hitCountComposite= createComposite(parent, 2);
			fHitCountButton= createCheckButton(hitCountComposite, "&Hit Count:");
			fHitCountButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					fHitCountText.setEnabled(fHitCountButton.getSelection());
					hitCountChanged();
				}
			});
			int hitCount= breakpoint.getHitCount();
			String hitCountString= ""; //$NON-NLS-1$
			if (hitCount > 0) {
				hitCountString= new Integer(hitCount).toString();
				fHitCountButton.setSelection(true);
			} else {
				fHitCountButton.setSelection(false);
			}
			fHitCountText= createText(hitCountComposite, hitCountString); //$NON-NLS-1$
			if (hitCount <= 0) {
				fHitCountText.setEnabled(false);
			}
			fHitCountText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					hitCountChanged();
				}
			});
		}
	}
	
	/**
	 * Validates the current state of the hit count editor.
	 * Hit count value must be a positive integer.
	 */
	private void hitCountChanged() {
		if (!fHitCountButton.getSelection()) {
			removeErrorMessage(fgHitCountErrorMessage);
			return;
		}
		String hitCountText= fHitCountText.getText();
		int hitCount= -1;
		try {
			hitCount= Integer.parseInt(hitCountText);
		} catch (NumberFormatException e1) {
			addErrorMessage(fgHitCountErrorMessage);
			return;
		}
		if (hitCount < 1) {
			addErrorMessage(fgHitCountErrorMessage);
		} else {
			if (fgHitCountErrorMessage.equals(getErrorMessage())) {
				removeErrorMessage(fgHitCountErrorMessage);
			}
		}
	}

	/**
	 * Creates the button to toggle enablement of the breakpoint
	 * @param parent
	 * @throws CoreException
	 */
	protected void createEnabledButton(Composite parent) throws CoreException {
		fEnabledButton= createCheckButton(parent, "&Enabled"); //$NON-NLS-1$
		fEnabledButton.setSelection(((IBreakpoint)getBreakpoint()).isEnabled());
	}
	
	/**
	 * Returns the breakpoint that this preference page configures
	 * @return the breakpoint this page configures
	 */
	protected IBPBreakpoint getBreakpoint() {
		return (IBPBreakpoint) getElement();
	}
	
	/**
	 * Allows subclasses to add type specific labels to the common BP
	 * breakpoint page.
	 * @param parent
	 */
	protected void createTypeSpecificLabels(Composite parent) {
		// Do nothing
	}
	/**
	* Allows subclasses to add type specific editors to the common BP
	* breakpoint page.
	* @param parent
	*/
   protected void createTypeSpecificEditors(Composite parent) throws CoreException {
   		// Do nothing
   }
	
	/**
	 * Creates a fully configured text editor with the given initial value
	 * @param parent
	 * @param initialValue
	 * @return the configured text editor
	 */
	protected Text createText(Composite parent, String initialValue) {
		Composite textComposite= new Composite(parent, SWT.NONE);
		GridLayout layout= new GridLayout();
		layout.numColumns= 2;
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		textComposite.setLayout(layout);
		textComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textComposite.setFont(parent.getFont());
		Text text= new Text(textComposite, SWT.SINGLE | SWT.BORDER);
		text.setText(initialValue);
		text.setFont(parent.getFont());
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}
	
	/**
	 * Creates a fully configured composite with the given number of columns
	 * @param parent
	 * @param numColumns
	 * @return the configured composite
	 */
	protected Composite createComposite(Composite parent, int numColumns) {
		Composite composit= new Composite(parent, SWT.NONE);
		composit.setFont(parent.getFont());
		GridLayout layout= new GridLayout();
		layout.numColumns= numColumns;
		layout.marginWidth= 0;
		layout.marginHeight= 0;
		composit.setLayout(layout);
		composit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return composit;
	}

	/**
	 * Creates a fully configured check button with the given text.
	 * @param parent the parent composite
	 * @param text the label of the returned check button
	 * @return a fully configured check button
	 */
	protected Button createCheckButton(Composite parent, String text) {
		Button button= new Button(parent, SWT.CHECK | SWT.LEFT);
		button.setText(text);
		button.setFont(parent.getFont());
		button.setLayoutData(new GridData());
		return button;
	}

	/**
	 * Creates a fully configured label with the given text.
	 * @param parent the parent composite
	 * @param text the test of the returned label
	 * @return a fully configured label
	 */
	protected Label createLabel(Composite parent, String text) {
		Label label= new Label(parent, SWT.NONE);
		label.setText(text);
		label.setFont(parent.getFont());
		label.setLayoutData(new GridData());
		return label;
	}

	/**
	 * Creates a fully configured radio button with the given text.
	 * @param parent the parent composite
	 * @param text the label of the returned radio button
	 * @return a fully configured radio button
	 */
	protected Button createRadioButton(Composite parent, String text) {
		Button button= new Button(parent, SWT.RADIO | SWT.LEFT);
		button.setText(text);
		button.setFont(parent.getFont());
		button.setLayoutData(new GridData());
		return button;
	}
	
	/**
	 * Check to see if the breakpoint should be deleted.
	 */
	public boolean performCancel() {
		try {
			IBreakpoint breakpoint = (IBreakpoint) getBreakpoint();
			if (breakpoint.getMarker().getAttribute(ATTR_DELETE_ON_CANCEL) != null) {
			    // if this breakpoint is being created, delete on cancel
				breakpoint.delete();
			}
		} catch (CoreException e) {
            UIUtil.openError(getShell(), "Error", "Unable to cancel breakpoint creation"); 
		}
		return super.performCancel();
	}

	/**
	 * Creates the controls that allow the user to specify the breakpoint's
	 * condition
	 * @param parent the composite in which the condition editor should be created
	 * @throws CoreException if an exception occurs accessing the breakpoint
	 */
	protected void createConditionEditor(Composite parent) throws CoreException {
		BPBreakpoint breakpoint = (BPBreakpoint) getBreakpoint();
		String label = "E&nable Condition Expression";
		Group conditionGroup= new Group(parent, SWT.NONE);
		conditionGroup.setFont(parent.getFont());
		conditionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		conditionGroup.setLayout(new GridLayout());
		conditionGroup.setText("Condition");
		fEnableConditionButton= createCheckButton(conditionGroup, label);
		fEnableConditionButton.setSelection(breakpoint.isConditionEnabled());
		fEnableConditionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setConditionEnabled(fEnableConditionButton.getSelection());
			}
		});
		fConditionLabel = createLabel(conditionGroup, "Condition Expression");
		fConditionEditor = new BreakpointConditionEditor(conditionGroup, this);
		setConditionEnabled(fEnableConditionButton.getSelection());
	}

	/**
	 * Sets the enabled state of the condition editing controls.
	 * @param enabled
	 */
	void setConditionEnabled(boolean enabled) {
		fConditionLabel.setEnabled(enabled);
		fConditionEditor.setEnabled(enabled);
	}

	/**
	 * Overridden here to increase visibility
	 * @see org.eclipse.jface.dialogs.DialogPage#convertHeightInCharsToPixels(int)
	 */
	public int convertHeightInCharsToPixels(int chars) {
		return super.convertHeightInCharsToPixels(chars);
	}
	
	/**
	 * Overridden here to increase visibility
	 * @see org.eclipse.jface.dialogs.DialogPage#convertWidthInCharsToPixels(int)
	 */
	public int convertWidthInCharsToPixels(int chars) {
		return super.convertWidthInCharsToPixels(chars);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
	 */
	public void dispose() {
		if (fConditionEditor != null) {
			fConditionEditor.dispose();
		}
		super.dispose();
	}

	
}
