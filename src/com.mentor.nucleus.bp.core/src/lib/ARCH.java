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

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Runstatetype_c;
import com.mentor.nucleus.bp.core.Stack_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.util.OoaofooaUtil;

public class ARCH {

	// ========================================================================
	// Bridge: Shutdown
	// ========================================================================
	public static void shutdown() {
		
		ModelRoot modelRoot = null;
		ModelRoot prev_modelRoot = null;
		Ooaofooa[] roots = Ooaofooa.getInstances();
		for (int i = 0; i < roots.length; i++) {
			ComponentInstance_c ee = ComponentInstance_c
					.ComponentInstanceInstance(roots[i]);
			if (ee != null) {
				synchronized (ee) {
					Stack_c[] stacks = Stack_c.getManyI_STACKsOnR2930(ee);
					for (int j = 0; j < stacks.length; j++) {
						stacks[j].setRunstate(Runstatetype_c.Terminated);
					}
					ee.notify();
					modelRoot = ee.getModelRoot();
					if (prev_modelRoot != modelRoot){
					TIM.terminate(ee);
					}
					prev_modelRoot = modelRoot;
				}
				// Signal single execution thread if needed.
				SystemModel_c system = OoaofooaUtil.getSystemForElement(ee);
				if (system != null) {
				  synchronized (system) {
					system.notify();
				  }
				}
			}
		}
	}
}
