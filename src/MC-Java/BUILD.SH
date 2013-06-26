#!/bin/sh
#====================================================================
#
# File:      $Source: /arch1/products/tiger/repository/MC-Java/BUILD.SH,v $
# Version:   $Revision: 1.9 $
# Modified:  $Date: 2013/01/10 23:16:28 $
# By:        $Author: rmulvey $
#
# (c) Copyright 2003-2013 Mentor Graphics Corporation  All rights reserved.
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

# $Log: BUILD.SH,v $
# Revision 1.9  2013/01/10 23:16:28  rmulvey
# job:dts0100939343
# Updated the copyright to 2013
#
# Revision 1.8  2012/01/23 21:24:37  kbrown
# job:dts0100848212
# Batch commit of copyright updated files.
#
# Revision 1.7  2011/05/30 20:22:53  kbrown
# job:dts0100742889
# Updated copy right.
#
# Revision 1.6  2010/01/05 03:17:01  kbrown
# job:dts0100644853
# Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
#
# Revision 1.5  2009/01/01 23:17:03  rmulvey
# Job:4060
# Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
#
# Revision 1.4.50.1  2008/12/31 16:22:51  rmulvey
# Job:4060
# This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
# report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
#
# Revision 1.4  2008/01/08 20:24:49  rmulvey
# Job:3349
# Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
#
# Revision 1.3.20.1  2007/12/21 17:55:00  rmulvey
# Job:3339
# Checking-in the copyriight changes in branch Review-i3349.  A detailed list of these changes is found
# in the report produced by the utility used to changesthese messages.  This report is here:
# Documentation/internal/test_results/R1_5_4/i3349/update-copyright-results.txt
#
# Revision 1.3  2007/09/05 02:43:10  kbrown
# Job:2673
# Promoting copyright changes for projects:
# MC-Java
# MC-Java.test
# pt_antlr
#
# Revision 1.2.718.1  2007/09/01 01:36:22  rmulvey
# Job:2673
# Updated copyright messages and version numbers in Review-i2673-01.
#
# Revision 1.2  2004/03/19 20:52:39  greg
# Job: 143
# Changed from binary to ASCII -kkv
#
# Revision 1.1  2003/01/23 00:15:55  campbell
# Changed Name and Java installation pointer.
#
# Revision 1.1  2003/01/22 20:04:16  campbell
# Finalize RCS Header
#
# Revision 1.5  2003/01/22 19:51:16  campbell
# Finalized RCS Header
#
# Revision 1.4  2003/01/22 19:50:07  campbell
# Updated all headers to RCS
#
