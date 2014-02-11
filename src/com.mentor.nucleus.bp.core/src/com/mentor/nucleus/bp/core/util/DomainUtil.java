//=====================================================================
//
//File:      $RCSfile: DomainUtil.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2012/01/23 21:27:40 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================
package com.mentor.nucleus.bp.core.util;

import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ExternalEntityInPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackageInDomain_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

/**
 * This utility class provides operations that allow a domain instance to be 
 * found based on a given model element.  Only a subset of MEs are supported.
 * As more are needed they can be added.  Notice that the supported MEs each 
 * have a rountine, getDomain(<ME Type>), and they are each listed in the 
 * generic getDomainFromUnknown(Object me) function as well.  This allows the 
 * lookup to be done without a cast when possible and makes it clear to user
 * that not all MEs are supported by this utility.
 *
 */
public class DomainUtil {

	public static Domain_c getDomainFromUknownME(Object me) {
		Domain_c dom = null;
		if (me instanceof Action_c) {
			dom = DomainUtil.getDomain((Action_c)me);
		} else if (me instanceof Attribute_c) {			
			dom = DomainUtil.getDomain((Attribute_c)me);
		} else if (me instanceof Bridge_c) {			
			dom = DomainUtil.getDomain((Bridge_c)me);
		} else if (me instanceof Function_c) {			
			dom = DomainUtil.getDomain((Function_c)me);
		} else if (me instanceof Operation_c) {			
			dom = DomainUtil.getDomain((Operation_c)me);
		} else if (me instanceof RequiredOperation_c) {			
			dom = DomainUtil.getDomain((RequiredOperation_c)me);
		} else if (me instanceof RequiredSignal_c) {			
			dom = DomainUtil.getDomain((RequiredSignal_c)me);
		} else if (me instanceof ProvidedOperation_c) {			
			dom = DomainUtil.getDomain((ProvidedOperation_c)me);
		} else if (me instanceof ProvidedSignal_c) {			
			dom = DomainUtil.getDomain((ProvidedSignal_c)me);
		} else if (me instanceof DerivedBaseAttribute_c) {			
			dom = DomainUtil.getDomain((DerivedBaseAttribute_c)me);
		}
		return dom;
	}
	
	public static Domain_c getDomain(Action_c me) {
		Domain_c dom = null;
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR517(StateMachine_c.getOneSM_SMOnR515(me));
		if (ism != null) {
			dom = Domain_c.getOneS_DOMOnR1(Subsystem_c
					.getManyS_SSsOnR2(ModelClass_c.getOneO_OBJOnR518(ism)));
		} else {
			ClassStateMachine_c csm = ClassStateMachine_c
					.getOneSM_ASMOnR517(StateMachine_c.getOneSM_SMOnR515(me));
			dom = Domain_c.getOneS_DOMOnR1(Subsystem_c
					.getManyS_SSsOnR2(ModelClass_c.getOneO_OBJOnR519(csm)));
		}
		return dom;
	}

	public static Domain_c getDomain(Attribute_c me) {
		return Domain_c.getOneS_DOMOnR1(Subsystem_c
				.getManyS_SSsOnR2(ModelClass_c.getManyO_OBJsOnR102(me)));
	}

	public static Domain_c getDomain(Bridge_c me) {
		return Domain_c.getOneS_DOMOnR300(ExternalEntityPackageInDomain_c
				.getOnePL_EEPIDOnR300(ExternalEntityPackage_c
						.getOneS_EEPKOnR33(ExternalEntityInPackage_c
								.getOneS_EEIPOnR33(ExternalEntity_c
										.getOneS_EEOnR19(me)))));
	}

	public static Domain_c getDomain(DerivedBaseAttribute_c me) {
		return Domain_c.getOneS_DOMOnR1(Subsystem_c
				.getManyS_SSsOnR2(ModelClass_c.getManyO_OBJsOnR102(Attribute_c
						.getManyO_ATTRsOnR106(BaseAttribute_c
								.getOneO_BATTROnR107(me)))));
	}

	public static Domain_c getDomain(Function_c me) {
		return Domain_c.getOneS_DOMOnR23(me);
	}

	public static Domain_c getDomain(Operation_c me) {
		return Domain_c.getOneS_DOMOnR1(Subsystem_c
				.getManyS_SSsOnR2(ModelClass_c.getOneO_OBJOnR115(me)));
	}

	public static Domain_c getDomain(RequiredOperation_c me) {
		return Domain_c
				.getOneS_DOMOnR4204(DomainAsComponent_c
						.getOneCN_DCOnR4204(Component_c
								.getOneC_COnR4010(Port_c
										.getOneC_POOnR4016(InterfaceReference_c
												.getOneC_IROnR4009(Requirement_c
														.getOneC_ROnR4500(RequiredExecutableProperty_c
																.getOneSPR_REPOnR4502(me)))))));
	}

	public static Domain_c getDomain(RequiredSignal_c me) {
		return Domain_c
				.getOneS_DOMOnR4204(DomainAsComponent_c
						.getOneCN_DCOnR4204(Component_c
								.getOneC_COnR4010(Port_c
										.getOneC_POOnR4016(InterfaceReference_c
												.getOneC_IROnR4009(Requirement_c
														.getOneC_ROnR4500(RequiredExecutableProperty_c
																.getOneSPR_REPOnR4502(me)))))));
	}

	public static Domain_c getDomain(ProvidedOperation_c me) {
		return Domain_c
				.getOneS_DOMOnR4204(DomainAsComponent_c
						.getOneCN_DCOnR4204(Component_c
								.getOneC_COnR4010(Port_c
										.getOneC_POOnR4016(InterfaceReference_c
												.getOneC_IROnR4009(Provision_c
														.getOneC_POnR4501(ProvidedExecutableProperty_c
																.getOneSPR_PEPOnR4503(me)))))));
	}

	public static Domain_c getDomain(ProvidedSignal_c me) {
		return Domain_c
				.getOneS_DOMOnR4204(DomainAsComponent_c
						.getOneCN_DCOnR4204(Component_c
								.getOneC_COnR4010(Port_c
										.getOneC_POOnR4016(InterfaceReference_c
												.getOneC_IROnR4009(Provision_c
														.getOneC_POnR4501(ProvidedExecutableProperty_c
																.getOneSPR_PEPOnR4503(me)))))));
	}

}
