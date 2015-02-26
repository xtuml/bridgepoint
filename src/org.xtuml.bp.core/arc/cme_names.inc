.//====================================================================
.//
.// File:      $RCSfile: cme_names.inc,v $
.// Version:   $Revision: 1.10 $
.// Modified:  $Date: 2013/01/10 22:54:04 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//  The functions in this file provide the name encodings for
.//  context menu entries and wizards in the generated code.
.//
.//====================================================================
.//
.function get_func_name
  .param inst_ref cme
$U_{cme.Key_Lett}_$Cr{cme.Specialism}$Cr{cme.Label}\
.end function
.//
.function get_action_class_name
  .param inst_ref cme
$Cr{cme.Specialism}$Cr{cme.Label}On$U_{cme.Key_Lett}Action\
.end function
.//
.function get_wizard_class_name
  .param inst_ref wfl
$Cr{wfl.Specialism}$Cr{wfl.Label}On$U_{wfl.Key_Lett}Wizard\
.end function
.//
.function get_page_name
  .param inst_ref wfl
$Cr{wfl.Specialism}$Cr{wfl.Label}On$U_{wfl.Key_Lett}WizardPage\
.end function
.//
.function get_label
  .param integer number
  .param inst_ref descrip_elem
  .if (number == 0)
${descrip_elem.descrip:UILabel}\
  .elif (number == 1)
${descrip_elem.descrip:UILabel1}\
  .elif (number == 2)
${descrip_elem.descrip:UILabel2}\
  .elif (number == 3)
${descrip_elem.descrip:UILabel3}\
  .elif (number == 4)
${descrip_elem.descrip:UILabel4}\
  .elif (number == 5)
${descrip_elem.descrip:UILabel5}\
  .elif (number == 6)
${descrip_elem.descrip:UILabel6}\
  .elif (number == 7)
${descrip_elem.descrip:UILabel7}\
  .elif (number == 8)
${descrip_elem.descrip:UILabel8}\
  .elif (number == 9)
${descrip_elem.descrip:UILabel9}\
  .end if
.end function