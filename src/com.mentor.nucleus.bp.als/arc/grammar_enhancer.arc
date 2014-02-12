.//====================================================================
.//
.// File:      $RCSfile: grammar_enhancer.arc,v $
.// Version:   $Revision: 1.32 $
.// Modified:  $Date: 2013/01/10 23:21:16 $
.//
.// Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/do_type.inc"
.include "${mc_archetypes}/arch_utils.inc"
.include "${mc_archetypes}/enums.inc"
.//
.include "arc/get_names.inc"
.//
.//===============================================
.function indent_string
  .param integer indent
  .//-------
  .assign s = ""
  .assign i = 0
  .while ( i < indent)
    .assign s = s + "  "
    .assign i = i + 1
  .end while
${s}\
.end function
.//===============================================
.function is_next_sibling_bang
  .param inst_ref node
  .//-------
  .select any next_node related by node->N[R7.'precedes']
  .invoke result = is_node_bang(next_node)
  .assign attr_rc = result.rc
.end function
.//===============================================
.function is_node_bang
  .param inst_ref node
  .//-------
  .assign rc = false
  .if (not_empty node)
    .select one t related by node->LN[R1]->T[R3]
    .if (not_empty t)
       .// "Term"
      .if (t.token_name == "BANG")
        .assign rc = true
      .end if
    .end if
  .end if
  .assign attr_rc = rc
.end function
.//===============================================
.function init_all_the_nodes
  .// init all the nodes
  .// inputs:
  .// output:
  .//    all attributes of N
.print "init all the nodes"
  .select many nodes from instances of N
  .for each node in nodes
    .assign node.validation_required = false
    .assign node.validation_label = ""
    .assign node.label_required = false
    .assign node.label_emitted = false
    .assign node.label_dcl_emitted = false
    .assign node.label = ""
    .assign node.pre_attach = ""
    .assign node.post_attach = ""
    .assign node.value = ""
    .assign node.in_options_sequence = false
  .end for
.end function
.//===============================================
.function init_all_the_rrefs
  .// init all the rref's
  .// inputs:
  .// outputs:
  .//   RR.validation_required
  .//   RR.label_required
.print "init all the rref's"
  .select many rrefs from instances of RR
  .for each rref in rrefs
    .select one node related by rref->LN[R3]->N[R1]
    .assign node.validation_required = true
    .assign node.label_required = true
    .invoke result = is_next_sibling_bang(node)
    .if (result.rc)
      .assign node.validation_required = false
    .end if
  .end for
.end function
.//===============================================
.function init_all_the_terms
  .// init all the terms
  .// inputs:
  .// outputs:
  .//   T.validation_required
  .//   T.label_required
.print "init all the terms"
  .select many terms from instances of T
  .for each term in terms
    .select one node related by term->LN[R3]->N[R1]
    .invoke result = is_next_sibling_bang(node)
    .assign rc = result.rc
    .if (term.token_name == "STRING_LITERAL")
      .if (NOT rc)
        .assign node.validation_required = true
      .end if
    .elif (term.token_name == "TOKEN_REF")
      .if (NOT rc)
        .assign node.validation_required = true
      .end if
    .end if
  .end for
.end function
.//===============================================
.function init_all_the_nlnodes
  .// init all the nlnodes
.print "init all the nlnodes"
  .select many nlnodes from instances of NLN
  .for each nlnode in nlnodes
    .assign nlnode.loop_index = 0
    .assign nlnode.loop_id_name = ""
    .assign nlnode.fncname_start = ""
    .assign nlnode.fncname_end = ""
  .end for
.end function
.//===============================================
.function init_all_the_rules
  .// init all the rule's
  .// inputs:
  .// outputs:
  .//   RR.validation_required
  .//   RR.label_required
.print "init all the rules's"
  .select many rules from instances of R
  .for each rule in rules
    .if ( rule.rule_name == "block" )
      .assign rule.has_text = false
    .else
      .assign rule.has_text = true
    .end if
    .select one first_child_node related by rule->NLN[R2]->N[R8]
    .select one arg_node related by first_child_node->LN[R1]->T[R3]
    .assign arg_string = ""
    .if ( not_empty arg_node )
      .if ( arg_node.token_name == "BANG" )
        .select one next_child_node related by first_child_node->N[R7.'precedes']
        .select one arg_node related by next_child_node->LN[R1]->T[R3]
      .end if
      .if ( arg_node.token_name == "ARG_ACTION" )
        .// we only support these for now
        .// some day we'll parse out the stuff
        .if ( arg_node.value == "boolean isLval" )
          .assign rule.param_type = "boolean"
          .assign rule.param_name = "isLval"  
        .elif ( arg_node.value == "boolean isRval" )
          .assign rule.param_type = "boolean"
          .assign rule.param_name = "isRval"  
        .elif ( arg_node.value == "boolean isRoot" )
          .assign rule.param_type = "boolean"
          .assign rule.param_name = "isRoot"  
        .elif ( arg_node.value == "boolean isChain" )
          .assign rule.param_type = "boolean"
          .assign rule.param_name = "isChain"  
        .elif ( arg_node.value == "boolean isKeyLett" )
          .assign rule.param_type = "boolean"
          .assign rule.param_name = "isKeyLett"  
        .elif ( arg_node.value == "boolean isAccess" )
          .assign rule.param_type = "boolean"
          .assign rule.param_name = "isAccess"  
        .elif ( arg_node.value == "OalConstants type" )
          .assign rule.param_type = "OalConstants"
          .assign rule.param_name = "type"  
        .end if
      .end if
    .end if
    .//
    .invoke grr = get_referenced_rules(rule)
    .assign rref_valid_set = grr.rref_set
    .assign rule_num = 1
    .for each rref in rref_valid_set
      .assign rref.var_name = "${rref.rule_name}${rule_num}_id"
      .assign rule_num = rule_num + 1
    .end for
  .end for
