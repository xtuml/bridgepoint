.//========================================================================
.//
.//File:      $RCSfile: create_properties_file.inc,v $
.//Version:   $Revision: 1.19 $
.//Modified:  $Date: 2013/01/10 22:49:49 $
.//
.//Copyright 2004-2013 by Mentor Graphics Corporation. All rights reserved.
.//
.//========================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
.// use this file except in compliance with the License.  You may obtain a copy 
.// of the License at
.//
.//       http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software 
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
.// License for the specific language governing permissions and limitations under
.// the License.
.//========================================================================
.function create_properties_file
    .// Get All T_TNS
        .//for each node in T_TNS
            .//Get O_OBJ
                .//Get Attrbiutes
            .//Get T_TPS
                .//For each T_TPS 
                    .//Get Role Name                    
    .select many nodes from instances of T_TNS
    .for each tree_node in nodes
        .select any class from instances of O_OBJ where (selected.Key_Lett == tree_node.Key_Lett)
        .//getting class name from mc_java
        .invoke class_name = get_class_name(class)
        .assign meta_model_class_name = class.Name
${class_name.body}.longname= ${meta_model_class_name}
        .select many attrs related by class->O_ATTR[R102]
        .for each attr in attrs
            .select one dt related by attr->S_DT[R114]
            .select one rattr related by attr->O_RATTR[R106]
            .if (((dt.Name != "unique_id") and (empty rattr)) and ("$l{attr.Descrip:User_Visible}" != "false"))             
                .assign name = "${attr.Name}"
                .assign fullname = "${attr.Descrip:Full Name}"
${class_name.body}.attribute_${name}.longname= ${fullname}
            .end if
        .end for
        .// cover external attributes
        .select many external_attributes related by tree_node->T_EDL[R1005]->T_EA[R1006]
        .for each external_attr in external_attributes
          .select one edl related by external_attr->T_EDL[R1006];
          .select any edl_class from instances of O_OBJ where (selected.Key_Lett == edl.Key_Lett)
          .select any attr related by edl_class->O_ATTR[R102] where (selected.Name == external_attr.Attribute_Name)
          .assign name = "${attr.Name}"
          .assign fullname = "${attr.Descrip:Full Name}"
${class_name.body}.attribute_${name}.longname= ${fullname}
        .end for
.//     
        .select many child_specs related by tree_node->T_TPS[R1000]
        .for each spec in child_specs
            .if (spec.NameOnly != "")
                .select any class from instances of O_OBJ where (selected.Key_Lett == tree_node.Key_Lett)
                .invoke class_name = get_class_name(class)
${class_name.body}.referential_$_{spec.NameOnly}.longname= ${spec.NameOnly}
            .end if     
        .end for
    .end for
.//
    .// Writing specific strings (Labels)
