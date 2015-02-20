.//========================================================================
.//
.//File:      $RCSfile: parse_chain.inc,v $
.//Version:   $Revision: 1.18 $
.//Modified:  $Date: 2013/01/17 03:38:58 $
.//
.//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
.//
.//========================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
.// use this file except in compliance with the License.  You may obtain a copy 
.// of the License at
.//
.//       http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software 
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
.// License for the specific language governing permissions and limitations under
.// the License.
.//========================================================================
.// This function is responsible for working through a relationship
.// chain returning each step in turn.
.//
.function parse_chain
  .param string rel_chain
  .param string direction
  .//
  .assign attr_key_lett = ""
  .assign attr_rel_num_str = ""
  .assign attr_remainder = ""
  .assign attr_phrase = ""
  .if ( direction == "forward" )
    .assign attr_key_lett = "$tcf_kl{rel_chain}"
    .assign attr_rel_num_str = "$tcf_rel{rel_chain}"
    .assign attr_remainder = "$tcf_rest{rel_chain}"
    .assign attr_phrase = "$tcf_phrase{rel_chain}"
  .else
    .assign attr_key_lett = "$tcb_kl{rel_chain}"
    .assign attr_rel_num_str = "$tcb_rel{rel_chain}"
    .assign attr_remainder = "$tcb_rest{rel_chain}"
    .assign attr_phrase = "$tcb_phrase{rel_chain}"
  .end if
  .//
  .invoke sti = STRING_TO_INTEGER(attr_rel_num_str)
  .assign rel_num = sti.result
  .select any attr_class from instances of O_OBJ where (selected.Key_Lett == "$r{attr_key_lett}")
  .if (empty attr_class)
    .print "ERROR: Class $r{attr_key_lett} in ${rel_chain} not found"
  .end if
  .select any attr_rel from instances of R_REL where (selected.Numb == rel_num)
  .if (empty attr_rel)
    .print "ERROR: Relationship number $r{attr_rel_num_str} in ${rel_chain} not found"
  .end if
.end function
.//
.function backward_rel_chain_nav_with_query
  .param string p_start_class_name
  .param string p_start_var_name
  .param string p_rel_chain
  .param string p_nav_card
  .param boolean cast
  .param string query
  .param string p_selection_class
  .//
  .// Keep a count so that we close all the parentheses correctly
  .assign num_parens = 0
  .while ("$r{p_rel_chain}" != "")
    .invoke nav = parse_chain(p_rel_chain, "backward")
    .if (not_empty nav.class)
      .invoke cn = get_class_name(nav.class)
      .if (not_empty nav.rel)
        .invoke result = get_nav_func_name(nav.class, nav.rel, p_nav_card) //Creates the function call to get related instances
        .assign nav_func_name = "${result.body}"
${cn.body}.${nav_func_name}${nav.phrase}(
        .assign num_parens = num_parens + 1
      .end if
    .end if
    .assign p_rel_chain = nav.remainder
  .end while
  .//
  .// Now pass in the beginning of the chain.
  .// The cast is required because it is passed in as an Object.
  .//
  .if(cast)
    .if((query != "") and (num_parens == 1))
(${p_start_class_name})${p_start_var_name}, new ClassQueryInterface_c() {
							public boolean evaluate(Object candidate) {
								return ((${p_selection_class})candidate).${query};
							}
						})
		.else
(${p_start_class_name})${p_start_var_name})
    .end if  
  .else
    .if((query != "") and (num_parens == 1))
${p_start_var_name}, new ClassQueryInterface_c() {
							public boolean evaluate(Object candidate) {
								return ((${p_selection_class})candidate).${query};
							}
						})
    .else
${p_start_var_name})
    .end if
  .end if
  .// Now emit all the closing braces for the 'get' calls
  .while (num_parens > 1)
    .if(num_parens == 2)
      .// insert the given query here
      .if(query != "")
      , new ClassQueryInterface_c() {
							public boolean evaluate(Object candidate) {
								return ((${p_selection_class})candidate).${query};
							}
						}
			.end if
    .end if
)
    .assign num_parens = num_parens - 1
  .end while  
.end function
.function backward_rel_chain_nav
  .param string p_start_class_name
  .param string p_start_var_name
  .param string p_rel_chain
  .param string p_nav_card
  .param boolean cast
    .invoke nav_string = backward_rel_chain_nav_with_query(p_start_class_name, p_start_var_name, p_rel_chain, p_nav_card, cast, "", "")
${nav_string.body}
.end function
.//
.function generate_backward_rel_chain_nav
  .param inst_ref p_tree_node  .// T_TNS
  .param string p_rel_chain
  .param string p_nav_card
  .param string p_start_var_name
  .param string p_result_var_name
    .invoke nav_string = generate_backward_rel_chain_nav_from_kl( p_tree_node.Key_Lett, p_rel_chain, p_nav_card, p_start_var_name, p_result_var_name, true )
${nav_string.body}
.end function

