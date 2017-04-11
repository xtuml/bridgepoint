---

This work is licensed under the Creative Commons CC0 License

---

# No default marking files
### xtUML Project Implementation Note

1. Abstract
-----------
The default marking files are not being populated into a new C project's `gen/`
folder.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#9040 No default marking files](https://support.onefact.net/issues/9040)  
<a id="2.2"></a>2.2 [#8495 Update to eclipse.org infrastructure style build](https://support.onefact.net/issues/8495)  

3. Background
-------------
This problem was introduced when the build was updated to work with Maven and
the mc tools were factored out of the separate plugins. See [[2.2]](#2.2)

4. Requirements
---------------
4.1 The default marking files shall be populated into the `gen/` folder for new
xtUML projects with a model compiler other than "None"  

5. Work Required
----------------
5.1 Updated `AbstractNature` to look in the `tools/mc/schema` folder for the
manifest file instead of the `mc3020` directory in the plugin itself

6. Implementation Comments
--------------------------
None

7. Unit Test
------------
7.1 Create a new project with the C source model compiler  
7.2 Verify that the `gen/` folder contains the default marking files  

8. User Documentation
---------------------
None

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint
Branch: 9040_mark_files

<pre>

 src/org.xtuml.bp.mc/src/org/xtuml/bp/mc/AbstractNature.java | 22 +++++++++-------------
 1 file changed, 9 insertions(+), 13 deletions(-)

</pre>

End
---

