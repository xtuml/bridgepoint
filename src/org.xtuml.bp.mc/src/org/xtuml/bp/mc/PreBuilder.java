package org.xtuml.bp.mc;

public class PreBuilder extends AbstractExportBuilder {

    // The shared instance
    private static PreBuilder singleton;

    /**
     * Returns the shared instance. Creates if it has not yet been created
     * 
     * @return the shared instance
     */
    public static PreBuilder getDefault() {
        if (PreBuilder.singleton == null) {
            PreBuilder.singleton = new PreBuilder();
        }
        return PreBuilder.singleton;
    }

}
