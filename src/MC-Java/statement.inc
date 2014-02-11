.//====================================================================
.//
.// File:      $RCSfile: statement.inc,v $
.// Version:   $Revision: 1.44 $
.// Modified:  $Date: 2013/01/10 23:16:40 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.// ----------------------------------------------------------
.// gen for statements
.// ----------------------------------------------------------
.function gen_for_statements
.select many for_statements from instances of ACT_FOR
.for each for_statement in for_statements
  .select one statement related by for_statement->ACT_SMT[R603]
  .assign statement.control = "TRUE"
  .assign statement.controlled_block = "${for_statement.block_id}"
  .invoke result = gen_for_statement(for_statement)
  .assign statement.buffer="${result.body}"
  .invoke result = gen_end_control_statement()
  .assign statement.buffer2="${result.body}"
.end for
.end function
.// --------------------------------------------------------
.// gen for statement
.// --------------------------------------------------------
.function gen_for_statement
  .param inst_ref statement
  .assign nestlevel = "${info.unique_num}"
  .select any tgt_obj from instances of O_OBJ where (selected.Key_Lett == statement.Target_Obj_KL)
  .if ( statement.is_implicit == "TRUE" )
    .invoke cn = get_class_name( tgt_obj )
${cn.body}  v_${statement.Loop_Variable_Name} = null;
  .end if
