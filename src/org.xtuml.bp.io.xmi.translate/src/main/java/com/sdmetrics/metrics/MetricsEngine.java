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
import java.util.Collections;
import java.util.HashMap;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;

/**
 * Calculates metrics and sets.
 * 
 * The metrics engine offers methods to retrieve a metric value (
 * {@link #getMetricValue getMetricValue()}) or set ({@link #getSet getSet()})
 * for a particular model element.
 * <p>
 * 
 * The engine uses a "lazy calculation" strategy: if a set or metric for a model
 * element is requested for the first time, the set or metric is calculated,
 * stored in a cache, and returned to the caller. On subsequent requests for the
 * same set or metric, the value is simply retrieved from the cache.
 */

public class MetricsEngine {
	/** The definitions of the metrics and sets. */
	private MetricStore metrics;
	/** The model with the elements for which to calculate metrics. */
	private Model model;
	/** Cache to store all measurement values. */
	private MetricValuesCache metricCache;
	/** Cache to keep the metric calculation procedures for reuse. */
	private MetricProcedureCache metricProcedures;
	/** Cache to keep the set calculation procedures for reuse. */
	private SetProcedureCache setProcedures;

	/**
	 * Initializes a new metrics engine.
	 * 
	 * @param metrics The definitions of the sets and metrics to calculate.
	 * @param model The model on which to operate. Must use the same metamodel
	 *        as the metric definitions.
	 */
	public MetricsEngine(MetricStore metrics, Model model) {
		if (metrics.getMetaModel() != model.getMetaModel()) {
			throw new IllegalArgumentException(
					"Metamodel of metrics and elements do no match.");
		}

		this.metrics = metrics;
		this.model = model;
		this.metricProcedures = metrics.getMetricProcedures();
		this.setProcedures = metrics.getSetProcedures();
		this.metricCache = new MetricValuesCache();
	}

	/**
	 * Retrieves the metamodel on which this metrics engine is based.
	 * 
	 * @return Metamodel of this engine.
	 */
	public MetaModel getMetaModel() {
		return metrics.getMetaModel();
	}

	/**
	 * Retrieves the metric definitions used by this metrics engine.
	 * 
	 * @return Metric definitions of this engine.
	 */
	public MetricStore getMetricStore() {
		return metrics;
	}

	/**
	 * Retrieves the model on which this metrics engine operates.
	 * 
	 * @return Model of this engine.
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Retrieves the value of a metric for a model element.
	 * <p>
	 * Returns the cached value if the metric has been calculated before for the
	 * element. Otherwise, the value is calculated and cached.
	 * 
	 * @param element Model element to retrieve the metric value for.
	 * @param metric The metric to retrieve. Must be taken from the metric store
	 *        of this engine.
	 * @return Value of the metric for the specified element.
	 * @throws SDMetricsException An error occurred during the metric
	 *         calculation.
	 */
	public Object getMetricValue(ModelElement element, Metric metric)
			throws SDMetricsException {
		// Check if the metric value has already been calculated.
		Object value = metricCache.getMetricValue(element, metric);
		if (value != null) {
			return value;
		}

		// preliminarily set the metric value to 0 in the cache, to preempt
		// any infinite recursion.
		metricCache.setMetricValue(element, metric, MetricTools.ZERO);

		// calculate the metric value.
		try {
			// Obtain the procedure to calculate the metrics
			String procedureName = metric.getProcedureName();
			MetricProcedure procedure = metricProcedures
					.getProcedure(procedureName);
			procedure.setMetricsEngine(this);

			// Perform the calculation and return the procedure for reuse
			value = procedure.calculate(element, metric);
			metricProcedures.returnProcedure(procedure);
		} catch (SDMetricsException ex) {
			ex.fillInPerpetrators(element, metric);
			throw ex;
		} catch (RuntimeException ex) {
			// wrap exceptions in an SDMetricsException so we know
			// what metric/element is to blame
			throw new SDMetricsException(element, metric, ex);
		}

		// Cache and return the final metric value.
		metricCache.setMetricValue(element, metric, value);
		return value;
	}

