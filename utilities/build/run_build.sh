#!/bin/bash

# set +v 

#
#  This script runs the nightly build.
#
#  Since it is the starting point for the build chain, it must be manually put 
#  into place for the build server to run. The variable BUILD_MOUNT holds the 
#  build server location that is the root for the build.
#  
#  Build Server Requirements:
#  1) run_build.sh, and init_git_repositories.sh must be present in ${BUILD_ROOT}
#  2) BridgePoint must be installed to the folder pointed to by the 
#     ${ECLIPSE_HOME} variable defined below.  The BridgePoint version must be 
#     in this ECLIPSE_HOME must be specified.
#  3) git and ant must be installed on the build server
# 
#  The build is performed under ${BUILD_ROOT}.  The result of the build:
#  1) plugins
#     The resulting plugins are found under ${BUILD_ROOT}
# 

# User defined variables:
export BUILD_MOUNT="${HOME}/build"
export ECLIPSE_HOME="${HOME}/MentorGraphics/BridgePoint/eclipse"
export BP_VERSION="4.2.0"

# Do not modify these variables:
export BUILD_ROOT="${BUILD_MOUNT}/work"
export GIT_REPO_ROOT="${BUILD_MOUNT}/git/xtuml"
export GIT_BP="${GIT_REPO_ROOT}/bridgepoint"
export BUILD_TOOLS="${BUILD_MOUNT}/utilities/bp_build_tools"
# if no arguments are present default to master
export BRANCH="master"
if [ $# -eq 1 ]; then
  export BRANCH="$1"
fi

# echo out variables
echo "BUILD_MOUNT=${BUILD_MOUNT}"
echo "BUILD_ROOT=${BUILD_ROOT}"
echo "BRANCH=${BRANCH}"
echo "GIT_REPO_ROOT=${GIT_REPO_ROOT}"

# this flag is constant and could potentially be removed, but it is 
# being left in case we do want to have the build be different then other 
# releases.
export BUILD_TYPE="nonrelease"

# This variable is used to decided if we want to look in head for files not
# found in the specified branch.  Currently it is always set  to yes.  It
# is being left in the script to allow this to be modified in the future if
# desired
export ALLOW_FALLBACK="yes"

export BUILD_DIR="${BUILD_ROOT}/${BRANCH}"
export LOG_DIR="${BUILD_DIR}/log"
export ERROR_FILE="${LOG_DIR}/errors.log"
export DIFF_FILE="${LOG_DIR}/diff.log"
export BUILD_LOG=""${LOG_DIR}/build.log""
export BUILD_ADMIN="build@onefact.net"
export MAIL_CMD="/usr/sbin/ssmtp"
export MAIL_TEMP="mailtemp"
export RELEASE_PKG="org.xtuml.bp.bld.pkg-feature"
export SHELLUSER="${USER}"
mkdir -p "${LOG_DIR}"

export TIMESTAMP=`date +%Y%m%d%H%M`

#
# This is the location, on the build server, where this build is found
#
export RELEASE_BASE="${BUILD_MOUNT}/releases"
export BUILD_TARGET="${BRANCH}-${TIMESTAMP}"
export RESULT_FOLDER="${RELEASE_BASE}/${BUILD_TARGET}"
mkdir -p "${RESULT_FOLDER}"

#
# This is where the extension result goes
#
export RESULT_FOLDER_EXTENSION="${RELEASE_BASE}/${BUILD_TARGET}-extension"
mkdir -p "${RESULT_FOLDER_EXTENSION}"

# 
# This section defines the external location for the build (the place where
# customers go to get this release). 
# Note that items in the following section will eventually need to be github 
# pages (I think) for now the release is not being moved off of the build server.
#
export RELEASE_DROP="${RELEASE_BASE}/${BRANCH}"
mkdir -p "${RELEASE_DROP}"
export DOWNLOAD_URL="http://xtuml.github.io/bridgepoint/"
export DISTRIBUTION_SERVER=""

# We do not currently use this, but when we were using cvs we tagged nightly 
# builds, and this was the format of the tag
export BUILD_TAG="`date +N%F`"

# assure that we are starting with a clean build folder.
rm -rf ${BUILD_DIR}

mkdir -p "${BUILD_DIR}"
mkdir -p "${LOG_DIR}"
mkdir -p "${BUILD_TOOLS}"
mkdir -p "${GIT_REPO_ROOT}"
mkdir -p "${BUILD_ROOT}"

# We will perform all work in the build's branch folder. 
cd  "${BUILD_DIR}"

# Do the dos2unix conversion using translate.
tr -d '\r' < "${BUILD_ROOT}/init_git_repositories.sh" > "${BUILD_ROOT}/init_git_repositories.tmp"
cmp -s "${BUILD_ROOT}/init_git_repositories.sh" "${BUILD_ROOT}/init_git_repositories.tmp" >/dev/null 2>&1
if [ $? -eq 1 ]; then
  echo -e "Putting new init_git_repositories.sh into place"
  mv "${BUILD_ROOT}/init_git_repositories.tmp" "${BUILD_ROOT}/init_git_repositories.sh"
  chmod a+x "${BUILD_ROOT}/init_git_repositories.sh"
else
  rm -f "${BUILD_ROOT}/init_git_repositories.tmp"
fi
bash "${BUILD_ROOT}/init_git_repositories.sh" >> ${BUILD_LOG}

echo -e "Setting permissions on tool directories..."
chmod -R a+rw ${BUILD_TOOLS} 

# Can do the copy and dos2unix translation in one step.
tr -d '\r' < ${GIT_REPO_ROOT}/bridgepoint/utilities/build/configure_build_process.sh > configure_build_process.sh

bash configure_build_process.sh >> ${BUILD_LOG}

bash create_bp_release.sh  >> ${BUILD_LOG}

distribute_and_notify $? >> ${BUILD_LOG}

# Clean up build files
cd ${BUILD_ROOT}
mv configure_build_process.sh ${BRANCH}

echo -e "End of run_build.sh"

#
# distribute the build and notify watchers that a build is complete
# if there are no errors that we also call the installer build script to 
# allow the installers to be built
#
function distribute_and_notify {
	echo -e "Entering run_build.sh::distribute_and_notify"
	if [ "$1" = "0" ]; then
	  # TODO: This was the prior implementation.  It needs to be reworked (RSH)
	  # ${RSH} ${DISTRIBUTION_SERVER} "(cd '${RELEASE_BASE}'; if [ ! -x '${RESULT_FOLDER}' ]; then mkdir '${RESULT_FOLDER}'; fi; cp -f '${RELEASE_DROP}'/BridgePoint_extension_'${BRANCH}'.zip '${BUILD_RESULT_FOLDER}'/BridgePoint_extension_'${BRANCH}'.zip ; )"
	  echo -e "Creating dated backup of the build"
	else
	  echo -e "create_bp_release.sh returned with a non-zero value ($?)"
	fi
	  
	# Prune similar releases that are five days old.
	# TODO: This was the prior implementation.  It needs to be reworked
	# ${RSH} ${DISTRIBUTION_SERVER} "(cd '${RELEASE_BASE}'; find -name '${BRANCH}-*' -mtime 5 -exec rm -rf {} \; ;)"
	
	# Build email report
	
	echo -e "From: Nightly Build System <issues@onefact.net>" > ${MAIL_TEMP}
	if [ -s ${ERROR_FILE} ]; then
	  echo -e "Subject: ERROR - Nightly build report for ${BUILD_TARGET} [#654]"  >> ${MAIL_TEMP}
	else
	  echo -e "Subject: Nightly build report for ${BUILD_TARGET} [#654]"  >> ${MAIL_TEMP}
	fi
	echo -e "To: ${BUILD_ADMIN}" >> ${MAIL_TEMP}
	echo -e "Nightly build report for: ${BUILD_TARGET}" >> ${MAIL_TEMP}
	echo -e "The files that were used for the nightly build, and the logs of each build are located at: ${BUILD_DIR} on `hostname`" >> ${MAIL_TEMP}
	
	# Search for errors in the logs
	if [ -s ${ERROR_FILE} ]; then
	  echo -e "The following was written to the error log:" >> ${MAIL_TEMP}
	  echo -e "---------------" >> ${MAIL_TEMP}
	  cat ${ERROR_FILE} >> ${MAIL_TEMP}
	  echo -e "---------------\n" >> ${MAIL_TEMP}
	else
	  echo -e "The release can be downloaded at: ${DOWNLOAD_URL}" >> ${MAIL_TEMP}
	  
	  echo -e "\nCHANGELOG:" >> ${MAIL_TEMP}
	  echo -e "---------------" >> ${MAIL_TEMP}
	  cat ${DIFF_FILE} >> ${MAIL_TEMP}
	  
	  ./build_installer_bp.sh ${BRANCH}
	  ./build_installer_bp_linux.sh ${BRANCH}
	fi
	cat ${MAIL_TEMP} | ${MAIL_CMD} ${BUILD_ADMIN}
	
	rm -rf ${MAIL_TEMP}
	echo -e "Exiting run_build.sh::distribute_and_notify"
}
