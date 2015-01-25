.//=====================================================================
.//
.// File:      $RCSfile$
.// Version:   $Revision$
.// Modified:  $Date$
.//
.// (c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
.//
.//=====================================================================
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
.//=====================================================================
.//
.// 
.function is_supported_class
  .param String keylett
    .assign attr_result = false
    .if(keylett == "S_DT")
	  .assign attr_result = true
	.elif(keylett == "C_I")
	  .assign attr_result = true
	.elif(keylett == "C_C")
	  .assign attr_result = true
    .elif(keylett == "C_EP")
	  .assign attr_result = true
    .elif(keylett == "C_IR")
	  .assign attr_result = true
	.elif(keylett == "C_PO")
      .assign attr_result = true 
	.end if
.end function
.// this function generates one method for each RTO -> RGO
.// association, which is used to resolve RGOs for a pasted
.// RTO
.function generate_RGO_resolve_methods
  .param inst_ref class
    .// currently only data type support
    .// is available
	.invoke supported = is_supported_class(class.Key_Lett)
    .if(supported.result)
      .invoke rto_cn = get_class_name(class)
      .select many rtos related by class->R_OIR[R201]->R_RTO[R203]
      .for each rto in rtos
        .select one rel related by rto->R_OIR[R203]->R_REL[R201]
        .invoke result = get_rgo_objects_associated_with_rto(rto)
        .assign rgoObjects = result.rgoObjects
        .for each rgoObject in rgoObjects
          .invoke rgo_cn = get_class_name(rgoObject)
	public void Resolve$lr{rgoObject.Name}forR${rel.Numb}(NonRootModelElement proxy) {
		if(proxy != null && proxy.isProxy() && !this.isProxy()) {
			${rgo_cn.body} element = null;
      Object oldKey = CorePlugin.getImportedElementKeyMap().get(proxy);
	  		// remove proxy instance from list, so that it is not resolved
			// in the call below
			proxy.delete_unchecked();
			// find matching instance
			element = (${rgo_cn.body}) Ooaofooa.getDefaultInstance().getInstanceList(${rgo_cn.body}.class).getGlobal(oldKey);
			if(element != null) {
				boolean result = true;
				try {
					Method method = element.getClass().getMethod(
							"Isreferringtodefault$lr{class.Name}", new Class[]{});
					Object objectResult = method.invoke(element, new Object[]{});
					if (objectResult != null) {
						result = ((Boolean) objectResult).booleanValue();
					}
				} catch (NoSuchMethodException e) {
					// do nothing
				} catch (IllegalArgumentException e) {
					// do nothing
				} catch (IllegalAccessException e) {
					// do nothing
				} catch (InvocationTargetException e) {
					// do nothing
				}
				if (!result) {
					// if the element is not referring to the default
					// then do not hook up with pasted element
					return;
				}
				// relate the possible type
				${rto_cn.body} existingRTO = ${rto_cn.body}.getOne$U_{class.Key_Lett}OnR${rel.Numb}(element);
				element.unrelateAcrossR${rel.Numb}From(existingRTO);
				element.relateAcrossR${rel.Numb}To(this);
				// check that resolution is allowed
				try {
					Method method = element.getClass().getMethod("Canreferto$lr{class.Name}", new Class[] {});
					Object objectResult = method.invoke(element, new Object[] {});
			        if ( objectResult != null ) {
			        	result = ((Boolean) objectResult).booleanValue();
			        }
				} catch (NoSuchMethodException e) {
					// do nothing
				} catch (IllegalArgumentException e) {
					// do nothing
				} catch (IllegalAccessException e) {
					// do nothing
				} catch (InvocationTargetException e) {
					// do nothing
				}
				if(!result) {
					element.unrelateAcrossR${rel.Numb}From(this);
					element.relateAcrossR${rel.Numb}To(existingRTO);
				}
			}
		}
	}
	      .end for
  	  .end for
  	.end if
.end function
.function get_rgo_objects_associated_with_rto
  .param inst_ref rto
    .select many attr_rgoObjects from instances of O_OBJ where (selected.Key_Lett == "NONE")
    .select one rgoOBJ related by rto->R_PART[R204]->R_SIMP[R207]->R_FORM[R208]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201];
    .if(not_empty rgoOBJ)
      .assign attr_rgoObjects = attr_rgoObjects | rgoOBJ
    .else
      .select one rgoOBJ related by rto->R_AONE[R204]->R_ASSOC[R209]->R_ASSR[R211]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
      .if(not_empty rgoOBJ)
        .assign attr_rgoObjects = attr_rgoObjects | rgoOBJ
      .else
        .select one rgoOBJ related by rto->R_AOTH[R204]->R_ASSOC[R210]->R_ASSR[R211]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
        .if(not_empty rgoOBJ)
          .assign attr_rgoObjects = attr_rgoObjects | rgoOBJ
        .else
          .select many rgoOBJs related by rto->R_SUPER[R204]->R_SUBSUP[R212]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
          .for each rgoOBJ in rgoOBJs
            .assign attr_rgoObjects = attr_rgoObjects | rgoOBJ
          .end for
        .end if
      .end if
    .end if
.end function
