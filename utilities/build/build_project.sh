#!/bin/bash
# Build BridgePoint projects, used in both command line and UI
# Usage: build_project.sh [projectName] [test] [-online] [-debug]
#
#  projectName = core, releng or test project to build
#  test = specifies wheter or not tests shall run (not to be confused with INCLUDE_TESTS variable)
#  -online = Default is to build offline, use this to redownload repository from the network
#  -debug = Allows debugging tests from the command line
#
# do not run maven if an xtuml, arc, inc or sql file did not change
SCRIPTPATH=`dirname $0`
source $SCRIPTPATH/build_configuration.sh
cmd="install"
offline="-o"
skipTests="-DskipTests"
projectName="org.xtuml.bp.releng.parent"

for arg in "$@"
do
  case "$arg" in
     "")
     echo "Usage: $0 [projectName] [-online] [-debug] [test]"
     exit 1
  ;;
     "test")
     skipTests=""
     shift
  ;;
    -online)
    offline=""
    shift
  ;;
    -debug)
    cmd="-DdebugPort=8000 $cmd"
    shift
  ;;
    "clean")
    cmd="clean"
    shift
  ;;
    *)
    projectName=$arg
    shift
  ;;
  esac
done

project=$(basename $projectName)
projectDir=$XTUML_DEVELOPMENT_REPOSITORY/src/$project
if [ ! -d $WORKSPACE/.metadata/.plugins/$project ]; then
  mkdir -p $WORKSPACE/.metadata/.plugins/$project
fi
timestampFile=$WORKSPACE/.metadata/.plugins/$project/maven_build.timestamp
if [[ "$project" == *"test"* ]]; then
  projectDir=$bp_test_path/src/$project
fi
if [[ "$project" == *"releng"* ]]; then
  projectDir=$XTUML_DEVELOPMENT_REPOSITORY/releng/$project
fi
if [ "$project" == "org.xtuml.bp.mctools" ] || [ "$project" == "org.xtuml.bp.mctools.parent" ]; then
  projectDir=$XTUML_DEVELOPMENT_REPOSITORY/releng/$project
fi

if [ "$cmd" == "clean" ]; then
  # remove the timestamp file
  echo "Removing timestamp file for clean"
  rm -rf $timestampFile
fi

buildMaven=""
function checkTimestamp {
ret_val=0
  find $projectDir -name "*.xtuml" -print0 | while read -d $'' file; do
  if [ "$timestampFile" -ot "$file" ]; then
    echo "File: $file has been modified triggering maven build."
    return 1
  fi
  done
  find $projectDir -name "*.arc" -print0 | while read -d $'' file; do
    if [ "$timestampFile" -ot "$file" ]; then
      echo "File: $file has been modified triggering maven build."
      return 1
    fi
  done
  find $projectDir -name "*.inc" -print0 | while read -d $'' file; do
    if [ "$timestampFile" -ot "$file" ]; then
      echo "File: $file has been modified triggering maven build."
      return 1
    fi
  done
  find $projectDir -name "*.sql" -print0 | while read -d $'' file; do
    if [ "$timestampFile" -ot "$file" ]; then
      echo "File: $file has been modified triggering maven build."
      return 1
    fi
  done
  # trigger generation for modified text files, we only use them
  # in the build process for matrices
  find $projectDir -name "*.txt" -print0 | while read -d $'' file; do
    if [ "$timestampFile" -ot "$file" ]; then
      echo "File: $file has been modified triggering maven build."
      return 1
    fi
  done
  # trigger generation when a build file is modified
  find $projectDir -name "generate.xml" -print0 | while read -d $'' file; do
    if [ "$timestampFile" -ot "$file" ]; then
      echo "File: $file has been modified triggering maven build."
      return 1
    fi
  done
  return 0
}

# test was given as an argument or we are running a full build, skip file change checks as we still
# must run maven
if [ "$skipTests" != "" ] && [[ $project != *"releng"* ]]; then
  buildMaven=$(checkTimestamp)
else
  buildMaven="true"
fi
if [ "$buildMaven" == "" ]; then
  # exit immediately
  exit 0;
fi
# Use GTK 2 here as the development environment sets it with the launcher
# and the setting does carry over to the test runs
export SWT_GTK3=0
export GDK_NATIVE_WINDOWS=true
orginal_dir=`pwd`
ret_val=0
# only run pkg-feature if building a test plugin
if [[ $project == *"test"* ]]; then
  cd ${XTUML_DEVELOPMENT_REPOSITORY}/src/org.xtuml.bp.pkg-feature && mvn $offline -Dtycho.disableP2Mirrors=true $skipTests -Dmaven.test.failure.ignore=true install && cd ${projectDir} && mvn $offline -Dtycho.disableP2Mirrors=true $skipTests -Dmaven.test.failure.ignore=true $cmd
  ret_val=$?
else
  cd ${projectDir} && mvn $offline -Dtycho.disableP2Mirrors=true $skipTests -Dmaven.test.failure.ignore=true $cmd
  ret_val=$?
fi
cd "$original_dir"
# Do not touch for cleans or for the parent releng project
if [ "$cmd" != "clean" ] && [ "$project" != "org.xtuml.bp.releng.parent" ]; then
  touch $timestampFile
fi
exit $ret_val
