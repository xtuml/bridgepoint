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
<a id="2.3"></a>2.3 [BridgePoint DEI #8953](https://support.onefact.net/issues/8953) High level manual test for MASL editor  

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
6.1 Support navigation from problem markers to expected editors   

6.1.1 Support model marker   

6.1.1.1 Override masl marker creation   
Introuce a new class that overrides the default ResourceUIValidatorExtension.  This class shall perform creating markers when the xtext infrastructure demands.  The data given is used to create a model marker.  The masl model marked is defined as one which is nearly the same as the xtext marker, only it has xtuml related data attached.   

6.1.1.2 Model marker   
The class mentioned above creates the data exactly the same as before, only now it adds a model path, id and class type.   

6.1.1.3 masl to xtUML   
In order to provide the necessary data for a model marker from a masl resource we need to do the following:   

* Walk the masl hierarchy up until we find a proper TopLevelElement, these are the roots for xtuml element   
* From the top level element we need to find the actual node for the problem element, this is used to provide the partial element and give guidance to proper navigation data (line number, offset, etc.)   
* Resolve the xtuml element by name and signature   
* Create the marker against the resolved xtuml element's root file   
* Add xtuml attributes: element path, element unique id, and element class type   

6.1.2 Support model marker navigation    
Eclipse does not support navigation from a marker to a non-resource related element.  Opening a marker will ask the ide what editor it shall use given the attached resource from the marker.  There are a few lines of code that could be changed in eclipse to better support this, but for this issue they are not considered.  For this issue a workaround was determined.   This workaround uses the IGotoMarker infrastructure in order to navigate to the proper element editor.  Eclipse looks for editors associated with an extension, in our case xtuml.  It then looks for an editor defined in the marker if any.  We will use the editor id as a new lightweight editor (RoutingEditor).  We could use the existing editors but creating this new one will allow us to use the design for all existing editors and future ones.   

6.1.2.1 RoutingEditor   
A new editor is created in core.ui.editor, RoutingEditor.  This editor is simply an xml entry and provides minimal text editing support.  The editor is not registered as the default and only can be directly executed through the context menu when an xtuml file is selected.  If it is executed a text editor will open contianing the xtuml contents.  The implementation for the editor only overrides the getAdapter method, and only covers the IGotoMarker interface case.   

6.1.2.2 IGotoMarker    
As stated above the RoutingEditor implements the IGotoMarker.  This implementation does the following:   

* Locates the element from the marker data stored, using the element path for PMC resolution, the class type and id for element resolution   
* Opens the editor associated with that element   
* Applies the same goto for marker call on the opened editor   
* Closes the routing editor   

6.2 Persistence   
The plugin.xml that defines the marker support for masl data is modified to set the persistence attribute to false.   

7. Design Comments
------------------

8. User Documentation
---------------------

9. Unit Test
------------
9.1 The test procedure is defined by test number 4.8 in [#8953](https://support.onefact.net/issues/8953).  
  
End
---

