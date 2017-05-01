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

package org.xtuml.bp.core.util;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;



import org.xtuml.bp.core.CorePlugin;


public class BPClassLoader extends ClassLoader {
	
	public BPClassLoader(String[] classpath, ClassLoader parent)
	{
      super( parent);
      ClassPathEntries = new ArrayList<String>();
      for (int i = 0; i < classpath.length; i++) {
    	  addClassPathEntry(classpath[i]);
	}
	}

	private ArrayList<String> ClassPathEntries;
	
	public void addClassPathEntry(String path) {
		synchronized(ClassPathEntries) {
			if (ClassPathEntries.indexOf(path) == -1) {
				ClassPathEntries.add(path);
			}
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
      
      throw new ClassNotFoundException();
	}
	
	private static Map<String, Class<?>> definitions = Collections.synchronizedMap(new HashMap<String, Class<?>>());

	public static void resetTheDefinitionsCache()
	{
 		definitions.clear();
	}
	
	@SuppressWarnings("restriction")
	private Class<?> findClassImpl(String name) {
		
		String filename = name.replace('.', '/').concat(".class"); //$NON-NLS-1$
		synchronized(ClassPathEntries) {
		Iterator<String> cpIt = ClassPathEntries.iterator();
		while (cpIt.hasNext()) {
			File cpe = new File(cpIt.next());
			File file = new File(cpe, filename);
			if (file.isFile() && file.exists()) {
				if (definitions.containsKey(file.toString())) {
				    return definitions.get(file.toString());
				}
			    byte[] classbytes;
			    try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
				  classbytes = new byte[(int) raf.length()];
				  raf.read(classbytes);
				  // raf is auto closed
			    } catch (IOException e) {
				  return null;
			    }

			    try {
			      Class<?> result = defineClass(name, classbytes, 0, classbytes.length);
			        definitions.put(file.toString(), result);
					  return result;
			    } catch (Error e) {
				throw e;
			  }
			      catch (Exception e) {
						CorePlugin.logError("Exception in BP Class loader defining class " + name, e); //$NON-NLS-1$
			      }
		    }
		}
			
		}
	  return null;
	}
	
	public InputStream getResourceAsStream(String name) {
		synchronized (ClassPathEntries) {
		Iterator<String> cpIt = ClassPathEntries.iterator();
		while (cpIt.hasNext()) {
			File cpe = new File(cpIt.next());
			File file = new File(cpe, name);
			if (file.isFile() && file.exists()) {
				try {
					return new FileInputStream(file);
				}
				catch (IOException ioe) {
					CorePlugin.out.println("Exception loading required resource:" + ioe.toString());
				}
			}
		}
		}
		return super.getResourceAsStream(name);
	}
}