HOWTO Install/Update Components in BridgePoint's Eclipse Base
--------------------------------------------------------------

### References
[1] [P2 Director](http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2Fp2_director.html)    
[2] [Packaging Repository](http://github.com/xtuml/packaging)    

###Introduction
We use the p2 director application in the mode where it does 
provisioning without running the target application.  We do this 
because we do not want to touch the configuration cache data in 
the eclipse base the installer will use. See [1] for additional 
information.  Note that this process described in the remaining 
steps relies on a "builder" eclipse, that is, an existing 
eclipse installation that contains the director tool we will 
use to update the target eclipse.   

The eclipse bases are stored in [2].  In order to add new software 
to the bases you must first clone the ```xtuml/packaging``` 
repository to your machine where you will run the eclipse p2 
director application.   

Before proceeding further, make sure there is a tag on the current 
BridgePoint/eclipse base in the ```xtuml/packaging``` repository.   

###Instructions
- To use the p2 director, you must find out the "installable unit" 
    the feature you want to install is part of and what repository
    you want to download the feature/bundle from.  I have relied
    on google search "eclipse what is the installable unit for xxx"
    to help locate the information.  If you are just updating an 
    existing package, you can also launch a working version of eclipse, 
    select ```Help > About ...```, then click the "Installation Details" 
    button. This lists the currently installed software, with the 
    installation units shown in the Id column.  
- Use the p2 director to add the desired installable unit.  The following 
    are examples for the software we typically add to the Eclipse Modeling Tools 
    base software we use.  Note, again, how we are using an already installed eclipse
    to run p2 director with a target destination of our pristine install base.
-  Add ANTLR  
```
$ /opt/xtuml/BridgePoint_e44s/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://antlreclipse.sourceforge.net/updates/ -installIU org.antlr.ui.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_for_Linux_e4.5/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   
- Add CDT  
```
$ /opt/xtuml/BridgePoint_e44s/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/releases/mars/ -installIU org.eclipse.cdt.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_for_Linux_e4.5/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   
- Add Papyrus-RT  
```
$ /opt/xtuml/BridgePoint_e44s/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/modeling/mdt/papyrus/updates/releases/mars,http://download.eclipse.org/releases/mars/,http://download.eclipse.org/papyrus-rt/updates/releases/mars/0.7.0/ -installIU org.eclipse.papyrusrt.umlrt.core.feature.feature.group,org.eclipse.papyrusrt.umlrt.core.feature.source.feature.group,org.eclipse.papyrusrt.feature.feature.group,org.eclipse.papyrusrt.feature.source.feature.group,org.eclipse.papyrusrt.umlrt.profile.feature.feature.group,org.eclipse.papyrusrt.umlrt.tooling.feature.feature.group,org.eclipse.papyrusrt.umlrt.profile.feature.source.feature.group,org.eclipse.papyrusrt.umlrt.tooling.feature.source.feature.group,org.eclipse.papyrus.sdk.feature.feature.group,org.eclipse.papyrus.sdk.feature.source.feature.group,org.eclipse.papyrusrt.rts-feature.feature.group,org.eclipse.papyrusrt.codegen-feature.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_for_Linux_e4.5/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   
- Add SDK Examples (for BridgePoint JUnit testing)  
```
$ /opt/xtuml/BridgePoint_e44s/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/eclipse/updates/4.5/ -installIU org.eclipse.sdk.examples.feature.group -destination /home/kbrown/build/git/xtuml/packaging/install_bases/BridgePoint_for_Linux_e4.5/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```   
- Don't forget to update both the Windows and Linux eclipse bases by running
    the commands with both destination directories.
- Use ```git status``` to check the changes that were made to see if they make 
    sense.  If they do, then commit and push the changes.   
