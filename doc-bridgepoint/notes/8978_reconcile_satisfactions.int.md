---

This work is licensed under the Creative Commons CC0 License

---

# Modify graphics reconciliation to reconcile satisfactions  
### xtUML Project Implementation Note

1. Abstract
-----------
Reconcile graphics for satisfactions when starting with a mdel that has no graphics.  

2. Document References
----------------------
<a id="2.1"></a>2.2 [BridgePoint DEI #8978](https://support.onefact.net/issues/8978) 
Graphics reconciliation is not connecting the ball and cup in a satisfaction.  
<a id="2.2"></a>2.2 [BridgePoint DEI #8484](https://support.onefact.net/issues/8484) 
Update import to create satisfactions.  
<a id="2.3"></a>2.3 [Implementation note for #8484](8484_create_satisfactions_on_import/doc-bridgepoint/notes/8484_masl_auto_satisfaction_creation/8484_masl_auto_satisfaction.int.md) 
Implementation note for Issuec8484.  
<a id="2.4"></a>2.4 [BridgePoint DEI #8977](https://support.onefact.net/issues/8977) 
Manual test for automatic satisfaction creation.  
<a id="2.5"></a>2.5 [BridgePoint DEI #9026](https://support.onefact.net/issues/9026)  
Handle graphics reconciliation of satisfaction where diagram has multiple interfaces formalized to the same provision.  

3. Background
-------------
3.1 Issue [[2.2]](#2.2) was raised to cause satisfactions to be 
autocreated during MASL import. That issue's work is complete as 
described in its implemetation note [[2.3]](#2.3). However, the graphics 
reconciliation associated with that task is not complete.
Without the graphics reconciliation the work associated with #8484 is worthless. In
fact, it leaves the model in a worse state than if satisfactions were never created
because the user can not connect the ball and cup. This issue is raised to reconcile the 
satisfaction's graphics.  

3.2 History  
Some graphics reconcailiation code for satisfaction does seem to exists. It is specific to
the case where some graphics already exist during graphics reconcilation (the path through 
graphics reconciliation done on model edits). This OAL can be seen in GD_MD.newConnector. 
It can be seen in that operation where the GD_AOS instance is created.   
Why not just use this? The reason is that it would appear it was never fully implemented. It
does not work.  

3.2 References  
3.2.1 This is what gets modified/created when a satisfaction is graphcially recionciled  
  * A new GD_AOS instance is added
  * A new DIM_CON instance is added 
  * DIM_ED.last_id (second parameter) changed from null to the id of the new GD_AOS instance
  * DIM_WAY coordinates are adjusted

3.2.2 Other useful references:  
  * @see Model Tool (CT_MTL.finalizeConnector()) - This is where drag/drop 
performs the satisfaction. 
  * @see the GD_MD.newConnector, the case where graphics ARE present. There is a 
spot there that does handle this. See it by looing at where the GD_AOS is created.  

3.4 Options:  
3.4.1 Extend the existing infrastructure so it works
* find satisfactions on provisions in addition to requirements
* modify the connector reconciliation to be able to handle this case where one
side of the connection has cardinality 0..1 (requirement) and the other has
cardinality 0..* (provision).  Note that this is similar to connector creation for
subtype/subertype.  
3.4.2 Speacial case satisfaction similial to linked asssocations  
* Handle satisfacition reconailiation in pass 3, after the provsion and requrement reconciliation is complete  
* shortcut the the search for "side 2" of the connector similar to what we do for linked associations. Instead of doing an exhaustive search for side2, write a interface routine in the Client EE that uses the satisfaction instance to obtain the provision instance.
* Once the requirement, and provision instances are in hand find the GD_GE instance associated with the provision (The requirement instance is in handle from the normal reconciliation flow). 
* Use the existing drag/drag satisfaction creation routine to perform the satisfaction (CT_MTL.finalizeConnector)  


Option 3.4.2 shall be used because even after modified the flow for 3.4.1 the GD_MD.newConnector operation would
have to be "special cased" to handle satisfactions (it is the only place a GD_AOS is created).  

4. Requirements
---------------
4.1 The graphics associated with a satisfaction must be reconciled.  

5. Work Required
----------------
5.1 Add a Satisfaction type to the ooaofgraphics OOA_Types datatype  
5.1.1 Modidy pass 3 of GD_ARS::ReconcileAllGraphics  
5.1.1.1 Assure that the getSatisfactionCount operation is only used in pass 3.  

5.2. Modify GD_ARS::reconcileGraphicsNoExistingConnectors  
5.2.1 Introduce satisfaction reconciliation to pass 3  

5.3 Create realized bridge CL::getProvisionFromSatisfaction(Instance Satisfaction)  
This operation return the Provision_c ooa instance from the given Satisfaction_c ooa instance.  

5.4 Modify CT_MTL.finalizeConnector to handle the case where it is not actually being called from 
the drag/drop operation.  

6. Implementation Comments
--------------------------
6.1 During implementation, thought experiment, it was observed that in the situation where one is reconciling a
diagram with multiple component refernces, it is possible to have a situation where these component references may
has provided interface formalized to the same interface. In this situation, in GD_ARS::reconcileGraphicsNoExistingConnectors where we are looking for the GD_GE instance formalized to the provision we will find multiple GD_GE instances. When this happens, there is no good way to know which of these graphical instances if the one we should reconcile to. Issue [[2.5]](#2.5) was raised about this. This will not affect import of masl models because they do not have this situation.  

7. Unit Test
------------
7.1 Run the manual test defined by [[2.4]](#2.4).  

8. User Documentation
---------------------
none  

9. Code Changes
---------------
Fork/Repository: rmulvey  
Branch: 8484_create_satisfaction_on_import  

<pre>

> org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    Canvas Tools/Model Tool/Model Tool.xtuml
> org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/Datatypes/
    Datatypes.xtuml
> org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    External Entities/External Entities.xtuml
> org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    Graphical Data/Auto Reconciliation Specification/
    Auto Reconciliation Specification.xtuml
> org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/Cl_c.java


</pre>

End
---

