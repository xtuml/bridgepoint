//=====================================================================
//
//File:      $RCSfile: IModelInspectorRegistry.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2012/01/23 21:29:53 $
//
//(c) Copyright 2004-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.core.inspector;

public interface IModelInspectorRegistry {
	public IModelClassInspector getInspector(Class modelClass);
}
