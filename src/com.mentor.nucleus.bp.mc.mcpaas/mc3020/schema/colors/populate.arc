.//============================================================================
.// File:      populate.arc
.//
.// Description:
.// This archetype file contains instance population definitions
.// for each object tagged as a static population.
.//
.// Notice:
.// (C) Copyright 1999-2014 Mentor Graphics Corporation
.//     All rights reserved.
.//
.//============================================================================
.//
.//============================================================================
.// *** Static population utilities:
.//============================================================================
.//
.//============================================================================
.// StaticManyRelationshipPopulateionBody
.//
.// <Abstract>
.//  This function defines object node list array for static instance
.//  populations having 'many' type relathionship. Here are;
.//    Input parameter condition:
.//      attr_MaxExtentSize >= attr_StaticExtentSize
.//    The array definition format:
.//      {previous_node, object}
.//        Where:
.//        previous_node:create a link to a previous node list array member
.//                     :null(0) means very top node of a link
.//        object       :specify object pool array member
.//    Note:
.//      The very last node of a link should be stored a participant
.//      object set attribute, like mc_C_R2{&T_B_C_R2_node1s[0], 0}
.//    Example:
.//      {&T_B_C_R2_node1s[0], 0}
.// <Input>
.//  obj:instance reference to an object
.//  rel:instance reference to a relationship
.// <Output>
.//  attr_MaxExtentSize:max size of an instance array for each object
.//  attr_StaticExtentSize:size of a static instance array for each object
.//  body:node list array
.// <Example>
.//  .function StaticManyRelationshipPopulateionBody
.//    .param inst_ref obj  .// O_OBJ
.//    .param inst_ref rel  .// R_REL
.//    .assign attr_MaxExtentSize = 0
.//    .assign attr_StaticExtentSize = 0
.//    .//
.//    .if ((obj.Key_Lett == "B") and (rel.Numb == 11))
.//      .assign attr_MaxExtentSize = 2
.//      .assign attr_StaticExtentSize = 2
.//    {0                   , (void *)&T_M_ObjectPool_s[0]},
.//    {&T_B_M_R11_node1s[0], (void *)&T_M_ObjectPool_s[1]}
.//    .elif ((obj.Key_Lett == "C") and (rel.Numb == 11))
.//      .assign attr_MaxExtentSize = 2
.//      .assign attr_StaticExtentSize = 2
.//    {0                   , (void *)&T_M_ObjectPool_s[0]},
.//    {&T_C_M_R11_node1s[0], (void *)&T_M_ObjectPool_s[1]}
.//    .else
.//      .assign attr_MaxExtentSize = 0
.//      .assign attr_StaticExtentSize = 0
.//    .end if
.//  .end function
.//============================================================================
.function StaticManyRelationshipPopulateionBody
  .param inst_ref obj  .// O_OBJ
  .param inst_ref rel  .// R_REL
  .assign attr_MaxExtentSize = 0
  .assign attr_StaticExtentSize = 0
  .//
  .if ((obj.Key_Lett == "") and (rel.Numb == 1))
    .assign attr_MaxExtentSize = 0
    .assign attr_StaticExtentSize = 0
  .else
    .assign attr_MaxExtentSize = 0
    .assign attr_StaticExtentSize = 0
  .end if
.end function
.//
.//============================================================================
.// StaticInstancePopulateionBody
.//
.// <Abstract>
.//  This function defines instance array list for static instance
.//  populations.
.// <Input>
.//  obj:instance reference to an object
.// <Output>
.//  attr_StaticExtentSize:size of a static instance array for each object
.//  body:instance array list
.// <Example>
.//  .function StaticInstancePopulateionBody
.//    .param inst_ref obj  .// O_OBJ
.//    .assign attr_StaticExtentSize = 0
.//    .if (obj.Key_Lett == "T")
.//      .assign attr_StaticExtentSize = 1
.//      .// tubeID, power, current_state
.//    {1, 500, O_T_STATE_1}
.//    .elif (obj.Key_Lett == "L")
.//      .assign attr_StaticExtentSize = 1
.//      .// lightID, current_state
.//    {1, O_L_STATE_1}
.//    .elif (obj.Key_Lett == "")
.//      .assign attr_StaticExtentSize = 0
.//    .end if
.//  .end function
.//============================================================================
.function StaticInstancePopulateionBody
  .param inst_ref obj  .// O_OBJ
  .assign attr_StaticExtentSize = 0
  .if (obj.Key_Lett == "A")
    .assign attr_StaticExtentSize = 0
  .elif (obj.Key_Lett == "")
    .assign attr_StaticExtentSize = 0
  .end if
.end function
.//
.//============================================================================
.// Do not add anything at the end of this file!
.//============================================================================
