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
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.math.ExpressionParser;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.ModelElement;

/**
 * Checks the design rules for a model and reports violations.
 */

public class RuleEngine {
	/**
	 * Prefix in the rule exemption information to indicate exemption of a
	 * certain design rule.
	 */
	private static final String EXEMPTIONPREFIX = "violates_";

	/** Metrics engine to use for rule checking. */
	private final MetricsEngine engine;
	/** Cache to keep the rule calculation procedures for reuse. */
	private final RuleProcedureCache ruleProcedures;

	/** Element type that holds the rule exemption information. */
	private final MetaModelElement exemptionType;
	/**
	 * Expression that produces the rule exemption information for the exemption
	 * type element.
	 */
	private final ExpressionNode exemptionExpression;

	/**
	 * Cache for rule procedures to store values that are expensive to
	 * calculate.
	 */
	private final HashMap<Object, Object> valueCache = new HashMap<>();

	/**
	 * Creates a new rule engine.
	 * 
	 * @param me Metrics engine to use for rule checking.
	 */
	public RuleEngine(MetricsEngine me) {
		this.engine = me;
		MetricStore store = me.getMetricStore();
		ruleProcedures = store.getRuleProcedures();
		exemptionType = store.getRuleExemptionType();
		exemptionExpression = new ExpressionNode(store.getRuleExemptionTag());
	}

	/**
	 * Returns the metrics engine that this rule engine uses.
	 * 
	 * @return Metrics Engine of this rule engine.
	 */
	public MetricsEngine getMetricsEngine() {
		return engine;
	}

	/**
	 * Gets the names of the rules that a model element is allowed to violate.
	 * Searches through the tagged values or comments of the model element,
	 * looking for occurrences of the exemption prefix "_violates". Extracts the
	 * identifiers following each such occurrence. These define the names of the
	 * rules the element is allowed to violate. Returns the set of these names.
	 * 
	 * @param element Element to retrieve rule exemptions for.
	 * @return The set of the names of the rules the model element is allowed to
	 *         violate.
	 * @throws SDMetricsException The tagged values or comments could not be
	 *         accessed.
	 */
	public Collection<String> collectExemptedRules(ModelElement element)
			throws SDMetricsException {
		Collection<ModelElement> ownedElements = element.getOwnedElements();
		if (exemptionType == null || ownedElements == null) {
			return Collections.emptySet();
		}

		HashSet<String> exemptRuleNames = new HashSet<>(2);
		// find all tagged values or comments
		for (ModelElement child : ownedElements) {
			if (child.getType() != exemptionType) {
				continue;
			}

			// Get the tag value or comment body string
			String tag = String.valueOf(engine.evalExpression(child,
					exemptionExpression, null));

			// find all occurrences of "violates_" in the tag value
			int index = tag.indexOf(EXEMPTIONPREFIX);
			while (index >= 0) {
				// extract the rule name following the prefix
				index += EXEMPTIONPREFIX.length();
				int ruleNameEndIndex = index;
				while (ruleNameEndIndex < tag.length()
						&& ExpressionParser.isIdentifierCharacter(tag
								.charAt(ruleNameEndIndex))) {
					ruleNameEndIndex++;
				}
				// add rule name to the set of exempted rules
				exemptRuleNames.add(tag.substring(index, ruleNameEndIndex));
				index = tag.indexOf(EXEMPTIONPREFIX, ruleNameEndIndex);
			}
		}
		return exemptRuleNames;
	}

	/**
	 * Checks a design rule for a model element.
	 * 
	 * @param element The model element to check.
	 * @param rule The rule to check.
	 * @return The list of detected rule violations.
	 * @throws SDMetricsException An error occurred checking the design rule.
	 */
	public List<RuleViolation> checkRule(ModelElement element, Rule rule)
			throws SDMetricsException {
		try {
			// Obtain the procedure to check the rule
			String procedureName = rule.getProcedureName();
			RuleProcedure procedure = ruleProcedures
					.getProcedure(procedureName);

			// Perform the check, return the procedure for reuse
			procedure.setRuleEngine(this);
			procedure.checkRule(element, rule);
			List<RuleViolation> result = procedure.getViolations();
			ruleProcedures.returnProcedure(procedure);
			if (result == null) {
				return Collections.emptyList();
			}
			return result;
		} catch (SDMetricsException ex) {
			ex.fillInPerpetrators(element, rule);
			throw ex;
		} catch (RuntimeException ex) {
			// wrap exceptions in an SDMetricsException so we know
			// what rule/element is to blame
			throw new SDMetricsException(element, rule, ex);
		}
	}

	/**
	 * Gets the value cache for rule procedures to store values that are
	 * expensive to calculate.
	 * <p>
	 * If there is expensive data to calculate that can be reused across model
	 * elements, rule procedures may store them here.
	 * 
	 * @return value cache for rule procedures
	 */
	Map<Object, Object> getValuesCache() {
		return valueCache;
	}

	/**
	 * Clears the value cache for rule procedures.
	 * <p>
	 * The cache can be cleared any time. When you perform a comprehensive rule
	 * check (checking all rules for all model elements), a good strategy is to
	 * check all elements of one type, and clear the cache before proceeding
	 * with the next type.
	 */
	public void clearValuesCache() {
		valueCache.clear();
	}
}
