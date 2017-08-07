---

This work is licensed under the Creative Commons CC0 License

---

# Saab- 31 : Create folder gen/code_generation if it doesn't exist
### xtUML Project Implementation Note


### 1. Abstract

The prebuilder assumes that the folder ```gen/code_generation``` exists. Instead, it 
shall create the folder when it is missing before trying to save its output.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9568](https://support.onefact.net/issues/9568) Headline issue.    

### 3. Background

The pre-builder must assure the necessary folders exist for saving the output file
before it attempts to write the file.  Otherwise the file write fails.  

### 4. Requirements

4.1 The pre-builder shall create ```<project>/gen/``` if it does not exist.    
4.2 The pre-builder shall create ```<project>/gen/code_generation``` if it does not exist.    

### 5. Work Required

5.1 When creating the ```code_generation/``` folder, use Java's ```mkdirs(...)``` rather
  than ```mkdir(...)``` because the former will create missing parent folders as needed. This
  guarantees the necessary folder tree will exist before we attempt to write to it.  

### 6. Implementation Comments

None.  

### 7. Unit Test

Two new JUnit tests are added to the bp.welcome.test suite.

7.1 Test without ```gen/```
  # Create MicrowaveOven project
  # Delete the ```gen/``` folder
  # Verify the ```gen/``` folder is really gone
  # Run Build Project
  # Verify that ```gen/code_generation/MicrowaveOven.sql``` exists  

7.2 Test without ```gen/code_generation/```
  # Create MicrowaveOven project
  # Delete the ```gen/code_generation/``` folder
  # Verify the ```gen/code_generation/``` folder is really gone
  # Run Build Project
  # Verify that ```gen/code_generation/MicrowaveOven.sql``` exists  

### 8. User Documentation

None.  

### 9. Code Changes

Fork/Repository: rmulvey/bridgepoint
Branch: 9568_code_gen_dir 

<pre>
doc-bridgepoint/notes/9568_code_gen_folder_int.md
src/org.xtuml.bp.mc/src/org/xtuml/bp/mc/AbstractExportBuilder.java
</pre>

Fork/Repository: rmulvey/bptest
Branch: 9568_code_gen_dir 

<pre>
src/org.xtuml.bp.test/src/org/xtuml/bp/test/common/BaseTest.java   |  12 ++-
src/org.xtuml.bp.test/src/org/xtuml/bp/test/common/TestingUtilities.java
src/org.xtuml.bp.welcome.test/META-INF/MANIFEST.MF                 |   3 +-
 .../src/org/xtuml/bp/welcome/test/WelcomePageTest.java             | 126 ++++++++++++++++++++++++------
 .../src/org/xtuml/bp/welcome/test/WelcomePageTestGPS.java          |  13 ++-
 .../src/org/xtuml/bp/welcome/test/WelcomeTestSuite.java            |  22 +-----
 

</pre>

### End

