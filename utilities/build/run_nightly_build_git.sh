# #!/bin/bash

# set +v 

#
#  This script runs the nightly build.
#
#  Since it is the starting point for the build chain, it must be manually put into 
#  place for the build server to run.
# 


export BUILD_ROOT="~/build/builds"
export BRANCH="master"
export GIT_REPO_ROOT="~/git/xtuml"
export BUILD_TOOLS="~/build/utilities/bp_build_tools"
export PT_HOME="${BUILD_TOOLS}/bridgepoint"
export PT_HOME_DRIVE=
export XTUMLGEN_HOME="${BUILD_TOOLS}/bridgepoint"
mkdir "${BUILD_TOOLS}"
echo -e "BUILD_ROOT=${BUILD_ROOT}
echo -e "BRANCH=${BRANCH}
echo -e "GIT_REPO_ROOT=${GIT_REPO_ROOT}
echo -e "PT_HOME=${PT_HOME}
echo -e "PT_HOME_DRIVE=${PT_HOME_DRIVE}
echo -e "XTUMLGEN_HOME=${XTUMLGEN_HOME}

cd "${BUILD_ROOT}"
pushd .

echo -e "Initializing git repositories..."
dos2unix -q init_git_repositories.sh
bash init_git_repositories.sh "${BRANCH}" "${GIT_REPO_ROOT}" "yes" > cfg_output.log
echo -e "Done."

echo -e "Initializing the eclipse bases and build tools from SVN..."
cp -f ${GIT_REPO_ROOT}/internal/utilities/build/init_svn_tools.sh .
dos2unix -q init_svn_tools.sh
bash init_svn_tools.sh "${BRANCH}" "nonrelease" >> cfg_output.log
echo -e "Done."

echo -e "Setting permissions on tool directories..."
chmod -R a+rw ${BUILD_TOOLS} 
echo -e "Done."

echo -e "Configuring build process..."
cp -f ${GIT_REPO_ROOT}/internal/utilities/build/configure_build_process.sh .
dos2unix -q configure_build_process.sh
bash configure_build_process.sh ${BRANCH} ${GIT_REPO_ROOT} nonrelease >> cfg_output.log
echo -e "Done."

echo -e "Processing the build..."
cd  "${BRANCH}"
bash process_build.sh ${BRANCH} ${GIT_REPO_ROOT} nonrelease > build_output.log 
echo -e "Done."

# Clean up build files
popd
mv configure_build_process.sh ${BRANCH}
mv init_svn_tools.sh ${BRANCH}
mv ${BRANCH}\build_output.log ${BRANCH}\log

cd ${BUILD_ROOT}

echo -e "End of run_nightly_build_git.bat"
