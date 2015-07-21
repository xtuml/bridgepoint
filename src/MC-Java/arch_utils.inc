.//====================================================================
.//
.// File:      $RCSfile: arch_utils.inc,v $
.// Version:   $Revision: 1.27.32.1 $
.// Modified:  $Date: 2013/07/19 12:26:01 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//  The functions in this file provide the name encodings for
.//  entities in the generated code.
.//
.//====================================================================
.//
.function get_root_pkg_name
  .param inst_ref pkg
$cr{pkg.Name}\
.end function
.//
.//====================================================================
.//
.function get_class_name
  .param inst_ref class
$Cr{class.Name}_c\
.end function
.//
.//====================================================================
.//
.function get_test_class_name
ClassQueryInterface_c\
.end function
.//
.//====================================================================
.//
.function get_instance_accessor
  .param inst_ref class
$Cr{class.Name}Instance\
.end function
.//
.//====================================================================
.//
.function get_ee_class_name
  .param inst_ref ee
  .// a slight optimization here to encode it directly 
  .// instead of calling get_ee_class_name_from_str
$Cr{ee.Key_Lett}_c\
.end function
.//
.//====================================================================
.//
.function get_sql_table_name
  .param inst_ref object
$_{object.Key_Lett}\
.end function
.//
.//====================================================================
.//
.function get_ee_class_name_from_str
  .param string kl
$Cr{kl}_c\
.end function
.//
.//====================================================================
.//
.function get_nav_func_name
  .param inst_ref class
  .param inst_ref rel
  .param string mult
  .if (mult == "many")
getMany$U_{class.Key_Lett}sOnR${rel.Numb}\
  .elif (mult == "one")
getOne$U_{class.Key_Lett}OnR${rel.Numb}\
  .else
    .print "ERROR: Invalid multiplicity provided to get_nav_func_name"
  .end if
.end function
.//
.//====================================================================
.//
.function get_attribute_name
  .param inst_ref attribute
m_$l_{attribute.Name}\
.end function
.//
.//====================================================================
.//
.function get_attribute_accessor
  .param inst_ref attribute
  .// a slight optimization here to encode it directly 
  .// instead of calling get_attribute_accessor_from_str
get$cr{attribute.Name}\
.end function
.//
.//====================================================================
.//
.function is_base_attribute_a_unique_id
  .param inst_ref attr
  .assign attr_is_unique_id = false
  .invoke result = get_base_attribute_type(attr)
  .assign type = result.type
  .if(type.Name == "unique_id")
    .assign attr_is_unique_id = true
  .end if
.end function
.//
.//====================================================================
.//
.function is_uniqueid_id_attr
   .param inst_ref attribute   .// O_ATTR
   .//
   .assign attr_result = false
   .invoke is_ref = is_referential_attr(attribute)
   .if (is_ref.result == true)
     .select any superAssoc related by attribute->O_RATTR[R106]->O_REF[R108]->R_RGO[R111]->R_SUB[R205]->R_SUBSUP[R213]->R_REL[R206] where ("$l{selected.Descrip:Optional}" == "true")
     .if (not_empty superAssoc)
       .// if is part of an identifier
       .// TBD should check base data type is unique_id too (see below) . . . . 
       .select any oida related by attribute->O_OIDA[R105]
       .if ( not_empty oida )
         .assign attr_result = true
       .end if
     .end if
   .else
     .// is_ref.result == false
     .select one type related by attribute->S_DT[R114]
     .// we only want an id assigner if this is a non-referential
     .// attribute of type unique_id
     .//
     .if ((type.Name == "unique_id"))
       .//
       .// and that is part of an identifier
       .select any oida related by attribute->O_OIDA[R105]
       .if ( not_empty oida )
         .assign attr_result = true
       .end if
     .end if
   .end if
.end function
.//
.//====================================================================
.//
.function is_referential_attr
   .param inst_ref attribute   .// O_ATTR
   .assign attr_result = false
   .select one rattr related by attribute->O_RATTR[R106]
   .if ( not_empty rattr )
     .assign attr_result = true
   .end if
