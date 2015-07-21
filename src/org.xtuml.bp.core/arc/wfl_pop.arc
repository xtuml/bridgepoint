.//=====================================================================
.//
.// File:      $RCSfile: wfl_pop.arc,v $
.// Version:   $Revision: 1.13.90.1 $
.// Modified:  $Date: 2013/07/19 12:25:14 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//=====================================================================
.//
.// This archetype creates workflow instances based on the interactions
.// with the USER External Entity found in the action language for each
.// function (S_SYNC). It does this by calling wfl_pop_blck_xlate which
.// scans the ooa of oal subsystem instances and creates W_WOF and W_CTI
.// instances as required.
.//
.//=====================================================================
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.include "${mc_archetypes}/arch_utils.inc"
.//
.assign arc_dir = "arc"
.assign sql_dir = "sql"
.include "${arc_dir}/wfl_block.inc"
.include "${arc_dir}/cme_names.inc"
.//
.select many cmes from instances of CME
.for each cme in cmes
  .invoke fn = get_func_name(cme)
  .select any function from instances of S_SYNC where (selected.Name == "${fn.body}")
  .if (not_empty function)
    .if ( "${function.Descrip:Translate}" == "native" )
      .print "WARNING: Function ${function.Name} has a native implementation; nothing done"
    .else
      .invoke wkfl = create_wfl("${fn.body}", "${function.Sync_Id}", cme);
${wkfl.body}
      .select one action related by function->ACT_FNB[R695]->ACT_ACT[R698]
      .if (not_empty action)
        .select any outer_blk related by action->ACT_BLK[R601] where (selected.Block_Id == action.Block_Id)
        .if (not_empty outer_blk)
          .invoke step = create_step(wkfl, outer_blk, "0", "0")
          .invoke result = wfl_pop_blck_xlate(step, outer_blk)
          .if ("${result.body}" != "")
${step.body}
${result.body}
          .else
            .print "WARNING: Empty body for function ${function.Name}"
          .end if
        .else
          .print "ERROR: No outer block found for function ${function.Name}"
        .end if
      .else
        .print "ERROR: No action found for function ${function.Name}"
	  .end if
    .end if
  .else
    .print "ERROR: No function, ${fn.body}, found for context menu entry: Specialism: ${cme.Specialism}  Label: ${cme.Label}  Key_Lett: ${cme.Key_Lett}"
  .end if
.end for
.emit to file "${sql_dir}/wfl.pei.sql"
