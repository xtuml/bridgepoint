.//=====================================================================
.//
.// File:      $RCSfile$
.// Version:   $Revision$
.// Modified:  $Date$
.//
.// (c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
.//=======================================================================
.//
.// This file is the entry point for generating the code for the
.// Model Explorer tree view.
.//
.// The following variables will distinguish this tree from others
.assign prefix = "**PREFIX**"
.assign label_provider_prefix = "**LABEL_PROVIDER_PREFIX**"
.assign plugin_name = "**PLUGIN_NAME**"
.assign path = "**PROJECT_WITH_SLASH**"
.assign package = "**PROJECT**"
.assign plugin_package = "**PLUGIN_PACKAGE**"
.assign rel_path = "src/${path}"
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
.//
.invoke chain_util_env = GET_ENV_VAR( "CHAIN_UTIL_DIR" )
.assign chain_util = chain_util_env.result
.if ( chain_util == "" )
  .print "\nERROR: Environment variable CHAIN_UTIL_DIR not set."
  .exit 100
.end if
.include "${chain_util}/arc/parse_chain.inc"
.//
.assign arc_dir = "../com.mentor.nucleus.bp.ui.tree/arc"
.include "${arc_dir}/create_generic_adapters.inc"
.include "${arc_dir}/create_generic_label_provider.inc"
.include "${arc_dir}/create_generic_content_provider.inc"
.include "${arc_dir}/create_generic_filters_content_provider.inc"
.include "arc/create_**VIEW_FILENAME**.inc"
.//
.invoke result = create_**VIEW_FILENAME**("${path}**/VIEWS**",plugin_name)
${result.body}
.emit to file "${rel_path}**/VIEWS**/${result.result}.java"
.select many tree_nodes from instances of T_TNS
.for each node in tree_nodes
  .assign node_name = "$Cr{node.CategoryName}"
  .invoke result = create_generic_adapters(node,node_name,path,package,plugin_package,plugin_name)
${result.body}
  .emit to file "${rel_path}**/ADAPTERS**/${result.result}.java"
.end for
.invoke result = create_generic_label_provider(path,label_provider_prefix,package)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.invoke result = create_generic_content_provider(path,prefix,package)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.invoke result = create_generic_filters_content_provider(path,prefix,package,plugin_name)
${result.body}
.emit to file "${rel_path}/${result.result}.java"
.//