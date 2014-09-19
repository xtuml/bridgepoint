HOWTO Create a New Development Workspace on Windows
----------------------------------------

References
------------
[1]  https://github.com/xtuml/internal/blob/master/doc-internal/process/HOWTO-create-new-development-workspace-Linux.md  
[2]  https://github.com/xtuml/internal/blob/master/doc-internal/process/templates/checklists/new-start.chk  
[3]  http://tucson.wv.mentorg.com/tiger_releases/

Preparations
------------
- NOTE 1: Prior to using this checklist you should already have an account on github.  If you do not, then see [2]  
- NOTE 2: These instructions are specific to Windows.  For the Linux version of these instructions see [1]


Steps
------------
<pre>
_- Download the latest released version of the tool.  You will find all the releases at [3]
  _- Run the installer.  You can install wherever you like, but these instructions 
     assume the default:  c:/MentorGraphics/BridgePoint
   
_- Launch the tool open a new eclipse workspace that will become your development workspace.
      Example:  c:/workspace/current

_- Open the Git Repository Exploring perspective

_- Select the option to clone and add to view:
   URI: https://github.com/xtuml/internal.git
  _- Enter your user name and password
  _- Select Next
  _- Select all the branches
  _- Select Next
  _- For the Local Destination select your github/repos folder (e.g. c:/git, which these instructions will use).
     NOTE: DO NOT SELECT YOUR WORKSPACE!  This is the local repository location.
  _- Select Finish.

_- Import the projects into your workspace
  _- In the Git Repository Exploring perspective select "internal"
  _- Right Click > Import Projects...
  _- Select "Import Projects Into Existing Workspace" and then "Next"
  _- Select all projects (do not select any other options)
  _- Ensure "Search for nested projects" is UNchecked.
  _- Select Finish

_- Switch to the Java perspective

_- Close these projects:
   libTrans
   MC-Java.test
   pt_antlr

_- Using the procedure from above, also clone these repositories:
   https://github.com/xtuml/editor.git
   https://github.com/xtuml/mc.git
   https://github.com/xtuml/models.git
   https://github.com/xtuml/modelsmg.git
  _- You can choose to import the contained projects from these repositories 
     into your workspace or not.  You are not required to do so.

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
 
