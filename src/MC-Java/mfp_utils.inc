.//====================================================================
.//
.// File:      $RCSfile: mfp_utils.inc,v $
.// Version:   $Revision: 1.12 $
.// Modified:  $Date: 2013/01/10 23:16:39 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.//  This file contains utility functions that are common to mfp code.
.//
.//====================================================================
.//
.//returns whether given O_OBJ inst is a component root or not
.function is_componentRootElement
  .param inst_ref class   .// O_OBJ
  .//
  .assign attr_result = false  
  .if (class.AdapterName != "")
     .assign attr_result = true
  .end if
.end function
.//===========
.//returns whether given Key_Lett is of a component root element or not
.function is_componentRootElementKL
  .param string key_Lett   .// O_OBJ.Key_Lett
  .//
  .assign attr_result = false  
  .select any class from instances of O_OBJ where (selected.Key_Lett == key_Lett)
  
  .if(not_empty class)
     .invoke r = is_componentRootElement(class)
     .assign attr_result = r.result
  .end if
.end function
.//
.function isContainmentAssociation
  .param inst_ref rel

  .assign attr_result = false 
  .if ( not_empty rel )
    .if(rel.ContainmentClassKL != "")
  		.assign attr_result = true     
    .end if
  .end if
.end function
.//
.function isCrossComponentAssociation
  .param inst_ref loc_obj
  .param inst_ref tar_obj
  .param inst_ref rel
  
  .assign attr_result = false

  .invoke containment = isContainmentAssociation(rel)
  .if(containment.result == true)
    .assign attr_result = true
  .end if

  .if(attr_result == false)
    .select any loc_eo from instances of EO where (( selected.Name == loc_obj.Name ) and ( selected.WritePosition != "none" ))
    .select any tar_eo from instances of EO where (( selected.Name == tar_obj.Name ) and ( selected.writePosition != "none" ))
  
    .//if any one of tar and loc obj is not persistable, need not to generate lazy loading code
    .if((not_empty loc_eo) and (not_empty tar_eo) )
      .//check relations explicitly marked as CrossComponent
      .assign cross_kw = "$l{rel.Descrip:CrossComponent}"
      .if (cross_kw == "true")
        .if (tar_obj.Key_Lett != "PE_PE")
          .assign attr_result = (cross_kw == "true")
        .else
          .assign loc_KL = loc_obj.Key_Lett
          .if ((((loc_KL == "EP_PKG") or (loc_KL == "C_C")) or (loc_KL == "O_OBJ")) or (loc_KL == "C_I"))
            .assign attr_result = (cross_kw == "true")
          .else
            .assign attr_result = (cross_kw != "true")
          .end if
        .end if
      .end if
    .end if
  .end if
.end function

.function get_component_root_element_eo
  .param inst_ref eo
.//	
	.assign attr_has_parent = false
	.assign attr_parent_eo=eo
	
	.if(not_empty attr_parent_eo)  
	 .while((not_empty attr_parent_eo) and (attr_parent_eo.ComponentRoot==False))
       .select many parent_eos related by attr_parent_eo->EO[R1.'is_parent_of']  where (selected.writePosition!="none")
          .if(empty parent_eos)
	    	.//Get the first sibling 				
	        .select any pre_sibling related by attr_parent_eo->EO[R2.'follows']
	        .if (not_empty pre_sibling)    
	    	  .assign first_sibling = pre_sibling
		      .while (not_empty pre_sibling)
		    	.assign first_sibling = pre_sibling
		    	.select any pre_sibling related by first_sibling->EO[R2.'follows']
	    	  .end while
	    	  .select many parent_eos related by first_sibling->EO[R1.'is_parent_of'] where (selected.writePosition!="none")
            .end if
          .end if
          .if(not_empty parent_eos)
            .for each peo in parent_eos
               .assign attr_parent_eo=peo
               .break for
            .end for
          .else
            .//has no root element(graphical element)
            .assign attr_parent_eo=eo-eo
            .break while
          .end if
	 .end while
   .end if
	
   .if (not_empty attr_parent_eo)
	  .assign attr_has_parent = true
   .end if
.end function
