Developer Getting Started Guide
----------------------------------------


Abstract
------------
This document is intended to provide an the information needed to create a fully functional development environment capable of building and testing BridgePoint eclipse plug-ins.  This will allow the developer to modify and extend the existing plug-ins as well as test changes made using the existing test unit tests.

References
------------
[1]  http://xtuml.org/download/ This is the location of BridgePoint Downloads.

[2] https://help.github.com/articles/fork-a-repo/ This is a link to git documentation that describes working with forks. BridgePoint development requires developers to have a working knowlegde of git and git forks.  
Throughout this document, we will refer to the repositories using the formula ```https://github.com/"username"/"repository".git``` where "username" is your personal Github user name. (For example: ```https://github.com/keithbrown/bridgepoint.git```)


Preparations
------------
### GitHub
  - If you do not already have a Github.com account, create one now.

  - For each of the following git repositories:
    - https://github.com/xtuml/bridgepoint
    - https://github.com/xtuml/models
    
  - Download the latest released version of the tool.  You will find all the releases at [1].
  
  - Install BridgePoint.  You may install wherever you like, but these instructions 
     assume:  ```c:/xtuml/BridgePoint```

  - The following packages are required to build BridgePoint.  Install them now.  For convience installing
    under Linux Ubunutu the installation commands are presented below for Ubunutu.  If installing in a 
    different Linux environment use must use the commands appropraite for your environment.  
    apt-get install libxtst6:i386  
    apt-get install libgtk2.0-0:i386  
    apt-get install gtk2-engines:i386  
    apt-get install gtk2-engines-*:i386  
    apt-get install --reinstall unity-gtk2-module:i386  
    apt-get install libgtkmm-2.4-1c2:i386  
    apt-get install libcanberra-gtk-module:i386  
    apt-get install tofrodos   
    apt-get install wine  
    sudo apt-get install libstdc++5  
    sudo apt-get install g++  
    sudo apr-get install ant  
    sudo apr-get install git  
    sudo apt-get install  openjdk-7-jdk  
    cd /usr/bin  
    sudo ln -s  fromdos dos2unix 
    sudo ln -s todos unix2dos  
  
  - Launch BridgePoint.  During startup, enter the name of a new eclipse workspace that will become your development workspace. ```Example:  ~/workspace```

  - Clone the BridgePoint repository: ```https://github.com/"username"/bridgepoint.git```
    - WARNING!: For the Local Destination enter a folder where you want to store your local git repositories. __DO NOT SELECT YOUR WORKSPACE FOLDER!__ ```Example: c:/git  (These instructions assume this is the folder used)```

  - Import the BridgePoint projects into your workspace
    - NOTE!: Ensure "Search for nested projects" is UNchecked.

  - Switch to the Java perspective: Using menu Window > Open Perspective > Other... > Java

  - Close these projects (Right click on the project, select Close Project):
    - libTrans
    - MC-Java.test
    - pt_antlr

  - Clone the ```https://github.com/"username"/models.git``` repository
    - NOTE: Do NOT import these project into your workspace.
    
  - Apply required preferences
    NOTE TODO: Most, but not all of these are in c:/git/bridgepoint/utilities/build/plugin_customization.ini. We can probably put them all in here, and specify it when the -pluginCustimization flag to the VM.  We should also be able to set the unit test history here.

  - TODO: Make sure the default installation includes this: ```c:/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup/xtUML_dev_eclipse_addons.p2f```

  - Exit BridgePoint

  - Edit ```c:/MentorGraphics/BridgePoint/eclipse/Launcher.bat``` in a text editor
    - Directly after "set BP_JVM=..." add the following:
```      
      set XTUML_DEVELOPMENT_REPOSITORY=C:/git/bridgepoint
      set XTUML_TEST_MODEL_REPOSITORY=C:/git/models/test/
      set XTUML_PRIVATE_MODEL_REPOSITORY=C:/git/modelsmg/test/
```

  - Prepare the development environment to call gen_erate:
    - Open Windows explorer and navigate to the BridgePoint plug-in installation folder.  The default is:
```
c:/xtuml/BridgePoint/eclipse_extensions/BridgePoint/eclipse/plugins/org.xtuml.bp.mc.c.binary_version/mc3020
```
    - Select all files and folders in the above folder
    - Select Copy
    - Open the Model Explorer view in Eclipse and navigate to: ```c:/git/bridgepoint/src/org.xtuml.bp.mc.c.binary_version/mc3020```
    - Paste the previous selection into this folder

  - Start BridgePoint
   
  - Select Project > Build Automatically

  - The build will take a while and should finish successfully

### Instructions specific to Windows 7 or Windows Vista
  - Exit BridgePoint
    - Configure Windows Vista or 7 for unit test running.   Skip this step for other OSes.
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
        - Note: Do NOT use the Windows setting that scaling text to make it easier to see. 
              This setting, in Windows 7, is found here:
              Personalize > Display > "Make it easier to read what is on your machine"
              That setting must be set to "smaller" 100%
              It is is adjusted your graphical compare restuls will not match,

## Congratulations!  Your environment is now built and ready for unit testing.

