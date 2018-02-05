---

This work is licensed under the Creative Commons CC0 License

---

# Constant based on user data type
### xtUML Project Analysis Note

### 1. Abstract

Currently constants are only allowed to be given core types. This issue is
raised to analyze and implement a mechanism to allow constants to be typed by
user defined data types.

### 2. Document References

<a id="2.1"></a>2.1 [#5106 Constant based on user data type](https://support.onefact.net/issues/5106)  

### 3. Background

None.

### 4. Requirements

4.1 BridgePoint shall allow assignment of types to symbolic constants under the
following conditions:  
4.1.1 The type is `boolean`, `integer`, `real`, or `string`, _OR_  
4.1.2 The type is a user defined type that is based on one of the four types
listed in 4.2.1, _OR_  
4.1.3 The type is a user defined type that is based on another user defined type
that satisfies 4.1.2 or 4.1.3.  
4.2 Verifier impact shall be considered.  
4.3 Model compiler impact shall be considered.  

### 5. Analysis

5.1 Loosening type rules

Currently, only `boolean`, `integer`, `real`, and `string` are allowed for
symbolic constants. The following is the OOA of OOA operation `isAllowedType` on
"Literal Symbolic Constant" (`CNST_LSC`).
```
if ( param.typeName == "boolean" or
     param.typeName == "integer" or
     param.typeName == "string" or
     param.typeName == "real" )
  return true;
end if;

return false;
```

To loosen the rules to allow these types along with types that are based on
these types, this operation will have to be modified to navigate from the type
that is passed in through R18 iteratively until the core type is found. Then it
can check if it is based on one of the four allowed types. 

![types.png](types.png)

5.2 Assignment of values

For constants, values are assigned to a free form string. These values are
validated by the `ConstantValueInputValidator` which invokes the
`InputValueValidator`. The `InputValueValidator` uses `S_DT` operation
`getCoreDataTypeName` to validate. This operation traverses R18 recursively, so
there would be no need for any modification to validate constant values that are
based on a UDT.

5.2 Verifier

Symbolic constant values are type cast directly from the string value to a Java
`Object` when a runtime value is created. When the value is accessed later, the
`instance_to_boolean`, `instance_to_int`, and `intance_to_real` bridges in the
`GD` EE handle converting non-String constants back to their primitive types.

Additionally, when values are accessed from runtime values, only the core data
types are considered, so the fact that the constant is a UDT has no bearing on
the evaluation once the value is stored.

Because of these two things, there is no need for any modification to allow UDT
based constants in verifier.

5.3 Model compilers

5.3.1 MC-Java

MC-Java currently has no support for symbolic constants, therefore supporting
UDT based constants is out of scope for this work.

5.3.2 MC3020

The `val_constant_values` function of `q.val.translate.arc` generates code for
symbolic constant values in MC3020. The implementation is dead simple: the
string contents of the "Value" attribute is inserted into any place in the code
where the constant is referenced. For booleans, reals, and integers, this is
enough. For strings, quotation marks are wrapped around the string value.

Since this work would not have any effect on the contents of the "Value"
attribute, no modification of MC3020 is required.

### 6. Work Required

6.1 Modify `isAllowedType` on "Literal Symbolic Constant" to allow types that
have an eventual core type of `boolean`, `integer`, `real`, or `string`.  
6.2 Create and run tests.  

### 7. Acceptance Test

7.1 Existing unit tests shall pass.  
7.2 An automated test shall be added to the parser test suite to test assigning
a UDT typed constant to an attribute typed by that UDT.  
7.3 A manual test shall be created for acceptance of the promotion, but may not
be added to the manual test regression suite. It shall test the following  
7.3.1 Creation and typing of a constant.  
7.3.1.1 Test all permutations of valid types for constants.  
7.3.1.2 Test at least one invalid typing for constants.  
7.3.2 Verifier test.  
7.3.3 MC3020 test.  

### End
