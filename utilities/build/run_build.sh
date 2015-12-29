#!/bin/bash

# set +v 

#
#  This script runs the nightly build.
#
#  The script requires at least two parameters:
#      
#     BridgePoint_Home_Directory - This is the location of a BridgePoint installation
#     Build_Root - This holds the build server location that is the 
#                   root for the build
#
#  Optional:
#     Branch_Name - This is an optional parameter that allows you to configure
#                   the branch to build
#     Fork_Name - This is an optional parameter that allows you to specify the
#                 fork to clone for the build.
#     upload location - This optional argument, if present, will upload the build to this given location.  example: user@myserver.com:/myfolder
#     package_only - This optional argument, if present and set to "yes" cause the build script to only package and notify
#     clean - This optional argument, it defaults to yes.  If yes, a clean build if performed.
#               
#  Since it is the starting point for the build chain, it must be manually put 
#  into place for the build server to run. The variable BUILD_MOUNT holds the 
#  build server location that is the root for the build.
#  
#  Build Server Requirements:
#  1) run_build.sh, and init_git_repositories.sh must be present in ${BUILD_ROOT}
#  2) git, ant, and jar must be installed on the build server
# 
#  The build is performed under ${Build_Root}.  The resulting update site and 
#  zipfile installers are found under ${Build_Root}/releases
# 

#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------

#
# Distribute the build and notify watchers that a build is complete.
# If there are no errors that we also call the installer build script to 
# allow the installers to be built.
#
function distribute_and_notify {
	echo -e "Entering run_build.sh::distribute_and_notify" 
	MAIL_TEMP="mailtemp"

	# Build email report
	echo -e "From: Nightly Build System <issues@onefact.net>" > ${MAIL_TEMP}
	
	if [ -s ${ERROR_FILE} ]; then
	  echo -e "Subject: Error! Nightly build report for ${BUILD_TARGET} [#654]"  >> ${MAIL_TEMP}
	else
	  echo -e "Subject: Nightly build report for ${BUILD_TARGET} [#654]"  >> ${MAIL_TEMP}
	fi

	# Get the changes from the last 2 days
	cd ${GIT_BP}
	git log --name-status --since="2 days ago" >> ${DIFF_FILE}
	cd "${BUILD_DIR}"
	
	END=$(date +%s)
	DIFF=$(( $END - $START ))
	BUILD_ADMIN="build@onefact.net,issues@onefact.net"
	SERVER_IP=$(dig +short myip.opendns.com @resolver1.opendns.com)

	echo -e "To: ${BUILD_ADMIN}" >> ${MAIL_TEMP}
	# A blank line needs to come after the "To" field or lines get lost
	echo -e "" >> ${MAIL_TEMP}
	echo -e "Build report for branch name: ${BRANCH}" >> ${MAIL_TEMP}
	echo -e "This build took: $((DIFF/60)) minutes" >> ${MAIL_TEMP}
	echo -e "The workspace for this build is located at: ${BUILD_DIR} on ${SERVER_IP}" >> ${MAIL_TEMP}
	echo -e "The complete logs are under ${LOG_DIR} on ${SERVER_IP}" >> ${MAIL_TEMP}
	echo -e "" >> ${MAIL_TEMP}

	SCP_CMD="scp youruser@${SERVER_IP}:${RESULT_FOLDER}/*.zip"
	DOWNLOAD_URL="http://support.onefact.net/redmine/releases"

	if [ -s ${ERROR_FILE} ]; then
	  echo -e "ERROR!: The following errors are present in the error log:" >> ${MAIL_TEMP}
	  echo -e "----------------------------------------------------------" >> ${MAIL_TEMP}
	  cat ${ERROR_FILE} >> ${MAIL_TEMP}
	  echo -e "----------------------------------------------------------" >> ${MAIL_TEMP}
	  echo -e "" >> ${MAIL_TEMP}
	fi

	if [ "${UPLOAD_SPEC}" != "" ]; then
      scp "${BUILD_LOG}" "${UPLOAD_SPEC}"
      scp "${ERROR_FILE}" "${UPLOAD_SPEC}"
      scp "${DIFF_FILE}" "${UPLOAD_SPEC}"
      scp "${ECLIPSE_LOG}" "${UPLOAD_SPEC}"
      echo -e "Build log: ${DOWNLOAD_URL}/${BL}" >> ${MAIL_TEMP}
      echo -e "Error log: ${DOWNLOAD_URL}/${EF}" >> ${MAIL_TEMP}
      echo -e "Eclipse log: ${DOWNLOAD_URL}/${EL}" >> ${MAIL_TEMP}
      echo -e "GIT change log for the last 2 days for this branch: ${DOWNLOAD_URL}/${DF}" >> ${MAIL_TEMP}
      echo -e "" >> ${MAIL_TEMP}

      echo -e "Downloads:" >> ${MAIL_TEMP}
      echo -e "----------" >> ${MAIL_TEMP}
      echo -e "You can copy these builds directly from the build server: ${SCP_CMD}" >> ${MAIL_TEMP}
      echo -e "The Linux release can be downloaded at: ${DOWNLOAD_URL}/BridgePoint_${BRANCH}_linux.zip" >> ${MAIL_TEMP}
      echo -e "The Windows release can be downloaded at: ${DOWNLOAD_URL}/BridgePoint_${BRANCH}_windows.zip" >> ${MAIL_TEMP}
      echo -e " " >> ${MAIL_TEMP}
	
	  cat ${MAIL_TEMP} | ${MAIL_CMD} "${BUILD_ADMIN}"
	else
	  echo -e "${MAIL_TEMP}"
	fi
	echo -e "Exiting run_build.sh::distribute_and_notify"
}

