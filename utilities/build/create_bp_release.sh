#!/bin/bash
#
#	create_bp_release.sh requires the following environment variables
#
#   ${BRANCH} - product version, actually this is any BRANCH/tag found in git
#   ${GIT_REPO_ROOT} - git repository root
#   ${BUILD_TYPE} - build type (release/nonrelease)
#
#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------

function configure_module_lists {
    cd ${GIT_BP}/src
    
    feature_file_list=$(find . -name "feature.xml")

    feature_modules=$(find . -name "feature.xml" | sed 's|/feature.xml||' | sed 's|./||')

    for feature in ${feature_file_list}; do
        plugins=$(sed -n '/<plugin/{:start /\/>/!{N;b start};/plugin\s*id=/p}' ${feature} | awk -F"id=" '{printf("%s\n", $2)}' | sed '/^\s*$/d' | sed s/\"// | sed s/\"//)
        plugins="$(echo $plugins | tr -d '\r')"
        plugin_modules="${plugins} ${plugin_modules} "
    done
}

# The jar_distribution function creates a jar file for every package that 
# will be delivered
function jar_distribution {
	echo -e "Entering create_bp_release.sh::jar_distribution"    

    cd ${GIT_BP}/src

    echo -e "Creating a jar of each project"

    for module in ${plugin_modules}; do
        if [ -d "${module}/src" ]; then
            echo -e "  Creating jar for ${module}"
            cd ${module}/src
            # First copy all files other than .java from the src to the bin
            echo -e "    Ready to loop to find files to package."
            file_list=$(find . -path "*.java"  -prune -o -print | sed s/.*gitignore// | sed '/^\s*$/d')
            echo -e "    The files are: ${file_list}"
            for file in ${file_list}; do
                echo -e "    Found file ${file}"
                if [ ! -d ${file} ]; then
                    mod_dir=`dirname ${file}`
                    echo -e "      mod_dir=${mod_dir}"
                    echo -e "      Copying $file to ${GIT_BP}/src/${module}/bin/${mod_dir}"
                    cp $file ${GIT_BP}/src/${module}/bin/${mod_dir}
                fi
                echo -e "      ready to look for next file"
            done
            echo -e "    Finished looping."

            cd ${GIT_BP}/src/${module}/bin

            jar_name=`grep -e "^source.*jar =" ../build.properties | awk '/^source/,/ =/ { sub("source.", ""); print $1;}' -`
            echo -e "    jar file name is ${jar_name}"
            jar -cvf ${jar_name} . > ${pkg_log_dir}/${jar_name}.log 2>&1
            echo -e "    Finished creating jar file."
        else
            echo -e "  Skipping jar creation for ${module}"
        fi
        
        cd ${GIT_BP}/src        
    done
	echo -e "Exiting create_bp_release.sh::jar_distribution"    
}

# The zip_distribution function creates a zip file that can later be 
# unzipped into the plugin directory of an eclipse installation
function zip_distribution {
	echo -e "Entering create_bp_release.sh::zip_distribution"    
    jar_distribution

    cd ${GIT_BP}/src

    echo -e "Zipping the distribution"

    for module in ${plugin_modules}; do
        module_release_version="${release_version}"

        # If MANIFEST.MF or plugin.xml file exists, get plugin version from there
        if [ -e ${module}/META-INF/MANIFEST.MF ]; then
            module_release_version=$(awk -F": " '{if (/[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${module}/META-INF/MANIFEST.MF)
            module_release_version="$(echo ${module_release_version} | tr -d '\r')"
            echo -e "Extracted version ${module_release_version} from MANIFEST.MF"
        elif [ -e ${module}/plugin.xml ]; then
            module_release_version=$(awk -F"\"" '{if (/ersion.*\=.*[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${module}/plugin.xml | sed '/^\s*$/d')
            echo -e "Extracted version ${module_release_version} from plugin.xml"
        fi

        echo -e "  Zipping ${module} (${module_release_version})"

        # Update the timestamp in the build ID
        if [ -e ${module}/about.mappings ]; then
            internal_version=`echo ${BRANCH} | cut -d"_" -f 4-`
            echo -e "0=${module_release_version} ${internal_version}\n1=${TIMESTAMP}\n" > ${module}/about.mappings
        fi

        mkdir ${BUILD_DIR}/plugins/${module}_${module_release_version}

        copy_included_files ${module} plugins ${module}_${module_release_version}
        cd ${GIT_BP}/src
        
        mv $module/bin/*.jar ${BUILD_DIR}/plugins/${module}_${module_release_version} > /dev/null 2>&1
    done

    create_all_features

    cd ${BUILD_DIR}
    mkdir ${RESULT_FOLDER_EXTENSION}/eclipse
    touch ${RESULT_FOLDER_EXTENSION}/eclipse/.eclipseextension
    cp -Rd features ${RESULT_FOLDER_EXTENSION}/eclipse
    cp -Rd plugins ${RESULT_FOLDER_EXTENSION}/eclipse

    jar_specific_plugins

    # Include org.antlr packages in zipped distribuition
    cp -Rd ${GIT_BP}/src/org.antlr_2.7.2 ${RESULT_FOLDER_EXTENSION}/eclipse/plugins

    cd ${RESULT_FOLDER_EXTENSION}/..
    zip -r BridgePoint_extension_${BRANCH}.zip BridgePoint_${BRANCH} > ${pkg_log_dir}/BridgePoint_extension_${BRANCH}_zip.log 2>&1
	echo -e "Exiting create_bp_release.sh::zip_distribution"    
}

function jar_specific_plugins {
	echo -e "Entering create_bp_release.sh::jar_specific_plugins"    
    cd ${RESULT_FOLDER_EXTENSION}/eclipse/plugins
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
	echo -e "Exiting create_bp_release.sh::jar_specific_plugins"    
}

function create_build {
	echo -e "Entering create_bp_release.sh::create_build"
	
    cd $BUILD_DIR

    # Can do the copy and dos2unix translation in one step.
	tr -d '\r' < ${GIT_BP}/utilities/build/headless_build.sh > headless_build.sh
	chmod a+x headless_build.sh
	# prepare the verify script as well
	tr -d '\r' < ${GIT_BP}/utilities/build/verify_build.sh > verify_build.sh
	chmod a+x verify_build.sh
    ./headless_build.sh
    zip_distribution
        
	echo -e "Exiting create_bp_release.sh::create_build"
}

function copy_included_files {
	echo -e "Entering create_bp_release.sh::copy_included_files"
	
    # This function copies all files listed in a plugin's
    # bin.includes variable from its build.properties file
    #
    # args: 1 = module 2 = parent folder (features or plugins) 3 = destination folder
    echo -e "  copying files: ${1} to ${2}/${3}"
    if [ ! -d ${BUILD_DIR}/${2}/${3} ]; then
      mkdir ${BUILD_DIR}/${2}/${3}
    fi
    if [ -e ${1}/build.properties ]; then
	  include_files=$(echo -e "\n" | cat ${1}/build.properties - | awk '{if (/bin.includes = /) {print $3; getline; while (! /=/) {print $1; if (getline != 1) break;}}}' - | tr -d '\\\\' | tr -d ',' | tr -d '\r')
	  
      cd ${1}
      for file in ${include_files}; do
        if [ "${file}" = "." ]; then
          # Special handling for plugins that will be packaged as jars
          file="bin/org/"
          cp -Rd ${file} ${BUILD_DIR}/${2}/${3}/
          plugins_to_jar="${plugins_to_jar} ${1}"
        else
          if [ -d ${file} ]; then
            cp --parents -Rd ${file} ${BUILD_DIR}/${2}/${3}/
          else
            cp --parents ${file} ${BUILD_DIR}/${2}/${3}/ > /dev/null 2>&1
          fi
        fi        
      done
      cd ..

	  exclude_files=$(echo -e "\n" | cat ${1}/build.properties - | awk '{if (/bin.excludes = /) {print $3; getline; while (! /=/) {print $1; if (getline != 1) break;}}}' - | tr -d '\\\\' | tr -d ',' | tr -d '\r')

      for file in ${exclude_files}; do
        rm -rf ${BUILD_DIR}/${2}/${3}/${file}
      done

    fi
	echo -e "Exiting create_bp_release.sh::copy_included_files"
}

function create_all_features {
	echo -e "Entering create_bp_release.sh::create_all_features"
	
    cd ${GIT_BP}/src

    echo -e "Processing features: ${feature_modules}"
    for feature in $feature_modules; do
      echo "  Processing ${feature}"
      feature_version=$(awk -F"\"" '{if (/[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${feature}/feature.xml | sed '/^\s*$/d')
      feature_less=$(echo $feature | sed s/"-feature"//)

      copy_included_files $feature features ${feature_less}_${feature_version}
      cd ${GIT_BP}/src
    done
	echo -e "Exiting create_bp_release.sh::create_all_features"
}

#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------

echo -e "Entering create_bp_release.sh"

date

pkg_log_dir="${LOG_DIR}/pkg_logs"

feature_modules=""
plugin_modules=""
plugins_to_jar=""

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

# Set the environment variable, PTC_MCC_DISABLED to true so that consistency 
# checking is not built
export PTC_MCC_DISABLED=true

release_version=`awk -F"\"" '{if (/ersion.*\=.*[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${GIT_BP}/src/org.xtuml.bp.pkg/plugin.xml`

# Set up the lists of features and plug-ins
configure_module_lists

# Kick off the build chain
create_build

echo -e "\nBuild complete, installation can be found at ${RELEASE_DROP}/BridgePoint_extension_${BRANCH}.zip\n"

# Check for errors, if found report them.  Note that the log file is moved after this script runs, 
# hence the different paths for where we grep and where we report the user to look.
grep -c -i -w "Error" ${BUILD_LOG}
error_count=$?
if [ ${error_count} -ne 1 ]; then
    echo -e "Errors found in the output log. Check ${BUILD_LOG}." >> ${ERROR_FILE}
fi

if [ -f $ERROR_FILE ]; then
    echo -e "Errors found during release creation:\n\n\n"
    cat $ERROR_FILE
fi

date

echo -e "Exiting create_bp_release.sh"

exit 0


