---

This work is licensed under the Creative Commons CC0 License

---

# Reconciler removing imported interface under a component
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the changes required to prevent the graphical reconciler
from removing valid imported interface references within a component diagram.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8815](https://support.onefact.net/issues/8151) Missing provisions requirements  
<a id="2.2"></a>2.2 [BridgePoint DEI #8619](https://support.onefact.net/issues/8619) Provisions and Requirements are not being created properly on component references   
<a id="2.3"></a>2.3 [BridgePoint DEI #8848](https://support.onefact.net/issues/8848) A move that downgrades an imported class should delete the imported class, not unassign it  

3. Background
-------------
The tool currently does not support creating imported interface references
during reconciliation.  If one exists that was created before the current master
base line, it will be removed for the same reason that creation does not work.   
The reconciler believes that the component reference in question does not
require any imported interface references.

4. Requirements
---------------
4.1 Reconciler shall keep the required imported interface references synchronized  
4.2 When downgrading an imported class during move, the imported class shall be   
    deleted rather than just unassigned.
    
5. Work Required
----------------
5.1 Support component as diagram parent for imported interface references

The following operations exist in Component Reference to support reconciliation:   

  * getImportedProvisionCount   
  * getImportedProvisionId   
  * getImportedProvisionProvisionId   
  * getImportedRequirementCount     
  * getImportedRequirementId     
  * getImportedRequirementRequirementId
  
They check the parent package of the shape and connector to assure equality
before considering a provision or requirement for reconciliation.  They are all
changed to account for comparison of components as the container.

5.2 Delete imported class on downgrade

Imported Class.downgradeCheck() is modified to call dispose on downgrade rather
than unassign.  This prevents leaving an unassigned imported class with a bad
graphic for any association that existed prior to downgrade.
     
6. Implementation Comments
--------------------------

7. Unit Test
------------
7.1 Test reconciler new creation    
7.1.1 Create a component reference under a component   
7.1.2 Assign the reference to a component with at least one formalized provision   
      and one formalized requirement   
7.1.3 Result an imported provision and requirement are created on the component   
      diagram

7.2 Test reconciler existing imported interface reference
7.2.1 Use the steps in 7.1 to create the test fixture  
7.2.2 Restart the tool
7.2.3 The component reference still has the expected imported interface   
	  references on the component diagram      

7.3 Test reconciler provision and requirement deleted   
7.3.1 Use the steps in 7.1 to create the test fixture   
7.3.2 Delete the provision and requirement on the assigned to component   
7.3.3 The component reference has the imported interface references removed   

7.4 Test downgrade of imported class   
7.4.1 Create a class in a package: pkgRTO   
7.4.2 Create a component in the same package: compDest   
7.4.3 Create an imported class in a package: pkgRGO   
7.4.4 Assign the imported class to the class in pkgRTO   
7.4.5 Create a class in the package along with the imported class   
7.4.6 Create an association between the new class and the imported class   
7.4.7 Move the class in pkgRTO to live under the component created   
7.4.8 The imported class is deleted along with the association   

8. User Documentation
--------------------- 

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/bridgepoint
Branch: 8815_Missing_provisions_requirements

<pre>

doc-bridgepoint/notes/8815_Missing_provisions_requirements/
    8815_Missing_provisions_requirements.int.md

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/
    Component Library/Component Reference/Component Reference.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Provision/
    Provision.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Requirement/
    Requirement.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Imported Class/
    Imported Class.xtuml

</pre>

End
---

