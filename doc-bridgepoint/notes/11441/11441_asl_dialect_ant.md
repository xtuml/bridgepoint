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

### 3. Background


### 4. Requirements

4.1 Item 1  
4.2 Item 2  

### 5. Analysis


### 6. Work Required

- Preferences : "Default Action Language"
    - UI and INI setting handling
    - 1 day
    
- Documentation
    - 1 day
    
- ooaofooa
  - add "asl" to ooa enum ActionDialect
  - Provided and Required Interface initializations
  - Transition::initialize() - disallow state transition actions
  - Attribute::migrateBaseToDerived - disallow in ASL
  - 1 day

- io.core/.../export_functions.inc (105 & 1106)
    - There is handling here to export MASL action bodies.  I think this will need
    to be updated to handle asl
    - 1 day
    
- io.core/arc/gen_import_java.inc (730 & 965)
    - update to add asl handling
    - 1 day

- io.core and io.mdl
    - Handle the import and export of an ASL model.  Captured by Levi as part of 11363
    
- org.xtuml.bp.ui.explorer/arc/create_explorer_view.inc
- org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/editor/GraphicalEditor.java  
    - Update to handle asl as a valid dialect & open ASL editor
    - 1 day
    
- org.xtuml.bp.ui.properties/arc/BuildPropertySource.arc
- org.xtuml.bp.ui.properties.test
    - Update to handle ASL
    - 1 day
    
- org.xtuml.bp.ui.text
    - Create ShowASLAction to go along with actions to show OAL or MASL
    - 1 day
    - This DOES NOT include the creation of the editor itself.  Just the hook to 
    open the proper editor
    

- We currently run automatic synchronization of references for MASL (9717).  Shall we 
do this for ASL or not?
  - NO.  Will work like OAL.
    
- How to handle "Publish to interface" for ASL... like MASL, or not? (10139)


### 7. Acceptance Test

TODO

### End
