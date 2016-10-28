package org.xtuml.bp.xtext.masl.lib

import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.Singleton
import java.util.List
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.URIMappingRegistryImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.XtextResourceSet
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration

/**
 * Built-in types like 'real' or 'integer' are provided by a library model. 
 * This class is responsible for loading it and providing its contents to the index, 
 * such that the elements can be referred to from other MASL models.
 */
@Singleton
class MASLLibraryProvider {

	public static val MODEL_URI = URI.createURI('lib:/BuiltinLibrary.int')
	static val MODEL_PATH = "org/xtuml/bp/xtext/masl/lib/BuiltinLibrary.int"

	static val LOG = Logger.getLogger(MASLLibraryProvider)

	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject IResourceDescription.Manager resourceDescriptionManager
	List<IResourceDescription> libraryDescriptions

	Map<String, URI> typesMap

	def synchronized List<IResourceDescription> getResourceDescriptions() {
		initialize
		libraryDescriptions
	}
	
	def getBuiltinTypeURI(String name) {
		initialize
		typesMap.get(name)
	}
	
	private def initialize() {
		if (libraryDescriptions == null) {
			libraryDescriptions = newArrayList
			try {
				val resourceSet = resourceSetProvider.get
				val resource = resourceSet.getResource(MODEL_URI, true)
				libraryDescriptions += resourceDescriptionManager.getResourceDescription(resource);
				typesMap = newHashMap
				EcoreUtil2
					.getAllContents(resource, true)
					.filter(TypeDeclaration)
					.forEach[
						typesMap.put(name, EcoreUtil.getURI(it))
					]
			} catch (Exception exc) {
				LOG.error('Error loading MASL library', exc)
			}
		}
	}
	
	def static register() {
		val url = MASLLibraryProvider.classLoader.getResource(MODEL_PATH)
		val uri = URI.createURI(url.toString)
		URIMappingRegistryImpl.INSTANCE.put(MODEL_URI, uri)
	}
}
