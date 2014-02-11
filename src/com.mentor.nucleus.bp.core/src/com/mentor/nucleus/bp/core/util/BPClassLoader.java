//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.eclipse.osgi.baseadaptor.bundlefile.BundleEntry;
import org.eclipse.osgi.baseadaptor.loader.ClasspathEntry;
import org.eclipse.osgi.baseadaptor.loader.ClasspathManager;
import org.eclipse.osgi.framework.debug.Debug;
import org.eclipse.osgi.internal.baseadaptor.DefaultClassLoader;

import com.mentor.nucleus.bp.core.CorePlugin;


public class BPClassLoader extends DefaultClassLoader {
	
	public BPClassLoader(String[] classpath, DefaultClassLoader parent)
	{
      super( parent, 
             parent.getDelegate(), 
             EclipseStarter.class.getProtectionDomain(), 
             parent.getClasspathManager().getBaseData(), 
             classpath);
      path = classpath;
	}

	private String [] path;
	private ArrayList<ClasspathEntry> cpeList = new ArrayList<ClasspathEntry>();
	
	public void initialize() {
		super.initialize();
        for (int i = 0; i < path.length; i++) {
          if (path[i].startsWith("external:")) {
            String filePath = path[i].replaceFirst("external:", "");
            addClassPathEntry(filePath);
          }
	    }
	}
	public void addClassPathEntry(String path) {
		ClasspathManager cpm = getClasspathManager();
        ClasspathEntry cpe = cpm.getExternalClassPath(path,
                cpm.getBaseData(), EclipseStarter.class.getProtectionDomain());
        if (cpe != null) {   
           File newCandidate = cpe.getBundleFile().getBaseFile();
       	   for (ClasspathEntry existing: cpeList) {
       		   File existingFile = existing.getBundleFile().getBaseFile();
       		   if (existingFile.equals(newCandidate)) {
       			   return;
       		   }
           }
           cpeList.add(cpe);
        }
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
      Class<?> clazz = null;
      clazz = findClassImpl(name);
      if (clazz != null) {
          return clazz;
      }
      try {
        clazz = super.loadClass(name);
      }
      catch (ClassNotFoundException e) {
        // Normal behavior
      }
      catch (NoClassDefFoundError e) {
        // Normal behavior
      }
      if (clazz != null) {
        return clazz;
      }

      clazz = findLocalClass(name);
	  return clazz;
	}
	
	private static Map<String, Class<?>> definitions = Collections.synchronizedMap(new HashMap<String, Class<?>>());

	public static void resetTheDefinitionsCache()
	{
		definitions.clear();
	}
	
	private Class<?> findClassImpl(String name) {
		if (Debug.DEBUG_ENABLED && Debug.DEBUG_LOADER)
			Debug.println("BPClassLoader[" + "].findClass(" + name + ")"); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
		
		String filename = name.replace('.', '/').concat(".class"); //$NON-NLS-1$
		Iterator<ClasspathEntry> cpIt = cpeList.iterator();
		while (cpIt.hasNext()) {
		  ClasspathEntry cp = cpIt.next();
		  BundleEntry entry = cp.getBundleFile().getEntry(filename);
		  if (entry != null) {
			if (definitions.containsKey(entry.getFileURL().toString())) {
			    return definitions.get(entry.getFileURL().toString());
			}
		    byte[] classbytes;
		    try {
			  classbytes = entry.getBytes();
		    } catch (IOException e) {
			  if (Debug.DEBUG_ENABLED && Debug.DEBUG_LOADER)
				Debug.println("  IOException reading " + filename + " from "); //$NON-NLS-1$ //$NON-NLS-2$
			  return null;
		    }

		    if (Debug.DEBUG_ENABLED && Debug.DEBUG_LOADER) {
			  Debug.println("  read " + classbytes.length + " bytes from " + filename); //$NON-NLS-1$ //$NON-NLS-2$
			  Debug.println("  defining class " + name); //$NON-NLS-1$
		    }

		    try {
		      Class<?> result = defineClass(name, classbytes, cp, entry);
		        definitions.put(entry.getFileURL().toString(), result);
				  return result;
		    } catch (Error e) {
			if (Debug.DEBUG_ENABLED && Debug.DEBUG_LOADER)
				Debug.println("  error defining class " + name); //$NON-NLS-1$
			throw e;
		  }
		      catch (Exception e) {
					CorePlugin.logError("Exception in BP Class loader defining class " + name, e); //$NON-NLS-1$
		      }
	    }
	  }
	  return null;
	}
	
	public InputStream getResourceAsStream(String name) {
		Iterator<ClasspathEntry> cpIt = cpeList.iterator();
		while (cpIt.hasNext()) {
		  ClasspathEntry cp = cpIt.next();
		  BundleEntry entry = cp.getBundleFile().getEntry(name);
		  if (entry != null) {
			  try {
			    return entry.getInputStream();
			  }
			  catch (IOException ioe) {
					CorePlugin.out.println("Exception loading required resource:" + ioe.toString());
			  }
		  }
		}
		return super.getResourceAsStream(name);
	}
}