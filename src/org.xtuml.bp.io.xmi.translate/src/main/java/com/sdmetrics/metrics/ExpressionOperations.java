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
import java.util.HashSet;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.math.ExpressionParser;
import com.sdmetrics.math.HashMultiSet;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.ModelElement;

/**
 * Holds the expression operations for metric, set, and condition expressions.
 */
class ExpressionOperations {

	private final ScalarOperationCache scalarOperations;
	private final BooleanOperationCache booleanOperations;
	private final SetOperationCache setOperations;

	private final HashSet<String> functionNames;
	private final HashSet<String> highPrecedenceRelationNames;
	private final HashSet<String> lowPrecedenceRelationNames;

	/**
	 * Constructor.
	 */
	ExpressionOperations() {
		functionNames = new HashSet<>();
		highPrecedenceRelationNames = new HashSet<>();
		lowPrecedenceRelationNames = new HashSet<>();

		scalarOperations = new ScalarOperationCache();
		booleanOperations = new BooleanOperationCache();
		setOperations = new SetOperationCache();
	}

	/**
	 * Cache for scalar operations.
	 */
	class ScalarOperationCache extends ProcedureCache<ScalarOperation> {

		/**
		 * Creates a new cache and registers the standard scalar operations.
		 */
		ScalarOperationCache() {
			super("operation");
			addProcedure(this, "size", SizeFunction.class, functionNames);
			addProcedure(this, "flatsize", FlatSizeFunction.class,
					functionNames);
			addProcedure(this, "length", LengthFunction.class, functionNames);
			addProcedure(this, "tolowercase", ToLowerFunction.class,
					functionNames);
			addProcedure(this, "typeof", TypeOfFunction.class, functionNames);
			addProcedure(this, "qualifiedname", QualifiedNameFunction.class,
					functionNames);
			addProcedureClass(".", DotOperator.class);
			addProcedure(this, "upto", UpToOperator.class,
					lowPrecedenceRelationNames);
			addProcedure(this, "topmost", TopMostOperator.class,
					lowPrecedenceRelationNames);
			addProcedureClass("->", InOperator.class);
			addProcedureClass("+", PlusOperator.class);
			addProcedureClass("-", MinusOperator.class);
			addProcedureClass("*", MultiplicationOperator.class);
			addProcedureClass("/", DivisionOperator.class);
			addProcedureClass("^", ExponentiationOperator.class);
			addProcedure(this, "ln", LogarithmFunction.class, functionNames);
			addProcedure(this, "exp", ExponentiationFunction.class,
					functionNames);
			addProcedure(this, "sqrt", SquareRootFunction.class, functionNames);
			addProcedure(this, "abs", AbsoluteFunction.class, functionNames);
			addProcedure(this, "floor", FloorFunction.class, functionNames);
			addProcedure(this, "ceil", CeilingFunction.class, functionNames);
			addProcedure(this, "round", RoundFunction.class, functionNames);
			addProcedure(this, "parsenumber", ParseNumberFunction.class,
					functionNames);
		}

		@Override
		protected Class<? extends ScalarOperation> loadClass(String className)
				throws ClassNotFoundException {
			return Class.forName(className).asSubclass(ScalarOperation.class);
		}
	}

	/**
	 * Adds an operation and registers its name to be recognized by the
	 * expression parser.
	 */
	private <T extends AbstractProcedure> void addProcedure(
			ProcedureCache<T> cache, String name, Class<? extends T> cls,
			HashSet<String> type) {
		cache.addProcedureClass(name, cls);
		type.add(name);
	}

	/**
	 * Cache for Boolean operations.
	 */
	class BooleanOperationCache extends ProcedureCache<BooleanOperation> {

