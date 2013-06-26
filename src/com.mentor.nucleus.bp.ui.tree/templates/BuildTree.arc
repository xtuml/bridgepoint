.//=====================================================================
.//
.// File:      $RCSfile$
.// Version:   $Revision$
.// Modified:  $Date$
.//
.// (c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
.//
.//=======================================================================
.// This document contains information proprietary and confidential to
.// Mentor Graphics Corp., and is not for external distribution.
.//=======================================================================
.//
.// This file is the entry point for generating the code for the
.// Model Explorer tree view.
.//
.// The following variables will distinguish this tree from others
.assign prefix = "**PREFIX**"
.assign label_provider_prefix = "**LABEL_PROVIDER_PREFIX**"
.assign plugin_name = "**PLUGIN_NAME**"
.assign path = "**PROJECT_WITH_SLASH**"
.assign package = "**PROJECT**"
.assign plugin_package = "**PLUGIN_PACKAGE**"
.assign rel_path = "src/${path}"
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
.//
.invoke chain_util_env = GET_ENV_VAR( "CHAIN_UTIL_DIR" )
.assign chain_util = chain_util_env.result
.if ( chain_util == "" )
  .print "\nERROR: Environment variable CHAIN_UTIL_DIR not set."
  .exit 100
.end if
.include "${chain_util}/arc/parse_chain.inc"
.//
.assign arc_dir = "../com.mentor.nucleus.bp.ui.tree/arc"
.include "${arc_dir}/create_generic_adapters.inc"
.include "${arc_dir}/create_generic_label_provider.inc"
.include "${arc_dir}/create_generic_content_provider.inc"
.include "${arc_dir}/create_generic_filters_content_provider.inc"
.include "arc/create_**VIEW_FILENAME**.inc"
.//
.invoke result = create_**VIEW_FILENAME**("${path}**/VIEWS**",plugin_name)
${result.body}
.emit to file "${rel_path}**/VIEWS**/${result.result}.java"
.select many tree_nodes from instances of T_TNS
.for each node in tree_nodes
  .assign node_name = "$Cr{node.CategoryName}"
  .invoke result = create_generic_adapters(node,node_name,path,package,plugin_package,plugin_name)
${result.body}
  .emit to file "${rel_path}**/ADAPTERS**/${result.result}.java"
.end for
.invoke result = create_generic_label_provider(path,label_provider_prefix,package)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.invoke result = create_generic_content_provider(path,prefix,package)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.invoke result = create_generic_filters_content_provider(path,prefix,package,plugin_name)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.//
.// $Log$
.// Revision 1.9  2013/01/10 23:19:52  rmulvey
.// job:dts0100939343
.// Updated the copyright to 2013
.//
.// Revision 1.8  2012/01/23 21:31:15  kbrown
.// job:dts0100848212
.// Batch commit of copyright updated files.
.//
.// Revision 1.7  2011/05/30 20:37:08  kbrown
.// job:dts0100742889
.// Updated copy right.
.//
.// Revision 1.6  2010/01/05 04:01:04  kbrown
.// job:dts0100644853
.// Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
.//
.// Revision 1.5  2009/01/01 23:28:04  rmulvey
.// Job:4060
.// Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
.//
.// Revision 1.4.18.1  2008/12/31 15:49:15  rmulvey
.// Job:4060
.// This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
.// report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
.//
.// Revision 1.4  2008/01/08 20:24:34  rmulvey
.// Job:3349
.// Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
.//
.// Revision 1.3.6.1  2007/12/21 17:54:11  rmulvey
.// Job:3339
.// Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
.// in the report produced by the utility used to changesthese messages.  This report is here:
.// Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
.//
.// Revision 1.3  2007/09/05 02:22:29  kbrown
.// Job:2673
.// Promoting copyright changes for plugins:
.// ui.text
.// ui.text.test
.// ui.tree
.// verifer.pkg
.// verifier.pkg-feature
.// welcome
.// welcome.test
.// help.bp.mc
.// help.bp.mc2020
.// internal.test
.//
.// Revision 1.2.24.1  2007/09/01 01:38:09  rmulvey
.// Job:2673
.// Updated copyright messages and version numbers in Review-i2673-01.
.//
.// Revision 1.2  2006/12/12 21:57:02  tlondon
.// Job: 2286
.//
.// Promotion.
.//
.// This is a batch promotion check the resource history for more information.
.// Or see the design note and implementation note named after this issue number.
.//
.// Revision 1.1.14.1  2006/12/11 19:01:35  tlondon
.// Job: 2286
.//
.// Commit changes to branch to allow others to test against in parallel with promotion.
.//
.// Revision 1.1.12.1  2006/10/21 12:49:20  campbell
.// Job:2286
.// Introduced.
.//
.// Revision 1.1.10.1  2006/07/19 14:34:50  greg
.// Job: 1850
.// Merge with HEAD 7/19/2007
.//
.// Revision 1.1.8.1  2006/07/10 15:00:10  jluebker
.// Job: 2164
.// Introduce
.//
.// Revision 1.1.4.1  2006/06/27 23:06:04  jeremyr
.// Job:2164
.// Introduce
.// Primarily based on the pre-existing com.mentor.nucleus.bp.ui.explorer/arc/BuildTree.arc
.//
.// Revision 1.1.2.6  2006/06/22 15:02:21  jeremyr
.// Job:2164
.// Uses a new define to specify the create view.inc file name
.//
.// Revision 1.1.2.5  2006/06/15 14:55:44  jeremyr
.// Job:2164
.// Changed view archetype name
.//
.// Revision 1.1.2.4  2006/06/13 22:02:11  jeremyr
.// Job:2164
.// Added support for specifying a different prefix name for the label provider
.//
.// Revision 1.1.2.3  2006/06/12 19:06:35  jeremyr
.// Job:2164
.// Template now supports generating adapters and view into optional adapters and views directories.
.//
.// Revision 1.1.2.2  2006/06/07 20:23:54  jeremyr
.// Job:2164
.// Made create_explorer_view function name project specific and added a parameter to the function
.//
.// Revision 1.1.2.1  2006/06/06 15:19:40  jeremyr
.// Job:2164
.// Introduce
.//
.// Revision 1.10.214.1  2006/06/01 20:20:33  jeremyr
.// Job:2164
.// Merged with i1625 where the original work was done.  Please refer to that Bugzilla number up to date June 1st, 2006 for more information about the files introduced and modified for this issue.
.//
.// Revision 1.10.188.5  2006/06/01 19:46:13  jeremyr
.// Job:1625
.// The build archetypes now pass in a path to the project's plugin.java file when creating the adapters.
.//
.// Revision 1.10.188.4  2006/06/01 13:53:42  jeremyr
.// Job:1625
.// Updated copyright to current standards
.//
.// Revision 1.10.188.3  2006/05/30 22:31:21  jeremyr
.// Job:1625
.// Adapters now generated by generic function
.//
.// Revision 1.10.188.2  2006/05/30 22:12:50  jeremyr
.// Job:1625
.// Refactored the plugin name and the package name to make the generated files more customizable.
.//
.// Revision 1.10.188.1  2006/05/26 23:23:54  jeremyr
.// Job:1625
.// Removed 6 .inc files which are no longer needed
.//
.// Modified the arc files to use the 3 new generic .inc files in the new project com.mentor.nucleus.bp.ui.tree/arc, which replace the 6 removed inc files.
.//
.// This had the effect of renaming generated FiltersContentProvider.java to ModelFiltersContentProvider to keep the filename in sync with the other files for the model explorer tree,and .cvsignore was updated accordingly.
.//
.// Other files were also updated to reflect the new name of that generated file.
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