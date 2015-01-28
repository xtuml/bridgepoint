#!/bin/bash
#=====================================================================
#
# File:      build_installer_bp_linux.sh
#
# Copyright 2006-2014 Mentor Graphics Corporation. All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================
#
#
# Script to create BridgePoint Installer
#   This script should live in c:\nightly_build on the build server and must
#   run locally.  It does not go through the steps of creating a new workspace
#   for each installer.  Instead it simply loads a pre-configured workspace
#   (C:/BridgePoint), modifies the BP extension, and builds the installer up
#   from there.
#

date

PRODUCT_VER="$1"

if [ "${PRODUCT_VER}" = "" ]; then
  echo "ERROR: You must provide a product version (e.g. master, R2_2_2) as the first argument."
  exit 1
fi

PRODUCT_NAME="BridgePoint"
ECLIPSE_VER="3.7"
BP_BASE_DIR="C:/BridgePoint_for_Linux_e${ECLIPSE_VER}"
TEMP_DIR="C:/temp_linux"
WINDOWS_FRAGMENT="com.mentor.nucleus.bp.core.win32.x86"
MCMC_EXE="com.mentor.nucleus.bp.mc.c.binary_4.2.1/mc3020/bin/mcmc"
SEQUENCE_CREATOR="com.mentor.nucleus.bp.sequencecapture_4.2.1.jar"
SERVER="tucson.wv"
REMOTE_RELEASE_DIR="/arch1/products/tiger/releases/${PRODUCT_VER}"
EXT_SRC_DIR="build@${SERVER}:${REMOTE_RELEASE_DIR}" 
EXT_SRC_FILE="${PRODUCT_NAME}_extension_${PRODUCT_VER}.zip"
#
# We had "path to long" issues when using the installer data dir reference under
# the c drive directly, so instead we use a mapped a drive letter to the desired
# path and use that instead.  This helps circumvent the issue.
INSTALLER_DATA_DIR="U:/"
BIN_DIR="C:/MIMIC/rlstools/bin"
WKSP_LOC="C:/MIMIC/mimic_ws_bp_linux_e3.7"
BUNDLE_NAME="${PRODUCT_NAME}"
PLATFORM="ixl"
DIRECTOR="${PRODUCT_NAME}_Linux_e3.7"
VCD_NAME="${PRODUCT_NAME}_Media"

# Put the new BridgePoint extension in place for the installer
echo "INFO: Copying new source data to ${TEMP_DIR}."
cd "${TEMP_DIR}"
echo "INFO:   Copying in ${EXT_SRC_DIR}/${EXT_SRC_FILE}."
scp -B "${EXT_SRC_DIR}/${EXT_SRC_FILE}" .
if [ ! -e "${TEMP_DIR}/${EXT_SRC_FILE}" ]; then
  echo "ERROR: Source extension (${TEMP_DIR}/${EXT_SRC_FILE}) does not exist.  Exiting."
  exit 2
fi
echo "INFO: Done."

# We run into file path length issues if we try to unzip right into INSTALLER_DATA_DIR, so unzip to a temp dir
echo "INFO: Unzipping new source data in ${TEMP_DIR}."
echo "INFO:   Unzipping ${EXT_SRC_FILE}."
rm -rf ${PRODUCT_NAME}
rm -rf ${PRODUCT_NAME}_${PRODUCT_VER}
unzip -q "${EXT_SRC_FILE}"
if [ -e "${PRODUCT_NAME}_${PRODUCT_VER}" ]; then
  mv "${PRODUCT_NAME}_${PRODUCT_VER}" "${PRODUCT_NAME}"
  rm -f "${EXT_SRC_FILE}"
else
  echo "ERROR: Error creating the new extension directory.  Exiting."
  exit 3
fi
echo "INFO: Done."

echo "INFO: Moving new extension data to ${INSTALLER_DATA_DIR}."
cd "${INSTALLER_DATA_DIR}"
rm -rf ${PRODUCT_NAME}
mv "${TEMP_DIR}/${PRODUCT_NAME}" .
echo "INFO: Done."

