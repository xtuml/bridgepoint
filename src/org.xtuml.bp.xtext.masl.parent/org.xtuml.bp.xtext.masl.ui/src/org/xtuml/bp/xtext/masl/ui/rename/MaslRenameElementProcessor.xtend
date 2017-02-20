package org.xtuml.bp.xtext.masl.ui.rename

import org.eclipse.xtext.ui.refactoring.impl.RenameElementProcessor

class MaslRenameElementProcessor extends RenameElementProcessor {
	
	override getElements() {
		if(renameElementContext instanceof XtumlRenameElementContext) 
			(renameElementContext as XtumlRenameElementContext).allTargetURI2EClass.map[key]
		else 
			super.getElements()
	}
	
}