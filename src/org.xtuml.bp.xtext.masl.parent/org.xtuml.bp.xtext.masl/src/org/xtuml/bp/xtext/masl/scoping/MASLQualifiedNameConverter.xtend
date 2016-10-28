package org.xtuml.bp.xtext.masl.scoping

import org.eclipse.xtext.naming.IQualifiedNameConverter

/**
 * By default qualified names are separated using '.'. in MASL it's '::'. 
 */
class MASLQualifiedNameConverter extends IQualifiedNameConverter.DefaultImpl {
	
	override getDelimiter() {
		'::'
	}
}