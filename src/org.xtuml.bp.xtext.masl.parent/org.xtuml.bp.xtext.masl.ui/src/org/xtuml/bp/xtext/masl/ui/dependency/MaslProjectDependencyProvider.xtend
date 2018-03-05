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
    private boolean upToDate = false
    
    @Inject MaslDependencyProvider internalDependencyProvider
    
    def private updateDependencies() {
        if ( !upToDate ) {
            val dependencyHandles = newHashSet
            val dependencyUris = newHashSet
            val dependencyHandleUris = newHashMap
            val dependencyUriHandles = newHashMap
            projectDependencies = newHashMap
            for ( project : ResourcesPlugin.workspace.root.projects.filter[hasNature(NATURE_ID)] ) {
                val projectDeps = newHashSet
                projectDependencies.put( project, projectDeps )
                val dependencies = DependencyData::getDependencies( project )
                for ( dependency : dependencies ) {
                    val uri = URI.createFileURI( dependency )
                    if ( dependencyUris.add( uri ) ) {
                        dependencyUriHandles.put( uri, dependency )
                        if ( dependencyHandles.add( dependency ) ) dependencyHandleUris.put( dependency, newHashSet( uri ) as Set<URI> )
                        else dependencyHandleUris.get( dependency ).add( uri )
                    }
                    projectDeps.add( dependency )
                }
            }
            internalDependencyProvider.setDependencies( dependencyHandles, dependencyUris, dependencyHandleUris, dependencyUriHandles )
            upToDate = true
        }
    }

    def public getDependencyHandles() {
        updateDependencies
        internalDependencyProvider.dependencyHandles
    }
    
    def public getDependencyUris() {
        updateDependencies
        internalDependencyProvider.dependencyUris
    }
    
    def public uriToHandle( URI uri ) {
        updateDependencies
        internalDependencyProvider.uriToHandle( uri )
    }
    
    def public handleToUris( String handle ) {
        updateDependencies
        internalDependencyProvider.handleToUris( handle )
    }
 
    
    def public getProjectDependencies( String containerHandle ) {
        updateDependencies
        val projectName = containerHandle.split( CONTAINER_HANDLE_SEPARATOR ).head
        val project = ResourcesPlugin.workspace.root.projects.filter[name==projectName].head
        if ( null != project )
            projectDependencies.get( project )
        else
            newHashSet
    }
    
}
