#!/bin/bash
BPHOMEDIR="C:/mgc/BridgePoint"
BP_VERSION="4.2.1"
WORKSPACE="$HOME/workspace"

function die() {
  echo -e $@
  exit 1
}

function displayHelp() {
  echo -e ""
  echo -e "Usage:  ./CLI.sh <Build | Import | Execute> <Argument List>"
  echo -e ""
  echo -e "Example:"
  echo -e "  ./CLI.sh Build -project MicrowaveOven -buildConfig Debug -cleanCLI"
  echo -e ""
  echo -e "To list complete usage information for a command:"
  echo -e "   ./CLI.sh Build -help"
  echo -e "   ./CLI.sh Import -help"
  echo -e "   ./CLI.sh Execute -help"
  echo -e "   ./CLI.sh Merge -help"
  echo -e ""
  echo -e "Note that you must edit the WORKSPACE variable inside this CLI.sh"
  echo -e "to point at the appropriate workspace."
  echo -e ""
  echo -e "For additional information, see the Command Line Interface "
  echo -e "documentation inside the Help system."
  echo -e "" 
  exit 2
}

# Check that a valid argument is specified
if [ "$1" != "Build" ]; then
  if [ "$1" != "Execute" ]; then
    if [ "$1" != "Import" ]; then
       if [ "$1" != "Merge" ]; then
          displayHelp
       fi
    fi
  fi
fi

#
# DO NOT MODIFY ANY OF THE FOLLOWING LINES.
#
export ORIGINAL_PATH=$PATH
export PATH=$PATH:$BPHOMEDIR/tools/docgen/docbook
export MGC_EMBEDDED_HOME=$BPHOMEDIR
LAUNCHER="$BPHOMEDIR/eclipse/plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar"
APPLICATION="com.mentor.nucleus.bp.cli.$1"

$BPHOMEDIR/jre/bin/java  -d32 -Xms256m -Xmx1g -XX:MaxPermSize=256m -jar $LAUNCHER -clean -noSplash -product com.mentor.nucleus.bp.pkg.BridgePoint -data $WORKSPACE -application $APPLICATION $2 "$3" $4 "$5" $6 "$7" $8 "$9"

# Restore the path
export PATH=$ORIGINAL_PATH

exit 0
