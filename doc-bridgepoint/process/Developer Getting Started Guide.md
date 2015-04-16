Developer Getting Started Guide
----------------------------------------


Abstract
------------
With the release of the BridgePoint code as Open Source Software in November 2014, many users have become interested in also becoming BridgePoint developers.

This document is intended to provide an interested xtUML & Java developer the information needed to create a fully functional development environment capable of building and testing BridgePoint eclipse plug-ins.  This will allow the developer to modify and extend the existing plug-ins as well as create new plug-ins that integrate into BridgePoint.

References
------------
[1]  http://xtuml.org/download/  (Note the download files are appended with the jar extension)

Preparations
------------
### GitHub
  - The BridgePoint source code repositories are housed on Github.com in several repositories under the xtUML organization.  If you do not already have a Github.com account, create one now.

  - In order to manage your edits to the source, you need to fork the source code.  For each of the repositories, you must navigate to the source home on Github and click the "Fork" button in the upper right corner and select your github account:
    - https://github.com/xtuml/bridgepoint
    - https://github.com/xtuml/models
    
  - Once the process is complete, you will have a fork of each of the repositories under your own Github account.  For the remainder of this document, we will refer to the repositories using the formula ```https://github.com/"username"/"repository".git``` where "username" is your personal Github user name. (For example: ```https://github.com/keithbrown/bridgepoint.git```)
  
  - If you previously had a github fork and are re-using it then follow these steps:
    - On the https://github.com/"username"/<repository> site select the Pull requests link on the right of the page
    - Then click the New pull request button on the right of the page (green button)
    - Next choose the base fork pull down, select your fork/<repository>
    - Now choose the head fork pull down, select xtuml/<repository>
    - Click the Create pull request button (green button)
    - Enter a message describing the pull request (updating to the latest xtuml master)
    - Again click the Create pull request button (green button)
    - On the next page click the Merge pull request button (green button)
    - Enter a message describing the merge (updating to the latest xtuml master) 
    - Click the Confirm Merge button (green button)
    - The fork is now up to date
    
  - Install a java JRE or JDK.

### OS-Specific Instructions
Any instructions below that are OS specific will be indicated.

Installation Steps
------------
### Install wine for linux
  - Install the latest wine for your OS
    e.g, username@hostname:~ sudo apt-get install wine
    
### Install BridgePoint
  - Download the latest released version of the tool.  You will find all the releases at [1].

  - Run the installer, the file name is BridgePoint_(bp_version)_<OS>.jar.
    ```bash
    For Windows:
      - Open the command prompt (cmd) and run
      		c:\> java -jar <download location>/BridgePoint_<bp_version>_<OS>.jar -console
    For linux:
      - Open a terminal and run
      		username@hostname:~/Downloads$ java -jar <download location>/BridgePoint_<bp_version>_<OS>.jar -console
    ```
      
    You may install wherever you like, but these instructions
     For Windows: 
     	assume the default:  ```c:/xtuml/BridgePoint```
     For Linux:
     	assume the default:  ```/home/$USER/xtuml/BridgePoint```

### Clone the Primary Github Repository
  - Launch BridgePoint by executing the Launcher.bat (Windows) or the Launcher.sh (Linux) file under the (installed_location)/eclipse directory.  During startup, enter the name of a new eclipse workspace that will become your development workspace. ```Windows Example:  c:/workspace/current```, ```Linux Example:  /home/$USER/current/```

  - Close the Welcome to BridgePoint UML page if open

  - Open the Git Repository Exploring perspective (Window > Open Perspective > Git Repository Exploring)

  - In the left hand view, select the option to Clone a Git repository.  This will pop up the "Clone Git Repository" dialog.
    - Enter the URI: ```https://github.com/"username"/bridgepoint.git```
    - Enter your user name and password for your github.com account
    - Select Next
    - Select all the branches
    - Select Next
    - For the Local Destination enter a folder where you want to store your local git repositories. __DO NOT SELECT YOUR WORKSPACE FOLDER!__ ```Example: (Windows) c:/git or (Linux) /home/$USER/git  (These instructions assume this is the folder used)```
    - Select Finish.
    
### Set up the Source Code Projects for Eclipse Development
  - Import the projects into your workspace
    - In the Git Repository Exploring perspective select "bridgepoint"
    - Right Click > Import Projects...
    - Select "Import existing projects" and then Next
    - Select all projects (do not select any other options)
    - Ensure "Search for nested projects" is Unchecked.
    - Select Finish

  - Switch to the Java perspective: Using menu Window > Open Perspective > Other... > Java

  - Close these projects (Right click on the project, select Close Project):
    - libTrans
    - MC-Java.test

