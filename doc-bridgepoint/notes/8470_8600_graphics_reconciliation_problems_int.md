---

This work is licensed under the Creative Commons CC0 License

---

# Graphics not rendered properly for subsuper relationships. Graphics reconciliation sometimes modifies diagrams and models it should not.
### xtUML Project Implementation Note

1. Abstract
-----------
Graphics reconciliation of subtype/supertype connectors is not working properly. 
This issue is raised to fix it.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8470](https://support.onefact.net/issues/8470) 
Graphics not rendered properly for subsuper relationships.  
<a id="2.2"></a>2.3 [BridgePoint DEI #8600](https://support.onefact.net/issues/8600) 
Graphics reconciliation sometimes modifies diagrams and models it should not.  

3. Background
-------------
The issue reports that when a subsuper relationship is imported, only the connector for the first subtype 
is connected to the supertype. The other subtypes are logically connected 
(you can view them in the properties view for the relationship), however no graphical connectors exist.

To analyze the problem I created a simple test model in my sandbox. This allowed me to see that 
the issue is that the subtype connector is actually not being drawn at all. What is happening is 
that the supertype connector is being connected from the super to the sub directly 
(and incorrectly of course).

Next I created a test model with just a supertype, no suptype. Reconciliation of it did the right 
thing (though the line was 0 length). 

This led me to believe that the reconciliation order was the main cause of the problem. The supertype 
must be created before the subtype during reconcile. In GD_ARS::ReconcileAllGraphics we have a 
3rd pass that currently is only used to create the linked class association. The reason is that 
the association must be created before the linked class association can be.  Of course subtype is 
similar, it can not be created until the supertype association exists. The work to resolve this issue
shall therefore defer the subtype creation to pass 3 as we do linked association.

4. Requirements
---------------
4.1 Graphic reconciliation shall properly create the connectors for subtype/supertype relationships.


5. Work Required
----------------


5.2  When i tested against the model that has both super and subtypes, no subtype or supertype lines were drawn.

5.2.1 The reason for this was that in GD_ARS.reconcileConnectorsNoExistingGraphics there is a special-case for the linkedAssociation case (the case where a connector is connected to a shape on one side but a connector, not a shape, on the other). This Subtype situation had to be added to this special case. When this was done, the subtype connector lines started being drawn. However, I still see that the properties view, when a subtype line is selected is not correct. I see that the reason is that the supertype connector is still being connected directly to a subtype.

5.2.2 I also see that after subtype connector creation the represents for the new connector created (GD_GE) is not being set properly, it is null. The reason seems to be that the call to CL::getInstanceFromOOAId for this connector type (subtype) is failing to find the ooaofooa instance.

5.2.2.1 The reason is that {O_OBJ | O_IOBJ}.getSubtypeid() was returning the Rel_ID. This does not uniquely identify the subtype instance. The classaslink was used as a guide, but it's cardinality is 1 on both sides, so it can use the Rel_ID. The next problem is that now that we use the Subtype's OIR_ID we DO find the ooaofooa instance, however, now when we go to find the "other side" there is no match because the superype's id is using the rel_ID.

5.2.2.2 Introduced a new bridge CL::getR_RELFromSubtype and I modified GD_ARS::reconcileConnectorsNoExistingGraphics to use this bridge to locate the "other side" of the subtype connecter during the "other side search" in pass 3 of connector reconciliation.




6. Implementation Comments
--------------------------
5.1 In testing this issue I first used the test model with only a supertype association. This test worked, 
but I observed that another model in my test workspace was also modified during reconcilation. I raised issue #8600
for this problem. I fixed this problem in this same branch.

5.1.1 In GD_MD::newConnector() in the case when there are noExistingGraphics is true, there 
was a blind select GD_ES->GD_GE[R10] that was used to find the "endElementGE". The endElementGE 
represents the element that a connector connects to. In some cases, for example a supertype with no 
subtype, is does not connect to anything, and in this case we expect endElementGE to be null. Before this 
change the blind select was populating this end element in cases it should not, and the 
"select any ... GD_ES->GD_GE[R10] " navigates from an element type to an actual instance and the 
instances are not necessarily in the same diagram or even model.

7. Unit Test
------------
7.1 Assure graphics reconcailtion works for models with supertype/subtype relationships
* Import a model that has a supertype and multiple suptypes (and no graphics)
* Open Model Exploer and select the model
* BridgePoint Utilities > Reconcile Graphics
* Result is that the graphics are correctly reconciled

7.2 Assure that models outside the moel being recincialed are not modified during graphic reconcilation  
* Use the test case outlined in #8600

8. User Documentation
---------------------
none

9. Code Changes
---------------
Branch name: < rmulvey 8470_reconcile_subsuper >

<pre>

doc-bridgepoint/notes/8470_8600_graphics_reconciliation_problems_int.md

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Imported Class/
    Imported Class.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Model Class/
    Model Class.xtuml

org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    External Entities/External Entities.xtuml
org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    Graphical Data/Auto Reconciliation Specification/
    Auto Reconciliation Specification.xtuml
org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    Graphical Data/Model/Model.xtuml
org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/Cl_c.java



</pre>

End
---




