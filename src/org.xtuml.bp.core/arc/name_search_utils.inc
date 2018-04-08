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
.//====================================================================
.//
.//
.function skip_class_with_name_attr
.param inst_ref obj
.assign attr_skip = false
    .if (obj.Key_Lett == "V_VAR")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "V_PAR")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "RV_VIS")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "MSG_R")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "PE_PE")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "PE_VIS")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "PE_SRS")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "PE_CVS")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "PE_CRS")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "I_INS")
      .assign attr_skip = true
    .end if
.end function
.//-------------------------------------------------------------------------
.function include_class_with_get_name_otfr
.param inst_ref obj
.assign attr_include = false
    .if (obj.Key_Lett == "R_REL")
      .assign attr_include = true
    .elif (obj.Key_Lett == "CNST_CSP")
      .assign attr_include = true
    .elif (obj.Key_Lett == "CNST_LSC")
      .assign attr_include = true
    .elif (obj.Key_Lett == "C_C")
      .assign attr_include = true
    .elif (obj.Key_Lett == "C_DG")
      .assign attr_include = true
    .elif (obj.Key_Lett == "C_SF")
      .assign attr_include = true
    .elif (obj.Key_Lett == "C_IR")
      .assign attr_include = true
    .elif (obj.Key_Lett == "SM_LEVT")
      .assign attr_include = true
    .elif (obj.Key_Lett == "SM_TXN")
      .assign attr_include = true
    .end if
	.// TODO - SKB not done with the list
.end function
