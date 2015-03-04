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
ant_opts="-Declipse-home=${ECLIPSE_HOME}"
cli_cmd="${ECLIPSE_HOME}/CLI.sh"
cli_opts="-nl en_US -consoleLog -pluginCustomization ${BUILD_DIR}/org.xtuml.bp.pkg/plugin_customization.ini -prebuildOnly"
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
        echo -e "Error checking out ${module} with tag: ${BRANCH}"
        return 1
    fi
}

function get_required_modules {
    cp -rf ${GIT_BP}/src/${RELEASE_PKG} .
    chown -R ${SHELLUSER} ${RELEASE_PKG}

    if [ -e ${RELEASE_PKG}/feature.xml ]; then
        plugin_modules=`grep "<plugin id=" $BUILD_DIR/$RELEASE_PKG/feature.xml | awk -F"=" '{printf("%s\n", $2)}' | sed s/\"// | sed s/\"//`
        release_version=`awk -F"\"" '{if (/[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${BUILD_DIR}/${RELEASE_PKG}/feature.xml`
        plugin_modules="${plugin_modules} ${independent_modules}"
        echo "release version: ${release_version}"
    fi
    
    cp -rf ${GIT_BP}/src/${antlr_tool} .
    chown -R ${SHELLUSER} ${antlr_tool}    
}

function extract_release_files {
	echo -e "Entering create_release_functions.sh::extract_release_files"
	
    modules="${internal_modules} ${plugin_modules}"

    # Rearrange modules so that core is built first
    modules=`echo ${modules} | sed s/org.xtuml.bp.core// | sed s/^/"org.xtuml.bp.core "/`

    for module in ${modules} ${all_feature_modules} ${model_compiler_modules} ${plugin_fragments}; do
        echo "Checking out ${module} for release: ${BRANCH}"
        cp -rf ${GIT_BP}/src/${module} .
        chown -R ${SHELLUSER} ${module}
    done
	echo -e "Exiting create_release_functions.sh::extract_release_files"
}

function extract_unit_test_modules {
	echo -e "Entering create_release_functions.sh::extract_unit_test_modules"
    for module in ${unit_test_modules}; do
        echo "Checking out ${module} for release: ${BRANCH}"
		cp -rf ${GIT_BP}/src/${module} .
        chown -R ${SHELLUSER} ${module}
    done
	echo -e "Exiting create_release_functions.sh::extract_unit_test_modules"
}

function build_modules {
	echo -e "Entering create_release_functions.sh::build_modules"

    # remove a number of plugins from the list of modules to build and compile
    modules=`echo ${modules} | sed s/org.xtuml.bp.bld.pkg// | sed s/org.xtuml.bp.doc// | sed s/org.xtuml.bp.welcome// | sed s/org.xtuml.bp.test// | sed s/org.xtuml.help.bp.mc//`

    cd ${BUILD_DIR}

    for module in ${modules}; do
        if [ -e ${module}/generate.xml ]; then
            echo -e "Building version ${BRANCH} of ${module} with the following command lines:"
            echo -e "        ${cli_cmd} ${cli_opts} -project ${module}"
            echo -e "        ${ant_cmd} ${ant_opts} -f ${module}/generate.xml nb_all"
            ${cli_cmd} ${cli_opts} -project ${module}
            ${ant_cmd} ${ant_opts} -f ${module}/generate.xml nb_all > ${build_log_dir}/${module}_build.log 2>&1
        elif [ -e ${module}/build.xml ] && [ ! -e ${module}/generate.xml ]; then
            echo -e "Building version ${BRANCH} of ${module}"
            ${ant_cmd} ${ant_opts} -f ${module}/build.xml nb_all > ${build_log_dir}/${module}_build.log 2>&1
        fi
    done

    # Check for errors and place in a temp file for later use.
    for module in ${modules}; do
        # Special case to exclude als.oal package as its built from als
        if [ ${module} != "org.xtuml.bp.als.oal" ] && [ ${module} != "org.xtuml.bp.ui.tree" ] && [ ${module} != "org.xtuml.bp.internal.tools" ]; then
            # Check for all cases of error, failed, and failure
            error_count=`grep -c -i -w "ERROR" ${build_log_dir}/${module}_build.log`
            failed_count=`grep -c -i -w "FAILED" ${build_log_dir}/${module}_build.log`
            failure_count=`grep -c -i -w "FAILURE" ${build_log_dir}/${module}_build.log`

            if [ ${error_count} -gt 0 ] || [ ${failed_count} -gt 0 ] || [ ${failure_count} -gt 0 ]; then
                build_log_path=`cygpath -m ${build_log_dir}/${module}_build.log`
                echo -e "Errors or failures found during the build of $module.  Check ${build_log_path}.\n" >> ${error_file}
            fi
        fi
    done

    modules="${modules} org.xtuml.bp.welcome"
	echo -e "Exiting create_release_functions.sh::build_modules"
}

