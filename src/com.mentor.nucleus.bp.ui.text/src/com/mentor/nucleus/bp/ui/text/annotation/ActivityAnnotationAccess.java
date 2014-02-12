//====================================================================
//
// File: $RCSfile: ActivityAnnotationAccess.java,v $
// Version: $Revision: 1.10 $
// Modified: $Date: 2013/01/10 23:20:50 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================

package com.mentor.nucleus.bp.ui.text.annotation;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;

public class ActivityAnnotationAccess extends DefaultMarkerAnnotationAccess
{
    /* (non-Javadoc)
     * @see org.eclipse.jface.text.source.IAnnotationAccess#getType(org.eclipse.jface.text.source.Annotation)
     */
    public Object getType(Annotation annotation)
	{
        return annotation.getType();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.IAnnotationAccess#isMultiLine(org.eclipse.jface.text.source.Annotation)
	 */
	public boolean isMultiLine(Annotation annotation)
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.IAnnotationAccess#isTemporary(org.eclipse.jface.text.source.Annotation)
	 */
	public boolean isTemporary(Annotation annotation)
	{
		return !annotation.isPersistent();
	}
}