.end function
.//===============================================
.// Get the var_name for the child rule ref node named "phrase" of the
.// node following the passed in node.
.function get_rel_phrase_varname
  .param inst_ref node   .// inst_ref<N>
  .assign attr_var_name = ""
  .select one next_node related by node->N[R7.'precedes']
  .select one nln related by next_node->NLN[R1]
  .select many child_nodes related by nln->N[R5]
  .for each child in child_nodes
    .select one rr related by child->LN[R1]->RR[R3]
    .if ( not_empty rr )
      .if ( rr.rule_name == "phrase" )
        .assign attr_var_name = rr.var_name
        .break for
      .end if
    .end if
  .end for
.end function
.//===============================================
.function set_validation_based_on_rule_bang
  .// set validation based on rule bang
.print "set validation based on rule bang"
  .invoke result = get_validate_constants()
  .assign ruleid_name = result.ruleid_name
  .select many rules from instances of R
  .for each rule in rules
    .select one p related by rule->NLN[R2]
    .assign p.loop_id_name = ruleid_name
    .select one node related by p->N[R1]
    .assign node.validation_required = true
    .select one first_child_node related by rule->NLN[R2]->N[R8]
    .invoke result = is_node_bang(first_child_node)
    .if (result.rc = true)
      .assign node.validation_required = false
      .select many child_nodes related by rule->N[R6]
      .for each child_node in child_nodes
        .assign child_node.validation_required = false
      .end for
    .else
      .assign p.fncname_start = "$c{rule.rule_name}_start"
      .assign p.fncname_end = "$c{rule.rule_name}_end"
    .end if
  .end for
.end function
.//===============================================
.function init_all_the_ebnfs
  .// init all the ebnf's
  .// inputs:
  .//    rule_node.validation_required
  .// outputs:
  .//    p.loop_id_name
  .//    p.loop_index
  .//    p.fncname_start
  .//    p.fncname_end
  .//    child_node.validation_required
  .//    child_node.label_required
.print "init all the ebnf's"
  .select many ebnfs from instances of EBNF
  .assign ebnf_index = 0
  .for each ebnf in ebnfs
    .select one p related by ebnf->NLN[R2]
    .select one node related by p->N[R1]
    .select one rule related by node->R[R6]
    .select one rule_node related by rule->NLN[R2]->N[R1]
    .if ((ebnf.decoration == "*") OR (ebnf.decoration == "+"))
      .assign ebnf_index = ebnf_index + 1
      .assign p.loop_index = ebnf_index
      .assign node.label = "loop${ebnf_index}_iD"
      .assign node.label_required = true
      .assign p.loop_id_name = node.label
      .if (rule_node.validation_required)
        .assign node.validation_required = true
        .assign p.fncname_start = "$c{rule.rule_name}_loop${ebnf_index}_start"
        .assign p.fncname_end = "$c{rule.rule_name}_loop${ebnf_index}_end"
      .end if
    .end if
  .end for
  .// do this in a separate loop because the ebnfs may not be ordered
  .for each ebnf in ebnfs
    .select one p related by ebnf->NLN[R2]
    .if ((ebnf.decoration == "*") OR (ebnf.decoration == "+"))
      .//
    .else
      .select one parent related by p->N[R1]->NLN[R5]
      .assign p.loop_id_name = parent.loop_id_name
      .if (ebnf.decoration == "=>")
        .// NOTE: this should be a recursive call to change all
        .//       nodes in the hierarchy except just the first level
        .select many child_nodes related by ebnf->NLN[R2]->N[R5]
        .for each child_node in child_nodes
          .assign child_node.validation_required = false
          .assign child_node.label_required = false
        .end for
      .end if
    .end if
  .end for
.end function
.//===============================================
.function get_referenced_rules
  .param inst_ref rule    .// inst_ref<R>
  .//
  .select many rref_set related by rule->N[R6]->LN[R1]->RR[R3]
  .assign rref_valid_set = rref_set - rref_set
  .for each rref in rref_set
    .assign can_insert = true
    .select one rref_node related by rref->LN[R3]->N[R1]
    .if ( rref_node.in_options_sequence )
      .assign can_insert = false
    .end if
    .select one parent_ebnf related by rref_node->NLN[R5]->EBNF[R2]
    .if ( not_empty parent_ebnf )
      .if ( parent_ebnf.decoration == "=>" )
        .assign can_insert = false
      .end if
    .end if
    .if ( can_insert )
      .assign rref_valid_set = rref_valid_set | rref
    .end if
  .end for
  .assign attr_rref_set = rref_valid_set
.end function
.//===============================================
.function handle_options_sequence
  .// handle "options" sequence
.print "handle 'options' sequence"
  .select many pnodes from instances of NLN
  .for each pnode in pnodes
    .select one node related by pnode->N[R8]
    .assign in_options_sequence = false
    .while (not_empty node)
      .select one term related by node->LN[R1]->T[R3]
      .select one rref related by node->LN[R1]->RR[R3]
      .if (not_empty term)
        .if (term.token_name == "OPTIONS")
          .assign in_options_sequence = true
          .assign node.in_options_sequence = true
          .assign node.validation_required = false
          .assign node.label_required = false
        .elif (term.token_name == "RCURLY")
          .assign in_options_sequence = false
          .assign node.in_options_sequence = true
          .assign node.validation_required = false
          .assign node.label_required = false
          .select one next_node related by node->N[R7.'precedes']
          .if (not_empty next_node)
            .select one next_term related by next_node->LN[R1]->T[R3]
            .if (not_empty next_term)
              .if (next_term.token_name == "COLON")
                .assign next_node.in_options_sequence = true
                .assign next_node.validation_required = false
                .assign next_node.label_required = false
              .end if
            .end if
          .end if
        .else
          .if (in_options_sequence)
            .assign node.in_options_sequence = true
            .assign node.validation_required = false
            .assign node.label_required = false
          .end if
        .end if
      .elif (not_empty rref)
        .if (in_options_sequence)
          .assign node.in_options_sequence = true
          .assign node.validation_required = false
          .assign node.label_required = false
        .end if
      .else
        .// must be EBNF
        .select one ebnf related by node->NLN[R1]->EBNF[R2]
        .if (in_options_sequence)
          .assign node.in_options_sequence = true
          .assign node.validation_required = false
          .assign node.label_required = false
        .end if
      .end if
      .select one node related by node->N[R7.'precedes']
    .end while
  .end for
