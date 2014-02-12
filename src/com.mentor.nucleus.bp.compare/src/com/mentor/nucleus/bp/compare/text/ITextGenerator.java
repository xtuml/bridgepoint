//=====================================================================
//
//File:      $RCSfile: ITextGenerator.java,v $
//Version:   $Revision: 1.11 $
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
