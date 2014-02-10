.//=====================================================================
.//
.// File:      $RCSfile: BuildTree.arc,v $
.// Version:   $Revision: 1.19 $
.// Modified:  $Date: 2013/01/10 23:15:39 $
.//
.// (c) Copyright 2003-2013 Mentor Graphics Corporation All rights reserved.
.//
.//=====================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not
.// use this file except in compliance with the License.  You may obtain a copy
.// of the License at
.//
.//      http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
.// License for the specific language governing permissions and limitations under
.// the License.
.//=====================================================================
.//
.// This file is the entry point for generating the code for the
.// Model Explorer tree view.
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "../com.mentor.nucleus.bp.core/color/ooaofooa_package_spec.clr"
.include "${mc_archetypes}/mfp_utils.inc"
.include "${mc_archetypes}/arch_utils.inc"
.//
.//
.invoke package = get_package()
.invoke compMark = markComponentsAndContainments()
.invoke chain_util_env = GET_ENV_VAR( "CHAIN_UTIL_DIR" )
.assign chain_util = chain_util_env.result
.if ( chain_util == "" )
  .print "\nERROR: Environment variable CHAIN_UTIL_DIR not set."
  .exit 100
.end if
.include "${chain_util}/arc/parse_chain.inc"
.//
.assign arc_dir = "arc"
.include "${arc_dir}/create_adapters.inc"
.include "${arc_dir}/create_label_provider.inc"
.include "${arc_dir}/create_content_provider.inc"
.include "${arc_dir}/create_filters_content_provider.inc"
.include "${arc_dir}/create_explorer_view.inc"
.//
.assign path = "com/mentor/nucleus/bp/ui/explorer"
.assign rel_path = "src/${path}"
.//
.invoke result = create_explorer_view(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.select many tree_nodes from instances of T_TNS
.for each node in tree_nodes
  .assign node_name = "$Cr{node.CategoryName}"
  .invoke result = create_adapters(node, node_name, path)
${result.body}
  .emit to file "${rel_path}/adapters/${result.result}.java"
.end for
.invoke result = create_label_provider(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.invoke result = create_content_provider(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.invoke result = create_filters_content_provider(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.//
.// $Log: BuildTree.arc,v $
.// Revision 1.19  2013/01/10 23:15:39  rmulvey
.// job:dts0100939343
.// Updated the copyright to 2013
.//
.// Revision 1.18  2012/01/23 21:26:06  kbrown
.// job:dts0100848212
.// Batch commit of copyright updated files.
.//
.// Revision 1.17  2011/05/30 20:30:05  kbrown
.// job:dts0100742889
.// Updated copy right.
.//
.// Revision 1.16  2010/01/05 03:42:29  kbrown
.// job:dts0100644853
.// Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
.//
.// Revision 1.15  2009/01/01 23:20:27  rmulvey
.// Job:4060
.// Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
.//
.// Revision 1.14.44.1  2008/12/31 15:53:08  rmulvey
.// Job:4060
.// This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
.// report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
.//
.// Revision 1.14  2008/01/08 20:14:16  rmulvey
.// Job:3349
.// Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
.//
.// Revision 1.13.14.1  2007/12/21 18:02:31  rmulvey
.// Job:3339
.// Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
.// in the report produced by the utility used to changesthese messages.  This report is here:
.// Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
.//
.// Revision 1.13  2007/09/05 02:17:25  kbrown
.// Job:2673
.// Promoting copyright changes for plugins:
.// mc.xmiexport
.// pkg
.// pkg-feature
.// test
.// ui.canvas
.// ui.canvas.test
.// ui.explorer
.// ui.explorer.test
.// ui.properties
.// ui.properties.test
.// ui.session
.//
.// Revision 1.12.62.1  2007/09/01 01:37:45  rmulvey
.// Job:2673
.// Updated copyright messages and version numbers in Review-i2673-01.
.//
.// Revision 1.12  2006/12/08 09:48:06  rmulvey
.// Job:2576
.// Promoting changes for the graphics-only compoent diagrams to head.  This is a batch promotion.  Additionally, note that there are known problems in the io.mdl.test suite.  These will be fixed tomorrow.  All other tests pass.  This promotion is being done so that testing of graphics-only component diagrams can commence while our next major merge (Verifier - bug 2286) is started.
.//
.// Revision 1.11.10.1  2006/11/17 23:15:39  tlondon
.// Job: 1687
.//
.// Add call to coloring functions, for marking elements as components.
.//
.// Revision 1.11  2006/08/31 20:41:58  tlondon
.// Job: 845
.//
.// Promoting changes from Review-i845 and some additional fixes from Review-i845-02.
.//
.// These changes include PLCM and CC support.  There are still a few issues that will be addressed shortly and will be treated as new issues against HEAD.
.//
.// Revision 1.10.264.1  2006/07/28 11:48:38  babar
.// Job:845
.// Created Review-i845 branch and checking in merged work for sharing purpose.
.//
.// Revision 1.10.234.1  2006/07/13 08:55:32  babar
.// Job:845
.// Checking in merged workspace so that other members can help in unit tests.
.//
.// Revision 1.10  2005/06/16 19:56:22  jmather
.// Job: 934
.// Remove include of ooaofooa_package_spec.clr
.//
.// Revision 1.9.120.1  2005/06/15 15:05:10  greg
.// Job: 934
.// Remove include of ooaofooa_package_spec.clr
.//
.// Revision 1.9.112.1  2005/06/14 21:29:44  greg
.// Job: 934
.// Remove include of ooaofooa_package_spec.clr
.//
.// Revision 1.9.108.2  2005/06/09 14:50:58  greg
.// Job: 934
.// Remove commented out code
.//
.// Revision 1.9.108.1  2005/06/08 23:26:07  greg
.// Job: 934
.// Remove include of ooaofooa_package_spec.clr
.//
.// Revision 1.9  2005/03/09 23:58:11  campbell
.// Job: 738
.// Add get_package invocation.
.//
.// Revision 1.8.140.1  2005/03/04 22:57:18  greg
.// Job: 738
.// Add get_package invocation
.//
.// Revision 1.8  2004/12/17 17:47:22  campbell
.// Job: 516
.// Remove no longer needed path for relationship chain utilities
.//
.// Revision 1.7.70.1  2004/12/15 23:02:37  greg
.// Job: 516
.// Remove no longer needed path for relationship chain utilities
.//
.// Revision 1.7.66.1  2004/12/15 22:12:22  greg
.// Job: 516
.// Remove no longer needed path for relationship chain utilities
.//
.// Revision 1.7  2004/09/09 16:27:25  babar
.// Job:175
.// Changes merged with head under i175: compare and support of multi
.// model root generally known as Ooaofooa
.//
.// Revision 1.6.2.1  2004/09/01 12:41:07  siddique
.// Job:175
.// Removed the parse_chain.inc and usr folder from explorer instead referred
.// from core.
.//
.// Revision 1.6  2004/07/15 19:31:49  greg
.// Job: 109
.// Changed arch_names to arch_utils
.//
.// Revision 1.5  2003/07/11 16:01:52  greg
.// Job: 20
.// Use PTC_MC_ARC_DIR environment variable for architecture path.
.// Moved working directory up one level.
.//
.// Revision 1.4  2003/07/10 20:40:57  campbell
.// Job: 20
.// Refactored the chain parsing archetype.
.//
.// Revision 1.3  2003/07/09 23:33:53  campbell
.// Job: 20
.// Fixed Implementation review observations.
.//
.// Revision 1.2  2003/07/03 23:40:37  campbell
.// Job: 20
.// Introduced.
.//
.// Revision 1.1.2.1  2003/07/03 21:21:35  campbell
.// Job: 20
.// Introduced.
.//
.// Revision 1.1  2003/06/27 22:02:05  campbell
.// Job: 20
.// Introduced.
.//