		/**
		 * Creates a new cache and registers the standard Boolean operations.
		 */
		BooleanOperationCache() {
			super("boolean operation");
			addProcedureClass("&", AndOperator.class);
			addProcedureClass("|", OrOperator.class);
			addProcedureClass("!", NotOperator.class);
			addProcedure(this, "isunique", IsUniqueFunction.class,
					functionNames);
			addProcedure(this, "startswithcapital",
					StartsWithCapitalFunction.class, functionNames);
			addProcedure(this, "startswithlowercase",
					StartsWithLowerCaseFunction.class, functionNames);
			addProcedure(this, "islowercase", IsLowerCaseFunction.class,
					functionNames);
			addProcedure(this, "instanceof", InstanceOfFunction.class,
					functionNames);
			addProcedureClass("->", InOperatorBool.class);
			addProcedure(this, "onlist", OnListOperator.class,
					highPrecedenceRelationNames);
			addProcedureClass("=", EqualsOperator.class);
			addProcedureClass("!=", NotEqualsOperator.class);
			addProcedureClass(">", GreaterThanOperator.class);
			addProcedureClass(">=", GreaterOrEqualOperator.class);
			addProcedureClass("<", LessThanOperator.class);
			addProcedureClass("<=", LessOrEqualOperator.class);
			addProcedure(this, "startswith", StartsWithOperator.class,
					highPrecedenceRelationNames);
			addProcedure(this, "endswith", EndsWithOperator.class,
					highPrecedenceRelationNames);
		}

		@Override
		protected Class<? extends BooleanOperation> loadClass(String className)
				throws ClassNotFoundException {
			return Class.forName(className).asSubclass(BooleanOperation.class);
		}

	}

	/**
	 * Cache for Boolean operations.
	 */
	class SetOperationCache extends ProcedureCache<SetOperation> {

		/**
		 * Creates a new cache and registers the standard Boolean operations.
		 */
		SetOperationCache() {
			super("set operation");
			addProcedureClass(".", DotOperatorSet.class);
			addProcedureClass("+", UnionOperation.class);
			addProcedureClass("-", DifferenceOperation.class);
			addProcedureClass("*", IntersectionOperation.class);
		}

		@Override
		protected Class<? extends SetOperation> loadClass(String className)
				throws ClassNotFoundException {
			return Class.forName(className).asSubclass(SetOperation.class);
		}
	}

	/**
	 * Provides access to the scalar operations.
	 * 
	 * @return The scalar operations.
	 */
	ProcedureCache<ScalarOperation> getScalarOperations() {
		return scalarOperations;
	}

	/**
	 * Provides access to the Boolean operations.
	 * 
	 * @return The Boolean operations.
	 */
	ProcedureCache<BooleanOperation> getBooleanOperations() {
		return booleanOperations;
	}

	/**
	 * Provides access to the Boolean operations.
	 * 
	 * @return The Boolean operations.
	 */
	ProcedureCache<SetOperation> getSetOperations() {
		return setOperations;
	}

	/**
	 * Registers the name of a custom function to be recognized by the
	 * expression parser.
	 * 
	 * @param name The name of the custom function
	 */
	void addCustomFunctionName(String name) {
		functionNames.add(name);
	}

	/**
	 * Creates a new expression parser that recognizes all the operations
	 * registered with this object.
	 * 
	 * @return Expression parser
	 */
	ExpressionParser getExpressionParser() {
		return new ExpressionParser(functionNames, highPrecedenceRelationNames,
				lowPrecedenceRelationNames);
	}

	// Scalar Operations

	/**
	 * Calculates the number of elements in a set.
	 */
	public static class SizeFunction extends ScalarOperation {

