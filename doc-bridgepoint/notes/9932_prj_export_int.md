---

This work is licensed under the Creative Commons CC0 License

---

# Dumping MASL project only produces prj file
### xtUML Project Implementation Note


### 1. Abstract

This note describes the fix for a user-reported issue with MASL-export.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9932](https://support.onefact.net/issues/9932) Headline issue.  
<a id="2.2"></a>2.2 [BridgePoint DEI #9612](https://support.onefact.net/issues/9612) Headline SR.    

### 3. Background

A user reports:  
```
When I dump a MASL project from inside the tool it only dumps out the ".prj" file 
and it does not dump of any of the masl terminator service files.

This is different behavior from what is observed in the round trip.

Does the round-trip use a different method for dumping out?
```
  
Furthermore, the user reports that the issue happens when the MASL project is 
imported via the BridgePoint command-line import tool.  However, performing project 
import via the GUI and then exporting the MASL project produces the desired `.tr` files.  

### 4. Requirements

4.1 `Export MASL Project` shall produce `.tr` files for port activities.    

### 5. Analysis 

5.1  The first step of investigation was to open the workspace created during the MASL
round trip process of SAC & SAC_PROC.  
5.1.1  We note that the component reference that should be assigned to the SAC domain is
not assigned.  The other two component references (INI and SOA) are unassigned too, but this
is expected since they do not exist.  
5.1.2  Obviously, if the SAC component reference is unassigned then there are no connected ports
between the project and domain and thus no way any terminator service action language would exist
or be output via the export process.  
 
5.2  The author used a modified version of the "BridgePoint CLI Import (OSX)" launch
configuration to do a `SAC_PROC` project import into an existing workspace that already 
had a `SAC` domain imported.  The modified program agruments for the launch are:
```
-os ${target.os} -ws ${target.ws} -arch ${target.arch} -nl ${target.nl} -consoleLog 
-pluginCustomization ${workspace_loc}/org.xtuml.bp.pkg/plugin_customization.ini 
-file /Users/kbrown/tmp/SAC_PROC/SAC_PROC.xtuml -targetProject p1 -deleteExisting -allowIPRs
``` 

5.2.1  This allowed the author to set breakpoints and perform inspection of the model data
at various points in the import process.  
5.2.1.1  We uncovered the fact that during the import the SAC domain is found and the component
reference is assigned properly.  
5.2.1.2  However, upon exit and re-open of the workspace the Component Reference is no longer
assigned to the SAC domain.  
5.2.1.3  This meant that the in-memory model data was being set up as expected but the changes
were not being persisted.   

5.3  The `masl_round_trip` tool uses the BridgePoint CLI "Launch" mode which avoids starting up
and shutting down many instances of eclipse and instead starts a single instance which can 
perform many actions.  This is the reason `masl_round_trip` was succeeding, because it was running
the pre-build step on the correct in-memory data.  

### 6. Work Required

6.1  `ImportHelper.resolveMASLProject()`  
6.1.1  At the beginning of this function, the flow starts a transaction by calling 
`TransactionManager.startTransactionsOnSelectedModelRoots(...)`.  As the function name suggests, the
transaction uses model roots that are part of the selection.  Via step debugging, we see that in the
current flow there are no model elements in the selection, thus, no transaction is created.  The code is
modified to add the `SystemModel_c` instance to the selection before making the call so that a transaction
is created.  

6.2  Change the CLI `ImportExecutor` to call `<SystemModel_c>.persistSelfAndChildren()` after the 
project or file is imported.  This will force persist the in-memory data before the application exits.  

### 7. Implementation Comments

7.1  During the CLI application shutdown process eclipse may throw exceptions like: 
```
org.eclipse.e4.core.di.InjectionException: org.eclipse.swt.SWTException: Invalid thread access
``` 
We researched a solution for this issue but turned up nothing.  We also attempted to catch and hide
the error, but again without success.  The problem is happening in eclipse code that is outside of BridgePoint
control.  We are calling the proper function `IWorkbench.close()` to exit the application.  No solution
to making this error go away was found.   

7.2  Removed obsolete and unused function `migrateSLDTs` from `ImportHelper.java`.  The function
has no implementation and is not called from anywhere.    

7.3  It is not clear why the resolution and assignment of component references inside the (now properly 
constructed) transaction in `ImportHelper` does not cause a persist of the in-memory data to occur. It could 
be that there is a race condition between persistence launched by a listener and the application shutdown 
is happening.  Whatever the cause, it is clear that making sure the CLI Import persists everything before
shutting down by making a direct invocation is a clear fix for this issue.  

### 8. Unit Test

8.1 Run the full `masl_round_trip` suite and verify success.    
* cd ~/git/models/masl/test
* edit `regression_test` to point at the correct BridgePoint installation on the `BPINSTALL=...` line
* ./regression_test -o /tmp/regout < all_tests
* __R__ The file diffs should all show 0

8.2 Run `masl_round_trip` on SAC_OOA and SAC_PROC and verify the `.tr` files are output for SAC_PROC.  
* cd ~/git/models/masl/test
* create a new file `sac_test` that only contains the text `../SAC/SAC_OOA ../SAC/SAC_PROC`
* ./regression_test -o /tmp/regout < sac_test
* __R__ The MASLFormatter may throw errors, but there are `.tr` files under /tmp/regout/1/SAC_PROC  

8.3 Verify UI export  
* Open the `/tmp/importwork` workspace created previously in 8.2  
* Open the p1 project in Navigator and delete the `masl/` folder if it exists
* In Model Explorer, expand p1/SAC_PROC and note that one component reference is assigned to SAC  
* Run `Export MASL Project`  
* __R__ Verify `.tr` files are created under the `SAC_PROC/masl/` tree  

### 9. User Documentation

None.  

### 10. Code Changes

Fork/Repository: keithbrown/bridgepoint   
Branch: 9932_masl_prj_export  

<pre>
doc-bridgepoint/notes/9932_prj_export_int.md
src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/BPCLIWorkbenchAdvisor.java
src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/ImportExecutor.java
src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/ImportHelper.java

</pre>

### End

