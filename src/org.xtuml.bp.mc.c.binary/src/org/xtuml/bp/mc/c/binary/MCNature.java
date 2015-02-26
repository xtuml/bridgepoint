package org.xtuml.bp.mc.c.binary;

import org.eclipse.core.resources.IProject;

import org.xtuml.bp.mc.AbstractNature;

public class MCNature extends AbstractNature {
	// The shared instance
	private static MCNature singleton;

	/**
	 * identifier of this nature in plugin.xml - (concatenate pluginid.natureid)
	 */
	public static final String MC_NATURE_ID = "org.xtuml.bp.mc.c.binary.MCNature"; //NON-NLS-1
	public static final String MC_NATURE_ID_OLD = "com.mentor.nucleus.bp.mc.c.binary.MCNature"; //NON-NLS-1

	/**
	 * identifier of this nature in plugin.xml - (concatenate
	 * pluginid.exportbuilderid)
	 */
	public static final String EXPORT_BUILDER_ID = "org.xtuml.bp.mc.c.binary.export_builder"; //NON-NLS-1

	public MCNature() {
		super(Activator.getDefault(), EXPORT_BUILDER_ID);
		singleton = this;
	}

	/**
	 * Returns the shared instance. Creates if it has not yet been created
	 * 
	 * @return the shared instance
	 */
	public static MCNature getDefault() {
		if (MCNature.singleton == null) {
			MCNature.singleton = new MCNature();
		}
		return MCNature.singleton;
	}

	@Override
	public boolean hasNature(IProject project) {
		return hasNature(project, MC_NATURE_ID) || hasNature(project, MC_NATURE_ID_OLD);
	}

	@Override
	public boolean addNature(IProject project) {
		return addNature(project, MC_NATURE_ID);
	}

}
