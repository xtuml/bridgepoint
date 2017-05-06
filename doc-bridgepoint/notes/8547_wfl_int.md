---

This work is licensed under the Creative Commons CC0 License

---

# Use create for WFL instead of SQL  
### xtUML Project Implementation Note

### 1. Abstract
Stop using the outdated idiom of generating instance data as SQL
and then importing them.  Create them.

### 2. Document References
<a id="2.1"></a>2.1 [BridgePoint DEI #8547](https://support.onefact.net/issues/8547) Use create for WFL instead of SQL  

### 3. Background
Currently, the workflow subsystem is generated as a population of
preexisting instances which are then (re)imported during a later step.
This can be accomplished using RSL `.create object instance`.


### 4. Requirements
4.1 Stop emitting SQL inserts for WFL.  
Stop formatting SQL `INSERT` statements for `W_WOF`, `W_CTI`, `W_STEP`,
`W_FLD` and `W_CFL`.

4.2 Create instances using `.create object instance`.  
Start creating instances of `W_WOF`, `W_CTI`, `W_STEP`, `W_FLD` and `W_CFL`
using the `.create object instance` RSL (and later OAL) statement.

4.3 Update Build  
Update the build steps and dependencies such that the extra import
step is now avoided.

### 5. Work Required
5.1 Create instead of INSERT  
In each spot where an `INSERT` statement was formatted, supply an
equivalent `.create object instance` statement.  Some logic needs
to be adjusted because of the way instances are related at load
time versus creation time.  Avoid creating an instance only to
learn later in the flow that it was not needed.  The previous logic
was able to "create" the instance as an `INSERT` statement but then
conditionally emit it.  The new logic must prevent creating an
instance that will then need to be deleted.

5.2 Build  
Remove the workflow PEI file and command line artifacts from the
generation and build flow.

### 6. Implementation Comments
This issue was more tricky and annoying than expected.  The original
estimation thought that a one-for-one substitution of `create` for `INSERT`
would yield an identical build on the first try.  However, the conditional
emission of the `INSERT` statements proved troublesome.  The first attempt
at resolving this involved deleting the newly created `W_STEP` when it
was found to be unneeded.  However, this did not work, because the creation
of the instance with correct referential keying interfered with the
association status of the related instances.  So, it was necessary to
provide logic to avoid creating the `W_STEP` instance altogether.  This
leaves the code looking strange, so a good comment is provided in that
routine.

### 7. Unit Test
7.1 Source Code Comparison  
7.1.1 Generate code using the master branch.  
7.1.2 Generate code using the engineering branch.  
7.1.3 Compare the generated code and see no functional differences.  

### 8. User Documentation

### 9. Code Changes

<pre>
Fork/Repository: cortlandstarrett/bridgepoint  
Branch: 8547_wfl  

 doc-bridgepoint/notes/8547_wfl_int.md   |  62 ++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/arc/wfl_block.inc | 111 +++++++++++++++++++++++++++++++++++++++++-------------
 src/org.xtuml.bp.core/arc/wfl_pop.arc   |  15 ++++-------
 src/org.xtuml.bp.core/generate.xml      |   3 +--
 4 files changed, 156 insertions(+), 35 deletions(-)

</pre>

End
---

