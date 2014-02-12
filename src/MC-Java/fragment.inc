.//====================================================================
.//
.// File:      fragment.inc
.//
.// Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//    Purpose:    This archetype file contains the fragment functions
.//                that are called when the generator parses the action
.//                language via the .al_xlate primitive.
.//
.//    Functions:  (marker for formatting)
.//
.//============================================================================
.//
.// This called by each entity which needs to record the Block_Id with which
.// it is associated.
.//
.//============================================================================
.function get_blck_id
  .invoke content = FILE_READ("blck_id")
  .invoke blck_id = STRING_TO_INTEGER(content.result)
  .assign attr_blck_id = blck_id.result
.end function
.//
.//============================================================================
.// 
.//  Change the action data so that it is marked as the one currently
.//  being translated.
.//
.//============================================================================
.function mark_action_under_translation
  .param inst_ref p_action   .// S_SYNC, S_BRG, O_TFR, SM_ACT, O_DBATTR
  .//
  .assign p_action.Suc_Pars = p_action.Suc_Pars + 10
.end function
.//
.//============================================================================
.// 
.//  Change the action data so that it is no longer marked as the one currently
.//  being translated.
.//
.//============================================================================
.function unmark_action_under_translation
  .param inst_ref p_action   .// S_SYNC, S_BRG, O_TFR, SM_ACT, O_DBATTR
  .//
  .assign p_action.Suc_Pars = p_action.Suc_Pars - 10
.end function
.//
.//============================================================================
.// 
.//  Find the action currently being translated.  
.//   If p_unmark is true, then unmark the action as being translated
.//
.//   Returns attr_id, attr_type
.//  
.//============================================================================
.function find_action_under_translation
  .param boolean p_unmark
  .//
  .assign attr_type = ""
  .assign attr_id = ""
  .select any sm from instances of SM_ACT where (selected.Suc_Pars >= 10)
  .if (not_empty sm)
    .assign attr_type = "SM"
    .assign attr_id = "${sm.Act_ID}"
    .if (p_unmark)
      .invoke unmark_action_under_translation( sm )
    .end if
  .else
    .select any tfr from instances of O_TFR where (selected.Suc_Pars >= 10)
    .if (not_empty tfr)
      .if ( tfr.Instance_Based == 0 )
        .assign attr_type = "CO"
      .else
        .assign attr_type = "IO"
      .end if
      .assign attr_id = "${tfr.Tfr_ID}"
      .if (p_unmark)
        .invoke unmark_action_under_translation( tfr )
      .end if
    .else
      .select any sync from instances of S_SYNC where (selected.Suc_Pars >= 10)
      .if (not_empty sync)
        .assign attr_type = "FN"
        .assign attr_id = "${sync.Sync_ID}"
        .if (p_unmark)
          .invoke unmark_action_under_translation( sync )
        .end if
      .else
        .select any brg from instances of S_BRG where (selected.Suc_Pars >= 10)
        .if ( not_empty brg )
          .assign attr_type = "BR"
          .assign attr_id = "${brg.Brg_ID}"
          .if (p_unmark)
            .invoke unmark_action_under_translation( brg )
          .end if
        .else
          .select any attr from instances of O_DBATTR where (selected.Suc_Pars >= 10)
          .if ( not_empty attr )
            .assign attr_type = "AT"
            .assign attr_id = "${attr.Attr_ID}"
            .if (p_unmark)
              .invoke unmark_action_under_translation( attr )
            .end if
          .end if
        .end if
      .end if
    .end if
  .end if
.end function
.//
.//============================================================================
.//
.// This is called first by the .al_xlate primitive when translating an action.
.// The SM_ACT pointed to by :sym.p_actn:esym. is marked as
.// the action currently being parsed. The indentation level of resultant
.// code is initialized. 
.// The output fragment attribute :sym.actn_ref:esym. is set to 
.// refer to the action currently being parsed (i.e., :sym.p_actn:esym.).
.// It is IMPORTANT to note that currently we can only parse one domain
.// at a time (ie - the generation db can have only one domain).
.//.Textflow break
.// :fb.Note::efb. We assume that the action has been successfully parsed.
.//
.//============================================================================
.//
.function actn_begin
  .param inst_ref p_actn
  .assign attr_actn_id = "${p_actn.Act_ID}"
  .assign attr_actn_typ = "SM"
  .assign attr_outer_blk_id = ""
  .invoke mark_action_under_translation( p_actn )
  .invoke FILE_WRITE("blck_id","${p_actn.Act_ID}")
.end function
.//
.//============================================================================
.//
.// This is called first by the .al_xlate primitive when translating a transform.
.//  The SM_TFM pointed to by :sym.p_actn:esym. is marked as
.// the action currently being parsed. The indentation level of resultant
.// code is initialized. 
.// The output fragment attribute :sym.actn_ref:esym. is set to 
.// refer to the action currently being parsed (i.e., :sym.p_actn:esym.).
.// It is IMPORTANT to note that currently we can only parse one domain
.// at a time (ie - the generation db can have only one domain).
.//.Textflow break
.// :fb.Note::efb. We assume that the action has been successfully parsed.
.//
.//============================================================================
.//
.function actn_trans_begin
  .param inst_ref p_tfm
  .assign attr_actn_id = "${p_tfm.Tfr_ID}"
  .assign attr_actn_typ = "CO"
  .assign attr_outer_blk_id = ""
  .invoke mark_action_under_translation( p_tfm )
  .invoke FILE_WRITE("blck_id","${attr_actn_id}")
.end function
.//
.//============================================================================
.// actn_ib_trans_begin
.//
.//============================================================================
.function actn_ib_trans_begin
  .param inst_ref p_tfm
  .assign attr_actn_id = "${p_tfm.Tfr_ID}"
  .assign attr_actn_typ = "IO"
  .assign attr_outer_blk_id = ""
  .invoke mark_action_under_translation( p_tfm )
  .invoke FILE_WRITE("blck_id","${attr_actn_id}")
.end function
.//
.//============================================================================
.// actn_synch_service_begin
.//
.//============================================================================
.function actn_synch_service_begin
  .param inst_ref p_sync_service   .//  S_SYNC
  .assign attr_actn_id = "${p_sync_service.Sync_ID}"
  .assign attr_actn_typ = "FN"
  .assign attr_outer_blk_id = ""
  .invoke mark_action_under_translation( p_sync_service )
  .invoke FILE_WRITE("blck_id","${attr_actn_id}")
.end function
.//
.//============================================================================
.// actn_bridge_begin
.//
.//============================================================================
.function actn_bridge_begin
  .param inst_ref p_bridge   .//  S_BRG
  .assign attr_actn_id = "${p_bridge.Brg_ID}"
  .assign attr_actn_typ = "BR"
  .assign attr_outer_blk_id = ""
  .invoke mark_action_under_translation( p_bridge )
  .invoke FILE_WRITE("blck_id","${attr_actn_id}")
.end function
.//
.//============================================================================
.// actn_attribute_begin
.//
.//============================================================================
.function actn_attribute_begin
  .param inst_ref p_attr   .//  O_DBATTR
  .assign attr_actn_id = "${p_attr.Attr_ID}"
  .assign attr_actn_typ = "AT"
  .assign attr_outer_blk_id = ""
  .invoke mark_action_under_translation( p_attr )
  .invoke FILE_WRITE("blck_id","${attr_actn_id}")
.end function
.//
.//============================================================================
.//
.// This function is called when a block :sym.p_blck:esym. has been created and we
.// need to add it to the existing action :sym.p_actn:esym..
.// The output fragment attribute :sym.body:esym. is set to 
.// the concatenation of :sym.p_actn.body:esym. and :sym.p_blck.body:esym..
.//
.//============================================================================
.//
.function actn_append_blck
  .param frag_ref p_actn
  .param frag_ref p_blck
  .assign attr_actn_id = p_actn.actn_id
  .assign attr_actn_typ = p_actn.actn_typ
  .assign attr_outer_blk_id = p_actn.outer_blk_id
${p_actn.body}\
  .if (attr_outer_blk_id == "")
    .assign attr_outer_blk_id = "${p_blck.blck_id}"
  .end if
${p_blck.body}\
  .assign p_actn.body = ""
  .assign p_blck.body = ""
.end function
.//
.//============================================================================
.//
.// This function is called once when we are done translating the
.// current action. We simply set the translation status' to INACTIVE.
.// The input fragment attribute :sym.p_actn.body:esym. is copied to
.// the output fragment attribute :sym.body:esym..
.//
.//============================================================================
.//
.function actn_end
  .param frag_ref p_actn
  INSERT INTO ACT_ACT VALUES ('${p_actn.actn_id}', '${p_actn.actn_typ}', '${p_actn.outer_blk_id}') ;
${p_actn.body}\
  .invoke find_action_under_translation(TRUE)
.end function
.//
.//============================================================================
.//
.// This function is called when we are starting 
.// a new block of statements. 
.//
.//============================================================================
.//
.function blck_begin
  .invoke get_prev_blck_id = get_blck_id()
  .assign attr_blck_id = "${info.unique_num}"
  .assign attr_prev_blck_id = get_prev_blck_id.blck_id
  .assign attr_prev_stmt_id = 0
  .assign attr_self_output = false
  .invoke FILE_WRITE("blck_id","${attr_blck_id}")
.end function
.//
.//============================================================================
.//
.// This function is called when we have a new complete statement :sym.p_stmt:esym.
.// that we need to append to the current active block :sym.p_blck:esym..
.//
.//============================================================================
.//
.function blck_append_stmt
  .param frag_ref p_blck
  .param frag_ref p_stmt
  .assign attr_blck_id = p_blck.blck_id
  .assign attr_prev_stmt_id = p_stmt.stmt_id
  .assign attr_prev_blck_id = p_blck.prev_blck_id
  .assign attr_self_output = p_blck.self_output
${p_blck.body}\
  INSERT INTO ACT_SMT VALUES ('${p_stmt.stmt_id}','${p_blck.blck_id}', '', '${p_blck.prev_stmt_id}') ;
