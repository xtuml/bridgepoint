//=====================================================================
//
//File:      $RCSfile: ContainerUtil.java,v $
//Version:   $Revision: 1.4 $
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
package org.xtuml.bp.core.util;

import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.common.NonRootModelElement;

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
public class ContainerUtil {

	public static NonRootModelElement getContainerFromUknownME(Object me) {
		NonRootModelElement nrme = null;
		if (me instanceof Action_c) {
			nrme = ContainerUtil.getContainer((Action_c)me);
		} else if (me instanceof Attribute_c) {			
			nrme = ContainerUtil.getContainer((Attribute_c)me);
		} else if (me instanceof Bridge_c) {			
			nrme = ContainerUtil.getContainer((Bridge_c)me);
		} else if (me instanceof Function_c) {			
			nrme = ContainerUtil.getContainer((Function_c)me);
		} else if (me instanceof Operation_c) {			
			nrme = ContainerUtil.getContainer((Operation_c)me);
		} else if (me instanceof RequiredOperation_c) {			
			nrme = ContainerUtil.getContainer((RequiredOperation_c)me);
		} else if (me instanceof RequiredSignal_c) {			
			nrme = ContainerUtil.getContainer((RequiredSignal_c)me);
		} else if (me instanceof ProvidedOperation_c) {			
			nrme = ContainerUtil.getContainer((ProvidedOperation_c)me);
		} else if (me instanceof ProvidedSignal_c) {			
			nrme = ContainerUtil.getContainer((ProvidedSignal_c)me);
		} else if (me instanceof DerivedBaseAttribute_c) {			
			nrme = ContainerUtil.getContainer((DerivedBaseAttribute_c)me);
		}
		return nrme;
	}
	
	public static NonRootModelElement getContainer(Action_c me) {
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR517(StateMachine_c.getOneSM_SMOnR515(me));
		ClassStateMachine_c csm = ClassStateMachine_c
		              .getOneSM_ASMOnR517(StateMachine_c.getOneSM_SMOnR515(me));

		Package_c pkg;
		ism = InstanceStateMachine_c
		              .getOneSM_ISMOnR517(StateMachine_c.getOneSM_SMOnR515(me));
        if (ism != null) {
	       pkg = Package_c.getOneEP_PKGOnR8000(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				                  ModelClass_c.getOneO_OBJOnR518(ism)));
        } else {
	       pkg = Package_c.getOneEP_PKGOnR8000(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				                  ModelClass_c.getOneO_OBJOnR519(csm)));
        }
        if (pkg != null) {
        	return pkg;
	    }
		Component_c comp;
        if (ism != null) {
	       comp = Component_c.getOneC_COnR8003(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				                  ModelClass_c.getOneO_OBJOnR518(ism)));
        } else {
	       comp = Component_c.getOneC_COnR8003(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				                  ModelClass_c.getOneO_OBJOnR519(csm)));
        }
        if (comp != null) {
        	return comp;
	    }
		return null;
	}

	public static NonRootModelElement getContainer(Attribute_c me) {

		Package_c pkg = Package_c.getOneEP_PKGOnR8000(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				                 ModelClass_c.getManyO_OBJsOnR102(me)));
        if (pkg != null) {
        	return pkg;
	    }
		Component_c comp = Component_c.getOneC_COnR8003(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				                 ModelClass_c.getManyO_OBJsOnR102(me)));
        if (comp != null) {
        	return comp;
	    }
		return null;
	}

	public static NonRootModelElement getContainer(Bridge_c me) {
		Package_c pkg = Package_c.getOneEP_PKGOnR8000(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				                 ExternalEntity_c.getOneS_EEOnR19(me)));
        if (pkg != null) {
        	return pkg;
	    }
		Component_c comp = Component_c.getOneC_COnR8003(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				                 ExternalEntity_c.getOneS_EEOnR19(me)));
        if (comp != null) {
        	return comp;
	    }
		return null;
	}

	public static NonRootModelElement getContainer(DerivedBaseAttribute_c me) {
		Package_c pkg = Package_c.getOneEP_PKGOnR8000(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				   ModelClass_c.getManyO_OBJsOnR102(Attribute_c
	    							.getManyO_ATTRsOnR106(BaseAttribute_c
	    									       .getOneO_BATTROnR107(me)))));
        if (pkg != null) {
        	return pkg;
	    }
		Component_c comp = Component_c.getOneC_COnR8003(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				   ModelClass_c.getManyO_OBJsOnR102(Attribute_c
	    							.getManyO_ATTRsOnR106(BaseAttribute_c
	    									       .getOneO_BATTROnR107(me)))));
        if (comp != null) {
        	return comp;
	    }
		return null;
	}

	public static NonRootModelElement getContainer(Function_c me) {
		Package_c pkg = Package_c.getOneEP_PKGOnR8000(
	    		   PackageableElement_c.getOnePE_PEOnR8001(me));
        if (pkg != null) {
        	return pkg;
	    }
		Component_c comp = Component_c.getOneC_COnR8003(
	    		   PackageableElement_c.getOnePE_PEOnR8001(me));
        if (comp != null) {
        	return comp;
	    }
		return null;
	}

	public static NonRootModelElement getContainer(Operation_c me) {
		Package_c pkg = Package_c.getOneEP_PKGOnR8000(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				                   ModelClass_c.getOneO_OBJOnR115(me)));
        if (pkg != null) {
        	return pkg;
	    }
		Component_c comp = Component_c.getOneC_COnR8003(
	    		   PackageableElement_c.getOnePE_PEOnR8001(
	    				                   ModelClass_c.getOneO_OBJOnR115(me)));
        if (comp != null) {
        	return comp;
	    }
		return null;
	}

	public static NonRootModelElement getContainer(RequiredOperation_c me) {
		Component_c comp = Component_c
		.getOneC_COnR4010(Port_c
				.getOneC_POOnR4016(InterfaceReference_c
						.getOneC_IROnR4009(Requirement_c
								.getOneC_ROnR4500(RequiredExecutableProperty_c
										          .getOneSPR_REPOnR4502(me)))));
        if (comp != null) {
        	return comp;
	    }
		return null;
	}

	public static NonRootModelElement getContainer(RequiredSignal_c me) {
		Component_c comp = Component_c
		.getOneC_COnR4010(Port_c
				.getOneC_POOnR4016(InterfaceReference_c
						.getOneC_IROnR4009(Requirement_c
								.getOneC_ROnR4500(RequiredExecutableProperty_c
										          .getOneSPR_REPOnR4502(me)))));
        if (comp != null) {
        	return comp;
	    }
		return null;
	}

	public static NonRootModelElement getContainer(ProvidedOperation_c me) {
		Component_c comp = Component_c
		.getOneC_COnR4010(Port_c
				.getOneC_POOnR4016(InterfaceReference_c
						.getOneC_IROnR4009(Provision_c
								.getOneC_POnR4501(ProvidedExecutableProperty_c
										          .getOneSPR_PEPOnR4503(me)))));
        if (comp != null) {
        	return comp;
	    }
		return null;
	}

	public static NonRootModelElement getContainer(ProvidedSignal_c me) {
		Component_c comp = Component_c
		.getOneC_COnR4010(Port_c
				.getOneC_POOnR4016(InterfaceReference_c
						.getOneC_IROnR4009(Provision_c
								.getOneC_POnR4501(ProvidedExecutableProperty_c
										          .getOneSPR_PEPOnR4503(me)))));
        if (comp != null) {
        	return comp;
	    }
		return null;
	}

}
