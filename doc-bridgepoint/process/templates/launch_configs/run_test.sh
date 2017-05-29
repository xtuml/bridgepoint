# There are some dependency issues with maven
# This script will run from a test project builder configuration, building org.xtuml.bp.pkg.feature before a test run
cmd="install"
if [[ $# -eq 3 ]] ; then
  cmd="clean"
fi
cd ${1}/../src/org.xtuml.bp.pkg-feature && mvn -Dtycho.disableP2Mirrors=true -Dmaven.test.failure.ignore=true install && cd ${2} && mvn -Dtycho.disableP2Mirrors=true -Dmaven.test.failure.ignore=true $cmd
