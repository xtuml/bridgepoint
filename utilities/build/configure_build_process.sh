#!/bin/bash
#
#	configure_build_process.sh requires the following environment variables
#
#   ${BRANCH} - product version, actually this is any branch/tag found in git
#   ${GIT_BP} - bridgepoint git repository root
#   ${GIT_REPO_ROOT} - git repository root
#   ${BUILD_TYPE} - build type (release/nonrelease)
#
#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------
function configure_build_files {
	echo -e "Entering configure_build_process.sh::configure_build_files"
	
    cd ${GIT_BP}/${utilities_project}/build
    # Copy files and do the dos2unix translation.
    tr -d '\r' < configure_external_dependencies.sh > ${BUILD_DIR}/configure_external_dependencies.sh 2>>${ERROR_FILE}
    tr -d '\r' < create_bp_release.sh > ${BUILD_DIR}/create_bp_release.sh 2>>${ERROR_FILE}
    tr -d '\r' < create_release_functions.sh > ${BUILD_DIR}/create_release_functions.sh 2>>${ERROR_FILE}
	tr -d '\r' < plugin_customization.ini > ${BUILD_DIR}/plugin_customization.ini 2>>${ERROR_FILE}
    chmod a+x ${BUILD_DIR}/configure_external_dependencies.sh 2>>${ERROR_FILE}
    chmod a+x ${BUILD_DIR}/create_bp_release.sh 2>>${ERROR_FILE}
    chmod a+x ${BUILD_DIR}/create_release_functions.sh 2>>${ERROR_FILE}
	chmod a+x ${BUILD_DIR}/plugin_customization.ini 2>>${ERROR_FILE}
	
	#
	# Files needed to build the tool.  Populate the git repo mc.c.binary plug-in
	# with the model compiler files from the BP we're using to build.  
	#	
  	cd ${ECLIPSE_HOME}/plugins/${orig_mc_project}_${HOST_BP_VERSION}
	cp -fr mc3020/* ${GIT_BP}/src/${mc_project}/mc3020
	cp -fr mc3020/* ${GIT_BP}/src/${mcjava_project}/mc3020

	echo -e "Exiting configure_build_process.sh::configure_build_files"
}

function configure_installer_files {
	echo -e "Entering configure_build_process.sh::configure_installer_files"
	
	cd ${STAGING_AREA}
	rm -rf installer_extras 2>>${ERROR_FILE}
	mkdir -p installer_extras
    rm -rf BridgePoint_e${eclipse_ver} 2>>${ERROR_FILE}
	mkdir -p BridgePoint_e${eclipse_ver}
    rm -rf BridgePoint_Linux_e${eclipse_ver} 2>>${ERROR_FILE}
	mkdir -p BridgePoint_Linux_e${eclipse_ver}
    
    cd ${GIT_REPO_ROOT}/packaging/install_bases
    cp -rf BridgePoint_e${eclipse_ver} ${STAGING_AREA} 2>>${ERROR_FILE}
    cp -rf BridgePoint_for_Linux_e${eclipse_ver} ${STAGING_AREA} 2>>${ERROR_FILE}
	
    cd ${GIT_BP}/src/${install_project}
    
    # First set up the extra files used by the installer
    cp -f splash.bmp ${installer_extras} 2>>${ERROR_FILE}
    cp -f bp.ico ${installer_extras} 2>>${ERROR_FILE}
    cp -f 1F.png ${installer_extras} 2>>${ERROR_FILE}
    cp -f TARGET_WINDOWS.txt ${installer_extras} 2>>${ERROR_FILE}
    cp -f TARGET_LINUX.txt ${installer_extras} 2>>${ERROR_FILE}
    cp -f welcome.html ${installer_extras} 2>>${ERROR_FILE}
    cp -f shortcutSpec.xml ${installer_extras} 2>>${ERROR_FILE}
    cp -f post_install_script.bat ${installer_extras} 2>>${ERROR_FILE}
    tr -d '\r' < post_install_script.sh > ${installer_extras}/post_install_script.sh 2>>${ERROR_FILE}

    # Next set up the windows files
    tr -d '\r' < build_installer_bp.sh > ${BUILD_DIR}/build_installer_bp.sh 2>>${ERROR_FILE}
    mkdir -p ${bp_deliverables}/extras
    cp -f Launcher.bat ${bp_deliverables}/extras 2>>${ERROR_FILE}
    cp -f CLI.bat ${bp_deliverables}/extras 2>>${ERROR_FILE}
    mkdir -p ${bp_deliverables}/tools
    cp -f create_shortcut.vbs ${bp_deliverables}/tools 2>>${ERROR_FILE}

    # Next set up the linux files
    # Copy files and do the dos2unix translation.
    tr -d '\r' < build_installer_bp_linux.sh > ${BUILD_DIR}/build_installer_bp_linux.sh 2>>${ERROR_FILE}
    mkdir -p ${bp_deliverables_linux}/extras
    tr -d '\r' < Launcher.sh > ${bp_deliverables_linux}/extras/Launcher.sh 2>>${ERROR_FILE}
    tr -d '\r' < CLI.sh > ${bp_deliverables_linux}/extras/CLI.sh 2>>${ERROR_FILE}

	# Add in the Windows fontchecker
    cd ${GIT_BP}/${utilities_project}/fontchecker/Release
    mkdir -p ${bp_deliverables_linux}/tools/fontchecker
    cp -f font_list.txt ${bp_deliverables}/tools/fontchecker/font_list.txt 2>>${ERROR_FILE}
    cp -f fontchecker.exe ${bp_deliverables}/tools/fontchecker/fontchecker.exe 2>>${ERROR_FILE}
    # TODO SKB (3/24/15) - I think this unix2dos is here simply as a safety measure, but no longer needed
    #unix2dos ${bp_deliverables}/tools/fontchecker/font_list.txt
    
	echo -e "Exiting configure_build_process.sh::configure_installer_files"
}

#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------
date

git_workspace_setup="${GIT_BP}/doc-bridgepoint/process/development-workspace-setup"
install_project="installer"
utilities_project="utilities"
# TODO - once the base BridgePoint is updated to the new plugin names, then orig_mc_project
#   should go away and we'll only use mc_project.
orig_mc_project="org.xtuml.bp.mc.c.binary"
mc_project="org.xtuml.bp.mc.c.binary"
mcjava_project="org.xtuml.bp.mc.java.source"
eclipse_ver="3.7"

echo -e "Entering configure_build_process.sh   BUILD_DIR=${BUILD_DIR} BRANCH=${BRANCH} GIT_REPO_ROOT=${GIT_REPO_ROOT} BUILD_TYPE=${BUILD_TYPE}"
#
# The following folders are used to stage the files required by the installer
#
bp_deliverables="${STAGING_AREA}/BridgePoint_e${eclipse_ver}/BridgePointDeliverables"
mkdir -p ${bp_deliverables}
extra_deliverables="${STAGING_AREA}/BridgePoint_e${eclipse_ver}/vcredist_x86"
mkdir -p ${extra_deliverables}
bp_deliverables_linux="${STAGING_AREA}/BridgePoint_for_Linux_e${eclipse_ver}/BridgePointDeliverables"
mkdir -p ${bp_deliverables_linux}
extra_deliverables_linux="${STAGING_AREA}/BridgePoint_for_Linux_e${eclipse_ver}/install_tools"
mkdir -p ${extra_deliverables_linux}
installer_extras="${STAGING_AREA}/installer_extras"
mkdir -p ${installer_extras}
installer_files="${STAGING_AREA}/installer/BridgePoint_e${eclipse_ver}/src"
mkdir -p ${installer_files}
installer_files_linux="${STAGING_AREA}/installer/BridgePoint_Linux_e${eclipse_ver}/src"
mkdir -p ${installer_files_linux}

cd ${BUILD_DIR}
configure_build_files
configure_installer_files

cd ${BUILD_DIR}

echo -e "Exiting configure_build_process.sh"

exit 0


