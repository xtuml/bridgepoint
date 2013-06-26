#!/bin/bash
#=====================================================================
#
# File:      $RCSfile: configure_build_process.sh,v $
# Version:   $Revision: 1.17 $
# Modified:  $Date: 2013/06/12 13:08:24 $
#
#(c) Copyright 2004-2012 by Mentor Graphics Corp. All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================
#

#
#
#	configure_build_process.sh takes the following arguments
#
#	$1 - product version, actually this is any tag found in cvs
#
date
function usage {
	echo -e "\nUsage: $0 <tag>\n"
	echo -e "\ttag  = the release version to be created"
	exit 1
}

if [ $# -ne 1 ]; then
	usage
fi

# Allows CVS checkout to fall back to HEAD version if the specified tag version is not found
export_flags=" -f "

product_version="$1"
base_dir=`pwd`
build_base="${base_dir}/build_tools"
build_dir="$build_base/$product_version"
log_dir="$build_dir/log"
timestamp=`date +%Y%m%d%H%M`
release_tag="$1"
error_file="${build_base}/.errors"
diff_file="${build_base}/.diff_log"
cvsroot="/arch1/products/tiger/repository"
remote_build_base="/arch1/products/tiger/nightly_build"
server="tucson.wv.mentorg.com"
rsh="ssh"

build_project="com.mentor.nucleus.bp.core"
install_project="Installer_MIP_MIMIC"
utilities_project="utilities"
build_install_modules="${build_project} ${install_project} ${utilities_project}"
eclipse_ver="3.7"
bp_deliverables="C:/BridgePoint_e${eclipse_ver}/BridgePointDeliverables"
extra_deliverables="C:/BridgePoint_e${eclipse_ver}/vcredist_x86"
mimic_files="C:/MIMIC/rlstools/TK_Director/BridgePoint_e${eclipse_ver}/src"
bp_deliverables_linux="C:/BridgePoint_for_Linux_e${eclipse_ver}/BridgePointDeliverables"
extra_deliverables_linux="C:/BridgePoint_for_Linux_e${eclipse_ver}/install_tools"
mimic_files_linux="C:/MIMIC/rlstools/TK_Director/BridgePoint_Linux_e${eclipse_ver}/src"

rm -f $error_file
rm -f $diff_file

if [ ! -x $build_base ]; then
	echo -e "Creating base build directory: $build_base"
	mkdir $build_base
fi

echo -e "Removing old build directory: $build_dir"
rm -rf $build_dir 2>>${error_file}
if [ "$?" != "0" ]; then
    cat ${error_file}
fi

if [ ! -x $build_dir ]; then
    echo -e "Creating build directory: $build_dir"
    cd $build_base; mkdir $product_version
fi

if [ ! -x $log_dir ]; then
    echo -e "Creating log directory: $log_dir"
    mkdir $log_dir
fi


echo "Configuring the build process in ${build_dir}"

# Get back to the base directory
cd ${base_dir}

# Fix permissions for remote server
chmod -R ug+w $build_dir

remote_build_dir="${remote_build_base}/build_tools/${product_version}"


#######################################################################
# Functions
#######################################################################
function get_build_and_installer_files {
    echo "remote build dir: ${remote_build_dir}"
    echo "cvsroot: ${cvsroot}"
    echo "product version: ${product_version}"
    echo "export flags: ${export_flags}"

	for module in ${build_install_modules}; do
		echo "Checking out ${module} for release: ${product_version}"
        # NOTE: Comment out the follow to skip CVS checkout of the project
		${rsh} ${server} "(cd '${remote_build_dir}'; cvs -d'${cvsroot}' export ${export_flags} -r '${product_version}' '${module}')" > ${log_dir}/cvs_export_${module}.log 2>&1

		chown -R ${USERNAME} ${module}
	done
}

function configure_build_files {
    cd ${build_dir}/${build_project}/tools
    unix2dos create_nightly_build.sh
    unix2dos create_release_functions.sh
    unix2dos create_tiger_release.sh
    unix2dos *.xml
    cp -f configure_external_dependencies.sh ${base_dir}/configure_external_dependencies.sh 2>>${error_file}
    cp -f create_nightly_build.sh ${base_dir}/create_nightly_build.sh 2>>${error_file}
    cp -f create_release_functions.sh ${base_dir}/create_release_functions.sh 2>>${error_file}
    cp -f create_tiger_release.sh ${base_dir}/create_tiger_release.sh 2>>${error_file}
    cp -f obfuscate_template.xml ${base_dir}/obfuscate_template.xml 2>>${error_file}
    cp -f tag_bp.sh ${base_dir}/tag_bp.sh 2>>${error_file}
    cp -f tag_bp_nb.sh ${base_dir}/tag_bp_nb.sh 2>>${error_file}
}

function configure_installer_files {
    cd ${build_dir}/${install_project}
    unix2dos *
    cp -f Launcher.bat ${bp_deliverables}/extras/Launcher.bat 2>>${error_file}
    cp -f CLI.bat ${bp_deliverables}/extras/CLI.bat 2>>${error_file}
    cp -f build_installer_bp.sh ${base_dir}/build_installer_bp.sh 2>>${error_file}
    cp -f create_shortcut.vbs ${bp_deliverables}/tools/create_shortcut.vbs 2>>${error_file}
    cp -f MSI_Director.java ${mimic_files}/MSI_Director.java 2>>${error_file}
    cp -f post_install_script.bat ${extra_deliverables}/post_install_script.bat 2>>${error_file}
    cp -f pre_uninstall_script.bat ${extra_deliverables}/pre_uninstall_script.bat 2>>${error_file}
    cp -f splash.bmp ${bp_deliverables}/splash.bmp 2>>${error_file}
    cp -f bp.ico ${bp_deliverables}/bp.ico 2>>${error_file}
    
    dos2unix *
    cp -f Launcher.sh ${bp_deliverables_linux}/extras/Launcher.sh 2>>${error_file}
    cp -f CLI.sh ${bp_deliverables_linux}/extras/CLI.sh 2>>${error_file}
    cp -f build_installer_bp_linux.sh ${base_dir}/build_installer_bp_linux.sh 2>>${error_file}
    cp -f MSI_DirectorLinux.java ${mimic_files_linux}/MSI_Director.java 2>>${error_file}
    cp -f post_install_script.sh ${extra_deliverables_linux}/post_install_script.sh 2>>${error_file}
    cp -f pre_uninstall_script.sh ${extra_deliverables_linux}/pre_uninstall_script.sh 2>>${error_file}    
    cp -f splash.bmp ${bp_deliverables_linux}/splash.bmp 2>>${error_file}
    cp -f bp.ico ${bp_deliverables_linux}/bp.ico 2>>${error_file}

    cd ${build_dir}/${utilities_project}/fontchecker/Release
    unix2dos font_list.txt
    cp -f font_list.txt ${bp_deliverables}/tools/fontchecker/font_list.txt 2>>${error_file}
    cp -f fontchecker.exe ${bp_deliverables}/tools/fontchecker/fontchecker.exe 2>>${error_file}
}


#######################################################################
# MAIN
#######################################################################
cd $build_dir
get_build_and_installer_files
configure_build_files
configure_installer_files
echo -e "\nBuild and installer files configured."

cd ${base_dir}
date
exit 0


