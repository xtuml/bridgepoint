#!/bin/bash
#=====================================================================
#
# Script to export build data from Subversion.  Takes the branch name
# to export from Subversion.  
#
#=====================================================================
#

# Variables
PRODUCT_VERSION="$1"
DATA_DIR="/cygdrive/c/"

# Check usage
if [ $# -lt 1 ]; then
  echo "Usage: $0 branch"
  exit 1
fi

# Set up the variable for the branch we'll export
BRANCH_NAME="trunk"
if [ "${PRODUCT_VERSION}" != "trunk" ]; then
  BRANCH_NAME="branches/${PRODUCT_VERSION}"
fi

# Remove existing dirs

# Export new dirs from svn
# ex: svn export http://wv-svn-01.wv.mentorg.com/svn/sle/xtuml/trunk/extra_files_for_build --username sle_build --password qkfJkv2=

# Make sure the export succeded

# unmap existing drives for eclipse bases? (maybe auto deleted when directory removed)
# ex: net use v: /Delete

# map drives for eclipse extensions into eclipse bases