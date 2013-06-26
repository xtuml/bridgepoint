.//=====================================================================
.//
.// File:      $RCSfile: create_context_menus.arc,v $
.// Version:   $Revision: 1.15 $
.// Modified:  $Date: 2013/01/10 22:54:04 $
.//
.// (c) Copyright 2003-2013 Mentor Graphics Corporation All rights reserved.
.//
.//=====================================================================
.// This document contains information proprietary and confidential to
.// Project Technology, Inc. and is not for external distribution.
.//=====================================================================
.//
.// This archetype is responsible for creating all the object 
.// contributions for the system, except those specialized
.// contributions for such things as dedicated editors etc.
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.include "color/ooaofooa_package_spec.clr"
.//
.// Fill the Processing subsystem with code
.//
.include "${mc_archetypes}/do_type.inc"
.include "${mc_archetypes}/value.inc"
.include "${mc_archetypes}/statement.inc"
.include "${mc_archetypes}/translate_oal.inc"
.//
.assign arc_dir = "arc"
.include "${arc_dir}/cme_names.inc"
.include "${arc_dir}/create_global_action.inc"
.include "${arc_dir}/create_object_action.inc"
.include "${arc_dir}/create_rename_action.inc"
.include "${arc_dir}/create_core_plugin.inc"
.include "${arc_dir}/ui_processing.inc"
.include "${arc_dir}/function_body.inc"
.include "${arc_dir}/create_selection_dialog_action.inc"
.//
.assign path = "com/mentor/nucleus/bp/core/ui"
.assign rel_path = "src/${path}"
.assign action_rel_path = "src/${path}/actions"
.//
.// First, populate the generation database with generated code
.//
.invoke containment = markComponentsAndContainments()
.invoke result = translate_all_oal("Ooaofooa", true)
.//
.invoke result = create_global_action(path, "delete")
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.//
.invoke result = create_rename_action(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.//
.select many menu_entries from instances of CME
.for each menu_entry in menu_entries
  .if(menu_entry.UsesSelectionDialog)
    .invoke result = create_selection_dialog_action(path, menu_entry)
${result.body}
    .emit to file "${action_rel_path}/${result.result}.java"
  .else
    .if (menu_entry.Global == false)
      .invoke result = create_object_action(path, menu_entry)
${result.body}
      .emit to file "${rel_path}/${result.result}.java"
    .end if
  .end if
.end for
.//
.clear
.invoke result = create_core_plugin(path)
${result.body}
.emit to file "plugin.xml"
.//
