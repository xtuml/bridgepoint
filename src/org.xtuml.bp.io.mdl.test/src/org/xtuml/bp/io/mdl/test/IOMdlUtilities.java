//=====================================================================
//
//File:      $RCSfile: IOMdlUtilities.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/01/10 23:12:53 $
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
package org.xtuml.bp.io.mdl.test;

import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;

public class IOMdlUtilities {

public class Package_by_name_c implements ClassQueryInterface_c {
	public boolean evaluate(Object candidate) {
		Package_c selected = (Package_c) candidate;
		return (selected.getName().equals(m_name));
	}
  Package_by_name_c(String name) {
	  m_name = name;
  }
  private String m_name;
}

}