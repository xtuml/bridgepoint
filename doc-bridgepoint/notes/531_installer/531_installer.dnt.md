---

This work is licensed under the Creative Commons CC0 License

---

# Create new installer for BridgePoint Pro
### xtUML Project Design Note

1. Abstract
-----------
This note shall provide a description of the design of a BridgePoint installer implemented using the IzPack framework.

2. Document References
----------------------
[1] https://support.onefact.net/redmine/issues/531 - Issues 531.  
[2] http://izpack.org/ - the installer framework used to implement the BridgePoint installer.  
[3] xtuml.org /build/staging - The location on the AWS server where the installer packaging will take place.  
[4] 37_build_server - The branch of xtuml/internal where the work to create a linux build server is taking place.  
[5] [Analysis Note] (https://github.com/leviathan747/bridgepoint/blob/master/doc-bridgepoint/notes/531_installer/531_installer.ant.md)

3. Background
-------------
See [5]

4. Requirements
---------------
See [5]

5. Analysis
-----------
See [5]

6. Design
---------
There are three distinct parts to the installation: install eclipse deliverables, 
install BridgePoint deliverables, and configure the installation with post-install
tasks. Each of these parts will be described separately in this section along with 
a description of the design and layout of the installer itself.  

6.1 Eclipse Deliverables  

In this step of the installation, the Eclipse deliverables are simply copied from
the staging area to the target directory chosen by the user. IzPack employs an XML
based install description (similar to Apache Ant) with the files to be copied organized
into packs. Each pack is to be a logical subset of the full installation. Eclipse is
installed as one pack using the IzPack XML notation.  

6.2 BridgePoint Deliverables  

In this step of the installation, the BridgePoint deliverables are simply copied from
the staging area to the target directory chosen by the user in the same fashion as the
Eclipse deliverables. BridgePoint deliverables are installed as a pack separate from
Eclipse. This design was chosen because there is a logical separation from the Eclipse
bases and the BridgePoint deliverables. It may additionally facilitate installations
with a previous Eclipse installation (in the future).  

6.3 Post-install Tasks  

In addition to the two packs, there are several post-install tasks that must be completed.
IzPack supports the need for these tasks by allowing post-install scripts to be specified
in the installation description.
These tasks fall into three categories:  

1. Copying plugin files from BrigePoint deliverables into the Eclipse installation.  

2. Finding and replacing the default target path with the user defined target path in certain
files.  

3. Miscellaneous tasks such as calling the `dos2unix` utility, displaying the release notes
in a browser, etc.  

Task 1 shall be integrated into the BridgePoint pack described in the previous subsection. The
plugin files will then be copied directly into the Eclipse installation avoiding being moved twice.
Task 2 shall remain in a post-install script run by IzPack after the installation of the Eclipse
and BridgePoint packs. For more information on this decision see section 7 Design Comments.
Task 3 shall remain in the post-install script. These miscellaneous tasks are best called from
a script which is executed by IzPack. This will enhance maintainability while not sacrificing any
performance.  

6.4 Installer Design and Layout  

IzPack offers many installer panels to create a suitable installer. The panels chosen for the 
BridgePoint installer are in order as follows:  

6.4.1 HTMLHelloPanel  

This panel provides the welcome message and any desired information about BridgePoint. The body of
this panel is created from custom HTML supporting insertion of BridgePoint and OneFact branding.  

6.4.2 TargetPanel  

This panel provides the user with the opportunity to chose the target directory of the installation.
The default is "$USER_HOME/xtuml/BridgePoint" (Linux) and "C:/xtuml/BridgePoint" (Windows).  

6.4.3 ShortcutPanel (Windows only)  

This panel gives the user the opportunity to specify creation of Desktop and Start Menu shortcuts.  

6.4.4 InstallPanel  

This panel is the panel shown when the install itself is occuring. It shows progress bars for each pack
and the installation as a whole.  

7. Design Comments
------------------
7.1 Post-install text find and replace  

There are three options for finding and replacing the default target in the installation files:  

1. Employ "parseable" files  
IzPack supports including "parsable" files in the installation. Essentially any parsable file will
be read by the installer and any references to environment variables or variables defined in the 
installation description will be replaced by the values of those variables. We will not take this
approach because it would require changing the BridgePoint source files to use a notation specific
to IzPack.  

2. Call `sed` or `update_text` utilities directly from IzPack  
It is conceivable to call these find and replace utilities directly from IzPack to replace the target
directory strings. We will not take this approach because it would unnecessarily add complexity to
the install description and would bring about tighter coupling between IzPack and the installation,
making it more difficult to switch installer technologies.  

3. Keep the find and replace tasks in a post-install script  
The last option is to keep these tasks as is in a post-install script. This is the optimal choice because not 
only does it preserve the source files' original state and maintain loose association between installer
and installer technology, but it doesn't sacrifice performance.

8. Unit Test
------------
See [5]

End
---
