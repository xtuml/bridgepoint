package org.xtuml.bp.mc;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class PreBuilder extends AbstractExportBuilder {

    // The shared instance
    private static PreBuilder singleton;
    
    private static final String DO_NOT_PARSE = "doNotParse";

    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        boolean doNotParse = null != args && args.containsKey(DO_NOT_PARSE) && Boolean.parseBoolean(args.get(DO_NOT_PARSE));
        preBuild(kind, !doNotParse, false, monitor);
        return null;
    }

    /**
     * Returns the shared instance. Creates if it has not yet been created
     * 
     * @return the shared instance
     */
    public static PreBuilder getDefault() {
        if (PreBuilder.singleton == null) {
            PreBuilder.singleton = new PreBuilder();
        }
        return PreBuilder.singleton;
    }

}
