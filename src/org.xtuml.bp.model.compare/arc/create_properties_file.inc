.//========================================================================
.//
.//File:      $RCSfile: create_properties_file.inc,v $
.//Version:   $Revision: 1.4 $
.//Modified:  $Date: 2013/01/17 03:36:13 $
.//
.//Copyright 2004-2014 by Mentor Graphics Corporation. All rights reserved.
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
ComparePlugin.MainWindow.Title= BridgePoint Model Comparison
title= BridgePoint Structural Compare
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
action.CopyLeftToRight.label=
action.CopyLeftToRight.tooltip=Copy all non-conflicting differences from left to right
action.CopyLeftToRight.image=copy_r_co.gif
action.CopyRightToLeft.label=
action.CopyRightToLeft.tooltip=Copy all non-conflicting differences from right to left
action.CopyRightToLeft.image=copy_l_co.gif
action.CopyDiffLeftToRight.label=
action.CopyDiffLeftToRight.image=copycont_r_co.gif
action.CopyDiffLeftToRight.tooltip=Copy selected differences from left to right
action.CopyDiffRightToLeft.label=
action.CopyDiffRightToLeft.image=copycont_l_co.gif
action.CopyDiffRightToLeft.tooltip=Copy selected differences from right to left
action.EnableAncestor.label=Enable Ancestor Pane
action.EnableAncestor.tooltip.unchecked=Show Ancestor Pane
action.EnableAncestor.tooltip.checked=Hide Ancestor Pane
action.EnableAncestor.description.unchecked=Show Ancestor Pane
action.EnableAncestor.description.checked=Hide Ancestor Pane
action.EnableAncestor.image=ancestorpane_co.gif
saveDialog.message= Save changes before leaving BridgePoint Structural Compare?
saveDialog.title= Unsaved Changes
.end function
