package org.xtuml.bp.xtext.masl.lib

import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.Singleton
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.XtextResourceSet

/**
 * Built-in types like 'real' or 'integer' are provided by a library model. 
 * This class is responsible for loading it and providing its contents to the index, 
 * such that the elements can be referred to from other MASL models.
 */
@Singleton
class MASLLibraryProvider {

	public static val MODEL_URI = URI.createURI('classpath:/org/xtuml/bp/xtext/masl/lib/BuiltinLibrary.int')

	static val LOG = Logger.getLogger(MASLLibraryProvider)

	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject IResourceDescription.Manager resourceDescriptionManager
	List<IResourceDescription> libraryDescriptions

	def synchronized List<IResourceDescription> getResourceDescriptions() {
		if (libraryDescriptions == null) {
			libraryDescriptions = newArrayList
			try {
				val resourceSet = resourceSetProvider.get
				resourceSet.classpathURIContext = this
				val resource = resourceSet.getResource(MODEL_URI, true)
				libraryDescriptions += resourceDescriptionManager.getResourceDescription(resource);
			} catch (Exception exc) {
				LOG.error('Error loading MASL library', exc)
			}
		}
		libraryDescriptions
	}

}
