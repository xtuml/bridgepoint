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
package com.mentor.nucleus.bp.io.mdl.test;

import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;

public class IOMdlUtilities {

public class DataTypePackage_by_name_c implements ClassQueryInterface_c {
	public boolean evaluate(Object candidate) {
		DataTypePackage_c selected = (DataTypePackage_c) candidate;
		return (selected.getName().equals(m_name));
	}
  DataTypePackage_by_name_c(String name) {
	  m_name = name;
  }
  private String m_name;
}
public class FunctionPackage_by_name_c implements ClassQueryInterface_c {
	public boolean evaluate(Object candidate) {
		FunctionPackage_c selected = (FunctionPackage_c) candidate;
		return (selected.getName().equals(m_name));
	}
  FunctionPackage_by_name_c(String name) {
	  m_name = name;
  }
  private String m_name;
}
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
public class ExternalEntityPackage_by_name_c implements ClassQueryInterface_c {
	public boolean evaluate(Object candidate) {
		ExternalEntityPackage_c selected = (ExternalEntityPackage_c) candidate;
		return (selected.getName().equals(m_name));
	}
  ExternalEntityPackage_by_name_c(String name) {
	  m_name = name;
  }
  private String m_name;
}
public class Subsystem_by_name_c implements ClassQueryInterface_c {
	public boolean evaluate(Object candidate) {
		Subsystem_c selected = (Subsystem_c) candidate;
		return (selected.getName().equals(m_name));
	}
  Subsystem_by_name_c(String name) {
	  m_name = name;
  }
  private String m_name;
}

}