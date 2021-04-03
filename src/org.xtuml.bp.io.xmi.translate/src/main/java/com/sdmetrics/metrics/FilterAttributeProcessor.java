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
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.ModelElement;

/**
 * Processes the standard filter attributes for individual model elements or
 * entire sets. The filter attributes are the attributes "target",
 * "targetcondition", "element", "eltype", "condition", and "scope".
 */
public class FilterAttributeProcessor {
	// The names of the filter attributes
	private static final String ATTR_TARGET = "target";
	private static final String ATTR_TARGETCOND = "targetcondition";
	private static final String ATTR_ELEMENT = "element";
	private static final String ATTR_ELTYPE = "eltype";
	private static final String ATTR_CONDEXP = "condition";
	private static final String ATTR_SCOPE = "scope";

	// The admissible values for the 'scope' attribute
	private static final String IDEM = "idem";
	private static final String NOTIDEM = "notidem";
	private static final String CONTAINEDIN = "containedin";
	private static final String NOTCONTAINEDIN = "notcontainedin";
	private static final String SAME = "same";
	private static final String OTHER = "other";
	private static final String HIGHER = "higher";
	private static final String LOWER = "lower";
	private static final String SAMEORHIGHER = "sameorhigher";
	private static final String SAMEORLOWER = "sameorlower";
	private static final String NOTHIGHER = "nothigher";
	private static final String NOTLOWER = "notlower";
	private static final String SAMEBRANCH = "samebranch";
	private static final String NOTSAMEBRANCH = "notsamebranch";

	/** The metrics engine for all calculations. */
	private final MetricsEngine engine;
	/** Expression of the "target" attribute. */
	private final ExpressionNode targetExpr;
	/** Expression of the "targetCondition". */
	private final ExpressionNode targetConditionExpr;
	/** Expression of the "element" attribute. */
	private final ExpressionNode elementExpr;
	/** Expression of the "eltype" attribute. */
	private final ExpressionNode eltypeExprt;
	/** Expression of the "condition" attribute. */
	private final ExpressionNode conditionExpr;
	/** Value of the "scope" attribute. */
	private final String scope;
	/**
	 * The validity of the most recent element to which the filter attributes
	 * were applied.
	 */
	private boolean valid;

	/**
	 * Constructor.
	 * @param engine Metric engine to evaluate the filter expressions
	 * @param attributes metric or set calculation procedure definition with the
	 *        filter attributes to apply.
	 * @throws SDMetricsException The "scope" attribute is set but does not
	 *         contain a string.
	 */
	public FilterAttributeProcessor(MetricsEngine engine,
			ProcedureAttributes attributes) throws SDMetricsException {
		this.engine = engine;
		targetExpr = attributes.getExpression(ATTR_TARGET);
		targetConditionExpr = attributes.getExpression(ATTR_TARGETCOND);
		elementExpr = attributes.getExpression(ATTR_ELEMENT);
		eltypeExprt = attributes.getExpression(ATTR_ELTYPE);
		conditionExpr = attributes.getExpression(ATTR_CONDEXP);
		scope = attributes.getStringValue(ATTR_SCOPE);
	}

	/**
	 * Applies the filter attributes to a candidate element.
	 * <p>
	 * Returns the element produced by the "element" filter attribute, or
	 * <code>null</code> if the "element" filter attribute yields no model
	 * element. If the "element" filter attribute is not set, the candidate
	 * element itself is returned.
	 * <p>
	 * In either case, after the call to this method, method {@link #isValid()}
	 * indicates if the returned element satisfies the conditions of the other
	 * filter attributes.
	 * 
	 * @param principal The model element for which the metric or set is
	 *        calculated.
	 * @param candidate The candidate model element.
	 * @param vars Variables for the evaluation of the condition expressions
	 * @return The result from applying the "element" filter attribute, if
	 *         specified, otherwise the candidate model element.
	 * @throws SDMetricsException An error occurred evaluating one of the filter
	 *         attributes.
	 */
	public ModelElement applyFilters(ModelElement principal,
			ModelElement candidate, Variables vars) throws SDMetricsException {
		// process "target" attribute
		valid = checkType(candidate.getType(), targetExpr);

		// check "targetCondition"
		if (valid && targetConditionExpr != null) {
			valid = engine.evalBooleanExpression(candidate,
					targetConditionExpr, vars);
		}

		ModelElement result = candidate;
		// process "element" and "eltype" attributes
		if (valid && elementExpr != null) {
			result = engine.evalModelElementExpression(candidate, elementExpr,
					vars);
			if (result == null) {
				return null;
			}

			valid = checkType(result.getType(), eltypeExprt);
		}

		if (valid && conditionExpr != null) {
			valid = engine.evalBooleanExpression(result, conditionExpr, vars);
		}

		if (valid && scope != null) {
			valid = checkScope(principal, result);
		}

		return result;
	}

