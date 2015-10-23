---

This work is licensed under the Creative Commons CC0 License

---

# Fix Mars
### xtUML Project Design Note


1. Abstract
-----------
With [2] the BridgePoint moved the eclipse base under the tool to Mars.1.  The BridgePoint 
code base and unit tests had some issues with this move.  This note describes changes to
resolve these issues.  This issue does not completely resolve the JUnit problems.  For additional
work in that area see [3].   

2. Document References
----------------------
[1] [BridgePoint DEI #7861](https://support.onefact.net/redmine/issues/7861)  
[2] [BridgePoint DEI #7682](https://support.onefact.net/redmine/issues/7682)  
[3] [BridgePoint DEI #7738](https://support.onefact.net/redmine/issues/7738)  

3. Background
-------------
See the Abstract.

4. Requirements
---------------
4.1  BridgePoint shall build cleanly in eclipse 4.5.  
4.2  BridgePoint shall pass execution smoke test in eclipse 4.5.   
4.3  BridgePoint JUnit tests shall run in eclipse 4.5.   

5. Analysis
-----------
5.1 JUnit and Java 7 changed functionality in Mars.  The test functions are no longer run in top-down
  file order.  They may run in a different order.  We have a number of JUnit test classes that depend on
  this top-down order.  
5.2 We must move to an updated version of ANTLR to avoid some code problems that Mars exposes in the way
  it handles external JAR dependencies.  See [2] for more details.  

6. Design
---------
6.1  To resolve the new JUnit function sequencing problems, the approach was to separate out test functions 
  into new classes when possible to reduce dependencies.  When order dependencies still remained, I modified
  all the existing ```test<Name>``` functions to be ```dotest<Name>```.  I then added a single new ```test<Name>``` 
  function to the class and call the ```dotest<Name>``` functions in the required order.  This reduces the number of
  tests that show as run in the JUnit view, but makes our ordering explicit where required instead of relying on
  implicit file-based ordering as before.    
6.2  The launch configs are updated to remove the -XXMaxPermSize attribute as it is no longer supported in Java 8.   
6.3  Mars has a new feature that does automatic error reporting back to the eclipse code maintainers.  This is a
  network operation that initializes when you start eclipse.  It was annoying and time consuming to have this run 
  during the JUnits, so the launch configs were modified to disable this by adding ```-Dorg.eclipse.epp.logging.aeri.ui.skipReports=true```    


7. Design Comments
------------------

8. Unit Test
------------

End
---
