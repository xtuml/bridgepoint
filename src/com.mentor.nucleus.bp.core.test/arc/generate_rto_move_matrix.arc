.//=======================================================================
.//
.// File:      $$RCSfile: generate_rto_move_matrix.arc,v $$
.// Version:   $$Revision: 1.3 $$
.// Modified:  $$Date: 2013/01/10 22:49:22 $$
.//
.// Copyright 2005-2014 Mentor Graphics Corporation.  All rights reserved.
.//
.//=======================================================================
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
.//=======================================================================
.include "../com.mentor.nucleus.bp.core/arc/generate_RGO_resolution_methods.inc"
.function get_cells
  .param Inst_Ref rto
  .param Inst_Ref_Set test_rtos
  .param Integer d
    .assign test_rto_count = cardinality test_rtos
    .assign total_columns = test_rto_count * 4
    .assign rto_index = 0
    .for each test_rto in test_rtos
      .assign rto_index = rto_index + 1
      .if(test_rto.Name == rto.Name)
        .break for
      .end if
    .end for
    .assign count = 0
    .assign space = "      "
    .assign matrix = ""
    .assign current_rto = 1
    .assign cCount = 0
    .while(count < total_columns)
      .assign cCount = cCount + 1
      .if(rto_index != current_rto)
        .assign matrix = matrix + "X "
      .else
        .if((cCount <= 2) and (d == 1))
          .assign matrix = matrix + "R1"
        .end if
        .if((cCount <= 2) and (d == 2))
          .assign matrix = matrix + "R2"
        .end if
        .if((cCount > 2) and (d == 1))
          .assign matrix = matrix + "R3"
        .end if
        .if((cCount > 2) and (d == 2))
          .assign matrix = matrix + "R2"
        .end if
      .end if
      .assign count = count + 1
      .assign matrix = matrix + space
      .if(cCount == 4)
        .assign cCount = 0
        .assign current_rto = current_rto + 1
      .end if
    .end while
${matrix}
.end function
.function generate_matrix
  .// generate the column top
  .assign source_count = 0
  .select many objects from instances of O_OBJ
  .select many test_rtos from instances of O_OBJ
  .assign test_rtos = test_rtos - objects
  .for each object in objects
    .invoke supported_rto = is_supported_class(object.Key_Lett)
    .if(supported_rto.result)
       .assign source_count = source_count + 1
       .assign test_rtos = test_rtos | object
    .end if
  .end for
  .assign source_count = source_count * 4
  .assign matrix_row = "        "
  .assign count = 0
  .assign cCount = 0
  .assign aCount = 1
  .while(count < source_count)
    .assign count = count + 1
    .assign cCount = cCount + 1
    .assign matrix_row = matrix_row + "A${aCount}C${cCount}"
    .if(cCount == 4)
      .assign cCount = 0
      .assign aCount = aCount + 1
    .end if
    .assign matrix_row = matrix_row + "    "
  .end while
${matrix_row}
  .assign bCount = 1
  .for each object in objects
    .invoke supported_rto = is_supported_class(object.Key_Lett)
    .if(supported_rto.result)
      .select many rtos related by object->R_OIR[R201]->R_RTO[R203]
      .for each rto in rtos
        .select one rel related by rto->R_OIR[R203]->R_REL[R201]
        .invoke rel_support = is_rel_supported(rel.Numb)
        .if(rel_support.result)
          .invoke result = get_rgo_objects_associated_with_rto(rto)
          .assign rgoObjects = result.rgoObjects
          .for each rgoObject in rgoObjects
            .invoke supported_rgo = is_supported_rgo(rgoObject.Key_Lett)
            .assign isSupported = supported_rgo.result
            .if(isSupported)
              .assign count = 0
              .assign dCount = 0
              .while(count < 2)
                .assign count = count + 1
                .assign dCount = dCount + 1
                .assign matrix_row = "B${bCount}D${dCount}"
                .assign spacing = "    "
                .if(bCount > 9)
                  .assign spacing = "   "
                .end if
                .assign matrix_row = matrix_row + spacing
                .invoke cells = get_cells(object, test_rtos, dCount)
                .assign matrix_row = matrix_row + cells.body
${matrix_row}
                .assign matrix_row = ""
                .if(dCount == 2)
                  .assign dCount = 0
                .end if
              .end while
              .assign bCount = bCount + 1
            .end if
          .end for
        .end if
      .end for
    .end if
  .end for
.end function
.function is_supported_rgo
  .param String key_lett
    .assign attr_result = false
    .if(key_lett == "C_IO")
      .assign attr_result = true
    .elif(key_lett == "C_PP")
      .assign attr_result = true    
    .elif(key_lett == "S_BRG")
      .assign attr_result = true    
    .elif(key_lett == "S_BPARM")
      .assign attr_result = true    
    .elif(key_lett == "S_SYNC")
      .assign attr_result = true    
    .elif(key_lett == "S_SPARM")
      .assign attr_result = true    
    .elif(key_lett == "S_MBR")
      .assign attr_result = true    
    .elif(key_lett == "SM_EVTDI")
      .assign attr_result = true    
    .elif(key_lett == "O_ATTR")
      .assign attr_result = true    
    .elif(key_lett == "O_TFR")
      .assign attr_result = true    
    .elif(key_lett == "O_TPARM")
      .assign attr_result = true    
    .elif(key_lett == "S_UDT")
      .assign attr_result = true    
    .elif(key_lett == "CL_IC")
      .assign attr_result = true    
    .elif(key_lett == "C_IR")
      .assign attr_result = true    
    .elif(key_lett == "SPR_PEP")
      .assign attr_result = true    
    .elif(key_lett == "SPR_REP")
      .assign attr_result = true    
    .elif(key_lett == "CL_IIR")
      .assign attr_result = true
    .elif(key_lett == "CL_POR")
      .assign attr_result = true  
    .end if
