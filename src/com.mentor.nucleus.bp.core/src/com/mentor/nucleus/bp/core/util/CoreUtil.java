//=====================================================================
//
//File:      $RCSfile: CoreUtil.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2012/06/21 02:38:52 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;

/**
 * Utility methods related to Core
 */
public class CoreUtil {
    public static boolean IsRunningHeadless = false;
	
    public static String getName(NonRootModelElement element) {
        String result = "";
        Class inputClass = element.getClass();
        Class[] type = new Class[0];
        Method getName = null;
        try {
            try {
                getName = inputClass.getMethod("getName", type); //$NON-NLS-1$
            } catch (NoSuchMethodException e) {
                // There is no accessor, try an explicit operation . . .
                getName = inputClass.getMethod("Get_name", type); //$NON-NLS-1$
            }
            Object[] args = new Object[0];
            result = (String) getName.invoke(element, args);
        } catch (Exception e) {

        }
        return result;
    }

    public static String getModelElementType(NonRootModelElement element) {
        IPersistenceHierarchyMetaData metaData = PersistenceManager
                .getHierarchyMetaData();
        return metaData.getComponentType(element);
    }

    /**
     * This is a delegate method for ui.canvas.GraphicsUtil#getCanvasEditorTitle
     * to remove cyclic dependencies
     */
    public static String getCanvasEditorTitle(NonRootModelElement element) {
        String title = null;
        Class graphicsUtilClass = null;
        Object graphicsUtilObj = null;
        try {
            Bundle ui_canvas = Platform
                    .getBundle("com.mentor.nucleus.bp.ui.canvas");//$NON-NLS-1$
            graphicsUtilClass = ui_canvas
                    .loadClass("com.mentor.nucleus.bp.ui.canvas.util.GraphicsUtil"); //$NON-NLS-1$
            graphicsUtilObj = graphicsUtilClass.newInstance();
        } catch (Exception cnf) {
            CorePlugin.logError("Problem accessing GraphicsUtil class ", cnf); //$NON-NLS-1$
            return title;
        }

        Class[] type;
        try {
            type = new Class[] { NonRootModelElement.class };

            Method getTitle = graphicsUtilClass.getMethod(
                    "getCanvasEditorTitle", type); //$NON-NLS-1$
            Object[] args = new Object[] { element };
            title = (String) getTitle.invoke(graphicsUtilObj, args);
        } catch (Exception e) {
            CorePlugin
                    .logError(
                            "Error invoking getCanvasEditorTitle(NRME) in GraphicsUtil  ", e); //$NON-NLS-1$
        }

        return title;
    }

    /**
     * This is a delegate method for ui.canvas.GraphicsUtil#getCanvasEditorPostTitle
     * to remove cyclic dependencies
     */
    public static String getCanvasEditorPostTitle(NonRootModelElement element, boolean checkElementToEdit) {
        String title = null;
        Class graphicsUtilClass = null;
        Object graphicsUtilObj = null;
        try {
            Bundle ui_canvas = Platform
                    .getBundle("com.mentor.nucleus.bp.ui.canvas");//$NON-NLS-1$
            graphicsUtilClass = ui_canvas
                    .loadClass("com.mentor.nucleus.bp.ui.canvas.util.GraphicsUtil"); //$NON-NLS-1$
            graphicsUtilObj = graphicsUtilClass.newInstance();
        } catch (Exception cnf) {
            CorePlugin.logError("Problem accessing GraphicsUtil class ", cnf); //$NON-NLS-1$
            return title;
        }

        Class[] type;
        try {
            type = new Class[] { NonRootModelElement.class, boolean.class };

            Method getTitle = graphicsUtilClass.getMethod(
                    "getCanvasEditorPostTitle", type); //$NON-NLS-1$
            Object[] args = new Object[] { element, checkElementToEdit };
            title = (String) getTitle.invoke(graphicsUtilObj, args);
        } catch (Exception e) {
            CorePlugin
                    .logError(
                            "Error invoking getCanvasEditorTitle(NRME) in GraphicsUtil  ", e); //$NON-NLS-1$
        }

        return title;
    }
    
    
    /**
     * A utility function that gets the canvas title and strips-off the
     * instance name returning just the diagram type.  This uses
     * getCanvasEditorPostTitle()
     * 
     * @see getCanvasEditorPostTitle()
     * @return Diagram type string as defined in the canvas plugin.xml 
     */
    public static String getDiagramTypeFromCanvasTitle(
			NonRootModelElement nrme, boolean checkElementToEdit) {
		String typeName = "";
		if (nrme != null) {
			String tempName = CoreUtil.getCanvasEditorPostTitle(nrme, true);
			if (tempName != null) {
				int firstColon = tempName.indexOf(':');
				if (firstColon > -1) {
					tempName = tempName.substring(firstColon+1);					
				}
				typeName = tempName;
			}
		}
		return typeName.trim();
    }
    
