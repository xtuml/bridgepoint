#!/bin/bash
BPHOMEDIR="C:/mgc/BridgePoint"
BP_VERSION="4.1.12"

function die() {
  echo -e $@
  exit 1
}

if [ "${MGLS_LICENSE_FILE}" == "" ]; then
  if [ "${LM_LICENSE_FILE}" == "" ]; then
    #
    # Since the license path is not already set, use the standard location.  BridgePoint users may
    # choose to set the value in the environment, or modify the following line to point to the correct
    # location (either local or remote).
    #
    # example configuration for a remote license server
    #    export MGLS_LICENSE_FILE=1717@svr-taz-eng-05
    #
    export MGLS_LICENSE_FILE="$BPHOMEDIR/license/license.dat"
  else
    export MGLS_LICENSE_FILE=$LM_LICENSE_FILE
  fi
fi

[ $MGLS_LICENSE_FILE ] || die "No license is setup in ENV variable MGLS_LICENSE_FILE or LM_LICENSE_FILE)"

#
# DO NOT MODIFY ANY OF THE FOLLOWING LINES.
#
export ORIGINAL_PATH=$PATH
export PATH=$PATH:$BPHOMEDIR/tools/docgen/docbook
export GDK_NATIVE_WINDOWS=true
export MGC_EMBEDDED_HOME=$BPHOMEDIR
export MGLS_DLL=
#export MGLS_PKGINFO_FILE=
export MGC_HOME=
export MGLS_HOME=$BPHOMEDIR/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.core.linux.x86_$BP_VERSION/os/linux/x86
export BP_JVM=$BPHOMEDIR/jre/lib/i386/client/libjvm.so

$BPHOMEDIR/eclipse/eclipse -vm $BP_JVM $1 $2 $3 $4 $5 $6 $7 $8 $9

# Restore the path
export PATH=$ORIGINAL_PATH
