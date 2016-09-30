package org.xtuml.bp.xtext.masl.validation

import com.google.inject.Inject
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar
import org.xtuml.bp.xtext.masl.lib.MASLLibraryProvider
import org.xtuml.bp.xtext.masl.masl.CodeBlock
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeExtensions
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider

class MASLTypeValidator extends AbstractMASLValidator {
	
	override register(EValidatorRegistrar registrar) {
		super.register(registrar)
	}
	
	
}