package org.xtuml.bp.core.common;

import java.util.Vector;
import org.eclipse.core.resources.IProject;

public interface IDependencyProvider {

	/**
	 * @param project Project handle for which to get dependencies
	 * @return Array of dependencies with variables translated to actual values 
	 */
	public Vector<String> getDependencies( IProject project );

	/**
	 * @param project Project handle for which to get dependencies
	 * @return Array of raw dependencies with variables left untranslated 
	 */
	public Vector<String> getRawDependencies( IProject project );

	/**
	 * @param project Project handle for which to get dependencies
	 * @param updatedDeps Array of dependencies to store
	 * Save modified dependencies
	 */
	public void setDependencies( IProject project, String[] updatedDeps );
	
	/**
	 * @param project Project handle for which to check dependency version
	 * @param versionId integer ID of the version to check against
	 * @return If the current version of the dependencies matches the versionId
	 *         passed in, -1 is returned. If they do not match, a positive integer is
	 *         returned which represents the current version of the dependencies.
	 */
	public int dependenciesChanged( IProject project, int versionId );

}
