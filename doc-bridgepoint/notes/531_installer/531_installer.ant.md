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
This section is only required if there is no preceding analysis note. If present
it sets out a brief analysis of the problem to be resolved by this design note.

* Item 1
* Item 2
* Item 3

6. Work Required
----------------
In this section, break out the consequential work (as a numbered list) needed
to meet the requirements specified in the Requirements section.

7. Acceptance Test
------------------
In this section, list the tests that need to be performed in order to
verify that all the requirements are satisfied.

End
---

