package org.xtuml.bp.ui.canvas.test;

import org.eclipse.core.runtime.CoreException;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.test.common.BaseTest;

public class RectilinearRoutingTests2 extends BaseTest {

	@Override
	public void initialSetup() throws Exception {
		// need to reset the test model
		loadProject("DelegationRectilinearRoutingTests");
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(BridgePointPreferencesStore.DEFAULT_ROUTING_STYLE,
						BridgePointPreferencesStore.RECTILINEAR_ROUTING);
	}

	public void testDelegationCreationFromInnerEast() throws CoreException {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithInnerReferences");
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("InnerEast",
				innerComponent);
		Provision_c destinationProvision = RectilinearRoutingTests.locateProvision("OuterEast",
				outerComponent);
		RectilinearRoutingTests.drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

	public void testDelegationCreationFromInnerWest() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithInnerReferences");
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("InnerWest",
				innerComponent);
		Provision_c destinationProvision = RectilinearRoutingTests.locateProvision("OuterWest",
				outerComponent);
		RectilinearRoutingTests.drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

	public void testDelegationCreationFromInnerNorth() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithInnerReferences");
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("InnerNorth",
				innerComponent);
		Provision_c destinationProvision = RectilinearRoutingTests.locateProvision("OuterNorth",
				outerComponent);
		RectilinearRoutingTests.drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

	public void testDelegationCreationFromInnerSouth() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithInnerReferences");
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("InnerSouth",
				innerComponent);
		Provision_c destinationProvision = RectilinearRoutingTests.locateProvision("OuterSouth",
				outerComponent);
		RectilinearRoutingTests.drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

}