echo "INFO: Setting up docgen executable"
cd "${BP_BASE_DIR}/BridgePointDeliverables/tools/docgen"
cp -f "${INSTALLER_DATA_DIR}/${PRODUCT_NAME}/eclipse/plugins/${MCMC_EXE}" docgen
echo "INFO: Done."

echo "INFO: Removing unneeded Windows plugin fragment."
cd "${INSTALLER_DATA_DIR}/${PRODUCT_NAME}/eclipse/plugins/"
rm -rf ${WINDOWS_FRAGMENT}*
echo "INFO: Done."

echo "INFO: Disabling sequence creator plugin."
cd "${INSTALLER_DATA_DIR}/${PRODUCT_NAME}/eclipse/plugins/"
mv -f ${SEQUENCE_CREATOR} ${SEQUENCE_CREATOR}.no
echo "INFO: Done."

# Start MIMIC Daemon and set MIMIC_SESSIONID
#  to the return of startDaemon step
echo "INFO: Running MIMIC to create the installer."
cd "${BIN_DIR}"
MIMIC_SESSIONID=`./mimic_cmd "startDaemon()"`
export MIMIC_SESSIONID
echo "INFO:   ${MIMIC_SESSIONID} is the new session id."

# Load the workspace
echo "INFO:   Trying to Open the Workspace"
./mimic_cmd "loadWorkSpace (${WKSP_LOC})"

if [ $? -eq 0 ]; then
  echo "INFO:   Using workspace: ${WKSP_LOC}"
else
  echo "ERROR: Couldn't open workspace ${WKSP_LOC}, exiting."
  exit 4
fi

# Verify step.
./mimic_cmd "verifyMetaData ()"

# Compression step.
./mimic_cmd "compressPackages ()"

# Generate VCD to put pieces together to create VCD
./mimic_cmd "generateVCD ()"

# Create the install bundle
./mimic_cmd "packInstallBundle (${BUNDLE_NAME},${PLATFORM},${DIRECTOR},${VCD_NAME},F)"

# Save your current workspace and exit your MIMIC session.
./mimic_cmd "saveWorkSpace ()"
./mimic_cmd "exitDaemon ()"
echo "INFO: Done."

# Rename the output file
DATESTAMP=`date +%Y%m%d%H%M`
echo "INFO: Renaming the output file to ${BUNDLE_NAME}_${PRODUCT_VER}_${DATESTAMP}.ixl."
cd "${WKSP_LOC}/Bundles"
if [ "${PRODUCT_VER}" = "master" ]; then
  rm -rf ${BUNDLE_NAME}_${PRODUCT_VER}_*.ixl
fi
mv "${BUNDLE_NAME}.ixl" "${BUNDLE_NAME}_${PRODUCT_VER}_${DATESTAMP}.ixl"
echo "INFO: Done."

# Make sure the output looks good
size=$(du -k ${BUNDLE_NAME}_${PRODUCT_VER}_${DATESTAMP}.ixl | sed 's/\([0-9]*\)\(.*\)/\1/')
if [ ${size} -lt 200000 ]; then
  echo "ERROR: Created installer file size is less than expected, exiting."
  exit 5
fi

# Move it to the release area
echo "INFO: Copying the new installer to the release website."
if [ "${PRODUCT_VER}" = "master" ]; then
  ssh ${SERVER} "(cd '${REMOTE_RELEASE_DIR}'; rm -rf ${BUNDLE_NAME}_${PRODUCT_VER}_*.ixl)"
fi
scp -B "${BUNDLE_NAME}_${PRODUCT_VER}_${DATESTAMP}.ixl" "${EXT_SRC_DIR}"
ssh ${SERVER} "(cd '${REMOTE_RELEASE_DIR}'; chmod 755 ${BUNDLE_NAME}_${PRODUCT_VER}_*.ixl)"
echo "INFO: Done."

echo "INFO: Linux Installer creation complete.  Goodbye."