${p_stmt.body}\
.end function
.//
.//============================================================================
.//
.// This function is called when variable :sym.p_var:esym. has gone out of scope
.// because we have reached the end of an action language block (eg - at the
.// end of an "if" or "for" block). The input fragment attribute :sym.p_blck.body:esym. is
.// copied to the output fragment attribute :sym.body:esym..
.// The output fragment for this function will be
.// passed to the "blck_end" function.
.//
.//============================================================================
.//
.function blck_var_out_of_scope
  .param frag_ref p_blck
  .param frag_ref p_var
  .assign attr_blck_id = p_blck.blck_id
  .assign attr_prev_blck_id = p_blck.prev_blck_id
  .assign attr_self_output = p_blck.self_output
  .if ( (p_var.var_name == "self") and (not attr_self_output) )
${p_var.body}\
    .assign attr_self_output = true
  .end if
${p_blck.body}\
  .assign p_blck.body = ""
.end function
.//
.//============================================================================
.//
.// This function is called when a block has been terminated.
.//
.//============================================================================
.//
.function blck_end
  .param frag_ref p_blck
  .assign act_id = ""
  .assign act_type = ""
  .invoke result = find_action_under_translation(FALSE)
  INSERT INTO ACT_BLK VALUES ('${p_blck.blck_id}','${result.id}','${result.type}') ;
  .assign attr_blck_id = p_blck.blck_id
${p_blck.body}\
  .assign p_blck.body = ""
  .invoke FILE_WRITE("blck_id","${p_blck.prev_blck_id}")
.end function
.//
.//============================================================================
.//
.// This function is called when we access a literal boolean 
.// r-value :sym.p_boolean_value:esym..
.//
.//============================================================================
.//
.function rval_literal_boolean
  .param string p_boolean_value
  .assign attr_val_id = "${info.unique_num}"
  .select any dt from instances of S_DT where ( selected.Name == "boolean" )
  .assign attr_dt_id = "${dt.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_LBO VALUES ('${attr_val_id}','${p_boolean_value}') ;
.end function
.//
.//============================================================================
.//
.// This function is called when we access an integer
.// r-value :sym.p_integer_value:esym..
.//
.//============================================================================
.//
.function rval_literal_integer
  .param string p_integer_value
  .assign attr_val_id = "${info.unique_num}"
  .select any dt from instances of S_DT where ( selected.Name == "integer" )
  .assign attr_dt_id = "${dt.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_LIN VALUES ('${attr_val_id}','${p_integer_value}') ;
.end function
.//
.//============================================================================
.//
.// This function is called when we access a real r-value :sym.p_real_value:esym..
.//
.//============================================================================
.//
.function rval_literal_real
  .param string p_real_value
  .assign attr_val_id = "${info.unique_num}"
  .select any dt from instances of S_DT where ( selected.Name == "real" )
  .assign attr_dt_id = "${dt.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_LRL VALUES ('${attr_val_id}','${p_real_value}') ;
.end function
.//
.//============================================================================
.//
.// This function is called when we access an enumerator r-value p_enum_name
.//  from the enumeration p_enum_dt_name
.//
.//============================================================================
.//
.function rval_literal_enum
  .param string p_enum_dt_name
  .param string p_enum_name
  .assign attr_val_id = "${info.unique_num}"
  .select any dt from instances of S_DT where ( selected.Name == p_enum_dt_name )
  .assign attr_dt_id = "${dt.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_LEN VALUES ('${attr_val_id}', '${dt.Name}', '${p_enum_name}' ) ;
.end function
.//
.//============================================================================
.//
.// This function is called when we access a string
.// r-value :sym.p_string_value:esym..
.//
.//============================================================================
.//
.function rval_literal_string
  .param string p_string_value
  .assign attr_val_id = "${info.unique_num}"
  .select any dt from instances of S_DT where ( selected.Name == "string" )
  .assign attr_dt_id = "${dt.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_LST VALUES ('${attr_val_id}','${p_string_value}') ;
.end function
.//
.//============================================================================
.//
.// This function is called when we access a received event
.// supplemental data item :sym.p_evt_di_name:esym. for action :sym.p_actn:esym.
.// (an SM_ACT reference) as an r-value. 
.//
.//============================================================================
.//
.function rval_read_rcvd_evt_di
  .param inst_ref p_actn
  .param string p_evt_di_name
  .assign attr_val_id = "${info.unique_num}"
  .select any state related by p_actn->SM_AH[R514]->SM_MOAH[R513]->SM_STATE[R511]
  .select any evtdi related by state->SM_SEME[R503]->SM_SEVT[R503]->SM_EVT[R525]->SM_EVTDI[R532] where ( selected.Name == p_evt_di_name )
  .assign attr_dt_id = "${evtdi.DT_ID}"
  .assign sm_id = "${evtdi.SM_ID}"
  .invoke blck_id = get_blck_id()
    INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
    INSERT INTO V_EDV VALUES ('${attr_val_id}') ;
  .select many evtdis related by p_actn->SM_AH[R514]->SM_MOAH[R513]->SM_STATE[R511]->SM_SEME[R503]->SM_SEVT[R503]->SM_EVT[R525]->SM_EVTDI[R532] where ( selected.Name == p_evt_di_name )
  .for each evtdi in evtdis
    .assign smed_id = "${evtdi.SMedi_ID}"
    INSERT INTO V_EPR VALUES ('${attr_val_id}','${sm_id}','${smed_id}') ;
  .end for
.end function
.//
.//============================================================================
.//
.// This function is called when we read the attribute :sym.p_attr_name:esym.
.// of the object instance variable referred to
.// by :sym.p_inst_ref_var:esym..  The :sym.p_inst_ref_var:esym. fragment
.// was produced by one of the "var_*" functions.
.//
.//============================================================================
.//
.function rval_read_obj_attr
  .param frag_ref p_inst_ref_var
  .param string p_attr_name
  .assign attr_val_id = "${info.unique_num}"
  .select any obj from instances of O_OBJ where ( selected.Key_Lett == p_inst_ref_var.obj_kl )  
  .select any attribute related by obj->O_ATTR[R102] where ( selected.Name == p_attr_name )
  .assign attr_dt_id = "${attribute.DT_ID}"
  .select one ref_attr related by attribute->O_RATTR[R106]
  .if ( not_empty ref_attr )
    .select one base_attr related by ref_attr->O_BATTR[R113]->O_ATTR[R106]
    .assign attr_dt_id = "${base_attr.DT_ID}"
  .end if
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_AVL VALUES ('${attr_val_id}','${p_attr_name}','0',\
'${p_inst_ref_var.var_name}','${p_inst_ref_var.blck_id}') ;
.end function
.//
.//============================================================================
.//
.// This function is called when we read the attribute :sym.p_attr_name:esym.
.// of the selected variable in the where clause :sym.p_where_clause:esym.
.// (produced by "begin_where"). 
.//
.//============================================================================
.//
.function rval_read_selected_attr
  .param frag_ref p_where_clause
  .param string p_attr_name
  .assign attr_val_id = "${info.unique_num}"
  .select any obj from instances of O_OBJ where ( selected.Key_Lett == p_where_clause.obj_kl )  
  .select any attribute related by obj->O_ATTR[R102] where ( selected.Name == p_attr_name )
  .assign attr_dt_id = "${attribute.DT_ID}"
  .select one ref_attr related by attribute->O_RATTR[R106]
  .if ( not_empty ref_attr )
    .select one base_attr related by ref_attr->O_BATTR[R113]->O_ATTR[R106]
    .assign attr_dt_id = "${base_attr.DT_ID}"
  .end if
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_SLR VALUES ('${attr_val_id}','${p_attr_name}') ;
.end function
.//
.//============================================================================
.//
.// This function is called when we access a transient variable :sym.p_var:esym.
.// r-value. A transient variable is similar to a local function variable
.// in a typical programming language.  The :sym.p_var:esym. fragment was
.// produced by one of the "var_*" functions.
.//
.//============================================================================
.//
.function rval_read_transient_var
  .param frag_ref p_var
  .assign attr_val_id = "${info.unique_num}"
  .assign attr_dt_id = p_var.dt_id
  .assign attr_blck_id = p_var.blck_id
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_TVL VALUES ('${attr_val_id}','${p_var.var_name}', '${p_var.blck_id}') ;
.end function
.//
.//============================================================================
.//
.// This function is called when we access a transform r-value.
.// This translates the invocation of 
.// transformer :sym.p_method_name:esym. into the corresponding function call of the static
.// function corresponding to the object with the keyletters :sym.p_obj_kl:esym..
.// The transformer (O_TFR) being translated is pointed to by :sym.p_param.ref:esym.
.// (:sym.p_param:esym. was produced by "param_end").
.//
.//============================================================================
.//
.function rval_transform
  .param string p_obj_kl
  .param string p_method_name
  .param frag_ref p_param
  .assign attr_val_id = "${info.unique_num}"
  .select any obj from instances of O_OBJ where ( selected.Key_Lett == p_obj_kl )  
  .select any trans related by obj->O_TFR[R115] where ( selected.Name == p_method_name )
  .assign attr_dt_id = "${trans.DT_ID}"\
  .invoke blck_id = get_blck_id()
  INSERT INTO ACT_TFM VALUES ('${p_param.stmt_id}','${p_obj_kl}','${p_method_name}') ;
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_TRV VALUES ('${attr_val_id}','${p_param.stmt_id}') ;
${p_param.body}
.end function
.//
.//============================================================================
.//
.// This function is called when we access a bridge r-value.
.// This translates the invocation of the 
.// bridge function :sym.p_method_name:esym. into the corresponding
.// static method call of the static
.// function corresponding to the external entity with 
.// key letters :sym.p_ee_keyletters:esym. that owns the bridge.
.// :fb.Note::efb. 
.// The fragment function "param_begin_bridge" has already been
.// called as well as the parameter functions param_add and param_end
.// which associate the corresponding parameters to their r-value.
.//
.//============================================================================
.//
.function rval_bridge
  .param string p_ee_keyletters
  .param string p_method_name
  .param frag_ref p_param
  .assign attr_val_id = "${info.unique_num}"
  .select any ee from instances of S_EE where ( selected.Key_Lett == p_ee_keyletters )  
  .select any brg related by ee->S_BRG[R19] where ( selected.Name == p_method_name )
  .assign attr_dt_id = "${brg.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO ACT_BRG VALUES ('${p_param.stmt_id}','${p_ee_keyletters}','${p_method_name}') ;
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_BRV VALUES ('${attr_val_id}','${p_param.stmt_id}') ;
${p_param.body}\
.end function
.//
.//============================================================================
.//
.// This function is called when we access a synch service r-value.
.// This translates the invocation of the 
.// domain function :sym.p_name:esym. into the corresponding
.// static method call of the static
.// function defined by the domain class.
.// :fb.Note::efb. 
.// The fragment function "param_begin_sync_service" has already been
.// called as well as the parameter functions param_add and param_end
.// which associate the corresponding parameters to their r-value.
.//
.//============================================================================
.//
.function rval_synch_service
  .param string p_name
  .param frag_ref p_param
  .assign attr_val_id = "${info.unique_num}"
  .select any func from instances of S_SYNC where ( selected.Name == p_name )
  .assign attr_dt_id = "${func.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO ACT_FNC VALUES ('${p_param.stmt_id}','${p_name}') ;
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_FNC VALUES ('${attr_val_id}','${p_param.stmt_id}') ;
${p_param.body}
.end function
.//
.//============================================================================
.//
.// This function is called when we apply a unary operator :sym.p_operator:esym.
.// to a single operand :sym.p_operand_rval:esym.. The :sym.p_operand_rval:esym.
.// fragment was produced by one of the other "rval_*" functions.  This function
.// uses the attributes :sym.p_operand_rval.var_name:esym. (the variable's name) 
.// and :sym.p_operand_rval.var_cond:esym. ("ONE" or "MANY").
.//
.//============================================================================
.//
.function rval_unary_op
  .param string p_operator
  .param frag_ref p_operand_rval
  .assign attr_val_id = "${info.unique_num}"
  .assign irf_val_id = "${p_operand_rval.val_id}"
  .assign attr_dt_id = "${p_operand_rval.dt_id}"
  .invoke blck_id = get_blck_id()
  .if ( ( (p_operator == "EMPTY") OR (p_operator == "NOT_EMPTY") ) OR (p_operator == "CARDINALITY") )
    .if ( (p_operator == "EMPTY") OR (p_operator == "NOT_EMPTY") )
      .select any dt from instances of S_DT where ( selected.Name == "boolean" )
      .assign attr_dt_id = "${dt.DT_ID}"
    .end if
    .if ( p_operator == "CARDINALITY" )
      .select any dt from instances of S_DT where ( selected.Name == "integer" )
      .assign attr_dt_id = "${dt.DT_ID}"
    .end if
    .assign irf_val_id = "${info.unique_num}"
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_UNY VALUES ('${attr_val_id}','${irf_val_id}','${p_operator}') ;
    .//
    .// operand is an instance, turn it into a value.
  INSERT INTO V_VAL VALUES ('${irf_val_id}','','${p_operand_rval.dt_id}','${blck_id.blck_id}') ;
    .if (p_operand_rval.var_is_set == "FALSE")
  INSERT INTO V_IRF VALUES ('${irf_val_id}','${p_operand_rval.var_name}','${p_operand_rval.blck_id}') ;
    .else
  INSERT INTO V_ISR VALUES ('${irf_val_id}','${p_operand_rval.var_name}') ;
    .end if
  .else
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}', '${blck_id.blck_id}') ;
  INSERT INTO V_UNY VALUES ('${attr_val_id}','${irf_val_id}','${p_operator}') ;
