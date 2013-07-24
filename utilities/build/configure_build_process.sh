#!/bin/bash
#=====================================================================
#
# File:      configure_build_process.sh
#
#(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================
#
#	configure_build_process.sh takes the following arguments
#
#   $1 - product version, actually this is any branch/tag found in git
#   $2 - git repository root
#   $3 - build type (release/nonrelease)
#
#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------
function usage {
  echo -e "\nThis script setups of the build by copying files out of the repository"
  echo -e "into the proper locations.\n"
  echo -e "\nUsage: $0 <branch/tag> <git repo root> <release/nonrelease>\n"
  echo -e "branch/tag  = the release version to be created"
  echo -e "git repo root = the parent directory of the git repositories"
  echo -e "build type = either release or nonrelease"
  echo -e ""
  echo -e "e.g: $0 master /git/xtuml nonrelease"
  echo -e "     $0 R4.0.0 /git/xtuml release"
  exit 1
}

function configure_build_files {
    cd ${git_internal}/${utilities_project}/build
    cp -f configure_external_dependencies.sh ${build_dir}/configure_external_dependencies.sh 2>>${error_file}
    cp -f create_bp_release.sh ${build_dir}/create_bp_release.sh 2>>${error_file}
    cp -f create_release_functions.sh ${build_dir}/create_release_functions.sh 2>>${error_file}
    cp -f process_build.sh ${build_dir}/process_build.sh 2>>${error_file}
    # TODO: cp -f tag_bp.sh ${build_dir}/tag_bp.sh 2>>${error_file}
    # TODO: cp -f tag_bp_nb.sh ${build_dir}/tag_bp_nb.sh 2>>${error_file}
    
    cd ${build_dir}
    dos2unix -q configure_external_dependencies.sh
    dos2unix -q create_bp_release.sh
    dos2unix -q create_release_functions.sh
    dos2unix -q process_build.sh
}

function configure_installer_files {
    cd ${git_internal}/src/${install_project}
    cp -f Launcher.bat ${bp_deliverables}/extras/Launcher.bat 2>>${error_file}
    cp -f CLI.bat ${bp_deliverables}/extras/CLI.bat 2>>${error_file}
    cp -f build_installer_bp.sh ${build_dir}/build_installer_bp.sh 2>>${error_file}
    cp -f create_shortcut.vbs ${bp_deliverables}/tools/create_shortcut.vbs 2>>${error_file}
    cp -f MSI_Director.java ${mimic_files}/MSI_Director.java 2>>${error_file}
    cp -f post_install_script.bat ${extra_deliverables}/post_install_script.bat 2>>${error_file}
    cp -f pre_uninstall_script.bat ${extra_deliverables}/pre_uninstall_script.bat 2>>${error_file}
    cp -f splash.bmp ${bp_deliverables}/splash.bmp 2>>${error_file}
    cp -f bp.ico ${bp_deliverables}/bp.ico 2>>${error_file}

    unix2dos -q ${bp_deliverables}/extras/Launcher.bat
    unix2dos -q ${bp_deliverables}/extras/CLI.bat
    unix2dos -q ${bp_deliverables}/tools/create_shortcut.vbs
    unix2dos -q ${mimic_files}/MSI_Director.java
    unix2dos -q ${extra_deliverables}/post_install_script.bat
    unix2dos -q ${extra_deliverables}/pre_uninstall_script.bat
    
    cp -f Launcher.sh ${bp_deliverables_linux}/extras/Launcher.sh 2>>${error_file}
    cp -f CLI.sh ${bp_deliverables_linux}/extras/CLI.sh 2>>${error_file}
    cp -f build_installer_bp_linux.sh ${build_dir}/build_installer_bp_linux.sh 2>>${error_file}
    cp -f MSI_DirectorLinux.java ${mimic_files_linux}/MSI_Director.java 2>>${error_file}
    cp -f post_install_script.sh ${extra_deliverables_linux}/post_install_script.sh 2>>${error_file}
    cp -f pre_uninstall_script.sh ${extra_deliverables_linux}/pre_uninstall_script.sh 2>>${error_file}    
    cp -f splash.bmp ${bp_deliverables_linux}/splash.bmp 2>>${error_file}
    cp -f bp.ico ${bp_deliverables_linux}/bp.ico 2>>${error_file}

    dos2unix -q ${bp_deliverables_linux}/extras/Launcher.sh
    dos2unix -q ${bp_deliverables_linux}/extras/CLI.sh
    dos2unix -q ${mimic_files_linux}/MSI_Director.java
    dos2unix -q ${extra_deliverables_linux}/post_install_script.sh
    dos2unix -q ${extra_deliverables_linux}/pre_uninstall_script.sh    

    dos2unix -q ${build_dir}/build_installer_bp_linux.sh
    dos2unix -q ${build_dir}/build_installer_bp.sh

    cd ${git_internal}/${utilities_project}/fontchecker/Release
    cp -f font_list.txt ${bp_deliverables}/tools/fontchecker/font_list.txt 2>>${error_file}
    cp -f fontchecker.exe ${bp_deliverables}/tools/fontchecker/fontchecker.exe 2>>${error_file}

    unix2dos -q ${bp_deliverables}/tools/fontchecker/font_list.txt
}

#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------
date

if [ $# -ne 3 ]; then
    usage
fi

branch="$1"
git_repo_root="$2"
build_type="$3"
base_dir=`pwd`
build_dir="${base_dir}/${branch}"
log_dir="${build_dir}/log"
error_file="${log_dir}/.errors"
timestamp=`date +%Y%m%d%H%M`

git_internal="${git_repo_root}/internal"
install_project="Installer_MIP_MIMIC"
utilities_project="utilities"
eclipse_ver="3.7"
bp_deliverables="C:/BridgePoint_e${eclipse_ver}/BridgePointDeliverables"
extra_deliverables="C:/BridgePoint_e${eclipse_ver}/vcredist_x86"
mimic_files="C:/MIMIC/rlstools/TK_Director/BridgePoint_e${eclipse_ver}/src"
bp_deliverables_linux="C:/BridgePoint_for_Linux_e${eclipse_ver}/BridgePointDeliverables"
extra_deliverables_linux="C:/BridgePoint_for_Linux_e${eclipse_ver}/install_tools"
mimic_files_linux="C:/MIMIC/rlstools/TK_Director/BridgePoint_Linux_e${eclipse_ver}/src"


echo "Configuring the build process in ${build_dir}"

if [ "${branch}" = "master" ] || [ "${build_type}" = "nonrelease" ]; then
    echo -e "Removing old build directory: ${build_dir}"

    rm -rf ${build_dir}
fi

if [ ! -x ${build_dir} ]; then
    echo -e "Creating build directory: ${build_dir}"
    cd ${base_dir}; mkdir ${branch}
else
    mv ${build_dir} ${build_dir}_${timestamp}
    echo -e "Release build directory ${build_dir} already exists.  Moving to ${build_dir}_${timestamp}"
    cd ${base_dir}; mkdir ${branch}
fi

if [ ! -x $log_dir ]; then
    echo -e "Creating log directory: $log_dir"
    mkdir $log_dir
fi

cd ${build_dir}
configure_build_files
configure_installer_files

echo -e "\nBuild and installer files configured."

cd ${base_dir}
exit 0


