---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Poor persistence during graphical modifications
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes a change that prevents an issue where persistence
performance is hindered due to reading the system model file.

2. Document References
----------------------  
[1] Issues 33, https://github.com/bridgepoint/doc/issues/33    

3. Background
-------------
Customers have reported poor graphical performance and noted that most of it was
due to file reads.  The issue shows itself more when working across slower
network shared file systems.

4. Requirements
---------------
4.1 Graphical modifications shall show better performance.

5. Work Required
----------------
5.1. Increase persistence performance
  
Investigation showed that code which checked the current persistence version was
being called even if a model file had already been loaded.  This is done on
every model file in a system.  The step is also performed any time the tool
tries to access an element from a given model file.

In order to address this the call to
PersistenceManager.isPersistenceVersionAcceptable() is moved to be called after
the check to see if the file has already been loaded.

6. Implementation Comments
--------------------------
6.1 Merge licensing changes

In order to run unit tests, changes from the v4.2.0 branch were merged into this
working branch.   

7. Unit Test
------------
_- Run all existing unit tests   
_R All tests pass

8. Code Changes
---------------
Branch name: 33_poor_performance_for_graphics_persistence
<pre>
com.mentor.nucleus.bp.als.oal/about.html
com.mentor.nucleus.bp.als.oal/plugin.xml
com.mentor.nucleus.bp.als.oal.test/plugin.xml

com.mentor.nucleus.bp.bld.pkg/about.html
com.mentor.nucleus.bp.bld.pkg/about.mappings
com.mentor.nucleus.bp.bld.pkg/about.properties
com.mentor.nucleus.bp.bld.pkg/plugin.xml
com.mentor.nucleus.bp.bld.pkg-feature/feature.xml
    
com.mentor.nucleus.bp.cdt/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.cdt/about.html

com.mentor.nucleus.bp.cli/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.cli/src/com/mentor/nucleus/bp/cli/BuildWorkbenchAdvisor.java
com.mentor.nucleus.bp.cli/about.html
    
com.mentor.nucleus.bp.compare/about.html
com.mentor.nucleus.bp.compare/plugin.xml

com.mentor.nucleus.bp.core/arc/create_core_plugin.inc
com.mentor.nucleus.bp.core/src/com/mentor/nucleus/bp/core/common/PersistableModelComponent.java
com.mentor.nucleus.bp.core/src/com/mentor/nucleus/bp/core/ui/preferences/ExportPreferences.java
com.mentor.nucleus.bp.core/src/com/mentor/nucleus/bp/core/util/BridgePointLicenseManager.java
com.mentor.nucleus.bp.core/src/com/mentor/nucleus/bp/core/util/UIUtil.java
com.mentor.nucleus.bp.core/.classpath
com.mentor.nucleus.bp.core/about.html
com.mentor.nucleus.bp.core/jlc_2013_2.jar
    
com.mentor.nucleus.bp.core.test/src/com/mentor/nucleus/bp/core/test/TigerNatureTestGenerics.java
com.mentor.nucleus.bp.core.test/plugin.xml
    
com.mentor.nucleus.bp.dap.pkg/about.html
com.mentor.nucleus.bp.dap.pkg/about.mappings
com.mentor.nucleus.bp.dap.pkg/about.properties
com.mentor.nucleus.bp.dap.pkg/plugin.xml
com.mentor.nucleus.bp.dap.pkg-feature/feature.xml
    
com.mentor.nucleus.bp.debug.ui/src/com/mentor/nucleus/bp/debug/ui/launch/BPLaunchDelegate.java
com.mentor.nucleus.bp.debug.ui/about.html
com.mentor.nucleus.bp.debug.ui/plugin.xml
    
com.mentor.nucleus.bp.debug.ui.test/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.debug.ui.test/plugin.xml
    
com.mentor.nucleus.bp.doc/license/licenseagreement.htm
om.mentor.nucleus.bp.doc/license/oss.htm
com.mentor.nucleus.bp.doc/license/Trademarks.htm
com.mentor.nucleus.bp.doc/ReleaseNotes/HTML/ReleaseNotes.htm
com.mentor.nucleus.bp.doc/WhatsNew/HTML/WhatsNew.htm
com.mentor.nucleus.bp.doc/about.html
com.mentor.nucleus.bp.doc/plugin.xml
    
