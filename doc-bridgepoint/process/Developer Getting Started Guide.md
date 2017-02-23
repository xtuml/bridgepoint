# Developer Getting Started Guide
This document provides the information required to create a fully functional BridgePoint development environment.  

Preparation 
-----------
1) [This is a link to git documentation that describes working with forks.](https://help.github.com/articles/fork-a-repo/ "fork-a-repo") BridgePoint development requires developers to have a working knowledge of git and git forks. Throughout this document we will refer to the repositories using the formula: ```https://github.com/"username"/"repository".git``` where "username" is your personal Github user name. (For example: ```https://github.com/keithbrown/bridgepoint.git```)

2) This document may be used in Linux, MAC, or Windows, but its examples use Linux. In Windows, cygwin is used to ease setup. Throughout this document we use "~" in the example path names.  If you are building on Windows replace "~" with  "c:" (or "/cygdrive/c" when using the cygwin shell).  

3) This document uses ```~/git``` as the root folder for git repostiories, and it uses ```~/workspace``` as the development workspace. You may substitute any folder you desire, but you must be consistent. 
__WARNING!:__ If you are updating your development environment to 5.8.5  or greater start with a clean workspace.

4) Optionally, a pre-configured development virtual machine (VirtualBox) is available for download. See intructions to download and setup [here](developer-vm-getting-started.md)

5) <a id="help"></a>If you have any problems or questions, check the [FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#bpdevelopers) or post to the [xtUML.org Forums] (https://xtuml.org/community/forum/xtuml-forum/) for help.


Setup Instructions
------------

  - If you do not already have a Github.com account, [create one now.](https://github.com/join)

  - For each of the following git repositories create a fork:  
    __WARNING!:__ If you already have a fork, [assure your fork is up to date]( https://help.github.com/articles/merging-an-upstream-repository-into-your-fork).
    - https://github.com/xtuml/bridgepoint
    - https://github.com/xtuml/mc
    - https://github.com/xtuml/pt_antlr
    - https://github.com/xtuml/models
    
  - [Download the latest version of the tool](https://s3.amazonaws.com/xtuml-releases/nightly-build/buildfiles.html "https://s3.amazonaws.com/xtuml-releases/nightly-build/buildfiles.html").
  
  - Install BridgePoint.  
    - You may unzip wherever you like, a suggestion is:  ```~/xtuml/BridgePoint```
      For MAC it is suggested that you unzip into the /Applications folder, doing so will create a folder named Eclipse.app with all files in it.

  - The following 3rd party tools are required to build BridgePoint.  Install them now.    
    - __LINUX__ - Linux Ubuntu installation commands are presented below.  If installing in a 
    different Linux distribution you must use the [commands appropriate for your Linux distribution](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#linux).
    ```
    sudo apt-get install tofrodos libstdc++5 g++ ant git default-jdk  
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
     
    - __MAC__
      - No action required
      
GUI Build Instructions
------------
The instructions in this section describe how to use the eclipse BridgePoint UI to build the BridgePoint plug-ins.   

  - Clone the repositories:
  ```
  git clone https://github.com/"username"/bridgepoint.git ~/git/bridgepoint
  git clone https://github.com/"username"/mc.git ~/git/mc
  git clone https://github.com/"username"/pt_antlr.git ~/git/pt_antlr
  git clone https://github.com/"username"/models.git ~/git/models
  ```
    
  - Prepare your development workspace with the required preferences. 
  ```
  mkdir -p ~/workspace/.metadata/.plugins/org.eclipse.core.runtime/.settings
  cp -f ~/git/bridgepoint/utilities/build/preferences/*  ~/workspace/.metadata/.plugins/org.eclipse.core.runtime/.settings
  ```

  - Set up pt_antlr for building BridgePoint 
  ```
  cp -f ~/git/pt_antlr/pt_antlr/antlr.jar  ~/git/bridgepoint/src/org.xtuml.bp.als/lib/antlr.jar
  ```
  
  - Launch BridgePoint (```<BridgePoint installation folder>/bridgepoint for MAC it is Eclipse.app```)
    - During startup, enter the name of a new eclipse workspace that will become your development workspace.   
    (Example:  ```~/workspace```)

  - Switch to the git repository perspective and add the repositories that were cloned above.
  
  - Import existing projects from the BridgePoint repository into your workspace.
    - __WARNING!:__ Ensure "Search for nested projects" is Unchecked.
    - __WARNING!:__ Import all projects from this bridgepoint repository, but do NOT import any projects from the other repositories.  Only projects from bridgepoint are needed in the workspace.

  - Switch to the Java perspective

  - Select Project > Build Automatically
    - This will cause each project in the workspace to build.  The builder will build dependent projects first. Since all projects are being processed, the build will take a while and should finish successfully.
    

### Congratulations!  Your environment is now built and ready for BridgePoint development.

  - If you have had any problems building, or have questions, check the [FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#bpdevelopers) or post to the [xtUML.org Forums] (https://xtuml.org/community/forum/xtuml-forum/) for help.
  - If you want to run BridgePoint unit tests, instructions are available in [HOWTO Run the BridgePoint Unit Tests](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-run-bridgepoint-unit-tests.md)
   

### Additional Notes
  - Information about running the BridgePoint build via the command line may be found [in the FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#clibuild).
