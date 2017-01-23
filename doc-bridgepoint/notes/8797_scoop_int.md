---

This work is licensed under the Creative Commons CC0 License

---

# Provide Data for MASL Editor 
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the implementation of the "scooper", a tool to automatically
generate MASL model data for use internally by the MASL editor for validation 
purposes.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8797](https://support.onefact.net/issues/8797) Headline issue.     
<a id="2.2"></a>2.2 [BridgePoint DEI #9032](https://support.onefact.net/issues/9032) Enhance the MASL refresher/scooper.   
<a id="2.3"></a>2.3 [8797 Design Note](https://github.com/cortlandstarrett/mc/blob/master/doc/notes/8797_scoop_dnt.md)   
<a id="2.4"></a>2.4 [BridgePoint DEI #9018](https://support.onefact.net/issues/9018) The BridgePoint Build ID reads "Internal Build"   
<a id="2.5"></a>2.5 [BridgePoint DEI #9032](https://support.onefact.net/issues/9032) Enhance the MASL refresher / scooper   
<a id="2.6"></a>2.6 [BridgePoint DEI #8801](https://support.onefact.net/issues/8801) MASL Round Trip Test     

3. Background
-------------
The user experience when creating a new MASL project is hampered by the 
requirement to manually export a domain or project.  The reason is that the MASL
editor does not have data to validate against. [[2.1]](#2.1)

4. Requirements
---------------
See [[2.3]](#2.3).   

5. Work Required
----------------
5.1  This work leverages the existing Java Pre-builder infrastructure and tool. But 
  it is invoked directly, we are not calling the project build chain.    

5.2  xtumlmc_build   
5.2.1  Add new parameters to xtumlmc_build   
   
| Flag         | Description                  |
|--------------|------------------------------|
| -xf          | Do not run the MASL formatter on output MASL |
| -xl          | Skip MASL activities.  This indicates we only want .mod, .prj, and .int files in the output |
   
5.2.2 The current implementation of ```-xl``` simply lets the existing masl export
  process produce the action language files then deletes them after the fact.  This
  is not optimal.  A follow-on issue is [[2.5]](#2.5) raised to re-evaluate this.   
  
5.3  org.xtuml.bp.x2m   
5.3.1  Add a new class ```Refresher``` that run the Java-based pre-build then 
  calls xtuml2masl with the arguments we want to not format MASL and skip the 
  MASL activities.   
5.3.2  Create a new listener ```MASLEditorPartListener``` that implements the 
  ```IPartListener2``` interface.  This listener is notified by the eclipse infrastructure
  when part lifecycle events happen.  Our new listener only cares about "open" 
  events.  When a part open event is received, we see if the part ID indicates this
  is the MASL editor.  If not, return immediately.  If the MASL editor is being
  opened, then we call the Refresher.   
5.3.3  It should be noted that the eclipse workbench __does not__ send part 
  open events at startup time for editors that were left open the last time
  eclipse shut down.     
5.3.4  The x2m plugin is modified to implement the IStartup interface so that it
  can add the new listener to the workbench when eclipse starts.  Otherwise the 
  eclipse lazy-loading scheme does not activate this plugin until the user 
  initiates an action, which we do not want as it is too late.   
  
6. Implementation Comments
--------------------------
6.1  This branch also contains a fix for [[2.4]](#2.4) to update the build ID
  on each build of BridgePoint with maven.    
  
6.2  We originally considered using a file scooping approach rather than using 
  existing java pre-build/exportSystem architecture.  This was implemented a 
  bit but we hit an issue with the Globals.xtuml integration and decided to just
  use java pre-build for now.  The code to do the file scooping is left in the 
  history of this branch, but is removed in the final version as it is not used
  and there were still some TODOs to make it work.   
   
6.3  Removed commented out code in ```ModelImportWizard.java``` that was attempting
  to export masl during import but had been commented out due to circular dependency.  This
  code is no longer needed.  It was associated with issue 8979.    
  
7. Unit Test
------------
7.1  Run the MASL Round Trip Test up to Import step 5.
* When the MASL is opened there are no red squigglies under elements in this domain.    
* Open navigator view and verify the ```.mod``` and ```.int``` files are inside the
models folder next to the SAC.xtuml that contains the C_C instance.   

8. User Documentation
---------------------
None.   

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint    
Branch: 8797_scoop    

<pre>

doc-bridgepoint/notes/8797_scoop_int.md

org.xtuml.bp.pkg/about.mappings
org.xtuml.bp.pkg/pom.xml

org.xtuml.bp.x2m/META-INF/MANIFEST.MF
org.xtuml.bp.x2m/src/org/xtuml/bp/x2m/X2MPlugin.java
org.xtuml.bp.x2m/src/org/xtuml/bp/x2m/generator/Generator.java
org.xtuml.bp.x2m/src/org/xtuml/bp/x2m/refresher/MASLEditorPartListener.java
org.xtuml.bp.x2m/src/org/xtuml/bp/x2m/refresher/Refresher.java
org.xtuml.bp.x2m/plugin.xml
</pre>

Fork/Repository: keithbrown/mc    
Branch: 8797_scoop    

<pre>

bin/xtuml_build

</pre>

End
---

