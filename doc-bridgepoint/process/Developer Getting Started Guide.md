# Developer Getting Started Guide
This document provides the information required to create a fully functional BridgePoint development environment.  This will allow the developer to debug, modify, and extend BridgePoint functionality and execute BridgePoint unit tests.

Preparation 
-----------
[1] [This is a link to git documentation that describes working with forks.](https://help.github.com/articles/fork-a-repo/ "fork-a-repo") BridgePoint development requires developers to have a working knowlegde of git and git forks. Throughout this document we will refer to the repositories using the formula: ```https://github.com/"username"/"repository".git``` where "username" is your personal Github user name. (For example: ```https://github.com/keithbrown/bridgepoint.git```)

[2] This document may be used in Linux or Windows, but it's examples use Linux. In Windows, cygwin is used to ease setup. Throughout this document we use "~" in the example path names.  If you are building on Windows replace "~" with  "c:" (or "/cygdrive/c" when using the cygwin shell).  Additionally, for Windows, when the instructions read Launcher.sh, replace with Launcher.bat.

[3] If you have any problems or questions, post to the [xtuml.org forums] (https://xtuml.org/community/forum/xtuml-forum/) for help.


Instructions
------------

  - If you do not already have a Github.com account, [create one now.](https://github.com/join)

  - For each of the following git repositories create a fork:
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
    sudo apt-get install libxtst6:i386  
    sudo apt-get install libgtk2.0-0:i386  
    sudo apt-get install gtk2-engines:i386  
    sudo apt-get install gtk2-engines-*:i386  
    sudo apt-get install --reinstall unity-gtk2-module:i386  
    sudo apt-get install libgtkmm-2.4-1c2:i386  
    sudo apt-get install libcanberra-gtk-module:i386  
    sudo apt-get install tofrodos   
    sudo apt-get install wine  
    sudo apt-get install libstdc++5  
    sudo apt-get install g++  
    sudo apr-get install ant  
    sudo apr-get install git  
    sudo apt-get install default-jdk  
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
    - Directly after "set BP_JVM=..." add the following:
    ```      
      set XTUML_DEVELOPMENT_REPOSITORY=~/build/git/bridgepoint
      set XTUML_TEST_MODEL_REPOSITORY=~/build/git/models/test/
      set XTUML_PRIVATE_MODEL_REPOSITORY=~/build/git/modelsmg/test/
    ```
    - Between the BP_JVM argument and the first command-line argument put this:
    ```
      -pluginCustomization ~/git/bridgepoint/utilities/build/plugin_customization.ini
    ```

  - Prepare the development environment to call gen_erate. In the command below replace "\<version\>" with the version of the product:
  ```
  cp -R ~/xtuml/BridgePoint/eclipse_extensions/BridgePoint/eclipse/plugins/org.xtuml.bp.mc.c.binary_<version>/mc3020/* ~/git/bridgepoint/src/org.xtuml.bp.mc.c.binary/mc3020
  ```

  - Launch BridgePoint (```~/xtuml/BridgePoint/Launcher.sh```)
    - During startup, enter the name of a new eclipse workspace that will become your development workspace.   
    (Example:  ```~/workspace```)

  - Switch to the git repository perspective and add the repositories that were cloned above.
  
  - Import existing projects from the BridgePoint repository into your workspace.
    - __WARNING!: Ensure "Search for nested projects" is Unchecked.__
    - __WARNING!: Only import the BridgePoint and MC projects. The other repositoires are not needed for build.__

  - Switch to the Java perspective
    - Close these projects (Right click on the project, select Close Project):
      - libTrans
      - pt_antlr

  - Select Project > Build Automatically

  - The build will take a while and should finish successfully

Additional Instructions specific to MS Windows
----------------------------------------------
  - Configure Windows Vista or 7 for unit test running.   Skip this step for other OSes.
    - Exit BridgePoint
    - Bring up the Windows Color and Appearance settings
      - Right click on the desktop background and select Personalize
      - In Vista select the Windows Color and Appearance hyperlink
      - In Windows 7 select the Windows Color hyperlink
      - In the window that appears, change the following attributes:
        - Active Title Bar    Size: 25 Font: Trebuchet 10
        - Border Padding      Size: 0
        - Caption Buttons     Size: 25
        - Icon                Size: 32 Font: Tahoma 8
        - Inactive Title Bar  Size: 25 Font: Trebuchet 10
        - Menu                Size: 19 Font: Tahoma 8
        - Message Box                  Font: Tahoma 8
        - Palette Title       Size: 17 Font: Tahoma 8
        - Selected Items      Size: 19 Font: Tahoma 8
        - Tooltip                      Font: Tahoma 8
    - If you have a smaller screen or resolution potential you may need to
          configure the start menu to not always be on top, or set it to auto-hide.
    - Note: Do NOT use the Windows setting that scales text to make it easier to see. 
              This setting, in Windows 7, is found here:
              Personalize > Display > "Make it easier to read what is on your machine"
              That setting must be set to "smaller" 100%
              It is is adjusted your graphical compare restuls will not match,

### Congratulations!  Your environment is now built and ready for BridgePoint development.