#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------

# Verify parameters
if [ "$#" -lt 2 ]; then
  echo "This script requires two parameters.  The other parameters are optional.  See below for usage."
  echo
  echo "run_build.sh BridgePoint_Home_Directory Build_Root <Branch_Name> <Fork_Name> <upload location> <package only> <clean>"
  echo
  echo "See the script header for more detail."
  exit 1
fi 

START=$(date +%s)

BPHOMEDIR="$1"
if [ "$2" = "/" ]; then
  BUILD_MOUNT="/build"
else
  BUILD_MOUNT="$2/build"
fi

# Do not modify these variables:
BUILD_ROOT="${BUILD_MOUNT}/work"
GIT_REPO_ROOT="${BUILD_MOUNT}/git/xtuml"
GIT_BP="${GIT_REPO_ROOT}/bridgepoint"

# if no arguments are present default to master on xtuml
if [ "$3" != "" ]; then
  BRANCH="$3"
else
  BRANCH="master"
fi
if [ "$4" != "" ]; then
  FORK="$4"
else
  FORK="xtuml"
fi
if [ "$5" != "" ]; then
  UPLOAD_SPEC="$5"
else
  UPLOAD_SPEC=""
fi
if [ "$6" != "" ]; then
  package_only="$6"
else
  package_only=""
fi
if [ "$7" != "" ]; then
  clean="$7"
else
  clean="yes"
fi

#-------------------------------------------------------------------------------
# Make sure github credentials are available in the environment
#-------------------------------------------------------------------------------
# Note:  No longer true since these are public repos, but leaving here since we
#        may have to reinstate this if that is no long true.  JLR 9/18/2015
#-------------------------------------------------------------------------------
#if [ "${GITUSER}" = "" ] || [ "${GITPASS}" = "" ]; then
#  echo "The build requires the environment variables GITUSER and GITPASS to be"
#  echo "set for checking files out of github.  GITPASS may be a real password "
#  echo "password or, preferably, a github API token for the specified GITUSER."
#  exit 1
#fi

# echo out variables
echo "BUILD_MOUNT=${BUILD_MOUNT}"
echo "BUILD_ROOT=${BUILD_ROOT}"
echo "BRANCH=${BRANCH}"
echo "FORK=${FORK}"
echo "GIT_REPO_ROOT=${GIT_REPO_ROOT}"
echo "BPHOMEDIR=${BPHOMEDIR}"

# this flag is constant and could potentially be removed, but it is 
# being left in case we do want to have the build be different then other 
# releases.
BUILD_TYPE="nonrelease"

# This variable is used to decided if we want to look in head for files not
# found in the specified branch.  Currently it is always set  to yes.  It
# is being left in the script to allow this to be modified in the future if
# desired
ALLOW_FALLBACK="yes"

BUILD_DIR="${BUILD_ROOT}/${BRANCH}"
WORKSPACE="${BUILD_DIR}"
LOG_DIR="${BUILD_DIR}/log"
EF="errors.txt"
DF="diff.txt"
BL="build.txt"
EL=".log"
ERROR_FILE="${LOG_DIR}/${EF}"
DIFF_FILE="${LOG_DIR}/${DF}"
BUILD_LOG="${LOG_DIR}/${BL}"
ECLIPSE_LOG="${BUILD_DIR}/.metadata/${EL}"
MAIL_CMD="/usr/sbin/ssmtp"
TIMESTAMP=`date +%Y%m%d%H%M`
RELEASE_BASE="${BUILD_MOUNT}/releases"
BUILD_TARGET="${BRANCH}-${TIMESTAMP}"
RESULT_FOLDER="${RELEASE_BASE}/${BUILD_TARGET}"
RESULT_FOLDER_EXTENSION="${RELEASE_BASE}/${BUILD_TARGET}/BridgePoint_${BRANCH}"
STAGING_AREA=${BUILD_MOUNT}/staging

