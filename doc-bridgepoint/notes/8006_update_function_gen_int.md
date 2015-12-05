---

This work is licensed under the Creative Commons CC0 License

---

# Update Function Generator
### xtUML Project Implementation Note


1. Abstract
-----------
The "Function Generator" is a small utility that reads a specially formatted text 
file and creates functions inside of a xtUML model.  The xtUML development team
uses it during the process of creating a model-based model compiler [2].  

2. Document References
----------------------
[1] [BridgePoint DEI #8006](https://support.onefact.net/redmine/issues/8006)  
[2] [HOWTO Create MCMC](https://github.com/xtuml/mc/blob/master/model/com.mentor.nucleus.bp.core/gen/HOWTO%20Create%20mcmc-docgen.txt)    
[3] [Function Generator JAR file](https://github.com/xtuml/mc/tree/master/etc)   
[4] [JD-GUI Java Decompiler](https://github.com/java-decompiler/jd-gui/)   

3. Background
-------------
The Function Generator utility has not been updated for a very long time.  It is still
named ```com.mentor.nucleus.bp.core.function.gen``` and the internal dependencies are still
to ```com.mentor.*``` plug-ins instead of ```org.xtuml.*``` plug-ins.  Thus, it does not run inside 
BridgePoint 5.x.

4. Requirements
---------------
4.1  Function generator shall run inside BridgePoint 5  

5. Work Required
----------------
5.1  The current compiled JAR file for the function generator plug-in is available at [3]      
5.2  Travis and Keith looked for the source code for the plug-in in the xtuml/bridgepoint, xtuml/mc, 
  and travislondon/bridgepoint git repositories as well as the archived CVS repository and did not find it.   
5.3  We used the Java Decompiler tool JD-GUI [4] to decompile the source of the JAR file.  There is just a
  single Java file that performs the import and function generation (```GenerateFunctionsFromList.java```).        
5.4  The java source file was put into the ```org.xtuml.bp.utilities``` plug-in and updated to change 
  dependencies from ```com.mentor.*``` to ```org.xtuml.*```.   
5.5  The ```org.xtuml.bp.utilities/plugin.xml``` file was edited to add the pop-up menu entry for the tool
  under the "BridgePoint Utilities" context menu.   
  
6. Implementation Comments
--------------------------
6.1  In the development branch, the author originally created a new plug-in ```org.xtuml.bp.function.gen```
  as the home for the tool, but then decided to move it into the ```org.xtuml.bp.utilities``` plug-in.   
  
7. Unit Test
------------
7.1  The ```o.oal``` file attached to this issue [1] shall properly create functions inside a model when
  importing into a package.   
  
8. Code Changes
---------------
Branch name: 8006_update_function_gen on keithbrown/bridgepoint repository

<pre>
 doc-bridgepoint/notes/8006_update_function_gen_int.md 
 src/org.xtuml.bp.utilities/plugin.xml
 src/org.xtuml.bp.utilities/src/org/xtuml/bp/utilities/actions/GenerateFunctionsFromList.java
</pre>

End
---

