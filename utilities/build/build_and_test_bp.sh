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
  # touch a timestamp file for every project with a pom as they
  # were all build seccessfully but only the single releng.parent project
  # ran through build_project.sh
  # handle bridgepoint projects
  for project in $(ls -1 $XTUML_DEVELOPMENT_REPOSITORY/src/); do
    if [ -d $XTUML_DEVELOPMENT_REPOSITORY/src/$project ] && [ -f $XTUML_DEVELOPMENT_REPOSITORY/src/$project/pom.xml ]; then
      if [ ! -d $WORKSPACE/.metadata/.plugins/$project ]; then
        mkdir -p $WORKSPACE/.metadata/.plugins/$project
      fi
      echo "Updating timestamp for $project"
      touch $WORKSPACE/.metadata/.plugins/$project/maven_build.timestamp
    fi
  done
  # handle test projects if INCLUDE_TESTS=true
  if [ $INCLUDE_TESTS == "true" ]; then
    for project in $(ls -l $bp_test_path/src/); do
      if [ -d $bp_test_path/src/$project ] && [ -f $bp_test_path/src/$project/pom.xml ]; then
        if [ ! -d $WORKSPACE/.metadata/.plugins/$project ]; then
          mkdir -p $WORKSPACE/.metadata/.plugins/$project
        fi
        touch $WORKSPACE/.metadata/.plugins/$project/maven_build.timestamp
      fi
    done
  fi
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
