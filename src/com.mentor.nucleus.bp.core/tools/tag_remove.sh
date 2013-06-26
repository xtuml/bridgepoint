#!/bin/bash

tagname="${1}"
modname="${2}"

if [ "${tagname}" == "" ]; then
  echo -e "\n\tERROR: You must give a tag name as an argument to the program.\n"
  exit
fi

if [ "${modname}" == "" ]; then
  echo -e "\n\tERROR: You must give a module name as the second argument to the program.\n"
  exit
fi

echo -e "\n\tUsing tag: ${tagname}, module name: ${modname}\n"

echo -e "Logging in to CVS as user 'build'"
export CVSROOT="/arch1/products/tiger/repository"

cvs rtag -d ${tagname} ${modname}


exit
