//========================================================================
//
//File:      $RCSfile: OALDocumentProvider.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:20:54 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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