---

This work is licensed under the Creative Commons CC0 License

---

# Stop building some Windows edition
### xtUML Project Implementation Note


### 1. Abstract

This note describes the details of changes to the BridgePoint process to 
stop creating some Windows editions of BridgePoint.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9745](https://support.onefact.net/issues/9745) Headline issue    

### 3. Background

Users no longer have an interest in 32-bit versions of BridgePoint for Windows.  We can 
increase the performance of the build process and reduce the amount of maintained packages
by stopping the process of creating these versions.  

Additionally, the build process creates a 64-bit developer version of BridgePoint during the 
build process.  This version is not supported and thus we don't publish the link.  We should stop
packaging this as well.  

### 4. Requirements

4.1 Stop creating Windows 32-bit developer and modeler versions.    
4.2 Stop creating Windows 64-bit developer version.   

### 5. Work Required

5.1  Update Maven POM files to comment out environment sections that specify Win32 32-bit  
5.1.1  Search all POMs in `bridgepoint` and `packagaing` repositories for `environment`.  Remove environment
sections that specify:  
```xml
<environment>
  <os>win32</os>
  <ws>win32</ws>
  <arch>x86</arch>
</environment>
```  

5.2  Update eclipse `build.properties` files that specify packaging of Win32 32-bit files.  Search all plugins
in `bridgepoint` and `packagaing` repositories.  Remove lines like:  
```xml  
root.win32.win32.x86=common,\
                     win.all
```

5.3  In order to have full control over which product is built for which OS, we had to separate the developer and 
modeler products into different plugins.  Thus, we created a new plugin `org.xtuml.bp.releng.parent.product-dev`. This
was required so we could put environment creation overrides into the POM file for the product plugins.  Without the
separation and override the environments were being inherited from `org.xtuml.bp.relent.parent` and all environments 
were being created all of the time.  The parent POM is modified to include the new developer module.   
 
5.4  Update the jenkins configuration in the `buildmt` repository to:  
5.4.1  Copy the materialized product zipfiles from the new `org.xtuml.bp.relent.parent.product-dev`   
5.4.2  Remove the creation of download links in `buildfiles.html` for Windows versions that no longer exist  

### 6. Implementation Comments

6.1 Update eclipse `build.properties` files that specify packaging of Linux 32-bit files.  Remove lines like:  
```xml  
root.linux.gtk.x86=common,\
                   linux.all
root.linux.gtk.x86.permissions.775=tools/mc/**
```

### 7. Unit Test

7.1  Run the build on the server  
* :+1: See that the Windows 32-bit packages are not created.  
* :+1: See that the Windows 64-bit developer package is not created.  


### 8. User Documentation

Add a note in the next release notes that 32-bit Windows is no longer available.  

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint   
Branch: 9745_dump_win32   

<pre>
doc-bridgepoint/notes/9745_dump_win32_int.md
releng/org.xtuml.bp.mctools.parent/build.properties
releng/org.xtuml.bp.mctools.parent/pom.xml
releng/org.xtuml.bp.releng.parent.product-dev/.externalToolBuilders/Build.launch
releng/org.xtuml.bp.releng.parent.product-dev/.externalToolBuilders/Clean.launch
releng/org.xtuml.bp.releng.parent.product-dev/.project
releng/org.xtuml.bp.releng.parent.product-dev/bridgepoint-dev.product
releng/org.xtuml.bp.releng.parent.product-dev/p2.inf
releng/org.xtuml.bp.releng.parent.product-dev/pom.xml
releng/org.xtuml.bp.releng.parent.product/pom.xml
releng/org.xtuml.bp.releng.parent/pom.xml
</pre>

Fork/Repository: keithbrown/packaging   
Branch: 9745_dump_win32   

<pre>
org.xtuml.bp.docgen/build.properties
org.xtuml.bp.docgen.parent/pom.xml
org.xtuml.bp.jre/build.properties
org.xtuml.bp.jre.parent/pom.xml
org.xtuml.bp.MinGW/build.properties
org.xtuml.bp.MinGW.parent/pom.xml
</pre>

Fork/Repository: keithbrown/buildmt  
Branch: 9745_dump_win32    

<pre>
buildmt/jenkins-home/jobs/bridgepoint/config.xml
buildmt/jenkins-home/jobs/publish/config.xml
</pre>

### End

