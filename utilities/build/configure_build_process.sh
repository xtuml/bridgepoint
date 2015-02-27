#!/bin/bash
#
#	configure_build_process.sh requires the following environment variables
#
#   ${BRANCH} - product version, actually this is any branch/tag found in git
#   ${GIT_REPO_ROOT} - git repository root
#   ${BUILD_TYPE} - build type (release/nonrelease)
#
#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------
function configure_build_files {
	echo -e "Entering configure_build_process.sh::configure_build_files"
	
    cd ${GIT_BP}/${utilities_project}/build
    cp -f configure_external_dependencies.sh ${build_dir}/configure_external_dependencies.sh 2>>${error_file}
    cp -f create_bp_release.sh ${build_dir}/create_bp_release.sh 2>>${error_file}
    cp -f create_release_functions.sh ${build_dir}/create_release_functions.sh 2>>${error_file}
    cp -f process_build.sh ${build_dir}/process_build.sh 2>>${error_file}

	#
	# files needed to build the tool  
	#	
  	cd ${git_workspace_setup}/BridgePointDev-Linux
	cp -f * ${PT_HOME}

   chmod a+x ./mc3020/bin/xtumlmc_build.exe
   chmod a+x ./bin/xtumlmc_gen_erate
   
    #
    # required things from dropins
    #
    cd ${git_workspace_setup}/dropins
    cp -f * ${ECLIPSE_HOME}/dropins
    
    cd ${build_dir}
    dos2unix -q configure_external_dependencies.sh
    dos2unix -q create_bp_release.sh
    dos2unix -q create_release_functions.sh
    dos2unix -q process_build.sh
	echo -e "Exiting configure_build_process.sh::configure_build_files"
}

function configure_installer_files {
	echo -e "Entering configure_build_process.sh::configure_installer_files"
    cd ${GIT_BP}/src/${install_project}
    mkdir -p "${bp_deliverables}/extras"
    mkdir -p "${bp_deliverables}/tools"
    mkdir -p "${bp_deliverables}/EclipseDeliverables"
    cp -f Launcher.bat ${bp_deliverables}/extras/Launcher.bat 2>>${error_file}
    cp -f CLI.bat ${bp_deliverables}/extras/CLI.bat 2>>${error_file}
    cp -f build_installer_bp.sh ${build_dir}/build_installer_bp.sh 2>>${error_file}
    cp -f create_shortcut.vbs ${bp_deliverables}/tools/create_shortcut.vbs 2>>${error_file}
    cp -f post_install_script.bat ${extra_deliverables}/post_install_script.bat 2>>${error_file}
    cp -f pre_uninstall_script.bat ${extra_deliverables}/pre_uninstall_script.bat 2>>${error_file}
    cp -f splash.bmp ${bp_deliverables}/splash.bmp 2>>${error_file}
    cp -f bp.ico ${bp_deliverables}/bp.ico 2>>${error_file}

    unix2dos -q ${bp_deliverables}/extras/Launcher.bat
    unix2dos -q ${bp_deliverables}/extras/CLI.bat
    unix2dos -q ${bp_deliverables}/tools/create_shortcut.vbs
    unix2dos -q ${extra_deliverables}/post_install_script.bat
    unix2dos -q ${extra_deliverables}/pre_uninstall_script.bat
    
    mkdir -p "${bp_deliverables_linux}/extras"
    cp -f Launcher.sh ${bp_deliverables_linux}/extras/Launcher.sh 2>>${error_file}
    cp -f CLI.sh ${bp_deliverables_linux}/extras/CLI.sh 2>>${error_file}
    cp -f build_installer_bp_linux.sh ${build_dir}/build_installer_bp_linux.sh 2>>${error_file}
    cp -f post_install_script.sh ${extra_deliverables_linux}/post_install_script.sh 2>>${error_file}
    cp -f pre_uninstall_script.sh ${extra_deliverables_linux}/pre_uninstall_script.sh 2>>${error_file}    
    cp -f splash.bmp ${bp_deliverables_linux}/splash.bmp 2>>${error_file}
    cp -f bp.ico ${bp_deliverables_linux}/bp.ico 2>>${error_file}

    dos2unix -q ${bp_deliverables_linux}/extras/Launcher.sh
    dos2unix -q ${bp_deliverables_linux}/extras/CLI.sh
    dos2unix -q ${extra_deliverables_linux}/post_install_script.sh
    dos2unix -q ${extra_deliverables_linux}/pre_uninstall_script.sh    

    dos2unix -q ${build_dir}/build_installer_bp_linux.sh
    dos2unix -q ${build_dir}/build_installer_bp.sh

    # copy the entire packing repository to the staging area for the installer to work with
    cd ${git_repo_root}
    cp -rf packaging ${staging_area} 2>>${error_file}

    cd ${GIT_BP}/${utilities_project}/fontchecker/Release
    mkdir -p "${bp_deliverables}/tools/fontchecker"
    cp -f font_list.txt ${bp_deliverables}/tools/fontchecker/font_list.txt 2>>${error_file}
    cp -f fontchecker.exe ${bp_deliverables}/tools/fontchecker/fontchecker.exe 2>>${error_file}

    unix2dos -q ${bp_deliverables}/tools/fontchecker/font_list.txt
	echo -e "Exiting configure_build_process.sh::configure_build_files"
}

