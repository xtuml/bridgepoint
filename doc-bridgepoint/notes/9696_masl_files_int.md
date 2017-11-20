---

This work is licensed under the Creative Commons CC0 License

---

# Inconsistencies with file contents and tool
### xtUML Project Implementation Note

### 1. Abstract

Inconsistencies with file contents and tool

### 2. Document References

<a id="2.1"></a>2.1 [#9696 Inconsistencies with file contents and tool](https://support.onefact.net/issues/9696)  

### 3. Background

A good description of the issue is provided in [[2.1]](#2.1). The reader should
refer to the issue description before continuing.

3.1 MASL service bodies

There are several xtUML action bodies that all map to "service" in MASL. They
are the following:

3.1.1 Provided operations  
3.1.2 Provided signals  
3.1.3 Required operations  
3.1.4 Required signals  
3.1.5 Operations  
3.1.6 Bridges  
3.1.7 Functions  

### 4. Requirements

4.1 Display "end state;" as the last line in MASL state action bodies  
4.2 Display "end service;" as the last line in MASL service bodies (See section
3.1)  

### 5. Work Required

5.1 Overview

There are three main aspects of this work:

5.1.1 Import of MASL actions into xtUML model elements (as `Action_Semantics`)  
5.1.2 Export of MASL actions in xtUML model elements (as `.masl` files)  
5.1.3 Creation of new MASL activities  

5.2 Import of MASL actions

Currently, MASL activities are stored in a `.masl` file named after the action
home (PMC) that they reside in. For example, all MASL domain service in a
package would be stored in a single `.masl` file named after the package. All
class services would be stored in a single `.masl` file named after the class,
etc. There exists an ANTLR parser that parses these files, matches existing
xtUML elements based on signature, and inserts the actions into the
`Action_Semantics` field of the xtUML element.

The grammar for the action parser does not include "end" as part of the code
block itself, but the importer supplies it. To fulfill the requirements for this
work, the importer shall be modified to supply the "end" keyword as well as the
appropriate keyword for the activity ("state;" or "service;"). In this way, the
full "end state;" or "end service;" phrase will be included in the
`Action_Semantics` and displayed by the editor.

5.3 Export of MASL actions

Currently, the MASL exporter appends "end service;" or "end state;" to the end
of the action body before the activity is serialized to a `.masl` file on save.
If the body itself ends with the "end" keyword, only " service;" or " state;" is
appended.

To fulfill the requirements for this work, nothing will be appended to the
action body (except a line break for formatting), since "end state;" or "end
service;" will already be part of the body.

5.4 New activities

Currently, when new MASL activities are initialized, the `Action_Semantics`
field is populated with the following:
```
begin
  null;
end
```
This is done because the MASL grammar does not support bodies with zero
statements. To fulfill the requirements for this work, each activity
initialization shall be modified to provide either
```
begin
  null;
end state;
```
or
```
begin
  null;
end service;
```
where appropriate.

### 6. Implementation Comments

6.1 Dialog of death and future improvements

This issue was originally raised because modifying the body to say "end
service;" or "end state;" and then reloading resulted in the dialog of death.
This was because doing so resulted in something like:
```
end state;
end state;
```
in the `.masl` file. This confused the import parser and the number of states
parsed did not match the number of xtUML states. This type of danger will still
be present in this work. For example, if the user deletes the "end state;" line,
the dialog of death can occur.

### 7. Unit Test

### 8. User Documentation

### 9. Code Changes

Fork/Repository: < enter your fork and repo name name >
Branch: < enter your branch name here >

<pre>

 Put the file list here 

</pre>

### End

