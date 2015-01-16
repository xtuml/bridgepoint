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
[1] [BridgePoint DEI #589](https://support.onefact.net/redmine/issues/1)  test model for string reentrancy  
[2] [Test Model](https://github.com/xtuml/models/tree/master/VandMC_testing/mctest/string_return_test/)  
[3] [Analysis Note](https://github.com/xtuml/bridgepoint/tree/master/doc-bridgepoint/notes/589_stringtest/589_returnstring.ant.md)  

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

5.1.6.1 Reasons for not choosing this option.

In C code, passing a parameter by-ref allows a form of return data.
This option can be made to work, but it suffers a serious complication
when the string-typed called function is used in an expression other
than a direct assignment.  It requires that temporary storage be
created in the calling context to hold data just long enough to get
it from the calling context into the called context and transfer
it into the second called context.

```
::f( s:g() );
```
In the above OAL, `f` is a function taking a string parameter, and `g`
is a function returning a string.  Trying to use a by-ref parameter
would require the following (abbreviated for clarity) C code.
```
char tempstring[MAX];
f( (g( tempstring ),tempstring) );
```
Note also that there may need to be several manufactured transient `tempstring`
variables in contexts where multiple string functions are called within
expressions.  This adds the requirement to manufacture unique names for each
of these temporary variables.

Finally, by returning a struct, some C and C++ compilers have the option
of silently optimizing to something similar to by-ref passing.  The compiler
likely does a better job of optimizing than does the programmer (or model
compiler) in this case.

6. Design
---------

6.1 Design of Return String Struct

6.1.1 A new data type _xtuml_string_ is declared as such:
```
typedef struct { char s[ ESCHER_SYS_MAX_STRINGLEN ]; } xtuml_string;
```

6.1.2 `xtuml_string` will be used only for return values, not for all strings.

6.2 Updates for Size of String

7. Design Comments
------------------

8. Unit Test
------------

End
---

