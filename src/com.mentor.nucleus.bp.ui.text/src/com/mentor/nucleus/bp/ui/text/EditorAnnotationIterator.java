//====================================================================
//
// File:      $RCSfile: EditorAnnotationIterator.java,v $
// Version:   $Revision: 1.9 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.ui.text;

import java.util.Iterator;
import org.eclipse.jface.text.source.IAnnotationModel;

import com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation;


/**
 * Filters problems based on their types.
 */
public class EditorAnnotationIterator implements Iterator {
		
	private Iterator fIterator;
	private Object fNext;
	private boolean fSkipIrrelevants;

	public EditorAnnotationIterator(IAnnotationModel model, boolean skipIrrelevants) {
		fIterator= model.getAnnotationIterator();
		fSkipIrrelevants= skipIrrelevants;
		skip();
	}

	private void skip() {
		while (fIterator.hasNext()) {
			Object next= fIterator.next();
			if (next instanceof IActivityProblemAnnotation) {
				IActivityProblemAnnotation a= (IActivityProblemAnnotation) next;
				if (fSkipIrrelevants) {
					if (a.isRelevant()) {
						fNext= a;
						return;
					}
				} else {
					fNext= a;
					return;
				}
			}
			else
			{
				fNext= next;
				return;
			}
		}
		fNext= null;
	}

	/*
	 * @see Iterator#hasNext()
	 */
	public boolean hasNext() {
		return fNext != null;
	}

	/*
	 * @see Iterator#next()
	 */
	public Object next() {
		try {
			return fNext;
		} finally {
			skip();
		}
	}

	/*
	 * @see Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
