---

This work is licensed under the Creative Commons CC0 License

---

# Returning Strings in C Generated Code
### xtUML Project Analysis Note

1. Abstract
-----------
MC-3020 represents strings using arrays of characters and pointers to those arrays.  This poses a problem when returning strings from called functions (class operations, messages and bridges).  A pointer can be returned, but the data behind the pointer must be stored where both calling and called contexts can access it.

2. Document References
----------------------
[1] Issues 589, test model for string reentrancy
    https://support.onefact.net/redmine/issues/589
[2] Test Model:  https://github.com/xtuml/models/tree/master/VandMC_testing/mctest/stringtest/

3. Background
-------------
C does not have a native string type; zero-delimited character arrays are employed to store lists of characters.  MC-3020 follows this common convention.  This approach has proven mostly adequate for the embedded applications targeted by the model compiler through the years.

However, a clean mechanism for returning strings has proven elusive.  C is able to return the pointer on the stack.  However, the string contents must then be copied from the called context into the calling context.  If a string is "manufactured" in the called context, it may be out of scope (on the stack) once control returns to the calling context.  C compilers and static checkers identify this as dubious (even erroneous) practice.

An explicit design choice to tolerate this practice has been made in the past based on the cost/benefit of alternatives.

4. Requirements
---------------
4.1 Provide a safe mechanism to return strings from called bodies.

4.2 Provide a test model to exercise return strings in the face of muliple threads.

5. Analysis
-----------
5.1 Options for Returning Strings from C Functions

5.1.1 Stack Return (present implementation)

5.1.2 Return Pointer to Static Buffer

5.1.3 Return Pointer to Allocated Buffer

5.1.4 Return Pointer to Rotating Buffer

5.1.5 Stack Return with String Struct

5.1.6 Architectural By-Ref Parameter

5.2 String Length Issues

s2_t in strcpy
size_t

6. Work Required
----------------
6.1 
6.1.1 

7. Acceptance Test
------------------
7.1 Test Model [2] shall run without failure.

End
---

