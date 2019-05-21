package org.xtuml.bp.ui.marking;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.xtuml.bp.core.XtUMLNature;

public class MarkingDataManager {

	// Map of MarkingData for each project
	private static HashMap<IProject, MarkingData> projectMarkingData = new HashMap<IProject, MarkingData>();
	
	private MarkingDataManager() {
	}

    public static void initialize(IProject[] projects){
    	for (IProject project : projects) {
    		if (XtUMLNature.hasNature(project)) {
    			// Trigger the marking data for the project to load
    			getMarkingData(project);
    		}
    	}
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
