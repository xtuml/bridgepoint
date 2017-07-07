What's New in BridgePoint 6.4.0
========================

Release Highlights
-------
* MASL Support Enhancements
* Document Generation Fixes
* Task-specific BridgePoint Products
* MacOS Support
* Infrastructure Improvements

-------------------------------------------------------------------------------

MASL Support Enhancements
------
This release includes many improvements to MASL support within the tool:

  * Many updates to the MASL parser.  Drastic reduction of false positive errors on valid syntax.   
  * Improvements to Refactor/Rename
  * Marking and pragma support improvements
  * ... and more.  See the full list of resolved issues in the Release Notes

This release also includes updated documentation in the MASL Modeling and Conversion Guide
regarding use and creation of MASL-idiom features.
  

Document Generation Fixes
------
BridgePoint's "Create documentation" feature is updated to work cleanly on Linux 
variants and MacOS. The feature now relies on system versions of the external ```xsltproc``` tool. 

  * __MS Windows__: ```xsltproc.exe``` is included in the BridgePoint distribution, no 
  additional steps are required.
  * __MacOS__: ```xsltproc``` is included in the operating system software, no additional 
  steps are required.
  * __Linux__: ```xsltproc``` is included in some distributions and not in others.  In a 
  terminal window, type ```$ which xsltproc```.  If the tool is found, no additional steps are 
  required. If the tool is not found, use your package manager (```apt-get``` or ```yum```) to install 
  it.  For example:
  ```
  $ sudo apt-get install xsltproc
  ```

Task-specific BridgePoint Products
------
The BridgePoint tool is modeled in xtUML and therefore BridgePoint is used to
create BridgePoint.  Most users simply wish to create xtUML applications and 
they do not need or want additional eclipse features to support BridgePoint 
tool development.  

To simplify the user experience for application modelers, BridgePoint is now 
available in two different forms: xtUML Modeler and BridgePoint Developer.  

The xtUML Modeler version of BridgePoint includes the smallest set of features 
needed for xtUML application editing, translating, and debugging.  

The BridgePoint Developer version is a superset of the xtUML Modeler version. It 
adds additional eclipse features that support Java and Xtext code development 
and testing.   


MacOS Support
------
BridgePoint is now supported on MacOS.  

Please note that the download for MacOS does not come with a JRE included. The tool
relies on a JRE (version 8 or later) to already exist on the system.  

Java installation [instructions are here](https://java.com/en/download/help/mac_install.xml).  


Infrastructure Improvements
------
This release also includes a number of enhancements to the internals of BridgePoint and 
the development infrastructure:

* Use relate and unrelate of pyrsl in MC-3020
* Use maven for consistent build approach locally and on build server
* JUnit test cleanup and automated testing 
* Run multiple CLI command in a single instance of eclipse
* Sample project creation hang fix
* ...and more.  See the release notes.   