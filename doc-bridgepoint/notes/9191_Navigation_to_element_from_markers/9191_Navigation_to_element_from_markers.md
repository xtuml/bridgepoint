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
As the issue states we currently cannot navigate from a problem marker to the proper editor (in this case element editor).  The reason for this is that eclipse has a very strong tie between marker and resource.  What BridgePoint has done in the past is to create a real file to allow marker creation (visible marker in problems view).  Markers have to be associated with a physical resource, it can be fake but will never show in the problems view if not existent.  The masl partial editor is no different and considered an element editor.

5.1.1 Support model marker   
In order to provide enough data in the marker to resolve a model element we must create our own markers (model markers).  These markers shall have enough information to resolve an xtUML element given a resource.  To do this we must take over the existing marker creation from the xtext infrastructure.  We need to override the code that currently creates markers for masl resources.  We shall take over marker creation and create a marker that suits the needs of an xtUML element.  This provides a minimalist approach, providing the exact same creation/deletion updates as before.  

5.1.2 Support model marker navigation   
Given the data provided in the model marker we need to support navigation to the proper editor.  This editor shall be opened when the marker is double-clicked or the Go To menu item is selected.  Additionally, the line number containing the problem shall be set and the problem text shall be selected.

5.2 Persistence   
Markers shall not be persisted as per the requirements.  A simple flag shall be set in the marker plugin extension definitions.   

6. Design
---------

7. Design Comments
------------------

8. User Documentation
---------------------

9. Unit Test
------------
<TODO>
End
---

