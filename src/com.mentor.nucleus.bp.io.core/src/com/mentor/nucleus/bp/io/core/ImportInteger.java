//========================================================================
//
//File:      $RCSfile: ImportInteger.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2012/01/23 21:25:21 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package com.mentor.nucleus.bp.io.core;

public class ImportInteger {
	public static boolean isInt(Object arg) {
		if (arg instanceof String && !((String) arg).isEmpty()) {
			try {
				Integer.parseInt((String) arg);
				return true; // Parsed as an integer, return true
			} catch (NumberFormatException nfe) {
				// Failed to parse as an integer, drop though and return false
			}
		}
		return false;
	}
}
