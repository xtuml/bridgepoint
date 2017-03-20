---

This work is licensed under the Creative Commons CC0 License

---

# Project terminator does not have to provide implementation for every domain terminator service
### xtUML Project Implementation Note


1. Abstract
-----------
A MASL project terminator does not have to provide an implementation for every domain terminator service. 
This issue is raised to implement support for this in BridgePoint.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9316](https://support.onefact.net/issues/9316)  
<a id="2.1.1"></a>2.1.1 [BridgePoint DEI #9311](https://support.onefact.net/issues/9311)  
This is the Service Pro issue associated with #9361.  
<a id="2.2"></a>2.2 [Design note associated with this issue](9316_project_terminator.dnt.md)   
<a id="2.3"></a>2.3 [MASL Conversion Guide](https://github.com/xtuml/bridgepoint/blob/master/src/org.xtuml.bp.doc/Reference/MASL/MASLConversionGuide/MASLConversionGuide.md)   

3. Background
-------------
See the design note [[2.2]](#2.2)  

4. Requirements
---------------
See the design note [[2.2]](#2.2)  

5. Work Required
----------------
See the design note [[2.2]](#2.2)  

6. Implementation Comments
--------------------------
none  

7. Unit Test
------------
See the design note [[2.2]](#2.2)  


8. User Documentation
---------------------
8.1 The MASL Conversion Guide [[2.3]](#2.3) is modified to describe this situation.   

9. Code Changes
---------------
Fork/Repository: rmulvey/models  
Branch: 9316_project_terminator  

<pre>
models/masl/test/9316_domain/*
models/masl/test/9316_proj/*
</pre>

Fork/Repository: rmulvey/mc 
Branch: 9316_project_terminator  

<pre>
bin/x2m b/bin/x2m
model/maslout/models/maslout/lib/xtuml2masl/maslout/maslout.xtuml 
</pre>


Fork/Repository: rmulvey/bridgepoint  
Branch: 9316_project_terminator  

<pre>
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/actions/
    PullSynchronizationChanges.java

org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/ImportHelper.java

org.xtuml.bp.io.mdl/src/org/xtuml/bp/io/mdl/wizards/ModelImportWizard.java



</pre>

End
---


