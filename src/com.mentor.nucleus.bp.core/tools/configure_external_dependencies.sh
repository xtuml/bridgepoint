#!/bin/bash
#=============================================================================
#
# File:      $RCSfile: configure_external_dependencies.sh,v $
# Version:   $Revision: 1.26 $
# Modified:  $Date: 2012/03/03 21:24:00 $
#
# (c) Copyright 2005-2012 Mentor Graphics Corporation
#     All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================
#
# This script is used in conjunction with the Model Compiler release checklist
# to create a Model Compiler release.
#

error()
{
    echo Error! $1
    exit 1
}

# This is used as a space to export junk out of CVS.  When you pass a -d <dir> 
# to cvs export, it doesn't like it if the <dir> already exists and has files in
# it, you get a "cvs [export aborted]: cannot export into working directory" 
# error.  If the dir exists but is empty, export doesn't outright fail, but it 
# does create a CVS dir underneath, which is exactly NOT the point of export.  
# So, we'll export to the temp dir, move the files, then delete the temp dir.
# The target dir is created (with parents) if it doesn't already exist
#
# Example usage:
#   src="myproject/stuff/data"
#   tgt="data"
#   do_export
#
#   src="anotherproj/coolfile.java"
#   tgt="src"
#   do_export
#
exportTempDir="export_temp"
function do_export()
{
	cvs export ${export_flags} -r ${TAG_NAME} -d ${exportTempDir} ${src}
	if [ ! -d ${tgt} ]; then
		mkdir -p ${tgt}
	fi
	echo "Moving exported files to: ${tgt}"
	cp -rf ${exportTempDir}/* ${tgt}
	rm -rf ${exportTempDir}
}

#
# Make sure the caller put the required binaries in-place as required by the
# checklist
#
test_user_supplied_binaries ()
{
    cd $USER_SUPPLIED_FILES

    if [ ! -e ./xtumlmc_build.exe ]
    then
        error "Missing ./xtumlmc_build.exe"
    fi

    if [ ! -e ./gen_erate.exe ]
    then
        error "Missing ./gen_erate.exe"
    fi

    if [ ! -e  ./vgalaxy8.vr ]
    then
        error "Missing ./vgalaxy8.vr"
    fi

    if [ ! -e  ./vgal8c.dll ]
    then
        error "Missing ./vgal8c.dll"
    fi

    if [ ! -e  ./msvcrt.dll ]
    then
        error "Missing ./msvcrt.dll"
    fi

    if [ ! -e  ./mc3020_doc.zip ]
    then
        error "Missing ./mc3020_doc.zip"
    fi
}

configure_dap()
{
    echo ""
    echo "Configuring the Data Access Pacakge for build."

    cd $DAP/bridgepoint
    mkdir bin
    mkdir log_dir
    mkdir samples
    mkdir samples/translate
    mkdir win32
    mkdir win32/client
    mkdir win32/client/bin
    mkdir win32/client/lib

    #cvs -q export $export_flags -r $TAG_NAME -d bin model_compilers/generator/bin
    src="model_compilers/generator/bin"
    tgt="bin"
    do_export

    cd $DAP/bridgepoint/samples
    #cvs -q export $export_flags -r $TAG_NAME -d translate libTRANS/libTRANS.def
    src="libTRANS/libTRANS.def"
    tgt="translate"
    do_export
	#cvs -q export $export_flags -r $TAG_NAME -d translate libTRANS/libTRANS.mk
    src="libTRANS/libTRANS.mk"
    tgt="translate"
    do_export
    #cvs -q export $export_flags -r $TAG_NAME -d translate libTRANS/pt_trans.c
    src="libTRANS/pt_trans.c"
    tgt="translate"
    do_export
	#cvs -q export $export_flags -r $TAG_NAME -d translate libTRANS/pt_trans.h
    src="libTRANS/pt_trans.h"
    tgt="translate"
    do_export

    cd $DAP/bridgepoint
    cp -f $USER_SUPPLIED_FILES/gen_erate.exe   ./win32/client/bin/gen_erate.exe
    cp -f $USER_SUPPLIED_FILES/gen_erate.exe   ./win32/client/bin/gen_import.exe
    cp -f $USER_SUPPLIED_FILES/gen_erate.exe   ./win32/client/bin/gen_file.exe
    cp -f $USER_SUPPLIED_FILES/vgalaxy8.vr     ./win32/client/bin
    cp -f $USER_SUPPLIED_FILES/msvcrt.dll      ./win32/client/bin
    cp -f $USER_SUPPLIED_FILES/vgal8c.dll      ./win32/client/lib
    cp -f $MC3020/mc3020/schema/sql/xtumlmc_schema.sql ./xtumlmc_schema.sql
    
    cd $DAP/bridgepoint/win32/client
    #cvs -q export $export_flags -r $TAG_NAME -d lib libTRANS/libTRANS.dll
    src="libTRANS/libTRANS.dll"
    tgt="lib"
    do_export

    cd $STARTDIR
}

configure_mc3020()
{
    echo ""
    echo "Configuring mc3020 for build."

    cd $MC3020
    rm -rf mc3020
    #mkdir mc3020
    #cvs -q export $export_flags -r $TAG_NAME -d mc3020 model_compilers/mc3020
    src="model_compilers/mc3020"
    tgt="mc3020"
    do_export
    
    # Files from mccommon will overwrite files from mc3020
    #cvs -q export $export_flags -r $TAG_NAME -d mc3020 model_compilers/mccommon
    src="model_compilers/mccommon"
    tgt="mc3020"
    do_export

    # For legacy purposes create a .pl version of xtumlmc_build
    cd $MC3020/mc3020/bin
    cp -f xtumlmc_build xtumlmc_build.pl
    
    cd $MC3020/mc3020
    rm -rf arc
    #cvs -q export $export_flags -r $TAG_NAME -d bin libTRANS/libTRANS.dll
    src="libTRANS/libTRANS.dll"
    tgt="bin"
    do_export
    
    cp -f $USER_SUPPLIED_FILES/xtumlmc_build.exe ./bin
    cp -f $USER_SUPPLIED_FILES/gen_erate.exe     ./bin
    cp -f $USER_SUPPLIED_FILES/msvcrt.dll        ./bin
    cp -f $USER_SUPPLIED_FILES/vgal8c.dll        ./bin
    cp -f $USER_SUPPLIED_FILES/vgalaxy8.vr       ./bin

    cd $REMOTE_BUILD_DIR
    cp -f $USER_SUPPLIED_FILES/mc3020_doc.zip $MC3020_HELP/doc.zip
    rm -f $MC3020_HELP/techpub.css
    rm -f $MC3020_HELP/toc.xml
    
    #cvs -q export $export_flags -r $TAG_NAME -d $MC3020_HELP MC-Documentation/external/mc3020/ug/xml/techpub.css
    src="MC-Documentation/external/mc3020/ug/xml/techpub.css"
    tgt="$MC3020_HELP"
    do_export
    #cvs -q export $export_flags -r $TAG_NAME -d $MC3020_HELP MC-Documentation/external/mc3020/ug/xml/toc.xml
    src="MC-Documentation/external/mc3020/ug/xml/toc.xml"
    tgt="$MC3020_HELP"
    do_export

    cd $STARTDIR
}

configure_mcc_src()
{
    echo ""
    echo "Configuring mcc_src for build."

    # Copy in the "bp.mc.mc3020/mc3020/" dir, then get rid of cruft we don't
    # need/want from there
    cd $MCC_SRC
    rm -rf mc3020
    cp -rf $MC3020/mc3020 .
    
    cd $MCC_SRC/mc3020
    rm -rf arc
    rm -rf examples
    rm -rf str_update

    cd $STARTDIR
}

configure_mcc_bin()
{
    echo ""
    echo "Configuring mcc_bin for build."

    # Copy in the "bp.mc.mc3020/mc3020/" dir, then get rid of cruft we don't
    # need/want from there
    cd $MCC_BIN
    rm -rf mc3020
    cp -rf $MC3020/mc3020 .
    
    cd $MCC_BIN/mc3020
    rm -rf arc
    rm -rf examples
    rm -rf str_update

    cd $STARTDIR
}

configure_mcsystemc_src()
{
    echo ""
    echo "Configuring mcsystemc_src for build."

    # Copy in the "bp.mc.mc3020/mc3020/" dir, then get rid of cruft we don't
    # need/want from there
    cd $MCSYSTEMC_SRC
    rm -rf mc3020
    cp -rf $MC3020/mc3020 .
    
    cd $MCSYSTEMC_SRC/mc3020
    rm -rf arc
    rm -rf examples
    rm -rf str_update

    cd $STARTDIR
}

configure_mccpp_src()
{
    echo ""
    echo "Configuring mccpp_src for build."

    # Copy in the "bp.mc.mc3020/mc3020/" dir, then get rid of cruft we don't
    # need/want from there
    cd $MCCPP_SRC
    rm -rf mc3020
    cp -rf $MC3020/mc3020 .
    
    cd $MCCPP_SRC/mc3020
    rm -rf arc
    rm -rf examples
    rm -rf str_update

    cd $STARTDIR
}

configure_vhdl_src()
{
    echo ""
    echo "Configuring mcvhdl_src for build."

    # Copy in the "bp.mc.mc3020/mc3020/" dir, then get rid of cruft we don't
    # need/want from there
    cd $MCVHDL_SRC
    rm -rf mc3020
    cp -rf $MC3020/mc3020 .
    
    cd $MCVHDL_SRC/mc3020
    rm -rf arc
    rm -rf examples
    rm -rf str_update

    cd $STARTDIR
}

configure_sample_models()
{
    echo ""
    echo "Configuring the sample models for inclusion."

    cd $STARTDIR
}


###############################################################################
###############################################################################

ARGNO=4

if [ $# -lt "$ARGNO" ]
then
  echo ""
  echo "Usage: $0 <Release Tag> <Release Version> <User files directory> <remote build root> [nonrelease]"
  echo ""
  echo "  Example: $0 R1_4_0_20061017 1.4.0 /arch1/products/tiger/extra_files_for_build /arch1/products/tiger/nightly_build/releases/R1_4_0_20061017"
  echo ""
  error "Exiting."
fi

STARTDIR=`pwd`
TAG_NAME=$1
RELEASE_VER=$2
USER_SUPPLIED_FILES=$3
REMOTE_BUILD_DIR=$4

if [ "$5" = "nonrelease" ]; then
    export_flags=" -f "
else
    export_flags=""
fi

export CVSROOT=:pserver:build@tucson.wv.mentorg.com:/arch1/products/tiger/repository

echo "Configuring external dependencies."
echo "   Tag name: $TAG_NAME"
echo "   User supplied files: $USER_SUPPLIED_FILES"
echo "   Remote build dir: $REMOTE_BUILD_DIR"

# Define Locations for Components
BP_PKG=$REMOTE_BUILD_DIR/com.mentor.nucleus.bp.pkg
DAP=$REMOTE_BUILD_DIR/com.mentor.nucleus.bp.dap.pkg
MC3020=$REMOTE_BUILD_DIR/com.mentor.nucleus.bp.mc.mc3020
MCC_SRC=$REMOTE_BUILD_DIR/com.mentor.nucleus.bp.mc.c.source
MCC_BIN=$REMOTE_BUILD_DIR/com.mentor.nucleus.bp.mc.c.binary
MCSYSTEMC_SRC=$REMOTE_BUILD_DIR/com.mentor.nucleus.bp.mc.systemc.source
MCCPP_SRC=$REMOTE_BUILD_DIR/com.mentor.nucleus.bp.mc.cpp.source
MCVHDL_SRC=$REMOTE_BUILD_DIR/com.mentor.nucleus.bp.mc.vhdl.source
MC3020_HELP=com.mentor.nucleus.help.bp.mc

test_user_supplied_binaries

configure_mc3020
configure_mcc_src
configure_mcc_bin
configure_mcsystemc_src
configure_mccpp_src
configure_vhdl_src

configure_dap

configure_sample_models

cd $STARTDIR

exit 0

