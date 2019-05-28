package org.xtuml.bp.core.common;

import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.xtuml.bp.core.Component_c;

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
	 * @param project Project handle for which to get dependencies
	 * @param dependency A string dependency to add to the dependencies
	 * Add a single dependency
	 */
	public void addDependency( IProject project, String dependency );

	/**
	 * @param project Project handle for which to get dependencies
	 * @param dependency A domain to add to the dependencies
	 * Add a dependency to a domain in the workspace. This adds a dependency
	 * on the generated ".int" file in the "models" directory of the project.
	 */
	public void addDependency( IProject project, Component_c dependency );
	
	/**
	 * @param project Project handle for which to check dependency version
	 * @param versionId integer ID of the version to check against
	 * @return If the current version of the dependencies matches the versionId
	 *         passed in, -1 is returned. If they do not match, a positive integer is
	 *         returned which represents the current version of the dependencies.
	 */
	public int dependenciesChanged( IProject project, int versionId );

}
