# This script is used to prepare an eclipse workspace for building.
# It will copy the required configuration data to a new workspace
# and pre-build all required xtuml projects.  After completing the
# the tool is ready to build.

#!/bin/bash

source build_configuration.sh

prev_dir=`pwd`

function importProjects {
  dir=`pwd`
  cd $GIT_DIR
  java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data ~/workspace -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll bridgepoint/src
  java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data ~/workspace -application org.eclipse.ereDevelopmentWorkspacedt.managedbuilder.core.headlessbuild -importAll bridgepoint/doc-bridgepoint
  java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data ~/workspace -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll bridgepoint/releng
  java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data ~/workspace -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll bridgepoint/utilities
  if [ "$INCLUDE_TESTS" == "true" ]; then
    java -cp ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar org.eclipse.equinox.launcher.Main -data ~/workspace -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll bptest/src
  fi
  cd $dir
}

function preBuildProjects {
  JAVA_ARGS=""
  if [ "$(uname)" == "Darwin" ];then
    JAVA_ARGS="-XstartOnFirstThread"
  fi
  java -Xms256m -Xmx1g $JAVA_ARGS -jar ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_*.jar -clean -noSplash -product org.xtuml.bp.pkg.BridgePoint -data ~/workspace -application org.xtuml.bp.cli.Build -project org.xtuml.bp.core -prebuildOnly
  java -Xms256m -Xmx1g $JAVA_ARGS -jar ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_*.jar -clean -noSplash -product org.xtuml.bp.pkg.BridgePoint -data ~/workspace -application org.xtuml.bp.cli.Build -project org.xtuml.bp.als -prebuildOnly
  java -Xms256m -Xmx1g $JAVA_ARGS -jar ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_*.jar -clean -noSplash -product org.xtuml.bp.pkg.BridgePoint -data ~/workspace -application org.xtuml.bp.cli.Build -project org.xtuml.bp.ui.canvas -prebuildOnly
  if [ "$INCLUDE_TESTS" == "true" ]; then
      java -Xms256m -Xmx1g $JAVA_ARGS -jar ${bp_install_dir}/plugins/org.eclipse.equinox.launcher_*.jar -clean -noSplash -product org.xtuml.bp.pkg.BridgePoint -data ~/workspace -application org.xtuml.bp.cli.Build -project org.xtuml.bp.core.test -prebuildOnly
  fi
}

function prepareDevelopmentWorkspace {
  # create the workspace if it does not
  # exist and set it up for development
  if [ ! -d ~/workspace ]; then
    mkdir -p ~/workspace/.metadata/.plugins/org.eclipse.core.runtime/.settings
  fi
  cp -f $GIT_DIR/bridgepoint/utilities/build/preferences/*  ~/workspace/.metadata/.plugins/org.eclipse.core.runtime/.settings
  cp -f $GIT_DIR/pt_antlr/pt_antlr/antlr.jar  $GIT_DIR/bridgepoint/src/org.xtuml.bp.als/lib/antlr.jar
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
prepareDevelopmentWorkspace
importProjects
preBuildProjects

cd $prev_dir

exit 0
