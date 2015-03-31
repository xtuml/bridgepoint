#!/bin/bash
#
#  init_git_repositories.sh requires the following environment variables
#
#   $BRANCH - product version, actually this is any branch/tag found in git
#   $GIT_REPO_ROOT - git repository root 
#   $ALLOW_FALLBACK - allow fallback to master is specified branch does not 
#            exist (yes or no)
#
#  Since this starting point for pulling the rest of the data from revision
#  control, it must be manually put into place for the build server to run.
#

#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------

init_repository () 
{

  repo_name="$1"
  echo -e "Entering init_git_repositories.sh::init_repository  repo_name=${repo_name}"
  
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
  echo -e "Exiting init_git_repositories.sh::init_repository"
}


#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------
echo -e "Entering init_git_repositories.sh"

branch=${BRANCH}
git_repo_root=${GIT_REPO_ROOT}
allow_fallback=${ALLOW_FALLBACK}
diff_file="${BUILD_DIR}/diff.log"

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