#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------
date

branch=${BRANCH}
git_repo_root=${GIT_REPO_ROOT}
build_type=${BUILD_TYPE}
build_dir="${BUILD_ROOT}/${branch}"
log_dir="${build_dir}/log"
error_file="${log_dir}/errors.log"

git_workspace_setup="${GIT_BP}/doc-bridgepoint/process/development-workspace-setup"
install_project="Installer"
utilities_project="utilities"
staging_area="${BUILD_MOUNT}/staging"
eclipse_ver="3.7"

echo -e "Entering configure_build_process.sh   build_dir=${build_dir} branch=${branch} git_repo_root=${git_repo_root} build_type=${build_type}"
#
# The follow folders are used to stage the files required by the installer
#
bp_deliverables="${staging_area}/BridgePoint_e${eclipse_ver}/BridgePointDeliverables"
extra_deliverables="${staging_area}/BridgePoint_e${eclipse_ver}/vcredist_x86"
bp_deliverables_linux="${staging_area}/BridgePoint_for_Linux_e${eclipse_ver}/BridgePointDeliverables"
extra_deliverables_linux="${staging_area}/BridgePoint_for_Linux_e${eclipse_ver}/install_tools"
installer_files="${staging_area}/installer/BridgePoint_e${eclipse_ver}/src"
installer_files_linux="${staging_area}/installer/BridgePoint_Linux_e${eclipse_ver}/src"
mkdir -p "${bp_deliverables}"
mkdir -p "${extra_deliverables}"
mkdir -p "${installer_files}"
mkdir -p "${bp_deliverables_linux}"
mkdir -p "${extra_deliverables_linux}"
mkdir -p "${installer_files_linux}"


if [ "${branch}" = "master" ] || [ "${build_type}" = "nonrelease" ]; then
    echo -e "Removing old build directory: ${build_dir}"

    rm -rf ${build_dir}
fi

#
# if the given build_dir exists then move the existing dir to 
# a dir with the same name and the current timestamp to assure this
# is a clean build
#
if [ ! -x ${build_dir} ]; then
    echo -e "Creating build directory: ${build_dir}"
    cd ${BUILD_ROOT}; mkdir ${branch}
else
    mv ${build_dir} ${build_dir}_${TIMESTAMP}
    echo -e "Release build directory ${build_dir} already exists.  Moving to ${build_dir}_${TIMESTAMP}"
    cd ${BUILD_ROOT}; mkdir ${branch}
fi

if [ ! -x $log_dir ]; then
    echo -e "Creating log directory: $log_dir"
    mkdir $log_dir
fi

cd ${build_dir}
configure_build_files
configure_installer_files

cd ${BUILD_ROOT}

echo -e "Exiting configure_build_process.sh"

exit 0


