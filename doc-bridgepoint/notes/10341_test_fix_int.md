---

This work is licensed under the Creative Commons CC0 License

---

# JUnit Test Cleanup
### xtUML Project Implementation Note


### 1. Abstract

This note describes work performed to clean up test cases.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10341](https://support.onefact.net/issues/10341) Headline issue   
<a id="2.2"></a>2.2 [BridgePoint DEI #9481](https://support.onefact.net/issues/9481) Build BridgePoint with new generator    
<a id="2.3"></a>2.3 [BridgePoint DEI #9716](https://support.onefact.net/issues/9716) BridgePoint hangs on CME activation (deadlock)    

### 3. Background

The recent update to pyrsl2 [2.2] highlighted the fact that some JUnit test cleanup was needed.  The test
failures need to be analyzed, run locally for comparison and fixed if possible.  
 
### 4. Requirements

4.1  Fix unit tests 

### 5. Work Required

5.1  Fix for canvas test `testCopyPackageToSystem`  
The issue was that the item being pasted should have been pasted as a sibling to the package
already on the canvas but was instead being pasted inside the package.  Modified the code
to manually select a paste point on the canvas to make sure the pasted element ended up as a 
sibling.  

5.2  Modified core test `testCanRenameAndCanDeleteCurrentStateAttribute`  
This test modified the name of a data type.  It turns out this has a negative effect that
causes a failure of a following test.  Modified this test case to put the original name 
back after doing the change and check.  

5.3  Update CME PEI data   
5.3.1  In pyrsl2, the tool actually creates links of the instances and to do this it requires
that referential attributes (even ones that are part of a set) cannot be empty strings.  This 
change in behavior caused failures in the filtering of context menu entries.  Specifically, 
none of the filtering was working as seen in manual testing and exposed in the JUnit test case
failure of `testRemoveSignal`.   

5.3.2  The context menu model uses a field named `Specialism` that serves as a means to handle
special cases.  Here is the description from the model itself:  
```
A value that dictates whether special processing is needed for the menu
entry. For example, a value of 'Rename' adds code to allow the user to
provide the new value in an in-place edit box and a value of 'New' places
the menu entry under a combined 'New' submenu.

Initially, valid values were 'Rename', 'New' and 'Delete'. No enumeration is
used to allow new specialisms to be added without requiring a change to
the model.
```  
5.3.2.1  Using pyrsl2, the PEI's Menu Entry Filter (MEF) instances were all invalid because
the empty string used as the default value of `Specialism` was now causing all the MEF's 
referential attributes to be wiped out during the batch relate process in the new generator.  
5.3.2.2  The chosen fix was to change the default value from empty string to "--".  With this
change the referential attributes of the MEF instances were all valid and the CME and MEF 
instances are linked properly.  The aforementioned `Specialism` description field is appended
with the following text:  
```
With the move to pyrsl2, referential attributes are not allowed to be empty.  Thus issue
#10341 changed the empty string for "Specialism" to be "--".  This value is special cased
in the archetypes to mean the same thing empty string previously did.
```  
I chose to take this default string change approach rather than converting `Specialism` to
an enumeration to maintain the original design and have the least amount of fallout on the 
model and archetypes.  
5.3.2.3  The change to "--" necessitated a few changes to the archetypes to special case
places where the `Specialism` field is used.  In these cases if we see "--" we now skip 
appending the specialism into the textual name of the entry.  

5.3.3  Remove invalid entries  
An inspection of the MEF instance data at runtime after the Specialism change revealed several
cases where MEF instances still had bad referentials.  These showed in the build output as:
```
[exec] create_core_plugin.inc: 176:  INFO: MEF ('None', 'None', 'None', 'new', 'imported component')
[exec] create_core_plugin.inc: 176:  INFO: MEF ('None', 'None', 'None', 'can', 'formalize generic pkg')
[exec] create_core_plugin.inc: 176:  INFO: MEF ('None', 'None', 'None', 'can', 'unformalize')
[exec] create_core_plugin.inc: 176:  INFO: MEF ('None', 'None', 'None', 'can', 'formFunc')
[exec] create_core_plugin.inc: 176:  INFO: MEF ('None', 'None', 'None', 'can', 'formCommFunc')
```  

Each case is broken down below showing the error message and the corresponding entry from the PEI data
and followed with the analysis and action taken:
```
[exec] create_core_plugin.inc: 176:  INFO:    SKB MEF ('None', 'None', 'None', 'new', 'imported component')
INSERT INTO MEF VALUES ('New',    'Imported Component',       'C_C',   'new', 'imported component' );
```
* In `C_C::actionFilter` there is only "new" handling for package.  Not "component" or "imported component"
  and yet the New CME on a component has Package and Component Reference in it.  Remove this new>package 
  action filter code
* Removed all of these C_C "New" MEF entries from the context menu pei file
    
```
[exec] create_core_plugin.inc: 176:  INFO:    SKB MEF ('None', 'None', 'None', 'can', 'formalize generic pkg')
[exec] create_core_plugin.inc: 176:  INFO:    SKB MEF ('None', 'None', 'None', 'can', 'unformalize')
INSERT INTO MEF VALUES ('Generic Package',        'Formalize',  'C_DG',    'can', 'formalize generic pkg' );
INSERT INTO MEF VALUES ('--',    'Unformalize',  'C_DG',    'can', 'unformalize' );
```
* No corresponding CME instances, MEF entries removed  
    
```
[exec] create_core_plugin.inc: 176:  INFO:    SKB MEF ('None', 'None', 'None', 'can', 'formFunc')
INSERT INTO MEF VALUES ('Function',    'Formalize',  'MSG_SM',    'can', 'formFunc' );
```
* No corresponding CME instance, MEF entry removed  
    
```
[exec] create_core_plugin.inc: 176:  INFO:    SKB MEF ('None', 'None', 'None', 'can', 'formCommFunc')
INSERT INTO MEF VALUES ('Communication Function',    'Formalize',  'MSG_SM',    'can', 'formCommFunc' );
```
* No corresponding CME instance, MEF entry removed  
   

### 6. Implementation Comments

6.1  While performing manual testing I ran into a CME deadlock consistently.  I added the 
test case to [2.3].    

### 7. Unit Test

7.1  Run unit tests locally and on the server and verify error/failure numbers decreased from 6e/6f    

### 8. User Documentation

None.

### 9. Code Changes

Fork/Repository: __keithbrown/bridgepoint__ 
Branch: __10341_test_fix__

<pre>
doc-bridgepoint/notes/10341_test_fix_int.md

doc-bridgepoint/notes/10341_test_fix_int.md
src/org.xtuml.bp.core/arc/cme_names.inc
src/org.xtuml.bp.core/arc/create_core_plugin.inc
models/org.xtuml.bp.core/context_menu/Context Menu/Context Menu Entry/Context Menu Entry.xtuml
models/org.xtuml.bp.core/ooaofooa/Component/Component/Component.xtuml
models/org.xtuml.bp.core/ooaofooa/Component/Delegation/Delegation.xtuml
src/org.xtuml/bp.core/sql/context_menu.pei.sql
</pre>

Fork/Repository: __keithbrown/bptest__ 
Branch: __10341_test_fix__

<pre>
src/org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/CanRenameCanDeleteTestGenerics.java
src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasCopyPasteTests.java
</pre>

### End

