.//=====================================================================
.//
.// File:      $RCSfile: kw_utils.inc,v $
.// Version:   $Revision: 1.10 $
.// Modified:  $Date: 2013/01/10 23:20:52 $
.//
.// (c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
.//
.//=====================================================================
.// This document contains information proprietary and confidential to
.// Mentor Graphics Corp. and is not for external distribution.
.//=====================================================================

.function contain_in_T_set
	.param inst_ref_set full_set
	.param inst_ref object
	.assign attr_isExist = FALSE
	
	.for each element in full_set
		.if (element.value == object.value)
			.assign attr_isExist = TRUE
			.break for
		.end if
	.end for
.end function
.function make_set_unique
	.param inst_ref_set set
	.assign attr_elements =  set - set	
	.for each element in set
		.invoke exists = contain_in_T_set(attr_elements, element)
		
		.if (exists.isExist == FALSE)
			.assign  attr_elements =  attr_elements | element;			
		.end if 
	.end for
.end function