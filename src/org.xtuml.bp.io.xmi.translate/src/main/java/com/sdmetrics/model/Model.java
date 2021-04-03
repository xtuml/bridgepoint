/*
 * SDMetrics Open Core for UML design measurement
 * Copyright (c) Juergen Wuest
 * To contact the author, see <http://www.sdmetrics.com/Contact.html>.
 * 
 * This file is part of the SDMetrics Open Core.
 * 
 * SDMetrics Open Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
    
 * SDMetrics Open Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with SDMetrics Open Core.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.sdmetrics.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.sdmetrics.math.MappedCollectionsIterator;

/**
 * Container for the model elements of the UML design to be analyzed. Provides
 * access to the model elements and element filtering based on qualified names.
 */
public class Model implements Iterable<ModelElement> {
	/** Metamodel on which the elements of this model are based. */
	private final MetaModel metaModel;

	/**
	 * Data structure to hold all model elements. Elements of each type are
	 * stored in a random access list.
	 */
	private final HashMap<MetaModelElement, ArrayList<ModelElement>> elementsByType;

	/**
	 * Data structure to hold the model elements that have been accepted for
	 * output as per the element filter settings.
	 */
	private HashMap<MetaModelElement, ArrayList<ModelElement>> acceptedElementsByType;

	/**
	 * Creates a new, empty model.
	 * 
	 * @param metaModel Metamodel that defines the element types and attributes.
	 */
	public Model(MetaModel metaModel) {
		this.metaModel = metaModel;
		elementsByType = new HashMap<>(metaModel.getNumberOfTypes());
		for (MetaModelElement type : metaModel) {
			elementsByType.put(type, new ArrayList<ModelElement>());
		}
	}

	/**
	 * Applies filter settings to the elements of this model.
	 * 
	 * @param filterStrings The list of element filters to apply. Can be
	 *        <code>null</code> or empty to disable filtering.
	 * @param acceptMatchingElements Set to <code>true</code> to accept elements
	 *        matching at least on of the element filters. Set to
	 *        <code>false</code> to accept only elements that matching none of
	 *        the element filters.
	 * @param ignoreRelationsToRejectedElements Set to <code>true</code> to
	 *        ignore links to rejected elements for metrics calculation, set to
	 *        <code>false</code> to include links to rejected elements for
	 *        metrics calculation.
	 */
	public void setFilter(String[] filterStrings,
			boolean acceptMatchingElements,
			boolean ignoreRelationsToRejectedElements) {

		if (filterStrings == null || filterStrings.length == 0) {
			// disable filtering; mark
			for (ModelElement elem : this) {
				elem.setLinksIgnored(false);
			}
			acceptedElementsByType = null; // means no filter is set
			return;
		}

		ElementFilters elementFilters = new ElementFilters(filterStrings);
		acceptedElementsByType = new HashMap<>(metaModel.getNumberOfTypes());

		// iterate over all elements and determine filter status
		for (MetaModelElement type : metaModel) {
			ArrayList<ModelElement> acceptedElementList = new ArrayList<>();
			acceptedElementsByType.put(type, acceptedElementList);

			for (ModelElement elem : elementsByType.get(type)) {

				boolean elementAccepted;
				if (elementFilters.matches(elem)) {
					elementAccepted = acceptMatchingElements;
				} else {
					elementAccepted = !acceptMatchingElements;
				}

				if (elementAccepted) {
					acceptedElementList.add(elem);
				}

				elem.setLinksIgnored(ignoreRelationsToRejectedElements
						&& !elementAccepted);
			}
		}
	}

	/**
	 * Returns the list of all elements of a given type. For example, a list of
	 * all classes, all packages, etc. This method ignores filter settings, and
	 * always returns all model elements.
	 * 
	 * @param type The type ID of the elements to return.
	 * @return A random access list of all elements of the specified type.
	 */
	public List<ModelElement> getElements(MetaModelElement type) {
		return elementsByType.get(type);
	}

	/**
	 * Returns the list of accepted elements of a given type. If the element
	 * filter is active, this method only returns the elements that should
	 * appear in the output data tables, as per the filter settings.
	 * 
	 * @param type The type of the elements to return.
	 * @return A random access list of all accepted elements of the specified
	 *         type.
	 */
	public List<ModelElement> getAcceptedElements(MetaModelElement type) {
		if (acceptedElementsByType == null) {
			// filters not set or disabled => return all elements.
			return elementsByType.get(type);
		}
		return acceptedElementsByType.get(type);
	}

	/**
	 * Returns an iterator over all model elements of the model, ignoring any
	 * filters settings.
	 * 
	 * @return Iterator over all model elements of all types.
	 */
	@Override
	public Iterator<ModelElement> iterator() {
		return new MappedCollectionsIterator<>(elementsByType);
	}

	/**
	 * Retrieves the metamodel on which this model is based.
	 * 
	 * @return This model's metamodel.
	 */
	public MetaModel getMetaModel() {
		return metaModel;
	}

	/**
	 * Adds a model element to this model.
	 * 
	 * @param el The model element to add.
	 */
	void addElement(ModelElement el) {
		elementsByType.get(el.getType()).add(el);
	}

	/**
	 * Removes a model element from this model.
	 * @param el The model element to remove.
	 */
	void removeElement(ModelElement el) {
		elementsByType.get(el.getType()).remove(el);
	}
}
