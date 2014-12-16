Developer Getting Started Guide
----------------------------------------

__THIS DOCUMENT IS STILL A DRAFT.  IT IS A WORK IN PROGRESS.  IT IS NOT YET READY FOR USE.__

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
The BridgePoint source code repositories are housed on Github.com.  If you do not already have a Github account, create one now.

### OS-Specific Instructions
For your conveniences, there are two sets of instructions in this guide, one for Windows and one for Linux.  Please proceed to the appropriate section to continue with the setup.


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
    - Enter the URI: ```https://github.com/xtuml/bposs.git```
    - Enter your user name and password for your github.com account
    - Select Next
    - Select all the branches
    - Select Next
    - For the Local Destination enter a folder where you want to store your local git repositories. __DO NOT SELECT YOUR WORKSPACE FOLDER!__ ```Example: c:/git  (These instructions assume this is the folder used)```
    - Select Finish.

### Set up the Source Code Projects for Eclipse Development
  - Import the projects into your workspace
    - In the Git Repository Exploring perspective select "internal"
    - Right Click > Import Projects...
    - Select "Import Projects Into Existing Workspace" and then Next
    - Select all projects (do not select any other options)
    - Ensure "Search for nested projects" is UNchecked.
    - Select Finish

  - Switch to the Java perspective: Using menu ```Window > Open Perspective > Other... > Java"

  - Close these projects (Right click on the project, select Close Project):
```
   libTrans
   MC-Java.test
   pt_antlr
```

### Clone Additional Repositories
- Switch to the Git Repository Exploring perspective
- In the following steps you will clone more git repositories, for these repositories you are not required to make a specific selection in the "Import projects into workspace" checkbox.  You may do as you wish.
- In the Git Repositories view, select the "Clone a Git Repository and add the clone to this view" button
- Using the same procedure as earlier, clone the https://github.com/xtuml/mc.git repository
- Using this same procedure, clone the https://github.com/xtuml/models.git repository

<pre>
_- Configure the tool using the files cloned from the repositories above:
  _- Copy c:/git/internal/doc-internal/process/templates/checklists/development-workspace-setup/BridgePointDev into c:/work
  _- Copy c:/git/internal/doc-internal/process/templates/checklists/development-workspace-setup/dropins/* into c:/MentorGraphics/BridgePoint/eclipse/dropins


_- Update eclipse preferences to common and required settings:
  _- Open the Navigator view
    _- Right-click in white space
    _- Import... > General > Preferences
    _- Select c:/git/internal/doc-internal/process/templates/checklists/development-workspace-setup/EclipsePreferences.epf
    _- Assure that "Import all" is selected
    _- Select Finish
	
_- Install required add-ons:
  _- Open the Navigator view
  _- Right-click in white space
  _- Import... > Install > Install Software Items from File
  _- Select c:/git/internal/doc-internal/process/templates/checklists/development-workspace-setup/xtUML_dev_eclipse_addons.p2f
  _- Select Finish
  _R Files are installed and you are prompted to restart. Do NOT restart
    
_- Exit BridgePoint

_- Modify Launcher.bat with required development settings:
  _- Open C:\MentorGraphics\BridgePoint\eclipse\Launcher.bat in a text editor
  _- Update the "set MGLS_LICENSE_FILE..." line to :
      set MGLS_LICENSE_FILE=c:\MentorGraphics\license.dat   
      For VMs (or even locally at your discretion) add:
      set MGLS_LICENSE_FILE=1717@wv-lic-01.wv.mentorg.com;1717@wv-lic-02.wv.mentorg.com;1717@svr-azt-eng-01  
  _- Directly after "set BP_JVM=..." add the following:
      set PT_HOME=/work/BridgePointDev/
      set PT_HOME_DRIVE=c:
      set XTUMLGEN_HOME=%MGC_EMBEDDED_HOME%\eclipse_extensions\BridgePoint\eclipse\plugins\com.mentor.nucleus.bp.dap.pkg_%BP_VERSION%\bridgepoint
      set XTUML_TEST_MODEL_REPOSITORY=C:/git/repos/models/test/
      set XTUML_PRIVATE_MODEL_REPOSITORY=C:/git/repos/modelsmg/test/
      set XTUML_DEVELOPMENT_REPOSITORY=C:/git/repos/internal

_- Install perl
  _- Install perl, any perl installation will work.  For a free version you can
     always download cygwin, installing the perl package.
  _- Once installed be sure to add the perl location to your PATH variable.

   
_- Start BridgePoint
   
_- Select Project > Build Automatically

_R The build is successful

_- Prepare to run unit tests:
  _- Exit BridgePoint
    _- Configure Windows Vista or Windows 7 for unit test running
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
  _- Launch BridgePoint

_- Setup development environment to run MC3020 under a launch configuration
  _- Open Windows explorer and navigate to the BridgePoint plug-in 
     installation folder.  The default is:
     C:/MentorGraphics/BridgePoint/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.mc.c.binary_"ver"/mc3020
  _- Select all files and folders in the above folder
  _- Select Copy
  _- Open the Model Explorer view in Eclipse and navigate to:
       c:/git/xtuml/internal/src/com.mentor.nucleus.bp.mc.c.binary/mc3020
  _- Paste the previous selection into this folder

_- Set the unit test history to assure the test summary tool capture all results
   _- Open the JUnit view
   _- Select the drop-down arrow in the upper-right of the view
   _- Select the History... option
   _- Set "Maximum count of remembered test runs" to 30 
	
_R Your environment is now built and ready for unit testing.


</pre>
 
Appendix A - Linux Specifics
------------
TODO
