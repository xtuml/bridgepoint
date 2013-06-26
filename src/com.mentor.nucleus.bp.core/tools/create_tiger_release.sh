#!/bin/bash
#=====================================================================
#
# File:      $RCSfile: create_tiger_release.sh,v $
# Version:   $Revision: 1.77 $
# Modified:  $Date: 2013/05/10 16:11:21 $
#
#(c) Copyright 2004-2012 by Mentor Graphics Corp. All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================
#
#
#	create_tiger_release.sh
#

#
#
#	create_tiger_release.sh takes the following arguments
#
#	$1 - product version, actually this is any tag found in CVS
#   [$2] - "nonrelease" may be specified as the third argument to allow the script
#          to pull data from HEAD if non can be found with the matching tag.
#
date
function usage {

	echo -e "\nUsage: $0 tag [nonrelease]\n"
	echo -e "tag  = the release version to be created"
	echo -e "[nonrelease] = this value may optionally be specified to allow the build to get data from HEAD if no matching tag is found\n"
	exit 1

}

if [ $# -eq 0 ]; then
	usage
fi

build_type="$2"
if [ "${build_type}" = "nonrelease" ]; then
    export_flags=" -f "
else
    export_flags=""
fi

product_version="$1"
base_dir=`pwd`
build_base="${base_dir}/releases"
build_dir="$build_base/$product_version"
doc_module="com.mentor.nucleus.bp.doc"
doc_module_mc3020="com.mentor.nucleus.help.bp.mc"
log_dir="$build_dir/log"
pkg_log_dir="${log_dir}/pkg_logs"
release_pkg="com.mentor.nucleus.bp.bld.pkg-feature"
release_base="/arch1/products/tiger/releases"
release_drop="${release_base}/${product_version}"
pkg_module="com.mentor.nucleus.bp.bld.pkg"
eclipse_pkg="EclipseDeploymentConfiguration"
timestamp=`date +%Y%m%d%H%M`
release_tag="$1"
error_file="${build_base}/.errors"
diff_file="${build_base}/.diff_log"
extension_dir="BridgePoint_${product_version}"

rm -f $error_file
rm -f $diff_file

if [ ! -x $build_base ]; then
	echo -e "Creating base build directory: $build_base"
	mkdir $build_base
fi

if [ "${product_version}" = "HEAD" ] || [ "${build_type}" = "nonrelease" ]; then
    echo -e "Removing old build directory: $build_dir"

    rm -rf $build_dir 2>>${error_file}

    if [ "$?" != "0" ]; then
        cat ${error_file}
    fi
fi

if [ ! -x $build_dir ]; then
    echo -e "Creating build directory: $build_dir"
    cd $build_base; mkdir $product_version
else
    mv $build_dir ${build_dir}_${timestamp}
    echo -e "Release build directory $build_dir already exists.  Moving to ${build_dir}_${timestamp}"
    cd $build_base; mkdir $product_version
fi

if [ ! -x $log_dir ]; then
    echo -e "Creating log directory: $log_dir"
    mkdir $log_dir
fi

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

#
# set the environment variable, PTC_MCC_DISABLED
# to true so that consistency checking is not
# built
#
export PTC_MCC_DISABLED=true

# Get back to the base directory
cd ${base_dir}

# Source the functions script
source ./create_release_functions.sh

# Fix permissions for remote server
chmod -R ug+w $build_dir

remote_build_dir="${remote_build_base}/releases/${product_version}"

function obfuscate_release {

	#
	#	The obfuscate_release function performs
	#	Java code obfuscation on the pluguins.
	#
	cd $base_dir

	echo -e "Obfuscating the release."

    cat obfuscate_template.xml | sed s/REPLACE-TAG/${product_version}/ - | sed s/REPLACE-VERSION/${release_version}/ | sed s/REPLACE-MCVERSION/${release_version}/ - > ${build_dir}/obfuscate.xml

    ob_file=`cygpath -m ${build_dir}/obfuscate.xml`
    ${ant_cmd} -f ${ob_file} > ${log_dir}/obfuscate_log.txt

	# Return to build dir
	cd ${build_dir}

	rm -rf ${extension_dir}
	mkdir ${extension_dir}
	mkdir ${extension_dir}/eclipse
	cp ../../.eclipseextension ${extension_dir}/eclipse
	cp -Rd features ${extension_dir}/eclipse
	cp -Rd plugins ${extension_dir}/eclipse

    jar_specific_plugins

	zip -r ${pkg_module}_${release_version}_obfuscated.zip ${extension_dir} > ${pkg_log_dir}/BridgePoint_extension_${release_version}_obfuscated_zip.log 2>&1
}

function jar_distribution {

	#
	#	The jar_distribution function creates a jar file
	#	for every package that will be delivered
	#

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

function zip_distribution {

	#
	#	The zip_distribution function creates a zip file
	#	that can later be unzipped into the plugin directory
	#	of an eclipse installation
	#

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
            internal_version=`echo $release_tag | cut -d"_" -f 4-`

            echo -e "0=${module_release_version} ${internal_version}\n1=${timestamp}\n" > ${module}/about.mappings
      	fi

		mkdir plugins/${module}_${module_release_version}

		copy_included_files ${module} plugins ${module}_${module_release_version}

		mv $module/bin/*.jar plugins/${module}_${module_release_version} > /dev/null 2>&1

	done

	mkdir ${build_dir}/features/${pkg_module}_${release_version}

	create_all_features

	# Include org.antlr packages in zipped distribuition
	${rsh} ${server} "(unzip /software/software_archive/Eclipse/plug-ins/antlr/org.antlr_*.zip -d ${remote_build_dir}/plugins)" > ${pkg_log_dir}/org.antlr_zip.log 2>&1

	# Return to build dir
	cd ${build_dir}

	mkdir ${extension_dir}
	mkdir ${extension_dir}/eclipse
	cp ../../.eclipseextension ${extension_dir}/eclipse
	cp -Rd features ${extension_dir}/eclipse
	cp -Rd plugins ${extension_dir}/eclipse

    jar_specific_plugins
    
	zip -r ${pkg_module}_${release_version}.zip ${extension_dir} > ${pkg_log_dir}/BridgePoint_extension_${release_version}_zip.log 2>&1

    # SKB 11/16/2012 : skip obfuscating - obfuscate_release
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
	${rsh} ${server} "(cd '$remote_build_dir/../../' ; ./configure_external_dependencies.sh  $release_tag $release_version $external_files $remote_build_dir $build_type)" > ${cvs_log_dir}/cvs_configure_files 2>&1

	# Generate list of modules needing verification
	all_modules="${internal_modules} ${plugin_modules} ${release_pkg} ${all_feature_modules} ${model_compiler_modules}"

	for module in $all_modules; do
		echo "Verifying checkout of $module"
		verify_checkout
		verify_rval="$?"
		${rsh} ${server} "(cvs -d'${cvsroot}' rdiff -s -D yesterday -r '${product_version}' '${module}' 2>/dev/null | grep 'changed from' )" >> ${diff_file}
	done

	if [ "$verify_rval" != "1" ]; then
	    zip_distribution
    fi

	# Copy plugins to release drop
    ${rsh} ${server} "(cd '${release_base}'; if [ ! -x '${release_drop}' ]; then mkdir '${release_drop}'; fi)"
	${rsh} ${server} "(cd '$remote_build_dir'; cp -f '${pkg_module}'_'${release_version}'.zip '${release_drop}'/BridgePoint_extension_'${product_version}'.zip ; touch '${release_drop}')"
	#SKB 11/16/2012 : skip obfuscating - ${rsh} ${server} "(cd '$remote_build_dir'; cp -f '${pkg_module}'_'${release_version}'_obfuscated.zip '${release_drop}'/BridgePoint_extension_'${product_version}'_obfuscated.zip ; zip obfuscate_BP.log.zip obfuscate_BP.log.xml; cp -f obfuscate_BP.log.zip '${release_drop}' )"
	${rsh} ${server} "(cd '${remote_build_dir}'; chown -R build:staff '${release_drop}')"

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

# Kick off the build chain
create_build
echo -e "\nBuild complete, installation can be found at ${release_drop}/BridgePoint_extension_${product_version}.zip\n"

# Check for errors, if found report them
error_count=`grep -c -i -w "^Error" ${base_dir}/nb_output.log`
if [ ${error_count} -gt 0 ]; then

    echo -e "Errors found in the output log. The most likely cause is that the build server directory mounts are not set up.  Another possibility is that the server failed to check out a probject.  Check ${base_dir}/nb_output.log." >> ${error_file}

fi

if [ -f $error_file ]; then

	echo -e "Errors found during release creation:\n\n\n"
	cat $error_file

fi

date
exit 0