${p_operand_rval.body}\
  .end if
.end function
.//
.//============================================================================
.//
.// This function is called when we apply the binary operator :sym.p_operator:esym.
.// to the two r-values :sym.p_left_operand_rval:esym. and :sym.p_right_operand_rval:esym..
.// The two input
.// fragments were produced by the other "rval_*" functions. Aside from the exceptions
.// of AND (&&) and OR (||), the operators in the action language translate
.// directly to the corresponding operators in C++.
.//.Textflow break
.// :fb.Note::efb. 
.// Operations on operands of type inst_ref_set<Object> are not
.// supported by this architecture.
.//
.//============================================================================
.//
.function rval_binary_op
  .param frag_ref p_left_operand_rval
  .param string p_operator
  .param frag_ref p_right_operand_rval
  .assign attr_val_id = "${info.unique_num}"
  .//
  .// assume the left operand is the correct type
  .assign attr_dt_id = p_left_operand_rval.dt_id
  .select any real_dt from instances of S_DT where ( selected.Name == "real" )
  .select any rightType from instances of S_DT where ("${selected.dt_id}" == p_right_operand_rval.dt_id)
  .select one coreType related by rightType->S_UDT[R17]->S_CDT[R18]->S_DT[R17]
  .if (not_empty coreType) 
    .assign rightType = coreType;
  .end if
  .if ( rightType == real_dt )
    .// this is the case when the left op may be integer and the right op is real
    .// if the left op is real, we're already covered
    .assign attr_dt_id = "${real_dt.DT_ID}"
  .end if
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_BIN VALUES ('${attr_val_id}','${p_right_operand_rval.val_id}','${p_left_operand_rval.val_id}','${p_operator}') ;
${p_left_operand_rval.body}\
${p_right_operand_rval.body}\
.end function
.//
.//
.//============================================================================
.//
.// This function is called when we reach a right parentheses in an expression.
.// The :sym.p_expression:esym. fragment was produced by one of the "rval_*"
.// functions.
.//
.//============================================================================
.//
.function rval_add_parens
  .param frag_ref p_expression
  .assign attr_val_id = "${info.unique_num}"
  .assign attr_dt_id = p_expression.dt_id
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_PEX VALUES ('${attr_val_id}','${p_expression.val_id}') ;
${p_expression.body}
.end function
.//
.//============================================================================
.//
.// This function is called when we first start processing a
.// state model event :sym.p_evt_label:esym. destined to an object assigner. 
.// The output fragment has two attributes set: :sym.param_type:esym. ("EVT")
.// and :sym.ref:esym. (an SM_EVT reference).
.//.Textflow break
.// :fb.Note::efb. 
.// We know that the
.// parameter functions param_add and param_end will follow to associate
.// the corresponding parameters.
.//
.//============================================================================
.//
.function param_begin_evt_assigner
  .param string p_evt_label
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//============================================================================
.//
.// This function is called when we first start processing a
.// state model event :sym.p_evt_label:esym. destined to an external entity. 
.// The output fragment has two attributes set: :sym.param_type:esym. ("EE")
.// and :sym.ref:esym. (an S_EEEVT reference).
.//.Textflow break
.// :fb.Note::efb. 
.// We know that the
.// parameter functions param_add and param_end will follow to associate
.// the corresponding parameters.
.//
.//============================================================================
.//
.function param_begin_evt_ext_entity
  .param string p_evt_label
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//
.//============================================================================
.//
.// This function is called when we first start processing a
.// state model creation event :sym.p_evt_label:esym. destined to an object. 
.// The output fragment has two attributes set: :sym.param_type:esym. ("EVT")
.// and :sym.ref:esym. (an SM_EVT reference).
.//.Textflow break
.// :fb.Note::efb. 
.// We know that the
.// parameter functions param_add and param_end will follow to associate
.// the corresponding parameters.
.//
.//============================================================================
.//
.function param_begin_evt_creation
  .param string p_evt_label
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//============================================================================
.//
.// This function is called when we first start processing an invocation of the 
.// transformer :sym.p_method_name:esym. belonging to the object with
.// key letters :sym.p_obj_kl:esym.. 
.// The output fragment has two attributes set: :sym.param_type:esym. ("TRANS")
.// and :sym.ref:esym. (an O_TFR reference).
.//.Textflow break
.// :fb.Note::efb. 
.// We know that the parameter functions param_add
.// and param_end will follow to associate the corresponding parameters.
.//
.//============================================================================
.//
.function param_begin_transform
  .param string p_obj_kl
  .param string p_method_name
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//
.function param_begin_synch_service
  .param string p_method_name
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//============================================================================
.//
.// This function is called when we first start processing an invocation of the
.// bridge :sym.p_method_name:esym. belonging to the external entity with 
.// the key letters :sym.p_ee_keyletters:esym..
.// The output fragment has two attributes set: :sym.param_type:esym. ("BRIDGE")
.// and :sym.ref:esym. (an S_BRG reference).
.//.Textflow break
.// :fb.Note::efb. 
.// We know that the parameter functions param_add and
.// param_end will follow to associate the corresponding parameters.
.//
.//============================================================================
.//
.function param_begin_bridge
  .param string p_ee_keyletters
  .param string p_method_name
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//============================================================================
.//
.// This function is called when we first start processing the
.// state model event :sym.p_evt_label:esym. destined to an object instance. 
.// The output fragment has two attributes set: :sym.param_type:esym. ("EVT")
.// and :sym.ref:esym. (an SM_EVT reference).
.//.Textflow break
.// :fb.Note::efb. 
.// We know that the
.// parameter functions param_add and param_end will follow to associate
.// the corresponding parameters.
.//
.//============================================================================
.//
.function param_begin_evt_obj_inst
  .param string p_evt_label
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//============================================================================
.//
.// This function is called when we first start processing the
.// a special where clause. This function is responsible for initializing any
.// attributes needed to build the parameter list for the special where clause
.// identified by :sym.p_key:esym..
.// This architecture has no special where clauses, so this function 
.// does nothing.
.//.Textflow break
.// :fb.Note::efb. 
.// We know that the
.// parameter functions param_add and param_end will follow to associate
.// the corresponding parameters.
.//
.//============================================================================
.//
.function param_begin_where_special
  .param string p_key
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//============================================================================
.// param_begin_ib_transform
.//
.//============================================================================
.function param_begin_ib_transform
  .param frag_ref p_obj_inst_ref_var
  .param string p_method_name
  .assign attr_stmt_id = "${info.unique_num}"
  .//
.end function
.//
.//============================================================================
.//
.// This function adds parameters any time there is an action
.// language parameter list. All we really do here is associate the value
.// of the parameter :sym.p_param_rval:esym. with the actual parameter
.// name :sym.p_param_name:esym..  Based on the type
.// of parameter list provided in :sym.p_param.param_type:esym.,
.// we find the concerned OOA parameter and set it's
.// Frag_buf schema field (added to ooa_schema.sql for this reason) to
.// its associated value in this context.
.// The :sym.p_param:esym. fragment was produced by
.// either a "param_begin_*" or the param_add function.
.// The output fragment has two attributes set: 
.// :sym.param_type:esym. (copied from :sym.p_param.param_type:esym.)
.// and :sym.ref:esym. (copied from :sym.p_param.ref:esym.).
.//
.//============================================================================
.//
.function param_add
  .param frag_ref p_param
  .param string p_param_name
  .param frag_ref p_param_rval
  .assign attr_stmt_id = "${p_param.stmt_id}"
