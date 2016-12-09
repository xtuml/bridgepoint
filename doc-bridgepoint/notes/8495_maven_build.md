---

This work is licensed under the Creative Commons CC0 License

---

# Update to eclipse.org-style build
### xtUML Project Design Note



1. Abstract
-----------
This note describes work performed to update the BridgePoint build to make it 
compatible with the build infrastructure provided by eclipse.org for eclipse 
projects.  This is done in preparation for papyrus-xtuml becoming a first-class
eclipse project.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8495](https://support.onefact.net/issues/8495) Headline issue.    
<a id="2.2"></a>2.2 [Apache Maven](https://maven.apache.org/)    
<a id="2.3"></a>2.3 [Hudson Continuous Integration server](http://hudson-ci.org/)     
<a id="2.4"></a>2.4 [Maven Lifecycle Documentation](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)   
<a id="2.5"></a>2.5 [Using Tycho for tests](https://eclipse.org/tycho/sitedocs/tycho-surefire/tycho-surefire-plugin/test-mojo.html)   
<a id="2.6"></a>2.6 [Tycho - Eclipse project](https://www.eclipse.org/tycho/)  
<a id="2.7"></a>2.7 [A Brief Overview of Building at Eclipse](https://wiki.eclipse.org/A_Brief_Overview_of_Building_at_Eclipse)     

3. Background
-------------
BridgePoint has long relied on a set of UNIX shell scripts to build BridgePoint
on a server.  The build involves a number of steps:  
1. Get code from source control  
2. Build plug-ins in the correct order  
3. Use post-build processing to copy extra files into plugins and the product configuration  
4. Create OS-specific product zip files  

The xtUML development team is creating an official project under eclipse called
papyrus-xtuml.  It will house the open source BridgePoint plug-ins.  The team
plans to be able to build the plug-ins using the build infrastructure hosted
by eclipse.org.  The BridgePoint build process needs to be enhanced to work on
these servers.  

4. Requirements
---------------
4.1  Support running a maven-based build process on a developer machine from the
  command line.  
4.2  Support building the BridgePoint plugins on eclipse build servers.  
4.3  Support building BridgePoint on a One Fact build server using the same 
  infrastructure as 4.1.  
  
5. Analysis
-----------
5.1  Eclipse.org uses Hudson[[2.3]](#2.3) and Maven[[2.2]](#2.2)/Tycho[[2.6]](#2.6) to 
  build plug-ins  and products as described in their build system overview [[2.7]](#2.7).   
  
5.2  Moving to a maven/tycho-based build will allow us to configure automatic
  multi-platform builds (including adding Mac to our built package set) and 
  automatic 32 and 64-bit builds where desired.  
  
5.3  Moving to a maven/tycho-based build will also allow us to integrate automated
  execution of JUnit tests with the tycho SureFire plugin.    
      
6. Design
---------
6.1 Structure  
* Maven uses POM (Project Object Model) files to define the configuration. These
  are arranged in a tree like structure with relative paths
* Root can be found in ```bridgepoint/releng/org.xtuml.bp.releng.parent/pom.xml```
* Configuration is inherited by children, but may be overridden/extended
* The Maven build is executed with “mvn verify” started from the directory of 
  the parent pom
* Build results are found in the target folder next to the pom.xmls
* Maven lifecycle documentation [[2.4]](#2.4)

6.2  Tycho   
* Tycho [[2.6]](#2.6) are the maven plugins for building osgi/Eclipse-Artifacts
* Tycho takes most information from existing Manifest.MF, feature.xml, etc. 
  (in regular maven for java you specify dependencies in the pom.xml)
* Tycho is very “picky” about the metadata. The build will fail if something is 
  configured in an odd fashion
  * That is why EclipseSource had to change the BREE (Bundle required execution 
  environment) to Java 8. Otherwise compilation failed
  * Exploding bundles (plugins as directory instead of JAR) is specified in the 
  Manifest.MF (Eclipse-BundleShape: dir)
* Tycho identifies the different artifacts by the packaging type in the pom.xml:
  * eclipse-plugin: Manifest.MF
  * eclipse-feature: feature.xml
  * eclipse-test-plugin: Manifest.MF
  * eclipse-repository: category.xml and/or *.product files

6.3  Product/Ready to use application.zip  
* See bridgepoint/releng/org.xtuml.bp.releng.parent.product
* The ```bridgepoint.product``` file may be opened in the IDE and edited. It 
  contains all features and plugins which will end up in the zip. 
  * Also it contains branding information like launcher-name, splash-screen, etc.
* The pom is of packaging type eclipse-repository
* The tycho-p2-director-plugin is configured to create the applications 
  (<goal>materialize-products</goal>) and to create zips (archive-products)
* The built environments are inherited from the parent pom (```bridgepoint/releng/org.xtuml.bp.releng.parent/pom.xml```). 
  This is done in the target-platform-configuration

6.4  Update Site/P2 Repository  
* The update site configuration can be found in bridgepoint/releng/org.xtuml.bp.releng.p2
* The ```category.xml``` can be edited in the IDE and contains the features available in the update site
* The pom is of packaging type eclipse-repository. No further configuration is needed.

6.5  Ant execution  
* Example of usage is at: ```bridgepoint/src/org.xtuml.bp.io.core/pom.xml```
* The maven-antrun-plugin is used to run ant
* Ant.home property is inherited from and specified in parent pom 
  (```bridgepoint/releng/org.xtuml.bp.releng.parent/pom.xml```)
* Ant file location and goal is specified
* Non-default tasks (antlr) are added by adding a dependency to ant-antlr

6.6  Xtend/Xtext generation  
* Configuration is inherited. Config can be found in ```bridgepoint/releng/org.xtuml.bp.releng.parent.generation/pom.xml```
* Xtend-maven-plugin is used. Code will be generated to a ```xtend-gen``` subdirectory

6.7  JREs for the product  
* The JREs are contributed to the product by a feature
* This feature can be found in the packaging repository at: ```packaging/features/org.xtuml.bp.jre```
* The ```feature.xml``` is empty on purpose, the magic happens in the ```build.properties``` file
* The ```build.properties``` specify a folder for every environment. The 
  contents of this folder will be put in the root location of an installation 
  (meaning next to the launcher). This may be used for other files as well.
* Eclipse will automatically pick up a JRE location in a folder called ```jre/``` 
  found next to the launcher
* The ```build.properties``` also specify that for linux the ```jre/``` folder 
  will be made executable
* __TODO__ In the JRE parent config, why is windows 32-bit specified but not 64-bit?

6.8  Tests
* There is an example for executing a test: ```bridgepoint/src/org.xtuml.bp.ui.explorer.test/pom.xml```
* The packaging type has to be eclipse-test-plugin (otherwise tests are not executed)
* The tests are configured via the tycho-surefire-plugin
  * Documentation details [[2.5]](#2.5)
* Configuration states testSuite (=id of the bundle container the test-suite), 
  testClass (fully qualified name of the test suite) and an argline  
  
6.9  OS-specific configurations  
6.9.1  The OS environments to build are configured in ```bridgepoint/releng/org.xtuml.bp.releng.parent/pom.xml```   
6.9.2  The following configurations are supported:  
  * Linux: 64-bit
  * Mac: 64-bit (with limitations like no mcmc, masl tools, etc...)
  * Windows: 64-bit and 32-bit  
  
6.10  MinGW  
6.10.1  On Windows 32 & 64-bit installs we want to package in MinGW (C compiler)  
6.10.2  This is done using a feature like we do for the JRE (see 6.9)  
6.10.3  I  wanted the files to be sucked in from outside the plug-in rather than
  live (perhaps duplicated) inside the plug-in.  The problem is that tycho does
  not support the one goal that I need to do this.  I found an old tycho bug for
  this and added a comment: https://bugs.eclipse.org/bugs/show_bug.cgi?id=386263#c6  
6.10.4  To work around this problem, I set up the MinGW feature like the JRE. This
  meant moving the MinGW files in the repository from their old home in under the
  ```BridgePointDeliverables``` folder into the plugin at ```packaging/features/org.xtuml.bp.MinGW/win32.all/MinGW```.   
6.10.5  __TODO__ There is a problem though: the generated ```p2content.xml``` does not
  list all the files to be installed by this feature like is found in the generated ```p2content.xml``` 
  produced by the maven build of the jre feature.  
  
6.11  docgen   
6.11.1  The docgen product feature is set up like the jre and MinGW features  
6.11.2  Configuring docgen in this way again required moving the docgen files 
  around in the packaging repository.  
6.11.3  __TODO__: suffers from the same problem as 6.10.5  

6.12  __TODO__ Model compiler files  
6.12.1  The mc files: archetypes, binaries, and schema need to be packaged and
  installed by maven.  
6.12.2  The approach is not yet determined.   Possibilities are:   
* fragments
* copy the files into the plugin before the maven build (via shell scripting?)
* to use the maven-resources plugin (https://maven.apache.org/plugins/maven-resources-plugin/copy-resources-mojo.html) 
  to copy it there during the maven build.
* other?   

6.12.3  Need to consider if we should put the MC files inside each mc plugin (like 
  we do currently), at the root of the BP installation, or in the shared bp.mc plug-in.    

6.13  __TODO__ MASL tools  
6.13.1  Need to "install" the MASL tools either into the ```tools/``` folder or 
  with the MC pieces.  So likely either duplicate or piggyback on the implementations
  in 6.11 or 6.12.  

7. Design Comments
------------------
7.1   We created a special instance of our existing linux build server on AWS 
  where we configured a Hudson server.   
7.1.1  Modified the security settings to open port 8080 for the web interface.   
7.1.2  You can start/stop the hudson with:  
* ```sudo service hudson start```
* ```sudo service hudson stop```
* It is configured to auto start

7.2   Maven can be used to run JUnit tests.  The Hudson job configuration page
  for BridgePoint has an area to pass flags to Maven.  Currently, the unit tests
  do not run completely successfully.  Thus, the build settings have them 
  disabled by setting ```skipTests=true``` and unchecking the ```Publish JUnit test result report```
  option.   

7.3  Maven can be used to build a single plug-in from the command line.  Open a
  terminal and go to the folder of the plug-in on disk.  From here the developer
  can execute ```mvn verify```.  If the developer runs with ```-o``` this will 
  tell maven to run offline and reuse the downloaded dependencies from previous 
  runs rather than re-downloading dependencies from the internet.

7.4  __TODO__ Need to update the build env on the hudson server from BridgePoint 5.5 to 5.8.0  

7.5  Improvements to be handled in a second phase:  
* Don't have hudson re-clone every time from scratch, use smart remote/branch switching
* Enable running JUnits with the build and make them all runnable
* Decide what to do about "the old way" of storing JRE and eclipse install bases 
in the packaging repository.  Can they be removed?  Should they?  Do we want or 
need to still support the old script-based build of BP Pro?
   

8. User Documentation
---------------------
8.1  __TODO__ - need to update HOWTO run a build doc for developers with info:
* Starting the hudson server  
* Running maven build locally
* Editing the branch to build on build job configuration webpage  
* Turning unit tests on/off   


9. Unit Test
------------
9.1 Successfully run a build of BridgePoint on a 1F AWS-based Hudson server (build 
branch 8495_maven_update)   

9.2  Successfully build locally by going to org.xtuml.bp.releng.parent and 
  running ```mvn verify```  
9.2.1  __TODO__ requires some work to deal with hardcoded paths in the maven pom files
  that are currently set up for the server.  
  
9.3  Successfully build on old build server with old/current script-based process      
9.3.1  __TODO__ requires work on the scripts to handle (copy and locate) files that
  now live in new homes in the repository.   

End
---