.end function
.//===============================================
.function assign_the_label_names
  .// assign the label names
.print "assign the label names"
  .select any root from instances of ROOT
  .select one proot related by root->NLN[R2]
  .select one rule_node related by proot->N[R8]
  .while (not_empty rule_node)
    .assign index = 0
    .select one rule related by rule_node->NLN[R1]->R[R2]
    .if (not_empty rule)
      .select many sub_nodes related by rule->N[R6]
      .for each sub_node in sub_nodes
        .select one pnode related by sub_node->NLN[R1]
        .if (not_empty pnode)
          .select one node related by pnode->N[R8]
          .while (not_empty node)
            .if (node.validation_required OR node.label_required)
              .assign index = index + 1
              .if (node.validation_required)
                .assign node.validation_label = "ast${index}_iD"
              .end if
              .if (node.label_required)
                .// use the loop id label as the returned id label
                .select one outer_p related by node->NLN[R5]
                .assign node.label = outer_p.loop_id_name
                .assign node.label_dcl_emitted = true
              .end if
            .end if
            .select one node related by node->N[R7.'precedes']
          .end while
        .end if
      .end for
    .end if
    .select one rule_node related by rule_node->N[R7.'precedes']
  .end while
.end function
.//===============================================
.function reset_all_working_vars
  .//-------
  .invoke init_all_the_nodes()
  .invoke init_all_the_rrefs()
  .invoke init_all_the_terms()
  .invoke init_all_the_nlnodes()
  .invoke init_all_the_rules()
  .invoke set_validation_based_on_rule_bang()
  .invoke handle_options_sequence()
  .invoke init_all_the_ebnfs()
  .invoke assign_the_label_names()
  .//
  .select many domains from instances of S_DOM
  .if (empty domains)
    .create object instance dom of S_DOM
    .select any dt from instances of S_DT
    .if (not_empty dt)
      .assign dom.Dom_ID = dt.Dom_ID
    .end if
  .end if
  .// reset all functions. it would be nice to delete them...
.print "reset all functions"
  .select many fncs from instances of S_SYNC
  .for each fnc in fncs
    .assign fnc.DT_ID = 0
    .assign fnc.Name = ""
  .end for
  .select many prms from instances of S_SPARM
  .for each prm in prms
    .assign prm.Sync_ID = 0
    .assign prm.DT_ID = 0
    .assign prm.Name = ""
  .end for
.end function
.//===============================================
.function get_validate_constants
  .//-------
  .invoke content = get_validation_context_name()
  .assign attr_fncclass = "${content.result}"
  .assign attr_upper_ruleid_name = "upperRule_iD"
  .assign attr_ruleid_name = "thisRule_iD"
  .assign attr_tokenclass = "Token"
.end function
.//===============================================
.function create_new_function
  .param string name
  .param inst_ref rule
  .param inst_ref ret_dt
  .//
  .// This function is called multiple times to create
  .// the same function (each time a rule reference (a rule can
  .// be referenced multiple times) validation function is generated).
  .// We only want to create the instance once.
  .//
  .assign func_name = "$cr{name}"
  .select any fnc from instances of S_SYNC where (selected.Name == func_name)
  .if (empty fnc)
    .// this is the first time we've tried to create this function
    .select any fnc from instances of S_SYNC where (selected.DT_ID == 0)
    .if (empty fnc)
      .create object instance fnc of S_SYNC
    .end if
    .assign fnc.Name = func_name
    .assign fnc.DT_ID = ret_dt.DT_ID
    .assign fnc.rule_name = rule.rule_name
    .//
  .end if
  .assign attr_fnc = fnc
.end function
.//===============================================
.function create_new_parameter
  .param string name
  .param inst_ref fnc
  .param inst_ref dt
  .//
  .// This function is called multiple times to create
  .// the same parameter (each time a rule reference (a rule can
  .// be referenced multiple times) validation function is generated).
  .// We only want to create the instance once.
  .//
  .select any prm related by fnc->S_SPARM[R24] where (selected.Name == name)
  .if (empty prm)
    .// this is the first time we've tried to create this parameter
    .select any prm from instances of S_SPARM where (selected.DT_ID == 0)
    .if (empty prm)
      .create object instance prm of S_SPARM
    .end if
    .assign prm.Name = name
    .assign prm.Sync_ID = fnc.Sync_ID
    .assign prm.DT_ID = dt.DT_ID
  .end if
  .assign attr_parm = prm
.end function
.//===============================================
.function find_data_type_by_name
  .param string name
  .//
  .select any dt from instances of S_DT where (selected.name == name)
  .if (empty dt)
    .print "ERROR: data type '${name}' not found"
  .end if
  .assign attr_dt = dt
.end function
.//===============================================
.function emit_rule_labels
  .param inst_ref node
  .param string s
  .//-------
  .assign attr_rule_label = ""
  .if (NOT node.in_options_sequence)
    .select one n related by node->LN[R1]
    .if (not_empty n)
      .if ((node.label_required) AND (node.validation_required))
${s}${node.label} = ${node.validation_label}:  // returned id, AST label
        .assign attr_rule_label = "${node.label}"
      .elif (node.label_required)
${s}${node.label} =   // returned id
        .assign attr_rule_label = "${node.label}"
      .elif (node.validation_required)
${s}${node.validation_label}: // AST label
      .end if
    .end if
  .end if
.end function
.//===============================================
.function emit_rule_init_action
  .param inst_ref node
  .param string s
  .//
  .invoke result = get_validate_constants()
  .assign upper_ruleid_name = result.upper_ruleid_name
  .assign ruleid_name = result.ruleid_name
