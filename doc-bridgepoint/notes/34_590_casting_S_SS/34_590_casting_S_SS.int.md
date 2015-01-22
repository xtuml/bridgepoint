---

This work is licensed under the Creative Commons CC0 License

---

# Cast Nicer and Remove S_SS Usage
### xtUML Project Implementation Note

1. Abstract
-----------
Casting for demotion from real to integer was a little messy.  
A use of a specialized package, S_SS (Subsystem), was missed in the
migration to generic pacakges.

2. Document References
----------------------
[1] [BridgePoint DEI #34](https://support.onefact.net/redmine/issues/34)  
[2] [BridgePoint DEI #590](https://support.onefact.net/redmine/issues/590)  

3. Background
-------------
3.1  C allows demotion from real to integer with casting.  However, MC-3020
was using a lot of extra code to detect the situation.  
3.2  S_SS has been deprecated for several releases.  

4. Requirements
---------------

4.1  Return value demotion from real to integer will continue to be
supported.  
4.2  The detection of the need for a cast on the return value will be
simplified.  
4.3  The return cast will use an implementation data type rather than
generic ANSI C types.  
4.4  The use of S_SS will be completely removed from MC-3020.  

5. Work Required
----------------

5.1  Use `te_aba.ReturnDataType` rather than several action body
traversals in `q.smt.generate.arc`.  
5.2  Substitute EP_PKG navigation for S_SS in `m.class.arc`.  

6. Implementation Comments
--------------------------

7. Unit Test
------------
7.1  Manually test with GPS Watch or Microwave Oven.  
7.1.1  Bring in GPS Watch or Microwave Oven from the QuickStart.  
7.1.2  Place the following marks into `gen/class.mark`.  
```
.invoke MarkPEIsDefinedInData( "*", "Tracking", "*" )
.invoke MarkStaticInstancePopulation( "*", "Tracking", "*" )
.invoke MarkReadOnly( "*", "Tracking", "*" )
.invoke MarkPersistentClass( "*", "Tracking", "*" )
.invoke MarkNonPersistentClass( "*", "Tracking", "*" )
.invoke MarkClassToTask( "*", "Tracking", "*", 1 )
```
Note:  Use "Microwave Oven" instead of "Tracking" if testing on the Microwave
Oven model.  
7.1.3  Generate the code by building the project.  
7.1.4  Look at the code generation log in the Console to be sure no errors
are reported.  

8. Code Changes
---------------
<pre>

Branch name:  xtuml/mc/34_590_casting_S_SS,
              xtuml/bridgepoint/34_590_casting_S_SS,
              xtuml/models/34_590_casting_S_SS
Fork name:  cortlandstarrett (copy of mc, fork of others)

xtuml/bridgepoint
doc-bridgepoint/notes/34_590_casting_S_SS/*

xtuml/mc
m.class.arc
m.domain.arc
q.smt.generate.arc
t.smt.return.c

xtuml/models
VandMCtesting/mctest/ex2/*

</pre>

End
---

