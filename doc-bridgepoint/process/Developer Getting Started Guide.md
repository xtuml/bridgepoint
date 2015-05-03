# Developer Getting Started Guide
This document provides the information required to create a fully functional BridgePoint development environment.  This will allow the developer to debug, modify, and extend BridgePoint functionality and execute BridgePoint unit tests.

Preparation 
-----------
1) [This is a link to git documentation that describes working with forks.](https://help.github.com/articles/fork-a-repo/ "fork-a-repo") BridgePoint development requires developers to have a working knowledge of git and git forks. Throughout this document we will refer to the repositories using the formula: ```https://github.com/"username"/"repository".git``` where "username" is your personal Github user name. (For example: ```https://github.com/keithbrown/bridgepoint.git```)

2) This document may be used in Linux or Windows, but its examples use Linux. In Windows, cygwin is used to ease setup. Throughout this document we use "~" in the example path names.  If you are building on Windows replace "~" with  "c:" (or "/cygdrive/c" when using the cygwin shell).  Additionally, for Windows, when the instructions read Launcher.sh, replace with Launcher.bat.

3) This document uses ```~/git``` as the root folder for git repostiories, and it uses ```~/workspace``` as the development workspace. You can substitute any folder you desire, but you must be consistent.

4) <a id="help"></a>If you have any problems or questions, check the [Build FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#bpdevelopers) or post to the [xtUML.org Forums] (https://xtuml.org/community/forum/xtuml-forum/) for help.


Instructions
------------

  - If you do not already have a Github.com account, [create one now.](https://github.com/join)

  - For each of the following git repositories create a fork:  
    __WARNING!:__ If you already have a fork, [assure your fork is up to date]( https://help.github.com/articles/merging-an-upstream-repository-into-your-fork).
    - https://github.com/xtuml/bridgepoint
    - https://github.com/xtuml/mc
    - https://github.com/xtuml/packaging
    - https://github.com/xtuml/models
    
  - [Download the latest released version of the tool](http://xtuml.org/download/ "xtuml.org/download").
  
  - Install BridgePoint.  
    - You may unzip wherever you like, but these instructions assume:  ```~/xtuml```

  - The following 3rd party tools are required to build BridgePoint.  Install them now.    
    - __LINUX__ - For convience installing
    under Linux Ubuntu, the installation commands are presented below.  If installing in a 
    different Linux environment you must use the commands appropriate for your environment.
    ```
    sudo apt-get install libxtst6:i386 libgtk2.0-0:i386 gtk2-engines:i386 gtk2-engines-*:i386 --reinstall unity-gtk2-module:i386 libgtkmm-2.4-1c2:i386 libcanberra-gtk-module:i386 tofrodos wine libstdc++5 g++ ant git default-jdk  
    ```
    - __WINDOWS__
      - Perl ([We recommend strawberry perl.](http://strawberryperl.com/ "strawberry perl"))
      - [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html "Oracle JDK")
      - [Cygwin](http://cygwin.com/install.html "Cygwin Install") (Make sure to select Git)

  - Clone the repositories:
  ```
  git clone https://github.com/"username"/bridgepoint.git ~/git/bridgepoint
  git clone https://github.com/"username"/mc.git ~/git/mc
  git clone https://github.com/"username"/packaging.git ~/git/packaging
  git clone https://github.com/"username"/models.git ~/git/models
  ```
    
  - Edit ```~/xtuml/BridgePoint/eclipse/Launcher.sh``` in a text editor (in Windows Launcher.bat)
    - Directly after "export BP_JVM=..." add the following (on Windows substitute "set" for "export"):
    ```      
      export XTUML_DEVELOPMENT_REPOSITORY=~/git/bridgepoint
      export XTUML_TEST_MODEL_REPOSITORY=~/git/models/test/
      export XTUML_PRIVATE_MODEL_REPOSITORY=~/git/modelsmg/test/
    ```
  - Prepare the development environment to call gen_erate. In the command below replace "\<version\>" with the version of the product:
  ```
  cp -R ~/xtuml/BridgePoint/eclipse/plugins/org.xtuml.bp.mc.c.binary_<version>/mc3020/* ~/git/bridgepoint/src/org.xtuml.bp.mc.c.binary/mc3020
  ```
  - Prepare your development workspace with the required preferences. 
  ```
  mkdir -p ~/workspace/.metadata/.plugins/org.eclipse.core.runtime/.settings
  cp -f ~/git/bridgepoint/utilities/build/preferences/*  ~workspace/.metadata/.plugins/org.eclipse.core.runtime/.settings
  ```

  - Launch BridgePoint (```~/xtuml/BridgePoint/eclipse/Launcher.sh```)
    - During startup, enter the name of a new eclipse workspace that will become your development workspace.   
    (Example:  ```~/workspace```)

  - Switch to the git repository perspective and add the repositories that were cloned above.
  
  - Import existing projects from the BridgePoint repository into your workspace.
    - __WARNING!:__ Ensure "Search for nested projects" is Unchecked.
    - __WARNING!:__ Import all projects from this bridgepoint repository, but do NOT import any projects from the other repositories.  Only projects from bridgepoint are needed in the workspace.

  - Switch to the Java perspective
    - Close these projects (Right click on the project, select Close Project):
      - libTrans
      - pt_antlr

  - Select Project > Build Automatically

  - The build will take a while and should finish successfully

### Congratulations!  Your environment is now built and ready for BridgePoint development.

  - [If you had any build problems get help here.](#help)
  - [If you want to run unit tests on MS Windows you must perform some additional steps described here.](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#windowstesting) 