${s}// rule init action
${s}{ // ${upper_ruleid_name} - caller's rule id
${s}  ${ruleid_name} = ${upper_ruleid_name};  // initialize current id (returned value)
  .// loop variables
  .select one rule related by node->R[R6]
  .invoke result = get_validate_constants()
  .assign upper_ruleid_name = result.upper_ruleid_name
  .assign ruleid_name = result.ruleid_name
  .select many ebnf_node_set related by rule->N[R6]->NLN[R1]->EBNF[R2]->NLN[R2]->N[R1] where (selected.label_required)
  .for each ebnf_node in ebnf_node_set
    .select one p related by ebnf_node->NLN[R1]
${s}  UUID ${p.loop_id_name} = IdAssigner.NULL_UUID;	// current id for loop ${p.loop_index}
    .assign ebnf_node.label_dcl_emitted = true
  .end for
  .invoke grr = get_referenced_rules(rule)
  .assign rref_valid_set = grr.rref_set
  .for each rref in rref_valid_set
${s}  UUID ${rref.var_name} = IdAssigner.NULL_UUID;	// ${rref.rule_name} returned id
  .end for
  .if (node.validation_required)
${s}  UUID rule_begin_id = IdAssigner.NULL_UUID;
  .end if
${s}}
.end function
.//===============================================
.function emit_rule_begin_action
  .param inst_ref node
  .param string s
  .//
  .select one rule related by node->R[R6]
  .select one p related by rule->NLN[R2]
  .select one rule_node related by p->N[R1]
  .if (rule_node.validation_required)
    .assign fncname = p.fncname_start
    .assign ruleid_name = p.loop_id_name
    .invoke result = get_validate_constants()
    .assign fncclass = result.fncclass
    .invoke result = find_data_type_by_name("unique_id")
    .assign unique_id_dt = result.dt
${s}// rule begin action
${s}{ if ( Thread.interrupted() ) throw new InterruptedException();
${s}  ${ruleid_name} = ${fncclass}.${fncname}(getModelRoot(),
    .invoke result = create_new_function(fncname, rule, unique_id_dt)
    .assign fnc = result.fnc
${s}     ${ruleid_name}	// current rule id
    .invoke result = create_new_parameter("a1_ruleid_name", fnc, unique_id_dt)
    .assign fnc.return_value = "a1_ruleid_name"
    .if ( rule.param_name != "" )
      .invoke result = find_data_type_by_name(rule.param_type)
      .assign param_dt = result.dt
${s}     ,${rule.param_name}
    .invoke result = create_new_parameter("a2_${rule.param_name}", fnc, param_dt)
    .end if
${s}    );
${s}    rule_begin_id = ${ruleid_name};
${s}}
  .end if
.end function
.//===============================================
.function emit_rule_end_action
  .param inst_ref node
  .param string s
  .//
  .if (node.validation_required)
    .select one outer_rule related by node->R[R6]
    .select one p related by node->NLN[R1]
    .assign fncname = p.fncname_end
    .invoke result = get_validate_constants()
    .assign fncclass = result.fncclass
    .assign upper_ruleid_name = result.upper_ruleid_name
    .assign ruleid_name = result.ruleid_name
    .assign tokenclass = result.tokenclass
    .invoke result = find_data_type_by_name("unique_id")
    .assign unique_id_dt = result.dt
    .invoke result = find_data_type_by_name(tokenclass)
    .assign tokenclass_dt = result.dt
${s}// rule end action
${s}{ if ( Thread.interrupted() ) throw new InterruptedException();
${s}  ${ruleid_name} = ${fncclass}.${fncname}(getModelRoot(),
      .invoke result = create_new_function(fncname, outer_rule, unique_id_dt)
      .assign fnc = result.fnc
${s}     LT(0)
      .invoke result = create_new_parameter("a1_rule_token", fnc, tokenclass_dt)
${s}    ,${upper_ruleid_name}	// upper rule id
      .invoke result = create_new_parameter("a2_upper_rule_id", fnc, unique_id_dt)
${s}    ,rule_begin_id	// start rule id
      .invoke result = create_new_parameter("a3_rule_begin_id", fnc, unique_id_dt)
${s}    ,${ruleid_name}	// current rule id
      .invoke result = create_new_parameter("a4_rule_id", fnc, unique_id_dt)
      .assign fnc.return_value = "a4_rule_id"
      .invoke grr = get_referenced_rules(outer_rule)
      .assign rref_valid_set = grr.rref_set
      .assign parm_num = 1
      .for each rref in rref_valid_set
${s}    ,${rref.var_name}
        .invoke result = create_new_parameter("b${parm_num}_${rref.var_name}", fnc, unique_id_dt)
        .assign parm_num = parm_num + 1
      .end for
${s}   );
${s}}
  .end if
.end function
.//======================
.function emit_loop_begin_action
  .param inst_ref node
  .param string s
  .//
  .if (node.validation_required)
    .select one rule related by node->R[R6]
    .// we must be on an ebnf node, therefore:
    .select one p related by node->NLN[R1]
    .assign fncname = p.fncname_start
    .assign loop_id_name = p.loop_id_name
    .select one outer_p related by node->NLN[R5]
    .assign outer_loop_id_name = outer_p.loop_id_name
    .invoke result = get_validate_constants()
    .assign fncclass = result.fncclass
    .assign upper_ruleid_name = result.upper_ruleid_name
    .invoke result = find_data_type_by_name("unique_id")
    .assign unique_id_dt = result.dt
