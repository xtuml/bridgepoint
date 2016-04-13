---

This work is licensed under the Creative Commons CC0 License

---

# Convert example models to C source MC (8397)
# Remove unused MC plugins (8396)
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the work done to get rid of the C binary model compiler and
to update the example projects to stop referring to the binary MC.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8397](https://support.onefact.net/issues/8397) - Headline issue  
<a id="2.2"></a>2.3 [BridgePoint DEI #8396](https://support.onefact.net/issues/8396) - Headline issue  
<a id="2.3"></a>2.3 [BridgePoint DEI #8395](https://support.onefact.net/issues/8395) - Spring cleaning  

3. Background
-------------
In the Spring of 2016, the xtUML team took a pass at doing some "spring 
cleaning".  The parent issue for this work is [[2.3]](#2.3).  Part of that work 
was to remove the C binary MC from BridgePoint.  Since BridgePoint is now 
open source and with the advent of python-based generator, we have no reason to 
lock the archetypes. 

4. Requirements
---------------
4.1  Remove `org.xtuml.bp.mc.c.binary` plug-in from BridgePoint.  

5. Work Required
----------------

6. Implementation Comments
--------------------------

7. Unit Test
------------

8. User Documentation
---------------------
8.1  Update Developer Getting Started guide to configure the C source MC for 
  building BridgePoint rather than C binary MC.  
8.2   

9. Code Changes
---------------
Repository: <repo>  
Branch name: < enter your branch name here >  

<pre>

< Put the file list here >

</pre>

End
---

