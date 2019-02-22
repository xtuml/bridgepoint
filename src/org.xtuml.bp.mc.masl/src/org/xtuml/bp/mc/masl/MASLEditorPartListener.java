package org.xtuml.bp.mc.masl;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.io.core.CorePlugin;

public class MASLEditorPartListener implements IPartListener2 {
    @Override
    public void partClosed(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partActivated(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partBroughtToTop(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partDeactivated(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partOpened(IWorkbenchPartReference partRef) {
        if (!partRef.getId().equals("org.xtuml.bp.xtext.masl.MASL")
                && !partRef.getId().equals("org.xtuml.bp.xtext.masl.MASLPartial")) {
            return;
        }

        IStructuredSelection sel = Selection.getInstance().getStructuredSelection();
        if (sel.isEmpty()) {
            return;
        }
        Object current = sel.iterator().next();
        Object dialectObj = current;
        if (dialectObj instanceof NonRootModelElement) {
            NonRootModelElement nrme = ((NonRootModelElement) dialectObj).getRoot();
            if (nrme instanceof SystemModel_c) {
                IProject project = (IProject) ((SystemModel_c) nrme).getAdapter(IProject.class);
                try {
                    project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, MCNature.BUILDER_ID,
                            new SingleEntryStringMap("refreshBuild", "true"), new NullProgressMonitor());
                } catch (CoreException e) {
                    CorePlugin.logError("Failed running MASL refresher: ", e);
                }
            }
        }
    }

    @Override
    public void partHidden(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partVisible(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partInputChanged(IWorkbenchPartReference partRef) {
    }

}
