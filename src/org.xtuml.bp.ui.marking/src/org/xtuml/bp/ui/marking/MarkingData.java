package org.xtuml.bp.ui.marking;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.xtuml.bp.core.CorePlugin;

public class MarkingData {

	public class Mark {
		public String markable_name;
		public String feature_name;
		public String path;
		public String value;
		
		public Mark() {
			markable_name = new String("");
			feature_name = new String("");
			path = new String("");
			value = new String("");
		}
	}
	
	private IProject project;
    
    private static final String DELIM = ",";

    private static final String FEATURE_FILE = "/gen/features.mark";
    private static final String MARKINGS_FILE = "/gen/application.mark";
    
    // Map of Element Types and a vector of associated features
    private HashMap<String, Vector<String>> featureMap;
    
    // Ordered map of fully-pathed application model elements and an ordered map of associated feature/value pairs
    private LinkedHashMap<String, LinkedHashMap<String,Mark>> markingsMap;
    
	public MarkingData(IProject project) {
		this.project = project;
	
		// Read in the feature map
		populateFeatures();
		
		// Read in the markings
		populateMarkings();
	}

	// Read the feature data from the file on disk.  Populate it into an
	// internal data structure.
	private void populateFeatures() {
		featureMap = new HashMap<String, Vector<String>>();
		Scanner inFile = new Scanner("");
		
		try {
			inFile = new Scanner(new FileReader(project.getLocation().toString() + FEATURE_FILE));
			inFile.useDelimiter(",|\\r|\\n");
		} catch (FileNotFoundException fnfe) {
			CorePlugin.logError("Error loading feature markings from " + FEATURE_FILE, fnfe);
		}
		
		while ( inFile.hasNext() ) {
			String elementType = inFile.next().trim();
			if ( elementType.startsWith("#") || elementType.isEmpty() ) {
				inFile.nextLine().trim();  // Throw away rest of line in case there are delimiters in the comment
				continue; 
			}
			
			String featureName = inFile.next().trim();
			Vector<String> list;
			
			if ( featureMap.containsKey(elementType) ) {
				// The element type has already been seen, add the feature to the list
				list = featureMap.get(elementType);
			} else {
				// Element Type has not been seen yet
				list = new Vector<String>();
				featureMap.put(elementType, list);
			}
			list.add(featureName);
		}
		
		inFile.close();
	}

	// Read the application marking data from the file on disk.  Populate it into an
	// internal data structure.
	private void populateMarkings() {
		markingsMap = new LinkedHashMap<String, LinkedHashMap<String,Mark>>();
		Scanner inFile = new Scanner("");
		
		try {
			inFile = new Scanner(new FileReader(project.getLocation().toString() + MARKINGS_FILE));
			inFile.useDelimiter(",|\\r|\\n");
		} catch (FileNotFoundException fnfe) {
			CorePlugin.logError("Problem loading " + MARKINGS_FILE, fnfe);
		}
		
		while ( inFile.hasNext() ) {
			String modelElement = inFile.next().trim();
			String featureName = inFile.next().trim();
			String elementType = inFile.next().trim();
			String featureValue = inFile.nextLine().trim();
			featureValue = featureValue.replaceFirst(",", "");
			updateFeature(modelElement, featureName, featureValue, elementType);
		}
		
		inFile.close();
	}

	/**
	 * Determine if the features are associated with valid OOA element types.
	 *
	 * @return String - An error message if issues were found, otherwise empty string
	 */
	public String validateFeatures() {
		Set<String> featureSet = featureMap.keySet();
		Iterator<String> featureSetIter = featureSet.iterator();
		String invalidElements = "";
		String msg = "";
		
		while (featureSetIter.hasNext()) {
			String ooaClassName = featureSetIter.next();
			ooaClassName = ooaClassName.replaceAll(" ", "");
			try {
				Class.forName(CorePlugin.getDefault().getBundle().getSymbolicName() + "." + ooaClassName + "_c");
			} catch (ClassNotFoundException e) {
				invalidElements = invalidElements + ooaClassName + "\n";
			}
		}
		
		if ( !invalidElements.isEmpty() ) {
			msg = "The features marking data contains the following invalid element types. You must\n" + 
					"correct these errors in order to edit application marks: \n\n" + invalidElements;
		}
		
		if ( msg.isEmpty() ) {
			if ( featureMap.isEmpty() ) {
				msg = "The gen/features.mark file does not contain any model compiler features. You must\n" +
					  "specify valid features to proceed.  For more information see the documentation at:\n\n" + 
					  "Help > BridgePoint UML Suite Help > Reference > User Interface > Marking Editor.\n";
			}
		}
		return msg;
	}
	