### Clone Additional Repositories
  - Switch to the Git Repository Exploring perspective
  
  - In the following steps you will clone more git repositories, for these repositories you are not required to make a specific selection in the "Import projects into workspace" checkbox.  You may do as you wish.
  
  - In the Git Repositories view, select the "Clone a Git Repository and add the clone to this view" button
  
  - Using this same procedure, clone the ```https://github.com/"username"/models.git``` repository
  
### Configure System for Development
  - For Windows copy ```c:/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup/dropins/*``` into ```c:/xtuml/BridgePoint/eclipse/dropins```
  - For linux copy ```/home/$USER/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup/dropins/*``` into ```/home/$USER/xtuml/BridgePoint/eclipse/dropins```

### Update Eclipse Preferences to Common and Required Settings
  - Open the Navigator view (Window > Show View > Other... > General > Navigator)
    - Right-click in white space
    - Select Import... > General > Preferences
    - Select Next
    - Select Browse
    - Navigate to the git bridgepoint repository:/doc-bridgepoint/process/development-workspace-setup
       - In Windows this would be: ```c:/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup```
       - In linux this would be: ```/home/$USER/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup```
    - Choose the EclipsePreferences.epf file and click OK
    - Assure that "Import all" is selected
    - Select Finish

### Install Required Add-ons
  - Open the Navigator view
    - Right-click in white space
    - Select Import... > Install > Install Software Items from File
    - Select Next
    - Select Browse...
    - Navigate to the git bridgepoint repository:/doc-bridgepoint/process/development-workspace-setup
       - In Windows this would be: ```c:/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup```
       - In linux this would be: ```/home/$USER/git/bridgepoint/doc-bridgepoint/process/development-workspace-setup```
    - Select the xtUML_dev_eclipse_addons.p2f file
    - Select OK
    - Select Next
    - Select Next again
    - Accept the license agreement
    - Select Finish.  The files are installed and you are prompted to restart. DO NOT restart yet.
    
  - Exit BridgePoint

### Modify Launcher.bat with Required Development Settings
  - Open the launcher script for the BridgePoint installation in a text editor.
    - In Windows this is: ```c:/xtuml/BridgePoint/eclipse/Launcher.bat```
	- In linux this is: ```/home/$USER/xtuml/BridgePoint/eclipse/Launcher.sh```
	
  - Directly after "set BP_JVM=..." add the following:
```      
For Windows GIT_PATH is:
	C:/git

For linux GIT_PATH is:
	/home/$USER/git

For Windows use set:
    set XTUML_DEVELOPMENT_REPOSITORY=<GIT_PATH>/bridgepoint
    set XTUML_TEST_MODEL_REPOSITORY=<GIT_PATH>/models/test
    
For linux use export:
    export XTUML_DEVELOPMENT_REPOSITORY=<GIT_PATH>/bridgepoint
    export XTUML_TEST_MODEL_REPOSITORY=<GIT_PATH>/models/test

```

### Setup Development Environment to Run MC3020 Under a Launch Configuration
  - Start BridgePoint using the bat (windows) or sh (linux) script.  Opening the workspace created above.
  
  - Open a file explorer and navigate to the BridgePoint plug-in installation folder.  The defaults are:
```
For Windows:

c:/xtuml/BridgePoint/eclipse/plugins/org.xtuml.bp.mc.c.binary_<bp_version>/mc3020

For linux:

/home/$USER/xtuml/BridgePoint/eclipse/plugins/org.xtuml.bp.mc.c.binary_<bp_version>/mc3020
```
  
  - Select all files and folders in the above folder
  
  - Select Copy
  
  - Open the Navigator view in Eclipse and navigate to: ```org.xtuml.bp.mc.c.binary/mc3020```
  
  - Paste the previous selection into this folder

### Install Perl
  - Install perl, any perl installation will work.  For a free version on windows you can use Strawberry Perl or download cygwin and install the perl package.
  
  - Once installed be sure to add the perl location to your PATH variable.

### Adjust ant permissions in linux
  ```bash
  - Change directory to <bp_install>/eclipse/plugins/org.apache.ant_1.8.2.<latest_version>/bin
  - Run chmod u+x antRun
  ```
### Build BridgePoint
  - Select Project > Build Automatically

  - The build will take a while and should finish successfully

### Prepare to Run Unit Tests
- If on the Windows operating system perform the following (DO NOT if using linux)
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

### Set the Unit Test History to Assure the Test Summary Tool Capture all Results
  - Open the JUnit view
   
  - Select the drop-down arrow in the upper-right of the view
  
  - Select the History... option
  
  - Set "Maximum count of remembered test runs" to 30 
	

## Congratulations!  Your environment is now built and ready for unit testing.

