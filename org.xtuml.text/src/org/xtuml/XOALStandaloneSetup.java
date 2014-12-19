
package org.xtuml;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class XOALStandaloneSetup extends XOALStandaloneSetupGenerated{

	public static void doSetup() {
		new XOALStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

