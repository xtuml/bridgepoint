---

This work is licensed under the Creative Commons CC0 License

---

# Docgen is failing 
### xtUML Project Implementation Note


### 1. Abstract

This note describes the changes made to resolve problems with Document Generation.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9055](https://support.onefact.net/issues/9055) Headline issue    

### 3. Background

Document generation is a feature of BridgePoint that processes a model and creates and HTML 
document from the model description data and screen captures of the canvases.  This process
relies on a custom ```docgen``` model compiler, eclipse-based BridgePoint tooling, and a common 
document processing tool known as xsltproc.   

BridgePoint ships with xsltproc binaries for Windows and Linux.  We found that the version shipped
for Linux works for Ubuntu 14 but not Ubuntu 16.  Also, BridgePoint recently introduced MacOS 
support and Document Generation does not work on Mac because it can't find xsltproc (which is
included in Mac distributions).  

### 4. Requirements

4.1 Document Generation shall work on Windows, Linux, and Mac.    

### 5. Work Required

5.1  Modify the docgen packaging to put the docgen binary in ```tools/docgen``` rather than ```tools/mc/bin```  

5.2  Update the document generation plugin to find ```xsltproc``` on the user's system and use it if it
  is not found in the ```tools/docgen``` folder.  

### 6. Implementation Comments

6.1 The ```build/extra_files``` folder housed pre-built binaries that were pulled into
  the build as part of the packaging process.  These are no longer needed as the files 
  are now used out of ```mc/bin``` or built by the server for every server build run.   

### 7. Unit Test

7.1  Run document generation on Microwave Oven for each OS: Windows, Ubuntu 14, Ubuntu 16, Mac

### 8. User Documentation

Update the FAQ webpage with information about installing xsltproc. 

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint  
Branch: 9055_docgen_fixes

<pre>
 doc-bridgepoint/notes/9055_docgen_fixes.int.md
 releng/org.xtuml.bp.mctools/pom.xml
 src/org.xtuml.bp.docgen/src/org/xtuml/bp/docgen/generator/Generator.java
</pre>

Fork/Repository: keithbrown/packaging  
Branch: 9055_docgen_fixes

<pre>
 build/README.md                                                      |   2 +-
 build/extra_files/.project                                           |  11 -----------
 build/extra_files/docgen                                             | Bin 1295014 -> 0 bytes
 build/extra_files/docgen.exe                                         | Bin 651939 -> 0 bytes
 build/extra_files/gen_erate.exe                                      | Bin 5732889 -> 0 bytes
 build/extra_files/gen_erate.pyz                                      | Bin 312484 -> 0 bytes
 build/extra_files/m2x                                                | Bin 646585 -> 0 bytes
 build/extra_files/masl                                               | Bin 239623 -> 0 bytes
 build/extra_files/mc3020_doc.zip                                     | Bin 461037 -> 0 bytes
 build/extra_files/mcmc                                               | Bin 1856419 -> 0 bytes
 build/extra_files/mcmc.exe                                           | Bin 1175308 -> 0 bytes
 build/extra_files/mcmc64                                             | Bin 2170101 -> 0 bytes
 build/extra_files/x2m                                                | Bin 680533 -> 0 bytes
 build/extra_files/xtumlmc_build.exe                                  | Bin 3994912 -> 0 bytes
 features/org.xtuml.bp.docgen.parent/pom.xml                          |   5 +++++
 features/org.xtuml.bp.docgen/build.properties                        |   3 +++
 features/org.xtuml.bp.docgen/linux.all/tools/docgen/.gitignore       |   1 +
 features/org.xtuml.bp.docgen/linux.all/tools/docgen/docbook/xsltproc | Bin 24960 -> 0 bytes
 features/org.xtuml.bp.docgen/mac.all/tools/docgen/.gitignore         |   1 +
 features/org.xtuml.bp.docgen/pom.xml                                 |  75 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 features/org.xtuml.bp.docgen/win32.all/tools/docgen/.gitignore       |   1 +
</pre>


### End

