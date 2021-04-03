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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.ModelElement;

/**
 * Base class for all rule procedures.
 */
public abstract class RuleProcedure extends AbstractProcedure {

	/** Rule engine of the procedure. */
	private RuleEngine ruleEngine;

	private List<RuleViolation> violations;

	/**
	 * Checks the rule for a model element.
	 * 
	 * @param element The element to check.
	 * @param rule The rule to check.
	 * @throws SDMetricsException Problem while checking the rule
	 */
	public abstract void checkRule(ModelElement element, Rule rule)
			throws SDMetricsException;

	/**
	 * Sets the rule engine for the procedure.
	 * 
	 * @param engine The rule engine.
	 */
	void setRuleEngine(RuleEngine engine) {
		setMetricsEngine(engine.getMetricsEngine());
		this.ruleEngine = engine;
	}

	/**
	 * Gets the list of rule violations reported by the procedure.
	 * 
	 * @return List of detected rule violations
	 */
	List<RuleViolation> getViolations() {
		return violations;
	}

	@Override
	void clear() {
		super.clear();
		ruleEngine = null;
		violations = null;
	}

	/**
	 * Gets the rule engine for this rule procedure.
	 * 
	 * @return The rule engine of the procedure.
	 */
	protected RuleEngine getRuleEngine() {
		return ruleEngine;
	}

	/**
	 * Reports a rule violation.
	 * 
	 * @param element The violating element.
	 * @param rule The violated rule.
	 * @param value The value of the rule violation.
	 */
	protected void reportViolation(ModelElement element, Rule rule, Object value) {
		if (violations == null) {
			violations = new LinkedList<>();
		}
		violations.add(new RuleViolation(element, rule, value));
	}

	/**
	 * Gets the cache to store data for reuse for subsequent checks of other
	 * model elements.
	 * 
	 * @return value cache for rule procedures
	 */
	protected Map<Object, Object> getValuesCache() {
		return ruleEngine.getValuesCache();
	}

	/**
	 * Evaluates the "value" expression of a rule.
	 * 
	 * @param element Model element for which the rule is evaluated
	 * @param attributes Attributes of the calculation procedure definition of
	 *        the rule.
	 * @param vars Variables for the evaluation of the expression
	 * @return Result of the "value" expression of the rule, or
	 *         <code>null</code> if none was specified.
	 * @throws SDMetricsException An error occurred evaluating the "value"
	 *         expression.
	 */
	protected Object getRuleValue(ModelElement element,
			ProcedureAttributes attributes, Variables vars)
			throws SDMetricsException {
		ExpressionNode value = attributes.getExpression("value");
		return (value == null) ? null : evalExpression(element, value, vars);
	}

	/**
	 * Evaluates the "minnodes" or "mincnt" expression of a rule.
	 * 
	 * @param element Model element for which the rule is evaluated
	 * @param attributes Attributes of the calculation procedure definition of
	 *        the rule.
	 * @param attrName Name of the attribute ("minnodes" or "mincnt").
	 * @param vars Variables for the evaluation of the expression
	 * @return Value of the "min" expression, or 1 if none was specified.
	 * @throws SDMetricsException An error occurred evaluating the expression.
	 */
	protected int getMinExpressionValue(ModelElement element,
			ProcedureAttributes attributes, String attrName, Variables vars)
			throws SDMetricsException {
		ExpressionNode node = attributes.getExpression(attrName);
		return (node == null) ? 1 : ((Number) evalExpression(element, node,
				vars)).intValue();
	}

}
