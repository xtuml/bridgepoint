#!/bin/sh
#====================================================================
#
# File:      $Source: /arch1/products/tiger/repository/MC-Java/GEN.SH,v $
# Version:   $Revision: 1.9 $
# Modified:  $Date: 2013/01/10 23:16:36 $
# By:        $Author: rmulvey $
#
# (c) Copyright 2003-2013 Mentor Graphics Corporation  All rights reserved.
#
#====================================================================
#
# $1 is the name of the domain
#

set -e
set -u

gi=pt_gen_import
gf=pt_gen_file
usage=" $0 <domain>
          where <domain> is the name of the .ooa file"
if [ $# -ne 1 ]; then
  echo "$usage"
fi

domain=$1
db=${domain}.gen
schema="ooa_schema.gen"
sql=${domain}.sql
obp=ooa_oal_pop.arc
arc=Java.arc

rm -rf $db

st=`date`
echo "copying the empty schema"
cp $schema $db

echo "importing the OOA schema"
$gi $db $sql

echo "generating the INSERT statements .."
echo "start time: `date`"
$gf $db $obp
echo "end time  : `date`"

echo "import the INSERT statements"
$gi $db ${domain}.bpi.sql

ofd=generated
rm -rf $ofd
mkdir $ofd

echo "generating Java code"
$gf $db $arc
et=`date`

echo "Total Time"
echo "  Start Time: $st"
echo "  End Time  : $et"

# $Log: GEN.SH,v $
# Revision 1.9  2013/01/10 23:16:36  rmulvey
# job:dts0100939343
# Updated the copyright to 2013
#
# Revision 1.8  2012/01/23 21:24:37  kbrown
# job:dts0100848212
# Batch commit of copyright updated files.
#
# Revision 1.7  2011/05/30 20:22:54  kbrown
# job:dts0100742889
# Updated copy right.
#
# Revision 1.6  2010/01/05 03:17:01  kbrown
# job:dts0100644853
# Batch commit of copyright change from 2009 to 2010 for BP CVS projects.
#
# Revision 1.5  2009/01/01 23:17:04  rmulvey
# Job:4060
# Batch promotion of copyright changes from Review-i4060 and Review-i4060-01.
#
# Revision 1.4.50.1  2008/12/31 16:22:51  rmulvey
# Job:4060
# This is a batch commit of 2009 copyright changes to branch Review-i4060.  This includes the
# report that outlines all changes made (before/after for each line changed).  This report is found here: <cvs>/Documentation/internal/test_results/R2_1_2/i4060/update-copyright-results.txt.
#
# Revision 1.4  2008/01/08 20:24:50  rmulvey
# Job:3349
# Promoted from Review-i3349.  The copyright has been updated to 2008 and the version has been bumped to 1.5.4.
#
# Revision 1.3.20.1  2007/12/21 17:55:01  rmulvey
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
# Revision 1.2.718.1  2007/09/01 01:36:28  rmulvey
# Job:2673
# Updated copyright messages and version numbers in Review-i2673-01.
#
# Revision 1.2  2004/03/19 20:52:39  greg
# Job: 143
# Changed from binary to ASCII -kkv
#
# Revision 1.3  2003/02/11 18:24:03  campbell
# BP 6.1 ODMS running
#
# Revision 1.2  2003/01/23 00:07:34  campbell
# Make work with latest tool versions
#
# Revision 1.1  2003/01/22 20:18:17  campbell
# Finalized RCS Header
#
