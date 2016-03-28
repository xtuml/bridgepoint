#!/bin/bash
#
#=====================================================================
#
# File:      configure_external_dependencies.sh
#
#=====================================================================
#

# Check arguments
if [ $# -ne 1 ]; then
    echo
    echo "Usage: ./configure_external_dependencies.sh <git_repo_root>"
    echo "      git_repo_root -- path to the root of the git repositories"
    echo
    exit 0
fi

#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------
#
# Make sure the caller put the required binaries in place
#
get_user_supplied_binaries ()
{
    echo -e "Entering configure_external_dependencies.sh::get_user_supplied_binaries"
    
    echo -e "Checking files in ${user_supplied_files}"
    cd $user_supplied_files
    missing_files=""
    if [ ! -e ./xtumlmc_build.exe ]; then
        missing_files+="./xtumlmc_build.exe"
    fi

    if [ ! -e ./gen_erate.exe ]; then
        missing_files+="./gen_erate.exe"
    fi

    if [ ! -e  ./vgalaxy8.vr ]; then
        missing_files+="./vgalaxy8.vr"
    fi

    if [ ! -e  ./vgal8c.dll ]; then
        missing_files+="./vgal8c.dll"
    fi

    if [ ! -e  ./msvcrt.dll ]; then
        missing_files+="./msvcrt.dll"
    fi

    if [ ! -e  ./mc3020_doc.zip ]; then
        missing_files+="./mc3020_doc.zip"
    fi
    
    if [ ! -e  ./mcmc ]; then
        missing_files+="./mcmc"
    fi

    if [ ! -e  ./mcmc.exe ]; then
        missing_files+="./mcmc.exe"
    fi
    
    if [ "$missing_files" != "" ]; then
       echo -e "Error!: Missing files: $missing_files"
       return 1
    fi
    echo -e "Exiting configure_external_dependencies.sh::get_user_supplied_binaries"
    return 0
}

configure_dap()
{
    echo ""
    echo "Configuring the Data Access Package for build."

    cd $dap/bridgepoint
    mkdir log_dir
    mkdir samples
    mkdir samples/translate
    mkdir win32
    mkdir win32/client
    mkdir win32/client/bin
    mkdir win32/client/lib

    cp -rp ${git_repo_root}/mc/etc/generator/bin ./bin

    cd $dap/bridgepoint/samples
    cp -fp ${git_repo_root}/mc/libTRANS/libTRANS.def ./translate/libTRANS.def
    cp -fp ${git_repo_root}/mc/libTRANS/libTRANS.mk  ./translate/libTRANS.mk
    cp -fp ${git_repo_root}/mc/libTRANS/pt_trans.c   ./translate/pt_trans.c
    cp -fp ${git_repo_root}/mc/libTRANS/pt_trans.h   ./translate/pt_trans.h

    cd $dap/bridgepoint
    cp -fp $user_supplied_files/gen_erate.exe   ./win32/client/bin/gen_erate.exe
    cp -fp $user_supplied_files/gen_erate.exe   ./win32/client/bin/gen_import.exe
    cp -fp $user_supplied_files/gen_erate.exe   ./win32/client/bin/gen_file.exe
    cp -fp $user_supplied_files/vgalaxy8.vr     ./win32/client/bin
    cp -fp $user_supplied_files/msvcrt.dll      ./win32/client/bin
    cp -fp $user_supplied_files/vgal8c.dll      ./win32/client/lib
    cp -fp ${git_repo_root}/mc/schema/sql/xtumlmc_schema.sql ./xtumlmc_schema.sql
    
    cd $dap/bridgepoint/win32/client
    cp -fp ${git_repo_root}/mc/libTRANS/libTRANS.dll ./lib/libTRANS.dll
    
    cd $dap/bridgepoint
    chmod -R g+w .
}

configure_mcc_src()
{
    echo ""
    echo "Configuring mcc_src for build."

    cd $mcc_src
    rm -rf mc3020/bin
    rm -rf mc3020/schema
    cd mc3020
    rm -rf ./arc
    cp -rp ${git_repo_root}/mc/arc ./arc
    rm -rf ./arc/sysc
    mv ./arc/c ./arc/specialized
    cp -rp ${git_repo_root}/mc/bin ./bin
    cp -rp ${git_repo_root}/mc/schema ./schema
    mv ./schema/default-manifest.xml ./

    cd $mcc_src/mc3020
    cp -fp ${git_repo_root}/mc/libTRANS/libTRANS.dll ./bin
    
    cp -fp $user_supplied_files/xtumlmc_build.exe ./bin
    cp -fp $user_supplied_files/gen_erate.exe     ./bin
    # The following line is just moving the linux generator out of the way so that the
    # build server still uses the windows generator.  Once we get BP building with the linux
    # python generator, the following line will not rename the file to .py and build_install_bp.sh should
    # be modified to remove the copy file handling on gen_erate.py
    cp -fp $user_supplied_files/gen_erate.pyz     ./bin/gen_erate.py
    cp -fp $user_supplied_files/mcmc              ./bin
    cp -fp $user_supplied_files/mcmc64            ./bin
    cp -fp $user_supplied_files/mcmc.exe          ./bin
    cp -fp $user_supplied_files/msvcrt.dll        ./bin
    cp -fp $user_supplied_files/msvcirt.dll       ./bin
    cp -fp $user_supplied_files/msvcp60.dll       ./bin
    cp -fp $user_supplied_files/vgal8c.dll        ./bin
    cp -fp $user_supplied_files/vgalaxy8.vr       ./bin
    
    # Since we're using the plug-in files themselves to build BridgePoint, make sure
    # they are executable and the xtumlmc_build.exe is in the right state (Windows vs. Linux)
    # and are in unix file format
	build_os=$(uname)
    if [ "${build_os}" = "Linux" ];  then
      mv -f ./bin/xtumlmc_build.exe ./bin/xtumlmc_build.exe.win
      tr -d '\r' < ./bin/xtumlmc_build > ./bin/xtumlmc_build.exe
      cp -fp ./bin/xtumlmc_build.exe ./bin/xtumlmc_build
	fi
    chmod a+x ./bin/xtumlmc_build*
	chmod a+x ./bin/gen_erate*
	chmod a+x ./bin/mcmc*
    
    cd ${bp_src_dir}
    cp -fp $user_supplied_files/mc3020_doc.zip $mc3020_help/doc.zip
    rm -f $mc3020_help/techpub.css
    rm -f $mc3020_help/toc.xml    
    cp -rp ${git_repo_root}/mc/doc/ug/xml/techpub.css $mc3020_help
    cp -rp ${git_repo_root}/mc/doc/ug/xml/toc.xml $mc3020_help
    
    cd $mcc_src/mc3020
    chmod -R g+w .
}

configure_mcc_bin()
{
    echo ""
    echo "Configuring mcc_bin for build."

    # Copy in the "bp.mc.c.source/mc3020/" dir
    cd $mcc_bin
    cp -rfp $mcc_src/mc3020 .
    
    # We brought across the C source arcs when we did this.  Remove.
    cd mc3020
    rm -rf ./arc

    cd $mcc_bin/mc3020
    chmod -R g+w .
}

configure_mcsystemc_src()
{
    echo ""
    echo "Configuring mcsystemc_src for build."

    # Copy in the "bp.mc.c.source/mc3020/" dir
    cd $mcsystemc_src
    cp -rfp $mcc_src/mc3020 .

    # We brought across the C source arcs when we did this.  Remove and bring in SystemC.
    cd mc3020
    rm -rf ./arc
    cp -rp ${git_repo_root}/mc/arc ./arc
    rm -rf ./arc/c
    mv ./arc/sysc ./arc/specialized
    mv ./schema/colors/system.mark ./schema/colors/system.mark.orig
    cat ./schema/colors/system.mark.sysc ./schema/colors/system.mark.orig > ./schema/colors/system.mark
    
    # We don't want the model-based MC for this version, so remove it
    rm -f ./bin/mcmc
    rm -f ./bin/mcmc64
    rm -f ./bin/mcmc.exe

    cd $mcsystemc_src/mc3020
    chmod -R g+w .
}

configure_mccpp_src()
{
    echo ""
    echo "Configuring mccpp_src for build."

    # Copy in the "bp.mc.c.source/mc3020/" dir
    cd $mccpp_src
    cp -rfp $mcc_src/mc3020 .

    # We brought across the C source arcs when we did this.  Remove and bring in SystemC/C++.
    cd mc3020
    rm -rf ./arc
    cp -rp ${git_repo_root}/mc/arc ./arc
    rm -rf ./arc/c
    mv ./arc/sysc ./arc/specialized

    # We don't want the model-based MC for this version, so remove it
    rm -f ./bin/mcmc
    rm -f ./bin/mcmc64
    rm -f ./bin/mcmc.exe

    cd $mccpp_src/mc3020
    chmod -R g+w .
}

configure_vhdl_src()
{
    echo ""
    echo "Configuring mcvhdl_src for build."

    # Copy in the "bp.mc.c.binary/mc3020/" dir
    cd $mcvhdl_src
    cp -rfp $mcc_bin/mc3020 .
    
    # We don't want the model-based MC for this version, so remove it
    rm -f ./mc3020/bin/mcmc
    rm -f ./mc3020/bin/mcmc64
    rm -f ./mc3020/bin/mcmc.exe

    cd $mcvhdl_src/mc3020
    chmod -R g+w .
}


configure_java_src()
{
    echo ""
    echo "Configuring java_src for build."

    # Copy in the "bp.mc.c.binary/mc3020/" dir
    cd $mcjava_src
    cp -rfp $mcc_bin/mc3020 .
    
    # We don't want the model-based MC for this version, so remove it
    rm -f ./mc3020/bin/mcmc
    rm -f ./mc3020/bin/mcmc64
    rm -f ./mc3020/bin/mcmc.exe

    cd $mcjava_src/mc3020
    chmod -R g+w .
}


#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------

echo -e "Entering configure_external_dependencies.sh"

git_repo_root=$1
bp_src_dir=${git_repo_root}/bridgepoint/src

# Define Locations for Components
user_supplied_files=${git_repo_root}/packaging/build/extra_files
bp_pkg=${bp_src_dir}/org.xtuml.bp.pkg
dap=${bp_src_dir}/org.xtuml.bp.dap.pkg
mcc_src=${bp_src_dir}/org.xtuml.bp.mc.c.source
mcc_bin=${bp_src_dir}/org.xtuml.bp.mc.c.binary
mcsystemc_src=${bp_src_dir}/org.xtuml.bp.mc.systemc.source
mccpp_src=${bp_src_dir}/org.xtuml.bp.mc.cpp.source
mcvhdl_src=${bp_src_dir}/org.xtuml.bp.mc.vhdl.source
mcjava_src=${bp_src_dir}/org.xtuml.bp.mc.java.source
mc3020_help=${bp_src_dir}/org.xtuml.help.bp.mc

get_user_supplied_binaries
if [ "$?" = "0" ];  then
  configure_mcc_src
  configure_mcc_bin
  configure_mcsystemc_src
  configure_mccpp_src
  configure_vhdl_src
  configure_java_src
  
  configure_dap
fi

echo -e "Exiting configure_external_dependencies.sh"

exit 0

