.//====================================================================
.//
.// File:      $RCSfile: wizard.arc,v $
.// Version:   $Revision: 1.12 $
.// Modified:  $Date: 2013/05/12 00:16:21 $
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
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.include "arc/cme_names.inc"
.include "arc/function_body.inc"
.include "arc/wizard.inc"
.include "arc/page.inc"
.include "arc/wfl_processing.inc"
.include "arc/ui_processing.inc"
.include "arc/ui_ident.inc"
.//
.select many workflows from instances of W_WOF
.for each wfl in workflows
  .select many fields related by wfl->W_STEP[R2005]->W_FLD[R2009]
  .if (not_empty fields)
    .invoke wizard_name = get_wizard_class_name(wfl)
    .invoke result =  skip_class_generation(wizard_name.body)
    .if (result.skip)
  	  	.// skipping the generation becasue this file is version controlled
  	.else
    	.invoke wizard = create_wizard(wfl)
${wizard.body}
    	.emit to file "src/com/mentor/nucleus/bp/core/ui/${wizard_name.body}.java"
    .end if
    .select many steps related by wfl->W_STEP[R2005]
    .for each step in steps
      .invoke gpcn = get_page_class_name(wfl, step)
      .assign page_class_name = gpcn.name
      .invoke result =  skip_class_generation(page_class_name)
  	  .if (result.skip)
  	  	.// skipping the generation becasue this file is version controlled
  	  .else
         .invoke page = create_page(wfl, step)
${page.body}
         .emit to file "src/com/mentor/nucleus/bp/core/ui/${page.name}.java"
      .end if
    .end for
  .end if
.end for