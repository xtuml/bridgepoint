//====================================================================
//
//File:      $RCSfile: CoreImportWizard.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2012/10/12 22:55:18 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================

package com.mentor.nucleus.bp.io.core;

import java.io.FileNotFoundException;

import org.eclipse.jface.dialogs.IDialogSettings;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.util.UIUtil;

/**
 * This wizard imports the model in whatever format the derived classes want.
 * @see Wizard
 */
public abstract class CoreImportWizard extends Wizard implements IImportWizard
{

	private static final int FILE_FORMAT_ERROR = 1;
	protected String m_windowTitle;
    protected Object m_domain;

	abstract protected CoreImportPage createImportPage(IStructuredSelection selection);
	abstract protected CoreImport createImportProcessor() throws FileNotFoundException;
	abstract protected boolean databaseClearRequired();

	/*
	* (non-Javadoc)
	* Method declared on IWizard.
	*/
	public void addPages()
	{
		super.addPages();
		CoreImportPage fImportPage = createImportPage(m_selection);
		addPage(fImportPage);
	}

	/**
	 *  @see Wizard#performFinish
	 */
	public boolean performFinish()
	{
			try
			{
				boolean doIt2 = true;
				CoreImport ci = createImportProcessor();
				int validate_result = ci.countAndValidateInsertStatements();
				if (validate_result == CoreImport.PPS_ERROR)
				{
					doIt2 = false;
				}
				else if (validate_result == CoreImport.PPS_ASK)
					{
					// warn about ignoring data
					doIt2 =
						UIUtil.openQuestion(
							getShell(),
							"Confirm Ignore",
							ci.m_errorMessage + " Do you want to continue?", true);
					if (doIt2)
					{
						ci.m_errorMessage = "";
					}
					else
					{
						// bail out early
						return false;
					}
				}
				if (doIt2)
				{
					Ooaofooa.m_display = Display.getCurrent();
					new ProgressMonitorDialog(getShell()).run(
						true,
						false,
						ci);
				}
				if (!ci.m_success)
				{
                    CorePlugin.showImportErrorMessage(true, ci.m_errorMessage);
				}
				return ci.m_success;
			}
			catch (Exception e) {
				CorePlugin.logError("Could not import model", e);
			}
		return false;
	}

	IWorkbench m_workbench;
	protected IStructuredSelection m_selection;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
		m_workbench = workbench;
		// create an empty selection
		m_selection = new StructuredSelection();
		setWindowTitle(m_windowTitle);
		setDefaultPageImageDescriptor(CorePlugin.getImageDescriptor("importsql_wiz.gif")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#getDialogSettings()
	 */
	public IDialogSettings getDialogSettings()
	{
		return CorePlugin.getDefault().getDialogSettings();
	}
	/**
	 * @return Returns the m_selection.
	 */
	public IStructuredSelection getSelection() {
		return m_selection;
	}
	/**
	 * @return Returns the m_domain.
	 */
	public Object getDomain() {
		return m_domain;
	}
	/**
	 * @param m_domain The m_domain to set.
	 * m_domain can either be a Domain_c instance or a DomainProxy instance
	 */
	public void setDomain(Object m_domain) {
		this.m_domain = m_domain;
	}

}
