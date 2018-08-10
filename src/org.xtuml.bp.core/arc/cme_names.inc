.//====================================================================
.//
.// File:      cme_names.inc
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
  .if ("${cme.Specialism}" == "--")
$U_{cme.Key_Lett}_$Cr{cme.Label}\
  .else
$U_{cme.Key_Lett}_$Cr{cme.Specialism}$Cr{cme.Label}\
  .end if
.end function
.//
.function get_action_class_name
  .param inst_ref cme
  .if ("${cme.Specialism}" == "--")
$Cr{cme.Label}On$U_{cme.Key_Lett}Action\
  .else
$Cr{cme.Specialism}$Cr{cme.Label}On$U_{cme.Key_Lett}Action\
  .end if
.end function
.//
.function get_wizard_class_name
  .param inst_ref wfl
  .if ("${wfl.Specialism}" == "--")
$Cr{wfl.Label}On$U_{wfl.Key_Lett}Wizard\
  .else
$Cr{wfl.Specialism}$Cr{wfl.Label}On$U_{wfl.Key_Lett}Wizard\
  .end if
.end function
.//
.function get_page_name
  .param inst_ref wfl
  .if ("${wfl.Specialism}" == "--")
$Cr{wfl.Label}On$U_{wfl.Key_Lett}WizardPage\
  .else
$Cr{wfl.Specialism}$Cr{wfl.Label}On$U_{wfl.Key_Lett}WizardPage\
  .end if
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