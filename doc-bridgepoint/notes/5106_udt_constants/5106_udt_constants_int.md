---

This work is licensed under the Creative Commons CC0 License

---

# Constant based on user data type
### xtUML Project Implementation Note

### 1. Abstract

See [[2.3]](#2.3) section 1.

### 2. Document References

<a id="2.1"></a>2.1 [Service Pro SR #5106 Constant based on user data type](https://support.onefact.net/issues/5106) Headline issue  
<a id="2.2"></a>2.2 [BridgePoint DEI #10175](https://support.onefact.net/issues/10175) Public duplicate of the SR  
<a id="2.3"></a>2.3 [#5106 analysis note](5106_udt_constants_int.md)  
<a id="2.4"></a>2.4 [Prototypes engineering note](https://github.com/travislondon/bridgepoint/blob/4896_5005_5106_9050_4859_5035_Prototypes/doc-bridgepoint/notes/4896_5005_5106_9050_4859_5035_Prototypes/4896_5005_5106_9050_4859_5035_Prototypes.md) Engineering note for prototypes  

### 3. Background

None.

### 4. Requirements

See [[2.3]](#2.3) section 4.

### 5. Additional analysis and design

5.1 Prototype

The initial work after analysis of this issue was done in the form of a quick
prototype meant to fulfill the requirements, but not be burdened with testing or
large amounts of documentation. This was done to "blaze the trail" for the
official implementation where more effort would be put into documentation and
testing. The following are the notes that were written about the implementation
from the engineer who produced the prototype.

> 5.3 Allow UDTs as constants  
> 
> To relax constant type assignment the canUseDataType() operation in Literal
> Symbolic Constant is modified.  The operation now checks that for an EDT or
> UDT.  In the case of a UDT the core data type is located recursively.  In all
> cases if the DT subtype is EDT the type is allowed for assignment.  The new OAL
> is below:  
> 
> ```
>     select one edt related by dt->S_EDT[R17];
>     if(not_empty edt)
>       return true;
>     end if;
>     select one udt related by dt->S_UDT[R17];
>     if(not_empty udt)
>       coreId = udt.getCoreType();
>       select any dt from instances of S_DT where (selected.DT_ID == coreId);
>       select one edt related by dt->S_EDT[R17];
>       if(not_empty edt)
>         return true;
>       end if;
>     end if;
> ```
> 
> The class ConstantValueInputValidator is modified to called the same recursion
> as above when dealing with a UDT.  
> 
> Verifier handles constants in all cases even for EDTs, no changes are required.  
> 
> q.val.translate.arc is modified to locate the constant enum value assigned and
> output the enum generated name.
> 
> ```
>     .select one edt related by te_dt->S_EDT[R17]
>     .if ( not_empty edt )
>       .assign enumString = edt.Name
>       .select many enums related by edt->S_ENUM[R27]
>       .for each enum in enums
>         .assign enumString = enumString + enum.Name
>         .if ( enumString == cnst_lsc.Value )
>           .select one te_enum related by s_enum->TE_ENUM[R2027]
>           .assign te_val.buffer = te_enum.GeneratedName
>           .break for
>         .end if
>       .end for
>     .elif ( 4 == te_dt.Core_Typ )
> ```

The full text of this engineering note can be found at [[2.4]](#2.4).

5.2 Default values

Currently new constants are given a default value based on the given type
(currently just `string`, `real`, `integer`, and `boolean`). During initial
testing of the prototype, it was observed that no default value is provided for
constants typed with a user defined type or an enumerated type. Default values
should be added for these types. For UDTs, the default value shall be the
default value of the core type that it is based on. For EDTs, the default value
shall be the first emumerator.

Default values are important because without them, there is potential for a
constant to have an invalid value. After the default is given, any time the
value is changed, the validation code prevents invalid values from being
entered, but the default itself must also be a valid value. This brings in an
additional requirement for EDTs that was not considered before: EDTs that do not
have at least one enumerator shall not be allowed for assignment to a constant.
This is because assigning such an enumerator would result in a constant which
cannot have a valid value.

5.3 Reassigning UDT base types

Another problem was discovered during initial testing of the prototype. UDTs can
be assigned a "core type" which it is based upon. This can be a problem because
it opens up a window to get around type checking for symbolic constants. Imagine
a user creates a new UDT called "MyUDT" based on the core type "integer". Next
he creates a constant called "MyConst", assigns the type to be "MyUDT" and the
value to be "1". Then, he selects "MyUDT" and reassigns the type to be
"inst_ref<Timer>". This currently is a totally valid sequence of edits, but it
results in a symbolic constant typed on a UDT that is _not_ based on one of the
four core types `string`, `real`, `boolean`, `integer`, or an enumerated type.
This is a violation of the requirements of this feature.

A check needs to be put in place to prevent this from happening. The check will
be have in the following ways:

5.3.1 If the user changes the core type of a UDT to another type which is valid
for constants, a downgrade dialog will be shown stating the constants that will
be affected and notifying that the default value of the new core type will be
used for the new value of the constant.  
5.3.2 If the user changes the core type of a UDT to a type that is invalid for
constants, a downgrade dialog will be shown stating the constants that will be
affected and notifying that the constant will be typed with the default type
(integer) and be given its default value (0).  
5.3.3 No dialog will be shown if a user changes the core type of a UDT which is
not used to type any constants.  

The same downgrade dialog used for cut/paste will be reused in this situation to
provide a consistent user experience.

### 6. Work Required

6.1 Update `CNST_LSC.canUseDataType` to allow EDTs and UDTs as described in
secion 5.1. Tighten the rules a little bit to only allow EDTs if they have at
least one enumerator.  
6.2 Update `ConstantValueInputValidator` as described in secion 5.1.  
6.3 Update `CNST_LSC.updateValueToDefault` to take a data type unique ID instead
of a data type name string as a parameter. Update all invocations accordingly.
Modify the body to select the core type (in the case of a UDT) and use the
default value of the core type. For EDTs, set the value to the first enumerator.  
6.4 Add new operation `CNST_LSC.downgradeCheck`. In this operation, check if the
type assigned to the constant is still valid. If no, set the type to the
default type. In all cases, reset the value to the default value for the new
type. Use the existing downgrade reporting mechanism to alert the user.  
6.5 In the CME function `S_UDT_SetType` to invoke `downgradeCheck` operation on
any instance of `CNST_LSC` which is either directly typed by the selected UDT or
typed by a UDT that is based on the selected UDT (and therefore is affected).  
6.6 Update the message in the downgrade dialog in `TransactionManager` to
contain an explanation of changes made to constants during downgrade.  

6.7 Update `q.val.translate.arc` in MC-3020 using the implementation in 5.1 as a
base. This implementation was untested and had several bugs that prevented it
from running properly. The overall logic is correct, so only the bugs have been
fixed but the algorithm remains the same as shown in 5.1.  
6.8 Update and rebuild `mcmc`  

### 7. Implementation Comments

7.1 `TransactionManager` change

During implementation, it was observed that sometimes downgrade warnings can be
added to the downgrade warning twice if the bridge call
`UTIL::reportElementDowngraded` is called twice with the same instances. The
obvious way to solve the problem is to ensure that BridgePoint application code
never invokes `reportElementDowngraded` twice with the same instances, however
this method introduced unnecessary complexity in the code for this work. Another
way is to modify the `affectedModelElementsNames` field in the
`TransactionManager` class to be an instance of a Java `Set` instead of a
`List`. This ensures that even if `reportElementDowngraded` is invoked twice,
each message in the dialog will be unique.

### 8. Unit Test

8.1 Existing unit tests shall pass  

8.2 New JUnit tests

One new JUnit test has been introduced in the verifier test suite to verify the
behavior of UDT constants. The test automates the same action as the MC-3020
test (see 8.4) in Verifier.

8.3 Model edit test  

8.3.1 Launch BridgePoint in a new workspace. Import `test_udt_constants` from
the models repository (`models/test/5106_udt_constants`).  
8.3.2 Navigate to `test_udt_constants::lib::test::types::NewConstants`.  
8.3.3 Right click `const1` > Set Type...  
8.3.4 Verify that `udt1` is assignable. Select `udt1`.  
8.3.5 Verify that the value for `const1` is set to empty string (default for
type `string`).  
8.3.6 Right click `const2` > Set Type...  
8.3.7 Verify that `udt2` is assignable. Select `udt2`.  
8.3.8 Verify that the value for `const2` is set to empty string (default for
type `string`).  
8.3.9 Right click `const3` > Set Type...  
8.3.10 Verify that `enum1` is assignable. Select `enum1`.  
8.3.11 Verify that the value for `const3` is set to "enum1::ONE".  
8.3.12 Right click `const3` > Set Type...  
8.3.13 Verify that `udt3` is not assignable.  
8.3.14 Verify that `enum2` is not assignable. Dismiss the type chooser.  

8.3.15 Right click `udt1` > Set Type...  
8.3.16 Select `boolean`.  
8.3.17 Verify that a downgrade dialog appears and that the following text is
displayed:  
```
test_udt_constants::lib::test::types::udt1  is associated with  test_udt_constants::lib::test::types::NewConstants::const1
test_udt_constants::lib::test::types::udt2  is associated with  test_udt_constants::lib::test::types::NewConstants::const2
test_udt_constants::lib::test::types::udt1  is associated with  test_udt_constants::lib::test::types::TestConstants::const1
test_udt_constants::lib::test::types::udt2  is associated with  test_udt_constants::lib::test::types::TestConstants::const2
```
8.3.18 Select OK.  
8.3.19 Navigate to `NewConstants::const1`. Verify that the value has been set to
`false` (default for type `boolean`).  
8.3.20 Navigate to `NewConstants::const2`. Verify that the value has been set to
`false` (default for type `boolean`).  
8.3.21 Right click `udt1` > Set Type...  
8.3.22 Select `component_ref`.  
8.3.23 Verify that a downgrade dialog appears and that the following text is
displayed:  
```
test_udt_constants::lib::test::types::udt1  is associated with  test_udt_constants::lib::test::types::NewConstants::const1
test_udt_constants::lib::test::types::udt2  is associated with  test_udt_constants::lib::test::types::NewConstants::const2
test_udt_constants::lib::test::types::udt1  is associated with  test_udt_constants::lib::test::types::TestConstants::const1
test_udt_constants::lib::test::types::udt2  is associated with  test_udt_constants::lib::test::types::TestConstants::const2
```
8.3.24 Select OK.  
8.3.25 Navigate to `NewConstants::const1`. Verify that the type has been set to
integer and the value has been set to `0` (default for type `integer`).  
8.3.26 Navigate to `NewConstants::const2`. Verify that the type has been set to
integer and the value has been set to `0` (default for type `integer`).  

8.4 MC-3020 test  

8.4.1 Switch to the C/C++ perspective.  
8.4.2 Build the project by selecting `test_udt_constants` and clicking the
hammer button (or right click > Build Project).  
8.4.3 In a shell, navigate to the project root and execute:
```
./Debug/test_udt_constants
```
8.4.4 Verify the following output:
```
Passed
Passed
Passed
```
This can also be found at `gen/expected_results.txt`.  

### 9. User Documentation

TODO

### 10. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 5106_udt_constants  

<pre>

</pre>

Fork/Repository: leviathan747/mc  
Branch: 5106_udt_constants  

<pre>

 arc/q.val.translate.arc                               |  16 ++++++++++++++--
 bin/mac/mcmc                                          | Bin 1569800 -> 1565704 bytes
 bin/mcmc                                              | Bin 2408744 -> 2374008 bytes
 bin/win/mcmc.exe                                      | Bin 1706557 -> 1963200 bytes
 mcmc/arlan/o2.oal                                     |  16 ++++++++++++++--
 model/escher/gen/ooaofooa.c                           |  49 +++++++++++++++++++++++++++++++++++++++----------
 model/escher/gen/ooaofooa_c_orig                      | 118 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--------------------------------------------------------
 model/escher/models/escher/mcmc/afunval/afunval.xtuml |  16 ++++++++++++++--
 8 files changed, 143 insertions(+), 72 deletions(-)

</pre>

Fork/Repository: leviathan747/bptest  
Branch: 5106_udt_constants  

<pre>

 src/org.xtuml.bp.debug.ui.test/src/VerifierTestSuite2.java                      |   4 +++-
 src/org.xtuml.bp.debug.ui.test/src/org/xtuml/bp/debug/test/UdtConstantTest.java | 208 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 2 files changed, 211 insertions(+), 1 deletion(-)

</pre>

Fork/Repository: leviathan747/models  
Branch: 5106_udt_constants  

<pre>

 test/test_udt_constants/.cproject                                                    | 126 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_udt_constants/.externalToolBuilders/Model Compiler.launch                  |  15 ++++++++
 test/test_udt_constants/.gitignore                                                   |   4 ++
 test/test_udt_constants/.launches/test_udt_constants.launch                          |  13 +++++++
 test/test_udt_constants/.project                                                     |  44 +++++++++++++++++++++
 test/test_udt_constants/gen/LOG_bridge.c                                             |  25 ++++++++++++
 test/test_udt_constants/gen/domain.mark                                              |   2 +
 test/test_udt_constants/gen/expected_results.txt                                     |   3 ++
 test/test_udt_constants/models/test_udt_constants/lib/lib.xtuml                      | 308 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_udt_constants/models/test_udt_constants/lib/test/classes/A/A.xtuml         | 107 ++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_udt_constants/models/test_udt_constants/lib/test/classes/classes.xtuml     |  81 +++++++++++++++++++++++++++++++++++++++
 test/test_udt_constants/models/test_udt_constants/lib/test/functions/functions.xtuml |  98 +++++++++++++++++++++++++++++++++++++++++++++++
 test/test_udt_constants/models/test_udt_constants/lib/test/test.xtuml                | 294 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_udt_constants/models/test_udt_constants/lib/test/types/types.xtuml         | 432 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_udt_constants/models/test_udt_constants/lib/testing/testing.xtuml          |  35 +++++++++++++++++
 test/test_udt_constants/models/test_udt_constants/test_udt_constants.xtuml           |  56 +++++++++++++++++++++++++++
 test/test_udt_constants/src/.gitignore                                               |   2 +
 17 files changed, 1645 insertions(+)

</pre>

### End

