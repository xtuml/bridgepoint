---

This work is licensed under the Creative Commons CC0 License

---

# Segregate Tests from Source
### xtUML Project Implementation Note


1. Abstract
-----------
Our test plugins can and should live in a separate configuration
repository from the source plugins.  This is easy with git and github.
Also, Eclipse governance wants to control source code only and not
the tests and documentation.

2. Document References
----------------------
[1] [8942](https://support.onefact.net/issues/8942) - Segregate Tests from Source  

3. Background
-------------
All plugins that are part of BridgePoint have been stored in the same
single confuration repository.  Long ago this was CVS; now it is git
(on github).

4. Requirements
---------------
4.1 Segregate Test Plugins  
Allow BridgePoint to be built from source with only the source code
repository.  Keep testing, but store tests in a separate repository.

5. Work Required
----------------
5.1 Create new repository on github.  
5.2 Identify test plugins.  
5.3 Move test plugins to new repository (xtuml/bptest).  
5.4 Delete test plugins from source code repository (xtuml/bridgepoint).  
5.5 Update Developer Getting Started to explain usage of tests repository.  

6. Implementation Comments
--------------------------
6.1 bp.bld.  
Two plugins were discovered that are not used any more since the
move to maven.  We can delete `org.xtuml.bp.bld.pkg` and
`org.xtuml.bp.bld.pkg-feature`.  

7. Unit Test
------------
7.1 Build BridgePoint successfully.  
7.2 Run it to be sure it launches.  

8. Code Changes
---------------
<pre>

Fork: cortlandstarrett/bridgepoint
      cortlandstarrett/bptest

Branch name: 8942_tests

xtuml/bridgepoint
deleted
org.xtuml.bp.bld.pkg/
org.xtuml.bp.bld.pkg-feature/
org.xtuml.bp.als.oal.test/
org.xtuml.bp.core.test/
org.xtuml.bp.debug.ui.test/
org.xtuml.bp.io.mdl.test/
org.xtuml.bp.model.compare.test/
org.xtuml.bp.search.test/
org.xtuml.bp.test/
org.xtuml.bp.ui.canvas.test/
org.xtuml.bp.ui.explorer.test/
org.xtuml.bp.ui.properties.test/
org.xtuml.bp.ui.text.test/
org.xtuml.bp.welcome.test/
org.xtuml.internal.test/

xtuml/bptest
added
org.xtuml.bp.als.oal.test/
org.xtuml.bp.core.test/
org.xtuml.bp.debug.ui.test/
org.xtuml.bp.io.mdl.test/
org.xtuml.bp.model.compare.test/
org.xtuml.bp.search.test/
org.xtuml.bp.test/
org.xtuml.bp.ui.canvas.test/
org.xtuml.bp.ui.explorer.test/
org.xtuml.bp.ui.properties.test/
org.xtuml.bp.ui.text.test/
org.xtuml.bp.welcome.test/
org.xtuml.internal.test/

</pre>

End
---

