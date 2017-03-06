---

This work is licensed under the Creative Commons CC0 License

---

# Support proper navigation to model elements from Problems view   
### xtUML Project Design Note

1. Abstract
-----------
This note describes an approach that allows proper navigation to model elements through model element identification.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9191](https://support.onefact.net/issues/9191) Using "Go To" from the problem Tab opens underlying file  
<a id="2.2"></a>2.2 [BridgePoint DEI #9121](https://support.onefact.net/issues/9121) Introduction of single buffer editor  

3. Background
-------------
[2.2] introduced a single buffer editor for model elements.  We have a few editors that require editing model elements rather than the direct physical resource.  When OAL editor support was added, and now, there is little support for tying an editor to an in-memory resource.  Bridgepoint has always, in eclipse, had model elements which require eclipse editor support.  The OAL editor support introduced a work-around that was always problematic.  The new single buffer editor is still tied to a physical resource through its problem markers.  Therefore navigation from the Problems view to the actual problem does not work.       

4. Requirements
---------------
4.1 Double clicking a problem in the problems view shall open the required single buffer editor    
4.2 Right clicking and choosing "Go To" shall open the required single buffer editor   
4.3 The user shall be taken to the line number associated with the problem       
4.4 Data shall not be persisted underneath the model structure  
4.5 This work shall not support modifying the .masl files directly   
4.6 This work shall not support masl file modification directly   

5. Analysis
-----------
5.1 Support navigation from problem markers to expected editor   

As the issue states we currently cannot navigate from a problem marker to the proper editor (in this case element editor).  The reason for this is that eclipse has a very strong tie between marker and resource.  What BridgePoint has done in the past is to create a fake file and persist to allow marker creation (visible marker in problems view).  Markers have to be associated with a physical resource, it can be fake but will never show in the problems view if not existent.   

6. Design
---------
In this section, describe in detail each step of the Work Required section of
the analysis, how the task will be accomplished, what technologies will
be used, algorithms, etc. Here is an example reference to the Document References section [[2.1]](#2.1)

6.1 Item 1  
```java
    // java code example
    public void clearDatabase(IProgressMonitor pm) 
    {
        // clear the corresponding graphics-root's database
        OoaofgraphicsUtil.clearGraphicsDatabase(rootId, pm);

        Ooaofooa.getDefaultInstance().fireModelElementUnloaded(this);
    }
```
6.1.1 Example sub-item
* Example List Element

6.2 Item 2  
6.2.1 Example sub-item
* Example List Element

7. Design Comments
------------------
If research carried out during this phase shows that a requirement stated in the
analysis note is infeasible or needs some modification, enumerate those changes
here. If there was no preceding analysis note, then this section documents any
deviations from the design as presented at the design review. Here is an example reference to the Document References section [[2.1]](#2.1)

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

8. User Documentation
---------------------
Describe the end user documentation that was added for this change. 

9. Unit Test
------------
Outline all the unit tests that need to pass and describe the method that you
will use to design and perform the tests. Here is an example reference to the Document References section [[2.1]](#2.1)

9.1 Item 1  
9.1.1 Example sub-item
* Example List Element

9.2 Item 2  
9.2.1 Example sub-item
* Example List Element

End
---

