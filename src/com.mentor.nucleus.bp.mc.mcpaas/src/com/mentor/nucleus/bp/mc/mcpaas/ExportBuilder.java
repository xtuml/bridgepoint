package com.mentor.nucleus.bp.mc.mcpaas;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.util.BridgePointLicenseManager;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.mc.AbstractExportBuilder;

public class ExportBuilder extends AbstractExportBuilder {

	// The shared instance
	private static ExportBuilder singleton;

	public ExportBuilder() {
		super(Activator.getDefault(), MCNature.getDefault());
	}

	@Override
    protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
            throws CoreException {
        boolean oalExportIsLicensed = false;
        try {
            // Check the license
            oalExportIsLicensed = BridgePointLicenseManager
                    .getLicense(
                            BridgePointLicenseManager.LicenseAtomic.XTUMLMCEXPORT,
                            true);
            if (!oalExportIsLicensed) {
                UIUtil.showErrorDialog("License Request Failed",
                        "Failed to get a Model Compiler prebuilder license.\n");
                return null;
            }

            boolean exportNeeded = readyBuildArea(monitor);

            // Calling build again here just forces any builders that have not
            // yet run to refresh before starting. This picks up changes we may 
            // have made to the external tool builder launch file.
            getProject().build(kind, monitor);

            if (exportNeeded) {
                PersistenceManager.getDefaultInstance();
                exportModel(monitor);
            }
        } finally {
            // Must check in this license because as specified in checkout above
            // it is set to "linger", and the linger starts at checkin
            if (oalExportIsLicensed) {
                BridgePointLicenseManager
                        .releaseLicense(BridgePointLicenseManager.LicenseAtomic.XTUMLMCEXPORT);
            }
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
