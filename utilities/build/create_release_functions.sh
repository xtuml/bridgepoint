#!/bin/bash -u
#
#    create_release_functions.sh - Contains all functions necessary for 
#     building a release
#
#    The following variables must be defined in any calling script
#
#    GIT_REPO_ROOT - the directory that is the parent folder of the git repositories
#    BUILD_DIR - the directory for which to build the release
#    LOG_DIR - the directory where logs will be kept.
#    BRANCH - the branch/tag representing the release version
#    RELEASE_PKG - the eclipse feature package
#
#
# Note: ant should be installed on the build server
#
echo -e "Entering create_release_functions.sh"
ant_cmd="ant"
ant_opts="-Declipse-home=${GIT_BP}"

#
# Note that the Bridgepoint cli uses the environment variable "WORKSPACE"
# as the location for the workspace being built.  This must be set properly
# for the cli build to function.  This is why we have a copy of the CLI.sh
# in the utilities/build folder that does NOT set WORKSPACE.  It allows us to
# set it here so it is not overriden
#
cli_cmd="${ECLIPSE_HOME}/CLI.sh"
cli_opts="-nl en_US -consoleLog -pluginCustomization ${BUILD_DIR}/plugin_customization.ini"

antlr_tool="pt_antlr"
internal_modules="org.xtuml.bp.als
                  org.xtuml.bp.internal.tools
                  org.xtuml.bp.test
                  org.xtuml.bp.ui.tree"
unit_test_modules="org.xtuml.bp.als.oal.test
                   org.xtuml.bp.core.test
                   org.xtuml.bp.io.sql.test
                   org.xtuml.bp.io.mdl.test
                   org.xtuml.bp.ui.canvas.test
                   org.xtuml.bp.ui.explorer.test
                   org.xtuml.bp.ui.text.test
                   org.xtuml.bp.ui.properties.test
                   org.xtuml.bp.compare.test
                   org.xtuml.internal.test
                   org.xtuml.bp.search.test
                   MC-Java.test
                   org.xtuml.bp.welcome.test
                   org.xtuml.bp.debug.ui.test"
independent_modules="org.xtuml.bp.mc.xmiexport
                     org.xtuml.bp.mc
                     org.xtuml.bp.mc.none
                     org.xtuml.bp.mc.c.source
                     org.xtuml.bp.mc.c.binary
                     org.xtuml.bp.mc.systemc.source
                     org.xtuml.bp.mc.cpp.source
                     org.xtuml.bp.mc.vhdl.source
                     org.xtuml.bp.mc.java.source
                     org.xtuml.bp.mc.template
                     org.xtuml.help.bp.mc
                     org.xtuml.bp.ui.session
                     org.xtuml.bp.debug.ui"
feature_pkg_modules="org.xtuml.bp.dap.pkg
                      org.xtuml.bp.pkg
                     org.xtuml.bp.verifier.pkg"
feature_modules="org.xtuml.bp.dap.pkg-feature
                 org.xtuml.bp.pkg-feature
                 org.xtuml.bp.verifier.pkg-feature"
plugin_fragments=""
all_feature_modules="$feature_pkg_modules $feature_modules"
model_compiler_modules="MC-Java"

build_log_dir="${LOG_DIR}/build_logs"
compile_log_dir="${LOG_DIR}/compile_logs"

if [ ! -d ${build_log_dir} ]; then
    mkdir -p $build_log_dir
fi

if [ ! -d ${compile_log_dir} ]; then
    mkdir -p ${compile_log_dir}
fi
echo -e "Exiting create_release_functions.sh"


function verify_checkout {
    dir_count=`ls ${module} | wc -l`

    if [ ${dir_count} -le 1 ]; then
        echo -e "Error in create_release_functions.sh::verify_checkout while checking out ${module} with tag: ${BRANCH}"
        return 1
    fi
    return 0
}

function get_required_modules {    
    # Import the top level release package into the build workspace
    ${cli_cmd} Import ${cli_opts} -deleteExisting -project "${GIT_BP}/src/${RELEASE_PKG}" 
    chown -R ${SHELLUSER} ${RELEASE_PKG}

    if [ -e ${RELEASE_PKG}/feature.xml ]; then
    	# Do the dos2unix conversion using translate
    	tr -d '\r' < "$BUILD_DIR/$RELEASE_PKG/feature.xml" > "$BUILD_DIR/$RELEASE_PKG/feature.xml.tmp"
    	mv "$BUILD_DIR/$RELEASE_PKG/feature.xml.tmp" "$BUILD_DIR/$RELEASE_PKG/feature.xml"
        plugin_modules=`grep "<plugin id=" $BUILD_DIR/$RELEASE_PKG/feature.xml | awk -F"=" '{printf("%s\n", $2)}' | sed s/\"// | sed s/\"//`
        release_version=`awk -F"\"" '{if (/[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${BUILD_DIR}/${RELEASE_PKG}/feature.xml`
        plugin_modules="${plugin_modules} ${independent_modules}"
        echo "release version: ${release_version}"
    fi
    
    # Import ${antlr_tool} into the build workspace
    ${cli_cmd} Import ${cli_opts} -deleteExisting -project "${GIT_BP}/src/${antlr_tool}"         
    chown -R ${SHELLUSER} ${antlr_tool}    
}

