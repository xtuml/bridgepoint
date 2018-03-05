package org.xtuml.bp.xtext.masl.ui.dependency

import com.google.inject.Singleton
import java.util.Map
import java.util.Set
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.emf.common.util.URI
import org.xtuml.bp.core.common.DependencyData

import static org.xtuml.bp.xtext.masl.lib.MASLContainerManager.*
import com.google.inject.Inject
import org.xtuml.bp.xtext.masl.dependency.MaslDependencyProvider
import static org.eclipse.xtext.ui.XtextProjectHelper.*

@Singleton
class MaslProjectDependencyProvider {

    private Map<IProject, Set<String>> projectDependencies
    
    @Inject MaslDependencyProvider internalDependencyProvider
    
    def private updateDependencies() {
        val dependencyHandles = newHashSet
        val dependencyUris = newHashMap
        projectDependencies = newHashMap
        for ( project : ResourcesPlugin.workspace.root.projects.filter[hasNature(NATURE_ID)] ) {
            val projectDeps = newHashSet
            projectDependencies.put( project, projectDeps )
            val dependencies = DependencyData::getDependencies( project )
            for ( dependency : dependencies ) {
                if ( dependencyHandles.add( dependency ) ) dependencyUris.put( dependency, URI.createFileURI( dependency ) )
                projectDeps.add( dependency )
            }
        }
        internalDependencyProvider.updateDependencies( dependencyHandles, dependencyUris )
    }

    def public getDependencyHandles() {
        updateDependencies
        internalDependencyProvider.dependencyHandles
    }
    
    def public getDependencyURIs() {
        updateDependencies
        internalDependencyProvider.dependencyURIs
    }
    
    def public uriToHandle( URI uri ) {
        updateDependencies
        internalDependencyProvider.uriToHandle( uri )
    }
    
    def public handleToUri( String handle ) {
        updateDependencies
        internalDependencyProvider.handleToUri( handle )
    }
 
    
    def public getProjectDependencies( String containerHandle ) {
        updateDependencies
        val dependencyHandles = newArrayList
        val projectName = containerHandle.split( CONTAINER_HANDLE_SEPARATOR ).head
        for ( project : ResourcesPlugin.workspace.root.projects.filter[name==projectName] ) {
            val projectDeps = projectDependencies.get( project )
            if ( null != projectDeps ) dependencyHandles += projectDeps
        }
        dependencyHandles
    }
    
}