function compile_modules {
	echo -e "Entering create_release_functions.sh::compile_modules"

    build_modules

    # Have to make sure the plugin compilation is ordered properly.
    # Move bp.utilities so it compiles to before bp.mc, and move several others to later in the build order.
    modules=`echo ${modules} | sed s/org.xtuml.bp.docgen// | sed s/org.xtuml.bp.cdt// | sed s/org.xtuml.bp.utilities// | sed s/org.xtuml.bp.welcome// | sed s/org.xtuml.bp.cli//`
    modules=`echo ${modules} | sed 's/org.xtuml.bp.mc /org.xtuml.bp.utilities org.xtuml.bp.mc /'`
    modules_to_compile_later="org.xtuml.bp.docgen org.xtuml.bp.cdt org.xtuml.bp.welcome org.xtuml.bp.cli"
    
    cd ${BUILD_DIR}

    for module in ${modules}; do
        if [ -e ${module}/generate.xml ]; then
            echo -e "Compiling version ${BRANCH} of ${module}"
            ${ant_cmd} ${ant_opts} -f ${module}/generate.xml compile > ${compile_log_dir}/${module}_compile.log 2>&1
        elif [ -e ${module}/build.xml  ] && [ ! -e ${module}/generate.xml ]; then
            echo -e "Compiling version ${BRANCH} of ${module}"
            ${ant_cmd} ${ant_opts} -f ${module}/build.xml compile > ${compile_log_dir}/${module}_compile.log 2>&1
        fi
    done

    for module in ${modules_to_compile_later}; do
        if [ -e ${module}/generate.xml ]; then
            echo -e "Compiling version ${BRANCH} of ${module}"
             ${ant_cmd} ${ant_opts} -f ${module}/generate.xml compile > ${compile_log_dir}/${module}_compile.log 2>&1
        elif [ -e ${module}/build.xml  ] && [ ! -e ${module}/generate.xml ]; then
            echo -e "Compiling version ${BRANCH} of ${module}"
            ${ant_cmd} ${ant_opts} -f ${module}/build.xml compile > ${compile_log_dir}/${module}_compile.log 2>&1
        fi
    done
    
    modules="${modules} ${modules_to_compile_later}"
    
    # Check for errors and place in a temp file for later use.
    for module in ${modules}; do
        # Special case to exclude als.oal package as its compiled from als
        if [ ${module} != "org.xtuml.bp.als.oal" ] && [ ${module} != "org.xtuml.bp.ui.tree" ] && [ ${module} != "org.xtuml.bp.internal.tools" ]; then
            # Check for all cases of error, failed, and failure
            error_count=`grep -c -i -w "ERROR" ${compile_log_dir}/${module}_compile.log`
            failed_count=`grep -c -i -w "FAILED" ${compile_log_dir}/${module}_compile.log`
            failure_count=`grep -c -i -w "FAILURE" ${compile_log_dir}/${module}_compile.log`

            if [ ${error_count} -gt 0 ] || [ ${failed_count} -gt 0 ] || [ ${failure_count} -gt 0 ]; then
                compile_log_path=`cygpath -m ${compile_log_dir}/${module}_compile.log`
                echo -e "Errors or failures found during the compilation of ${module}. Check ${compile_log_path}.\n" >> ${error_file}
            fi
        fi
    done
	echo -e "Exiting create_release_functions.sh::compile_modules"
}


