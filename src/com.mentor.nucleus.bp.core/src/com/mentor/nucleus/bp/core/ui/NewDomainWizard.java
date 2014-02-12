//========================================================================
//
//File:      $RCSfile: NewDomainWizard.java,v $
//Version:   $Revision: 1.31 $
//Modified:  $Date: 2012/10/12 22:55:13 $
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ComponentResourceListener;
import com.mentor.nucleus.bp.core.common.IDConvertor;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.OALPersistenceUtil;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class NewDomainWizard extends Wizard implements INewWizard {

	private SystemModel_c m_sys = null;
	
	// Reference to the pages provided by this wizard
	private WizardNewDomainCreationPage m_creationPage;

	private String m_domainName;

	private boolean m_useTemplate;

	private String m_templateFile;

	private boolean m_parseOnImport;
	
	public NewDomainWizard() {
		super();
	}
	
	public NewDomainWizard(SystemModel_c system, String domainName, String templateFile, boolean parseOnImport) {
		super();
		m_sys = system;
		m_domainName = domainName;
		if(templateFile == null || templateFile.equals("")) {
			m_useTemplate = false;
		} else {
			m_useTemplate = true;
			m_templateFile = templateFile;
		}
		m_parseOnImport = parseOnImport;
	}
	
		
	// List used to store duplicate names found during import.  Used to report the
	// names to the user.
	public static Vector<String> duplicateNames = new Vector<String>();
	
    private class myRunnable implements IRunnableWithProgress
    {
        public boolean result;
        public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        	try {
				ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
					public void run(IProgressMonitor monitor) throws CoreException {
						ComponentResourceListener.setIgnoreResourceChanges(true);
							result = createDomain(getContainer(), null);
					}
				}, monitor);
			} catch (CoreException e) {
				CorePlugin.logError("Unable to create domain.", e);
			}
        }
    }
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
    public boolean performFinish() {
        myRunnable op = new myRunnable();
        m_useTemplate = m_creationPage.useTemplate();
        m_templateFile = m_creationPage.getTemplateLocationFieldValue();
        m_domainName = m_creationPage.getDomainNameFieldValue();
        m_parseOnImport = m_creationPage.parseOnImport();
        try {
            IWizardContainer container = getContainer();
            if(container != null){
                container.run(false, false, op);
            }else{
                // For unit tests.
                op.run(new NullProgressMonitor());
            }
        } catch (InvocationTargetException e) {
            CorePlugin.logError("Internal error: " + e.getMessage(), e); //$NON-NLS-1$
            return false;
        } catch (InterruptedException e) {
            CorePlugin.logError("Internal error: " + e.getMessage(), e); //$NON-NLS-1$
            return false;
        }
        // Send an empty transaction to signal the work is done
		Transaction tr = null;
		try {
			tr = m_sys.getTransactionManager().startTransaction(m_sys
				.Get_name(), m_sys.getModelRoot(),
				false);

		}
		catch (TransactionException te) {
			CorePlugin.logError("Unable to create domain: Transaction already in progress.", te);
		}
		finally {
			if (tr != null) {
				m_sys.getTransactionManager().endTransaction(tr);
			}
		}
        // save page values
        m_creationPage.internalSaveWidgetValues();

        return op.result;
    }
    
    public static void displayDuplicateDialog() {
        if (duplicateNames.size() > 0) {
			String title = "Duplicate Model Elements Renamed";
			String msg = duplicateNames.toString();
			msg = msg.replace(",", "");
			msg = msg.replace("[", "");
			msg = msg.replace("]", "");
			UIUtil.openInformation(CorePlugin
					.getDefault().getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					title, msg);
		}
    }
    
	public boolean createDomain(IWizardContainer container, ProgressMonitorDialog dialog) {
        IProject project = (IProject)m_sys.getAdapter(IProject.class);
        String domainName = PersistenceManager.getDefaultInstance()
				.getUniqueNameOfParent(m_sys.getPersistableComponent(),
						m_domainName, null);
		final String id = Ooaofooa.createModelRootId(
				project, 
				domainName, true);
        final Ooaofooa modelRoot = Ooaofooa.getInstance(id, false);
		String message = "";//$NON-NLS-1$
		if(container != null && dialog == null)
			dialog = new ProgressMonitorDialog(getShell());

		if ( !m_useTemplate )
		{
			final CorePlugin plugin = CorePlugin.getDefault();
			URL installURL = plugin.getBundle().getEntry("/");//$NON-NLS-1$
			try {
				URL url = new URL(installURL, Ooaofooa.MODELS_DIRNAME + "/default." + Ooaofooa.MODELS_EXT);  //$NON-NLS-1$
				final InputStream inStream = url.openStream();
                ImportStreamStatus iss = new ImportStreamStatus(id, inStream);
                if(container == null) {
                	iss.run(new NullProgressMonitor());
                } else {
                	dialog.run(true, false, iss);
                }
				message = iss.getMessage();
				try { inStream.close();	} catch (IOException e2) { /* do nothing */ }
			} catch (IOException e1) {
				CorePlugin.logError("Internal error: failed to open default." + Ooaofooa.MODELS_EXT, e1);//$NON-NLS-1$
				return false;
			} catch (InterruptedException e) {
				CorePlugin.logError("Internal error: import was interrupted", e); //$NON-NLS-1$
				return false;
			} catch (InvocationTargetException e) {
				CorePlugin.logError("Internal error: plugin.doLoad not found", e); //$NON-NLS-1$
				return false;
			}
		}
		else
		{
			String templateFileName = m_templateFile;
			final IPath templatePath = new Path(templateFileName);
            // import the domain file into the model root with the id of the full path of the domain file
			if ( templatePath.getFileExtension().equals(Ooaofooa.MODELS_EXT) )
			{
				final InputStream inStream;
				try {
					inStream = new FileInputStream (templatePath.toFile());
				} catch (FileNotFoundException e) {
					CorePlugin.logError("Internal error: failed to open "+templateFileName, e);
					return false;
				}
				try {
                    ImportStreamStatus iss = new ImportStreamStatus(id, inStream);
                    if(container == null) {
                    	// for unit tests to prevent displaying progress dialogs
                    	iss.run(new NullProgressMonitor());
                    } else {
                    	dialog.run(true, false, iss);
                    }
					message = iss.getMessage();
					inStream.close();
				} catch (InterruptedException e) {
					CorePlugin.logError("Internal error: import was interrupted", e); //$NON-NLS-1$
					return false;
				} catch (InvocationTargetException e) {
					CorePlugin.logError("Internal error: plugin.doLoad not found", e); //$NON-NLS-1$
					return false;
				} catch (IOException e) {
					CorePlugin.logError("Unable to close stream to import file.", e); //$NON-NLS-1$
					/* do nothing */
				}
			}
			else
			{
				try {
                    ImportFileStatus ifs = new ImportFileStatus(id, templatePath.toFile());
                    if(container == null) {
                        // For unit tests.
                    	ifs.run(new NullProgressMonitor());
                    } else {
                    	dialog.run(true, false, ifs);
                    }
					message = ifs.getMessage();
				} catch (InterruptedException e) {
					CorePlugin.logError("Internal error: import was interrupted", e); //$NON-NLS-1$
					return false;
				} catch (InvocationTargetException e) {
					CorePlugin.logError("Internal error: plugin.doLoad not found", e); //$NON-NLS-1$
					return false;
				}
			}
			if (!message.equals("")) {
                CorePlugin.showImportErrorMessage(true, message);
				return true;
			}
		}
		// set the just loaded Domain instance name to the name specified by the user
		final Domain_c dom = Domain_c.DomainInstance(modelRoot,null,false);
		if ( dom == null )
		{
			// There was a load error, already logged
			return false;
		}
        dom.setName(Ooaofooa.Getuniqueinitialname(modelRoot, m_domainName, dom.Converttoinstance()));
		IDConvertor.getInstance().recreateUUID(dom);        

        // Create a PMC for the new domain
        try {
            if(dom.getPersistableComponent()==null){
            PersistenceManager.getDefaultInstance().registerModel(dom, project);
            }
            IRunnableWithProgress persistenceRunnable = new IRunnableWithProgress() {
			
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
                    try {
                        monitor.beginTask("Persisting newly created model...", getComponentCount(dom));
                        persistSelfAndChildren(dom, monitor);
                        displayDuplicateDialog();
                        monitor.done();
                    } catch (CoreException e) {
                        CorePlugin.logError("Unable to persist newly created model.", e);
                    } finally {
                    	duplicateNames.clear();
                    }			
				}
			
			};
	        if ( m_parseOnImport )
	        {
	            try {
                    if(container == null) {
                        // For unit tests.
                        CorePlugin.parseAll(dom, new NullProgressMonitor());
                    } else {
    	                dialog.run(false, false, new IRunnableWithProgress () {

    	                    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
    	                        CorePlugin.parseAll(dom, monitor);
    	                    }
    	                    
    	                });
                    }
	            } catch (InvocationTargetException e) {
	                CorePlugin.logError("Unable to parse model", e);
	            } catch (InterruptedException e) {
	                CorePlugin.logError("Unable to parse model", e);
	            }
	        }
	        
	        OALPersistenceUtil.persistOAL(modelRoot);
	        
			if(container == null) {
				persistenceRunnable.run(new NullProgressMonitor());
			} else {
				dialog.run(false, false, persistenceRunnable);
			}
        } catch (CoreException e) {
            CorePlugin.logError("Unable to register model", e);
        } catch (InvocationTargetException e) {
            CorePlugin.logError("Unable to persist newly created model.", e);
        } catch (InterruptedException e) {
            CorePlugin.logError("Unable to persist newly created model.", e);
        }
		return true;
	}
    public static int getComponentCount(NonRootModelElement me) throws CoreException {
        int count = 0;
        IPersistenceHierarchyMetaData metaData=PersistenceManager.getHierarchyMetaData();
        PersistableModelComponent component=PersistenceManager.getComponent(me);
        if(component==null)
        {
            NonRootModelElement parent=metaData.getParentComponentRootModelElement(me);
            component= new PersistableModelComponent(me,parent.getFile());
        }
        count = count + 1;
        List children = metaData.getChildrenComponentRootModelElements(me);
        for (Iterator iter = children.iterator(); iter.hasNext();) {
            NonRootModelElement child = (NonRootModelElement) iter.next();
            count = count + getComponentCount(child);
        }        
        return count;
    }
    
	/**
	 * This routine checks to see if the given name is a duplicate. If it is, it
	 * makes it unique by appending an incrementing value to the end of the name.
	 * 
	 * @param elementType 
	 * @param elementName
	 * @param duplicateElementMap
	 * @return A unique element name
	 */
	private static  String determineElementName(String elementType, String elementName, Map<String, Map<String, Integer>> duplicateElementMap) {
		String result = elementName;
		if (!elementName.equals("")) {
			if (duplicateElementMap.containsKey(elementType)) {
				Map<String, Integer> nameMap = duplicateElementMap
						.get(elementType);
				Integer val = nameMap.get(elementName);
				if (val == null) {
					val = new Integer(1);
				} else {
					// Found a duplicate, append to its name
					val = val + 1;
					result = result + "-" + val.toString();
				}
				nameMap.put(elementName, val);
			} else {
				// Create a new entry for this type and put the element into it.
				Map<String, Integer> nameMap = new HashMap<String, Integer>();
				nameMap.put(elementName, new Integer(1));
				duplicateElementMap.put(elementType, nameMap);
			}
		}
		return result;
	}
    public static void persistSelfAndChildren(NonRootModelElement me, IProgressMonitor monitor) throws CoreException {
        IPersistenceHierarchyMetaData metaData=PersistenceManager.getHierarchyMetaData();
        PersistableModelComponent component=PersistenceManager.getComponent(me);
        if(component==null)
        {
            NonRootModelElement parent=metaData.getParentComponentRootModelElement(me);
            component= new PersistableModelComponent(me,parent.getFile());
        }
        component.persist();
        monitor.worked(1);
    	// Map used to determine if duplicate names exist during import
    	Map<String, Map<String, Integer>> duplicateElementMap = new HashMap<String, Map<String, Integer> >() ;
    	boolean firstDuplicateOfType = true;
    	String parentName = me.getName();
        List children = metaData.getChildrenComponentRootModelElements(me);
        for (Iterator iter = children.iterator(); iter.hasNext();) {
            NonRootModelElement child = (NonRootModelElement) iter.next();
            String childName = child.getName();
            String childType = child.getClass().getSimpleName();
            String uniqueName = determineElementName(childType, childName, duplicateElementMap);
            if ( !uniqueName.equals(childName) ) {
            	child.setName(uniqueName);
            	
            	// Remember duplicates
            	if (firstDuplicateOfType) {
            		duplicateNames.add("Duplicates found in " + parentName + "\n");
            		firstDuplicateOfType = false;
            	}
            	duplicateNames.add("\t" + childName + "\t->\t" + uniqueName + "\n");
            }
            persistSelfAndChildren(child, monitor);
        }
    }
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		if ( ! selection.isEmpty() )
	    {
		    Object context = selection.iterator().next();
		    if (context instanceof SystemModel_c) {
		    	m_sys = (SystemModel_c)context;
		    }
	    }
		m_creationPage = new WizardNewDomainCreationPage("newxtUMLDomainCreationPage");//$NON-NLS-1$
		m_creationPage.setTitle("New xtUML Model"); 
		m_creationPage.setDescription("Create a new xtUML Model");
		setWindowTitle("New xtUML Model");
        setNeedsProgressMonitor(true);
		this.addPage(m_creationPage);
	}

	/**
	 * @return Returns the m_sys.
	 */
	public SystemModel_c getSystemModel() {
		return m_sys;
	}

	/**
	 * @param m_sys The m_sys to set.
	 */
	public void setSystemModel(SystemModel_c m_sys) {
		this.m_sys = m_sys;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#getDialogSettings()
	 */
	public IDialogSettings getDialogSettings()
	{
		return CorePlugin.getDefault().getDialogSettings();
	}
	
	private class ImportStreamStatus implements IRunnableWithProgress
	{
		CorePlugin plugin = CorePlugin.getDefault();		
		String m_id;
		InputStream m_inStream;
		String m_message;
		public ImportStreamStatus(String id, InputStream inStream) {
			m_id = id;
			m_inStream = inStream;
		}
		public void run(IProgressMonitor monitor)
		throws InvocationTargetException,
		InterruptedException {
			m_message = plugin.doLoad(m_sys, m_id, m_inStream, monitor, false, true);
		}
		String getMessage()
		{
			return m_message;
		}
	}
	
	private class ImportFileStatus implements IRunnableWithProgress
	{
		CorePlugin plugin = CorePlugin.getDefault();		
		String m_id;
		File m_inFile;
		String m_message;
		public ImportFileStatus(String id, File inFile) {
			m_id = id;
			m_inFile = inFile;
		}
		public void run(IProgressMonitor monitor)
		throws InvocationTargetException,
		InterruptedException {
			m_message = plugin.doLoadSql(m_sys, m_id, m_inFile, monitor, false, true);
		}
		String getMessage()
		{
			return m_message;
		}
	}

}