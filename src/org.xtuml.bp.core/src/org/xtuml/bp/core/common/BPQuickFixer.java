package org.xtuml.bp.core.common;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

import org.xtuml.bp.core.CorePlugin;

public class BPQuickFixer implements IMarkerResolutionGenerator {

	@Override
    public IMarkerResolution[] getResolutions(IMarker mk) {
        try {
           Object problem = mk.getAttribute(IMarker.MESSAGE);
           if (problem.toString().startsWith("Found a dangling reference")) {
             return new IMarkerResolution[] {
                new BPQuickFix(problem.toString(), mk.getResource())
             };
           }
        }
        catch (CoreException e) {
           CorePlugin.logError("QuickFixer.java: Exception handling dangling reference", e);
        }
        return new IMarkerResolution[0];
     }
}
