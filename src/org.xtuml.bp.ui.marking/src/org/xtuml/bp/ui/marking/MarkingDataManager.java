package org.xtuml.bp.ui.marking;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;

public class MarkingDataManager {

	// Map of MarkingData for each project
	private static HashMap<IProject, MarkingData> projectMarkingData = new HashMap<IProject, MarkingData>();
	
	private MarkingDataManager() {
	}

	public static MarkingData getMarkingData(IProject project) {
		MarkingData md = projectMarkingData.get(project);
		if ( md == null ) {
			md = new MarkingData(project);
			projectMarkingData.put(project, md);
		}
		return md;
	}
	
}
