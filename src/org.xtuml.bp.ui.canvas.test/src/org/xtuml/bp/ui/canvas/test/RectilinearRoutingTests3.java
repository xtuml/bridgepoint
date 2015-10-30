package org.xtuml.bp.ui.canvas.test;

import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.test.common.BaseTest;

public class RectilinearRoutingTests3 extends BaseTest {

	@Override
	public void initialSetup() throws Exception {
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(BridgePointPreferencesStore.DEFAULT_ROUTING_STYLE,
						BridgePointPreferencesStore.RECTILINEAR_ROUTING);
	}

	public void testDelegationCreationFromOuterEastToComponent() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("OuterEast",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		RectilinearRoutingTests.drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

	public void testDelegationCreationFromOuterWestToComponent() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("OuterWest",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		RectilinearRoutingTests.drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

	public void testDelegationCreationFromOuterNorthToComponent() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("OuterNorth",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		RectilinearRoutingTests.drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

	public void testDelegationCreationFromOuterSouthToComponent() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("OuterSouth",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		RectilinearRoutingTests.drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

}
