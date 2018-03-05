package org.xtuml.bp.xtext.masl.dependency

import com.google.inject.Inject
import com.google.inject.Provider
import org.apache.log4j.Logger
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.emf.common.util.URI
import java.util.Set
import java.util.Map
import com.google.inject.Singleton

@Singleton
class MaslDependencyProvider {

    static val LOG = Logger.getLogger( MaslDependencyProvider )

    protected Set<String> dependencyHandles = newHashSet
    protected Map<String, URI> dependencyUris = newHashMap
    
    @Inject Provider<XtextResourceSet> resourceSetProvider
    @Inject IResourceDescription.Manager resourceDescriptionManager

    def public updateDependencies( Set<String> dependencyHandles, Map<String, URI> dependencyUris ) {
        this.dependencyHandles = dependencyHandles
        this.dependencyUris = dependencyUris
    }

    def public getDependencyHandles() {
        dependencyHandles.toList
    }
    
    def public getDependencyURIs() {
        dependencyUris.values.toList
    }
    
    def public uriToHandle( URI uri ) {
        dependencyUris.entrySet.filter[value==uri].head.key
    }
    
    def public handleToUri( String handle ) {
        dependencyUris.get( handle )
    }
    
    def public getResourceDescriptions( String containerHandle ) {
        try {
            val resourceSet = resourceSetProvider.get
            val resource = resourceSet.getResource( handleToUri( containerHandle ), true )
            #[resourceDescriptionManager.getResourceDescription( resource )]
        } catch (Exception exc) {
            LOG.error('Error loading MASL library', exc)
        }
    }
    
}
