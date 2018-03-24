package org.xtuml.bp.core.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.xtuml.bp.core.CorePlugin;

public class DependencyData implements IDependencyProvider {

    private static final String DEPENDENCY_FILE = ".dependencies";
    private static final String PATH_SEPARATOR = "/";

    private static DependencyData defaultInstance = null;

    private Map<IProject, Integer> versionIds;
    
    private DependencyData() {
        versionIds = new HashMap<>();
        ResourcesPlugin.getWorkspace().addResourceChangeListener( new DependencyFileChangeListener() );
    }

    /**
     * @param project Project handle for which to get dependencies
     * @return Array of dependencies with variables translated to actual values 
     */
    @Override
    public Vector<String> getDependencies( IProject project ) {
        return doGetDependencies( project );
    }

    /**
     * @param project Project handle for which to get dependencies
     * @return Array of raw dependencies with variables left untranslated 
     */
    @Override
    public Vector<String> getRawDependencies(IProject project) {
        return doGetRawDependencies( project );
    }

    /**
     * @param project Project handle for which to get dependencies
     * @param updatedDeps Array of dependencies to store
     * Save modified dependencies
     */
    @Override
    public void setDependencies( IProject project, String[] updatedDeps ) {
        if ( null != project ) {
            persist( project, updatedDeps );
            IResource dependenciesResource = project.getFile( DEPENDENCY_FILE );
            if ( null != dependenciesResource ) {
                try {
                    dependenciesResource.refreshLocal( IResource.DEPTH_INFINITE, null );
                } catch (CoreException e) {}
            }
        }
    }

    /**
     * @param project Project handle for which to check dependency version
     * @param versionId integer ID of the version to check against
     * @return If the current version of the dependencies matches the versionId
     *         passed in, -1 is returned. If they do not match, a positive integer is
     *         returned which represents the current version of the dependencies.
     */
    @Override
    public int dependenciesChanged( IProject project, int versionId ) {
        if ( null == project ) return -1;
        else {
            if ( versionIds.containsKey( project ) ) {
                final int currentVersionId = getVersionId( project );
                if ( currentVersionId == versionId ) return -1;
                else return currentVersionId;
            }
            else {
                initializeVersionId( project );
                return 1;
            }
        }
    }
    
    public static DependencyData getDefaultInstance() {
        if ( null == defaultInstance ) defaultInstance = new DependencyData();
        return defaultInstance;
    }
    
    private synchronized int getVersionId( IProject project ) {
        return versionIds.get( project );
    }
    
    private synchronized void initializeVersionId( IProject project ) {
        // initialize version id to 1
        versionIds.put( project, 1 );
    }

    private synchronized void incrementVersionId( IProject project ) {
        if ( versionIds.containsKey( project ) ) {
            versionIds.put( project, versionIds.get( project ) + 1 );
        }
        else initializeVersionId( project );
    }
    
    // Read the feature data from the file on disk.  Populate it into an
    // internal data structure.
    private static void populateDependencies(IProject project, Vector<String> dependencyList, Vector<String> commentList) {
        Scanner inFile = new Scanner("");
        
        try {
            inFile = new Scanner(new FileReader(project.getLocation().toString() + PATH_SEPARATOR + DEPENDENCY_FILE));
            inFile.useDelimiter("\\r|\\n");
        } catch (FileNotFoundException fnfe) {
            // Don't throw an error.  User may never have created the file yet.
        }
        
        while ( inFile.hasNext() ) {
            String dependencyPath = inFile.next().trim();
            if ( dependencyPath.isEmpty() ) {
                // skip blank lines
                continue;
            }
            if ( dependencyPath.startsWith("#") ) {
                // Store off comment lines so they can be written back out later
                commentList.add(dependencyPath);  
                continue; 
            }
            
            dependencyList.add(dependencyPath);
        }
        
        inFile.close();
    }

    /**
     * Write the modified application marking data to disk
     */
    private static void persist(IProject project, String[] updatedDeps) {
        try { 
            Vector<String> dependencyList = new Vector<String>(); 
            Vector<String> commentList = new Vector<String>();

            if ( project == null ) {
                CorePlugin.logError("No project given. Can't write dependency data.", null);
                return;
            } else {
                populateDependencies(project, dependencyList, commentList);
            }
            
            FileOutputStream fout = new FileOutputStream(project.getLocation().toString() + PATH_SEPARATOR + DEPENDENCY_FILE);
            PrintStream stream = new PrintStream(fout);
            
            // Persist the new dependencies
            int len = updatedDeps.length;
            dependencyList.clear();
            for ( int i=0; i < len; i++ ) {
                stream.println(updatedDeps[i]);
            }

            // Persist the comments (Note, commented out lines are all moved together to the bottom)
            for (String comment : commentList ) {
                stream.println(comment);
            }

            fout.close();
        } catch (IOException e) {
            CorePlugin.logError("Error persisting to " + DEPENDENCY_FILE, null);
        }        
    }

    /**
     * @return Array of dependencies with variables translated to actual values 
     */
    private static Vector<String> doGetDependencies(IProject project) {
        Vector<String> dependencyList = new Vector<String>(); 
        Vector<String> commentList = new Vector<String>();

        if ( project == null ) {
            CorePlugin.logError("No project given. Can't retrieve dependencies.", null);
            return new Vector<String>();
        } else {
            populateDependencies(project, dependencyList, commentList);
        }

        Vector<String> modifiedDeps = new Vector<String>();
        String r;
        // Iterate through each item replacing variables PROJECT_LOC and WORKSPACE_LOC with 
        // real values
        int len = dependencyList.size();
        for (int i=0; i < len; i++) {
            String d = dependencyList.get(i);
            if (d.contains("PROJECT_LOC")) {
                if ( project != null ) {
                    r = d.replace("PROJECT_LOC", project.getLocation().toOSString());
                    modifiedDeps.add(r);
                }
            } else if (d.contains("WORKSPACE_LOC")) {
                final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
                IResource ir = root.findMember(d.replace("WORKSPACE_LOC", ""));
                if (ir != null) {
                    r = ir.getLocation().toOSString();
                    modifiedDeps.add(r);
                }
            } else {
                modifiedDeps.add(d);
            }
        }
        return modifiedDeps;
    }

    /**
     * @return Array of raw dependencies with variables left untranslated 
     */
    private static Vector<String> doGetRawDependencies(IProject project) {
        Vector<String> dependencyList = new Vector<String>(); 
        Vector<String> commentList = new Vector<String>();

        if ( project == null ) {
            CorePlugin.logError("No project given. Can't retrieve raw dependencies.", null);
            return new Vector<String>();
        } else {
            populateDependencies(project, dependencyList, commentList);
        }

        return dependencyList; 
    }
    
    private class DependencyFileChangeListener implements IResourceChangeListener, IResourceDeltaVisitor {
        
        @Override
        public void resourceChanged( IResourceChangeEvent evt ) {
            if ( IResourceChangeEvent.POST_CHANGE == evt.getType() ) {
                try {
                    evt.getDelta().accept( this );
                } catch (CoreException e) {}
            }
        }

        @Override
        public boolean visit( IResourceDelta delta ) throws CoreException {
            if ( delta.getResource().getProjectRelativePath().equals( new Path( DEPENDENCY_FILE ) ) ) {
                incrementVersionId( delta.getResource().getProject() );
                return false;
            }
            else return true;
        }
        
    }

}
