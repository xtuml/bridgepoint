#!/bin/bash
#=====================================================================
#
# Script to export build data from Subversion.  Takes the branch name
# to export from Subversion.  
#
#=====================================================================
#
#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------
error ()
{
    echo Error! $1
    exit 1
}

get_svn_project ()
{
    project="$1"
    export_trunk="false"
    if [ "${branch}" = "master" ]; then
        export_trunk="true"
    else
        # check out the branch
        echo "  Attempting to check out ${project} from the branch ${branch} in SVN."
        svn export http://wv-svn-01.wv.mentorg.com/svn/sle/xtuml/branches/${branch}/${project} --username sle_build --password qkfJkv2= --force
        
        # if export failed and build type != nonrelease, then error
        if [ ! -x ${project} ] && [ "${build_type}" != "nonrelease" ]; then
            error "SVN branch ${branch} does not exist and is required for this build. Exiting.\n"
        fi
        
        # if export failed and build type = nonrelease, then checkout master
        if [ ! -x ${project} ] && [ "${build_type}" = "nonrelease" ]; then
            echo "  The project ${project} does not exist in branch ${branch}.  Will try trunk."
            export_trunk="true"
        fi
        
    fi
    
    if [ "${export_trunk}" = "true" ]; then
        echo "  Attempting to check out ${project} from trunk in SVN."
        svn export http://wv-svn-01.wv.mentorg.com/svn/sle/xtuml/trunk/${project} --username sle_build --password qkfJkv2= --force
    fi
    
    if [ ! -x ${project} ]; then
        error "SVN checkout of ${project} failed. Exiting. \n"
    fi
}

#-------------------------------------------------------------------------------
#  Main
#-------------------------------------------------------------------------------

# Variables
DATA_DIR="/cygdrive/c/"
UTILS_DIR="utilities"
ECLIPSE_VER="3.7"
BP_WIN_BASE="BridgePoint_e${ECLIPSE_VER}"
BP_LINUX_BASE="BridgePoint_for_Linux_e${ECLIPSE_VER}"
MIMIC_TOOL="MIMIC"
ANT_TOOL="apache-ant-1.6.1"
ECLIPSE_TOOL="eclipse3.7.2"
BP_TOOL="bp_build_tools"
GENERATOR_MGLS="mgls"
branch="$1"
build_type="$2"


# Check usage
if [ $# -lt 2 ]; then
  echo "Usage: $0 branch build-type"
  echo "  Example: $0 master nonrelease"
  exit 1
fi

# Remove existing dirs
echo -e "Removing existing data for installers."
cd ${DATA_DIR}
rm -rf ${BP_WIN_BASE}
if [ -x ${BP_WIN_BASE} ]; then
  error "Removal of ${BP_WIN_BASE} failed. Exiting. \n"
fi
rm -rf ${BP_LINUX_BASE}
if [ -x ${BP_LINUX_BASE} ]; then
  error "Removal of ${BP_LINUX_BASE} failed. Exiting. \n"
fi
rm -rf ${MIMIC_TOOL}
if [ -x ${MIMIC_TOOL} ]; then
  error "Removal of ${MIMIC_TOOL} failed. Exiting. \n"
fi
rm -rf ${ECLIPSE_TOOL}
if [ -x ${ECLIPSE_TOOL} ]; then
  error "Removal of ${ECLIPSE_TOOL} failed. Exiting. \n"
fi
cd ${UTILS_DIR}
rm -rf ${BP_TOOL}
if [ -x ${BP_TOOL} ]; then
  error "Removal of ${BP_TOOL} failed. Exiting. \n"
fi
rm -rf ${GENERATOR_MGLS}
if [ -x ${GENERATOR_MGLS} ]; then
  error "Removal of ${GENERATOR_MGLS} failed. Exiting. \n"
fi
echo -e "Done."

# Export new dirs from svn
echo -e "Exporting new bases from SVN."
cd ${DATA_DIR}
get_svn_project ${BP_WIN_BASE}
get_svn_project ${BP_LINUX_BASE}
get_svn_project ${MIMIC_TOOL}
get_svn_project ${ECLIPSE_TOOL}
#  The source code generate.xml files expect the ant tool to be in a specific
#  folder under eclipse.  We could modify these files, but for now it is easier
#  to just check out the ant tool to the current expected location.
cd ${ECLIPSE_TOOL}
mkdir ant
cd ant
get_svn_project ${ANT_TOOL}
cd ${DATA_DIR}
if [ ! -x ${UTILS_DIR} ]; then
    mkdir ${UTILS_DIR}
fi
cd ${UTILS_DIR}
get_svn_project ${BP_TOOL}
get_svn_project ${GENERATOR_MGLS}
cd ${DATA_DIR}
echo -e "Done."

# unmap existing drives and shares for eclipse bases
echo -e "Removing existing shares for eclipse bases."
net use U: /Delete
net use V: /Delete
net share ${BP_WIN_BASE} /Delete
net share ${BP_LINUX_BASE} /Delete
echo -e "Done."

# map drives for eclipse extensions into eclipse bases
echo -e "Setting up new shared drives for eclipse bases."
net share ${BP_WIN_BASE}=C:\\${BP_WIN_BASE} /grant:everyone,FULL
net share ${BP_LINUX_BASE}=C:\\${BP_LINUX_BASE} /grant:everyone,FULL
net use U: \\\\localhost\\${BP_LINUX_BASE}\\BridgePointDeliverables\\eclipse_extensions
net use V: \\\\localhost\\${BP_WIN_BASE}\\BridgePointDeliverables\\eclipse_extensions
echo -e "Done."
