Developer Getting Started Guide
----------------------------------------


Abstract
------------
With the release of the BridgePoint code as Open Source Software in November 2014, many users have become interested in also becoming BridgePoint developers.

This document is intended to provide an interested xtUML & Java developer the information needed to create a fully functional development environment capable of building and testing BridgePoint eclipse plug-ins.  This will allow the developer to modify and extend the existing plug-ins as well as create new plug-ins that integrate into BridgePoint.

References
------------
[1]  http://xtuml.org/download/


Preparations
------------
### GitHub
  - The BridgePoint source code repositories are housed on Github.com in several repositories under the xtUML organization.  If you do not already have a Github.com account, create one now.

  - In order to manage your edits to the source, you need to fork the source code.  For each of the repositories, you must navigate to the source home on Github and click the "Fork" button in the upper right corner:
    - https://github.com/xtuml/bposs
    - https://github.com/xtuml/bridgepoint
    - https://github.com/xtuml/models
    - https://github.com/xtuml/mc
 
  - Once the process is complete, you will have a fork of each of the repositories under your own Github account.  For the remainder of this document, we will refer to the repositories using the formula ```https://github.com/"username"/"repository".git``` where "username" is your personal Github user name. (For example: ```https://github.com/keithbrown/bposs.git```)

### OS-Specific Instructions
The instructions for Windows and Linux development environments are very similar.  However, the instructions provided here are currently focused on a Windows environment.  Linux-specific instructions are forthcoming.


Windows Steps
------------
### Install BridgePoint
  - Download the latest released version of the tool.  You will find all the releases at [1].

  - Run the installer.  You many install wherever you like, but these instructions 
     assume the default:  ```c:/MentorGraphics/BridgePoint```

### Clone the Primary Github Repository
  - Launch BridgePoint.  During startup, enter the name of a new eclipse workspace that will become your development workspace. ```Example:  c:/workspace/current```

  - Open the Git Repository Exploring perspective

  - In the left hand view, select the option to clone and add to view.  This will pop up the "Clone Git Repository" dialog.
    - Enter the URI: ```https://github.com/"username"/bposs.git```
    - Enter your user name and password for your github.com account
    - Select Next
    - Select all the branches
    - Select Next
    - For the Local Destination enter a folder where you want to store your local git repositories. __DO NOT SELECT YOUR WORKSPACE FOLDER!__ ```Example: c:/git  (These instructions assume this is the folder used)```
    - Select Finish.

### Set up the Source Code Projects for Eclipse Development
  - Import the projects into your workspace
    - In the Git Repository Exploring perspective select "bposs"
    - Right Click > Import Projects...
    - Select "Import Projects Into Existing Workspace" and then Next
    - Select all projects (do not select any other options)
    - Ensure "Search for nested projects" is UNchecked.
    - Select Finish

  - Switch to the Java perspective: Using menu Window > Open Perspective > Other... > Java

  - Close these projects (Right click on the project, select Close Project):
    - libTrans
    - MC-Java.test
    - pt_antlr

### Clone Additional Repositories
  - Switch to the Git Repository Exploring perspective
  
  - In the following steps you will clone more git repositories, for these repositories you are not required to make a specific selection in the "Import projects into workspace" checkbox.  You may do as you wish.
  
  - In the Git Repositories view, select the "Clone a Git Repository and add the clone to this view" button
  
  - Using the same procedure as earlier, clone the ```https://github.com/"username"/mc.git``` repository
 
  - Using this same procedure, clone the ```https://github.com/"username"/models.git``` repository
  
  - Using this same procedure, clone the ```https://github.com/"username"/bridgepoint.git``` repository

### Configure System for Development
  - Copy ```c:/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup/BridgePointDev``` into ```c:/work```

  - Copy ```c:/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup/dropins/*``` into ```c:/MentorGraphics/BridgePoint/eclipse/dropins```

### Update Eclipse Preferences to Common and Required Settings
  - Open the Navigator view
    - Right-click in white space
    - Select Import... > General > Preferences
    - Select ```c:/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup/EclipsePreferences.epf```
    - Assure that "Import all" is selected
    - Select Finish

### Install Required Add-ons
  - Open the Navigator view
    - Right-click in white space
    - Select Import... > Install > Install Software Items from File
    - Select ```c:/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup/xtUML_dev_eclipse_addons.p2f```
    - Select Finish.  The files are installed and you are prompted to restart. Do NOT restart.
    
  - Exit BridgePoint

### Modify Launcher.bat with Required Development Settings
  - Open ```c:/MentorGraphics/BridgePoint/eclipse/Launcher.bat``` in a text editor

  - Directly after "set BP_JVM=..." add the following:
```      
      set PT_HOME=/work/BridgePointDev/
      set PT_HOME_DRIVE=c:
      set XTUMLGEN_HOME=%MGC_EMBEDDED_HOME%/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.dap.pkg_%BP_VERSION%/bridgepoint
      set XTUML_DEVELOPMENT_REPOSITORY=C:/git/bposs
      set XTUML_TEST_MODEL_REPOSITORY=C:/git/models/test/
      set XTUML_PRIVATE_MODEL_REPOSITORY=C:/git/modelsmg/test/
```

### Install Perl
  - Install perl, any perl installation will work.  For a free version you can use Strawberry Perl or download cygwin and install the perl package.
  
  - Once installed be sure to add the perl location to your PATH variable.

### Build BridgePoint
  - Start BridgePoint
   
  - Select Project > Build Automatically

  - The build will take a while and should finish successfully

### Prepare to Run Unit Tests
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
  
- Launch BridgePoint

### Setup Development Environment to Run MC3020 Under a Launch Configuration
  - Open Windows explorer and navigate to the BridgePoint plug-in installation folder.  The default is:
```
c:/MentorGraphics/BridgePoint/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.mc.c.binary_version/mc3020
```
  
  - Select all files and folders in the above folder
  
  - Select Copy
  
  - Open the Model Explorer view in Eclipse and navigate to: ```c:/git/bposs/src/com.mentor.nucleus.bp.mc.c.binary_version/mc3020```
  
  - Paste the previous selection into this folder

### Set the Unit Test History to Assure the Test Summary Tool Capture all Results
  - Open the JUnit view
   
  - Select the drop-down arrow in the upper-right of the view
  
  - Select the History... option
  
  - Set "Maximum count of remembered test runs" to 30 
	

## Congratulations!  Your environment is now built and ready for unit testing.