${s}// loop ${p.loop_index} begin action
${s}{ if ( Thread.interrupted() ) throw new InterruptedException();
${s}  if (IdAssigner.NULL_UUID.equals(${loop_id_name})) {
${s}    // first time thru loop
${s}    ${loop_id_name} = ${fncclass}.${fncname}(getModelRoot(),
    .invoke result = create_new_function(fncname, rule, unique_id_dt)
    .assign fnc = result.fnc
${s}       ${upper_ruleid_name}	// upper rule id
    .invoke result = create_new_parameter("a1_upper_ruleid_name", fnc, unique_id_dt)
${s}      ,rule_begin_id	// start rule id
      .invoke result = create_new_parameter("a2_rule_begin_id", fnc, unique_id_dt)
${s}      ,${outer_loop_id_name}	// current rule id
    .invoke result = create_new_parameter("a3_current_rule_id", fnc, unique_id_dt)
    .assign fnc.return_value = "a2_rule_begin_id"
${s}      ); }
${s}}
  .end if
.end function
.//======================
.function emit_loop_end_action
  .param inst_ref node
  .param string s
  .//
  .if (node.validation_required)
    .select one rule related by node->R[R6]
    .// we must be on an ebnf node, therefore:
    .select one p related by node->NLN[R1]
    .assign loop_id_name = p.loop_id_name
    .assign fncname = p.fncname_end

    .invoke result = get_validate_constants()
    .assign fncclass = result.fncclass
    .assign tokenclass = result.tokenclass
    .assign upper_ruleid_name = result.upper_ruleid_name
    .assign ruleid_name = result.ruleid_name
    .invoke result = find_data_type_by_name("unique_id")
    .assign unique_id_dt = result.dt
    .invoke result = find_data_type_by_name(tokenclass)
    .assign tokenclass_dt = result.dt
${s}// loop ${p.loop_index} end action
${s}{ if ( Thread.interrupted() ) throw new InterruptedException();
${s}  ${loop_id_name} = ${fncclass}.${fncname}(getModelRoot(),
    .invoke result = create_new_function(fncname, rule, unique_id_dt)
    .assign fnc = result.fnc
${s}     LT(0)
    .invoke result = create_new_parameter("a1_rule_token", fnc, tokenclass_dt)
${s}    ,${upper_ruleid_name}	// upper rule id
    .invoke result = create_new_parameter("a2_upper_ruleid_name", fnc, unique_id_dt)
${s}    ,${ruleid_name}	// outer loop rule id
    .invoke result = create_new_parameter("a3_ruleid_name", fnc, unique_id_dt)
${s}    ,${loop_id_name}	// loop id (current rule id)
    .invoke result = create_new_parameter("a4_loop_id_name", fnc, unique_id_dt)
    .assign fnc.return_value = "a3_ruleid_name"
    .invoke grr = get_referenced_rules( rule )
    .assign rref_valid_set = grr.rref_set
    .assign parm_num = 1
    .for each rref in rref_valid_set
${s}    ,${rref.var_name}
      .invoke result = create_new_parameter("b${parm_num}_${rref.var_name}", fnc, unique_id_dt)
      .assign parm_num = parm_num + 1
    .end for
${s}    );
${s}${ruleid_name} = ${loop_id_name};
${s}}
  .end if
.end function
.//===============================================
.function emit_rule_ref_action
  .param inst_ref node
  .param string s
  .//
  .if (node.validation_required)
    .select one rule related by node->R[R6]
    .select one p related by rule->NLN[R2]
    .select one parent related by node->NLN[R5]
    .select one rref related by node->LN[R1]->RR[R3]
    .select one refd_rule related by rref->R[R4]
    .assign loop_id_name = parent.loop_id_name
    .assign fncname = "$C{rref.rule_name}_validate"
    .invoke result = get_validate_constants()
    .assign fncclass = result.fncclass
    .assign ruleid_name = result.ruleid_name
    .assign upper_ruleid_name = result.upper_ruleid_name
    .assign tokenclass= result.tokenclass
    .invoke result = find_data_type_by_name("unique_id")
    .assign unique_id_dt = result.dt
    .invoke result = find_data_type_by_name(tokenclass)
    .assign tokenclass_dt = result.dt
    .invoke result = find_data_type_by_name("string")
    .assign string_dt = result.dt
${s}// '${rref.rule_name}' action
${s}{ if ( Thread.interrupted() ) throw new InterruptedException();
    .if ( (rule.rule_name == "instance_chain") and (rref.rule_name == "relationship") )
${s}    // clear the phrase variable each time thru the loop
      .invoke pv = get_rel_phrase_varname( node )
${s}    ${pv.var_name} = IdAssigner.NULL_UUID;      
    .end if
    .if ( refd_rule.has_text )
     if ( #${node.validation_label} != null ) {
    .end if
${s}    ${rref.var_name} = ${fncclass}.${fncname}(getModelRoot(),
    .invoke result = create_new_function(fncname, rule, unique_id_dt)
    .assign fnc = result.fnc
${s}     LT(0)
    .invoke result = create_new_parameter("a1_rule_token", fnc, tokenclass_dt)
    .if ( rref.param_val != "" )
${s}    ,${rref.param_val}
      .select one referenced_rule related by rref->R[R4]
      .invoke result = find_data_type_by_name(referenced_rule.param_type)
      .assign param_dt = result.dt
      .invoke result = create_new_parameter("a2_${referenced_rule.param_name}", fnc, param_dt)
    .end if
    .if ( refd_rule.has_text )
${s}    ,#${node.validation_label}.getText()	// text value of node
      .invoke result = create_new_parameter("a3_text", fnc, string_dt)
    .end if
${s}    ,${node.label}	// id returned by '${rref.rule_name}'
    .invoke result = create_new_parameter("a4_rule_ref_id", fnc, unique_id_dt)
${s}    ,${upper_ruleid_name}	// upper rule id
    .invoke result = create_new_parameter("a5_upper_rule_id", fnc, unique_id_dt)
${s}    ,${loop_id_name}	// current rule id
    .invoke result = create_new_parameter("a6_current_rule_id", fnc, unique_id_dt)
    .assign fnc.return_value = "a6_current_rule_id"
${s}    );
${s}    ${loop_id_name} = ${rref.var_name};
    .if ( refd_rule.has_text )
${s}  }
    .end if
${s}}
  .end if