		@Override
		public Integer calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Collection<?> c = evalSetExpression(element, node.getLeftNode(),
					vars);
			return Integer.valueOf(c.size());
		}
	}

	/**
	 * Calculates the number of elements in a set, ignoring the cardinality of
	 * elements for multisets.
	 */
	public static class FlatSizeFunction extends ScalarOperation {

		@Override
		public Integer calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Collection<?> c = evalSetExpression(element, node.getLeftNode(),
					vars);
			if (MetricTools.isMultiSet(c)) {
				return Integer.valueOf(((HashMultiSet<?>) c).flatSetSize());
			}
			return Integer.valueOf(c.size());
		}
	}

	/**
	 * Calculates the length of a string.
	 */
	public static class LengthFunction extends ScalarOperation {

		@Override
		public Integer calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			return Integer.valueOf(leftValue.toString().length());
		}
	}

	/**
	 * Converts all of the characters in a string to lower case. The default
	 * locale applies.
	 */
	public static class ToLowerFunction extends ScalarOperation {

		@Override
		public String calculateValue(ModelElement element, ExpressionNode node,
				Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			return leftValue.toString().toLowerCase();
		}
	}

	/**
	 * Returns the type name of a model element as a string.
	 */
	public static class TypeOfFunction extends ScalarOperation {

		@Override
		public String calculateValue(ModelElement element, ExpressionNode node,
				Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			if (leftValue instanceof ModelElement) {
				ModelElement elem = (ModelElement) leftValue;
				return elem.getType().getName();
			}
			if (MetricTools.isEmptyElement(leftValue)) {
				return "";
			}
			throw new SDMetricsException(element, null, "Operator '"
					+ node.getValue()
					+ "' can only be applied to model elements.");
		}
	}

	/**
	 * Returns the fully qualified name of a model element as a string.
	 */
	public static class QualifiedNameFunction extends ScalarOperation {
		@Override
		public String calculateValue(ModelElement element, ExpressionNode node,
				Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			if (leftValue instanceof ModelElement) {
				ModelElement elem = (ModelElement) leftValue;
				return elem.getFullName();
			}
			if (MetricTools.isEmptyElement(leftValue)) {
				return "";
			}
			throw new SDMetricsException(element, null, "Operator '"
					+ node.getValue()
					+ "' can only be applied to model elements.");
		}
	}

	/**
	 * Processes the dot operator in metric expressions.
	 */
	public static class DotOperator extends ScalarOperation {
		@Override
		public Object calculateValue(ModelElement element, ExpressionNode node,
				Variables vars) throws SDMetricsException {
			// Suppresses any errors and return empty string
			// if something cannot be evaluated as it should.
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			if (!(leftValue instanceof ModelElement)) {
				return "";
			}
			try {
				return evalExpression((ModelElement) leftValue,
						node.getRightNode(), vars);
			} catch (Exception ex) {
				return "";
			}
		}
	}

	/**
	 * Processes the "upto" operator.
	 */
	public static class UpToOperator extends ScalarOperation {
		@Override
		public Object calculateValue(ModelElement element, ExpressionNode node,
				Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			while (leftValue != null) {
				if (leftValue instanceof ModelElement) {
					ModelElement elem = (ModelElement) leftValue;
					if (evalBooleanExpression(elem, 
							node.getRightNode(), vars)) {
						return elem;
					}
					leftValue = evalExpression(elem, node.getLeftNode(), vars);
				} else {
					leftValue = null;
				}
			}
			return "";
		}
	}

	/**
	 * Processes the "topmost" operator.
	 */
	public static class TopMostOperator extends ScalarOperation {
		@Override
		public Object calculateValue(ModelElement element, ExpressionNode node,
				Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			Object result = "";
			while (leftValue != null) {
				if (leftValue instanceof ModelElement) {
					ModelElement elem = (ModelElement) leftValue;
					if (evalBooleanExpression(elem, 
							node.getRightNode(), vars)) {
						result = elem;
					}
					leftValue = evalExpression(elem, node.getLeftNode(), vars);
				} else {
					leftValue = null;
				}
			}
			return result;
		}
	}

	/**
	 * Determines the cardinality of an element in a set.
	 */
	public static class InOperator extends ScalarOperation {
		@Override
		public Object calculateValue(ModelElement element, ExpressionNode node,
				Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			ExpressionNode rightNode = node.getRightNode();
			Collection<?> c = evalSetExpression(element, rightNode, vars);
			return Integer.valueOf(MetricTools.elementCount(c, leftValue));
		}
	}

	/**
	 * Calculates the unary and binary plus operator for metric expressions.
	 * Performs numerical addition or string concatenation, depending on the
	 * types of the elements.
	 */
	public static class PlusOperator extends ScalarOperation {
		@Override
		public Object calculateValue(ModelElement element, ExpressionNode node,
				Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);

			// process unary operator
			if (node.getOperandCount() == 1) {
				return leftValue;
			}

			// String concatenation if left hand side is not a number
			if (!(leftValue instanceof Number)) {
				Object rightValue = evalExpression(element,
						node.getRightNode(), vars);
				if (rightValue instanceof Number) {
					return leftValue.toString()
							+ java.text.NumberFormat.getInstance().format(
									rightValue);
				}
				return leftValue.toString() + rightValue.toString();
			}

			// numerical addition
			float left = getFloatValue(leftValue, element);
			float right = getFloatValue(element, node.getRightNode(), vars);
			return Float.valueOf(left + right);
		}
	}

	/**
	 * Calculates the unary and binary minus operator for metric expressions.
	 */
	public static class MinusOperator extends ScalarOperation {

		@Override
		public Float calculateValue(ModelElement element, ExpressionNode node,
				Variables vars) throws SDMetricsException {
			float left = getFloatValue(element, node.getLeftNode(), vars);
			if (node.getOperandCount() == 1) {
				return Float.valueOf(-left);
			}
			float right = getFloatValue(element, node.getRightNode(), vars);
			return Float.valueOf(left - right);
		}
	}

	/**
	 * Base class for binary metric operators that operate on numerical values
	 * only.
	 */
	abstract static class BinaryNumericalOperator extends ScalarOperation {
		@Override
		public final Float calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			float left = getFloatValue(element, node.getLeftNode(), vars);
			float right = getFloatValue(element, node.getRightNode(), vars);
			return Float.valueOf(calculateValue(left, right));
		}

		/**
		 * Calculates the numerical value of the operation.
		 * 
		 * @param left The value of the left hand side operand.
		 * @param right The value of the right hand side operand.
		 * @return Value of the operation.
		 */
		protected abstract float calculateValue(float left, float right);
	}

	/**
	 * Calculates the product of two numbers.
	 */
	public static class MultiplicationOperator extends BinaryNumericalOperator {
		@Override
		protected float calculateValue(float left, float right) {
			return left * right;
		}
	}

	/**
	 * Calculates the quotient of two numbers.
	 */
	public static class DivisionOperator extends BinaryNumericalOperator {
		@Override
		protected float calculateValue(float left, float right) {
			return left / right;
		}
	}

	/**
	 * Exponentiates two numbers.
	 */
	public static class ExponentiationOperator extends BinaryNumericalOperator {
		@Override
		protected float calculateValue(float left, float right) {
			return (float) java.lang.Math.pow(left, right);
		}
	}

	/**
	 * Base class for unary metric operators that operate on numerical values
	 * only.
	 */
	abstract static class UnaryNumericalOperator extends ScalarOperation {
		@Override
		public final Float calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			float left = getFloatValue(element, node.getLeftNode(), vars);
			return Float.valueOf(calculateValue(left));
		}

		/**
		 * Calculates the numerical value of the operation.
		 * 
		 * @param operand The value of the single operand.
		 * @return Value of the operation.
		 */
		protected abstract float calculateValue(float operand);
	}

	/**
	 * Calculates the natural logarithm (base <code>e</code> of a value.
	 */
	public static class LogarithmFunction extends UnaryNumericalOperator {
		@Override
		protected float calculateValue(float left) {
			return (float) Math.log(left);
		}
	}

	/**
	 * Calculates the <code>e</code> raised to the power of a value.
	 */
	public static class ExponentiationFunction extends UnaryNumericalOperator {
		@Override
		protected float calculateValue(float left) {
			return (float) Math.exp(left);
		}
	}

	/**
	 * Calculates the square root of a value.
	 */
	public static class SquareRootFunction extends UnaryNumericalOperator {
		@Override
		protected float calculateValue(float left) {
			return (float) Math.sqrt(left);
		}
	}

	/**
	 * Calculates the absolute value of value.
	 */
	public static class AbsoluteFunction extends UnaryNumericalOperator {
		@Override
		protected float calculateValue(float left) {
			return Math.abs(left);
		}
	}

	/**
	 * Rounds to the next lower integer (towards -infinity).
	 */
	public static class FloorFunction extends UnaryNumericalOperator {
		@Override
		protected float calculateValue(float left) {
			return (float) Math.floor(left);
		}
	}

	/**
	 * Rounds to the next higher integer (towards +infinity).
	 */
	public static class CeilingFunction extends UnaryNumericalOperator {
		@Override
		protected float calculateValue(float left) {
			return (float) Math.ceil(left);
		}
	}

	/**
	 * Rounds to the nearest integer ("half up").
	 */
	public static class RoundFunction extends UnaryNumericalOperator {
		@Override
		protected float calculateValue(float left) {
			return (float) Math.floor(left + 0.5);
		}
	}

	/**
	 * Returns the numerical value of a string containing a number. Assumes
	 * Java-style formatting for decimal numbers.
	 * 
	 * @since 2.31
	 */
	public static class ParseNumberFunction extends ScalarOperation {

		@Override
		public Number calculateValue(ModelElement element, ExpressionNode node,
				Variables vars) throws SDMetricsException {
			String str = evalExpression(element, node.getOperand(0), vars)
					.toString();
			try {
				float value = Float.parseFloat(str);
				return MetricTools.getNumber(value);
			} catch (NumberFormatException ex) {
				throw new SDMetricsException(element, null,
						"Could not parse as number: '" + str + "'");
			}
		}
	}

	// Boolean operations

	/**
	 * Calculates the "and" operator. Short-circuits if possible.
	 */
	public static class AndOperator extends BooleanOperation {
		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			if (evalBooleanExpression(element, node.getLeftNode(), vars)) {
				return evalBooleanExpression(element, node.getRightNode(), vars);
			}
			return false;
		}
	}

	/**
	 * Calculates the "or" operator. Short-circuits if possible.
	 */
	public static class OrOperator extends BooleanOperation {
		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			if (!evalBooleanExpression(element, node.getLeftNode(), vars)) {
				return evalBooleanExpression(element, node.getRightNode(), vars);
			}
			return true;
		}
	}

	/**
	 * Calculates the "not" operator.
	 */
	public static class NotOperator extends BooleanOperation {
		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			return !evalBooleanExpression(element, node.getLeftNode(), vars);
		}
	}

	/**
	 * Checks if all elements in a multiset have cardinality 1.
	 */
	public static class IsUniqueFunction extends BooleanOperation {

		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Collection<?> c = evalSetExpression(element, node.getLeftNode(),
					vars);
			if (!(MetricTools.isMultiSet(c))) {
				return true;
			}

			return c.size() == ((HashMultiSet<?>) c).flatSetSize();
		}
	}

	/**
	 * Checks if a string starts with a capital (upper case) letter.
	 */
	public static class StartsWithCapitalFunction extends BooleanOperation {

		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			String s = leftValue.toString();
			if (s.length() < 1) {
				return true;
			}
			return s.charAt(0) == Character.toUpperCase(s.charAt(0));
		}
	}

	/**
	 * Checks if a string starts with a lower case letter.
	 */
	public static class StartsWithLowerCaseFunction extends BooleanOperation {

		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			String s = leftValue.toString();
			if (s.length() < 1) {
				return true;
			}
			return s.charAt(0) == Character.toLowerCase(s.charAt(0));
		}
	}

	/**
	 * Checks that a string does not contain any capital letters.
	 */
	public static class IsLowerCaseFunction extends BooleanOperation {

		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			String s = leftValue.toString();
			return s.equals(s.toLowerCase());
		}
	}

	/**
	 * Checks that a model element has a particular type or one of its
	 * sub-types.
	 */
	public static class InstanceOfFunction extends BooleanOperation {
		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {

			// Candidate element is either "self" (if there is only one argument
			// to the function), or the first argument of the function
			ModelElement candidate = element;
			if (node.getOperandCount() == 2) {
				Object o = evalExpression(element, node.getOperand(0), vars);
				if (MetricTools.isEmptyElement(o)) {
					return false;
				}
				if (!(o instanceof ModelElement)) {
					throw new SDMetricsException(element, null,
							"First argument of binary function "
									+ node.getValue()
									+ " must yield a model element");
				}
				candidate = (ModelElement) o;
			}

			// Type to check is the last argument of the function
			Object base = evalExpression(element,
					node.getOperand(node.getOperandCount() - 1), vars);
			MetaModelElement baseType = null;
			if (base instanceof ModelElement) {
				baseType = ((ModelElement) base).getType();
			} else {
				baseType = getMetaModel().getType(base.toString());
			}

			if (baseType == null) {
				throw new SDMetricsException(element, null,
						"Unknown base type '" + base.toString() + "'");
			}

			// Check if model element is an instance of the base type
			return candidate.getType().specializes(baseType);
		}
	}

	/**
	 * Checks if a set contains a particular model element or value.
	 */
	public static class InOperatorBool extends BooleanOperation {

		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			Collection<?> c = evalSetExpression(element, node.getRightNode(),
					vars);
			return c.contains(leftValue);
		}
	}

	/**
	 * Checks if a word list contains a particular word.
	 */
	public static class OnListOperator extends BooleanOperation {

		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			ExpressionNode rhsNode = node.getRightNode();
			String wordListName;
			if (rhsNode.isIdentifier() || rhsNode.isStringConstant()) {
				wordListName = rhsNode.getValue();
			} else {
				wordListName = evalExpression(element, rhsNode, vars)
						.toString();
			}
			return getMetricsEngine().getMetricStore().isWordOnList(
					leftValue.toString(), wordListName);
		}
	}

	/**
	 * Base class for binary comparison operators. The operators perform
	 * numerical comparisons when both arguments are numbers, and string or
	 * object comparisons otherwise.
	 */
	abstract static class BooleanComparator extends BooleanOperation {
		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			Object rightValue = evalExpression(element, node.getRightNode(),
					vars);

			if (leftValue instanceof Number) {
				if (rightValue instanceof Number) {
					float lf = ((Number) leftValue).floatValue();
					float rf = ((Number) rightValue).floatValue();

					return compare(lf, rf);
				}
			}

			return compare(leftValue, rightValue);
		}

		/**
		 * Performs a numerical comparison.
		 * 
		 * @param left left hand side operand to compare
		 * @param right right hand side operand to compare
		 * @return result of the comparison
		 */
		protected abstract boolean compare(float left, float right);

		/**
		 * Performs an object or string comparison.
		 * 
		 * @param left left hand side operand to compare
		 * @param right right hand side operand to compare
		 * @return result of the comparison
		 */
		protected abstract boolean compare(Object left, Object right);

	}

	/**
	 * Compares for equality. Object identity is determined by the equals()
	 * method.
	 */
	public static class EqualsOperator extends BooleanComparator {
		@Override
		protected boolean compare(float left, float right) {
			return left == right;
		}

		@Override
		protected boolean compare(Object left, Object right) {
			return left.equals(right);
		}
	}

	/**
	 * Compares for non-equality. Object identity is determined by the equals()
	 * method.
	 */
	public static class NotEqualsOperator extends BooleanComparator {
		@Override
		protected boolean compare(float left, float right) {
			return left != right;
		}

		@Override
		protected boolean compare(Object left, Object right) {
			return !left.equals(right);
		}
	}

	/** Performs "greater than" comparisons for numerical or string values. */
	public static class GreaterThanOperator extends BooleanComparator {
		@Override
		protected boolean compare(float left, float right) {
			return left > right;
		}

		@Override
		protected boolean compare(Object left, Object right) {
			int comp = left.toString().compareTo(right.toString());
			return comp > 0;
		}
	}

	/** Performs "greater or equal" comparisons for numerical or string values. */
	public static class GreaterOrEqualOperator extends BooleanComparator {
		@Override
		protected boolean compare(float left, float right) {
			return left >= right;
		}

		@Override
		protected boolean compare(Object left, Object right) {
			int comp = left.toString().compareTo(right.toString());
			return comp >= 0;
		}
	}

	/** Performs "less than" comparisons for numerical or string values. */
	public static class LessThanOperator extends BooleanComparator {
		@Override
		protected boolean compare(float left, float right) {
			return left < right;
		}

		@Override
		protected boolean compare(Object left, Object right) {
			int comp = left.toString().compareTo(right.toString());
			return comp < 0;
		}
	}

	/** Performs "greater or equal" comparisons for numerical or string values. */
	public static class LessOrEqualOperator extends BooleanComparator {
		@Override
		protected boolean compare(float left, float right) {
			return left <= right;
		}

		@Override
		protected boolean compare(Object left, Object right) {
			int comp = left.toString().compareTo(right.toString());
			return comp <= 0;
		}
	}

	/**
	 * Checks if a string value starts with a specified prefix.
	 */
	public static class StartsWithOperator extends BooleanOperation {

		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			Object rightValue = evalExpression(element, node.getRightNode(),
					vars);
			return leftValue.toString().startsWith(rightValue.toString());
		}
	}

	/**
	 * Checks if a string value ends with a specified prefix.
	 */
	public static class EndsWithOperator extends BooleanOperation {

		@Override
		public boolean calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			Object leftValue = evalExpression(element, node.getLeftNode(), vars);
			Object rightValue = evalExpression(element, node.getRightNode(),
					vars);
			return leftValue.toString().endsWith(rightValue.toString());
		}
	}

	// Set operations

	/**
	 * Calculates the dot operator for set expressions.
	 */
	public static class DotOperatorSet extends SetOperation {
		@Override
		public Collection<?> calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {
			// Suppresses any errors and returns empty set
			// if something cannot be evaluated as it should.
			Object lhsObj = evalExpression(element, node.getLeftNode(), vars);
			if (!(lhsObj instanceof ModelElement)) {
				return Collections.EMPTY_SET;
			}
			try {
				return evalSetExpression((ModelElement) lhsObj,
						node.getRightNode(), vars);
			} catch (Exception ex) {
				return Collections.EMPTY_SET;
			}
		}
	}

	/**
	 * Base class for binary set operations where both operands are sets.
	 */
	abstract static class BinarySetOperation extends SetOperation {
		@Override
		public Collection<?> calculateValue(ModelElement element,
				ExpressionNode node, Variables vars) throws SDMetricsException {

			// evaluate the left and right hand side operands
			Collection<?> leftValue = evalSetExpression(element,
					node.getLeftNode(), vars);
			Collection<?> rightValue = evalSetExpression(element,
					node.getRightNode(), vars);

			return calculateValue(leftValue, rightValue);
		}

		/**
		 * Calculates the numerical value of the set operation.
		 * 
		 * @param left The value of the left hand side operand.
		 * @param right The value of the right hand side operand.
		 * @return Result set for the operation.
		 */
		public abstract Collection<?> calculateValue(Collection<?> left,
				Collection<?> right);
	}

	/**
	 * Calculates the union of two sets. Result is a multiset if any of the
	 * operands is a multiset.
	 */
	public static class UnionOperation extends BinarySetOperation {
		@SuppressWarnings("unchecked")
		@Override
		public Collection<?> calculateValue(Collection<?> left,
				Collection<?> right) {
			boolean isMultiSet = MetricTools.isMultiSet(right)
					|| MetricTools.isMultiSet(left);
			@SuppressWarnings("rawtypes")
			Collection result = MetricTools.createHashSet(isMultiSet, left);
			result.addAll(right);
			return result;
		}
	}

	/**
	 * Calculates the difference of two sets. The result is of the same type as
	 * the base (left hand side) set.
	 */
	public static class DifferenceOperation extends BinarySetOperation {
		@Override
		public Collection<?> calculateValue(Collection<?> left,
				Collection<?> right) {
			Collection<?> result = MetricTools.createHashSet(
					MetricTools.isMultiSet(left), left);
			result.removeAll(right);
			return result;
		}
	}

	/**
	 * Calculates the intersection of two sets. Result is a multiset if both
	 * sets are multisets. Otherwise, the result is a regular set.
	 */
	public static class IntersectionOperation extends BinarySetOperation {
		@Override
		public Collection<?> calculateValue(Collection<?> left,
				Collection<?> right) {
			boolean isMultiSet = MetricTools.isMultiSet(right)
					&& MetricTools.isMultiSet(left);
			Collection<?> result = MetricTools.createHashSet(isMultiSet, left);
			result.retainAll(right);
			return result;
		}
	}
}
