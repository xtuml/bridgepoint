---

This work is licensed under the Creative Commons CC0 License

---

# Multiple projects depending on same INT file not working
### xtUML Project Implementation Note

### 1. Abstract

A dependency MASL that is specified by two projects can only be resolved from
one project. The other project shows parse errors.

### 2. Document References

<a id="2.1"></a>2.1 [#10155 Multiple projects depending on same INT file not working](https://support.onefact.net/issues/10155)  
<a id="2.2"></a>2.2 [#9021 Improve mechanism for inter-domain references - Ensure .int (interface) files are provided to MASL editor](https://support.onefact.net/issues/9021)  
<a id="2.3"></a>2.3 [#9021 design note](9021_dependency_dnt.md)  
<a id="2.4"></a>2.4 [#9021 implementation note](9021_dependency_int.md)  

### 3. Background

This issue is a follow up bug fix of work done as part of [[2.2]](#2.2). The
user should read the existing engineering documentation ([[2.3]](#2.3),
[[2.4]](#2.4)) to get familiar with existing work.

The previous work done focused a lot on user experience and was not clear in its
requirements for the actually visibility behavior of dependencies. After the
work was promoted, a bug was observed where two projects were not able to have
visibility to the same dependency resource. Not only must this bug be addressed,
but it raised more questions about the visibility behavior.

The Xtext framework which the MASL editor is based on has a mechanism for
defining global scope. For the rest of this note, "visibility" will be
discussed in the sense that any given resource has "visibility" (i.e. can
access and reference) a defined set of other resources. The requirements will
specify rules for such visibility. Additionally, the reader should note that
there exists a special resource included in the MASL editor plugin itself which
contains definitions for data types built in to the MASL language itself. This
resource will be referred to as the builtin library.

### 4. Requirements

4.1 Any resource in the `models` folder of a project shall have visibility to
any other resource in the `models` folder of the project, any resource defined
by the project `.dependencies` file, and the builtin library.  
4.2 Any resource in the `masl` folder of a project shall have visibility to
any other resource in the `masl` folder of the project, any resource defined
by the project `.dependencies` file, and the builtin library.  
4.3 Any resource which is a referenced as a dependency by a project shall have
visibility to any of its referring projects' dependency resources. It shall also
have visibility to the builtin library.  
4.3.1 If the set of all dependencies of all referring projects contains two
dependency resources that have the same domain name but different locations on
disk, the second one will be ignored (it will not be visible), and a warning
shall be printed to the error log which notes that it has been ignored.  
4.4 The builtin library shall have no external visibility.  

### 5. Work Required

5.1 Update `MaslProjectDependencyProvider` to create only one container handle
for each dependency. This fixes the presenting behavior.  
5.2 Introduce a mechanism in `MaslProjectDependencyProvider` to allow
dependencies to have visibility to other sibling dependencies.  
5.3 Update the routine that builds the dependency maps to be able to
incrementally update.  
5.3.1 Add code which checks to see if the dependencies have changed before
updating.  
5.4 Re-work `DependencyData` as a singleton instance. Add a resource change listener
which marks the dependencies as changed when the `.dependencies` file is
modified.  

### 6. Implementation Comments

6.1 When the dependency mechanism was first introduced, it was decided that the
`.dependencies` file would be loaded from disk each time the MASL editor needed
to know dependencies. This was done for the purpose of simplicity. As it turns
out, the MASL editor needs this information a lot (maybe 10-20 times each time
an editor is opened). The typical pattern is that visibility information would
be stored in an in memory data structure and simply accessed by the editor. It
was decided that we needed to do something to be smarter and avoid loading the
file so many times. A mechanism was added so that the MASL editor can ask if the
file has changed before loading it again. In this way the in memory visibility
map is only rebuilt when a `.dependencies` file is changed.

### 7. Unit Test

This test assumes that the tester is using a brand new workspace and has
appropriately set the default dialect to MASL.

7.1 Setup  
7.1.1 Download the `test.zip` file attached to [[2.1]](#2.1).  
7.1.2 Unzip `test.zip` in a known location. From now on, this location will be
referred to as `<test_loc>`.  
7.1.3 Import `Domain1` and `Domain2` into a workspace using `Import > General >
Existing Projects into Workspace` and selecting the `test.zip` file.  
7.1.4 Open and clear the "Error Log" view.  

7.2 Simple test  
7.2.1 Navigate to and open the function `foo` in `Domain1`.  
7.2.2 Verify that there is a parse error on line 2.  
7.2.3 Navigate to the project within the workspace in a file explorer or
terminal instance that is outside of eclipse.  
7.2.4 Add a file called `.dependencies` at the root of the `Domain1` project
with the following contents:  
```
<test_loc>/utils/FILESYSTEM_OOA
```
replacing "<test_loc>" with the absolute path of `<test_loc>`.  
7.2.5 Refresh `Domain1`. Close and reopen `foo`.  
7.2.6 Verify that the parse error has disappeared.  

7.2.7 Navigate to and open the function `bar` in `Domain2`.  
7.2.8 Repeat steps 7.2.2 through 7.2.6 for `Domain2`.  

7.3 Test dependency of dependency  
7.3.1 Right click on `Domain1` and select "Project Preferences".  
7.3.2 In the "Dependencies" section add a dependency:  
```
<test_loc>/utils/LOG_OOA
```
replacing "<test_loc>" with the absolute path of `<test_loc>`.  
7.3.3 Close and reopen `foo`. Uncomment line 4.  
7.3.4 Verify that no parse errors appear.  

7.3.5 Repeat steps 7.3.1 through 7.3.4 for `Domain2`.  

7.4 Test conflict  
7.4.1 Close all editors.  
7.4.2 Add the following dependency to `Domain1`:  
```
<test_loc>/utils/REGEX_OOA
```
replacing "<test_loc>" with the absolute path of `<test_loc>`.  
7.4.3 Add the following dependency to `Domain2`:  
```
<test_loc>/utils/REGEX_OOA_2
```
replacing "<test_loc>" with the absolute path of `<test_loc>`.  
7.4.4 Open `foo`.  
7.4.5 Verify that two warnings are added to the Error Log which say "Ignoring
duplicate dependency ...".  
7.4.6 Open `bar`.  
7.4.7 Verify that no more warnings are added to the log.  
7.4.8 Uncomment line 5 in `foo` and `bar`.  
7.4.9 Verify that no parser errors appear.  

### 8. User Documentation

None.

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 10155_dependency  

<pre>

 doc-bridgepoint/notes/10155_dependency_int.md                                                                                                | 163 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/arc/create_core_plugin_class.arc                                                                                       |   6 +++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/DependencyData.java                                                                       | 361 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++----------------------------------------------------
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/IDependencyProvider.java                                                                  |  36 +++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/BridgePointProjectDependenciesPreferences.java                                    |   6 +--
 src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/dependency/MaslProjectDependencyProvider.java   | 357 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-----------------------------------------------------------
 src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/dependency/MaslProjectDependencyProvider.xtend_ | 138 ++++++++++++++++++++++++++++++++++++++--------------------
 src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/index/MaslWorkspaceProjectsState.xtend          |   9 +++-
 src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl/src/org/xtuml/bp/xtext/masl/dependency/MaslDependencyProvider.xtend               |  30 ++++++++-----
 9 files changed, 780 insertions(+), 326 deletions(-)

</pre>

### End

