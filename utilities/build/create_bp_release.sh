#!/bin/bash
#
#	create_bp_release.sh requires the following environment variables
#
#   ${BRANCH} - product version, actually this is any branch/tag found in git
#   ${GIT_REPO_ROOT} - git repository root
#   ${BUILD_TYPE} - build type (release/nonrelease)
#
#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------

# The jar_distribution function creates a jar file for every package that 
# will be delivered
function jar_distribution {
    compile_modules

    cd $BUILD_DIR

    echo -e "Creating a jar of each project"

    for module in $plugin_modules; do
        echo -e "  Creating jar for ${module}"
        if [ $module != ${pkg_module} ] && [ $module != ${doc_module} ] && [ $module != ${doc_module_mc3020} ]; then
            cd ${module}/src
            # First copy all files other than .java from the src to the bin
            echo -e "    Ready to loop to find files to package."
            file_list=`find . -path "*.java"  -prune -o -print`
            echo -e "    The files are: ${file_list}"
            for file in ${file_list}; do
                echo -e "    Found file ${file}"
                if [ ! -d ${file} ]; then
                    mod_dir=`dirname ${file}`
                    echo -e "      mod_dir=${mod_dir}"
                    echo -e "    Copying $file to ${BUILD_DIR}/${module}/bin/${mod_dir}"
                    cp $file ${BUILD_DIR}/${module}/bin/${mod_dir}
                fi
                echo -e "      ready to look for next file"
            done
            echo -e "    Finished looping."

            cd ${BUILD_DIR}/${module}/bin

            jar_name=`grep -e "^source.*jar =" ../build.properties | awk '/^source/,/ =/ { sub("source.", ""); print $1;}' -`
            echo -e "    jar file name is ${jar_name}"
            jar -cvf ${jar_name} . > ${pkg_log_dir}/${jar_name}.log 2>&1
            echo -e "    Finished creating jar file."

            cd $BUILD_DIR
        fi
    done
}

