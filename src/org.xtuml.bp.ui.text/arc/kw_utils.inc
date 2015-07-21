.//=====================================================================
.//
.// File:      $RCSfile: kw_utils.inc,v $
.// Version:   $Revision: 1.10 $
.// Modified:  $Date: 2013/01/10 23:20:52 $
.//
.// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
.//
.//=====================================================================
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