package org.xtuml.bp.debug.test;

import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.util.WorkspaceUtil;

public class GlobalTestSetupClass {

	public GlobalTestSetupClass() {

	}

	@Test
	public void setUpGlobalSetting() throws CoreException {

		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException
		CorePlugin.getDefault().getPreferenceStore().
        setValue(BridgePointPreferencesStore.
  		              USE_DEFAULT_NAME_FOR_CREATION, true);

	}
}
