#!/bin/bash
#=====================================================================
#
# File:      process_build.sh
#
#(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================
#
#
# Variables

product_version="$1"
git_repo_root="$2"
release_type="$3"

if [ $# -lt 3 ]; then
  echo "Usage: $0 branch git_repository_root release_type"
  exit 1
fi

BUILD_ADMIN="pt_development@mentor.com"
MAIL_CMD="/usr/sbin/ssmtp"
MAIL_TEMP="mailtemp"
base_dir=`pwd`
build_dir="${base_dir}/${branch}"
ROOTDIR=`cygpath -m ${build_dir}`
timestamp=`date +%Y%m%d`
build_tgt="${product_version}-${timestamp}"
release_base="/arch1/products/tiger/releases"
release_drop="${release_base}/${product_version}"
release_drop_dt="${release_base}/${build_tgt}"
download_url="http://tucson.wv.mentorg.com/tiger_releases/${product_version}"
log_dir="${build_dir}/log"
error_file="${log_dir}/.errors"
diff_file="${log_dir}/diff.log"
server="tucson.wv.mentorg.com"
rsh="ssh"
nb_tag=""

version_length=${#product_version}

if [ ${version_length} -lt 21 ]; then
  ./create_bp_release.sh ${product_version} ${git_repo_root} ${release_type}

  if [ "$?" = "0" ]; then
    ${rsh} ${server} "(cd '${release_base}'; if [ ! -x '${release_drop_dt}' ]; then mkdir '${release_drop_dt}'; fi; cp -f '${release_drop}'/BridgePoint_extension_'${product_version}'.zip '${release_drop_dt}'/BridgePoint_extension_'${product_version}'.zip ; chown -R build:staff '${release_drop_dt}')"
	echo -e "Creating dated backup of the build"
  else
    echo -e "create_bp_release.sh returned with a non-zero value ($?)"
  fi
  
  # Prune similar releases that are five days old.
  ${rsh} ${server} "(cd '${release_base}'; find -name '${product_version}-*' -mtime 5 -exec rm -rf {} \; ;)"
else
  echo -e "The version name ${product_version} is too long for the build server to handle." > ${error_file}
fi

# Build email report

echo -e "From: Nightly Build System <build@projtech.com>" > ${MAIL_TEMP}
if [ -s ${error_file} ]; then
  echo -e "Subject: ERROR - Nightly build report for ${build_tgt}"  >> ${MAIL_TEMP}
else
  echo -e "Subject: Nightly build report for ${build_tgt}"  >> ${MAIL_TEMP}
fi
echo -e "To: ${BUILD_ADMIN}" >> ${MAIL_TEMP}
echo -e "Nightly build report for: ${build_tgt}" >> ${MAIL_TEMP}
echo -e "The files that were used for the nightly build, and the logs of each build are located at: ${ROOTDIR} on `hostname`" >> ${MAIL_TEMP}

# Search for errors in the logs
if [ -s ${error_file} ]; then
  echo -e "The following was written to the error log:" >> ${MAIL_TEMP}
  echo -e "---------------" >> ${MAIL_TEMP}
  cat ${error_file} >> ${MAIL_TEMP}
  echo -e "---------------\n" >> ${MAIL_TEMP}
else
  echo -e "The release can be downloaded at: ${download_url}" >> ${MAIL_TEMP}
  
  if [ -s ${diff_file} ] && [ "${product_version}" = "HEAD" ]; then
    nb_tag="`date +N%F`"
    echo -e "The code base is tagged with ${nb_tag}" >> ${MAIL_TEMP}
  fi

  echo -e "\nCHANGELOG:" >> ${MAIL_TEMP}
  echo -e "---------------" >> ${MAIL_TEMP}
  cat ${diff_file} >> ${MAIL_TEMP}
  
  ./build_installer_bp.sh ${product_version}
  ./build_installer_bp_linux.sh ${product_version}
fi
cat ${MAIL_TEMP} | ${MAIL_CMD} ${BUILD_ADMIN}

rm -rf ${MAIL_TEMP}

# TODO
#if [ "${nb_tag}" != "" ]; then
#  ./tag_bp_nb.sh ${nb_tag}
#fi

exit 0
