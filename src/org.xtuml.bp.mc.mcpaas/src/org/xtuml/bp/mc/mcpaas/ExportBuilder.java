package org.xtuml.bp.mc.mcpaas;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.mc.AbstractExportBuilder;

public class ExportBuilder extends AbstractExportBuilder {

	// The shared instance
	private static ExportBuilder singleton;

	public ExportBuilder() {
		super(Activator.getDefault(), MCNature.getDefault());
	}

	@Override
    protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
            throws CoreException {
		boolean exportNeeded = readyBuildArea(monitor);

		// Calling build again here just forces any builders that have not
		// yet run to refresh before starting. This picks up changes we may
		// have made to the external tool builder launch file.
		getProject().build(kind, monitor);

		if (exportNeeded) {
			PersistenceManager.getDefaultInstance();
			exportModel(monitor);
		}
        return null;
    }
	
	/**
	 * Returns the shared instance. Creates if it has not yet been created
	 * 
	 * @return the shared instance
	 */
	public static ExportBuilder getDefault() {
		if (ExportBuilder.singleton == null) {
			ExportBuilder.singleton = new ExportBuilder();
		}
		return ExportBuilder.singleton;
	}
}