.end function
.//
.//====================================================================
.//
.function is_single_unique_id
  .param inst_ref attr   .// O_ATTR
  .//
  .assign attr_result = false
  .select one class related by attr->O_OBJ[R102]
  .select any id related by class->O_ID[R104] where (selected.Oid_ID == 0)
  .select many attrs related by id->O_OIDA[R105]->O_ATTR[R105]
  .if((cardinality attrs) == 1)
    .select any idAttr related by id->O_OIDA[R105]->O_ATTR[R105]
    .if(idAttr == attr)
      .select one type related by attr->S_DT[R114]
      .select one refAttr related by attr->O_RATTR[R106]
      .if(not_empty refAttr)
        .select one baseAttr related by refAttr->O_BATTR[R113]->O_ATTR[R106]
        .select one type related by baseAttr->S_DT[R114]
      .end if
      .if(type.Name == "unique_id")
        .assign attr_result = true
      .end if
    .end if
  .end if
.end function
.//
.//====================================================================
.//
.function attr_is_persistent
  .param inst_ref attr   .// O_ATTR
  .//
  .select one type related by attr->S_DT[R114]
  .assign attr_result = ("$l{attr.Descrip:Persistent}" != "false") and (type.Name != "state<State_Model>")
.end function
.//
.//====================================================================
.//  This function returns all attributes of a class that are part of
.//  the first identifier
.//====================================================================
.function get_all_primary_id_attributes
  .param inst_ref class
  .//
  .select many attr_result from instances of O_ATTR where (selected.Attr_ID == -1)
  .select any id related by class->O_ID[R104] where (selected.Oid_ID == 0)
  .if(not_empty id)
    .select many attr_result related by id->O_OIDA[R105]->O_ATTR[R105];
  .end if
.end function
.//
.//====================================================================
.//  This function returns the necessary generated unqiue key
.//  used in an instance map
.//====================================================================
.function get_unique_instance_key
  .param inst_ref_set attrs
  .param string attr_prefix
  .//
  .assign keyString = ""
  .assign attr_found_key = false;
	.for each attr in attrs
    .invoke aip = attr_is_persistent(attr)
    .if(aip.result)
	    .if(first attrs)
	      .assign keyString = "{"
	    .end if
		  .select one datatype related by attr->S_DT[R114]
		  .assign type = datatype.Name
  	  .invoke gan = get_attribute_name ( attr )
		  .assign attrName = attr_prefix + gan.body
		  .select one refAttr related by attr->O_RATTR[R106]
		  .if(not_empty refAttr)
		    .select one rtAttr related by refAttr->O_BATTR[R113]->O_ATTR[R106]
		    .select one datatype related by rtAttr->S_DT[R114]
		    .assign type = datatype.Name
		  .end if
      .assign current_string = ""
		  .if(type == "unique_id")
		    .assign attr_found_key = true
			  .assign current_string = "${attrName}"	
		  .elif(type == "integer")
  	    .assign attr_found_key = true
			  .assign current_string = "new UUID(0, new Long(${attrName}))"	
		  .elif(type == "string")
		    .assign attr_found_key = true
			  .assign current_string = "${attrName}"
		  .end if
		  .if((not_first attrs) and ((cardinality attrs) > 1))
		    .if(current_string != "")
  		    .assign keyString = keyString + ","
  		  .end if
	    .end if
		  .if(current_string != "")
		    .assign keyString = keyString + current_string
		  .end if
	    .if(last attrs)
	      .assign keyString = keyString + "}"
		.end if
	  .end if
	.end for
	.assign attr_key = keyString
.end function
.//
.//====================================================================
.//  This function returns the necessary generated unqiue key
.//  used in an instance map
.//====================================================================
.function get_unique_instance_key_from_rto
  .param Inst_Ref object
  .param Inst_Ref rto
  .assign keyString = ""
  .assign attr_key = ""
  .select many rtida_set related by rto->O_RTIDA[R110]
  .select many attrs from instances of O_ATTR where (selected.Attr_ID == -1)
  .for each rtida in rtida_set
    .select one id related by rtida->O_OIDA[R110] where (selected.Oid_ID == 0)
    .if(not_empty id)
      .select one idAttr related by id->O_ATTR[R105]
      .// we need a where clause here for subtype supertype
	    .// (multiple ref attrs map to one id attr)
		  .select any refAttr related by rtida->O_REF[R111]->O_RATTR[R108]->O_ATTR[R106] where ( selected.Obj_ID == object.Obj_ID )
		  .assign attrs = attrs | refAttr
    .end if
  .end for
  .invoke uk = get_unique_instance_key(attrs,"")
  .assign attr_key = uk.key
  .assign attr_found_key = uk.found_key
