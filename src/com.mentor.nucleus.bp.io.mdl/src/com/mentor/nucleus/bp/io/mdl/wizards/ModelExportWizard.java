//====================================================================
//
//File:      $RCSfile: ModelExportWizard.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2013/06/12 13:09:18 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================

package com.mentor.nucleus.bp.io.mdl.wizards;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.io.core.CoreExport;
import com.mentor.nucleus.bp.io.core.CorePlugin;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;

/**
 * This wizard exports model elements selected to a file.
 * @see Wizard
 */
public class ModelExportWizard extends Wizard implements IImportWizard {

	private ModelExportPage fExportPage;
	private IRunnableWithProgress exporter;
	private File m_outputFile;
	private ByteArrayOutputStream outStream;
	
	public ModelExportWizard() {
		super();
	}
	protected ModelExportPage createExportPage() {
		fExportPage =
			new ModelExportPage("BridgePoint Model Export", null);
		fExportPage.setTitle("BridgePoint Model Export");
		fExportPage.setDescription("Enter the destination file name");
		return fExportPage;
	}
	protected ModelExportPage getExportPage() {
		return fExportPage;
	}
	
	/**
	 * 
	 * @return true is the export was created with the prefernece to enable
	 *              export of executable oal instance and false if not.
	 *              
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 */
	private boolean createExportProcessor() throws FileNotFoundException, InterruptedException, InvocationTargetException {
		// we need to add the GD_GE instances for those elements
		// selected
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		final NonRootModelElement[] selectedElements = Selection.getInstance()
				.getSelectedNonRootModelElements();
		
        IRunnableWithProgress runnableLoader = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor) {
        		// here we force load the selected elements to
        		// prevent outputting null ids (which can happen
        		// in certain circumstances) issue 3303 may
        		// fix this at which point this code can be
        		// removed
        		for(int i = 0; i < selectedElements.length; i++) {
        			PersistableModelComponent component = selectedElements[i]
        					.getPersistableComponent();
        			component.loadComponentAndChildren(monitor);
        		}
            }
        };
        
		if(getContainer() == null) {
			runnableLoader.run(new NullProgressMonitor());
		} else {
			new ProgressMonitorDialog(getContainer().getShell()).run(true, false, runnableLoader);
		}
		
		for(int i = 0; i < selectedElements.length; i++) {
			elements.add(selectedElements[i]);
		}
		addGraphicalElementsFor(selectedElements, elements);
		outStream = new ByteArrayOutputStream();
		exporter = com.mentor.nucleus.bp.core.CorePlugin
				.getStreamExportFactory().create(
						outStream,
						elements.toArray(new NonRootModelElement[elements.size()]), 
						true, true);
		boolean exportOALInstances = false;
		if (exporter instanceof CoreExport) {
			
			((CoreExport)exporter).setExportGraphics(CoreExport.USER_PREFERENCE);
			// Check the license.  This tests the license without checking it out.  If there is
			// no availble license this will return false.
			exportOALInstances = ((CoreExport)exporter).setExportOAL(CoreExport.USER_PREFERENCE);
		}
		return exportOALInstances;
	}
	
	private void addGraphicalElementsFor(
			final NonRootModelElement[] selectedElements,
			List<NonRootModelElement> elements) {
			for(int i = 0; i < selectedElements.length; i++) {
				final int count = i;
				// for this release all elements are exported
				// at the system level
				Ooaofgraphics graphicsRoot = Ooaofgraphics.getDefaultInstance();
				GraphicalElement_c ge = GraphicalElement_c
					.GraphicalElementInstance(graphicsRoot,
							new ClassQueryInterface_c() {
				
					public boolean evaluate(Object candidate) {
						GraphicalElement_c selected = (GraphicalElement_c) candidate;
						return selected.getOoa_id().equals(selectedElements[count].Get_ooa_id());
					}
				
				});
				if(ge != null)
					elements.add(ge);
			}
	}
	
	public boolean performFinish() {
		boolean successfulExport = false;
		String errorMsg = "Unable to export to destination file.";  
		FileOutputStream fos;
		try {
			m_outputFile = new File(fExportPage.getDestinationFilePath());
			if (!m_outputFile.exists()) {
				m_outputFile.createNewFile();
			} else {
				if (getExportPage().getOverwriteWarning()) {
					boolean replaceFile = successfulExport = UIUtil
							.openQuestion(
									getShell(),
									"Confirm Replace",
									"The file '"
											+ getExportPage()
													.getDestinationFilePath()
											+ "' already exists. Do you want to overwrite it?", true);
					if (!replaceFile) {
						// If the user doesn't want to overwrite the file return
						// but leave the dialog so they can choose another name
						// if they want to
						return successfulExport;
					}
				}
			}
			createExportProcessor();
			boolean exportOALIsSelected = false;
			NonRootModelElement[] selectedElements = null;
			if (exporter instanceof CoreExport) {
				exportOALIsSelected = ((CoreExport) exporter).exportOAL();
				if (exportOALIsSelected) {
					selectedElements = Selection.getInstance()
							.getSelectedNonRootModelElements();
					((CoreExport) exporter).parseAllForExport(selectedElements, getContainer().getShell());
				}
				
			}
			// Perform the export
			if (getContainer() == null) {
				exporter.run(new NullProgressMonitor());
			} else {
				new ProgressMonitorDialog(getContainer().getShell()).run(true,
						false, exporter);
			}
			fos = new FileOutputStream(m_outputFile);
			fos.write(outStream.toByteArray());
			fos.close();
			successfulExport = true;
			
			// Destroy the OAL instances
			if (selectedElements != null) {
				for (int i = 0; i < selectedElements.length; i++) {
					AllActivityModifier.disposeAllBodies(selectedElements[i].getModelRoot());
				}
			}
		} catch (FileNotFoundException e) {
			CorePlugin.logError(errorMsg, e);
		} catch (IOException e) {
			CorePlugin.logError(errorMsg, e);
		} catch (InvocationTargetException e) {
			CorePlugin.logError(errorMsg, e);
		} catch (InterruptedException e) {
			CorePlugin.logError(errorMsg, e);
		}		
		if (!successfulExport) {
			Shell parent= getShell();
			if (getContainer() != null) {
				parent = getContainer().getShell();
			}
			
			UIUtil.openError(parent, "Export Failed", errorMsg);
			
			if (m_outputFile.exists()) {
				m_outputFile.delete();
			}
		}
		
		return successfulExport;
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Export");
		setDefaultPageImageDescriptor(com.mentor.nucleus.bp.core.CorePlugin
				.getImageDescriptor("importsql_wiz.gif")); //$NON-NLS-1$		
	}

	public void addPages() {
		super.addPage(createExportPage());
	}

	public IDialogSettings getDialogSettings() {
		return CorePlugin.getDefault().getDialogSettings();
	}
	
}