${p_param.body}\
  INSERT INTO V_PAR VALUES ('${attr_stmt_id}','${p_param_rval.val_id}', '${p_param_name}') ;
${p_param_rval.body}\
.end function
.//
.//============================================================================
.//
.// This function is called when a parameter list has been
.// exhausted.  The :sym.p_param:esym. fragment was produced by
.// either a "param_begin_*" or the param_add function.
.// The output fragment has two attributes set:
.// :sym.param_type:esym. (copied from :sym.p_param.param_type:esym.)
.// and :sym.ref:esym. (copied from :sym.p_param.ref:esym.).
.//
.//============================================================================
.//
.function param_end
  .param frag_ref p_param
  .assign attr_stmt_id = "${p_param.stmt_id}"
${p_param.body}\
.end function
.//
.//============================================================================
.//
.// This function is called only once when we start .al_xlate'ing
.// the action :sym.p_actn:esym. (an SM_ACT reference). 
.// This is done to associate a programming entity with the
.// action language usage of "self". In the case of C++, we use "this".
.// Since "self" can be used anywhere a variable of type inst_ref<Object>
.// can be used, we
.// must define all the attributes that may be needed for the output fragment.
.//
.//============================================================================
.//
.function var_declare_self_obj_inst_ref
  .param inst_ref p_actn
  .assign attr_var_name = "self"
  .invoke result = get_blck_id()
  .assign attr_blck_id = result.blck_id
  .select any dt from instances of S_DT where (selected.Name == "inst_ref<Object>")
  .assign attr_dt_id = "${dt.DT_ID}"
  .assign attr_obj_kl = ""
  .invoke result = find_action_under_translation(FALSE)
  .if ( result.type == "SM") 
    .select any act from instances of SM_ACT where ( "${selected.Act_ID}" == result.id )
    .select one ism related by act->SM_SM[R515]->SM_ISM[R517]
    .if ( not_empty ism )
      .// there is no self for an ASM state, so no .else needed
      .select one obj related by ism->O_OBJ[R518]
      .assign attr_obj_kl = obj.Key_Lett   
    .end if
  .elif (result.type == "IO") 
    .select any tfr from instances of O_TFR where ( "${selected.Tfr_ID}" == result.id )
    .select one obj related by tfr->O_OBJ[R115]
    .assign attr_obj_kl = obj.Key_Lett
  .elif (result.type == "AT") 
    .select any attr from instances of O_ATTR where ( "${selected.Attr_ID}" == result.id )
    .select one obj related by attr->O_OBJ[R102]
    .assign attr_obj_kl = obj.Key_Lett
  .end if
  .if ( (result.type == "SM") or ((result.type == "IO") or (result.type == "AT")) )
  INSERT INTO V_VAR VALUES ('${attr_var_name}','${attr_blck_id}','${attr_dt_id}') ;
  INSERT INTO V_INT VALUES ('${attr_var_name}','${attr_blck_id}','${attr_obj_kl}') ;
  .end if
.end function
.//
.//============================================================================
.//
.// This function is called when transient variable :sym.p_var_name:esym. is
.// first encountered. 
.// The type of this variable won't be known until we make the
.// assignment to it. So at this point, the output instance 
.// attribute is not associated with a type.
.//
.//============================================================================
.//
.function var_declare
  .param string p_var_name
  .assign attr_var_name = "${p_var_name}"
  .invoke result = get_blck_id()
  .assign attr_blck_id = result.blck_id
  .assign attr_dt_id = "0"
.end function
.//
.//============================================================================
.//
.// This function is called when we encounter the first usage of an action
.// language variable :sym.p_var_name:esym. 
.// that holds an object instance reference to an object with
.// key letters :sym.p_obj_kl:esym..
.// Because this variable can be used in a myriad of ways, we
.// must define all the attributes that may be needed for the output fragment.
.//
.//============================================================================
.//
.function var_declare_obj_inst_ref
  .param string p_var_name
  .param string p_obj_kl
  .assign attr_val_id = "${info.unique_num}"
  .assign attr_var_name = "${p_var_name}"
  .invoke result = get_blck_id()
  .assign attr_blck_id = result.blck_id
  .assign attr_obj_kl = "${p_obj_kl}"
  .assign attr_var_is_set = "FALSE"
  .select any dt from instances of S_DT where (selected.Name == "inst_ref<Object>")
  .assign attr_dt_id = "${dt.DT_ID}"
  INSERT INTO V_VAR VALUES ('${p_var_name}','${attr_blck_id}','${attr_dt_id}') ;
  INSERT INTO V_INT VALUES ('${p_var_name}','${attr_blck_id}','${p_obj_kl}') ;
.end function
.//
.//============================================================================
.//
.// This function is called when we encounter the first usage of the action
.// language variable :sym.p_var_name:esym. that holds an object instance 
.// reference set of an object with the key letters :sym.p_obj_kl:esym..
.// Because this variable can be used in a myriad of ways, we
.// must define all the attributes that may be needed for the output fragment.
.//
.//============================================================================
.//
.function var_declare_obj_inst_ref_set
  .param string p_var_name
  .param string p_obj_kl
  .assign attr_val_id = "${info.unique_num}"
  .assign attr_var_name = "${p_var_name}"
  .invoke result = get_blck_id()
  .assign attr_blck_id = result.blck_id
  .assign attr_obj_kl = "${p_obj_kl}"
  .assign attr_var_is_set = "TRUE"
  .select any dt from instances of S_DT where (selected.Name == "inst_ref_set<Object>")
  .assign attr_dt_id = "${dt.DT_ID}"
  INSERT INTO V_VAR VALUES ('${p_var_name}','${attr_blck_id}','${attr_dt_id}') ;
  INSERT INTO V_INS VALUES ('${p_var_name}','${attr_blck_id}','${p_obj_kl}') ;
.end function
.//
.//============================================================================
.//
.// This function is called when we first encounter an action language
.// variable :sym.p_var_name:esym. that holds an event instance reference.
.//
.//============================================================================
.//
.function var_declare_evt_inst_ref
  .param string p_var_name
  .assign attr_var_name = "${p_var_name}"
  .invoke result = get_blck_id()
  .assign attr_blck_id = result.blck_id
  .select any dt from instances of S_DT where (selected.Name == "inst<Event>")
  .assign attr_dt_id = "${dt.DT_ID}"
.end function
.//
.//============================================================================
.//
.// This function is called when we encounter an action language
.// select statement from the instances of the object with key letters
.// :sym.p_obj_kl:esym..
.// The parameter :sym.p_is_implicit_decl:esym. is TRUE if
.// this is the first usage of the object instance reference variable
.// represented by the :sym.p_select_var:esym. fragment (which was produced 
.// by one of the "var_declare_*" functions).  
.// The function uses the fragment attribute :sym.p_select_var.var_name:esym..
.// The parameter :sym.p_cardinality:esym. is either "ANY" or "MANY".
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE, 
.// and the attribute :sym.body:esym. will contain the generated code.
.//
.//============================================================================
.//
.function stmt_select_from_instances_of
  .param string p_cardinality
  .param frag_ref p_select_var
  .param boolean p_is_implicit_decl
  .param string p_obj_kl
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_FIO VALUES ('${attr_stmt_id}','${p_select_var.var_name}',\
'${p_select_var.blck_id}','${p_is_implicit_decl}','${p_cardinality}','${p_obj_kl}') ;
  .if (p_is_implicit_decl)
${p_select_var.body}\
  .end if
.end function
.//
.//
.//============================================================================
.//
.// This function is called when we encounter an action language
.// select statement from the instances of the object with key letters
.// :sym.p_obj_kl:esym. and a where clause :sym.p_where_clause:esym. (produced 
.// by the "end_where" function).
.// The parameter :sym.p_is_implicit_decl:esym. is TRUE if
.// this is the first usage of the object instance reference variable
.// represented by the :sym.p_select_var:esym. fragment (which was produced 
.// by one of the "var_declare_*" functions).  
.// The function uses the fragment attribute :sym.p_select_var.var_name:esym..
.// The parameter :sym.p_cardinality:esym. is either "ANY" or "MANY".
.//
.//============================================================================
.//
.function stmt_select_from_instances_of_where
  .param string p_cardinality
  .param frag_ref p_select_var
  .param boolean p_is_implicit_decl
  .param string p_obj_kl
  .param frag_ref p_where_clause
  .assign attr_stmt_id = "${p_where_clause.stmt_id}"
  INSERT INTO ACT_FIW VALUES ('${attr_stmt_id}','${p_select_var.var_name}',\
'${p_select_var.blck_id}','${p_is_implicit_decl}','${p_cardinality}','${p_obj_kl}',\
'${p_where_clause.val_id}') ;
  .if (p_is_implicit_decl)
${p_select_var.body}\
  .end if
${p_where_clause.body}\
.end function
.//
.//============================================================================
.//
.// This function is called after all other functions have
.// been called for a select based on a related by chain :sym.p_chain:esym.. 
.// The parameter :sym.p_is_implicit_decl:esym. is TRUE if
.// this is the first usage of the object instance reference variable
.// represented by the :sym.p_select_var:esym. fragment (which was produced 
.// by one of the "var_declare_*" functions).  
.// If the :sym.p_chain.error_occurred:esym. attribute 
.// (produced by one of the "chain_*" functions) is FALSE, the 
.// :sym.p_select_var.error_occurred:esym. attribute is set to false,
.// and no code is generated.
.// The parameter :sym.p_cardinality:esym. is either "ONE", "ANY" or "MANY".
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE, 
.// and the attribute :sym.body:esym. will contain the generated code.
.//.Textflow break
.// :fb.Note::efb. 
.// The functions "chain_begin", "chain_add_link", and "chain_end" have already
.// been called.
.//
.//============================================================================
.//
.function stmt_select_related_by
  .param string p_cardinality
  .param frag_ref p_select_var
  .param boolean p_is_implicit_decl
  .param frag_ref p_chain
  .assign attr_stmt_id = "${p_chain.stmt_id}"
  INSERT INTO ACT_SEL VALUES ('${p_chain.stmt_id}','${p_select_var.var_name}',\
'${p_select_var.blck_id}','${p_is_implicit_decl}','${p_cardinality}',\
'${p_chain.chain_start}','${p_chain.chain_start_blck_id}') ;
  INSERT INTO ACT_SR VALUES ('${p_chain.stmt_id}') ;
