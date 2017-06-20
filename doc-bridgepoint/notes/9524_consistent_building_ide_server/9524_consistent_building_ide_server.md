---

This work is licensed under the Creative Commons CC0 License

---

# Build using maven scripts in UI  
### xtUML Project Design Note

### 1. Abstract

This note describes the changes to use the maven scripts in the UI for building.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9524](https://support.onefact.net/issues/9524) Use consistent build and test approach  
<a id="2.2"></a>2.1 [BridgePoint DEI #9636](https://support.onefact.net/issues/9636) Work in offline mode with maven    
<a id="2.3"></a>2.1 [HOWTO Run unit tests](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-run-bridgepoint-unit-tests.md) HOWTO-run-bridgepoint-unit-tests.md  

### 3. Background

The build server uses the maven build tool for building BridgePoint.  This tool uses the various ant scripts through out the BridgePoint projects for building.  The UI uses the eclipse build infrastructure and only runs the ant script portion of the server build.  

### 4. Requirements

4.1 Exercise maven scripts to build BridgePoint in the UI    
4.1.1 The UI build shall be done as closely as possible to that of the build server  
4.1.2 The user experience shall remain the same  
4.1.3 The build shall complete using Build Automatically, Build All and with individual project builds.  
4.1.4 The build shall be incremental  
4.1.5 Cleaning shall be done using the maven scripts  
4.2 Allow unit testing to occur using the maven scripts  
4.3 Builds shall not require the UI  
4.4 It shall be possible to build using the maven scripts without a network connection  


### 5. Analysis

5.1 Exercise maven scripts to build BridgePoint in the UI  

There exists a plug-in for Eclipse, m2e.  This plug-in integrates maven support into Eclipse including but not limited to an added maven builder.  During analysis this was the first thought on the best approach to building in the UI using the maven scripts.  After installing the required plug-in and testing, it was found that this is not the ideal solution for now.  The following reasons determined that choice:  

* The builder was not running at all times  
* The builder output was not showing in the UI console but rather in the console (if one exists) that started the BridgePoint process  
* Enabling the maven nature changed additional data to support m2e, i.e, .classpath.  
* m2e did not out of the box support the ant-run maven plug-in  
* m2e builds assume you need to clean before each build (with code generation this is not good)  
* The plug-in is currently not part of the build (not a huge problem)  

With m2e installed the possibility to build still exists without the above issues.  However, the approach would require a shift in the building paradigm.  Basically the Build Project et. al would not work as you have to right click each pom and choose Run As > Maven Build.  In general it may be nice to start using the m2e plug-in in the future for maintaining and extending the maven scripts, but they provide/require more than a simple command line run.    

The chosen approach is to use the Eclipse Program internal tool builders.  These allow you to simply point at an executable and run it.  In this case the mvn executable.  This matches the build server build as closely as possible.  It also leaves the user experience as is.

Cleaning shall be done using a similar builder as for building only it shall be configured to run on clean only.  It shall execute the maven clean phase which requires calling the existing ant clean targets from maven.  

5.2 Unit testing shall be triggered either from the existing UI launches or by runing a new Test builder.    

### 6. Design

6.1 Exercise maven scripts to build BridgePoint in the UI  
6.1.1 Add a Build and Clean external tool builder for every project with a pom file  
6.1.1.1 Use utilities/build/build_project.sh for building/cleaning/testing    
```  
  ${workspace_loc:/utilities/build/build_project.sh} [clean]
```  
6.1.2. Add a Test external tool builder for every test project  
6.1.2.1 Use this command for testing and set only to run on build, build auto and after clean.  The test builder is disabled for all projects by default.  This allows building in normal cases and testing when desired.    
```  
 ${workspace_loc:/utilities/build/build_project.sh} test
```  
6.1.3 Remove existing external tool builders for ant scripts  
6.2 Modify all .project files from projects with a pom file  
6.2.1 Remove all existing external tool builders used to run ant scripts  
6.2.2 Add new builders with Test disabled by default  
6.2.3 All test projects from bptest shall have a Test builder  
6.3 Modify all projects which contain a clean_all ant target  
6.3.1 Each project's pom file shall be modified to use the ant-run maven plug-in calling the clean_all ant target during the clean phase  
6.4 Disable the parent pom project (releng) builders by default  
6.4.1 They can be used and will build all child projects, however this will not run the necessary pre-builders and will create archives for the entire project.  This uses quite a bit of disk space.  

6.5 Support individual project builds  

The build server runs maven from the org.xtuml.bp.releng.parent project which builds the entire tool.  Doing this allows for all projects including testing projects to exist in the same reactor.  For individual plug-in builds this does not work for testing plug-ins.  It was found that we have an extra requirement entry in the parent.tests projects which is not necessary.  With this present the versions of the core plug-ins were not being properly considered.  Leaving the test plug-ins such that they point at older versions.  The maven scripts are configured such that the test plug-ins can always locate the proper plug-in.  Therefore the extra requirement statement is removed.  

6.6 Disable downloading on subsequent builds  

An attempt was made to use the -o option for maven.  This puts maven into offline mode and prevents downloading during the build.  This could work with an extra step to initially run a build on the org.xtuml.bp.releng.parent project.  Even when doing this maven would then later fail due to an upgraded external plug-in (seemed like it was not fully offline).  Additionally this would require a build of the projects that need pre-build with disabled Build builders.  The steps would be  

* Disable Build builder for all projects which require prebuild  
* Build each of the projects  
* Enable Build builder for all projects which require prebuild  
* Enable Build builder on org.xtuml.bp.releng.parent  
* Build org.xtuml.bp.releng.parent  
* Disable Build builder on org.xtuml.bp.releng.parent  

We would also need to determine how to disable downloading upgrades or fully put maven into offline mode.  Therefore it was left so that online mode was used.  

This issue was raised to further look into this problem, https://support.onefact.net/issues/9636.  

### 7. Design Comments
7.1  Building offline   
Maven supports an option to build offline, preventing the need for downloading as long as all required data has already been downloaded.  Builders have been added for the org.xtuml.bp.releng.parent project.  The idea was that we could run the Build/Test from this project matching what occurs on the build server.  Other builders could then use the -o option to run offline.  During testing multiple occasions have shown maven expecting a download causing the build to fail.  Offline building was therefore not considered with this work.  

7.2 MASL build issues  
Originally the MASL projects were left alone as they have always built fine with Maven.  Towards the end of this work the branch was updated with the latest master.  With this update the following failures occur:  

ERROR: 	MASLRuntimeModule.xtend - /Users/travislondon/git/bridgepoint/src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl/src/org/xtuml/bp/xtext/masl/MASLRuntimeModule.xtend
14: org.xtuml.bp.xtext.masl.lib.MASLDelegatingAllContainerState cannot be resolved to a type.
[ERROR] 
ERROR: 	MASLRuntimeModule.xtend - /Users/travislondon/git/bridgepoint/src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl/src/org/xtuml/bp/xtext/masl/MASLRuntimeModule.xtend
54: The method or field MASLDelegatingAllContainerState is undefined

To synchronize all projects the MASL parent project now has Build/Clean/Test builders.  

### 8. User Documentation
8.1 Update testing howto  
[2.3] was updated and simplified to account for the new build/test approach.  
8.2 Three new scripts were added: bridgepoint/utilities/run_and_test_bp.sh, build_configuration.sh, prepare_build.sh  
The build_configuration.sh script host common environment variables that are used through building and testing.  Both run_and_test_bp.sh and prepare_build.sh source this configuration script.  In order to build BridgePoint an eclipse workspace must be setup, this is what prepare_build.sh is used for.  It configures a workspace and pre-builds xtuml projects in order to prepare for building.  This script only needs to run once unless starting clean.  The run_and_test_bp.sh script will trigger the maven build and testing.  This script can be rerun as necessary.  

### 9. Unit Test

9.1 Run the Developers Getting Started Guide  
9.1.R The tool builds without error in the UI  
9.2 Modify something in a project causing errors and build  
9.2.R Error markers are created  
9.3 Clean ui.explorer  
9.3.1 Build ui.canvas  
9.3.R ui.explorer is built  
9.3 For each project containing a clean_all ant target  
9.3.1 Run Clean... on selected project    
9.3.1.R The ant clean_all target is run as part of the maven clean  
9.4 Run Test build on a test project  
9.4.R The build occurs and the UI test is launched and run  
9.5 Run a build with testing on a build server  
9.5.R The build is successful and the test results are at 100%  
9.6 Run the [2.3] documemnt  
9.6.R The tool is built and tests are run  

### End
