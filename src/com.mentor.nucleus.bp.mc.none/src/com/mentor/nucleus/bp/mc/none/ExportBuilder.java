//========================================================================
//
// File: ExportBuilder.java
//
// Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//

package com.mentor.nucleus.bp.mc.none;

import com.mentor.nucleus.bp.mc.AbstractExportBuilder;

public class ExportBuilder extends AbstractExportBuilder {

	// The shared instance
	private static ExportBuilder singleton;

	public ExportBuilder() {
		super(Activator.getDefault(), MCNature.getDefault());
	}

	/**
	 * Returns the shared instance. Creates if it has not yet been created
	 * 
	 * @return the shared instance
	 */
	public static ExportBuilder getDefault() {
		if (ExportBuilder.singleton == null) {
			ExportBuilder.singleton = new ExportBuilder();
		}
		return ExportBuilder.singleton;
	}
}
