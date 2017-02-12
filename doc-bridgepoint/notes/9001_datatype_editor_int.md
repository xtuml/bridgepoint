---

This work is licensed under the Creative Commons CC0 License

---

# Validating cell editor for Data Type Definition
### xtUML Project Implementation Note

1. Abstract
-----------
UserDataTypes have a property 'Data Type Definition' which holds their MASL
signature as a string. A validating editor is needed for these properties, i.e.
when the user selects the property in the properties view, an editor opens that
validates whether the MASL signature is correct with highlighting and content
assist.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#9001 Validating cell editor for Data Type Definition](https://support.onefact.net/issues/9001)  

3. Background
-------------
The original plan was to use a cell editor right in the properties view. The
plan has been changed to use an editor that operates on in a main editor tab.

4. Requirements
---------------
4.1 BridgePoint shall supply an editor to edit UserDataType definitions  
4.1.1 The editor shall open from the properties view, context menu, and be
default for double-click of a UserDataType.  
4.1.2 The editor shall be Xtext based and provide all the highlighting and
content assist features of the regular editor  

5. Work Required
----------------

6. Implementation Comments
--------------------------

7. Unit Test
------------

These tests use the SAC model

7.1 Access test  
7.1.1 Navigate to SAC::SAC::SAC::types  
7.1.2 Double-click on "group_type". Verify that the type definition is
opened for edit. Close the editor.  
7.1.3 Right click then _Open with > Type Definition Editor_. Verify that the
type definition is opened for edit. Close the editor.  
7.1.4 In the properties view, select "Data Type Definition". Verify that the
definition can be viewed in the properties. Click the button with "...". Verify
that the type definition is opened for edit.  

7.2 Features test  
7.2.1 In the open editor, verify that syntax highlighting is active.  
7.2.2 Add a new member by typing "new_member: l" on a new line.  
7.2.3 Press Ctrl-Space. Verify that content assist appears and suggests
"logged_on_type" as an option.

7.3 Persistence test  
7.3.1 Make a change to the type and save  
7.3.2 Close and reopen the project  
7.3.3 Verify that the change you made was reloaded properly  

8. User Documentation
---------------------

9. Code Changes
---------------
Fork/Repository: < enter your fork and repo name name >
Branch: < enter your branch name here >

<pre>

< Put the file list here >

</pre>

End
---

