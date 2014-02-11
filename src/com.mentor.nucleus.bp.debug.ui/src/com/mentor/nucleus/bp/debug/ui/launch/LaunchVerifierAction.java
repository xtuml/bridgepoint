package com.mentor.nucleus.bp.debug.ui.launch;


//====================================================================
//
// File:      $RCSfile: LaunchVerifierAction.java,v $
// Version:   $Revision: 1.15 $
// Modified:  $Date: 2013/01/17 03:36:29 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;


public class LaunchVerifierAction extends LaunchShortcut implements IActionDelegate {
    public LaunchVerifierAction() {
        super();
        setMode(ILaunchManager.DEBUG_MODE);
    }

    public void run(IAction action) {
        IStructuredSelection selection = (IStructuredSelection) Selection.getInstance()
                                                                         .getStructuredSelection();

        if ((selection == null) || selection.isEmpty()) {
            return;
        }

        if (selection.size() == 1) {
            Object context = selection.getFirstElement();

            if (context instanceof SystemModel_c ) {
				IProject prj = (IProject) ((SystemModel_c) context).getAdapter(IProject.class);
            	launchProject(prj);
            }
            else {
	            if (context instanceof Domain_c || context instanceof ComponentReference_c) {
	                launchMultipleModel(new NonRootModelElement[] {(NonRootModelElement) context});
	            }

	            if(context instanceof Component_c) {
	            	Component_c component = (Component_c) context;
	            	List<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
	            	list.add(component);
	            	NonRootModelElement[] children = BPDebugUtils.getComponentChildren(component);
	            	for(int i = 0; i < children.length; i++) {
	            		list.add(children[i]);
	            	}
	            	launchMultipleModel(list.toArray(new NonRootModelElement[list.size()]));
	            }
	            
	            if(context instanceof ComponentPackage_c) {
	            	NonRootModelElement[] componentPackageChildren = BPDebugUtils.getComponentPackageChildren((ComponentPackage_c) context);
	            	launchMultipleModel(componentPackageChildren);
	            }
	            if (context instanceof Package_c) {
	            	NonRootModelElement[] packageChildren = BPDebugUtils.getPackageChildren((Package_c) context);
	            	launchMultipleModel(packageChildren);
	            }
            }
        } else {
            Vector<NonRootModelElement> model = null;
            Iterator ite = selection.iterator();

            while (ite.hasNext()) {
                Object o = ite.next();

                if (o instanceof Domain_c) {
                    if (model == null) {
                        model = new Vector<NonRootModelElement>();
                    }
                    model.add((NonRootModelElement) o);
                } else if (o instanceof Component_c) {
                	if(model == null) {
                		model = new Vector<NonRootModelElement>();
                	}
                	model.add((NonRootModelElement) o);
                	NonRootModelElement[] children = BPDebugUtils.getComponentChildren((Component_c) o);
                	for(int i = 0; i < children.length; i++) {
                		model.add(children[i]);
                	}
                } else if (o instanceof ComponentReference_c) {
                	if(model == null) {
                		model = new Vector<NonRootModelElement>();
                	}
                	Component_c component = Component_c.getOneC_COnR4201((ComponentReference_c) o);
                	model.add(component);
                	NonRootModelElement[] children = BPDebugUtils.getComponentChildren(component);
                	for(int i = 0; i < children.length; i++) {
                		model.add(children[i]);
                	}                	
                } else if (o instanceof ComponentPackage_c) {
                	if(model == null) {
                		model = new Vector<NonRootModelElement>();
                	}
	            	ComponentPackage_c compPackage = (ComponentPackage_c) o;
	            	NonRootModelElement[] children = BPDebugUtils.getComponentPackageChildren(compPackage);
	            	for(int i = 0; i < children.length; i++) {
	            		model.add(children[i]);
	            	}
                } else if (o instanceof Package_c) {
                	if(model == null) {
                		model = new Vector<NonRootModelElement>();
                	}
	            	Package_c pkg = (Package_c) o;
            	    NonRootModelElement[] children = BPDebugUtils.getPackageChildren(pkg);
            	    for(int i = 0; i < children.length; i++) {
            		  model.add(children[i]);
            	    }
            	}
            }

            if (model != null) {
                launchMultipleModel(model.toArray(new NonRootModelElement[model.size()]));
            }
        }
    }

    protected void launchMultipleModel(NonRootModelElement[] elements) {
    	List<IFile> fileList = new ArrayList<IFile>();
    	for(int i = 0; i < elements.length; i++) {
    		if(elements[i] instanceof ComponentReference_c) {
    			// if the current element is an imported
    			// component add the referred to component's
    			// file and it's children's files
    			IFile[] files = getImportedComponentFiles((ComponentReference_c) elements[i]);
    			for(int j = 0; j < files.length; j++) {
    				fileList.add(files[j]);
    			}
    		} else {
    			// otherwise just add the file
    			fileList.add(elements[i].getFile());
    		}
    	}
    	launchSetting(fileList.toArray(new IFile[fileList.size()]), elements);
    }
    
	private IFile[] getImportedComponentFiles(ComponentReference_c icomponent) {
		List<IFile> fileList = new ArrayList<IFile>();
		Component_c component = Component_c.getOneC_COnR4201(icomponent);
		fileList.add(component.getFile());
		NonRootModelElement[] componentChildren = BPDebugUtils.getComponentChildren(component);
		for(int i = 0; i < componentChildren.length; i++) {
			if(componentChildren[i] instanceof ComponentReference_c) {
				IFile[] files = getImportedComponentFiles((ComponentReference_c) componentChildren[i]);
				for(int j = 0; j < files.length; j++) {
					fileList.add(files[j]);
				}
			} else {
				fileList.add(componentChildren[i].getFile());
			}
		}
		return fileList.toArray(new IFile[fileList.size()]);
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		// do nothing
    }
}
