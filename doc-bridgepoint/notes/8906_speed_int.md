---

This work is licensed under the Creative Commons CC0 License

---

# Build Improvements for 5.8 Speed
### xtUML Project Implementation Note


1. Abstract
-----------
Speed up the build by allowing a simple OAL change to cause translation
of only a single class.

2. Document References
----------------------
[1] [8906](https://support.onefact.net/issues/8906) - Build Improvements for 5.8  

3. Background
-------------
Any change to the bp.core model causes the entirety of bp.core to be
translated and compiled.  There is no finer granularity than a core build.
A core build takes about 30 minutes on a typical machine.  bp.core is
translated in four separate chunks divided along package boundaries and
to be roughly evenly matched.  However, there is no mechanism to build
one of the fourths by itself.

The bp.core build is controlled by an `ant` file `generate.xml` which
calls an MC-Java ant file, `build.xml`.

4. Requirements
---------------
4.1 Stop using `${info.unique_num}` in generated OAL.  
`${info.unique_num}` depends upon how much RSL has been run before
each line.  This means that generated code can be different due to
build order and processing and not because of changes to model source.
This is not acceptable.  
4.2 Provide a mechanism to communicate a selected Package and Class
to the bp.core build process.  
4.3 Update MC-Java to know about a selected Package and Class.  
MC-Java must detect that generation for a specific Package and/or
Class has been requested and then reduce the build time using this
information.  

5. Work Required
----------------
5.1 Find usage of `${info.unique_num}` that affects OAL translation.
Provide a functional alternative using model artifact information.  
5.2 Establish a properties file (`generate.properties`) to be referenced
by the bp.core ant file.  Allow entry of Package and Class.  
5.3 Pass properties into MC-Java and conditionally translate sections
of model.  
  
6. Implementation Comments
--------------------------
6.1 With the supplied changes, a core build can run in roughly 5 minutes.
(down from 30 minutes).  The parse is now a significant amount of time.  
6.2 Consider update prebuilder to not parse and to emit a small fraction
of OAL.
  
7. Unit Test
------------
7.1 Build BridgePoint.  
7.2 Run it to be sure it launches.  
7.3 Edit the Description of Model Class.  
7.4 Add "Subsystem" and "Model Class" to bp.core/generate.properties.  
7.5 Build Project.  
7.6 Be sure only the `ModelClass_c.java` file was generated.  

8. Code Changes
---------------
<pre>

Fork: cortlandstarrett/bridgepoint
Fork: cortlandstarrett/mc

Branch name: 8906_speed

xtuml/bridgepoint
 doc-bridgepoint/notes/8906_speed_int.md   | 86 +++++++++++++++++++++++++++++++++++++++++++++++++++
 src/MC-Java/build.xml                     |  4 +++
 src/MC-Java/java.arc                      | 26 +++++++++++++---
 src/MC-Java/statement.inc                 | 21 +++++++------
 src/org.xtuml.bp.core/generate.properties |  2 ++
 src/org.xtuml.bp.core/generate.xml        | 14 ++++++++-
 6 files changed, 138 insertions(+), 15 deletions(-)

</pre>

End
---

