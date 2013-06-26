//=====================================================================
//
//File:      $RCSfile: ITextGenerator.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:49:47 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.compare.text;

import org.eclipse.core.runtime.IProgressMonitor;

public interface ITextGenerator {
	public int getPartCount();
	public void generateFor(
		Object object,
		ITextBuffer buffer,
		int indentationLevel,
		IProgressMonitor monitor);
	public void generateFor(
		Object object,
		int partIndex,
		ITextBuffer buffer,
		int indentationLevel,
		IProgressMonitor monitor);
	public interface ITextBuffer {
		public void append(char[] text, int startIndex, int length);
		public void append(String text);
		public int getLength();
		public String getText();
		public void clear();
	}
}
