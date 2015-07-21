---

This work is licensed under the Creative Commons CC0 License

---

# Returning Strings in C Generated Code
### xtUML Project Analysis Note

1. Abstract
-----------
MC-3020 represents strings using arrays of characters and pointers to
those arrays.  This poses a problem when returning strings from called
functions (class operations, messages and bridges).  A pointer can be
returned, but the data behind the pointer must be stored where both
calling and called contexts can access it.

2. Document References
----------------------
[1] [1F Redmine issue 589, test model for return string reentrancy](https://support.onefact.net/redmine/issues/589)  
[2] [Test Model](https://github.com/xtuml/models/tree/master/VandMC_testing/mctest/string_return_test/)

3. Background
-------------
C does not have a native string type.  Zero-delimited character arrays are
employed to store lists of characters.  MC-3020 follows this common
convention.  The approach has proven mostly adequate for the embedded
applications targeted by the model compiler through the years.

A completely clean, compiler-warning free and thread safe mechanism for
returning strings has proven elusive.  C is able to return the pointer on the
stack.  However, the string contents must then be copied from the called
context into the calling context.  If a string is "manufactured" in the called
context, it may be out of scope (on the stack) once control returns to the
calling context.  C compilers and static checkers identify this as dubious
(even erroneous) practice.

An explicit design choice to tolerate this practice has been made in the
past based on the cost/benefit of alternatives.

For clarity, an example is provided.  Consider the following OAL:
```
ASSIGN c = buffer::twist( s1:jstr1, s2:ss2 );
```
The class _buffer_ has operation _twist_ which returns a string.  The OAL
is translated by MC-3020 into the following ANSI C code:
```
Escher_strcpy( c, stringtest_buffer_op_twist( jstr1, ss2 ) );
```
The C function `stringtest_buffer_op_twist` returns a pointer to an array
of characters.  The `Escher_strcpy` routine is immediately invoked to copy
the character contents of the string into the variable `c`.  It is possible
that `stringtest_buffer_op_twist` is creating or manipulating the string to
be returned using local (automatic) variables (on the stack).  In the
context of this call and in the context of `Escher_strcpy`, the buffer
carrying the return string is located in deallocated stack space.


4. Requirements
---------------
4.1 Provide a safe mechanism to return strings from called bodies.

4.2 Provide a test model to exercise return strings in the face of multiple
threads.

5. Analysis
-----------
### 5.1 Options for Returning Strings from C Functions

#### 5.1.1 Stack Return (present implementation)

5.1.1.1 The current implementation returns (on the stack) a pointer to an
array of characters.  The array of characters may exist as an attribute
value in a class instance; it may also exist as a local automatic variable
in the called scope.  In this latter case, upon return to the calling
scope, the pointer itself is returned without issue from the called scope
into the calling scope.  However, the returned pointer refers to an array
of characters in "deallocated stack space" (the stack space of the called
function).

5.1.1.2 In the calling scope, the MC-3020 generated code always emits a
`strcpy` invocation which immediately upon return retrieves the character
array data and copies it into the calling context variable (return
assignment).  Since the code is generated, this pattern is implemented
in 100% of cases.  No other code is run between receiving the pointer
back from the function call and copying the character data from the
previous scope.

5.1.1.3 **Risks:**  This method is risky, because stack implementations
are not guaranteed to be predictable and consistent from one target to
another.  This practice is documented in the code as "dubious".  C
compilers and static checkers detect this abuse of the stack and flag a
warning or error.  However, the risk of employing this method is arguably
less than other supported methods of returning strings from C functions.
The risk of stack corruption due to multi-threading is blunted by the fact
that virtually all popular threading implementations provide private
stacks to each thread.  After some effort, no test scenario has been
created that is able to overwrite the stack data.

#### 5.1.2 Return Pointer to Static Buffer

5.1.2.1 The method in 5.1.1 could be modified to provide a `static` memory
variable to avoid leaving live data on the deallocated stack.  At return
time, the data to be returned could be copied into the `static` variable.
The return pointer would be directed to this private memory-based
variable.  Such a strategy would successfully avoid the stack exposure.

5.1.2.2 **Risks:**  This method renders all functions returning
strings to be non-reentrant.  Even though the compiler may like this
method better, the practical multi-threading runtime environment will
overwrite the static space erratically but predictably.

#### 5.1.3 Return Pointer to Rotating Buffer

5.1.3.1 The method from 5.1.1 as modified in 5.1.2 could be modified
further.  Instead of allocating a local `static` variable to contain the
return data, a shared and managed central pool of buffers could be used.
Such a mechanism exists in MC-3020 generated code.  It is called `strget`.

5.1.3.2 `strget` is a function that manages a two-dimensional array of
characters (array of string buffers).  Upon invocation, `strget` returns a
pointer to an indexed buffer and then increments the index.  This method
recently has been augmented (in a patch) to mutex its index.

5.1.3.3 **Risks:**  Even though this method is currently used, it is
intended to be used sparingly such that the number of buffers required
remains small.  If the number of buffers grows and the usage of the
buffers is not strictly temporary, this method will fail with buffer
overwrites.  The addition of mutexing increases exposure to deadlock.

#### 5.1.4 Return Pointer to Allocated Buffer

5.1.4.1 The method from 5.1.3 could be modified to use a buffer that is
dynamically allocated on the heap (with malloc) and deallocated (with free).

5.1.4.2 **Risks:**  The nature of the buffer management is transitory
between two different contexts.  It would be difficult and messy to
allocate the buffer in one context (the called context) and free it in
another (calling context).

#### 5.1.5 Stack Return with String Struct

5.1.5.1 ANSI C passes arrays by pointer and returns arrays by pointer.
However, ANSI C passes structures (`struct`) by value.  Consider the
following type declaration:
```
typedef struct { char s[ MAX_STRING_LEN ]; } xtuml_string;
```
This code defines a structured type named `xtuml_string` which contains an
array of characters like traditional strings.  This construct uses the same
amount of memory as a traditional approach to store character data.  However,
when passed or returned to called functions, C will pass by value and return by
value using the stack.  The management of the stack will be controlled by the C
compiler for the target of choice.  Thus, assumptions need not be made about
what is safe usage of the stack.

5.1.5.2 **Risks:**  This method suffers some performance degradation due
to strings being copied multiple times from buffer to buffer.
Specifically, when returning a string, the called context will copy the
string into the return variable space on the stack.  In the calling
context this variable will be copied again into the return assignment
target.

5.1.5.3 Example
```
#include <stdio.h>
#include <string.h>

typedef struct { char s[ 80 ]; } xtuml_string;

/* Return type is xtuml_string.
   Parameters are conventional strings.  */
static xtuml_string sfun( char * );
static xtuml_string sfun( char * a )
{
  xtuml_string returnstring;
  char c[ 80 ];
  strcpy( c, a );
  strcpy( returnstring.s, c );
  return returnstring;
}

int main ( void )
{
  char name[ 80 ];
  printf( "xt%s\n", strcpy( name, sfun( "UML" ).s ) );
  return 0;
}
```
Output is:  
`xtUML`  

#### 5.1.6 Architectural By-Ref Parameter

5.1.6.1 There is more than one way to return data from a called function.
By-ref parameters provide a means of passing a reference (pointer) to a
variable into a called function.  In the called context, the function can
populate the data area of the by-ref variable indirectly through the
pointer.

5.1.6.2 The model compiler could translate xtUML body return strings to
architectural by-ref parameters.  When functions are called in xtUML OAL, a
pointer to the assignment target string could be passed as a parameter.  The
translated OAL inside of the called body would need to populate the buffer
behind the passed by-ref parameter instead of (or in addition to) returning the
string.

5.1.6.3 **Risks:** This method is attractive in many ways but does have
its challenges.  It is attractive, because it can be good for performance.
Some buffer copying may be avoided.  This method successfully
avoids "dubious" stack manipulation.  It would seem to be robust in the
face of multi-threaded architectures.  More analysis will need to be done
regarding returning a value when no explicit assignment is being
performed.  Consider the following OAL:
```
if ( "Atlas Shrugged" == book::name() )
  generate book1:read() to self;
end if;
```
`book::name()` does not get assigned but is simply compared in the calling
context.  An architectural by-ref parameter would need to be allocated,
passed and populated to/from the generated operation body.

Also, the mapping of parameters in OAL to parameters in generated code is
direct and results in code that is relatively easy to relate to the source
action language constructs.  Coercing return values into by-ref parameters
adds a bit of churn to the code, especially if only some types (strings)
use the architectural parameter method.  One additional disadvantage is
the amount of effort for implementation.  This approach would likely cost
the most to implement.

### 5.2 String Length Issues

5.2.1 While analyzing this issue, it became clear that string handling
issues (still) exist in MC-3020 with regard to string length.  It is
apparent that long strings were not anticipated or considered in the
original design of the model compiler.  At least one place exists in the
MC where strings are restricted to lengths of 2^15 or 2^16 (32k or 64k).
``signed short`` and ``unsigned short`` integers are used as loop
variables and indexes.

5.2.2 The use of ANSI C `size_t` will be more widely and consistently
applied when dealing with "memory buffers".  Strings fall roughly into
this category.

6. Work Required
----------------
6.1 Select an Alternative

6.2 Design and Implement the Selected Alternative

7. Acceptance Test
------------------
7.1 Test Model [2] shall run without failure.

7.2 C compilers and static syntax checkers shall not complain about stack abuse.

End
---

