package com.mentor.nucleus.bp.core.common;
//=====================================================================
//
//File:      $RCSfile: ModelElementFileFacade.java,v $ 
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:11 $ 
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;

public class ModelElementFileFacade implements IAdaptable {
	private NonRootModelElement m_element;

	public ModelElementFileFacade(Object arg) {
		m_element = (NonRootModelElement) arg;
	}

	public static ModelElementFileFacade getFacadeOf(Object element) {
		if (element instanceof IAdaptable) {
			return (ModelElementFileFacade) ((IAdaptable) element)
					.getAdapter(ModelElementFileFacade.class);
		}
		return null;
	}

	public Object getModelElement() {
		return m_element;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof ModelElementFileFacade) {
			return m_element == ((ModelElementFileFacade) obj)
					.getModelElement();

		} else {
			return false;
		}

	}
	public Object getAdapter(Class adapter) {
		if (adapter == IResource.class
				&& m_element instanceof IAdaptable) {
			return ((IAdaptable) m_element).getAdapter(IFile.class);
		}
		return null;
	}
}
