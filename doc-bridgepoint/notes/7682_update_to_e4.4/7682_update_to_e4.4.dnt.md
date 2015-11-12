---

This work is licensed under the Creative Commons CC0 License

---

# Package BridgePoint into an Eclipse 4.5 base
### xtUML Project Design Note


1. Abstract
-----------
This work describes the incremental step "packaging" in the larger task of 
moving BridgePoint onto eclipse 4.

2. Document References
----------------------
[1] [BridgePoint DEI #7682](https://support.onefact.net/redmine/issues/7682)  
[2] [P2 Director Help](http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2Fp2_director.html)  
[3] [Java 8 Downloads](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html  
)  
[4] [DEI #7758 - Invalid install dir in mingwgnu.bat](https://support.onefact.net/redmine/issues/7758)  
[5] [Stackoverflow - grammar files .g compatibility frome Antlr 2.7 to 4?](http://stackoverflow.com/questions/31115588/grammar-files-g-compatibility-frome-antlr-2-7-to-4)  
[6] [HOWTO Update and Install Eclipse Base Components](https://github.com/xtuml/bridgepoint/blob/7682_update_to_e4.4/doc-bridgepoint/process/HOWTO-update-install-eclipse-base-components.md)  
[7] [DEI #36 - Move BridgePoint onto eclipse 4](https://support.onefact.net/redmine/issues/36)   
[8] [DEI #7954 - Upgrade to a newer ANTLR](https://support.onefact.net/redmine/issues/7954)  

3. Background
-------------
This work is a substep in a larger task to move to BridgePoint onto eclipse 4 [7]. 
Prior steps in this larger task reconfigured the BridgePoint source code to 
work in both eclipse 3.7 and eclipse 4.x.   

4. Requirements
---------------
4.1  The BridgePoint installation package shall include eclipse 4 from the
  xtuml/packaging repository.  
4.2  The xtUML Modeling perspective shall be usable when runnning the new environment
  installed by this work.  
4.3  BridgePoint shall launch without throwing plug-in dependency exceptions.  

5. Analysis
-----------
5.1  The first step of this issue is to update the build scripts to use the new eclipse 4
  in the xtuml/packaging repository.  The scripts can be modified in the branch and run on
  a local machine so that we don't have to have the build server up for a long time while 
  this work is ongoing.  
5.2  After the data is packaged properly, the next step is to run the application and work 
  through eclipse and BridgePoint dependency issues until the tool starts and runs cleanly.  

6. Design
---------
6.1  Branch xtuml/packaging to 7682_update_to_e4.4  

6.2  Update build scripts  
6.2.1  Update the eclipse version to 4.5 in ```utilities/build/configure_build_process.sh```and ```installer/build_installer_bp.sh```   
6.2.2  Add some more date-stamping in ```utilities/build/headless_build.sh``` to get better capture of build time   

6.3 Update ```eclipse.ini```  
  Clean out redundant settings and fix splash screen display.   

6.4  Update build order in ```bridgepoint/utilities/build/preferences/org.eclipse.core.resources.prefs```   
6.4.1  Move up ```bp.ui.text``` as other ```ui.*``` projects depend on it    
6.4.2  Add ```bp.welcome``` ahead of ```bp.core.test``` to satisfy dependency     
6.4.3  Remove build order in ```bridgepoint/utilities/build/plugin_customization.ini```.  This order
  is not used and is confusing to have laying around.   

6.5  Add needed packages to the eclipse base   
  The commands presented in the following subitems are what I used on my system.  Obviously, paths would
  need to change to run the same commands on a different system.  Also note that I specifically chose to 
  run the p2.director from an installed eclipse while targeting the install into a completely different
  eclipse that lives under the git repository.  This was done so that eclipse runtime configuration data (that is
  written when you run eclipse) was not written into our pristine install data.  Also note that these commands 
  must be run to update both the Windows and Linux versions.  That is the command is run twice with the destination
  set to ```.../install_bases/BridgePoint_e4.5/...``` and ```.../install_bases/BridgePoint_for_Linux_e4.5/...```.   
6.5.1  Add ANTLR  
```
$ /opt/xtuml/BridgePoint_e44s/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://antlreclipse.sourceforge.net/updates/ -installIU org.antlr.ui.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_for_Linux_e4.5/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   
6.5.2 Add CDT  
```
$ /opt/xtuml/BridgePoint_e44s/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/releases/mars/ -installIU org.eclipse.cdt.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_for_Linux_e4.5/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   
6.5.3 Add Papyrus-RT  
```
$ /opt/xtuml/BridgePoint_e44s/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/modeling/mdt/papyrus/updates/releases/mars,http://download.eclipse.org/releases/mars/,http://download.eclipse.org/papyrus-rt/updates/releases/mars/0.7.0/ -installIU org.eclipse.papyrusrt.umlrt.core.feature.feature.group,org.eclipse.papyrusrt.umlrt.core.feature.source.feature.group,org.eclipse.papyrusrt.feature.feature.group,org.eclipse.papyrusrt.feature.source.feature.group,org.eclipse.papyrusrt.umlrt.profile.feature.feature.group,org.eclipse.papyrusrt.umlrt.tooling.feature.feature.group,org.eclipse.papyrusrt.umlrt.profile.feature.source.feature.group,org.eclipse.papyrusrt.umlrt.tooling.feature.source.feature.group,org.eclipse.papyrus.sdk.feature.feature.group,org.eclipse.papyrus.sdk.feature.source.feature.group,org.eclipse.papyrusrt.rts-feature.feature.group,org.eclipse.papyrusrt.codegen-feature.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_for_Linux_e4.5/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   
6.5.4 Add SDK Examples (for BridgePoint JUnit testing)  
```
$ /opt/xtuml/BridgePoint_e44s/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/eclipse/updates/4.5/ -installIU org.eclipse.sdk.examples.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_for_Linux_e4.5/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   

6.6 The latest eclipse CDT requires Java8. Put the JRE 8 files into the install 
  bases for Windows and Linux.  Update Launcher and CLI scripts to point at the new JRE.   
6.6.1  The new JRE is downloaded from [3]  
6.6.2  Update the ```CLI.*``` and ```Launcher.*``` scripts to call java from the new JRE path in the installed location.   

7. Design Comments
------------------
7.1  Fixed [4], a typo in mingwgnu.bat and Launcher.bat  
7.2  Updated the HOWTO Install Components document [6] into xtuml/bridgepoint repository.  We had
   an old and outdated version in the xtuml/internal repository.  
7.3  The move to JRE 8 could be done as a stand-alone step to assure that the update 
  does not have any fallout.  We are choosing to perform the JRE 8 update and the Eclipse 4 
  update together.  If we have problems in the next step, we can perform the JRE 8 update 
  as a standalone step in the old Eclipse 3.7 base and re-run tests there.   
7.4  During the development process we decided to move to eclipse 4.5 (the Mars.1 release).  This allows 
  us to integrate much more cleanly with Papyrus-RT.  It should be noted that even though we are 
  moved to eclipse 4.5, the branch name is unchanged and still says "e4.4".
7.5  ANTLR.  The original Luna-based pass at this upgrade to eclipse 4 intended to use ANTLR 2.7.2 plugins as 
  we have done for a very long time using the eclipse 2.x compatibility plugins.  However, the ANTLR 2.7.2 
  plug-ins simply would not behave properly in Mars.1 due to new Java library integration rules.  I kept getting
  "Access Restriction" dependency errors from eclipse on the projects that attempted to integrate with ANTLR.  In 
  the end I decided to make a slight upgrade to ANTLR 2.7.6 plug-ins because they do not require the Eclipse 2.x 
  compatibility support and they are properly configured to deal with the dependencies and avoid "Access Restriction"
  problems.  Finally, during a call on 10/21/2015 with Mike Millinkovich of the Eclipse Foundation, he told us that
  old ANTLR dependency would likely be an issue for making BridgePoint a proper project under the Eclipse Foundation.   I raised an issue [8] to investigate upgrading to a newer ANTLR as separate work.
7.5.1  Simply as a matter of historical record, here is the command to add compatibility support for eclipse 2.x 
  style plug-ins:  
```
$ /opt/xtuml/BridgePoint_e4.4/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/eclipse/updates/4.4/ -installIU org.eclipse.osgi.compatibility.plugins.feature.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_e4.4/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   

    
8. Unit Test
------------
8.1  Successfully run a branch build on the build server of 7682_update_to_e4.4  
8.2  Using the BridgePoint server built package (Windows & Linux)  
8.2.1  Successfully run the BridgePoint Launcher with no dependency errors raised  
8.2.2  Successfully open the xtUML Modeling perspective  
8.2.3  Successfully create a new xtUML project  

End
---