.end function
.//
.//====================================================================
.//
.function get_base_attribute_type
  .param inst_ref attr
  .select any attr_type from instances of S_DT where (selected.DT_ID == -1)
  .select one rattr related by attr->O_RATTR[R106]
  .if(empty rattr)
     .select one attr_type related by attr->S_DT[R114]
  .else
     .select one b_attr related by rattr->O_BATTR[R113]->O_ATTR[R106]
     .invoke result = get_base_attribute_type(b_attr)
     .assign attr_type = result.type
  .end if
.end function
.//
.//====================================================================
.//
.function get_referential_var_name
  .param inst_ref obj     .// O_OBJ
  .param string text_phrase
$cr{text_phrase}$cr{obj.Name}\
.end function
.//
.//====================================================================
.//
.function get_attribute_accessor_from_str
  .param string name
get$cr{name}\
.end function
.//
.//====================================================================
.//
.function get_current_state_accessor_name
get_current_state\
.end function
.//
.//====================================================================
.//
.function get_ui_display_handle_name
m_display\
.end function
.//
.//====================================================================
.//
.function get_id_attributes
  .param inst_ref class
  .//
  .select any attr_result related by class->O_ATTR[R102]
  .assign attr_cardinality = 0
  .// Select the primary id
  .select any id related by class->O_ID[R104] where (selected.Oid_ID == 0)
  .if (not_empty id)
    .select any attr_result related by id->O_OIDA[R105]->O_ATTR[R105]
    .assign attr_cardinality = cardinality attr_result
  .else
    .assign attr_result = attr_result - attr_result
    .assign attr_cardinality = 0
  .end if
.end function
.//
.//====================================================================
.//
.function get_enumeration_class_name
  .param inst_ref dt   .// S_DT
$Cr{dt.Name}_c\
.end function
.//
.//====================================================================
.//
.function get_enumerator_name_from_value
  .param inst_ref p_enum_val
  .//
  .select one enum related by p_enum_val->S_ENUM[R824]
$_{enum.Name}\
.end function
.//
.//============================================================================
.//
.function GetEnumElementDefaultValueInfoString
  .//
  .assign attr_name  = "OOA_UNINITIALIZED_ENUM"
  .assign attr_value = -1
.end function
.//
.//====================================================================
.//
.function get_action_filter_class_name
  .param inst_ref p_class  .// O_OBJ
$r{p_class.Name}ActionFilter\
.end function
.//
.//====================================================================
.//
.function notifies_changes
  .param inst_ref elem
  .param string class_kl
  .//
  .assign attr_result = true
  .if ( class_kl == "ACT_SMT" )
    .invoke gss = get_context_for_statement( elem )
    .if ( gss.pkg_ok )
      .assign pkg = gss.pkg
      .invoke csfnc = check_pkg_for_notifies_changes(pkg)
      .assign attr_result = csfnc.result
    .elif ( gss.root_ok )
      .assign attr_result = true
    .end if
  .elif ( class_kl == "O_ATTR" )
    .if ( "$l{elem.Descrip:Persistent}" != "false" )
       .if ("${elem.Descrip:Notify_Changes}" == "false")
          .assign attr_result = false
       .else
        .select one pkg related by elem->O_OBJ[R102]->PE_PE[R8001]->EP_PKG[R8000]
        .invoke csfnc = check_pkg_for_notifies_changes(pkg)
        .assign attr_result = csfnc.result
	   .end if
    .else
      .// non-persistent attribute doesn't notify changes
      .assign attr_result = false
    .end if
  .elif ( class_kl == "O_OBJ" )
    .select one pkg related by elem->PE_PE[R8001]->EP_PKG[R8000]
    .invoke csfnc = check_pkg_for_notifies_changes(pkg)
    .assign attr_result = csfnc.result
  .else
    .if ("${elem.Descrip:Notify_Changes}" == "false")
      .assign attr_result = false
    .end if
  .end if
.end function
.//
.//====================================================================
.//
.function check_pkg_for_notifies_changes
  .param inst_ref pkg
  .assign attr_result = true
  .invoke nc = notifies_changes( pkg, "EP_PKG" )
  .assign attr_result = nc.result
  .if (attr_result)
    .// still true, also check parent package
    .select one parent related by pkg->PE_PE[R8001]->EP_PKG[R8000]
    .if (not_empty parent)
      .invoke nc = notifies_changes( parent, "EP_PKG" )
      .assign attr_result = nc.result
    .end if
  .end if
