HOWTO create a new development workspace
----------------------------------------
Note: Prior to using this checklist you should already have an account
      on github.  If you do not, then see:
      https://github.com/xtuml/internal/blob/master/doc-internal/process/templates/checklists/new-start.chk

_- Clone the github/internal repository:
   _- open a cygwin shell
   _- cd <Git local repository folder>
   _- ./git clone https://github.com/xtuml/internal
   _R git/internal is now cloned
   
_- Download the latest released version of the tool.  You will find all the 
   releases here:
   http://tucson.wv.mentorg.com/tiger_releases/R<version>
   
_- Run the installer down-loaded in the previous step.  You can installer
   wherever you like, but these instructions assume the default:
   c:/MentorGraphics/BridgePoint
   
_- Using the files in the file above:
   _- copy <git>/internal/doc-internal/process/templates/checklists/development-workspace-setup/BridgePointDev into c:/MentorGraphics
   _- copy <git>/internal/doc-internal/process/templates/checklists/development-workspace-setup/dropins/* into C:\MentorGraphics\BridgePoint\eclipse\dropins

_- Launch the tool using the desktop shortcut that was created and open a 
   new eclipse workspace that will become your development workspace.
      Example:  c:/workspace/current

_- Update eclipse preferences to common and required settings:
   _- Open the navigator view
	_- Right-click in white space
	_- Import... > General > preferences
	_- Select <git>/internal/doc-internal/process/templates/checklists/development-workspace-setup/EclipsePreferences.edf
	_- Assure that "Import all" is selected
	_- select finish
	
_- Open the SVN perspective

_- You will be promoted to install the SVN connector.  Install the latest 
   available of the SVN Kit connector.
   
_R You will be prmooted to restart.  Do NOT restart yet.
	
_- Install required add-ons:
	_- Open the navigator view
	_- Right-click in white space
	_- Install... > Software from file
	_- Select <git>/internal/doc-internal/process/templates/checklists/development-workspace-setup/xtUML_dev_eclipse_addons.p2f
    _- Select finish
    _R Files are installed and you are promoted to restart
    _- Do NOT restart
    
_- Exit eclipse

_- Modify Launcher.bat with required development settings:
	_- Open C:\MentorGraphics\BridgePoint\eclipse\BridgePoint_Launcher.bat in a
       text editor
    _- Update the "set MGLS_LICENSE_FILE..." line to :
          set MGLS_LICENSE_FILE=c:\MentorGraphics\license.dat   
          For VMs (or even locally at your discretion) add:
          set MGLS_LICENSE_FILE=1717@wv-lic-01.wv.mentorg.com;1717@wv-lic-02.wv.mentorg.com;1717@svr-azt-eng-01  
    _- Directly after "set BP_VERSION=..." add the following:
          set PT_HOME=\work\BridgePointDev\
          set PT_HOME_DRIVE=c:
          set XTUMLGEN_HOME=%MGC_EMBEDDED_HOME%\eclipse_extensions\BridgePoint\eclipse\plugins\com.mentor.nucleus.bp.dap.pkg_%BP_VERSION%\bridgepoint
 		  set XTUML_TEST_MODEL_REPOSITORY=C:/git/repos/models/test/
		  set XTUML_PRIVATE_MODEL_REPOSITORY=C:/git/repos/modelsmg/test/
		  set XTUML_DEVELOPMENT_REPOSITORY=C:/git/repos/internal

_- Delete the <git>/internal clone folder you created earlier

_- Install perl
   - Install perl, any perl installation will work.  For a free version you can
     always download cygwin, installing the perl package.
   - Once installed be sure to add the perl location to your PATH variable.

_- Launch eclipse using the desktop shortcut

_- Open the Git repository exploring perspective

_- Select the option to clone and add to view:
   URI: https://github.com/xtuml/internal.git
   _- enter your user name and password
   _- Select next
   _- Select all the branches
   _- select next
   _- For the Local Destination select your github/repos folder.
      NOTE: DO NOT SELECT YOUR WORKSPACE!  This is the local RCS repository.
   _- Select finish.

_- Using the procedure from above, also clone these repositories:
   https://github.com/xtuml/editor.git
   https://github.com/xtuml/mc.git
   https://github.com/xtuml/models.git
   https://github.com/xtuml/modelsmg.git
   
_- Switch to the Java perspective

_- Close these projects:
   libTrans
   pt_antrl
   
_- Selected build automatically

_R The build is successful

_- Prepare to run unit tests:
    _- Exit Eclipse
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
	     - Note: Do NOT use the Windows setting that scaling text to make it easier to 
	             see. 
	             This setting, in Windows 7, is found here:
	             Personalize > Display > "Make it easier to read what is on your machine"
	             That setting must be set to "smaller" 100%
	             It is is adjusted your graphical compare restuls will not match,
	_- Launch Eclipse

_- Setup development environment to run MC3020 under a launch configuration
   _- Open Windows explorer and navigate to the BridgePoint plugin 
      installation folder.  The default is:
      C:\MentorGraphics\BridgePoint\eclipse_extensions\BridgePoint\eclipse\plugins\com.mentor.nucleus.bp.mc.mc3020_<ver>\mc3020
   _- Select all files and folders in the above folder
   _- Select copy
   _- Open the Model Explorer view in Eclipse and navigate to:
      <workspace>/com.mentor.nucleus.bp.mc.mc3020/mc3020
   _- Paste the previous selection into this folder
   _- Open the Model Explorer view in Eclipse and navigate to:
      <workspace>/com.mentor.nucleus.bp.mc.c.binary/mc3020
   _- Paste the previous selection into this folder
       
	
_R Your environment is now built and ready for unit testing.


 