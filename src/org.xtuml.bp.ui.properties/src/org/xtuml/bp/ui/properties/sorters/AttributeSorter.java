package org.xtuml.bp.ui.properties.sorters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.xtuml.bp.core.sorter.MetadataSortingManager.ISorter;

/**
 * Attributes have a custom order that is not meta-model based or alphanumeric based.
 * 
 * @author Travis London
 *
 */
public class AttributeSorter implements ISorter {

	/*
	 * We want the following order for now
	 * 
	 * Array Dimensions [0]
	 * Name             [1]
	 * Prefix           [2]
	 * Prefix Mode      [3]
	 * Root Name        [4]
	 * Default Value    [5]
	 * Description      [6]
	 * Type             [7]
	 * 
	 */
	static Map<String, Integer> orderedObjects = new HashMap<String, Integer>();
	static {
		orderedObjects.put("Array Dimensions", 0);
		orderedObjects.put("Attribute Name", 1);
		orderedObjects.put("Attribute Name Prefix", 2);
		orderedObjects.put("Prefix Mode", 3);
		orderedObjects.put("Attribute Root Name", 4);
		orderedObjects.put("Default Value", 5);
		orderedObjects.put("Description", 6);
		orderedObjects.put("Type", 7);		
	}
	
	@Override
	public boolean canSort(Object[] elements) {
		return true;
	}

	/**
	 * Elements will be of interface type {@code IPropertySheetEntry}
	 */
	@Override
	public void sort(Object[] elements) {
		// custom sorting to allow Pfx_Mode directly under Attribute Name Prefix
		// Note they will come alpha sorted
		int count = 0;
		Object[] entries = Arrays.copyOf(elements, elements.length);
		for(Object object : entries) {
			IPropertySheetEntry entry = (IPropertySheetEntry) object;
			Integer location = orderedObjects.get(entry.getDisplayName());
			if(location != null) {
				elements[location] = entry;
			} else {
				// use the count, more elements then ordered
				elements[count] = entry;
			}
			count += 1;
		}
	}

}
