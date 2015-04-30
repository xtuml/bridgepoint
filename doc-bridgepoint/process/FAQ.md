# BridgePoint FAQ

## Table of Contents
  * [Installation Issues](#installation)
    * [Errors During Unzip](#unziperrors)
  * [BridgePoint Developer Issues](#bpdevelopers)
    * [ANTRL Build Error](#antrlbuilderror)
    * [Linux Distribution-Specific Instructions](#linux)
    * [Windows Unit Test Configuration](#windowstesting)
    
Installation <a id="installation"></a>
------------
  * Errors During Unzip<a id="unziperrors"></a>
  When unzipping the BridgePoint distribution if you see a message that indicates a duplicate file is 
  being installed or there missing files in the distribution, the problem is likely with the 
  unzip utility you are using.  We suggest you use [7-Zip](http://www.7-zip.org/download.html).
  
BridgePoint Developer Issues <a id="bpdevelopers"></a>
----------------------------

* ANTRL Build Error<a id="antrlbuilderror"></a>  
  After following the [Developer's Getting Started Guide](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md) 
  if you see errors in plugins caused by missing dependant files, and those files refer to ANTRL, follow these 
  instructions to resolve the issue:
  '''
  - open a terminal (or cygwin shell if windows)
  - Open the Navigator view in eclipse
  - delete org.xtuml.bp.als/sql/oal_grammar.sql
  - "touch" <installation folder>/BridgePoint/eclipse/plugins/org.xtuml.bp.als.oal/bnf/oal.bnf
    - (just change a comment)
  - Right click on bp.als/generate.xml and choose Run As...
  - Uncheck everything
  - Check grammar
  - Click Run  
  '''  
  This problem is that ANTRL is not running when it should in some cases. This is a sporatic dependancy 
  issue that has not yet been completly resolved.  It is raised in the issue tracking system as 
  issue [7631](https://support.onefact.net/redmine/issues/7631).
  
* Linux Distribution-Specific Instructions <a id="linux"></a>
  * Package Requiments for various Linux Distributions  
    The BridgePoint [Developer's Getting Started Instructions](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md) 
    has instructions specifcally for Linux Ubunutu. Here are package requirements for some other Linux Distributions.  
      * Fedora 19:  
        ```$ sudo yum install wine gcc-c++  dos2unix compat-libstdc++-33 gtk2.i686 ant git```
      
      * Debian Wheezy:  
        ```$ sudo apt-get install ia32-libs ia32-libs-gtk libgtk2.0-0 lib32ncurses5 ant git```
  
* Windows Test Configuration  <a id="windowstesting"></a>
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
  
