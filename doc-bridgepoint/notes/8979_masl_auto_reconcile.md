---

This work is licensed under the Creative Commons CC0 License

---

# Automatically run graphics reconciliation during MASL model import

### xtUML Project Implementation Note

1. Abstract
-----------
This issue is raised to perform graphics reconciliation automatically when 
when a masl model is imported.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8979](https://support.onefact.net/issues/8979) 
Automatically run graphics reconciliation during MASL model import.  
<a id="2.2"></a>2.2 [BridgePoint DEI #8986](https://support.onefact.net/issues/8986) Annotate MASL models to communicate to Import.  
<a id="2.3"></a>2.3 [BridgePoint DEI #8801](https://support.onefact.net/issues/8801) MASL round trip test.  

3. Background
-------------
During the process of converting a masl model to xtuml using m2x, m2x 
adds information to the description field of packages to indicate if
a masl domain model or project model is being imported [[2.1]](#2.1). This issue
is raised to use that information as a flag that to cause graphics 
reconciliation to occur automatically when such a model is imported.


4. Requirements
---------------
4.1 If a package description contains the one of the following strings then
graphics reconciliation shall be run after the model is imported.    
4.1.1 masl_domain  
4.1.2 masl_project  

4.2 If a package description contains the string masl_project then after import
"Export MASL Project" shall be called on the package.  

4.3 If a package description contains the string masl_domain then after import
"Export MASL Domains" shall be called on the package.  
 
5. Work Required
----------------
5.1 Modify ImportHelper.java::resolveMASLproject() to keep track of is a 
masl_project or masl_domain string is seen in a package.

5.2 Modify ModelImportWizard.java:: importModel() to use the flags modified 
by ImportHelper::resolveMASLproject() to determine if graphics reconciliation
and Export MASL {Domains | Project} need to be called and if so do it.  

6. Implementation Comments
--------------------------
6.1 After implementation a cyclic build error was encountered caused by the 
new dependency on bp.x2m from bp.io.mdl. 

On the local build the errors look like this:   
```
A cycle was detected in the build path of project 'org.xtuml.bp.cdt'. 

The cycle consists of projects {org.xtuml.bp.io.mdl, org.xtuml.bp.x2m, 
org.xtuml.bp.mc, org.xtuml.bp.utilities, org.xtuml.bp.cdt, org.xtuml.bp.mc.c.source, 
org.xtuml.bp.mc.cpp.source, org.xtuml.bp.mc.systemc.source, 
org.xtuml.bp.cli, org.xtuml.bp.model.compare, org.xtuml.bp.mc.java.source, 
org.xtuml.bp.compare, org.xtuml.bp.docgen, org.xtuml.bp.internal.tools, 
org.xtuml.bp.mc.none, org.xtuml.bp.welcome}
```

On the build server the error looks like this:  
```
[ERROR] The projects in the reactor contain a cyclic reference: Edge between 
Vertex{label=org.xtuml.bp:org.xtuml.bp.mc.c.source:5.8.6} and 
Vertex{label=org.xtuml.bp:org.xtuml.bp.mc:5.8.6} introduces to cycle in the 
graph org.xtuml.bp:org.xtuml.bp.mc:5.8.6 --> org.xtuml.bp:org.xtuml.bp.mc.c.source:5.8.6 --> org.xtuml.bp:org.xtuml.bp.mc:5.8.6 -> [Help 1]
```

6.1.1 To work around this problem in the local build and test the changes made,
I modified the following setting and changed it from its default of "error" to 
"warning". 
```
Windows -> Preferences -> Java-> Compiler -> Building -> Circular Dependencies
```
6.1.2 The change called out in 6.1.1 does not work as-is on the build 
server. It was decided to simply comment out the calls to 
Genererator.Generator.exportProject in io/mdl/wizards/ModelImportWizard.java
and the import specification in that same file to 
import org.xtuml.bp.x2m.generator.Generator that causes the cyclic dependency.  
Of course this means only automatic graphics reconciliation will occur. The
"Export MASL {Domains | Project} will still need to be done manually until this
export issue is resolved. The need for this functionality may be deprecated soon
anyway.  
  
7. Unit Test
------------
7.1 Use the Convert and Import section of [[2.3]](#2.3) to test this.  

8. User Documentation
---------------------
The masl user manual was updated and the instruction that told the user to 
manually perform graphics reconciliation was removed. If 6.1 is resolved then
that same document will need to be updated to remove the manully calling of
MASL Export.  

9. Code Changes
---------------
Fork/Repository: rmulvey
Branch: 8979_automatically_reconcile_on_masl_import

<pre>

doc-bridgepoint/> notes/8979_masl_auto_reconcile.md

org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/ImportHelper.java

org.xtuml.bp.io.mdl/> src/org/xtuml/bp/io/mdl/wizards/ModelImportWizard.java


</pre>

End
---

