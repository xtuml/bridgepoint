---

This work is licensed under the Creative Commons CC0 License

---

# Inter-project reference resolution 
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the changes required to address RTO resolution across
file boundaries.  More specifically the issue is related to proxy paths becoming
invalid during load.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #xxx1](https://support.onefact.net/issues/7877) 7877 interproject reference synch  

3. Background
-------------
The original issue was raised to capture a bug where an inter-project reference
was unreachable due to a bad proxy path.  The proxy path is incorrectly set when
the RGO is modified with RTO not being present at write time.  This can occur
with inter-project references if the RTO is renamed/moved while the RGO is not
present in the workspace.  If this occurs and the RGO is brought into the
workspace it will fail to locate the RTO as it no longer lives at the stale
proxy path location.    

4. Requirements
---------------
4.1 RTOs shall be resolvable so long as they are present in the workspace.  
4.2 RGOs shall no longer be force persisted to try and guarantee the proxy path.   

5. Work Required
----------------
5.1 Load RTO during batch relating

The problem described in the background is technically due to us not loading    
potential RTO containers during batch relate.  If an RGO comes into play after   
a path change has been made to the RTO, the batch relate stage will not properly
locate the RTO.  We currently only search all roots within the same project for
the RTO.  

5.1.1

Changes are made which call PersistenceManager.ensureAllInstancesLoaded() during
batch relate.  This is done after the original search, allowing previous
behavior to remain unchanged.  There are three cases where we do not try to load
any RTOs.

5.1.1.1

The first case is when an element triggers a load of an RGO, which then
tries to load the element's file again before any associating occurred.  This
was causing stack overflow issues.  To prevent these loads, a simple check for
isProxy is added.  We do not want to have proxies that are being batch related
triggering loads.  When the real element is loaded the proxy will be replaced by
it.

5.1.1.2

The second case is pretty much the same as the first.  The difference here is
that the element does not have to refer to something.  For instance where the
RTO end is conditional.  A check against the stored id being null prevents this
stack overflow loading behavior.  Additional checks are added to
PersistenceManager.ensureAllInstancesLoaded() that use the status value of the
pmc to be loaded.  Two checks were added: status != LOADING and
status != LOADED.   
  
5.1.1.3

The final case is where an element's unique identifier is an integer.  Right now
we are creating a fake UUID with the least significant bits being set to 0.  The
most significant matches the id.  Considering the only possible locations for
integer over UUID will not require proxy loading, a check for the least
significant bits is made. 

5.1.2

In the situation where an RTO is modified without the presence of the RGO we
must call batch relate on the RGO trying to load the RTO proxy.  This will
trigger the above code to properly load and locate the RTO.
 
5.2 Forced RGO persistence

Currently we force persist all RGOs when renaming a model element.  The move
logic performs the same forcing.  Considering that forcing is no longer required
and we like to dirty files only when necessary,  all logic searching for the RGO
to persist is removed.  This will leave the file in an unsynchronized state, but
the changes in 5.1 will allow the tool to behave normally.   

6. Implementation Comments
--------------------------

7. Unit Test
------------

7.1 Resolution of RTO with a good proxy path     
7.1.1 Load any attribute that is not pointing at a global datatype   
7.1.2 Result is the attribute is associated with the correct data type   
7.2.4 Result is the attribute's root file is not modified

7.2 Resolution of RTO with a bad proxy path   
7.2.1 Modify an attribute's root file, such that the proxy path to a non-global
      datatype is incorrect
7.2.2 Load the modified attribute (from a fully unloaded state)   
7.2.3 Result is the attribute is associated with the correct data type
7.2.4 Result is the attribute's root file is not modified   

7.3 Resolution of non-existent RTO
7.3.1 In the file system delete a datatype's package file
7.3.2 Load an attribute that refers to the deleted RTO (from a fully unloaded state)      
7.3.3 Result is the attribute is not associated with the correct type (non-proxy)
7.3.4 Result is the attribute remains associated with the RTO proxy element   
7.3.5 Result is the integrity tool reports the dangling reference   

* Example List Element

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

8. User Documentation
--------------------- 

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/bridgepoint   
Branch: 7877_interproject_reference_synch   

<pre>
doc-bridgepoint/notes/7877_interproject_reference_synch/
    7877_interproject_reference_synch.int.md

MC-Java/java.arc

org.xtuml.bp.core/src/org/xtuml/bp/core/common/ComponentTransactionListener.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/ModelElementMovedModelDelta.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/NonRootModelElement.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistenceManager.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/Transaction.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/TransactionManager.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/PasteAction.java

org.xtuml.bp.core.test/src/CoreGlobalsTestSuiteGenerics.java
org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/references/
    ReferenceResolutionOnLoadTest.java

org.xtuml.bp.io.core/arc/export_functions.inc
</pre>

End
---

