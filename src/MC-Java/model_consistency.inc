.//====================================================================
.//
.// File:      $RCSfile: model_consistency.inc,v $
.// Version:   $Revision: 1.19 $
.// Modified:  $Date: 2013/01/10 23:16:42 $
.//
.// (c) Copyright 2005-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//====================================================================
.//
.// This archetype generates the methods used to provide model
.// consistency checks
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.include "${mc_archetypes}/model_consistency_functions.inc"
.function gen_dom_consistency_check
  .param frag_ref package
  //
  public void checkDomainConsistency()  
  {
    if (Boolean.valueOf(System.getenv("PTC_MCC_ENABLED")) == false) {  //$$NON-NLS-1$$
      return;
    }
    .if (package.ui_root_class_name != "" )
  	setRoot( null );
    .end if
    .select many class_set from instances of O_OBJ;
    .for each class in class_set
      .if ("${class.Descrip:PEI}" != "true")
        .invoke cn = get_class_name(class)
    ${cn.body}.checkClassConsistency(this);
      .end if
    .end for
  }
.end function
.function gen_class_consistency_checks
  .param Inst_Ref object
  .param String dom_name
  .param frag_ref package
  public static void checkClassConsistency (ModelRoot modelRoot) {
  .if ( package.is_eclipse_plugin )
    Ooaofooa.log.println(
	  ILogger.OPERATION,
	  "${object.Name}", //$$NON-NLS-1$$
  .else
    System.out.println(
  .end if
	  " Operation entered: ${object.Name}::checkClassConsistency"); //$$NON-NLS-1$$
     if (Boolean.valueOf(System.getenv("PTC_MCC_ENABLED")) == false) {  //$$NON-NLS-1$$
       return;
     }
     $cr{object.Name}_c [] objs = $cr{object.Name}_c.$cr{object.Name}Instances(modelRoot,null,false); 
     
     for ( int i = 0; i < objs.length; i++) {
       objs[i].checkConsistency();
     }  
  } 
 .// End checkClassConsistency
 .//
 .// Start consistency check
  public boolean checkConsistency () {
  .if ( package.is_eclipse_plugin )
    Ooaofooa.log.println(
	  ILogger.OPERATION,
	  "${object.Name}", //$$NON-NLS-1$$
  .else
    System.out.println(
  .end if
	  " Operation entered: ${object.Name}::checkConsistency");  //$$NON-NLS-1$$
    if (Boolean.valueOf(System.getenv("PTC_MCC_ENABLED")) == false) {  //$$NON-NLS-1$$
      return true;
    }
	ModelRoot modelRoot = getModelRoot();
    boolean      retval = true;
      .if ( dom_name == "Ooaofgraphics")
        .if ( object.Key_Lett == "GD_GE" )
      // Check for GD <-> OOA metamodel consistency
          .assign error_message = "Consistency: GD_GE.represents attribute is empty"
          .assign identifier_string = ""
          .invoke gd_ge_error = create_test_class_error(error_message, "" ,identifier_string, package, object)
    if ( getRepresents() == null){
${gd_ge_error.body}
	}
        .end if
      .end if
      .select many IDs related by object->O_ID[R104]
      .for each ID in IDs
        .assign obarraynum = "${info.unique_num}"
        .select many id_attrs related by ID->O_OIDA[R105]->O_ATTR[R105]
        .if (not_empty id_attrs)
          .assign is_referring = false
          .invoke class_body = create_test_class(object, id_attrs, is_referring)
          .invoke string_params = create_test_class_print_params(id_attrs)
          .invoke id_test_select = create_test_class_select(object, class_body, class_body, obarraynum.body, package.application_root_class)
          .assign zero_error_message = " Cardinality of an identifier is zero. "
          .invoke id_test_zero = create_test_class_test(object, obarraynum.body, "zero", zero_error_message.body, "", "array", package)
          .assign gt_one_error_message = " Cardinality of an identifier is greater than 1. "
          .invoke id_test_gt_one = create_test_class_test(object, obarraynum.body, "greater_than_one", gt_one_error_message.body, string_params.body, "array", package)
${class_body.body}
${id_test_select.body}
${id_test_zero.body}
${id_test_gt_one.body}
        .end if
      .end for
      .select many rgos related by object->O_ATTR[R102]->O_RATTR[R106]->O_REF[R108]->R_RGO[R111]
      .for each rgo in rgos
        .select many ref_attrs related by rgo->O_REF[R111]->O_RATTR[R108]->O_ATTR[R106]
        .select one form related by rgo->R_FORM[R205]
        .select one assr related by rgo->R_ASSR[R205]
        .select one subt related by rgo->R_SUB[R205]
        .if (not_empty form)
          .select one rel related by form->R_SIMP[R208]->R_REL[R206]
          .if (not_empty rel)
          // ${object.Name} is a referring class in association: rel.Numb = ${rel.Numb}
          .end if  .// not empty rel
          .select one participant related by form->R_SIMP[R208]->R_PART[R207]
          .select one part_obj related by participant->R_RTO[R204]->O_ID[R109]->O_OBJ[R104]
          .if (not_empty part_obj)
            .if (not_empty ref_attrs)
              .// determine if we need a selector, e.g. it is unconditional or the multiplicity is one
              .if ((participant.Cond == 0) or (participant.Mult == 0))
                .assign oparraynum = "${info.unique_num}"
                .assign is_referring = true
                .invoke op_class_body = create_test_class(part_obj, ref_attrs, is_referring)
                .invoke string_params = create_test_class_print_params(ref_attrs)
                .invoke op_test_select = create_test_class_select(part_obj, op_class_body, op_class_body, oparraynum.body, package.application_root_class)
                .assign zero_error_message = " Association: ${rel.Numb}: Cardinality of a participant is 0. "
                .invoke id_test_zero = create_test_class_test(object, oparraynum.body, "zero", zero_error_message.body, "", "array", package)
                .assign ne_one_error_message = " Association: ${rel.Numb}: Cardinality of a participant is not equal to 1. "
                .invoke id_test_ne_one = create_test_class_test(object, oparraynum.body, "not_equal_one", ne_one_error_message.body, string_params.body, "array", package)
          // The participating class is: ${part_obj.Name}
                .assign deprecated = "${part_obj.Descrip:Deprecated}"
                .if ("$lr{deprecated}" == "true") 
		  // This class is deprecated, no consistency check generated

		        .else
${op_class_body.body}
${op_test_select.body}
                  .if (participant.Cond == 0) .// it is unconditional
          // The participant is unconditional
                    .if (participant.Mult == 0 )
          // The multiplicity of the participant is one
${id_test_ne_one.body}
                    .else
${id_test_zero.body}
                    .end if .//participant multiplicity is 1
                  .elif (participant.Mult == 0 ) .// conditional but multiplicity is one
                    .assign gt_one_error_message = " Association: ${rel.Numb}: Cardinality of a participant is greater than 1. "
                    .invoke id_test_gt_one = create_test_class_test(object, oparraynum.body, "greater_than_one", gt_one_error_message.body, string_params.body, "array", package)
${id_test_gt_one.body}                
                  .end if .// participant is unconditional
                .end if .// participant is not deprecated
              .end if .// a selector is needed
            .end if  .// not empty ref_attrs
          .end if  .// not empty part_obj
        .elif (not_empty assr)  .//  not a simple, is it assoc?
          .select one rel related by assr->R_ASSOC[R211]->R_REL[R206]
          .if (not_empty rel)
          // ${object.Name} is a link class in association: rel.Numb = ${rel.Numb}
          .end if
          .select one one_assoc related by assr->R_ASSOC[R211]->R_AONE[R209]
          .select one aone_id related by one_assoc->R_RTO[R204]->O_ID[R109]
          .select one aone_obj related by aone_id->O_OBJ[R104]
          .if (not_empty aone_id)
            .select many on_attrs related by one_assoc->R_RTO[R204]->O_RTIDA[R110]->O_REF[R111]->O_RATTR[R108]->O_ATTR[R106]
            .if (not_empty on_attrs)
              .// determine if we need a selector, e.g. it is unconditional or the multiplicity is one
              .if ( (one_assoc.Cond == 0 ) or (one_assoc.Mult == 0))
                .assign onarraynum = "${info.unique_num}"
                .assign is_referring = true
                .invoke on_class_body = create_test_class(aone_obj, on_attrs, is_referring)
                .invoke on_test_select = create_test_class_select(aone_obj, on_class_body, on_class_body, onarraynum.body, package.application_root_class)
                .assign zero_error_message = " Association: ${rel.Numb}: Cardinality of unconditional one side of link is equal to zero. "
                .invoke id_test_zero = create_test_class_test(object, onarraynum.body, "zero", zero_error_message.body, "", "array", package)
                .assign ne_one_error_message = " Association: ${rel.Numb}: Cardinality of one side of link is not equal to 1. "
                .invoke id_test_ne_one = create_test_class_test(object, onarraynum.body, "not_equal_one", ne_one_error_message.body, "", "array", package)
          // The one side class in the association is: ${aone_obj.Name}
                .assign deprecated = "${aone_obj.Descrip:Deprecated}"
                .if ("$lr{deprecated}" == "true") 
		  // This class is deprecated, no consistency check generated

		        .else
${on_class_body.body}
${on_test_select.body}
                  .if (one_assoc.Cond == 0 ) .// it is unconditional
          // The one side class is unconditional
                    .if (one_assoc.Mult == 0) ) .// Is unconditional and multiplicity is 1 
          // The multiplicity of the one side class is one
${id_test_ne_one.body}
                    .else
${id_test_zero.body}
                    .end if .// multiplicity is one
                  .elif (one_assoc.Mult == 0) .// conditional but multiplicity is one
                    .assign gt_one_error_message = " Association: ${rel.Numb}: Cardinality of one side of link is greater than 1. "
                    .invoke id_test_gt_one = create_test_class_test(object, onarraynum.body, "greater_than_one", gt_one_error_message.body, "", "array", package)
${id_test_gt_one.body}
                  .end if .// is unconditional
                .end if .// aone_obj is not deprecated
              .end if .// a selector is needed
            .end if  .// not empty on_attrs
          .end if .// if not empty one side identifier
          .select one oth_assoc related by assr->R_ASSOC[R211]->R_AOTH[R210]
          .select one aoth_id related by oth_assoc->R_RTO[R204]->O_ID[R109]
          .select one aoth_obj related by aoth_id->O_OBJ[R104]
          .if (not_empty aoth_id)
            // Other side
            .select many ot_attrs related by oth_assoc->R_RTO[R204]->O_RTIDA[R110]->O_REF[R111]->O_RATTR[R108]->O_ATTR[R106]
            .if (not_empty ot_attrs)
              .// determine if we need a selector, e.g. it is unconditional or the multiplicity is one
              .if ((oth_assoc.Cond == 0 ) or (oth_assoc.Mult == 0))
                .assign otarraynum = "${info.unique_num}"
                .assign is_referring = true
                .invoke ot_class_body = create_test_class(aoth_obj, ot_attrs, is_referring)
                .invoke ot_test_select = create_test_class_select(aoth_obj, ot_class_body, ot_class_body, otarraynum.body, package.application_root_class)
                .assign zero_error_message = " Association: ${rel.Numb}: Cardinality of unconditional other side of link is equal to zero. "
                .invoke id_test_zero = create_test_class_test(object, otarraynum.body, "zero", zero_error_message.body, "", "array", package)
                .assign ne_one_error_message = " Association: ${rel.Numb}: Cardinality of other side of link is not equal to 1. "
                .invoke id_test_ne_one = create_test_class_test(object, otarraynum.body, "not_equal_one", ne_one_error_message.body, "", "array", package)
          // The other side class in the association is: ${aoth_obj.Name}
                .assign deprecated = "${aoth_obj.Descrip:Deprecated}"
                .if ("$lr{deprecated}" == "true") 
		  // This class is deprecated, no consistency check generated

		        .else
${ot_class_body.body}
${ot_test_select.body}
                  .if (oth_assoc.Cond == 0 ) .// it is unconditional
          // The other side class is unconditional
                    .if (oth_assoc.Mult == 0)  .// Is unconditional and multiplicity is 1
          // The multiplicity of the other side class is one
${id_test_ne_one.body}
                    .else
${id_test_zero.body}
                    .end if .// multiplicity is 1
                  .elif (oth_assoc.Mult == 0)  .// conditional but multiplicity is 1
                    .assign gt_one_error_message = " Association: ${rel.Numb}: Cardinality of other side of link is greater than 1. "
                    .invoke id_test_gt_one = create_test_class_test(object, otarraynum.body, "greater_than_one", gt_one_error_message.body, "", "array", package)
${id_test_gt_one.body}                
                  .end if .// is unconditional
                .end if .// aoth_obj is not deprecated
              .end if .// a selector is needed
            .end if  .// not empty ot_attrs
          .end if .// not empty aoth_id
        .elif (not_empty subt) .// not simple, not assoc, is it super/sub?
          .select one rel related by subt->R_SUBSUP[R213]->R_REL[R206]
          .if (not_empty rel)
          // ${object.Name} is a subtype in association: rel.Numb = ${rel.Numb}
          .end if 
          .select one supt_obj related by subt->R_SUBSUP[R213]->R_SUPER[R212]->R_RTO[R204]->O_ID[R109]->O_OBJ[R104]
          .if (not_empty supt_obj) 
            .if (not_empty ref_attrs)
              .assign starraynum = "${info.unique_num}"
              .assign is_referring = true
              .invoke st_class_body = create_test_class(supt_obj, ref_attrs, is_referring)
              .invoke st_test_select = create_test_class_select(supt_obj, st_class_body, st_class_body, starraynum.body, package.application_root_class)
              .assign ne_one_error_message = " Association: ${rel.Numb}: Cardinality of a supertype is not equal to 1. "
              .invoke id_test_ne_one = create_test_class_test(object, starraynum.body, "not_equal_one", ne_one_error_message.body, "", "array", package)
          // The supertype class is: ${supt_obj.Name}
.// Exclude the test for supertype on R8001.
.if ( (rel.Numb == 8001) and (supt_obj.Name == "Packageable Element") )
         //    Note: The consistency test for this supertype has been removed 
         //          until generic package _migration_ is complete.
.else
${st_class_body.body}
${st_test_select.body}
${id_test_ne_one.body}
.end if
            .end if  .// not empty ref_attrs
          .end if .// not empty supt_obj
        .end if .// simple or assoc or super/sub
      .end for  .// each rgo in rgos
      .select many rtos related by object->O_ID[R104]->R_RTO[R109]
      .for each rto in rtos
        .select one part related by rto->R_PART[R204]
        .select one one_side related by rto->R_AONE[R204]
        .select one oth_side related by rto->R_AOTH[R204]
        .select one supt related by rto->R_SUPER[R204]
        .select many rto_part_attrs related by rto->O_ID[R109]->O_OIDA[R105]->O_ATTR[R105]
        .if (not_empty part)
          .select one rel related by part->R_SIMP[R207]->R_REL[R206]
          .if (not_empty rel)
          // ${object.Name} is a participating class in association: rel.Numb = ${rel.Numb}
          .end if .// not empty rel
          .select one formalizer related by part->R_SIMP[R207]->R_FORM[R208]
          .if (empty formalizer)
            .select many other_participants related by part->R_SIMP[R207]->R_PART[R207]
            .assign other_participants = other_participants - part
            .if ( not_empty other_participants )
          // Association is unformalized
            .end if .// not empty other participants
          .else
            .select any single_ref_attr related by part->R_SIMP[R207]->R_FORM[R208]->R_RGO[R205]->O_REF[R111]->O_RATTR[R108]->O_ATTR[R106]
            .select one ref_attr_obj related by single_ref_attr->O_OBJ[R102]
             // Object: ${ref_attr_obj.Name}
            .select many part_attrs related by rto->O_RTIDA[R110]->O_REF[R111]->O_RATTR[R108]->O_ATTR[R106]
            .if (not_empty part_attrs)
              .// determine if we need a selector, e.g. it is unconditional or the multiplicity is one
              .if ((formalizer.Cond == 0) or (formalizer.Mult == 0))
                .assign ptarraynum = "${info.unique_num}"
                .assign is_referring = false
                .invoke part_class_body = create_test_class(ref_attr_obj, part_attrs, is_referring)
                .invoke param_body = create_test_class_parameters(part_attrs, false)
                .invoke string_params = create_test_class_print_params(part_attrs)
                .invoke pt_test_select = create_test_class_select(ref_attr_obj, part_class_body, param_body, ptarraynum.body, package.application_root_class)
                .assign zero_error_message = " Association: ${rel.Numb}: Cardinality of an unconditional formalizer is equal to zero. "
                .invoke id_test_zero = create_test_class_test(object, ptarraynum.body, "zero", zero_error_message.body, "", "array", package)
                .assign ne_one_error_message = " Association: ${rel.Numb}: Cardinality of a formalizer is not equal to one. "
                .invoke id_test_ne_one = create_test_class_test(object, ptarraynum.body, "not_equal_one", ne_one_error_message.body, "","array", package)
${part_class_body.body}
${pt_test_select.body}
                .if (formalizer.Cond == 0) .// it is unconditional
                  .if ( formalizer.Mult == 0 ) .// Is unconditional and multiplicity is 1
${id_test_ne_one.body}
                  .else
${id_test_zero.body}                
                  .end if  .// unconditional and multiplicity is 1
                .elif (formalizer.Mult == 0) .// conditional but multiplicity is one
                  .assign gt_one_error_message = " Association: ${rel.Numb}: Cardinality of a formalizer is greater than 1. "
                  .invoke id_test_gt_one = create_test_class_test(object, ptarraynum.body, "greater_than_one", gt_one_error_message.body, "","array", package)
${id_test_gt_one.body}
                .end if  .// is unconditional
              .end if .// a selector is needed
            .end if .// not empty ref_attrs
          .end if .// empty formalizer
        .elif (not_empty one_side)
          .select one rel related by one_side->R_ASSOC[R209]->R_REL[R206]
          .if (not_empty rel)
          // Associated One Side: rel.Numb = ${rel.Numb}
          .end if .// not empty rel
          .select one cl_as_link related by one_side->R_ASSOC[R209]->R_ASSR[R211]
          .select many lnk_ref_attrs related by rto->O_RTIDA[R110]->O_REF[R111]->O_RATTR[R108]->O_ATTR[R106]
          .select one link_ref_attr_obj related by lnk_ref_attrs->O_OBJ[R102]
          .select one other_assoc related by one_side->R_ASSOC[R209]->R_AOTH[R210]
            // Link Object: ${link_ref_attr_obj.Name}
          .select many other_rto_part_attrs related by other_assoc->R_RTO[R204]->O_ID[R109]->O_OIDA[R105]->O_ATTR[R105]
          .select one other_side_object related by other_rto_part_attrs->O_OBJ[R102]
            // Other Side Class: ${other_side_object.Name}
          .if (other_assoc.Cond == 0 )
            .if ((object.Key_Lett != "R_RTO") and (object.Key_Lett != "R_RGO"))  .// R_RTO and R_RGO are conditional on if the association is formalized
              .assign is_referring = false
              .invoke link_class_body = create_test_class(link_ref_attr_obj, lnk_ref_attrs, is_referring)
              .assign is_referring = false
              .invoke link_class_body = create_test_class(link_ref_attr_obj, lnk_ref_attrs, is_referring)
              .assign relNumb = 0;
              .if (not_empty rel)
                .assign relNumb = rel.Numb
              .end if
              .invoke param_body = create_test_class_parameters(lnk_ref_attrs, false)
              .assign cl_lnk_arraynum = "${info.unique_num}"
              .invoke lk_test_select = create_test_class_select(link_ref_attr_obj, link_class_body, param_body, cl_lnk_arraynum.body, package.application_root_class)
              .assign zero_error_message = " Association: ${rel.Numb}: Cardinality of a link is equal to zero. "
              .invoke id_test_zero = create_test_class_test(object, cl_lnk_arraynum.body, "zero", zero_error_message.body, "", "array", package)
              .assign ne_one_error_message = " Association: ${rel.Numb}: Cardinality of a link is not equal to 1. "
              .invoke id_test_ne_one = create_test_class_test(object, cl_lnk_arraynum.body, "not_equal_one", ne_one_error_message.body, "", "array", package)
${link_class_body.body}
${lk_test_select.body}
${id_test_zero.body}

              .if ((other_assoc.Mult == 0) and (cl_as_link.Mult == 0))
${id_test_ne_one.body}
              .end if .// client as link multiplicity is one
            .end if
          .end if .// other_assoc conditionality is unconditional
        .elif (not_empty oth_side)
          .select one rel related by oth_side->R_ASSOC[R210]->R_REL[R206]
          .if (not_empty rel)
          // Associated Other Side: rel.Numb = ${rel.Numb}
          .end if .// not empty rel
          .select one cl_as_link related by oth_side->R_ASSOC[R210]->R_ASSR[R211]
          .select many lnk_ref_attrs related by rto->O_RTIDA[R110]->O_REF[R111]->O_RATTR[R108]->O_ATTR[R106]
          .select one link_ref_attr_obj related by lnk_ref_attrs->O_OBJ[R102]
          .select one one_assoc related by oth_side->R_ASSOC[R210]->R_AONE[R209]
            // Link Object: ${link_ref_attr_obj.Name}
          .if (one_assoc.Cond == 0)
            .if ((object.Key_Lett != "R_RTO") and (object.Key_Lett != "R_RGO")) .// R_RTO and R_RGO are conditional on if the association is formalized
              .assign is_referring = false
              .invoke link_class_body = create_test_class(link_ref_attr_obj, lnk_ref_attrs, is_referring)
              .assign relNumb = 0;
              .if (not_empty rel)
                .assign relNumb = rel.Numb
              .end if
              .invoke param_body = create_test_class_parameters(lnk_ref_attrs, false)
              .assign cl_lnk_arraynum = "${info.unique_num}"
              .invoke lk_test_select = create_test_class_select(link_ref_attr_obj, link_class_body, param_body, cl_lnk_arraynum.body, package.application_root_class)
              .assign zero_error_message = " Association: ${rel.Numb}: Cardinality of a link is equal to zero. "
              .invoke id_test_zero = create_test_class_test(object, cl_lnk_arraynum.body, "zero", zero_error_message.body, "", "array", package)
              .assign ne_one_error_message = " Association: ${rel.Numb}: Cardinality of a link is not equal to 1. "
              .invoke id_test_ne_one = create_test_class_test(object, cl_lnk_arraynum.body, "not_equal_one", ne_one_error_message.body, "", "array", package)
${link_class_body.body}
${lk_test_select.body}
${id_test_zero.body}
              .if ( (one_assoc.Mult == 0 ) and (cl_as_link.Mult == 0) ) .// Multiplicity is one
${id_test_ne_one.body}
              .end if
            .end if
          .end if .// one_assoc is unconditional
        .elif (not_empty supt)
          .select one rel related by supt->R_SUBSUP[R212]->R_REL[R206]
          .if (not_empty rel)
          // Supertype: rel.Numb = ${rel.Numb}
          .end if .// not empty rel
          .select many cl_as_subts related by supt->R_SUBSUP[R212]->R_SUB[R213]
          .assign subcounter = "${info.unique_num}"
    int objs${subcounter} = 0;
          .for each cl_as_subt in cl_as_subts
            .select many ref_attrs related by cl_as_subt->R_RGO[R205]->O_REF[R111]->O_RATTR[R108]->O_ATTR[R106]
            .select one ref_attr_obj related by ref_attrs->O_OBJ[R102]
            // Subtype Object: ${ref_attr_obj.Name}
            .if (not_empty ref_attrs)
              .assign is_referring = false
              .invoke part_class_body = create_test_class(ref_attr_obj, ref_attrs, is_referring)
              .invoke param_body = create_test_class_parameters(ref_attrs, false)
              .assign subtarraynum = "${info.unique_num}"
              .invoke st_test_select = create_test_class_select(ref_attr_obj, part_class_body, param_body, subtarraynum.body, package.application_root_class)
${part_class_body.body}
${st_test_select.body} 
    objs${subcounter} = objs${subcounter} + objs${subtarraynum}.length;
            .end if
          .end for
          .assign ne_one_error_message = " Association: ${rel.Numb}: Cardinality of subtype is not equal to 1. "
          .invoke id_test_ne_one = create_test_class_test(object, subcounter.body, "not_equal_one", ne_one_error_message.body, "","int", package)
${id_test_ne_one.body}
        .end if
      .end for .// each rto in rtos
    return retval;
  }
.// End check ids
.end function
