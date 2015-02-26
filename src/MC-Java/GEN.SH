#!/bin/sh
#====================================================================
#
# File:      $Source: /arch1/products/tiger/repository/MC-Java/GEN.SH,v $
# Version:   $Revision: 1.9 $
# Modified:  $Date: 2013/01/10 23:16:36 $
# By:        $Author: rmulvey $
#
# (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
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
arc=Java.arc

rm -rf $db

st=`date`
echo "copying the empty schema"
cp $schema $db

echo "importing the OOA schema"
$gi $db $sql

ofd=generated
rm -rf $ofd
mkdir $ofd

echo "generating Java code"
$gf $db $arc
et=`date`

echo "Total Time"
echo "  Start Time: $st"
echo "  End Time  : $et"

