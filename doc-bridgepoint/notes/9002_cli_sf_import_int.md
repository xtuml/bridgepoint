---

This work is licensed under the Creative Commons CC0 License

---

# Add Single File Command Line Import
### xtUML Project Implementation Note


1. Abstract
-----------
BridgePoint has long supported importing projects using the ```CLI.[bat|sh]``` 
script.  This note describes the work done to add support for importing a 
single xtUML file.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9002](https://support.onefact.net/issues/9002) Headline issue     
<a id="2.2"></a>2.2 [BridgePoint DEI #9007](https://support.onefact.net/issues/9007) Marking Editor Help Text      
<a id="2.3"></a>2.3 [BridgePoint DEI #9010](https://support.onefact.net/issues/9010) Marking Editor Warning Messages      
<a id="2.4"></a>2.4 [BridgePoint DEI #9012](https://support.onefact.net/issues/9012) Unit test for CLI single file import         

3. Background
-------------
In order to perform a MASL round-trip test via the command line, the CLI tool
needed to be updated to support import of an xtUML file into a project.    

4. Requirements
---------------
4.1 Import an xtUML file via the command line into an existing project in a 
  workspace.     

5. Work Required
----------------
5.1  Add the new arguments to ```Import.java``` with descriptive help messages.    
5.2  In ```ImportWorkbenchAdvisor.java``` handle the new arguments for importing
  a given file.  Add new ```importFile()``` function to encapsulate the file 
  import process separately from project import.   
5.3  In the ```ProjectUtilities.java``` class, us a utility function instead of 
  calling ```readAndDispatch()``` loops directly.  The utility function is safer
  because it included additional checks against the ```Display``` in case it is 
  null or disposed.    
    
6. Implementation Comments
--------------------------
6.1  Issue [[2.2]](#2.2) called out some fixes to the Marking Editor documentation
  which are fixed in this development branch.   
6.2  Issue [[2.3]](#2.3) identified a help message that is desired for the 
  Marking Editor tool that warns if the features.mark file is incomplete.  This
  issue is addressed in this branch.      
  
7. Unit Test
------------
7.1  Functional Test  - captured as issue 9012   
* Create a workspace
* Create an xtUML project ```sac_ooa```  
* Exit BridgePoint   
* Edit ```CLI.sh``` WORKSPACE variable to have the path of the workspace created
  above.
* Convert MASL domain SAC_OOA to xtUML
* Run the Import with a command like ```$ ./CLI.sh Import -file /tmp/sac/SAC_OOA/SAC_OOA.xtuml -targetProject sac_ooa```
* __R__ No errors are produced  
* Open BridgePoint
* The SAC model is found under sac_ooa

8. User Documentation
---------------------
8.1  Update the CLI user documentation to call out the new arguments for Import.   
  
9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 9002_cli_sf_import   

<pre>
doc-bridgepoint/notes/9002_cli_sf_import_int.md

org.xtuml.bp.cli/META-INF/MANIFEST.MF
org.xtuml.bp.cli/src/org/xtuml/bp/cli/Import.java
org.xtuml.bp.cli/src/org/xtuml/bp/cli/ImportWorkbenchAdvisor.java

org.xtuml.bp.doc/Reference/UserInterface/CommandLineInterface/
    CommandLineInterface.html
org.xtuml.bp.doc/Reference/UserInterface/CommandLineInterface/
    CommandLineInterface.md
org.xtuml.bp.doc/Reference/UserInterface/MarkingEditor/MarkingEditor.html
org.xtuml.bp.doc/Reference/UserInterface/MarkingEditor/MarkingEditor.md

org.xtuml.bp.ui.marking/src/org/xtuml/bp/ui/marking/MarkingData.java

org.xtuml.bp.utilities/src/org/xtuml/bp/utilities/ui/ProjectUtilities.java



</pre>

End
---

