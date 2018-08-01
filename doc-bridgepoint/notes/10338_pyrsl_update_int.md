---

This work is licensed under the Creative Commons CC0 License

---

# Update to pyrsl v2.1.0 
### xtUML Project Implementation Note


### 1. Abstract

This note describes the work performed to update the BridgePoint
build to work with pyrsl 2.0.1.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10338](https://support.onefact.net/issues/10338) Headline issue  
<a id="2.2"></a>2.2 [BridgePoint DEI #9481](https://support.onefact.net/issues/9481) Update BridgePoint build to work with pyrsl 2  
<a id="2.3"></a>2.3 [pyrsl v2.1.0](https://github.com/xtuml/pyrsl/releases/tag/v2.1.0)   

### 3. Background

Issue 9481 [2.2] updated the BridgePoint build to work with pyrsl 2.0.1.  Soon
after that work was completed, version 2.1.0 [2.3] of pyrsl was released.

### 4. Requirements

4.1 Ship pyrsl 2.1.0 with BridgePoint  
4.2 Build BridgePoint itself with pyrsl 2.1.0    

### 5. Work Required

5.1 Update the mctools POM file    
The file `bridgepoint/releng/org.xtuml.bp.mctools/pom.xml` specifies which
version of the pyrsl tool to download.  The update to move to v2.1.0 was
pushed directly to master before this work.  

5.2 Fix `select` statements  
Various plugins, and MC-Java itself, have archetypes that use `select` 
statements that need to be updated.  Pyrsl 2.1.0 enforces correct use of `select any` when
the cardinality of a relationship is "many".  The old generator used to allow `select one`
in this case but those statements now create an error.  All erroneous usages must be
changed to use `select any`.  

These changes must be made in both the `bridgepoint` and `bptest` repositories.    

5.3 Fix build dependency  
The `io.mdl` build's `class2table` target requires the `ooaofgraphics.sql` file as 
input.  The ant dependency clause `class2table.depends` is updated in 
`src/org.xtuml.bp.io.mdl/generate.xml` to make sure this file is created so it is available
when required.   

### 6. Implementation Comments

None.  

### 7. Unit Test

7.1  Clean, then build locally with pyrsl v2.1.0   

### 8. User Documentation

None.  

### 9. Code Changes

Fork/Repository: __keithbrown/bridgepoint__  
Branch: __10338_pyrsl_update__   

<pre>

</pre>

Fork/Repository: __keithbrown/bptest__  
Branch: __10338_pyrsl_update__   

<pre>
src/org.xtuml.bp.core.test/arc/gen_delete_test.arc
</pre>

### End

