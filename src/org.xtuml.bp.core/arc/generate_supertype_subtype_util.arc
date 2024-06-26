.//=======================================================================
.//
.// File:      $RCSfile: generate_supertype_subtype_util.arc,v $
.// Version:   $Revision: 1.7 $
.// Modified:  $Date: 2013/01/17 03:38:58 $
.//
.// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//=======================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not
.// use this file except in compliance with the License.  You may obtain a copy
.// of the License at
.//
.//      http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
.// License for the specific language governing permissions and limitations under
.// the License.
.//=======================================================================
.//
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.//
package org.xtuml.bp.core.util;
//========================================================================
//
// File: org.xtuml.bp.core/src/org/xtuml/bp/core/util/SupertypeSubtypeUtil.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.7 $$
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
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
import java.util.ArrayList;
import java.util.List;

import org.xtuml.bp.core.*;
import org.xtuml.bp.core.common.*;

/**
 *  This class holds a utility method to determine
 *  if a given child and parent are participating in
 *  a supertype/subtype association
 */
public class SupertypeSubtypeUtil {
 	public static boolean isSupertypeOf(NonRootModelElement child, NonRootModelElement parent) {
.select many subs from instances of R_SUB
.for each sub in subs
  .// select the child
  .select one child related by sub->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
  .select one parent related by sub->R_SUBSUP[R213]->R_SUPER[R212]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
  .invoke gn = get_class_name(child)
  .assign className = gn.body
  .invoke gnp = get_class_name(parent)
  .assign parentClassName = gnp.body
		if(child.getClass() == ${className}.class) {
			if(parent.getClass() == ${parentClassName}.class) {
				return true;
			}
		}
.end for
		return false;
	}

	public static List<NonRootModelElement> getSubtypes(NonRootModelElement supertype) {
		return getSubtypes(supertype, true);
	}	
	public static List<NonRootModelElement> getSubtypes(NonRootModelElement supertype, boolean load) {
  		List<NonRootModelElement> subtypes = new ArrayList<NonRootModelElement>();
.select many objects from instances of O_OBJ
.for each object in objects
  .invoke superObjName = get_class_name(object)
  .assign superClassName = superObjName.body
  .// select the supertype class
  .select many supers related by object->R_OIR[R201]->R_RTO[R203]->R_SUPER[R204]
  .if((cardinality supers) != 0)
  			if(supertype instanceof ${superClassName}) {
    .for each super in supers
      .select one association related by super->R_SUBSUP[R212]->R_REL[R206]
      .// select the subtype class
      .select many subtypeObjs related by super->R_SUBSUP[R212]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
      .for each subtypeObj in subtypeObjs
        .invoke subtypeObjName = get_class_name(subtypeObj)
        .assign subClassName = subtypeObjName.body
                ${subtypeObjName.body} subtype_${subClassName} = ${subtypeObjName.body}.getOne${subtypeObj.Key_Lett}OnR${association.Numb}((${superClassName}) supertype, load);
                if(subtype_${subClassName} != null) {
	  				subtypes.add(subtype_${subClassName});
	  			}
      .end for
    .end for
  			}
  .end if
.end for
  		return subtypes;
	}

	public static NonRootModelElement getSupertype(NonRootModelElement subtype) {
.select many objects from instances of O_OBJ
.for each object in objects
  .invoke subObjName = get_class_name(object)
  .assign subClassName = subObjName.body
  .// select the supertype class
  .select any sub related by object->R_OIR[R201]->R_RGO[R203]->R_SUB[R205]
  .if(not_empty sub)
  			if (subtype instanceof ${subClassName}) {
    .select one association related by sub->R_SUBSUP[R213]->R_REL[R206]
    .select one super related by sub->R_SUBSUP[R213]->R_SUPER[R212]->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
    .invoke supertypeObjName = get_class_name(super)
    .assign superClassName = supertypeObjName.body
            return ${superClassName}.getOne${super.Key_Lett}OnR${association.Numb}((${subClassName}) subtype);
  			}
  .end if
.end for
		return null;
	}	
}
.//
.emit to file "src/org/xtuml/bp/core/util/SupertypeSubtypeUtil.java"
.//
