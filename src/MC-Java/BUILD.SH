#!/bin/sh
#====================================================================
#
# File:      $Source: /arch1/products/tiger/repository/MC-Java/BUILD.SH,v $
# Version:   $Revision: 1.9 $
# Modified:  $Date: 2013/01/10 23:16:28 $
# By:        $Author: rmulvey $
#
# (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
#
#====================================================================
#
# This script compiles the java code in the generated directory, or 
# runs the final program depending on the parameter passed in.
#
# $1 - Explicit command, either compile, or run
# See usage below

set -e
set -u

#These need to change to point to the location of 
#javac, and java
javaroot="c:/Progra~1/Java/bin"
java="${javaroot}/java"
javac="${javaroot}/javac"

#usage=" $0 <command>
#          where <command> is one of compile, or run
#        To compile the code in the generated directory, issue:
#          $0 compile
#        To run the code in the generated directory, issue:
#          $0 run"

if [ $# -ne 1 ]; then
  echo "$usage"
  exit 1
fi

command=$1

cwd=`pwd`

case $command in
  compile )
    cd generated
    $javac *.java
    cd $cwd
    ;;
  run )
    cd generated
    $java -cp . Odms foo bar zar 
    cd $cwd
    ;;
  * )
    echo "Command $command not supported"
    echo "$usage"
    exit 2
    ;;
esac

