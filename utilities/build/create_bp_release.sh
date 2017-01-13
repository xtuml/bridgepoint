#!/bin/bash

#-------------------------------------------------------------------------------
# Functions
#-------------------------------------------------------------------------------

function configure_module_lists {
    cd ${git_bp}/src
    
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

    cd ${git_bp}/src

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
                    echo -e "      Copying $file to ${git_bp}/src/${module}/bin/${mod_dir}"
                    cp $file ${git_bp}/src/${module}/bin/${mod_dir}
                fi
                echo -e "      ready to look for next file"
            done
            echo -e "    Finished looping."

            cd ${git_bp}/src/${module}/bin

            jar_name=`grep -e "^source.*jar =" ../build.properties | awk '/^source/,/ =/ { sub("source.", ""); print $1;}' -`
            echo -e "    jar file name is ${jar_name}"
            jar -cvf ${jar_name} . > ${pkg_log_dir}/${jar_name}.log 2>&1
            echo -e "    Finished creating jar file."
        else
            echo -e "  Skipping jar creation for ${module}"
        fi
        
        cd ${git_bp}/src        
    done
	echo -e "Exiting create_bp_release.sh::jar_distribution"    
}

# The zip_distribution function creates a zip file that can later be 
# unzipped into the plugin directory of an eclipse installation
function zip_distribution {
	echo -e "Entering create_bp_release.sh::zip_distribution"    
    jar_distribution

    cd ${git_bp}/src

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
            internal_version=""
            if [ "${branch}" != "master" ]; then
                internal_version="${branch}"
            fi
            echo -e "0=${module_release_version} ${internal_version}\n1=${timestamp}\n" > ${module}/about.mappings
        fi

        mkdir -p ${build_dir}/plugins/${module}_${module_release_version}

        copy_included_files ${module} plugins ${module}_${module_release_version}
        cd ${git_bp}/src
        
         cp -f $module/bin/*.jar ${build_dir}/plugins/${module}_${module_release_version} > /dev/null 2>&1
    done

    create_all_features

    cd ${build_dir}
    mkdir -p ${site_result_dir}/eclipse
    cp -Rd features ${site_result_dir}/eclipse
    cp -Rd plugins ${site_result_dir}/eclipse

    jar_specific_plugins
 
    # Copy the MASL jars to the plugins dir.
    cd ${git_bp}/src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.updatesite/target/repository/features
    cp -f *.jar ${site_result_dir}/eclipse/features
    cd ${git_bp}/src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.updatesite/target/repository/plugins
    cp -f *.jar ${site_result_dir}/eclipse/plugins

    cd ${site_result_dir}/..
    zip -r BridgePoint_extension_${branch}.zip BridgePoint_${branch} > ${pkg_log_dir}/BridgePoint_extension_${branch}_zip.log 2>&1
	rm -rf BridgePoint_${branch}
	echo -e "Exiting create_bp_release.sh::zip_distribution"    
}

function jar_specific_plugins {
    echo -e "Entering create_bp_release.sh::jar_specific_plugins"    
    cd ${site_result_dir}/eclipse/plugins
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
    echo -e "Exiting create_bp_release.sh::jar_specific_plugins"    
}

function copy_included_files {
    echo -e "Entering create_bp_release.sh::copy_included_files"
	
    # This function copies all files listed in a plugin's
    # bin.includes variable from its build.properties file
    #
    # args: 1 = module 2 = parent folder (features or plugins) 3 = destination folder
    echo -e "  copying files: ${1} to ${2}/${3}"
    if [ ! -d ${build_dir}/${2}/${3} ]; then
      mkdir -p ${build_dir}/${2}/${3}
    fi
    if [ -e ${1}/build.properties ]; then
	  include_files=$(echo -e "\n" | cat ${1}/build.properties - | awk '{if (/bin.includes = /) {print $3; getline; while (! /=/) {print $1; if (getline != 1) break;}}}' - | tr -d '\\\\' | tr -d ',' | tr -d '\r')
	  
      cd ${1}
      for file in ${include_files}; do
        if [ "${file}" = "." ]; then
          # Special handling for plugins that will be packaged as jars
          file="bin/org/"
          cp -Rd ${file} ${build_dir}/${2}/${3}/
          plugins_to_jar="${plugins_to_jar} ${1}"
        else
          if [ -d ${file} ]; then
            cp --parents -Rd ${file} ${build_dir}/${2}/${3}/
          else
            cp --parents ${file} ${build_dir}/${2}/${3}/ > /dev/null 2>&1
          fi
        fi        
      done
      cd ..

	  exclude_files=$(echo -e "\n" | cat ${1}/build.properties - | awk '{if (/bin.excludes = /) {print $3; getline; while (! /=/) {print $1; if (getline != 1) break;}}}' - | tr -d '\\\\' | tr -d ',' | tr -d '\r')

      for file in ${exclude_files}; do
        rm -rf ${build_dir}/${2}/${3}/${file}
      done

    fi
	echo -e "Exiting create_bp_release.sh::copy_included_files"
}

function create_all_features {
	echo -e "Entering create_bp_release.sh::create_all_features"
	
    cd ${git_bp}/src

    echo -e "Processing features: ${feature_modules}"
    for feature in $feature_modules; do
      echo "  Processing ${feature}"
      feature_version=$(awk -F"\"" '{if (/[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${feature}/feature.xml | sed '/^\s*$/d')
      feature_less=$(echo $feature | sed s/"-feature"//)

      copy_included_files $feature features ${feature_less}_${feature_version}
      cd ${git_bp}/src
    done
	echo -e "Exiting create_bp_release.sh::create_all_features"
}

#-------------------------------------------------------------------------------
# Main
#-------------------------------------------------------------------------------

echo -e "Entering create_bp_release.sh"

date

if [ "$#" -lt 6 ]; then
  echo "This script has required parameters.  See below for usage."
  echo
  echo "create_bp_release.sh Build_dir Branch_name Git_BP Log_dir Timestamp Site_result_dir"
  echo ""
  echo "  Build_dir - path to the folder where the build is happening"
  echo "  Branch_name - product version, any branch/tag found in git"
  echo "  Git_BP - path to BridgePoint git repository"
  echo "  Log_dir - path to the folder where logs are stored"
  echo "  Timestamp - the timestamp to apply to the build IDs"
  echo "  Site_result_dir - path to folder where the update site data is packaged"
  echo ""
  exit 1
fi 

build_dir="$1"
branch="$2"
git_bp="$3"
log_dir="$4"
timestamp="$5"
site_result_dir="$6"

echo "Create BP Release invocation: ./create_bp_release.sh ${build_dir} ${branch} ${git_bp} ${log_dir} ${timestamp} ${site_result_dir}"

pkg_log_dir="${log_dir}/pkg_logs"

feature_modules=""
plugin_modules=""
plugins_to_jar=""

if [ ! -x $pkg_log_dir ]; then
  echo -e "Creating package log directory: $pkg_log_dir"
  mkdir -p ${pkg_log_dir}
fi

if [ ! -x ${build_dir}/plugins ]; then
  echo -e "Creating plugin directory: ${build_dir}/plugins"
  mkdir -p ${build_dir}/plugins
fi

if [ ! -x ${build_dir}/features ]; then
  echo -e "Creating feature directory: ${build_dir}/features"
  mkdir -p ${build_dir}/features
fi

release_version=$(awk -F": " '{if (/[0-9]\.[0-9]\.[0-9]/) {print $2; exit;}}' ${git_bp}/src/org.xtuml.bp.pkg/META-INF/MANIFEST.MF)
release_version="$(echo ${release_version} | tr -d '\r')"

# Set up the lists of features and plug-ins
configure_module_lists

zip_distribution

echo -e "Exiting create_bp_release.sh"

exit 0


