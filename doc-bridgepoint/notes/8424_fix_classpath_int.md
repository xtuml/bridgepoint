---

This work is licensed under the Creative Commons CC0 License

---

# Stop hardcoding version in GPS Watch .classpath file
### xtUML Project Implementation Note



1. Abstract
-----------
This note explains a solution for the problem of having to hardcode the 
BridgePoint version number into the classpath for the GPS Watch example project.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8424](https://support.onefact.net/issues/8424) - Headline issue  
<a id="2.2"></a>2.2 [ANTLR plugin for Eclipse](http://antlreclipse.sourceforge.net/)  
<a id="2.3"></a>2.3 [HOWTO Update the BP version number](../process/HOWTO-update-the-BP-version-number.md)

3. Background
-------------
The BridgePoint example GPS Watch project includes hand-craft Java files as well
as the application model. The hand-craft java refers to generated Java classes 
inside the OOAofOOA.  In order to refer to these, the project's `.classpath` 
file points at `org.xtuml.bp.core/core.jar`.  There are three solutions, one is
to include `core.jar` inside the GPS Watch project.  Another option is to give 
the user insructions to modify the GPS Watch project classpath to point at 
`core.jar` (this is what was done previously).  The last (and currently used) 
option is to refer to `core.jar` inside the installed BridgePoint.  The drawback
of this approach is that the BridgePoint installation on disk is versioned. 
Thus, the `.classpath` file includes a BridgePoint version number in the path. 
This is cumbersome as it requires updating every time we increment the 
BridgePoint version.  

4. Requirements
---------------
4.1  Provide an method of referring to `core.jar` in the class path that does 
  not require user configuration.  
4.2  Provide an method of referring to `core.jar` in the class path that does 
  not refer to a hard-coded version.  

5. Work Required
----------------
5.1 Investigate a solution  
5.1.1  We tried using wildcards in place of the version number.  All attempts 
  failed.  Google searches all showed either JARs included in the project or 
  specific version references.  
5.1.2  We noticed that the project classpath setup preferences included a pre-
  defined classpath variable named `ANTLR_HOME` that referred to a jar file 
  inside the eclipse installation. This variable provides exactly the same 
  functionality we are looking for here.      

5.2 Pulled the source code for antlreclipse [2.2] and grep'ed the code to see
  how `ANTLR_HOME` is configured.  It is done programmatically when the plugins
  start.  There is some code that sets this classpath variable inside the Java
  core infrastructure of the current runtime.  
  
5.3 Our implementation performs the same functionality.  We now create a
  classpath variable `BP_CORE_HOME` when the core plugin starts.  This variable
  contains a path to `core.jar` that is automatically calculated at plugin
  startup.  
  
5.4 The `org.xtuml.bp.welcome/models/GPS Watch.zip/.classpath` file is modified
  to use this variable.  
From:  
`<classpathentry kind="var" path="ECLIPSE_HOME/plugins/org.xtuml.bp.core_5.3.4/core.jar"/>`  
To:  
`<classpathentry kind="var" path="BP_CORE_HOME/core.jar"/>`     

5.5 There is one down-side to this approach.  When we launch BridgePoint from
  a debug configuration, the BP_CORE_HOME variable resolves to the source code
  path of `org.xtuml.bp.core` in the host environment.  Since the compiled 
  source here is always class files and not a packaged JAR file, the end result
  is that the classpath entry to `core.jar` does not resolve and must be 
  manually fixed if the developer wishes to perform Java builds on GPS Watch 
  inside the debug environment. Here is an example of the error:
  ```Project 'GPS Watch' is missing required library: '/home/kbrown/git2/bridgepoint/src/org.xtuml.bp.core/core.jar' ``` 

  Of course, due to the hardcoded version number, the path often had to be
  updated by the developer in the same scenario under the old method, so this
  is not an introduction of an error as much as it is simply a failure to fix
  this scenario.  
    
5.6 Update the models in the `models` and `modelsmg` repositories to use the 
  new variable.  Look for `core.jar` references in `.classpath` files and update
  them to the new line as in 5.4.  
  
5.7 Update the version bump HOWTO doc [2.3] to remove the instructions to update
  `GPS Watch.zip` for the version number change.  
  
6. Implementation Comments
--------------------------

7. Unit Test
------------
7.1 Automated Test   
7.1.1  Verify bp.welcome does not throw any classpath-related errors  

8. User Documentation
---------------------

9. Code Changes
---------------
Repository:  bridgepoint  
Branch name: 8424_fix_classpath  

<pre>
doc-bridgepoint/notes/8424_fix_classpath_int.md
doc-bridgepoint/process/HOWTO-update-the-BP-version-number.md
src/org.xtuml.bp.core/META-INF/MANIFEST.MF
src/org.xtuml.bp.core/arc/create_core_plugin_class.arc
src/org.xtuml.bp.welcome/models/GPS Watch.zip
src/org.xtuml.bp.welcome.test/src/org/xtuml/bp/welcome/test/WelcomePageTestMetamodel.java
src/org.xtuml.bp.welcome.test/src/org/xtuml/bp/welcome/test/WelcomePageTest.java
</pre>

Repository:  models  
Branch name: 8424_fix_classpath  

<pre>
applications/Operational Theatre/.classpath
applications/gps/GPS Watch/.classpath
applications/sumo/Sumo Simulator/.classpath
test/EERealizedStaticDataTestLibraryDuplicateRealizedEE/.classpath
test/EERealizedStaticDataTestLocalRealizedEE/.classpath
test/ExternalEntityRealizedTests/.classpath
test/RealizedClassOnwer/.classpath
test/RealizedClassTest/.classpath
test/RealizedComponentTest/.classpath
test/SR2569403761/.classpath
test/VIEC/.classpath
test/VerifierBindingTest/.classpath
test/VerifierMessageTestGlobals/.classpath
test/VerifierRealizedUDTTest/.classpath

</pre>

Repository:  modelsmg  
Branch name: 8424_fix_classpath  

<pre>
test/dts0100959004/.classpath
</pre>

End
---

