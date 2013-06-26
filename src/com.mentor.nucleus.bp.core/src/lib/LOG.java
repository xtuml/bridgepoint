//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package lib;

import java.util.UUID;

import com.mentor.nucleus.bp.core.CorePlugin;

public class LOG {

	// ========================================================================
	// Bridge: LogInteger
	// ========================================================================
	public static void LogInteger(int ee_message) {

		CorePlugin.out.println("LogInteger:  " + ee_message);

	}

	// ============================================================================
	// Bridge: LogSuccess
	// ============================================================================
	public static void LogSuccess(String ee_message) {

		CorePlugin.out.println("LogSuccess:  " + ee_message);

	}

	// ============================================================================
	// Bridge: LogFailure
	// ============================================================================
	public static void LogFailure(String ee_message) {

		CorePlugin.out.println("LogFailure:  " + ee_message);

	}

	// ============================================================================
	// Bridge: LogInfo
	// ============================================================================
	public static void LogInfo(String ee_message) {

		CorePlugin.out.println("LogInfo:  " + ee_message);

	}

	// ============================================================================
	// Bridge: LogDate
	// ============================================================================
	public static void LogDate(Object ee_d, String ee_message) {
		String objString = (ee_d == null) ? "<null>" : ee_d.toString();
		CorePlugin.out.println("LogDate:  " + objString + " "
				+ ee_message);
	}

	// ============================================================================
	// Bridge: LogTime
	// ============================================================================
	public static void LogTime(String ee_message, Object ee_t) {
		String objString = (ee_t == null) ? "<null>" : ee_t.toString();

		CorePlugin.out.println("LogTime:  " + objString + " " + ee_message);
	}

	// ============================================================================
	// Bridge: LogReal
	// ============================================================================
	public static void LogReal(String ee_message, float ee_r) {

		CorePlugin.out.println("LogReal:  " + ee_r + " " + ee_message);

	}

	// ============================================================================
	// Bridge: LogUnique_ID 
	// ============================================================================
	public static void LogUnique_ID(String ee_message, UUID ee_id) {
		String objString = (ee_id == null) ? "<null>" : ee_id.toString();

		CorePlugin.out.println("LogUUID:  " + objString + " "
				+ ee_message);

	}
}
