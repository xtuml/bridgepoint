package com.mentor.nucleus.bp.core.search;
//========================================================================
//
//File:      $RCSfile: DocumentCharSequence.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2012/01/23 21:28:24 $
//
//Copyright (c) 2005-2014 Mentor Graphics Corporation.  All rights reserved.
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
