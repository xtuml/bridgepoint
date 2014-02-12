//=====================================================================
//
//File:      $RCSfile: OoaofgraphicsUtil.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2012/10/15 22:08:49 $
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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;

/**
 * Utility methods that access Ooaofgraphic instances, 
 * which we can't access directly from this plugin, as
 * that would create a dependency loop.
 */
public class OoaofgraphicsUtil
{
    /**
     * Retrieves and returns the Ooaofgraphics class from the 
     * canvas plugin, without making any specific by-name reference
     * to that class.
     */
    public static Class getGraphicsClass()
    {
        // get the Ooaofgraphics class from the canvas plugin
        final String packageName = "com.mentor.nucleus.bp.ui.canvas"; //$NON-NLS-1$
        Bundle bundle = Platform.getBundle(packageName);
        try {
            return bundle.loadClass(
                packageName + "." + "Ooaofgraphics"); //$NON-NLS-1$ //$NON-NLS-2$
        } catch (ClassNotFoundException e) {
            CorePlugin.logError("Could not retrieve Ooaofgraphics class", e);
            return null;
        }
    }
    
    /**
     * Retrieves and returns the canvas paste action class
     */
    public static Class<?> getCanvasPasteActionClass() {
        // get the Ooaofgraphics class from the canvas plugin
        final String packageName = "com.mentor.nucleus.bp.ui.graphics"; //$NON-NLS-1$
        Bundle bundle = Platform.getBundle(packageName);
        try {
            return bundle.loadClass(
                packageName + ".actions." + "CanvasPasteAction"); //$NON-NLS-1$ //$NON-NLS-2$
        } catch (ClassNotFoundException e) {
            CorePlugin.logError("Could not retrieve CanvasPasteAction class", e);
            return null;
        }    	
    }
    
    /**
     * Retrieves and returns the graphics-root that corresponds with 
     * the model-root of the given ID, through reflection on the given 
     * graphics-class.
     */
    public static Object getGraphicsRoot(String modelRootId, Class graphicsClass)
    {
        try {
            // from the given graphics-class, get the graphics-root 
            // corresponding to the given model-root
            Method getInstanceMethod = graphicsClass.getMethod("getInstance", //$NON-NLS-1$
                new Class[] {modelRootId.getClass()});
            return getInstanceMethod.invoke(
                null, new Object[] {modelRootId});
        } 
        catch (Exception e) {
            CorePlugin.logError("Could not retrieve graphics root", e);
            return null;
        }
    }
    
    /**
     * Clears the database of the graphics-root corresponding 
     * with the model-root of the given ID, employing the given progress monitor.
     */
    public static void clearGraphicsDatabase(String modelRootId, 
        IProgressMonitor monitor)
    {
        try {
            // clear the database of the graphics-root; note that we 
            // have to specify the IProgressMonitor interface by name, rather
            // than simply getting the class of the given progress monitor,
            // since the parameter is typed to the interface and not the class
            Class graphicsClass = getGraphicsClass();
            Method clearDatabaseMethod = graphicsClass.getMethod("clearDatabase", //$NON-NLS-1$ 
                new Class[] {Class.forName("org.eclipse.core.runtime.IProgressMonitor")}); //$NON-NLS-1$
            clearDatabaseMethod.invoke(
                getGraphicsRoot(modelRootId, graphicsClass), 
                new Object[] {monitor});
        } 
        catch (Exception e) {
            CorePlugin.logError("Could not clear graphics database", e); 
        }
    }

