package com.mentor.nucleus.bp.cli;
//=====================================================================
//
//File:      $RCSfile: OutputStreamListener.java,v $
//Version:   $Revision: 1.4.30.2 $
//Modified:  $Date: 2013/07/23 15:06:38 $
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

public class OutputStreamListener implements IStreamListener {

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
			System.out.print(text);
		}
	}

}