.end function 
.//
.//====================================================================
.// attr_pkg is only valid if attr_pkg_ok == true
.// attr_root is only valid if attr_root_ok == true
.//
.function get_context_for_statement
  .param inst_ref stmt  .// ACT_SMT
  .//
  .select one act related by stmt->ACT_BLK[R602]->ACT_ACT[R601]
  .select any attr_pkg from instances of EP_PKG
  .assign attr_pkg_ok = false
  .assign attr_sys_ok = false
  .assign attr_return_dt_name = "void"
  .select one smact related by act->ACT_SAB[R698]->SM_ACT[R691]
  .if (empty smact)
    .//might as well handle transitions now
    .select one smact related by act->ACT_TAB[R698]->SM_ACT[R688]
  .end if
  .select one ism related by smact->SM_SM[R515]->SM_ISM[R517]
  .if ( not_empty ism )
    .select one attr_pkg related by ism->O_OBJ[R518]->PE_PE[R8001]->EP_PKG[R8000]
    .assign attr_pkg_ok = true
  .end if
  .select one asm related by smact->SM_SM[R515]->SM_ASM[R517]
  .if (not_empty asm)
    .select one attr_pkg related by asm->O_OBJ[R519]->PE_PE[R8001]->EP_PKG[R8000]
    .assign attr_pkg_ok = true
  .end if
  .select one tfr related by act->ACT_OPB[R698]->O_TFR[R696]
  .if (not_empty tfr)
    .select one attr_pkg related by tfr->O_OBJ[R115]->PE_PE[R8001]->EP_PKG[R8000]
    .assign attr_pkg_ok = true
    .select one ret_dt related by tfr->S_DT[R116]
    .assign attr_return_dt_name = ret_dt.Name
  .end if
  .select one func related by act->ACT_FNB[R698]->S_SYNC[R695]
  .if (not_empty func)
    .assign attr_root_ok = true
    .select one ret_dt related by func->S_DT[R25]
    .assign attr_return_dt_name = ret_dt.Name
  .end if
  .select one brg related by act->ACT_BRB[R698]->S_BRG[R697]
  .if (not_empty brg)
    .assign attr_root_ok = true
    .select one ret_dt related by func->S_DT[R20]
    .assign attr_return_dt_name = ret_dt.Name
  .end if
.end function
.//
.//
.//============================================================================
.// SortSetAlphabeticallyByNameAttr
.// Given a set of instances, sets the attribute Order to
.// a value corresponding to the alphabetical order its Name attribute
.// is in the set
.//
.// <item_set> - from instances of anything with Name and Order attributes
.//============================================================================
.function SortSetAlphabeticallyByNameAttr
  .param inst_ref_set item_set
  .//
  .// Clear the 'Order' attribute of all set members.
  .for each item in item_set
    .assign item.Order = 0
  .end for
  .//
  .assign item_set_copy = item_set
  .for each item in item_set
    .for each item_copy in item_set_copy
      .if ( item.Name != item_copy.Name )
        .if ( item_copy.Name > item.Name )
          .assign item_copy.Order = item_copy.Order + 1
        .end if
      .end if
    .end for
  .end for