if [ "${package_only}" != "yes" ]; then
  # assure that we are starting with a clean build folder.
  rm -rf ${BUILD_DIR}
fi

mkdir -p "${RESULT_FOLDER}"
mkdir -p "${RESULT_FOLDER_EXTENSION}"
mkdir -p "${STAGING_AREA}"
mkdir -p "${BUILD_DIR}"
mkdir -p "${LOG_DIR}"
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
RETVAL=0
if [ "${package_only}" != "yes" ]; then
  echo -e "Getting files from github, this could take a while."
  bash "${BUILD_ROOT}/init_git_repositories.sh" ${BRANCH} ${GIT_REPO_ROOT} ${ALLOW_FALLBACK} ${FORK} >> ${BUILD_LOG} 2>&1
  echo -e "Done getting files from github."

  # Can do the copy and dos2unix translation in one step.
  echo -e "Configuring files for the build process."
  tr -d '\r' < ${GIT_BP}/utilities/build/configure_build_process.sh > configure_build_process.sh
  chmod a+x configure_build_process.sh

  bash configure_build_process.sh ${BUILD_DIR} ${GIT_REPO_ROOT} ${ERROR_FILE} ${STAGING_AREA} >> ${BUILD_LOG}
  cd  "${BUILD_DIR}"
  echo -e "Done configuring files for the build process."

  echo -e "Configuring external dependencies."
  bash configure_external_dependencies.sh ${GIT_REPO_ROOT} > ${LOG_DIR}/configure_externals.log 2>&1
  cd  "${BUILD_DIR}"
  echo -e "Done configuring external dependencies."

  echo -e "Building BridgePoint.  This will take a long time."
  cd $BUILD_DIR
  tr -d '\r' < ${GIT_BP}/utilities/build/headless_build.sh > headless_build.sh
  chmod a+x headless_build.sh

  ./headless_build.sh "${BRANCH}" "${BPHOMEDIR}" "${WORKSPACE}" "${GIT_BP}" "${clean}"
  RETVAL=$?
  echo -e "Done building."

  # TODO - we'll re-enable this check when headless_build stops reporting errors
  #if [ ! -s ${ERROR_FILE} ]; then
    #exit 1
  #fi
  # TODO - we'll re-enable this check when headless_build stops reporting errors
  #if [ $RETVAL -eq 0 ]; then
    #exit 1
  #fi
fi

# This packages the build
echo -e "Packaging BridgePoint into a full eclipse environment."
cd  "${BUILD_DIR}"
bash create_bp_release.sh "${BUILD_DIR}" "${BRANCH}" "${GIT_BP}" "${LOG_DIR}" "${TIMESTAMP}" "${RESULT_FOLDER_EXTENSION}" >> ${BUILD_LOG}

bp_release_version=$(awk -F": " '{if (/[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${GIT_BP}/src/org.xtuml.bp.pkg/META-INF/MANIFEST.MF)
bp_release_version="$(echo ${bp_release_version} | tr -d '\r')"

bash build_installer_bp.sh ${BRANCH} ${STAGING_AREA} ${RESULT_FOLDER} windows ${bp_release_version} "${UPLOAD_SPEC}" >> ${BUILD_LOG}
cd  "${BUILD_DIR}"
  
bash build_installer_bp.sh ${BRANCH} ${STAGING_AREA} ${RESULT_FOLDER} linux ${bp_release_version} "${UPLOAD_SPEC}" >> ${BUILD_LOG}
if [ $? != "0" ]; then
  echo -e "Error! The build_installer_bp.sh script failed." >> ${ERROR_FILE}
fi

# Change git errors logged when we try to check out an invalid branch into warnings
sed -e 's;error: pathspec;warning: pathspec;' < ${BUILD_LOG} > ${BUILD_LOG}_new
mv ${BUILD_LOG}_new ${BUILD_LOG}

# Quick check to make sure core.jar file size is "big enough"
size=$( wc -c "${GIT_BP}/src/org.xtuml.bp.core/bin/core.jar" | awk '{print $1}' )
if [ $size -lt 9000000 ]; then
  echo -e "ERROR: The build did not succeed.  The core.jar file is too small." >> ${BUILD_LOG}
fi

grep -c -i -w "Error" ${BUILD_LOG}
error_count=$?
if [ ${error_count} -ne 1 ]; then
  echo -e "Errors found in the build output log. Check ${BUILD_LOG}." >> ${ERROR_FILE}
fi

cd  "${BUILD_DIR}"
distribute_and_notify  

date
echo -e "End of run_build.sh"