	/**
	 * Retrieves the contents of a set for a model element.
	 * <p>
	 * Returns the cached set if the set has been calculated before for the
	 * element. Otherwise, the set is calculated and cached.
	 * <p>
	 * Sets typically contain either model elements, numbers (mix of instances
	 * of Integer and Float), or strings.
	 * 
	 * @param element Model element to retrieve the set for.
	 * @param set The set to retrieve. Must be taken from the metric store of
	 *        this engine.
	 * @return Contents of the set for the specified element.
	 * @throws SDMetricsException An error occurred during the metric
	 *         calculation.
	 */
	public Collection<?> getSet(ModelElement element, Set set)
			throws SDMetricsException {
		// Check if the set has already been calculated.
		Collection<?> result = metricCache.getSet(element, set);
		if (result != null) {
			return result;
		}

		// preliminarily set the contents to be the empty set to preempt
		// any infinite recursion
		metricCache.setSet(element, set, Collections.EMPTY_SET);

		try {
			result = computeSet(element, set);
		} catch (SDMetricsException ex) {
			ex.fillInPerpetrators(element, set);
			throw ex;
		} catch (RuntimeException ex) {
			// wrap exceptions in an SDMetricsException so we know
			// what set/element is to blame
			throw new SDMetricsException(element, set, ex);
		}
		metricCache.setSet(element, set, result);
		return result;
	}

	/**
	 * Calculates a set. Does not use the cache. The set definition need not be
	 * taken from the metric store of the engine.
	 * 
	 * @param element Model element to calculate the set for.
	 * @param set Metric object with the set definition to calculate.
	 * @return The contents of the resulting set.
	 * @throws SDMetricsException if an error occurred during calculation of the
	 *         set.
	 */
	Collection<?> computeSet(ModelElement element, Set set)
			throws SDMetricsException {
		// Obtain the procedure to calculate the set
		String procedureName = set.getProcedureName();
		SetProcedure procedure = setProcedures.getProcedure(procedureName);
		// Perform the calculation, return the procedure for reuse
		procedure.setMetricsEngine(this);
		Collection<?> result = procedure.calculate(element, set);
		setProcedures.returnProcedure(procedure);
		return result;
	}

	/**
	 * Evaluates a metric expression. Metric expressions return a scalar value
	 * (a number, string, or model element).
	 * 
	 * @param element The model element for which to calculate the metric
	 *        expression.
	 * @param node Root node of the metric expression operator tree.
	 * @param vars Variables for the expression evaluation
	 * @return The result of the metric expression.
	 * @throws SDMetricsException An error occurred evaluating the expression.
	 */
	Object evalExpression(ModelElement element, ExpressionNode node,
			Variables vars) throws SDMetricsException {
		// Check the node type (operation, identifier, or constant), and act
		// accordingly.
		if (node.isOperation()) {
			return evalOperationExpression(element, node, vars);
		}

		if (node.isNumberConstant()) {
			return Float.valueOf(node.getValue());
		}

		if (node.isStringConstant()) {
			return node.getValue();
		}

		if (node.isIdentifier()) {
			return evalIdentifier(element, node.getValue(), vars);
		}

		throw new SDMetricsException(element, null,
				"Unknown expression node type.");
	}

	/**
	 * Evaluate a metric expression that returns a model element.
	 * 
	 * @param element The model element for which to calculate the metric
	 *        expression.
	 * @param node Root node of the metric expression operator tree.
	 * @param vars Variables for the expression evaluation
	 * @return The resulting model element, or <code>null</code> if the
	 *         expression did not produce a model element.
	 * @throws SDMetricsException An error occurred evaluating the metric
	 *         expression.
	 */
	ModelElement evalModelElementExpression(ModelElement element,
			ExpressionNode node, Variables vars) throws SDMetricsException {
		Object obj = evalExpression(element, node, vars);
		if (!(obj instanceof ModelElement)) {
			return null;
		}
		return (ModelElement) obj;
	}

