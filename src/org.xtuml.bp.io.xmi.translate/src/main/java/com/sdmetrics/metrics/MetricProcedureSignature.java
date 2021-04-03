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
import java.util.TreeSet;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.ModelElement;

/**
 * Calculates a "signature" metric procedure.
 */
public class MetricProcedureSignature extends MetricProcedure {

	@Override
	public String calculate(ModelElement element, Metric metric)
			throws SDMetricsException {

		ProcedureAttributes attributes = metric.getAttributes();
		Variables vars = new Variables(element);

		StringBuilder signature = new StringBuilder();
		ExpressionNode prefix = attributes.getExpression("prologue");
		if (prefix != null) {
			signature.append(evalExpression(element, prefix, vars));
		} else {
			signature.append(element.getName());
			signature.append('(');
		}

		// retrieve set of elements related to e via specified relation
		Collection<ModelElement> set = evalElementSetExpression(element,
				attributes.getRequiredExpression("set"), vars);
		if (set != null) {
			// Gather the elements that define the signature
			FilterAttributeProcessor fap = getFilterAttributeProcessor(attributes);
			TreeSet<ModelElement> paramList = new TreeSet<>(
					ModelElement.getComparator());
			for (ModelElement projElem : fap.validIteration(set, vars)) {
				paramList.add(projElem);
			}

			// Get separator definition
			String separatorString = ",";
			ExpressionNode separatorNode = attributes
					.getExpression("separator");
			if (separatorNode != null) {
				separatorString = String.valueOf(evalExpression(element,
						separatorNode, vars));
			}

			// add each element to the signature
			ExpressionNode valueNode = attributes.getExpression("value");
			Iterator<ModelElement> it = paramList.iterator();
			while (it.hasNext()) {
				if (valueNode != null) {
					signature
							.append(evalExpression(it.next(), valueNode, vars));
				} else {
					signature.append(it.next().getXMIID());
				}
				if (it.hasNext()) {
					signature.append(separatorString);
				}
			}
		}

		ExpressionNode postfix = attributes.getExpression("epilogue");
		if (postfix != null) {
			signature.append(evalExpression(element, postfix, vars));
		} else {
			signature.append(')');
		}

		return signature.toString();
	}
}
