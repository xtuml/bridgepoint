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

import java.io.InputStream;


public class BPClassLoader {
	
	public BPClassLoader()
	{
	}

	
	public void initialize() {
	}
	public void addClassPathEntry(String path) {
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
	  return null;
	}
	

	public static void resetTheDefinitionsCache()
	{
	}
	
	private Class<?> findClassImpl(String name) {
	  return null;
	}
	
	public InputStream getResourceAsStream(String name) {
		return null;
	}
}