    public static boolean isGraphicalRepresentable(NonRootModelElement element) {
        // we should use more accurate method to determins
        Class inputClass = element.getClass();
        Class[] type = new Class[0];
        Method get_ooa_id = null;

        try {
            get_ooa_id = inputClass.getMethod("Get_ooa_id", type); //$NON-NLS-1$
            Object[] args = new Object[0];
            Object id = get_ooa_id.invoke(element, args);
            if (id != null) {
                if (((Long) id).longValue() > 0) {
                    return true;
				}
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static PersistableModelComponent getComponentOfElementInResize(NonRootModelElement element) {
        PersistableModelComponent comp = null;
        Class graphicsUtilClass = null;
        Object graphicsUtilObj = null;
        try {
            Bundle ui_canvas = Platform
                    .getBundle("com.mentor.nucleus.bp.ui.canvas");//$NON-NLS-1$
            graphicsUtilClass = ui_canvas
                    .loadClass("com.mentor.nucleus.bp.ui.canvas.util.GraphicsUtil"); //$NON-NLS-1$
            graphicsUtilObj = graphicsUtilClass.newInstance();
        } catch (Exception cnf) {
            CorePlugin.logError("Problem accessing GraphicsUtil class ", cnf); //$NON-NLS-1$
            return comp;
        }

        Class[] type;
        try {
            type = new Class[] { NonRootModelElement.class };

            Method getTitle = graphicsUtilClass.getMethod(
                    "getComponentOfElementInResize", type); //$NON-NLS-1$
            Object[] args = new Object[] { element };
            comp = (PersistableModelComponent) getTitle.invoke(graphicsUtilObj, args);
        } catch (Exception e) {
            CorePlugin
                    .logError(
                            "Error invoking getComponentOfElementInResize(NonRootModelElement) in GraphicsUtil  ", e); //$NON-NLS-1$
        }
        return comp;
    }
    
    public static boolean supportsPaste(Object target, String child, boolean checkCanPasteOperation) {
		String elementToPaste = child.replaceAll("_c", "").toLowerCase();
		boolean supportsPaste = hasMethod(target, "Paste" + elementToPaste, new Class[] {UUID.class}); //$NON-NLS-1$
		// the copy/cut methods use this to determine if a paste
		// operation exists, but they do not care if a Canpaste*
		// operation returns false as that is not an architectural
		// limitation
		if (supportsPaste && checkCanPasteOperation) {
			try {
				// See if there is a canPaste<ME> operation for this situation and if there is, call it and use it's 
				// result to determine if the paste is allowed
				Method method = target.getClass().getMethod("Canpaste" + elementToPaste, new Class[] { }); //$NON-NLS-1$
				Object object = method.invoke(target, new Object[] {});
				supportsPaste = ((Boolean) object).booleanValue();
			} catch (Throwable err) { 
				// Ignore
			}
		}
		return supportsPaste;
    }
    
    public static boolean supportsCopy(Object source, Object child) {
		if(source == null || child == null) return false;
		
		String simpleClassName = child.getClass().getSimpleName();
		String classPart = simpleClassName.substring(0, simpleClassName.length() - 2).toLowerCase();
		boolean canCopy = true;
		
		// Copy is allowed if there is a supporting paste for the
		// current destination, and if a canCopy* operation exists and
		// returns true
		try {
// NOTE: I am breaking our rules and leaving this commented-out code in place 
//			for now.  This block turns-off cut/copy/paste support for 
//			specialized packages.   When SPs were removed from the UI with 
//			dts0100889208 this was 	considered, but testing fallout was too large.
//			
//			// PE_PE navigation is present (isInGenericPackage).  Do not remove this comment.
//			//
//			// Don't allow cut/copy/paste on any specialized packages
//			// Note: This is being done as a part of SP removal.  During this
//			//       change the ability to create new SPs was removed from the
//			//       UI, and with this we can not allow copy/paste of these
//			//       elements.  When SPs are removed from the meta-model this 
//			//       check can be removed.
//			if (!(child instanceof Package_c) && !((NonRootModelElement) child).isInGenericPackage()) {
//				return false;
//			}
			
			Method method = source.getClass().getMethod("Cancopy" + classPart, new Class[] {UUID.class}); //$NON-NLS-1$
			Object object = method.invoke(source, new Object[] {((NonRootModelElement) child).Get_ooa_id()});
			canCopy = ((Boolean) object).booleanValue();
			return canCopy;
		} catch (SecurityException e) {
			// Allow this
		} catch (NoSuchMethodException e) {
			// Allow this
		} catch (IllegalArgumentException e) {
			canCopy = false;
		} catch (IllegalAccessException e) {
			canCopy = false;
		} catch (InvocationTargetException e) {
			canCopy = false;
		}
		// check for a paste operation (if we got here then
		// a paste* operation may exist but a canCopy* did
		// not
		return supportsPaste(source, child.getClass().getSimpleName(), false);
    }

	public static boolean hasMethod(Object target, String method,
			Class<?>[] args) {
		  try {
			    // execute the reflection call
		        target.getClass().getMethod(method, args);
		  } catch (NoSuchMethodException e) {
			    // if there was a no such method exception
			    // return false
			  	return false;
		  }
		  // otherwise return true
		  return true;
	}

	public static void loadWorkspace(IProgressMonitor monitor) throws CoreException {
		// this will cause initialization if necessary
		PersistenceManager.getDefaultInstance();
		// gather all system components, and load
		// the children
		IProject[] projects = getProjectsInWorkspace();
		monitor.subTask("Loading workspace...");
		for(int i = 0; i < projects.length; i++) {
			PersistableModelComponent rootComponent = PersistenceManager
					.getRootComponent(projects[i]);
			loadComponentAndChildren(monitor, rootComponent);
			if (monitor.isCanceled()) {
				// user cancelled, return
				return;
			}
		}
		monitor.done();
	}
	
	private static void loadComponentAndChildren(IProgressMonitor monitor,
			PersistableModelComponent component) {
		if(component == null) {
			monitor.worked(1);
			return;
		}
		if (monitor.isCanceled()) {
			return;
		}
		if (!component.isLoaded()) {
			try {
				monitor.subTask("Loading file: "
						+ component.getFile().getFullPath());
				component.load(new NullProgressMonitor());
			} catch (CoreException e) {
				CorePlugin.logError("", e);
			}
		}
		monitor.worked(1);
		// now get the children and load
		Collection<?> children = component.getChildren();
		Iterator<?> iterator = children.iterator();
		while (iterator.hasNext()) {
			PersistableModelComponent child = (PersistableModelComponent) iterator
					.next();
			loadComponentAndChildren(monitor, child);
		}
	}

	public static IProject[] getProjectsInWorkspace() throws CoreException {
		List<IProject> projectList = new ArrayList<IProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
			.getProjects();
		for (int i = 0; i < projects.length; i++) {
			if (projects[i].isOpen() && projects[i].exists()
					&& projects[i].hasNature(XtUMLNature.ID)) {
				projectList.add(projects[i]);
			}
		}
		return projectList.toArray(new IProject[projectList.size()]);
	}

	public static int getChildrenCountOfProjects(IProject[] projects) throws CoreException {
		int count = 0;
		for(int i = 0; i < projects.length; i++) {
			if (projects[i].isOpen() && projects[i].exists()
					&& projects[i].hasNature(XtUMLNature.ID)) {
				// get the models folder, and count the files under it
				// that end in the model extension
				IFolder models = projects[i].getFolder(Ooaofooa.MODELS_DIRNAME);
				if(models.exists()) {
					count += getChildrenCountOfFolder(models);
				}
			}
		}
		return count;
	}

	private static int getChildrenCountOfFolder(IFolder folder) throws CoreException {
		// get the models folder, and count the files under it
		// that end in the model extension
		int count = 0;
		IResource[] children = folder.members();
		for(int i = 0; i < children.length; i++) {
			if(children[i] instanceof IFile) {
				count++;
			} else {
				count = count + getChildrenCountOfFolder((IFolder) children[i]);
			}
		}
		return count;
	}

}

