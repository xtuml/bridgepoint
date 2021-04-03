package com.sdmetrics.model;

import java.util.ArrayList;

/**
 * Processes the element filter matching.
 */
class ElementFilters {
	/** Stores the tokenized element filter strings. */
	private final String[][] filters;
	/** Name fragments of a model element to test. */
	private final ArrayList<String> nameFragments = new ArrayList<>();

	/**
	 * Constructor.
	 * @param filterStrings The list of applicable element filter strings.
	 */
	ElementFilters(String[] filterStrings) {
		// tokenize the list of filter strings
		filters = new String[filterStrings.length][];
		for (int i = 0; i < filters.length; i++) {
			filters[i] = filterStrings[i].split("\\.");
		}
	}

	/**
	 * Tests if a model element matches one of the filter strings.
	 * 
	 * @param element the element to test
	 * @return <code>true</code> if the model element matches at least one of
	 *         the filter strings of this filter, <code>false</code> if it
	 *         matches none of the filter strings.
	 */
	boolean matches(ModelElement element) {
		// collect the fragments of the qualified name of the model
		ModelElement me = element;
		nameFragments.clear();
		while (me != null) {
			nameFragments.add(me.getName());
			me = me.getOwner();
		}

		// check if any of the filter strings gives a match
		for (String[] filter : filters) {
			if (matches(filter)) {
				return true;
			}
		}
		return false;
	}

	private boolean matches(String[] filter) {
		int fragmentCount = nameFragments.size();
		if (fragmentCount < filter.length) {
			return false;
		}
		for (int i = 0; i < filter.length; i++) {
			if (!"#".equals(filter[i])) {
				if (!filter[i].equals(nameFragments.get(fragmentCount - i - 1))) {
					return false;
				}
			}
		}
		return true;
	}
}