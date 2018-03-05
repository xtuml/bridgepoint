package org.xtuml.bp.core.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.xtuml.bp.core.CorePlugin;

public class DependencyData {

    private static final String DEPENDENCY_FILE = "/.dependencies";
    
    
    
	// Read the feature data from the file on disk.  Populate it into an
	// internal data structure.
	private static void populateDependencies(IProject project, Vector<String> dependencyList, Vector<String> commentList) {
		Scanner inFile = new Scanner("");
		
		try {
			inFile = new Scanner(new FileReader(project.getLocation().toString() + DEPENDENCY_FILE));
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
	public static void persist(IProject project, String[] updatedDeps) {
		try { 
			Vector<String> dependencyList = new Vector<String>(); 
			Vector<String> commentList = new Vector<String>();

			if ( project == null ) {
				CorePlugin.logError("No project given. Can't write dependency data.", null);
				return;
			} else {
				populateDependencies(project, dependencyList, commentList);
			}
			
			FileOutputStream fout = new FileOutputStream(project.getLocation().toString() + DEPENDENCY_FILE);
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
	public static Vector<String> getDependencies(IProject project) {
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
	public static Vector<String> getRawDependencies(IProject project) {
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

}
