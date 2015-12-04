# HOWTO Run the BridgePoint Unit Tests
This document provides the information required to run the BridgePoint development unit tests.  

Preparation 
-----------
1) See the [Developer Getting Started Guide](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md) for getting the proper environment set for running the BridgePoint development unit tests.  

Instructions
------------
NOTE: In the following sections, **Run** is used, but **Debug** works the same.
- Select **Run > Run Configurations...**, and note the following:
  - Section **Eclipse Application** contains the launchers for the BridgePoint builds
    - The **BP Application** launchers are for Windows.
    - The **x BP Application** launchers are for Linux.
    - **CLI** launchers are for the command line interface.
  - Section **JUnit Plug-in Test** contains the individual launchers for the defined BridgePoint plug-in unit tests.
  - Section **Launch Group** has a member called **BridgePoint Unit Tests**, which will launch all of the JUint plug-in tests.
- To run the standard unit test suite, select the **Launch Group > BridgePoint Unit Tests** configuration.
  - This will show a tabbed window with the **Launches** tab selected.
    - The window contains a list of JUnit plug-in tests with checkboxes.
  - Select the **Run** button to launch the tests.
    - This will cause each selected test in the **Launches** tab to be executed.
    - The builder will build BridgePoint, if necessary, and launch the build as the test target. Since all tests are being run, the execution will take a while.
    - A summary of the results of the tests can be found in the TODO called unit_test_summary.txt.

Troubleshooting
---------------
- If the **Launch Group > BridgePoint Unit Tests** selection doesn't have the **Run** button activated, examine the list of tests for any test with a red X icon. Deselect these tests, as there is a configuration problem.
  - An issue should be generated, if one doesn't already exist, for this problem. Note the test name, BridgePoint version, and repository revision(s).
- Sometimes tests freeze due to race conditions in the automated button presses. No CPU activity after a reasonable wait period is a good indicator. A manual button press will resolve the freeze, but often requires a guess as to which button to press. Run the test over again individually from the **JUnit Plug-in Test** section.