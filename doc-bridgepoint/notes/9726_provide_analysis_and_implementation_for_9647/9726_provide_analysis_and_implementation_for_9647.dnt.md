---

This work is licensed under the Creative Commons CC0 License

---

# Address offline mode issues with maven  
### xtUML Project Design Note


### 1. Abstract

See [[2.1]](#2.1).

### 2. Document References
<a id="2.1"></a>2.1 [BridgePoint DEI #9726](https://support.onefact.net/issues/9726) Provide analysis and implementation for 9647    
<a id="2.2"></a>2.2 [BridgePoint Analysis 9726](https://github.com/travislondon/bridgepoint/blob/9726_1/doc-bridgepoint/notes/9726_provide_analysis_and_implementation_for_9647/9726_provide_analysis_and_implementation_for_9647.ant.md) 9726 Analysis    

### 3. Background

See [[2.1]](#2.1).  

### 4. Requirements

See [[2.1]](#2.1).  

### 5. Analysis

See [[2.1]](#2.1). 

### 6. Design

6.1 Modify build_project.sh  
6.1.1 Modify build_project.sh to work with both core plug-ins and test plug-ins  
6.1.2 After building with maven touch a timestamp file at this location: $WORKSPACE/.metadata/.plugins/$project  
6.1.3 Check .xtuml, .sql, .arc, and .inc in project being built against timestamp  
6.1.3.1 Scan and compare timestamp for all required file types  
6.1.3.2 Use maven to build if any of the required file types are modified later than the timestamp  
6.1.3.3 Exit immediately if timestamp is greater than any of the required file types  
6.1.4 Have script delete timestamp file for project being cleaned  
6.1.5 Support following parameters:  

* projectName  
* test  
* -online  
* -debug  
* clean  

6.1.6 Determine project location given just a project name  
6.1.7 Do not touch timestamp if calling to clean or building a releng project (Full build)  
6.1.8 Use -o for all builds unless the -online option is given  
6.1.9 INCLUDE_TESTS is not important for testing, always use -DskipTests=true unless test argument is passed  
6.2 Modify build_and_test_bp.sh  
6.2.1 Call build_project.sh from the build_and_test_bp.sh script passing the org.xtuml.bp.releng.parent as project  
6.2.2 Pass the test and -online arguments to build_project.sh, tests will not run if INCLUDE_TESTS=false as maven will never call them  
6.2.3 Scan all projects under bridgepoint touching eaches timestamp as its a full build  
6.2.4 Scan all projects under bptest touch eaches timestamp if INCLUDE_TESTS=true  
6.3 Build.launch, Clean.launch, and Test.launch  
6.3.1 For each launch type in every project change to call build_project.sh  
6.3.2 For each pass the eclipse variable value for build_project  
6.3.3 For Clean.launch pass the clean option  
6.3.4 For Test.launch pass the test option  
6.4 Change default workspace value of auto building to true  


### 7. Design Comments

Implementation of the desired approach in [[2.1]](#2.1) was not possible.  The approach to go offline rather than use the -o option was not possible as for p2 downloads would require a complete mirror of the plug-ins used.  This would take much longer and much more space.  Use of the -o approach was brought back for this reason.  With the checking of timestamps a build loop no longer occurs.  

### 8. User Documentation

The Developer Getting Started Guide was updated to add information about building with the modified build_project.sh script.    

### 9. Unit Test

9.1 Start with a clean bridgepoint and bptest repository  
9.2 Configure build_configuration.sh to match your test workspace  
9.3 Configure build_configuration.sh so INCLUDE_TESTS=true  
9.4 Remove the destination workspace if existing  
9.5 Remove ~/.m2  
9.6 Modify build_and_test_bp.sh removing the "test" argument to build_project.sh  
9.7 Run build_and_test_bp.sh  
9.R Network is used, downloading maven repositories  
9.R All projects are built successfully, including test projects  
9.R No tests were run  
9.8 Disable network connection  
9.9 Start BridgePoint pointing at the test workspace  
9.R Build automatically triggers building of the workspace  
9.R No maven build was run  
9.R The JDT builder is run and there are no errors in the workspace  
9.10 Configure ui.text.test to disable the Build buider and enable the Test builder  
9.11 ui.text.test is built using maven  
9.12 ui.text.test is run  
9.13 Restore the builders  
9.14 Make a java change to cause an error in ui.text.test and build  
9.R No maven build was run, java error is visible in problems view   
9.15 Make a java change in bp.core and build  
9.R No maven build was run  
9.16 For the file types, .xtuml, .sql, .arc and .inc  
9.16.1 Touch the file type  
9.16.2 Build the project  
9.16.R The maven build is run  
9.16.R The maven build is successful    
9.16.R The ant targets are run  
9.17 Run clean on ui.canvas  
9.R The maven clean is run  
9.R The maven clean is successful  
9.18 Run build on ui.canvas  
9.R The maven build is run  
9.R The maven build is successful  
9.R All code is regenerated  
9.19 Run build_project.sh org.xtuml.bp.core.test  
9.R No maven build is run  
9.20 Run build_project.sh org.xtuml.bp.core.test test  
9.R The maven build is run  
9.R The maven build is successful  
9.R The core.tests are run  
9.21 Run build_project.sh org.xtuml.bp.core.test test -debug  
9.R The maven build is run  
9.R The maven build is successful  
9.R The core.tests are started but waiting for a remote debug connection  
9.22 Kill build_project.sh  
9.23 Restore network connection  
9.24 Restore the "test" argument to build_project.sh in build_and_test_bp.sh  
9.25 Run build_and_test_bp.sh  
9.R All projects are built successfully  
9.R All tests run and pass 100%  
9.26 Run a build server build with tests  
9.R All projects build successfully  
9.R All tests run and pass 100%  

### End
