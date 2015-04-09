#!/bin/bash

##
## This script is written so that is uses environment variables from the 
## server build script, if present.  However, if not, it has defaults to allow
## just a branch name to be passed, so you can use this script after import 
## has been done just to rebuild without having to go through the rest of the 
## build server script
##

###  If branch is not set then default to master, but if command-line argument
###  is given use it as the branch name regardless of if BRANCH is already set 
###
if [ "$BRANCH" == "" ]; then
  export BRANCH="master"
fi
if [ $# -eq 1 ]; then
  export BRANCH="$1"
fi

if [ "$BPHOMEDIR" == "" ]; then
  export BPHOMEDIR="${HOME}/MentorGraphics/BridgePoint"
fi  

if [ "$ECLIPSE_HOME" == "" ]; then
  export ECLIPSE_HOME="${BPHOMEDIR}/eclipse"
fi
if [ "$WORKSPACE" == "" ]; then
  export WORKSPACE="${HOME}/build/work/${BRANCH}"
fi

if [ "$GIT_BP" == "" ]; then
	export GIT_BP="${HOME}/build/git/xtuml/bridgepoint"
fi

export CORE="${GIT_BP}/src/org.xtuml.bp.core"

export GDK_NATIVE_WINDOWS=true
export BP_JVM=$BPHOMEDIR/jre/lib/i386/client/libjvm.so

bp_jvm="-vm $ECLIPSE_HOME/../jre/lib/i386/client/libjvm.so"
eclipse_args="${bp_jvm} -pluginCustomization ${WORKSPACE}/plugin_customization.ini -nosplash -application org.eclipse.jdt.apt.core.aptBuild --launcher.suppressErrors"
eclipse_cdt_args="${bp_jvm} -pluginCustomization ${WORKSPACE}/plugin_customization.ini -nosplash -application org.eclipse.cdt.managedbuilder.core.headlessbuild --launcher.suppressErrors"

####  Import all plugins
####  Note:
####  1) we have a separate -import for each plugin as opposed
####  to using -importAll <tree> because importAll imports nested projects,
####  and our build does not allow this.
####  2) -import <plugin> is import such that it may spawn a separate thread 
####  for each import.  This means the order of import is not guaranteed
####
import_cmd=""
for PROJECT in $(ls -1 "${GIT_BP}"/src); do 
  if [ "$PROJECT" != "org.antlr_2.7.2" ] && [ "$PROJECT" != "README.md" ]; then
    import_cmd+=" -import ${GIT_BP}/src/$PROJECT "
  fi
done
echo "Importing projects."
${ECLIPSE_HOME}/eclipse ${eclipse_cdt_args} ${import_cmd} -data "${WORKSPACE}" 

# Before running the build make sure the ant log folder is present
# The import calls will have created the .metadata folder
mkdir -p ${WORKSPACE}/.metadata/bridgepoint/build/log

# Remove previous logs before we build
rm -f "${WORKSPACE}/.metadata/.log"

###  Clean build
echo "Performing a clean build."
${ECLIPSE_HOME}/eclipse ${eclipse_args} -data "$WORKSPACE"

## touch a generated file to fix dependencies
if [ -e "${CORE}/plugin.xml" ]; then 
  echo "Touching a generated file: ($CORE/plugin.xml)" 
  touch "$CORE/plugin.xml"
else
  echo "ERROR! $CORE/plugin.xml was not generated" 
fi

## build all again
echo "Performing a build."
${ECLIPSE_HOME}/eclipse ${eclipse_cdt_args} -build all -data "$WORKSPACE" 

RETVAL=$?
if [ $RETVAL -eq 0 ]; then
	echo "The build SUCCEEDED."
	exit 0
fi
if [ $RETVAL -ne 0 ]; then
 	echo "The build FAILED."
 	exit 1
fi
