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
