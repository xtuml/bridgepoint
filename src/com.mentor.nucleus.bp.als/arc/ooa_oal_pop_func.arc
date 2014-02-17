.//====================================================================
.//
.// File:      $RCSfile: ooa_oal_pop_func.arc,v $
.// Version:   $Revision: 1.11 $
.// Modified:  $Date: 2013/01/10 23:21:16 $
.//
.// Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.// Purpose:  The calls to AL_xlate below invoke fragments (in fragment.arc)
.//           which emit SQL INSERT INTO statements used to populate the 
.//           OOA of BPAL database. 
.//           The INSERT statements are imported (using gen_import) into 
.//           the extended ooa_schema. Code may them be emitted by iterating 
.//           over the model schema as for other parts of the OOA of OOA.
.//
.//====================================================================
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "arc/get_names.inc"
.//
.include "${mc_archetypes}/fragment.inc"
.//
.select many funcs from instances of S_SYNC
.// generate INSERT statements for function bodies
.for each func in funcs
  .if ("${func.Descrip:Translate}" == "native")
    .print "$cr{func.Name} marked as native"
  .elif ("${func.Descrip:ParserValidateFunction}" == "TRUE")
    .print "Parsing ${func.name}"
    .AL_xlate synch_service func
  .end if
.end for
.//
.invoke lang_name = get_lang_name()
.emit to file "sql/${lang_name.result}_validate.bpi.sql"
