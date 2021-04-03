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
import com.sdmetrics.model.ModelElement;

/**
 * Calculates a "connectedcomponents" metric procedure.
 */
public class MetricProcedureConnectedComponents extends MetricProcedure {

	/**
	 * {@inheritDoc}
	 * @return Number of connected components as defined by the metric.
	 */
	@Override
	public Integer calculate(ModelElement element, Metric metric)
			throws SDMetricsException {
		return Integer.valueOf(connectedComponents(element, metric)
				.getConnectedComponentCount());
	}

	/*
	 * Performs the actual calculation of the metric.
	 */
	private StronglyConnectedComponents<ModelElement> connectedComponents(
			ModelElement element, Metric metric) throws SDMetricsException {

		ProcedureAttributes attributes = metric.getAttributes();
		Variables vars = new Variables(element);

		int minNodes = 1; // minimum size of connected components to report
		ExpressionNode minnExpr = attributes.getExpression("minnodes");
		if (minnExpr != null) {
			minNodes = ((Number) evalExpression(element, minnExpr, vars))
					.intValue();
		}

		Collection<ModelElement> nodeSet = evalElementSetExpression(element,
				attributes.getRequiredExpression("set"), vars);
		ExpressionNode neighborExpr = attributes.getRequiredExpression("nodes");
		StronglyConnectedComponents.Graph<ModelElement> graph = new ElementGraph(
				getMetricsEngine(), nodeSet, neighborExpr, vars);

		boolean undirected = attributes.getBooleanValue("undirected", false);
		if (undirected) {
			graph = new ChangeDirectionsAdapter<>(graph, true);
		}

		StronglyConnectedComponents<ModelElement> scc = new StronglyConnectedComponents<>();
		scc.calculateConnectedComponents(graph, minNodes, false);
		return scc;
	}

	/**
	 * Calculates the connected components for a model element.
	 * 
	 * @param engine The metrics engine to calculate the connected components
	 * @param element Model element for which to calculate the connected
	 *        components
	 * @param metric Metric that defines the connected components
	 * @return The connected components for the specified model element and
	 *         metric.
	 * @throws SDMetricsException Problem during the calculation
	 */
	public StronglyConnectedComponents<ModelElement> getConnectedComponents(
			MetricsEngine engine, ModelElement element, Metric metric)
			throws SDMetricsException {
		setMetricsEngine(engine);
		return connectedComponents(element, metric);
	}
}
