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
<a id="2.7"></a>2.7 [BridgePoint SR #10320](https://support.onefact.net/issues/10320) Project Primus Documentation  
<a id="2.8"></a>2.8 [BridgePoint SR #11363](https://support.onefact.net/issues/11363) parse WASL project and render deployment  

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

In order to fulfill the implementation requirements, 3 logical chunks must be
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
existing meta-model instances of "Port", "Requirement", "Provided Signal",
"Provided Operation", etc and create new instances with the same names, types,
etc.

For import from `.int` or `.mod` file, the file must be parsed into a useable
format first. The MASL import parser which converts MASL text to a string of
serial MASL [2.6] can be reused for this purpose.

A difficulty facing this feature and the concept of "refreshing" existing
terminators is how to gracefully update existing elements without deleting
everything and starting from scratch. In [2.5], a similar feature was added to
create interface definitions from a set of domain functions. The logic and
lessons learned there shall be reused for this issue.

5.3 MASL export

The `x2m` MASL exporter must be updated to recognize deployments as a valid
element for emitting a MASL `.prj` definition. New functions must be added to
the exporter to traverse the new model and generate appropriate serial MASL.

### 6. Design

6.1 Deployment subsystem

A new subsystem "Deployment" was introduced to model deployments. Below is a
screenshot of the model.

![deployments.png](deployments.png)

6.1.1 Editing deployments

The design of the model for deployments takes into account both the requirements
that the model must be independent of source material (4.5) and that it should
be refreshable from source material (4.4). To preserve loose coupling between
the deployment and its source but also ensure that the deployment stays
reasonably in sync with the source, the model was designed so that very few
things are editable manually by the user.

In the model of deployments, a user may edit:
- The name of the deployment
- Description fields on deployments, terminators, terminator services, and
  terminator service parameters
- Action semantics of terminator services
- Parse indicator of terminator services
- Dialect of terminator services

Everything else is not editable directly by the user, but must be edited in the
source (port message in a domain or `.int` or `.mod` file). Furthermore,
terminator services and terminator service parameters may not be deleted by the
user. Terminators and deployments may be deleted. Stale services may be deleted
(see section 6.3.1).

6.1.2 Provided and required terminators

Two different types of terminators are supported by the new model. Provided
terminators represent all of the public services of a particular domain. These
are named with the same name as the domain and appear brown in the model
explorer. Services on provided terminators are automatically assigned the
"None" dialect. For MASL users, the dialect should always be "None" for provided
services, however this design leaves the model such that it may be repurposed
for future work.

Required terminators represent the terminators of a domain. They are named with
the name of the domain followed by `::` followed by the name of the terminator
itself as in: `Tracking::UI` and appear blue in the model explorer. Services on
required terminators are automatically assigned the dialect of the source
service or the workspace default dialect when necessary. Required services may
be marked to be excluded from generation by setting the dialect to "None".

6.2 Importing terminators into deployments

Terminators may be imported in one of two ways: by referring to an existing
domain component or by parsing a MASL model file (`.int` or `.mod`).

6.2.1 Importing from a component

A CME is created to allow the user to import terminator definitions from a
domain component in visibility. The CME allows the user to select multiple
components. The standard BridgePoint element chooser was reused to implement
this CME, however, the archetypes which generate it and support classes had to
be modified to allow multi-select from the chooser.

Once a component or components are selected to import, the action iterates over
the ports of the component. Required ports are mapped to required terminators in
the deployment where the name of the component is the domain name and the name
of the port is the terminator name. If the component has a provided port with
the same name as the component, the messages on this port are mapped to the
provided terminator for that domain. If there are no such provided ports, no
provided terminator is created.

6.2.2 Importing from MASL

Another CME is created to allow the user to import terminator definitions from a
file.

6.2.2.1 File browsing

Two new bridges in the `Utilities` EE were created to support file browsing.
`selectFiles` takes a string parameter `ext` and returns an object of type
`instance` (mapped to `Object` in Java). When invoked, a file browser is
brought up to the user. After the user selects one or more files and confirms
or cancels, the bridge returns an object which is an iterator (instance
`java.util.Iterator`) over the pathnames of the selected files. If `ext` is not
an empty string, it is used to filter only files of the given extension.
Another bridge `getNextString` has been implemented which takes a parameter
`string_iterator` and returns a string. If the input iterator has a "next"
string, it is returned, otherwise empty string is returned. These two bridges
can be used in a pattern like the following to print out the pathnames of all
selected files:

```
files = Util::selectFiles(ext:"");
file = Util::getNextString(string_iterator:files);
while ("" != file)
  LOG::LogInfo(message:file);
  file = Util::getNextString(string_iterator:files);
end while;
```

This pattern was used because MC-Java and xtUML in general do not have robust
support for collection types. This provides a good way to interface with hand
craft code that returns multiple elements and iterate over them without relying
on arrays or instance sets which are native in xtUML.

This mechanism was designed to be reusable in OAL for future features.

6.2.2.2 Parsing MASL

Once file names are provided by the file browsing mechanism, they must be
parsed. The MASL import parser used by `masl2xtuml` is leveraged for this
purpose. A new bridge `Utilities::parseMASLFile` was created for this purpose.
This bridge accepts a `filename` as input and returns an iterator of serial MASL
(SMASL) lines. The bridge uses the Java `ProcessBuilder` utility to launch the
standalone Java parser application and pipe its output back to the bridge. When
the process has terminated, the bridge returns an iterator over the list of
SMASL lines.

The `getNextString` bridge is used again to get the next line of SMASL from OAL
and along with the string methods `contains`, `indexof`, and `split`, the SMASL
spec is followed to populate terminators with instances parsed from the MASL
file. "terminator" definitions in MASL are mapped to required terminators and
domain level "service" definitions are mapped to terminator services in a
provided terminator named after the domain.

6.2.3 Type reference

Terminator services and service parameters may refer to types which are not
xtUML core types. In order to maintain loose coupling between the source and the
deployment itself, new types must be created if they are not found within the
deployment project. The standard way type references are created for MASL is by
creating a new xtUML UDT and setting its core type to be `MASLtype`. Public
domain types referenced by project terminators must be qualified with the name
of the domain as in `UI::GoalCriteria`.

The import mechanism searches for these types in a package called "Shared"
adjacent to the deployment itself. If they are not found, they are created.
`MASLtype` is expected to be in a package called "types" at the top level of
the model and is created if it is not found in that location.

6.2.4 Xtext MASL dependencies

Dependencies on domain services and types can be defined in the `.dependencies`
file or by using the editor from the "Project Preferences" pane. These
dependencies are used by the Xtext MASL editor to allow references to external
elements to be resolved in MASL action language. As a convenience feature, these
dependencies are automatically added to the `.dependencies` file when a provided
terminator is imported. With this automatic process, a modeler can immediately
start making invocations to provided terminator services from action language
once they are imported.

For terminators imported from a MASL file, an absolute path reference to the
selected file is added to the `.dependencies` file.

For terminators imported from a component, a dependency on a file named
`<Component_Name>.int` adjacent to the component source (`.xtuml`) file is added
to the `.dependencies` file. This new dependency uses the `WORKSPACE_LOC`
substitution variable, so it is not an absoulte filesystem path. This location
is the location where the `.int` file for the referenced domain will be
generated by the MASL refresher.

This functionality has been abstracted into two new bridges
`Utilities::addDependecy` which takes a System Model instance and a string and
`Utilities::addDomainDependency` which takes a System Model instance and a
Component instance. The interface `IDependencyProvider` and class
`DependencyData` have been updated to support these bridges.

6.3 Refreshing terminators from source material

Refreshing model elements is a very difficult problem. Since the signature of a
service is the identifying key, it can be difficult to identify which service
must be updated if the signature changes. The approach of this design is
conservative, trying to make good decisions when safe while not losing any
data. The algorithm is presented as follows in pseudocode:

```
A new terminator is created from source material.
if no terminator of the same name already exists:
  DONE
else
  mergeTerminators
end if

mergeTerminators
  Update attributes from new terminator
  for each existing service in old terminator:
    if the signature exactly matches the signature of a new service:
      mergeServices
    else if the name matches the name of a new service and no overloading is being used:
      mergeServices
    else
      mark the old service as stale
    end if
  end for
  move over any new services that were not already merged
  delete the new terminator (it will be empty because all services will be merged or moved)

mergeServices
  Update attributes from new service
  Update return type from new service (if applicable)
  for each existing parameter:
    if there is a parameter in the new list:
      if the name of the first new parameter is the same:
        mergeParameters
      else
        insert the new parameter before the existing parameter (and remove it from the new service definition)
      end if
    else
      delete the existing parameter
    end if
  end for
  for each new parameter left over:
    move the parameter to the end of the existing service parameter list
  end for
  delete the new service (all parameters and info have been merged)

mergeParameters
  Update attributes from new parameter
  Update type from new parameter
```

This algorithim is smart in a few ways to preserve IDs, but most importantly, it
will never lose important information. In particular, it is quite difficult to
tell the difference between a service which has been renamed (or signature
changed in another way) and a change where one service has been added and
another has been deleted. In a case like this, the danger would be losing action
language.

6.3.1 Stale services

In order to prevent action language from ever being wrongfully deleted, the
merge/refresh algorithm never deletes services but rather marks them as "stale"
if it thinks they may need to be removed. In the case of stale service being
created, the modeler must determine whether the service should be deleted or
whether the action language must be copied to the new version of the service.
Once the action language is copied, the stale service should be deleted.

Stale services will be clearly marked by the yellow triangular warning glyph. A
CME is provided on the terminator element to delete all stale services by right
clicking and selecting "Delete stale services". This CME is not available if
the terminator has no stale services. Stale services may also be deleted by
selecting the service itself and using the "Delete" CME.

This feature and how it should be used will be documented in the BridgePoint
documentation.

6.4 MASL export

The `x2m` application has been extended to support the new model elements. No
surprises were encountered here as MASL and SMASL already support all the
necessary elements. Provided terminators are ignored by the export as they have
no analog in a `.prj` file.

6.5 MASL convert/import

Although convert and import of MASL projects to xtUML deployments is not
required for this work, other former iUML users may need it in the future.

Also, it is desirable for deployments to become the default way of doing system
modeling for MASL users. If this happens it would be a good thing to deprecate
the non-preferred way of system modeling over time. Additionally, convert/import
will be required to test deployments as part of MASL round trip and we cannot
remove the old way of system modeling from the documentation as long as it is
the only way supported by convert/import.

The following is a "checklist" of the steps necessary to fully deprecate the
"project package" way of doing system modeling:

6.5.1 Phase 1: Fully support deployments

6.5.1.1 Implement deployments in `m2x`. Make deployments the default element
produced for system models.  
6.5.1.2 Assure that MASL round trip tests still pass.  

6.5.2 Phase 2: Deprecate old style system modeling (for MASL)

6.5.2.1 Remove references to the old way of doing system modeling from any MASL
documentation.  
6.5.2.2 Search and destroy all old style MASL system models in example models
(e.g. GPS Watch) and test models. Replace with deployments.  

6.5.3 Phase 3: Remove support for old style system modeling (for MASL)

6.5.3.1 Remove support for converting old style MASL system models from
`m2x`.  
6.5.3.2 Remove support for exporting old style MASL system models from `x2m`.  
6.5.3.3 Remove any related support code from BridgePoint.  

"Phase 1" at least is required for [[2.8]](#2.8). Consider also completing
"phase 2" and "phase 3" at the same time as part of that work.

### 7. Design Comments

7.1 MASL refresher

The MASL refresher had to be updated slightly to support deployments. While in
the area, the logic used to determine the output path was updated to use the
persistable model component file as a reference location which is slightly more
consistent than depending on the package structure pattern.

7.2 Automatically delete stale services

During design, there was a consideration to delete stale services automatically
when the action body and the description field are empty. Additionally a
preference could be supplied such that they are always deleted (and revision
control is relied upon to prevent data loss). This design decided against these
measures in an attempt to prevent the tool from doing anything "without
permission" from the user.

7.3 Automatic dependency maintenance

Section 6.2.4 describes the design for automatically adding dependencies for
imported provided terminators. This is not a part of the project requirements,
however it was observed to have a high value to cost ratio, so it has been
included. This feature allows a modeler to very quickly and easily begin writing
actions. Because this is not a project requirement, effort will not be spent
adding new tests.

7.4 Archetype changes

Several archetypes used to generate BridgePoint were changed as part of this
work.

7.4.1 `java.arc`

Changed so Java imports can be declared in a class description field

7.4.2 `chooser_elements_provider.inc`, `create_selection_dialog_action.inc`

IPR check added if the element chooser has no elements. If IPRs are not enabled
and the chooser is empty, show a dialog asking the user if IPRs should be
enabled. If "Yes", enable IPRs and attempt to populate the list again.

7.4.3 `create_core_plugin.inc`

Changed to allow "..." to be added to any CME label via the "extraEntryText"
field.

7.4.4 `create_core_plugin_class.arc`

Changed to special case "Terminator" and "Terminator Service" so different
icons are used for provided/required terminators and services.

7.4.5 `create_global_action.inc`

Fixed a bug in the delete action generation. The "canDeleteAction" method
inspects the current selection and returns a boolean value of whether or not the
selection may be deleted. Currently for a multiple element selection, only the
last element in the selection is checked for deletability. This wrongfully
allows some elements to be deleted if they are selected in a group with other
elements that may be deleted. The archetype was changed to do a logical "and" of
the boolean results of each element in the selection. This way if any element
may not be deleted, the whole selection may not be deleted.

7.4.6 `function_body.inc`

Changed to allow multiple selections in element chooser dialog.

7.4.7 `wfl_block.inc`

Removed block which references presumably deprecated model element. See the
following conversation from the xtUML Community chat:

> Levi Starrett: did we used to have a metamodel element with the key letters
> ACT_AT? perhaps "assign transient" or something  
>
> Keith Brown: I don't know but maybe could check old versions of the
> xtumlmc_schema.sql file in the mc repo.  
>
> Cort Starrett: ACT_AI  
>
> Levi: the code i'm looking at is from a very long time ago. I think
> it has been there since before 1F which makes it difficult to find accurate
> stuff from before then  
> Levi: yes, i think it is similar to act_ai but not act_ai  
>
> Cort: I just did.  ACT_AT was not in there (for 10 years).  
>
> Levi: was it not there ever?  
>
> Cort: I have a schema from March 2006.  
> Cort: Note that the 3020 schema did not have the action language in it that
> far back.  
>
> Levi: hmm this is really interesting  
>
> Cort: The key letters seem somehow familiar.  
>
> Levi: what it looks like from the code i'm viewing is that either 1) ACT_AI
> used to be ACT_AT and was renamed at some point or 2) ACT_AT and ACT_AI were
> two different flavors of assignment. not sure which  
> Levi: either way, this particular codepath has not been exercised in many many
> years otherwise we would have hit this issue sooner  
> Levi: pyrsl is complaining that there is not link from ACT_SMT->ACT_AT[R603]  
> Levi: in this case, i think it is safe to remove this block  

### 8. User Documentation

8.1 Documentation for this work will be incorporated into the documentation
provided as part of issue #10320 [[2.7]](#2.7).

8.2 The welcome project shall be changed to reflect the changes in the
metamodel.

8.3 Palette and context menu documentation shall be updated along with MASL
documentation.

### 9. Unit Test

9.1 Existing (passing) unit tests shall pass.  

9.2 Model edit test suites shall be updated to include Deployments.  
9.2.1 Some of these are generated automatically (e.g. Copy/Paste tests). In
these cases it will be verified that coverage is added for Deployments.  

9.3 Import from component definition unit test:  
9.3.1 A component may be imported into a deployment. All terminators, types, and
other elements are imported correctly.  
9.3.2 The deployment still works as expected when the source component is
removed from the workspace.  
9.3.3 Multiple components may be selected simultaneously in the chooser.  
9.3.4 If IPRs are disabled, a dialog appears asking if the user would like to
enable them.  

9.4 Import from file unit tests:  
9.4.1 A `.int` file may be imported into a deployment. All terminators, types,
and other elements are imported correctly.  
9.4.2 A `.mod` file may be imported into a deployment. All terminators, types,
and other elements are imported correctly.  

9.5 Terminator update unit tests:  
9.5.1 Several unit tests will be added to test updating terminators with various
changes applied. These will be clearly documented in the tests.  

9.6 Edit test (manual test):  
9.6.1 The items in the 6.1.1 list shall be editable.  
9.6.2 All other items shall not be editable.  

9.7 Export unit tests:  
9.7.1 A deployment may be exported to MASL.  
9.7.2 Services marked with the "None" dialect shall be excluded from export.  

9.8 GPS Watch test (manual test):  
9.8.1 The GPS Watch project will be changed to use deployments.  
9.8.2 The GPS Watch project shall generate MASL which compiles and runs as
expected.  

### End
