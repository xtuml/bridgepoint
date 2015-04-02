#!/bin/bash
BPHOMEDIR="${HOME}/MentorGraphics/BridgePoint"
BPBUILD="${HOME}/build/work"

###  If branch is not set then default to master, but if command-line argument
###  is given use it as the branch name regardless of if BRANCH is already set 
###
if [ $BRANCH -eq "" ]; then
  export BRANCH="master"
fi

if [ $# -eq 1 ]; then
  export BRANCH="$1"
fi

export GDK_NATIVE_WINDOWS=true
export BP_JVM=$BPHOMEDIR/jre/lib/i386/client/libjvm.so

####  importAll  
# $BPHOMEDIR/eclipse/eclipse -vm $BP_JVM -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll $BPBUILD/git/xtuml/bridgepoint -data "$BPBUILD/$BRANCH"

###  Clean build
$BPHOMEDIR/eclipse/eclipse -vm $BP_JVM -application org.eclipse.cdt.managedbuilder.core.headlessbuild -cleanBuild all -data "$BPBUILD/$BRANCH"

## build bp.core first
$BPHOMEDIR/eclipse/eclipse -vm $BP_JVM -application org.eclipse.cdt.managedbuilder.core.headlessbuild -build "org.xtuml.bp.core" -data "$BPBUILD/$BRANCH"

## build all
$BPHOMEDIR/eclipse/eclipse -vm $BP_JVM -application org.eclipse.cdt.managedbuilder.core.headlessbuild -build "org.xtuml.bp.core" -data "$BPBUILD/$BRANCH"

## touch a a generated file file to fix dependencies
if [ -e "$BPBUILD/git/xtuml/bridgepoint/plugin.xml" ] 
  touch "$BPBUILD/git/xtuml/bridgepoint/plugin.xml"
fi

## build all again
$BPHOMEDIR/eclipse/eclipse -vm $BP_JVM -application org.eclipse.cdt.managedbuilder.core.headlessbuild -build "org.xtuml.bp.core" -data "$BPBUILD/$BRANCH"