${p_chain.body}
  .if (p_is_implicit_decl)
${p_select_var.body}\
  .end if
.end function
.//
.//============================================================================
.//
.// This function is called after all other functions have
.// been called for a select based on a related by chain :sym.p_chain:esym. with
.// a where clause :sym.p_where_clause:esym. (produced by the "end_where" function). 
.// The parameter :sym.p_is_implicit_decl:esym. is TRUE if
.// this is the first usage of the object instance reference variable
.// represented by the :sym.p_select_var:esym. fragment (which was produced 
.// by one of the "var_declare_*" functions).  
.// If the :sym.p_chain.error_occurred:esym. attribute 
.// (produced by one of the "chain_*" functions) is FALSE, the 
.// :sym.p_select_var.error_occurred:esym. attribute is set to false,
.// and no code is generated.
.// The parameter :sym.p_cardinality:esym. is either "ANY" or "MANY".
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE, 
.// and the attribute :sym.body:esym. will contain the generated code.
.//.Textflow break
.// :fb.Note::efb. 
.// The functions "chain_begin", "chain_add_link", and "chain_end" have already
.// been called.
.//
.//============================================================================
.//
.function stmt_select_related_by_where
  .param string p_cardinality
  .param frag_ref p_select_var
  .param boolean p_is_implicit_decl
  .param frag_ref p_chain
  .param frag_ref p_where_clause
  .assign attr_stmt_id = "${info.unique_num}"
  .assign attr_stmt_id = "${p_chain.stmt_id}"
  INSERT INTO ACT_SEL VALUES ('${p_chain.stmt_id}','${p_select_var.var_name}',\
'${p_select_var.blck_id}','${p_is_implicit_decl}','${p_cardinality}',\
'${p_chain.chain_start}','${p_chain.chain_start_blck_id}') ;
  INSERT INTO ACT_SRW VALUES ('${p_chain.stmt_id}','${p_where_clause.val_id}') ;
${p_chain.body}
  .if (p_is_implicit_decl)
${p_select_var.body}\
  .end if
${p_where_clause.body}\
.end function
.//
.//============================================================================
.// rval_read_transform_param
.//
.//============================================================================
.function rval_read_transform_param
  .param inst_ref p_trans .//  O_TFR
  .param string p_param_name
  .assign attr_val_id = "${info.unique_num}"
  .select any tparm related by p_trans->O_TPARM[R117] where ( selected.Name == p_param_name )
  .assign attr_dt_id = "${tparm.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_PVL VALUES ('${attr_val_id}', '${p_param_name}') ;
  .//
.end function
.//
.//============================================================================
.// rval_read_bridge_param
.//
.//============================================================================
.function rval_read_bridge_param
  .param inst_ref p_brg .//  S_BRG
  .param string p_param_name
  .assign attr_val_id = "${info.unique_num}"
  .select any bparm related by p_brg->S_BPARM[R21] where ( selected.Name == p_param_name )
  .assign attr_dt_id = "${bparm.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_PVL VALUES ('${attr_val_id}', '${p_param_name}') ;
  .//
.end function
.//
.//============================================================================
.// rval_read_synch_service_param
.//
.//============================================================================
.function rval_read_synch_service_param
  .param inst_ref p_ss .//  S_SYNC
  .param string p_param_name
  .assign attr_val_id = "${info.unique_num}"
  .select any sparm related by p_ss->S_SPARM[R24] where ( selected.Name == p_param_name )
  .assign attr_dt_id = "${sparm.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_PVL VALUES ('${attr_val_id}', '${p_param_name}') ;
  .//
.end function
.//
.//============================================================================
.//
.// This function is called when we first encounter a where clause in
.// a select statement for the object with keyletters :sym.p_obj_keyletters:esym..
.// We must declare attributes that we need along the way
.// to determine the type of the selected instances, etc.
.//
.//============================================================================
.//
.function where_begin
  .param string p_obj_keyletters
  .assign attr_stmt_id = "${info.unique_num}"
  .assign attr_obj_kl = p_obj_keyletters
.end function
.//
.//============================================================================
.//
.// This function is called when we are done parsing the where clause
.// :sym.p_where_clause:esym.. The test that the where clause performs
.// is the expression :sym.p_expression.rval:esym..
.//
.//============================================================================
.//
.function where_add_normal
  .param frag_ref p_where_clause
  .param frag_ref p_expression
  .assign attr_stmt_id = "${p_where_clause.stmt_id}"
  .assign attr_val_id = "${p_expression.val_id}"
  .assign attr_obj_kl = p_where_clause.obj_kl
${p_expression.body}\
.end function
.//
.//============================================================================
.//
.// This function is called when we are done parsing the special where clause
.// :sym.p_where_clause:esym.. The identifier of the special where clause
.// is :sym.p_key:esym..
.// The parameters to the special where clause (the question marks in the 
.// where specification) are passed as parameters :sym.p_param:esym. in the 
.// same order as the where specification.
.// This architecture has no special where clauses, so this function does 
.// nothing.
.//
.//============================================================================
.//
.function where_add_special
  .param frag_ref p_where_clause
  .param string p_key
  .param frag_ref p_param
.end function
.//
.//============================================================================
.//
.// This function is called when we are done processing the where clause
.// :sym.p_where_clause:esym..
.//
.//============================================================================
.//
.function where_end
  .param frag_ref p_where_clause
  .assign attr_stmt_id = "${p_where_clause.stmt_id}"
  .assign attr_val_id = "${p_where_clause.val_id}"
  .assign attr_obj_kl = p_where_clause.obj_kl
${p_where_clause.body}\
.end function
.//
.//============================================================================
.//
.// This function is called when we first encounter a related by
.// select chain with cardinality :sym.p_cardinality:esym. (either "ONE", 
.// "ANY", or "MANY") to be placed in the variable :sym.p_select_var:esym.
.// (which was produced by one of the var_declare_* functions). 
.// We must declare attributes that we need along the way
.// to determine the cardinality of the select, etc.
.//
.//============================================================================
.//
.function chain_begin
  .param string p_cardinality
  .param frag_ref p_select_var
  .param boolean p_where_clause
  .assign attr_stmt_id = "${info.unique_num}"
  .assign attr_chain_start = "${p_select_var.var_name}"
  .assign attr_chain_start_blck_id = "${p_select_var.blck_id}"
  .assign attr_link_id = 0
.end function
.//
.//============================================================================
.//
.// This function is called to add a relationship select link across
.// relationship :sym.p_Rnum:esym. to the object with key letters
.// :sym.p_right_obj_kl:esym. using the relationship phrase :sym.p_rel_phrase:esym.
.// (when needed to determine which end of the relationship to follow for
.// reflexive relationships).
.// This link should be added to the relationship chain 
.// :sym.p_chain:esym. (produced by either chain_begin or chain_add_link).
.// The :sym.p_chain.prev_obj_kl:esym. is used by this function.
.// (other attributes are used by called functions)
.// :fb.Note::efb. 
.// In the case of multiple links in the chain, this function is
.// called once for each link from left to right. This necessitates
.// the :sym.post:esym. attribute, which has all the code generated
.// needed for any post processing the link may require.
.//
.//============================================================================
.//
.function chain_add_link
  .param frag_ref p_chain
  .param string p_right_obj_kl
  .param integer p_Rnum
  .param string p_rel_phrase
  .assign attr_stmt_id = "${p_chain.stmt_id}"
  .assign attr_chain_start = "${p_chain.chain_start}"
  .assign attr_chain_start_blck_id = "${p_chain.chain_start_blck_id}"
  .assign attr_link_id = "${info.unique_num}"
  INSERT INTO ACT_LNK VALUES ('${attr_link_id}','${p_right_obj_kl}',\
'${p_rel_phrase}','${p_chain.stmt_id}',${p_Rnum},'${p_chain.link_id}') ;
${p_chain.body}\
.end function
.//============================================================================
.//
.// This function is called when we have completed processing
.// all the links in a relationship chain. All we need to do here is copy 
.// the attributes associated with :sym.p_chain:esym. to the output
.// fragment. The fragment :sym.p_chain:esym. was produced by "chain_add_link".
.//
.//============================================================================
.//
.function chain_end
  .param frag_ref p_chain
  .assign attr_stmt_id = "${p_chain.stmt_id}"
  .assign attr_chain_start = "${p_chain.chain_start}"
  .assign attr_chain_start_blck_id = "${p_chain.chain_start_blck_id}"
${p_chain.body}\
.end function
.//
.//============================================================================
.//
.// This function is called when the transient
.// variable :sym.p_trans_var:esym. (produced by a "var_declare*"
.// function) is assigned an r-value :sym.p_rval:esym. (produced
.// by an "rval_*" function).  If this is the first time
.// used (:sym.p_is_implicit_decl:esym. is TRUE), then we must
.// declare this transient variable.  Also, if the type
.// is currently unknown (:sym.p_trans_var.var_type:esym. is "?"),
.// we must assign the type of the transient
.// variable :sym.(p_trans_var.var_type, p_trans_var.data_type_ref):esym. based
.// on the r-value type.  The output fragment
.// attribute :sym.already_indented:esym. is set to FALSE,
.// and the attribute :sym.body:esym. contains the generated code.
.//.Textflow break
.// :fb.Note::efb. 
.// Assignments to transient variables of type inst_ref_set<Object> are not
.// supported by this architecture.
.//
.//============================================================================
.//
.function stmt_assign_transient_var
  .param frag_ref p_trans_var
  .param boolean p_is_implicit_decl
  .param frag_ref p_rval
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_AT VALUES ('${attr_stmt_id}','${p_rval.val_id}',\
'${p_trans_var.var_name}','${p_trans_var.blck_id}','${p_is_implicit_decl}') ;
  .if (p_is_implicit_decl)
    .if (p_trans_var.dt_id != "0")
