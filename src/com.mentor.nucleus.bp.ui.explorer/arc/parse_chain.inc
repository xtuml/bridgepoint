.//=====================================================================
.//
.// File:      $RCSfile$
.// Version:   $Revision$
.// Modified:  $Date$
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation All rights reserved.
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
.// This function is responsible for working through a relationship
.// chain returning each step in turn.
.//
.function parse_chain
  .param string rel_chain
  .param string direction
  .param string helper_class_path
  .//
  .// Puts the whole chain into 'rel_chain.txt', then invokes
  .// RelChainParser which removes one link at a time and returns the key
  .// letters and the relationship id in files as specified below.
  .// 'remainder.txt' contains what is left of the chain after the parser
  .// has removed the link. This is moved back into 'rel_chain' and the
  .// while loops until the chain has been consumed.
  .// We can perform this process backwards (from the end of the string
  .// to the beginning) or forwards, because the nested traversal function
  .// calls have to be emitted in either order depending on whether we are
  .// generating code for a getChildren or a getParent function.
  .//
  .invoke result = FILE_WRITE("./rel_chain.txt","${rel_chain}")
  .invoke result = SHELL_COMMAND("java -classpath ${helper_class_path} RelChainParser ${direction} ./rel_chain.txt")
  .invoke key_lett = FILE_READ("./key_lett.txt")
  .invoke rel_num = FILE_READ("./rel_num.txt")
  .invoke remainder = FILE_READ("./remainder.txt")
  .invoke rel_int = STRING_TO_INTEGER("$r{rel_num.result}")
  .assign attr_remainder = remainder.result
  .select any attr_class from instances of O_OBJ where (selected.Key_Lett == "$r{key_lett.result}")
  .if (empty attr_class)
    .print "ERROR: Class $r{key_lett.result} in ${rel_chain} not found"
  .end if
  .select any attr_rel from instances of R_REL where (selected.Numb == rel_int.result)
  .if (empty attr_rel)
    .print "ERROR: Relationship number $r{rel_num.result} in ${rel_chain} not found"
  .end if
.end function
.//
.function backward_rel_chain_nav
  .param string p_start_class_name
  .param string p_start_var_name
  .param string p_rel_chain
  .param string p_nav_card
  .param string helper_class_path
  .//
  .// Keep a count so that we close all the parentheses correctly
  .assign num_parens = 0
  .while ("$r{p_rel_chain}" != "")
    .invoke nav = parse_chain(p_rel_chain, "backward",helper_class_path)
    .if (not_empty nav.class)
      .invoke cn = get_class_name(nav.class)
      .if (not_empty nav.rel)
        .invoke result = get_nav_func_name(nav.class, nav.rel, p_nav_card)
        .assign nav_func_name = "${result.body}"
${cn.body}.${nav_func_name}(\
        .assign num_parens = num_parens + 1
      .end if
    .end if
    .assign p_rel_chain = nav.remainder
  .end while
  .//
  .// Now pass in the beginning of the chain.
  .// The cast is required because it is passed in as an Object.
  .//
(${p_start_class_name})${p_start_var_name})\
  .// Now emit all the closing braces for the 'get' calls
  .while (num_parens > 1)
)\
    .assign num_parens = num_parens - 1
  .end while
;
.end function
.//
.function generate_backward_rel_chain_nav
  .param inst_ref p_tree_node  .// T_TNS
  .param string p_rel_chain
  .param string p_nav_card
  .param string p_start_var_name
  .param string p_result_var_name
  .param string helper_class_path
  .//
  .invoke nav = parse_chain(p_rel_chain, "backward",helper_class_path)
  .invoke ecn = get_class_name(nav.class)
  .//
  .select any start_class from instances of O_OBJ where (selected.Key_Lett == p_tree_node.Key_Lett)
  .invoke scn = get_class_name(start_class)
  .//
  .// Declare the variable to hold the result of the relationship navigation
  .if ( p_nav_card == "one" )
    ${ecn.body} ${p_result_var_name} = \
  .else
    ${ecn.body} [] ${p_result_var_name} = \
  .end if
  .//
  .invoke nav_string = backward_rel_chain_nav( scn.body, p_start_var_name, p_rel_chain, p_nav_card, helper_class_path )
${nav_string.body}\
.end function
.//
.function generate_forward_rel_chain_nav
  .param string p_rel_chain
  .param string p_end_kl
  .param string p_start_class_name
  .param string p_start_var_name
  .param string p_result_var_name
  .param string helper_class_path
  .//
  .// nav_card is always "one"
  .//
  .select any end_class from instances of O_OBJ where (selected.Key_Lett == p_end_kl)
  .invoke ecn = get_class_name(end_class)
  .//
    ${ecn.body} ${p_result_var_name} = \
    .// Keep a count so that we close all the parentheses correctly
    .assign num_parens = 0
    .assign cur_key_lett = p_end_kl
    .while ("$r{p_rel_chain}" != "")
      .invoke nav = parse_chain(p_rel_chain, "forward",helper_class_path)
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
