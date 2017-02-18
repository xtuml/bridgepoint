---

This work is licensed under the Creative Commons CC0 License

---

# MASL round trip utility
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the implementation of a tool to help users perform automated
MASL flow round trip testing.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9066](https://support.onefact.net/issues/9066) Headline issue.    
<a id="2.2"></a>2.2 [BridgePoint DEI #9083](https://support.onefact.net/issues/9083) CLI Single file import shall have the ability to create a project     
<a id="2.3"></a>2.3 [BridgePoint DEI #9038](https://support.onefact.net/issues/9038) Clear up problems with SAC model.       

3. Background
-------------
Testing the MASL flow consists of several parts:  
* Convert a MASL model
* Import the converted model into BridgePoint
* Export the model from BridgePoint back into MASL format
* Diff the original MASL data with the exported data to verify correctness of the flow  
  
BridgePoint developers and MASL users desire a means to perform this round trip
test automatically.  This will greatly aid everyone's ability to verify the 
correctness of the flow and perform testing when tools inside the flow change.   
 
4. Requirements
---------------
4.1  BridgePoint shall include a tool to automate round trip MASL testing.  

5. Work Required
----------------
5.1  ```masl_round_trip```  
5.1.1  Create a shell script that leverages the existing tool chain: ```masl2xtuml```, 
  ```CLI Import```, ```xtuml2masl```, and ```masldiff```.   
5.1.2  The script shall run the tools in the proper order.   

5.2  CLI Import   
5.2.1  The CLI Import handling for single-file models does not support automatic
  creation of a project to import into.  It requires a workspace and an xtUML 
  project to already exist.  
5.2.2  An issue is raised [[2.2]](#2.2) to provide this feature later.  
5.2.3  For now the solution to full automation is to create a workspace and an
  xtUML project inside.  Then zip up this workspace and save it off to the side.   
  The round trip script can then unzip the workspace clean for each domain/component
  it is processing and import into the pre-existing workspace.   

5.3  ```org.xtuml.bp.mctools```   
5.3.1  Update the mc packaging plugin to include the ```masl_round_trip``` script
  and default workspace.   
   
6. Implementation Comments
--------------------------
6.1  Limitations  
6.1.1  The tool does not support MASL Projects at this time.  The issue is that
  it currently re-creates (re-unzips) the clean workspace for each input model. This 
  makes it impossible for a MASL Project to have component references to other 
  projects.  If or when BridgePoint adds the ability to create a project on single 
  file import [[2.2]](#2.2), then the issue becomes that we must have the ability 
  to turn on inter-project references on the new project.   
  
7. Unit Test
------------
7.1 Execute the tool and verify results:  
```
/opt/xtuml/BridgePoint6/tools/mc/bin/masl_round_trip -d ~/git/models/masl/SAC/SAC_OOA/ ~/git/masl/examples/petrol/PSC_OOA/ -o /tmp/difftest
```  
At the time of this writing, the SAC model mostly shows diffs because of an extra
newline at the end of some files.  There is also a diff on two types.  A separate
issue [[2.3]](#2.3) exists to clean up the SAC model.  The diffs reports will decrease
as that issue progresses.     

The PSC model shows many diffs.  Most notably is that the PSC orignal MASL model
uses keyletter naming of files and the BP MASL export no longer uses keyletter
file naming.   

8. User Documentation
---------------------
None.   

9. Code Changes
---------------
Fork/Repository: keithbrown/xtuml   
Branch: 9066_masl_round_trip   

<pre>
doc-bridgepoint/notes/9066_masl_round_trip_int.md

org.xtuml.bp.mctools/linux.all/tools/mc/bin/.gitignore
org.xtuml.bp.mctools/mac.all/tools/mc/bin/.gitignore
org.xtuml.bp.mctools/pom.xml

</pre>

Fork/Repository: keithbrown/mc   
Branch: 9066_masl_round_trip   

<pre>

</pre>

End
---

