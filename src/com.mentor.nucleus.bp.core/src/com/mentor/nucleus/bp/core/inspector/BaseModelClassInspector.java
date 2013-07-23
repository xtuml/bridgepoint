//=====================================================================
//
//File:      $RCSfile: BaseModelClassInspector.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2012/01/23 21:29:53 $
//
//(c) Copyright 2004-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.core.inspector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.sorter.*;

public abstract class BaseModelClassInspector implements IModelClassInspector {

	MetadataSortingManager sortingManager;

	public BaseModelClassInspector(MetadataSortingManager aSortingManager) {
		super();
		sortingManager = aSortingManager;
	}

	protected void sort(Object[] elements) {
		if (sortingManager != null) {
			sortingManager.sort(elements);
		}
	}

	protected Object invokeMethod(Object object, String methodName) {
		try {
			Class objectClass = object.getClass();
			Method method = objectClass.getMethod(methodName, null);
			return method.invoke(object, null);
		} catch (SecurityException e) {
			CorePlugin.logError("Inspector could not invoke " + methodName, e);
		} catch (IllegalArgumentException e) {
			CorePlugin.logError("Inspector could not invoke " + methodName, e);
		} catch (NoSuchMethodException e) {
			CorePlugin.logError("Inspector could not find " + methodName, e);
		} catch (IllegalAccessException e) {
			CorePlugin.logError("Inspector could not invoke " + methodName, e);
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Inspector could not invoke " + methodName, e);
		}

		return null;
	}

}
