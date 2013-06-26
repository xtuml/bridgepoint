Instructions for modifying ant package for timing.

_- Extract the source package located at
	\\phoenix\software\software_archive\Eclipse\Ant\apache-ant-1.6.1-src.zip
	
_- In a console, change directories to the extracted source

_- Modify the file located at com.mentor.nucleus.bp.core/tools/pt_ant/ExecTask.java

_- Copy the modified file to the following directory under the extracted source:
	src/main/org/apache/tools/ant/taskdefs
	
_- Run the following command under the ant source directory:
	bash-2.05$ sh build.sh

Intructions for installing modified package.

_- Unzip the file located at:
	  \\phoenix\software\software_archive\Eclipse\Ant\apache-ant-1.6.1-bin.zip
	  
_- Copy the lib directory found in the ant installation to lib_pt.

_- Copy the ant command found in the bin directory to ant_pt

_- Modify the ant_pt command, setting the ANT_LIB variable equal to the lib_pt
   directory.
   
_- Use the ant_pt command to build projects with timing exec calls.
