
package org.argouml.xtuml;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class OALStandaloneSetup extends OALStandaloneSetupGenerated{

	public static void doSetup() {
		new OALStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

