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

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;

import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.model.VerifierExceptionBreakpoint;

/**
 * 
 */
public class VerifierExceptionBreakpointPage extends PropertyPage {

	private TableViewer fBreakpointViewer;
	private VerifierExceptionContentProvider fContentProvider;
	
	/**
	 * Store the breakpoint properties.
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		IWorkspaceRunnable wr = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
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
	 * @see org.eclipse.jdt.internal.debug.ui.propertypages.JavaBreakpointPage#doStore()
	 */
	protected void doStore() throws CoreException {
		IStructuredSelection sel = (IStructuredSelection)fBreakpointViewer.getSelection();
		Object [] items = sel.toArray();
		for ( int i = 0; i < items.length; ++i ) {
			int typeId = VerifierExceptionBreakpoint.getBPTypeId((String)items[i]);
			VerifierExceptionBreakpoint breakpoint= new VerifierExceptionBreakpoint(typeId);
			DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(breakpoint);
		}						
	}

	protected Control createContents(Composite parent) {
		noDefaultAndApplyButton();
		Composite mainComposite= createComposite(parent, 1);
		createTypeSpecificEditors(mainComposite);
		setValid(true);
		return mainComposite;
	}

	/**
	 * @see org.eclipse.jdt.internal.debug.ui.propertypages.JavaBreakpointPage#createTypeSpecificEditors(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificEditors(Composite parent) {
		createLabel(parent, "Choose exception breakpoints to create");
		createViewer(parent);
		
	}
	/**
	 * Create and initialize the thread filter tree viewer.
	 */
	protected void createViewer(Composite parent) {
		GridData data= new GridData(GridData.FILL_BOTH);
		data.heightHint= 100;

		fContentProvider= new VerifierExceptionContentProvider();
		
		fBreakpointViewer= new TableViewer(parent, SWT.MULTI | SWT.BORDER);
		fBreakpointViewer.getTable().setLayoutData(data);
		fBreakpointViewer.setContentProvider(fContentProvider);
		fBreakpointViewer.setInput(getElement());
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

	class VerifierExceptionContentProvider implements ITreeContentProvider {

		public void dispose() {
			// nothing to do
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// don't care about the input
		}

		public Object[] getChildren(Object parentElement) {
			return new Object[0];
		}

		public Object getParent(Object element) {
			return null;
		}

		public boolean hasChildren(Object element) {
			return false;
		}

		public Object[] getElements(Object inputElement) {
			int [] avail = VerifierExceptionBreakpoint.getAvailableTypes();
			Object [] result = new Object[avail.length];
			int next = 0;
			for ( int i = 0; i < avail.length; ++i ) {
				result[next++] = VerifierExceptionBreakpoint.getTypeLabel(avail[i]);
			}		
			return result;
		}
	
	}


}
