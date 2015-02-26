.//====================================================================
.//
.// File:      $RCSfile: function_gen.arc,v $
.// Version:   $Revision: 1.13 $
.// Modified:  $Date: 2013/01/10 23:21:16 $
.//
.// Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//
.//===============================================
.//
.// Generate the SQL so the functions can be imported in to Model Builder/Gen
.//
.include "arc/get_names.inc"
.include "arc/als_sql.inc"
  .//
  .// Yes, we need to format the insert's exactly like
  .// this because MB import is picky
  .//
-- BP 6.1D content: synch_service syschar: 3

  .invoke x = output_datatype_definitions()
${x.body}
  .//
  .select many fncs from instances of S_SYNC where (selected.DT_ID != 0)
  .for each fnc in fncs
    .invoke x = output_function_sql( fnc, "ParserValidateFunction: TRUE" )
${x.body}\
  .end for
.//
.invoke lang_name = get_lang_name()
.assign tstfile = "sql/${lang_name.result}_functions.sql"
.emit to file "${tstfile}"
.//
