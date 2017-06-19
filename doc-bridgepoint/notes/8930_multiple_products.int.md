---

This work is licensed under the Creative Commons CC0 License

---

# Build "user" and "developer" versions of BridgePoint
### xtUML Project Implementation Note


### 1. Abstract

This note describes the work performed to create two similar, but task-focused,
versions of BridgePoint.  One is for xtUML modelers.  The other is for BridgePoint
developers.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #8930](https://support.onefact.net/issues/8930) Headline Issue    

### 3. Background

The BridgePoint installation is very big.  It pulls in many packages from the 
greater eclipse ecosystem.  We have decided that we want to trim the fat and 
only package the bare minimum set of packages that allow a user to accomplish
the task at hand.  

One version is targeted towards xtUML application modelers.  The second version
is targeted at BridgePoint developers.  Since the BridgePoint code base is 
modeled, the developer version package set is naturally a superset of the user
version.   

A natural benefit of removing unneeded packages is that menu items, preferences,
help, and other eclipse UI bits are thinned down and clutter is reduced.   

Another benefit will be reduced build times on the server because there is much
less data to download and package from eclipse mirrors.  Of course this also 
means that the installation package of BridgePoint itself is reduced in size.  

### 4. Requirements

4.1 Reduce the number of eclipse features included to the minimum set.   

4.2 Create two versions of the BridgePoint installation.  
4.2.1  The "user" version shall be useful for xtUML application modeling,
  debugging, and code generation.  
4.2.2  The "developer" version shall include the features of 4.2.1 and add tools
  for Java editing and debugging, Xtext development, and JUnit test execution.  

4.3  Both versions shall be built, packaged, and published with a single build 
  invocation.  

4.4  BridgePoint code/models and tests shall build and run successfully with the
  updated developer version of the tool.  
  
### 5. Work Required

5.1 Organize product definitions  
5.1.1  Add new product definition at ```releng/org.xtuml.bp.releng.parent.product/bridgepoint-dev.product```  
5.1.2  The current product definition remains at ```releng/org.xtuml.bp.releng.parent.product/bridgepoint.product```  

5.2 Disable Eclipse AERI (Automatic Error Reporting Interface)  
5.2.1  Edit the vmargs definitions in both products to add the flag to disable
  AERI.  We do not want eclipse to be unnecessarily trying to contact a server
  on the internet.  Added
  
```
  <vmArgs>-Dorg.eclipse.epp.logging.aeri.ui.skipReports=true</vmArgs>
```

  Note that I had to do this by editing the product definitions in a text editor. When
  I attempted to modify the vmargs in the visual product editor wizard page eclipse
  crashed.  
  
5.3  Simplify product packaging.  Minimize included features.  
5.3.1  User version now includes only

```
     org.xtuml.bp.pkg.feature
     org.xtuml.bp.jre
     org.xtuml.bp.MinGW
     org.xtuml.bp.docgen
     org.xtuml.bp.mctools
     org.xtuml.bp.xtext.masl.feature
     org.eclipse.cdt
     org.eclipse.platform
     org.eclipse.help
     org.eclipse.egit
```
 
5.3.2  Developer version includes all the features of 5.3.1 plus
 
```
     org.antlr.ui
     org.eclipse.jdt
     org.eclipse.xtext.sdk
     org.eclipse.pde
```
 
5.4  Build modifications
5.4.1  Update ```releng/org.xtuml.bp.releng.parent.product/pom.xml``` to include
  the new developer product in the active product configurations.  Adding the 
  new product here causes both user and developer versions to be built and 
  packaged in a single build invocation.  
  
5.5  Update the JUnit code base to remove tests designed to use CVS.  These are
  no longer run as part of the unit test suite.  These tests were the reason we
  included the "SDK examples" feature, which is now removed.  
  
5.6  Update the html file created by the server build to include links to the 
  developer version as well as the user version.  
  
### 6. Implementation Comments
6.1  An observation on file sizes:  
* Linux  
  * current: 584 MB
  * user: 348 MB
  * developer: 392 MB
* Mac  
  * current: 439 MB
  * user: 203 MB 
  * developer: 247 MB

6.2  In the user version, we found that "Installed JREs" list has no data and is
  missing a path to the JRE on the system.  This prevented running the GPS Watch
  UI launch until it is set.  
  
### 7. Unit Test

7.1 Ran the following unit tests  

| Test | Result | Time |  
|------|--------|------|  
| Core | pass   | 514s |  
| Core2 | 1 Fail |  525s |  
| Core RTO Move|  pass | 210s |   
| io.mdl | pass | 173s |   
| io.mdl2 | pass | 97s  |   
| Parser | pass | 455s |   
| ui.canvas | pass | 167s |   
| ui.explorer | pass | 52s |   
| ui.properties | pass | 36s |   
| ui.text. | pass | 123s   |   


### 8. User Documentation
8.1 Updated the buildfiles.html page to link both versions  
8.2 Updated the developer getting started guide to specify downloading the 
  BridgePoint Developer version   
8.3 Updated the FAQ to explain the two versions  

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint  
Branch: 8930_multiple_products  

<pre>
doc-bridgepoint/notes/8930_multiple_products.int.md
releng/org.xtuml.bp.releng.parent.product/bridgepoint-dev.product
releng/org.xtuml.bp.releng.parent.product/bridgepoint.product
releng/org.xtuml.bp.releng.parent.product/pom.xml
</pre>

Fork/Repository: keithbrown/bptest  
Branch: 8930_multiple_products  

<pre>
src/org.xtuml.bp.core.test/META-INF/MANIFEST.MF 
src/org.xtuml.bp.core.test/generate.xml         
src/org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/ImportDuplicatedModelTest.java
src/org.xtuml.bp.debug.ui.test/META-INF/MANIFEST.MF
src/org.xtuml.bp.test/META-INF/MANIFEST.MF         
src/org.xtuml.bp.test/src/org/xtuml/bp/test/common/BaseTest.java 
src/org.xtuml.bp.test/src/org/xtuml/bp/test/common/CVSUtils.java  
src/org.xtuml.bp.ui.canvas.test/META-INF/MANIFEST.MF              
</pre>

Fork/Repository: keithbrown/buildmt  
Branch: 8930_multiple_products  

<pre>
buildmt/jenkins-home/jobs/publish/config.xml
</pre>

### End

