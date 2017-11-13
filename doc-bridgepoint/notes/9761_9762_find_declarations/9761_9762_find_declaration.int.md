---

This work is licensed under the Creative Commons CC0 License

---

# Introduce a mechanism that allows OAL Editor users to navigate to declarations
### xtUML Project Implementation Note

### 1. Abstract

This note describes the changes required to support opening declarations for the selection or cursor location in an OAL editor.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9761](https://support.onefact.net/issues/9761)  AE8-When a variable representing an OAL instance is selected in the editor, a CME shall be present that allows the user to find the declaration of the instance.  
<a id="2.2"></a>2.2 [BridgePoint DEI #9762](https://support.onefact.net/issues/9762) AE9-When a declaration is found using Find Declaration, the user shall be able to select it to navigate to the declaration.  
<a id="2.3"></a>2.3 [Design Note](9761_9762_find_declarations.dnt.md) The engineering design note for this issue.  
<a id="2.4"></a>2.4 [Contributing Actions to the Eclipse Workbench](https://www.eclipse.org/articles/Article-action-contribution/index.html) Eclipse Documentation.  
<a id="2.5"></a>2.5 [Article that describes #TextEditorContext
default](https://stackoverflow.com/questions/22374204/add-context-menu-entry-to-texteditor) This article helped answer a question that the Eclipse documentation [[2.4]](#2.4) did not about editor menu contributions.  
<a id="2.6"></a>2.6 [Example Open Declartion Implemenation](https://www.javatips.net/api/texlipse-master/source/net/sourceforge/texlipse/actions/OpenDeclarationAction.java)  
<a id="2.7"></a>2.7 The following resources were useful in implementing the Open Declaration keybinding to F3  
* [FAQ How do I provide a keyboard shortcut for my action?](https://wiki.eclipse.org/FAQ_How_do_I_provide_a_keyboard_shortcut_for_my_action%3F)  
* [Eclipse Help - Contexts and Keybinings](https://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2FwrkAdv_keyBindings_contexts.htm)  
* [Eclipse Help - Commands](https://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2Fworkbench_cmd_commands.htm)  
* [Eclipse - Platform Command Framework](https://wiki.eclipse.org/Platform_Command_Framework)  
* [Simple example - this was the most help of all](http://wiki.bioclipse.net/index.php?title=How_to_add_menus_and_actions)  

### 3. Background

See the Design note [[2.3]](#2.3)

### 4. Requirements

See the Design note [[2.3]](#2.3)

### 5. Work Required

This section elaborates on each point of the Work Required section of the design
note [[2.3]](#2.3) and describes additional implementation as needed to explain
implementation.

6.1 Determination of item to consider using location

No additional details needed, see the design note [[2.3]](#2.3).

6.2 Resolution

No additional details needed, see the design note [[2.3]](#2.3).

6.3 Open Declaration context menu entry

The `plugin.xml` action was implemented in
`bp.ui.text/arc/create_plugin_xml.arc` as follows:  
```
   <extension
        point="org.eclipse.ui.popupMenus">
        <viewerContribution
            id="org.xtuml.bp.ui.text.activity.opendeclaration"
            targetID="#TextEditorContext">
         <action
               id="org.xtuml.bp.ui.text.activity.OpenDeclarationAction"
               label="Open Declaration"
               tooltip="Open the associated Model Element declaration for this reference."
               menubarPath="org.xtuml.bp.ui.context-internal"
               class="org.xtuml.bp.ui.text.activity.OpenDeclarationAction">
         </action>
      </viewerContribution>
   </extension>
```

Note that the targetID is set to the default editor target of
"#TextEditorContext". This is because no OAL editors currently use
`setEditorContextMenuID()` to override the base text editor context menu id, and
such an ID is required for the editor popupMenu contribution which is desired in
this implementation. The following resources were referenced during this work:
[[2.4]](#2.4), [[2.5]](#2.5).

6.3.1 This new CME shall be in the Eclipse menu in the section with the other BridgePoint CMEs

This is complete, but note that there are no other BP menu items in the OAL Editor popup menu.

6.3.2 Work to tie the F3 shortcut to the Open Declaration action

The following references were helpful in this task: [[2.7]](2.7). The
implementation is found in `bp.ui.text/arc/create_plugin_xml.arc`. A handler
class, `org.xtuml.bp.ui.text.activity/OpenDeclarationHandler.java` that extends
`AbstractHandler` was added to hander the F3 command processing. Note that the
menu action handler, `org.xtuml.bp.ui.text.activity/OpenDeclarationAction.java`
are both entry points that perform the same action.

6.3.3 TODO: Call out the implementation location that is used by BOTH
`OpenDeclarationHandler.java` and `OpenDeclarationAction.java` to actually
perform the work.

### 6. Implementation Comments

None

### 7. Unit Test

TODO

### 8. User Documentation

TODO

### 9. Code Changes

Fork/Repository: rmulvey  
Branch: 9761_9762_find_declaration  

<pre>

</pre>

### End
