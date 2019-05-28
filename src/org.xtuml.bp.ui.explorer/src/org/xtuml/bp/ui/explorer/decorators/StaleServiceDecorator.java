package org.xtuml.bp.ui.explorer.decorators;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Deployment_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.TerminatorService_c;
import org.xtuml.bp.core.Terminator_c;

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
        } else if (element instanceof SystemModel_c) {
            for (Package_c pkg : Package_c.getManyEP_PKGsOnR1401((SystemModel_c) element, null, false)) {
                if (isStale(pkg)) {
                    return true;
                }
            }
            return false;
        } else if (element instanceof Package_c) {
            for (PackageableElement_c pe : PackageableElement_c.getManyPE_PEsOnR8000((Package_c) element, null, false)) {
                Package_c subpkg = Package_c.getOneEP_PKGOnR8001(pe, false);
                if (isStale(subpkg)) {
                    return true;
                }
                Deployment_c depl = Deployment_c.getOneD_DEPLOnR8001(pe, false);
                if (isStale(depl)) {
                    return true;
                }
            }
            return false;
        } else if (element instanceof Deployment_c) {
            for (Terminator_c term : Terminator_c.getManyD_TERMsOnR1650((Deployment_c) element, null, false)) {
                if (isStale(term)) {
                    return true;
                }
            }
            return false;
        } else if (element instanceof Terminator_c) {
            for (TerminatorService_c svc : TerminatorService_c.getManyD_TSVCsOnR1651((Terminator_c) element, null, false)) {
                if (isStale(svc)) {
                    return true;
                }
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
