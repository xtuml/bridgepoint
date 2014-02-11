//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.LeafSymbolicConstant_c;
import com.mentor.nucleus.bp.core.LiteralSymbolicConstant_c;
import com.mentor.nucleus.bp.core.SymbolicConstant_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

/**
 * Helper class used when delaing with Dimensions and Return_Dimensions
 * attributes.
 *
 */
public class DimensionsUtil {
    private static String matchedString;

	/**
     * 
     * This is a utility function that takes a dimension string which has been
     * accepted by the DimensionsValidator, and returns a vector of array 
     * dimensions.
     * 
     * @param dimensions This is a well-formed dimensions string.  This 
     *        string is well-formed, because it has passed through the 
     *        DimensionsValidator class before being passed here.
	 * @param element is the NonRootModelElement object the dimension array
	 *        defined for. This object used to retrieve the model root used
	 *        later in Class Query Interface. 
     * @return
     */
    public static Vector getDimensionsData(String dimensions, NonRootModelElement element){
    	Vector dims = new Vector(); 
    	dimensions = dimensions.replace(" ", "");
    	Pattern constant = Pattern.compile("^\\[\\w*([a-z]|[A-Z])\\w*\\]");
    	Pattern digits = Pattern.compile("^\\[\\d+\\]");
    	Pattern dynamic = Pattern.compile("^\\[\\]");
        Matcher const_match = null;
        Matcher digits_match = null;
        Matcher dynamic_match = null;
		while (dimensions.length() > 0) {
			const_match = constant.matcher(dimensions);
			if (const_match.find() && element != null) {
				matchedString = const_match.group();
				dimensions = dimensions.substring(matchedString.length(),dimensions.length());
				matchedString = matchedString.substring(1, matchedString
						.length() - 1);
				class SymbolicConstant_test25535_c implements
						ClassQueryInterface_c {
					public boolean evaluate(Object candidate) {
						SymbolicConstant_c selected = (SymbolicConstant_c) candidate;
						return (selected.getName().equals(matchedString));
					}
				}
				SymbolicConstant_c[] v_sycs = SymbolicConstant_c
						.SymbolicConstantInstances(element.getModelRoot(),
								new SymbolicConstant_test25535_c());

				String contant_value = LiteralSymbolicConstant_c
						.getOneCNST_LSCOnR1503(
								LeafSymbolicConstant_c
										.getOneCNST_LFSCOnR1502(v_sycs[0]))
						.getValue();
				dims.add(Integer.valueOf(contant_value));
			}
			digits_match = digits.matcher(dimensions);
			if (digits_match.find()) {
				matchedString = digits_match.group();
				dimensions = dimensions.substring(matchedString.length(),dimensions.length());
				String digitValue = matchedString.substring(1, matchedString
						.length() - 1);
				dims.add(Integer.valueOf(digitValue));
			}
			dynamic_match = dynamic.matcher(dimensions);
			if (dynamic_match.find()) {
				matchedString = dynamic_match.group();
				dimensions = dimensions.substring(matchedString.length(),dimensions.length());
				// If a value for the number of elements in this dimension
				// isn't specified we use 0 to represent dynamic sizing
				dims.add(new Integer(0));
			}
		}
    	return dims;
    }
}
