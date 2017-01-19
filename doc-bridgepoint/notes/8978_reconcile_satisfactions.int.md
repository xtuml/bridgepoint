---

This work is licensed under the Creative Commons CC0 License

---

# Modify graphics reconciliation to reconcile satisfactions  
### xtUML Project Implementation Note

1. Abstract
-----------
Currently component references get automatically assigned, and interface references get formalized, but satisactions do not get created. This issue is raised to auto create satisfaction during import of a MASL model.  

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

3. Background
-------------
Issue [[2.2]](#2.2) was raised to cause satisfactions to be autocreated during MASL import. That
issue's work is complete as described in its implemetation note [[2.3]](#2.3). 
However, the graphics reconciliation associated with that task is not complete.
Without the graphics reconciliation the work associated with #8484 is worthless. In
fact, it leaves the model in a worse state than if satisfactions were never created
because the user can not connect the ball and cup. This issue is raised to reconcile the 
satisfaction's graphics.
 
Code already exists in the reconciliation done WITH existing graphics to create the
satisfaction. This OAL can be seen in GD_MD.newConnector. It can be seen where
the GD_AOS is created.  Why not just use this? Te reason is that it would appear it
was never fully implemented. In the bp.ui.graphics/plugin.xml the reconciliation
entries that invoke getSatisfactionCount are already implemented in classes Provision
and Imported Provision in the ooaofooa. However, the associated entries to return the
identifiers are not implemented. Instead, at that spot we have text that reads:
"marker for boolean paramater" just to allow the count operation to be invoked properly,
there is no operation to get the ooaofood UUID associated with the satisfaction.
Looking in the history in {Imported} Provision.xtuml it can be seen that in BP v5.4
there was an operation named getConnectedId that would return the ID of ANY requirement
associated with a provisions satisfaction. It was removed likely because it was simply
blindly selecting any satisfaction and that is not a correct implementation. This is what
the old oal looked like:
```
select any satisfactionInstance related by self->C_SF[R4002] where (selected.Provision_Id == self.get_ooa_id());
if(not empty satisfactionInstance)
  return satisfactionInstance.Requirement_Id;
else
  return GD::NULL_UNIQUE_ID();
end if;
```
# This is what gets modified/created when a satisfaction is graphcially recionciled
## A new GD_AOS instance is added
## A new DIM_CON instance is added 
## DIM_ED.last_id (second parameter) changed from null to the id of the new GD_AOS instance
## DIM_WAY coordinates are adjusted

# Other useful references:
## @see Model Tool (CT_MTL.finalizeConnector()) - This is where drag/drop 
performs the satisfaction. 
## @see the GD_MD.newConnector, the case where graphics ARE present. There is a 
spot there that does handle this. See it by looing at where the GD_AOS is created.  


# Options:
## Extend the existing infrastructure so it works
### find satisfactions on on requirements in addition to provisions
### modify the connector reconciliation to be bale to handle this case where one
  	side of the connection has cardinality 0..1 (requirement) and the other has
  	cardinality 0..* (provision).  Note that this is similar to connector creation for
  	subtype/subertype.

## Once satisfaction are found on provisions, write a special routine to reconcile
  	   satisfaction. Special case it.


4. Requirements
---------------
4.1 The graphics associated with a satisfaction must be reconciled.  

5. Work Required
----------------
5.1 Put the satisfaction in pass 3 to assure the GD_GE for the requirement and provision
   is present

5.2. Since there may be multiple requirements satisfied to a provision, when we find
   a provision that has satisfaction(s) the GD_ARS.reconcileConnectorsNoGraphics
   shall handle processing in the graphics client EE (CL)

5.2.1. Create CL::reconcileSatisfaction(provision:<instance>, canvas:<instance>)
5.2.1.1 paramater 1 is a provision or imported provision
5.2.1.2 parameter 2 is a package or component

5.2.2 Find all the requirements satisfied to the provisions on the same canvas
5.2.2.1 For each requirement found reconcile the graphics
5.2.2.2 Use oal from Model Tool (CT_MTL.finalizeConnector()) to create a new operation that
just creates graphics associated with a satisfaction.
5.2.2.2.1 It is worth noting that the parameters used in CT_MTL.finalizeConnector the
startElement is the element beingMoved and the endElement is the destination. Therefore,
for this task:
```
   startElement is the GD_GE with represents of Requirement_c
   endElement is the GD_GE with represents of Provision_c
```

6. Implementation Comments
--------------------------
none  

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

</pre>

End
---

