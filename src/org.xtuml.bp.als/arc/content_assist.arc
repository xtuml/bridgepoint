-- CONTENT ASSIST FUNCTIONS
.select many lns from instances of LN
.for each ln in lns
  .select one r related by ln->N[R1]->R[R6]
  .if ( not_empty r )
    .select one rr related by ln->RR[R3]
    .select one t related by ln->T[R3]
    .assign subname = ""
    .if ( not_empty rr )
      .assign subname =  rr.rule_name
    .elif ( not_empty t )
      .if ( t.token_name == "STRING_LITERAL" )
        .assign subname = "strlit_${t.value}"
      .else
        .assign subname = t.value
      .end if
    .end if
    .assign fncname = "$c{r.rule_name}_$l{subname}_content_assist"
    .select any s_sync from instances of S_SYNC where ( selected.Name == fncname )
    .if ( not_empty s_sync )
INSERT INTO CAF VALUES ( '${ln.nodeId}', '${fncname}' );
    .end if
  .end if
.end for
.emit to file "sql/content_assist.sql"
