.//=====================================================================
.//
.// File:      $RCSfile: BuildTree.arc,v $
.// Version:   $Revision: 1.19 $
.// Modified:  $Date: 2013/01/10 23:15:39 $
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
.// Model Explorer tree view.
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "../com.mentor.nucleus.bp.core/color/ooaofooa_package_spec.clr"
.include "${mc_archetypes}/mfp_utils.inc"
.include "${mc_archetypes}/arch_utils.inc"
.//
.//
.invoke package = get_package()
.invoke compMark = markComponentsAndContainments()
.invoke chain_util_env = GET_ENV_VAR( "CHAIN_UTIL_DIR" )
.assign chain_util = chain_util_env.result
.if ( chain_util == "" )
  .print "\nERROR: Environment variable CHAIN_UTIL_DIR not set."
  .exit 100
.end if
.include "${chain_util}/arc/parse_chain.inc"
.//
.assign arc_dir = "arc"
.include "${arc_dir}/create_adapters.inc"
.include "${arc_dir}/create_label_provider.inc"
.include "${arc_dir}/create_content_provider.inc"
.include "${arc_dir}/create_filters_content_provider.inc"
.include "${arc_dir}/create_explorer_view.inc"
.//
.assign path = "com/mentor/nucleus/bp/ui/explorer"
.assign rel_path = "src/${path}"
.//
.invoke result = create_explorer_view(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.select many tree_nodes from instances of T_TNS
.for each node in tree_nodes
  .assign node_name = "$Cr{node.CategoryName}"
  .invoke result = create_adapters(node, node_name, path)
${result.body}
  .emit to file "${rel_path}/adapters/${result.result}.java"
.end for
.invoke result = create_label_provider(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.invoke result = create_content_provider(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.invoke result = create_filters_content_provider(path)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.//