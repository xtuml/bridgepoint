---

This work is licensed under the Creative Commons CC0 License

---

# Analyze missing keywords in content assist
### xtUML Project Analysis Note


### 1. Abstract

This note does an analysis of the content assist functionality to determine
which keywords are are provided by content assist proposals and which ones are
not.

### 2. Document References

<a id="2.1"></a>2.1 [#10007 Analyze missing keywords in content assist](https://support.onefact.net/issues/10007)  
<a id="2.2"></a>2.2 [keyword test model](https://support.onefact.net/attachments/download/1057/10007_keywords.zip)  
<a id="2.3"></a>2.3 [#9763 content assistance design note](9763_content_assistance/9763_content_assistance_dnt.md)  
<a id="2.4"></a>2.4 [#9749 phase 1 use cases](9749_usecases/9749_usecases_phase_1.md)  

### 3. Background

Information on the original design and use cases of content assist can be found
in [[2.3]](#2.3) and [[2.4]](#2.4). 

This analysis was conducted using BridgePoint 6.8.1 (Build ID: 2017-12-27 08:06)

### 4. Requirements

4.1 Analysis shall determine all situations where OAL keywords are not provided
by content assist proposals.  
4.1.1 The analysis shall consider the OAL grammar to guarantee full coverage of
the language.  
4.2 Analysis shall produce a list of all OAL keywords.  
4.3 Analysis shall interpret missing keyword situations.  

### 5. Analysis

5.1 Keywords

The following is the exhaustive list of keywords in OAL:

```
_debug
_dump
_off
_on
_sor
_stat
_trace
across
and
any
assign
assigner
break
bridge
by
cardinality
class
continue
control
create
creator
delete
each
elif
else
empty
end
event
false
for
from
generate
if
in
instance
instances
many
not
not_empty
object
of
one
or
param
rcvd_evt
relate
related
return
select
selected
self
send
sender
stop
to
transform
true
unrelate
using
where
while
```

This list was derived directly from the grammar file with one exception. The
keyword `sender` does not exist in the grammar, but it represents a special
built in local variable in message bodies. This analysis shall consider `sender`
to be a keyword.

5.2 Coverage through statements

The following list of statements provide full coverage of the grammar for
keywords. Keep in mind that it is not enough to cover all keywords. One must
cover all branches of the parse tree that use keywords, therefore many keywords
are repeated in this list. Note that this note is an exhaustive analysis, so
cases that are not considered common practice (e.g. explicit assign statement
with "assign" keyword) will still be considered and tested as part of this
analysis.

5.2.1
```
assign x = y;
```

5.2.2
```
control stop;
```

5.2.3
```
break;
```

5.2.4
```
bridge LOG::LogInfo( message: "hello world" );
```

5.2.4.1
```
bridge self.attr1 = TIM::get_second( date:TIM::current_date() );
```

5.2.4.2
```
bridge param.param1 = TIM::get_second( date:TIM::current_date() );
```

5.2.5
```
send Port1::msg() to sender;
```

5.2.5.1
```
send self.attr1 = Port1::msg2();
```

5.2.5.2
```
send param.param1 = Port1::msg2();
```

5.2.6
```
continue;
```

5.2.7
```
create object instance foo of FOO;
```

5.2.8
```
create event instance evt1 of BAR1:evt1() to bar;
```

5.2.8.1
```
create event instance evt1_1 of BAR1 to bar;
```

5.2.8.2
```
create event instance evt1_2 of BAR1:evt1 to bar;
```

5.2.8.3
```
create event instance evt1l3 of BAR4* to bar;
```

5.2.9
```
create event instance evt2 of BAR2:evt2() to BAR creator;
```

5.2.10
```
create event instance evt3 of BAR_A1:evt3() to BAR assigner;
```

5.2.11
```
create event instance evt4 of BAR_A1:evt3() to BAR class;
```

5.2.12
```
delete object instance foo;
```

5.2.13
```
for each foo in foos
  // for loop
end for;
```

5.2.14
```
generate BAR1:evt1() to bar;
```

5.2.15
```
if true
  // if branch
elif false
  // elif branch
else
  // else branch
end if;
```

5.2.16
```
relate a to b across R1 using c;
```

5.2.16.1
```
relate a to b across R1.'a_to_b' using c;
```

5.2.17
```
unrelate a from b across R1 using c;
```

5.2.17.1
```
unrelate a from b across R1.'a_to_b' using c;
```

5.2.18
```
return 1;
```

5.2.19
```
select any foo from instances of FOO where ( selected.id == 1 );
```

5.2.20
```
select one foo related by bar->FOO[R2];
```

5.2.21
```
select many foos from instances of FOO;
```

5.2.22
```
transform FOO::tfm();
```

5.2.22.1
```
transform self.attr1 = FOO::tfm2();
```

5.2.22.2
```
transform param.param1 = FOO::tfm2();
```

5.2.23
```
while true
  // while loop
end while;
```

5.2.24
```
_debug _trace _on;
```

5.2.25
```
_debug _trace _off;
```

5.2.26
```
_debug _dump _on;
```

5.2.27
```
_debug _dump _off;
```

5.2.28
```
_debug _sor _on;
```

5.2.29
```
_debug _sor _off;
```

5.2.30
```
_debug _on;
```

5.2.31
```
_debug _off;
```

5.2.32
```
_debug _stat;
```

5.2.33
```
cond = ( true or false );
```

5.2.34
```
cond = ( true and false );
```

5.2.35
```
cond = ( not true );
```

5.2.36
```
x = ( cardinality foos );
```

5.2.37
```
cond = ( not_empty foos );
```

5.2.38
```
cond = ( empty foos );
```

5.2.39
```
x = ( param.param1 );
```

5.2.40
```
x = ( rcvd_evt.param1 );
```

5.2.41
```
self.op();
```

5.3 Test model

The previous list of statements was tested through a test model [[2.2]](#2.2).
Comments have been included in action bodies to associate lines of OAL with the
specific cases listed in section 5.2.

5.4 Results

5.4.1 Missing proposals

Each row in the following table represents a location in the grammar where
keyword proposals are missing. Some rows refer to multiple use cases from
section 5.2. This is because in some cases, the use cases overlap in the branch
of the parse tree they cover.

| ID         | Keyword     | Use Case(s) |
|------------|-------------|-------------|
| 5.4.1.1    | `assign`    | 5.2.1       |
| 5.4.1.2    | `bridge`    | 5.2.4, 5.2.4.1, 5.2.4.2 |
| 5.4.1.2.1  | `self`      | 5.2.4.1     |
| 5.4.1.2.2  | `param`     | 5.2.4.2     |
| 5.4.1.3    | `to`        | 5.2.5       |
| 5.4.1.3.1  | `self`      | 5.2.5.1     |
| 5.4.1.3.2  | `param`     | 5.2.5.2     |
| 5.4.1.4    | `of`        | 5.2.7       |
| 5.4.1.5    | `of`        | 5.2.8, 5.2.9, 5.2.10, 5.2.11 |
| 5.4.1.6    | `to`        | 5.2.8, 5.2.8.1, 5.2.8.2, 5.2.8.3, 5.2.9, 5.2.10, 5.2.11, 5.2.14 |
| 5.4.1.7    | `assigner`  | 5.2.10      |
| 5.4.1.8    | `in`        | 5.2.13      |
| 5.4.1.9    | `to`        | 5.2.16      |
| 5.4.1.10   | `across`    | 5.2.16      |
| 5.4.1.11   | `using`     | 5.2.16, 5.2.16.1 |
| 5.4.1.12   | `from`      | 5.2.17      |
| 5.4.1.13   | `across`    | 5.2.17      |
| 5.4.1.14   | `using`     | 5.2.17, 5.2.17.1 |
| 5.4.1.15   | `transform` | 5.2.22, 5.2.22.1, 5.2.22.2 |
| 5.4.1.15.1 | `self`      | 5.2.22.1    |
| 5.4.1.15.2 | `param`     | 5.2.22.2    |
| 5.4.1.16   | `_debug`    | 5.2.24, 5.2.25, 5.2.26, 5.2.27, 5.2.28, 5.2.29, 5.2.30, 5.2.31, 5.2.32 |
| 5.4.1.17   | `_trace`    | 5.2.24, 5.2.25 |
| 5.4.1.18   | `_dump`     | 5.2.26, 5.2.27 |
| 5.4.1.19   | `_sor`      | 5.2.28, 5.2.29 |
| 5.4.1.20   | `_on`       | 5.2.24, 5.2.26, 5.2.28, 5.2.30 |
| 5.4.1.21   | `_off`      | 5.2.25, 5.2.27, 5.2.29, 5.2.31 |
| 5.4.1.22   | `_stat`     | 5.2.32      |
| 5.4.1.23   | `or`        | 5.2.33      |
| 5.4.1.24   | `and`       | 5.2.34      |
| 5.4.1.25   | `rcvd_evt`  | 5.2.40      |

5.4.2 Other notes

5.4.2.1 Proposal for `break` is only available in `for` or `while` loop.  
5.4.2.2 Proposal for `continue` is only available in `for` or `while` loop.  

5.4.5 Analysis of missing proposals

There are 31 missing keyword proposals.

17 of these are legitimate missing proposals:  
5.4.1.2.1, 5.4.1.2.2, 5.4.1.3, 5.4.1.3.1, 5.4.1.3.2, 5.4.1.4, 5.4.1.5, 5.4.1.6,
5.4.1.8, 5.4.1.9, 5.4.1.10, 5.4.1.11, 5.4.1.12, 5.4.1.13, 5.4.1.14, 5.4.1.15.1,
and 5.4.1.15.2.  
These ones should be added.

12 of these are left out by design:  
5.4.1.1, 5.4.1.2, 5.4.1.7, 5.4.1.15, 5.4.1.16, 5.4.1.17, 5.4.1.18, 5.4.1.19,
5.4.1.20, 5.4.1.21, 5.4.1.22, and 5.4.1.25.  
These ones are left out because they represent keywords that are not recommended
to be used (such as `rcvd_evt`) or debug keywords.

2 of these might require more discussion:  
5.4.1.23 and 5.4.1.24.  
These two are conditional operators. They are left out because it would be
difficult to determine when they are needed in an expression (due to type
checking) so most of the time they would simply be added noise. It is
recommended that they remain left out unless requirements change.

### End
