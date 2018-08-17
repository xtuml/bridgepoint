---

This work is licensed under the Creative Commons CC0 License

---

# Move to eclipse oxygen
### xtUML Project Implementation Note

### 1. Abstract

BridgePoint currently runs on Eclipse Mars. Consider a move to Neon or Oxygen.

### 2. Document References

In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.  
<a id="2.1"></a>2.1 [BridgePoint DEI #10010](https://support.onefact.net/issues/10010)  
<a id="2.2"></a>2.2 [BridgePoint DEI #10345](https://support.onefact.net/issues/10345) Update unit 
tests to account for the move to Eclipse Oxygen.  

### 3. Background

The current release of BridgePoint, 6.14, is built with and and ships with Eclipse Mars. Eclipse Mars was release in 2015. There is
currently no pressing technical reason for this move.  However, a move to Eclipse Oxygen keeps the xtUML editor,
Verifier and model compilers on a platform that is mature yet safe. In reviewing options 
Eclipse Neon (released in 2016) is now a bit dated, and Eclispe Photon was just released this year. Eclipse Oxygen 
(released in 2017) is the selected version for this update.
  

### 4. Requirements

4.1 The version of Eclipse shipped with BridgePoint shall be updated to Eclipse Oxygen  
4.2 The BridgePoint build server shall be updated to build BridgePoint using Eclipse Oxygen  

### 5. Work Required


5.1 Install the Eclipse Oxygen Modeling release   
* http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/oxygen/3a/eclipse-modeling-oxygen-3a-macosx-cocoa-x86_64.dmg
 (SHA 512)  
 
5.2 Install m2e using "Help > Install New Software...", work with "Oxygen", search for m2e  
* When installing m2e only select "maven integration for eclipse" item for m2e  

5.3 Install Xtext Complete SDK using "Help > Install New Software...", work with "Oxygen", search xtext sdk  
* Used File > Import > Install > Install from existing installation to try to install BridgePoint from the v6.14 release
 
5.4 Update pom.xml files
* Update tyco version from 0.25.0 to 1.0.0
* Remove org.eclipse.antrl.ui
 ** This was not needed for the build and the new version of antrl was not compatible with Oxygen.  
* Update Apache ant from org.apache.ant_1.9.6.v201510161327 to org.apache.ant_1.10.1.v20170504-0840
* Update the Eclipse repository and update sites to point to oxygen instead of Mars.
** Mars Repository: 
		http://download.eclipse.org/releases/mars/
** Oxygen Repository  
		http://download.eclipse.org/releases/oxygen/
** Mars Update Site:  
		http://download.eclipse.org/eclipse/updates/4.5/  
** Oxygen Update Site  
		http://download.eclipse.org/eclipse/updates/4.7/   
* Updated org.codehaus.mojo (for exec-maven-plugin artifact) from 1.4.0 to 1.6.0  
* Updated org.eclipse.emf for (org.eclipse.emf.mwe2.launch artifact) from 2.8.3 to 2.9.0.201605261059  
* Updated xtext from  2.9.2 to 2.12.0  
* In src/org.xtuml.bp.xtext.masl.parent/pom.xml a dependency was added as follows:  
```
<dependencies>
						<dependency>
						    <groupId>org.eclipse.platform</groupId>
						    <artifactId>org.eclipse.equinox.common</artifactId>
						    <version>3.10.0</version>
						</dependency>
</dependencies>
```

5.5 prepare_build.sh  
Updated from org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar to 
  org.eclipse.equinox.launcher_1.4.0.v20161219-1356.jar  
  


### 6. Implementation Comments

It was difficult get the maven/tyco dependies correct on this update. I used a couple of this to help.  
6.1 https://www.eclipse.org/Xtext/documentation/350_continuous_integration.html  (under Maven Tycho Hints)  
Note that this page was stale, but even seeing the old version compatiblity requirements helped a little.  
6.2 The Oxygen maven p2 repository  
I browsed the repository directly and looked for the misc required plugin versions for the time-frame of the Oxygen release.  


### 7. Unit Test

A seperate issue has been raised to update the unit tests [[#2.2](#2.2)].

### 8. User Documentation

none. 

### 9. Code Changes

<pre>
Fork/Repository: rmulvey/bridgepoint  
Branch: 10010_oxygen  
URL: https://github.com/xtuml/bridgepoint/pull/619  

Fork/Repository: rmulvey/packaging  
Branch: 10010_oxygen  
URL: https://github.com/xtuml/packaging/pull/45

Fork/Repository: rmulvey/bptest  
Branch: 10010_oxygen  
URL: https://github.com/xtuml/bptest/pull/87

</pre>

### End

