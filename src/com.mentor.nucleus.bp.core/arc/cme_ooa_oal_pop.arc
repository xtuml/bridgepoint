.//====================================================================
.//
.// File:      $RCSfile: cme_ooa_oal_pop.arc,v $
.// Version:   $Revision: 1.9 $
.// Modified:  $Date: 2013/01/10 22:54:04 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
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
.include "${mc_archetypes}/arch_utils.inc"
.include "${mc_archetypes}/fragment.inc"
.//
.select many funcs from instances of S_SYNC
.// generate INSERT statements for function bodies
.for each func in funcs
  .if ("${func.Descrip:Translate}" == "native")
    .print "$cr{func.Name} marked as native"
  .elif ("$u{func.Descrip:ContextMenuFunction}" == "TRUE")
    .print "Parsing ${func.name}"
    .AL_xlate synch_service func
  .end if
.end for
.emit to file "sql/Context_menu.bpi.sql"
