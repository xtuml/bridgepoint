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
import org.eclipse.emf.ecore.resource.Resource

@Singleton
class MaslDependencyProvider {

    static val LOG = Logger.getLogger( MaslDependencyProvider )

    protected Set<String> dependencyHandles = newHashSet
    protected Set<URI> dependencyUris = newHashSet
    protected Map<String, Set<URI>> dependencyHandleUris = newHashMap
    protected Map<URI, String> dependencyUriHandles = newHashMap
    
    private Map<URI, Resource> cachedDependencyResources = newHashMap
    
    @Inject Provider<XtextResourceSet> resourceSetProvider
    @Inject IResourceDescription.Manager resourceDescriptionManager

    def public setDependencies( Set<String> dependencyHandles, Set<URI> dependencyUris, Map<String, Set<URI>> dependencyHandleUris, Map<URI, String> dependencyUriHandles ) {
        this.dependencyHandles = dependencyHandles
        this.dependencyUris = dependencyUris
        this.dependencyHandleUris = dependencyHandleUris;
        this.dependencyUriHandles = dependencyUriHandles;
    }

    def public getDependencyHandles() {
        dependencyHandles
    }
    
    def public getDependencyUris() {
        dependencyUris
    }
    
    def public uriToHandle( URI uri ) {
        dependencyUriHandles.get( uri )
    }
    
    def public handleToUris( String handle ) {
        dependencyHandleUris.get( handle )
    }
    
    def public getResourceDescriptions( String containerHandle ) {
        try {
            val resourceSet = resourceSetProvider.get
            val resourceDescriptions = newArrayList
            for ( uri : handleToUris( containerHandle ) )
                if ( cachedDependencyResources.containsKey( uri ) )
                    resourceDescriptions += resourceDescriptionManager.getResourceDescription( cachedDependencyResources.get( uri ) )
                else {
                    val res = resourceSet.getResource( uri, true )
                    cachedDependencyResources.put( uri, res )
                    resourceDescriptions += resourceDescriptionManager.getResourceDescription( res )
                }
            resourceDescriptions
        } catch (Exception exc) {
            LOG.error('Error loading MASL library', exc)
        }
    }
    
}
