package org.xtuml.bp.xtext.masl.scoping

import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.util.Strings

/**
 * By default qualified names are separated using '.'. in MASL it's '::'. 
 */
class MASLQualifiedNameConverter extends IQualifiedNameConverter.DefaultImpl {
	
	override getDelimiter() {
		'::'
	}
	
	override QualifiedName toQualifiedName(String qualifiedNameAsString) {
		if (qualifiedNameAsString === null) 
			throw new IllegalArgumentException("Qualified name cannot be null")
		if (qualifiedNameAsString == '') 
			throw new IllegalArgumentException("Qualified name cannot be empty")
		var segs = Strings.split(qualifiedNameAsString, getDelimiter()).map[trim]  
		return QualifiedName.create(segs) 
	}
}