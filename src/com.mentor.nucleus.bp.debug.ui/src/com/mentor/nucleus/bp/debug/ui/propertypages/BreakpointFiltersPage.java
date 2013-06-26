//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.debug.ui.propertypages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.debug.ui.model.BPBreakpoint;
import com.mentor.nucleus.bp.debug.ui.model.BPLineBreakpoint;

/**
 * 
 */
public class BreakpointFiltersPage extends PropertyPage {

	InstanceFilterEditor fInstanceFilterEditor;

	/**
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		doStore();
		return super.performOk();
	}

	/**
	 * Stores the values configured in this page.
	 */
	protected void doStore() {
		if (fInstanceFilterEditor != null) {
			fInstanceFilterEditor.doStore();
		}
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		noDefaultAndApplyButton();
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setFont(parent.getFont());
		mainComposite.setLayout(new GridLayout());
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		createTypeSpecificEditors(mainComposite);
		createInstanceFilterEditor(mainComposite);
		setValid(true);
		return mainComposite;
	}
	
	public void createInstanceFilterEditor(Composite parent) {
		BPBreakpoint breakpoint= getBreakpoint();
		ModelClass_c[] instances = breakpoint.getAllClasses();
		if (instances.length > 0) {
			fInstanceFilterEditor= new InstanceFilterEditor(parent, breakpoint);
		}
	}

	/**
	 * Allow subclasses to create type-specific editors.
	 * @param parent
	 */
	protected void createTypeSpecificEditors(Composite parent) {
		// Do nothing.
	}

	public BPBreakpoint getBreakpoint() {
		return (BPBreakpoint) getElement();
	}

}