.function generate_backward_rel_chain_nav_with_query
  .param string kl
  .param string p_rel_chain
  .param string p_nav_card
  .param string p_start_var_name
  .param string p_result_var_name
  .param boolean cast
  .param string query
    .invoke nav_string = generate_backward_rel_chain_nav_from_kl_with_query(kl, p_rel_chain, p_nav_card, p_start_var_name, p_result_var_name, cast, query);
${nav_string.body}
.end function

.function generate_backward_rel_chain_nav_from_kl
  .param string kl
  .param string p_rel_chain
  .param string p_nav_card
  .param string p_start_var_name
  .param string p_result_var_name
  .param boolean cast
    .invoke nav_string = generate_backward_rel_chain_nav_from_kl_with_query(kl, p_rel_chain, p_nav_card, p_start_var_name, p_result_var_name, cast, "");
${nav_string.body}
.end function

.function generate_backward_rel_chain_nav_from_kl_with_query
  .param string kl
  .param string p_rel_chain
  .param string p_nav_card
  .param string p_start_var_name
  .param string p_result_var_name
  .param boolean cast
  .param string query
    .invoke nav_string = generate_backward_rel_chain_nav_from_kl_with_query_(kl, p_rel_chain, p_nav_card, p_start_var_name, p_result_var_name, cast, query, true);
${nav_string.body}
.end function
.function generate_backward_rel_chain_nav_from_kl_with_query_
  .param string kl
  .param string p_rel_chain
  .param string p_nav_card
  .param string p_start_var_name
  .param string p_result_var_name
  .param boolean cast
  .param string query
  .param boolean closeStatement
.//
  .invoke nav = parse_chain(p_rel_chain, "backward")
  .invoke ecn = get_class_name(nav.class)	.//Getting formatted class name for
  .//
  .select any start_class from instances of O_OBJ where (selected.Key_Lett == kl)
  .invoke scn = get_class_name(start_class)
  .//
  .// Declare the variable to hold the result of the relationship navigation
  .if(p_result_var_name != "")
    .if ( p_nav_card == "one" )
    ${ecn.body} ${p_result_var_name} = 
    .else
    ${ecn.body} [] ${p_result_var_name} = 
    .end if
  .end if
  .//
  .invoke nav_string = backward_rel_chain_nav_with_query( scn.body, p_start_var_name, p_rel_chain, p_nav_card, cast, query, ecn.body)
  .if(closeStatement)
${nav_string.body};
  .else
${nav_string.body}  
  .end if
.end function

.//
.function generate_forward_rel_chain_nav
  .param string p_rel_chain
  .param string p_end_kl
  .param string p_start_class_name
  .param string p_start_var_name
  .param string p_result_var_name
  .//
  .// nav_card is always "one"
  .//
  .select any end_class from instances of O_OBJ where (selected.Key_Lett == p_end_kl)
  .invoke ecn = get_class_name(end_class)
  .//
  .if(p_result_var_name != "")
    ${ecn.body} ${p_result_var_name} = \
  .end if
    .// Keep a count so that we close all the parentheses correctly
    .assign num_parens = 0
    .assign cur_key_lett = p_end_kl
    .while ("$r{p_rel_chain}" != "")
      .invoke nav = parse_chain(p_rel_chain, "forward")
      .if (not_empty nav.rel)
        .select any cur_class from instances of O_OBJ where (selected.Key_Lett == "$r{cur_key_lett}")
        .if (not_empty cur_class)
          .invoke ccn = get_class_name(cur_class)
          .invoke result = get_nav_func_name(cur_class, nav.rel, "one")
          .assign nav_func_name = "${result.body}"
${ccn.body}.${nav_func_name}(\
          .//
          .// Note we don't assign the newly parsed key letter until after
          .// emitting the call. This is because the navigation is being 
          .// traversed in the reverse direction (i.e child to parent). This
          .// is also why the chain is being processed forwards.
          .//
          .if (not_empty nav.class)
            .assign next_class = nav.class
            .assign cur_key_lett = next_class.Key_Lett
          .end if
          .assign num_parens = num_parens + 1
        .end if
      .end if
      .assign p_rel_chain = nav.remainder
    .end while
    .//
    .// Now pass in the beginning of the chain which is always 'arg'.
    .// The cast is required because 'arg' is passed into getParent
    .// as an Object.
    .//
(${p_start_class_name})${p_start_var_name})\
    .// Now emit all the closing braces for the 'getOne' calls
    .while (num_parens > 1)
)\
      .assign num_parens = num_parens -1
    .end while
;
.end function
.//
.//This function is used by compare.test
.//
.function generate_parse_chain_parts
  .param string p_start_class_name
  .param string p_rel_chain
  .assign num_parens = 0
  .while ("$r{p_rel_chain}" != "")
    .invoke nav = parse_chain(p_rel_chain, "backward")
    .if (not_empty nav.class)
      .invoke cn = get_class_name(nav.class)
      .if (((not_empty nav.rel) and (not_empty nav.class)) and ("${cn.body}" != ""))
      	.if(num_parens != 0)
${cn.body}
      	.end if
        .assign num_parens = num_parens + 1
      .end if
    .end if
    .assign p_rel_chain = nav.remainder
  .end while
.end function