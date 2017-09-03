# Developer Getting Started Guide
This document provides the information required to create a fully functional BridgePoint development environment.  

Preparation 
-----------
1) [This is a link to git documentation that describes working with forks.](https://help.github.com/articles/fork-a-repo/ "fork-a-repo") BridgePoint development requires developers to have a working knowledge of git and git forks. Throughout this document we will refer to the repositories using the formula: ```https://github.com/"username"/"repository".git``` where "username" is your personal Github user name. (For example: ```https://github.com/keithbrown/bridgepoint.git```)

2) This document may be used in Linux, MAC, or Windows, but its examples use Linux. In Windows, cygwin is used to ease setup. Throughout this document we use ```~``` in the example path names.  If you are building on Windows replace ```~``` with  ```c:``` (or ```/cygdrive/c``` when using the cygwin shell).  

3) This document uses ```~/git``` as the root folder for git repostiories, and it uses ```~/workspace``` as the development workspace. You may substitute any folder you desire, but you must be consistent. 
__WARNING!:__ If you are updating your development environment to 5.8.5  or greater start with a clean workspace.

4) Optionally, a pre-configured development virtual machine (VirtualBox) is available for download. See intructions to download and setup [here](developer-vm-getting-started.md)

5) <a id="help"></a>If you have any problems or questions, check the [FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#bpdevelopers) or post to the [xtUML.org Forums](https://xtuml.org/community/forum/xtuml-forum/) for help.


Setup Instructions
------------

  - If you do not already have a Github.com account, [create one now.](https://github.com/join)

  - For each of the following git repositories create a fork:  
    __WARNING!:__ If you already have a fork, [assure your fork is up to date]( https://help.github.com/articles/merging-an-upstream-repository-into-your-fork).
    - https://github.com/xtuml/bridgepoint
    - https://github.com/xtuml/bptest
    - https://github.com/xtuml/mc
    - https://github.com/xtuml/pt_antlr
    - https://github.com/xtuml/packaging
    - https://github.com/xtuml/models
    - https://github.com/xtuml/packaging
    
  - [Download the latest BridgePoint Developer version of the tool](https://s3.amazonaws.com/xtuml-releases/nightly-build/buildfiles.html "https://s3.amazonaws.com/xtuml-releases/nightly-build/buildfiles.html").
  
  - Install BridgePoint.  
    - You may unzip wherever you like, a suggestion is:  ```~/xtuml/```
      - On MacOS, you must now run the additional step: ```cp ~/xtuml/BridgePoint.app/Contents/MacOS/bridgepoint ~/xtuml/BridgePoint.app/Contents/Eclipse```

  - The following 3rd party tools are required to build BridgePoint.  Install them now.    
    - __ALL__  
      - [Maven](https://maven.apache.org/install.html)    

    - __MAC__
      - Install pypy and git (we suggest via homebrew)
      
    - __LINUX__ - Linux Ubuntu installation commands are presented below.  If installing in a 
    different Linux distribution you must use the [commands appropriate for your Linux distribution](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#linux).
    ```
    sudo apt-get install libstdc++5 g++ ant git default-jdk  
    ```  
    The build runs __a lot__ faster if the pypy python tool is available.  We recommend it be installed:
    ```
    sudo add-apt-repository ppa:pypy/ppa
    sudo apt-get update
    sudo apt-get install pypy pypy-dev
    ```   
    - __WINDOWS__
      - Perl ([We recommend strawberry perl.](http://strawberryperl.com/ "strawberry perl"))
      - [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html "Oracle JDK")
      - [Cygwin](http://cygwin.com/install.html "Cygwin Install") (Make sure to select Git)
     
Build Instructions
------------
  - Clone the repositories:
  ```
  git clone https://github.com/"username"/bridgepoint.git ~/git/bridgepoint
  git clone https://github.com/"username"/bptest.git ~/git/bptest
  git clone https://github.com/"username"/mc.git ~/git/mc
  git clone https://github.com/"username"/pt_antlr.git ~/git/pt_antlr
  git clone https://github.com/"username"/models.git ~/git/models
  git clone https://github.com/"username"/packaging.git ~/git/packaging
  ```

  - Modify ```~/git/bridgepoint/utilities/build/build_configuration.sh``` to account for your local paths. Also adjust the flag to indicate if you want to run all the JUnit tests during build or not.  

  - Build BridgePoint:
  ```~/git/bridgepoint/utilities/build/build_and_test_bp.sh```

### Congratulations!  Your environment is now built and ready for BridgePoint development.

  - Launch BridgePoint (```<BridgePoint installation folder>/bridgepoint for MAC it is Eclipse.app```)
    - During startup, enter the the eclipse workspace specified in  
    ```~/git/bridgepoint/utilities/build/build_configuration.sh``` above.  

  - Switch to the git repository perspective and add the repositories that were cloned above.
  
Debugging   
---------
  - **Debugging the command-line build** (this is done when there is a problem specific to the command-line build)  
    - Open the BridgePoint UI  
    - Set any breakpoints that are relevant to the issues  
    - Restart the test(s) for the project with issues including the debug option:  
      ```~/git/bridgepoint/utilities/build/build_project.sh [test plugin (example: org.xtuml.bp.core.test)] test -debug```
    - The tests will wait for a remote debugging session, create a new launch configuration in the UI using
    - Select **Debug > Debug Configurations...**  
    - Right click on Remote Java Application and select New
    - Choose the test projet and click Debug
      - The maven build will continue once the remote debugger is fully connected
        - Any breakpoints set will now be hit as long as execution takes it through such a path
        - Debug just as one would if developing in the UI
        - Once the test run is complete with no failures or errors, Run the build again.
    - View the file located under the current directory at: target/site/surefire-report.html for results  
    - If there are still problems repeat the debug process, otherwise continue to the next problem if one exists.
  - **Debugging Issues From the Eclipse Launch Configurations** (this is done when there is a test bug)  
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
          ~/git/bridgepoint/utilities/build/build_project.sh [test plugin (example: org.xtuml.bp.core.test)] test  
           ```

Building single projects on the command line  
----------------  
Any project can be built on the command line using the build_project.sh script:  

```~/git/bridgepoint/utilities/build/build_project.sh [plugin (example: org.xtuml.bp.core)]```  

The following options are available for building:  

* projectName  
* test  
* clean  
* -online  
* -debug  

By default the projects are built offline from maven.  This allows for a quicker build.  Using the -online mode will allow maven to build online once again.  This is helpful when an update is available that is desired.  

Adding new unit tests  
----------------  
To add new tests to the BridgePoint testing environment see the [HOWTO add unit tests...](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-add-unit-tests-to-testing-environment.md) document.  

Tips and Tricks
---------------  
1. The UI build can be triggered by enabling the Build builder on the ```org.xtuml.bp.releng.parent``` project.  When that build is run the entire xtuml tool will be built.  Note that testing will not occur with this build.  
  - To test you can enable the Test builder on the org.xtuml.bp.releng.parent.tests project.  This will build all test projects and run each test suite.  

FAQ/Troubleshooting
---------------
- Check the Unit Testing section of [BridgePoint FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#unittesting) 
