---

This work is licensed under the Creative Commons CC0 License

---

# Deprecate Unused Subsystems
### xtUML Project Implementation Note


1. Abstract
-----------
Let us continue to prune the metamodel of unused model artifacts.

2. Document References
----------------------
[1] [8914](https://support.onefact.net/issues/8914) - Delete deprecated OOA pacakges  

3. Background
-------------
Communication and Access is a relational class diagram that models the
paths of events and data between classes.  In a very distant version of
BridgePoint, this information was used to derive a diagram graphically
illustrating the movement of data and control between classes.  It was
very nice.  However, it has not been supported in many years.

Automatic Wiring is a subsystem containing an association class that
links EEs to function across domains.  Logic existed in the past to
automatically invoke a domain function in a foreign domain with the same
name as a bridge in a local domain.  This functionality was superceded
by components, interfaces, ports and inter-component messaging.

4. Requirements
---------------
4.1 Delete Communication and Access  
4.2 Delete Automatic Wiring  
4.3 Delete associated unused model, code, compiler, configuration artifacts.

5. Work Required
----------------
5.1 Delete Communication and Access  
5.2 Delete Automatic Wiring  
5.3 Search for the class keyletters in other places and surgically elide.  
```
ack S_AW from within bp.core/models
Removed a few disposes.
ack CA_ from within bp.core/models
Removed lots of disposes.

Removed CA_ and S_AW from ooa_schema.
Removed clearooa2.sql.  This was used with the old gen database tools.
Removed microwave.sql with is out of date and not used.

Updated export_functions.inc to not add a special brace around CA_SMSMC.
Similar update for import_functions.inc.
```
  
6. Implementation Comments
--------------------------
6.1 `xtumlmc_schema`  
Note that `MSG_M` wants a `participateInCommunication BOOLEAN` attribute.
This needs to stay in the MC schema for a release or two.  
  
7. Unit Test
------------
7.1 Build the schema.  Make sure no referentials shifted.  
7.2 Build BridgePoint.  
7.3 Run GPS Watch.  

8. Code Changes
---------------
<pre>

Fork: cortlandstarrett/bridgepoint
Fork: cortlandstarrett/mc

Branch name: 8914_cacomm_wiring

xtuml/bridgepoint

xtuml/mc

</pre>

End
---

