HOWTO Install/Update Components in BridgePoint's Eclipse Base
--------------------------------------------------------------

### References
[1] P2 Director. http://help.eclipse.org/indigo/index.jsp?topic=/org.eclipse.platform.doc.isv/guide/p2_director.html  
[2] BridgePoint SVN. http://wv-svn-01.wv.mentorg.com/svn/sle/xtuml  

###Instructions
- Get OSS approval for the package(s) to update
- We use the p2 director application in the mode where it does
    provisioning without running the target application.  We do
	this because we do not want to touch the configuration cache
	data in the eclipse base the installer will use. See [1] for 
	additional information.  Note that this process described in
	the remaining steps relies on a "builder" eclipse, that is, 
	an existing eclipse installation that contains the director
	tool we will use to update the target eclipse.
- The eclipse bases are stored in our SVN repository [2].  The 
    easiest way to update the eclipse base is to remote desktop
    into svr-azt-eng-03, then start BridgePoint on the workspace
	c:\workspaces\build_artifacts.  Here you will see projects 
	for the Windows and Linux bases.  Note that the BridgePoint/eclipse
	bases the installer uses (and SVN points at) are on disk 
	at C:/workspaces/build_artifacts/BridgePoint_*.
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
C:/MentorGraphics/BridgePoint/eclipse/eclipsec.exe  
 -application org.eclipse.equinox.p2.director  
 -repository http://download.eclipse.org/releases/galileo/   
 -installIU org.eclipse.cdt.feature.group   
 -destination C:/workspaces/build_artifacts/BridgePoint_e3.7/EclipseDeliverables/eclipse  
 -profile epp.package.modeling  
```
- Don't forget to update both the Windows and Linux eclipse bases
- Now go back to the running BridgePoint/eclipse with the view of the 
    SVN projects, refresh, the projects, look over the changes and see if 
	they make sense.  If they do, then commit the changes.