.end function
.//
.//====================================================================
.//
.function gen_parameter_list
  .param string root_pkg_name
  .param inst_ref_set parameters
  .param boolean prefix_param_delimiter
  .//
  .if ( not_empty parameters )
    .invoke SortSetAlphabeticallyByNameAttr( parameters )
    .assign item_count = cardinality parameters
    .assign item_number = 0
    .assign param_delimiter = ""
    .if(prefix_param_delimiter)
      .assign param_delimiter = ","
    .end if
    .while ( item_number < item_count )
      .for each parameter in parameters
        .if ( parameter.Order == item_number )
          .select one value related by parameter->V_VAL[R800]
          .if (value.buffer == "")
            .invoke gen_value(root_pkg_name, value)
          .end if
			.//
			.// now, we want to detm if the parameter is a real, in which case
			.// we might have to add an (int) cast, below; first, though, we have to 
			.// detm what kind of call the parameter is a part of - whether to 
			.// an operation, a bridge, or a function; 
			.// if the call is to an operation
			.select any paramDataType from instances of S_DT where (selected.DT_ID == -1)
			.select one oper related by parameter->ACT_TFM[R627]->O_TFR[R673]
			.if (empty oper)
			  .select one oper related by parameter->V_TRV[R811]->O_TFR[R829]
			.end if
			.if (not_empty oper)
				.// detm the parameter's type through it's associated operation-parameter
				.select one operParam related by oper->O_TPARM[R117] where (selected.Name == parameter.Name)
				.select one paramDataType related by operParam->S_DT[R118]
			.// otherwise
			.else 
				.// if the call is to a bridge
				.select any bridge related by parameter->ACT_BRG[R628]->S_BRG[R674]
				.if (empty bridge)
				  .select one bridge related by parameter->V_BRV[R810]->S_BRG[R828]
				.end if
				.if (not_empty bridge)
					.// detm the parameter's type through it's associated bridge-parameter
					.select one bridgeParam related by bridge->S_BPARM[R21] where (selected.Name == parameter.Name)
					.select one paramDataType related by bridgeParam->S_DT[R22]
				.// otherwise
				.else
					.// if the call is to a function
					.select any func related by parameter->ACT_FNC[R669]->S_SYNC[R675]
					.if (empty func)
					  .select one func related by parameter->V_FNV[R817]->S_SYNC[R827]
					.end if
					.if (not_empty func)
						.// detm the parameter's type through it's associated function-parameter
						.select one funcParam related by func->S_SPARM[R24] where (selected.Name == parameter.Name)
						.select one paramDataType related by funcParam->S_DT[R26]
					.end if
				.end if
			.end if
			.//
			.// if the parameter's data-type could be detm'd, above
			.assign intCast1 = ""
			.assign intCast2 = ""
			.if (not_empty paramDataType)
				.// detm the (core) data-type of the value supplied for the parameter
			    .select any valueDataType related by value->S_DT[R820]
			    .select one coreDataType related by valueDataType->S_UDT[R17]->S_DT[R18]
			    .if (not_empty coreDataType) 
			    	.assign valueDataType = coreDataType;
			    .end if
			    
			    .// if the value supplied is a real, and parameter is meant to take an integer
				.if ((valueDataType.Name == "real") and (paramDataType.Name == "integer"))
					.// cast the value to an int, to avoid a "possible loss of precision"
					.// syntax error in the generated code
					.assign intCast1 = "(int)("
					.assign intCast2 = ")"
			    .end if
			.end if
			.//
${param_delimiter}${intCast1}${value.buffer}${intCast2}\
          .assign param_delimiter = ","
          .break for
        .end if
      .end for
      .assign item_number = item_number + 1
    .end while
  .end if
.end function
.//
.//====================================================================
.//
.function is_reflexive
  .param inst_ref p_rel
  .//
  .assign attr_result = false
  .select one srel related by p_rel->R_SIMP[R206]
  .if ( not_empty srel )
    .select many part_set related by srel->R_PART[R207]
    .if ( (cardinality part_set) > 1 )
      .select any part1 related by srel->R_PART[R207]
      .select any part2 related by srel->R_PART[R207] where (selected.OIR_ID != part1.OIR_ID)
      .if ( part1.Obj_ID == part2.Obj_ID )
        .assign attr_result = true
      .end if
    .else
      .select any part related by srel->R_PART[R207]
      .select one form related by srel->R_FORM[R208]
      .if ( not_empty form )
        .if ( part.Obj_ID == form.Obj_ID )
          .assign attr_result = true
        .end if
      .end if
    .end if
  .else
    .select one arel related by p_rel->R_ASSOC[R206]
    .if ( not_empty arel )
      .select one aone related by arel->R_AONE[R209]
      .select one aoth related by arel->R_AOTH[R210]
      .if ( aone.Obj_ID == aoth.Obj_ID )
        .assign attr_result = true
      .end if
    .end if
  .end if
.end function
.//
.//====================================================================
.//
.// This function determines if the value is an invocation of
.// a USER bridge 
.//
.//====================================================================
.function is_user_bridge_invocation
  .param inst_ref p_val
  .//
  .assign attr_result = false
  .assign attr_Method_Name = ""
  .select one brg_call related by p_val->V_BRV[R801]->S_BRG[R828]
  .if ( not_empty brg_call )
    .select one ee related by brg_call->S_EE[R19]
    .if (ee.Key_Lett == "USER")
      .assign attr_result = true
      .assign attr_Method_Name = brg_call.Name
    .end if
  .end if
