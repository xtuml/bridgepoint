package org.xtuml.bp.ui.explorer.decorators;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.TerminatorService_c;

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
