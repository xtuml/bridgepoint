# HOWTO Run the BridgePoint Unit Tests
This document provides the information required to run the BridgePoint development unit tests.  

Preparation 
-----------
1) See the [Developer Getting Started Guide](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md). This document assumes you have followed its instructions to get a good build of the product. If you have not, stop now and do so. Addtionally, this document uses the same naming conventions found in the preparation section of the that document, so take time to look at the now before continuing.  

2) For each of the following git repositories create a fork:  
  __WARNING!:__ If you already have a fork, [assure your fork is up to date]( https://help.github.com/articles/merging-an-upstream-repository-into-your-fork).
  
  - https://github.com/xtuml/models
  - https://github.com/xtuml/bptest

3) The following environment variables must exist in your environment BEFORE you launch BridgePoint.  
```
export XTUML_BPTEST_REPOSITORY=~/git/bptest/
export XTUML_DEVELOPMENT_REPOSITORY=~/git/bridgepoint
export XTUML_TEST_MODEL_REPOSITORY=~/git/models/test/
export XTUML_PRIVATE_MODEL_REPOSITORY=~/git/modelsmg/test/
```

4) If you want to run BridgePoint unit tests on MS Windows you must perform some additional steps [described here.](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#windowstesting) 

Instructions
------------
In normal testing, **Run** is used to best simulate a runtime environment, while **Debug** is used to set breakpoints and debug the runtime environment.
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
    - A summary of the results of the tests can be found in < BridgePoint installation >/eclipse/test_results/unit_test_summary.txt. The creation of this document is implemented in the org.xtuml.bp.test plugin.

### If a test fails or you need to debug the test failure
1. Try running the test individually by selecting **Run > Run Configurations... > JUnit Plug-in Test** and selecting the test.
2. To debug the test, select **Debug > Debug Configurations... > JUnit Plug-in Test** and select the test to debug.
3. Set breakboints in the generated or realized BridgePoint java source code in your host environment as needed.

FAQ/Troubleshooting
---------------
- Check the Unit Testing section of [BridgePoint FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#unittesting) 
