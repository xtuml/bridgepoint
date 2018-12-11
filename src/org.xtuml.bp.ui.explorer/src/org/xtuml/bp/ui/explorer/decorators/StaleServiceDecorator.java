package org.xtuml.bp.ui.explorer.decorators;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Deployment_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.TerminatorService_c;
import org.xtuml.bp.core.Terminator_c;
import org.xtuml.bp.core.inspector.IModelClassInspector;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.inspector.ObjectElement;

public class StaleServiceDecorator implements ILightweightLabelDecorator {

    private static final ImageDescriptor SYNC_OVERLAY = CorePlugin.getImageDescriptor("warning_co.gif");
    public static final String ID = "org.xtuml.bp.ui.explorer.synchronizationDecorator"; //$NON-NLS-1$

    @Override
    public void decorate(Object element, IDecoration decoration) {
        if (isStale(element)) {
            decoration.addOverlay(SYNC_OVERLAY, IDecoration.BOTTOM_LEFT);
        }
    }

    private boolean isStale(Object element) {
        if (element instanceof TerminatorService_c) {
            return ((TerminatorService_c) element).getIs_stale();
        } else if (element instanceof SystemModel_c || element instanceof Package_c || element instanceof Deployment_c || element instanceof Terminator_c){
            IModelClassInspector elementInspector = new ModelInspector().getInspector(element.getClass());
            for (ObjectElement el : elementInspector.getChildRelations(element)) {
              if (isStale(el.getValue())) return true;
            }
            return false;
        } else {
        	return false;
        }
    }

    @Override
    public void addListener(ILabelProviderListener listener) {
        // not required
    }

    @Override
    public void dispose() {
        // not required
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        // not required
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
        // not required
    }

}
