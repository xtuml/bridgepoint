# This script sets up environment variables that
# are required to prepare a development workspace
# as well as build such a workspace
export GIT_DIR=~/git
export XTUML_DEVELOPMENT_REPOSITORY=${GIT_DIR}/bridgepoint
export XTUML_TEST_MODEL_REPOSITORY=${GIT_DIR}/models/test
export INCLUDE_TESTS=true
export mcj_path=~${XTUML_DEVELOPMENT_REPOSITORY}/src/MC-Java
export bp_test_path=~${XTUML_DEVELOPMENT_REPOSITORY}/../bptest
# If on mac use the following
export bp_install_dir=~/xtuml/BridgePoint.app/Contents/Eclipse/
export WORKSPACE=~/workspace
#export bp_install_dir=~/xtuml/BridgePoint

