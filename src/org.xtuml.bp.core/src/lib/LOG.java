//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 

package lib;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.xtuml.bp.core.CorePlugin;

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

		CorePlugin.err.println("LogFailure:  " + ee_message);

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
	  String objString = null;
		if (ee_d instanceof Instant) {
		  objString = LocalDateTime.ofInstant( (Instant)ee_d, ZoneId.systemDefault() ).format(DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss zzz"));
			CorePlugin.out.println("LogDate:  " + objString + " " + ee_message);
		}
	}

	// ============================================================================
	// Bridge: LogTime
	// ============================================================================
	public static void LogTime(String ee_message, long ee_t) {
		CorePlugin.out.println("LogTime:  " + ee_t + " " + ee_message);
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
