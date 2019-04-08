package org.xtuml.bp.mc.mc3020;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.xtuml.bp.mc.AbstractNature;
import org.xtuml.bp.mc.mc3020.preferences.Mc3020Preferences;

public class Mc3020Nature extends AbstractNature {
    // The shared instance
    private static Mc3020Nature singleton;

    /**
     * identifier of this nature in plugin.xml - (concatenate pluginid.natureid)
     */
    public static final String BUILDER_ID = "org.xtuml.bp.mc.mc3020.mc3020_builder";
    public static final String MC_NATURE_ID = "org.xtuml.bp.mc.mc3020nature"; // NON-NLS-1

    public Mc3020Nature() {
        super(Activator.getDefault(), BUILDER_ID);
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
    public void deconfigure() throws CoreException {
        // Restore default preferences. This effectively removes the settings file.
        Mc3020Preferences prefs = new Mc3020Preferences(getProject());
        prefs.restoreDefaults();
        prefs.savePreferences();
        super.deconfigure();
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
