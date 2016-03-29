#!/bin/bash
#=====================================================================
#
# File:      build_installer_bp.sh
#
#=====================================================================
#

date

# Check arguments
if [ $# -lt 5 ]; then
    echo
    echo "Usage: ./build_installer_bp.sh <product_branch> <staging_path> <output_dir> <os> <release_version> <upload location>"
    echo "      product_branch -- e.g. master, R4_2_1"
    echo "      staging_path -- path to the location of the Eclipse bases and BridgePoint deliverables"
    echo "      output_dir -- path to the location to output the installers"
    echo "      os - windows, linux or osx"
    echo "      release_version -- e.g. 5.3.2"
    echo "   optional:"
    echo "      SCP_UPLOAD_FOLDER_SPEC -- folder specification for scp upload: user@myserver.com:/myfolder"
    echo
    exit 0
fi

PRODUCT_BRANCH="$1"
STAGING_PATH="$2"
OUTPUT_DIR="$3"
OS_ARG="$4"
BP_VERSION="$5"
SCP_UPLOAD_FOLDER_SPEC="$6"

echo "Installer invocation: ./build_installer_bp.sh ${PRODUCT_BRANCH} ${STAGING_PATH} ${OUTPUT_DIR} ${OS_ARG} ${BP_VERSION} ${SCP_UPLOAD_FOLDER_SPEC}"

PRODUCT_NAME="BridgePoint"
ECLIPSE_VER="4.5"

# Assume windows unless we case-insensitively match Linux on the argument
OS="windows"
BP_BASE_DIR="${STAGING_PATH}/${PRODUCT_NAME}_e${ECLIPSE_VER}"
DOCGEN_EXE="docgen.exe"
MCMC_EXE="org.xtuml.bp.mc.c.binary_${BP_VERSION}/mc3020/bin/mcmc.exe"
if [ "${OS_ARG,,}" = "linux" ] || [ "${OS_ARG,,}" = "osx" ]; then
  OS="linux"
  BP_BASE_DIR="${STAGING_PATH}/${PRODUCT_NAME}_for_Linux_e${ECLIPSE_VER}"
  MCMC_EXE="org.xtuml.bp.mc.c.binary_${BP_VERSION}/mc3020/bin/mcmc"
  DOCGEN_EXE="docgen"
fi

INSTALL_PROJECT="installer"
TEMP_DIR="/tmp"
SEQUENCE_CREATOR="org.xtuml.bp.sequencecapture_${BP_VERSION}.jar"
SERVER="xtuml.org"
EXT_SRC_FILE="${PRODUCT_NAME}_extension_${PRODUCT_BRANCH}.zip"
INSTALLER_DATA_DIR="${BP_BASE_DIR}/EclipseDeliverables/eclipse"

echo "INFO: Building ${PRODUCT_NAME} installer for ${OS}."

# Put the new ${PRODUCT_NAME} extension in place for the installer
echo "INFO: Copying new source data to ${TEMP_DIR}."
cd "${TEMP_DIR}"
echo "INFO:   Copying in ${OUTPUT_DIR}/${EXT_SRC_FILE}."
cp -f "${OUTPUT_DIR}/${EXT_SRC_FILE}" .
if [ ! -e "${OUTPUT_DIR}/${EXT_SRC_FILE}" ]; then
  echo "ERROR: Source extension (${TEMP_DIR}/${EXT_SRC_FILE}) does not exist.  Exiting."
  exit 2
fi
echo "INFO: Done."

echo "INFO: Unzipping new source data in ${TEMP_DIR}."
echo "INFO:   Unzipping ${EXT_SRC_FILE}."
rm -rf ${PRODUCT_NAME}
rm -rf ${PRODUCT_NAME}_${PRODUCT_BRANCH}
unzip -q "${EXT_SRC_FILE}"
if [ -e "${PRODUCT_NAME}_${PRODUCT_BRANCH}" ]; then
  mv "${PRODUCT_NAME}_${PRODUCT_BRANCH}" "${PRODUCT_NAME}"
  rm -f "${EXT_SRC_FILE}"
else
  echo "ERROR: Error creating the new extension directory.  Exiting."
  exit 3
fi
echo "INFO: Done."

echo "INFO: Configuring correct mc build tools for ${OS}."
cd "${PRODUCT_NAME}/eclipse/plugins"
if [ "${OS}" = "linux" ]; then
      mcplugin="./org.xtuml.bp.mc.c.binary_${BP_VERSION}/mc3020/bin"
      tr -d '\r' < ${mcplugin}/xtumlmc_build > ${mcplugin}/xtumlmc_build.exe
      cp -f ${mcplugin}/xtumlmc_build.exe ${mcplugin}/xtumlmc_build
      mv -f ${mcplugin}/gen_erate.py ${mcplugin}/gen_erate.pyz

      mcplugin="./org.xtuml.bp.mc.c.source_${BP_VERSION}/mc3020/bin"
      tr -d '\r' < ${mcplugin}/xtumlmc_build > ${mcplugin}/xtumlmc_build.exe
      cp -f ${mcplugin}/xtumlmc_build.exe ${mcplugin}/xtumlmc_build
      mv -f ${mcplugin}/gen_erate.py ${mcplugin}/gen_erate.pyz

      mcplugin="./org.xtuml.bp.mc.cpp.source_${BP_VERSION}/mc3020/bin"
      tr -d '\r' < ${mcplugin}/xtumlmc_build > ${mcplugin}/xtumlmc_build.exe
      cp -f ${mcplugin}/xtumlmc_build.exe ${mcplugin}/xtumlmc_build
      mv -f ${mcplugin}/gen_erate.py ${mcplugin}/gen_erate.pyz

      mcplugin="./org.xtuml.bp.mc.java.source_${BP_VERSION}/mc3020/bin"
      tr -d '\r' < ${mcplugin}/xtumlmc_build > ${mcplugin}/xtumlmc_build.exe
      cp -f ${mcplugin}/xtumlmc_build.exe ${mcplugin}/xtumlmc_build
      mv -f ${mcplugin}/gen_erate.py ${mcplugin}/gen_erate.pyz

      mcplugin="./org.xtuml.bp.mc.systemc.source_${BP_VERSION}/mc3020/bin"
      tr -d '\r' < ${mcplugin}/xtumlmc_build > ${mcplugin}/xtumlmc_build.exe
      cp -f ${mcplugin}/xtumlmc_build.exe ${mcplugin}/xtumlmc_build
      mv -f ${mcplugin}/gen_erate.py ${mcplugin}/gen_erate.pyz

      mcplugin="./org.xtuml.bp.mc.vhdl.source_${BP_VERSION}/mc3020/bin"
      tr -d '\r' < ${mcplugin}/xtumlmc_build > ${mcplugin}/xtumlmc_build.exe
      cp -f ${mcplugin}/xtumlmc_build.exe ${mcplugin}/xtumlmc_build
      mv -f ${mcplugin}/gen_erate.py ${mcplugin}/gen_erate.pyz
else
      mcplugin="./org.xtuml.bp.mc.c.binary_${BP_VERSION}/mc3020/bin"
      mv -f ${mcplugin}/xtumlmc_build.exe.win ${mcplugin}/xtumlmc_build.exe

      mcplugin="./org.xtuml.bp.mc.c.source_${BP_VERSION}/mc3020/bin"
      mv -f ${mcplugin}/xtumlmc_build.exe.win ${mcplugin}/xtumlmc_build.exe

      mcplugin="./org.xtuml.bp.mc.cpp.source_${BP_VERSION}/mc3020/bin"
      mv -f ${mcplugin}/xtumlmc_build.exe.win ${mcplugin}/xtumlmc_build.exe

      mcplugin="./org.xtuml.bp.mc.java.source_${BP_VERSION}/mc3020/bin"
      mv -f ${mcplugin}/xtumlmc_build.exe.win ${mcplugin}/xtumlmc_build.exe

      mcplugin="./org.xtuml.bp.mc.systemc.source_${BP_VERSION}/mc3020/bin"
      mv -f ${mcplugin}/xtumlmc_build.exe.win ${mcplugin}/xtumlmc_build.exe

      mcplugin="./org.xtuml.bp.mc.vhdl.source_${BP_VERSION}/mc3020/bin"
      mv -f ${mcplugin}/xtumlmc_build.exe.win ${mcplugin}/xtumlmc_build.exe
fi
echo "INFO: Done."

echo "INFO: Moving new update site data to ${INSTALLER_DATA_DIR}."
cd "${INSTALLER_DATA_DIR}"
cp -r "${TEMP_DIR}/${PRODUCT_NAME}/eclipse/features" .
cp -r "${TEMP_DIR}/${PRODUCT_NAME}/eclipse/plugins" .
rm -rf "${TEMP_DIR}/${PRODUCT_NAME}"
echo "INFO: Done."

echo "INFO: Setting up docgen executable"
cd "${BP_BASE_DIR}/BridgePointDeliverables/tools/docgen"
cp -f "${INSTALLER_DATA_DIR}/plugins/${MCMC_EXE}" ${DOCGEN_EXE}
cp -f "./docgen.xsl" "./docbook/docbook-xsl-1.75.1/xhtml/"
echo "INFO: Done."

# Reorganize files into a structure that can be zipped up as the "installer"
echo "INFO: Zipping up the whole installation to ${OUTPUT_DIR}/${PRODUCT_NAME}_${PRODUCT_BRANCH}_${OS}.zip"
cd ${BP_BASE_DIR}
mv EclipseDeliverables BridgePoint
mv BridgePointDeliverables/* BridgePoint
rmdir BridgePointDeliverables
cp ../installer_extras/bp.ico BridgePoint

# There are various files in the installation that need to be executable.  
# Some are even part of the eclipse base (like antRun).  Here we just come in
# with a wide sweep and make everything executable. 
chmod -R a+x BridgePoint
zip -r "${OUTPUT_DIR}/${PRODUCT_NAME}_${PRODUCT_BRANCH}_${OS}.zip" BridgePoint  
cd "${OUTPUT_DIR}"
chmod -R g+w .
echo "INFO: Done."

# Make sure the output looks good
size=$(du -k ${PRODUCT_NAME}_${PRODUCT_BRANCH}_${OS}.zip | sed 's/\([0-9]*\)\(.*\)/\1/')
if [ "${size}" -lt "300000" ]; then
  echo "ERROR: Created installer file size is less than expected, exiting."
  exit 5
fi

if [ "${SCP_UPLOAD_FOLDER_SPEC}" != "" ]; then
  echo "INFO: Copying the new installer to the release website.  ( ${SCP_UPLOAD_FOLDER_SPEC} )"
  scp "${PRODUCT_NAME}_${PRODUCT_BRANCH}_${OS}.zip" "${SCP_UPLOAD_FOLDER_SPEC}"
fi
echo "INFO: ${PRODUCT_NAME} for ${OS} installer creation complete." 
