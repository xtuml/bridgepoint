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

import com.sdmetrics.model.ModelElement;

/**
 * Base class for all set procedures.
 */
public abstract class SetProcedure extends AbstractProcedure {

	/**
	 * Calculates the set procedure.
	 * 
	 * @param element The model element for which to calculate the procedure.
	 * @param set Definition of the set to calculate.
	 * @return The value of the resulting set.
	 * @throws SDMetricsException Problem during the calculation of the set.
	 */
	protected abstract Collection<?> calculate(ModelElement element, Set set)
			throws SDMetricsException;
}
