---

This work is licensed under the Creative Commons CC0 License

---

# Add timestamps to BridgePoint Build
### xtUML Project Implementation Note


### 1. Abstract

This note describes the work to add more timestamps into the BridgePoint build
and captures a first watermark.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9535](https://support.onefact.net/issues/9535)   

### 3. Background

Building ```org.xtuml.bp.core``` takes a long time.  We are interested in seeing
how long each of the big segments of the build takes.  As we expect some im-
provements to the build time soon because of enhancements to generator, we want 
to be able to quantitatively compare the old and new data.  

### 4. Requirements

4.1 The BridgePoint build process shall be augmented with additional timestamps.    

### 5. Work Required

5.1 Add an ant macro to print out a current timestamp  
5.2 Use the new macro in MC-Java and bp.core ant build files: ```<echotimestamp />```    

### 6. Implementation Comments

6.1  Current ```org.xtuml.bp.core``` build log with new timestamps on Keith's
  2017 Macbook Pro (16 GB RAM).  
    
  Summary:   
  
|  Target   |  Duration  |   
|-----------|------------|  
| java 1    |  11 mins   |  
| java 2    |  2 mins    |  
| java 3    |  50 secs   |  
| java 4    | 2 min 15 secs |  
| cme_menus | 3 min 20 secs |  
| inspector.java | 20 secs |  
| cell_modifiers | 15 secs |  
| generate_supertype_suptype_util | 10 secs |  
| action_language_description_util | 10 secs |  
| generate_rto_util | 15 secs |  
  
Console output:  
```
Buildfile: /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/generate.xml

sql.depends:
        [echo] Plugins home: /Users/kbrown/xtuml/m625.2017-04-26-1158/BridgePoint.app/Contents/Eclipse/plugins/org.apache.ant_1.9.6.v201510161327/../..

sql:
        [echo] Generating ooaofooa.sql ...
        [copy] Copying 1 file to /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/gen/code_generation
      [delete] Deleting: /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/gen/code_generation/ooaofooa.xtuml

java.depends:

java1:
        [echo] Generating java files 1...

...
sql:
        [echo] Generating file_io.sql ...
        [copy] Copying 1 file to /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.io.core/models/org.xtuml.bp.io.core/file_io
      [delete] Deleting: /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.io.core/models/org.xtuml.bp.io.core/file_io/Globals.xtuml

...
generate.schema.absolute:
        [exec] schema_gen.arc: 407:  INFO:  File 'file_io_schema.sql' CREATED

generate.schema.relative:

generate-with-file_io.java:
        [echo] 2017-05-09 17:38:19
        [echo] Building from: gen/code_generation/ooaofooa-1.sql
        [copy] Copying 1 file to /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/gen/code_generation
      [delete] Deleting: /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/gen/code_generation/ooaofooa-1.xtuml
        [exec] java.arc: 514:  INFO:  Time is: Tue May  9 17:38:35 2017
        [exec] translate_oal.inc: 40:  INFO:  ************** generate literal booleans            *****************
        ...
        [exec] java.arc: 2109:  INFO:  File '/Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/CommunicationLink_c.java' CREATED
        [exec] java.arc: 2140:  INFO:  File '/Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/CommunicationLinkActionFilter.java' CREATED
        [echo] 2017-05-09 17:49:18

java2:
        [echo] Generating java files 2...

...
generate-with-file_io.java:
        [echo] 2017-05-09 17:49:18
        [echo] Building from: gen/code_generation/ooaofooa-2.sql
        [copy] Copying 1 file to /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/gen/code_generation
      [delete] Deleting: /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/gen/code_generation/ooaofooa-2.xtuml
        [exec] java.arc: 514:  INFO:  Time is: Tue May  9 17:49:28 2017
        [exec] translate_oal.inc: 40:  INFO:  ************** generate literal booleans            *****************
        ...
        [exec] java.arc: 2017:  INFO:  convertToInstance written as native code
        [exec] java.arc: 2109:  INFO:  File '/Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/UseCaseParticipant_c.java' CREATED
        [echo] 2017-05-09 17:51:19

java3:
        [echo] Generating java files 3...

...
generate-with-file_io.java:
        [echo] 2017-05-09 17:51:19
        [echo] Building from: gen/code_generation/ooaofooa-3.sql
        [copy] Copying 1 file to /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/gen/code_generation
      [delete] Deleting: /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/gen/code_generation/ooaofooa-3.xtuml
        [exec] java.arc: 514:  INFO:  Time is: Tue May  9 17:51:27 2017
        [exec] translate_oal.inc: 40:  INFO:  ************** generate literal booleans            *****************
        ...
        [exec] java.arc: 636:  INFO:   Translating Object:   Select
        [exec] java.arc: 2109:  INFO:  File '/Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/Select_c.java' CREATED
        [echo] 2017-05-09 17:52:09

java4:
        [echo] Generating java files 4...

...
generate-with-file_io.java:
        [echo] 2017-05-09 17:52:09
        [echo] Building from: gen/code_generation/ooaofooa-4.sql
        [copy] Copying 1 file to /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/gen/code_generation
      [delete] Deleting: /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/gen/code_generation/ooaofooa-4.xtuml
        [exec] java.arc: 514:  INFO:  Time is: Tue May  9 17:52:21 2017
        [exec] translate_oal.inc: 40:  INFO:  ************** generate literal booleans            *****************
        ...
        [exec] java.arc: 2403:  INFO:   Function EP_PKG_AssignAsPackageReference
        [exec] java.arc: 2516:  INFO:  File '/Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/Ooaofooa.java' CREATED
        [echo] 2017-05-09 17:54:25

...
cme:
        [echo] Generating context_menu.sql ...
        [copy] Copying 1 file to /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/models/org.xtuml.bp.core/context_menu
      [delete] Deleting: /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/models/org.xtuml.bp.core/context_menu/Globals.xtuml

...
generate.schema.absolute:
        [exec] schema_gen.arc: 407:  INFO:  File 'context_menu_schema.sql' CREATED

...
inspector.sql:
        [echo] Generating ooaofooa_hierarchy.sql ...
        [copy] Copying 1 file to /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa_hierarchy
      [delete] Deleting: /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa_hierarchy/Globals.xtuml

inspector.schema.depends:

inspector.schema:
        [echo] Generating ooaofooa_hierarchy_schema.sql ...

...
generate.schema.absolute:
        [exec] schema_gen.arc: 407:  INFO:  File 'ooaofooa_hierarchy_schema.sql' CREATED

...
cme_sql:
        [echo] Generating ooaofooa.sql ...

cme_menus.depends:

cme_menus:
        [echo] Generating Action Classes ...
        [echo] 2017-05-09 17:54:29
        [exec] wfl_pop.arc: 37:  INFO:  WARNING: Function S_SYS_Delete has a native implementation; nothing done
        [exec] translate_oal.inc: 40:  INFO:  ************** generate literal booleans            *****************
        ...
        [exec] wizard.arc: 63:  INFO:  File 'src/org/xtuml/bp/core/ui/CommunicationSignalFormalizeOnMSG_AMWizardPage2.java' CREATED
        [exec] quick_fix.arc: 99:  INFO:  File 'src/org/xtuml/bp/core/common/BPQuickFix.java' CREATED
        [echo] 2017-05-09 17:57:51

sql.depends:
        [echo] Plugins home: /Users/kbrown/xtuml/m625.2017-04-26-1158/BridgePoint.app/Contents/Eclipse/plugins/org.apache.ant_1.9.6.v201510161327/../..

...
inspector.java:
        [echo] Generating src/org/xtuml/bp/core/inspector/SystemInspector.java ...
        [echo] 2017-05-09 17:57:52
        [exec] create_core_plugin_class.arc: 1260:  INFO:  File 'src/org/xtuml/bp/core/CorePlugin.java' CREATED
        ...
        [exec] create_metadata_elements_sorters.arc: 372:  INFO:  File 'src/org/xtuml/bp/core/sorter/MetadataSortingManager.java' CREATED
        [exec] create_dependson.arc: 91:  INFO:  File 'src/org/xtuml/bp/core/inspector/PropertyDependsOn.java' CREATED
        [echo] 2017-05-09 17:58:12
       [touch] Creating /Users/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/inspector/SystemInspector.java

cell_modifier.depends:

cell_modifiers:
        [echo] Generating src/org/xtuml/bp/core/ui/cells/CellModifierProvider.java ...
        [echo] 2017-05-09 17:58:12
        [exec] generate_cell_modifiers.arc: 559:  INFO:  File 'src/org/xtuml/bp/core/ui/cells/providers/SystemModelCellProvider.java' CREATED
        ...
        [exec] generate_cell_modifiers.arc: 563:  INFO:  File 'src/org/xtuml/bp/core/ui/cells/CellModifierProvider.java' CREATED
        [echo] 2017-05-09 17:58:27

generate_supertype_subtype_util.depends:

generate_supertype_subtype_util:
        [echo] Generating src/org/xtuml/bp/core/util/SupertypeSubtypeUtil.java ...
        [echo] 2017-05-09 17:58:27
        [exec] generate_supertype_subtype_util.arc: 122:  INFO:  File 'src/org/xtuml/bp/core/util/SupertypeSubtypeUtil.java' CREATED
        [echo] 2017-05-09 17:58:39

action_language_description_util.depends:

action_language_description_util:
        [echo] Generating src/org/xtuml/bp/core/util/ActionLanguageDescriptionUtil.java ...
        [echo] 2017-05-09 17:58:39
        [exec] action_language_description_util.arc: 167:  INFO:  File 'src/org/xtuml/bp/core/util/ActionLanguageDescriptionUtil.java' CREATED
        [echo] 2017-05-09 17:58:51

generate_rto_util.depends:

generate_rto_util:
        [echo] Generating src/org/xtuml/bp/core/util/RTOUtil.java ...
        [echo] 2017-05-09 17:58:51
        [exec] generate_rto_util.arc: 118:  INFO:  File 'src/org/xtuml/bp/core/util/RTOUtil.java' CREATED
        [echo] 2017-05-09 17:59:04

...
BUILD SUCCESSFUL
Total time: 20 minutes 56 seconds
```  

### 7. Unit Test

7.1  Run build of ```org.xtuml.bp.core```, see new timestamps.  

### 8. User Documentation

None.   

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint  
Branch: 9535_timestamp  

<pre>

doc-bridgepoint/notes/9535_timestamp_int.md
src/MC-Java/build.xml
src/MC-Java/common.xml
src/org.xtuml.bp.core/generate.xml

</pre>

### End