${p_trans_var.body}
    .else
  INSERT INTO V_VAR VALUES ('${p_trans_var.var_name}','${p_trans_var.blck_id}','${p_rval.dt_id}') ;
  INSERT INTO V_TRN VALUES ('${p_trans_var.var_name}','${p_trans_var.blck_id}') ;
    .end if
    .assign p_trans_var.dt_id = p_rval.dt_id
  .end if
${p_rval.body}\
.end function
.//
.//============================================================================
.//
.function stmt_assign_bridge_param
  .param inst_ref p_bridge
  .param string p_param
  .param frag_ref p_expression_rval
  .//
  .// TODO:  implement
  .//
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//============================================================================
.//
.function stmt_assign_synch_service_param
  .param inst_ref p_synch_service
  .param string p_param
  .param frag_ref p_expression_rval
  .//
  .// TODO:  implement
  .//
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//============================================================================
.//
.function stmt_assign_transform_param
  .param inst_ref p_trans
  .param string p_param
  .param frag_ref p_expression_rval
  .//
  .// TODO:  implement
  .//
  .assign attr_stmt_id = "${info.unique_num}"
.end function
.//
.//============================================================================
.//
.// This function generates the code for a "For each" statement and
.// places it in the output fragment attribute :sym.body:esym..  The
.// output fragment attribute :sym.already_indented:esym. is set to
.// TRUE.  The parameter :sym.p_inst_ref_var:esym. is the fragment containing
.// information on the loop variable (produced by a "var_declare*"
.// function).  If this is the first time :sym.p_inst_ref_var:esym. is
.// used :sym.(p_is_implicit_decl:esym is TRUE), then we must declare
.// the loop variable.  The parameter :sym.p_inst_ref_set_var:esym. is
.// the fragment containing the information on the set of instances the loop
.// will iterate over (produced by the "var_declare_obj_inst_ref_set"
.// function).  All the statements contained in the for loop have been
.// processed, and the generated code is contained
.// in :sym.p_for_blck.body:esym. (produced by "blck_end" function).
.//
.//============================================================================
.//
.function stmt_for
  .param frag_ref p_inst_ref_var
  .param boolean p_is_implicit_decl
  .param frag_ref p_inst_ref_set_var
  .param frag_ref p_for_blck
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_FOR VALUES ('${attr_stmt_id}','${p_for_blck.blck_id}',\
'${p_inst_ref_var.var_name}','${p_inst_ref_var.blck_id}',\
'${p_inst_ref_set_var.var_name}','${p_inst_ref_set_var.blck_id}',\
'${p_is_implicit_decl}','${p_inst_ref_var.obj_kl}') ;
  .if (p_is_implicit_decl)
${p_inst_ref_var.body}\
  .end if
${p_for_blck.body}\
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to create an instance of an OOA object
.// with key letters :sym.p_obj_kl:esym., and places the generated code in the
.// output fragment attribute :sym.body:esym..  The output fragment attribute
.// :sym.already_indented:esym. is set to FALSE.  The
.// parameter :sym.p_inst_ref_var:esym. is the fragment containing
.// information on the variable (produced by a "var_declare*" function) that
.// will contain the reference to the newly created instance.
.// If this is the first time :sym.p_inst_ref_var:esym. is
.// used :sym.(p_is_implicit_decl:esym. is TRUE), then we must declare
.// the variable as well.
.//
.//============================================================================
.//
.function stmt_create_obj_inst
  .param frag_ref p_inst_ref_var
  .param boolean p_is_implicit_decl
  .param string p_obj_kl
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_CR VALUES ('${attr_stmt_id}','${p_inst_ref_var.var_name}',\
'${p_inst_ref_var.blck_id}','${p_is_implicit_decl}','${p_obj_kl}') ;
  .if (p_is_implicit_decl)
${p_inst_ref_var.body}\
  .end if
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to create an instance of an OOA object
.// with key letters :sym.p_obj_kl:esym.,
.// and places the generated code in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to FALSE.
.// The handle to the created instance is not used or assigned to any
.// variable.
.//
.//============================================================================
.//
.function stmt_create_obj_inst_no_var
  .param string p_obj_kl
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_CNV VALUES ('${attr_stmt_id}','${p_obj_kl}') ;
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to delete an instance of an OOA object
.// referred to by the instance reference variable :sym.p_inst_ref_var:esym.
.// (produced by a "var_declare_*" functions),
.// and places the generated code in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to FALSE.
.//
.//============================================================================
.//
.function stmt_delete_obj_inst
  .param frag_ref p_inst_ref_var
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_DEL VALUES ('${attr_stmt_id}','${p_inst_ref_var.var_name}',\
'${p_inst_ref_var.blck_id}') ;
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to 
.// relate two instances in a non-associative relationship :sym.p_Rnum:esym..  
.// The two instances to relate are in the variables described by
.// fragments :sym.p_inst_ref_var1:esym. and :sym.p_inst_ref_var2:esym.
.// (produced by a "var_declare_*" functions).
.// The :sym.p_rel_phrase:esym. parameter is 
.// the relationship phrase on a reflexive relationship to determine which
.// direction of the relationship to follow.
.// The generated code is in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE.
.//
.//============================================================================
.//
.function stmt_relate
  .param frag_ref p_inst_ref_var1
  .param frag_ref p_inst_ref_var2
  .param integer p_Rnum
  .param string p_rel_phrase
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_REL VALUES ('${attr_stmt_id}','${p_inst_ref_var1.var_name}',\
'${p_inst_ref_var1.blck_id}','${p_inst_ref_var2.var_name}',\
'${p_inst_ref_var2.blck_id}','${p_rel_phrase}',${p_Rnum}) ;
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to 
.// relate two instances in a relationship :sym.p_Rnum:esym. formalized
.// by an associative object. 
.// The two instances to relate are in the variables described by
.// fragments :sym.p_inst_ref_var1:esym. and :sym.p_inst_ref_var2:esym.
.// (produced by a "var_declare_*" functions).
.// The associative object instance is in the variable described by
.// the fragment :sym.p_assoc_inst_ref_var:esym..
.// The :sym.p_rel_phrase:esym. parameter is 
.// the relationship phrase on a reflexive relationship to determine which
.// direction of the relationship to follow.
.// The generated code is in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE.
.//
.//============================================================================
.//
.function stmt_relate_using
  .param frag_ref p_inst_ref_var1
  .param frag_ref p_inst_ref_var2
  .param integer p_Rnum
  .param frag_ref p_assoc_inst_ref_var
  .param string p_rel_phrase
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_RU VALUES ('${attr_stmt_id}','${p_inst_ref_var1.var_name}',\
'${p_inst_ref_var1.blck_id}','${p_inst_ref_var2.var_name}',\
'${p_inst_ref_var2.blck_id}','${p_assoc_inst_ref_var.var_name}',
'${p_assoc_inst_ref_var.blck_id}','${p_rel_phrase}',${p_Rnum}) ;
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to 
.// unrelate two instances in a non-associative relationship :sym.p_Rnum:esym..  
.// The two instances to unrelate are in the variables described by
.// fragments :sym.p_inst_ref_var1:esym. and :sym.p_inst_ref_var2:esym.
.// (produced by a "var_declare_*" functions).
.// The :sym.p_rel_phrase:esym. parameter is 
.// the relationship phrase on a reflexive relationship to determine which
.// direction of the relationship to follow.
.// The generated code is in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE.
.//
.//============================================================================
.//
.function stmt_unrelate
  .param frag_ref p_inst_ref_var1
  .param frag_ref p_inst_ref_var2
  .param integer p_Rnum
  .param string p_rel_phrase
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_UNR VALUES ('${attr_stmt_id}','${p_inst_ref_var1.var_name}',\
'${p_inst_ref_var1.blck_id}','${p_inst_ref_var2.var_name}',\
'${p_inst_ref_var2.blck_id}','${p_rel_phrase}',${p_Rnum}) ;
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to unrelate two instances in a
.// relationship :sym.p_Rnum:esym. formalized by an associative object.  The
.// two instances to unrelate are in the variables described by
.// fragments :sym.p_inst_ref_var1:esym. and :sym.p_inst_ref_var2:esym. (produced
.// by the "var_declare_*" functions).  The associative object instance relating
.// the two instances is in the variable described by the
.// fragment :sym.p_assoc_inst_ref_var:esym..  The :sym.p_rel_phrase:esym. parameter
.// is the relationship phrase on a reflexive relationship to determine which
.// direction of the relationship to follow.  The generated code is in the output
.// fragment attribute :sym.body:esym..  The output fragment
.// attribute :sym.already_indented:esym. is set to TRUE.
.//
.//============================================================================
.//
.function stmt_unrelate_using
  .param frag_ref p_inst_ref_var1
  .param frag_ref p_inst_ref_var2
  .param integer p_Rnum
  .param frag_ref p_assoc_inst_ref_var
  .param string p_rel_phrase
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_URU VALUES ('${attr_stmt_id}','${p_inst_ref_var1.var_name}',\
'${p_inst_ref_var1.blck_id}','${p_inst_ref_var2.var_name}',\
'${p_inst_ref_var2.blck_id}','${p_assoc_inst_ref_var.var_name}',\
'${p_assoc_inst_ref_var.blck_id}','${p_rel_phrase}',${p_Rnum}) ;
.end function
.//
.//============================================================================
.//
.// This function generates the code needed
.// to generate the event :sym.p_evt_label:esym. to the object
.// instance in the variable described by :sym.p_inst_ref_var:esym.
.// (produced by a "var_declare_*" function). The parameters to
.// the event are referenced via the :sym.p_evt_frag:esym. parameter
.// (produced by the "param_end" function).
.// The generated code is in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE.
.//.Textflow break
.// :fb.Note::efb. 
.// The function "param_begin_evt_obj_inst" has already
.// been called as well as the parameter functions param_add and param_end
.// which associate the corresponding supplemental data parameters.
.//.Textflow break
.// :fb.Note::efb. 
.// The parameter :sym.p_evt_label:esym. is not used because the information
.// needed is already stored in :sym.p_evt_frag.ref:esym. (an SM_EVT reference).
.// The parameter is required by the generator.
.//
.//============================================================================
.//
.function stmt_generate_obj_inst
  .param string p_evt_label
  .param frag_ref p_evt_frag
  .param frag_ref p_inst_ref_var
  .assign attr_stmt_id = "${p_evt_frag.stmt_id}"
  INSERT INTO E_GEN VALUES ('${attr_stmt_id}','${p_inst_ref_var.var_name}',\
'${p_inst_ref_var.blck_id}','${p_evt_label}') ;
${p_evt_frag.body}
.end function
.//
.//============================================================================
.//
.// This function generates the code needed
.// to generate the event :sym.p_evt_label:esym. to the assigner for the object
.// with the key letters :sym.p_obj_kl:esym..
.// The parameters to
.// the event are referenced via the :sym.p_evt_frag:esym. parameter
.// (produced by the "param_end" function).
.// The generated code is in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE.
.//.Textflow break
.// :fb.Note::efb. 
.// The function "param_begin_evt_assigner" has already
.// been called as well as the parameter functions param_add and param_end
.// which associate the corresponding supplemental data parameters.
.//.Textflow break
.// :fb.Note::efb. 
.// The parameter :sym.p_evt_label:esym. is not used because the information
.// needed is already stored in :sym.p_evt_frag.ref:esym. (an SM_EVT reference).
.// The parameter is required by the generator.
.//
.//============================================================================
.//
.function stmt_generate_assigner
  .param string p_evt_label
  .param frag_ref p_evt_frag
  .param string p_obj_kl
  .assign attr_stmt_id = "${p_evt_frag.stmt_id}"
  INSERT INTO E_GAR VALUES ('${attr_stmt_id}','${p_evt_label}','${p_obj_kl}') ;
