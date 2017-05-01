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
#-------------------------------------------------------------------------------
# Example for testing this script:
#    ./init_git_repositories.sh 7769_remove_sp /home/onefact/1F-test/build/git/xtuml yes rmulvey
#
# Branch 7769_remove_sp only exists in rmulvey's bridgepoint.  So for mc and
# packaging the script will fallback to xtuml/master.
#
# Output of "git branch -vv" after the script is done:
#    build/git/xtuml/bridgepoint$ git branch -vv
#    * 7769_remove_sp     1af2f04 [rmulvey/7769_remove_sp]   ...<snipped comments>...
#
#    build/git/xtuml/mc$ git branch -vv
#    * master 606f429 [origin/master]    ...<snipped comments>...
#
#    build/git/xtuml/packaging$ git branch -vv
#    * master 99e3e35 [origin/master]   ...<snipped comments>... 
#-------------------------------------------------------------------------------


# Check arguments
if [ $# -ne 4 ]; then
    echo
    echo "Usage: ./init_git_repositories.sh <product_branch> <git_repo_root> <allow_fallback> <user_fork>"
    echo "      product_branch -- e.g. master, R4_2_1"
    echo "      git_repo_root -- path to the location of the git repository root"
    echo "      allow_fallback -- if the branch cannot be found, use master? (yes/no)"
    echo "      user_fork -- e.g. rmulvey, xtuml"
    echo
    exit 0
fi

#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------

init_repository () 
{
  repo_name="$1"
  echo -e "--------------------------------------"
  echo -e "Entering init_git_repositories.sh::init_repository  repo_name=${repo_name}"
  
  #-----------------------------------------------------------------------------
  # Determine which user and branch combination to use by checking to see if the
  # branch exists in the remote repository.  The remote user defaults to "xtuml"
  # in the run_build.sh script if it was not specified.
  # Exit status "2" means there were no matches.
  #-----------------------------------------------------------------------------
  git ls-remote --exit-code https://github.com/${user_fork}/${repo_name}.git ${branch} &>/dev/null

  if [ "$?" = "2" ]; then
    #---------------------------------------------------------------------------
    # The branch was not found, fallback to xtuml/master if allowed.
    #---------------------------------------------------------------------------
    if [ "${allow_fallback}" = "no" ]; then
      echo -e "   ERROR in init_repository:  user/branch ${user_fork}/${branch} not found"
      echo -e "                              and fallback is not allowed.  Exiting..."
      exit 1
    else
      echo -e "   Warning:  ${user_fork}/${repo_name} does not contain branch ${branch}."
      echo -e "             Using fallback xtuml/master."
      remote_user="xtuml"
      remote_branch="master"
    fi
  else
    remote_user=${user_fork}
    remote_branch=${branch}
  fi
  echo -e "   Proceeding with remote_user = ${remote_user}; remote_branch = ${remote_branch}."

  GIT_URL="https://github.com/${remote_user}/${repo_name}.git"

  #-----------------------------------------------------------------------------
  # If the repository does not exist locally, clone it from github.
  #-----------------------------------------------------------------------------
  if [ ! -x "${git_repo_root}/${repo_name}" ]; then
    echo -e "   ${repo_name} does not exist, cloning from ${GIT_URL}."
    cd ${git_repo_root}
    git clone ${GIT_URL}
  fi

  cd ${git_repo_root}/${repo_name}

  #-----------------------------------------------------------------------------
  # If the remote does not exist locally, add it.
  #-----------------------------------------------------------------------------
  if [ "$(git remote | grep ${remote_user})" = "" ]; then
    echo -e "   remote: ${remote_user} does not exist, adding it."
    git remote add ${remote_user} ${GIT_URL}
  fi

  #-----------------------------------------------------------------------------
  # Make sure our refs are up to date.  Remove any remote tracking branches
  # that no longer exist on the remote.
  #-----------------------------------------------------------------------------
  git fetch --prune ${remote_user}
  
  #-----------------------------------------------------------------------------
  # Just to be safe, discard any local changes before we try to switch branches.
  # Note: We've gone back and forth on whether or not this "descrutive" reset
  #       should be part of this script.  See https://support.onefact.net/redmine/issues/7995 
  #       If you are a developer who runs these build scripts locally, you may want
  #       be "safe" and comment out this line to make sure you don't ever wipe out
  #       local changes.
  #-----------------------------------------------------------------------------
  git reset --hard HEAD

  #-----------------------------------------------------------------------------
  # Switch to the desired branch.  First see if we've used it before.  If not,
  # then get it from the remote.
  #-----------------------------------------------------------------------------
  git checkout ${remote_branch}
  if [ "$?" = "1" ]; then
    # We have never checked out (and thus created) a local branch named <branch>.
    # The git fetch above will have pulled all the remotes into the origin/refs 
    # already.  So, now we can check out the branch based on the remote.
    git checkout --track ${remote_user}/${remote_branch}
    if [ "$?" = "1" ]; then
      if [ "${allow_fallback}" = "no" ]; then
        exit 1
      else
        git checkout master
      fi
    fi
  fi
  
  #-----------------------------------------------------------------------------
  # If the branch we're using is master, we make a hard changeover to the state of origin/master.
  # Otherwise, pull in the latest origin changes to the local branch
  #-----------------------------------------------------------------------------
  if [ "${remote_branch}" = "master" ]; then
    echo -e "   Branch is master, doing a hard reset to ${remote_user}/master."
    git reset --hard ${remote_user}/master
  else
    git pull
  fi

  #-----------------------------------------------------------------------------
  # Switching branches can cause files deleted in the branch to now show up as
  # untracked files locally.  The following command cleans these up.  Note that
  # this is a "destructive" operation, so developers running the script locally
  # not want this behavior.
  #-----------------------------------------------------------------------------
  git clean -fd
  
  echo -e "Exiting init_git_repositories.sh::init_repository"
}


#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------
echo -e "Entering init_git_repositories.sh"

branch=$1
git_repo_root=$2
allow_fallback=$3
user_fork=$4

# Create git repo parent
if [ ! -x ${git_repo_root} ]; then
  echo -e "Creating git repositories parent directory: ${git_repo_root}"
  mkdir -p ${git_repo_root}
fi

# Set up each repository
init_repository bridgepoint
init_repository mc
init_repository packaging
init_repository pt_antlr

echo -e "Exiting init_git_repositories.sh"

exit 0
