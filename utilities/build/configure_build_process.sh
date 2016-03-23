#!/bin/bash
#
#	configure_build_process.sh requires arguments.  See the usage screen for 
#     more info.
#
#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------
function configure_build_files {
	echo -e "Entering configure_build_process.sh::configure_build_files"
	
    cd ${git_bp}/${utilities_project}/build
    # Copy files and do the dos2unix translation.
    tr -d '\r' < configure_external_dependencies.sh > ${build_dir}/configure_external_dependencies.sh 2>>${error_file}
    tr -d '\r' < create_bp_release.sh > ${build_dir}/create_bp_release.sh 2>>${error_file}
	tr -d '\r' < plugin_customization.ini > ${build_dir}/plugin_customization.ini 2>>${error_file}
    chmod a+x ${build_dir}/configure_external_dependencies.sh 2>>${error_file}
    chmod a+x ${build_dir}/create_bp_release.sh 2>>${error_file}
	chmod a+x ${build_dir}/plugin_customization.ini 2>>${error_file}
	chmod -R g+w ${build_dir}
	
	echo -e "Exiting configure_build_process.sh::configure_build_files"
}

function configure_installer_files {
	echo -e "Entering configure_build_process.sh::configure_installer_files"
	
	cd ${staging_area}
	rm -rf installer_extras 2>>${error_file}
	mkdir -p installer_extras
    rm -rf BridgePoint_e${eclipse_ver} 2>>${error_file}
    rm -rf BridgePoint_for_Linux_e${eclipse_ver} 2>>${error_file}
    
    cd ${git_repo_root}/packaging/install_bases
    cp -rf BridgePoint_e${eclipse_ver} ${staging_area} 2>>${error_file}
    cp -rf BridgePoint_for_Linux_e${eclipse_ver} ${staging_area} 2>>${error_file}
	
    cd ${git_bp}/src/${install_project}
    cp -f bp.ico ${installer_extras} 2>>${error_file}

    tr -d '\r' < build_installer_bp.sh > ${build_dir}/build_installer_bp.sh 2>>${error_file}
    chmod a+x ${build_dir}/build_installer_bp.sh

    # Next set up the windows files
    cp -f Launcher.bat ${eclipse_deliverables}/eclipse 2>>${error_file}
    cp -f CLI.bat ${eclipse_deliverables}/eclipse 2>>${error_file}
    mkdir -p ${bp_deliverables}/tools
    cp -f create_shortcut.vbs ${bp_deliverables}/tools 2>>${error_file}

    # Next set up the linux files
    # Copy files and do the dos2unix translation.
    tr -d '\r' < Launcher.sh > ${eclipse_deliverables_linux}/eclipse/Launcher.sh 2>>${error_file}
    tr -d '\r' < CLI.sh > ${eclipse_deliverables_linux}/eclipse/CLI.sh 2>>${error_file}

	# Add in the Windows fontchecker
    cd ${git_bp}/${utilities_project}/fontchecker/Release
    mkdir -p ${bp_deliverables}/tools/fontchecker
    cp -f font_list.txt ${bp_deliverables}/tools/fontchecker/font_list.txt 2>>${error_file}
    cp -f fontchecker.exe ${bp_deliverables}/tools/fontchecker/fontchecker.exe 2>>${error_file}
    
	echo -e "Exiting configure_build_process.sh::configure_installer_files"
}

function configure_masl_files {
    echo -e "Entering configure_build_process.sh::configure_masl_files"
    
    mkdir -p ${bp_deliverables_linux}/tools/masl/lib
    
    cd ${git_mc}/bin
    
    # Copy files and do the dos2unix translation.
    tr -d '\r' < masl2xtuml > ${bp_deliverables_linux}/tools/masl/masl2xtuml 2>>${error_file}
    tr -d '\r' < xtuml2masl > ${bp_deliverables_linux}/tools/masl/xtuml2masl 2>>${error_file}
    chmod a+x ${bp_deliverables_linux}/tools/masl/masl2xtuml 2>>${error_file}
    chmod a+x ${bp_deliverables_linux}/tools/masl/xtuml2masl 2>>${error_file}
    
    cd ${bp_deliverables_linux}/tools/masl
    cp -fp ${user_supplied_files}/m2x  ./lib
    cp -fp ${user_supplied_files}/masl ./lib
    cp -fp ${user_supplied_files}/x2m  ./lib
    chmod a+x lib/masl 2>>${error_file}
    chmod a+x lib/m2x  2>>${error_file}
    chmod a+x lib/x2m  2>>${error_file}
    
    cp -fp ${git_mc}/masl/parser/lib/antlr-3.5.2-complete.jar ./lib
    
    # Build the MASL parser
    cd ${git_mc}/masl/parser
    ant dist
    
    cd ${bp_deliverables_linux}/tools/masl
    cp -fp ${git_mc}/masl/parser/dist/lib/MASLParser.jar ./lib
    
    chmod -R g+w ${bp_deliverables_linux}/tools/masl
    
    echo -e "Exiting configure_build_process.sh::configure_masl_files"
}


#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------
date

# Verify parameters
if [ "$#" -lt 4 ]; then
  echo "This script requires four parameters.  See below for usage."
  echo
  echo "configure_build_process.sh Build_Dir Git_repo_root Error_file Staging_area"
  echo ""
  echo "  Build_Dir - path to folder where the build is taking place"
  echo "  Git_repo_root - path to local root of all git repositories"
  echo "  Error_file - name of the file where errors are logged"
  echo "  Staging_area - path to the folder where we stage files to be packaged for installation"
  echo ""
  exit 1
fi 

build_dir="$1"
git_repo_root="$2"
error_file="$3"
staging_area="$4"

git_bp="${git_repo_root}/bridgepoint"
git_mc="${git_repo_root}/mc"
user_supplied_files=${git_repo_root}/packaging/build/extra_files
git_workspace_setup="${git_bp}/doc-bridgepoint/process/development-workspace-setup"
install_project="installer"
utilities_project="utilities"
mc_project="org.xtuml.bp.mc.c.binary"
mcjava_project="org.xtuml.bp.mc.java.source"
eclipse_ver="4.5"

echo -e "Entering configure_build_process.sh   build_dir=${build_dir} git_repo_root=${git_repo_root}"
#
# The following folders are used to stage the files required by the installer
#
bp_deliverables="${staging_area}/BridgePoint_e${eclipse_ver}/BridgePointDeliverables"
mkdir -p ${bp_deliverables}
bp_deliverables_linux="${staging_area}/BridgePoint_for_Linux_e${eclipse_ver}/BridgePointDeliverables"
mkdir -p ${bp_deliverables_linux}
installer_extras="${staging_area}/installer_extras"
mkdir -p ${installer_extras}

eclipse_deliverables="${staging_area}/BridgePoint_e${eclipse_ver}/EclipseDeliverables"
eclipse_deliverables_linux="${staging_area}/BridgePoint_for_Linux_e${eclipse_ver}/EclipseDeliverables"

cd ${build_dir}
configure_build_files
configure_installer_files
configure_masl_files

chmod -R g+w ${staging_area}
    
cd ${build_dir}

echo -e "Exiting configure_build_process.sh"

exit 0