${p_evt_frag.body}\
.end function
.//
.//============================================================================
.//
.// This function generates the code needed
.// to generate the event :sym.p_evt_label:esym. to the external entity 
.// with the key letters :sym.p_ee_keyletters:esym..  The parameters to
.// the event are referenced via the :sym.p_evt_frag:esym. parameter
.// (produced by the "param_end" function).  The generated code is in the
.// output fragment attribute :sym.body:esym..  The output fragment
.// attribute :sym.already_indented:esym. is set to TRUE.
.//.Textflow break
.// :fb.Note::efb. 
.// The function "param_begin_evt_ext_entity" has already
.// been called as well as the parameter functions param_add and param_end
.// which associate the corresponding supplemental data parameters.
.//.Textflow break
.// :fb.Note::efb. 
.// The parameter :sym.p_evt_label:esym. is not used because the information
.// needed is already stored in :sym.p_evt_frag.ref:esym. (an S_EEEVT reference).
.// The parameter is required by the generator.
.//
.//============================================================================
.//
.function stmt_generate_ext_entity
  .param string p_evt_label
  .param frag_ref p_evt_frag
  .param string p_ee_keyletters
  .assign attr_stmt_id = "${p_evt_frag.stmt_id}"
  INSERT INTO E_GEE VALUES ('${attr_stmt_id}','${p_evt_label}','${p_ee_keyletters}') ;
${p_evt_frag.body}\
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to create an instance of the
.// event :sym.p_evt_label:esym. to the object instance in the variable
.// described by :sym.p_obj_inst_ref_var:esym. (produced by a "var_declare_*"
.// function). The parameters to the event are referenced via
.// the :sym.p_evt_frag:esym. parameter (produced by the "param_end"
.// function).
.//.Textflow break
.// :fb.Note::efb. 
.// The function "param_begin_evt_ext_entity" has already
.// been called as well as the parameter functions param_add and param_end
.// which associate the corresponding supplemental data parameters.
.//.Textflow break
.// :fb.Note::efb. 
.// The parameter :sym.p_evt_label:esym. is not used because the information
.// needed is already stored in :sym.p_evt_frag.ref:esym. (an SM_EVT
.// reference).  The parameter is required by the generator.
.//
.//============================================================================
.//
.function stmt_create_evt_obj_inst
  .param frag_ref p_evt_inst_var
  .param boolean p_is_implicit_decl
  .param string p_evt_label
  .param frag_ref p_evt_frag
  .param frag_ref p_obj_inst_ref_var
  .assign attr_stmt_id = "${p_evt_frag.stmt_id}"
  INSERT INTO E_CEI VALUES ('${attr_stmt_id}','${p_evt_inst_var.var_name}',\
'${p_evt_inst_var.blck_id}','${p_is_implicit_decl}','${p_evt_label}',\
'${p_obj_inst_ref_var.var_name}','${p_obj_inst_ref_var.blck_id}') ;
  .if (p_is_implicit_decl)
  INSERT INTO V_VAR VALUES ('${p_evt_inst_var.var_name}',\
'${p_evt_inst_var.blck_id}','') ;
  .end if
${p_evt_frag.body}
.end function
.//
.//
.//============================================================================
.//
.// This function generates the code needed
.// to create an instance of the event :sym.p_evt_label:esym. to the assigner
.// for the object with keyletters :sym.p_obj_keyletters:esym..
.// The parameters to
.// the event are referenced via the :sym.p_param:esym. parameter
.// (produced by the "param_end" function).
.//.Textflow break
.// :fb.Note::efb. 
.// The function "param_begin_evt_assigner" has already
.// been called as well as the parameter functions param_add and param_end
.// which associate the corresponding supplemental data parameters.
.//.Textflow break
.// :fb.Note::efb. 
.// The parameter :sym.p_evt_label:esym. is not used because the information
.// needed is already stored in :sym.p_param.ref:esym. (an SM_EVT reference).
.// The parameter is required by the generator.
.//
.//============================================================================
.//
.function stmt_create_evt_assigner
  .param frag_ref p_evt_inst_var
  .param boolean p_is_implicit_decl
  .param string p_evt_label
  .param frag_ref p_param
  .param string p_obj_keyletters
  .assign attr_stmt_id = "${p_param.stmt_id}"
  INSERT INTO E_CEA VALUES ('${attr_stmt_id}','${p_evt_inst_var.var_name}','${p_evt_inst_var.blck_id}','${p_is_implicit_decl}','${p_evt_label}') ;
  .if (p_is_implicit_decl)
  INSERT INTO V_VAR VALUES ('${p_evt_inst_var.var_name}','${p_evt_inst_var.blck_id}','') ;
  .end if
${p_param.body}
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to 
.// to create an instance of a creation event :sym.p_evt_label:esym. to the
.// object with keyletters :sym.p_obj_keyletters:esym..
.// The parameters to
.// the event are referenced via the :sym.p_param:esym. parameter
.// (produced by the "param_end" function).
.// The created event instance is placed in the variable described
.// by the fragment :sym.p_evt_inst_var:esym. (produced by the
.// "var_declare_evt_inst" function).
.// If this is the first time :sym.p_evt_inst_var:esym. is used
.// (:sym.p_is_implicit_decl:esym is TRUE), 
.// then we must declare the variable as well. 
.// The generated code is in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE.
.//.Textflow break
.// :fb.Note::efb. 
.// The function "param_begin_evt_creation" has already
.// been called as well as the parameter functions param_add and param_end
.// which associate the corresponding supplemental data parameters.
.//.Textflow break
.// :fb.Note::efb. 
.// The parameter :sym.p_evt_label:esym. is not used because the information
.// needed is already stored in :sym.p_param.ref:esym. (an SM_EVT reference).
.// The parameter is required by the generator.
.//
.//============================================================================
.//
.function stmt_create_evt_creation
  .param frag_ref p_evt_inst_var
  .param boolean p_is_implicit_decl
  .param string p_evt_label
  .param frag_ref p_param
  .param string p_obj_keyletters
  .assign attr_stmt_id = "${p_param.stmt_id}"
  INSERT INTO E_CEC VALUES ('${attr_stmt_id}','${p_evt_inst_var.var_name}','${p_evt_inst_var.blck_id}','${p_is_implicit_decl}','${p_evt_label}') ;
  .if (p_is_implicit_decl)
  INSERT INTO V_VAR VALUES ('${p_evt_inst_var.var_name}','${p_evt_inst_var.blck_id}','') ;
  .end if
${p_param.body}
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to 
.// to create an instance of the event :sym.p_evt_label:esym. to the external
.// entity with keyletters :sym.p_ext_entity_keyletters:esym..
.// The external entity's type must be "non_ooa".
.// The parameters to
.// the event are referenced via the :sym.p_param:esym. parameter
.// (produced by the "param_end" function).
.//.Textflow break
.// :fb.Note::efb. 
.// The function "param_begin_evt_ext_entity" has already
.// been called as well as the parameter functions param_add and param_end
.// which associate the corresponding supplemental data parameters.
.//.Textflow break
.// :fb.Note::efb. 
.// The parameter :sym.p_evt_label:esym. is not used because the information
.// needed is already stored in :sym.p_param.ref:esym. (an SM_EVT reference).
.// The parameter is required by the generator.
.//
.//============================================================================
.//
.function stmt_create_evt_ext_entity
  .param frag_ref p_evt_inst_var
  .param boolean p_is_implicit_decl
  .param string p_evt_label
  .param frag_ref p_param
  .param string p_ext_entity_keyletters
  .assign attr_stmt_id = "${p_param.stmt_id}"
  INSERT INTO E_CEE VALUES ('${attr_stmt_id}','${p_evt_inst_var.var_name}','${p_evt_inst_var.blck_id}','${p_is_implicit_decl}','${p_evt_label}') ;
  .if (p_is_implicit_decl)
  INSERT INTO V_VAR VALUES ('${p_evt_inst_var.var_name}','${p_evt_inst_var.blck_id}','') ;
  .end if
${p_param.body}
.end function
.//
.//============================================================================
.//
.// This function generates the code needed
.// to generate the creation event :sym.p_evt_label:esym. to the object
.// with the key letters :sym.p_obj_kl:esym..
.// The parameters to
.// the event are referenced via the :sym.p_evt_frag:esym. parameter
.// (produced by the "param_end" function).
.//.Textflow break
.// :fb.Note::efb. 
.// The function "param_begin_evt_creation" has already
.// been called as well as the parameter functions param_add and param_end
.// which associate the corresponding supplemental data parameters.
.//.Textflow break
.// :fb.Note::efb. 
.// The parameter :sym.p_evt_label:esym. is not used because the information
.// needed is already stored in :sym.p_evt_frag.ref:esym. (an SM_EVT reference).
.// The parameter is required by the generator.
.//
.//============================================================================
.//
.function stmt_generate_creation
  .param string p_evt_label
  .param frag_ref p_evt_frag
  .param string p_obj_kl
  .assign attr_stmt_id = "${p_evt_frag.stmt_id}"
  INSERT INTO E_GEC VALUES ('${attr_stmt_id}','${p_evt_label}','${p_obj_kl}') ;
