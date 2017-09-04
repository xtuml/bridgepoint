#!/bin/bash
#
# Support functions for the main build entry points for command line and UI building
function create_timestamps {
 a timestamp file for every project with a pom as they
  # were all built seccessfully but only the single releng.parent project
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
}

