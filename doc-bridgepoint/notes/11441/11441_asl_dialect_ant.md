---

This work is licensed under the Creative Commons CC0 License

---

# Add ASL dialect into BridgePoint
### xtUML Project Analysis Note


### 1. Abstract

This note captures the analysis of the changes required in order to support the 
new ASL dialect in BridgePoint.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #11441](https://support.onefact.net/issues/11441) Headline issue    
<a id="2.2"></a>2.2 [BridgePoint DEI #8417](https://support.onefact.net/issues/8417) Storing action semantics as files  
<a id="2.3"></a>2.3 [ASL Reference Manual](http://www.ooatool.com/docs/ASL03.pdf)   
<a id="2.4"></a>2.4 [BridgePoint DEI #9717](https://support.onefact.net/issues/9717)  Interface management errors  
<a id="2.5"></a>2.5 [BridgePoint DEI #10139](https://support.onefact.net/issues/10139)  Published interface operations have wrong dialect in MASL mode  

### 3. Background

Project Caledonia adds support for models migrated from iUML into BridgePoint that 
use the ASL [2.3] action language.  To facilitate this, ASL must be added as a 
supported dialect inside BridgePoint alongside OAL and MASL.  

### 4. Requirements

4.1 ASL action language shall be persistable in activity bodies inside an xtUML model.     
4.2 ASL action language shall be editable in activity bodies inside an xtUML model.     

### 5. Analysis

5.1  BridgePoint models currently store action bodies as either OAL (the default) or MASL.  OAL is
persisted in `Action_semantics` fields on action instances (e.g. `S_SYNC` or 'O_TFR').  For MASL the
action bodies are persisted in separate `*.masl` files that are siblings to the corresponding `*.xtuml`
files that contain the instances.  The MASL Snippet editor reads and writes MASL activities from/to 
the appropriate `*.masl` file.   
5.1.1  ASL shall be stored like OAL inside `*.xtuml` files.  

5.2  ASL shall be supported as a valid "dialect".  The action body dialect is stored in the `Dialect`
attribute on every action instance as a sibling attribute to the `Action_semantics` field.  This dialect
attribute is read by action language of metamodel actions, hand-craft Java and archetypes.    

The following places use or manipulate the dialect and must be updated to support ASL as well as OAL and MASL:   

5.2.1 Preferences : "Default Action Language"   
The action language preferences must be updated on the user interface and in the code that handles settings
coming from `plugin_customization.ini`. 
  - 1 day
    
5.2.2 Documentation  
All documentation that refers to the dialect attribute must be updated.  Additionally, any screenshots or 
documentation about default action language must be updated to reflect ASL as a valid choice.    
  - 0.5 day
    
5.2.3 `ooaofooa` Metamodel   
We analyzed places in the metamodel of BridgePoint where the `Dialect` attribute is read or used to make
decisions.  This turned up a number of places where changes are required:  
5.2.3.1 Add "asl" to `ooaofooa` enum `ActionDialect`   
5.2.3.2 Provided and Required Interface initializations   
5.2.3.3 `Transition::initialize()` - disallow state transition actions when modeling with ASL    
5.2.3.4 `Attribute::migrateBaseToDerived()` - disallow when using ASL    
  - 1 day

5.2.4 `io.core/.../export_functions.inc` (105 & 1106)   
5.2.4.1 There is handling here to export MASL action bodies.  This will need to be updated to handle ASL.    
  - 1 day
    
5.2.5 `io.core/arc/gen_import_java.inc` (730 & 965)   
5.2.5.1  The import generating archetype must be updated to add ASL handling  
  - 1 day

5.2.6 `io.core` and `io.mdl`   
5.2.6.1 These plugins handle the import and export of an ASL model. Obviously they must be able
to handle models that use ASL.  This work is a key component of Project Caledonia and is not 
so much a factor of the dialect attribute this issue focuses on.  Further analysis of import and export
is left to other issues in the project.     
    
5.2.7 `ui.explorer/arc/create_explorer_view.inc` and `ui.graphics/src/org/xtuml/bp/ui/graphics/editor/GraphicalEditor.java`   
5.2.7.1 Update to handle ASL as a valid dialect & open ASL editor when activities are opened 
in model explorer.   
  - 1 day
    
5.2.8 `ui.properties/arc/BuildPropertySource.arc` and `ui.properties.test`  
5.2.8.1 Update to handle ASL activities displayed and editable from the Properties view.  
  - 1 day
    
5.2.9 `ui.text`   
5.2.9.1 Create `ShowASLAction` to go along with actions to show OAL or MASL.  
5.2.9.2 Note that this does not include the creation of the ASL editor itself. Just the hook to 
open the proper editor.   
  - 1 day
    
5.3  Additional notes  
5.3.1 BridgePoint currently runs automatic synchronization of references for MASL [2.4]. The development team
had a discussion and decided that ASL shall work like OAL (and not perform automatic synchronization) rather 
than behave like models that use MASL.    
    
5.3.2 The BridgePoint MASL modeling idiom occasionally sets the dialect to "None" on interface messages to 
denote places where no action language should exist [2.5].  The development team discussed this and decided that 
for models that use ASL, they will be handled like models that use MASL rather than following the handling for
OAL.
  
### 6. Work Required

The places to be modified are detailed in the Analysis section of this note.   

### 7. Acceptance Test

7.1  ASL models shall be put through round trip testing using BridgePoint, including editing
of the model inside the tool.   
7.1.1  Editing shall be initiated via the Model Explorer and Properties views.  
7.2  Use the preferences to set the default action language to ASL in the UI.   
7.3  Use the preferences to set the default action language to ASL in the INI file.   
7.4  Set the default action language to ASL and verify correct behavior when using "Publish to interface"    

### End
