package org.xtuml.bp.cli;

import org.eclipse.core.runtime.NullProgressMonitor;

public class PrintingProgressMonitor extends NullProgressMonitor {

    @Override
    public void beginTask(String name, int totalWork) {
        if (name != null && name.length() > 0)
            System.out.println(name);
    }

}