${p_evt_frag.body}\
.end function
.//
.//============================================================================
.//
.// This function generates the code needed
.// to generate an event using an already created event instance variable
.// in the fragment :sym.p_evt_inst_var_frag:esym. (produced by the function
.// "var_declare_evt_inst").
.// The generated code is in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE.
.//
.//============================================================================
.//
.function stmt_generate_evt_inst
  .param frag_ref p_evt_inst_var_frag
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO E_GPR VALUES('${attr_stmt_id}','${p_evt_inst_var_frag.var_name}',\
'${p_evt_inst_var_frag.blck_id}') ;
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to 
.// assign an r-value :sym.p_expression_rval:esym. (produced by an "rval_*" function)
.// to the attribute :sym.p_attr_name:esym. of the object instance contained
.// in the variable :sym.p_inst_ref_var:esym. (produced by a "var_declare*" function).
.//.Textflow break
.// :fb.Note::efb. 
.// If the r-value is an enumeration, it's value and type
.// (attribute :sym.p_expression_rval.rval:esym. and :sym.p_expression_rval.data_type_ref:esym.)
.// will be changed (on the fly)
.// from the string representation of the action language to the
.// enumeration class of the architecture.
.//
.//============================================================================
.//
.function stmt_assign_obj_attr
  .param frag_ref p_inst_ref_var
  .param string p_attr_name
  .param frag_ref p_expression_rval 
  .assign attr_stmt_id = "${info.unique_num}"
  .assign is_mda_result = false
  .if ( "$l{p_inst_ref_var.var_name}" == "self" )
    .invoke result = find_action_under_translation(FALSE)
    .if ( result.type == "AT" )
      .select any attr from instances of O_ATTR where ( "${selected.Attr_ID}" == result.id )
      .if ( p_attr_name == attr.Name )  
        .assign is_mda_result = true
      .end if
    .end if
  .end if
  .if ( is_mda_result )
  INSERT INTO ACT_RET VALUES ('${attr_stmt_id}', '${p_expression_rval.val_id}') ;
  .else
  INSERT INTO ACT_AI VALUES ('${attr_stmt_id}','${p_expression_rval.val_id}',\
'${p_inst_ref_var.var_name}','${p_inst_ref_var.blck_id}','${p_attr_name}') ;
  .end if
${p_expression_rval.body}\
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to 
.// invoke the transformer :sym.p_method_name:esym. belonging to the
.// object with key letters :sym.p_obj_kl:esym. 
.// that doesn't have a return value. 
.// The parameters to
.// the transformer are referenced via the :sym.p_param:esym. parameter
.// (produced by the "param_end" function).
.//.Textflow break
.// :fb.Note::efb. 
.// The function
.// "param_begin_transform" has already been called as well as the parameter
.// functions param_add and param_end  which associate the corresponding
.// parameters to their r-value.
.//
.//============================================================================
.//
.function stmt_transform_void
  .param string p_obj_kl
  .param string p_method_name
  .param frag_ref p_param
  .assign attr_stmt_id = "${p_param.stmt_id}"
  INSERT INTO ACT_TFM VALUES ('${attr_stmt_id}','${p_obj_kl}','${p_method_name}') ;
${p_param.body}\
.end function
.//
.//============================================================================
.// rval_transform_instance
.//
.// This function is invoked when an instance-based transformer invocation that 
.// has a return value is accessed in the BPAL. Note that the parameter list 
.// for the transformer ('p_xform_param' fragment) will have been completely
.// processed prior to the invocation of 'rval_transform_instance'.
.//============================================================================
.function rval_transform_instance
  .param frag_ref p_obj_inst_ref_var
  .param string p_method_name
  .param frag_ref p_xform_param
  .//
  .assign attr_stmt_id = "${p_xform_param.stmt_id}"
  INSERT INTO ACT_TFM VALUES ('${attr_stmt_id}','${p_obj_inst_ref_var.obj_kl}','${p_method_name}','${p_obj_inst_ref_var.blck_id}','${p_obj_inst_ref_var.var_name}') ;
  .assign attr_val_id = "${info.unique_num}"
  .select any obj from instances of O_OBJ where ( selected.Key_Lett == p_obj_inst_ref_var.obj_kl )  
  .select any trans related by obj->O_TFR[R115] where ( selected.Name == p_method_name )
  .assign attr_dt_id = "${trans.DT_ID}"
  .invoke blck_id = get_blck_id()
  INSERT INTO V_VAL VALUES ('${attr_val_id}','','${attr_dt_id}','${blck_id.blck_id}') ;
  INSERT INTO V_TRV VALUES ('${attr_val_id}','${attr_stmt_id}') ;
${p_xform_param.body}\
.end function
.//
.//============================================================================
.// stmt_return_void
.//
.//============================================================================
.function stmt_return_void
  .//
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_RET VALUES ('${attr_stmt_id}', '0') ;
.end function
.//
.//
.//============================================================================
.// stmt_return_rval
.//
.//============================================================================
.function stmt_return_rval
  .param frag_ref p_return_rval
  .//
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_RET VALUES ('${attr_stmt_id}', '${p_return_rval.val_id}') ;
${p_return_rval.body}\
.end function
.//
.//============================================================================
.// stmt_transform_instance_void
.//
.// This function is invoked when an instance-based transformer invocation that 
.// does not have a return value is accessed in the BPAL. Note that the 
.// parameter list for the transformer ('p_xform_param' fragment) will have 
.// been completely processed prior to the invocation of 
.// 'stmt_transform_instance_void'.
.//============================================================================
.function stmt_transform_instance_void
  .param frag_ref p_obj_inst_ref_var
  .param string p_method_name
  .param frag_ref p_xform_param
  .assign attr_stmt_id = "${p_xform_param.stmt_id}"
  INSERT INTO ACT_TFM VALUES ('${attr_stmt_id}','${p_obj_inst_ref_var.obj_kl}','${p_method_name}','${p_obj_inst_ref_var.blck_id}','${p_obj_inst_ref_var.var_name}') ;
${p_xform_param.body}\
.end function
.//
.//
.//============================================================================
.//
.// This function generates the code for the if block of an "If" statement.
.//
.//============================================================================
.//
.function stmt_if
  .param frag_ref p_condition_rval
  .param frag_ref p_if_blck
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_IF VALUES ('${attr_stmt_id}','${p_if_blck.blck_id}','${p_condition_rval.val_id}') ;
${p_condition_rval.body}\
${p_if_blck.body}\
.end function
.//
.//============================================================================
.//
.// This function generates the code for the else block of an "If" statement and
.// places it in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE.
.// All the statements contained in the block associated with the
.// else have been processed.
.//
.//============================================================================
.//
.function stmt_else
  .param frag_ref p_else_blck
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_E VALUES ('${attr_stmt_id}','${p_else_blck.blck_id}','') ;
${p_else_blck.body}\
.end function
.//
.//============================================================================
.//
.// This function generates the code for the elif if block of 
.// an "If" statement.
.//
.//============================================================================
.//
.function stmt_elif
  .param frag_ref p_condition_rval
  .param frag_ref p_elif_blck
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_EL VALUES ('${attr_stmt_id}','${p_elif_blck.blck_id}','${p_condition_rval.val_id}','') ;
${p_condition_rval.body}\
${p_elif_blck.body}\
.end function
.//
.//
.//============================================================================
.//
.// This function generates the code for a "While" statement.
.//
.//============================================================================
.//
.function stmt_while
  .param frag_ref p_condition_rval
  .param frag_ref p_while_blck
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_WHL VALUES ('${attr_stmt_id}','${p_condition_rval.val_id}',\
'${p_while_blck.blck_id}') ;
${p_condition_rval.body}\
${p_while_blck.body}\
.end function
.//
.//
.//============================================================================
.//
.// This function generates the code for a "Break" statement and
.// places it in the output fragment attribute :sym.body:esym..
.//
.//============================================================================
.//
.function stmt_break
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_BRK VALUES ('${attr_stmt_id}');
.end function
.//
.//
.//============================================================================
.//
.// This function generates the code for a "Continue" statement and
.// places it in the output fragment attribute :sym.body:esym..
.// The output fragment attribute :sym.already_indented:esym. is set to TRUE.
.//
.//============================================================================
.//
.function stmt_continue
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_CON VALUES ('${attr_stmt_id}');
.end function
.//
.//============================================================================
.//
.// This function generates the code needed to 
.// invoke the bridge function :sym.p_method_name:esym. belonging to the
.// external entity with key letters :sym.p_ee_keyletters:esym. 
.// that doesn't have a return value. 
.// The parameters to
.// the bridge function are referenced via the :sym.p_param:esym. parameter
.// (produced by the "param_end" function).
.//.Textflow break
.// :fb.Note::efb. 
.// The fragment function
.// "param_begin_bridge" has already been called as well as the parameter
.// functions param_add and param_end which associate the corresponding
.// parameters to their r-value.
.//
.//============================================================================
.//
.function stmt_bridge_void
  .param string p_ee_keyletters
  .param string p_method_name
  .param frag_ref p_param
  .assign attr_stmt_id = "${p_param.stmt_id}"
  INSERT INTO ACT_BRG VALUES ('${attr_stmt_id}','${p_ee_keyletters}','${p_method_name}') ;
${p_param.body}\
.end function
.//
.//
.function stmt_synch_service_void
  .param string p_method_name
  .param frag_ref p_param
  .assign attr_stmt_id = "${p_param.stmt_id}"
  INSERT INTO ACT_FNC VALUES ('${attr_stmt_id}','${p_method_name}') ;
${p_param.body}\
.end function
.//
.//============================================================================
.//
.// This function is called when the BP-AL statement ':sym.control stop:esym.' is
.// encountered.  The parameter p_cmd will contain the string ":sym.STOP:esym.".  If
.// other control statements are added in the future, the string will
.// contain the command.
.//
.//============================================================================
.//
.function stmt_control
  .param string p_cmd
  .assign attr_stmt_id = "${info.unique_num}"
  INSERT INTO ACT_CTL VALUES ('${attr_stmt_id}','${p_cmd}');
.end function
