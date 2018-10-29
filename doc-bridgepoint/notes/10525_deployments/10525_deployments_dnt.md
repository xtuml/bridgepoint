---

This work is licensed under the Creative Commons CC0 License

---

# Implement deployments
### xtUML Project Design Note

### 1. Abstract

This issue is raised to introduce new model elements to xtUML to support
bridging in a way that is more natural to modelers using MASL.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10525](https://support.onefact.net/issues/10525) Implement deployments  
<a id="2.2"></a>2.2 [Deployments design proposal](https://drive.google.com/open?id=1iyGsdaXuTgobcNbE-J1i3huwrQmOBP5x)  
<a id="2.3"></a>2.3 [Project SRS (internal document)](https://docs.google.com/document/d/14eUh9kFz_i3o7cYbi_L1BQ7EAxrAq38806vm3PkcnJ8/edit)  
<a id="2.4"></a>2.4 [#8544 Exceptions implementation note](../8544_exception_ui_int.md)  
<a id="2.5"></a>2.5 [#9958 Publish to interface implementation note](../9958_publish_interface/9958_publish_interface.int.md)  
<a id="2.6"></a>2.6 [#8277 Serial MASL specification](https://github.com/xtuml/mc/blob/master/doc/notes/8073_masl_parser/8277_serial_masl_spec.md)  

### 3. Background

See [[2.2]](#2.2) and [[2.3]](#2.3)

### 4. Requirements

Requirements have been copied from [2.3] and are shown here for convenience.

4.1 A new metamodel element shall be added to represent 'Deployment'  
4.1.1 Deployments shall be editable as any other packageable element (create,
delete, rename, move, copy/paste, etc.)  
4.2 BridgePoint shall allow the user to add domains to the deployment from
existing component definitions in the workspace  
4.3 BridgePoint shall allow the user to add domains to the deployment from
textual “.int” or “.mod” files  
4.4 BridgePoint shall allow the user to manually update (refresh) a domain if
the source domain definition changes  
4.5 A domain within a deployment shall not require its referenced element to
remain in the workspace after it has been added to the deployment  
4.6 Domains and terminators within a deployment shall be viewable from the model
explorer tree  
4.7 Terminator services shall be viewable from the model explorer tree as
children of their terminators  
4.7.1 Terminator services shall be editable by the MASL editor  
4.7.2 Terminator services shall have a property to be marked as excluded from
generation (currently existing MASL utilities do not export project terminator
services marked with dialect “none”, this shall be reviewed)  
4.8 MASL export shall be available for deployments  
4.8.1 When exporting a deployment, the tool shall generate “.prj” and “.tr”
files as it currently does  
4.9 Deployments shall be represented on the canvas. Domains, terminators, and
terminator services shall not have graphical elements.  
4.10 Design and implementation shall consider broader applications of
deployments beyond MASL system modeling  
4.11 MASL Documentation shall be updated to account for the changes made to the
MASL flow by this project  

### 5. Analysis

In order to fulfill the implementaiton requirements, 3 logical chunks must be
completed:

5.1 Adding a new packageable element

A new element and all the necessary plumbing must be added. This includes
updating graphical and CME PEI data, adding the component to the model itself
and setting up all the infrastructure to allow it to be editable in the ways
outlined in the requirements.

In [2.4], the `Exception` model element was added to BridgePoint. This changeset
can be used as a reference when completing this part.

5.2 Import of terminator definitions

The process of importing terminator definitions must be realized. For import
from existing component definitions, OAL routines must be defined to read
exisitng meta-model instances of "Port", "Requriment", "Provided Signal",
"Provided Operation", etc and create new instances with the same names, types,
etc.

For import from `.int` or `.mod` file, the file must be parsed into a useable
format first. The MASL import parser which converts MASL text to a string of
serial MASL [2.6] can be reused for this purpose.

A difficulty facing this feature and the concept of "refreshing" existing
terminators is how to gracefully updated existing elements without deleting
everything and starting from scratch. In [2.5], a similar feature was added to
create interface definitions from a set of domain functions. The logic and
lessons learned there shall be reused for this issue.

5.3 MASL export

The `x2m` MASL exporter must be updated to recognize deployments as a valid
element for emitting a MASL `.prj` definition. New functions must be added to
the exporter to traverse the new model and generate appropriate serial MASL.

### 6. Design

6.1

### 7. Design Comments

### 8. User Documentation

### End
