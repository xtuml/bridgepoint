#!/bin/bash
#=====================================================================
#
# File:      create_bp_release.sh
#
#(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================
#
#	create_tiger_release.sh takes the following arguments
#
#   $1 - product version, actually this is any branch/tag found in git
#   $2 - git repository root
#   $3 - build type (release/nonrelease)
#
#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------
function usage {
  echo -e "\nThis script builds BridgePoint.\n"
  echo -e "\nUsage: $0 <branch/tag> <git repo root> <release/nonrelease>\n"
  echo -e "branch/tag  = the release version to be created"
  echo -e "git repo root = the parent directory of the git repositories"
  echo -e "build type = either release or nonrelease"
  echo -e ""
  echo -e "e.g: $0 master /git/xtuml nonrelease"
  echo -e "     $0 R4.0.0 /git/xtuml release"
  exit 1
}

# The jar_distribution function creates a jar file for every package that 
# will be delivered
function jar_distribution {
    compile_modules

    cd $build_dir

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
                    echo -e "    Copying $file to ${build_dir}/${module}/bin/${mod_dir}"
                    cp $file ${build_dir}/${module}/bin/${mod_dir}
                fi
                echo -e "      ready to look for next file"
            done
            echo -e "    Finished looping."

            cd ${build_dir}/${module}/bin

            jar_name=`grep -e "^source.*jar =" ../build.properties | awk '/^source/,/ =/ { sub("source.", ""); print $1;}' -`
            echo -e "    jar file name is ${jar_name}"
            jar -cvf ${jar_name} . > ${pkg_log_dir}/${jar_name}.log 2>&1
            echo -e "    Finished creating jar file."

            cd $build_dir
        fi
    done
}

