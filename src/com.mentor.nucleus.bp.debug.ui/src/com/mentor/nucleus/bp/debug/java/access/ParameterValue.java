package com.mentor.nucleus.bp.debug.java.access;

public class ParameterValue {
	private Object value;
	private int arrayDep;

	public ParameterValue(Object obj, int arrayDepth) {
		value = obj;
		arrayDep = arrayDepth;
	}

	public Object getValue() {
		return value;
	}

	public int getArrayDepth() {
		return arrayDep;
	}
}
