package com.mentor.nucleus.bp.core.ui.cells;
//========================================================================
//
//File:      $RCSfile: ICellProvider.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:39:58 $
//
//(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;

public interface ICellProvider {

	public CellEditor getCellEditor(NonRootModelElement element, Composite parent, ObjectElement data);

	public boolean supportsEdit(NonRootModelElement element,
			ObjectElement data, Composite parent);
	
	public String getValue(NonRootModelElement element, ObjectElement data);
	
	public void setValue(NonRootModelElement element, ObjectElement data, Object value);
}
