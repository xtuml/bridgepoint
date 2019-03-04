package org.xtuml.bp.mc.docgen;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xtuml.bp.mc.AbstractExportBuilder;

public class DocgenBuilder extends AbstractExportBuilder {

    public static final String CONSOLE_NAME = "MC-3020 Build Console";

    // The shared instance
    private static DocgenBuilder singleton;

    public DocgenBuilder() {
        super(Activator.getDefault(), DocgenNature.getDefault());
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        preBuild(kind, true, false, monitor);
        return null;
    }

    /**
     * Returns the shared instance. Creates if it has not yet been created
     * 
     * @return the shared instance
     */
    public static DocgenBuilder getDefault() {
        if (DocgenBuilder.singleton == null) {
            DocgenBuilder.singleton = new DocgenBuilder();
        }
        return DocgenBuilder.singleton;
    }

}
