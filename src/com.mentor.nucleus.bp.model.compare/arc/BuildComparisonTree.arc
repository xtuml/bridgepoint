.//========================================================================
.//
.//File:      $RCSfile: BuildComparisonTree.arc,v $
.//Version:   $Revision: 1.2 $
.//Modified:  $Date: 2013/01/10 22:50:00 $
.//
.//Copyright 2004-2013 by Mentor Graphics Corporation. All rights reserved.
.//
.//========================================================================
.//This document contains information proprietary and confidential to
.//Mentor Graphics Corp., and is not for external distribution.
.//========================================================================
.//
.// This file is the entry point for generating the code for the Model Compare
.// tree and text generators
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.//
.// NOTE: iterator here is a synonym of iterator
.assign arc_dir = "arc"
.include "${arc_dir}/create_properties_file.inc"
.//
.assign path = "com/mentor/nucleus/bp/model/compare"
.assign rel_path = "src/${path}"
.assign package_initial = "com.mentor.nucleus.bp.model.compare"
.//
.// Generating the Properties File
.//
.invoke prop_file = create_properties_file()
${prop_file.body}
.emit to file "${rel_path}/ComparePluginMessages.properties"
.//
.// $Log: BuildComparisonTree.arc,v $
.// Revision 1.2  2013/01/10 22:50:00  rmulvey
.// job:dts0100939343
.// Updated the copyright to 2013
.//
.// Revision 1.1  2012/06/12 19:10:06  tlondon
.// Job: dts0100803433
.// Add new compare plugin to HEAD
.//
.// Revision 1.12.10.1  2012/05/08 21:17:46  tlondon
.// Job: dts0100803433
.//
.// Commit recreated branch for compare/merge.
.//
.// Revision 1.11  2011/05/30 20:28:43  kbrown
.// job:dts0100742889
.// Updated copy right.
.//
.// Revision 1.10  2010/01/05 03:52:39  kbrown
.// job:dts0100644853
.// Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
.//
.// Revision 1.9  2009/01/01 23:18:45  rmulvey
.// Job:4060
.// Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
.//
.// Revision 1.8.12.1  2008/12/31 16:15:05  rmulvey
.// Job:4060
.// This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
.// report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
.//
.// Revision 1.8  2008/01/08 20:15:50  rmulvey
.// Job:3349
.// Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
.//
.// Revision 1.7.6.1  2007/12/21 18:01:20  rmulvey
.// Job:3339
.// Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
.// in the report produced by the utility used to changesthese messages.  This report is here:
.// Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
.//
.// Revision 1.7  2007/09/05 01:40:35  kbrown
.// Job:2673
.// Promoting copyright changes for plugins:
.// edge_uml_suite
.// als
.// als.oal
.// als.oal.test
.// bld.pkg
.// bld.pkg-feature
.// compare
.// compare.test
.//
.// Revision 1.6.582.1  2007/09/01 01:49:28  rmulvey
.// Job:2673
.// Updated copyright messages and version numbers in Review-i2673-01.
.//
.// Revision 1.6  2004/09/23 20:08:02  campbell
.// Job: 421
.// Backing out undocumented changes.
.//
.// Revision 1.4  2004/09/09 16:29:54  babar
.// Job:175
.// Introduced under i175: compare and support of multi
.// model root generally known as Ooaofooa
.//
.// Revision 1.1.2.6  2004/08/24 06:25:48  babar
.// Job:175
.// Reverted back from head
.//
.// Revision 1.2  2004/08/10 12:05:01  babar
.// Job:175
.// Introduced under i175
.//
.// Revision 1.1.2.5  2004/07/30 10:26:39  babar
.// Job:175
.// Moved ImageMapping to root and updated
.//
.// Revision 1.1.2.4  2004/07/30 07:15:32  siddique
.// Job:175
.// Cleanup performed, updated archetypes, removed extra files and folders 
.// as the inspectors were moved to core, updated create_util_class.java
.//
.// Revision 1.1.2.3  2004/07/28 09:55:01  siddique
.// Job:175
.// Corrected few problems
.//
.// Revision 1.1.2.2  2004/07/19 16:32:44  babar
.// Job:175
.// changed include for arch_utils.inc
.//
.// Revision 1.1.2.1  2004/07/16 10:32:15  siddique
.// Job:175
.// Renaming BuildCompTree to BuildComparisonTree and create_adapter 
.// to create_iterator
.//
.//