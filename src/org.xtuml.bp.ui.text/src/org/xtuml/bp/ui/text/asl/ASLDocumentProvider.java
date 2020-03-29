// TODO - This is pretty much identical to OALDocumentProvider.  I think this two could be refactored
//   and reused
package org.xtuml.bp.ui.text.asl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class ASLDocumentProvider extends FileDocumentProvider {

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
				new ASLPartitionScanner(),
				new String[] { ASLPartitionScanner.CONTENT_TYPE_multiline_comment });
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
	}
    
    protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
        return null;
	}
}