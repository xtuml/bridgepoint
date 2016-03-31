---

This work is licensed under the Creative Commons CC0 License

---

# Spring Cleanup, Phase I
### xtUML Project Implementation Note


1. Abstract
-----------
In the spring of 2016, the xtUML team decided to do some spring cleaning to the BridgePoint code base.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8395](https://support.onefact.net/issues/8395) Spring Cleaning parent issue    
<a id="2.2"></a>2.2 [BridgePoint DEI #8398](https://support.onefact.net/issues/8398) Remove Odometer plugin    
<a id="2.3"></a>2.3 [BridgePoint DEI #8399](https://support.onefact.net/issues/8399) Remove "installer" project  
<a id="2.4"></a>2.4 [BridgePoint DEI #8402](https://support.onefact.net/issues/8402) (Re)move OAL xtext prototype plugins   
<a id="2.5"></a>2.5 [BridgePoint DEI #8396](https://support.onefact.net/issues/8396) Remove Unused MC plugins  

3. Background
-------------
BridgePoint has accumulated some cruft over the years in its code base.  The 2016 spring cleaning
is meant to rectify this.  A number of areas have been identified and created as subtasks of [[2.1]](#2.1).
This first phase of work handles the simplest cases.  

4. Requirements
---------------
4.1 Create "graveyard" repository in the github xtuml organization    
4.2 Move obsolete plug-ins to graveyard    

5. Work Required
----------------
5.1 Changes for [[2.2]](#2.2)  
5.1.1  'org.xtuml.bp.qa.odometer' is moved to the graveyard.  

5.2 Changes for [[2.3]](#2.3)  
5.2.1  The 'installer' project contains the script that organizes and creates the BridgePoint distribution
  zipfile.  It also houses the CLI and Launcher scripts.  
5.2.2  The author decided not to completely remove the installer project. Instead, we just removed all the 
  old izpack stuff and pare it down to what is actually still used.  
  
5.3 Changes for [[2.4]](#2.4)  
5.3.1  The 'org.xtuml.bp.xtext.oal.*' plug-ins are moved to the graveyard.  

5.4 Changes for [[2.5]](#2.5)  
5.4.1  VHDL and MCPaaS plugins are removed. These plug-ins are known to have no dependencies, so they are 
  completely safe to move to the graveyard.  
5.4.2  This issue is only partially completed as part of this promotion and will remain open for the remaining
  work.  

5.5 The files moved to the graveyard are committed directly to master branch on that repository.  

6. Implementation Comments
--------------------------
None.

7. Unit Test
------------
7.1  The changes for this branch were chosen specifically because they have no testing or production fallout.  

8. Code Changes
---------------
Repository: bridgepoint  
Branch name: 8395_cleanup_1

<pre>

doc-bridgepoint/notes/8395_cleanup_1.int.md

-installer/1F.png
installer/build_installer_bp.sh
-installer/build.sh
-installer/create_shortcut.vbs
-installer/done.html
-installer/install_linux.xml
-installer/install_windows.xml
-installer/post_install_script.bat
-installer/post_install_script.sh
installer/README.md
-installer/shortcutSpec.xml
-installer/side-image.png
-installer/TARGET_LINUX.txt
-installer/TARGET_WINDOWS.txt
-installer/welcome.html
-installer/xtuml_puzzle.png

org.xtuml.bp.bld.pkg-feature/feature.xml

org.xtuml.bp.docgen/generate.xml

-org.xtuml.bp.mc.mcpaas

-org.xtuml.bp.mc.vhdl.source

-org.xtuml.bp.qa.odometer

-org.xtuml.bp.xtext.oal

-org.xtuml.bp.xtext.oal.tests

-org.xtuml.bp.xtext.oal.ui

utilities/build/Build-Flow.txt
utilities/build/configure_build_process.sh
utilities/build/configure_external_dependencies.sh


</pre>

End
---

