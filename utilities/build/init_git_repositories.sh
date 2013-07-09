#!/bin/bash
#=====================================================================
#
# File:      init_git_repositories.sh
#
#(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================
#
#
#  init_git_repositories.sh takes the following arguments
#
#   $1 - product version, actually this is any branch/tag found in git
#   $2 - git repository root 
#   $3 - allow fallback to master is specified branch does not 
#            exist (yes or no)
#
#  Since this starting point for pulling the rest of the data from revision
#  control, it must be manually put into place for the build server to run.
#

#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------
usage () {
  echo -e "\nThis script creates or updates local copies of the git repositories.\n"
  echo -e "\nUsage: $0 <branch/tag> <git repo root> [fallback]\n"
  echo -e "branch/tag  = the release version to be created"
  echo -e "git repo root = the parent directory of the git repositories"
  echo -e "fallback = allow fallback to master if branch/tag does not exist"
  echo -e ""
  echo -e "e.g: $0 master /git/xtuml"
  echo -e "     $0 my_branch /git/xtuml yes"
  exit 1
}

init_repository () 
{
  repo_name="$1"
  
  if [ ! -x "${git_repo_root}/${repo_name}" ]; then
    cd ${git_repo_root}
    git clone https://mgbuilder:bui!db0y@github.com/xtuml/${repo_name}.git
  fi
  cd ${git_repo_root}/${repo_name}
  git checkout ${branch}
  if [ "$?" = "1" ]; then
    if [ "${allow_fallback}" = "no" ]; then
      exit 1
    else
      git checkout master
    fi
  fi
  git fetch
  echo -e "Changes for repository: ${repo_name}" >> ${diff_file}
  echo -e "------------------------------------" >> ${diff_file}
  git diff --stat origin/${branch} >> ${diff_file}
  git merge origin/${branch}
}


#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------
# Check args
if [ $# -lt 2 ]; then
  usage
fi

branch="$1"
git_repo_root="$2"
allow_fallback="yes"
base_dir=`pwd`
diff_file="${base_dir}/diff.log"

if [ "$3" != "" ] && [ "$3" != "yes" ]; then
  allow_fallback="no"
fi

# Create git repo parent
if [ ! -x ${git_repo_root} ]; then
  echo -e "Creating git repositories parent directory: ${git_repo_root}"
  mkdir -p ${git_repo_root}
fi

# Set up each repository
init_repository internal
init_repository editor
init_repository mc

echo -e "Repositories are now initialized."
exit 0
