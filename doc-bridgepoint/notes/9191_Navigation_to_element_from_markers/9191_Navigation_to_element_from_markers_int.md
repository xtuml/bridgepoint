---

This work is licensed under the Creative Commons CC0 License

---

# Support proper navigation to model elements from Problems view  
### xtUML Project Implementation Note

1. Abstract
-----------
This note list the code changes required by the design.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9191](https://support.onefact.net/issues/9191) Using "Go To" from the problem Tab opens underlying file  
<a id="2.1"></a>2.1 [9191_Navigation_to_element_from_markers.md](https://github.com/xtuml/bridgepoint/blob/9191_Navigation_to_element_from_markers/doc-bridgepoint/notes/9191_Navigation_to_element_from_markers/9191_Navigation_to_element_from_markers.md) Design note  

3. Background
-------------
See [2.1].  

4. Requirements
---------------

5. Work Required
----------------

6. Implementation Comments
--------------------------

7. Unit Test
------------
See [2.1].  

8. User Documentation
---------------------

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/bridgepoint
Branch: 9191_Navigation_to_element_from_markers

<pre>

doc-bridgepoint/notes/9191_Navigation_to_element_from_markers/
    9191_Navigation_to_element_from_markers_int.md
doc-bridgepoint/notes/9191_Navigation_to_element_from_markers/
    9191_Navigation_to_element_from_markers.md

org.xtuml.bp.core/arc/create_core_plugin.inc
org.xtuml.bp.core/src/org/xtuml/bp/core/common/IntegrityChecker.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/editor/RoutingEditor.java
org.xtuml.bp.core/src/org/xtuml/bp/core/util/EditorUtil.java

org.xtuml.bp.ui.explorer/arc/create_explorer_view.inc

org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/plugin.xml
org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/
    xtext/masl/ui/MASLUiModule.xtend
org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/
    xtext/masl/ui/validation/ModelResourceUIValidatorExtension.java

org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/MASLUiModule.xtend
org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/validation/
    ModelResourceUIValidatorExtension.java
org.xtuml.bp.xtext.masl.ui/plugin.xml



</pre>

End
---

