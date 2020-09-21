# This script sets up environment variables that
# are required to prepare a development workspace
# as well as build such a workspace
if [ -z $BP_GIT_DIR ]
then
  export BP_GIT_DIR=~/git
fi

if [ -z $bp_install_dir ]
then
  # for mac us this install dir
  export bp_install_dir=~/xtuml/BridgePoint.app/Contents/Eclipse/
  # for linux uncomment this line and comment the mac version
  #export bp_install_dir=~/xtuml/BridgePoint
fi

if [ -z $WORKSPACE ]
then
  export WORKSPACE=~/workspace
fi

if [ -z $INCLUDE_TESTS ]
then
  export INCLUDE_TESTS=false
fi

export XTUML_DEVELOPMENT_REPOSITORY=${BP_GIT_DIR}/bridgepoint
export XTUML_TEST_MODEL_REPOSITORY=${BP_GIT_DIR}/models/test
export mcj_path=${XTUML_DEVELOPMENT_REPOSITORY}/src/MC-Java
export bp_test_path=${XTUML_DEVELOPMENT_REPOSITORY}/../bptest

