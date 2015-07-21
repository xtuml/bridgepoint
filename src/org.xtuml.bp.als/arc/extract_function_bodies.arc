.//====================================================================
.//
.// File:      $RCSfile: extract_function_bodies.arc,v $
.// Version:   $Revision: 1.11 $
.// Modified:  $Date: 2013/01/10 23:21:16 $
.//
.// Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.include "arc/get_names.inc"
.include "arc/als_sql.inc"
.//
.select many fncs from instances of S_SYNC where (selected.DT_ID != 0)
.for each fnc in fncs
  .invoke FILE_WRITE( "sql/${fnc.Name}.oal", "$t2tick{fnc.Action_Semantics}" )
.end for
.//
.assign num_util_funcs = 0;
.for each fnc in fncs
  .if ("${fnc.Descrip:ParserUtilityFunction}" == "TRUE")
    .if ( num_util_funcs == 0 )
-- BP 6.1D content: synch_service syschar: 3

  .invoke x = output_datatype_definitions()
${x.body}

    .end if
    .print "Saving ${fnc.name}"
    .assign num_util_funcs = num_util_funcs + 1
    .//
    .invoke x = output_function_sql( fnc, "ParserValidateFunction: TRUE\nParserUtilityFunction: TRUE\n" )
${x.body}\
  .end if
.end for
.//
.invoke lang_name = get_lang_name()
.assign tstfile = "sql/${lang_name.result}_utilities.sql"
.emit to file "${tstfile}"