	/**
	 * Tests if the resulting model element from the most recent application of
	 * filter attributes fulfills all filter conditions.
	 * <p>
	 * Filters are applied by calling method {@link #applyFilters} or methods
	 * hasNext() and next() on the iterators produced by methods
	 * {@link #fullIteration} or {@link #validIteration(Collection, Variables)}.
	 * 
	 * @return <code>true</code> if the model element returned by the most
	 *         recent filter application fulfills all filter attribute
	 *         conditions.
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Evaluates element filter attributes "target" and "eltype".
	 * <p>
	 * These filter attributes contain an expression of the form
	 * [+]type_1[|[+]type_2|...|[+]type_n] that lists the name of all admissible
	 * element types. For element types prefixed by a "+", the type itself or
	 * any of its subtypes are admissible.
	 * 
	 * @param type metamodel element type to check
	 * @param typeTree Operator tree of the "target" or "eltype" expression.
	 * @return <code>true</code> if the typeTree is <code>null</code>
	 *         ("empty tree", admits all model element types), or the tree
	 *         contains the specified element type. <code>false</code> if the
	 *         typeTree is not empty and does not contain the specified type.
	 * @throws SDMetricsException typeTree contains an unknown model element
	 *         type
	 */
	private boolean checkType(MetaModelElement type, ExpressionNode typeTree)
			throws SDMetricsException {
		if (typeTree == null) {
			return true;
		}

		boolean isPlusOperator = typeTree.isOperation()
				&& "+".equals(typeTree.getValue())
				&& typeTree.getOperandCount() == 1;

		if (typeTree.isOperation() && !isPlusOperator) {
			if (checkType(type, typeTree.getLeftNode())) {
				return true;
			}
			if (typeTree.getRightNode() != null) {
				return checkType(type, typeTree.getRightNode());
			}
			return false;
		}

		ExpressionNode typeNameNode = typeTree;
		if (isPlusOperator) {
			typeNameNode = typeTree.getLeftNode();
		}

		MetaModelElement candidate = engine.getMetaModel().getType(
				typeNameNode.getValue());
		if (candidate == null) {
			throw new SDMetricsException(null, null,
					"Unknown model element type '" + typeNameNode.getValue()
							+ "'.");
		}

		if (isPlusOperator) {
			return type.specializes(candidate);
		}
		return type == candidate;
	}

	/**
	 * Evaluates filter attribute "scope".
	 * 
	 * @param principal The model element for which the set or metric is
	 *        calculated.
	 * @param candidate The model element whose scope to test.
	 * @return <code>true</code> if the scope condition is satisfied
	 * @throws SDMetricsException scope attribute value is invalid
	 */
	private boolean checkScope(ModelElement principal, ModelElement canidate)
			throws SDMetricsException {
		if (scope.equals(IDEM)) {
			return (principal == canidate);
		}
		if (scope.equals(NOTIDEM)) {
			return !(principal == canidate);
		}
		if (scope.equals(CONTAINEDIN)) {
			return contains(principal, canidate);
		}
		if (scope.equals(NOTCONTAINEDIN)) {
			return !contains(principal, canidate);
		}

		ModelElement parent1 = principal.getOwner();
		ModelElement parent2 = canidate.getOwner();

		boolean same = (parent1 == parent2);
		if (scope.equals(SAME)) {
			return same;
		}
		if (scope.equals(OTHER)) {
			return !same;
		}

		boolean lower = contains(parent1, parent2);
		if (scope.equals(LOWER)) {
			return lower;
		}
		if (scope.equals(NOTLOWER)) {
			return !lower;
		}
		if (scope.equals(SAMEORLOWER)) {
			return (same || lower);
		}

		boolean higher = contains(parent2, parent1);
		if (scope.equals(HIGHER)) {
			return higher;
		}
		if (scope.equals(NOTHIGHER)) {
			return !higher;
		}
		if (scope.equals(SAMEORHIGHER)) {
			return (same || higher);
		}

		boolean sameBranch = (same || lower || higher);
		if (scope.equals(SAMEBRANCH)) {
			return sameBranch;
		}
		if (scope.equals(NOTSAMEBRANCH)) {
			return !sameBranch;
		}

		throw new SDMetricsException(null, null, "Illegal scope criterion '"
				+ scope + "'.");
	}

