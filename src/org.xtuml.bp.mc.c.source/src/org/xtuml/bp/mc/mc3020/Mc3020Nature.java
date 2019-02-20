package org.xtuml.bp.mc.mc3020;

import org.eclipse.core.resources.IProject;
import org.xtuml.bp.mc.AbstractNature;

public class Mc3020Nature extends AbstractNature {
    // The shared instance
    private static Mc3020Nature singleton;

    /**
     * identifier of this nature in plugin.xml - (concatenate pluginid.natureid)
     */
    public static final String MC_NATURE_ID = "org.xtuml.bp.mc.mc3020nature"; // NON-NLS-1

    public Mc3020Nature() {
        super(Activator.getDefault(), Mc3020Builder.BUILDER_ID);
        singleton = this;
    }

    /**
     * Returns the shared instance. Creates if it has not yet been created
     * 
     * @return the shared instance
     */
    public static Mc3020Nature getDefault() {
        if (Mc3020Nature.singleton == null) {
            Mc3020Nature.singleton = new Mc3020Nature();
        }
        return Mc3020Nature.singleton;
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
