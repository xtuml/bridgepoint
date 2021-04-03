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
 * A graph consisting of model elements.
 * <p>
 * The edges of the graph are defined by a set expression, which yields, for a
 * model element <code>e</code>, the set of neighbor elements to which there is
 * an outgoing edge from <code>e</code>.
 */
public class ElementGraph implements
		StronglyConnectedComponents.Graph<ModelElement> {

	/** Metrics engine to calculate the neighbors of a model element. */
	private final MetricsEngine me;
	/**
	 * The model elements that constitute the nodes of the graph.
	 */
	private final Collection<ModelElement> nodes;
	/** Set expression that defines the neighbors of a mode element. */
	private final ExpressionNode neighborExpression;
	/** Variables for the calculation of the neighbor nodes. */
	private final Variables variables;

	/**
	 * Constructor.
	 * @param engine Metrics engine to calculate the neighbors of a model
	 *        element.
	 * @param nodes The model elements that constitute the nodes of the graph.
	 * @param neighbors Set expression that yields the set of neighbors of a
	 *        model element.
	 * @param vars Variables for the expression evaluation
	 */
	public ElementGraph(MetricsEngine engine, Collection<ModelElement> nodes,
			ExpressionNode neighbors, Variables vars) {
		this.me = engine;
		this.neighborExpression = neighbors;
		this.nodes = nodes;
		this.variables = vars;
	}

	@Override
	public Collection<ModelElement> getNodes() {
		return nodes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ModelElement> getNeighbors(ModelElement element)
			throws SDMetricsException {
		return (Collection<ModelElement>) me.evalSetExpression(element,
				neighborExpression, variables);
	}
}
