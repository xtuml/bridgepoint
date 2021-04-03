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

import com.sdmetrics.math.ExpressionNode;

/**
 * Stores the attributes of the calculation procedure definition of a metric,
 * set, matrix, or rule in the metric definition file.
 */
public class ProcedureAttributes {
	/**
	 * The attributes of the procedure. Key is the attribute name, values is the
	 * root node of the operator tree of the attribute.
	 */
	private final HashMap<String, ExpressionNode> attributes = 
			new HashMap<>(4);

	/**
	 * Retrieves the operator tree for an optional attribute holding an
	 * expression.
	 * 
	 * @param attrName Name of the attribute.
	 * @return Root node of the operator tree of the specified attribute, or
	 *         <code>null</code> if the attribute is not set.
	 */
	public ExpressionNode getExpression(String attrName) {
		return attributes.get(attrName);
	}

	/**
	 * Retrieves the operator tree for a required attribute holding an
	 * expression.
	 * 
	 * @param attrName Name of the attribute.
	 * @return Root node of the operator tree of the specified attribute
	 * @throws SDMetricsException The specified attribute is not set.
	 */
	public ExpressionNode getRequiredExpression(String attrName)
			throws SDMetricsException {
		ExpressionNode result = attributes.get(attrName);
		if (result == null) {
			throw new SDMetricsException(null, null,
					"Missing required attribute '" + attrName + "'.");
		}
		return result;
	}

	/**
	 * Retrieves the value of an optional attribute holding a simple string.
	 * 
	 * @param attrName Name of the attribute.
	 * @return String value of the specified attribute, or <code>null</code> if
	 *         the attribute is not set.
	 * @throws SDMetricsException the specified attribute contains operators
	 *         rather than a simple string value.
	 */
	public String getStringValue(String attrName) throws SDMetricsException {
		ExpressionNode node = attributes.get(attrName);
		if (node == null) {
			return null;
		}
		if (node.isOperation()) {
			throw new SDMetricsException(null, null, "Attribute '" + attrName
					+ "' may not contain operators.");
		}
		return node.getValue();
	}

	/**
	 * Retrieves the value of a required attribute holding a simple string.
	 * 
	 * @param attrName Name of the attribute.
	 * @return String value of the specified attribute.
	 * @throws SDMetricsException the specified attribute is not set or contains
	 *         operators rather than a simple string value.
	 */
	public String getRequiredStringValue(String attrName)
			throws SDMetricsException {
		if (!attributes.containsKey(attrName)) {
			throw new SDMetricsException(null, null,
					"Missing required attribute '" + attrName + "'.");
		}
		return getStringValue(attrName);
	}

	/**
	 * Retrieves the value of a boolean attribute.
	 * 
	 * @param attrName Name of the attribute.
	 * @param defaultValue Value to return if the attribute is not set at all.
	 * @return <code>true</code> if the attribute is set to "true",
	 *         <code>false</code> if the attribute is set to anything else than
	 *         "true", <code>defaultValue</code> if the attribute is not set at
	 *         all.
	 * @throws SDMetricsException the specified attribute contains operators
	 *         rather than a simple string value.
	 */
	public boolean getBooleanValue(String attrName, boolean defaultValue)
			throws SDMetricsException {
		String test = getStringValue(attrName);
		if (test == null) {
			return defaultValue;
		}
		return test.equals("true");
	}

	/**
	 * Sets the expression node of an attribute.
	 * 
	 * @param attrName Name of the attribute.
	 * @param value Root node of the operator tree of the expression.
	 */
	void setExpressionNode(String attrName, ExpressionNode value) {
		attributes.put(attrName, value);
	}
}
