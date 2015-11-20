#!/bin/bash
# This command gets the location of the directory that holds this script.  We
# use this to set up the rest of the paths for launching.  If you want to copy
# this script elsewhere, you should modify BPHOMEDIR to explicitly set the 
# location where BridgePoint is installed to.
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
BPHOMEDIR="$DIR/.."

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

if [ "${WORKSPACE}" == "" ]; then
    #
    # Since the workspace to use is not set, use the standard location.  
    # Users may choose to set the value in the environment, or modify 
    # the following line to point to the correct location.    
    #
    export WORKSPACE="$HOME/workspace"
fi

[ $WORKSPACE ] || die "No workspace to use is set up in ENV variable WORKSPACE."

#
# DO NOT MODIFY ANY OF THE FOLLOWING LINES.
#
LAUNCHER="$BPHOMEDIR/eclipse/plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar"
APPLICATION="org.xtuml.bp.cli.$1"

$BPHOMEDIR/jre/bin/java  -d32 -Xms256m -Xmx1g -XX:MaxPermSize=256m -jar $LAUNCHER -clean -noSplash -product org.xtuml.bp.pkg.BridgePoint -data $WORKSPACE -application $APPLICATION $2 "$3" $4 "$5" $6 "$7" $8 "$9"

exit 0
