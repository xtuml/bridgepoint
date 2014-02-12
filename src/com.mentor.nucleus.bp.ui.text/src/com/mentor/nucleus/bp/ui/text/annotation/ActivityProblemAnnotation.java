package com.mentor.nucleus.bp.ui.text.annotation;
//====================================================================
//
// File:      $RCSfile: ActivityProblemAnnotation.java,v $
// Version:   $Revision: 1.11 $
// Modified:  $Date: 2013/01/10 23:20:50 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

/**
 * Problem-annotations that are specific to activities.  
 * 
 * The problems considered here are temporary ones detected by the parser
 * that exist up until the point the editor saves the changes to the 
 * activity.  At that time, the problems are converted into markers, and these 
 * associated problem-annnotations are likewise converted into 
 * marker-annotations.       
 */
public class ActivityProblemAnnotation
	extends Annotation
	implements IActivityProblemAnnotation {

	private List fOverlaids;
	public ActivityProblem fProblem;
	private ActivityAnnotationType fType;
	private Image fImage;
	private boolean fImageInitialized = false;
	private static Image fgImage;
	private static boolean fgImageInitialized = false;

	public ActivityProblemAnnotation(ActivityProblem problem) {

		fProblem = problem;

		if (fProblem.isError())
			fType = ActivityAnnotationType.ERROR;
		else if (fProblem.isWarning())
			fType = ActivityAnnotationType.WARNING;
		else
			fType = ActivityAnnotationType.INFO;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.Annotation#paint(org.eclipse.swt.graphics.GC, org.eclipse.swt.widgets.Canvas, org.eclipse.swt.graphics.Rectangle)
	 */
	public void paint(GC gc, Canvas canvas, Rectangle bounds) {
		// nothing is needed until quick fix/IMG_OBJS_FIXABLE_PROBLEM is supported
	}
	/*
	 * @see IProblemAnnotation#getImage(Display)
	 */
	public ActivityAnnotationType getAnnotationType() {
		return fType;
	}

    /**
     * See parent method overridden.
     */
    public String getType() 
    {
        return fType.toString();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.text.source.Annotation#isPersistent()
     */
    public boolean isPersistent()
    {
        return false;
    }
    
	public String getMessage() {
		return fProblem.getMessage();
	}
	public boolean isRelevant() {
		return true;
	}
	public boolean hasOverlay() {
		return false;
	}
	public Iterator getOverlaidIterator() {
		if (fOverlaids != null)
			return fOverlaids.iterator();
		return null;
	}
	public void addOverlaid(IActivityProblemAnnotation annotation) {
		if (fOverlaids == null)
			fOverlaids = new ArrayList(1);
		fOverlaids.add(annotation);
	}
	public void removeOverlaid(IActivityProblemAnnotation annotation) {
		if (fOverlaids != null) {
			fOverlaids.remove(annotation);
			if (fOverlaids.size() == 0)
				fOverlaids = null;
		}
	}
	public boolean isProblem() {
		return (fProblem.isError() || fProblem.isWarning());
	}

	public boolean isError() {
		return fProblem.isError();
	}

	public boolean isWarning() {
		return fProblem.isWarning();
	}

}
