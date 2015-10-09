HOWTO Install/Update Components in BridgePoint's Eclipse Base
--------------------------------------------------------------

### References
[1] [P2 Director](http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2Fp2_director.html)    
[2] [Packaging Repository](http://github.com/xtuml/packaging)    

###Instructions
- We use the p2 director application in the mode where it does
    provisioning without running the target application.  We do
    this because we do not want to touch the configuration cache
    data in the eclipse base the installer will use. See [1] for 
    additional information.  Note that this process described in
    the remaining steps relies on a "builder" eclipse, that is, 
    an existing eclipse installation that contains the director
    tool we will use to update the target eclipse.  
- The eclipse bases are stored in [2].  In order to add new software
    to the bases you must first clone the xtuml/packaging repository.  
- Before proceeding make sure there is a tag containing the current 
    BridgePoint/eclipse base  
- To use the p2 director, you must find out the "installable unit" 
    the feature you want to install is part of and what repository
    you want to download the feature/bundle from.  I have relied
    on google search "eclipse what is the installable unit for xxx"
    to help locate the information.  If you are just updating an 
    existing package, you can also launch a working version of eclipse, 
    select Help > About ..., then click the "Installation Details" 
    button. This lists the currently installed software, with the 
    installation units shown in the Id column.  
- Use the p2 director to add the desired installable unit.
    For example, for CDT we use a command similar to this one:
```
$ /opt/xtuml/BridgePoint_e4.4/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/releases/luna/ -installIU org.eclipse.cdt.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_e4.4/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   
to add the Eclipse 2.x compatibility feature, the command is similar to:   
```
 /opt/xtuml/BridgePoint_e4.4/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/eclipse/updates/4.4/ -installIU org.eclipse.osgi.compatibility.plugins.feature.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_e4.4/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```
- Don't forget to update both the Windows and Linux eclipse bases   
- Use ```git status``` to check the changes that were made to see if they make 
    sense.  If they do, then commit and push the changes.   