# The zip_distribution function creates a zip file that can later be 
# unzipped into the plugin directory of an eclipse installation
function zip_distribution {
    jar_distribution

    cd $build_dir

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

            echo -e "0=${module_release_version} ${internal_version}\n1=${timestamp}\n" > ${module}/about.mappings
        fi

        mkdir plugins/${module}_${module_release_version}

        copy_included_files ${module} plugins ${module}_${module_release_version}

        mv $module/bin/*.jar plugins/${module}_${module_release_version} > /dev/null 2>&1
    done

    mkdir ${build_dir}/features/${pkg_module}_${release_version}

    create_all_features

    cd ${build_dir}
    mkdir ${extension_dir}
    mkdir ${extension_dir}/eclipse
    touch ${extension_dir}/eclipse/.eclipseextension
    cp -Rd features ${extension_dir}/eclipse
    cp -Rd plugins ${extension_dir}/eclipse

    jar_specific_plugins

    # Include org.antlr packages in zipped distribuition
    cp -Rd ${git_repo_root}/internal/src/org.antlr_2.7.2 ${extension_dir}/eclipse/plugins

    zip -r BridgePoint_extension_${branch}.zip ${extension_dir} > ${pkg_log_dir}/BridgePoint_extension_${branch}_zip.log 2>&1
}

function jar_specific_plugins {
    cd ${extension_dir}/eclipse/plugins
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
    cd ${build_dir}
}

function create_build {
    cd $build_dir

    get_required_modules
    extract_release_files
    
    ./configure_external_dependencies.sh ${branch} ${git_repo_root} ${build_dir} ${build_type} > ${log_dir}/configure_externals.log 2>&1
    
    # Generate list of modules needing verification
    all_modules="${internal_modules} ${plugin_modules} ${release_pkg} ${all_feature_modules} ${model_compiler_modules}"

    for module in $all_modules; do
        echo "Verifying checkout of $module"
        verify_checkout
        verify_rval="$?"
    done

    if [ "$verify_rval" != "1" ]; then
        zip_distribution
    fi

    # If theis build is being run on the official build server, opy plugins to release drop location.
    host=`hostname`
    if [ "${host}" = "svr-orw-sle-10" ]; then
      cd ${build_dir}
      ${rsh} ${server} "(cd '${release_base}'; if [ ! -x '${release_drop}' ]; then mkdir '${release_drop}'; fi)"
      scp BridgePoint_extension_${branch}.zip build@${server}:${release_drop}
      ${rsh} ${server} "(touch '${release_drop}'; chown -R build:staff '${release_drop}')"
    fi
}

function create_feature {
    cd $build_dir
    copy_included_files ${release_pkg} features ${pkg_module}_${release_version}
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
          cp -Rd ${file} ${build_dir}/${2}/${3}/
          plugins_to_jar="${plugins_to_jar} ${1}"
        else
          if [ -d ${file} ]; then
            cp --parents -Rd ${file} ${build_dir}/${2}/${3}/
          else
            cp --parents ${file} ${build_dir}/${2}/${3}/ > /dev/null 2>&1
          fi
        fi

        cd $build_dir
      done

      exclude_files=`echo -e "\n" | cat ${1}/build.properties - | awk '{if (/bin.excludes = /) {print $3; getline; while (/^[[:blank:]]/) {print $1; getline;}}}' - | tr -d '\\\\' | tr -d ','`

      for file in ${exclude_files}; do
        rm -rf ${2}/${3}/${file}
      done

    fi
}

function create_all_features {
    create_feature

    cd $build_dir

    echo -e "Processing features: ${feature_modules}"
    for feature in $feature_modules; do
      echo "  Processing ${feature}"
      feature_version=`awk -F"\"" '{if (/[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${feature}/feature.xml`

      feature_less=`echo $feature | sed s/"-feature"//`

      copy_included_files $feature features ${feature_less}_${feature_version}

      if [ -e ${feature_less}/about.mappings ]; then
        ib=`cat ${feature_less}/about.mappings | grep -c "Internal Build"`
        if [ $ib -gt 0 ]; then
          cat ${feature_less}/about.mappings | sed s/"1=Internal Build"/1=${timestamp}/ > ${feature_less}/about.mappings.tmp
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

if [ $# -ne 3 ]; then
	usage
fi

branch="$1"
git_repo_root="$2"
build_type="$3"
base_dir=`pwd`
base_dir="${base_dir}/.."
build_dir="${base_dir}/${branch}"
log_dir="${build_dir}/log"
error_file="${log_dir}/.errors"
pkg_log_dir="${log_dir}/pkg_logs"
release_pkg="com.mentor.nucleus.bp.bld.pkg-feature"
release_base="/arch1/products/tiger/releases"
release_drop="${release_base}/${branch}"
doc_module="com.mentor.nucleus.bp.doc"
doc_module_mc3020="com.mentor.nucleus.help.bp.mc"
pkg_module="com.mentor.nucleus.bp.bld.pkg"
timestamp=`date +%Y%m%d%H%M`
extension_dir="BridgePoint_${branch}"
server="tucson.wv.mentorg.com"
rsh="ssh"

if [ ! -x $pkg_log_dir ]; then
	echo -e "Creating package log directory: $pkg_log_dir"
	mkdir ${pkg_log_dir}
fi

if [ ! -x $build_dir/plugins ]; then
    echo -e "Creating plugin directory: ${build_dir}/plugins"
    mkdir ${build_dir}/plugins
fi

if [ ! -x ${build_dir}/features ]; then
	echo -e "Creating feature directory: ${build_dir}/features"
	mkdir ${build_dir}/features
fi

echo "Starting the build process in ${build_dir}"

# Move the log files created by earlier scripts into the new log dir
mv ${base_dir}/cfg_output.log ${log_dir}
mv ${base_dir}/diff.log ${log_dir}

# Set the environment variable, PTC_MCC_DISABLED to true so that consistency 
# checking is not built
export PTC_MCC_DISABLED=true

# Get back to the base directory
cd ${base_dir}

# Source the functions script
source ${build_dir}/create_release_functions.sh

# Kick off the build chain
create_build

echo -e "\nBuild complete, installation can be found at ${release_drop}/BridgePoint_extension_${branch}.zip\n"

# Check for errors, if found report them.  Note that the log file is moved after this script runs, 
# hence the different paths for where we grep and where we report the user to look.
error_count=`grep -c -i -w "Error" ${build_dir}/build_output.log`
if [ ${error_count} -gt 0 ]; then
    echo -e "Errors found in the output log. Check ${log_dir}/build_output.log." >> ${error_file}
fi

if [ -f $error_file ]; then
    echo -e "Errors found during release creation:\n\n\n"
    cat $error_file
fi

date
exit 0