	/**
	 * Modify the user-specified value for an application mark
	 *
	 * @param modelElement string path of an instance in the application model deliminated by ::
	 * @param featureName string feature name
	 * @param newValue string value to assign to the feature
	 * @param elemType the xtUML model element type name
	 */
	public void updateFeature(String modelElement, String featureName, String newValue, String elemType) {
		LinkedHashMap<String,Mark> markList;

		if ( markingsMap.containsKey(modelElement) ) {
			// The model element has already been seen, add the feature to the list
			markList = markingsMap.get(modelElement);
		} else {
			// Element Type has not been seen yet
			markList = new LinkedHashMap<String,Mark>();
			markingsMap.put(modelElement, markList);
		}
		Mark mark = new Mark();
		mark.markable_name = elemType;
		mark.feature_name = featureName;
		mark.path = modelElement;
		mark.value = newValue;
		markList.put(getCombinedRef(featureName, elemType), mark);
	}

	/**
	 * A mark is uniquely identified by its path and the combined referential 
	 * composed of the feature name and markable name.  This function is used to 
	 * "make" the combined referential string for the internal data structure used
	 * to store and order the marks.
	 */
	public static String getCombinedRef(String f, String m) {
		return f+"###"+m;
	}
	
	/**
	 * Write the modified application marking data to disk
	 */
	public void persist() {
		try {
			FileOutputStream fout = new FileOutputStream(project.getLocation().toString() + MARKINGS_FILE);
			PrintStream stream = new PrintStream(fout);
			
			// Persist the markings
			for (Map.Entry<String, LinkedHashMap<String,Mark>> elementEntry : markingsMap.entrySet()) {
				for ( Map.Entry<String, Mark> featureEntry : elementEntry.getValue().entrySet()) {
					if ( ! featureEntry.getValue().value.isEmpty() ) {
						stream.println(featureEntry.getValue().path + DELIM + featureEntry.getValue().feature_name + DELIM + featureEntry.getValue().markable_name + DELIM + featureEntry.getValue().value);
					}
				}
			}
			fout.close();
		} catch (IOException e) {
			CorePlugin.logError("Error persisting to " + MARKINGS_FILE, e);
		}        
	}

	/**
	 * The feature marking file contains markable elements and the available
	 * features.  This returns a collection of the markable elements specified.
	 *
	 * @return Array of model element types that have features that can be marked
	 */
	public String[] getMarkables() {
		return featureMap.keySet().stream().toArray(String[]::new);
	}

	/**
	 * Returns the names of features associated with a given element type
	 *
	 * @param elementType string name of an OOA element type (e.g. Model Class, Component)
	 * @return Vector<String> collection of feature names
	 */
	public Vector<String> getFeatures(String elementType) {
		return featureMap.get(elementType);
	}

	/**
	 * Returns an ordered collection of marks for a given 
	 * application model instance.  Makes sure we return a clean/unique set
	 * of marks that avoids path collisions by using the element type to resolve
	 * ambiguities.
	 *
	 * @param modelElement string path of an instance in the application model deliminated by ::
	 * @param elemType the xtUML model element type name
	 * @return LinkedHashMap<String,ValueSet> ordered collection of feature/value pairs
	 */
	public LinkedHashMap<String,Mark> getMarks(String modelElement, String elemType) {
		LinkedHashMap<String,Mark> uncleanMarks = markingsMap.get(modelElement);
		LinkedHashMap<String,Mark> uniqueMarks = new LinkedHashMap<String,Mark>();
		
		if ( uncleanMarks == null ) {
			return null;
		} else {
			for (Map.Entry<String, Mark> entry : uncleanMarks.entrySet())
			{
				if ( entry.getValue().markable_name.equals(elemType) ) {
					uniqueMarks.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return uniqueMarks;
	}
}
