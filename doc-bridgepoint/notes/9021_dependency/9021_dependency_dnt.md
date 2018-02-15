---

This work is licensed under the Creative Commons CC0 License

---

# Improve mechanism for inter-domain references - Ensure .int (interface) files are provided to MASL editor 
### xtUML Project Design Note


### 1. Abstract

This note describes the design for an improvement to the way inter-domain
dependencies are managed inside BridgePoint.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9021](https://support.onefact.net/issues/9021) Headline DEI    
<a id="2.2"></a>2.2 [BridgePoint DEI #9679](https://support.onefact.net/issues/9679) Headline SR    
<a id="2.3"></a>2.3 [Prototype implementation for automatic generation](https://github.com/xtuml/mc/pull/289)   

### 3. Background

3.1  Cross references  
MASL models often refer to public model elements from other domains.  Domains
publish their publicly-accessible elements in files named `<domain>.int`.  The
dependent domain's MASL then refers to these elements using the form 
`<domain>::<element>`.  BridgePoint currently requires these `.int` files to be 
copied by the user into the `models/` folder of the project that wants to use 
the elements inside the `.int`. Copying these files around is a pain for the user.

There are only two types of element that can be referred to across domains: types 
and services. 

3.2  Types  
Types can be referred to in two ways:  
* Declared as a transient variable in an action body.  
* A structural type reference type, encoded with a special name of the type itself (e.g. `MyDom::MyType`). 
Note that the idiom that BridgePoint currently enforces requires that the modeler 
create a local xtUML type that mirrors the type found in the other domain. This 
is to allow the MASL export to produce properly constructed MASL in the face of 
BridgePoint not having type references as first class model elements. 

3.3  Services  
Services can be referred to in only one way: invoked in an action body.  

3.4  Usage    
Models will refer to external types and services. MASL users tend to
invoke public services from other domains directly when it is used as a
library (e.g. the service domains like Logging and Filesystem).  

3.5  Terminology clarification  
The word "project" is overloaded in MASL-xtUML modeling.  Throughout this document
references to "project", "eclipse project", or "xtUML project" all refer to a directory
on disk that contains a `.project` XML file that allows for the display and management
of the project inside the BridgePoint/eclipse framework.  

A "MASL Project" is a special model that contains components and component references
wired together to from a cohesive system in the MASL sense of interconnected domains.
Hence, in the MASL idiom inside BridgePoint, a project may contain a MASL Project or 
a MASL Domain.  But more generally, a project may also contain an xtUML model, a C
or Java application, or just a set of files.   

### 4. Requirements

4.1  The user shall be able to specify a list of directories that may contain
  interface files to use when validating the MASL model inside the current project.  
4.1.1  The list shall be configurable for each project.  
4.1.2  The list shall be persisted in a file under the project.  
4.1.3  User shall be able to specify an absolute path for a dependent directory  
4.1.4  User shall be able to specify a workspace-relative path for a dependent directory   

4.2  When the current model is being validated, it shall assure that validation 
  is performed against up-to-date dependencies.    

4.3  The contents of the dependent folders shall not be persisted as part of the
  configuration management of the depending project.  
  
4.4  The contents of the dependent folders shall not be exported to the current
  project's `masl/` folder as part of "Export MASL [Domain|Project]".  

### 5. Analysis

5.1 Original BridgePoint MASL Parser Implementation  
5.1.1  In the original (current) implementation, the xText tooling is restricted
  to only look for MASL to parse and validate under the `models/` folder of the
  project.   
5.1.2  This restriction in the MASL parser is implemented in commits 
  [34197ec](https://github.com/xtuml/bridgepoint/commit/34187ec2b37800b3ba35988c44535933a25b1a0f) 
  and [fbc9e5d](https://github.com/xtuml/bridgepoint/commit/fbc9e5dae5a04b6bfc26433a8e2dcf4b63ad5cc0).   
5.1.2.1  Of specific interest is the file `org.xtuml.bp.xtext.masl/src/org/xtuml/bp/xtext/masl/lib/MASLDelegatingAllContainerState.xtend` 
  and the function `getVisibleContainerHandles()` and the setup of `newVisibleHandles`.  

5.2  Comparable Implementations  
5.2.1  Eclipse CDT and the specification of linker arguments (-L and -l)  
5.2.1.1  Linker arguments are accessed via the project properties (Figure 1)   
5.2.1.2  The linker dependencies are not shown in the UI tree view of the project  
5.2.1.3  The user may specify a file system or workspace-based variable path
![Figure 1](c_lib_setting.png)  
__Figure 1__  

5.2.1.4  The values set by the user are stored in the `<project>/.cproject` XML file under 
  the project in the linker section, like this:
 ```xml
 <tool id="cdt.managedbuild.tool.macosx.cpp.linker.macosx.exe.debug.472415824" name="MacOS X C++ Linker" superClass="cdt.managedbuild.tool.macosx.cpp.linker.macosx.exe.debug">
     <option id="macosx.cpp.link.option.paths.871898357" superClass="macosx.cpp.link.option.paths" valueType="libPaths">
         <listOptionValue builtIn="false" value="/Users/kbrown/tmp"/>
         <listOptionValue builtIn="false" value="&quot;${workspace_loc:/${ProjName}/gen}&quot;"/>
     </option>
``` 

5.2.2  Eclipse JDT and configuring the classpath   
5.2.2.1  Java project class path is also edited via the project properties (Figure 2)  
![Figure 2](java_lib_config.png)  
__Figure 2__  

5.2.2.2  The Package Explorer view shows the user-configured "Referenced Libraries" in
  a special way in the project tree (Figure 3).   
5.2.2.3  The user-configured values are persisted in `<project>/.classpath` file  
![Figure 3](jdt_libs_tree_classpath.png)  
__Figure 3__  



### 6. Design

TODO - this section is just a brain dump right now

- Reference the prototype work Cort did to scoop and create the .int

- If we shall support direct filesystem links (not to projects inside eclipse) 
then it seems to me the masl parser should refresh the current project before it 
runs as this is needed (either manually or programmatically) for the current 
project to "see" the latest version of the files on disk. 

- Use folder links.  Stored pretty cleanly in .project, which is good.  
  - Can currently be created with New > Folder > Advanced
  - This "brings in" all the content from the referred to folder.  We really only want the .int files loaded into memory for validation.
  - Put under models/?  This "just works" with the current xtext masl architecture
  - Put under .libs/ (.externals/, models/.dependencies/)?  May have to make xtext see this new folder?  See 5.1. 
  - Links can be absolute or workspace relative
  - Show the dependencies in the Model Explorer nicely somehow like JDT does. 
  - New CME that pops a dialog for configuring the dependency in a nicer way than New > Folder
  - What happens when we have a linked folder when there is no data?  Is it cool?  What about if an .int is deleted from a dependent folder?

```xml
 49     <linkedResources>
 50         <link>
 51             <name>models/servicedom</name>
 52             <type>2</type>
 53             <locationURI>WORKSPACE_LOC/services/interfaces</locationURI>
 54         </link>
 55         <link>
 56             <name>models/servicedom2</name>
 57             <type>2</type>
 58             <location>/Users/kbrown/tmp/servicedom2/ints</location>
 59         </link>
 60         <link>
 61             <name>models/servicedominvalid</name>
 62             <type>2</type>
 63             <location>/Users/kbrown/tmp/servicedominvalid</location>
 64         </link>
 65     </linkedResources>
```
__Links in a .project file__
### 7. Design Comments

None.  

### 8. User Documentation

TODO - updated docs are definitely necessary

### 9. Unit Test

TODO - exercise the functionality

### End
