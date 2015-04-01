#!/bin/bash
BPHOMEDIR="C:/mgc/BridgePoint"

#
# DO NOT MODIFY ANY OF THE FOLLOWING LINES.
#
export ORIGINAL_PATH=$PATH
export PATH=$PATH:$BPHOMEDIR/tools/docgen/docbook
export GDK_NATIVE_WINDOWS=true
export BP_JVM=$BPHOMEDIR/jre/lib/i386/client/libjvm.so

$BPHOMEDIR/eclipse/eclipse -vm $BP_JVM $1 $2 $3 $4 $5 $6 $7 $8 $9

# Restore the path
export PATH=$ORIGINAL_PATH
