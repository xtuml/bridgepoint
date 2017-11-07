.//---------------------------------------
.function generate_leaf_node_content_assist_function
  .param inst_ref ln
  .param inst_ref r
  .param integer count
  .assign attr_count = count
  .assign attr_generated = false
  .select one rr related by ln->RR[R3]
  .select one t related by ln->T[R3]
  .assign subname = ""
  .if ( not_empty rr )
    .select any sibling_rr related by r->N[R6]->LN[R1]->RR[R3] where ( ( selected.nodeId != rr.nodeId ) and ( selected.rule_name == rr.rule_name ) )
    .if ( empty sibling_rr )
      .assign subname =  "${rr.rule_name}"
    .else
      .assign subname =  "${rr.rule_name}${attr_count}"
    .end if
    .assign attr_count = attr_count + 1
  .elif ( not_empty t )
    .select any sibling_t related by r->N[R6]->LN[R1]->T[R3] where ( ( selected.nodeId != t.nodeId ) and ( selected.token_name == t.token_name ) and ( selected.value == t.value ) )
    .assign count_text = ""
    .if ( not_empty sibling_t )
      .assign count_text = "${attr_count}"
    .end if
    .if ( t.token_name == "STRING_LITERAL" )
      .assign subname = "strlit_${t.value}${count_text}"
      .assign attr_count = attr_count + 1
    .elif ( t.token_name == "TOKEN_REF" )
      .assign subname = "${t.value}${count_text}"
      .assign attr_count = attr_count + 1
    .end if
  .end if
  .if ( "" != subname )
    .assign fncname = "$c{r.rule_name}_$l{subname}_content_assist"
    .select any s_sync from instances of S_SYNC where ( selected.Name == fncname )
    .if ( not_empty s_sync )
INSERT INTO CAF VALUES ( '${ln.nodeId}', '${fncname}', 'leaf' );
      .assign attr_generated = true
    .else
.//-- Missing content assist function '${fncname}'
.//      .assign attr_generated = true
    .end if
  .end if
.end function
.//---------------------------------------
.function generate_rule_lookahead_content_assist_function
  .param inst_ref r
  .assign attr_generated = false
  .assign fncname = "$c{r.rule_name}_lookahead_content_assist"
  .select any s_sync from instances of S_SYNC where ( selected.Name == fncname )
  .if ( not_empty s_sync )
INSERT INTO CAF VALUES ( '${r.nodeId}', '${fncname}', 'lookahead' );
    .assign attr_generated = true
  .else
.//-- Missing content assist function '${fncname}'
.//    .assign attr_generated = true
  .end if
.end function
.//---------------------------------------
.function generate_rule_begin_content_assist_function
  .param inst_ref r
  .assign attr_generated = false
  .assign fncname = "$c{r.rule_name}_begin_content_assist"
  .select any s_sync from instances of S_SYNC where ( selected.Name == fncname )
  .if ( not_empty s_sync )
INSERT INTO CAF VALUES ( '${r.nodeId}', '${fncname}', 'begin' );
    .assign attr_generated = true
  .else
.//-- Missing content assist function '${fncname}'
.//    .assign attr_generated = true
  .end if
  .assign fncname = "$c{r.rule_name}_begin2_content_assist"
  .select any s_sync from instances of S_SYNC where ( selected.Name == fncname )
  .if ( not_empty s_sync )
INSERT INTO CAF VALUES ( '${r.nodeId}', '${fncname}', 'begin2' );
    .assign attr_generated = true
  .else
.//-- Missing content assist function '${fncname}'
.//    .assign attr_generated = true
  .end if
.end function
.//---------------------------------------
.function descend_node
  .param inst_ref node
  .param inst_ref r
  .param integer count
  .assign attr_count = count
  .assign attr_generated = false
  .while ( not_empty node )
    .select one ln related by node->LN[R1]
    .if ( not_empty ln )
      .invoke result = generate_leaf_node_content_assist_function( ln, r, attr_count )
      .if ( result.generated )
${result.body}
        .assign attr_generated = result.generated
      .end if
      .assign attr_count = result.count
    .else
      .select one inner_node related by node->NLN[R1]->N[R8]
      .invoke result = descend_node( inner_node, r, attr_count )
      .assign attr_count = result.count
      .if ( result.generated )
${result.body}
        .assign attr_generated = result.generated
      .end if
    .end if
    .select one node related by node->N[R7.'precedes']
  .end while
.end function
.//---------------------------------------
.// Main code
.//---------------------------------------
.// This routine performs a depth first search on each
.// rule definition generating content assist function
.// instances for leaf nodes that have a content assist
.// function provided in the ooa of ooa. A one based index
.// is appended for situations where a rule reference,
.// string literal, or token appears more than onece in a
.// single rule
.//
-- CONTENT ASSIST FUNCTIONS
.select many rules from instances of R
.for each r in rules
  .invoke result = generate_rule_lookahead_content_assist_function( r )
  .if ( result.generated )
${result.body}
  .end if
  .invoke result = generate_rule_begin_content_assist_function( r )
  .if ( result.generated )
${result.body}
  .end if
  .select one first_node related by r->NLN[R2]->N[R8]
  .invoke result = descend_node( first_node, r, 1 )
  .if ( result.generated )
${result.body}
  .end if
.end for
.emit to file "sql/content_assist.sql"
