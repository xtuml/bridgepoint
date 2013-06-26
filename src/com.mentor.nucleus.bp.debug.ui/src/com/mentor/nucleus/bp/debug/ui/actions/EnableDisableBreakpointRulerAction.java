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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.ITextEditor;

import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;

public class EnableDisableBreakpointRulerAction extends AbstractBreakpointRulerAction {

    private static final String ENABLE_BREAKPOINT = "&Enable Breakpoint";
	private static final String DISABLE_BREAKPOINT = "&Disable Breakpoint";

	/**
     * Creates the action to enable/disable breakpoints
     */
    public EnableDisableBreakpointRulerAction(ITextEditor editor, IVerticalRulerInfo info) {
        setInfo(info);
        setTextEditor(editor);
        setText(ENABLE_BREAKPOINT);
    }

    /**
     * @see Action#run()
     */
    public void run() {
        if (getBreakpoint() != null) {
            new Job("Enable/Disable") { //$NON-NLS-1$
                protected IStatus run(IProgressMonitor monitor) {
                    try {
                        getBreakpoint().setEnabled(!getBreakpoint().isEnabled());
                        return Status.OK_STATUS;
                    } catch (final CoreException e) {
                        Display.getDefault().asyncExec(new Runnable(){
                            public void run() {
                                UIUtil.openError(getTextEditor().getEditorSite().getShell(), "Enable/Disable breakpoint(s) failed", "Exceptions occurred enabling/disabling the breakpoint(s)"); //$NON-NLS-1$ //$NON-NLS-2$        
                            }
                        });                        
                    }
                    return Status.CANCEL_STATUS;
                }
            }.schedule();
        }
    }

    /**
     * @see IUpdate#update()
     */
    public void update() {
        setBreakpoint(determineBreakpoint());
        if (getBreakpoint() == null) {
            setEnabled(false);
            return;
        }
        setEnabled(true);
        try {
            boolean enabled = getBreakpoint().isEnabled();
            setText(enabled ? DISABLE_BREAKPOINT : ENABLE_BREAKPOINT );
        } catch (CoreException ce) {
            BPDebugUIPlugin.logError("Marker error", ce);
        }
    }
}
