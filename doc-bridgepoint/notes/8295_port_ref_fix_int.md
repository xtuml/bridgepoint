---

This work is licensed under the Creative Commons CC0 License

---

# Fix Issue with Port References
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes a fix for an issue that appears after running the "Synchronize library/references" operations.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8295](https://support.onefact.net/issues/8295) - Headline issue.  
<a id="2.2"></a>2.2 [BridgePoint DEI #8064](https://support.onefact.net/issues/8064) - Customer SR: Port names not shown correctly in model explorer for imported components.     

3. Background
-------------
During ongoing BridgePoint development work, the xtUML team noticed a problem with blank port
reference names after running the synchronize operation.  Around the same time, a customer
submitted an SR against BridgePoint 5.2 (and 5.0) that described the same problem.  The reporter had
even debugged the issue and provided a suggested fix [[2.2]](#2.2).   Per ususal process, the xtUML dev
team opened a public DEI [[2.1]](#2.1) corresponding to the SR.

4. Requirements
---------------
4.1 After running either "Synchronize ..." operation, port names on component references shall not be blank.  

5. Work Required
----------------
5.1  In ```ooaofooa::Component::Component Library::Component Reference```, the ```synchronize``` operation performs an improper navigation, resulting in an null instance to be related across R4709.

```select one existingPort related by localRef->C_PO[R4016];```

should be

```select one existingPort related by interfaceRef->C_PO[R4016];```

as it is in the ```assignToComp``` operation in the same class.

5.1.1  The fix was first applied in a branch v5.2.2 based off the previous release branch v5.2.0.  For this work, the 
  author branched off the latest master and then cherry picked in the change (8071b10) from the v5.2.2 branch.

6. Implementation Comments
--------------------------
6.1 In the process of writing this note, the author found that the URLs for redmine issues in the template
  were specified incorrectly.
    
  Template says: ```https://support.onefact.net/redmine/xxx1```

  Should be: ```https://support.onefact.net/issues/xxx1```

  The ANT and DNT templates are OK.  The INT template is updated as part of this changeset.
  
7. Unit Test
------------
7.1 Manual test to verify the fix  

  * Create GPS Watch
  * Turn on IPRs on GPS Watch
  * Create a second xtUML Project "Lib2"
  * Inside Lib2, create a package and an interface "testif"
  * Open the Library package inside GPS Watch
  * Add a new provided interface on Location
  * Formalize it to testif
  * Expand the System package in GPS Watch
  * Expand the Location component ref
  * __R__ The ports are all be named. Specifically, "Port1" is one of the names. (In the error case this port name showed as blank)

8. Code Changes
---------------
Branch name: 8295_port_ref_fix

<pre>
doc-bridgepoint/notes/8295_port_ref_fix_int.md
doc-bridgepoint/process/templates/int_template.md
org.xtuml.bp.core/ooaofooa/Component/Component Library/Component Reference/Component Reference.xtuml 
</pre>

End
---

