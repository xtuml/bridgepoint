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

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;

/**
 * Base class for metric, set, and rule procedures as well as scalar, boolean,
 * and set operations.
 */
public class AbstractProcedure {

	/** The name of the procedure in the metric definition file. */
	private String name;

	/** The metrics engine for calculation of the procedure. */
	private MetricsEngine metricsEngine;

	// Methods for use by specializations

	/**
	 * Gets the metrics engine to use for calculations.
	 * 
	 * @return Metrics engine for this procedure
	 */
	protected MetricsEngine getMetricsEngine() {
		return metricsEngine;
	}

	/**
	 * Gets the element set specified by a "relation" or "relset" attribute.
	 * 
	 * @param element Element for which to retrieve the set.
	 * @param attributes Attributes of the procedure definition.
	 * @param vars Variables for the evaluation of expressions
	 * @return The set specified by the "relation" or "relset" attribute.
	 * @throws SDMetricsException Neither "relation" nor "relset" attribute was
	 *         specified.
	 */
	@SuppressWarnings("unchecked")
	protected Collection<ModelElement> getRelationOrSet(ModelElement element,
			ProcedureAttributes attributes, Variables vars)
			throws SDMetricsException {

		// Check "relation attribute"
		String relation = attributes.getStringValue("relation");
		if (relation != null) {
			return element.getRelations(relation);
		}

		// Check "relset" attribute
		ExpressionNode setExpr = attributes.getExpression("relset");
		if (setExpr == null) {
			throw new SDMetricsException(element, null,
					"Neither 'relation' nor 'relset' attribute specified.");
		}
		return (Collection<ModelElement>) metricsEngine.evalSetExpression(
				element, setExpr, vars);
	}

	/**
	 * Creates a new filter attribute processing object.
	 * 
	 * @param attributes Attributes of the procedure definition
	 * @return Filter attribute processor for this procedure
	 * @throws SDMetricsException One of the filter attributes contains illegal
	 *         values
	 */
	protected FilterAttributeProcessor getFilterAttributeProcessor(
			ProcedureAttributes attributes) throws SDMetricsException {
		return new FilterAttributeProcessor(getMetricsEngine(), attributes);
	}

	/**
	 * Gets the model for which this calculation takes place.
	 * 
	 * @return The model for this calculation.
	 */
	protected Model getModel() {
		return metricsEngine.getModel();
	}

	/**
	 * Gets the metamodel for which this calculation takes place.
	 * 
	 * @return The metamodel for this calculation
	 */
	protected MetaModel getMetaModel() {
		return metricsEngine.getMetaModel();
	}

	/**
	 * Calculates the value of a metric expression for a model element.
	 * 
	 * @param element The model element to evaluate the expression for.
	 * @param node Root node of the expression to evaluate
	 * @param vars Variables for the expression evaluation
	 * @return The resulting metric value of the expression
	 * @throws SDMetricsException a problem occurred during the evaluation
	 */
	protected Object evalExpression(ModelElement element, ExpressionNode node,
			Variables vars) throws SDMetricsException {
		return metricsEngine.evalExpression(element, node, vars);
	}

	/**
	 * Calculates the value of a boolean expression for a model element.
	 * 
	 * @param element The model element to evaluate the expression for.
	 * @param node Root node of the expression to evaluate
	 * @param vars Variables for the expression evaluation
	 * @return The resulting boolean value of the expression
	 * @throws SDMetricsException a problem occurred during the evaluation
	 */
	protected boolean evalBooleanExpression(ModelElement element,
			ExpressionNode node, Variables vars) throws SDMetricsException {
		return metricsEngine.evalBooleanExpression(element, node, vars);
	}

	/**
	 * Calculates a set expression for a model element.
	 * 
	 * @param element The model element to evaluate the expression for.
	 * @param node Root node of the expression to evaluate
	 * @param vars Variables for the expression evaluation
	 * @return the result set of the evaluation
	 * @throws SDMetricsException a problem occurred during the evaluation
	 */
	protected Collection<?> evalSetExpression(ModelElement element,
			ExpressionNode node, Variables vars) throws SDMetricsException {
		return metricsEngine.evalSetExpression(element, node, vars);
	}

	/**
	 * Calculates a set expression that returns a set of model elements.
	 * 
	 * @param element The model element to evaluate the expression for.
	 * @param node Root node of the expression to evaluate
	 * @param vars Variables for the expression evaluation
	 * @return the resulting element set
	 * @throws SDMetricsException a problem occurred during the evaluation
	 */
	@SuppressWarnings("unchecked")
	protected Collection<ModelElement> evalElementSetExpression(
			ModelElement element, ExpressionNode node, Variables vars)
			throws SDMetricsException {
		return (Collection<ModelElement>) metricsEngine.evalSetExpression(
				element, node, vars);
	}

	/**
	 * Checks if a metric or set can be applied to a related element. Used for
	 * recursive or nesting metrics/sets. If the metric or set is not
	 * inheritable, the related element must be of the same type as the element
	 * for which the metric is being calculated. If the metric or set is
	 * inheritable, the related element must be an ancestor or descendant for
	 * which the metric is also available.
	 * 
	 * @param element The element for which the metric/set/rule is calculated.
	 * @param metric The metric, set, or rule to be checked.
	 * @param candidate The related element to check.
	 * @return <code>true</code> if the metric/set/rule can be applied to the
	 *         candidate element, else <code>false</code>
	 * @since 2.3
	 */
	protected boolean isCompatible(ModelElement element, MetricEntry metric,
			ModelElement candidate) {
		MetaModelElement elType = element.getType();
		MetaModelElement candType = candidate.getType();
		if (elType == candType) {
			return true;
		}

		if (metric.isInheritable()) {
			return (candType.specializes(elType) || elType
					.specializes(candType))
					&& metricsEngine.getMetricStore().getMetric(candType,
							metric.getName()) != null;
		}
		return false;
	}

	// Package internal house keeping methods

	/**
	 * Sets the name of this procedure from the metric definition file.
	 * 
	 * @param name The name of the procedure.
	 */
	void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of this procedure.
	 * 
	 * @return name The name of the procedure.
	 * 
	 */
	String getName() {
		return name;
	}

	/**
	 * Sets the metrics engine for this procedure.
	 * 
	 * @param engine The metrics engine
	 */
	void setMetricsEngine(MetricsEngine engine) {
		this.metricsEngine = engine;
	}

	/**
	 * Clears the procedure for reuse.
	 * <p>
	 * Deletes the reference to the metrics engine.
	 */
	void clear() {
		metricsEngine = null;
	}
}
