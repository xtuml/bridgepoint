package com.mentor.nucleus.bp.debug.ui.launch;


//====================================================================
//
//File:      $RCSfile: LaunchShortcut.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2013/01/10 23:17:49 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.debug.ui.ILaunchShortcut;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;


public class LaunchShortcut implements ILaunchShortcut {
    /* the mode of launching */
    private String mode;

    /* the selected project */
    private IProject selectedProject;

    public LaunchShortcut() {
        super();
    }

    public void launch(ISelection selection, String mode) {
        this.mode = mode;

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sselect = (IStructuredSelection) selection;

            if (!sselect.isEmpty()) {
                Object context = sselect.getFirstElement();

                if (context instanceof IFile) {
                    IFile modelFile = ((IFile) context);
                    IContainer folder = modelFile.getParent();

                    if (folder instanceof IFolder &&
                            folder.getName().equals("models")) { //$NON-NLS-1$

                        if (modelFile.getName().endsWith("xtuml")) { //$NON-NLS-1$
                            selectedProject = modelFile.getProject();
                            launchModelFile(modelFile);
                        } else {
                            selectedProject = modelFile.getProject();

                            if (selectedProject != null) {
                                launchProject(selectedProject);
                            }
                        }
                    } else {
                        BPDebugUIPlugin.logError("The selected model is invalid.",
                            null);

                        IWorkbenchWindow win = PlatformUI.getWorkbench()
                                                         .getActiveWorkbenchWindow();

                        UIUtil.showMessageDialoginLaunch(win.getShell(),
                            "Verifier Launcher",
                            "The selected model is invalid.",
                            UIUtil.BPMessageTypes.ERROR);
                    }
                } else if ((context instanceof IProject)) {
                    if (context instanceof IProject) {
                        selectedProject = (IProject) context;

                        if (selectedProject != null) {
                            launchProject(selectedProject);
                        }
                    }
                }
            }
        }
    }

    public void launch(IEditorPart editor, String mode) {
        this.mode = mode;

        IEditorInput input = editor.getEditorInput();

        if (input instanceof IFileEditorInput) {
            IFile file = ((IFileEditorInput) input).getFile();
            IContainer folder = file.getParent();

            if (folder instanceof IFolder && folder.getName().equals("models")) { //$NON-NLS-1$

                IProject proj = ((IFileEditorInput) input).getFile().getProject();

                selectedProject = file.getProject();

                if (proj != null) {
                    launchModelFile(file);
                }
            } else {
                IWorkbenchWindow win = PlatformUI.getWorkbench()
                                                 .getActiveWorkbenchWindow();
                UIUtil.showMessageDialoginLaunch(win.getShell(),
                    "Verifier Launcher", "The selected model is invalid.",
                    UIUtil.BPMessageTypes.ERROR);
            }
        }
    }

    protected void launchSetting(IFile[] files, NonRootModelElement[] elements) {
        if ((elements == null) || (elements.length == 0)) {
            return;
        }

        if(files == null || files.length == 0) {
        	return;
        }
        
        if(elements == null) {
        	openLaunchConfigDialog();
        	return;
        }
        
        ILaunchConfiguration[] configs = getLaunchConfigurations();

        //if no launch config, open the config dialog
        if (configs.length == 0) {
            openLaunchConfigDialog();
        } else if (configs.length == 1) {
        	if(VerifierLaunchConfiguration.configurationHasExactElementsSelected(elements, configs[0])) {
        		updateLaunchConfig(configs[0]);
        	} else {
        		openLaunchConfigDialog();
        	}
        } else if (configs.length > 1) {
        	//check if there exists a launch for the same project
        	int index = 0;
        	boolean result = false;
        	
        	for (int i = 0; i < configs.length; i++) {
        		
        		// check that the configuration has only
        		// the given files selected
        		if(VerifierLaunchConfiguration.configurationHasExactElementsSelected(elements, configs[i])) {
        			index = i;
        			result = true;
        			break;
        		}
        	}
        	if(result) {
        		updateLaunchConfig(configs[index]);
        	} else {
        		// open the dialog to create a unique configuration
        		// related to the current selection
        		openLaunchConfigDialog();
        	}
        }
    }


	private void updateLaunchConfig(ILaunchConfiguration config) {
		DebugUITools.launch(config, mode);
    }

    public void launchProject(IProject project) {
        ILaunchConfiguration[] configs = getLaunchConfigurations();

        if (configs.length == 0) {
            openLaunchConfigDialog();
        } else if (configs.length == 1) {
			try {
				Map storedModelsMap = configs[0].getWorkingCopy().getAttribute(VerifierLaunchConfiguration.ATTR_SELECTEDMODELS,
					new HashMap());
				Set projectSet = storedModelsMap.keySet();
				Iterator iterator = projectSet.iterator();
	            while(iterator.hasNext()) {
	            	if (project.getName().equals(iterator.next())) {
	            		// found a selection with the given project
	            		// assume configuration is fully setup
	            		VerifierLaunchConfiguration.updateLaunchConfigForProject(project, configs[0], mode);
	            		return;
	            	}
	            }
			} catch (CoreException e) {
				CorePlugin.logError("Unable to get attribute value", e);
			} //$NON-NLS-1$
            openLaunchConfigDialog();
        } else if (configs.length > 1) {
            try {
                int index = 0;
                boolean foundConfig = false;
                for (int i = 0; i < configs.length; i++) {
                    Map storedModelsMap = configs[i].getWorkingCopy().getAttribute(VerifierLaunchConfiguration.ATTR_SELECTEDMODELS,
                            new HashMap()); //$NON-NLS-1$
                    Set projectSet = storedModelsMap.keySet();
                    Iterator iterator = projectSet.iterator();
                    // if the configuration has at least one
                    // element selected under this project
                    while(iterator.hasNext()) {
                        if (project.getName().equals(iterator.next())) {
                            index = i;
                            foundConfig = true;
                            break;
                        }
                    }
                }

                if(foundConfig)
                	VerifierLaunchConfiguration.updateLaunchConfigForProject(project, configs[index], mode);
                else
                	openLaunchConfigDialog();
            } catch (CoreException e) {
                BPDebugUIPlugin.logError("Unable to get attribute value", e);
            }
        }
    }

	public void launchModelFile(IFile modelFile) {
        IFile[] tmpFile = new IFile[1];
        tmpFile[0] = modelFile;

        launchSetting(tmpFile, null);
    }

	private int openLaunchConfigDialog() {
        ILaunchConfigurationType type = DebugPlugin.getDefault()
                                                   .getLaunchManager()
                                                   .getLaunchConfigurationType(VerifierLaunchConfiguration.LAUNCH_ID);

        ILaunchConfigurationWorkingCopy workingCopy = null;

        try {
            BPMainTabGroup bpGroup = new BPMainTabGroup();

            workingCopy = type.newInstance(null, bpGroup.getUniqueName());
        } catch (CoreException e) {
            BPDebugUIPlugin.logError("Unable to create ILaunchConfigurationWorkingCopy instance",
                e);
        }

        ILaunchGroup group = DebugUITools.getLaunchGroup(workingCopy, mode);

        int retcode = -1;

		try {
			Map<String, String> temp = new HashMap<String, String>();
			Selection selection = Selection.getInstance();
			NonRootModelElement[] selectedElements = selection
					.getSelectedNonRootModelElements();
			StringBuffer ids = new StringBuffer();
			for (int i = 0; i < selectedElements.length; i++) {
				ids.append(VerifierLaunchConfiguration.getVerifiableElementId(selectedElements[i])
						.toString());
			}
			if (ids.length() == 0) {
				retcode = DebugUITools.openLaunchConfigurationDialog(Display
						.getDefault().getActiveShell(), workingCopy, group
						.getIdentifier(), null);
				selection = Selection.getInstance();
				selectedElements = selection.getSelectedNonRootModelElements();
				for (int i = 0; i < selectedElements.length; i++) {
					ids.append(VerifierLaunchConfiguration.getVerifiableElementId(selectedElements[i])
							.toString());
				}
			}
			for (int i = 0; i < selectedElements.length; i++) {
				SystemModel_c system = getSystemForSelectedElement(selectedElements[i]);
			temp.put(system.getName(), ids.toString());
			// selection the current BP selection in the configuration
			workingCopy.setAttribute(
					VerifierLaunchConfiguration.ATTR_SELECTEDMODELS, temp);
			}
			if (retcode == -1) {
				retcode = DebugUITools.openLaunchConfigurationDialog(Display
						.getDefault().getActiveShell(), workingCopy, group
						.getIdentifier(), null);
			}
			// The return code will be Window.OK or Window.CANCEL. We only want
			// to
			// save this configuration if the user selected ok
			if (retcode == Window.OK) {
				workingCopy.doSave();
			}
		} catch (CoreException e) {
            BPDebugUIPlugin.logError("Unable to save ILaunchConfigurationWorkingCopy instance",
                e);
        }

        return retcode;
    }

    private SystemModel_c getSystemForSelectedElement(NonRootModelElement element) {
    	if (element instanceof Package_c) {
    		return SystemModel_c.getOneS_SYSOnR1405((Package_c)element);
    	}
    	else if (element instanceof Component_c) {
			SystemModel_c system = SystemModel_c.getOneS_SYSOnR4606(
					               ComponentPackage_c.getOneCP_CPOnR4608(
							                            (Component_c) element));
			if (system != null) {
				return system;
			}
			else {
				Package_c pkg = Package_c.getOneEP_PKGOnR8000(
						PackageableElement_c.getManyPE_PEsOnR8001(
								                         (Component_c)element));
				if (pkg != null) {
					return getSystemForSelectedElement(pkg);
				}
			    Component_c comp = Component_c.getOneC_COnR8003(
					PackageableElement_c.getManyPE_PEsOnR8001(
							                             (Component_c)element));
			    if (comp != null) {
				  return getSystemForSelectedElement(comp);
			    }
			}
		} else if (element instanceof ComponentPackage_c) {
			return SystemModel_c
					.getOneS_SYSOnR4606((ComponentPackage_c) element);
		} else if (element instanceof ComponentReference_c) {
			Package_c pkg = Package_c.getOneEP_PKGOnR8000(
					PackageableElement_c.getManyPE_PEsOnR8001(
							                    (ComponentReference_c)element));
			if (pkg != null) {
				return getSystemForSelectedElement(pkg);
			}
			Component_c comp = Component_c.getOneC_COnR8003(
					PackageableElement_c.getManyPE_PEsOnR8001(
							                    (ComponentReference_c)element));
			if (comp != null) {
				return getSystemForSelectedElement(comp);
			}
			return getSystemForSelectedElement(Component_c
							 .getOneC_COnR4201((ComponentReference_c) element));
		} else if (element instanceof Domain_c) {
			return SystemModel_c.getOneS_SYSOnR28((Domain_c) element);
		} else if (element instanceof SystemModel_c) {
			return (SystemModel_c)element;
		}
		return null;
	}


	private ILaunchConfiguration[] getLaunchConfigurations() {
        ILaunchConfiguration[] configs = new ILaunchConfiguration[0];
        ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
        ILaunchConfigurationType type = manager.getLaunchConfigurationType(VerifierLaunchConfiguration.LAUNCH_ID);
        Vector<ILaunchConfiguration> configsList = new Vector<ILaunchConfiguration>();

        try {
            configs = manager.getLaunchConfigurations(type);

            int index = 0;

            for (int i = 0; i < configs.length; i++) {
                Map modelsmap = configs[i].getAttribute(VerifierLaunchConfiguration.ATTR_SELECTEDMODELS,
                        new Hashtable());

                if (modelsmap.size() > 0) {
                    configsList.add(index, (ILaunchConfiguration) configs[i]);
                    index++;
                }
            }
        } catch (CoreException e) {
            BPDebugUIPlugin.logError("Unable to get attribute value", e);

            return configs;
        }

        configs = new ILaunchConfiguration[configsList.size()];

        return (ILaunchConfiguration[]) configsList.toArray(configs);
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void dispose() {
    }
}
