package org.xtuml.bp.ui.properties.sorters;

import java.util.HashMap;

import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.sorter.MetadataSortingManager.ISorter;
import org.xtuml.bp.core.ui.Selection;

public class BridgePointPropertySheetSorters {

	static HashMap<Class<?>, ISorter> sorters = new HashMap<Class<?>, ISorter>();
	
	static {
		sorters.put(Attribute_c.class, new AttributeSorter());
	}
	
	public static ISorter getSorter() {
		// we have no other way to determine the model element
		// for which the properties are shown for  So we are
		// using the current core selection as that is the only
		// data the properties view has to populate with (its guaranteed)
		NonRootModelElement[] selected = Selection.getInstance().getSelectedNonRootModelElements();
		// properties can only be shown for one element at
		// a time
		if(selected.length == 1) {
			return sorters.get(selected[0].getClass());
		}
		return null;
	}

}
