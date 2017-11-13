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
<a id="2.8"></a>2.8 [dts0100856831 implementation note](https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20121102/technical/notes/dts0100856831.int) This is an old implementation note from when `MultipleOccurrenceElement` was first introduced  

### 3. Background

See the Design note [[2.3]](#2.3)

### 4. Requirements

See the Design note [[2.3]](#2.3)

### 5. Work Required

This section elaborates on each point of the Work Required section of the design
note [[2.3]](#2.3) and describes additional implementation as needed to explain
implementation.

5.1 Determination of item to consider using location

No additional details needed, see the design note [[2.3]](#2.3).

5.2 Resolution

No additional details needed, see the design note [[2.3]](#2.3).

5.3 Open Declaration context menu entry

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

5.3.1 This new CME shall be in the Eclipse menu in the section with the other BridgePoint CMEs

This is complete, but note that there are no other BP menu items in the OAL Editor popup menu.

5.3.2 Work to tie the F3 shortcut to the Open Declaration action

The following references were helpful in this task: [[2.7]](2.7). The
implementation is found in `bp.ui.text/arc/create_plugin_xml.arc`. A handler
class, `org.xtuml.bp.ui.text.activity/OpenDeclarationHandler.java` that extends
`AbstractHandler` was added to hander the F3 command processing. Note that the
menu action handler, `org.xtuml.bp.ui.text.activity/OpenDeclarationAction.java`
are both entry points that perform the same action.

5.3.3 OAL operations

The bulk of the work to find declarations is done as instance based OAL
operations. The entry point for these is `findDeclaration` on `ACT_ACT` there
are also operations on `ACT_SMT`, `ACT_BLK`, `V_VAL`, `V_LOC`, and `V_VAR`.

### 6. Implementation Comments

6.1 `MultipleOccurrenceElement`

The `MultipleOccurrenceElement` class was introduced to support elements that
can show up in more than one place in the tree (see [[2.8]](#2.8)).

For this work, it was necessary to select the element in the Model Explorer
based on the OOA of OOA instance. Some work had to be done to consistently map
from the OOA instance to the `MultipleOccurrenceElement` and get the same
element each time.

### 7. Unit Test

7.1 New unit tests

A new suite of unit tests was introduced as part of this work. The matrix and
implementation can be found in `org.xtuml.bp.ui.text.test` in the `bptest`
repository.

7.1.1 Conditional tests

The matrix itself consists of > 20,000 tests, however a reasonable subset (<
1,000 tests) has been chosen. The generator utilizes the `-c` flag to only
generate the subset and conditionally exclude the rest of the tests. The full
suite could be run by removing the `-c` option in the `generate.xml` file.

7.2 The existing unit tests shall pass

### 8. User Documentation

8.1 A new table has been added to the "BridgePoint Context Menu Tools" to
describe the menu items specific to the activity editor. The "Open Declaration"
tool has been described there.

8.2 The Activity Editor page in the "xtUML Modeling Perspective > Editors"
section has been modified to include a paragraph about the "Open Declaration"
tool. It has additionally been converted to markdown.

### 9. Code Changes

Fork/Repository: rmulvey/bridgepoint  
Branch: 9761_9762_find_declaration  

<pre>

 doc-bridgepoint/notes/9761_9762_find_declarations/9761_9762_find_declaration.int.md                                          | 155 ++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/9761_9762_find_declarations/9761_9762_find_declarations.dnt.md                                         | 157 +++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/process/templates/launch_configs/Open Declarations Test (OSX).launch                                         | 681 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/process/templates/launch_configs/Open Declarations Test.launch                                               | 681 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/review-minutes/9761_9762_find_declarations.dnt_rvm.md                                                        |  51 +++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Body/Block/Block.xtuml                                               |  68 +++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Body/Body/Body.xtuml                                                 | 191 +++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Body/Statement/Statement.xtuml                                       | 273 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Value/Value/Value.xtuml                                              | 208 +++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Value/Variable Location/Variable Location.xtuml                      |  23 ++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Value/Variable/Variable.xtuml                                        |  74 +++++++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/MultipleOccurrenceElement.java                                            |  75 +++++++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/OALPersistenceUtil.java                                                   |  11 +++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/util/UIUtil.java                                                                 |  62 +++++++++-------
 src/org.xtuml.bp.doc/README.md                                                                                               |   1 +
 src/org.xtuml.bp.doc/Reference/UserInterface/BridgePointContextMenuTools/BridgePointContextMenuTools.html                    |  23 +++++-
 src/org.xtuml.bp.doc/Reference/UserInterface/BridgePointContextMenuTools/BridgePointContextMenuTools.md                      |  13 +++-
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/Editors/HTML/Activity.htm                                         | 259 ----------------------------------------------------------------
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/Editors/HTML/Activity.html                                        |  55 ++++++++++++++
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/Editors/HTML/Activity.md                                          |  50 +++++++++++++
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/Editors/HTML/Editors.htm                                          |   2 +-
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/Editors/HTML/{Activity_files/image001.jpg => activity_editor.jpg} | Bin
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/HTML/xtUMLModeling.htm                                            |   2 +-
 src/org.xtuml.bp.doc/topics_Reference.xml                                                                                    |   2 +-
 src/org.xtuml.bp.ui.explorer/arc/create_adapters.inc                                                                         |   3 +-
 src/org.xtuml.bp.ui.explorer/arc/create_content_provider.inc                                                                 |   1 +
 src/org.xtuml.bp.ui.explorer/arc/create_label_provider.inc                                                                   |   1 +
 src/org.xtuml.bp.ui.explorer/generate.xml                                                                                    |   2 +-
 src/org.xtuml.bp.ui.explorer/src/org/xtuml/bp/ui/explorer/MultipleOccurrenceElement.java                                     |  49 ------------
 src/org.xtuml.bp.ui.text/arc/create_plugin_xml.arc                                                                           |  42 +++++++++++
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/EditorConfiguration.java                                                   |   9 ++-
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/activity/ActivityEditor.java                                               |  24 +++---
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/activity/OpenDeclarationAction.java                                        | 305 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/activity/OpenDeclarationHandler.java                                       |  23 ++++++
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/editor/BPTextDefaultTextDoubleClickStategy.java                            |  36 +++++++++
 utilities/build/build_project.sh                                                                                             |  15 ++++
 36 files changed, 3273 insertions(+), 354 deletions(-)

</pre>

Fork/Repository: leviathan747/bptest  
Branch: 9761_9762_find_declaration  

<pre>

 src/org.xtuml.bp.test/UnitTestGenerator.pl                                                              |   53 ++-
 src/org.xtuml.bp.ui.explorer.test/src/org/xtuml/bp/ui/explorer/test/ExplorerTest.java                   |    2 +-
 src/org.xtuml.bp.ui.text.test/META-INF/MANIFEST.MF                                                      |    5 +-
 src/org.xtuml.bp.ui.text.test/generate.xml                                                              |   36 ++
 src/org.xtuml.bp.ui.text.test/matrices/non_generated/9761_9762_find_declarations_matrix.txt             | 4014 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.text.test/pom.xml                                                                   |   47 +++
 src/org.xtuml.bp.ui.text.test/src/org/xtuml/bp/ui/text/test/opendeclarations/.gitignore                 |    2 +
 src/org.xtuml.bp.ui.text.test/src/org/xtuml/bp/ui/text/test/opendeclarations/OpenDeclarationsTests.java |  843 ++++++++++++++++++++++++++++++++++++++++
 8 files changed, 4985 insertions(+), 17 deletions(-)

</pre>

### End
