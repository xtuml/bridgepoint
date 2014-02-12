.//=====================================================================
.//
.// File:      $RCSfile: create_editorinput_factories_java.arc,v $
.// Version:   $Revision: 1.10 $
.// Modified:  $Date: 2013/01/17 03:28:52 $
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
.// This archetype is responsible for creating the EditorInput.java
.// file for com.mentor.nucleus.bp.ui.text.
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.include "arc/create_util.inc"
.//
.invoke chain_util_env = GET_ENV_VAR( "CHAIN_UTIL_DIR" )
.assign chain_util = chain_util_env.result
.if ( chain_util == "" )
  .print "\nERROR: Environment variable CHAIN_UTIL_DIR not set."
  .exit 100
.end if
.include "${chain_util}/arc/parse_chain.inc"
.//
.//
.function generate_forward_chains
    .param inst_ref_set classes
    .//
    .//
    .for each model_class in classes
        .select any nav_chain from instances of NAV_OBJ where (selected.Src_Key_Lett == model_class.key_lett)
        .//
	    .invoke result = get_class_name(model_class)
	    .assign classname = result.body
	    .//
	    .if ( first classes )
	if (modelElement instanceof ${classname}){
	    .else
	}else if (modelElement instanceof ${classname}){
	    .end if
	    .//
		.assign tgt_var_name = "source"
		.assign rel_nav_body = ""
	    .select many nav_chains from instances of NAV_OBJ where (selected.Src_Key_Lett == model_class.key_lett)
	    .for each nav_chain in nav_chains
	        .select any target_model_class from instances of O_OBJ where (selected.key_lett == nav_chain.tgt_key_lett)
	        .assign tgt_var_name = "var$Cr{target_model_class.Name}"
	        .invoke rel_nav = generate_forward_rel_chain_nav(nav_chain.Nav_Tgt_To_Src, nav_chain.tgt_key_lett, classname, "modelElement", tgt_var_name ) 
        ${rel_nav.body}
        if (${tgt_var_name} != null){
            return ${tgt_var_name};
        }
	    .end for
    .end for
    }else if(getSupportedModelElementList().contains(modelElement.getClass().getName())){
	       return modelElement;
	}
.end function
.//
.function generate_backward_chains
    .param inst_ref_set classes
    .//
    .//
    .assign first_element = true
    .for each model_class in classes
        .select many nav_chains from instances of NAV_OBJ where (selected.Src_Key_Lett == model_class.key_lett)
        .for each nav_chain in nav_chains
            .select any target_model_class from instances of O_OBJ where (selected.key_lett == nav_chain.tgt_key_lett)
	        .//
		    .invoke result = get_class_name(target_model_class)
		    .assign classname = result.body
		    .//
		    .if ( first_element )
	if (modelElement instanceof ${classname}){
	            .assign first_element = false
		    .else
	}else if (modelElement instanceof ${classname}){
		    .end if
		    .//
		    .invoke rel_nav = generate_backward_rel_chain_nav( target_model_class, nav_chain.Nav_Tgt_To_Src, "one", "modelElement", "source" ) 
            ${rel_nav.body}
            if (source != null){
                return source;
	        }
	        .//
        .end for
    .end for
    }else if(getSupportedModelElementList().contains(modelElement.getClass().getName())){
	       return modelElement;
	}
.end function
.//
.//
.// ------------------------------------------------------------
.// Main code
.// -------------------------------------------------------------
.//
.//
.invoke result = get_editor_obj_set()
.assign oal_object_set = result.oal_obj_set
.assign dsc_object_set = result.dsc_obj_set
.assign oal_source_set = result.oal_source_set
.assign dsc_source_set = result.dsc_source_set
.include "arc/create_activity_editorinput_factory_java.inc"  
.include "arc/create_description_editorinput_factory_java.inc"  
.//