# This script is used to prepare an eclipse workspace for building.
# It will copy the required configuration data to a new workspace
# and pre-build all required xtuml projects.  After completing the
# the tool is ready to build.

#!/bin/bash

SCRIPTPATH=`dirname $0`

source $SCRIPTPATH/build_configuration.sh

prev_dir=`pwd`

JAVA_ARGS=""
if [ "$(uname)" == "Darwin" ]; then
  JAVA_ARGS="-XstartOnFirstThread"
fi


function importProjects {
  dir=`pwd`
  cd $BP_GIT_DIR
  # Explicitly import doc-bridgepoint and utilities
  java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import bridgepoint/doc-bridgepoint
  java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import bridgepoint/utilities
  cd bridgepoint/src
  for file in `ls -1`; do
    java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import $file 
    # if this is the xtext parent, we need to import the nested projects
    if [ $file == "org.xtuml.bp.xtext.masl.parent" ]; then
      cd $file 
      for nestedFile in `ls -1`; do
        if [ -d $nestedFile ]; then
           java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import $nestedFile
        fi
      done
      cd ..
    fi 
  done
  cd ../doc-bridgepoint
  for file in `ls -1`; do
    java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import $file
  done
  cd ../releng
  for file in `ls -1`; do
     java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import $file
  done
 cd ../utilities
  for file in `ls -1`; do
     java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import $file
  done
  if [ "$INCLUDE_TESTS" == "true" ]; then
    cd ../../bptest/src
    for file in `ls -1`; do
       java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import $file
    done
  fi
  cd $dir
}

function preBuildProjects {
  java -Xms256m -Xmx1g $JAVA_ARGS -jar ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_*.jar -clean -noSplash -product org.xtuml.bp.pkg.BridgePoint -data $WORKSPACE -application org.xtuml.bp.cli.Build -project org.xtuml.bp.core -prebuildOnly
  java -Xms256m -Xmx1g $JAVA_ARGS -jar ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_*.jar -clean -noSplash -product org.xtuml.bp.pkg.BridgePoint -data $WORKSPACE -application org.xtuml.bp.cli.Build -project org.xtuml.bp.als -prebuildOnly
  java -Xms256m -Xmx1g $JAVA_ARGS -jar ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_*.jar -clean -noSplash -product org.xtuml.bp.pkg.BridgePoint -data $WORKSPACE -application org.xtuml.bp.cli.Build -project org.xtuml.bp.ui.canvas -prebuildOnly
  if [ "$INCLUDE_TESTS" == "true" ]; then
      java -Xms256m -Xmx1g $JAVA_ARGS -jar ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_*.jar -clean -noSplash -product org.xtuml.bp.pkg.BridgePoint -data $WORKSPACE -application org.xtuml.bp.cli.Build -project org.xtuml.bp.core.test -prebuildOnly
  fi
}

function prepareDevelopmentWorkspace {
  # create the workspace if it does not
  # exist and set it up for development
  if [ ! -d $WORKSPACE ]; then
    mkdir -p $WORKSPACE/.metadata/.plugins/org.eclipse.core.runtime/.settings
  fi
  cp -f $BP_GIT_DIR/bridgepoint/utilities/build/preferences/*  $WORKSPACE/.metadata/.plugins/org.eclipse.core.runtime/.settings
  cp -f $BP_GIT_DIR/pt_antlr/pt_antlr/antlr.jar  $BP_GIT_DIR/bridgepoint/src/org.xtuml.bp.als/lib/antlr.jar
  echo "Preparing workspace"
}

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
# do not prepare the workspace if already done
# here we lazily check for the presence of org.xtuml.bp.core.prefs
# and antlr.jar
if [ ! -f $WORKSPACE/.metadata/.plugins/org.eclipse.core.runtime/.settings/org.xtuml.bp.core.prefs ] || [ ! -f $XTUML_DEVELOPMENT_REPOSITORY/src/org.xtuml.bp.als/lib/antlr.jar ]; then
  prepareDevelopmentWorkspace
fi
# do not import projects unless those requiring
# prebuild are not present
if [ ! -d $WORKSPACE/.metadata/.plugins/org.eclipse.core.resources/.projects/org.xtuml.bp.core ] ||
   [ ! -d $WORKSPACE/.metadata/.plugins/org.eclipse.core.resources/.projects/org.xtuml.bp.als ] ||
   [ ! -d $WORKSPACE/.metadata/.plugins/org.eclipse.core.resources/.projects/org.xtuml.bp.ui.canvas ] ||
   [ ! -d $WORKSPACE/.metadata/.plugins/org.eclipse.core.resources/.projects/org.xtuml.bp.core ]; then
  importProjects
fi
# always prebuild for now rather than try to fully
# cover dependencies
preBuildProjects

cd $prev_dir

exit 0
