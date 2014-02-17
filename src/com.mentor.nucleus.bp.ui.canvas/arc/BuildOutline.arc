.//=====================================================================
.//
.// File:      $RCSfile: BuildOutline.arc,v $
.// Version:   $Revision: 1.13 $
.// Modified:  $Date: 2013/01/10 23:19:14 $
.//
.// (c) Copyright 2003-2014 Mentor Graphics Corporation All rights reserved.
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
.//
.// This file is the entry point for generating the code for the
.// Model Outline tree view.
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.//
.assign arc_dir = "arc"
.include "${arc_dir}/create_outline_sorter.inc"
.//
.assign path = "com/mentor/nucleus/bp/ui/canvas"
.assign rel_path = "src/${path}"
.//
.invoke result = create_outline_sorter(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.//