.end function
.//====================================================================
.//
.// This function determines if the value has an invocation of
.// a USER bridge 
.//
.//====================================================================
.function has_user_bridge_invocation
  .param inst_ref p_val
  .//
  .assign attr_result = false
  .assign attr_Method_Name = ""
  .invoke iubi = is_user_bridge_invocation(p_val)
  .if ( iubi.result )
    .assign attr_result = true
    .assign attr_Method_Name = iubi.Method_Name
  .else
    .select one bin_expr related by p_val->V_BIN[R801]
    .if ( not_empty bin_expr )
      .select one lop related by bin_expr->V_VAL[R802]
      .invoke hubi = has_user_bridge_invocation(lop)
      .if ( hubi.result )
        .assign attr_result = true
        .assign attr_Method_Name = hubi.Method_Name
      .else
        .select one rop related by bin_expr->V_VAL[R803]
        .invoke hubi = has_user_bridge_invocation(lop)
        .if ( hubi.result )
          .assign attr_result = true
          .assign attr_Method_Name = hubi.Method_Name
        .end if
      .end if
    .else
      .select one unary_expr related by p_val->V_UNY[R801]
      .if ( not_empty unary_expr )
        .select one op related by unary_expr->V_VAL[R804]
        .invoke hubi = has_user_bridge_invocation(op)
        .if ( hubi.result )
          .assign attr_result = true
          .assign attr_Method_Name = hubi.Method_Name
        .end if
      .end if  .// not_empty unary_expr 
    .end if   .// not_empty bin_expr
  .end if
.end function
.//
.// Returns (in .coreType) the core-data-type associated with the 
.// given (core or non-core) data-type.
.//
.function getCoreDataType
	.param inst_ref type
	.select one coreType related by type->S_UDT[R17]->S_DT[R18]
	.if (not_empty coreType) 
		.assign type = coreType;
	.end if
	.assign attr_coreType = type;
.end function
.//
.// --------------------------------------------------------
.// utility function to get last link in relationship chain
.// --------------------------------------------------------
.//
.function get_last_link_in_chain
  .param inst_ref sel_inst_rel  .// ACT_SEL
  .//
  .select any first_link related by sel_inst_rel->ACT_LNK[R637]
  .assign last_link = first_link
  .select one next_link related by first_link->ACT_LNK[R604.'succeeds']
  .while (not_empty next_link)
    .assign last_link = next_link
    .select one next_link related by next_link->ACT_LNK[R604.'succeeds']
  .end while
  .assign attr_last_link = last_link
