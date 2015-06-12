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

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.xtuml.bp.core.CorePlugin;


public class BPClassLoader extends URLClassLoader {
	
	public BPClassLoader(String[] classpath) {
		super(null);
		path = classpath;
		initialize();
	}

	private String[] path;
	
	public void initialize() {
        for (int i = 0; i < path.length; i++) {
          if (path[i].startsWith("external:")) {
            String filePath = path[i].replaceFirst("external:", "");
            addClassPathEntry(filePath);
          }
	    }
	}
	
	public void addClassPathEntry(String path) {
		try {
			addURL(new URL(path));
		} catch (MalformedURLException e) {
			CorePlugin.logError("Incorrect URL, " + path, e);
		}
	}

}