---

This work is licensed under the Creative Commons CC0 License

---

# Install and run BridgePoint with the 4.4 base
### xtUML Project Design Note

1. Abstract
-----------
This note describes the steps to install and run BridgePoint under the eclipse
4.4 environment.   

2. Document References
----------------------
[1] [BridgePoint DEI #7702](https://support.onefact.net/redmine/issues/7702)  
[2] https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/notes/36_Update_eclipse_base/36_Update_eclipse_base.ant.md   
[3] [BridgePoint DEI #7684](https://support.onefact.net/redmine/issues/7684)   
[4] https://github.com/xtuml/packaging/tree/master/install_bases/BridgePoint_e3.7/EclipseDeliverables   
[5] http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/luna/SR2/eclipse-modeling-luna-SR2-win32.zip   
[6] http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/luna/SR2/eclipse-modeling-luna-SR2-linux-gtk.tar.gz   

3. Background
-------------
Reference [2] documents the work required to complete movement to the eclipse   
4.4 base.  This document will cover installing and running tests on this new   
base.   

4. Requirements
---------------
4.1 BridgePoint installed on eclipse 4.4   
4.2 BridgePoint unit tests run on BridgePoint with eclipse 4.4   
4.3 Installer base is updated with eclipse 4.4   

5. Analysis
-----------
See [2].   

6. Design
---------
6.1 Install BridgePoint into 4.4 eclipse base   

The work here is to download the eclipse 4.4 zip file.  The chosen eclipse release   
is code named Luna and the two zip files are at [5] and [6].  Next we shall   
install the Eclipse 2.0 Style Plugin support.  At this point we can unzip an   
update-site version of BridgePoint under the dropins directory.  Doing so will   
require us to manually copy all org.antlr.* plug-ins from another fully installed   
version of BridgePoint.  The version of BridgePoint to copy the plug-ins from   
shall be 5.0.  These plug-ins shall also live in the dropins folder.  

6.2 Run automated tests   

Part of this work requires running of the automated tests.  While the issue does    
not require all tests to pass, it does require that the results are reported.   
Any failing tests shall be fixed with [3].   

6.2.1 Install unit test plug-ins   

The unit test plug-ins must be installed.  To do so we must build them in a 5.0   
build environment.  These will be built on the eclipse 3.7 version.  Once they   
are built they need to be exported using the PDE plug-in export wizard.  These   
exported plug-ins shall then be placed into the eclipse dropins folder.   

6.2.2 Running the tests   

To run the tests we must check out the doc-bridgepoint project from the git-hub   
bridgepoint repository.  Once done, each test under the JUnit-Plugin test   
category shall be run.  The results shall be captured under the document   
repository.   

6.2.3 Run smoke test

A smoke test shall be run and the results reported.   

6.3 Update the installer base   

The installer base shall be updated to include the eclipse 4.4 base.  At this   
point this is simply including a new eclipse-4.4 folder under the packaging   
repository [4].   

We cannot provide a full installer at this point as the step to install the   
Eclipse 2.0 Style Plugin support must be done through the UI itself.   

7. Design Comments
------------------

8. Unit Test
------------
8.1 BridgePoint shall be installed in eclipse 4.4   
8.2 Unit tests shall be run against the installed version of BridgePoint
8.3 Run smoke test on installed BridgePoint plug-ins   

End
---

