#!/bin/bash

if [ "$#" -lt 1 ]; then
  echo "This script has required parameters.  See below for usage."
  echo
  echo "verify_build.sh Build_dir"
  echo ""
  echo "  Build_dir - path to the workspace being built"
  echo ""
  exit 1
fi 

workspace="$1"

original_dir="$PWD"
cd ${workspace}/.metadata/bridgepoint/build/log

# Check for all cases of error, failed, and failure
grep -c -i -w "ERROR" *
error_count=$?
grep -c -i -w "FAILED" *
failed_count=$?
grep -c -i -w "FAILURE" *
failure_count=$?

cd $original_dir

if [ ${error_count} -ne 1 ] || [ ${failed_count} -ne 1 ] || [ ${failure_count} -ne 1 ]; then
	echo -e "Errors or failures found during the build.\n"
	exit 1
fi

exit 0