action.NextDiff.label=
action.PrevDiff.label=
action.NextChange.label=
action.PrevChange.label=
action.NextDiff.tooltip= Next Difference
action.PrevDiff.tooltip= Previous Difference
action.NextChange.tooltip= Next Change
action.PrevChange.tooltip= Previous Change
action.NextDiff.image=dlcl16/next_diff_nav.gif
action.PrevDiff.image=dlcl16/prev_diff_nav.gif
action.NextChange.image=dlcl16/next_change_nav.gif
action.PrevChange.image=dlcl16/prev_change_nav.gif
action.IgnoreAncestor.label=
action.copy.label= Copy
action.selectAll.label= Select All
action.IgnoreWhiteSpace.label= Ignore Whitespace
Editor.FindReplace.label= Find/Replace...
ComparePlugin.MainWindow.Title= BridgePoint Model Comparison
title= xtUML Compare
.end function
.//
.// $Log: create_properties_file.inc,v $
.// Revision 1.19  2013/01/10 22:49:49  rmulvey
.// job:dts0100939343
.// Updated the copyright to 2013
.//
.// Revision 1.18  2012/07/13 17:24:06  rmulvey
.// job:dts0100900301
.// Promoted from Review-dts0100900301-01
.//
.// Revision 1.17.16.1  2012/07/13 02:47:00  rmulvey
.// job:dts0100900301
.// Created a new working branch, Review-dts0100900301-01
.//
.// Revision 1.17.14.1  2012/07/10 14:58:18  tlondon
.// Job: dts0100900301
.// Commit changes to address problems found during testing
.//
.// Revision 1.17  2012/04/21 22:41:58  rmulvey
.// job:dts0100836674
.// Promoted from Review-dts0100836674-4
.//
.// Revision 1.16.4.1  2012/04/16 21:17:38  tlondon
.// Job: dts0100836674
.//
.// Update branch with changes from the -03 variant
.//
.// Revision 1.16.2.1  2012/04/05 20:29:04  tlondon
.// Job: dts0100836674
.//
.// Commit work in progress
.//
.// Revision 1.16  2012/03/09 05:30:12  rmulvey
.// job:dts0100710176 dts0100852140
.// Promoted from Promotion-710176-852140
.//
.// Revision 1.15.4.1  2012/03/07 15:10:15  rmulvey
.// job:dts0100710176 dts0100852140
.// Created a promotion branch to help with final testing.
.//
.// Revision 1.15.2.1  2012/02/29 15:42:43  tlondon
.// Job: dts0100710176
.//
.// Add icons and label entries in properties file
.//
.// Revision 1.15  2012/01/23 21:31:04  kbrown
.// job:dts0100848212
.// Batch commit of copyright updated files.
.//
.// Revision 1.14  2011/09/29 23:17:47  kbrown
.// job:dts0100802307 dts0100798784 dts0100749929
.// Batch commit of promotion files.
.//
.// Revision 1.13.10.1  2011/09/29 17:39:49  tlondon
.// Job: dts0100798784
.//
.// Use own resource bundle and specify title string, to allow xtUML Compare label in content viewer.
.//
.// Revision 1.13  2011/05/30 20:28:43  kbrown
.// job:dts0100742889
.// Updated copy right.
.//
.// Revision 1.12  2010/01/05 03:52:39  kbrown
.// job:dts0100644853
.// Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
.//
.// Revision 1.11  2009/01/01 23:18:45  rmulvey
.// Job:4060
.// Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
.//
.// Revision 1.10.12.1  2008/12/31 16:15:05  rmulvey
.// Job:4060
.// This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
.// report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
.//
.// Revision 1.10  2008/01/08 20:15:50  rmulvey
.// Job:3349
.// Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
.//
.// Revision 1.9.6.1  2007/12/21 18:01:20  rmulvey
.// Job:3339
.// Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
.// in the report produced by the utility used to changesthese messages.  This report is here:
.// Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
.//
.// Revision 1.9  2007/09/05 01:40:35  kbrown
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
.// Revision 1.8.2.1  2007/09/01 01:49:28  rmulvey
.// Job:2673
.// Updated copyright messages and version numbers in Review-i2673-01.
.//
.// Revision 1.8  2007/07/27 14:53:01  rmulvey
.// Job:2924
.// Promoting from Review-i2924
.// Fix typo in window title.
.//
.// Revision 1.7.46.1  2007/07/26 20:28:49  campbell
.// Job: 2924
.// Fix typo in window title.
.//
.// Revision 1.7.44.1  2007/07/25 18:32:26  campbell
.// Job: 2924
.// Fix Typo in window title.
.//
.// Revision 1.7  2006/07/07 14:41:02  greg
.// Job: 1872
.// Changed to look at the value assigned to the new "User_Visible" keyword rather than that assigned to "Persistent".
.//
.// Revision 1.6.84.1  2006/07/03 19:49:29  jmather
.// Job: 1872
.// Changed to look at the value assigned to the new "User_Visible" keyword rather than that assigned to "Persistent".
.//
.// Revision 1.6.64.1  2006/05/30 22:56:28  jmather
.// Job: 1872
.// Changed to look at the value assigned to the new "User_Visible" keyword rather than that assigned to "Persistent".
.//
.// Revision 1.6  2006/01/11 00:27:23  pablo
.// Job: 1051
.// Add check for Persistent:false keyword in attribute description
.//
.// Revision 1.5.58.1  2006/01/06 20:45:26  greg
.// Job: 1051
.// Add check for Persistent:false keyword in attribute description
.//
.// Revision 1.5  2005/07/15 21:57:07  jmather
.// Job: 1081
.// Remove dependency on referential attributes having the type "same_as<BaseAttribute>"
.//
.// Revision 1.4.328.1  2005/07/12 21:55:40  greg
.// Job: 1081
.// Remove dependency on referential attributes having the type "same_as<BaseAttribute>"
.//
.// Revision 1.4.326.1  2005/07/07 17:25:51  greg
.// Job: 1082
.// Remove dependency on referential attributes having the type "same_as<BaseAttribute>"
.//
.// Revision 1.4  2004/09/09 16:29:54  babar
.// Job:175
.// Introduced under i175: compare and support of multi
.// model root generally known as Ooaofooa
.//
.// Revision 1.1.2.5  2004/08/24 06:25:48  babar
.// Job:175
.// Reverted back from head
.//
.// Revision 1.2  2004/08/10 12:05:01  babar
.// Job:175
.// Introduced under i175
.//
.// Revision 1.1.2.4  2004/07/30 07:15:32  siddique
.// Job:175
.// Cleanup performed, updated archetypes, removed extra files and folders 
.// as the inspectors were moved to core, updated create_util_class.java
.//
.// Revision 1.1.2.3  2004/07/15 13:24:08  siddique
.// Job:175
.// Wokring prototype introduced.
.//
.// Revision 1.1.2.2  2004/07/12 06:46:56  siddique
.// Job:175
.// Text Generation support added, without creation of comparison trees.
.//
.// Revision 1.1.2.1  2004/07/07 13:18:42  siddique
.// Job:175
.// Properties file included, archetypes updated and design note re-introduced.
.//
.//
