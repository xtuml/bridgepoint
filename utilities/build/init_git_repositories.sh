#!/bin/bash
#
#=====================================================================
#
# File:      init_git_repositories.sh
#
#=====================================================================
#
#  Since this starting point for pulling the rest of the data from revision
#  control, it must be manually put into place for the build server to run.
#

# Check arguments
if [ $# -ne 3 ]; then
    echo
    echo "Usage: ./init_git_repositories.sh <product_branch> <git_repo_root> <allow_fallback>"
    echo "      product_branch -- e.g. master, R4_2_1"
    echo "      git_repo_root -- path to the location of the git repository root"
    echo "      allow_fallback -- if the branch cannot be found, use master? (yes/no)"
    echo
    exit 0
fi

#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------

init_repository () 
{

  repo_name="$1"
  echo -e "Entering init_git_repositories.sh::init_repository  repo_name=${repo_name}"
  
  if [ ! -x "${git_repo_root}/${repo_name}" ]; then
    cd ${git_repo_root}
    git clone https://${GITUSER}:${GITPASS}@github.com/xtuml/${repo_name}.git
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
  
  # If the branch we're using is master, we make a hard changeover to the state of origin/master.
  # Otherwise, pull in the latest origin changes to the local branch
  if [ "${branch}" = "master" ]; then
    git reset --hard origin/master
  else
    git pull
  fi
  echo -e "Exiting init_git_repositories.sh::init_repository"
}


#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------
echo -e "Entering init_git_repositories.sh"

branch=$1
git_repo_root=$2
allow_fallback=$3

# Create git repo parent
if [ ! -x ${git_repo_root} ]; then
  echo -e "Creating git repositories parent directory: ${git_repo_root}"
  mkdir -p ${git_repo_root}
fi

# Set up each repository
init_repository bridgepoint
init_repository mc
init_repository packaging

echo -e "Exiting init_git_repositories.sh"

exit 0
