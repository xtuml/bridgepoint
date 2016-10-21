---

This work is licensed under the Creative Commons CC0 License

---

# Build Script Changes to Add MASL Editor Plugins
### xtUML Project Implementation Note

1. Abstract
-----------
The MASL editor plugins need to be built and added to BridgePoint.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8441](https://support.onefact.net/issues/8441) Integrate richer XText MASL editor  
<a id="2.2"></a>2.2 [HOWTO Install/Update Components in BridgePoint's Eclipse Base](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-update-install-eclipse-base-components.md)

3. Background
-------------
This note covers the changes needed to incorporate the MASL editor plugins from
TypeFox into the BridgePoint build process.  The plugins are located in the
BridgePoint repository at ```src/org.xtuml.bp.xtext.masl.parent```.  Maven is
used to build the plugins and they have a runtime dependency on the Xcore SDK.

4. Requirements
---------------
4.1 Install the required packages on the build server.  

4.2 Add Xcore SDK to BridgePoint's Eclipse base.  

4.3 Modify the BridgePoint build scripts.  
4.3.1 Build the plugins.  
4.3.1 Install the plugins.  

5. Work Required
----------------
5.1 Install the required packages on the build server.  
5.1.1 Java8  
```
$ sudo add-apt-repository ppa:webupd8team/java
$ sudo apt-get update
$ sudo apt-get install oracle-java8-installer
```
5.1.2 maven
```
$ sudo apt-get install maven
```

5.2 Add Xcore SDK to BridgePoint's Eclipse base.  
See [[2.2]](#2.2) for the details on adding plugins to BridgePoint's Eclipse
base.  The following commands were used to add the Xcore SDK to the packages
repository:
* For Linux BridgePoint base:
```
$ /opt/xtuml/BridgePoint/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://www.eclipse.org/modeling/amalgam/downloads/package/modeling/mars/ -installIU org.eclipse.emf.ecore.xcore.sdk.feature.group -destination ~/git/packaging/install_bases/BridgePoint_for_Linux_e4.5/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```
* For Windows BridgePoint base:
```
$ /opt/xtuml/BridgePoint/eclipse/eclipse -application org.eclipse.equinox.p2.director -repository http://www.eclipse.org/modeling/amalgam/downloads/package/modeling/mars/ -installIU org.eclipse.emf.ecore.xcore.sdk.feature.group -destination ~/git/packaging/install_bases/BridgePoint_e4.5/EclipseDeliverables/eclipse/ -profile epp.package.modeling
```

5.3 Modify the BridgePoint build scripts.  
5.3.1 Build the plugins by adding the following lines to ```bridgepoint/utilities/build/headless_build.sh```:
```bash
# Build the MASL plugins using maven.
cd "${git_bp}/src/org.xtuml.bp.xtext.masl.parent"
mvn clean install
```

5.3.2 Install the plugins by adding the following copy commands to ```bridgepoint/utilities/build/create_bp_release.sh```:  
```bash
# Copy the MASL jars to the plugins dir.
cd ${git_bp}/src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.updatesite/target/repository/features
cp -f *.jar ${site_result_dir}/eclipse/features
cd ${git_bp}/src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.updatesite/target/repository/plugins
cp -f *.jar ${site_result_dir}/eclipse/plugins
```

6. Implementation Comments
--------------------------
6.1 Adding the Xcore SDK caused many of the Eclipse Modeling Framework plugins
(org.eclipse.emf.*) already in the BridgePoint base to be updated.  This is
being noted because a significant number of files in packaging were changed.
It is not expected to be a problem.  

6.2 There were references to the old MASL plugins still in the source code
that needed to be removed.  The following lines were removed
from ```bridgepoint/src/org.xtuml.bp.bld.pkg-feature/feature.xml```:   
```xml
    <plugin id="org.xtuml.bp.ui.xtext.masl"
          download-size="0"
          install-size="0"
          version="5.5.0"/>
 
    <plugin id="org.xtuml.bp.ui.xtext.masl.ide"
          download-size="0"
          install-size="0"
          version="5.5.0"/>
 
    <plugin id="org.xtuml.bp.ui.xtext.masl.ui"
          download-size="0"
          install-size="0"
          version="5.5.0"/>
```

7. Unit Test
------------
Run the build on the server and verify it produced expected output.

8. User Documentation
---------------------
None.

9. Code Changes
---------------
Fork/Repository:  jason-rhodes/bridgepoint  
Branch:  8441_masl_editor_2

<pre>
> doc-bridgepoint/notes/8441_MASL_plugin_build_changes.md
> doc-bridgepoint/process/Xtext-getting-started.md

org.xtuml.bp.bld.pkg-feature/feature.xml

utilities/build/create_bp_release.sh
utilities/build/headless_build.sh
</pre>


Fork/Repository:  jason-rhodes/packaging  
Branch:  8441_masl_editor_2

<pre>
Added/updated plugins to install Xcore SDK.
</pre>

End
---

