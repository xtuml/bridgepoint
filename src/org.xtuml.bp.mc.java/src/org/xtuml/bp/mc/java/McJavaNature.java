package org.xtuml.bp.mc.java;

import org.eclipse.core.resources.IProject;

import org.xtuml.bp.mc.AbstractNature;

public class McJavaNature extends AbstractNature {
    // The shared instance
    private static McJavaNature singleton;

    /**
     * identifier of this nature in plugin.xml - (concatenate pluginid.natureid)
     */
    public static final String MC_NATURE_ID = "org.xtuml.bp.mc.java.mcjavanature"; // NON-NLS-1

    /**
     * identifier of this nature in plugin.xml - (concatenate
     * pluginid.exportbuilderid)
     */
    public static final String EXPORT_BUILDER_ID = "org.xtuml.bp.mc.java.mcjava_builder"; // NON-NLS-1

    public McJavaNature() {
        super(Activator.getDefault(), EXPORT_BUILDER_ID);
        singleton = this;
    }

    /**
     * Returns the shared instance. Creates if it has not yet been created
     * 
     * @return the shared instance
     */
    public static McJavaNature getDefault() {
        if (McJavaNature.singleton == null) {
            McJavaNature.singleton = new McJavaNature();
        }
        return McJavaNature.singleton;
    }

    @Override
    public boolean hasNature(IProject project) {
        return hasNature(project, MC_NATURE_ID);
    }

    @Override
    public boolean addNature(IProject project) {
        return addNature(project, MC_NATURE_ID);
    }

}
