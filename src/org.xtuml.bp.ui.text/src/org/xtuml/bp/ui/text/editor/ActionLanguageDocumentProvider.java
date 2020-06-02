// TODO SKB - working on refactoring OALDocumentProvider and ASLDocumentProvider into here 
package org.xtuml.bp.ui.text.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.xtuml.bp.ui.text.asl.ASLActivityEditorInput;
import org.xtuml.bp.ui.text.asl.ASLPartitionScanner;
import org.xtuml.bp.ui.text.editor.oal.OALPartitionScanner;

public class ActionLanguageDocumentProvider extends FileDocumentProvider {

	public static enum ALType {
		OAL, ASL
	};

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			// Depending on what type of editor we're creating a document for, set
			// up the appropriate document partitioner
			if (element instanceof ASLActivityEditorInput) {
				setDocumentPartitioner(document, ALType.ASL);
			} else {
				setDocumentPartitioner(document, ALType.OAL);
			}
		}
		return document;
	}

	public static void setDocumentPartitioner(IDocument document, ALType alType) {
		IDocumentPartitioner partitioner;

		if (alType == ALType.ASL) {
			partitioner = new FastPartitioner(new ASLPartitionScanner(),
					new String[] { ASLPartitionScanner.CONTENT_TYPE_multiline_comment });
		} else {
			partitioner = new FastPartitioner(new OALPartitionScanner(),
					new String[] { OALPartitionScanner.CONTENT_TYPE_multiline_comment });
		}
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
	}

	protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
		return null;
	}
}