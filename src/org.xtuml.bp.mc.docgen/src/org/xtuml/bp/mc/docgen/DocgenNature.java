package org.xtuml.bp.mc.docgen;

import org.eclipse.core.resources.IProject;
import org.xtuml.bp.mc.AbstractNature;

public class DocgenNature extends AbstractNature {
    // The shared instance
    private static DocgenNature singleton;

    /**
     * identifier of this nature in plugin.xml - (concatenate pluginid.natureid)
     */
    public static final String BUILDER_ID = "org.xtuml.bp.mc.docgen.docgen_builder";
    public static final String MC_NATURE_ID = "org.xtuml.bp.mc.docgennature"; // NON-NLS-1

    public DocgenNature() {
        super(Activator.getDefault(), BUILDER_ID);
        singleton = this;
    }

    /**
     * Returns the shared instance. Creates if it has not yet been created
     * 
     * @return the shared instance
     */
    public static DocgenNature getDefault() {
        if (DocgenNature.singleton == null) {
            DocgenNature.singleton = new DocgenNature();
        }
        return DocgenNature.singleton;
    }

    @Override
    public boolean hasNature(IProject project) {
        return hasNature(project, MC_NATURE_ID);
    }

    @Override
    public boolean addNature(IProject project) {
        return addNature(project, MC_NATURE_ID);
    }

    @Override
    public boolean removeNature(IProject project) {
        return removeNature(project, MC_NATURE_ID);
    }

}
