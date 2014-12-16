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
  
  # Make sure our refs are up to date.  Remove any remote tracking branches
  # that no longer exist on the remote.
  git fetch --prune
  
  # Just to be safe, discard any local changes before we try to switch branches.
  git reset --hard HEAD

  # Switch to the desired branch.  First see if we've used it before.  If not,
  # then get it from the remote.
  git checkout ${branch}
  if [ "$?" = "1" ]; then
    # We have never checked out (and thus created) a local branch named <branch>.
    # The git fetch above will have pulled all the remotes into the origin/refs 
    # already.  So, now we can check out the branch based on the remote.
    git checkout --track origin/${branch}
    if [ "$?" = "1" ]; then
      if [ "${allow_fallback}" = "no" ]; then
        exit 1
      else
        git checkout master
      fi
    fi
  fi
  
  echo -e "\nChanges since yesterday for repository: ${repo_name}" >> ${diff_file}
  echo -e "------------------------------------------------------" >> ${diff_file}
  # If the branch we're using is master, we make a hard changeover to the state of origin/master.
  # Otherwise, merge in the latest origin changes to the local branch (but don't autocommit
  # fast forwards or stage other commits)
  if [ "${branch}" = "master" ]; then
    git reset --hard origin/master
    git diff --name-status HEAD@{yesterday} >> ${diff_file}
  else
    git merge origin/${branch} --no-commit --no-ff
    git diff --name-status HEAD@{yesterday} >> ${diff_file}
  fi
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
init_repository packaging

echo -e "Repositories are now initialized."
exit 0
