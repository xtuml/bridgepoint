.//=====================================================================
.//
.// File:      $RCSfile: create_util.inc,v $
.// Version:   $Revision: 1.20 $
.// Modified:  $Date: 2013/01/10 23:20:52 $
.//
.// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
.//
.//============================================================
.// get_query_class_name - returns the class name for a test interface for an object
.//============================================================
.function get_query_class_name
  .param inst_ref obj
  .//
query$cr{obj.name}_c\
.end function
.//
.//
.//============================================================
.// get_id_attr_set - returns the set of identifying attributes for an object
.//============================================================
.function get_id_attr_set
  .param inst_ref obj
  .//
  .print "Processing IdSet for object ${obj.name} (${obj.key_lett})"
  .select any obj_id from instances of O_ID where (selected.oid_id == -1)
  .assign id_set_number = 0
  .while (id_set_number < 3)
    .select any obj_id related by obj->O_ID[R104] where (selected.oid_id == id_set_number)
    .assign count = cardinality obj_id
    .if (count > 0)
      .break while
    .else
      .assign id_set_number = id_set_number + 1
    .end if
  .end while
  .if (id_set_number > 2)
    .print "Cannot handle class without an identifier: ${obj.name}"
    .exit 99
  .end if
  .select many obj_id_attr_set related by obj_id->O_OIDA[R105]
  .select many attr_set related by obj_id_attr_set->O_ATTR[R105]
  .assign attr_attr_set = attr_set
.end function
.//
.//
.//============================================================
.// get_core_datatype - returns the core data type of an attribute
.//============================================================
.function get_core_datatype
  .param inst_ref attr
  .//
  .select one base_attr related by attr->O_BATTR[R106]
  .if (empty base_attr)
    .select one base_attr related by attr->O_RATTR[R106]->O_BATTR[R113]
  .end if
  .select one ult_attr related by base_attr->O_ATTR[R106]
  .select one dt related by ult_attr->S_DT[R114]
  .select one cdt related by dt->S_CDT[R17]
  .if (empty cdt)
    .select one cdt related by dt->S_UDT[R17]->S_DT[R18]->S_CDT[R17]
  .end if
  .assign attr_cdt = cdt
.end function
.//
.//
.//============================================================
.// get_editor_obj_set - returns several sets of objects:
.//    attr_dsc_obj_set - set of objects with attribute named "Descrip"
.//    attr_oal_obj_set = set of objects with attribute named "Action_Semantics"
.//    attr_canvas_dsc_obj_set - set of objects which have canvas editors and
.//            an attribute named "Descrip"
.//    attr_canvas_oal_obj_set - set of objects which have canvas editors and
.//            an attribute named Action_Semantics"
.//    attr_both_obj_set - set of objects which have an attributed named
.//            "Descrip" and/or one named "Action_Semantics"
.//    attr_dsc_source_set - set of objects specified by NAV_OBJ.src_key_lett
.//            with Usage either "Both" or "Descrip"
.//    attr_oal_source_set - set of objects specified by NAV_OBJ.src_key_lett
.//            with Usage either "Both" or "Action_Semantics"
.//============================================================
.function get_editor_obj_set
  .//
  .select many dsc_attr_set from instances of O_ATTR where (selected.name == "Descrip")
  .select many dsc_obj_set related by dsc_attr_set->O_OBJ[R102]
  .for each obj in dsc_obj_set
    .select many clr_obj_set from instances of NAV_OBJ where (selected.src_key_lett == obj.key_lett)
    .if ( not_empty clr_obj_set )
      .assign remove_src_obj = false
      .for each clr_obj in clr_obj_set
        .if ( (clr_obj.Usage == "Both") or (clr_obj.Usage == "Descrip") )
          .select any tgt_obj from instances of O_OBJ where (selected.key_lett == clr_obj.tgt_key_lett)
          .assign dsc_obj_set = dsc_obj_set | tgt_obj
          .assign remove_src_obj = true
        .end if
      .end for
      .if ( remove_src_obj )
        .assign dsc_obj_set = dsc_obj_set - obj
      .end if
    .end if
  .end for
  .select many oal_attr_set from instances of O_ATTR where (selected.name == "Action_Semantics")
  .select many oal_obj_set related by oal_attr_set->O_OBJ[R102]
  .for each obj in oal_obj_set
    .select many clr_obj_set from instances of NAV_OBJ where (selected.src_key_lett == obj.key_lett)
    .if ( not_empty clr_obj_set )
      .assign remove_src_obj = false
      .for each clr_obj in clr_obj_set
        .if ( (clr_obj.Usage == "Both") or (clr_obj.Usage == "Action_Semantics") )
          .select any tgt_obj from instances of O_OBJ where (selected.key_lett == clr_obj.tgt_key_lett)
          .assign oal_obj_set = oal_obj_set | tgt_obj
          .assign remove_src_obj = true
        .end if
      .end for
      .if ( remove_src_obj )
        .assign oal_obj_set = oal_obj_set - obj
      .end if
    .end if
  .end for
  .//
  .// Get the set of objects which have description editors and canvas editors.
  .// The default editor will be the canvas editor in every case.
  .// There are no objects which have both canvas editors and action semantics editors
  .select many canvas_dsc_obj_set from instances of O_OBJ where ((selected.key_lett == "S_DOM") OR (selected.key_lett == "S_SS"))
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "SM_ISM")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "SM_ASM")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "SQ_S")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "COMM_COMM")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "UC_UCC")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "A_A")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "CP_CP")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "C_C")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "IP_IP")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "S_SYS")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .select any another_obj from instances of O_OBJ where ( selected.key_lett == "EP_PKG")
  .assign canvas_dsc_obj_set = canvas_dsc_obj_set | another_obj
  .assign canvas_oal_obj_set = canvas_dsc_obj_set - canvas_dsc_obj_set
  .//
  .assign both_obj_set = oal_obj_set | dsc_obj_set
  .//
  .select many dsc_nav_source_set from instances of NAV_OBJ where ((selected.Usage == "Descrip") or (selected.Usage == "Both"))
  .select many attr_dsc_source_set from instances of O_OBJ where (selected.Key_Lett == "-1")
  .for each dsc_nav_source in dsc_nav_source_set
    .select any src_obj from instances of O_OBJ where (selected.Key_Lett == dsc_nav_source.src_key_lett)
    .assign attr_dsc_source_set = attr_dsc_source_set | src_obj
  .end for
  .//
  .select many oal_nav_source_set from instances of NAV_OBJ where ((selected.Usage == "Action_Semantics") or (selected.Usage == "Both"))
  .select many attr_oal_source_set from instances of O_OBJ where (selected.Key_Lett == "-1")
  .for each oal_nav_source in oal_nav_source_set
    .select any src_obj from instances of O_OBJ where (selected.Key_Lett == oal_nav_source.src_key_lett)
    .assign attr_oal_source_set = attr_oal_source_set | src_obj
  .end for
  .//  
  .assign attr_dsc_obj_set = dsc_obj_set
  .assign attr_oal_obj_set = oal_obj_set
  .assign attr_canvas_dsc_obj_set = canvas_dsc_obj_set
  .assign attr_canvas_oal_obj_set = canvas_oal_obj_set
  .assign attr_both_obj_set = both_obj_set
.end function
.//
.//
.//============================================================
.// get_model_adapter_class_name - returns the class name for the
.//     model adapter class
.//============================================================
.function get_model_adapter_class_name
  .//
  .assign attr_class_name = "ModelAdapter"
.end function
.//
