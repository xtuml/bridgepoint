---

This work is licensed under the Creative Commons CC0 License

---

# Move BridgePoint build away from special configuration and tools
### xtUML Project Implementation Note


1. Abstract
-----------
The BridgePoint build currently requires the addition of a number of environment
variables as well as setting up a "BridgePointDev" folder with special tooling/frontends 
for xtumlmc_build and xtumlmc_gen_erate.  

This should no longer be needed because the model compilers contain the tooling needed
to launch xtumlmc_build and generator. This note describes work to update the plug-in ANT 
build files (generate.xml's) to use xtumlmc_build (and its frontend to xtumlmc_gen_erate) 
out of the C binary model compiler ```mc3020/bin``` folder.

2. Document References
----------------------
[1] [BridgePoint DEI #7520](https://support.onefact.net/redmine/issues/7520)    
[2] [Developer Getting Started Guide](https://github.com/keithbrown/bridgepoint/blob/7520_build_tools_change/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md)  

3. Background
-------------
The build used to rely on gen_file and gen_import.  Eventually it was migrated to the 
more generic gen_erate tool.  On Windows, we had DOS batch files that set up some
environment variables and paths to run these tools.  The xtumlmc_build perl
script is capable of running generator via a command line function call like 
```xtumlmc_build xtumlmc_gen_erate ...args...``` without the need for the frontend
scripts on DOS or Linux.     

The BridgePoint build required setting PT_HOME, PT_HOME_DRIVE, and either XTUMLGEN_HOME (DOS),
or XTUMLMC_HOME (LINUX) environment variables in the Launcher script.  When a new 
developer was getting set up to build BridgePoint they had to copy all these external scripts
into place, then set the environment variables properly in their Launcher.  This is a pain
and is error prone.  Given that xtumlmc_build can run generator, we will modify the BridgePoint
build to get away from all this special configuration.

4. Requirements
---------------
4.1  BridgePoint build shall no longer rely on PT_HOME, PT_HOME_DRIVE, XTUML*_HOME variables     
4.2  BridgePoint build shall no longer rely on "BridgePointDev" scripts on Windows or Linux    

5. Work Required
----------------
5.1. Modify ```MC-Java/build.xml``` and plug-in ```generate.xml``` files to remove usage of PT_HOME* 
variables.  Add a new variable ```util_bin_dir``` that point at the c binary MC plug-in's ```bin/``` 
folder.   
5.1.1  A design decision was made here.  We have two choices: use the scripts in the installed host
BridgePoint's binary MC folder, or use the scripts that the developer pastes into the c binary plug-in
in the plug-in set under development (inside the local git repository).  
5.1.1.1  Host plug-ins:  
  - PROS: BridgePoint build doesn't rely on the developer to copy the files into the binary MC plug-in  
  - CONS: This path is to a versioned plug-in.  Meaning the path inside ```generate.xml``` must rely on
  the BP_VERSION environment variable that is set in the Launcher script.   

5.1.1.2  Development plug-ins:  
  - PROS: The new path in ```generate.xml``` requires no version, reducing a dependence on the Launcher script.   
  - CONS: BridgePoint build relies on the developer to copy the files into the binary MC plug-in (follow 
  the Developer HOWTO README).  

5.1.1.3  The author's decision was to use a path into the development plug-ins (5.1.1.2).  Depending on how 
the nightly build is eventually set up on the server, we may need to change this path.  This would be a simple 
search and replace on the defintions of ```util_bin_dir``` in the code.   
  
5.2. Modify ```MC-Java/build.xml``` and ```bp.core/generate.xml``` in the ```generate-with-file_io.java``` target and
usage to remove parallelization in core build.  
  
5.3. In the build files, define the environment variable ```XTUMLMC_HOME``` in the exec calls to xtumlmc_build.exe.   

5.4  Change calls to the script ```xtumlmc_gen_erate ...``` to be calls to ```xtumlmc_build.exe xtumlmc_gen_erate ...```     

5.5  Update the BridgePoint Developer getting started HOWTO [2] to make sure the mc files are copied into the plug-in
before build and to remove references to the PT_HOME* and XTUML*_HOME environment variables.  

5.6  Updated xtumlmc_build script   
5.6.1  Updated the determination of the platform and setting of ```$osplatform``` variable.  On the author's Windows
VM the ```uname()``` was returning "MINGW64_NT" which the code block at line 76 was not recognizing as Windows.  Fixed.  
5.6.2  When determining if we're building the bp.core plug-in, handle both cases of name: ```com.mentor.nucleus.bp.core``` or ```org.xtuml.bp.core```    
5.6.3  This requires a new xtumlmc_build.exe for Windows.  

6. Implementation Comments
--------------------------
6.1  The use of parallel tasks during ```bp.core``` plug-in build has been problematic.
Different machines, especially VMs have struggled with running tasks in parallel.  Additionally, 
the parallelization requires a special script ```generate-with-file_io.pl``` that configures 
separate build dirs for the simultaneously running tasks.  Because of the problems and the desire
to move away from special files in the build, parallelization in ```bp.core``` build is removed.   

6.2  The ```bp.io.core/.../ImportInteger.java``` handcraft file was being dirtied on build. A diff
with HEAD showed no changes.  It was determined that the cause was that this file was being run
through the code formatter during build.  The format task is modified to ignore this file.   

7. Unit Test
------------
7.1  Build Cleanly (Run on both Windows and Linux)
  - Modify Launcher script to remove PT_HOME* and XTUML*_HOME variables.  
  - Start BridgePoint  
  - Build  
  - __R__ Build is successful  

7.2  Verify build
  - After successful build, run the UI Explorer test  
  - __R__ Test is successful  
  
8. Code Changes
---------------
Branch name: 7520_build_tools_change

<pre>
REPO: keithbrown/bridgepoint

doc-bridgepoint/process/Developer Getting Started Guide.md

MC-Java/build.xml

org.xtuml.bp.als/generate.xml

org.xtuml.bp.compare/generate.xml

org.xtuml.bp.core/generate.xml

org.xtuml.bp.core.test/generate.xml

org.xtuml.bp.io.core/generate.xml

org.xtuml.bp.io.mdl/generate.xml

org.xtuml.bp.io.sql/generate.xml

org.xtuml.bp.model.compare/generate.xml

org.xtuml.bp.model.compare.test/
    generate.xml

org.xtuml.bp.ui.canvas/generate.xml

org.xtuml.bp.ui.canvas.test/generate.xml

org.xtuml.bp.ui.explorer/generate.xml

org.xtuml.bp.ui.properties/generate.xml

org.xtuml.bp.ui.properties.test/
    generate.xml

org.xtuml.bp.ui.session/generate.xml

org.xtuml.bp.ui.text/generate.xml

org.xtuml.bp.ui.tree/templates/
    generate.xml
org.xtuml.bp.ui.tree/generate_schema.xml

REPO: keithbrown/mc
mc/bin/xtumlmc_build

</pre>

End
---

