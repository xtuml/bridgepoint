#!/bin/bash
# This command gets the location of the directory that holds this script.  We
# use this to set up the rest of the paths for launching.  If you want to copy
# this script elsewhere, you should modify BPHOMEDIR to explicitly set the 
# location where BridgePoint is installed to.
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
BPHOMEDIR="$DIR/.."

#
# DO NOT MODIFY ANY OF THE FOLLOWING LINES.
#
export GDK_NATIVE_WINDOWS=true
export BP_JVM=$BPHOMEDIR/jre/lib/i386/client/libjvm.so

$BPHOMEDIR/eclipse/eclipse -vm $BP_JVM $1 $2 $3 $4 $5 $6 $7 $8 $9

