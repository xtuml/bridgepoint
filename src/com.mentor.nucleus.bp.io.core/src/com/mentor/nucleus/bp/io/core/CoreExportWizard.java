//====================================================================
//
//File:      $RCSfile: CoreExportWizard.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2012/10/12 22:55:18 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================

package com.mentor.nucleus.bp.io.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.util.UIUtil;

// org.eclipse.jdt.internal.ui.jarpackager.JarPackageWizard was used
// as an example to create this wizard.

/**
 * This wizard exports the model in BP 6.1 format.
 * @see Wizard
 */
public abstract class CoreExportWizard extends Wizard implements IExportWizard {
	
	protected String m_windowTitle;
    protected Object m_domain;
    
	abstract protected CoreExportPage createExportPage();
	abstract protected CoreExportPage getExportPage();
	abstract protected CoreExport createExportProcessor() throws FileNotFoundException;

	/*
	 * (non-Javadoc)
	 * Method declared on IWizard.
	 */
	public void addPages() {
		super.addPages();
		CoreExportPage fExportPage = createExportPage();
		addPage(fExportPage);
	}
	
	protected void prepare(){
		Domain_c dom = (Domain_c)getDomain();
		IPersistenceHierarchyMetaData metaData = PersistenceManager.getHierarchyMetaData();
		iterateToLoad(metaData, dom);
	}
	
	private void iterateToLoad(IPersistenceHierarchyMetaData metaData, NonRootModelElement me){
		List children = metaData.getChildrenComponentRootModelElements(me);
		for (Object child : children) {
			iterateToLoad(metaData, (NonRootModelElement)child);
		}
	}
	
	/**
	 *  @see Wizard#performFinish
	 */
	public boolean performFinish()  
	{
        prepare();    
		// here's where the code is actually output
		IPath path = new Path(getExportPage().getDestinationFilePath());
		File fh = path.toFile();
		boolean writeFile = true;
		if (fh.exists() && getExportPage().getOverwriteWarning())
			writeFile = UIUtil.openQuestion(getShell(), "Confirm Replace", 
				"The file '" + getExportPage().getDestinationFilePath() +
				"' already exists. Do you want to overwrite it?", true );
            
		if ( writeFile ) {
			if ( ensureDirectoryExists(path.removeLastSegments(1).toFile()) )
			{
				try {
					try {
						new ProgressMonitorDialog(getShell()).run(true, false, 
							createExportProcessor());
					 } catch (InvocationTargetException e) {
						// handle exception
					 } catch (InterruptedException e) {
						fh.delete();
					 }
					return true;
				}
				catch( FileNotFoundException f )
				{
					UIUtil.openError(getShell(), "File Not Found", 
							"Unable to open file:\n\n" + f.getLocalizedMessage());
				}
			}
		}
		return false;
	}

	IWorkbench m_workbench;
	protected IStructuredSelection m_selection;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		m_workbench = workbench;
		m_selection = selection;
		setWindowTitle(m_windowTitle);
		setDefaultPageImageDescriptor(CorePlugin.getImageDescriptor("exportsql_wiz.gif"));//$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#getDialogSettings()
	 */
	public IDialogSettings getDialogSettings() {
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
	/**
	 * Attempts to ensure that the specified directory exists on the local file system.
	 * Answers a boolean indicating success.
	 *
	 * @return boolean
	 * @param directory java.io.File
	 */
	protected boolean ensureDirectoryExists(File directory) {
		if (directory.exists() && !directory.isDirectory()) {
			UIUtil.openError(getShell(), "Directory Error", 
				"Specified target exists, but is not a directory"); 
			return false;
		}

		if (!directory.exists()) {
			if ( !UIUtil.openQuestion(getShell(), "Question", 
					"Target directory does not exist. Would you like to create it?", true) )
				return false;

			if (!directory.mkdirs()) {
				UIUtil.openError(getShell(), "Error", 
					"Error creating directory.");
				return false;
			}
		}

		return true;
	}
}
