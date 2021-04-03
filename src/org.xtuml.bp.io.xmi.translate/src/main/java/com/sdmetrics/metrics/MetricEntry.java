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

/**
 * Base class for entries in the metric definition file (metrics, sets, rules,
 * or matrices). An entry has a name, a brief informal description, a full
 * description, a location (line number), and a calculation procedure that
 * defines the entry.
 */
public abstract class MetricEntry {
	/** Name of the entry in the metric definition file. */
	protected String name;
	/** Entry ID number (array index for efficient storage). */
	protected int id;
	/** Description of the entry. */
	protected Description description = new Description();
	/** Name of the calculation procedure for the entry. */
	protected String procedureName;
	/** Attributes of the calculation procedure for the entry. */
	protected ProcedureAttributes attributes = null;
	/** Line number of the entry in the metric definition file. */
	protected int location;
	/** Indicates if this entry can be inherited. */
	private boolean inheritable = false;

	/**
	 * Constructor.
	 * 
	 * @param name Name of the entry.
	 */
	public MetricEntry(String name) {
		this.name = name;
	}

	/**
	 * Creates a copy of the metric entry. The copy shares the description and
	 * procedure definition attributes by reference.
	 * 
	 * @param original The metric entry to copy.
	 */
	MetricEntry(MetricEntry original) {
		this(original.name);
		this.description = original.description;
		this.procedureName = original.procedureName;
		this.attributes = original.attributes;
		this.location = original.location;
		this.inheritable = original.inheritable;
	}

	/**
	 * Retrieves the name of the entry.
	 * 
	 * @return name of the entry
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds text to the description for this entry.
	 * 
	 * @param str Character array with the text to add
	 * @param offset Index of the first character to add
	 * @param len length of the text to add to this entry's description.
	 */
	void addDescription(char[] str, int offset, int len) {
		description.add(str, offset, len);
	}

	/**
	 * Gets the brief description for this entry.
	 * 
	 * @return One-liner informally describing the entry.
	 */
	public String getBriefDescription() {
		return description.getBriefDescription();
	}

	/**
	 * Gets the full description for this entry.
	 * 
	 * @return Full, detailed description for the entry.
	 */
	public String getFullDescription() {
		return description.getDescription();
	}

	/**
	 * Gets the calculation procedure attributes for this entry.
	 * 
	 * @return Calculation procedure attributes for this entry
	 */
	public ProcedureAttributes getAttributes() {
		return attributes;
	}

	/**
	 * Gets the name of the calculation procedure.
	 * 
	 * @return Name of the calculation procedure (projection, compoundmetric,
	 *         etc)
	 */
	public String getProcedureName() {
		return procedureName;
	}

	/**
	 * Gets the line number of this entry in the metric definition file.
	 * 
	 * @return Line number of the entry, or <code>0</code> if the location is
	 *         not known.
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * Sets the name of the calculation procedure. To maintain backwards
	 * compatibility it can be useful to replace an old/deprecated name by its
	 * new name.
	 * 
	 * @param newName New name for the calculation procedure.
	 */
	void setProcedureName(String newName) {
		procedureName = newName;
	}

	/**
	 * Sets the calculation procedure attributes for this entry.
	 * 
	 * @param model calculation procedure attributes for this entry.
	 */
	void setAttributes(ProcedureAttributes model) {
		this.attributes = model;
	}

	/**
	 * Sets the line number of this entry's definition in the XML file.
	 * 
	 * @param line Line number of the definition.
	 */
	void setLocation(int line) {
		location = line;
	}

	/**
	 * Sets the integer ID for this entry. IDs are unique for each model element
	 * type, and provide an array index for the metric.
	 * 
	 * @param id The ID for the entry.
	 */
	void setId(int id) {
		this.id = id;
	}

	/**
	 * Retrieves the integer ID for this entry.
	 * 
	 * @return Integer ID of the entry.
	 */
	int getID() {
		return id;
	}

	/**
	 * Checks if this entry can be inherited.
	 * 
	 * @return <code>true</code> if the entry should be passed on to descendant
	 *         types
	 */
	boolean isInheritable() {
		return inheritable;
	}

	/**
	 * Sets the inheritability of this entry.
	 * 
	 * @param value <code>true</code> if the entry should be passed on to
	 *        descendant types, else <code>false</code>
	 */
	void setInheritable(boolean value) {
		inheritable = value;
	}
}