# The zip_distribution function creates a zip file that can later be 
# unzipped into the plugin directory of an eclipse installation
function zip_distribution {
    jar_distribution

    cd $BUILD_DIR

    echo -e "Zipping the distribution"

    for module in $plugin_modules ${plugin_fragments}; do
        module_release_version="${release_version}"

        # If MANIFEST.MF or plugin.xml file exists, get plugin version from there
        if [ -e ${module}/META-INF/MANIFEST.MF ]; then
            module_release_version=`awk -F": " '{if (/[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${module}/META-INF/MANIFEST.MF`
        elif [ -e ${module}/plugin.xml ]; then
            module_release_version=`awk -F"\"" '{if (/ersion.*\=.*[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${module}/plugin.xml`
            if [ ${module} = "com.mentor.nucleus.bp.core" ]; then
              core_release_version=${module_release_version}
            fi
        fi

        echo -e "  Zipping ${module} (${module_release_version})"

        # Update the timestamp in the build ID
        if [ -e ${module}/about.mappings ]; then
            internal_version=`echo ${branch} | cut -d"_" -f 4-`

            echo -e "0=${module_release_version} ${internal_version}\n1=${TIMESTAMP}\n" > ${module}/about.mappings
        fi

        mkdir plugins/${module}_${module_release_version}

        copy_included_files ${module} plugins ${module}_${module_release_version}

        mv $module/bin/*.jar plugins/${module}_${module_release_version} > /dev/null 2>&1
    done

    mkdir ${BUILD_DIR}/features/${pkg_module}_${release_version}

    create_all_features

    cd ${BUILD_DIR}
    mkdir ${EXTENSION_DIR}
    mkdir ${EXTENSION_DIR}/eclipse
    touch ${EXTENSION_DIR}/eclipse/.eclipseextension
    cp -Rd features ${EXTENSION_DIR}/eclipse
    cp -Rd plugins ${EXTENSION_DIR}/eclipse

    jar_specific_plugins

    # Include org.antlr packages in zipped distribuition
    cp -Rd ${git_repo_root}/internal/src/org.antlr_2.7.2 ${EXTENSION_DIR}/eclipse/plugins

    zip -r BridgePoint_extension_${branch}.zip ${EXTENSION_DIR} > ${pkg_log_dir}/BridgePoint_extension_${branch}_zip.log 2>&1
}

function jar_specific_plugins {
    cd ${EXTENSION_DIR}/eclipse/plugins
    for jarplugin in ${plugins_to_jar}; do
      jar_plugin_fullname="${jarplugin}_${release_version}"
      echo -e "Converting ${jar_plugin_fullname} to a jar file."
      cd ${jar_plugin_fullname}
      # Use the Manifest file declared in our plugin.  Don't let jar create one.
      jar cMf "${jar_plugin_fullname}.jar" *
      mv  "${jar_plugin_fullname}.jar" ../
      cd ../
      rm -rf "${jar_plugin_fullname}"      
    done
    cd ${BUILD_DIR}
}

function create_build {
    cd $BUILD_DIR

    get_required_modules
    extract_release_files
    
    ./configure_external_dependencies.sh > ${LOG_DIR}/configure_externals.log 2>&1
    
    # Generate list of modules needing verification
    all_modules="${internal_modules} ${plugin_modules} ${RELEASE_PKG} ${all_feature_modules} ${model_compiler_modules}"

    for module in $all_modules; do
        echo "Verifying checkout of $module"
        verify_checkout
        verify_rval="$?"
    done

    if [ "$verify_rval" != "1" ]; then
        zip_distribution
    fi

}

function create_feature {
    cd $BUILD_DIR
    copy_included_files ${RELEASE_PKG} features ${pkg_module}_${release_version}
}

function copy_included_files {
    # This function copies all files listed in a plugins
    # bin.includes variable from its build.properties file
    #
    # args: 1 = module 2 = parent folder (features or plugins) 3 = destination folder
    echo -e "  copying files: ${1} to ${2}/${3}"
    if [ ! -d ${2}/${3} ]; then
      mkdir ${2}/${3}
    fi
    if [ -e ${1}/build.properties ]; then
      include_files=`echo -e "\n" | cat ${1}/build.properties - | awk '{if (/bin.includes = /) {print $3; getline; while (/^[[:blank:]]/) {print $1; getline;}}}' - | tr -d '\\\\' | tr -d ','`

      for file in ${include_files}; do
        cd ${1}

        if [ ${file} = "." ]; then
          # Special handling for plugins that will be packaged as jars
          file="bin/com/"
          cp -Rd ${file} ${BUILD_DIR}/${2}/${3}/
          plugins_to_jar="${plugins_to_jar} ${1}"
        else
          if [ -d ${file} ]; then
            cp --parents -Rd ${file} ${BUILD_DIR}/${2}/${3}/
          else
            cp --parents ${file} ${BUILD_DIR}/${2}/${3}/ > /dev/null 2>&1
          fi
        fi

        cd $BUILD_DIR
      done

      exclude_files=`echo -e "\n" | cat ${1}/build.properties - | awk '{if (/bin.excludes = /) {print $3; getline; while (/^[[:blank:]]/) {print $1; getline;}}}' - | tr -d '\\\\' | tr -d ','`

      for file in ${exclude_files}; do
        rm -rf ${2}/${3}/${file}
      done

    fi
}

function create_all_features {
    create_feature

    cd $BUILD_DIR

    echo -e "Processing features: ${feature_modules}"
    for feature in $feature_modules; do
      echo "  Processing ${feature}"
      feature_version=`awk -F"\"" '{if (/[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${feature}/feature.xml`

      feature_less=`echo $feature | sed s/"-feature"//`

      copy_included_files $feature features ${feature_less}_${feature_version}

      if [ -e ${feature_less}/about.mappings ]; then
        ib=`cat ${feature_less}/about.mappings | grep -c "Internal Build"`
        if [ $ib -gt 0 ]; then
          cat ${feature_less}/about.mappings | sed s/"1=Internal Build"/1=${TIMESTAMP}/ > ${feature_less}/about.mappings.tmp
          mv ${feature_less}/about.mappings.tmp ${feature_less}/about.mappings
        fi
      fi
      copy_included_files ${feature_less} plugins ${feature_less}_${feature_version}
    done
}

#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------
date

branch=${BRANCH}
git_repo_root=${GIT_REPO_ROOT}
build_type=${BUILD_TYPE}
pkg_log_dir="${LOG_DIR}/pkg_logs"

doc_module="com.mentor.nucleus.bp.doc"
doc_module_mc3020="com.mentor.nucleus.help.bp.mc"
pkg_module="com.mentor.nucleus.bp.bld.pkg"

if [ ! -x $pkg_log_dir ]; then
	echo -e "Creating package log directory: $pkg_log_dir"
	mkdir ${pkg_log_dir}
fi

if [ ! -x $BUILD_DIR/plugins ]; then
    echo -e "Creating plugin directory: ${BUILD_DIR}/plugins"
    mkdir ${BUILD_DIR}/plugins
fi

if [ ! -x ${BUILD_DIR}/features ]; then
	echo -e "Creating feature directory: ${BUILD_DIR}/features"
	mkdir ${BUILD_DIR}/features
fi

echo "Starting the build process in ${BUILD_DIR}"

# Move the log files created by earlier scripts into the new log dir
mv ${BUILD_ROOT}/cfg_output.log ${LOG_DIR}
mv ${BUILD_ROOT}/diff.log ${LOG_DIR}

# Set the environment variable, PTC_MCC_DISABLED to true so that consistency 
# checking is not built
export PTC_MCC_DISABLED=true

# Get back to the base directory
cd ${BUILD_ROOT}

# Source the functions script
source ${BUILD_DIR}/create_release_functions.sh

# Kick off the build chain
create_build

echo -e "\nBuild complete, installation can be found at ${RELEASE_DROP}/BridgePoint_extension_${branch}.zip\n"

# Check for errors, if found report them.  Note that the log file is moved after this script runs, 
# hence the different paths for where we grep and where we report the user to look.
error_count=`grep -c -i -w "Error" ${BUILD_DIR}/build_output.log`
if [ ${error_count} -gt 0 ]; then
    echo -e "Errors found in the output log. Check ${LOG_DIR}/build_output.log." >> ${ERROR_FILE}
fi

if [ -f $ERROR_FILE ]; then
    echo -e "Errors found during release creation:\n\n\n"
    cat $ERROR_FILE
fi

date
exit 0


