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

  date  
  if [ "$perform_clean" == "yes" ]; then
    ###  Clean build
    echo "Performing a clean build."
    ${bphomedir}/eclipse/eclipse ${eclipse_args} -cleanBuild all -data "$workspace" 
  else
    echo "Performing a build (not clean)."
    ${bphomedir}/eclipse/eclipse ${eclipse_args} -build all -data "$workspace" 
  fi
  date
 
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
bphomedir="$2"
workspace="$3"
git_bp="$4"
perform_clean="$5"
bp_jvm=$bphomedir/jre/lib/i386/client/libjvm.so

echo "Headless build invocation: ./headless_build.sh ${branch} ${bphomedir} ${workspace} ${git_bp} ${perform_clean}"

eclipse_args="-vm ${bp_jvm} -nosplash -application org.eclipse.cdt.managedbuilder.core.headlessbuild --launcher.suppressErrors"


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
####  Note:
####  1) we have a separate -import for each plugin as opposed
####  to using -importAll <tree> because importAll imports nested projects,
####  and our build does not allow this.
####  2) -import <plugin> is import such that it may spawn a separate thread 
####  for each import.  This means the order of import is not guaranteed
####
import_cmd=""
for PROJECT in $(ls -1 "${git_bp}"/src); do 
  if [ "$PROJECT" != "org.antlr_2.7.2" ] && [ "$PROJECT" != "README.md" ]; then
    import_cmd+=" -import ${git_bp}/src/$PROJECT "
  fi
done
echo "Importing projects."
${bphomedir}/eclipse/eclipse ${eclipse_args} ${import_cmd} -data "${workspace}" 

# Before running the build make sure the ant log folder is present
# The import calls will have created the .metadata folder
mkdir -p ${workspace}/.metadata/bridgepoint/build/log

# Build the MASL plugins using maven.
cd "${git_bp}/src/org.xtuml.bp.xtext.masl.parent"
mvn clean install

build "${perform_clean}" ""
RETVAL=$?
if [ $RETVAL -ne 0 ]; then
  echo "The first build FAILED."
  exit 1
fi

build "no" "${git_bp}/src/org.xtuml.bp.core/plugin.xml"
RETVAL=$?
if [ $RETVAL -ne 0 ]; then
  echo "The second build FAILED."
  exit 1
fi

