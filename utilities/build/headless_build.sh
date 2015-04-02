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
if [ $BRANCH -eq "" ]; then
  export BRANCH="master"
fi
if [ $# -eq 1 ]; then
  export BRANCH="$1"
fi

if [ $BPHOMEDIR -eq "" ]; then
  export BPHOMEDIR="${HOME}/MentorGraphics/BridgePoint"
fi  

if [ $ECLIPSE_HOME -eq "" ]; then
  export ECLIPSE_HOME="${BPHOMEDIR}/eclipse"
fi
if [ $WORKSPACE -eq "" ]; then
  export WORKSPACE="${HOME}/build/work"
fi

WORKSPACE="$BPBUILD/$BRANCH"
export GDK_NATIVE_WINDOWS=true
export BP_JVM=$BPHOMEDIR/jre/lib/i386/client/libjvm.so

bp_jvm="-vm $ECLIPSE_HOME/../jre/lib/i386/client/libjvm.so"
eclipse_args="${bp_jvm} -nosplash -application org.eclipse.cdt.managedbuilder.core.headlessbuild --launcher.suppressErrors"
vm_args="-vmargs -Dorg.eclipse.cdt.core.console=org.eclipse.cdt.core.systemConsole -Declipse.log.level=ALL"

####  importAll  
# ${ECLIPSE_HOME}/eclipse ${eclipse_args} -importAll "$BPBUILD/git/xtuml/bridgepoint" -data "${WORKSPACE}"

###  Clean build
${ECLIPSE_HOME}/eclipse ${eclipse_args} -cleanBuild all -data "$WORKSPACE" ${vm_args}

## build bp.core first
${ECLIPSE_HOME}/eclipse ${eclipse_args} -build "org.xtuml.bp.core" -data "$WORKSPACE" ${vm_args}

## build all
${ECLIPSE_HOME}/eclipse ${eclipse_args} -build "org.xtuml.bp.core" -data "$WORKSPACE" ${vm_args}

## touch a a generated filess to fix dependencies
if [ -e "$BPBUILD/git/xtuml/bridgepoint/plugin.xml" ]; then 
  touch "$BPBUILD/git/xtuml/bridgepoint/plugin.xml"
fi

## build all again
${ECLIPSE_HOME}/eclipse ${eclipse_args} -build "org.xtuml.bp.core" -data "$WORKSPACE" ${vm_args}
