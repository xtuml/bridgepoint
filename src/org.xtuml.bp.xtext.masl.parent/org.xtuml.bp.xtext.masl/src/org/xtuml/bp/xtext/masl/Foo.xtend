package org.xtuml.bp.xtext.masl

import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import com.google.inject.Inject
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage

class Foo {
	@Inject extension StructurePackage s 
	@Inject extension BehaviorPackage 
	
	def foo(){
		s.abstractNamed_Name
	}
}