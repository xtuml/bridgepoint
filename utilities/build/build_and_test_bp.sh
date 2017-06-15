# This script will build the xtuml tool
# it uses the maven scripts to build.  
# There are two possible parameters one being
# a test plugin name "core, ui.text". and
# -debug which will start the test run in
# remote debug mode (there is an eclipse 
# lanuch config that points at port 8000).

#!/bin/bash

export GIT_DIR=~/git
export XTUML_DEVELOPMENT_REPOSITORY=~/git/bridgepoint
export XTUML_TEST_MODEL_REPOSITORY=~/git/models/test
export INCLUDE_TESTS=true
export mcj_path=~${XTUML_DEVELOPMENT_REPOSITORY}/src/MC-Java
export bp_test_path=~${XTUML_DEVELOPMENT_REPOSITORY}/../bptest
# If on mac use the following
export bp_install_dir=~/xtuml/BridgePoint.app/Contents/Eclipse/
export WORKSPACE=~/workspace
#export bp_install_dir=~/xtuml/BridgePoint

prev_dir=`pwd`

project="org.xtuml.bp.releng.parent"
dir="${XTUML_DEVELOPMENT_REPOSITORY}/releng/${project}"
if [ "${1}" != "" ]; then
  project="${1}"
  dir="${XTUML_DEVELOPMENT_REPOSITORY}/../bptest/src/org.xtuml.bp.${project}.test" 
  echo ${dir}
fi

debug=""
if [ "$2" == "-debug" ];then
  debug="-DdebugPort=8000"
fi

cd $dir 
mvn $debug -Dtycho.disableP2Mirrors=true -Dmaven.test.failure.ignore=true install
mvn -Daggregate=true surefire-report:report-only

if [ "$(uname)" == "Darwin" ];then
  open $dir/target/site/surefire-report.html
else
  firefox $dir/target/site/surefire-report.html
fi

cd $prev_dir

exit 0
