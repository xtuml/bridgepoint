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

### 5. Work Required

5.1 Organize product definitions  
5.1.1  Add new product definition at ```releng/org.xtuml.bp.releng.parent.product/bridgepoint-dev.product```  
5.1.2  The current product defintion remains at ```releng/org.xtuml.bp.releng.parent.product/bridgepoint.product```  
5.1.3  The new "developer" product definition 

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
     org.antlr.ui
     org.eclipse.platform
     org.eclipse.help
     org.eclipse.egit
```
 
5.3.2  Developer version includes all the features of 5.3.1 plus
 
```
     org.eclipse.jdt
     org.eclipse.xtext.sdk
     org.eclipse.pde
```
 
### 6. Implementation Comments


### 7. Unit Test

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element


### 8. User Documentation


### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint  
Branch: 8930_multiple_products  

<pre>

</pre>

Fork/Repository: keithbrown/bptest  
Branch: 8930_multiple_products  

<pre>

</pre>

### End

