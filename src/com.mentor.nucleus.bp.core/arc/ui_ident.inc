.//====================================================================
.//
.// File:      $RCSfile: ui_ident.inc,v $
.// Version:   $Revision: 1.14 $
.// Modified:  $Date: 2013/01/17 03:38:58 $
.//
.// (c) Copyright 2004-2013 by Mentor Graphics Corp.  All rights reserved.
.//
.//=======================================================================
.// This document contains information proprietary and confidential to
.// Mentor Graphics Corp., and is not for external distribution.
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