.end function
.// --------------------------------------------------------------------
.// utility function to determine conditionality of an association chain
.// --------------------------------------------------------------------
.// if any association in the chain is conditional then the whole chain
.// is determined conditional
.//
.//
.function determine_chain_conditionality
  .param inst_ref sel_inst_rel
  .assign attr_isConditional = false
  .select any mc_inst from instances of O_OBJ where (selected.Key_Lett == "Impossible")
  .select any mc_from_set from instances of O_OBJ where (selected.Key_Lett == "Impossible")
  .select any starting_mc from instances of O_OBJ where (selected.Key_Lett == "Impossible")
  .if(sel_inst_rel.Start_Variable_Name == "self")
    .select one body related by sel_inst_rel->ACT_SMT[R603]->ACT_BLK[R602]->ACT_ACT[R601]
    .select any sm_act from instances of SM_ACT where ( "${selected.Act_ID}" == body.Action_Id )
    .if(not_empty sm_act)
      .select one mc_inst related by sm_act->SM_SM[R515]->SM_ISM[R517]->O_OBJ[R518]
    .end if
    .select any dba from instances of O_DBATTR where ( "${selected.Attr_ID}" == body.Action_Id )
    .if(not_empty dba)
      .select one mc_inst related by dba->O_BATTR[R107]->O_ATTR[R106]->O_OBJ[R102]
    .end if
    .select any op from instances of O_TFR where ( "${selected.Tfr_ID}" == body.Action_Id )
    .if(not_empty op)
      .select one mc_inst related by op->O_OBJ[R115]
    .end if
  .else
    .select one mc_inst related by sel_inst_rel->V_VAR[R613]->V_INT[R814]->O_OBJ[R818]
    .select one mc_from_set related by sel_inst_rel->V_VAR[R613]->V_INS[R814]->O_OBJ[R819]
  .end if
  .if(not_empty mc_inst)
    .assign starting_mc = mc_inst
  .else
  	.assign starting_mc = mc_from_set
  .end if
  .select any first_link related by sel_inst_rel->ACT_LNK[R637] where (selected.Next_Link_Id == "0")
  .assign curr_link = first_link
  .while (not_empty curr_link)
	.select any target_mc from instances of O_OBJ where (selected.Key_Lett == curr_link.Target_Obj_KL)
    .select one assoc related by curr_link->R_REL[R681]
    .if(not_empty assoc)
      .select one simp related by assoc->R_SIMP[R206]
      .if(not_empty simp)
        .invoke conditionality = determine_simple_association_conditionality( curr_link, simp, target_mc )
		.assign attr_isConditional = conditionality.isConditional
		.if(conditionality.isConditional == true)
		  .break while
		.end if
      .end if
	  .select one assr related by assoc->R_ASSOC[R206]
	  .if(not_empty assr)
	    .invoke conditionality = determine_associative_link_conditionality( curr_link, assr, target_mc, starting_mc )
		.assign attr_isConditional = conditionality.isConditional
		.if(conditionality.isConditional == true)
		  .break while
		.end if
	  .end if
	  .select one subsup related by assoc->R_SUBSUP[R206]
      .if (not_empty subsup)
        .invoke conditionality = determine_subtype_supertype_conditionality( subsup, target_mc )
		.assign attr_isConditional = conditionality.isConditional
		.if(conditionality.isConditional == true)
		  .break while
		.end if
      .end if
	  .select one comp related by assoc->R_COMP[R206]
	  .if(not_empty comp)
	    .invoke conditionality = determine_compositional_conditionality( curr_link, comp, target_mc )
		.assign attr_isConditional = conditionality.isConditional
		.if(conditionality.isConditional == true)
		  .break while
		.end if
	  .end if
    .end if
  .select one curr_link related by curr_link->ACT_LNK[R604.'precedes']
  .end while
.end function
.// --------------------------------------------------------------------
.// utility function to determine conditionality of a simple association
.// --------------------------------------------------------------------
.// 
.function determine_simple_association_conditionality
  .param inst_ref link
  .param inst_ref simp
  .param inst_ref target_mc
  .assign attr_isConditional = false
  .// assumes association is formalized
  .select one part related by simp->R_PART[R207]
  .select one form related by simp->R_FORM[R208]												   
  .select one part_obj related by part->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
  .select one form_obj related by form->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
  .if(part_obj == form_obj)
    .// Association is reflexive
    .if(link.Rel_Phrase == part.Txt_Phrs)
      .if(part.Cond == 1)
        .// Conditional association
  	    .assign attr_isConditional = true
	  .end if
	.elif(link.Rel_Phrase == form.Txt_Phrs)
	  .if(form.Cond == 1)
  	    .// Conditional association
  	    .assign attr_isConditional = true
  	  .end if
	.end if			  
  .elif(part_obj == target_mc)
    .// Target class in chain is the participant
    .if(part.Cond == 1)
      .// Conditional association
      .assign attr_isConditional = true
    .end if
  .elif(form_obj == target_mc)
    .// Target class in chain is the formalizer
    .if(form.Cond == 1)
      .// Conditional association
      .assign attr_isConditional = true
    .end if
  .end if 