com.mentor.nucleus.bp.docgen/about.html
com.mentor.nucleus.bp.docgen/plugin.xml
    
com.mentor.nucleus.bp.internal.tools/plugin.xml
com.mentor.nucleus.bp.internal.tools/update_BP_version.xml
    
com.mentor.nucleus.bp.io.core/arc/gen_import_java.inc
com.mentor.nucleus.bp.io.core/src/com/mentor/nucleus/bp/io/core/CoreExport.java
com.mentor.nucleus.bp.io.core/about.html
com.mentor.nucleus.bp.io.core/plugin.xml
    
com.mentor.nucleus.bp.io.image/about.html
com.mentor.nucleus.bp.io.image/plugin.xml
    
com.mentor.nucleus.bp.io.mdl/src/com/mentor/nucleus/bp/io/mdl/wizards/ModelExportWizard.java
com.mentor.nucleus.bp.io.mdl/about.html
com.mentor.nucleus.bp.io.mdl/plugin.xml
    
com.mentor.nucleus.bp.io.mdl.test/plugin.xml
    
com.mentor.nucleus.bp.io.sql/about.html
com.mentor.nucleus.bp.io.sql/plugin.xml

com.mentor.nucleus.bp.io.sql.test/plugin.xml
    
com.mentor.nucleus.bp.mc/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.mc/src/com/mentor/nucleus/bp/mc/AbstractExportBuilder.java
com.mentor.nucleus.bp.mc/src/com/mentor/nucleus/bp/mc/MCBuilderArgumentHandler.java
com.mentor.nucleus.bp.mc/about.html
    
com.mentor.nucleus.bp.mc.c.binary/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.mc.c.binary/src/com/mentor/nucleus/bp/mc/c/binary/MCNewProjectWizard.java
com.mentor.nucleus.bp.mc.c.binary/about.html
    
com.mentor.nucleus.bp.mc.c.source/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.mc.c.source/src/com/mentor/nucleus/bp/mc/c/source/MCNewProjectWizard.java
com.mentor.nucleus.bp.mc.c.source/about.html
    
com.mentor.nucleus.bp.mc.cpp.source/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.mc.cpp.source/src/com/mentor/nucleus/bp/mc/cpp/source/MCNewProjectWizard.java
com.mentor.nucleus.bp.mc.cpp.source/about.html
    
com.mentor.nucleus.bp.mc.mcpaas/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.mc.mcpaas/src/com/mentor/nucleus/bp/mc/mcpaas/ExportBuilder.java
com.mentor.nucleus.bp.mc.mcpaas/src/com/mentor/nucleus/bp/mc/mcpaas/MCNewProjectWizard.java
    
com.mentor.nucleus.bp.mc.none/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.mc.none/about.html
    
com.mentor.nucleus.bp.mc.systemc.source/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.mc.systemc.source/src/com/mentor/nucleus/bp/mc/systemc/source/MCNewProjectWizard.java
com.mentor.nucleus.bp.mc.systemc.source/about.html
    
com.mentor.nucleus.bp.mc.template/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.mc.template/templates/model_compiler/java/MCNewProjectWizard.java
com.mentor.nucleus.bp.mc.template/about.html
    
com.mentor.nucleus.bp.mc.vhdl.source/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.mc.vhdl.source/src/com/mentor/nucleus/bp/mc/vhdl/source/MCNewProjectWizard.java
com.mentor.nucleus.bp.mc.vhdl.source/about.html
    
com.mentor.nucleus.bp.mc.xmiexport/about.html
com.mentor.nucleus.bp.mc.xmiexport/plugin.xml
    
com.mentor.nucleus.bp.model.compare/about.html
com.mentor.nucleus.bp.model.compare/plugin.xml
    
com.mentor.nucleus.bp.model.compare.test/META-INF/MANIFEST.MF
    
com.mentor.nucleus.bp.pkg/about.html
com.mentor.nucleus.bp.pkg/about.mappings
com.mentor.nucleus.bp.pkg/about.properties
com.mentor.nucleus.bp.pkg/plugin.properties
com.mentor.nucleus.bp.pkg/plugin.xml
    
