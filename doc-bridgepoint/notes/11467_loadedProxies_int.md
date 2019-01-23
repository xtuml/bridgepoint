---

This work is licensed under the Creative Commons CC0 License

---

# Pointless loadProxies map created during import? 
### xtUML Project Implementation Note


### 1. Abstract

This note describes work performed to remove unused code that exists in the import functionality.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #11467](https://support.onefact.net/issues/11467)  Headline issue   
<a id="2.2"></a>2.2 [Search results for "loadedProxies" in bridgepoint code](https://support.onefact.net/issues/11467#note-1)   

### 3. Background

Recent work caused the author to step through some of BridgePoint's "import" code.  During the code
analysis the author identified that a map (named `loadedProxies`) is created and populated with 
instances that are never read back out of the map.  

In addition, the use case makes little sense. The import code is created via archetype and uses a 
pattern of code. Here is an example:
```java
    private void createR_RGO(Ooaofooa modelRoot, String table, Vector parms, Vector rawParms, int numParms,
            IProgressMonitor pm) {
        ReferringClassInAssoc_c newInst = null;
        if (table.equalsIgnoreCase("R_RGO")) { //$NON-NLS-1$
            newInst = ReferringClassInAssoc_c.resolveInstance(modelRoot,
                    IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
                    IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
                    IdAssigner.createUUIDFromString((String) parms.elementAt(2)));
            pm.worked(1);
            metadata.setComponentOfME(newInst, m_component);
            loadedInstances.add(newInst);
        } else if (table.equalsIgnoreCase("R_RGO_PROXY")) //$NON-NLS-1$
        {
            String path = (String) parms.lastElement();
            newInst = ReferringClassInAssoc_c.createProxy(modelRoot,
                    IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
                    IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
                    IdAssigner.createUUIDFromString((String) parms.elementAt(2)), removeTics(path), projRelPath);
            if (newInst.isProxy()) {
                loadedInstances.add(newInst);
                pm.worked(1);
            } else {
                loadedProxies.add(newInst);
            }
            // newInst component is set during createProxy()
        }
    }
```  

Note that if the `new_inst` handle we get back from `*.createProxy()` actually is a proxy, we put it into 
the `loadedInstances` map along with all the other instances that __are not__ proxies. If the `newInst` 
handle we back __is not__ a proxy, i.e. is a real instance found instead of creating a proxy, then we put 
this real instance into the `loadedProxies` map.   

And then, as mentioned previously, we don't see anywhere `loadedProxies` map is ever read. We did a search 
of the whole workspace. Results of that search may be found in <a href="#2.2">2.2</a>.  

There are 636 places where BridgePoint code adds to the `loadedProxies` map between the files 
`ImportModelComponent.java` and `ImportModelStream.java`.  This is a lot of cleanup that can be 
applied to the code to remove this unnecessary bit.   

### 4. Requirements

4.1 Remove code that creates and populates `loadedProxies` map.    

### 5. Work Required

5.1  Edit `src/org.xtuml.bp.io.core/arc/gen_import_java.inc` to remove the declaration of the
`loadedProxies` variable.   
```java
    private List<NonRootModelElement> loadedInstances = new Vector<NonRootModelElement>();
-   private List<NonRootModelElement> loadedProxies = new Vector<NonRootModelElement>();
    private List<NonRootModelElement> loadedGraphicsInstances = new Vector<NonRootModelElement>();
```
5.2  Edit `src/org.xtuml.bp.io.core/arc/import_functions.inc` with the following code removal
to stop populating the map:  
```java
        if (newInst.isProxy()) {
          loadedInstances.add(newInst);
          pm.worked(1);
-       } else {
-         loadedProxies.add(newInst);
        }
-       // newInst component is set during createProxy()
      }
```

### 6. Implementation Comments

None.  

### 7. Unit Test

7.1  Diff code in affected plugins and make sure all file changes are expected.  
7.2  Run all unit tests on server and verify results are similar to a recent nightly build with test.  

### 8. User Documentation

None.  

### 9. Code Changes

Fork/Repository: __keithbrown/bridgepoint__   
Branch: __11467_loadedProxies__  

<pre>

doc-bridgepoint/notes/11467_loadedProxies_int.md
src/org.xtuml.bp.io.core/arc/gen_import_java.inc
src/org.xtuml.bp.io.core/arc/import_functions.inc

</pre>

### End

