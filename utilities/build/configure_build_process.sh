#!/bin/bash
#
#	configure_build_process.sh requires the following arguments
#
#   build_dir - path to folder where the build is taking place
#   git_repo_root - path to local root of all git repositories
#
#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------
function configure_build_files {
	echo -e "Entering configure_build_process.sh::configure_build_files"
	
    cd ${git_bp}/${utilities_project}/build
    # Copy files and do the dos2unix translation.
    tr -d '\r' < configure_external_dependencies.sh > ${build_dir}/configure_external_dependencies.sh 2>>${ERROR_FILE}
    tr -d '\r' < create_bp_release.sh > ${build_dir}/create_bp_release.sh 2>>${ERROR_FILE}
	tr -d '\r' < plugin_customization.ini > ${build_dir}/plugin_customization.ini 2>>${ERROR_FILE}
    chmod a+x ${build_dir}/configure_external_dependencies.sh 2>>${ERROR_FILE}
    chmod a+x ${build_dir}/create_bp_release.sh 2>>${ERROR_FILE}
	chmod a+x ${build_dir}/plugin_customization.ini 2>>${ERROR_FILE}
	chmod -R g+w ${build_dir}
	
	echo -e "Exiting configure_build_process.sh::configure_build_files"
}

function configure_installer_files {
	echo -e "Entering configure_build_process.sh::configure_installer_files"
	
	cd ${STAGING_AREA}
	rm -rf installer_extras 2>>${ERROR_FILE}
	mkdir -p installer_extras
    rm -rf BridgePoint_e${eclipse_ver} 2>>${ERROR_FILE}
    rm -rf BridgePoint_for_Linux_e${eclipse_ver} 2>>${ERROR_FILE}
    
    cd ${git_repo_root}/packaging/install_bases
    cp -rf BridgePoint_e${eclipse_ver} ${STAGING_AREA} 2>>${ERROR_FILE}
    cp -rf BridgePoint_for_Linux_e${eclipse_ver} ${STAGING_AREA} 2>>${ERROR_FILE}
	
    cd ${git_bp}/src/${install_project}
    
    # First set up the extra files used by the installer
    cp -f splash.bmp ${installer_extras} 2>>${ERROR_FILE}
    cp -f bp.ico ${installer_extras} 2>>${ERROR_FILE}
    cp -f 1F.png ${installer_extras} 2>>${ERROR_FILE}
    cp -f TARGET_WINDOWS.txt ${installer_extras} 2>>${ERROR_FILE}
    cp -f TARGET_LINUX.txt ${installer_extras} 2>>${ERROR_FILE}
    cp -f install_linux.xml ${installer_extras} 2>>${ERROR_FILE}
    cp -f install_windows.xml ${installer_extras} 2>>${ERROR_FILE}
    cp -f welcome.html ${installer_extras} 2>>${ERROR_FILE}
    cp -f done.html ${installer_extras} 2>>${ERROR_FILE}
    cp -f shortcutSpec.xml ${installer_extras} 2>>${ERROR_FILE}
    cp -f post_install_script.bat ${installer_extras} 2>>${ERROR_FILE}
    tr -d '\r' < post_install_script.sh > ${installer_extras}/post_install_script.sh 2>>${ERROR_FILE}

    tr -d '\r' < build_installer_bp.sh > ${build_dir}/build_installer_bp.sh 2>>${ERROR_FILE}
    chmod a+x ${build_dir}/build_installer_bp.sh

    # Next set up the windows files
    cp -f Launcher.bat ${eclipse_deliverables}/eclipse 2>>${ERROR_FILE}
    cp -f CLI.bat ${eclipse_deliverables}/eclipse 2>>${ERROR_FILE}
    mkdir -p ${bp_deliverables}/tools
    cp -f create_shortcut.vbs ${bp_deliverables}/tools 2>>${ERROR_FILE}

    # Next set up the linux files
    # Copy files and do the dos2unix translation.
    tr -d '\r' < Launcher.sh > ${eclipse_deliverables_linux}/eclipse/Launcher.sh 2>>${ERROR_FILE}
    tr -d '\r' < CLI.sh > ${eclipse_deliverables_linux}/eclipse/CLI.sh 2>>${ERROR_FILE}

	# Add in the Windows fontchecker
    cd ${git_bp}/${utilities_project}/fontchecker/Release
    mkdir -p ${bp_deliverables}/tools/fontchecker
    cp -f font_list.txt ${bp_deliverables}/tools/fontchecker/font_list.txt 2>>${ERROR_FILE}
    cp -f fontchecker.exe ${bp_deliverables}/tools/fontchecker/fontchecker.exe 2>>${ERROR_FILE}
    
    chmod -R g+w ${STAGING_AREA}
    
	echo -e "Exiting configure_build_process.sh::configure_installer_files"
}

#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------
date

# Verify parameters
if [ "$#" -lt 2 ]; then
  echo "This script requires two parameters.  See below for usage."
  echo
  echo "configure_build_process.sh Build_Dir Git_repo_root"
  echo
  echo "See the script header for more detail."
  exit 1
fi 

build_dir="$1"
git_repo_root="$2"

git_bp="${git_repo_root}/bridgepoint"
git_workspace_setup="${git_bp}/doc-bridgepoint/process/development-workspace-setup"
install_project="installer"
utilities_project="utilities"
mc_project="org.xtuml.bp.mc.c.binary"
mcjava_project="org.xtuml.bp.mc.java.source"
eclipse_ver="3.7"

echo -e "Entering configure_build_process.sh   build_dir=${build_dir} git_repo_root=${git_repo_root}"
#
# The following folders are used to stage the files required by the installer
#
bp_deliverables="${STAGING_AREA}/BridgePoint_e${eclipse_ver}/BridgePointDeliverables"
mkdir -p ${bp_deliverables}
bp_deliverables_linux="${STAGING_AREA}/BridgePoint_for_Linux_e${eclipse_ver}/BridgePointDeliverables"
mkdir -p ${bp_deliverables_linux}
installer_extras="${STAGING_AREA}/installer_extras"
mkdir -p ${installer_extras}

eclipse_deliverables="${STAGING_AREA}/BridgePoint_e${eclipse_ver}/EclipseDeliverables"
eclipse_deliverables_linux="${STAGING_AREA}/BridgePoint_for_Linux_e${eclipse_ver}/EclipseDeliverables"

cd ${build_dir}
configure_build_files
configure_installer_files

cd ${build_dir}

echo -e "Exiting configure_build_process.sh"

exit 0


