package com.mentor.nucleus.bp.ui.text.annotation;
//====================================================================
//
// File:      $RCSfile: IActivityProblemAnnotation.java,v $
// Version:   $Revision: 1.11 $
// Modified:  $Date: 2013/01/10 23:20:50 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import java.util.Iterator;

public interface IActivityProblemAnnotation {

	/**
	 * Interface of annotations representing markers
	 * and problems.
	 * 
	 * @see org.eclipse.core.resources.IMarker
	 * @see org.eclipse.jdt.core.compiler.IProblem
	 */

	ActivityAnnotationType getAnnotationType();

	boolean isWarning();
	boolean isError();

	String getMessage();

	/**
	 * Returns whether this annotation is relavant.
	 * <p>
	 * If the annotation is overlaid then it is not
	 * relevant. After all overlays have been removed
	 * the annotation might either become relevant again
	 * or stay irrelevant.
	 * </p>
	 * 
	 * @return <code>true</code> if relevant
	 * @see #hasOverlay()
	 */
	boolean isRelevant();

	/**
	 * Returns whether this annotation is overlaid.
	 * 
	 * @return <code>true</code> if overlaid
	 */
	boolean hasOverlay();

	/**
	 * Returns an iterator for iterating over the
	 * annotation which are overlaid by this annotation.
	 * 
	 * @return an iterator over the overlaid annotaions
	 */
	Iterator getOverlaidIterator();

	/**
	 * Adds the given annotation to the list of
	 * annotations which overlaid by this annotations.
	 *  
	 * @param annotation  the problem annoation
	 */
	void addOverlaid(IActivityProblemAnnotation annotation);

	/**
	 * Removes the given annotation from the list of
	 * annotations which are overlaid by this annotation.
	 *  
	 * @param annotation  the problem annoation
	 */
	void removeOverlaid(IActivityProblemAnnotation annotation);

	/**
	 * Tells whether this annotation is a problem
	 * annotation.
	 * 
	 * @return <code>true</code> if it is a problem annotation
	 */
	boolean isProblem();
}
