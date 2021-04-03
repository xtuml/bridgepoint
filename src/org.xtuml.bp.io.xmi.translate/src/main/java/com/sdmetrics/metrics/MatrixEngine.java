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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.math.IntegerMatrix;
import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;

/**
 * Calculates relation matrices.
 */
public class MatrixEngine {

	/** Metrics engine to use. */
	private final MetricsEngine engine;
	/** The model with all available elements. */
	private final Model model;

	/** Set to calculate the set definition procedures for the matrices. */
	private final Set proxy;

	/**
	 * Creates a new matrix engine.
	 * 
	 * @param me The metrics engine to be used for the calculation.
	 */
	public MatrixEngine(MetricsEngine me) {
		this.engine = me;
		model = me.getModel();

		proxy = new Set("dummy", me.getMetaModel().getType(
				MetaModel.BASE_ELEMENT));
		proxy.setMultiSet(true);
	}

	/**
	 * Returns the metrics engine that this matrix engine uses.
	 * 
	 * @return Metrics engine of the matrix engine.
	 */
	public MetricsEngine getMetricsEngine() {
		return engine;
	}

	/**
	 * Calculates a relation matrix.
	 * 
	 * @param matrix Definition of the matrix to calculate.
	 * @return The matrix
	 * @throws SDMetricsException An error occurred during the calculation of
	 *         the matrix.
	 */
	public MatrixData calculate(Matrix matrix) throws SDMetricsException {
		// create lists of accepted row and column elements
		final List<ModelElement> rows = getElementList(matrix.getRowType(),
				matrix.getRowCondition());
		final List<ModelElement> columns = getElementList(matrix.getColumnType(),
				matrix.getColumnCondition());

		// create a mapping from the column elements to their column index
		HashMap<Object, Integer> colIndices = new HashMap<>();
		for (int i = 0; i < columns.size(); i++) {
			colIndices.put(columns.get(i), Integer.valueOf(i));
		}

		ProcedureAttributes attributes = matrix.getAttributes();
		proxy.setAttributes(attributes);
		// set the procedure name, for backwards compatibility, replace
		// deprecated "setoperation" with the current "compoundset"
		String procName = matrix.getProcedureName();
		if ("setoperation".equals(procName)) {
			procName = "compoundset";
		}
		proxy.setProcedureName(procName);

		// Calculate the contents of the matrix
		IntegerMatrix values = new IntegerMatrix();
		for (int row = 0; row < rows.size(); row++) {
			Collection<?> set = engine.computeSet(rows.get(row), proxy);
			for (Object colElement : set) {
				Integer colIndex = colIndices.get(colElement);
				if (colIndex != null) {
					values.increment(row, colIndex.intValue());
				}
			}
		}

		return new MatrixData(matrix, rows, columns, values);
	}

	/**
	 * Returns the list of elements of a type that satisfy a given condition.
	 * 
	 * @param type Type of the elements to return
	 * @param condition The condition for the elements to return,
	 *        <code>null</code> to return all elements.
	 * @return List of matching elements
	 * @throws SDMetricsException Error evaluating the condition
	 */
	private List<ModelElement> getElementList(MetaModelElement type,
			ExpressionNode condition) throws SDMetricsException {

		List<ModelElement> candidateElements = model.getAcceptedElements(type);
		if (condition == null) {
			return candidateElements;
		}

		List<ModelElement> result = new ArrayList<>();
		for (ModelElement elem : candidateElements) {
			if (engine.evalBooleanExpression(elem, condition, null)) {
				result.add(elem);
			}
		}
		return result;
	}
}
