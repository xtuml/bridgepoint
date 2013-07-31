//========================================================================
//
//File:      $RCSfile: ITreeDifferencerProvider.java,v $
//Version:   $Revision: 1.2.30.1 $
//Modified:  $Date: 2013/07/08 14:38:14 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.model.compare;

import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * This interface allows clients to provide a tree that allows the "real"
 * elements to become wrapped in comparable objects.
 * 
 * @author Travis London
 *
 */
public interface ITreeDifferencerProvider extends ITreeContentProvider {
	public ComparableTreeObject getComparableTreeObject(Object realElement);

	public Object[] getChildrenOfType(Object parent, Class<?> type);
}
