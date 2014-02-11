//========================================================================
//
//File:      $RCSfile: WizardDelegate.java,v $
//Version:   $Revision: 1.13 $
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;

/**
 *
 * This wizard implementation manages passing wizard interface calls
 *  to a downstream partner (aslo assumed to implement IWizard).
 * 
 */
public class WizardDelegate implements IWizard {

  IWorkbenchWizard delegate = null;
  String m_extensionName = "";//$NON-NLS-1$
  // The GettingStarted demo only works with certain toolsets
  String m_RequiredToolset = "";//$NON-NLS-1$
  
  WizardDelegate(String extensionName, String delegateName) {
	    if (extensionName != null && delegateName != null) {
	      delegate = getDelegate(extensionName, delegateName);
	      m_extensionName = extensionName;
	    }
	  }

  WizardDelegate(String extensionName, String delegateName, String toolset) {
	    if (extensionName != null && delegateName != null) {
	      m_RequiredToolset = toolset;
	      delegate = getDelegate(extensionName, delegateName);
	      m_extensionName = extensionName;
	    }
	  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#addPages()
   */
  public void addPages() {
    if (delegate != null)
      delegate.addPages();

  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#canFinish()
   */
  public boolean canFinish() {
    if (delegate != null)
      return delegate.canFinish();
    else
      return true;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#createPageControls(org.eclipse.swt.widgets.Composite)
   */
  public void createPageControls(Composite pageContainer) {
    CorePlugin.logError(
      "WizardDelegate.createPageControls called illegally.",
      null);
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#dispose()
   */
  public void dispose() {
    CorePlugin.logError("WizardDelegate.dispose called illegally.", null);
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getContainer()
   */
  public IWizardContainer getContainer() {
    CorePlugin
      .logError("WizardDelegate.getContainer called illegally.", null);
    return null;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getDefaultPageImage()
   */
  public Image getDefaultPageImage() {
    if (delegate != null)
      return delegate.getDefaultPageImage();
    else
      return null;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getDialogSettings()
   */
  public IDialogSettings getDialogSettings() {
    if (delegate != null)
      return delegate.getDialogSettings();
    else
      return null;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
   */
  public IWizardPage getNextPage(IWizardPage page) {
    if (delegate != null)
      return delegate.getNextPage(page);
    else
      return null;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getPage(java.lang.String)
   */
  public IWizardPage getPage(String pageName) {
    if (delegate != null)
      return delegate.getPage(pageName);
    else
      return null;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getPageCount()
   */
  public int getPageCount() {
    if (delegate != null)
      return delegate.getPageCount();
    else
      return 0;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getPages()
   */
  public IWizardPage[] getPages() {
    if (delegate != null)
      return delegate.getPages();
    else
      return new IWizardPage[0];
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getPreviousPage(org.eclipse.jface.wizard.IWizardPage)
   */
  public IWizardPage getPreviousPage(IWizardPage page) {
    if (delegate != null)
      return delegate.getPreviousPage(page);
    else
      return null;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getStartingPage()
   */
  public IWizardPage getStartingPage() {
    if (delegate != null)
      return delegate.getStartingPage();
    else
      return null;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getTitleBarColor()
   */
  public RGB getTitleBarColor() {
    CorePlugin.logError(
      "WizardDelegate.getTitleBarColor called illegally.",
      null);
    return null;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#getWindowTitle()
   */
  public String getWindowTitle() {
    CorePlugin.logError(
      "WizardDelegate.getWindowTitle called illegally.",
      null);
    return null;
  }
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    if (delegate != null)
      delegate.init(workbench, selection);
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#isHelpAvailable()
   */
  public boolean isHelpAvailable() {
    if (delegate != null)
      return delegate.isHelpAvailable();
    else
      return false;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#needsPreviousAndNextButtons()
   */
  public boolean needsPreviousAndNextButtons() {
    if (delegate != null)
      return delegate.needsPreviousAndNextButtons();
    else
      return false;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#needsProgressMonitor()
   */
  public boolean needsProgressMonitor() {
    if (delegate != null)
      return delegate.needsProgressMonitor();
    else
      return false;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#performCancel()
   */
  public boolean performCancel() {
    if (delegate != null)
      return delegate.performCancel();
    else
      return true;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#performFinish()
   */
  public boolean performFinish() {
    if (delegate != null)
      return delegate.performFinish();
    else
      return true;
  }
  /*
   *  (non-Javadoc)
   * Peform finish in the case where the resource is already created.
   * Call this method after performing own finish processing.
   */
  public boolean performFinish(IProject project) {
    if (delegate != null) {
      Class<?>[] argTypes = new Class[1];
      argTypes[0] = IProject.class;
      try {
        Method performFinish = delegate.getClass().getMethod(
          "performFinish", argTypes); //$NON-NLS-1$
        Object[] args = new Object[1];
        args[0] = project;
        Object result = performFinish.invoke(delegate, args);
        return ((Boolean) result).booleanValue();
      } catch (NoSuchMethodException e) {
        CorePlugin.logError(
          "No performFinish method found for downstream wizard: ",
          e);
      } catch (SecurityException e) {
        CorePlugin.logError("performFinish method inaccessible: ", e);
      } catch (InvocationTargetException e) {
        CorePlugin.logError("performFinish method inaccessible: ", e);
      } catch (IllegalArgumentException e) {
        CorePlugin.logError("performFinish method inaccessible: ", e);
      } catch (IllegalAccessException e) {
        CorePlugin.logError("performFinish method inaccessible: ", e);
      } catch (ClassCastException e) {
        CorePlugin.logError(
          "performFinish method does not return a Boolean value: ",
          e);
      }
    }
    return true;
  }
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizard#setContainer(org.eclipse.jface.wizard.IWizardContainer)
   */
  public void setContainer(IWizardContainer wizardContainer) {
    CorePlugin
      .logError("WizardDelegate.setContainer called illegally.", null);
  }
  public String[] getChoices() {
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    IExtensionPoint extPt = reg.getExtensionPoint(m_extensionName);
    if (extPt != null) {
      IExtension[] exts = extPt.getExtensions();
      String[] result = new String[exts.length];
      for (int i = 0; i < exts.length; i++) {
        IConfigurationElement[] elems = exts[i].getConfigurationElements();
        result[i] = elems[0].getAttribute("name"); //$NON-NLS-1$
      }
      return result;
    }
    else {
      return new String[0];
    }
  }
  private Class<?> getDelegateProvider(String extensionName, String delegateName) {
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    IExtensionPoint extPt = reg.getExtensionPoint(extensionName); 
    if (extPt != null) {
      IExtension[] exts = extPt.getExtensions();
      for (int i = 0; i < exts.length; i++) {
        IConfigurationElement[] elems = exts[i].getConfigurationElements();
        for (int j = 0; j < elems.length; j++) {
          String foundDelegateName = elems[j].getAttribute("name"); //$NON-NLS-1$
          if (delegateName == null || delegateName.equals("")//$NON-NLS-1$
            || (foundDelegateName != null && foundDelegateName.equals(delegateName))) {
            String className = elems[j].getAttribute("class"); //$NON-NLS-1$
            if (className != null) {
              String bundleID = exts[i].getNamespaceIdentifier();
              Bundle bundle = Platform.getBundle( bundleID );  
              if (bundle != null) {
                try {
                  return bundle.loadClass(className);
                } catch (Throwable e) {
                  CorePlugin.logError("Unable to initialize Model Compiler:", e);
                }
              }
              else {
                CorePlugin.logError("Bundle not found for client package", null);   
              }
            }
            else {
              CorePlugin.logError("Class name attribute not supplied in code builder declaration", null);   
            }
          }
        }
      }
    }
    return null;
  }
  
  public IWorkbenchWizard getDelegate() {
	  return delegate;
  }
  
  private IWorkbenchWizard getDelegate(String extensionName, String delegateName) {
    Class<?> delegateProvider = getDelegateProvider(extensionName, delegateName);
    if (delegateProvider != null) {
      Class<?>[] argTypes = new Class[1];
      argTypes[0] = Object.class;
      try {
        Method getWizard = delegateProvider.getMethod("getWizard", argTypes); //$NON-NLS-1$
        Object[] args = new Object[1];
        args[0] = m_RequiredToolset;
        return (IWorkbenchWizard) getWizard.invoke(null, args);
      } catch (Throwable e) {
    	  if (e instanceof NoSuchMethodException) {
    		  CorePlugin.logError("No getWizard method found for class: ", e);
    	  } else if (e instanceof SecurityException) {
    		  CorePlugin.logError("getPages method inaccessible: ", e);
    	  } else if (e instanceof InvocationTargetException) {
    		  CorePlugin.logError("getPages method inaccessible: ", e);
    	  } else if (e instanceof IllegalArgumentException) {
    		  CorePlugin.logError("getPages method inaccessible: ", e);
    	  } else if (e instanceof IllegalAccessException) {
    		  CorePlugin.logError("getPages method inaccessible: ", e);
    	  } else if (e instanceof ClassCastException) {
    		  CorePlugin.logError(
    				  "getWizard method does not return an instance of IWizard: ",
    				  e);
    	  } else {    		  
    		  CorePlugin.logError(
    				  "Unexpected exception encountered: ",
    				  e);
    	  }
      }
    }
    return null;
  }
}