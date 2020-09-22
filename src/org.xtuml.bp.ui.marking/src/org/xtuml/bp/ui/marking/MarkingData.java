package org.xtuml.bp.ui.marking;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.TerminatorService_c;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;

public class MarkingData {

	public class Mark {
		public String markable_name;
		public String feature_name;
		public String path;
		public String value;
		public NonRootModelElement nrme;
		
		public Mark() {
			markable_name = new String("");
			feature_name = new String("");
			path = new String("");
			value = new String("");
			nrme = null;
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
			// With the change to add the marking listener the data may be attempted to be loaded on a 
			// project without the .mark files.  This may be fine as not all projects use this style of
			// marking.  So we don't do anything when the file is not found.
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
			// With the change to add the marking listener the data may be attempted to be loaded on a 
			// project without the .mark files.  This may be fine as not all projects use this style of
			// marking.  So we don't do anything when the file is not found.
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
	 * @param modelElement string path of an instance in the application model delimited by ::
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
      if (modelElement.equals("*")) {
          mark.nrme = null;
      } else {
		mark.nrme = MarkingData.getNRMEForMark(modelElement, elemType, this.project);
      }
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
	 * Iterate through all the marks and update model path keys. 
    * Retains marks with '*' data as is.
    * Retries added for possible model loading race conditions. This only 
    * applies to attribute changes.
	 *
    * @param deltaToHandle - the change data to use for retries.
    * 
	 * @return Flag indicating if the markings had to be updated or not 
	 */
   public boolean recalculatePathKeys(IModelDelta deltaToHandle) {
		LinkedHashMap<String,Mark> newMarkList;
		LinkedHashMap<String, LinkedHashMap<String,Mark>> newMarkingsMap = new LinkedHashMap<String, LinkedHashMap<String,Mark>>();
		boolean marksUpdated = false;
		
		for (Map.Entry<String, LinkedHashMap<String,Mark>> elementEntry : markingsMap.entrySet()) {
			Set<Map.Entry<String, Mark>> featureEntrySet = elementEntry.getValue().entrySet();
         String newPath;
         String currentPath = elementEntry.getKey();

         Map.Entry<String, Mark> firstfeatureEntry = featureEntrySet.iterator().next(); 
         // Handle '*' in path as a special case per Ciera requirements.
         if (currentPath.equals("*")) {
             newPath = currentPath;
         } else if ( (deltaToHandle.getKind() == Modeleventnotification_c.DELTA_ATTRIBUTE_CHANGE) &&
        		    ((AttributeChangeModelDelta) deltaToHandle).getOldValue() != null &&
                     !(currentPath.matches(".*\\b" +
                       ((AttributeChangeModelDelta)deltaToHandle).getOldValue().toString() + 
                       "\\b.*")) ) {
             // Attribute change with attribute not in path.
             newPath = currentPath;
         } else {
			// Run a path check on the first mark in the set.  If it's OK, then all the marks in this set
			// are OK and we skip further processing.  If it's not OK, we must iterate through all the marks
			// for this model element and update them.
			// Use the mark's nrme to get the updated path and see if it matches elementEntry.getKey()
            newPath = MarkingData.getPathkey((NonRootModelElement) firstfeatureEntry.getValue().nrme, project);
            // Due to the possibility of a race condition between the model update causing this transaction and 
            // this transaction handling, additional steps need to be taken to ensure the return of the 
            // previous call wasn't due to being out of sync with the model.
            if ((newPath == null) || newPath.isEmpty()) {
               // Is this mark involved in the current transaction as an attribute change?
               if ( deltaToHandle.getKind() == Modeleventnotification_c.DELTA_ATTRIBUTE_CHANGE ) { 
                  AttributeChangeModelDelta change = (AttributeChangeModelDelta)deltaToHandle;
                  String oldAttributeValue = change.getOldValue().toString();
                  String newAttributeValue = change.getNewValue().toString();
                  // This change would have to affect the path to return a null NRME, so check the path.
                  String modelElement = firstfeatureEntry.getValue().path;
                  if (modelElement.matches(".*\\b" + oldAttributeValue + "\\b.*")) {
                     // There can be more than one match in the path, so we have to change each until a
                     // valid nrme is found.
                     NonRootModelElement newNrme = null;
                     boolean allTried = false;
                     int idx = modelElement.indexOf(oldAttributeValue);
                     int lastIdx = modelElement.lastIndexOf(oldAttributeValue);
                     while (!allTried && (null == newNrme)) {
                         String updatedPath = modelElement.replaceFirst("(.{" + idx + "})\\b" + oldAttributeValue + "\\b(.*)", "$1" + newAttributeValue + "$2");
                         newNrme = MarkingData.getNRMEForMark(updatedPath, firstfeatureEntry.getValue().markable_name, this.project);
                         if (idx == lastIdx) {
                             allTried = true;
                         } else {
                             idx = modelElement.indexOf(oldAttributeValue, idx + oldAttributeValue.length());
                             updatedPath = modelElement;
                         }
                     }
                     newPath = MarkingData.getPathkey(newNrme, project);
                  } 
               }
            }
         }
			if ((newPath == null) || newPath.isEmpty()) {
				// A container for the marked element must have been deleted, remove the mark
				marksUpdated = true;
				continue;
			} else if ( newPath.equals(currentPath)) {
				// This element's path is not affected, short circuit and copy across the whole 
				// mark list to newMarkingsMap and move on
				newMarkingsMap.put(newPath, elementEntry.getValue());
				continue;
			} else {
				// This element's path _is_ changed, iterate through each mark and update the path 
				// in the mark and store under the new path in the newMarkingsMap
				marksUpdated = true;
				newMarkList = new LinkedHashMap<String,Mark>();
				newMarkingsMap.put(newPath, newMarkList);
						
				for ( Map.Entry<String, Mark> featureEntry : featureEntrySet) {
					Mark origMark = featureEntry.getValue();
					Mark mark = new Mark(); 
					mark.markable_name = origMark.markable_name; 
					mark.feature_name = origMark.feature_name; 
					mark.path = newPath; 
					mark.value = origMark.value; 
					mark.nrme = origMark.nrme; 
					newMarkList.put(featureEntry.getKey(), mark);
				}
			}
		}
		
		// Use the newly updated map of markings
		if (marksUpdated) {
			markingsMap = newMarkingsMap;
		}
		
		return marksUpdated;
	}

	/**
    * Check the value data, contained in the mark, for change and update on change.
    * @param nrmeOfChange - the NRME obtained from the transaction
    * @param newAttributeValue - the new value
    * @param oldAttributeValue - the old value
    * @return Indicates if the marking data needs to be persisted.
    */
   public boolean updateValueData(NonRootModelElement nrmeOfChange, String newAttributeValue, String oldAttributeValue)
   {
      LinkedHashMap<String, LinkedHashMap<String,Mark>> newMarkingsMap = new LinkedHashMap<String, LinkedHashMap<String,Mark>>();
      boolean marksUpdated = false;
      
      for (Map.Entry<String, LinkedHashMap<String,Mark>> elementEntry : markingsMap.entrySet()) {
         LinkedHashMap<String,Mark> newMarkList = new LinkedHashMap<String,Mark>();
         for ( Map.Entry<String, Mark> featureEntry : elementEntry.getValue().entrySet() ) {
            // Check for model loading/mark loading race condition.
            NonRootModelElement nrmeCurrent = featureEntry.getValue().nrme;
            if ( (null == nrmeCurrent) && !featureEntry.getValue().path.equals("*") ){
                nrmeCurrent = getNRMEForMark(featureEntry.getValue().path, featureEntry.getValue().markable_name, this.project);
            }
            String markValue = featureEntry.getValue().value;
            if ( markValue.matches(".*\\b" + oldAttributeValue + "\\b.*") && 
                 (featureEntry.getValue().path.equals("*") || nrmeOfChange.equals(nrmeCurrent)) ) {
               marksUpdated = true;
               Mark origMark = featureEntry.getValue();
               Mark mark = new Mark();
               mark.markable_name = origMark.markable_name;
               mark.feature_name = origMark.feature_name;
               mark.path = origMark.path;
               mark.value = markValue.replace(oldAttributeValue, newAttributeValue);
               mark.nrme = origMark.nrme;
               newMarkList.put(featureEntry.getKey(), mark);
            } else {
               // Unchanged entry
               newMarkList.put(featureEntry.getKey(), featureEntry.getValue());
            }
         }
         newMarkingsMap.put(elementEntry.getKey(), newMarkList);
      }
      // Use the newly updated map of markings
      if (marksUpdated) {
         markingsMap = newMarkingsMap;
      }
      return marksUpdated;
   }

   /**
	 * Write the modified application marking data to disk
	 */
	public void persist() {
		try {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(project.getFullPath().append(MARKINGS_FILE));
			
			StringJoiner markingContentBuilder = new StringJoiner("\n");
			// Persist the markings
			for (Map.Entry<String, LinkedHashMap<String,Mark>> elementEntry : markingsMap.entrySet()) {
				for ( Map.Entry<String, Mark> featureEntry : elementEntry.getValue().entrySet()) {
					if ( ! featureEntry.getValue().value.isEmpty() ) {
						markingContentBuilder.add(featureEntry.getValue().path + DELIM + featureEntry.getValue().feature_name + DELIM + featureEntry.getValue().markable_name + DELIM + featureEntry.getValue().value);
					}
				}
			}
			ByteArrayInputStream bias = new ByteArrayInputStream(markingContentBuilder.toString().getBytes());
			file.setContents(bias, IFile.DEPTH_ONE, new NullProgressMonitor());
		} catch (CoreException e) {
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
	 * @param modelElement string path of an instance in the application model delimited by ::
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

	/**
	 * Returns an collection of marks for a given application model instance using 
	 * a NonRootModelElement to find matches. 
	 *
	 * @param nrme A NonRootModelElement instance to match against 
	 * @return LinkedHashMap<String,Mark> collection of feature/value pairs
	 */
	public LinkedHashMap<String,Mark> getMarks(NonRootModelElement nrme) {
		LinkedHashMap<String,Mark> marks = new LinkedHashMap<String,Mark>();
		
		for (Map.Entry<String, LinkedHashMap<String,Mark>> entry : markingsMap.entrySet())
		{
			marks = entry.getValue();
			for (Map.Entry<String, Mark> markEntry : marks.entrySet())
			{
				if ( markEntry.getValue().nrme == nrme ) {
					return getMarks(markEntry.getKey(), markEntry.getValue().markable_name);
				}
			}
		}
		return null;
	}

	public static String getPathkey(NonRootModelElement inst, IProject project) {
		String signature = new String("");
		if (inst == null) { return ""; }
		String pathkey = ((NonRootModelElement) inst).getPath();
		
		// If we're dealing with anything other than a package under the system and the path comes back as 
		// just the element name, this means the instance is being deleted and does not currently have a 
		// valid path.
		if ( !(inst instanceof Package_c) ||
				((inst instanceof Package_c) && ( SystemModel_c.getOneS_SYSOnR1401((Package_c)inst) == null))) {
			if (pathkey.equals(inst.getName())) {
				return "";
			}
		}

		// If the instance requires a full signature, replace the last segment which
		// is the name with the full signature
		if (inst instanceof Function_c) {
			signature = ((Function_c) inst).Getsignature(1);
		} else if (inst instanceof Operation_c) {
			signature = ((Operation_c) inst).Getsignature(1);
		} else if (inst instanceof InterfaceOperation_c) {
			signature = ((InterfaceOperation_c) inst).Getsignature(1);
		} else if (inst instanceof InterfaceSignal_c) {
			signature = ((InterfaceSignal_c) inst).Getsignature(1);
		} else if (inst instanceof TerminatorService_c) {
			signature = ((TerminatorService_c) inst).Getsignature(1);
		}
		
		if (!signature.isEmpty()) {
			signature = signature.replaceAll(", ", " ");
			String[] pathPieces = pathkey.split("::");
			String updatedPath = new String("");
			for (int i=0; i<pathPieces.length-1; ++i) {
				updatedPath = updatedPath.concat(pathPieces[i] + "::");
			}
			updatedPath = updatedPath.concat(signature);
			pathkey = updatedPath;
		}

		pathkey = pathkey.replaceFirst(project.getName() + "::", "");
		return pathkey;
	}
	
	/*
	 * Gather and return a collection of all the application model instances of a requested type
	 * (i.e. OOA metamodel class like Attribute, Model Class, Function, etc) from all the model roots
	 * in the given project.
	 */
	public static ArrayList<Object> getInstancesForType(String elementType, IProject project) {
		ArrayList<Object> allInstances = new ArrayList<Object>();

		try {
			String ooaClassName = elementType.trim();
			ooaClassName = ooaClassName.replaceAll(" ", "");
			// Get the Class object for the metamodel class with the specified name
			Class<?> clazz = Class.forName(CorePlugin.getDefault().getBundle().getSymbolicName() + "." + ooaClassName + "_c");

			ModelRoot[] roots = Ooaofooa.getInstancesUnderSystem(project.getName());
			// This Java reflection call helps invoke a method like this:
			// ModelClass_c[] mcs = ModelClass_c.ModelClassInstances(modelRoot);
			// So the loop here finds all instances of a given OOAofOOA class in
			// a project.
			Method instancesMethod = clazz.getMethod(ooaClassName + "Instances", ModelRoot.class);
			for (ModelRoot modelroot : roots) {
				Object[] instances = (Object[]) instancesMethod.invoke(null, modelroot);
				Collections.addAll(allInstances, instances);
			}
		} catch (ClassNotFoundException | NoSuchMethodException | NullPointerException | SecurityException |
				IllegalAccessException | IllegalArgumentException | InvocationTargetException | 
				ExceptionInInitializerError e) {
			CorePlugin.logError(e.toString(), e);
		}
		return allInstances;
	}
	
	public static NonRootModelElement getNRMEForMark(String path, String elementType, IProject project) {
		ArrayList<Object> instances = MarkingData.getInstancesForType(elementType, project);
		for (Object candidate: instances) {
			String candidatePath = MarkingData.getPathkey((NonRootModelElement) candidate, project);
			if ( path.equals(candidatePath) ) {
				return (NonRootModelElement) candidate;
			}
		}
		return null;
	}
}
