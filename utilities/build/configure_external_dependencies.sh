#!/bin/bash
#
#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------
error()
{
    echo Error! $1
    exit 1
}

#
# Make sure the caller put the required binaries in place
#
get_user_supplied_binaries ()
{
    echo -e "Entering configure_external_dependencies.sh::get_user_supplied_binaries"
    
    mkdir $user_supplied_files
    
    # Get the need files from the revision control folder
    cp ${GIT_REPO_ROOT}/packaging/* $user_supplied_files
    
    cd $user_supplied_files
    if [ ! -e ./xtumlmc_build.exe ]; then
        error "Missing ./xtumlmc_build.exe"
    fi

    if [ ! -e ./gen_erate.exe ]; then
        error "Missing ./gen_erate.exe"
    fi

    if [ ! -e  ./vgalaxy8.vr ]; then
        error "Missing ./vgalaxy8.vr"
    fi

    if [ ! -e  ./vgal8c.dll ]; then
        error "Missing ./vgal8c.dll"
    fi

    if [ ! -e  ./msvcrt.dll ]; then
        error "Missing ./msvcrt.dll"
    fi

    if [ ! -e  ./mc3020_doc.zip ]; then
        error "Missing ./mc3020_doc.zip"
    fi
    
    if [ ! -e  ./mcmc ]; then
        error "Missing ./mcmc"
    fi

    if [ ! -e  ./mcmc.exe ]; then
        error "Missing ./mcmc.exe"
    fi
    
    echo -e "Exiting configure_external_dependencies.sh::get_user_supplied_binaries"
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

    cp -r ${GIT_REPO_ROOT}/mc/etc/generator/bin ./bin

    cd $dap/bridgepoint/samples
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/libTRANS.def ./translate/libTRANS.def
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/libTRANS.mk  ./translate/libTRANS.mk
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/pt_trans.c   ./translate/pt_trans.c
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/pt_trans.h   ./translate/pt_trans.h

    cd $dap/bridgepoint
    cp -f $user_supplied_files/gen_erate.exe   ./win32/client/bin/gen_erate.exe
    cp -f $user_supplied_files/gen_erate.exe   ./win32/client/bin/gen_import.exe
    cp -f $user_supplied_files/gen_erate.exe   ./win32/client/bin/gen_file.exe
    cp -f $user_supplied_files/vgalaxy8.vr     ./win32/client/bin
    cp -f $user_supplied_files/msvcrt.dll      ./win32/client/bin
    cp -f $user_supplied_files/vgal8c.dll      ./win32/client/lib
    cp -f $MC3020/mc3020/schema/sql/xtumlmc_schema.sql ./xtumlmc_schema.sql
    
    cd $dap/bridgepoint/win32/client
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/libTRANS.dll ./lib/libTRANS.dll

    cd ${BUILD_DIR}
}

configure_mcc_src()
{
    echo ""
    echo "Configuring mcc_src for build."

    cd $mcc_src
    rm -rf mc3020
    mkdir mc3020
    cd mc3020
    cp -r ${GIT_REPO_ROOT}/mc/bin ./bin
    cp -r ${GIT_REPO_ROOT}/mc/schema ./schema
    mv ./schema/default-manifest.xml ./

    cd $mcc_src/mc3020
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/libTRANS.dll ./bin
    
    cp -f $user_supplied_files/xtumlmc_build.exe ./bin
    cp -f $user_supplied_files/gen_erate.exe     ./bin
    cp -f $user_supplied_files/mcmc              ./bin
    cp -f $user_supplied_files/mcmc.exe          ./bin
    cp -f $user_supplied_files/msvcrt.dll        ./bin
    cp -f $user_supplied_files/vgal8c.dll        ./bin
    cp -f $user_supplied_files/vgalaxy8.vr       ./bin

    cd ${BUILD_DIR}
    cp -f $user_supplied_files/mc3020_doc.zip $mc3020_help/doc.zip
    rm -f $mc3020_help/techpub.css
    rm -f $mc3020_help/toc.xml    
    cp -r ${GIT_REPO_ROOT}/mc/doc/ug/xml/techpub.css $mc3020_help
    cp -r ${GIT_REPO_ROOT}/mc/doc/ug/xml/toc.xml $mc3020_help
    
    cd ${BUILD_DIR}
}

configure_mcc_bin()
{
    echo ""
    echo "Configuring mcc_bin for build."

    # Copy in the "bp.mc.c.source/mc3020/" dir
    cd $mcc_bin
    rm -rf mc3020
    cp -rf $mcc_src/mc3020 .
    
    cd ${BUILD_DIR}
}

configure_mcsystemc_src()
{
    echo ""
    echo "Configuring mcsystemc_src for build."

    # Copy in the "bp.mc.c.source/mc3020/" dir
    cd $mcsystemc_src
    rm -rf mc3020
    cp -rf $mcc_src/mc3020 .

    # We don't want the model-based MC for this version, so remove it
    rm -f ./mc3020/bin/mcmc
    rm -f ./mc3020/bin/mcmc.exe
     
    cd ${BUILD_DIR}
}

configure_mccpp_src()
{
    echo ""
    echo "Configuring mccpp_src for build."

    # Copy in the "bp.mc.c.source/mc3020/" dir
    cd $mccpp_src
    rm -rf mc3020
    cp -rf $mcc_src/mc3020 .

    # We don't want the model-based MC for this version, so remove it
    rm -f ./mc3020/bin/mcmc
    rm -f ./mc3020/bin/mcmc.exe
     
    cd ${BUILD_DIR}
}

configure_vhdl_src()
{
    echo ""
    echo "Configuring mcvhdl_src for build."

    # Copy in the "bp.mc.c.source/mc3020/" dir
    cd $mcvhdl_src
    rm -rf mc3020
    cp -rf $mcc_src/mc3020 .
    
    # We don't want the model-based MC for this version, so remove it
    rm -f ./mc3020/bin/mcmc
    rm -f ./mc3020/bin/mcmc.exe
     
    cd ${BUILD_DIR}
}


#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------

echo -e "Entering configure_external_dependencies.sh"

# Define Locations for Components
user_supplied_files=${BUILD_DIR}/extra_files_for_build
bp_pkg=${BUILD_DIR}/com.mentor.nucleus.bp.pkg
dap=${BUILD_DIR}/com.mentor.nucleus.bp.dap.pkg
mcc_src=${BUILD_DIR}/com.mentor.nucleus.bp.mc.c.source
mcc_bin=${BUILD_DIR}/com.mentor.nucleus.bp.mc.c.binary
mcsystemc_src=${BUILD_DIR}/com.mentor.nucleus.bp.mc.systemc.source
mccpp_src=${BUILD_DIR}/com.mentor.nucleus.bp.mc.cpp.source
mcvhdl_src=${BUILD_DIR}/com.mentor.nucleus.bp.mc.vhdl.source
mc3020_help=${BUILD_DIR}/com.mentor.nucleus.help.bp.mc

get_user_supplied_binaries

configure_mcc_src
configure_mcc_bin
configure_mcsystemc_src
configure_mccpp_src
configure_vhdl_src

configure_dap

cd ${BUILD_DIR}

echo -e "Exiting configure_external_dependencies.sh"

exit 0

