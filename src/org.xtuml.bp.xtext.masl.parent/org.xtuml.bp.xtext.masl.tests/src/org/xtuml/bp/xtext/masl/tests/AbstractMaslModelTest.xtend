package org.xtuml.bp.xtext.masl.tests

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.resource.EObjectAtOffsetHelper
import org.eclipse.xtext.resource.XtextResource
import org.junit.runner.RunWith
import org.xtuml.bp.xtext.masl.masl.behavior.CodeBlock
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel

import static extension org.eclipse.xtext.EcoreUtil2.*

@RunWith(XtextRunner)
@InjectWith(MASLInjectorProvider)
abstract class AbstractMaslModelTest {
	
	@Inject extension ParseHelper<MaslModel>
	@Inject extension EObjectAtOffsetHelper 
	
	protected def EObject getElement(CharSequence domainDeclaration, CharSequence variableDeclaration, CharSequence expression) {
		val rs = '''
			domain foo is 
				«IF !domainDeclaration.toString.empty»«domainDeclaration»;«ENDIF»
				service foo();
			end;
			end domain;
		'''.parse.eResource.resourceSet
		val hasCaret = expression.toString.indexOf('^') != -1
		val text = '''
			service foo::foo(param: in integer) 
			is
				«IF !variableDeclaration.toString.empty»«variableDeclaration»;«ENDIF»
			begin
				«IF !hasCaret»^«ENDIF»«expression»;
			end;
		'''
		val offset = text.indexOf('^')
		val model = text.replace('^', '').parse(rs)
		if(hasCaret) 
			resolveContainedElementAt(model.eResource as XtextResource, offset)
		else
			model.getAllContentsOfType(CodeBlock).head.statements.last
	}
}