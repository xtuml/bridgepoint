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
