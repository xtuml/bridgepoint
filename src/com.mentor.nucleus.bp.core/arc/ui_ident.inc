.//====================================================================
.//
.// File:      $RCSfile: ui_ident.inc,v $
.// Version:   $Revision: 1.14 $
.// Modified:  $Date: 2013/01/17 03:38:58 $
.//
.// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
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
.//====================================================================
.//
.function get_ui_identifier
  .param inst_ref class
  .param string ref_name
  .//
  .invoke gcn = get_class_name( class )
  .select any hr_id_attr related by class->O_ATTR[R102] where ( selected.Name == "Name" )
  .if ( not_empty hr_id_attr )
    .if (gcn.body == "Component_c")
((${gcn.body})${ref_name}).getLabel()\
    .else
      .invoke gaa = get_attribute_accessor(hr_id_attr)
      .select one dt related by hr_id_attr->S_DT[R114]
      .if (dt.Name == "string")
((${gcn.body})${ref_name}).${gaa.body}()\
      .elif (dt.Name == "integer")
Integer.toString(((${gcn.body})${ref_name}).${gaa.body}())\
      .elif (dt.Name == "unique_id")
Long.toString(((${gcn.body})${ref_name}).${gaa.body}())\
      .end if
    .end if
  .else
((${gcn.body})${ref_name}).Get_name()\
  .end if
.end function
