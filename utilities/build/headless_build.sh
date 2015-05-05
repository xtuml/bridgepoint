#!/bin/bash

build()
{
  perform_clean="$1"
  file_to_touch="$2"	
  
  # Remove previous logs, we really don't care about any failures that happen
  # prior to this final build
  rm -f "${workspace}/.metadata/.log"

  if [ "${file_to_touch}" != "" ]; then
    if [ -e "${file_to_touch}" ]; then 
      echo "Touching a generated file: ($file_to_touch)" 
      touch "$file_to_touch"
    else
      echo "ERROR! file not present:  $file_to_touch" 
      exit 1
    fi
  fi
  
  if [ "$perform_clean" == "yes" ]; then
    ###  Clean build
    echo "Performing a clean build."
    "${bp_launcher}" ${launcher_args} -cleanBuild all -data "$workspace" 
  else
    echo "Performing a build (not clean)."
    "${bp_launcher}" ${launcher_args} -build all -data "$workspace" 
  fi
  
  exit $?
}

## Verify the command-line
if [ "$#" -lt 4 ]; then
  echo "Usage:"
  echo
  echo "headless_build.sh <branch name> <BridgePoint home folder> <workspace folder> <BridgePoint git folder> <clean flag>"
  echo "    branch name - name of a branch in git to build"
  echo "    BridgePoint home folder - path of the BridgePoint installation to use (e.g. /home/ubuntu/xtuml/BridgePoint"
  echo "    workspace folder - path to the workspace to build"
  echo "    BridgePoint git folder - path to the local BridgePoint git repository"
  echo "    clean flag - run a clean build or not (yes / no)"

  exit 1
fi
branch="$1"
bp_launcher="$2"/eclipse/Launcher.sh
workspace="$3"
git_bp="$4"
perform_clean="$5"

launcher_args="-nosplash -application org.eclipse.cdt.managedbuilder.core.headlessbuild --launcher.suppressErrors"


# Set the environment variable, PTC_MCC_DISABLED to true so that consistency
# checking is not built
export PTC_MCC_DISABLED=true
export GDK_NATIVE_WINDOWS=true

###
### Setup the workspace by "priming it" with the needed settings
###
workspace_settings_folder="${workspace}/.metadata/.plugins/org.eclipse.core.runtime/.settings"
mkdir -p "${workspace_settings_folder}"
cp -f "${git_bp}"/utilities/build/preferences/* "${workspace_settings_folder}"

####  Import all plugins
####  We have a separate -import for each plugin as opposed
####  to using -importAll <tree> because importAll imports nested projects,
####  and our build does not allow this.
import_cmd=""
for PROJECT in $(ls -1 "${git_bp}"/src); do 
  if [ "$PROJECT" != "org.antlr_2.7.2" ] && [ "$PROJECT" != "README.md" ]; then
    import_cmd+=" -import ${git_bp}/src/$PROJECT "
  fi
done
echo "Importing projects."
"${bp_launcher}" ${launcher_args} ${import_cmd} -data "${workspace}" 
echo "Importing projects. - DONE"

build "${perform_clean}" ""
RETVAL=$?
if [ $RETVAL -ne 0 ]; then
  echo "ERROR! The headless build FAILED."
  exit 1
fi