.end function
.//===============================================
.function emit_string_literal_action
  .param inst_ref node
  .param string s
  .//
  .if (node.validation_required)
    .select one rule related by node->R[R6]
    .select one p related by rule->NLN[R2]
    .assign loop_id_name = p.loop_id_name
    .select one term related by node->LN[R1]->T[R3]
    .invoke result = get_validate_constants()
    .assign fncclass = result.fncclass
    .assign ruleid_name = result.ruleid_name
    .assign upper_ruleid_name = result.upper_ruleid_name
    .assign tokenclass= result.tokenclass
    .assign fncname = "String_literal_$l_{term.value}_validate"
    .invoke result = find_data_type_by_name("unique_id")
    .assign unique_id_dt = result.dt
    .invoke result = find_data_type_by_name(tokenclass)
    .assign tokenclass_dt = result.dt
${s}// Literal ${node.value} action
${s}{ if ( Thread.interrupted() ) throw new InterruptedException();
${s}  ${loop_id_name} = ${fncclass}.${fncname}(getModelRoot(),
    .invoke result = create_new_function(fncname, rule, unique_id_dt)
    .assign fnc = result.fnc
${s}     LT(0)
    .invoke result = create_new_parameter("a1_rule_token", fnc, tokenclass_dt)
${s}    ,${upper_ruleid_name}	// upper rule id
    .invoke result = create_new_parameter("a2_upper_rule_id", fnc, unique_id_dt)
${s}    ,${loop_id_name}	// current rule id
    .invoke result = create_new_parameter("a3_current_rule_id", fnc, unique_id_dt)
    .assign fnc.return_value = "a3_current_rule_id"
${s}    );
${s}}
  .end if
.end function
.//===============================================
.function emit_term_action
  .param inst_ref node
  .param string s
  .//
  .if (node.validation_required)
    .select one rule related by node->R[R6]
    .select one term related by node->LN[R1]->T[R3]
    .select one p related by rule->NLN[R2]
    .assign loop_id_name = p.loop_id_name
    .invoke result = get_validate_constants()
    .assign fncclass = result.fncclass
    .assign ruleid_name = result.ruleid_name
    .assign upper_ruleid_name = result.upper_ruleid_name
    .assign tokenclass= result.tokenclass
    .assign fncname = "Terminal_$l_{term.value}_validate"
    .invoke result = find_data_type_by_name("unique_id")
    .assign unique_id_dt = result.dt
    .invoke result = find_data_type_by_name(tokenclass)
    .assign tokenclass_dt = result.dt
    .invoke result = find_data_type_by_name("string")
    .assign string_dt = result.dt
${s}// Terminal ${term.value} action
${s}{ if ( Thread.interrupted() ) throw new InterruptedException();
${s}  if ( #${node.validation_label} != null ) {
${s}   ${loop_id_name} = ${fncclass}.${fncname}(getModelRoot(),
    .invoke result = create_new_function(fncname, rule, unique_id_dt)
    .assign fnc = result.fnc
${s}     LT(0)
    .invoke result = create_new_parameter("a1_rule_token", fnc, tokenclass_dt)
${s}    ,#${node.validation_label}.getText()	// text value of node
    .invoke result = create_new_parameter("a2_text", fnc, string_dt)
${s}    ,${upper_ruleid_name}	// upper rule id
    .invoke result = create_new_parameter("a3_upper_rule_id", fnc, unique_id_dt)
${s}    ,${loop_id_name}	// current rule id
    .invoke result = create_new_parameter("a4_current_rule_id", fnc, unique_id_dt)
    .assign fnc.return_value = "a4_current_rule_id"
${s}    );
${s}  }
${s}}
  .end if
.end function
.//===============================================
.function pre_attach_prepend
  .param inst_ref node
  .param string value
  .//
  .assign node.pre_attach = "${value}${node.pre_attach}"
.end function
.//===============================================
.function pre_attach_append
  .param inst_ref node
  .param string value
  .//
  .assign node.pre_attach = "${node.pre_attach}${value}"
.end function
.//===============================================
.function post_attach_prepend
  .param inst_ref node
  .param string value
  .//
  .assign node.post_attach = "${value}${node.post_attach}"
.end function
.//===============================================
.function post_attach_append
  .param inst_ref node
  .param string value
  .//
  .assign node.post_attach = "${node.post_attach}${value}"
.end function
.//===============================================
.function format_options_sequence
  .param inst_ref child_node    .// inst_ref<N>
  .param string s_long
  .//
  .select one term related by child_node->LN[R1]->T[R3]
  .if (not_empty term)
    .assign child_node.value = term.value
    .if (term.token_name == "COLON")
      .invoke post_attach_prepend(child_node, "\n")
    .elif (term.token_name == "OPTIONS")
      .invoke pre_attach_append(child_node, s_long)
    .else
      .invoke pre_attach_append(child_node, " ")
    .end if
  .else
    .// must be rref
    .select one rref related by child_node->LN[R1]->RR[R3]
    .assign child_node.value = rref.rule_name
    .invoke pre_attach_append(child_node, " ")
  .end if
