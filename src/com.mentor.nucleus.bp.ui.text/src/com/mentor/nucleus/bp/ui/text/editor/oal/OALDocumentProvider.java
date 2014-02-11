//========================================================================
//
//File:      $RCSfile: OALDocumentProvider.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:20:54 $
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

package com.mentor.nucleus.bp.ui.text.editor.oal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class OALDocumentProvider extends FileDocumentProvider {

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			setDocumentPartitioner(document);
		}
		return document;
	}
	
	public static void setDocumentPartitioner(IDocument document){
		IDocumentPartitioner partitioner =
			new FastPartitioner(
				new OALPartitionScanner(),
				new String[] { OALPartitionScanner.CONTENT_TYPE_multiline_comment });
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
	}
    
    protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
        return null;
	}
}