# This script will build the xtuml tool
# it uses the maven scripts to build.  
# There are two possible parameters one being
# a test plugin name "core, ui.text". and
# -debug which will start the test run in
# remote debug mode (there is an eclipse 
# lanuch config that points at port 8000).

#!/bin/bash

SCRIPTPATH=`dirname $0`

source $SCRIPTPATH/build_configuration.sh

# Check for prebuild output in bp.core, if not
# present then run the prepare script
if [ ! -f $XTUML_DEVELOPMENT_REPOSITORY/src/org.xtuml.bp.core/sql/ooaofooa-1.sql ]; then
  $XTUML_DEVELOPMENT_REPOSITORY/utilities/build/prepare_build.sh
fi

prev_dir=`pwd`

project="org.xtuml.bp.releng.parent"

prev_dir=$(pwd)
dir=$XTUML_DEVELOPMENT_REPOSITORY/releng/$project
cd $dir
$XTUML_DEVELOPMENT_REPOSITORY/utilities/build/build_project.sh $project test -online
maven_return=$?
if [ $maven_return == 0 ]; then
  create_timestamps
fi
if [ $maven_return == 0 ] && [ "${INCLUDE_TESTS}" == "true" ]; then
  mvn -Dtycho.disableP2Mirrors=true -Daggregate=true surefire-report:report-only
  if [ "$(uname)" == "Darwin" ];then
    open $dir/target/site/surefire-report.html
  else
    xdg-open $dir/target/site/surefire-report.html
  fi
fi

cd $prev_dir

exit 0