com.mentor.nucleus.bp.pkg-feature/feature.xml
    
com.mentor.nucleus.bp.search/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.search/about.html
    
com.mentor.nucleus.bp.search.test/META-INF/MANIFEST.MF
    
com.mentor.nucleus.bp.sequencecapture/META-INF/MANIFEST.MF

com.mentor.nucleus.bp.test/plugin.xml
    
com.mentor.nucleus.bp.ui.canvas/about.html
com.mentor.nucleus.bp.ui.canvas/plugin.xml

com.mentor.nucleus.bp.ui.canvas.test/plugin.xml
    
com.mentor.nucleus.bp.ui.explorer/about.html
com.mentor.nucleus.bp.ui.explorer/plugin.xml
    
com.mentor.nucleus.bp.ui.explorer.test/plugin.xml
    
com.mentor.nucleus.bp.ui.graphics/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.ui.graphics/about.html
    
com.mentor.nucleus.bp.ui.properties/about.html
com.mentor.nucleus.bp.ui.properties/plugin.xml
    
com.mentor.nucleus.bp.ui.properties.test/plugin.xml
    
com.mentor.nucleus.bp.ui.search/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.ui.search/about.html
    
com.mentor.nucleus.bp.ui.sem/about.html
com.mentor.nucleus.bp.ui.sem/plugin.xml
    
com.mentor.nucleus.bp.ui.session/about.html
com.mentor.nucleus.bp.ui.session/plugin.xml

com.mentor.nucleus.bp.ui.text/arc/create_plugin_xml.arc
com.mentor.nucleus.bp.ui.text/about.html

com.mentor.nucleus.bp.ui.text.test/plugin.xml
    
com.mentor.nucleus.bp.utilities/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.utilities/about.html

com.mentor.nucleus.bp.verifier.pkg/about.html
com.mentor.nucleus.bp.verifier.pkg/about.mappings
com.mentor.nucleus.bp.verifier.pkg/about.properties
com.mentor.nucleus.bp.verifier.pkg/plugin.xml
    
com.mentor.nucleus.bp.verifier.pkg-feature/license_files/colorschememapping.xml
com.mentor.nucleus.bp.verifier.pkg-feature/license_files/filelist.xml
com.mentor.nucleus.bp.verifier.pkg-feature/license_files/themedata.thmx
com.mentor.nucleus.bp.verifier.pkg-feature/feature.xml
com.mentor.nucleus.bp.verifier.pkg-feature/license.html
    
com.mentor.nucleus.bp.welcome/css/graphics/rootpage/background.jpg
com.mentor.nucleus.bp.welcome/css/graphics/rootpage/brandmark.gif
com.mentor.nucleus.bp.welcome/models/GPS Watch/.classpath
com.mentor.nucleus.bp.welcome/models/GPS Watch/.externalToolBuilders/Model Compiler.launch
com.mentor.nucleus.bp.welcome/about.html
com.mentor.nucleus.bp.welcome/plugin.xml

com.mentor.nucleus.bp.welcome.test/META-INF/MANIFEST.MF
    
com.mentor.nucleus.help.bp.mc/META-INF/MANIFEST.MF
com.mentor.nucleus.help.bp.mc/about.html

Installer_MIP_MIMIC/
    build_installer_bp_linux.sh
Installer_MIP_MIMIC/
    build_installer_bp.sh
Installer_MIP_MIMIC/
    CLI.bat
Installer_MIP_MIMIC/
    CLI.sh
Installer_MIP_MIMIC/
    Launcher.bat
Installer_MIP_MIMIC/
    Launcher.sh
Installer_MIP_MIMIC/
    MSI_Director.java
Installer_MIP_MIMIC/
    MSI_DirectorLinux.java
Installer_MIP_MIMIC/
    post_install_script.bat
Installer_MIP_MIMIC/
    post_install_script.sh
Installer_MIP_MIMIC/
    splash.bmp
Installer_MIP_MIMIC/
    splash.xcf

utilities/build/
    create_release_functions.sh
</pre>
End
---

