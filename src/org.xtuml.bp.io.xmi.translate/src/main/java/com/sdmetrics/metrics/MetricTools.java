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
package com.sdmetrics.metrics;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.sdmetrics.math.HashMultiSet;

/**
 * Collection of static helper methods that are useful for the implementation of
 * calculation procedures.
 */
public class MetricTools {

	/** Default return value 0. */
	static final Integer ZERO = Integer.valueOf(0);
	/** Default return value 1. */
	static final Integer ONE = Integer.valueOf(1);

	/** Hides public constructor. */
	private MetricTools() {
		// utility class
	}
	
	/**
	 * Creates a new empty set or multiset.
	 * 
	 * @param multi <code>true</code> to create a multiset, <code>false</code>
	 *        to create a regular set.
	 * @return New empty HashSet or {@link HashMultiSet}
	 */
	public static Collection<?> createHashSet(boolean multi) {
		if (multi) {
			return new HashMultiSet<>(1);
		}
		return new HashSet<>(1);
	}

	/**
	 * Creates a new set or multiset with initial contents.
	 * 
	 * @param multi <code>true</code> to create a multiset, <code>false</code>
	 *        to create a regular set.
	 * @param contents Initial contents of the (multi) set.
	 * @return New HashSet or {@link HashMultiSet} with initial contents.
	 * @since 2.3
	 */
	public static Collection<?> createHashSet(boolean multi,
			Collection<?> contents) {
		if (multi) {
			return new HashMultiSet<Object>(contents);
		}
		return new HashSet<Object>(contents);
	}

	/**
	 * Tests if a collection is a multiset.
	 * 
	 * @param c Collection to test.
	 * @return <code>true</code> if c is an instance of {@link HashMultiSet}.
	 */
	public static boolean isMultiSet(Collection<?> c) {
		return c instanceof HashMultiSet;
	}

	/**
	 * Counts the number of occurrences of an element in a set or multiset.
	 * 
	 * @param c The set or multiset. This must be an instance of java.util.Set
	 *        or {@link HashMultiSet}.
	 * @param element The element to count.
	 * @return If c is a multiset, the cardinality of the element in c.
	 *         Otherwise 1 if c contains the element, 0 if c does not contain
	 *         the element.
	 */
	public static int elementCount(Collection<?> c, Object element) {
		if (isMultiSet(c)) {
			return ((HashMultiSet<?>) c).getElementCount(element);
		} else if (c.contains(element)) {
			return 1;
		}
		return 0;
	}

	/**
	 * Removes all occurrences of an element in a set or multiset.
	 * 
	 * @param c The set or multiset. This must be an instance of java.util.Set
	 *        or {@link HashMultiSet}.
	 * @param element The element to remove.
	 */
	public static void removeAny(Collection<?> c, Object element) {
		if (isMultiSet(c)) {
			((HashMultiSet<?>) c).removeAny(element);
		} else {
			c.remove(element);
		}
	}

	/**
	 * Obtains an iterator over the unique elements of a set or multiset.
	 * 
	 * @param <T> Type of the elements in the set
	 * @param c The set or multiset.
	 * @return An iterator that, in the case of multisets, ignores the
	 *         cardinality of elements and returns each element only once.
	 */
	public static <T> Iterator<T> getFlatIterator(Collection<T> c) {
		if (isMultiSet(c)) {
			return ((HashMultiSet<T>) c).getFlatIterator();
		}
		return c.iterator();
	}

	/**
	 * Optimizes a float return value for a metric.
	 * <p>
	 * If the value has no fractional part and less than 6 significant digits,
	 * its value is wrapped in an Integer.
	 * 
	 * @param f The return value of the metric
	 * @return If possible, an Integer with the value of the float, otherwise a
	 *         Float instance
	 */
	public static Number getNumber(float f) {
		if (f > -100000 && f < 100000) {
			if (f == Math.floor(f)) {
				return Integer.valueOf((int) f);
			}
		}
		return Float.valueOf(f);
	}

	/**
	 * Checks if an object represents an empty model element reference. Empty
	 * model element references are represented by empty strings.
	 * 
	 * @param o Object to test
	 * @return <code>true</code> if <code>o</code> is an empty String
	 * @since 2.3
	 */
	public static boolean isEmptyElement(Object o) {
		return (o instanceof String) && "".equals(o);
	}
}
