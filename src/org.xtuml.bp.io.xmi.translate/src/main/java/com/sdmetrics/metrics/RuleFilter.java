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

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.math.ExpressionParser;
import com.sdmetrics.model.MetaModelElement;

/**
 * Represents and evaluates a design rule filter expression.
 * <p>
 * A design rule can be assigned one or more "application areas", for example,
 * rules aimed at the analysis phase, the design phase, rules for real time
 * systems, etc. The rule filter selects rules based on their application areas.
 */
public class RuleFilter {
	/** Operator tree of the filter expression. */
	private ExpressionNode filterRoot;

	/** Constructs a new empty rule filter that accepts all application areas. */
	public RuleFilter() {
		filterRoot = null;
	}

	/**
	 * Construct a new rule filter from a filter string.
	 * 
	 * @param filter The rule filter string.
	 * @throws SDMetricsException The filter string could not be parsed or
	 *         contains illegal operations.
	 */
	public RuleFilter(String filter) throws SDMetricsException {
		if (filter != null && filter.length() > 0) {
			ExpressionParser p = new ExpressionParser();
			filterRoot = p.parseExpression(filter);
			if (filterRoot == null) {
				throw new SDMetricsException(null, null,
						"Invalid design rule filter: " + filter + "\n"
								+ p.getErrorInfo());
			}
			String problem = validateFilter(filterRoot);
			if (problem.length() > 0) {
				throw new SDMetricsException(null, null,
						"Invalid design rule filter: " + filter + "\n"
								+ problem);
			}
		}
	}

	/**
	 * Checks if the application area(s) of a design rule match this filter.
	 * 
	 * @param rule The design rule to check.
	 * @return <code>true</code> if the design rule matches and should be
	 *         checked according to this filter.
	 */
	public boolean match(Rule rule) {
		return evalAppAreaMatch(rule.getApplicableAreas(), filterRoot);
	}

	/* Recursively evaluate the rule's operator tree to determine the match. */
	private boolean evalAppAreaMatch(Collection<String> areas,
			ExpressionNode filter) {
		if (filter == null) {
			return true; // no filter => accept everything
		}

		if (filter.isIdentifier()) {
			if (areas == null) {
				return true; // no phases specified => all phases are accepted!
			}
			return areas.contains(filter.getValue());
		} else if (filter.isStringConstant()) {
			if (areas == null) {
				return false; // no phases specified => not accepted
			}
			return areas.contains(filter.getValue());
		}

		if ("!".equals(filter.getValue())) {
			return !evalAppAreaMatch(areas, filter.getLeftNode());
		}

		if ("&".equals(filter.getValue())) {
			return evalAppAreaMatch(areas, filter.getLeftNode())
					&& evalAppAreaMatch(areas, filter.getRightNode());
		}

		return evalAppAreaMatch(areas, filter.getLeftNode())
				|| evalAppAreaMatch(areas, filter.getRightNode());
	}

	/**
	 * Validates a rule filter expression.
	 * 
	 * @param filter Operator tree of the rule filter string.
	 * @return Empty string if everything is OK, else a description of the
	 *         problems found.
	 */
	private String validateFilter(ExpressionNode filter) {
		if (filter == null) {
			return "";
		}
		if (filter.isIdentifier() || filter.isStringConstant()) {
			return "";
		}
		if (filter.isOperation()) {
			if ("&".equals(filter.getValue()) || "|".equals(filter.getValue())) {
				return validateFilter(filter.getLeftNode())
						+ validateFilter(filter.getRightNode());
			} else if ("!".equals(filter.getValue())) {
				return validateFilter(filter.getLeftNode());
			} else {
				return "Operation '" + filter.getValue()
						+ "' not allowed in filter expressions.";
			}
		}
		return "Unexpected operand '" + filter.getValue() + "'.";
	}

	/**
	 * Checks the identifiers of the rule filter for plausibility.
	 * <p>
	 * An identifier is valid if it is explicitly defined as the application
	 * area of at least one rule. Otherwise, the identifier is suspect because
	 * it is not listed by any of the rules.
	 * 
	 * @param metricStore Contains the definitions of the rules
	 * @return Empty string if all application areas of the filter string are
	 *         valid, otherwise the name of a suspect application area in the
	 *         filter string.
	 */
	public String checkIdentifiers(MetricStore metricStore) {
		// collect explicitly defined application areas from all design rules
		HashSet<String> explicitAppAreas = new HashSet<>();
		for (MetaModelElement type : metricStore.getMetaModel()) {
			for (Rule rule : metricStore.getRules(type)) {
				if (rule.isEnabled() && rule.getApplicableAreas() != null) {
					explicitAppAreas.addAll(rule.getApplicableAreas());
				}
			}
		}

		return checkIdentifiers(explicitAppAreas, filterRoot);
	}

	/* Recursively check the identifiers in the operator tree. */
	private String checkIdentifiers(Collection<String> areas,
			ExpressionNode node) {
		if (node == null) {
			return "";
		}
		if (node.isIdentifier() || node.isStringConstant()) {
			String appArea = node.getValue();
			return areas.contains(appArea) ? "" : appArea;
		}
		String result = checkIdentifiers(areas, node.getLeftNode());
		if (result.length() == 0) {
			result = checkIdentifiers(areas, node.getRightNode());
		}
		return result;
	}
}
