package org.xtuml.bp.xtext.masl.scoping

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.impl.ImportNormalizer
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider
import org.xtuml.bp.xtext.masl.MASLExtensions

/** 
 * Elements form the same domain and elements from the built-in library should always be visible.
 */
class MASLImportScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {
	
	@Inject extension MASLExtensions
	
	override protected internalGetImportedNamespaceResolvers(EObject context, boolean ignoreCase) {
		val resolvers = super.internalGetImportedNamespaceResolvers(context, ignoreCase)
		
		// add elements from same domain
		val domainName=context.domainName
		if(domainName != null)
			resolvers += new ImportNormalizer(QualifiedName.create(domainName), true, false)

		return resolvers
	}
}