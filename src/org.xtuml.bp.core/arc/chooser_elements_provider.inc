.//=====================================================================
.//
.// File:      $RCSfile: chooser_elements_provider.inc,v $
.// Version:   $Revision: 1.5 $
.// Modified:  $Date: 2013/01/10 22:54:05 $
.//
.// (c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
.// The function declared below is responsible for creating the body
.// of a method that delivers the list of elemenst to be presented in a chooser.
.//
.//
.function generate_chooser_element_function
  .param inst_ref function
  .select one wfl related by function->W_WOF[R2001]
  .select one cme related by wfl->CME[R2012]
  .print "Generating chooser element provider for ${cme}"
  .if (not_empty wfl)
      .assign sel_decl = "IStructuredSelection"
      .assign iteration_req = false
	  .select any class from instances of O_OBJ where selected.Obj_id == 0;
	  .assign elemClass = class
      .select many context_items related by wfl->W_CTI[R2003]
	  .assign firstContextItemName = ""
	  .for each context_item in context_items
	    .if(first context_items)
          .select one elemClass related by context_item->O_OBJ[R2002]
		  .assign firstContextItemName = context_item.Name
		.elif (last context_items)
          .select one class related by context_item->O_OBJ[R2002]
		.end if
	  .end for
      .invoke cn = get_class_name(class)
	  .invoke contextClName = get_class_name(elemClass)
	  .assign class_name = "${class.Name}"
public static ${cn.body}[] getElements(${contextClName.body} v_${firstContextItemName}) {
      List<${cn.body}> elementList = new ArrayList<${cn.body}>();
	  .invoke traversal = get_pe_traversal(elemClass.Key_Lett)
	  .assign peTraversal = traversal.result
	  .assign secondaryPETraversal = traversal.result2
	  .print "${class.Key_Lett} peTraversal: ${peTraversal}"
	  .print "elemClass: ${elemClass.Key_Lett}"
	  .if (peTraversal != "")
      PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(${peTraversal});
	    .if(secondaryPETraversal != "")
	  if(pe == null) {
	  		pe = PackageableElement_c.getOnePE_PEOnR8001(${secondaryPETraversal});
	  }
	    .end if
      Package_c pkg = Package_c.getOneEP_PKGOnR8000(pe);
      Component_c comp = Component_c.getOneC_COnR8003(pe);
	    .if(not cme.EnableVisibilityFiltering)
	  boolean isInGenericPackage = ((pkg !=null) || (comp !=null));
      if (!isInGenericPackage) {
	    .end if
	  .end if
        Ooaofooa[] instancesUnderSystem = Ooaofooa.getInstancesUnderSystem(v_${firstContextItemName}.getModelRoot());
        for(int i = 0; i < instancesUnderSystem.length; i++) {
    	  ${cn.body}[] elementInstances = ${cn.body}.$r{class_name}Instances(instancesUnderSystem[i]);
    	  for(int j = 0; j < elementInstances.length; j++) {
		  	${cn.body} elem = elementInstances[j];
    	    .// TODO: generate code from the second where clause
			.invoke precondition = get_precondition(class.Key_Lett, cme.EnableVisibilityFiltering, elemClass.Key_Lett)
			${precondition.preamble}
    			  elementList.add(elem);
			${precondition.postamble}
    	  }
        }
		SystemModel_c sys = ((Ooaofooa)v_${firstContextItemName}.getModelRoot()).getRoot();
		if (sys.getUseglobals()) {
		  PackageableElement_c[] pes = PackageableElement_c.
		             getManyPE_PEsOnR9100(GlobalElementInSystem_c.
				                                     getManyG_EISsOnR9100(sys));
          ${cn.body}[] elementInstances = ${cn.body}.getMany${class.Key_Lett}sOnR8001(pes);
  	      for(int j = 0; j < elementInstances.length; j++) {
		  	${cn.body} elem = elementInstances[j];
			if (!elementList.contains(elem)) {
			  // add elements once only
    		  elementList.add(elem);
		    }
  	      }
		}
      .if ((peTraversal != "") and (not cme.EnableVisibilityFiltering))
        .invoke typeName = get_ooa_type_name(class.Key_Lett)
		.assign tn = typeName.result
		.assign globalEnable = "false"
		.if (tn != "CLASS")
		  .assign globalEnable = "true"
		.end if
	  }
      else {
	  	PackageableElement_c[] pes = null;
        if (pkg != null) {
          pkg.Clearscope();
          pkg.Collectvisibleelementsforname(${globalEnable}, Gd_c.Null_unique_id(), false,
                      "", pkg.getPackage_id(), Elementtypeconstants_c.${tn});
		  class PETest implements ClassQueryInterface_c {
				public boolean evaluate(Object candidate) {
					SearchResultSet_c selected = (SearchResultSet_c) candidate;
					return (selected.getName().equals("") &&
                         selected.getType() == Elementtypeconstants_c.${tn});
				}
		  }
		  SearchResultSet_c results =
			           SearchResultSet_c.getOnePE_SRSOnR8005(pkg, new PETest());
          pes = PackageableElement_c.getManyPE_PEsOnR8002(
		                    ElementVisibility_c.getManyPE_VISsOnR8006(results));
        }
        else {
          if (comp != null) {
            comp.Clearscope();
            comp.Collectvisibleelementsforname(${globalEnable}, Gd_c.Null_unique_id(), "",
            		             comp.getId(), Elementtypeconstants_c.${tn});
  		    class PETest implements ClassQueryInterface_c {
				public boolean evaluate(Object candidate) {
					ComponentResultSet_c selected = (ComponentResultSet_c) candidate;
					return (selected.getName().equals("") &&
                       selected.getType() == Elementtypeconstants_c.${tn});
				}
		    }
		    ComponentResultSet_c results =
			       ComponentResultSet_c.getOnePE_CRSOnR8007(comp, new PETest());
            pes = PackageableElement_c.getManyPE_PEsOnR8004(
                          ComponentVisibility_c.getManyPE_CVSsOnR8008(results));
          }
        }
        for (int i = 0; pes != null && i < pes.length; i++) {
		    .invoke from_traversal = get_from_pe_traversal(class.Key_Lett)
          ${from_traversal.result}
			.invoke precondition = get_precondition(class.Key_Lett, cme.EnableVisibilityFiltering, elemClass.Key_Lett)
			${precondition.preamble}
            elementList.add(elem);
			${precondition.postamble}
        }
      }
	  .else
		if (Pref_c.Getsystemboolean(
				"com.mentor.nucleus.bp.ui.project.references", sys.getName()) == true) {
			SystemModel_c[] systems = SystemModel_c.SystemModelInstances(Ooaofooa.getDefaultInstance());
			for (int i = 0; i < systems.length; i++) {
				if (systems[i] != sys && systems[i].getUseglobals()) {
					instancesUnderSystem = Ooaofooa.getInstancesUnderSystem(systems[i].getName());
					for (int j = 0; j < instancesUnderSystem.length; j++) {
						${cn.body}[] elementInstances = ${cn.body}.$r{class_name}Instances(instancesUnderSystem[j]);
						for (int k = 0; k < elementInstances.length; k++) {
							${cn.body} elem = elementInstances[k];
							if (instancesUnderSystem[j].getInstanceList(
									ComponentPackage_c.class).isEmpty()) {
								elementList.add(elem);
							}
						}
					}
				}
			}
		}
	  .end if
	return elementList.toArray(new ${cn.body}[elementList.size()]);
  } // end getElements(${contextClName.body}) 
	.end if
.end function
