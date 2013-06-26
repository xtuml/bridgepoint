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
package com.mentor.nucleus.bp.debug.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;

import com.mentor.nucleus.bp.debug.ui.model.BPLineBreakpoint;


/**
 * Presents the standard properties dialog to configure
 * the attibutes of a Java Breakpoint from the ruler popup menu of a 
 * text editor.
 */
public class OALBreakpointPropertiesRulerAction extends AbstractBreakpointRulerAction {

	private static final String BREAKPOINT_PROPERTIES = "Breakpoint &Properties...";

	/**
	 * Creates the action to enable/disable breakpoints
	 */
	public OALBreakpointPropertiesRulerAction(ITextEditor editor, IVerticalRulerInfo info) {
		setInfo(info);
		setTextEditor(editor);
		setText(BREAKPOINT_PROPERTIES);
	}
	/**
	 * @see Action#run()
	 */
	public void run() {
		if (getBreakpoint() != null) {
			PropertyDialogAction action= 
				new PropertyDialogAction(getTextEditor().getEditorSite(), new ISelectionProvider() {
					public void addSelectionChangedListener(ISelectionChangedListener listener) {
					}
					public ISelection getSelection() {
						return new StructuredSelection(getBreakpoint());
					}
					public void removeSelectionChangedListener(ISelectionChangedListener listener) {
					}
					public void setSelection(ISelection selection) {
					}
				});
			action.run();	
		}
	}
	
	/**
	 * @see IUpdate#update()
	 */
	public void update() {
		setBreakpoint(determineBreakpoint());
		if (getBreakpoint() == null || !(getBreakpoint() instanceof BPLineBreakpoint)) {
			setBreakpoint(null);
			setEnabled(false);
			return;
		}
		setEnabled(true);
	}
}
