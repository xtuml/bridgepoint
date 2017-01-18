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
<a id="2.2"></a>2.2 [BridgePoint DEI #8692](https://support.onefact.net/issues/869) CLI export problem    

3. Background
-------------
A MASL user reported "The xtuml2masl tool can be used to export a MASL model via the command line."[[2.2]](#2.2)

4. Requirements
---------------
4.1 xtuml2masl shall work from the command line and generate MASL from and xtUML model without error  

5. Work Required
----------------

6. Implementation Comments
--------------------------

7. Unit Test
------------
7.1 Test
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

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 8998_cli_export

<pre>

</pre>

Fork/Repository: keithbrown/mc   
Branch: 8998_cli_export

<pre>
bin/xtumlmc_build
</pre>

End
---

