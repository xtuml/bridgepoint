package lib;

//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

public class LOG {

	// ========================================================================
	// Bridge: LogInteger
	// ========================================================================
	public static void LogInteger(int ee_message) {

		System.out.println("USERs LogInteger:  " + ee_message);

	}

	// ============================================================================
	// Bridge: LogSuccess
	// ============================================================================
	public static void LogSuccess(String ee_message) {

		System.out.println("USERs LogSuccess:  " + ee_message);

	}

	// ============================================================================
	// Bridge: LogFailure
	// ============================================================================
	public static void LogFailure(String ee_message) {

		System.out.println("USERs LogFailure:  " + ee_message);

	}

	// ============================================================================
	// Bridge: LogInfo
	// ============================================================================
	public static void LogInfo(String ee_message) {

		System.out.println("USERs LogInfo:  " + ee_message);

	}

	// ============================================================================
	// Bridge: LogDate
	// ============================================================================
	public static void LogDate(Object ee_d, String ee_message) {

		System.out.println("USERs LogDate:  " + ee_d + " " + ee_message);

	}

	// ============================================================================
	// Bridge: LogTime
	// ============================================================================
	public static void LogTime(String ee_message, Object ee_t) {

		System.out.println("USERs LogTime:  " + ee_t + " " + ee_message);

	}

	// ============================================================================
	// Bridge: LogReal
	// ============================================================================
	public static void LogReal(String ee_message, float ee_r) {

		System.out.println("USERs LogReal:  " + ee_r + " " + ee_message);

	}

}
