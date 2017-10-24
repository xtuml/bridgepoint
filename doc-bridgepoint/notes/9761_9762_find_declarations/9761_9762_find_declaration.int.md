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
<a id="2.5"></a>2.5 [Article that describes #TextEditorContext default](https://stackoverflow.com/questions/22374204/add-context-menu-entry-to-texteditor) This article helped answer a question that the Eclipse documentation [[2.4](#2.4)] did not about editor menu contributions.  
<a id="2.6"></a>2.6 [Example Open Declartion Implemenation](https://www.javatips.net/api/texlipse-master/source/net/sourceforge/texlipse/actions/OpenDeclarationAction.java)  
  

### 3. Background

See the Design note [[2.3](#2.3)]

### 4. Requirements

See the Design note [[2.3](#2.3)]

### 5. Work Required

This section eloborates on each point of the Work Required section of the design note [2.3](#2.3) and
describes additional implemenation as needed to explain implemenation.  

6.1 Determination of item to consider using location  

No addtional details needed, see the design note [[2.3](#2.3)].  

6.2 Resolution  

No addtional details needed, see the design note [[2.3](#2.3)].  

6.3 Open Declaration context menu entry  

The plugin.xml action was implemented in bp.ui.text/arc/create_plugin_xml.arc as follows:  
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
Note that the targetID is set to the default editor target of "#TextEditorContext". This is because no OAL editors currently 
use ```setEditorContextMenuID()``` to override the base text editor context menu id, and such an ID is required for a the editor
popupMenu contribution which is desired in this implemenation. The following resources were referenced during this work: 
[[2.4](#2.4)], [[2.5](#2.5)].  



6.3.1 TODO: This new CME shall be in the Eclipse menu in the section with the other BridgePoint CMEs
6.3.2 TODO: This action shall be tied to the CTRL + Left Mouse (on MAC this is Cmd+Left Mouse)  
6.3.3 TODO: This action shall be tied to the F3 shortcut  
6.3.4 TODO: The Find Declaration CMS shall be shown, but greyed out on invalid activations  


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

doc-bridgepoint/notes/9761_9762_find_declarations/
    9761_9762_find_declarations_matrix.txt
doc-bridgepoint/notes/9761_9762_find_declarations/
    9761_9762_find_declarations.dnt.md
doc-bridgepoint/notes/9761_9762_find_declarations/
    9761_9762_find_declarations.int.md
doc-bridgepoint/review-minutes/9761_9762_find_declarations.dnt_rvm.md

org.xtuml.bp.ui.text/arc/create_plugin_xml.arc
org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/activity/
    OpenDeclarationAction.java

</pre>

### End

