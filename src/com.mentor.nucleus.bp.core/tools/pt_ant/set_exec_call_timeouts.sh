#!/bin/bash
#=====================================================================
#
# File:      $RCSfile: set_exec_call_timeouts.sh,v $
# Version:   $Revision: 1.12 $
# Modified:  $Date: 2012/01/23 21:28:26 $
#
#(c) Copyright 2004-2012 by Mentor Graphics Corp. All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================

#
# script to retrieve all exec timeouts
# Change them to 1000000
# Then get actual runtime
# and set the timeouts accordingly

REQUIRED_MODULES="com.mentor.nucleus.bp.als.oal MC-Java pt_antlr"

PLUGIN_MODULES="com.mentor.nucleus.bp.core
                com.mentor.nucleus.bp.als
	   	    	com.mentor.nucleus.bp.ui.text      
	   	    	com.mentor.nucleus.bp.ui.properties	          
	     	    com.mentor.nucleus.bp.ui.explorer
	   	   		com.mentor.nucleus.bp.ui.canvas   	    	
	   	    	com.mentor.nucleus.bp.io.core
	   	    	com.mentor.nucleus.bp.io.mdl
	   	    	com.mentor.nucleus.bp.io.sql
	   	    	com.mentor.nucleus.bp.compare
	   	    	com.mentor.nucleus.bp.sequencecapture"

ALL_MODULES="${REQUIRED_MODULES} ${PLUGIN_MODULES}"

cvsroot=":ext:${USER-build}@phoenix:/arch1/products/tiger/repository"
CVS_RSH="rsh"

export  cvsroot
export CVS_RSH

JOB_NUM="$1"

if [ ! -d tmp_wksp ]; then
	echo -e "Creating temporary work directory: tmp_wksp\n"
	mkdir tmp_wksp
fi

echo -e "Changing directory to tmp_wksp\n"
cd tmp_wksp

if [ ! -d cvs_logs ]; then

	echo -e "Creating cvs check out log directory: cvs_logs \n"
	mkdir cvs_logs  

fi

for module in $ALL_MODULES; do
	if [ ! -d $module ]; then
	echo -e "Checking out $module ... \n"
	cvs -d${cvsroot} co $module > cvs_logs/${module}_cvsco_log.txt 2>&1 
	fi
done

# Set MC-Java timeouts to 1000000

echo -e "Setting timeout values to 1000000 milliseconds for MC-Java ... \n"

sed -i s/'timeout=\"[0-9]*\"'/'timeout="1000000"'/ MC-Java/build.xml 

# build modules to time exec calls

count=0

for module in $PLUGIN_MODULES; do

	cd $module

	if [ -e generate.xml ]; then

		# Backup original build script
		cp generate.xml generate_orig.xml
	
		echo -e "Setting timeout values to 1000000 milliseconds for ${module} ... \n"

		sed -i s/'timeout=\"[0-9]*\"'/'timeout="1000000"'/ generate.xml 

		echo -e "Building ${module} ... \n"
		c:/eclipse/ant/apache-ant-1.6.1/bin/ant_pt -Declipse-home=c:/eclipse -f generate.xml nb_all > ${module}_build_log.txt 2>&1

	else
		echo -e "ERROR: The build script for $module does not exist.\n"
		exit

	fi
	
	echo -e "Checking if ${module} built correctly ... \n"
	success_count=`cat ${module}_build_log.txt | grep -c "BUILD SUCCESSFUL"`

	if [ "$success_count" -eq 0 ]; then
		
		echo -e "Build failed for $module, fix the problem and run $0 again\n"
		exit
		
	fi
		
	# Generate line number

	echo -e "Generating list of timeout line numbers ... \n"
	grep -n "timeout=" generate.xml | awk -F":" '{printf("%s\n", $1)}' > ${module}_exec_lines.txt

	# Verify that number of timeouts is equal to number of timed calls

	echo -e "Verifying that the number of timed exec calls matches the number of timeout entries ...\n"
	
	project=`echo $module | cut -d"." -f3-`

	timeouts=`cat ${project}_exec_times.txt | wc -l`
	line_numbers=`cat ${module}_exec_lines.txt | wc -l`

	if [ $timeouts -ne $line_numbers ]; then

		echo -e "The number of timeout entries does not match the number of timed exec calls \n"
		exit
	
	fi

	# Set timeouts for each line number

	echo -e "Setting the timeout values for ${module} ... \n"

	count=0

	for line in `cat ${module}_exec_lines.txt`; do

		count=$(($count + 1))

		timeout_value=`sed -n ${count}p ${project}_exec_times.txt`

		sed -i ${line}s/'timeout=\"[0-9]*\"'/timeout=\"${timeout_value}\"/ generate.xml

	done	
	
	cd ..

done

# Special case to set all timeout values in MC-Java to the highest value found

echo -e "Generating a list of all MC-Java timed exec calls ... \n"

for file in `/bin/find . -name mc-java_exec_times.txt`; do

	cat $file >> mc-java_timeout_values.txt

done

echo -e "Finding the largest timeout value for MC-Java ... \n"

largest_timeout=0

for timeout in `cat mc-java_timeout_values.txt`; do

	if [ ${timeout} -gt ${largest_timeout} ]; then
	
	largest_timeout="${timeout}"

	fi

done

# Generate line number

echo -e "Generating list of timeout line numbers ... \n"

grep -n "timeout=" MC-Java/build.xml | awk -F":" '{printf("%s\n", $1)}' > mc-java_exec_lines.txt

# Backup original MC-Java build script
cp MC-Java/build.xml MC-Java/build_orig.xml

# Set timeout for each exec call 

echo -e "Setting timeout values for MC-Java ... \n"

sed -i s/'timeout=\"[0-9]*\"'/timeout=\"${largest_timeout}\"/ MC-Java/build.xml

# Commit changes if a job id is given

if [ "$JOB_NUM" != "" ]; then

	for module in $PLUGIN_MODULES MC-Java; do
	
		cd $module
		
		if [ "$module" != "MC-Java" ]; then
		
			echo -e "Checking for modifications to $module/generate.xml ...\n"
			compare=`diff --suppress-common-lines -y generate.xml generate_orig.xml`
		
		elif [ "$module" = "MC-Java" ]; then
			
			echo -e "Checking for modifications to $module/build.xml ...\n"
			compare=`diff --suppress-common-lines -y build.xml build_orig.xml`
			
		fi
		
		if [ "$compare" != "" ]; then

				msg="Job: $JOB_NUM
$0: Setting exec timeout values for $module."					
			
			if [ "$module" != "MC-Java" ]; then
			
				echo -e "Commiting changes to $module/generate.xml ...\n"
				cvs commit -m "$msg" generate.xml
			
			elif [ "$module" = "MC-Java" ]; then
			
				echo -e "Commiting changes to $module/build.xml ...\n"
				cvs commit -m "$msg" build.xml
				
			fi
		
		fi
		
	cd ..
		
	done
	
fi

exit
