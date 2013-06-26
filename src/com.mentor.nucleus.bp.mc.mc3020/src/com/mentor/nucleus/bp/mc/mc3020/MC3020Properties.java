// ========================================================================
//
//File: $RCSfile: MC3020Properties.java,v $
//Version: $Revision: 1.11 $
//Modified: $Date: 2013/01/10 22:49:41 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.mc.mc3020;

import java.util.Properties;

public class MC3020Properties {

	public static final int MARKING_FILE_LOCATION = 0;

	public static final int GENERATED_SOURCE_CODE_DEST = 1;

	public static final int BUILD_COMMAND = 2;

	public static final int ADDITIONAL_ARGS_FOR_BUILDER = 3;

	public static final int GENERATED_CODE_DEST = 4;

	public static final int LAUNCH_SPEC_LOCATION = 5;

    public static final int XBUILD_LOCAL_LOCATION = 6;

	private static final String MARKING_FILE_LOCATION_NAME = "marking_file_location"; //$NON-NLS-1$

	private static final String MARKING_FILE_LOCATION_DEFAULT = "default/marking"; //$NON-NLS-1$

	private static final String GENERATED_SOURCE_CODE_DEST_NAME = "generated_source_code_dest"; //$NON-NLS-1$

	private static final String GENERATED_SOURCE_CODE_DEST_DEFAULT = "src"; //$NON-NLS-1$

	private static final String BUILD_COMMAND_NAME = "build_command"; //$NON-NLS-1$

	private static final String BUILD_COMMAND_DEFAULT = ""; //$NON-NLS-1$

	private static final String ADDITIONAL_ARGS_FOR_BUILDER_NAME = "additional_args_for_builder"; //$NON-NLS-1$

	private static final String ADDITIONAL_ARGS_FOR_BUILDER_DEFAULT = "-e"; //$NON-NLS-1$

	private static final String GENERATED_CODE_DEST_NAME = "generated_code_dest"; //$NON-NLS-1$

	private static final String GENERATED_CODE_DEST_DEFAULT = "code_generation"; //$NON-NLS-1$

	private static final String LAUNCH_SPEC_LOCATION_NAME = "launch_spec_location"; //$NON-NLS-1$

	private static final String LAUNCH_SPEC_LOCATION_DEFAULT = "default/launch_specification"; //$NON-NLS-1$

    private static final String XBUILD_LOCAL_LOCATION_NAME = "xbuild_local_location"; //$NON-NLS-1$

    private static final String XBUILD_LOCAL_LOCATION_DEFAULT = "mc3020/bin/xtumlmc_build.exe"; //$NON-NLS-1$

	private static String names[] = { MARKING_FILE_LOCATION_NAME,
			GENERATED_SOURCE_CODE_DEST_NAME, BUILD_COMMAND_NAME,
			ADDITIONAL_ARGS_FOR_BUILDER_NAME, GENERATED_CODE_DEST_NAME,
			LAUNCH_SPEC_LOCATION_NAME, XBUILD_LOCAL_LOCATION_NAME };

	private static String defaults[] = { MARKING_FILE_LOCATION_DEFAULT,
			GENERATED_SOURCE_CODE_DEST_DEFAULT, BUILD_COMMAND_DEFAULT,
			ADDITIONAL_ARGS_FOR_BUILDER_DEFAULT, GENERATED_CODE_DEST_DEFAULT,
			LAUNCH_SPEC_LOCATION_DEFAULT, XBUILD_LOCAL_LOCATION_DEFAULT };

	public static String getPropertyOrDefault(Properties props, int key) {
		String result = props.getProperty(names[key]);
		if (result.equals("")) { //$NON-NLS-1$
			//use default
			result = defaults[key];
		}
		return result;
	}
}