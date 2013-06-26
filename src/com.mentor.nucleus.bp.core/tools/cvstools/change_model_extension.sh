#!/bin/bash
#=====================================================================
#
# File:      $RCSfile: change_model_extension.sh,v $
# Version:   $Revision: 1.8 $
# Modified:  $Date: 2012/01/23 21:27:50 $
#
#(c) Copyright 2005-2012 by Mentor Graphics Corp. All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================
#
# Script to change the extension for a tiger model
# and the parent folder if requested
#

if [ "$1" = "-h" ]; then
  echo -e "$0: Script for changing tiger model extensions and"
  echo -e "optionally changing the storage location\n\n"
  echo -e "Available arguments: repository_path, new_extension,"
  echo -e "                     original_extension, original_location,"
  echo -e "                     new_location, username.\n"
  echo -e "repository_path      - The path to the repository which contains"
  echo -e "                       the modules that need to change."
  echo -e "new_extension        - The new extension of tiger models."
  echo -e "original_extension   - The original extension of tiger models."
  echo -e "original_location    - The original location of tiger models."
  echo -e "new_location         - The new directory to store tiger models.\n"
  echo -e "The username argument is used to change ownership on all"
  echo -e "changed files so that any user may access them."
  exit 0
fi

if [ $# -lt 4 ]; then
  echo "Usage: $0 repository_path new_extension original_extension original_location new_location"
  echo "See $0 -h for more information"
  exit 2
fi

repository=$1
new_extension=$2
original_extension=$3
original_path=$4
# initialize new path as a string since it may not be set
new_path=""
new_path=$5
user=$6
if [ "$user" = "" ]; then
  user=cvstestuser
fi

if [ -d $1 ]; then
  cd $1
  # copy all model files
  all_model_files=`find . -name *.mdl,v`
  # check that at least one was found
  model_files_check=`echo $all_model_files | grep -c "*"`
  if [ $model_files_check = 0 ]; then
    for model_file in $all_model_files; do
      directory=`dirname $model_file`
      model_name_with_ext=`basename $model_file`
      model_name=`echo $model_name_with_ext | cut -d"." -f1`
      cp -a $model_file $directory/$model_name.$new_extension,v 
    done
  fi
  if [ $new_path != "" ]; then
    model_folders=`find . -name $original_path`
    if [ "$model_folders" != "" ]; then
      for model_folder in $model_folders; do
        directory=`dirname $model_folder`
        xtuml_models="$model_folder/*.xtuml,v"
        bak_models="$model_folder/*.bak,v"
        sql_models="$model_folder/*.sql,v"
        xtuml_models_check=`echo $xtuml_models | grep -c "*"`
        bak_models_check=`echo $bak_models | grep -c "*"`
        sql_models_check=`echo $sql_models | grep -c "*"`
        # check that folder is actually a models folder
        if [ "$xtuml_models_check" = 0 ] || [ "$bak_models_check" = 0 ] || [ "$sql_models_check" = 0 ]; then
          cp -ra $model_folder $directory/$new_path
        fi
      done
    fi
  fi
else
  echo "Could not find the repository directory"
fi