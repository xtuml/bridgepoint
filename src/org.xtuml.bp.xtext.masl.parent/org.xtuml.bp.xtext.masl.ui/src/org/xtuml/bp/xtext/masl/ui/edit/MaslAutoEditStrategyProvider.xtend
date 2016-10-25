package org.xtuml.bp.xtext.masl.ui.edit

import org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider
import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider.IEditStrategyAcceptor
import org.eclipse.jface.text.IDocument
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper

class MaslAutoEditStrategyProvider extends DefaultAutoEditStrategyProvider {

	/**
	 * Skip automatically inserting a closing single quote.
	 */	
	override protected configureStringLiteral(IEditStrategyAcceptor acceptor) {
		acceptor.accept(partitionInsert.newInstance("\"","\""),IDocument.DEFAULT_CONTENT_TYPE);
		acceptor.accept(partitionDeletion.newInstance("\"","\""),IDocument.DEFAULT_CONTENT_TYPE);
		acceptor.accept(partitionEndSkippingEditStrategy.get(),TerminalsTokenTypeToPartitionMapper.STRING_LITERAL_PARTITION);
	}
}