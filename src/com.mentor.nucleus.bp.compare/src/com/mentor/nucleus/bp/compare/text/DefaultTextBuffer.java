//=====================================================================
//
//File:      $RCSfile: DefaultTextBuffer.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:49:47 $
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