.end function
.function is_rel_supported
  .param Integer rel_numb
    .assign attr_result = true
    .if(rel_numb == 4205)
      .assign attr_result = false
    .elif(rel_numb == 4006)
      .assign attr_result = false
    .elif(rel_numb == 4004)
      .assign attr_result = false
    .elif(rel_numb == 17)
      .assign attr_result = false
    .elif(rel_numb == 1500)
      .assign attr_result = false
    .elif(rel_numb == 4016)
      .assign attr_result = false  
    .end if
.end function
#===========================================================================
#
# Description:
# This test matrix is used to test move capabilities
#
#
# Version:      $$Revision: 1.3 $$
#
# Notice:
# (C) Copyright 2007-2014 Mentor Graphics Corporation
#     All rights reserved.
#===========================================================================
#
#
Degrees of Freedom:

rto(A)
.assign count = 0
.select many objects from instances of O_OBJ
.for each object in objects
  .invoke supported_rto = is_supported_class(object.Key_Lett)
  .if(supported_rto.result)
    .assign count = count + 1
${count}. ${object.Name}
  .end if
.end for

rgo(B)
.assign count = 0
.for each object in objects
  .invoke supported_rto = is_supported_class(object.Key_Lett)
  .if(supported_rto.result)
    .select many rtos related by object->R_OIR[R201]->R_RTO[R203]
    .for each rto in rtos
      .select one rel related by rto->R_OIR[R203]->R_REL[R201]
      .invoke rel_support = is_rel_supported(rel.Numb)
      .if(rel_support.result)
        .invoke result = get_rgo_objects_associated_with_rto(rto)
        .assign rgoObjects = result.rgoObjects
        .for each rgoObject in rgoObjects
          .invoke supported_rgo = is_supported_rgo(rgoObject.Key_Lett)
          .assign isSupported = supported_rgo.result
          .if(isSupported)
            .assign count = count + 1
${count}. ${rgoObject.Name}
          .end if
        .end for
      .end if
    .end for
  .end if
.end for

destination(C)
1. Within Scope (Same Project)
2. Within Scope (Different Project) 
3. Out of Scope (Same Project)
4. Out of Scope (Different Project)

RGO reference(D)
1. Default
2. Not Default

Results:
1. rgoResolvedChanged      "Referring element was not set back to cut rto"
2. rgoResolvedNotChanged   "Referring element was not left as existing rto"
3. rgoUnresolved           "Referring element was not left as default rto"

Matrix:
.invoke matrix = generate_matrix()
${matrix.body}
.emit to file "matrices/generated/RTOMoveMatrix.txt"
.// generate a mapping file for retrieval of test elements
//========================================================================
//
// File: ElementMap.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.3 $$
//
// Copyright 2005-2014 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 
package com.mentor.nucleus.bp.core.test.rtomove;

import java.util.HashMap;

import com.mentor.nucleus.bp.core.*;

public class ElementMap {
	static HashMap<String, Class<?>> map = new HashMap<String, Class<?>>();
	
	public static Class<?> getElementType(String key) {
		if(map.isEmpty()) {
			initializeMap();
		}
		return map.get(key);
	}

	private static void initializeMap() {
.assign count = 0
.for each object in objects
  .invoke supported_rto = is_supported_class(object.Key_Lett)
  .if(supported_rto.result)
    .assign count = count + 1
		map.put("A${count}", $Cr{object.Name}_c.class);
  .end if
.end for
.assign count = 0
.for each object in objects
  .invoke supported_rto = is_supported_class(object.Key_Lett)
  .if(supported_rto.result)
    .select many rtos related by object->R_OIR[R201]->R_RTO[R203]
    .for each rto in rtos
      .select one rel related by rto->R_OIR[R203]->R_REL[R201]
      .invoke rel_support = is_rel_supported(rel.Numb)
      .if(rel_support.result)
        .invoke result = get_rgo_objects_associated_with_rto(rto)
        .assign rgoObjects = result.rgoObjects
        .for each rgoObject in rgoObjects
          .invoke supported_rgo = is_supported_rgo(rgoObject.Key_Lett)
          .assign isSupported = supported_rgo.result
          .if(isSupported)
            .assign count = count + 1
		map.put("B${count}", $Cr{rgoObject.Name}_c.class);
          .end if
        .end for
      .end if
    .end for
  .end if
.end for
	}
	
	public static String getAssociationFor(String key) {
.assign aCount = 0;
.assign bCount = 0;
.for each object in objects
  .invoke supported_rto = is_supported_class(object.Key_Lett)
  .if(supported_rto.result)
    .assign aCount = aCount + 1
		if(key.contains("A${aCount}")) {
    .select many rtos related by object->R_OIR[R201]->R_RTO[R203]
    .for each rto in rtos
      .select one rel related by rto->R_OIR[R203]->R_REL[R201]
      .invoke rel_support = is_rel_supported(rel.Numb)
      .if(rel_support.result)
        .invoke result = get_rgo_objects_associated_with_rto(rto)
        .assign rgoObjects = result.rgoObjects
        .for each rgoObject in rgoObjects
          .invoke supported_rgo = is_supported_rgo(rgoObject.Key_Lett)
          .assign isSupported = supported_rgo.result
          .if(isSupported)
            .assign bCount = bCount + 1
			if(key.contains("B${bCount}D")) {
				return "${rel.Numb}";
			}
          .end if
        .end for
      .end if
    .end for
		}
  .end if
.end for
		return "";
	}
}
.emit to file "src/com/mentor/nucleus/bp/core/test/rtomove/ElementMap.java"