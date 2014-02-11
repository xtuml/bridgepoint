//========================================================================
//
//File:      $RCSfile: DelegatingWizard.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2012/01/23 21:28:00 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import com.mentor.nucleus.bp.core.CorePlugin;

public abstract class DelegatingWizard extends Wizard implements INewWizard {

  private WizardDelegate delegateWizard = new WizardDelegate(
    getExtensionPoint(),
    "");   //$NON-NLS-1$

  // Reference to the pages provided by this wizard
  private IWorkbench m_workbench = null;
  private IStructuredSelection m_selection = null;
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
   */
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    m_workbench = workbench;
    m_selection = selection;
  }
  /*
   * get the extension point this wizard should obtain its delegate from
   */
  public abstract String getExtensionPoint();

  public void setDelegatingWizard(String extensionName, String delegateName) {
    delegateWizard = new WizardDelegate(extensionName, delegateName);
  }

  public WizardDelegate getDelegatingWizard() {
    return delegateWizard;
  }
  public void addPages() {
    super.addPages();
    delegateWizard.addPages();
  }
  public boolean canFinish() {
    boolean result = super.canFinish();
    if (result) {
      return delegateWizard.canFinish();
    }
    return result;
  }
  /**
   * Returns the successor of the given page.
   * <p>
   * This method is typically called by a wizard page
   * </p>
   *
   * @param page the page
   * @return the next page, or <code>null</code> if none
   */
  public IWizardPage getNextPage(IWizardPage page) {
    IWizardPage next = null;
    if (isActive(page)) {
      next = super.getNextPage(page);
      if (next == null) {
        //
        // We are at the last page of this wizard
        // start the next wizard at its first page.
        //
        next = delegateWizard.getStartingPage();
      }
    }
    else {
      next = delegateWizard.getNextPage(page);
    }
    if (next != null) {
      next.setWizard(this);
    }
    return next;
  }
  /**
   * Returns the wizard page with the given name belonging to this wizard.
   *
   * @param pageName the name of the wizard page
   * @return the wizard page with the given name, or <code>null</code> if none
   */
  public IWizardPage getPage(String pageName) {
    IWizardPage page = super.getPage(pageName);
    if (page == null) {
      page = delegateWizard.getPage(pageName);
    }
    if (page != null) {
      page.setWizard(this);
    }
    return page;
  }
  /**
   * Returns the number of pages in this wizard.
   *
   * @return the number of wizard pages
   */
  public int getPageCount() {
    return super.getPageCount() + delegateWizard.getPageCount();
  }
  /**
   * Returns all the pages in this wizard.
   *
   * @return a list of pages
   */
  public IWizardPage[] getPages() {
    int size = super.getPages().length + delegateWizard.getPages().length;
    IWizardPage[] result = new IWizardPage[size];
    for (int i = 0; i < super.getPages().length; i++) {
      result[i] = super.getPages()[i];
    }
    for (int i = 0; i < delegateWizard.getPages().length; i++) {
      result[super.getPages().length + i] = delegateWizard.getPages()[i];
    }
    return result;
  }
  /**
   * Returns the predecessor of the given page.
   * <p>
   * This method is typically called by a wizard page
   * </p>
   *
   * @param page the page
   * @return the previous page, or <code>null</code> if none
   */
  public IWizardPage getPreviousPage(IWizardPage page) {
    IWizardPage prev = null;
    if (isActive(page)) {
      //
      // This can result in a null page. The client
      // wizard resolves this (see else clause below)
      //
      prev = super.getPreviousPage(page);
    }
    else {
      prev = delegateWizard.getPreviousPage(page);
      if (prev == null) {
        //
        // The delegate has no more pages in this direction,
        // so start at the current wizards last page . . . 
        //
        prev = getLastPage();
      }
    }
    if (prev != null) {
      prev.setWizard(this);
    }
    return prev;
  }
  /**
   * Returns the first page inserted into the wizard or its
   * downstream delegate.
   */
  public IWizardPage getStartingPage() {
    IWizardPage start = super.getStartingPage();
    if (start == null) {
      //
      // The current wizard has no pages of its own,
      // see if the delegate has any.
      //
      start = delegateWizard.getStartingPage();
    }
    if (start != null) {
      start.setWizard(this);
    }
    return start;
  }
  /**
   * Returns whether this wizard needs Previous and Next buttons.
   * <p>
   * The result of this method is typically used by the container.
   * </p>
   *
   * @return <code>true</code> if Previous and Next buttons are required,
   *   and <code>false</code> if none are needed
   */
  public boolean needsPreviousAndNextButtons() {
      return super.needsPreviousAndNextButtons() ||
        delegateWizard.needsPreviousAndNextButtons() ||
        getPages().length > 1;
  }
  /**
   * Returns whether this wizard needs a progress monitor.
   * <p>
   * The result of this method is typically used by the container.
   * </p>
   *
   * @return <code>true</code> if a progress monitor is required,
   *   and <code>false</code> if none is needed
   */
  public boolean needsProgressMonitor() {
    boolean result = super.needsProgressMonitor();
    return result || delegateWizard.needsProgressMonitor();
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#performFinish()
   */
  public boolean performFinish() {
    //
    // Since this is a delegating wizard, only the performFinish(IProject)
    // variant of this method should be called. This version does nothing.
    //
    CorePlugin.logError(
        "DelegatingWizard.performFinish called illegally.",
        null);
    return false;
  }
  public boolean performFinish(IProject newProject) {
    return delegateWizard.performFinish(newProject);
  }
  /**
   * Performs any actions appropriate in response to the user 
   * having pressed the Cancel button, or refuse if canceling
   * now is not permitted.
   *
   * @return <code>true</code> to indicate the cancel request
   *   was accepted, and <code>false</code> to indicate
   *   that the cancel request was refused
   */
  public boolean performCancel() {
    boolean result = super.performCancel();
    if (result) {
      return delegateWizard.performCancel();
    }
    return result;
  }
  /*
   * Returns the list of available model compilers
   */
  public String[] getChoices() {
    return delegateWizard.getChoices();
  }

  /*
   * Sets the selected delegate wizard
   */
  public void setDelegate(String Name) {
    delegateWizard = new WizardDelegate(getExtensionPoint(), Name);
    delegateWizard.init(m_workbench, m_selection);
  }
  /*
   * Sets the selected delegate wizard with a required toolset
   */
  public void setDelegate(String Name, String toolset) {
    delegateWizard = new WizardDelegate(getExtensionPoint(), Name, toolset);
    delegateWizard.init(m_workbench, m_selection);
  }

  /**
   * This can be called right after setDelegate to see if the delegate was 
   * set or not.
   * 
   * @return The delegate help by this wizard or null if there is not one.
   */
  public IWorkbenchWizard getDelegate() {
	  return delegateWizard.getDelegate();
  }
  
  /*
   * Returns whether this wizard owns the currently active page.
   * Note that this requires getNextPage and getPreviousPage to
   * cache the current page.
   */
  private boolean isActive(IWizardPage current) {
    IWizardPage page = super.getStartingPage();
    while (page != null) {
      if (page == current) {
        return true;
      }
      page = super.getNextPage(page);
    }
    return false;
  }
  /**
   * Finds and returns the last page for this wizard.
   */
  private IWizardPage getLastPage() {
    IWizardPage page = super.getStartingPage();
    while (super.getNextPage(page) != null) {
      page = super.getNextPage(page);
    }
    return page;
  }
}