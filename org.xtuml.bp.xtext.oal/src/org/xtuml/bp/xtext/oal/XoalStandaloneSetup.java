
package org.xtuml.bp.xtext.oal;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class XoalStandaloneSetup extends XoalStandaloneSetupGenerated{

	public static void doSetup() {
		new XoalStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

