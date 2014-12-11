HOWTO Create a New Development Workspace on Linux
----------------------------------------

References
------------
[1]  https://github.com/xtuml/internal/blob/master/doc-internal/process/HOWTO-create-new-development-workspace.md  
[2]  https://github.com/xtuml/internal/blob/master/doc-internal/process/templates/checklists/new-start.chk  
[3]  http://tucson.wv.mentorg.com/tiger_releases/

Preparations
------------
- NOTE 1: Prior to using this checklist you should already have an account on github.  If you do not, then see [2]  
- NOTE 2: These instructions are specific to Linux.  For the Windows version of these instructions see [1]


Steps
------------
<pre>
_- Prior to starting this checklist you should read the following on xtUML.org:
   -https://www.xtuml.org/community/topic/tips-for-installing-on-linux-mint-15/
   or
   -https://www.xtuml.org/community/topic/tips-for-installing-on-linux-fedora-19/
   
   The one that is relevant to you will be dependent on the linux version
   you are installing.
   
_- Download the latest released version of the tool.  You will find all the releases at [3]

The BridgePoint installer generally will not run out of the box on Linux.  Some system configuration is 
typically required.  Here are some additional packages we suggest be installed and prepatory 
configuration before running the installer.  We've segmented the advice based on the most popular 
distributions:

  Ubuntu 14.04LTS:
    $ sudo apt-get install libxtst6:i386
    $ sudo apt-get install libgtk2.0-0:i386
    $ sudo apt-get install gtk2-engines:i386
    $ sudo apt-get install gtk2-engines-*:i386
    $ sudo apt-get install --reinstall unity-gtk2-module:i386
    $ sudo apt-get install libgtkmm-2.4-1c2:i386
    $ sudo apt-get install libcanberra-gtk-module:i386
    $ sudo apt-get install tofrodos 
    $ sudo apt-get install wine 
    $ sudo apt-get install libstdc++5
    $ sudo apt-get install g++
    $ cd /usr/bin 
    $ sudo ln -s  fromdos dos2unix 
    $ sudo ln -s todos unix2dos  
    
  Fedora 19:
    $ sudo yum install wine 
    $ sudo yum install gcc-c++
    $ sudo yum install dos2unix
    $ sudo yum install compat-libstdc++-33 
    $ sudo yum install gtk2.i686
    
  Debian Wheezy:
    $ sudo apt-get install ia32-libs 
    $ sudo apt-get install ia32-libs-gtk 
    $ sudo apt-get install libgtk2.0-0 
    $ sudo apt-get install lib32ncurses5
  
_- Download the latest released version of the tool.  You will find all the releases at [3].
   Linux installers have an ixl file extension.
  _- Add execute permissions to the downloaded installer file
     (chmod a+x BridgePoint_vX.X.XX_XXXX.ixl)
  _- Make sure you have write permissions on the target install folder

  _- Run the installer.  You can install wherever you like, but these instructions 
     assume the default:  ~/MentorGraphics/BridgePoint
_- Launch the tool open a new eclipse workspace that will become your development workspace.
      Example:  ~/workspace/current

_- Open the Git Repository Exploring perspective

_- Select the option to clone and add to view:
   URI: https://github.com/xtuml/internal.git
  _- Enter your user name and password
  _- Select Next
  _- Select all the branches
  _- Select Next
  _- For the Local Destination select your github/repos folder (e.g. ~/git, which these 
     instructions will use).
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
  _- Copy ~/git/internal/doc-internal/process/templates/checklists/development-workspace-setup/BridgePointDev-Linux into ~/util
    _- Open ~/util/BridgePointDev-Linux/bin/xtumlmc_gen_erate in a text editor.  Update the home dir and version to match your installation.
  _- Copy ~/git/internal/doc-internal/process/templates/checklists/development-workspace-setup/dropins/* into ~/MentorGraphics/BridgePoint/eclipse/dropins
  _- Assure that the pasted files have execution permission
  
_- Update eclipse preferences to common and required settings:
  _- Open the Navigator view
    _- Right-click in white space
    _- Import... > General > Preferences
    _- Select ~/git/internal/doc-internal/process/templates/checklists/development-workspace-setup/EclipsePreferences.epf
    _- Assure that "Import all" is selected
    _- Select Finish
    _R This triggers SVN connector discovery select the most recent SVN Kit connector
	
_- Install required add-ons:
  _- Open the Navigator view
  _- Right-click in white space
  _- Import... > Install > Install Software Items from File
  _- Select ~/git/internal/doc-internal/process/templates/checklists/development-workspace-setup/xtUML_dev_eclipse_addons.p2f
  _- Select Finish
  _R Files are installed and you are prompted to restart. Do NOT restart
    
_- Exit BridgePoint

_- Modify Launcher.sh with required development settings:
  _- Open ~/MentorGraphics/BridgePoint/eclipse/Launcher.sh in a text editor
  _- (Skip for BridgePoint 4.2.0 and on) Update the "set MGLS_LICENSE_FILE..." line to :
      export MGLS_LICENSE_FILE=~/MentorGraphics/license.dat   
      For VMs (or even locally at your discretion) add:
      export MGLS_LICENSE_FILE=1717@wv-lic-01.wv.mentorg.com;1717@wv-lic-02.wv.mentorg.com;1717@svr-azt-eng-01  
  _- Directly after "export BP_JVM=..." add the following:
      export PT_HOME=/home/<your username>/util/BridgePointDev-Linux
      export PT_HOME_DRIVE=
      export XTUMLMC_HOME=$BPHOMEDIR/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.mc.c.binary_$BP_VERSION/
      export XTUML_TEST_MODEL_REPOSITORY=~/git/models/test/
      export XTUML_PRIVATE_MODEL_REPOSITORY=~/git/modelsmg/test/
      export XTUML_DEVELOPMENT_REPOSITORY=~/git/internal

_- Add execute permission to the generator utility scripts
   _- chmod a+x ~/util/BridgePointDev-Linux/mc3020/bin/xtumlmc_build.exe
   _- chmod a+x ~/util/BridgePointDev-Linux/bin/xtumlmc_gen_erate
_- Remove unwanted carriage returns in one of the generator utility scripts
   _- cd ~/MentorGraphics/BridgePoint4200/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.mc.c.binary_4.2.0/mc3020/bin
   _- cp xtumlmc_build xtumlmc_build.copy
   _- tr -d "\r" < xtumlmc_build.copy > xtumlmc_build

_- Start BridgePoint
	  
_- Select Project > Build Automatically

_R The build is successful

# NOTE: For now we have no special configuration to set up the graphics to run 
# unit tests.  These just don't work on Linux at this time.

_- Setup development environment to run MC3020 under a launch configuration
  _- Open a file explorer and navigate to the BridgePoint plug-in 
     installation folder.  The default is:
     ~/MentorGraphics/BridgePoint/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.mc.c.binary_"ver"/mc3020
  _- Select all files and folders in the above folder
  _- Select Copy
  _- Open the Model Explorer view in Eclipse and navigate to:
       ~/git/xtuml/internal/src/com.mentor.nucleus.bp.mc.c.binary/mc3020
  _- Paste the previous selection into this folder
  _- Assure that the pasted files have execution permission

_- Set the unit test history to assure the test summary tool capture all results
   _- Open the JUnit view
   _- Select the drop-down arrow in the upper-right of the view
   _- Select the History... option
   _- Set "Maximum count of remembered test runs" to 30 
	
_R Your environment is now built and ready for unit testing.

</pre>
