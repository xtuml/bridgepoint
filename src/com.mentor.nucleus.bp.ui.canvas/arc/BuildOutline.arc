.//=====================================================================
.//
.// File:      $RCSfile: BuildOutline.arc,v $
.// Version:   $Revision: 1.13 $
.// Modified:  $Date: 2013/01/10 23:19:14 $
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
.// Model Outline tree view.
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
.assign arc_dir = "arc"
.include "${arc_dir}/create_outline_sorter.inc"
.//
.assign path = "com/mentor/nucleus/bp/ui/canvas"
.assign rel_path = "src/${path}"
.//
.invoke result = create_outline_sorter(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.//
.// $Log: BuildOutline.arc,v $
.// Revision 1.13  2013/01/10 23:19:14  rmulvey
.// job:dts0100939343
.// Updated the copyright to 2013
.//
.// Revision 1.12  2012/01/23 21:24:07  kbrown
.// job:dts0100848212
.// Batch commit of copyright updated files.
.//
.// Revision 1.11  2011/05/30 20:36:29  kbrown
.// job:dts0100742889
.// Updated copy right.
.//
.// Revision 1.10  2010/01/05 04:11:28  kbrown
.// job:dts0100644853
.// Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
.//
.// Revision 1.9  2009/01/01 23:17:35  rmulvey
.// Job:4060
.// Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
.//
.// Revision 1.8.74.1  2008/12/31 15:49:54  rmulvey
.// Job:4060
.// This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
.// report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
.//
.// Revision 1.8  2008/01/08 20:28:07  rmulvey
.// Job:3349
.// Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
.//
.// Revision 1.7.32.1  2007/12/21 17:56:26  rmulvey
.// Job:3339
.// Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
.// in the report produced by the utility used to changesthese messages.  This report is here:
.// Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
.//
.// Revision 1.7  2007/09/05 02:08:45  kbrown
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
.// Revision 1.6.850.1  2007/09/01 01:23:50  rmulvey
.// Job:2673
.// Updated copyright messages and version numbers in Review-i2673-01.
.//
.// Revision 1.6  2004/09/09 16:26:15  babar
.// Job:175
.// Changes merged with head under i175: compare and support of multi
.// model root generally known as Ooaofooa. Corrected reference to 
.// parse chain
.//
.// Revision 1.5.10.3  2004/09/02 18:11:30  babar
.// Job:175
.// Merged with head and committed back into branch for 
.// pre-commit review purposes
.//
.// Revision 1.5  2004/07/15 19:31:07  greg
.// Job: 109
.// Changed arch_names to arch_utils
.//
.// Revision 1.4  2003/07/11 15:57:37  greg
.// Job: 20
.// Use PTC_MC_ARC_DIR environment variable for archtecture path.
.// Moved working directory up one level.
.//
.// Revision 1.3  2003/07/10 16:24:36  campbell
.// Job: 20
.// Fixed Implementation review observations.
.//
.// Revision 1.1.2.2  2003/07/09 23:31:16  campbell
.// Job: 20
.// Fixed Implementation review observations.
.//
.// Revision 1.1.2.1  2003/07/03 15:29:42  campbell
.// Job: 20
.// Introduced Outline sorting support.
.//