.end function
.// --------------------------------------------------------------------
.// utility function to determine conditionality of an associative link
.// --------------------------------------------------------------------
.// 
.function determine_associative_link_conditionality
  .param inst_ref link
  .param inst_ref assoc
  .param inst_ref target_mc
  .param inst_ref starting_mc
  .assign attr_isConditional = false
  .select one aone related by assoc->R_AONE[R209]
  .select one aoth related by assoc->R_AOTH[R210]
  .select one assr related by assoc->R_ASSR[R211]
  .select one aone_mc related by aone->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
  .select one aoth_mc related by aoth->R_RTO[R204]->R_OIR[R203]->O_OBJ[R201]
  .select one assr_mc related by assr->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]																	     
  .if(aone_mc == aoth_mc)
    .// association is reflexive
    .if(link.Rel_Phrase == aone.Txt_Phrs)
      .if(aone.Cond == 1)
        .// Conditional association
    	.assign attr_isConditional = true
  	  .end if
  	.elif(link.Rel_Phrase == aoth.Txt_Phrs)
  	  .if(aoth.Cond == 1)
        .// Conditional association
    	.assign attr_isConditional = true
      .end if
  	.end if			  
  .elif(aone_mc == target_mc)
    .// target class is aone
	.if(aone.Cond == 1)
	  .// Conditional association
	  .assign attr_isConditional = true
	.end if
  .elif(aoth_mc == target_mc)
    .// target class is aoth
	.if(aoth.Cond == 1)
	  .// Conditional association
	  .assign attr_isConditional = true
	.end if
  .elif(assr_mc == target_mc)
    .// target class is associator
	.// Check associated at following target direction
	.if(aone_mc == aoth_mc)
	  .// association is reflexive
	  .if(link.Rel_Phrase == aone.Txt_Phrs)
	    .if(aone.Cond == 1)
	      .// Conditional association
	      .assign attr_isConditional = true
	    .end if
	  .elif(link.Rel_Phrase == aoth.Txt_Phrs)
	    .if(aoth.Cond == 1)
	      .// Conditional association
	      .assign attr_isConditional = true
	    .end if
   	  .end if			  
	.end if
	.if(starting_mc == aone_mc)
	  .// starting model class is aone
	  .if(aoth.Cond == 1)
        .// Conditional association
	    .assign attr_isConditional = true
	  .end if
	.elif(starting_mc == aoth_mc)
	  .// starting model class is aoth
	  .if(aone.Cond == 1)
	  	.assign attr_isConditional = true
	  .end if
	.end if  
  .end if
.end function
.// -------------------------------------------------------------------------------
.// utility function to determine conditionality of a subtype/supertype association
.// -------------------------------------------------------------------------------
.// 
.function determine_subtype_supertype_conditionality
  .param inst_ref subsup
  .param inst_ref target_mc
  .assign attr_isConditional = false
  .select many subtypes related by subsup->R_SUB[R213]
  .for each subtype in subtypes
    .select one subtype_mc related by subtype->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
    .if(subtype_mc == target_mc)
      .// target class is supertype
	  .assign attr_isConditional = true
    .end if
  .end for  
.end function
.// -------------------------------------------------------------------------
.// utility function to determine conditionality of a composition association
.// -------------------------------------------------------------------------
.// 
.function determine_compositional_conditionality
  .param inst_ref link
  .param inst_ref comp
  .param inst_ref target_mc
  .assign attr_isConditional = false
  .select one cone related by comp->R_CONE[R214]
  .select one coth related by comp->R_COTH[R215]
  .select one cone_mc related by cone->R_OIR[R203]->O_OBJ[R201]
  .select one coth_mc related by coth->R_OIR[R203]->O_OBJ[R201]
  .if(cone_mc == coth_mc)
  	.// association is reflexive
    .if(link.Rel_Phrase == cone.Txt_Phrs)
      .if(cone.Cond == 1)
        .// Conditional association
        .assign attr_isConditional = true
      .end if
    .elif(link.Rel_Phrase == coth.Txt_Phrs)
      .if(coth.Cond == 1)
        .// Conditional association
      	.assign attr_isConditional = true
      .end if
    .end if			  
  .elif(cone_mc == target_mc)
    .// target class is cone
	.if(cone.Cond == 1)
	  .// Conditional association
	  .assign attr_isConditional = true
	.end if
  .elif(coth_mc == target_mc)
    .// target class is coth
	.if(coth.Cond == 1)
	  .// Conditional association
	  .assign attr_isConditional = true
	.end if
  .end if	
.end function
.//
.//====================================================================
.//
.function is_persistent
  .param inst_ref class   .// O_OBJ

  .assign attr_result = true
  .//check parent SS property
  .select one pkg related by class->PE_PE[R8001]->EP_PKG[R8000]
  .assign persistent_kw = "$l{pkg.Descrip:Persistent}"
  .assign attr_result = not (persistent_kw == "false")
  
  .//Override persistent property if specified in the class
  .assign persistent_kw = "$l{class.Descrip:Persistent}"
  .if(persistent_kw == "false")
    .assign attr_result = false
  .elif (persistent_kw == "true")
    .assign attr_result = true
  .end if
.end function
