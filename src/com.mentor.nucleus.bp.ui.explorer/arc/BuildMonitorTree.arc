.//=====================================================================
.//
.// File:      $RCSfile$
.// Version:   $Revision$
.// Modified:  $Date$
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
.// Monitor tree view.
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/mfp_utils.inc"
.include "${mc_archetypes}/arch_utils.inc"
.//
.//
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
.include "${arc_dir}/create_mon_label_provider.inc"
.include "${arc_dir}/create_mon_content_provider.inc"
.include "${arc_dir}/create_mon_explorer_view.inc"
.//
.assign path = "com/mentor/nucleus/bp/ui/explorer"
.assign rel_path = "src/${path}"
.//
.invoke result = create_monitor_view(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.select many tree_nodes from instances of T_TNS
.for each node in tree_nodes
  .assign node_name = "$Cr{node.CategoryName}"
  .invoke result = create_adapters(node, node_name, path)
${result.body}
  .emit to file "${rel_path}/adapters/${result.result}.java"
.end for
.invoke result = create_monitor_label_provider(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.invoke result = create_monitor_content_provider(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.//
.// $Log$
.// Revision 1.9  2013/01/10 23:15:38  rmulvey
.// job:dts0100939343
.// Updated the copyright to 2013
.//
.// Revision 1.8  2012/01/23 21:26:06  kbrown
.// job:dts0100848212
.// Batch commit of copyright updated files.
.//
.// Revision 1.7  2011/05/30 20:30:05  kbrown
.// job:dts0100742889
.// Updated copy right.
.//
.// Revision 1.6  2010/01/05 03:42:29  kbrown
.// job:dts0100644853
.// Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
.//
.// Revision 1.5  2009/01/01 23:20:27  rmulvey
.// Job:4060
.// Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
.//
.// Revision 1.4.44.1  2008/12/31 15:53:08  rmulvey
.// Job:4060
.// This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
.// report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
.//
.// Revision 1.4  2008/01/08 20:14:16  rmulvey
.// Job:3349
.// Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
.//
.// Revision 1.3.14.1  2007/12/21 18:02:31  rmulvey
.// Job:3339
.// Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
.// in the report produced by the utility used to changesthese messages.  This report is here:
.// Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
.//
.// Revision 1.3  2007/09/05 02:17:25  kbrown
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
.// Revision 1.2.58.1  2007/09/01 01:37:45  rmulvey
.// Job:2673
.// Updated copyright messages and version numbers in Review-i2673-01.
.//
.// Revision 1.2  2006/12/12 21:48:41  tlondon
.// Job: 2286
.//
.// Promotion.
.//
.// This is a batch promotion check the resource history for more information.
.// Or see the design note and implementation note named after this issue number.
.//
.// Revision 1.1.8.1  2006/12/11 19:03:25  tlondon
.// Job: 2286
.//
.// Commit changes to branch to allow others to test against in parallel with promotion.
.//
.// Revision 1.1.6.2  2006/10/30 17:24:29  campbell
.// Job: 2286
.// Integrate mfputils with Verifier HEAD.
.//
.// Revision 1.1.6.1  2006/10/21 12:38:01  campbell
.// Job:2286
.// Copies of explorer arcs to generate Spotlight View (needs cleaned up so that both ME and Spotlight use the tree generator project).
.//
.// Revision 1.1.4.1  2006/08/10 20:59:13  campbell
.// Job: 2286
.// Introduced.
.//
.// Revision 1.1.2.1  2006/07/24 19:23:54  campbell
.// Job: 2286
.// Introduced.
.//
.//