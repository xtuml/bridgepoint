.//====================================================================
.//
.// File:      ooa_oal_pop.arc
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
.invoke mc_ss_start_check = GET_ENV_VAR( "PTC_MCC_SS_START")
.assign mc_ss_start = mc_ss_start_check.result
.invoke mc_ss_end_check = GET_ENV_VAR( "PTC_MCC_SS_END")
.assign mc_ss_end = mc_ss_end_check.result
.//
.include "${mc_archetypes}/arch_utils.inc"
.include "${mc_archetypes}/fragment.inc"
.//
.select any action from instances of SM_ACT
.select any domain from instances of S_DOM
.assign translate_enabled = true
.if (mc_ss_start != "")
  .assign translate_enabled = false
.end if
.// Note order of processing mirrors that in
.// MC-Java, to allow build partitioning.
.if (translate_enabled == true)
  .select many bridges from instances of S_BRG
  .// generate INSERT statements for function bodies
  .for each brg in bridges
    .if ("${brg.Descrip:Translate}" == "native")
      .print "$cr{brg.Name} marked as native"
    .else
      .print "Parsing ${brg.name}"
      .AL_xlate bridge brg
    .end if
  .end for
.end if
.select many subsystems from instances of S_SS
.for each subsystem in subsystems
  .if ((mc_ss_start != "") and (mc_ss_start == "${subsystem.Name}"))
    .assign translate_enabled = true
  .end if
  .if ((mc_ss_end != "") and (mc_ss_end == "${subsystem.Name}"))
    .assign translate_enabled = false
    .break for
  .end if
  .if (translate_enabled == true)
    .select many objects related by subsystem->O_OBJ[R2]
    .for each object in objects
      .print "Parsing ${object.name}"
      .select one ism related by object->SM_ISM[R518]
      .// generate INSERT statements for instance state model
      .if (not_empty ism)
        .select one sm related by ism->SM_SM[R517]
        .select many states related by sm->SM_STATE[R501]
        .for each state in states 
          .select one action related by state->SM_MOAH[R511]->SM_AH[R513]->SM_ACT[R514]
          .AL_xlate instance_sm action
        .end for
      .end if
      .select one asm related by object->SM_ASM[R519]
      .// generate INSERT statements for assigner state model
      .if (not_empty asm)
        .select one sm related by asm->SM_SM[R517]
        .select many states related by sm->SM_STATE[R501]
        .for each state in states 
          .select one action related by state->SM_MOAH[R511]->SM_AH[R513]->SM_ACT[R514]
          .AL_xlate assigner_sm action
        .end for
      .end if
      .select many tfms related by object->O_TFR[R115]
      .// generate INSERT statements for transformer bodies
      .for each tfm in tfms
        .if ("${tfm.Descrip:Translate}" == "native")
          .print "$cr{tfm.Name} marked as native"
        .else
          .AL_xlate transformer tfm
        .end if
      .end for
      .select many dbattrs related by object->O_ATTR[R102]->O_BATTR[R106]->O_DBATTR[R107]
      .// generate INSERT statements for derived attribute bodies
      .for each dbattr in dbattrs
        .select one attr related by dbattr->O_BATTR[R107]->O_ATTR[R106]
        .if ("${attr.Descrip:Translate}" == "native")
          .print "$cr{object.Name}.$cr{attr.Name} marked as native"
        .else
          .AL_xlate attribute dbattr
        .end if
      .end for
    .end for
  .end if
.end for
.if (translate_enabled == true)
  .select many funcs from instances of S_SYNC where (("$U_{selected.Descrip:ContextMenuFunction}" != "TRUE") and ("$U_{selected.Descrip:ParserValidateFunction}" != "TRUE"))
  .// generate INSERT statements for function bodies
  .for each func in funcs
    .if ("${func.Descrip:Translate}" == "native")
      .print "$cr{func.Name} marked as native"
    .else
      .print "Parsing ${func.name}"
      .AL_xlate synch_service func
    .end if
  .end for
.end if
.emit to file "sql/$cr{domain.Name}.bpi.sql"
.//