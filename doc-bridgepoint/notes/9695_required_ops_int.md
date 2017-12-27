---

This work is licensed under the Creative Commons CC0 License

---

# Required Operation MASL Editing Not Possible
### xtUML Project Implementation Note

### 1. Abstract

From [[2.1]](#2.1):

> When double clicking a required operation in the model explorer from an
> imported MASL domain you are presented with the MASL. If I have a new domain
> created in xtUML and do the same action you are presented with the required
> operation description editor. If you then go to the properties pane for the
> required operation in the new domain and attempt to edit using the Action
> Semantics Field property it does not open the editor. Therefore I am unable
> to write MASL for a required operation.

### 2. Document References

<a id="2.1"></a>2.1 [#9695 Required Operation MASL Editing Not Possible](https://support.onefact.net/issues/9695)  
<a id="2.2"></a>2.2 [#9943 Enhancement to add 'new xtUML-MASL project' functionality](https://support.onefact.net/issues/9943)  

### 3. Background

From [[2.1]](#2.1):

> I've found the cause of the issue. There is a workaround.
> 
> Because of the way the MASL idiom works, it is required for the description of
> the package containing the MASL domain or project component to contain either
> "masl_domain" or "masl_project" respectively. For example, a project with
> structure:
> 
> ```
> MASLDomain
> - MaslDomain (package)
>   - MaslDomain (component)
>   -Shared
> - types
> ```
> 
> The "MASLDomain" package should have "masl_domain" in the description field.
> This problem does not affect imported domains or domains built from
> copy/pasting from the MASL template domain because both of these paths already
> have "masl_domain" in the description, so it would only present when a domain
> is built from scratch.  The workaround is to unformalize the port from the
> interface, add "masl_domain" to the description of the package containing the
> domain component and then to reformalize the interface to the port.

### 4. Requirements

4.1 Provide a better mechanism than the "masl_domain" string to identify MASL
domains during interface formalization.  

### 5. Work Required

5.1 Overview

The current logic that affects this issue is as follows: If a modeler that is
editing in MASL (MASL dialect is set to the default), when an interface is
formalized, if it is a required interface in a MASL domain or if it is a
provided interface in a MASL project, set all the new activities' dialect to
"MASL". Otherwise, set it to "None". This logic prevents activities that should
not be created (i.e. provided operations and signals in domains) from being
created. To determine whether a modeler is editing a MASL domain or project, in
the past we have keyed off the "masl_project" or "masl_domain" string being
present in the containing package. This works for imported domains or domains
built from the MASL template project, but breaks down when a new domain is made
from scratch (unless the modeler manually enters the string in the description).

5.2 Solution

MASL modelers spend the majority of their time modeling domains. Knowing this
fact, the logic has been changed to assume that the project being edited is a
MASL domain unless the "masl_project" string exists. In this way, a new domain
with nothing in the package description will function properly as a MASL domain.

### 6. Implementation Comments

6.1 For the time being, for MASL projects, we will continue to rely on the
existence of the "masl_project" string. This will be clearly documented in the
MASL modeling guide. A future enhancement is proposed ([[2.2]](#2.2)) which
would improve the experience for projects and domains.

### 7. Unit Test

7.1 Test the scenario in [[2.1]](#2.1)  

### 8. User Documentation

8.1 A paragraph has been added to the MASL modeling guide in the help which
documents that projects must contain "masl_project" in the package description.

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9695_required_opt  

<pre>

 doc-bridgepoint/notes/9695_required_ops_int.md                                                                  | 108 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Interface Reference/Interface Reference.xtuml |   4 ++--
 src/org.xtuml.bp.doc/Reference/MASL/MASLConversionGuide/MASLConversionGuide.md                                  |   7 +++++++
 3 files changed, 117 insertions(+), 2 deletions(-)

</pre>

### End

