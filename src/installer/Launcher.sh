#!/bin/bash

# BPHOMEDIR is used at runtime to determine the base installation folder.
# It is the BridgePoint folder.
export BPHOMEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )/.." && pwd )

# The JVM tested with BridgePoint
export BP_JVM=$BPHOMEDIR/jre/lib/i386/client/libjvm.so

# GDK_NATIVE_WINDOWS=1 simply makes sure that every GDK window gets its native 
# X window, making problematic applications work better.
export GDK_NATIVE_WINDOWS=true

$BPHOMEDIR/eclipse/eclipse -vm $BP_JVM $1 $2 $3 $4 $5 $6 $7 $8 $9

