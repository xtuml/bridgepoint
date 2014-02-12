package com.mentor.nucleus.bp.cli;
//=====================================================================
//
//File:      $RCSfile: ForwardingStream.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:17:04 $
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
