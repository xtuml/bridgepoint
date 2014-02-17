//=====================================================================
//
//File:      $RCSfile: AlphaSorter.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2012/01/23 21:28:59 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.sorter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class AlphaSorter implements MetadataSortingManager.ISorter, Comparator {

	public static final String APLHA_METHOD_1 = "getName"; //$NON-NLS-1$
	public static final String APLHA_METHOD_2 = "Get_name"; //$NON-NLS-1$
	private boolean fIgnoreCase = true;
	private boolean descending = false;

	public boolean canSort(Object[] elements) {
		Class type = getType(elements);
		if (type == null || !NonRootModelElement.class.isAssignableFrom(type)) {
			return false;
		}

		Method[] methods = type.getMethods();
		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			if (methodName.equals(APLHA_METHOD_1) || methodName.equals(APLHA_METHOD_2)) {
				if (methods[i].getParameterTypes().length == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void setIgnoreCase(boolean value) {
		fIgnoreCase = value;
	}
	
	public void sort(Object[] elements) {
		descending = false;
		Arrays.sort(elements, this);
	}

	public void sortDescending(Object[] elements) {
		descending  = true;
		Arrays.sort(elements, this);
	}
	
	public int compare(Object arg0, Object arg1) {
		if (arg0 instanceof NonRootModelElement && arg1 instanceof NonRootModelElement) {
			if(descending) {
				// switch the arguments
				Object temp = arg0;
				arg0 = arg1;
				arg1 = temp;
			}
				
			String compArg0 = getTextRepresentation(arg0);
			String compArg1 = getTextRepresentation(arg1);
			if(fIgnoreCase) {
				return compArg0.compareToIgnoreCase(compArg1);
			} else {
				return compArg0.compareTo(compArg1);
			}
		}
		return 1;
	}

	public static String getTextRepresentation(Object object) {
		String value = getStringByMethodDynamically(object, APLHA_METHOD_1);
		if (value == null) {
			value = getStringByMethodDynamically(object, APLHA_METHOD_2);
		}
		if (value == null) {
			throw new IllegalArgumentException("Does not have appropiate methods " + APLHA_METHOD_1 + " or " + APLHA_METHOD_2); 
		}
		return value;
	}

	private static Class getType(Object[] elements) {
		if (elements.length > 0) {
			return elements[0].getClass();
		}
		return null;
	}

	private static String getStringByMethodDynamically(Object object, String methodName) {
		try {
			Method method = object.getClass().getMethod(methodName, new Class[0]);
			if (method != null) {
				Object value = method.invoke(object, new Object[0]);
				return String.valueOf(value);
			}
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}

		return null;
	}
}