function extract_release_files {
	echo -e "Entering create_release_functions.sh::extract_release_files"
	
    modules="${internal_modules} ${plugin_modules}"

    # Rearrange modules so that core is built first
    modules=`echo ${modules} | sed s/org.xtuml.bp.core// | sed s/^/"org.xtuml.bp.core "/`

	# Import all of the projects we need into the build workspace
    for module in ${modules} ${all_feature_modules} ${model_compiler_modules} ${plugin_fragments}; do
        echo "Importing into build workspace: ${module} for release: ${BRANCH}"
        ${cli_cmd} Import ${cli_opts} -deleteExisting -project "${GIT_BP}/src/${module}"
        chown -R ${SHELLUSER} ${module}
    done
	echo -e "Exiting create_release_functions.sh::extract_release_files"
}

function extract_unit_test_modules {
	echo -e "Entering create_release_functions.sh::extract_unit_test_modules"
    for module in ${unit_test_modules}; do
        echo "Importing into build workspace: ${module} for release: ${BRANCH}"
		${cli_cmd} Import ${cli_opts} -deleteExisting -project "${GIT_BP}/src/${module}"
        chown -R ${SHELLUSER} ${module}
    done
	echo -e "Exiting create_release_functions.sh::extract_unit_test_modules"
}

function build_modules {
	echo -e "Entering create_release_functions.sh::build_modules"

    # remove a number of plugins from the list of modules to build and compile
    modules=`echo ${modules} | sed s/org.xtuml.bp.bld.pkg// | sed s/org.xtuml.bp.doc// | sed s/org.xtuml.bp.welcome// | sed s/org.xtuml.bp.test// | sed s/org.xtuml.help.bp.mc//`

    cd ${BUILD_DIR}

	###  Clean build
	bp_jvm=$ECLIPSE_HOME/../jre/lib/i386/client/libjvm.so
	${ECLIPSE_HOME}/eclipse -vm $bp_jvm -application org.eclipse.cdt.managedbuilder.core.headlessbuild -cleanBuild all -data "$WORKSPACE"
	
	## build bp.core first
	${ECLIPSE_HOME}/eclipse -vm $bp_jvm -application org.eclipse.cdt.managedbuilder.core.headlessbuild -build "org.xtuml.bp.core" -data "$WORKSPACE"
	
	## build all
	${ECLIPSE_HOME}/eclipse -vm $bp_jvm -application org.eclipse.cdt.managedbuilder.core.headlessbuild -build "org.xtuml.bp.core" -data "$WORKSPACE"
	
	## touch a a generated filess to fix dependencies
	if [ -e "$BPBUILD/git/xtuml/bridgepoint/plugin.xml" ]; then 
	  touch "$BPBUILD/git/xtuml/bridgepoint/plugin.xml"
	fi
	
	## build all again
	${ECLIPSE_HOME}/eclipse -vm $bp_jvm -application org.eclipse.cdt.managedbuilder.core.headlessbuild -build "org.xtuml.bp.core" -data "$WORKSPACE"

# Don't bother checking the build logs, there is too much cruft from CLI and
# ordering issues
#
#    # Check for errors and place in a temp file for later use.
#    for module in ${modules}; do
#    	module_build_log="${build_log_dir}/${module}_build.log"
#        # Special case to exclude als.oal package as its built from als
#        if [ ${module} != "org.xtuml.bp.als.oal" ] && [ ${module} != "org.xtuml.bp.ui.tree" ] && [ ${module} != "org.xtuml.bp.internal.tools" ]; then
#            # Check for all cases of error, failed, and failure
#            grep -c -i -w "ERROR" ${module_build_log}
#            error_count=$?
#            grep -c -i -w "FAILED" ${module_build_log}
#            failed_count=$?
#            grep -c -i -w "FAILURE" ${module_build_log}
#            failure_count=$?
#
#            if [ ${error_count} -ne 1 ] || [ ${failed_count} -ne 1 ] || [ ${failure_count} -ne 1 ]; then
#                echo -e "Errors or failures found during the build of $module.  Check ${module_build_log}.\n" >> ${ERROR_FILE}
#            fi
#        fi
#    done

	echo -e "Exiting create_release_functions.sh::build_modules"
}

