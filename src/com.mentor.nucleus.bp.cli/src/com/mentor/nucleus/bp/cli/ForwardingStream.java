package com.mentor.nucleus.bp.cli;
//=====================================================================
//
//File:      $RCSfile: ForwardingStream.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:17:04 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
import java.io.OutputStream;
import java.io.PrintStream;

public class ForwardingStream extends PrintStream {

	private PrintStream other;

	public ForwardingStream(OutputStream out, PrintStream other) {
		super(out);
		this.other = other;
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		other.write(buf, off, len);
		super.write(buf, off, len);
	}

}