    /**
     * Sets the id of the graphics-root that corresponds to the 
     * given model-root.
     */
    public static void setId(String oldModelRootId, String newModelRootId) 
    {
        try {
            // set the id of the graphics-root to that given
            Class graphicsClass = getGraphicsClass();
            Method setIdMethod = graphicsClass.getMethod("setId", //$NON-NLS-1$ 
                new Class[] {oldModelRootId.getClass()});
            setIdMethod.invoke(
                getGraphicsRoot(oldModelRootId, graphicsClass), 
                new Object[] {newModelRootId});
        } 
        catch (Exception e) {
            CorePlugin.logError("Could not set graphics-root ID", e); 
        }
    }
  public static void checkGraphicsConsistency(String modelRootId){
    try {
        Class graphicsClass = getGraphicsClass();
        Method checkDatabaseMethod = graphicsClass.getMethod("checkDomainConsistency", //$NON-NLS-1$
            new Class[] {}); //$NON-NLS-1$
        checkDatabaseMethod.invoke(
            getGraphicsRoot(modelRootId, graphicsClass),
            new Object[] {});
    }
    catch (Exception e) {
        CorePlugin.logError("Could not check graphics domain consistency", e);
    }

  }

	public static void deleteGraphicsRoot(String id) {
		// get the OoaofgraphicsBase class from the canvas plugin
		final String packageName = "com.mentor.nucleus.bp.ui.canvas"; //$NON-NLS-1$
		Bundle bundle = Platform.getBundle(packageName);
		try {
			Class<?> baseClass = bundle.loadClass(packageName
					+ "." + "OoaofgraphicsBase"); //$NON-NLS-1$ //$NON-NLS-2$
			Method deleteMethod = baseClass.getMethod("delete",
					new Class<?>[] {});
			deleteMethod.invoke(getGraphicsRoot(id, getGraphicsClass()),
					new Object[] {});
		} catch (ClassNotFoundException e) {
			CorePlugin
					.logError("Could not retrieve OoaofgraphicsBase class", e);
		} catch (SecurityException e) {
			CorePlugin
					.logError(
							"Could not retrieve delete method from OoaofgraphicsBase class",
							e);
		} catch (NoSuchMethodException e) {
			CorePlugin
					.logError(
							"Could not retrieve delete method from OoaofgraphicsBase class",
							e);
		} catch (IllegalArgumentException e) {
			CorePlugin
					.logError(
							"Could not invoke delete method from OoaofgraphicsBase class",
							e);
		} catch (IllegalAccessException e) {
			CorePlugin
					.logError(
							"Could not invoke delete method from OoaofgraphicsBase class",
							e);
		} catch (InvocationTargetException e) {
			CorePlugin
					.logError(
							"Could not invoke delete method from OoaofgraphicsBase class",
							e);
		}
	}

	public static void addInstance(Object rootObject) {
		final String packageName = "com.mentor.nucleus.bp.ui.canvas"; //$NON-NLS-1$
		Bundle bundle = Platform.getBundle(packageName);
		Class<?> baseClass;
		try {
			baseClass = bundle.loadClass(packageName
					+ "." + "OoaofgraphicsBase"); //$NON-NLS-1$
			Method addInstanceMethod = baseClass.getMethod("addInstance", //$NON-NLS-1$
					new Class[] { getGraphicsClass() });
			addInstanceMethod.invoke(null, new Object[] { rootObject });
		} catch (ClassNotFoundException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		} catch (SecurityException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		} catch (NoSuchMethodException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		} catch (IllegalArgumentException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		} catch (IllegalAccessException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		}
	}

	public static Object findInstance(String id) {
		final String packageName = "com.mentor.nucleus.bp.ui.canvas"; //$NON-NLS-1$
		Bundle bundle = Platform.getBundle(packageName);
		Class<?> baseClass;
		try {
			baseClass = bundle.loadClass(packageName
					+ "." + "OoaofgraphicsBase"); //$NON-NLS-1$
			Method findInstanceMethod = baseClass.getMethod("findInstance", //$NON-NLS-1$
					new Class[] { String.class });
			return findInstanceMethod.invoke(null, new Object[] { id });
		} catch (ClassNotFoundException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		} catch (SecurityException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		} catch (NoSuchMethodException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		} catch (IllegalArgumentException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		} catch (IllegalAccessException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Unable to add graphical root to map.", e);
		}
		return null;
	}

}
