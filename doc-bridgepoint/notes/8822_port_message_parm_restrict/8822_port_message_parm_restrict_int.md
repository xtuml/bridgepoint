---

This work is licensed under the Creative Commons CC0 License

---

# Property Parameter In Interface Reference Restricting  
### xtUML Project Implementation Note  

1. Abstract
-----------
This implementation restricts where a user can change parameter properties on an interface.


2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8822](https://support.onefact.net/issues/8822) 
This is a link to this issue in the issue tracking system.  
<a id="2.2"></a>2.2  [Statement of Work]  
<a id="2.3"></a>2.3 [Analysis Note #8822](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8822_port_message_parm_restrict/8822_port_message_parm_restrict_ant.md)

3. Background
-------------
Property Parameter instances are the same in both the Interface definition and the usage in interface references (provided signals and provided operations). Currently BridgePoint provides no restrictions on the editing operation capability in the interface references, which means that changes made there are reflected back into the interface definition.  


4. Requirements
---------------
See Analysis Note [[2.3]](#2.3)  

5. Work Required
----------------
See Analysis Note [[2.3]](#2.3)  

6. Implementation Comments
--------------------------

7. Unit Test
------------
This section describes manual testing for the acceptance tests in the analysis note. [[2.3]](#2.3)  
The manual testing is run using the sample GPS Watch model.  

7.1 Rename  

7.1.1 C_PP in interface reference  
* Expand System>GPS Watch::Library::Location>LOC>LocationProvider>getLocation  
* Select latitude and verify the context menu has "Rename" unselectable.  

7.1.2 C_PP in interface definition   
* Expand LocationInterfaces>LocationProvider>getLocation  
* Select latitude and verify the context menu has "Rename" selectable.  

7.2 Delete  

7.2.1 C_PP in interface reference  
* Expand System>GPS Watch::Library::Location>LOC>LocationProvider>getLocation  
* Select latitude and verify the context menu has "Delete" unselectable.  

7.2.2 C_PP in interface definition   
* Expand LocationInterfaces>LocationProvider>getLocation  
* Select latitude and verify the context menu has "Delete" selectable.  

7.2.3 CL_IP in interface reference  
* Expand System>GPS Watch::Library::Location>LOC  
* Select LocationProvider and verify the context menu has "Delete" unselectable.  

7.2.4 CL_IR in interface reference  
* Expand System>GPS Watch::Library::Tracking>LOC  
* Select LocationProvider and verify the context menu has "Delete" unselectable.  

7.2.5 C_I in interface definition  
* Expand LocationInterfaces  
* Select LocationProvider and verify the context menu has "Delete" selectable.  

7.2.6 SPR_PO in interface reference  
* Expand System>GPS Watch::Library::Location>LOC>LocationProvider  
* Select getLocation and verify the context menu has "Delete" unselectable.  

7.2.7 SPR_RO in interface reference  
* Expand System>GPS Watch::Library::Tracking>LOC>LocationProvider  
* Select getLocation and verify the context menu has "Delete" unselectable.  

7.2.8 C_IO in interface definition  
* Expand LocationInterfaces>LocationProvider  
* Select getLocation and verify the context menu has "Delete" selectable.  

7.2.9 SPR_PS in interface reference  
* Expand System>GPS Watch::Library::Location>LOC>LocationProvider  
* Select registerListener and verify the context menu has "Delete" unselectable.  

7.2.10 SPR_RS in interface reference  
* Expand System>GPS Watch::Library::Tracking>LOC>LocationProvider  
* Select registerListener and verify the context menu has "Delete" unselectable.  

7.2.11 C_AS in interface definition  
* Expand LocationInterfaces>LocationProvider  
* Select registerListener and verify the context menu has "Delete" selectable.  

7.3 Set Type  

7.3.1 C_PP in interface reference  
* Expand System>GPS Watch::Library::Location>LOC>LocationProvider>getLocation  
* Select latitude and verify the context menu has no "Set Type..." entry.  

7.3.2 C_PP in interface definition   
* Expand LocationInterfaces>LocationProvider>getLocation  
* Select latitude and verify the context menu has a "Set Type..." entry.  

7.4 Reorder  

7.4.1 C_PP in interface reference  
* Expand System>GPS Watch::Library::Location>LOC>LocationProvider>getLocation  
* Select latitude and verify the context menu has no "Move Down" or "Move Up" entries.  
* Select longitude and verify the context menu has no "Move Down" or "Move Up" entries.  

7.4.2 C_PP in interface definition   
* Expand LocationInterfaces>LocationProvider>getLocation  
* Select latitude and verify the context menu has both "Move Down" and "Move Up" entries.  
* Select longitude and verify the context menu has both "Move Down" and "Move Up" entries.  

8. User Documentation
---------------------
None; bug fix. 

9. Code Changes
---------------
Fork/Repository: <https://github.com/lwriemen/bridgepoint.git>  
Branch: 8822_provided_parameters

9.1 New Code  

org.xtuml.bp.core::External Entities::Utilities::isProvidedParameter  
<pre>
    Object viewer = null;
    Class<?> explorerViewClass = null;
    Class<?> explorerTreeViewerClass = null;

        try {
            Bundle ui_explorer = Platform.getBundle("org.xtuml.bp.ui.explorer");//$NON-NLS-1$
            explorerViewClass = ui_explorer.loadClass("org.xtuml.bp.ui.explorer.ExplorerView"); //$NON-NLS-1$
            explorerTreeViewerClass = ui_explorer.loadClass("org.xtuml.bp.ui.explorer.ExplorerTreeViewer"); //$NON-NLS-1$
        } catch (Exception cnf) {
            CorePlugin.logError("Problem accessing GraphicsUtil class ", cnf); //$NON-NLS-1$
            return false;
        }

        Class<?>[] type;
        try {
            type = new Class<?>[]{};

            Method getExplorerTreeViewer = explorerViewClass.getMethod("getExplorerTreeViewer", type); //$NON-NLS-1$
            Object[] args = new Object[]{};
            viewer = getExplorerTreeViewer.invoke(explorerViewClass, args);
        } catch (Exception e) {
            CorePlugin.logError("Error invoking getExplorerTreeViewer in GraphicsUtil  ", e); //$NON-NLS-1$
        }

        Tree foundTree = null;
        try {
            type = new Class<?>[]{};
            Method getTree = explorerTreeViewerClass.getMethod("getTree", type); //$NON-NLS-1$
            Object[] args = new Object[]{};
            foundTree = (Tree) getTree.invoke(viewer, args);
        } catch (Exception e) {
            CorePlugin.logError("Error invoking findTreeItem in GraphicsUtil  ", e); //$NON-NLS-1$
        }

        TreeItem[] foundItems = foundTree.getSelection();
        Object parent = foundItems[0].getParentItem().getData();
        if ((parent instanceof InterfaceSignal_c) || (parent instanceof InterfaceOperation_c)) {
            return false;
        }
        return true;
 
</pre>  
org.xtuml.bp.core::Component::Property Parameter::canDelete  
<pre>
return not Util::isProvidedParameter();
</pre>  
org.xtuml.bp.core::Component::Property Parameter::canRename  
<pre>
return not Util::isProvidedParameter();
</pre>  
org.xtuml.bp.core::Component::Provision::canDelete  
<pre>
return not self.isFormal();
</pre>  
org.xtuml.bp.core::Component::Requirement::canDelete  
<pre>
return not self.isFormal();
</pre>  

9.2 Changed Code  
<pre>  
--- a/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Property Parameter/Property Parameter.xtuml     
+++ b/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Property Parameter/Property Parameter.xtuml     
@@ -443,10 +443,12 @@ INSERT INTO O_TFR
'if (param.name == "can" )
   if ( param.value == "move up" )
     select one prev_attr related by self->C_PP[R4021.''succeeds''];
-    return not_empty prev_attr;
+    return (not_empty prev_attr) and not Util::isProvidedParameter();
   elif ( param.value == "move down" )
     select one next_attr related by self->C_PP[R4021.''precedes''];
-    return not_empty next_attr;
+    return (not_empty next_attr) and not Util::isProvidedParameter();
+  elif ( param.value == "SetType")
+    return not Util::isProvidedParameter();
   end if;
 end if;
 return false;
</pre>  

<pre>
--- a/src/org.xtuml.bp.core/sql/context_menu.pei.sql
+++ b/src/org.xtuml.bp.core/sql/context_menu.pei.sql
@@ -334,10 +334,6 @@ INSERT INTO CME VALUES ('New', 'Attribute',                   'SQ_CIP','SQ_AV',
 INSERT INTO CME VALUES ('New', 'Attribute',                   'SQ_CP', 'SQ_CPA',           false , '', '', false, '', false, false, '' ,'->SQ_CPA[R935]');
 INSERT INTO CME VALUES ('',                                   'Unformalize',   'SQ_COP', '', false );
 
-INSERT INTO CME VALUES ('Delete', '',                         'SPR_RO', '',                 true );
-INSERT INTO CME VALUES ('Delete', '',                         'SPR_RS', '',                 true );
-INSERT INTO CME VALUES ('Delete', '',                         'SPR_PO', '',                 true );
-INSERT INTO CME VALUES ('Delete', '',                         'SPR_PS', '',                 true );
 INSERT INTO CME VALUES ('', 'Set To Provider',                         'C_AS', '',                 false );
 INSERT INTO CME VALUES ('', 'Set From Provider',                         'C_AS', '',                 false );
 INSERT INTO CME VALUES ('', 'Set To Provider',                         'C_IO', '',                 false );
@@ -412,6 +408,7 @@ INSERT INTO MEF VALUES ('',       'Move Up',       'SM_EVTDI',    'can', 'move u
 INSERT INTO MEF VALUES ('',       'Move Down',     'SM_EVTDI',    'can', 'move down' );
 INSERT INTO MEF VALUES ('',       'Move Up',       'C_PP',    'can', 'move up' );
 INSERT INTO MEF VALUES ('',       'Move Down',     'C_PP',    'can', 'move down' );
+INSERT INTO MEF VALUES ('Set',    'Type',          'C_PP',    'can', 'SetType' );
 INSERT INTO MEF VALUES ('',       'Move Up',       'O_TFR',    'can', 'move up' );
 INSERT INTO MEF VALUES ('',       'Move Down',     'O_TFR',    'can', 'move down' );
 INSERT INTO MEF VALUES ('',       'Move Up',       'C_AS',    'can', 'move up' );

</pre>

End
---

