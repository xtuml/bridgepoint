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
    ${eclipse_home}/eclipse ${eclipse_args} -cleanBuild all -data "$workspace" 
  else
    echo "Performing a build (not clean)."
    ${eclipse_home}/eclipse ${eclipse_args} -build all -data "$workspace" 
  fi
  
  exit $?
}

## Verify the command-line
if [ "$#" -lt 2 ]; then
  echo "Usage:"
  echo
  echo "headless_build.sh <branch name> <bridgepoint home folder> <eclipse home folder> <workspace folder> <bridgepoint git folder> <clean flag>"
  echo
  echo "See the script header for more detail."

  exit 1
fi
branch="$1"
bphomedir="$2"
eclipse_home="$3"
workspace="$4"
git_bp="$5"
perform_clean="$6"
BP_JVM=$bphomedir/jre/lib/i386/client/libjvm.so

bp_jvm="-vm $eclipse_home/../jre/lib/i386/client/libjvm.so"
eclipse_args="${bp_jvm} -pluginCustomization ${workspace}/plugin_customization.ini -nosplash -application org.eclipse.cdt.managedbuilder.core.headlessbuild --launcher.suppressErrors"


# Set the environment variable, PTC_MCC_DISABLED to true so that consistency
# checking is not built
export PTC_MCC_DISABLED=true
export GDK_NATIVE_WINDOWS=true

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
${eclipse_home}/eclipse ${eclipse_args} ${import_cmd} -data "${workspace}" 

# Before running the build make sure the ant log folder is present
# The import calls will have created the .metadata folder
mkdir -p ${workspace}/.metadata/bridgepoint/build/log

build "${perform_clean}" ""
RETVAL=$?
if [ $RETVAL -ne 0 ]; then
  echo "The first build FAILED."
  exit 1
fi

