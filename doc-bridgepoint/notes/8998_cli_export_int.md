---

This work is licensed under the Creative Commons CC0 License

---

# MASL Command Line Export
### xtUML Project Implementation Note


1. Abstract
-----------
Users report that the command line xtUML to MASL process is not working properly.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8998](https://support.onefact.net/issues/8998) Headline Issue.  
<a id="2.2"></a>2.2 [BridgePoint DEI #8692](https://support.onefact.net/issues/8692) CLI export problem    
<a id="2.3"></a>2.3 [BridgePoint DEI #9000](https://support.onefact.net/issues/9000) MASL CLI Export test       

3. Background
-------------
A MASL user reported "The xtuml2masl tool can be used to export a MASL model via the command line."[[2.2]](#2.2)

4. Requirements
---------------
4.1 xtuml2masl shall work from the command line and generate MASL from and xtUML model without error  

5. Work Required
----------------
5.1  The ```ExportBuild.exportSystem()``` function has the ability to take a flag
  indicating if it should parse or not.  This work simply uses that flag (setting it to
  not parse).   
5.2  "CLI Build"   
5.2.1  Add new flag ```-doNotParse```   
5.2.2  This file is only passed downstream to the build chain if ```-prebuildOnly``` is 
  being used.   
5.3  xtumlmc_build   
5.3.1  The script is modified in the xtuml2masl function to not call the CLI Build command 
  on all the projects in the workspace, rather just the ones indicated on the command
  line via ```-i```.  In addition, use the new ```-doNotParse``` flag.   
```
foreach my $inproj (@inputs) {
  my $proj = basename($inproj);
  system( "$directory/CLI.sh Build -prebuildOnly -doNotParse -project $proj " );
}
```      
     
6. Implementation Comments
--------------------------
None.   

7. Unit Test
------------
7.1 Test - Captured as [[2.3]](#2.3)     
1. Convert the SAC_OOA example domain    
2. Create an xtUML project named SAC_OOA, import the model produced in step 1    
3. Convert via the command line with a command such as: ```$ ./xtuml2masl -v -i /tmp/maslworkspace/SAC_OOA -d SAC -o /tmp/maslworkspace/SAC_OOA/masl```    
4. Output should be similar to (Note: no errors seen).  Output masl in produced.    
```
Initializing MASL export...
Invoking BridgePoint pre-builder...
Starting CLI Build
Performing the build of project: SAC_OOA
Build complete.  Exiting.
cat /tmp/maslworkspace/SAC_OOA/gen/code_generation/a.xtuml | x2m -i/tmp/maslworkspace/SAC_OOA  -dSAC -k | masl -v -i/tmp/maslworkspace/SAC_OOA  -dSAC -o/tmp/maslworkspace/SAC_OOA/masl
103 ___ info ___ Starting domain.
104 ___ info ___ Done.
java -cp /tmp/bp586/tools/mc/bin//antlr-3.5.2-complete.jar:/tmp/bp586/tools/mc/bin//MASLParser.jar MaslFormatter -r -i /tmp/maslworkspace/SAC_OOA/masl/SAC.orig -o /tmp/maslworkspace/SAC_OOA/masl/SAC
Done.
```

8. User Documentation
---------------------
8.1  Update the masl2xtuml "man page".  Convert source to markdown
  and fix errors in the documentation.       
8.2  Update the xtuml2masl "man page".  Convert source to markdown
  and fix errors in the documentation.    
8.3  Update the Command Line Interface documentation.  Convert the
  source to markdown, update included image, remove old doc.  Fix
  link to the file now moved one level up in folder structure.      
    
9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 8998_cli_export   

<pre>
doc-bridgepoint/notes/8998_cli_export_int.md

org.xtuml.bp.cli/src/org/xtuml/bp/cli/Build.java
org.xtuml.bp.cli/src/org/xtuml/bp/cli/BuildWorkbenchAdvisor.java

org.xtuml.bp.doc/Reference/MASL/masl2xtuml/masl2xtuml.html
org.xtuml.bp.doc/Reference/MASL/masl2xtuml/masl2xtuml.md
org.xtuml.bp.doc/Reference/MASL/xtuml2masl/xtuml2masl.html
org.xtuml.bp.doc/Reference/MASL/xtuml2masl/xtuml2masl.md
org.xtuml.bp.doc/Reference/UserInterface/CommandLineInterface/
    CommandLineInterface.html
org.xtuml.bp.doc/Reference/UserInterface/CommandLineInterface/
    CommandLineInterface.md
org.xtuml.bp.doc/Reference/UserInterface/CommandLineInterface/debug_config.png
org.xtuml.bp.doc/Reference/UserInterface/CommandLineInterface/HTML/
    CommandLineInterface.htm
org.xtuml.bp.doc/Reference/UserInterface/CommandLineInterface/HTML/
    CommandLineInterface_files/image001.gif
org.xtuml.bp.doc/Reference/UserInterface/CommandLineInterface/HTML/
    CommandLineInterface_files/image002.gif
org.xtuml.bp.doc/Reference/UserInterface/CommandLineInterface/HTML/
    CommandLineInterface_files/image003.gif
org.xtuml.bp.doc/build.properties
org.xtuml.bp.doc/topics_Reference.xml

</pre>

Fork/Repository: keithbrown/mc   
Branch: 8998_cli_export    

<pre>
bin/xtumlmc_build
</pre>

End
---

