//=====================================================================
//
//File:      $RCSfile $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 22:54:10 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.common;

import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.Enumerator_c;

public class InputValueValidator {
    /*
     * Validate the input against the underlying type.
     */
    public static String isValid(DataType_c dt, String newValue) {
        String dtName = dt.Getcoredatatypename();

        if (!dtName.equals("string") && newValue.contains(" ")) {
            return "Numeric values cannot contain spaces.";
        }

        if (dtName.equals("integer")) {
            try {
                int val = 0;
                if (newValue.startsWith("0b")) {
                    val = Integer.parseInt(newValue.substring(2), 2);
                } else if (newValue.startsWith("0x")) {
                    val = Integer.parseInt(newValue.substring(2), 16);
                } else {
                    val = Integer.parseInt(newValue);
                }
                val = val + 0; // Stop compiler warnings
            } catch (NumberFormatException nfe) {
                return "Invalid integer value.";
            }
        }

        if (dtName.equals("boolean")) {
            if (!(newValue.toLowerCase().equals("true")
                    || newValue.toLowerCase().equals("false"))) {
                return "Invalid boolean value.";
            }
        }

        if (dtName.equals("real")) {
            try {
                float val = Float.parseFloat(newValue);
                val = val + 0; // Stop compiler warnings
            } catch (NumberFormatException nfe) {
                return "Invalid real value.";
            }
        }
        EnumerationDataType_c edt = EnumerationDataType_c.getOneS_EDTOnR17(dt);
        if (edt != null) {
        	String [] enumParts = newValue.split("::");
        	if (enumParts.length != 2) {
        		return "Invalid enumeration specification.";
        	}
        	else {
        	  if (!enumParts[0].equals(dt.getName())) {
        		  return "Mismatched enumeration.";
        	  }
        	  else {
        		Enumerator_c [] enums = Enumerator_c.getManyS_ENUMsOnR27(edt);
        		boolean goodEnum = false;
        		for (int i=0; i < enums.length; i++) {
        		  if (enums[i].getName().equals(enumParts[1])) {
        			goodEnum = true;
        			break;
        		  }
        		}
        		if (goodEnum == false) {
        		  return "Enumerator not found.";
        		}
        	  }
        	}
        }
        // No errors found
        return null;
    }
}
