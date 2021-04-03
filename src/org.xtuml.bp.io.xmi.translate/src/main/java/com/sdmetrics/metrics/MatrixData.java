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

import java.util.List;

import com.sdmetrics.math.IntegerMatrix;
import com.sdmetrics.model.ModelElement;

/**
 * Data structure to store the data for one matrix.
 * 
 * The rows of each matrix are the source model elements from which the relation
 * of the matrix originates, the columns are the target model elements. The cell
 * values show the presence or number of relations between the two model
 * elements of the respective row and column.
 * */
public class MatrixData {
	/** The definition of the matrix. */
	private final Matrix definition;
	/** The list of row model elements of the matrix. */
	private final List<ModelElement> rowElements;
	/** The list of column model elements of the matrix. */
	private final List<ModelElement> colElements;
	/** Stores the contents of the matrix. */
	private final IntegerMatrix values;

	/**
	 * Creates a new relation matrix.
	 * 
	 * @param matrix definition of the matrix
	 * @param rows list of row model elements of the matrix
	 * @param columns list of column model elements of the matrix
	 * @param values the contents of the matrix
	 */
	MatrixData(Matrix matrix, List<ModelElement> rows,
			List<ModelElement> columns, IntegerMatrix values) {
		this.definition = matrix;
		this.rowElements = rows;
		this.colElements = columns;
		this.values = values;
	}

	/**
	 * Gets the definition of the matrix.
	 * 
	 * @return Definition of the matrix in the metric definition file.
	 */
	public Matrix getMatrixDefinition() {
		return definition;
	}

	/**
	 * Gets the number of rows (source model elements) of this relation matrix.
	 * 
	 * @return The number R of rows of the matrix. Valid row indices run from 0
	 *         to R-1.
	 */
	public int getNumberOfRows() {
		return rowElements.size();
	}

	/**
	 * Gets the number of columns (target model elements) of this relation
	 * matrix.
	 * 
	 * @return The number C of columns of the matrix. Valid column indices run
	 *         from 0 to C-1.
	 */
	public int getNumberOfColumns() {
		return colElements.size();
	}

	/**
	 * Tests if this relation matrix is empty (a null matrix).
	 * 
	 * @return <code>true</code> if all cells of the matrix are zero,
	 *         <code>false</code> if there is at least one non-zero entry.
	 */
	public boolean isEmpty() {
		return values.isEmpty();
	}

	/**
	 * Gets the value of a cell of this relation matrix.
	 * 
	 * @param row Row index of the cell to access.
	 * @param col Column index of the cell to access.
	 * @return Value of the specified cell.
	 */
	public Integer getValueAt(int row, int col) {
		return values.get(row, col);
	}

	/**
	 * Gets the source element in a row of this relation matrix.
	 * 
	 * @param row Row index.
	 * @return The model element at the specified row.
	 */
	public ModelElement getRowElement(int row) {
		return rowElements.get(row);
	}

	/**
	 * Gets the target model element in a column of this relation matrix.
	 * 
	 * @param col Column index.
	 * @return The model element at the specified column.
	 */
	public ModelElement getColumnElement(int col) {
		return colElements.get(col);
	}
}
