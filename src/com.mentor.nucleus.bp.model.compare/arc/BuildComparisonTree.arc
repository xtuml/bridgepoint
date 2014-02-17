.//========================================================================
.//
.//File:      $RCSfile: BuildComparisonTree.arc,v $
.//Version:   $Revision: 1.2 $
.//Modified:  $Date: 2013/01/10 22:50:00 $
.//
.//Copyright 2004-2014 by Mentor Graphics Corporation. All rights reserved.
.//
.//========================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
.// use this file except in compliance with the License.  You may obtain a copy 
.// of the License at
.//
.//       http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software 
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
.// License for the specific language governing permissions and limitations under
.// the License.
.//========================================================================
.//
.// This file is the entry point for generating the code for the Model Compare
.// tree and text generators
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
.// NOTE: iterator here is a synonym of iterator
.assign arc_dir = "arc"
.include "${arc_dir}/create_properties_file.inc"
.//
.assign path = "com/mentor/nucleus/bp/model/compare"
.assign rel_path = "src/${path}"
.assign package_initial = "com.mentor.nucleus.bp.model.compare"
.//
.// Generating the Properties File
.//
.invoke prop_file = create_properties_file()
${prop_file.body}
.emit to file "${rel_path}/ComparePluginMessages.properties"
.//