package com.mentor.nucleus.bp.core.search;
//========================================================================
//
//File:      $RCSfile: DocumentCharSequence.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2012/01/23 21:28:24 $
//
//Copyright (c) 2005-2012 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

public class DocumentCharSequence implements CharSequence {

	private IDocument document;

	public DocumentCharSequence(IDocument document) {
		this.document = document;
	}
	
	@Override
	public char charAt(int index) {
		try {
			return document.getChar(index);
		} catch (BadLocationException e) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public int length() {
		return document.getLength();
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		try {
			return document.get(start, end - start);
		} catch (BadLocationException e) {
			throw new IndexOutOfBoundsException();
		}
	}

}
