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

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.MetaModelElement;

/**
 * Represents the definition of a relation matrix in the metric definition file.
 * A matrix has a row and a column type, and, optionally row and column filter
 * expressions.
 */
public class Matrix extends MetricEntry {
	/** Type of the source elements that make up the rows. */
	private final MetaModelElement rowType;
	/** Type of the target elements that make up the columns. */
	private final MetaModelElement columnType;
	/** Condition expression for row elements. */
	private ExpressionNode rowCondition = null;
	/** Condition expression for column elements. */
	private ExpressionNode columnCondition = null;

	/**
	 * Creates a new relation matrix definition.
	 * 
	 * @param name Name of the relation matrix.
	 * @param rowType Type of the source elements that make up the rows.
	 * @param columnType Type of the target elements that make up the columns.
	 */
	public Matrix(String name, MetaModelElement rowType,
			MetaModelElement columnType) {
		super(name);
		this.rowType = rowType;
		this.columnType = columnType;
	}

	/**
	 * Retrieves the element type of the source elements (rows).
	 * 
	 * @return Metamodel element type of the row elements.
	 */
	public MetaModelElement getRowType() {
		return rowType;
	}

	/**
	 * Retrieves the element type of the target elements (columns).
	 * 
	 * @return Metamodel element type of the column elements.
	 */
	public MetaModelElement getColumnType() {
		return columnType;
	}

	/**
	 * Gets the condition expression for row elements to be included in the
	 * matrix.
	 * 
	 * @return Root node of the row condition expression operator tree.
	 */
	public ExpressionNode getRowCondition() {
		return rowCondition;
	}

	/**
	 * Gets the condition expression for column elements to be included in the
	 * matrix.
	 * 
	 * @return Root node of the column condition expression operator tree.
	 */
	public ExpressionNode getColumnCondition() {
		return columnCondition;
	}

	/**
	 * Sets the condition for row elements to be included in the matrix.
	 * 
	 * @param conditionExpr Root node of the row condition's operator tree.
	 */
	void setRowCondition(ExpressionNode conditionExpr) {
		this.rowCondition = conditionExpr;
	}

	/**
	 * Sets the condition for column elements to be included in the matrix.
	 * 
	 * @param conditionExpr Root node of the column condition's operator tree.
	 */
	void setColumnCondition(ExpressionNode conditionExpr) {
		this.columnCondition = conditionExpr;
	}

	/**
	 * Returns a string representation of the matrix definition.
	 * 
	 * @return String with the name of matrix and the row/column type names, and
	 *         the line number of the definition
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("relation matrix ");
		sb.append(name);
		sb.append(" [");
		sb.append(rowType.getName());
		sb.append(" X ");
		sb.append(columnType.getName());
		sb.append("]");
		if (location > 0) {
			sb.append(" (line ");
			sb.append(location);
			sb.append(")");
		}
		return sb.toString();
	}
}
