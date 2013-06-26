.//====================================================================
.//
.// File:      $RCSfile: ooa_oal_pop.arc,v $
.// Version:   $Revision: 1.13 $
.// Modified:  $Date: 2013/01/10 23:16:44 $
.//
.// (c) Copyright 2003-2013 Mentor Graphics Corporation  All rights reserved.
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
.// $Log: ooa_oal_pop.arc,v $
.// Revision 1.13  2013/01/10 23:16:44  rmulvey
.// job:dts0100939343
.// Updated the copyright to 2013
.//
.// Revision 1.12  2012/01/23 21:24:37  kbrown
.// job:dts0100848212
.// Batch commit of copyright updated files.
.//
.// Revision 1.11  2011/05/30 20:22:54  kbrown
.// job:dts0100742889
.// Updated copy right.
.//
.// Revision 1.10  2010/04/14 02:41:07  rmulvey
.// job:dts0100631941 dts0100633469 dts0100676286 dts0100641311 dts0100670560
.// Promoted from Review-dts0100631941-05
.//
.// Revision 1.9.16.1  2010/03/29 14:34:03  rmulvey
.// job:dts0100631941 dts0100633469  dts0100676286  dts0100641311 dts0100670560
.// Created branch Review-631941-633469-676286-641311-670560 to serve as a prmotion workspace for a batch of issues being promoted together.
.//
.// Revision 1.9.14.1  2010/03/18 14:54:25  campbell
.// Job:dts0100631941
.// Check in work in progress. Split build into two passes.
.//
.// Revision 1.9  2010/01/05 03:17:02  kbrown
.// job:dts0100644853
.// Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
.//
.// Revision 1.8  2009/01/01 23:17:04  rmulvey
.// Job:4060
.// Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
.//
.// Revision 1.7.50.1  2008/12/31 16:22:51  rmulvey
.// Job:4060
.// This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
.// report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
.//
.// Revision 1.7  2008/01/08 20:24:50  rmulvey
.// Job:3349
.// Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
.//
.// Revision 1.6.20.1  2007/12/21 17:55:01  rmulvey
.// Job:3339
.// Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
.// in the report produced by the utility used to changesthese messages.  This report is here:
.// Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
.//
.// Revision 1.6  2007/09/05 02:43:10  kbrown
.// Job:2673
.// Promoting copyright changes for projects:
.// MC-Java
.// MC-Java.test
.// pt_antlr
.//
.// Revision 1.5.94.1  2007/09/01 01:36:27  rmulvey
.// Job:2673
.// Updated copyright messages and version numbers in Review-i2673-01.
.//
.// Revision 1.5  2006/06/02 21:28:40  greg
.// Job: 1554
.// - Only generated function bodies for functions that are not context menu entries
.//   or OAL validation functions
.//
.// Revision 1.4  2004/12/08 21:29:53  greg
.// Job: 384
.// Added include for arch_utils.inc
.//
.// Revision 1.3.64.1  2004/12/08 13:29:14  campbell
.// Job: 384
.// Added include for arch_utils.inc
.//
.// Revision 1.3.36.1  2004/11/18 02:37:00  greg
.// Job: 384
.// Added include for arch_utils.inc
.//
.// Revision 1.3.6.1  2004/10/27 00:32:14  greg
.// Job: 384
.// Added include for arch_utils.inc
.//
.// Revision 1.3  2004/09/17 21:19:14  greg
.// Job: 31
.// Add MDA support
.//
.// Revision 1.2  2004/03/19 20:52:39  greg
.// Job: 143
.// Changed from binary to ASCII -kkv
.//
.// Revision 1.2  2003/02/11 18:24:03  campbell
.// BP 6.1 ODMS running
.//
.// Revision 1.1  2003/01/22 20:18:17  campbell
.// Finalized RCS Header
.//