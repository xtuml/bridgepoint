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

### 7. Implementation Comments

### 8. Unit Test

### 9. User Documentation

### 10. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 5106_udt_constants  

<pre>

</pre>

Fork/Repository: leviathan747/mc  
Branch: 5106_udt_constants  

<pre>

</pre>

### End