	/**
	 * Evaluates a condition expression. Condition expressions return a boolean
	 * value <code>true</code> or <code>false</code>.
	 * 
	 * @param element The model element for which to calculate the condition
	 *        expression.
	 * @param node Root node of the condition expression operator tree.
	 * @param vars Variables for the expression evaluation
	 * @return The result of the condition expression.
	 * @throws SDMetricsException An error occurred evaluating the expression.
	 */
	boolean evalBooleanExpression(ModelElement element, ExpressionNode node,
			Variables vars) throws SDMetricsException {
		String operator = node.getValue();
		ProcedureCache<BooleanOperation> scops = metrics
				.getExpressionOperations().getBooleanOperations();

		BooleanOperation op = scops.getProcedure(operator);
		op.setMetricsEngine(this);
		boolean result = op.calculateValue(element, node, vars);
		scops.returnProcedure(op);
		return result;
	}

	/**
	 * Evaluates a scalar operator in a metric expression. Scalar operators
	 * return numbers, strings, or model elements.
	 * 
	 * @param element The model element for which to calculate the operation.
	 * @param node Node with the operator.
	 * @param vars Variables for the expression evaluation
	 * @return The result of the operation.
	 * @throws SDMetricsException An error occurred evaluating the operation.
	 */
	private Object evalOperationExpression(ModelElement element,
			ExpressionNode node, Variables vars) throws SDMetricsException {

		String operator = node.getValue();
		ProcedureCache<ScalarOperation> scops = metrics
				.getExpressionOperations().getScalarOperations();

		ScalarOperation op = scops.getProcedure(operator);
		op.setMetricsEngine(this);
		Object result = op.calculateValue(element, node, vars);
		scops.returnProcedure(op);
		return result;
	}

	/**
	 * Evaluates an identifier in a metric expression.
	 * <p>
	 * The identifier may refer to a metric, an attribute, or a variable
	 * (including the implicit variable _self). The type of identifier is
	 * checked in that order.
	 * 
	 * @param element The model element for which to evaluate the identifier.
	 * @param identifier The identifier.
	 * @param vars Variables for the expression evaluation
	 * @return The value of the identifier.
	 * @throws SDMetricsException The identifier could not be resolved.
	 */
	private Object evalIdentifier(ModelElement element, String identifier,
			Variables vars) throws SDMetricsException {
		// check if identifier is a metric
		Metric metric = metrics.getMetric(element.getType(), identifier);
		if (metric != null) {
			return getMetricValue(element, metric);
		}

		// check if identifier is a single-valued attribute
		if (element.getType().hasAttribute(identifier)) {
			if (!element.getType().isSetAttribute(identifier)) {
				if (element.getType().isRefAttribute(identifier)) {
					ModelElement ref = element.getRefAttribute(identifier);
					if (ref == null) {
						return ""; // no element referenced -> empty string
					}
					return ref;
				}
				// plain data attribute, return its value
				return element.getPlainAttribute(identifier);
			}
		}

		// _self returns the current model element; also accept the
		// deprecated "self"
		if ("_self".equals(identifier) || "self".equals(identifier)) {
			return element;
		}

		// check variables in the value map
		if (vars != null) {
			if (vars.hasVariable(identifier)) {
				return vars.getVariable(identifier);
			}
		}

		throw new SDMetricsException(element, null,
				"No metric or single-valued attribute '" + identifier
						+ "' for elements of type '"
						+ element.getType().getName() + "'.");
	}

