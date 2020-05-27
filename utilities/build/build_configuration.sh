# This script sets up environment variables that
# are required to prepare a development workspace
# as well as build such a workspace
export BP_GIT_DIR=~/git
export bp_install_dir=~/devBridgePoint
export WORKSPACE=~/workspace/build
export INCLUDE_TESTS=false
export XTUML_DEVELOPMENT_REPOSITORY=${BP_GIT_DIR}/bridgepoint
export XTUML_TEST_MODEL_REPOSITORY=${BP_GIT_DIR}/models/test
export mcj_path=${XTUML_DEVELOPMENT_REPOSITORY}/src/MC-Java
export bp_test_path=${XTUML_DEVELOPMENT_REPOSITORY}/../bptest

