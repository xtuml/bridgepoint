//=====================================================================
//
//File:      $RCSfile: DefaultTextBuffer.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:49:47 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.compare.text;

import com.mentor.nucleus.bp.compare.text.ITextGenerator.ITextBuffer;

public class DefaultTextBuffer implements ITextBuffer {
	StringBuffer internalBuffer = new StringBuffer(1000);

	public void append(char[] text, int offset, int length) {
		internalBuffer.append(text, offset, length);
	}

	public void append(String text) {
		internalBuffer.append(text);
	}

	public void clear() {
		internalBuffer.setLength(0);
	}

	public String getText() {
		return internalBuffer.toString();
	}

	public int getLength() {
		return internalBuffer.length();
	}

}
