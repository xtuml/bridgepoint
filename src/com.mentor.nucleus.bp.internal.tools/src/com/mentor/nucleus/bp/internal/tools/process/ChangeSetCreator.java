//========================================================================
//
//File:      $RCSfile: ChangeSetCreator.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:14:59 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License
//======================================================================== 
package com.mentor.nucleus.bp.internal.tools.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

public class ChangeSetCreator extends ActionDelegate {

	public void run() {
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		if(selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			if(structuredSelection.size() == 1) {
				if(structuredSelection.getFirstElement() instanceof IFile) {
					IFile implementationNote = (IFile) structuredSelection.getFirstElement();
					if(implementationNote.getFileExtension().equals("int")) {
						InputStream inStream;
						try {
							inStream = implementationNote.getContents();
							long byteCount = implementationNote.getRawLocation().toFile().length();
							byte[] byteArray = new byte[(int) byteCount];
							inStream.read(byteArray);
							IDocument document = new Document(new String(byteArray));
							FindReplaceDocumentAdapter frda = new FindReplaceDocumentAdapter(document);
							IRegion startRegion = frda.find(0, "8. Code Changes", true, true, true, false);
							int startOffset = startRegion.getOffset() + 17;
							IRegion endRegion = frda.find(startOffset, "End", true, true, true, false);
							int endOffset = endRegion.getOffset();
							String fileList = document.get(startOffset, endOffset - startOffset);
							fileList = fileList.replaceAll("---------------\r\n", "");
							fileList = fileList.replaceAll("Branch name:.*\r\n", "");
							fileList = fileList.replaceAll("\r\n    ", "");
							fileList = fileList.replaceAll("\r\n", ",");
							fileList = fileList.replaceAll(">", "");
							String[] filesString = fileList.split(",");
							if(filesString.length == 0)
								return;
							ArrayList<IFile> list = new ArrayList<IFile>();
							for(int i = 0; i < filesString.length; i++) {
								if(!filesString[i].equals("")) {
									IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(filesString[i]);
									if(resource instanceof IFile) {
										list.add((IFile) resource);
									}
								}
							}
							if(list.size() == 0) return;
							IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
							String workingSetName = "chgset-" + implementationNote.getName().replaceAll(".int", "");
							IWorkingSet set = workingSetManager.createWorkingSet(workingSetName, list.toArray(new IFile[list.size()]));
							IWorkingSet existingSet = workingSetManager.getWorkingSet(workingSetName);
							if(existingSet != null) {
								workingSetManager.removeWorkingSet(existingSet);
							}
							workingSetManager.addWorkingSet(set);
							inStream.close();
						} catch (CoreException e) {	} catch (IOException e) { } catch (BadLocationException e) { }
					}
				}
			}
		}
	}

	public void run(IAction action) {
		run();
	}

}
