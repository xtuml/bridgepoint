---

This work is licensed under the Creative Commons CC0 License

---

# Convert example models to C source MC (8397)
# Remove unused MC plugins (8396)
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the work done to get rid of the C binary model compiler and
to update the example projects to stop referring to the binary MC.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8397](https://support.onefact.net/issues/8397) - Headline issue  
<a id="2.2"></a>2.3 [BridgePoint DEI #8396](https://support.onefact.net/issues/8396) - Headline issue  
<a id="2.3"></a>2.3 [BridgePoint DEI #8395](https://support.onefact.net/issues/8395) - Spring cleaning  

3. Background
-------------
In the Spring of 2016, the xtUML team took a pass at doing some "spring 
cleaning".  The parent issue for this work is [[2.3]](#2.3).  Part of that work 
was to remove the C binary MC from BridgePoint.  Since BridgePoint is now 
open source and with the advent of python-based generator, we have no reason to 
lock the archetypes. 

4. Requirements
---------------
4.1  Remove `org.xtuml.bp.mc.c.binary` plug-in from BridgePoint.  
4.2  Remove `org.xtuml.bp.dap.*` plug-in and feature from BridgePoint.  
4.3  Remove `org.xtuml.bp.mc.xmiexport` plug-in from BridgePoint.  
4.4  The BridgePoint build shall be modified to not rely on C Binary MC.  
4.5  Source artifacts removed from BridgePoint shall be archived inside the
  `xtuml/graveyard` repository.  
4.6  Example projects inside `org.xtuml.bp.welome` shall be modified to use
  C Source MC instead of C Binary MC.  
       
5. Work Required
----------------
5.1  Modify `MC-Java/build.xml` to rely on the populated utilities under 
  `org.xtuml.bp.mc.c.source` rather than `org.xtuml.bp.mc.c.binary`.    

5.2 Many of the BridgePoint plug-in `generate.xml` files specify a path to the
  C Binary MC plug-in `bin/` folder to find tools to use during the build. These
  places are changed to the C Source MC.  
  From:  
  ```<property name="util_bin_dir" value="${eclipse-home}/src/org.xtuml.bp.mc.c.binary/mc3020/bin"/>```
  To:  
  ```<property name="util_bin_dir" value="${eclipse-home}/src/org.xtuml.bp.mc.c.source/mc3020/bin"/>```

5.3 Update the feature/plug-in heirarchy packaging to remove C Binary MC from 
  the containing feature.  

5.4  Remove the `org.xtuml.bp.dap.pkg-feature` feature and 
  `org.xtuml.bp.dap.pkg` plug-in.  

5.5  Remove the DocGen plug-in dependency on `org.xtuml.bp.mc.c.binary` and 
  replace it with `org.xtuml.bp.mc.c.source`.  

5.6  Remove the `org.xtuml.bp.mc.c.binary` plug-in and put it into the 
  graveyard.        

5.7  Modify `org.xtuml.bp.mc.c.source` so that it acts as a builder for the 
  C Binary MC.  
5.7.1  In the same way that the MC plugins handle both `org.xtuml` and 
  `com.mentor.nucleus` naming convention for the same MC.  The C Source MC
  can register itself as the handler for projects that use `org.xtuml.bp.mc.c.binary`
  and `com.mentor.nucleus.bp.mc.c.binary`.  This allows existing projects to
  not have to change.  They will simply use C Source MC instead of C Binary MC
  seamlessly.  

5.8  Remove the `org.xtuml.bp.mc.xmiexport` plug-in and put it into the
  graveyard.  

5.9  Modify the parent MC plug-in `org.xtuml.bp.mc` and the other remaining
  MC plug-ins (C++, Java, SystemC) to remove any references to 
  `org.xtuml.bp.mc.c.binary`.      

5.10  Update example models in `org.xtuml.bp.welcome/models` in their `.project`
  files to use C Source MC.  This is not strictly necessary due to the feature
  specified in 5.7, but it good form simply for users who may inspect the 
  internals of these projects.  

5.11  Modify the build scripts to stop doing processing on 
  `org.xtuml.bp.mc.c.binary` plug-in and it's files.  
  
5.12  Update the build order specification the build server uses in 
  `utilities/build/preferences/org.eclipse.core.resources.prefs`. Remove 
  `org.xtuml.bp.mc.c.binary` from the list.  


6. Implementation Comments
--------------------------

7. Unit Test
------------
7.1 Manual test (because of bootstrapping the test environment)   
7.1.1 Open the Launch configurations.  Duplicate `xBP Application - Mars`.     
7.1.2 On the new duplicate configuration, switch to the Plug-ins tab.  Change 
  the dropdown to "plug-ins selected below"   
7.1.3 In the filter box, enter "binary", uncheck org.xtuml.bp.mc.c.binary (from 
  the Host platform)    
7.1.4 Launch this configuration   
7.1.5 Create MicrowaveOven example    
7.1.6 Open `MicrowaveOven/.project`, verify specifies `org.xtuml.bp.mc.c.source`
  in two places    
7.1.7 Verify the project builds successfully.  
7.1.8 Modify `MicrowaveOven/.project`, change `org.xtuml.bp.mc.c.source` to 
  `org.xtuml.bp.mc.c.binary` in two places  
7.1.9 Verify the project still builds successfully.  
7.1.10  Inspect the second line of the xtumlmc_build output Console log.  Verify
  that it says `org.xtuml.bp.mc.c.source` was used.  

8. User Documentation
---------------------
8.1  Update Developer Getting Started guide to configure the C source MC for 
  building BridgePoint rather than C binary MC.    

9. Code Changes
---------------
Repository: bridgepoint  
Branch name: 8397_move_to_source_mc  

<pre>

A   doc-bridgepoint/notes/8005_Test_Orderring/8005_Test_Orderring.md
A   doc-bridgepoint/notes/8397_move_to_source_mc_int.md
M   doc-bridgepoint/process/Developer Getting Started Guide.md
M   doc-bridgepoint/process/development-workspace-setup/BridgePointDev-Linux/bin/xtumlmc_gen_erate
M   src/MC-Java/build.xml
M   src/MC-Java/ooa_schema.sql
M   src/installer/build_installer_bp.sh
D   src/installer/install_linux.xml
D   src/installer/install_windows.xml
D   src/installer/post_install_script.sh
M   src/org.xtuml.bp.als/generate.xml
M   src/org.xtuml.bp.bld.pkg-feature/feature.xml
M   src/org.xtuml.bp.cli/META-INF/MANIFEST.MF
M   src/org.xtuml.bp.cli/generate.xml
M   src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/BuildWorkbenchAdvisor.java
M   src/org.xtuml.bp.compare/generate.xml
M   src/org.xtuml.bp.core.test/generate.xml
M   src/org.xtuml.bp.core/generate.xml
D   src/org.xtuml.bp.dap.pkg-feature/.gitignore
D   src/org.xtuml.bp.dap.pkg-feature/.project
D   src/org.xtuml.bp.dap.pkg-feature/build.properties
D   src/org.xtuml.bp.dap.pkg-feature/feature.xml
D   src/org.xtuml.bp.dap.pkg-feature/license.html
D   src/org.xtuml.bp.dap.pkg/.gitignore
D   src/org.xtuml.bp.dap.pkg/.project
D   src/org.xtuml.bp.dap.pkg/META-INF/MANIFEST.MF
D   src/org.xtuml.bp.dap.pkg/about.html
D   src/org.xtuml.bp.dap.pkg/about.ini
D   src/org.xtuml.bp.dap.pkg/about.mappings
D   src/org.xtuml.bp.dap.pkg/about.properties
D   src/org.xtuml.bp.dap.pkg/bridgepoint/.gitignore
D   src/org.xtuml.bp.dap.pkg/bridgepoint/readme.txt
D   src/org.xtuml.bp.dap.pkg/build.properties
D   src/org.xtuml.bp.dap.pkg/custom.build.properties
D   src/org.xtuml.bp.dap.pkg/icons/bpAbout-lg.gif
D   src/org.xtuml.bp.dap.pkg/icons/bpAbout-sml.gif
D   src/org.xtuml.bp.dap.pkg/icons/bpAbout.gif
D   src/org.xtuml.bp.dap.pkg/icons/green-bp-dbl.gif
D   src/org.xtuml.bp.dap.pkg/icons/green-bp.gif
D   src/org.xtuml.bp.dap.pkg/plugin.xml
D   src/org.xtuml.bp.dap.pkg/plugin_customization.ini
D   src/org.xtuml.bp.dap.pkg/plugin_customization.properties
M   src/org.xtuml.bp.docgen/META-INF/MANIFEST.MF
M   src/org.xtuml.bp.docgen/generate.xml
M   src/org.xtuml.bp.docgen/src/org/xtuml/bp/docgen/generator/Generator.java
M   src/org.xtuml.bp.io.core/generate.xml
M   src/org.xtuml.bp.io.mdl/generate.xml
D   src/org.xtuml.bp.mc.c.binary/.classpath
D   src/org.xtuml.bp.mc.c.binary/.gitignore
D   src/org.xtuml.bp.mc.c.binary/.project
D   src/org.xtuml.bp.mc.c.binary/.settings/org.eclipse.jdt.core.prefs
D   src/org.xtuml.bp.mc.c.binary/META-INF/MANIFEST.MF
D   src/org.xtuml.bp.mc.c.binary/about.html
D   src/org.xtuml.bp.mc.c.binary/build.properties
D   src/org.xtuml.bp.mc.c.binary/build_settings/build_setting.properties
D   src/org.xtuml.bp.mc.c.binary/defaults/launch_specification/Model Compiler.launch
D   src/org.xtuml.bp.mc.c.binary/generate.xml
D   src/org.xtuml.bp.mc.c.binary/log_dir/.gitignore
D   src/org.xtuml.bp.mc.c.binary/log_dir/README.txt
D   src/org.xtuml.bp.mc.c.binary/mc3020/.gitignore
D   src/org.xtuml.bp.mc.c.binary/mc3020/README.txt
D   src/org.xtuml.bp.mc.c.binary/plugin.xml
D   src/org.xtuml.bp.mc.c.binary/src/org/xtuml/bp/mc/c/binary/Activator.java
D   src/org.xtuml.bp.mc.c.binary/src/org/xtuml/bp/mc/c/binary/ExportBuilder.java
D   src/org.xtuml.bp.mc.c.binary/src/org/xtuml/bp/mc/c/binary/MCNature.java
D   src/org.xtuml.bp.mc.c.binary/src/org/xtuml/bp/mc/c/binary/MCNewProjectWizard.java
M   src/org.xtuml.bp.mc.c.source/META-INF/MANIFEST.MF
M   src/org.xtuml.bp.mc.c.source/generate.xml
M   src/org.xtuml.bp.mc.c.source/plugin.xml
M   src/org.xtuml.bp.mc.c.source/src/org/xtuml/bp/mc/c/source/MCNewProjectWizard.java
M   src/org.xtuml.bp.mc.cpp.source/META-INF/MANIFEST.MF
M   src/org.xtuml.bp.mc.cpp.source/generate.xml
M   src/org.xtuml.bp.mc.systemc.source/META-INF/MANIFEST.MF
M   src/org.xtuml.bp.mc.systemc.source/generate.xml
D   src/org.xtuml.bp.mc.xmiexport/.classpath
D   src/org.xtuml.bp.mc.xmiexport/.gitignore
D   src/org.xtuml.bp.mc.xmiexport/.project
D   src/org.xtuml.bp.mc.xmiexport/META-INF/MANIFEST.MF
D   src/org.xtuml.bp.mc.xmiexport/about.html
D   src/org.xtuml.bp.mc.xmiexport/build.properties
D   src/org.xtuml.bp.mc.xmiexport/generate.xml
D   src/org.xtuml.bp.mc.xmiexport/plugin.xml
D   src/org.xtuml.bp.mc.xmiexport/src/org/xtuml/bp/mc/xmiexport/XMIExport.java
D   src/org.xtuml.bp.mc.xmiexport/src/org/xtuml/bp/mc/xmiexport/XMIExportBuilder.java
D   src/org.xtuml.bp.mc.xmiexport/src/org/xtuml/bp/mc/xmiexport/XMIExportNature.java
M   src/org.xtuml.bp.mc/META-INF/MANIFEST.MF
M   src/org.xtuml.bp.mc/generate.xml
M   src/org.xtuml.bp.mc/src/org/xtuml/bp/mc/AbstractNature.java
M   src/org.xtuml.bp.mc/src/org/xtuml/bp/mc/MCBuilderArgumentHandler.java
M   src/org.xtuml.bp.model.compare.test/generate.xml
M   src/org.xtuml.bp.model.compare/generate.xml
M   src/org.xtuml.bp.pkg-feature/feature.xml
M   src/org.xtuml.bp.ui.canvas.test/generate.xml
M   src/org.xtuml.bp.ui.canvas/generate.xml
M   src/org.xtuml.bp.ui.explorer/generate.xml
M   src/org.xtuml.bp.ui.properties.test/generate.xml
M   src/org.xtuml.bp.ui.properties/generate.xml
M   src/org.xtuml.bp.ui.session/generate.xml
M   src/org.xtuml.bp.ui.text/generate.xml
M   src/org.xtuml.bp.ui.tree/generate_schema.xml
M   src/org.xtuml.bp.ui.tree/templates/generate.xml
M   src/org.xtuml.bp.welcome/models/MicrowaveOven/.project
M   src/org.xtuml.bp.welcome/models/TemplateProject/.project
M   src/org.xtuml.bp.welcome/models/avpace/.project
M   src/org.xtuml.bp.x2m/META-INF/MANIFEST.MF
M   src/org.xtuml.bp.x2m/generate.xml
M   utilities/build/configure_build_process.sh
M   utilities/build/configure_external_dependencies.sh
M   utilities/build/preferences/org.eclipse.core.resources.prefs
</pre>

End
---