for ( int i${nestlevel} = 0; i${nestlevel} < v_${statement.Set_Variable_Name}.length; i${nestlevel}++)
{
  v_${statement.Loop_Variable_Name} = v_${statement.Set_Variable_Name}[i${nestlevel}] ;
.end function
.//
.// ----------------------------------------------------------
.// gen if statements
.// ----------------------------------------------------------
.function gen_if_statements
.select many if_statements from instances of ACT_IF
.for each if_statement in if_statements
  .select one statement related by if_statement->ACT_SMT[R603]
  .assign statement.control = "TRUE"
  .assign statement.controlled_block = "${if_statement.block_id}"
  .invoke result = gen_if_statement(if_statement)
  .assign statement.buffer="${result.body}"
  .invoke result = gen_end_control_statement()
  .assign statement.buffer2="${result.body}"
.end for
.end function
.// --------------------------------------------------------
.// gen if statement
.// --------------------------------------------------------
.function gen_if_statement
  .param inst_ref statement
  .select one test related by statement->V_VAL[R625]
if ( ${test.buffer} )
{
.end function
.// ----------------------------------------------------------
.// gen while statements
.// ----------------------------------------------------------
.function gen_while_statements
.select many while_statements from instances of ACT_WHL
.for each while_statement in while_statements
  .select one statement related by while_statement->ACT_SMT[R603]
  .assign statement.control = "TRUE"
  .assign statement.controlled_block = "${while_statement.block_id}"
  .invoke result = gen_while_statement(while_statement)
  .assign statement.buffer="${result.body}"
  .invoke result = gen_end_control_statement()
  .assign statement.buffer2="${result.body}"
.end for
.end function
.// --------------------------------------------------------
.// gen while statement
.// --------------------------------------------------------
.function gen_while_statement
  .param inst_ref statement
  .select one test related by statement->V_VAL[R626]
while ( ${test.buffer} )
{
.end function
.// --------------------------------------------------------
.// gen end control statement
.// --------------------------------------------------------
.function gen_end_control_statement
}
.end function
.// ----------------------------------------------------------
.// gen else statements
.// ----------------------------------------------------------
.function gen_else_statements
.select many else_statements from instances of ACT_E
.for each else_statement in else_statements
  .select one statement related by else_statement->ACT_SMT[R603]
  .assign statement.control = "TRUE"
  .assign statement.controlled_block = "${else_statement.block_id}"
  .invoke result = gen_else_statement()
  .assign statement.buffer="${result.body}"
  .invoke result = gen_end_control_statement()
  .assign statement.buffer2="${result.body}"
.end for
.end function
.// --------------------------------------------------------
.// gen else statement
.// --------------------------------------------------------
.function gen_else_statement
else
{
.end function
.//
.// ----------------------------------------------------------
.// gen elif statements
.// ----------------------------------------------------------
.function gen_elif_statements
.select many elif_statements from instances of ACT_EL
.for each elif_statement in elif_statements
  .select one statement related by elif_statement->ACT_SMT[R603]
  .assign statement.control = "TRUE"
  .assign statement.controlled_block = "${elif_statement.block_id}"
  .invoke result = gen_elif_statement(elif_statement)
  .assign statement.buffer="${result.body}"
  .invoke result = gen_end_control_statement()
  .assign statement.buffer2="${result.body}"
.end for
.end function
.// --------------------------------------------------------
.// gen elif statement
.// --------------------------------------------------------
.function gen_elif_statement
  .param inst_ref statement
  .select one test related by statement->V_VAL[R659]
else if ( ${test.buffer} )
{
.end function
.//
.// --------------------------------------------------------
.// assignment to attribute statements
.// --------------------------------------------------------
.function gen_asgn_attr_statements
.select many assign_statements from instances of ACT_AI
.for each assign_statement in assign_statements
  .select one statement related by assign_statement->ACT_SMT[R603]
  .invoke result = gen_asgn_attr_statement(assign_statement)
  .assign statement.buffer="${result.body}"
.end for
.end function
.//
.// --------------------------------------------------------
.// gen_asgn_attr_statement
.// --------------------------------------------------------
.function gen_asgn_attr_statement
  .param inst_ref statement
	.//
	.// resolve the value's core data type
	.select one value related by statement->V_VAL[R609]
	.select any valueType from instances of S_DT where ("${selected.DT_ID}" == value.DT_ID)
	.invoke result = getCoreDataType(valueType);
	.assign valueType = result.coreType;
	.//
    .// if the value is of the 'real' type
	.assign intCast1 = ""
	.assign intCast2 = ""
	.if (valueType.Name == "real")
		.// resolve the assignee's core data type
		.select one instance related by statement->V_VAR[R629]->V_INT[R814];
		.select any class from instances of O_OBJ where (selected.Key_Lett == instance.Key_Lett)
		.select any attr from instances of O_ATTR where (selected.Name == statement.Member_Variable_Name)
	    .select any attrType from instances of S_DT where (selected.DT_ID == attr.DT_ID)
		.invoke result = getCoreDataType(attrType);
		.assign varType = result.coreType;
	    .//
	    .// if the assignee is of the integer type
		.if (varType.Name == "integer")
			.// cast the value to an int, to avoid a "possible loss of precision"
			.// syntax error in the generated code
			.assign intCast1 = "(int)("
			.assign intCast2 = ")"
		.end if
	.end if 
	.//
  .if ("$l{statement.Instance_Variable_Name}" != "self")
if (v_${statement.Instance_Variable_Name} != null) {
  v_${statement.Instance_Variable_Name}.\
  .end if
set$cr{statement.Member_Variable_Name}( \
  .select one value related by statement->V_VAL[R609]
${intCast1}${value.buffer}${intCast2}) ;
  .if ("$l{statement.Instance_Variable_Name}" != "self")
}
else {
  Throwable t = new Throwable();
  t.fillInStackTrace();
  CorePlugin.logError("Attribute write attempted on null instance.", t);
}
  .end if
.end function
.//
.// --------------------------------------------------------
.// assignment to transient statements
.// --------------------------------------------------------
.function gen_asgn_trans_statements
.select many assign_trans_statements from instances of ACT_AT
.for each assign_trans_statement in assign_trans_statements
  .invoke result = gen_asgn_trans_statement(assign_trans_statement)
  .select one statement related by assign_trans_statement->ACT_SMT[R603]
  .assign statement.buffer="${result.body}"
.end for
.end function
.//
.// --------------------------------------------------------
.// gen_asgn_trans_statement
.// --------------------------------------------------------
.function gen_asgn_trans_statement
  .param inst_ref statement
  .select one value related by statement->V_VAL[R635]
  .select any valueType from instances of S_DT where ("${selected.DT_ID}" == value.DT_ID)
  .if (statement.is_implicit == "TRUE")
    .if ( valueType.Name == "inst_ref<Object>" )
      .select one vint related by value->V_TVL[R801]->V_VAR[R805]->V_INT[R814]
      .select any new_obj from instances of O_OBJ where (selected.Key_Lett == vint.Key_Lett)
      .invoke cn = get_class_name( new_obj )
${cn.body} \
    .elif ( valueType.Name == "inst_ref_set<Object>" )
      .select one vins related by value->V_TVL[R801]->V_VAR[R805]->V_INS[R814]
      .select any new_obj from instances of O_OBJ where (selected.Key_Lett == vins.Key_Lett)
      .invoke cn = get_class_name( new_obj )
${cn.body} [] \
    .else
      .invoke result = do_type(valueType)
${result.body} \
    .end if
  .end if
	.//
	.// resolve the value's core data type
    .select one valueCoreType related by valueType->S_UDT[R17]->S_CDT[R18]->S_DT[R17]
    .if (not_empty valueCoreType) 
    	.assign valueType = valueCoreType;
    .end if
    .//
    .// if the value is of the 'real' type
	.assign intCast1 = ""
	.assign intCast2 = ""
	.if (valueType.Name == "real")
		.// resolve the assignee's core data type
		.select any var related by statement->V_VAR[R636];
	    .select any varType from instances of S_DT where ("${selected.DT_ID}" == var.DT_ID)
		.invoke result = getCoreDataType(varType);
		.assign varType = result.coreType;
	    .//
	    .// if the assignee is of the integer type
		.if (varType.Name == "integer")
			.// cast the value to an int, to avoid a "possible loss of precision"
			.// syntax error in the generated code
			.assign intCast1 = "(int)("
			.assign intCast2 = ")"
		.end if
	.end if
	.//
v_${statement.Target_Variable_Name} = ${intCast1}${value.buffer}${intCast2} ;
.end function
.// --------------------------------------------------------
.// create instance statements
.// --------------------------------------------------------
.function gen_create_instance_statements
  .param string main_class_name
  .param boolean output_change_messages
  .select many create_instance_statements from instances of ACT_CR
  .for each create_instance_statement in create_instance_statements
    .invoke result = gen_create_instance_statement(create_instance_statement, main_class_name, output_change_messages)
    .select one statement related by create_instance_statement->ACT_SMT[R603]
    .assign statement.buffer="${result.body}"
  .end for
.end function
.//
.// --------------------------------------------------------
.// create instance statement
.// --------------------------------------------------------
.function gen_create_instance_statement
  .param inst_ref statement
  .param string main_class_name
  .param boolean output_change_messages
  .invoke package = get_package()
  .select any new_obj from instances of O_OBJ where (selected.Key_Lett == statement.New_Obj_KL)
  .invoke cn = get_class_name( new_obj )
  .if (statement.is_implicit == "TRUE")
${cn.body} \
  .end if
v_${statement.Variable_Name} = new ${cn.body}(modelRoot) ;
    .select one action related by statement->ACT_SMT[R603]->ACT_BLK[R602]->ACT_ACT[R601]
    .if (not_empty action)
      .select one cr_statement related by statement->ACT_SMT[R603];
      .invoke prop_delta_obj = notifies_changes(new_obj, "O_OBJ")
      .if ( output_change_messages AND prop_delta_obj.result)
${package.application_root_class}.getDefaultInstance().fireModelElementCreated(new BaseModelDelta(Modeleventnotification_c.DELTA_NEW, v_${statement.Variable_Name}));
      .end if
    .end if
.end function
.//
.// --------------------------------------------------------
.// delete instance statements
.// --------------------------------------------------------
.function gen_delete_instance_statements
  .param string main_class_name
  .param boolean output_change_messages
  .select many delete_statements from instances of ACT_DEL
  .assign del_count = 0
  .for each delete_statement in delete_statements
    .invoke result = gen_delete_instance_statement(delete_statement, main_class_name, output_change_messages, del_count)
    .assign del_count = del_count + 1
    .select one statement related by delete_statement->ACT_SMT[R603];
    .assign statement.buffer = "${result.body}"
  .end for
.end function
.//
.// --------------------------------------------------------
.// delete instance statement
.// --------------------------------------------------------
.function gen_delete_instance_statement
  .param inst_ref delete_statement
  .param string main_class_name
  .param boolean output_change_messages
  .param integer del_count
    .invoke package = get_package()
    .select one statement related by delete_statement->ACT_SMT[R603]
    .// MC-Java only creates V_INT instances
    .select any del_var related by delete_statement->V_VAR[R634]->V_INT[R814]
    .if ( empty del_var )
       .print "ERROR: delete statement doesn't have a related variable"
       .exit 100
    .end if
    .select any del_obj from instances of O_OBJ where (selected.Key_Lett == del_var.Key_Lett)
    .invoke prop_delta_obj = notifies_changes(del_obj, "O_OBJ")
    .if ("$l{delete_statement.Variable_Name}" != "self")
if (v_${delete_statement.Variable_Name} != null) {
      .if ( output_change_messages AND prop_delta_obj.result)
// get the location of this element in the instance list
// before deleting
if ( v_${delete_statement.Variable_Name}.delete() ) {
    ${package.application_root_class}.getDefaultInstance().fireModelElementDeleted(new BaseModelDelta(Modeleventnotification_c.DELTA_DELETE, v_${delete_statement.Variable_Name}));
}
      .else
v_${delete_statement.Variable_Name}.delete() ;
      .end if
}
else {
  Throwable t = new Throwable();
  t.fillInStackTrace();
  CorePlugin.logError("Delete attempted on null instance.", t);
}
    .else
      .if ( output_change_messages AND prop_delta_obj.result)
if ( delete() ) {
    ${package.application_root_class}.getDefaultInstance().fireModelElementDeleted(new BaseModelDelta(Modeleventnotification_c.DELTA_DELETE, this));
}
      .else
delete() ;
      .end if
    .end if
.end function
.// --------------------------------------------------------
.// create event instance statements
.// --------------------------------------------------------
.function gen_create_event_instance_statements
  .select many create_event_instance_statements from instances of E_CEI
  .for each create_event_instance_statement in create_event_instance_statements
    .invoke result = gen_create_event_instance_statement(create_event_instance_statement)
    .select one statement related by create_event_instance_statement->ACT_SMT[R700]
    .assign statement.buffer="${result.body}"
  .end for
.end function
.//
.// --------------------------------------------------------
.// create event instance statement
.// --------------------------------------------------------
.function gen_create_event_instance_statement
  .param inst_ref statement
  .select any new_evt from instances of SM_EVT where (selected.Drv_Lbl == statement.Event_Label)
  .select one object related by new_evt->SM_SM[R502]->SM_ISM[R517]->O_OBJ[R518]
  .if (empty object)
    .select one object related by new_evt->SM_SM[R502]->SM_ASM[R517]->O_OBJ[R519]
  .end if
  .if (statement.is_implicit == "TRUE")
EV_$u_{object.Name}_$u_{new_evt.Mning} \
  .end if
v_${statement.Variable_Name} = new EV_$u_{object.Name}_$u_{new_evt.Mning}() ;
v_${statement.Variable_Name}.recipient = \
  .if ("$l{statement.Recipient_Variable_Name}" != "self")
v_${statement.Recipient_Variable_Name} ;
  .else
this ;
  .end if
.end function
.//
.// --------------------------------------------------------
.// relate statements
.// --------------------------------------------------------
.function gen_relate_statements
  .select many rel_statements from instances of ACT_REL
  .for each rel_statement in rel_statements
    .invoke result = gen_relate_statement(rel_statement)
    .select one statement related by rel_statement->ACT_SMT[R603]
    .assign statement.buffer = "${result.body}"
  .end for
.end function
.//
.// --------------------------------------------------------
.// relate statement
.// --------------------------------------------------------
.function gen_relate_statement
  .param inst_ref rel_statement
  .assign one_var_name = "v_" + rel_statement.One_Side_Variable_Name
  .assign oth_var_name = "v_" + rel_statement.Other_Side_Variable_Name
  .if ("$l{rel_statement.One_Side_Variable_Name}" == "self")
    .assign one_var_name = "this"
  .end if
  .if ( "$l{rel_statement.Other_Side_Variable_Name}" == "self")
    .assign oth_var_name = "this"
  .end if
  .//
  .select one rel related by rel_statement->R_REL[R653]
  .invoke is_refl = is_reflexive( rel )
  .assign rel_phrase = ""
  .if ( is_refl.result )
    .assign rel_phrase = "$cr{rel_statement.relationship_phrase}"
  .end if
  .if ("$l{rel_statement.One_Side_Variable_Name}" != "self")
if (${one_var_name} != null) {
  .end if
  ${one_var_name}.relateAcrossR${rel_statement.Numb}To${rel_phrase}(${oth_var_name});
  .if ("$l{rel_statement.One_Side_Variable_Name}" != "self")
}
else {
  Throwable t = new Throwable();
  t.fillInStackTrace();
  CorePlugin.logError("Relate attempted on null left hand instance.", t);
}
  .end if
.end function
.//
.// --------------------------------------------------------
.// relate using statements
.// --------------------------------------------------------
.function gen_relate_using_statements
  .select many rel_using_stmts from instances of ACT_RU
  .for each rel_using_stmt in rel_using_stmts
    .invoke result = gen_relate_using_statement(rel_using_stmt)
    .select one statement related by rel_using_stmt->ACT_SMT[R603]
    .assign statement.buffer = "${result.body}"
  .end for
.end function
.//
.// --------------------------------------------------------
.// relate using statement
.// --------------------------------------------------------
.function gen_relate_using_statement
  .param inst_ref rel_using_stmt
  .//
  .select one rel related by rel_using_stmt->R_REL[R654]
  .invoke is_refl = is_reflexive( rel )
  .assign one_rel_phrase = ""
  .assign oth_rel_phrase = ""
  .if ( is_refl.result )
    .select one aone related by rel->R_ASSOC[R206]->R_AONE[R209]
    .select one aoth related by rel->R_ASSOC[R206]->R_AOTH[R210]
    .select one one_obj related by rel_using_stmt->V_VAR[R617]->V_INT[R814]->O_OBJ[R815]
    .if ( one_obj.Obj_ID == aone.Obj_ID )
      .if ( aone.Txt_Phrs == rel_using_stmt.relationship_phrase )
        .assign one_rel_phrase = "$cr{aone.Txt_Phrs}"
        .assign oth_rel_phrase = "$cr{aoth.Txt_Phrs}"
      .else
        .assign one_rel_phrase = "$cr{aoth.Txt_Phrs}"
        .assign oth_rel_phrase = "$cr{aone.Txt_Phrs}"
      .end if
    .else
      .if ( aoth.Txt_Phrs == rel_using_stmt.relationship_phrase )
        .assign one_rel_phrase = "$cr{aoth.Txt_Phrs}"
        .assign oth_rel_phrase = "$cr{aone.Txt_Phrs}"
      .else
        .assign one_rel_phrase = "$cr{aone.Txt_Phrs}"
        .assign oth_rel_phrase = "$cr{aoth.Txt_Phrs}"
      .end if
    .end if
  .end if
  .if ("$l{rel_using_stmt.Associative_Variable_Name}" != "self")
if (v_${rel_using_stmt.Associative_Variable_Name} != null) {
  v_${rel_using_stmt.Associative_Variable_Name}.\
  .end if  
relateAcrossR${rel_using_stmt.Numb}To${one_rel_phrase}( \
  .if ("$l{rel_using_stmt.One_Side_Variable_Name}" != "self")
v_${rel_using_stmt.One_Side_Variable_Name} \
  .else
this \
  .end if
) ; \
  .if ("$l{rel_using_stmt.Associative_Variable_Name}" != "self")
  v_${rel_using_stmt.Associative_Variable_Name}.\
  .end if  
relateAcrossR${rel_using_stmt.Numb}To${oth_rel_phrase}( \
  .if ("$l{rel_using_stmt.Other_Side_Variable_Name}" != "self")
v_${rel_using_stmt.Other_Side_Variable_Name} \
  .else
this \
  .end if
) ;
  .if ("$l{rel_using_stmt.Associative_Variable_Name}" != "self")
}
else {
  Throwable t = new Throwable();
  t.fillInStackTrace();
  CorePlugin.logError("Relate using attempted on null link class instance.", t);
}
  .end if
.end function
.//
.// --------------------------------------------------------
.// unrelate statements
.// --------------------------------------------------------
.function gen_unrelate_statements
  .select many unrel_statements from instances of ACT_UNR
  .for each unrel_stmt in unrel_statements
    .invoke result = gen_unrelate_statement(unrel_stmt) 
    .select one statement related by unrel_stmt->ACT_SMT[R603]
    .assign statement.buffer = "${result.body}"
  .end for
.end function
.//
.// --------------------------------------------------------
.// unrelate statement
.// --------------------------------------------------------
.function gen_unrelate_statement
  .param inst_ref unrel_stmt
  .assign one_var_name = "v_" + unrel_stmt.One_Side_Variable_Name
  .assign oth_var_name = "v_" + unrel_stmt.Other_Side_Variable_Name
  .if ("$l{unrel_stmt.One_Side_Variable_Name}" == "self")
    .assign one_var_name = "this"
  .end if
  .if ( "$l{unrel_stmt.Other_Side_Variable_Name}" == "self")
    .assign oth_var_name = "this"
  .end if
  .select one rel related by unrel_stmt->R_REL[R655]
  .invoke is_refl = is_reflexive( rel )
  .assign rel_phrase = ""
  .if ( is_refl.result )
    .assign rel_phrase = "$cr{unrel_stmt.relationship_phrase}"
  .end if
  .if ("$l{unrel_stmt.One_Side_Variable_Name}" != "self")
if (${one_var_name} != null) {
  .end if
${one_var_name}.unrelateAcrossR${unrel_stmt.Numb}From${rel_phrase}(${oth_var_name});
  .if ("$l{unrel_stmt.One_Side_Variable_Name}" != "self")
}
else {
  Throwable t = new Throwable();
  t.fillInStackTrace();
  CorePlugin.logError("Unrelate attempted on null left hand instance.", t);
}
  .end if
.end function
.//
.// --------------------------------------------------------
.// unrelate using statements
.// --------------------------------------------------------
.function gen_unrelate_using_statements
  .select many unrel_using_stmts from instances of ACT_URU
  .for each unrel_using_stmt in unrel_using_stmts
    .invoke result = gen_unrelate_using_statement(unrel_using_stmt)
    .select one statement related by unrel_using_stmt->ACT_SMT[R603]
    .assign statement.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_unrelate_using_statement
  .param inst_ref unrel_using_stmt
  .//
  .select one rel related by unrel_using_stmt->R_REL[R656]
  .invoke is_refl = is_reflexive( rel )
  .assign one_rel_phrase = ""
  .assign oth_rel_phrase = ""
  .if ( is_refl.result )
    .select one aone related by rel->R_ASSOC[R206]->R_AONE[R209]
    .select one aoth related by rel->R_ASSOC[R206]->R_AOTH[R210]
    .select one one_obj related by unrel_using_stmt->V_VAR[R622]->V_INT[R814]->O_OBJ[R815]
    .if ( one_obj.Obj_ID == aone.Obj_ID )
      .if ( aone.Txt_Phrs == unrel_using_stmt.relationship_phrase )
        .assign one_rel_phrase = "$cr{aone.Txt_Phrs}"
        .assign oth_rel_phrase = "$cr{aoth.Txt_Phrs}"
      .else
        .assign one_rel_phrase = "$cr{aoth.Txt_Phrs}"
        .assign oth_rel_phrase = "$cr{aone.Txt_Phrs}"
      .end if
    .else
      .if ( aoth.Txt_Phrs == unrel_using_stmt.relationship_phrase )
        .assign one_rel_phrase = "$cr{aoth.Txt_Phrs}"
        .assign oth_rel_phrase = "$cr{aone.Txt_Phrs}"
      .else
        .assign one_rel_phrase = "$cr{aone.Txt_Phrs}"
        .assign oth_rel_phrase = "$cr{aoth.Txt_Phrs}"
      .end if
    .end if
  .end if
  .if ("$l{unrel_using_stmt.Associative_Variable_Name}" != "self")
if (v_${unrel_using_stmt.Associative_Variable_Name} != null) {
v_${unrel_using_stmt.Associative_Variable_Name}.\
  .end if  
unrelateAcrossR${unrel_using_stmt.Numb}From${one_rel_phrase}( \
  .if ("$l{unrel_using_stmt.One_Side_Variable_Name}" != "self")
v_${unrel_using_stmt.One_Side_Variable_Name} \
  .else
this \
  .end if
) ; \
  .if ("$l{unrel_using_stmt.Associative_Variable_Name}" != "self")
v_${unrel_using_stmt.Associative_Variable_Name}.\
  .end if
unrelateAcrossR${unrel_using_stmt.Numb}From${oth_rel_phrase}( \
  .if ("$l{unrel_using_stmt.Other_Side_Variable_Name}" != "self")
v_${unrel_using_stmt.Other_Side_Variable_Name} \
  .else
this \
  .end if
) ;
  .if ("$l{unrel_using_stmt.Associative_Variable_Name}" != "self")
}
else {
  Throwable t = new Throwable();
  t.fillInStackTrace();
  CorePlugin.logError("Unrelate using attempted on null link class instance.", t);
}
  .end if
.end function
.//
.// --------------------------------------------------------
.// select statements
.// --------------------------------------------------------
.function gen_select_statements
  .select many sel_insts from instances of ACT_FIO
  .for each sel_inst_stmt in sel_insts
    .select one statement related by sel_inst_stmt->ACT_SMT[R603]
    .invoke result = gen_select_statement(sel_inst_stmt, "")
	.assign statement.buffer = "${result.body}"
  .end for
.end function
.function gen_select_statement
  .param inst_ref sel_inst
  .param string where_clause_arg
  .// --------------------------------------------------------
  .// select instance statement
  .// --------------------------------------------------------
    .select any tgt_obj from instances of O_OBJ where (selected.Key_Lett == sel_inst.Target_Obj_KL)
    .assign mr = "modelRoot"
    .if ( "$l{tgt_obj.Descrip:PEI}" == "true" )
      .invoke package = get_package()
      .assign application_root_class = package.application_root_class
      .assign mr = "${application_root_class}.getDefaultInstance()"
    .end if
    .invoke cn = get_class_name( tgt_obj )
    .select one statement related by sel_inst->ACT_SMT[R603]
    .if (sel_inst.cardinality == "MANY")
      .if (sel_inst.is_implicit == "TRUE")
${cn.body} [] \
      .end if
v_${sel_inst.Variable_Name} = ${cn.body}.$cr{tgt_obj.Name}Instances(${mr}${where_clause_arg}) ;
    .else
      .// ANY case
      .if (sel_inst.is_implicit == "TRUE")
${cn.body} \
      .end if
v_${sel_inst.Variable_Name} = ${cn.body}.$cr{tgt_obj.Name}Instance(${mr}${where_clause_arg}) ;
    .end if
.end function
.//
.// --------------------------------------------------------
.// utility function to get reflexive relationship phrase
.// --------------------------------------------------------
.function get_func_suffix
  .param inst_ref  link_rel   .// R_REL
  .param inst_ref  tgt_obj    .// O_OBJ
  .param string link_phrase
  .//
  .invoke is_refl = is_reflexive(link_rel)
  .assign attr_result = ""
  .if ( is_refl.result )
    .select one assoc related by link_rel->R_ASSOC[R206]
    .if (not_empty assoc )
      .select one assr related by assoc->R_ASSR[R211]
      .if ( assr.Obj_ID != tgt_obj.Obj_ID )
        .// phrases are reversed when navigating from assr to aone or aoth
        .select one aone related by assoc->R_AONE[R209]
        .select one aoth related by assoc->R_AOTH[R210]
        .if ( link_phrase == aone.Txt_Phrs )
          .assign attr_result = "$cr{aoth.Txt_Phrs}"
        .else
          .assign attr_result = "$cr{aone.Txt_Phrs}"
        .end if
      .else
        .assign attr_result = "$cr{link_phrase}"
      .end if
    .else
      .assign attr_result = "$cr{link_phrase}"
    .end if
  .end if
.end function
.//
.// --------------------------------------------------------
.// select instance related by statement
.// --------------------------------------------------------
.function gen_select_inst_rel_by_statements
  .select many sel_inst_rel_by_stmts from instances of ACT_SR
  .for each sel_inst_rel_by_stmt in sel_inst_rel_by_stmts
    .select one statement related by sel_inst_rel_by_stmt->ACT_SEL[R664]->ACT_SMT[R603]
    .invoke result = gen_select_inst_rel_by_statement(sel_inst_rel_by_stmt, "")
    .assign statement.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_select_inst_rel_by_statement
  .param inst_ref sel_inst_rel_subt
  .param string where_clause_arg
  .//
  .select one sel_inst_rel related by sel_inst_rel_subt->ACT_SEL[R664]
  .invoke gll = get_last_link_in_chain(sel_inst_rel)
  .assign last_link = gll.last_link
  .select any tgt_obj from instances of O_OBJ where (selected.Key_Lett == last_link.Target_Obj_KL)
  .invoke cn = get_class_name( tgt_obj )
  .select one link_rel related by last_link->R_REL[R681]
  .invoke gfs = get_func_suffix(link_rel, tgt_obj, last_link.Rel_Phrase)
  .assign func_suffix = gfs.result
  .if (sel_inst_rel.cardinality == "MANY")
    .if (sel_inst_rel.is_implicit == "TRUE")
${cn.body} [] \
    .end if
    .invoke nfn = get_nav_func_name( tgt_obj, last_link, "many" )
v_${sel_inst_rel.Variable_Name} = ${cn.body}.${nfn.body}${func_suffix}(\
  .else
    .// cardinality == "ONE" or "ANY"
    .if (sel_inst_rel.is_implicit == "TRUE")
${cn.body} \
    .end if
    .invoke nfn = get_nav_func_name( tgt_obj, last_link, "one" )
v_${sel_inst_rel.Variable_Name} = ${cn.body}.${nfn.body}${func_suffix}(\
  .end if
  .assign link_count = 1
  .select one link related by last_link->ACT_LNK[R604.'succeeds']
  .while (not_empty link)
    .select any lnk_obj from instances of O_OBJ where (selected.Key_Lett == link.Target_Obj_KL)
    .invoke lcn = get_class_name( lnk_obj )
    .select one link_rel related by link->R_REL[R681]
      .invoke gfs = get_func_suffix(link_rel, tgt_obj, link.Rel_Phrase)
      .assign func_suffix = gfs.result
    .if (sel_inst_rel.cardinality == "ONE")
      .invoke nfn = get_nav_func_name( lnk_obj, link, "one" )
${lcn.body}.${nfn.body}${func_suffix}(\
      .else
      .// cardinality == "ANY" or "MANY"
      .invoke nfn = get_nav_func_name( lnk_obj, link, "many" )
${lcn.body}.${nfn.body}${func_suffix}(\
      .end if
    .assign link_count = link_count + 1
    .select one next_link related by link->ACT_LNK[R604.'succeeds']
    .assign link = next_link
  .end while
  .if ("$l{sel_inst_rel.Start_Variable_Name}" == "self")
this\
  .else
v_${sel_inst_rel.Start_Variable_Name}\
  .end if
  .while (link_count > 0)
    .if ( link_count == 1 )
${where_clause_arg}\
    .end if
)\
    .assign link_count = link_count - 1
  .end while
;
.end function
.// --------------------------------------------------------
.// select instance where statements
.// --------------------------------------------------------
.function gen_select_inst_where_statements
  .select many sel_inst_where_stmts from instances of ACT_FIW
  .for each sel_inst_where_stmt in sel_inst_where_stmts
    .select one statement related by sel_inst_where_stmt->ACT_SMT[R603]
    .invoke result = gen_select_inst_where_statement(sel_inst_where_stmt)
    .assign statement.buffer = "${result.body}"
  .end for
.end function
.//
.function traverse_where_clause
  .param inst_ref p_val  .// V_VAL
  .//
  .assign attr_param_decl = ""
  .assign attr_init_stmt = ""
  .assign attr_member_decl = ""
  .assign attr_args = ""
  .//
  .select one pex related by p_val->V_PEX[R801]
  .if ( not_empty pex )
    .select one in_pex related by pex->V_VAL[R824]
    .invoke x = traverse_where_clause( in_pex )
    .if ( x.param_decl != "" )
      .assign attr_param_decl = x.param_decl
      .assign attr_init_stmt  = x.init_stmt
      .assign attr_member_decl = x.member_decl
      .assign attr_args = x.args
    .end if
    .invoke result = gen_paren_expr_value(p_val)
    .assign p_val.buffer = "${result.body}"
  .else
    .select one bin related by p_val->V_BIN[R801]
    .if (not_empty bin)
      .select one lhs related by bin->V_VAL[R802]
      .invoke x = traverse_where_clause( lhs )
      .if ( x.param_decl != "" )
        .assign attr_param_decl = x.param_decl
        .assign attr_init_stmt  = x.init_stmt
        .assign attr_member_decl = x.member_decl
        .assign attr_args = x.args
      .end if
      .select one rhs related by bin->V_VAL[R803]
      .invoke y = traverse_where_clause( rhs )
      .if ( y.param_decl != "" )
        .if ( attr_param_decl == "" )
          .assign attr_param_decl = y.param_decl
          .assign attr_init_stmt  = y.init_stmt
          .assign attr_member_decl = y.member_decl
          .assign attr_args = y.args
        .else
          .assign attr_param_decl  = "${attr_param_decl}, ${y.param_decl}"
          .assign attr_init_stmt   = "${attr_init_stmt}\n\t\t${y.init_stmt}"
          .assign attr_member_decl = "${attr_member_decl}\n\t${y.member_decl}"
          .assign attr_args        = "${attr_args}, ${y.args}"
        .end if
      .end if
      .invoke result = gen_binary_op_value( p_val )
      .assign p_val.buffer = "${result.body}"
    .else 
      .select one unary related by p_val->V_UNY[R801]
      .if (not_empty unary)
        .select one operand related by unary->V_VAL[R804]
        .invoke x = traverse_where_clause( operand )
        .if ( x.param_decl != "" )
          .assign attr_param_decl = x.param_decl
          .assign attr_init_stmt  = x.init_stmt
          .assign attr_member_decl = x.member_decl
          .assign attr_args = x.args
        .end if
        .invoke result = gen_unary_op_value(p_val)
        .assign p_val.buffer = "${result.body}"
      .else 
        .select one transient_val related by p_val->V_TVL[R801]
        .select one attribute_val related by p_val->V_AVL[R801]
        .select one operation_val related by p_val->V_TRV[R801]
        .if (((not_empty transient_val) or (not_empty attribute_val)) or (not_empty operation_val))
          .assign temp_var = "p${info.unique_num}"
          .select any type from instances of S_DT where ("${selected.DT_ID}" == p_val.DT_ID)
          .invoke result = do_type(type)
          .assign attr_param_decl  = "${result.body} ${temp_var}"
          .assign attr_init_stmt   = "\t\tm_${temp_var} = ${temp_var};"
          .assign attr_member_decl = "\tprivate ${result.body} m_${temp_var};"
          .assign attr_args        = p_val.buffer
          .assign p_val.buffer     = "m_${temp_var}"
        .else 
          .// do nothing
        .end if
      .end if
    .end if
  .end if
.end function
.//
.function get_class_query_decl
  .param inst_ref tgt_obj
  .param inst_ref where_clause
  .//
  .invoke cn = get_class_name( tgt_obj )
  .assign test_num = "${info.unique_num}"
  .invoke tcn = get_test_class_name()
  .assign new_test_class_name = "$cr{tgt_obj.Name}_test${test_num}_c"
  class ${new_test_class_name} implements ${tcn.body}
  {
  .invoke twc = traverse_where_clause ( where_clause )
  .if ( twc.param_decl != "" )
	${new_test_class_name}( ${twc.param_decl} ) {
${twc.init_stmt}
	}
${twc.member_decl}
  .end if  
	public boolean evaluate (Object candidate)
	{
		${cn.body} selected = (${cn.body})candidate;
		return ${where_clause.buffer} ;
	}
  }
  .assign attr_query_new = ", new ${new_test_class_name}(${twc.args})"
.end function
.//
.function gen_select_inst_where_statement
  .param inst_ref sel_inst_where
.// --------------------------------------------------------
  .// select instance where statement
  .// --------------------------------------------------------
  .select any tgt_obj from instances of O_OBJ where (selected.Key_Lett == sel_inst_where.Target_Obj_KL)
  .select one owner_action related by sel_inst_where->ACT_SMT[R603]->ACT_BLK[R602]->ACT_ACT[R601]
  .select any src_tfr from instances of O_TFR where ("${selected.Tfr_ID}" == owner_action.Action_Id)
  .select one src_obj related by src_tfr->O_OBJ[R115]
  .select any where_clause related by sel_inst_where->V_VAL[R610]
  .assign non_single_unique_id = true
  .// only generate specific selections on ids for binary
  .// operations
  .select one pex related by where_clause->V_PEX[R801]
  .select one binary_op related by where_clause->V_BIN[R801]
  .if(not_empty pex)
    .select one binary_op related by pex->V_VAL[R824]->V_BIN[R801]
  .end if
  .if(not_empty binary_op)
    .// support an or case but only one level for now
	.select many binary_ops from instances of V_BIN where (false)
	.if(binary_op.Operator == "||")
	  .select one lhs related by binary_op->V_VAL[R802]
	  .select one rhs related by binary_op->V_VAL[R803]
	  .select one lBinary related by lhs->V_BIN[R801]
	  .select one rBinary related by rhs->V_BIN[R801]
	  .if(empty lBinary)
	    .select one pex related by lhs->V_PEX[R801]
	    .if(not_empty pex)
	      .select one lBinary related by pex->V_VAL[R824]->V_BIN[R801]
	    .end if
      .end if
	  .if(empty rBinary)
	    .select one pex related by rhs->V_PEX[R801]
	    .if(not_empty pex)
	      .select one rBinary related by pex->V_VAL[R824]->V_BIN[R801]
	    .end if
      .end if
	  .if(not_empty lBinary and not_empty rBinary)
	    .assign binary_ops = binary_ops | lBinary;
		.assign binary_ops = binary_ops | rBinary;
	  .end if
	.else
	  .assign binary_ops = binary_ops | binary_op
	.end if
	.for each binary in binary_ops
      .// also only for binary operations that have
      .// a left hand attribute value
      .select one lhs related by binary->V_VAL[R802]
      .select one sel_val related by lhs->V_SLR[R801]
      .if(not_empty sel_val)
        .// we also only generate this for those attribute
        .// values which are unique ids that are the only
        .// unique id for a class
        .select any attribute related by tgt_obj->O_ATTR[R102] where (selected.Name == sel_val.Variable_Name)
        .if(not_empty attribute)
          .invoke isui = is_single_unique_id(attribute)
          .if((isui.result) and (binary.Operator == "=="))
            .assign non_single_unique_id = false
            .invoke cn = get_class_name( tgt_obj )
			.select one bVal related by binary->V_VAL[R801];
            .invoke twc = traverse_where_clause ( bVal )
            .select one r_val related by binary->V_VAL[R803]
            .assign value = r_val.buffer
            .if(twc.args != "")
	           .assign value = twc.args
            .end if
            .if (sel_inst_where.is_implicit == "TRUE")
			  .if(first binary_ops)
			    .if((sel_inst_where.cardinality == "ANY") or (sel_inst_where.cardinality == "ONE"))
${cn.body} \
                .end if
              .end if
            .end if
            .if(tgt_obj.Key_Lett == "S_SYS")
			  .if(first binary_ops)
			    .if((sel_inst_where.cardinality == "ANY") or (sel_inst_where.cardinality == "ONE"))
v_${sel_inst_where.Variable_Name} = (${cn.body}) Ooaofooa.getDefaultInstance().getInstanceList(${cn.body}.class).get(${value});
                .else
${cn.body} v_${sel_inst_where.Variable_Name}1 = (${cn.body}) Ooaofooa.getDefaultInstance().getInstanceList(${cn.body}.class).get(${value});
				.end if
              .else
			    .if((sel_inst_where.cardinality == "ANY") or (sel_inst_where.cardinality == "ONE"))
if(v_${sel_inst_where.Variable_Name} == null) {
	v_${sel_inst_where.Variable_Name} = (${cn.body}) Ooaofooa.getDefaultInstance().getInstanceList(${cn.body}.class).get(${value});
}
                .else
${cn.body} v_${sel_inst_where.Variable_Name}2 = (${cn.body}) Ooaofooa.getDefaultInstance().getInstanceList(${cn.body}.class).get(${value});
List<${cn.body}> v_${sel_inst_where.Variable_Name}List = new ArrayList<${cn.body}>();
if(v_${sel_inst_where.Variable_Name}1 != null) {
	v_${sel_inst_where.Variable_Name}List.add(v_${sel_inst_where.Variable_Name}1);
}
if(v_${sel_inst_where.Variable_Name}2 != null) {
	v_${sel_inst_where.Variable_Name}List.add(v_${sel_inst_where.Variable_Name}2);
}
${cn.body}[] v_${sel_inst_where.Variable_Name} = v_${sel_inst_where.Variable_Name}List.toArray(new ${cn.body}[v_${sel_inst_where.Variable_Name}List.size()]);
				.end if
			  .end if
            .else
			  .assign sysRef = "null, "
			  .if (not_empty src_obj)
			     .if (src_obj.Key_Lett == "S_SYS")
			       .assign sysRef = "this, "
				 .end if
			  .end if
			  .if(first binary_ops)
			    .if((sel_inst_where.cardinality == "ANY") or (sel_inst_where.cardinality == "ONE"))
v_${sel_inst_where.Variable_Name} = (${cn.body}) modelRoot.getInstanceList(${cn.body}.class).getGlobal(${sysRef}${value});
                .else
${cn.body} v_${sel_inst_where.Variable_Name}1 = (${cn.body}) modelRoot.getInstanceList(${cn.body}.class).getGlobal(${sysRef}${value});
List<${cn.body}> v_${sel_inst_where.Variable_Name}List = new ArrayList<${cn.body}>();
if(v_${sel_inst_where.Variable_Name}1 != null) {
	v_${sel_inst_where.Variable_Name}List.add(v_${sel_inst_where.Variable_Name}1);
}
${cn.body}[] v_${sel_inst_where.Variable_Name} = v_${sel_inst_where.Variable_Name}List.toArray(new ${cn.body}[v_${sel_inst_where.Variable_Name}List.size()]);
				.end if
              .else
			    .if((sel_inst_where.cardinality == "ANY") or (sel_inst_where.cardinality == "ONE"))
if(v_${sel_inst_where.Variable_Name} == null) {
	v_${sel_inst_where.Variable_Name} = (${cn.body}) modelRoot.getInstanceList(${cn.body}.class).getGlobal(${sysRef}${value});
}
                .else
${cn.body} v_${sel_inst_where.Variable_Name}2 = (${cn.body}) modelRoot.getInstanceList(${cn.body}.class).getGlobal(${sysRef}${value});
if(v_${sel_inst_where.Variable_Name}2 != null) {
	v_${sel_inst_where.Variable_Name}List.add(v_${sel_inst_where.Variable_Name}2);
}
v_${sel_inst_where.Variable_Name} = v_${sel_inst_where.Variable_Name}List.toArray(new ${cn.body}[v_${sel_inst_where.Variable_Name}List.size()]);
				.end if
			  .end if
            .end if
          .end if
		.end if
      .end if
    .end for
  .end if
  .if(non_single_unique_id == true)
    .invoke gcqd = get_class_query_decl( tgt_obj, where_clause )
${gcqd.body}\
    .invoke gss = gen_select_statement(sel_inst_where, gcqd.query_new )
${gss.body}\
  .end if
.end function
.// --------------------------------------------------------
.// select related by where statements
.// --------------------------------------------------------
.function gen_select_rel_where_statements
  .invoke cme_env = GET_ENV_VAR( "GEN_CONTEXT_MENU_WIZARD" )
  .select many sel_rel_where_stmts from instances of ACT_SRW
  .for each sel_rel_where_stmt in sel_rel_where_stmts
    .select one statement related by sel_rel_where_stmt->ACT_SEL[R664]->ACT_SMT[R603]
    .invoke result = gen_select_rel_where_statement(sel_rel_where_stmt, cme_env.result)
    .assign statement.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_select_rel_where_statement
  .param inst_ref sel_rel_where_subt
  .param string cme_env
  .//
  .select one sel_rel_where related by sel_rel_where_subt->ACT_SEL[R664]
  .// --------------------------------------------------------
  .// select related by where statement
  .// --------------------------------------------------------
  .invoke gll = get_last_link_in_chain( sel_rel_where )
  .assign last_link = gll.last_link
  .select any tgt_obj from instances of O_OBJ where (selected.Key_Lett == last_link.Target_Obj_KL)
  .select any where_clause related by sel_rel_where_subt->V_VAL[R611]
  .assign emit_code = true
  .assign userBridgeName = ""
  .select one where_clause related by sel_rel_where_subt->V_VAL[R611]
  .if ( cme_env == "TRUE" )
    .invoke hubi = has_user_bridge_invocation( where_clause )
    .if ( hubi.result )
      .assign emit_code = false
      .assign userBridgeName = hubi.Method_Name
    .end if
  .end if
  .if ( emit_code )
    .invoke gcqd = get_class_query_decl( tgt_obj, where_clause )
${gcqd.body}\
    .invoke gss = gen_select_inst_rel_by_statement(sel_rel_where_subt, gcqd.query_new )
${gss.body}\
  .else
  // select related by where USER::${userBridgeName}
    .if(userBridgeName == "selectExisting")
      .// currently the selection dialog only supports
      .// a single selection, if multiple selection is
      .// desired then this must change
      .assign sel_rel_where.cardinality = "ONE"
    .else
      .assign sel_rel_where.cardinality = "MANY"
    .end if
    .assign temp = sel_rel_where.Variable_Name
    .assign sel_rel_where.Variable_Name = sel_rel_where.Variable_Name + "Instances"
    .if(userBridgeName == "selectExisting")
      .assign sel_rel_where.Variable_Name = temp  
    .end if
    .invoke gss = gen_select_inst_rel_by_statement(sel_rel_where_subt, "" )
${gss.body}\
    .assign sel_rel_where.Variable_Name = temp
  .end if
.end function
.//
.// --------------------------------------------------------
.// generate event statements
.// --------------------------------------------------------
.function gen_event_gen_statements
  .select many event_gen_stmts from instances of E_GEN
  .for each event_gen_stmt in event_gen_stmts
    .select one statement related by event_gen_stmt->ACT_SMT[R700]
    .invoke result = gen_event_gen_statement(event_gen_stmt)
    .assign statement.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_event_gen_statement
  .param inst_ref evt_gen
  .select any evt from instances of SM_EVT where (selected.Drv_Lbl == evt_gen.Event_Label)
// Generate event ${evt_gen.Event_Label}:$cr{evt.Mning} to ${evt_gen.Variable_Name}
  .if ("$l{evt_gen.Variable_Name}" != "self")
if (v_${evt_gen.Variable_Name} != null) {
  v_${evt_gen.Variable_Name}.$cr{evt.Mning}(false \
  .else
$cr{evt.Mning}(true \
  .end if
  .select many parameters related by evt_gen->V_PAR[R701]
  .if ( not_empty parameters )
    .invoke params = gen_parameter_list( parameters, false )
, ${params.body} \
  .end if
) ;
  .if ("$l{evt_gen.Variable_Name}" != "self")
}
else {
  Throwable t = new Throwable();
  t.fillInStackTrace();
  CorePlugin.logError("Attempted to fire null event instance.", t);
}
  .end if
.end function
.//
.// --------------------------------------------------------
.// generate assigner event statement
.// --------------------------------------------------------
.function gen_event_gen_ass_statements
  .select many event_gen_ass_stmts from instances of E_GAR
  .for each event_gen_ass_stmt in event_gen_ass_stmts
    .select one statement related by event_gen_ass_stmt->ACT_SMT[R700]
    .invoke result = gen_event_gen_ass_statement(event_gen_ass_stmt)
    .assign statement.buffer = "${result.body}"
  .end for
.end function
.//
.function gen_event_gen_ass_statement
  .param inst_ref ass_evt_gen
  .select any evt from instances of SM_EVT where (selected.Drv_Lbl == ass_evt_gen.Event_Label)
  .select any object related by evt->SM_SM[R502]->SM_ASM[R517]->O_OBJ[R519]
// Generate event ${ass_evt_gen.Event_Label}:$cr{evt.Mning} to ${ass_evt_gen.Key_Lett}
  .select any tgt_obj from instances of O_OBJ where (selected.Key_Lett == ass_evt_gen.Key_Lett)
  .if (ass_evt_gen.Key_Lett != object.Key_Lett)
$cr{tgt_obj.Name}_assgner_c.$cr{evt.Mning}(false \
  .else
$cr{tgt_obj.Name}_assgner_c.$cr{evt.Mning}(true \
  .end if
  .select many parameters related by ass_evt_gen->V_PAR[R703]
  .if ( not_empty parameters )
    .invoke params = gen_parameter_list( parameters, false )
, ${params.body} \
  .end if
) ;
.end function
.//
.// --------------------------------------------------------
.// transform statement
.// --------------------------------------------------------
.function gen_transform_statements
  .select many transform_stmts from instances of ACT_TFM
  .for each transform_stmt in transform_stmts
    .select one statement related by transform_stmt->ACT_SMT[R603]
	.if (not_empty statement)
	   .// some Transforms are declared as values in which
	   .// case we do not want to generate any code for them here
       .invoke result = gen_transform_statement(transform_stmt)
       .assign statement.buffer = "${result.body}"
	.end if
  .end for
.end function
.//
.function gen_transform_statement
  .param inst_ref transform_call
  .assign root_arg_included = false
  .select any targetObject from instances of O_OBJ where (selected.Key_Lett == transform_call.Object_KL)
  .select any oper related by targetObject->O_TFR[R115] where (selected.Name == transform_call.Method_Name)
  .if (oper.Instance_Based != 1)
    .assign root_arg_included = true
    .invoke cn = get_class_name ( targetObject)
${cn.body}.$cr{transform_call.Method_Name}(modelRoot\
  .else
    .if ("$l{transform_call.Variable_Name}" != "self")
if (v_${transform_call.Variable_Name} != null) {
v_${transform_call.Variable_Name}.\
    .end if
$cr{transform_call.Method_Name}(\
  .end if
  .select many parameters related by transform_call->V_PAR[R627]
  .if ( not_empty parameters )
    .invoke params = gen_parameter_list( parameters, root_arg_included )
${params.body} \
  .end if
) ;
  .if (("$l{transform_call.Variable_Name}" != "self") and (oper.Instance_Based == 1))
}
else {
  Throwable t = new Throwable();
  t.fillInStackTrace();
  CorePlugin.logError("Attempted to call an operation on a null instance.", t);
}
  .end if
.end function
.//
.// --------------------------------------------------------
.// bridge statement
.// --------------------------------------------------------
.function gen_bridge_statements
  .select many bridge_stmts from instances of ACT_BRG
  .for each bridge_stmt in bridge_stmts
    .select one statement related by bridge_stmt->ACT_SMT[R603]
	.if (not_empty statement)
	   .// some Bridges are declared as values in which
	   .// case we do not want to generate any code for them here
       .invoke result = gen_bridge_statement(bridge_stmt)
       .assign statement.buffer = "${result.body}"
	.end if
  .end for
.end function
.//
.function gen_bridge_statement
  .param inst_ref bridge_call
  .//
$cr{bridge_call.External_Entity_KL}_c.$cr{bridge_call.Method_Name}(\
  .select many parameters related by bridge_call->V_PAR[R628]
  .if ( not_empty parameters )
    .invoke params = gen_parameter_list( parameters, false )
${params.body} \
  .end if
) ;
.end function
.//
.// --------------------------------------------------------
.// function statement
.// --------------------------------------------------------
.function gen_function_statements
  .select many func_stmts from instances of ACT_FNC
  .for each func_stmt in func_stmts
    .select one statement related by func_stmt->ACT_SMT[R603]
	.if (not_empty statement)
       .invoke result = gen_func_statement(func_stmt)
       .assign statement.buffer = "${result.body}"
	.end if
  .end for
.end function
.//
.function gen_func_statement
  .param inst_ref func_call
  .//
  .select any domain from instances of S_DOM
  .invoke result = get_domain_name( domain )
${result.body}.$cr{func_call.Function_Name}(modelRoot \
  .select many parameters related by func_call->V_PAR[R669]
  .if ( not_empty parameters )
    .invoke params = gen_parameter_list( parameters, true )
${params.body} \
  .end if
) ;
.end function
.//
.// --------------------------------------------------------
.// return statements
.// --------------------------------------------------------
.function gen_return_statements
.select many return_statements from instances of ACT_RET
.for each return_statement in return_statements
  .select one statement related by return_statement->ACT_SMT[R603]
  .invoke result = gen_return_statement(return_statement)
  .assign statement.buffer="${result.body}"
.end for
.end function
.//
.// --------------------------------------------------------
.// return statement
.// --------------------------------------------------------
.function gen_return_statement
  .param inst_ref returnStatement
return \
  .select one value related by returnStatement->V_VAL[R668]
  .if (not_empty value)
		.//
		.// resolve the value's core data type
		.select any valueType from instances of S_DT where ("${selected.DT_ID}" == value.DT_ID)
		.invoke result = getCoreDataType(valueType);
		.assign valueType = result.coreType;
		.//
	    .// if the value is of the 'real' type
		.assign intCast1 = ""
		.assign intCast2 = ""
		.if (valueType.Name == "real")
			.// if we can resolve the name of the data type of the return type of the enclosing body
			.select one statement related by returnStatement->ACT_SMT[R603]
			.invoke result = get_context_for_statement(statement)
			.if (result.return_dt_name != "")
				.// resolve the core type of the return type
				.select any returnType from instances of S_DT where (selected.Name == result.return_dt_name)
				.invoke result = getCoreDataType(returnType);
				.assign returnType = result.coreType;
			    .//
			    .// if the return type is integer
				.if (returnType.Name == "integer")
					.// cast the value to an int, to avoid a "possible loss of precision"
					.// syntax error in the generated code
					.assign intCast1 = "(int)("
					.assign intCast2 = ")"
  .end if
			.end if
		.end if 
		.//
${intCast1}${value.buffer}${intCast2}\
  .end if
;
.end function
.//
.// --------------------------------------------------------
.// control statements
.// --------------------------------------------------------
.function gen_control_statements
.param string main_class_name
.select many control_statements from instances of ACT_CTL
.for each control_statement in control_statements
  .invoke result = gen_control_statement(control_statement, main_class_name)
  .select one statement related by control_statement->ACT_SMT[R603]
  .assign statement.buffer="${result.body}"
.end for
.end function
.//
.// --------------------------------------------------------
.// control statement
.// --------------------------------------------------------
.function gen_control_statement
  .param inst_ref statement
  .param string main_class_name
  .//
  .// the only command defined is "STOP";
${main_class_name}.shutdown();
.end function
.//
.// --------------------------------------------------------
.// break statements
.// --------------------------------------------------------
.function gen_break_statements
  .select many break_statements from instances of ACT_BRK
  .for each break_statement in break_statements
    .invoke result = gen_break_statement()
    .select one statement related by break_statement->ACT_SMT[R603]
    .assign statement.buffer="${result.body}"
  .end for
.end function
.//
.// --------------------------------------------------------
.// break statement
.// --------------------------------------------------------
.function gen_break_statement
  .//
break;
.end function
.//
.// --------------------------------------------------------
.// continue statements
.// --------------------------------------------------------
.function gen_continue_statements
  .select many continue_statements from instances of ACT_CON
  .for each continue_statement in continue_statements
    .invoke result = gen_continue_statement()
    .select one statement related by continue_statement->ACT_SMT[R603]
    .assign statement.buffer="${result.body}"
  .end for
.end function
.//
.// --------------------------------------------------------
.// continue statement
.// --------------------------------------------------------
.function gen_continue_statement
  .//
continue;
.end function