	/**
	 * Evaluates a set expression. Set expressions return sets.
	 * 
	 * @param element The model element for which to calculate the set
	 *        expression.
	 * @param node Root node of the set expression operator tree.
	 * @param vars Variables for the expression evaluation
	 * @return The contents of the resulting set.
	 * @throws SDMetricsException An error occurred evaluating the set
	 *         expression.
	 */
	Collection<?> evalSetExpression(ModelElement element, ExpressionNode node,
			Variables vars) throws SDMetricsException {

		// The root of a set expression must be an operator or identifier
		// accordingly.
		if (node.isIdentifier()) {
			return evalSetIdentifier(element, node.getValue(), vars);
		}

		// Make sure we have a binary operator
		if (!node.isOperation()) {
			throw new SDMetricsException(element, null,
					"Illegal set expression.");
		}

		// Process the operator
		String operator = node.getValue();
		ProcedureCache<SetOperation> scops = metrics.getExpressionOperations()
				.getSetOperations();
		SetOperation op = scops.getProcedure(operator);
		op.setMetricsEngine(this);
		Collection<?> result = op.calculateValue(element, node, vars);
		scops.returnProcedure(op);
		return result;
	}

	/**
	 * Evaluates an identifier in a set expression.
	 * 
	 * @param element The model element for which to evaluate the set
	 *        identifier.
	 * @param identifier The set identifier.
	 * @param vars Variables for the expression evaluation
	 * @return The the set specified by the identifier.
	 * @throws SDMetricsException The identifier could not be resolved.
	 */
	private Collection<?> evalSetIdentifier(ModelElement element,
			String identifier, Variables vars) throws SDMetricsException {
		// Check if there is a set of that name for the element
		Set set = metrics.getSet(element.getType(), identifier);
		if (set != null) {
			return getSet(element, set);
		}

		// Check multi-valued attributes of the element
		if (element.getType().hasAttribute(identifier)) {
			if (element.getType().isSetAttribute(identifier)) {
				return element.getSetAttribute(identifier);
			}
		}

		// Check the variables
		if (vars != null) {
			Object o = vars.getVariable(identifier);
			if (o != null) {
				if (o instanceof Collection) {
					return (Collection<?>) o;
				}

				throw new SDMetricsException(element, null, "Variable '"
						+ identifier + "' is not a set.");
			}
		}

		throw new SDMetricsException(element, null, "Unknown set identifier '"
				+ identifier + "' for elements of type '"
				+ element.getType().getName() + "'.");
	}

	/** Cache to store all measurement values. */
	class MetricValuesCache {
		/** Metric value cache. */
		private final HashMap<ModelElement, Object[]> metricValues = new HashMap<>();
		/** Set value cache. */
		private final HashMap<ModelElement, Collection<?>[]> setValues = new HashMap<>();

		/**
		 * Sets the value for a metric of an element.
		 * 
		 * @param element the model element
		 * @param metric the metric
		 * @param value the value of the metric
		 */
		void setMetricValue(ModelElement element, Metric metric, Object value) {
			Object[] values = metricValues.get(element);
			if (values == null) {
				values = new Object[metrics.getMetrics(element.getType())
						.size()];
				metricValues.put(element, values);
			}
			values[metric.getID()] = value;
		}

		/**
		 * Sets the value of a set for a model element.
		 * 
		 * @param element the model element
		 * @param set the set
		 * @param value the value of the metric
		 */
		void setSet(ModelElement element, Set set, Collection<?> value) {
			Collection<?>[] collections = setValues.get(element);
			if (collections == null) {
				collections = new Collection<?>[metrics.getSets(element.getType())
						.size()];
				setValues.put(element, collections);
			}
			collections[set.getID()] = value;
		}

		/**
		 * Retrieves the value of a metric for a model element.
		 * 
		 * @param element the model element
		 * @param metric the metric
		 * @return Value of the metric, <code>null</code> if the value has not
		 *         yet been cached
		 */
		Object getMetricValue(ModelElement element, Metric metric) {
			Object[] result = metricValues.get(element);
			if (result != null) {
				return result[metric.getID()];
			}
			return null;
		}

		/**
		 * Retrieves the value of a set for a model element.
		 * 
		 * @param element the model element
		 * @param set the set
		 * @return Value of the set, <code>null</code> if the value has not yet
		 *         been cached
		 */
		Collection<?> getSet(ModelElement element, Set set) {
			Collection<?>[] result = setValues.get(element);
			if (result != null) {
				return result[set.getID()];
			}
			return null;
		}
	}
}
