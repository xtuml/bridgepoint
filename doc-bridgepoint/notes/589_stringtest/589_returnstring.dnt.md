---

This work is licensed under the Creative Commons CC0 License

---

# Return Strings Safely
### xtUML Project Design Note


1. Abstract
-----------
MC-3020 returns strings in a non-portable and marginally unsafe manner.
A new design for returning strings from action bodies is offered.

2. Document References
----------------------
[1] [BridgePoint DEI #589](https://support.onefact.net/redmine/issues/589)  test model for string reentrancy  
[2] [Test Model](https://github.com/xtuml/models/tree/master/VandMC_testing/mctest/string_return_test/) VandMC_testing/mctest/string_return_test  
[3] [Analysis Note](https://github.com/xtuml/bridgepoint/tree/master/doc-bridgepoint/notes/589_stringtest/589_returnstring.ant.md) doc-bridgepoint/notes/589_stringtest/  
[4] [revealing Stack Overflow comment on returning structures](http://stackoverflow.com/questions/18412094/a-legal-array-assignment-is-it-possible) whole thread  

3. Background
-------------
Several design options were explored in [3]


4. Requirements
---------------
See [3].


5. Analysis
-----------
### 5.1 Options for Returning Strings from C Functions

#### 5.1.1 Stack Return (present implementation) ...REJECTED

#### 5.1.2 Return Pointer to Static Buffer ...REJECTED

#### 5.1.3 Return Pointer to Rotating Buffer  ...REJECTED

#### 5.1.4 Return Pointer to Allocated Buffer  ...REJECTED

#### 5.1.5 Stack Return with String Struct  ...SELECTED

#### 5.1.6 Architectural By-Ref Parameter ...2nd CHOICE

5.1.6.1 Reasons for not liking this option.

Note that there need to be several manufactured transient `tempstring`
variables in contexts where multiple string functions are called within
expressions.  This adds the requirement to manufacture unique names for each
of these temporary variables.

6. Design
---------

6.1 Design of Return String Struct

6.1.1 A new data type `xtuml_string` is declared as such:
```
typedef struct { char s[ ESCHER_SYS_MAX_STRINGLEN ]; } xtuml_string;
```

6.1.2 `xtuml_string` will be used only for return values, not for all strings.

6.1.3 Link TE_BLK to TE_ABA across R2011 to allow easy access to the
implementation return data type.  This is easy for S_SYNC, O_TFR, O_DBATTR,
S_BRG and SM_ACT (both states and transitions).  However, there are four types of
messages that are translated before the TE_BLK instances are created.  For these,
queries are added later in the sys.populate processing to link them up.

The linking of these instances earlier in the processing allows removal of
these traversals later in the ABA rollup routine.

6.1.4 Clean up and remove the previous "scope transfer" variables that are now
no longer needed.

6.2 Updates for Size of String

6.2.1 Instead of using `s2_t` which is `short`, use `Escher_size_t` for a
couple of loop indexes in `strcpy` and `stradd`.

7. Design Comments
------------------
### 7.1 Return String Struct (5.1.5) ... FAILED!  
The return string struct failed.  There are inconsistencies in the ANSI C
standard, and there are bugs in gcc!  In the ANSI 2011 standard, the return
string struct design is technically good.  However, there are inconsistencies
in ANSI C 1990 and 1999, and the various implementations in C compilers are
not trustworthy.  Read [4].

I discovered the problem after running on a newer version of gcc and then
running on an older gcc.  The design can be reconsidered later, although
it will not likely be worth changing back to this design.

### 7.2 New Design for Architectural By-Ref Parameter

#### 7.2.1 Create Prototype String TE_PARM  
Early in the system population query, create an instance of TE_PARM that
is linked to a string TE_DT.  This will be useful to make copies for the
architectural string parameter.

#### 7.2.2 Duplicate Prototype String TE_PARM  
During the manufacture of TE_ABAs for bodies returning a string, duplicate
the above string TE_PARM and link it into the front of the parameter list.

#### 7.2.3 Link as First Parm  
Link the architectural by-ref parameter at the head of the list of parameters
or alone in bodies not taking arguments.  The name of the parameter will be
something that alphabetizes to the front of the list.  "A0xtumlsret" is chosen.

#### 7.2.4 Prepend Architectural String Parm to Invocation Parms  
In the expression value generation, add functionality to V_TRV, V_FNV,
V_BRV and V_MSV to prepend a uniquely named local transient to the
invocation list.

#### 7.2.5 Change Return Statement to Use VarParm  
In the return statement, strcpy the return value into the "A0xtumlsret"
parameter and return (a pointer) to it.

### 7.3 Instance Loading  
When the _InstanceLoading_ mark is being used (for the model-based model
compiler), the string approach may need a few tweaks.  The model compiler
processes string data heavily.  This will be tested as part of preparing
the next release.


8. Unit Test
------------
8.1 Run [2].  It is expected to compile and run continuously.

8.2 Build and run GPS Watch on Windows.

8.3 Build and run the Microwave oven on both Windows and Unix.

8.4 Run cppcheck --enable=all *.c.  See no reported stack violations.

8.5 gcc -c -Wall *.c.  See no warnings or errors regarding stack violations.


End
---

