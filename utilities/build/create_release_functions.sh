#!/bin/bash -u
#
#    create_release_functions.sh - Contains all functions necessary for 
#     building a release
#
#    The following variables must be defined in any calling script
#
#    BUILD_DIR - the directory for which to build the release
#    LOG_DIR - the directory where logs will be kept.
#    BRANCH - the branch/tag representing the release version
#
#
echo -e "Entering create_release_functions.sh"

build_log_dir="${LOG_DIR}/build_logs"

if [ ! -d ${build_log_dir} ]; then
    mkdir -p $build_log_dir
fi

echo -e "Exiting create_release_functions.sh"


function check_build_modules {
	echo -e "Entering create_release_functions.sh::check_build_modules"

    # remove a number of plugins from the list of modules to build and compile
    modules=`echo ${modules} | sed s/org.xtuml.bp.bld.pkg// | sed s/org.xtuml.bp.doc// | sed s/org.xtuml.bp.welcome// | sed s/org.xtuml.bp.test// | sed s/org.xtuml.help.bp.mc//`

    cd ${BUILD_DIR}

	#
	# Check for errors and place in a temp file for later use.
	for module in ${modules}; do
		module_build_log="${build_log_dir}/${module}_build.log"
	    # Special case to exclude als.oal package as its built from als
	    if [ ${module} != "org.xtuml.bp.als.oal" ] && [ ${module} != "org.xtuml.bp.ui.tree" ] && [ ${module} != "org.xtuml.bp.internal.tools" ]; then
	        Check for all cases of error, failed, and failure
	        grep -c -i -w "ERROR" ${module_build_log}
	        error_count=$?
	        grep -c -i -w "FAILED" ${module_build_log}
	        failed_count=$?
	        grep -c -i -w "FAILURE" ${module_build_log}
	        failure_count=$?
	
	        if [ ${error_count} -ne 1 ] || [ ${failed_count} -ne 1 ] || [ ${failure_count} -ne 1 ]; then
	            echo -e "Errors or failures found during the build of $module.  Check ${module_build_log}.\n" >> ${ERROR_FILE}
	        fi
	    fi
	done

	echo -e "Exiting create_release_functions.sh::check_build_modules"
}

