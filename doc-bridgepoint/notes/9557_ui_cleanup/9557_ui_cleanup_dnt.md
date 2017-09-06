---

This work is licensed under the Creative Commons CC0 License

---

# User Interface Enhancements and Simplification 
### xtUML Project Design Note


### 1. Abstract

This note describes work to enhance and simplify the BridgePoint user 
interface.  This is done in response to user requests and specific
requirements, which are detailed below.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9557](https://support.onefact.net/issues/9557) Saab-9: Mix of MASL and OAL terms in the same perspective.    
<a id="2.2"></a>2.2 [BridgePoint DEI #9558](https://support.onefact.net/issues/9558) User Experience: Remove or filter menu items    
<a id="2.3"></a>2.3 [BridgePoint DEI #9563](https://support.onefact.net/issues/9563) Saab- 8: Unrelated and/or undocumented options in context menu   
<a id="2.4"></a>2.4 [Papyrus for Information Modeling Customization Guide](https://wiki.eclipse.org/Papyrus_for_Information_Modeling/Customization_Guide)   

### 3. Background

Users request that menu items be reduced and clarified.  In addition, the
introduction of MASL action language is presenting some confusion for long-
time OAL users.  Users want to reduce this confusion by not presenting MASL-
specific items when they are using OAL.  

### 4. Requirements

The requirements derive from the SRS for the Saab 2017 Issue list and 
associated issues [[2.1]](#2.1) [[2.2]](#2.2) [[2.3]](#2.3).   

4.1  BridgePoint shall not present any dialect specific menu items that are not
  relevant to the dialect the user is working with.  

4.2  BridgePoint shall provide a mechanism to allow the user hide unwanted model
  element creation tools in the palette and context menus.   

4.3  The mechanism provided to resolve 4.2 shall be reusable and shareable 
  between unique BridgePoint installations.   

4.4  The “Require MASL-style identifiers for model elements” shall be renamed 
  to remove “MASL” from the title.   

4.5  The menu options shall be analyzed to determine where they can be 
  simplified. Figures 1 through 5 provide examples of menu items that are prime 
  candidates for removal from user view.  

| | | |
| ---------- | ---------- | ----------- |  
| ![Figure 1](bp_exit.jpeg) | ![Figure 2](bp_help.jpeg) | ![Figure 3](bp_window_menu.jpeg)|   
| **Figure 1** | **Figure 2** |  **Figure 3** |    
| ![Figure 4](bp_menu.jpeg) | ![Figure 5](bp_model_explorer_rightclick_context_menu.jpeg) | |    
| **Figure 4** | **Figure 5** | .  |  

4.5.1  Another menu screenshot with items to be addressed was added after the
  original SRS:  

| |   
| --- |   
| ![Figure 6](screenshot0.png) |   
| **Figure 6** |  

4.6  All BridgePoint menu contributions shall be analyzed to assure each is 
  documented and the documentation is up to date.   

4.7  The number of BridgePoint menu contributions shall be reduced.  

4.8  BridgePoint shall not present the modeler with menu items related to 
  BridgePoint tool development.  

4.9  Only BridgePoint menu contributions shall be modified.   

### 5. Analysis

5.1  BridgePoint is now delivered in two different forms: "Developer" and
  "Modeler" versions.  The goal is to separate functionality used to
  develop and build BridgePoint itself from core modeling and translation
  functionality that all modelers use.  The Modeler version is what is
  delivered to users.  BridgePoint developers must download the Developer
  version on their own.  

  This tool packaging distinction can be leveraged even further during this
  work.  
   
  BridgePoint has tooling in `bp.utilities` and `bp.internal.tools` 
  that can be reorganized to address requirements to not present normal users
  with developer tools.   
    
5.1.1  The items called out in Figures 1-4 of 4.5 are already addressed in
  the original work to create the Modeler version.    
    
5.2  The requirements will be addressed with several distinct changes:  
  * Programmatic and declarative updates  
  * Reorganization of underlying BridgePoint code
  * Utilizing an user-controlled configuration file  
  
5.3  The Papyrus for Information Modeling (PIM) version of Papyrus was 
  specifically created to simplify the Papyrus UI and make it 
  more acceptable for the specific use case of information modeling. The 
  approach and methods of reduction used in that effort [[2.4]](#2.4) 
  will inform this work.  

5.4  Graphical symbols  
5.4.1  In `bp.ui.graphics/plugin.xml` the "symbol" stanzas (e.g. at 
  line 874) are what define elements for the Graphics (Canvas and Palette). If 
  one comments out the symbols they go away from the palette.  However, 
  commenting out the symbol also removes it from being able to be used on
  the canvas and makes it impossible to see the removed elements on existing 
  diagrams that include the elements.  

5.4.2  Disabling these symbols does not affect the `New >` context menu.  

5.4.3  `bp.ui.graphics` is shipped as a JAR file, so this makes it harder 
  (not very use friendly) for us to simply swap out one `plugin.xml` for 
  another that has restricted UI.  We would have to change the plugin to ship
  as a directory if we wanted the user to modify the plugin contents at all.  

### 6. Work Required  

6.1  Update preference text   
6.1.1  The “Require MASL-style identifiers for model elements” shall be 
  renamed to "Enable restricted identifier naming for model elements".  
6.1.2  The hover tip mentions this is the “MASL standard”.  This mention
  of MASL shall be removed.  
6.1.3  The changes need to be made in `org.xtuml.bp.core/.../BridgePointPreferences.java`   
  
6.2  Additional Separation of Developer Tooling   
6.2.1   Create new developer tools "parent" and feature plugins. Use
  the `org.xtuml.bp.mctools[.parent]` as a guide.
6.2.1.1  Make the `bp.internal.tools` plug-in a child of the new feature. 
6.2.1.2  Adjust the Developer product to include the new parent plug-in, but 
  do not include it in the Modeler product.   
6.2.1.3  Add the new parent to the `org.xtuml.bp.releng.parent/pom.xml`.   
6.2.1.4  Remove the internal.tools plugin from `org.xtuml.bp.pkg-feature/feature.xml`  

6.2.2  Move the following from `bp.utilities` to `bp.internal.tools`.  Move both the 
  Java code and the associated UI declaration in `plugin.xml`: 
  * Load and Persist   
  * Generate Functions From List  

6.2.3  The `CreateTestProjectAndImportTestModel` and `FixMissingMatrixEntryAction` 
  are no longer used and shall be removed.   

6.3  User Configuration of the Palette and CMEs  
6.3.1  BridgePoint will now use system properties to control what elements shall be
  excluded on the user interface.  These system properties will be of the form
  `-Ddisable.bp.<label>` and if set to true will cause the element to be hidden
  from the UI.  The use can set these properties in the `bridgepoint.ini` 
  file. Developers can set the value in the debug application launch.  
  
6.3.2  Modifying the Palette
  The solution proposed here does not rely on the user modifying the 
  `plugin.xml` file.  Instead, `bp.ui.graphics/.../GraphicsCreationToolEntry.java`
  is modified in the constructor.  The symbols (see 5.4) are still loaded
  from `plugin.xml` but the constructor is modified to use the information
  read from the configuration file (see 6.3.1).  If the tool being processed in the 
  constructor matches one of the excluded tools from the configuration file
  then the newly created tool's visibility is set to false.  
  
```
public GraphicsCreationToolEntry(String label, String shortDesc,
         CreationFactory factory, ImageDescriptor iconSmall,
         ImageDescriptor iconLarge, int ooaType) {
  super(label, shortDesc, iconSmall, iconLarge, GraphicsCreationTool.class);
  setToolProperty(CreationTool.PROPERTY_CREATION_FACTORY, factory);
  type = ooaType;
+ // Look for a system property "disable.bp.<label>=true"
+ // if one is found, then the label is meant to be excluded so 
+ //   call setVisible(false);
}
```

6.3.3  Modifying the CMEs
  There are entries to be restricted (i.e. hidden by the user) in the context 
  menu both in the `New >` submenu and at the top level.  For example,
  `New > Actor` and `Export MASL Domain`.                  

6.3.3.1  Use the config file system properties (see 6.4.1) and the 
  "visibility" modifier on menu contributions in `org.xtuml.bp.core/plugin.xml`.  These
  checks will hide the CMEs if a match is found in the configuration.  

6.3.3.2  Update the archetype `create_core_pluin.inc` to add the visiblity stanza to the 
  objectContributions.  

```xml
    <objectContribution
      adaptable="true"
      objectClass="org.xtuml.bp.core.Package_c"
      id="org.xtuml.bp.core.ui.InteractionActorOnEP_PKGAction">
        <action
          label="Actor"
          class="org.xtuml.bp.core.ui.InteractionActorOnEP_PKGAction"
          menubarPath="org.xtuml.bp.ui.newroot/org.xtuml.bp.ui.interactionroot/org.xtuml.bp.ui.newinteractionmenu"
          enablesFor="1"
          id="org.xtuml.bp.core.ui.InteractionActorOnEP_PKGAction">
        </action>
+       <visibility>
+         <not>
+           <systemProperty name="disable.bp.Actor" value="true"/>
+         </not>
+       </visibility>
    </objectContribution>
```  

6.3.3.3  Modify `org.xtuml.bp.x2m/plugin.xml` and `org.xtuml.bp.ui.marking/plugin.xml`
  to use the same system property visiblity checks on the `objectContributions` that
  add the "Export MASL ..." and "Manage Project Markings" context menu entries.  


### 7. Design Comments
None.  

### 8. Documentation  
TODO  

### 9. Acceptance Test

9.1 Item 1  
9.2 Item 2  

### End
