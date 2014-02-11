//=====================================================================
//
//File:      $RCSfile: ConvertModel2UUIDAction.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2012/10/12 22:55:13 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;

import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.IDConvertor;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.UUIDMap;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class ConvertModel2UUIDAction implements IObjectActionDelegate {

	private static final String MAPFILE_EXT = "uuidmap"; //$NON-NLS-1$
	private IFile modelFile;

	private static Runnable runnable = null;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
		if (modelFile != null) {

			final Ooaofooa mr = Ooaofooa.getInstance(modelFile, true);
			final Domain_c domain = Domain_c.DomainInstance(mr);

			IPath path = modelFile.getLocation().removeFileExtension();
			path = path.removeFirstSegments(path.segmentCount() - 1);
			final IFile mapFile = modelFile.getParent().getFile(
				path.addFileExtension(MAPFILE_EXT));
			
			runnable = new Runnable() {
				public void run(){
					UUIDMap inputMap = new UUIDMap(null);
					if (mapFile.exists()) {
						try {
							InputStream in = mapFile.getContents();
							inputMap = new UUIDMap(null);
							inputMap.load(in);
							in.close();
						} catch (Exception e) {
							UIUtil.openError(PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell(), "Error", 
									"Could not load the map file");
							CorePlugin.logError("error while loading map file", e); //$NON-NLS-1$
							runnable = null;
							return;
						}
					}

					UUIDMap newMap = null;
					if (inputMap != null) {
						newMap = IDConvertor.getInstance().convertToUUID(domain,
							                                             inputMap);
					} else {
						newMap = IDConvertor.getInstance().convertToUUID(domain);
					}

					try {
						persistSingleFileModel(mr, modelFile);

						ByteArrayOutputStream out = new ByteArrayOutputStream();
						newMap.store(out);
						ByteArrayInputStream in = new ByteArrayInputStream(out
							.toByteArray());
						if (mapFile.exists()) {
							mapFile.setContents(in, true, true, null);
						} else {
							mapFile.create(in, true, null);
						}
					} catch (Exception e) {
						CorePlugin.logError("error while writing new map file", e);  //$NON-NLS-1$
					}
					runnable = null;
					inputMap.clear();
					newMap.clear();
				}
			};
			
			
			BusyIndicator.showWhile(null, runnable); 
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		modelFile = null;
		if (runnable == null && selection instanceof IStructuredSelection) {
			Object selectedObject = ((IStructuredSelection) selection)
				.getFirstElement();
			if (selectedObject instanceof IFile) {
				IFile file = (IFile) selectedObject;
				if (Ooaofooa.MODELS_EXT.equals(file.getFileExtension()) && file.getLocation().segmentCount() == 3) {
					IPath path = file.getFullPath();
					if(path.segmentCount() == 3 && path.segment(1).equals(Ooaofooa.MODELS_DIRNAME)){
						Ooaofooa mr = Ooaofooa.getInstance(file, true);
						Domain_c domain = Domain_c.DomainInstance(mr);
						if(!(domain == null || file.isReadOnly())){
							if(IdAssigner.isUUIDDummy(domain.getDom_id())){
								modelFile = file;
								action.setEnabled(true);
								return;
							}
						}
					}
				}
			}
		}
		action.setEnabled(false);
	}
	
	private void persistSingleFileModel(Ooaofooa mr, IFile file){
		AbstractModelExportFactory factory = AbstractModelExportFactory.getInstance();
		
        try {
			IRunnableWithProgress runnable = factory.create(mr,
					file.getLocation().toString(), true);
            runnable.run(new NullProgressMonitor());
        } catch (Exception e) {
            CorePlugin.logError("Could not persist model", e);
        }

        // get Eclipse to notice that the model's file has changed on disk 
        try {
            file.refreshLocal(IFile.DEPTH_ZERO, new NullProgressMonitor());
        } catch (CoreException x) {
            CorePlugin.logError("Could not refresh persisted model file.", x);
        }
		
	}
}
