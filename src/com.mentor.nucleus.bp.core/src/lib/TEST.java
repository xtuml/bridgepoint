package lib;

import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.PendingEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;

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

public class TEST {


	// ========================================================================
	// Bridge: current_state
	// ========================================================================
	public static String current_state(Object event_inst) {
		if (event_inst instanceof PendingEvent_c) {
			PendingEvent_c pendingEvent = (PendingEvent_c) event_inst;
			StateMachineState_c curState = StateMachineState_c.
			                                getOneSM_STATEOnR2915(Instance_c.
			                                   getOneI_INSOnR2935(pendingEvent));
			if (curState != null) {
				return curState.getName();
			}
		}
		return "Error";
	}
}
