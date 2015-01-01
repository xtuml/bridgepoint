---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Create new installer for BridgePoint Pro
### xtUML Project Analysis Note

1. Abstract
-----------
This note shall provide an analysis of the options for the installer technology that will be used by BridgePoint.

2. Document References
----------------------
[1] https://support.onefact.net/redmine/issues/531 - Issues 531.  
[2] http://izpack.org/ - an open source java-based installer.  
[3] xtuml.org /build/staging - The location on the AWS server where the installer packaging will take place.  
[4] 37_build_server - The branch of xtuml/internal where the work to create a linux build server is taking place.  


3. Background
-------------
Prior to BP v5.0 BridgePoint used the Mentor Graphics proprietary instaler, MIP and 
its associated licensing libraries. With the move to open source, this installer cannot
be used. This issue is raised to investigate options for a new installation mechanism for
the tool.

4. Requirements
---------------
4.1. Installer shall run on Windows, and Linux.  
4.1.1. Ubuntu and Redhat shall be tested.  
4.1.2 Mac is desirable, but not required.  
4.2. Installer shall install everything required to run BridgePoint. This includes items that were placed in the eclipse droppins folder by the prior installer.  
4.3. Installer must consider, and account for, the tasks performed by the previous installer's post-install and pre-uninstall scripts.  
4.4. Installer creation shall be scriptable. It shall run on the linux build server.  
4.5. Installer shall have a command line (non-interactive) mode.  
4.6. Installer shall support (minimal) branding.  
4.7. Installer shall show the readme at the end of installation.  
4.8. Installer shall create desktop and start menu shortcuts on Windows.  
4.9. In choosing the installer technology, open source, and freely available technology shall be preferred.  
4.10. An eclipse update site option shall be investigated.  
4.11. The installation procedure used by Eclipse itself shall be investigated as an option.  

5. Analysis
-----------
5.1 IzPack  

This analysis shall investigate IzPack as a free and open source installer solution.  

5.1.1 Compatibility  

IzPack packages an installer as an executalbe .jar file and is therefore compatible with any system that has the Java runtime environment installed. It has been tested and proven to work on Windows (8.1 tested), Linux (Ubuntu 14.04 tested), and Mac (OS X Yosemite tested).  

5.1.2 Post-install and pre-uninstall scripts  

IzPack supports executing a file on post-install and pre-uninstall. The current post-install and pre-uninstall jobs can be handled by this feature.  

5.1.3 Build scripting  

The IzPack compiler is itself a Java program. It has an acceptable commandline interface and can be scripted to build installers for each platform to be included in the nightly build.  

5.1.4 Installer user experience and branding  

IzPack creates installers with both a GUI and a commandline interface.  

5.1.4.1 GUI  

A compiled IzPack installer provides a "wizard"-like GUI installer experience. Preferences for look and feel are moderately customizable, however IzPack branding is prevalent. Support for adding custom images, logos, and other brands is provided.  

5.1.4.2 CLI  

A compiled IzPack installer also provides a commandline interface option. IzPack provides support for naming authors and a homepage in the commandline version of the installer.  

5.1.5 Readme  

A readme can be displayed within the installer window  

5.2 Eclipse update site

6. Work Required
----------------
6.1 Create Windows and Linux IzPack install descriptions (XML) for compilation to their respective installers.  
6.2 Create installer messages and branding to be used in the GUI version of the installers.  
6.3 Create scripts to build the installers as part of the nightly build.  
6.4 Test installers on each target platform.  

7. Acceptance Test
------------------
7.1 The installer shall run on Windows and Linux fully installing Bridgpoint and satisfying all requirements in section 4.

End
---

