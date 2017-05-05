package org.xtuml.bp.xtext.masl.tests

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.eclipse.xtext.resource.EObjectAtOffsetHelper
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.runner.RunWith
import org.xtuml.bp.xtext.masl.masl.structure.MaslModel

@RunWith(XtextRunner)
@InjectWith(MASLInjectorProvider)
abstract class AbstractMaslModelTest {
	
	@Inject extension ParseHelper<MaslModel>
	@Inject extension EObjectAtOffsetHelper 
	@Inject protected extension ValidationTestHelper
	@Inject protected Provider<XtextResourceSet> resourceSetProvider
		
	protected def EObject getElementAtCaret(Pair<String, CharSequence>... fileName2content) {
		getElementsAtCarets(fileName2content).head
	}

	protected def List<EObject> getElementsAtCarets(Pair<String, CharSequence>... fileName2content) {
		val rs = resourceSetProvider.get
		val result = newArrayList()
		for(f: fileName2content) {
			val content = f.value.toString
			val cleanContent = content.replace('^', '')
			val model = cleanContent.parse(URI.createURI(f.key), rs)
			try {
				assertNoErrors(model.eResource)
			} catch (Throwable exc) {
				System.err.println(cleanContent)
				throw(exc)
			}
			var offset = content.indexOf('^')
			var correction = 0
			while(offset != -1) {
				result += resolveContainedElementAt(model.eResource as XtextResource, offset - correction)
				offset = content.indexOf('^', offset + 1) 
				correction++
			} 
		}
		return result
	}
}