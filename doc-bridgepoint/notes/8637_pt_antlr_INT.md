---

This work is licensed under the Creative Commons CC0 License

---

# Move pt_antlr from xtuml/bridgepoint to xtuml/pt_antlr
### xtUML Project Implementation Note


1. Abstract
-----------
BridgePoint build relies on a customized version of ANTLR 2.7 called pt_antlr. 
This library is undesireable to the eclipse foundation for license reasons.  The
xtUML team has decided to remove the library from the source code to be submitted
to the eclipse foundation for papyrus-xtuml.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8637](https://support.onefact.net/issues/8637) Headline issue  

3. Background
-------------
See Abstract.

4. Requirements
---------------
4.1 ```pt_antlr``` shall be treated as a build-time library requirement and not 
  part of the BridgePoint source code.   
4.2 ```org.xtuml.bp.als``` shall no longer include ```lib/antlr.jar```   

5. Work Required
----------------
5.1  Create new repository ```xtuml/pt_antlr```   
5.2  Move ```xtuml/bridgepoint/src-other/pt_antlr``` to ```xtuml/pt_antlr/pt_antlr```   
5.3  Delete ```antlr.jar``` from ```xtuml/bridgepoint/src/org.xtuml.bp.als/lib/```   
5.4  Change build scripts accordingly to pull the ```antlr.jar``` from the ```pt_antlr```
  repository and put it into ```xtuml/bridgepoint/src/org.xtuml.bp.als/lib/``` before 
  BridgePoint build is started.  This requires updating the script that pulls down
  the correct git repositories as well as the scripts that shuffles files around
  into the right places for building.     
5.5  Update the Developer Getting Started instructions to instruct the developer
  to put the ```antlr.jar``` into the right spot   
5.6  Put a ```.gitignore``` into ```org.xtuml.bp.als/lib/``` to ignore ```antlr.jar```, 
  which will also serve to keep the ```lib/ dir``` from disappearing from 
  revision control due to being empty.   
5.6.1  Add a README in that folder that says tells the developer to see the 
  getting started guide instructions about ```pt_antlr```   

6. Implementation Comments
--------------------------
None.    

7. Unit Test
------------
7.1 After promotion, build BridgePoint on the build server and verify success.    

8. User Documentation
---------------------
Updated the instructional documents [Developer's Getting Started Guide](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md)
and [Building BridgePoint From The Command Line](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Building%20BridgePoint%20From%20The%20Command%20Line.md).   

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 8637_pt_antlr   

<pre>

doc-bridgepoint/notes/8637_pt_antlr_INT.md
doc-bridgepoint/process/Building BridgePoint From The Command Line.md
doc-bridgepoint/process/Developer Getting Started Guide.md

org.xtuml.bp.als/lib/.gitignore
org.xtuml.bp.als/lib/README.md

utilities/build/configure_external_dependencies.sh
utilities/build/init_git_repositories.sh

pt_antlr/ (deleted)

</pre>

End
---