	/**
	 * Checks if a model element directly or indirectly owns another model
	 * element.
	 * 
	 * @param containing The containing model element.
	 * @param contained The candidate contained model element.
	 * @return <code>true</code> if "containing" element directly or indirectly
	 *         owns the "contained" element.
	 */
	private boolean contains(ModelElement containing, ModelElement contained) {
		if (contained == null) {
			return false;
		}
		ModelElement context = contained.getOwner();
		while (context != null) {
			if (context == containing) {
				return true;
			}
			context = context.getOwner();
		}
		return false;
	}

	/**
	 * Applies element filters and filter attributes to an element set and
	 * returns an iteration over the resulting elements.
	 * <ul>
	 * <li>Elements in the input set that should be ignored according to element
	 * filter settings are immediately dismissed.
	 * <li>To each remaining element, the filter attributes are applied (see
	 * {@link #applyFilters}.
	 * <li>The resulting elements are returned as values of the iteration.
	 * <li>Method {@link #isValid()} indicates if the most recently returned
	 * element of the iteration fulfills the filter attribute conditions.
	 * </ul>
	 * 
	 * @param set Element set, typically the result of a "relation" or "relset"
	 *        attribute in a projection-like metric.
	 * @param vars Variables for the evaluation of expressions
	 * @return Iteration over the resulting elements
	 * @throws SDMetricsException Error evaluating the filter attributes
	 */
	public Iterable<ModelElement> fullIteration(
			final Collection<ModelElement> set, final Variables vars)
			throws SDMetricsException {

		return new Iterable<ModelElement>() {
			@Override
			public Iterator<ModelElement> iterator() {
				FilteringIterator result = new FilteringIterator(set, vars);
				result.returnValidsOnly = false;
				return result;
			}
		};
	}

	/**
	 * Applies element filters and filter attributes to an element set and
	 * returns an iteration over the valid elements.
	 * <ul>
	 * <li>Elements in the input set that should be ignored according to element
	 * filter settings are immediately dismissed.
	 * <li>To each remaining element, the filter attributes are applied (see
	 * {@link #applyFilters}.
	 * <li>If the resulting element also fulfills the filter attribute
	 * conditions, it is returned as values of the iteration. Otherwise, the
	 * element is dismissed.
	 * </ul>
	 * 
	 * @param set Element set, typically the result of a "relation" or "relset"
	 *        attribute in a projection-like metric.
	 * @param vars Variables for the evaluation of expressions
	 * @return Iteration over the resulting elements
	 * @throws SDMetricsException Error evaluating the filter attributes
	 */
	public Iterable<ModelElement> validIteration(
			final Collection<ModelElement> set, final Variables vars)
			throws SDMetricsException {

		return new Iterable<ModelElement>() {
			@Override
			public Iterator<ModelElement> iterator() {
				FilteringIterator result = new FilteringIterator(set, vars);
				result.returnValidsOnly = true;
				return result;
			}
		};
	}

	private class FilteringIterator implements Iterator<ModelElement> {
		private final Iterator<ModelElement> it;
		private final Variables variables;
		private final ModelElement principal;
		
		private boolean hasNext = false;
		private ModelElement next = null;
		private boolean nextKnown = false;
		boolean returnValidsOnly = false;

		/**
		 * Creates a new filtering iterator.
		 * 
		 * @param set Element set to iterate.
		 * @param vars Variables for the evaluation of filter expressions
		 */
		FilteringIterator(Collection<ModelElement> set, Variables vars) {
			this.variables = vars;
			principal = vars.getPrincipal();
			it = set.iterator();
		}

		/**
		 * Tests if the iteration has more elements.
		 * 
		 * @return <tt>true</tt> if the iterator has still more elements.
		 */
		@Override
		public boolean hasNext() {
			if (!nextKnown) {
				findNext();
			}
			return hasNext;
		}

		/**
		 * Returns the next element in the iteration.
		 * 
		 * @return Next element in the iteration.
		 */
		@Override
		public ModelElement next() {
			if (!nextKnown) {
				findNext();
			}

			if (!hasNext) {
				throw new NoSuchElementException();
			}

			nextKnown = false;
			return next;
		}

		private void findNext() throws SDMetricsException {
			nextKnown = true;
			while (it.hasNext()) {
				ModelElement candidate = it.next();
				// Dismiss the element if links to the element should be ignored
				// as per the element filter settings
				if (candidate.getLinksIgnored()) {
					continue;
				}

				// Apply the filter attributes and dismiss elements that do not
				// return a result
				candidate = applyFilters(principal, candidate, variables);
				if (candidate == null) {
					continue;
				}

				// Optionally dismiss elements that do not fulfill the filter
				// attribute conditions
				if (returnValidsOnly && !isValid()) {
					continue;
				}

				hasNext = true;
				next = candidate;
				return;
			}

			// end of the iteration has been reached
			hasNext = false;
			next = null;
		}

		@Override
		public void remove() {
			it.remove();
		}
	}
}
