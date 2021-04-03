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

import com.sdmetrics.model.ModelElement;

/**
 * Checks a "cycle" rule.
 * <p>
 * Implementation note on cache usage: this procedure uses the cache of the rule
 * engine to store the strongly connected components of the graph considered by
 * the rule. The optimal time to clear the cache is when you are done checking
 * this rule for all model elements.
 */
public class RuleProcedureCycle extends RuleProcedure {

	private static final String CC_CACHE_KEY = "RuleProcedureCycle.connectedComponents";

	@Override
	public void checkRule(ModelElement element, Rule rule)
			throws SDMetricsException {
		// check if connected components have already been calculated for this
		// rule
		HashMap<Rule, StronglyConnectedComponents<ModelElement>> ccCache = getConnectedComponentCache();
		StronglyConnectedComponents<ModelElement> scc = ccCache.get(rule);
		if (scc == null) {
			// No, so calculate and cache them
			scc = new StronglyConnectedComponents<>();
			ProcedureAttributes attrs = rule.getAttributes();
			int minCount = getMinExpressionValue(element, attrs, "minnodes",
					null);

			ElementGraph graph = new ElementGraph(getMetricsEngine(),
					getModel().getAcceptedElements(element.getType()),
					attrs.getRequiredExpression("nodes"), null);

			scc.calculateConnectedComponents(graph, minCount, true);
			ccCache.put(rule, scc);
		}

		// report if the model element is part of a cycle
		int ccIndex = scc.getIndexFor(element);
		if (ccIndex >= 0) {
			int ccSize = scc.getConnectedComponent(ccIndex).size();
			StringBuilder label = new StringBuilder("cyc# ");
			label.append(ccIndex + 1);
			if (ccSize > 1) {
				label.append(" (");
				label.append(ccSize);
				label.append(" nodes)");
			} else {
				label.append(" (1 node)");
			}
			reportViolation(element, rule, label.toString());
		}
	}

	/*
	 * Each cycle rule in the metric definition file calculates the connected
	 * components for one graph, and uses the same graph for all model elements
	 * the rule checks. Therefore, the SCC of the graph are calculated once per
	 * rule, and then stored in the rule engine's cache for the checking of
	 * subsequent elements.
	 */
	private HashMap<Rule, StronglyConnectedComponents<ModelElement>> getConnectedComponentCache() {
		@SuppressWarnings("unchecked")
		HashMap<Rule, StronglyConnectedComponents<ModelElement>> result = 
			(HashMap<Rule, StronglyConnectedComponents<ModelElement>>) getValuesCache().get(CC_CACHE_KEY);
		if (result == null) {
			result = new HashMap<>();
			getValuesCache().put(CC_CACHE_KEY, result);
		}
		return result;
	}
}
