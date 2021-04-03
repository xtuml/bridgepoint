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

import java.util.regex.Pattern;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.model.ModelElement;

/**
 * Calculates the "substring" procedure to extract substrings from a string.
 * 
 * @since 2.31
 */
public class MetricProcedureSubString extends MetricProcedure {

	@Override
	public Object calculate(ModelElement element, Metric metric)
			throws SDMetricsException {

		ProcedureAttributes attributes = metric.getAttributes();
		Variables vars = new Variables(element);

		// get source string, separator, and position
		ExpressionNode sourceNode = attributes.getRequiredExpression("source");
		final String source = evalExpression(element, sourceNode, vars).toString();

		ExpressionNode sepNode = attributes.getRequiredExpression("separator");
		String separator = evalExpression(element, sepNode, vars).toString();
		separator = Pattern.quote(separator);

		int position = -1;
		ExpressionNode positionNode = attributes.getExpression("position");
		if (positionNode != null) {
			position = ((Number) evalExpression(element, positionNode, vars))
					.intValue();
		}

		int limit = -1;
		ExpressionNode limitNode = attributes.getExpression("limit");
		if (limitNode != null) {
			limit = ((Number) evalExpression(element, limitNode, vars))
					.intValue();
		}

		// extract the substring
		String[] fragments = source.split(separator, limit);
		int positionToUse = position;
		if (position < 0) {
			positionToUse = fragments.length + position;
		}

		if (positionToUse < 0 || positionToUse >= fragments.length) {
			throw new SDMetricsException(element, metric,
					"Illegal separator position " + position + " for source '"
							+ source + "'");
		}

		String result = fragments[positionToUse].trim();

		// apply end separator
		ExpressionNode endSepNode = attributes.getExpression("endseparator");
		if (endSepNode != null) {
			String endseparator = evalExpression(element, endSepNode, vars)
					.toString();
			int endIndex = result.indexOf(endseparator);
			if (endIndex >= 0) {
				result = result.substring(0, endIndex).trim();
			}
		}

		// apply result processing expression
		ExpressionNode resultNode = attributes.getExpression("result");
		if (resultNode != null) {
			vars.setVariable("_value", result);
			return evalExpression(element, resultNode, vars);
		}

		return result;
	}
}
