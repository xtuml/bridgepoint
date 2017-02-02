package org.xtuml.bp.ui.canvas.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;

@RunWith(OrderedRunner.class)
public class RectilinearRoutingTests3 extends BaseTest {

	private static boolean isFirstTime = true;
	@Override
//	@Before
	public void initialSetup() throws Exception {
		if (!isFirstTime)
			return;
		isFirstTime = false;
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(BridgePointPreferencesStore.DEFAULT_ROUTING_STYLE,
						BridgePointPreferencesStore.RECTILINEAR_ROUTING);
	}

	@Test
	public void testDelegationCreationFromOuterEastToComponent() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("OuterEast",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		RectilinearRoutingTests.drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

	@Test
	public void testDelegationCreationFromOuterWestToComponent() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("OuterWest",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		RectilinearRoutingTests.drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

	@Test
	public void testDelegationCreationFromOuterNorthToComponent() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = RectilinearRoutingTests.locateProvision("OuterNorth",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		RectilinearRoutingTests.drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

	@Test
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
