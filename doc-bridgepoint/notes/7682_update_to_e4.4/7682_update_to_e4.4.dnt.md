---

This work is licensed under the Creative Commons CC0 License

---

# Package BridgePoint into an Eclipse 4.4 base
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

3. Background
-------------
This work is a substep in a larger task to move to BridgePoint onto eclipse 4 [7]. 
Prior steps in this larger task reconfigured the BridgePoint source code to 
work in both eclipse 3.7 and eclipse 4.x.  Prior work also added an Eclipse 
Modeling Tools version of eclipse 4.4 (Luna) to the xtuml/packaging repository. 

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
6.2.1  Update the eclipse version to 4.4 in ```utilities/build/configure_build_process.sh```and ```installer/build_installer_bp.sh```   
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
  written when you run eclipse) was not written into our pristine install data.   
6.5.1 Add CDT  
```
$ /opt/xtuml/BridgePoint_e4.4/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/releases/luna/ -installIU org.eclipse.cdt.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_e4.4/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   
6.5.2  Add Compatibility Support for eclipse 2.x style plug-ins  
```
$ /opt/xtuml/BridgePoint_e4.4/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/eclipse/updates/4.4/ -installIU org.eclipse.osgi.compatibility.plugins.feature.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_e4.4/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   
6.5.3  Add antlr 2.7.2 plugins to ```plugins/``` folder in the eclipse 4 base just as we 
  do in the eclipse 3.7 base  
6.5.3.1  I considered moving to a newer version of antlr plugins.   However, after 
  doing some research I discovered that antlr 4 grammars are not compatible with 
  antlr 2 grammars. See [5].  
6.5.3.2  The antlr 3 plug-in set seems to provide no tangible advantage for us
  over the plugins we use already.  The version 3 plugins also have
  some extra dependencies to other eclipse packages that we would have to add to our eclipse base.  
6.5.3.3  In the end, I decided it made the most sense to install the very small and 
  simple eclipse 2.x compatibility feature rather than attempt to move to antlr4 or 
  the antlr3 plugins at this time.  

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
    
8. Unit Test
------------
8.1  Successfully run a branch build on the build server of 7682_update_to_e4.4  
8.2  Using the BridgePoint server built package (Windows & Linux)  
8.2.1  Successfully run the BridgePoint Launcher with no dependency errors raised  
8.2.2  Successfully open the xtUML Modeling perspective  
8.2.3  Successfully create a new xtUML project  

End
---

