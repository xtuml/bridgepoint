package com.mentor.nucleus.bp.cli;
//=====================================================================
//
//File:      $RCSfile: ErrorOutputStreamListener.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:17:09 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
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
