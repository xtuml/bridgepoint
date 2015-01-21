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
    
    cd ${BUILD_DIR}
    export_trunk="false"
    if [ "${BRANCH}" = "master" ]; then
        export_trunk="true"
    else
        # check out the branch
        svn export http://wv-svn-01.wv.mentorg.com/svn/sle/xtuml/branches/${BRANCH}/extra_files_for_build --username sle_build --password qkfJkv2=
        
        # if export failed and build type != nonrelease, then error
        if [ ! -x $USER_SUPPLIED_FILES ] && [ "${BUILD_TYPE}" != "nonrelease" ]; then
            echo -e "SVN branch ${BRANCH} does not exist and is required for this build. Exiting.\n"
            exit 1
        fi
        
        # if export failed and build type = nonrelease, then checkout master
        if [ ! -x $USER_SUPPLIED_FILES ] && [ "${BUILD_TYPE}" = "nonrelease" ]; then
            export_trunk="true"
        fi
        
    fi
    
    if [ "${export_trunk}" = "true" ]; then
        svn export http://wv-svn-01.wv.mentorg.com/svn/sle/xtuml/trunk/extra_files_for_build --username sle_build --password qkfJkv2=
    fi
    
    cd $USER_SUPPLIED_FILES
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

    cd $DAP/bridgepoint
    mkdir log_dir
    mkdir samples
    mkdir samples/translate
    mkdir win32
    mkdir win32/client
    mkdir win32/client/bin
    mkdir win32/client/lib

    cp -r ${GIT_REPO_ROOT}/mc/etc/generator/bin ./bin

    cd $DAP/bridgepoint/samples
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/libTRANS.def ./translate/libTRANS.def
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/libTRANS.mk  ./translate/libTRANS.mk
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/pt_trans.c   ./translate/pt_trans.c
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/pt_trans.h   ./translate/pt_trans.h

    cd $DAP/bridgepoint
    cp -f $USER_SUPPLIED_FILES/gen_erate.exe   ./win32/client/bin/gen_erate.exe
    cp -f $USER_SUPPLIED_FILES/gen_erate.exe   ./win32/client/bin/gen_import.exe
    cp -f $USER_SUPPLIED_FILES/gen_erate.exe   ./win32/client/bin/gen_file.exe
    cp -f $USER_SUPPLIED_FILES/vgalaxy8.vr     ./win32/client/bin
    cp -f $USER_SUPPLIED_FILES/msvcrt.dll      ./win32/client/bin
    cp -f $USER_SUPPLIED_FILES/vgal8c.dll      ./win32/client/lib
    cp -f $MC3020/mc3020/schema/sql/xtumlmc_schema.sql ./xtumlmc_schema.sql
    
    cd $DAP/bridgepoint/win32/client
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/libTRANS.dll ./lib/libTRANS.dll

    cd ${BUILD_DIR}
}

configure_mcc_src()
{
    echo ""
    echo "Configuring mcc_src for build."

    cd $MCC_SRC
    rm -rf mc3020
    mkdir mc3020
    cd mc3020
    cp -r ${GIT_REPO_ROOT}/mc/bin ./bin
    cp -r ${GIT_REPO_ROOT}/mc/schema ./schema
    mv ./schema/default-manifest.xml ./

    cd $MCC_SRC/mc3020
    cp -f ${GIT_REPO_ROOT}/mc/libTRANS/libTRANS.dll ./bin
    
    cp -f $USER_SUPPLIED_FILES/xtumlmc_build.exe ./bin
    cp -f $USER_SUPPLIED_FILES/gen_erate.exe     ./bin
    cp -f $USER_SUPPLIED_FILES/mcmc              ./bin
    cp -f $USER_SUPPLIED_FILES/mcmc.exe          ./bin
    cp -f $USER_SUPPLIED_FILES/msvcrt.dll        ./bin
    cp -f $USER_SUPPLIED_FILES/vgal8c.dll        ./bin
    cp -f $USER_SUPPLIED_FILES/vgalaxy8.vr       ./bin

    cd ${BUILD_DIR}
    cp -f $USER_SUPPLIED_FILES/mc3020_doc.zip $MC3020_HELP/doc.zip
    rm -f $MC3020_HELP/techpub.css
    rm -f $MC3020_HELP/toc.xml    
    cp -r ${GIT_REPO_ROOT}/mc/doc/ug/xml/techpub.css $MC3020_HELP
    cp -r ${GIT_REPO_ROOT}/mc/doc/ug/xml/toc.xml $MC3020_HELP
    
    cd ${BUILD_DIR}
}

configure_mcc_bin()
{
    echo ""
    echo "Configuring mcc_bin for build."

    # Copy in the "bp.mc.c.source/mc3020/" dir
    cd $MCC_BIN
    rm -rf mc3020
    cp -rf $MCC_SRC/mc3020 .
    
    cd ${BUILD_DIR}
}

configure_mcsystemc_src()
{
    echo ""
    echo "Configuring mcsystemc_src for build."

    # Copy in the "bp.mc.c.source/mc3020/" dir
    cd $MCSYSTEMC_SRC
    rm -rf mc3020
    cp -rf $MCC_SRC/mc3020 .

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
    cd $MCCPP_SRC
    rm -rf mc3020
    cp -rf $MCC_SRC/mc3020 .

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
    cd $MCVHDL_SRC
    rm -rf mc3020
    cp -rf $MCC_SRC/mc3020 .
    
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
USER_SUPPLIED_FILES=${BUILD_DIR}/extra_files_for_build
BP_PKG=${BUILD_DIR}/com.mentor.nucleus.bp.pkg
DAP=${BUILD_DIR}/com.mentor.nucleus.bp.dap.pkg
MCC_SRC=${BUILD_DIR}/com.mentor.nucleus.bp.mc.c.source
MCC_BIN=${BUILD_DIR}/com.mentor.nucleus.bp.mc.c.binary
MCSYSTEMC_SRC=${BUILD_DIR}/com.mentor.nucleus.bp.mc.systemc.source
MCCPP_SRC=${BUILD_DIR}/com.mentor.nucleus.bp.mc.cpp.source
MCVHDL_SRC=${BUILD_DIR}/com.mentor.nucleus.bp.mc.vhdl.source
MC3020_HELP=${BUILD_DIR}/com.mentor.nucleus.help.bp.mc

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

