package com.mentor.nucleus.bp.cli;
//=====================================================================
//
//File:      $RCSfile: ErrorOutputStreamListener.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:17:09 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IStreamMonitor;

import com.mentor.nucleus.bp.core.CorePlugin;

public class ErrorOutputStreamListener implements IStreamListener {

	private ByteArrayOutputStream stream;

	public void setStream(ByteArrayOutputStream stream) {
		this.stream = stream;
	}
	
	@Override
	public void streamAppended(String text, IStreamMonitor monitor) {
		if(stream != null) {
			try {
				stream.write(text.getBytes());
			} catch (IOException e) {
				BPCLIPreferences.logError("Unable to write console data to stream", e);
			}
		} else {
			System.err.print(text);
		}
	}

}