.end function
.//===============================================
.function format_leaf_node
  .param inst_ref child_node    .// inst_ref<N>
  .param inst_ref outer_node    .// inst_ref<N>
  .param string loop_begin_code
  .param string s_med
  .param string s_long
  .param string sx
  .//
  .assign attr_loop_begin_code = loop_begin_code
  .select one n related by child_node->LN[R1]
  .if (child_node.label_required AND (NOT child_node.label_emitted))
    .assign child_node.label_emitted = true
  .end if
  .select one term related by n->T[R3]
  .if (not_empty term)
    .assign child_node.value = term.value
    .if (term.token_name == "COLON")
      .assign s = s_long
      .invoke pre_attach_append(child_node, "  ")
      .invoke post_attach_prepend(child_node, "\n")
      .invoke result = emit_rule_init_action(outer_node, s_long)
      .invoke pre_attach_prepend(child_node, "${result.body}")
      .invoke result = emit_rule_begin_action(outer_node, s_long)
      .invoke post_attach_append(child_node, "${result.body}")
    .elif (term.token_name == "OR")
      .invoke pre_attach_append(child_node, "${s_med}")
      .invoke post_attach_append(child_node, "\n")
    .elif (term.token_name == "BANG")
      .//don't emit any bangs, except for the param_data_access rule
      .assign child_node.value = ""
      .select any prior_node related by child_node->N[R7.'follows']
      .if (not_empty prior_node)
        .select one prior_term related by prior_node->LN[R1]->T[R3]
        .if ( not_empty prior_term )
          .if (prior_term.token_name == "STRING_LITERAL")
            .assign child_node.value = "!"
          .elif (prior_term.value == "TOK_DOT")
            .assign child_node.value = "!"
          .end if
        .end if
      .end if
    .elif (term.token_name == "ARG_ACTION" )
      .// these are taken care of elsewhere
      .assign child_node.value = ""
    .elif (term.token_name == "STRING_LITERAL")
      .assign child_node.value = """${term.value}"""
      .invoke pre_attach_prepend(child_node, attr_loop_begin_code)
      .assign attr_loop_begin_code = ""
      .invoke result = emit_rule_labels(child_node, sx)
      .invoke pre_attach_append(child_node, result.body)
      .invoke pre_attach_append(child_node, s_long)
      .invoke post_attach_prepend(child_node, "\n")
      .if (child_node.validation_required)
        .invoke result = emit_string_literal_action(child_node, sx)
        .invoke post_attach_append(child_node, result.body)
      .end if
    .elif (term.token_name == "ACTION" )
      .assign child_node.value = "{ ${term.value} }"
    .else
      .assign child_node.value = term.value
      .invoke pre_attach_prepend(child_node, attr_loop_begin_code)
      .assign attr_loop_begin_code = ""
      .invoke result = emit_rule_labels(child_node, sx)
      .invoke pre_attach_append(child_node, result.body)
      .invoke pre_attach_append(child_node, s_long)
      .invoke post_attach_prepend(child_node, "\n")
      .if (child_node.validation_required)
        .invoke result = emit_term_action(child_node, sx)
        .invoke post_attach_append(child_node, result.body)
      .end if
    .end if
  .else
    .// Not a T. Must be RR
    .select one rref related by n->RR[R3]
    .assign child_node.value = rref.rule_name
    .invoke pre_attach_prepend(child_node, attr_loop_begin_code)
    .assign attr_loop_begin_code = ""
    .invoke result = emit_rule_labels(child_node, sx)
    .invoke pre_attach_append(child_node, result.body)
    .invoke pre_attach_append(child_node, s_long)
    .select one p related by child_node->R[R6]->NLN[R2]
    .assign loop_id_name = p.loop_id_name
    .if ( rref.rule_name == "block" )
      .assign loop_id_name = "rule_begin_id"
    .elif ( result.rule_label != "" )
      .assign loop_id_name = result.rule_label
    .end if
    .select one next_child_node related by child_node->N[R7.'precedes']
    .select one arg_node related by next_child_node->LN[R1]->T[R3]
    .assign arg_string = ""
    .if ( not_empty arg_node )
      .if ( arg_node.token_name == "ARG_ACTION" )
        .assign arg_string = ", ${arg_node.value}"
        .assign rref.param_val = arg_node.value
      .end if
    .end if
    .invoke post_attach_prepend(child_node, "[${loop_id_name}${arg_string}]\n")
    .invoke result = emit_rule_ref_action(child_node, sx)
    .invoke post_attach_append(child_node, result.body)
  .end if
.end function
.//===============================================
.//
.function emit_children
  .param inst_ref outer_node
  .param boolean in_outer_rule
  .param integer indent
  .//-------
  .assign so = "   "
  .invoke result = indent_string(indent)
  .assign s_med = result.body
  .assign s_long = s_med + "  "
  .assign indent = indent + 1
  .assign s = s_long
  .if (indent == 1)
    .assign s = " "
  .end if
  .// get the containing rule
  .select one outer_rule related by outer_node->R[R6]
  .//---------------------------------------------
  .// see if we are starting a EBNF phrase.
  .//   examples:  (aa)   (bb)+  (cc)=>  (dd)*
  .//---------------------------------------------
  .assign loop_begin_code = ""
  .select one nln related by outer_node->NLN[R1]
  .if (not_empty nln)
    .if (nln.node_type == "Ebnf")
      .assign outer_node.value = ""
      .select one e related by nln->EBNF[R2]
      .if (e.decoration == "=>")
        .invoke pre_attach_append(outer_node, "${s_med}(")
        .invoke post_attach_prepend(outer_node, "${s_med})${e.decoration}\n")
        .assign s_long = " "
        .assign s_med = " "
        .assign s = " "
      .elif ((e.decoration == "*") OR (e.decoration == "+"))
        .// ebnf construct with zero-or-more or one-or-more
        .//   emit some loop-begin java code
        .invoke loop_begin = emit_loop_begin_action(outer_node, "${s_long}${so}")
        .assign loop_begin_code = "${loop_begin.body}"
        .invoke pre_attach_append(outer_node, "${s_med}(\n")
        .invoke post_attach_prepend(outer_node, "${s_med})${e.decoration}\n")
      .else
        .invoke pre_attach_append(outer_node, "${s_med}(\n")
        .invoke post_attach_prepend(outer_node, "${s_med})${e.decoration}\n")
      .end if
${outer_node.pre_attach}\
    .end if
  .end if
  .//---------------------------------------------
  .// handle the sequence of sibling nodes at the current level
  .//---------------------------------------------
  .select one child_node related by outer_node->NLN[R1]->N[R8]
  .while(not_empty child_node)
    .if (child_node.in_options_sequence)
      .invoke format_options_sequence( child_node, s_long )
