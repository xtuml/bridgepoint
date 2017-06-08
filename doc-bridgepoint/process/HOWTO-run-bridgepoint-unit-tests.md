# HOWTO Run the BridgePoint Unit Tests
This document provides the information required to run the BridgePoint development unit tests.  

Preparation 
-----------
1) See the [Developer Getting Started Guide](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md) for getting the proper environment set for running the BridgePoint development unit tests.  

2) This document uses ```~/git``` as the root folder for git repostiories, and it uses ```~/workspace``` as the development workspace. You may substitute any folder you desire, but you must be consistent.

3) Create the following environment variables only if you plan to use a non-default repository location.  You can simply create a script similar to below:

```
export XTUML_DEVELOPMENT_REPOSITORY=~/git/bridgepoint
export XTUML_TEST_MODEL_REPOSITORY=~/git/models/test/

<BridgePoint_Install>/bridgepoint &
```

4) If you want to run BridgePoint unit tests on MS Windows you must perform some additional steps [described here.](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#windowstesting) 

5) Clone repositories required for building and testing using the build scripts:

```
cd ~/git
git clone https://github.com/xtuml/bptest
git clone https://github.com/xtuml/packaging
```  

Testing  
-------  
Using the same build scripts used for the BridgePoint deployment build the product and run the unit tests from the command line.  
- Ensure that Maven is installed on your Operating System, consult the Download and Install sections at http://maven.apache.org for installation instructions.  
- Set bp_install_dir to the current BridgePoint installation    
- Prepare the CLI.sh script for the BridgePoint installation    
    ```
    cp ~/git/bridgepoint/src/installer/CLI.sh $bp_install_dir
    chmod u+x $bp_install_dir/CLI.sh
    dos2unix $bp_install_dir/CLI.sh
    ```
- Modify CLI.sh to point at the development workspace if the location is different than the default ~/workspace.  Edit CLI.sh, setting the WORKSPACE variable to point at the location of the development workspace.
- Add these additional environment variables:  
    * INCLUDE_TESTS=true  
    * mcj_path=~/git/bridgepoint/src/MC-Java  
    * bp_test_path=~/git/bptest    
- If you have not built in the UI according to the [Developer Getting Started Guide](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md), then you must check out all of the plug-ins from the bridgepoint repository according to the guide.  You also must import these plug-ins into the same workspace as specified in the CLI.sh preparation step.  
- If you have not built in the UI according to the [Developer Getting Started Guide](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md), then run prebuilder on the following projects:  
	* org.xtuml.bp.core  
	* org.xtuml.bp.als  
	* org.xtuml.bp.ui.canvas  
	* org.xtuml.bp.core.test  
	
    ```
    $bp_install_dir/CLI.sh Build -project [project] -prebuildOnly  
    ```
- Change directory to ~/git/bridgepoint/releng/org.xtuml.bp.releng.parent  
- Run maven install (This will build and run the tests, it will take a while)  
```
   mvn -Dtycho.disableP2Mirrors=true -Dmaven.test.failure.ignore=true install
```
- Run report generation (This will generate a result file)  
```
   mvn -Daggregate=true surefire-report:report-only
```
- View the file located under the current directory at: target/site/surefire-report.html for results  
- If you encounter any errors are failures that require debugging open the BridgePoint UI  
- Set any breakpoints that are relevant to the issues  
- Restart the tests for the project with issues including the debugging port option (Navigate to the test plugin folder):  
     ```
      cd ~/git/bptest/src/[test-plugin]
      cwd=`pwd` && cd ../../../bridgepoint/src/org.xtuml.bp.pkg-feature && mvn -Dtycho.disableP2Mirrors=true -Dmaven.test.failure.ignore=true install && cd $cwd && mvn -Dtycho.disableP2Mirrors=true -Dmaven.test.failure.ignore=true install -DdebugPort=8000
     ```
- The tests will wait for a remote debugging session, create a new launch configuration in the UI using
  - Select **Debug > Debug Configurations...**  
  - Right click on Remote Java Application and select New
  - Choose the test projet and click Debug
- The maven build will continue once the remote debugger is fully connected
- Any breakpoints set will now be hit as long as execution takes it through such a path
- Debug just as one would if developing in the UI
- Once the test run is complete with no failures or errors, navigate to the owning test plug-in on the command line.  
- Run maven again for that test plugin.  
     ```
      cd ~/git/bptest/src/[test-plugin]
      cwd=`pwd` && cd ../../../bridgepoint/src/org.xtuml.bp.pkg-feature && mvn -Dtycho.disableP2Mirrors=true -Dmaven.test.failure.ignore=true install && cd $cwd && mvn -Dtycho.disableP2Mirrors=true -Dmaven.test.failure.ignore=true install
      cd ~/git/bridgepoint-fork-main/releng/org.xtuml.bp.releng.parent
      mvn -Daggregate=true surefire-report:report-only
     ```
- View the file located under the current directory at: target/site/surefire-report.html for results  
- If there are still problems repeat the debug process, otherwise continue to the next problem if one exists.  

### Alternatively, if you have access to the build server you can run the tests there following the instructions located at [Run Hudson build server.](https://docs.google.com/document/d/1B5sri4AyGV6lwe_BpIAsRPeX4eXPZTObCdEme53ZVVw/edit)

Addressing Issues with UI
-------------------------
Use **Debug** to rerun any tests with issues, setting breakpoints as necessary.
- Select **Debug > Debug Configurations...**, and note the following:
  - Section **Eclipse Application** contains the launchers for the BridgePoint builds
    - The **BP Application** launchers are for Windows.
    - The **x BP Application** launchers are for Linux.
    - **CLI** launchers are for the command line interface.
  - Section **JUnit Plug-in Test** contains the individual launchers for the defined BridgePoint plug-in unit tests.
  - Section **Launch Group** has a member called **BridgePoint Unit Tests**, which will launch all of the JUint plug-in tests.
- Select the appropriate test suite with problems under the **JUnit Plug-in Test** section
  - Select the **Debug** button to launch the test.
    - This will cause the selected test to be executed.
    - The builder will build BridgePoint, if necessary, and launch the build as the test target.  
    - Examine any stops caused by breakpoints set and address the issue.  
    - Once the test run is complete with no failures or errors, navigate to the owning test plug-in on the command line.  
    - Run maven again for that test plugin.  
      ```
      cd ~/git/bptest/src/[test-plugin]
      mvn -Dtycho.disableP2Mirrors=true -Dmaven.test.failure.ignore=true install
      cd ~/git/bridgepoint-fork-main/releng/org.xtuml.bp.releng.parent
      mvn -Daggregate=true surefire-report:report-only
      ```
- View the file located under the current directory at: target/site/surefire-report.html for results  
- If there are still problems repeat the run in the UI, otherwise continue to the next problem if one exists.  

Adding new tests  
----------------  
To add new tests to the BridgePoint testing environment see the [HOWTO add unit tests...](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-add-unit-tests-to-testing-environment.md) document.  

FAQ/Troubleshooting
---------------
- Check the Unit Testing section of [BridgePoint FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#unittesting) 
