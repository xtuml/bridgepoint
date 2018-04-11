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
.// This function processes objects that have a Name attribute.  We generally want
.// to include these in name-based searches, but there are a few we don't want
.// so we skip them.
.function skip_class_with_name_attr
.param inst_ref obj
.assign attr_skip = false
    .if (obj.Key_Lett == "V_VAR")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "V_PAR")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "RV_VIS")
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
    .elif (obj.Key_Lett == "C_EP")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "S_EEEDI")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "SM_NLEVT")
      .assign attr_skip = true
    .elif (obj.Key_Lett == "S_DT")
      .assign attr_skip = true
    .end if
.end function
.//-------------------------------------------------------------------------
.// This function processes objects that have a get_name() operation.  Unlike above,
.// here we generally want to _skip_ these in the search, but there are some we do
.// want so we include them.
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
    .elif (obj.Key_Lett == "SM_EVT")
      .assign attr_include = true
    .elif (obj.Key_Lett == "SM_TXN")
      .assign attr_include = true
    .elif (obj.Key_Lett == "SM_NSTXN")
      .assign attr_include = true
    .elif (obj.Key_Lett == "SM_CRTXN")
      .assign attr_include = true
    .elif (obj.Key_Lett == "O_IOBJ")
      .assign attr_include = true
    .elif (obj.Key_Lett == "O_RATTR")
      .assign attr_include = true
    .elif (obj.Key_Lett == "COMM_LNK")
      .assign attr_include = true
    .elif (obj.Key_Lett == "S_EDT")
      .assign attr_include = true
    .elif (obj.Key_Lett == "S_UDT")
      .assign attr_include = true
    .elif (obj.Key_Lett == "S_SDT")
      .assign attr_include = true
    .elif (obj.Key_Lett == "SQ_AV")
      .assign attr_include = true
    .elif (obj.Key_Lett == "SQ_EEP")
      .assign attr_include = true
    .elif (obj.Key_Lett == "SQ_CP")
      .assign attr_include = true
    .elif (obj.Key_Lett == "SQ_COP")
      .assign attr_include = true
    .elif (obj.Key_Lett == "SQ_PP")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_AM")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_B")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_E")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_F")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_IA")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_IAM")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_ISM")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_IOP")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_A")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_O")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_SIG")
      .assign attr_include = true
    .elif (obj.Key_Lett == "MSG_SM")
      .assign attr_include = true
    .end if
.end function
