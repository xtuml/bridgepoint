.//====================================================================
.//
.// File:      $RCSfile: als_sql.inc,v $
.// Version:   $Revision: 1.9 $
.// Modified:  $Date: 2013/01/10 23:21:16 $
.//
.// Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.function output_datatype_definitions
  .select many dt_set from instances of S_DT
  .for each dt in dt_set
    .select one cdt related by dt->S_CDT[R17]
    .if ( not_empty cdt )
INSERT INTO S_CDT
    VALUES (${cdt.DT_ID},
    ${cdt.Core_Typ});
INSERT INTO S_DT
    VALUES (${dt.DT_ID},
    ${dt.Dom_ID},
    '${dt.Name}',
    '${dt.Descrip}');
    .else
      .select one udt related by dt->S_UDT[R17]
      .if ( not_empty udt )
INSERT INTO S_UDT
    VALUES (${udt.DT_ID},
    ${udt.CDT_ID},
    ${udt.Gen_Type});
INSERT INTO S_DT
    VALUES (${dt.DT_ID},
    ${dt.Dom_ID},
    '${dt.Name}',
    '${dt.Descrip}');
      .else
        .select one edt related by dt->S_EDT[R17]
        .if ( not_empty edt )
INSERT INTO S_EDT
    VALUES (${edt.DT_ID});
INSERT INTO S_DT
    VALUES (${dt.DT_ID},
    ${dt.Dom_ID},
    '${dt.Name}',
    '${dt.Descrip}');
          .select many enum_set related by edt->S_ENUM[R27]
          .for each enum in enum_set
INSERT INTO S_ENUM
    VALUES (${enum.Enum_ID},
    '${enum.Name}',
    '${enum.Descrip}',
    ${enum.EDT_ID});
          .end for
        .end if
      .end if
    .end if
  .end for
.end function
.//
.function output_function_sql
  .param inst_ref fnc
  .param string p_description
  .//
  .invoke als = get_lang_name()
  .select one ret_dt related by fnc->S_DT[R25]
  .select many prms related by fnc->S_SPARM[R24]
  .assign prm_count = cardinality prms
  .// the domain code is stripped off automatically when imported into Model Builder
  .assign sync_id = fnc.Sync_ID
  .assign dom_id = ret_dt.Dom_ID
  .assign ret_dt_id = ret_dt.DT_ID
  .assign descrip = p_description
  .if ("${fnc.Descrip:Translate}" == "native")
    .assign descrip = descrip + "\nTranslate: native\n"
  .end if
INSERT INTO S_SYNC
    VALUES (${sync_id},
    ${dom_id},
    '${fnc.Name}',
    '${descrip}',
  .invoke act_sem = FILE_READ( "sql/${fnc.Name}.${als.result}" )
  .if ( act_sem.result != "" )
    '${act_sem.result}',
  .else
    'return param.${fnc.return_value};',
  .end if
    ${ret_dt_id},
  .if ("${fnc.Descrip:Translate}" == "native")
    0);
  .else
    3);
  .end if
  .for each prm in prms
    .assign sparm_id = prm.SParm_ID
    .assign sparm_dt_id = prm.DT_ID
INSERT INTO S_SPARM 
    VALUES (${sparm_id},
    ${sync_id},
    '${prm.Name}',
    ${sparm_dt_id},
    0);
  .end for
.end function
