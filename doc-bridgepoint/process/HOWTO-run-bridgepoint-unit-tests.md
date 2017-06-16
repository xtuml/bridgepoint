# HOWTO Run the BridgePoint Unit Tests
Unit tests are run on the build server. If you have access to the build server you can run the tests there, following the instructions located at [Run Hudson build server.](https://docs.google.com/document/d/1B5sri4AyGV6lwe_BpIAsRPeX4eXPZTObCdEme53ZVVw/edit)


This document provides information required to run the BridgePoint unit tests from the command line and from inside Eclipse under Linux and MAC environments.  Using the same build scripts used for the BridgePoint deployment build the product and run the unit tests from the command line.  


Preparation
-----------
1. Clone and/or update the bptest and models repositories.  
```
git clone https://github.com/"username"/bptest.git ~/git/bptest
git clone https://github.com/"username"/models.git ~/git/models
```  
2. Ensure that Maven is installed on your Operating System, consult the Download and Install sections at http://maven.apache.org for installation instructions.  
3. Modify ```~/git/bridgepoint/utilities/build/build_configuration.sh``` to account for your local paths.
4. Assure that BridgePoint version 6.2.5 or later is installed before continuing.
5. Run the following script to prepare your workspace: ```~/git/bridgepoint/utilities/build/prepare_build.sh```  

Build and Test From the Command Line  
-------  
1. Run ~/git/bridgepoint/utilities/build/build_and_test_bp.sh  
2. Check the result
     * View the file located under the ~/git/bridgepoint/releng/org.xtuml.bp.releng.parent directory at: target/site/surefire-report.html for results  
3. Debugging Errors  
     * **Debugging the command-line build** (this is done when there is a problem specific to the command-line build)  
          * Open the BridgePoint UI
          * Set any breakpoints that are relevant to the issues
          * Restart the test(s) for the project with issues including the debug option:  ```~/git/bridgepoint/utilities/build/build_and_test_bp.sh [test plugin (example: core)] debug```
          * The tests will wait for a remote debugging session, create a new launch configuration in the UI using
          * Select **Debug > Debug Configurations...**  
          * Right click on Remote Java Application and select New
          * Choose the test projet and click Debug
               * The maven build will continue once the remote debugger is fully connected
                    * Any breakpoints set will now be hit as long as execution takes it through such a path
                    * Debug just as one would if developing in the UI
                    * Once the test run is complete with no failures or errors, Run the build again.
          * View the file located under the current directory at: target/site/surefire-report.html for results  
          * If there are still problems repeat the debug process, otherwise continue to the next problem if one exists.  
     * **Debugging Issues From the Eclipse Launch Configurations** (this is done when there is a test bug)  
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
                ~/git/bridgepoint/utilities/build/build_and_test_bp.sh [test plugin (example: core)]
                ```

Adding new tests  
----------------  
To add new tests to the BridgePoint testing environment see the [HOWTO add unit tests...](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-add-unit-tests-to-testing-environment.md) document.  

Tips and Tricks
---------------  
1. The UI build can be triggered by enabling the Build builder on the org.xtuml.bp.releng.parent project.  When that build is run the entire xtuml tool will be built.  Note that testing will not occur with this build.  
    * To test you can enable the Test builder on the org.xtuml.bp.releng.parent.tests project.  This will build all test projects and run each test suite.  

FAQ/Troubleshooting
---------------
- Check the Unit Testing section of [BridgePoint FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#unittesting) 
