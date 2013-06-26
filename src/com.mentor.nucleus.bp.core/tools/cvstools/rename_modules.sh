#!/bin/bash
#=====================================================================
#
# File:      $RCSfile: rename_modules.sh,v $
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
# Script to rename parts of cvs tiger module names
#

# if the argument number does not match the expected arguments exit
# there can be four arguments, but the fourth is not needed.  Its used
# so that the script can fix the repository permissions with a good user

if [ "$1" = "-h" ]; then
  echo -e "$0: Script for renaming cvs modules which contain java packages\n\n"
  echo -e "Available arguments: module_location, original_module_part, new_module_part, username.\n"
  echo -e "module_location      - The path to the repository which contains"
  echo -e "                       the modules that need to change."
  echo -e "original_module_part - The part of the module name that is"
  echo -e "                       to be renamed, i.e, com.somecompany."
  echo -e "new_module_part      - The part to change the original_module_part"
  echo -e "                       to, i.e, com.anothercompany."
  echo -e "username             - This argument is optional, if not used the default"
  echo -e "                       cvstestuser will be used.\n"
  echo -e "The username argument is used to change ownership on all"
  echo -e "changed files so that any user may access them."
  exit 0
fi

# script must be run as root
if [ "$USER" != "root" ]; then
  echo "Log in as root, then run $0"
  exit 1
fi

if [ $# -lt 3 ]; then
  echo "Usage: $0 module_location original_module_part new_module_part"
  echo "Run command with -h for more information"
  exit 2
fi

user=$4
if [ "$4" = "" ]; then
  user=cvstestuser
fi

# function to search all files under a changed directory for the original part
# and modify it to the new part
#
# Usage: path original_module_part new_module_part end_part
#
function change_all_refs {
  cd "$1"
  # set field seperator to newline only
  IFS=$'\n'
  for file in `find .`; do
    if [ -f "$file" ]; then
      filetype=`file -b "$file"`
      if [ "$filetype" != "data" ]; then
        search_result=`grep -c "$2" $file`
        if [ $search_result -ne 0 ]; then
            chmod u+w "$file"
            cat "$file" | sed s/"$2"/$3/g > tmp_file
            mv tmp_file "$file"
            chmod u-w "$file"
        fi
      fi
    fi
  done
  # unset field seperator to return to defaults
  unset IFS
  cd ..
}

# if the original part is equal to the new part no change can be done, exit
if [ $2 = $3 ]; then
   echo "Error in arguments: the original module part must not equal the new module part"
   exit 1
fi

# if the given path to the modules cannot be found exit with error
if [ -d $1 ]; then
  cd $1
  
  # get list of modules in given directory
  modules="$2.*"

  # check that at least one module was found
  module_check=`echo $modules | grep -c "*"`

  if [ $module_check -gt 0 ]; then
    echo "Error unable to find any modules that contain the original module part given"
    exit 1
  fi

  # split the original part and the new part for a count
  # add each separate part to an array
  original_split=`echo $2 | cut -d"." -f- --output-delim=" "`
  change_split=`echo $3 | cut -d"." -f- --output-delim=" "`
  # count the number of parts given
  original_part_count=0
  for part in $original_split; do
    original_part_count="$(( $original_part_count + 1 ))"
    original_part[$original_part_count]=$part
  done
  change_part_count=0
  for part in $change_split; do
    change_part_count="$(( $change_part_count + 1 ))"
    change_part[$change_part_count]=$part
  done

  # modify original part to work with search
  # this adds a \ to the search pattern
  count=0
  search_string=""
  for search_string_part in $original_split; do
    count=$(( $count + 1 ))
    if [ $count != $original_part_count ]; then 
      search_string=$search_string$search_string_part\\.
    else
      search_string=$search_string$search_string_part
    fi
  done
  
  # build original directory path
  x=0
  original_path=""
  while [ $x -lt $original_part_count ]; do
    x=$(( $x + 1 ))
    original_path=$original_path/${original_part[$x]}
  done

  # build new src directory path
  x=0
  new_path=""
  while [ $x -lt $change_part_count ]; do
    x=$(( $x + 1 ))
    new_path=$new_path/${change_part[$x]}
  done

  # modify orginal path to work with search
  # this adds a \ to the search pattern
  count=0
  path_search_string=""
  for search_string_part in $original_split; do
    count=$(( $count + 1 ))
    if [ $count != $original_part_count ]; then 
      path_search_string=$path_search_string$search_string_part\\/
    else
      path_search_string=$path_search_string$search_string_part
    fi
  done
  original_path_search_string=$path_search_string

  # modify new path to work with search
  # this adds a \ to the search pattern
  count=0
  path_search_string=""
  for search_string_part in $change_split; do
    count=$(( $count + 1 ))
    if [ $count != $change_part_count ]; then 
      path_search_string=$path_search_string$search_string_part\\/
    else
      path_search_string=$path_search_string$search_string_part
    fi
  done
  new_path_search_string=$path_search_string

  for module in $modules; do

    # gather the common part
    end_part=`echo $module | cut -d"." -f$(( $original_part_count + 1 ))-`

    # store new module name
    new_module=$3.$end_part
            
    # create new module
    mkdir $new_module

    # get the folder of the first common part
    end_parent_folder=`echo $module | cut -d"." -f$(( $original_part_count + 1 ))`

    # copy original contents to new module
    original_contents="$module/* $module/.[A-Z,a-z]*"
    original_contents_check=`echo $original_contents | grep -c "*"`
    if [ $original_contents_check = 0 ]; then
      for element in $original_contents; do
        cp -r $element $new_module
      done
      # remove common src part as it is copied later for
      # simplifications
      rm -rf $new_module/src/$original_path
    fi
     
    # if a src dir exists try to rename folder structure
    if [ -d $module/src/$original_path ]; then
            
      new_path_full=$new_module/src/$new_path
            
      # create the new src directory structure
      mkdir -p $new_path_full

      # copy source contents to new module
      cp -ra $module/src/$original_path/$end_parent_folder $new_path_full
	    
    fi
    
    # change all references to old part in new module
    change_all_refs $new_module $search_string $3
    change_all_refs $new_module "$original_path_search_string" "$new_path_search_string"
    
    # do a recursive ownership change so that all may access
    chown -R $user:staff $new_module
    
    # also change directory permissions to allow group writes
    IFS=$'\n'
    for dir in `find $new_module`; do
      if [ -d "$dir" ]; then
        chmod ug+rw "$dir"
      fi
    done
    unset IFS   
  done
    
else

  echo "Could not locate the module path specified."
  exit 1

fi