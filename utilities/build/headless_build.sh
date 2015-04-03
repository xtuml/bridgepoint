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

export GDK_NATIVE_WINDOWS=true
export BP_JVM=$BPHOMEDIR/jre/lib/i386/client/libjvm.so

bp_jvm="-vm $ECLIPSE_HOME/../jre/lib/i386/client/libjvm.so"
eclipse_args="${bp_jvm} -nosplash -application org.eclipse.cdt.managedbuilder.core.headlessbuild --launcher.suppressErrors"
vm_args="-vmargs -Dorg.eclipse.cdt.core.console=org.eclipse.cdt.core.systemConsole -Declipse.log.level=ALL"

####  importAll  
# ${ECLIPSE_HOME}/eclipse ${eclipse_args} -importAll "$GIT_BP" -data "${WORKSPACE}"

###  Clean build
${ECLIPSE_HOME}/eclipse ${eclipse_args} -cleanBuild all -data "$WORKSPACE" ${vm_args}

## build all
${ECLIPSE_HOME}/eclipse ${eclipse_args} -build all -data "$WORKSPACE" ${vm_args}

## touch a a generated filess to fix dependencies
if [ -e "$GIT_BP/plugin.xml" ]; then 
  touch "$GIT_BP/plugin.xml"
fi

## build all again
${ECLIPSE_HOME}/eclipse ${eclipse_args} -build all -data "$WORKSPACE" ${vm_args}
