#!/bin/bash

##
## Note: This script is written so that is uses environment variables from the 
## server build script, if present.
##

original_dir="$PWD"
cd ${WORKSPACE}/.metadata/bridgepoint/build/log

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