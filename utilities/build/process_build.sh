#!/bin/bash
#
# Variables

product_version="$1"
git_repo_root="$2"
release_type="$3"

if [ $# -lt 3 ]; then
  echo "Usage: $0 branch git_repository_root release_type"
  exit 1
fi

timestamp=`date +%Y%m%d`
build_tgt="${product_version}-${timestamp}"
release_drop_dt="${RELEASE_BASE}/${build_tgt}"
mkdir -p "${release_drop_dt}"
version_length=${#product_version}
nb_tag=""

./create_bp_release.sh ${product_version} ${git_repo_root} ${release_type}

if [ "$?" = "0" ]; then
  ${RSH} ${DISTRIBUTION_SERVER} "(cd '${RELEASE_BASE}'; if [ ! -x '${release_drop_dt}' ]; then mkdir '${release_drop_dt}'; fi; cp -f '${RELEASE_DROP}'/BridgePoint_extension_'${product_version}'.zip '${release_drop_dt}'/BridgePoint_extension_'${product_version}'.zip ; )"
  echo -e "Creating dated backup of the build"
else
  echo -e "create_bp_release.sh returned with a non-zero value ($?)"
fi
  
# Prune similar releases that are five days old.
${RSH} ${DISTRIBUTION_SERVER} "(cd '${RELEASE_BASE}'; find -name '${product_version}-*' -mtime 5 -exec rm -rf {} \; ;)"

# Build email report

echo -e "From: Nightly Build System <build@projtech.com>" > ${MAIL_TEMP}
if [ -s ${ERROR_FILE} ]; then
  echo -e "Subject: ERROR - Nightly build report for ${build_tgt}"  >> ${MAIL_TEMP}
else
  echo -e "Subject: Nightly build report for ${build_tgt}"  >> ${MAIL_TEMP}
fi
echo -e "To: ${BUILD_ADMIN}" >> ${MAIL_TEMP}
echo -e "Nightly build report for: ${build_tgt}" >> ${MAIL_TEMP}
echo -e "The files that were used for the nightly build, and the logs of each build are located at: ${BUILD_DIR} on `hostname`" >> ${MAIL_TEMP}

# Search for errors in the logs
if [ -s ${ERROR_FILE} ]; then
  echo -e "The following was written to the error log:" >> ${MAIL_TEMP}
  echo -e "---------------" >> ${MAIL_TEMP}
  cat ${ERROR_FILE} >> ${MAIL_TEMP}
  echo -e "---------------\n" >> ${MAIL_TEMP}
else
  echo -e "The release can be downloaded at: ${DOWNLOAD_URL}" >> ${MAIL_TEMP}
  
  if [ -s ${DIFF_FILE} ] && [ "${product_version}" = "HEAD" ]; then
    nb_tag="`date +N%F`"
    echo -e "The code base is tagged with ${nb_tag}" >> ${MAIL_TEMP}
  fi

  echo -e "\nCHANGELOG:" >> ${MAIL_TEMP}
  echo -e "---------------" >> ${MAIL_TEMP}
  cat ${DIFF_FILE} >> ${MAIL_TEMP}
  
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
