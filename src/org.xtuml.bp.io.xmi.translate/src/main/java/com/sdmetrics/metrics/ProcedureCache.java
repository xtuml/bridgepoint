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

import java.util.HashMap;
import java.util.LinkedList;

/**
 * A cache for calculation procedure and expression operation instances during a
 * calculation run.
 * <p>
 * Each calculation of a metric, set, or rule for a model elements requires a
 * calculation procedure instance that executes the calculation algorithm.
 * Likewise, each operation in a metric, set or boolean expression requires an
 * operation instance to calculate the operation value. Because the calculation
 * classes are configurable, their instantiation involves reflection. To avoid
 * having to create lots of calculation procedure instances by reflection, we
 * keep them in a cache for reuse.
 * 
 * @param <T> The type of calculation procedures stored in this cache.
 */
abstract class ProcedureCache<T extends AbstractProcedure> {

	/** Represents one calculation procedure class. */
	private class ProcedureInfo {
		/** The calculation procedure class. */
		Class<? extends T> procedureClass;
		/** The available instances of the class. */
		LinkedList<T> availableInstances = new LinkedList<>();
	}

	/**
	 * Stores the calculation procedure instances by their procedure names
	 * (projection, compoundmetric, etc).
	 */
	private final HashMap<String, ProcedureInfo> buckets = new HashMap<>();

	/**
	 * Describes elements in the cache (such as"metric procedure" or
	 * "boolean operator").
	 */
	private final String procedureDescription;

	/**
	 * Constructor.
	 * 
	 * @param description Description of the elements in the cache.
	 */
	ProcedureCache(String description) {
		this.procedureDescription = description;
	}

	/**
	 * Adds a custom defined procedure from the metric definition file. Checks
	 * if the procedure class can be loaded, has the expected type, and can be
	 * instantiated.
	 * 
	 * @param name Name of the procedure
	 * @param className Fully qualified class name of the procedure
	 * @throws SDMetricsException Procedure class is unsuitable.
	 */
	void addProcedureClass(String name, String className)
			throws SDMetricsException {

		Class<? extends T> procedureClass = null;
		try {
			procedureClass = loadClass(className);
		} catch (Exception ex) {
			throw new SDMetricsException(
					null,
					null,
					"Could not load class '"
							+ className
							+ "'. "
							+ "\n"
							+ ex.getClass().getSimpleName()
							+ ": "
							+ ex.getMessage()
							+ "\nMake sure the class is on the classpath, has public visibility, and extends the required base class.");
		}

		addProcedureClass(name, procedureClass);

		// We create the first instance immediately to find potential errors
		// as early as possible.
		T procedure = getProcedure(name);
		returnProcedure(procedure);
	}

	/**
	 * Adds a standard procedure that is available at compile time.
	 * 
	 * @param name Name of the procedure
	 * @param procClass Class of the procedure
	 */
	void addProcedureClass(String name, Class<? extends T> procClass) {
		ProcedureInfo procInfo = new ProcedureInfo();
		procInfo.procedureClass = procClass;
		buckets.put(name, procInfo);
	}

	/**
	 * Checks if this cache has a calculation procedure of a given name.
	 * 
	 * @param name Name of the procedure
	 * @return <code>true</code> if a procedure class has been registered for
	 *         this name
	 */
	boolean hasProcedure(String name) {
		return buckets.containsKey(name);
	}

	/**
	 * Obtains a procedure instance from the cache.
	 * <p>
	 * The instance should be returned to the cache after use.
	 * 
	 * @param procedureName Name of the procedure
	 * @return An instance the procedure's class
	 * @throws SDMetricsException The instance could not be created.
	 */
	T getProcedure(String procedureName) throws SDMetricsException {
		ProcedureInfo procInfo = buckets.get(procedureName);
		if (procInfo == null) {
			throw new SDMetricsException(null, null, "Unknown "
					+ procedureDescription + " '" + procedureName + "'.");
		}

		if (procInfo.availableInstances.isEmpty()) {
			try {
				// Create and return a new instance
				T result = procInfo.procedureClass.getConstructor(new Class<?>[0])
						.newInstance(new Object[0]);
				result.setName(procedureName);
				return result;
			} catch (Exception ex) {
				throw new SDMetricsException(
						null,
						null,
						"Could not instantiate class '"
								+ procInfo.procedureClass.getName()
								+ "'. "
								+ "\n"
								+ ex.getClass().getSimpleName()
								+ ": "
								+ ex.getMessage()
								+ "\nMake sure the class has a public constructor with empty argument list and throws no exceptions.");
			}
		}

		// return an existing instance from the cache
		return procInfo.availableInstances.removeLast();
	}

	/**
	 * Returns a procedure instance to the cache for reuse.
	 * 
	 * @param procedure The procedure instance.
	 */
	void returnProcedure(T procedure) {
		procedure.clear();
		ProcedureInfo procInfo = buckets.get(procedure.getName());
		procInfo.availableInstances.add(procedure);
	}

	/**
	 * Load the procedure class.
	 * 
	 * @param className Fully qualified class name of the procedure class.
	 * @return Class object for the specified class
	 * @throws ClassNotFoundException The class could not be loaded.
	 */
	protected abstract Class<? extends T> loadClass(String className)
			throws ClassNotFoundException;
}
