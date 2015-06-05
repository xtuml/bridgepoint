---

This work is licensed under the Creative Commons CC0 License

---

# Install and run BridgePoint with the 4.4 base
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the implementation for installing and testing BridgePoint in   
the eclipse 4.4 base.   

2. Document References
----------------------
[1] [BridgePoint DEI #7702](https://support.onefact.net/redmine/issues/7702)  
[2] https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/notes/7702_Install_BridgePoint_into_eclipse_4.4/7702_Install_BridgePoint_into_eclipse_4.4.dnt.md   

3. Background
-------------
See [2].   

4. Requirements
---------------
See [2].   

5. Work Required
----------------
5.1 Install BridgePoint into 4.4 eclipse base   

A download of the versions of eclipse specified in [2] was done.  This was
unzipped into a BridgePoint_Luna folder under the default xtuml directory.  The   
eclipse version was started and the Eclipse 2.0 style plug-in installed.  The   
BridgePoint plugins were installed under the dropins folder with the following   
structure:   

BridgePoint/eclipse/plugins   
BridgePoint/eclipse/features   

The org.antlr.* plug-ins were installed directly under the dropins folder.   

5.2 Run automated tests   

5.2.1 Install unit test plug-ins   

The unit test plug-ins were built in an eclipse 3.7 environment.  The PDE export   
was then used to extract the plug-ins in an installable format.  These plug-ins   
were copied into the installations from section 5.1.   

5.2.2 Running the tests   

Unfortunately, the unit tests cannot be run.  Only one or two of the test   
plug-ins were loaded.  They were insignificant (like io.sql.test).  A manual   
attempt was made using the osgi console to install the plug-ins.  This did not   
provide any useful information.  The error log did not provide any useful   
information either.

5.2.3 Run smoke test

A smoke test was run, the results are listed below:

- Splash screen does not work by default, manual modification of the   
  configuration file does not allow the splash screen.  No splash screen is   
  shown at this point.   
- The welcome page is not available   
- The BP documentation has strange characters   
- Getting started help project creation links do nothing   
- The new xtUML project wizard does not show an option for model compiler choice   
- CDT builders are missing (CDT was manually installed)   
- The XMI and export builders are missing   
- There is no xtUML icon overlay in other navigator views (Package Explorer, Navigator)   
- Verifier fails to run with "Nothing to verify"   

5.3 Update the installer base   

The downloads described in section 5.1 were unzipped into the packaging   
repository.  Two new projects were added:   

- BridgePoint_e4.4   
- BridgePoint_for_Linux_e4.4   

The contents of the 3.7 matching folders were copied and the unzipped eclipse   
installs overwritten.  Additionally the eclipse.ini files were updated for   
specific BridgePoint needs.   

6. Implementation Comments
--------------------------

7. Unit Test
------------
See [2].   

8. Code Changes
---------------
Branch name: master

<pre>

BridgePoint_e4.4/*   
BridgePoint_for_Linux_e4.4/*   

doc-bridgepoint/36_Update_eclipse_base/*   
doc-bridgepoint/7702_Install_BridgePoint_into_eclipse_4.4/*   

</pre>

End
---