${child_node.pre_attach}${child_node.value}${child_node.post_attach}\
    .else
      .// not in options sequence
      .select one nln related by child_node->NLN[R1]
      .// process the item
      .if (not_empty nln)
${loop_begin_code}\
        .assign loop_begin_code = ""
        .if (nln.node_type == "Ebnf")
          .// sibling node is a new ebnf construct
          .// recursively handle all the nodes within the ebnf construct
          .invoke result = emit_children(child_node, false, indent)
${result.body}\
        .else
          .print "ERROR: found pnode which is not an EBNF in emit_children"
        .end if
      .else
        .// not a NLN. Must be a LN
        .invoke fln = format_leaf_node( child_node, outer_node, loop_begin_code, s_med, s_long, "${s}${so}" )
        .assign loop_begin_code = fln.loop_begin_code
${child_node.pre_attach}${child_node.value}${child_node.post_attach}\
      .end if
    .end if
    .select one child_node related by child_node->N[R7.'precedes']
  .end while
  .//---------------------------------------------
  .// see if we are ending an EBNF phrase.
  .//   examples:  (aa)   (bb)+  (cc)=>  (dd)*
  .//---------------------------------------------
  .select one nln related by outer_node->NLN[R1]
  .if (not_empty nln)
    .if (nln.node_type == "Ebnf")
      .select one e related by nln->EBNF[R2]
      .if ((e.decoration == "*") OR (e.decoration == "+"))
        .invoke inner_result = emit_loop_end_action(outer_node, "${s}${so}")
        .invoke post_attach_prepend(outer_node, inner_result.body)
      .end if
${outer_node.post_attach}\
    .end if
  .end if
.end function
.//===============================================
.function emit_root
  .param inst_ref root
  .//-------
  .select one p related by root->NLN[R2]
  .assign sep = ""
  .assign trail_brace = false
  .assign action_nl = false
  .assign prev_term_name = ""
  .select one node related by p->N[R8]
  .while(not_empty node)
    .select one n related by node->LN[R1]
    .if (not_empty n)
      .if (n.node_type == "Term")
        .select one t related by n->T[R3]
        .if (t.token_name == "SEMI")
${t.value}
          .assign sep = "  "
        .elif (t.token_name == "RCURLY")
${t.value}
          .assign sep = ""
        .elif (t.token_name == "OPTIONS")
${t.value}
          .assign sep = "  "
        .elif (t.token_name == "TOKENS")
${t.value}
          .assign sep = "  "
        .elif (t.token_name == "ACTION")
          .if (action_nl)

          .end if
          .if ( prev_term_name != "header" )
{ 
    Ooaofooa p_modelRoot;
    public OalParser(Ooaofooa aModelRoot, TokenStream lexer){
        this(lexer);
        p_modelRoot = aModelRoot;
    }
    
    public Ooaofooa getModelRoot(){
    	return p_modelRoot;
    }    

  ${t.value} 
  
	/**
	 * The following ANTLR-generated constructors should *never* be called,
	 * since they don't provide a (required) model-root to this parser.
	 */
}
          .else
${t.value}
import java.util.UUID;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.IdAssigner;
          .end if
          .assign action_nl = true
          .assign sep = "  "
        .elif (t.token_name == "header")
${t.value} {
          .assign trail_brace = true
          .assign sep = ""
        .elif (t.token_name == "class")
          .if (trail_brace)
            .assign trail_brace = false

}
${t.value}\
          .end if
        .else
${sep}${t.value}\
          .assign sep = " "
        .end if
        .assign prev_term_name = t.token_name
      .elif (n.node_type == "Rref")
        .select one r related by n->RR[R3]
${sep}${r.rule_name}\
          .assign sep = " "
      .end if
    .else
      .// NLN
      .// non-leaf node found in root. forget it
    .end if
    .select one node related by node->N[R7.'precedes']
  .end while
.end function
.//===============================================
.function emit_rule_parameters
  .param inst_ref rule   .// inst_ref<R>
  .//-------
  .invoke result = get_validate_constants()
  .assign ruleid_name = result.ruleid_name
  .assign upper_ruleid_name = result.upper_ruleid_name
  .assign arg_string = ""
  .if ( rule.param_name != "" )
    .select any dt from instances of S_DT where ( selected.Name == rule.param_type )
    .invoke param_type = do_type( dt )
    .assign arg_string = ", ${param_type.body} ${rule.param_name}"
  .end if
[UUID ${upper_ruleid_name}${arg_string}]
   returns[UUID ${ruleid_name}] throws InterruptedException\

.end function
.//===============================================
.function emit_rule
  .param inst_ref outer_rule   .// inst_ref<R>
  .//-------
  .print "Rule: ${outer_rule.rule_name}"
  .select one p related by outer_rule->NLN[R2]
  .select one outer_node related by p->N[R1]
  .invoke result = emit_rule_parameters(outer_rule)
  .assign outer_node.post_attach = "${result.body}"
  .assign indent = 1
  .assign in_outer_rule = true
  .invoke contents = emit_children(outer_node, in_outer_rule, indent)
${outer_rule.rule_name}\
${outer_node.post_attach}\
${contents.body}\
  .invoke result = emit_rule_end_action(outer_node, "    ")

${result.body}\
  ;
.end function
.//===============================================
.// Mainline
.//===============================================
.//
.// Generate the .g file
.//
.//
.invoke reset_all_working_vars()
.//
.select any root from instances of ROOT
.invoke out = emit_root(root)
${out.body}\
.//
.select many rules from instances of R
.for each rule in rules
  .if (rule.rule_name != "Root")
    .invoke out = emit_rule(rule)
${out.body}\
  .end if
.end for
.//
.invoke lang_name = get_lang_name()
.//
.invoke package = get_common_package_name()
.invoke src_path = get_common_package_source_path()
.assign outdir = "../${package.result}.${lang_name.result}/src/${src_path.result}/${lang_name.result}"
.assign bnffile = "${outdir}/${lang_name.result}.g"
.emit to file "${bnffile}"
.//===============================================
