---

This work is licensed under the Creative Commons CC0 License

---

# Support navigation to a referred to package from a referring package
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the changes required to support navigating from a package    
reference to the referred to package.  This allows open actions to show the
diagram for the referred to package.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8862](https://support.onefact.net/issues/8862) Navigate to referred to packages.   
<a id="2.2"></a>2.2 [BridgePoint DEI #8848](https://support.onefact.net/issues/8848) A move that downgrades an imported class should delete the imported class, not unassign it  
<a id="2.3"></a>2.3 [BridgePoint DEI #8863](https://support.onefact.net/issues/8863) Show package reference in ME  
<a id="2.3"></a>2.4 [BridgePoint DNT for #8458](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8458_model_element_move_tests/8458_Model_Element_Move_Tests.dnt.md) Element move tests   

3. Background
-------------
For other elements which refer to another element elsewhere in the model we
navigate to the referring element's editor.  For package references we should do
the same.

For issue [2.2] we should perform the same downgrade during move that is done
outside of move.  Currently we unassign component references and imported
classes during a move, but dispose of the elements if the user intentionally
unassigns the element.  This leaves cause to unnecessary clean up of unusable
elements.  Additionally the connectors attached to these two elements are left
as un-decorated lines.

For issue [2.3] we currently do not show the path to a referred to project in
model explorer.  We do however show this path in diagrams.  The two shall be
consistent, showing the path in both cases.

4. Requirements
---------------
4.1 Shall allow for navigation from a referring project to the referred to   
    project  
4.2 Component references and imported classes shall be removed as part of a   
    downgrade   
4.3 The path to a referred to package shall be used as the label in model   
    explorer   

5. Work Required
----------------
5.1 Support navigation to referred to package

In core.util.EditorUtil the method getElementToEdit is extended to support
passing a referred to package if present as the element to edit.  If there is no
referred to project the package itself is used.

5.2 Dispose Component Reference and Imported Class at downgrade   

The downgradeCheck operations in Component Reference and Imported Class are
modified to call each classes dispose operation.  Calls to unassign are removed.

5.3 Show referred to package path in ME

The ooaofooa_hierarchy.pei.sql and UITree.pei.sql files are modified to use a
new Label attribute added to the Package class.  This attribute is derived,
returning a path if referring or the element's name if not.  This new attribute   
has the following description markings:

User_Visible:false
Persistent:false
  
The get_connector_text operation in Package is modified to return Label.

6. Implementation Comments
--------------------------

7. Unit Test
------------
7.1 Support navigation to referred to package   

Using the graphical editor:   

7.1.1 Referring package     
7.1.1.1 Double click a referring package   
7.1.1.2 The referred to package's diagram is shown   

7.1.2 Non-referring package   
7.1.2.1 Double click a non-referring package   
7.1.2.2 The diagram for the package is shown   

7.1.3 Repeat 7.1.1 and 7.1.2 using Model Explorer   
7.1.3.1 Navigate to test package   
7.1.3.2 The label in Model Explorer when the package is referring shows the path   
        to the referred to package      

7.2 Downgrade of component reference and imported class   
7.2.1 Run test 7.26 from [2.4]   
7.2.2 The test passes   
7.2.3 Run test 7.26 using an imported class rather than component reference   
7.2.4 The test passes   

8. User Documentation
---------------------

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/bridgepoint
Branch: 8815_Support_graphical_navigation_of_package_references   

<pre>

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/
    Component Library/Component Reference/Component Reference.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package/
    Package.xtuml
org.xtuml.bp.core/sql/ooaofooa_hierarchy.pei.sql
org.xtuml.bp.core/src/org/xtuml/bp/core/util/EditorUtil.java

org.xtuml.bp.ui.explorer/sql/UITree.pei.sql

</pre>

End
---

