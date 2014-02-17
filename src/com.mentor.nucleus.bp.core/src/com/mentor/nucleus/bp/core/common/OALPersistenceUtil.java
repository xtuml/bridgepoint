//========================================================================
//
//File:      $RCSfile: OALPersistenceUtil.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package com.mentor.nucleus.bp.core.common;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.BridgeBody_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.DerivedAttributeBody_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationBody_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.ProvidedOperationBody_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignalBody_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.RequiredOperationBody_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignalBody_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.StateActionBody_c;
import com.mentor.nucleus.bp.core.TransitionActionBody_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;

public class OALPersistenceUtil {

	
	/**
	 * This routine is used to detetmine if the specified
	 * ModelElement is an instance of a type that has an
	 * associated Body_c instance.  If it is, this routine
	 * returns that instance, if not it returns  null.
     *
	 */
	public static Body_c getOALModelElement(ModelElement me)
	{
		Body_c bdy = null;
		if (me instanceof Action_c) {
			bdy = Body_c.getOneACT_ACTOnR698(StateActionBody_c
					.getOneACT_SABOnR691((Action_c) me));
			if (bdy == null) {
				bdy = Body_c.getOneACT_ACTOnR698(TransitionActionBody_c
						.getOneACT_TABOnR688((Action_c) me));
			}
		} else if (me instanceof Function_c) {
			bdy = Body_c.getOneACT_ACTOnR698(FunctionBody_c
					.getOneACT_FNBOnR695((Function_c) me));
		} else if (me instanceof Bridge_c) {
			bdy = Body_c.getOneACT_ACTOnR698(BridgeBody_c
					.getOneACT_BRBOnR697((Bridge_c) me));
		} else if (me instanceof Operation_c) {
			bdy = Body_c.getOneACT_ACTOnR698(OperationBody_c
					.getOneACT_OPBOnR696((Operation_c) me));
		} else if (me instanceof DerivedBaseAttribute_c) {
			bdy = Body_c.getOneACT_ACTOnR698(DerivedAttributeBody_c
					.getOneACT_DABOnR693((DerivedBaseAttribute_c) me));
		} else if (me instanceof RequiredSignal_c) {
			bdy = Body_c.getOneACT_ACTOnR698(RequiredSignalBody_c
					.getOneACT_RSBOnR684((RequiredSignal_c) me));
		} else if (me instanceof RequiredOperation_c) {
			bdy = Body_c.getOneACT_ACTOnR698(RequiredOperationBody_c
					.getOneACT_ROBOnR685((RequiredOperation_c) me));
		} else if (me instanceof ProvidedSignal_c) {
			bdy = Body_c.getOneACT_ACTOnR698(ProvidedSignalBody_c
					.getOneACT_PSBOnR686((ProvidedSignal_c) me));
		} else if (me instanceof ProvidedOperation_c) {
			bdy = Body_c.getOneACT_ACTOnR698(ProvidedOperationBody_c
					.getOneACT_POBOnR687((ProvidedOperation_c) me));
		} else if (me instanceof Transition_c) {
		    bdy = Body_c.getOneACT_ACTOnR698(TransitionActionBody_c
				.getOneACT_TABOnR688(Action_c.getOneSM_ACTOnR514(
						ActionHome_c.getOneSM_AHOnR513(
								TransitionActionHome_c.getOneSM_TAHOnR530(
										                  (Transition_c)me)))));
	    }
		return bdy;
	}
	
	public static void persistOAL(final Ooaofooa modelRoot) {
		persistOAL( Action_c.ActionInstances(modelRoot) );
		persistOAL( Function_c.FunctionInstances(modelRoot) );
		persistOAL( Bridge_c.BridgeInstances(modelRoot) );
		persistOAL( Operation_c.OperationInstances(modelRoot) );
		persistOAL( DerivedBaseAttribute_c.DerivedBaseAttributeInstances(modelRoot) );
		persistOAL( RequiredSignal_c.RequiredSignalInstances(modelRoot) );
		persistOAL( RequiredOperation_c.RequiredOperationInstances(modelRoot) );
		persistOAL( ProvidedSignal_c.ProvidedSignalInstances(modelRoot) );
		persistOAL( ProvidedOperation_c.ProvidedOperationInstances(modelRoot) );
	}
	
	private static void persistOAL(ModelElement[] mes) {
		if (mes != null) {
		    for(int i = 0; i < mes.length; i++) {
		    	Body_c bdy = getOALModelElement(mes[i]);
		    	if (bdy != null) {
		    		bdy.Initialize();
		    	}
		    }
		}
	}
};
