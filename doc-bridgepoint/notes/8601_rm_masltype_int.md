---

This work is licensed under the Creative Commons CC0 License

---

# Update types to remove xtUML global for MASLtype
### xtUML Project Implementation Note

1. Abstract
-----------
Early in MASL development, we introduced a new global UDT "MASLtype". A type
"MASLtype" is also created by the MASL to xtUML converter. We need to remove
the global MASLtype and update the converter to derive other MASL types from
the MASLtype existing in the project. This is to prevent non-MASL users from
seeing MASLtype in the type chooser.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8601 Update types to remove xtUML global for MASLtype](https://support.onefact.net/issues/8601)  

3. Background
-------------

4. Requirements
---------------
4.1 'MASLtype' shall not appear as a built-in type in the BridgePoint type chooser  
4.2 Non-core types converted by `m2x` shall be UDTs derived from the 'MASLtype'
UDT created in the 'types' package  

5. Work Required
----------------
5.1 Remove 'MASLtype' from `Globals.xtuml`. This was a simple revert of a
previous commit where 'MASLtype' was introduced  
5.2 Update `IDLINK` `stitchID` in maslin to link to the created 'MASLtype' in
the project and not the built-in 'MASLtype'  
5.3 Update the `m2x` executable in the packaging repository  

6. Implementation Comments
--------------------------

7. Unit Test
------------
7.1 Open the type chooser in a non-MASL xtUML project. Ensure IPRs are
_disabled_.  
7.2 Verify that 'MASLtype' is not available in the type chooser.  

7.3 Select a user defined type from a converted MASL model.  
7.4 In the properties view, select "Basic > Core Type". Open the type chooser.  
7.5 Verify that this type is referring to 'MASLtype' in the system level 'types'
package.  

8. User Documentation
---------------------

9. Code Changes
---------------
Branch name: bridgepoint/8601_rm_masltype

<pre>

 src/org.xtuml.bp.pkg/globals/Globals.xtuml | 16 ----------------
 1 file changed, 16 deletions(-)

</pre>

Branch name: mc/8601_rm_masltype

<pre>

 model/maslin/gen/masl2xtuml_IDLINK_bridge.c | 33 +++++++++++++++++----------------
 1 file changed, 17 insertions(+), 16 deletions(-)

</pre>

Branch name: packaging/8601_rm_masltype

<pre>

 build/extra_files/m2x | Bin 597230 -> 597304 bytes
 1 file changed, 0 insertions(+), 0 deletions(-)

</pre>

End
---

