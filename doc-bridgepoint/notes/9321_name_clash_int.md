---

This work is licensed under the Creative Commons CC0 License

---

# Terminator in domain in project with same name as another domain doesn't get .tr files exported
### xtUML Project Implementation Note

1. Abstract
-----------
As title

2. Document References
----------------------
<a id="2.1"></a>2.1 [#9321 Terminator in domain in project with same name as another domain doesn't get .tr files exported](https://support.onefact.net/issues/9321)  

3. Background
-------------

The problem is in the `C_CsetActions` function in the OOA of OOA. This function
selects a port within a component based on the name of the terminator parsed
from the `.masl` file. In MASL projects, provided ports which correspond to
project terminators are named `<domain>__<terminator>`, and required ports which
map to the public services for each domain are named simply `<domain>`. In this
situation where a terminator in one domain matches the name of another domain,
when `C_CsetActions` searches the collections of ports for one which matches the
terminator name, it finds the port which represents the public services of the
other domain.

The current algorithm is to select for a port called `<terminator>`. If it is
found, it is a terminator in a domain component. If it is not found, select a
port called `<domain>__<terminator>` if this is found, it is a terminator in a
project component. If neither is found it is an error. This algorithm is
susceptible to the problem described above, because in a project component, the
first selection could find a port that is not the correct one. To solve this
problem, the order of selections should be reversed: first select for
`<domain>__<terminator>` and if not found, then search for `<terminator>` only.
That way, false positives in project components can be avoided.

4. Requirements
---------------
4.1 Terminator services shall be exported when another domain in the project has
the same name as the terminator  

5. Work Required
----------------
5.1 Reverse the order of selection of the port instance in `C_CsetActions`  

6. Implementation Comments
--------------------------
None

7. Unit Test
------------
7.1 MASL round trip with `9321_name_clash` test case
7.2 Round trip regression test

8. User Documentation
---------------------
None

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint  
Branch: 9321_name_clash  

<pre>

</pre>

End
---

